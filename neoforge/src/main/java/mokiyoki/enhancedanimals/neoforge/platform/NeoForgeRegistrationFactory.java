package mokiyoki.enhancedanimals.neoforge.platform;

import mokiyoki.enhancedanimals.platform.registry.RegistrationProvider;
import mokiyoki.enhancedanimals.platform.registry.RegistryEntry;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class NeoForgeRegistrationFactory implements RegistrationProvider.Factory {

    @Override
    public <T> RegistrationProvider<T> create(ResourceKey<? extends Registry<T>> registryKey, String modId) {
        DeferredRegister<T> register = DeferredRegister.create(registryKey, modId);
        register.register(modEventBus(modId));
        return new Provider<>(register, registryKey, modId);
    }

    static IEventBus modEventBus(String modId) {
        IEventBus bus = ModList.get().getModContainerById(modId)
                .orElseThrow(() -> new IllegalStateException("Mod " + modId + " is not loaded"))
                .getEventBus();
        if (bus == null) {
            throw new IllegalStateException("Mod " + modId + " has no event bus yet");
        }
        return bus;
    }

    private record Provider<T>(DeferredRegister<T> register, ResourceKey<? extends Registry<T>> registryKey, String modId, List<RegistryEntry<T, ? extends T>> entries) implements RegistrationProvider<T> {

        private Provider(DeferredRegister<T> register, ResourceKey<? extends Registry<T>> registryKey, String modId) {
            this(register, registryKey, modId, new ArrayList<>());
        }

        @Override
        public <I extends T> RegistryEntry<T, I> register(String name, Supplier<? extends I> supplier) {
            DeferredHolder<T, I> holder = this.register.register(name, supplier);
            RegistryEntry<T, I> entry = new RegistryEntry<>() {
                @Override
                public I get() {
                    return holder.get();
                }

                @Override
                public ResourceKey<T> getKey() {
                    return holder.getKey();
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
