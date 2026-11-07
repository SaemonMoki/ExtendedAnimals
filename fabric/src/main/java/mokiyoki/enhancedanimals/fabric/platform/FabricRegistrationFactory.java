package mokiyoki.enhancedanimals.fabric.platform;

import mokiyoki.enhancedanimals.platform.registry.RegistrationProvider;
import mokiyoki.enhancedanimals.platform.registry.RegistryEntry;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class FabricRegistrationFactory implements RegistrationProvider.Factory {

    @Override
    public <T> RegistrationProvider<T> create(ResourceKey<? extends Registry<T>> registryKey, String modId) {
        return new Provider<>(registryKey, modId);
    }

    private static final class Provider<T> implements RegistrationProvider<T> {
        private final ResourceKey<? extends Registry<T>> registryKey;
        private final String modId;
        private final List<RegistryEntry<T, ? extends T>> entries = new ArrayList<>();
        private Registry<T> registry;

        private Provider(ResourceKey<? extends Registry<T>> registryKey, String modId) {
            this.registryKey = registryKey;
            this.modId = modId;
        }

        @SuppressWarnings("unchecked")
        private Registry<T> registry() {
            if (this.registry == null) {
                Registry<?> found = BuiltInRegistries.REGISTRY.get(this.registryKey.location());
                if (found == null) {
                    throw new IllegalStateException("Unknown registry " + this.registryKey.location());
                }
                this.registry = (Registry<T>) found;
            }
            return this.registry;
        }

        @Override
        public <I extends T> RegistryEntry<T, I> register(String name, Supplier<? extends I> supplier) {
            ResourceKey<T> key = ResourceKey.create(this.registryKey, ResourceLocation.fromNamespaceAndPath(this.modId, name));
            I value = supplier.get();
            Holder<T> holder = Registry.registerForHolder(registry(), key, value);
            RegistryEntry<T, I> entry = new RegistryEntry<>() {
                @Override
                public I get() {
                    return value;
                }

                @Override
                public ResourceKey<T> getKey() {
                    return key;
                }

                @Override
                public Holder<T> holder() {
                    return holder;
                }
            };
            this.entries.add(entry);
            return entry;
        }

        @Override
        public Collection<RegistryEntry<T, ? extends T>> getEntries() {
            return Collections.unmodifiableList(this.entries);
        }

        @Override
        public ResourceKey<? extends Registry<T>> getRegistryKey() {
            return this.registryKey;
        }

        @Override
        public String getModId() {
            return this.modId;
        }
    }
}
