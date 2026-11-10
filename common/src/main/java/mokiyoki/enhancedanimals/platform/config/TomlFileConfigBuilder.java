package mokiyoki.enhancedanimals.platform.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TomlFileConfigBuilder implements ConfigBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger("eanimod-config");

    private final Path file;
    private final List<String> currentSection = new ArrayList<>();
    private final Map<List<String>, List<String>> sectionComments = new LinkedHashMap<>();
    private final List<Entry<?>> entries = new ArrayList<>();
    private List<String> pendingComment = List.of();

    public TomlFileConfigBuilder(Path file) {
        this.file = file;
    }

    @Override
    public ConfigBuilder comment(String... comment) {
        this.pendingComment = Arrays.stream(comment)
                .flatMap(line -> Arrays.stream(line.split("\n")))
                .collect(Collectors.toList());
        return this;
    }

    @Override
    public ConfigBuilder push(String section) {
        this.currentSection.add(section);
        if (!this.pendingComment.isEmpty()) {
            this.sectionComments.put(List.copyOf(this.currentSection), this.pendingComment);
            this.pendingComment = List.of();
        } else {
            this.sectionComments.putIfAbsent(List.copyOf(this.currentSection), List.of());
        }
        return this;
    }

    @Override
    public ConfigBuilder pop() {
        if (this.currentSection.isEmpty()) {
            throw new IllegalStateException("pop() called without a matching push()");
        }
        this.currentSection.removeLast();
        return this;
    }

    @Override
    public ConfigValue<Boolean> define(String path, boolean defaultValue) {
        return add(path, defaultValue, List.of(), raw -> switch (raw) {
            case "true" -> true;
            case "false" -> false;
            default -> null;
        }, String::valueOf);
    }

    @Override
    public ConfigValue<Integer> defineInRange(String path, int defaultValue, int min, int max) {
        return add(path, defaultValue, List.of("Range: " + rangeText(min, max, Integer.MIN_VALUE, Integer.MAX_VALUE)), raw -> {
            try {
                long value = raw.contains(".") ? Math.round(Double.parseDouble(raw)) : Long.parseLong(raw.replace("_", ""));
                return value >= min && value <= max ? (int) value : null;
            } catch (NumberFormatException e) {
                return null;
            }
        }, String::valueOf);
    }

    @Override
    public ConfigValue<Double> defineInRange(String path, double defaultValue, double min, double max) {
        return add(path, defaultValue, List.of("Range: " + rangeText(min, max, -Double.MAX_VALUE, Double.MAX_VALUE)), raw -> {
            try {
                double value = Double.parseDouble(raw.replace("_", ""));
                return value >= min && value <= max ? value : null;
            } catch (NumberFormatException e) {
                return null;
            }
        }, String::valueOf);
    }

    @Override
    public <E extends Enum<E>> ConfigValue<E> defineEnum(String path, E defaultValue) {
        E[] constants = defaultValue.getDeclaringClass().getEnumConstants();
        String allowed = Arrays.stream(constants).map(Enum::name).collect(Collectors.joining(", "));
        return add(path, defaultValue, List.of("Allowed Values: " + allowed), raw -> {
            String name = unquote(raw);
            for (E constant : constants) {
                if (constant.name().equalsIgnoreCase(name)) {
                    return constant;
                }
            }
            return null;
        }, value -> quote(value.name()));
    }

    public void load() {
        if (!this.currentSection.isEmpty()) {
            throw new IllegalStateException("Config has unclosed sections: " + this.currentSection);
        }

        Map<List<String>, String> rawValues = new HashMap<>();
        if (Files.isRegularFile(this.file)) {
            try {
                rawValues = parse(Files.readAllLines(this.file, StandardCharsets.UTF_8));
            } catch (IOException | RuntimeException e) {
                LOGGER.error("Could not read config file {}, using defaults", this.file, e);
            }
        }

        for (Entry<?> entry : this.entries) {
            String raw = rawValues.get(entry.fullPath());
            if (raw != null && !entry.load(raw)) {
                LOGGER.warn("Invalid value '{}' for config option '{}' in {}, using the default", raw, entry.key, this.file);
            }
        }

        try {
            Files.createDirectories(this.file.getParent());
            Files.writeString(this.file, write(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGGER.error("Could not write config file {}", this.file, e);
        }
    }

    private <T> ConfigValue<T> add(String key, T defaultValue, List<String> extraComments, Function<String, T> parser, Function<T, String> writer) {
        List<String> comments = new ArrayList<>(this.pendingComment);
        comments.addAll(extraComments);
        this.pendingComment = List.of();
        Entry<T> entry = new Entry<>(List.copyOf(this.currentSection), key, comments, defaultValue, parser, writer);
        this.entries.add(entry);
        return entry;
    }

    private String write() {
        StringBuilder out = new StringBuilder();
        writeEntries(out, List.of(), "");
        for (Map.Entry<List<String>, List<String>> section : this.sectionComments.entrySet()) {
            List<String> path = section.getKey();
            String indent = "\t".repeat(path.size() - 1);
            if (!out.isEmpty()) {
                out.append('\n');
            }
            for (String comment : section.getValue()) {
                out.append(indent).append('#').append(comment).append('\n');
            }
            out.append(indent).append('[')
                    .append(path.stream().map(TomlFileConfigBuilder::key).collect(Collectors.joining(".")))
                    .append("]\n");
            writeEntries(out, path, indent + "\t");
        }
        return out.toString();
    }

    private void writeEntries(StringBuilder out, List<String> section, String indent) {
        for (Entry<?> entry : this.entries) {
            if (entry.section.equals(section)) {
                for (String comment : entry.comments) {
                    out.append(indent).append('#').append(comment).append('\n');
                }
                out.append(indent).append(key(entry.key)).append(" = ").append(entry.write()).append('\n');
            }
        }
    }

    static Map<List<String>, String> parse(List<String> lines) {
        Map<List<String>, String> values = new HashMap<>();
        List<String> section = List.of();
        for (String rawLine : lines) {
            String line = rawLine.strip();
            if (line.isEmpty() || line.startsWith("#")) {
                continue;
            }
            if (line.startsWith("[")) {
                int end = line.lastIndexOf(']');
                if (end < 0) {
                    throw new IllegalArgumentException("Bad section header: " + line);
                }
                section = splitKeyPath(line.substring(1, end).strip());
                continue;
            }

            Cursor cursor = new Cursor(line);
            List<String> keyPath = new ArrayList<>(section);
            keyPath.add(cursor.readKey());
            cursor.skipWhitespace();
            if (!cursor.consume('=')) {
                throw new IllegalArgumentException("Expected '=' in: " + line);
            }
            cursor.skipWhitespace();
            values.put(keyPath, cursor.readValue());
        }
        return values;
    }

    private static List<String> splitKeyPath(String text) {
        List<String> parts = new ArrayList<>();
        Cursor cursor = new Cursor(text);
        while (true) {
            cursor.skipWhitespace();
            parts.add(cursor.readKey());
            cursor.skipWhitespace();
            if (!cursor.consume('.')) {
                break;
            }
        }
        return List.copyOf(parts);
    }

    private static String key(String key) {
        return key.matches("[A-Za-z0-9_-]+") ? key : quote(key);
    }

    private static String quote(String text) {
        return '"' + text.replace("\\", "\\\\").replace("\"", "\\\"") + '"';
    }

    private static String unquote(String raw) {
        if (raw.length() >= 2 && (raw.startsWith("\"") && raw.endsWith("\"") || raw.startsWith("'") && raw.endsWith("'"))) {
            return raw.substring(1, raw.length() - 1);
        }
        return raw;
    }

    private static String rangeText(Number min, Number max, Number lowest, Number highest) {
        boolean noMin = min.equals(lowest);
        boolean noMax = max.equals(highest);
        if (noMin && noMax) {
            return "any";
        } else if (noMax) {
            return "> " + min;
        } else if (noMin) {
            return "< " + max;
        }
        return min + " ~ " + max;
    }

    private final class Entry<T> implements ConfigValue<T> {
        private final List<String> section;
        private final String key;
        private final List<String> comments;
        private final T defaultValue;
        private final Function<String, T> parser;
        private final Function<T, String> writer;
        private volatile T value;

        private Entry(List<String> section, String key, List<String> comments, T defaultValue, Function<String, T> parser, Function<T, String> writer) {
            this.section = section;
            this.key = key;
            this.comments = comments;
            this.defaultValue = defaultValue;
            this.parser = parser;
            this.writer = writer;
            this.value = defaultValue;
        }

        private List<String> fullPath() {
            List<String> path = new ArrayList<>(this.section);
            path.add(this.key);
            return path;
        }

        private boolean load(String raw) {
            T parsed = this.parser.apply(raw.strip());
            this.value = parsed != null ? parsed : this.defaultValue;
            return parsed != null;
        }

        private String write() {
            return this.writer.apply(this.value);
        }

        @Override
        public T get() {
            return this.value;
        }

        @Override
        public T getDefault() {
            return this.defaultValue;
        }

        @Override
        public String toString() {
            return String.join(".", fullPath()) + "=" + this.value;
        }
    }

    private static final class Cursor {
        private final String text;
        private int pos;

        private Cursor(String text) {
            this.text = text;
        }

        private void skipWhitespace() {
            while (this.pos < this.text.length() && Character.isWhitespace(this.text.charAt(this.pos))) {
                this.pos++;
            }
        }

        private boolean consume(char c) {
            if (this.pos < this.text.length() && this.text.charAt(this.pos) == c) {
                this.pos++;
                return true;
            }
            return false;
        }

        private String readKey() {
            if (this.pos >= this.text.length()) {
                throw new IllegalArgumentException("Missing key in: " + this.text);
            }
            char first = this.text.charAt(this.pos);
            if (first == '"' || first == '\'') {
                return readQuoted(first);
            }
            int start = this.pos;
            while (this.pos < this.text.length()) {
                char c = this.text.charAt(this.pos);
                if (!(Character.isLetterOrDigit(c) || c == '_' || c == '-')) {
                    break;
                }
                this.pos++;
            }
            if (start == this.pos) {
                throw new IllegalArgumentException("Bad key in: " + this.text);
            }
            return this.text.substring(start, this.pos);
        }

        private String readValue() {
            if (this.pos < this.text.length()) {
                char first = this.text.charAt(this.pos);
                if (first == '"' || first == '\'') {
                    return '"' + readQuoted(first) + '"';
                }
            }
            int end = this.text.indexOf('#', this.pos);
            String value = (end < 0 ? this.text.substring(this.pos) : this.text.substring(this.pos, end)).strip();
            this.pos = this.text.length();
            return value.toLowerCase(Locale.ROOT).equals("true") || value.toLowerCase(Locale.ROOT).equals("false")
                    ? value.toLowerCase(Locale.ROOT) : value;
        }

        private String readQuoted(char quote) {
            StringBuilder out = new StringBuilder();
            this.pos++;
            while (this.pos < this.text.length()) {
                char c = this.text.charAt(this.pos++);
                if (c == quote) {
                    return out.toString();
                }
                if (c == '\\' && quote == '"' && this.pos < this.text.length()) {
                    char escaped = this.text.charAt(this.pos++);
                    switch (escaped) {
                        case 'n' -> out.append('\n');
                        case 't' -> out.append('\t');
                        case 'r' -> out.append('\r');
                        default -> out.append(escaped);
                    }
                } else {
                    out.append(c);
                }
            }
            throw new IllegalArgumentException("Unterminated string in: " + this.text);
        }
    }
}
