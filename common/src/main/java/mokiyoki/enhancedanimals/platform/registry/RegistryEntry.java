package mokiyoki.enhancedanimals.platform.registry;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public interface RegistryEntry<R, T extends R> extends Supplier<T> {

    @Override
    T get();

    ResourceKey<R> getKey();

    default ResourceLocation getId() {
        return getKey().location();
    }

    Holder<R> holder();
}
