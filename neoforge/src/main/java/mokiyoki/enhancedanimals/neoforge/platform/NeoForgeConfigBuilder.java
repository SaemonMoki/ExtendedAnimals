package mokiyoki.enhancedanimals.neoforge.platform;

import mokiyoki.enhancedanimals.platform.config.ConfigBuilder;
import mokiyoki.enhancedanimals.platform.config.ConfigValue;
import net.neoforged.neoforge.common.ModConfigSpec;

public class NeoForgeConfigBuilder implements ConfigBuilder {

    private final ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

    public ModConfigSpec build() {
        return this.builder.build();
    }

    @Override
    public ConfigBuilder comment(String... comment) {
        this.builder.comment(comment);
        return this;
    }

    @Override
    public ConfigBuilder push(String section) {
        this.builder.push(section);
        return this;
    }

    @Override
    public ConfigBuilder pop() {
        this.builder.pop();
        return this;
    }

    @Override
    public ConfigValue<Boolean> define(String path, boolean defaultValue) {
        return wrap(this.builder.define(path, defaultValue));
    }

    @Override
    public ConfigValue<Integer> defineInRange(String path, int defaultValue, int min, int max) {
        return wrap(this.builder.defineInRange(path, defaultValue, min, max));
    }

    @Override
    public ConfigValue<Double> defineInRange(String path, double defaultValue, double min, double max) {
        return wrap(this.builder.defineInRange(path, defaultValue, min, max));
    }

    @Override
    public <E extends Enum<E>> ConfigValue<E> defineEnum(String path, E defaultValue) {
        return wrap(this.builder.defineEnum(path, defaultValue));
    }

    private static <T> ConfigValue<T> wrap(ModConfigSpec.ConfigValue<T> value) {
        return new ConfigValue<>() {
            @Override
            public T get() {
                return value.get();
            }

            @Override
            public T getDefault() {
                return value.getDefault();
            }
        };
    }
}
