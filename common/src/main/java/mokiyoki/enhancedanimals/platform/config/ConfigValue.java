package mokiyoki.enhancedanimals.platform.config;

import java.util.function.Supplier;

public interface ConfigValue<T> extends Supplier<T> {

    @Override
    T get();

    T getDefault();
}
