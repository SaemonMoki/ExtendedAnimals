package mokiyoki.enhancedanimals.platform.registry;

import mokiyoki.enhancedanimals.platform.Services;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.Collection;
import java.util.function.Supplier;

public interface RegistrationProvider<T> {

    static <T> RegistrationProvider<T> get(ResourceKey<? extends Registry<T>> registryKey, String modId) {
        return Services.REGISTRATION.create(registryKey, modId);
    }

    <I extends T> RegistryEntry<T, I> register(String name, Supplier<? extends I> supplier);

    Collection<RegistryEntry<T, ? extends T>> getEntries();

    ResourceKey<? extends Registry<T>> getRegistryKey();

    String getModId();

    interface Factory {
        <T> RegistrationProvider<T> create(ResourceKey<? extends Registry<T>> registryKey, String modId);
    }
}
