package mokiyoki.enhancedanimals.platform.config;

public interface ConfigBuilder {

    ConfigBuilder comment(String... comment);

    ConfigBuilder push(String section);

    ConfigBuilder pop();

    ConfigValue<Boolean> define(String path, boolean defaultValue);

    ConfigValue<Integer> defineInRange(String path, int defaultValue, int min, int max);

    ConfigValue<Double> defineInRange(String path, double defaultValue, double min, double max);

    <E extends Enum<E>> ConfigValue<E> defineEnum(String path, E defaultValue);
}
