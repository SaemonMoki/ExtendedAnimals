package mokiyoki.enhancedanimals.capability.post;

import net.minecraft.nbt.INBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by saemon on 29/09/2018.
 */
public class PostCapabilityProvider implements ICapabilitySerializable<INBTBase> {

    @CapabilityInject(IPostCapability.class)
    public static final Capability<IPostCapability> POST_CAP = null;

    private IPostCapability instance = POST_CAP.getDefaultInstance();

    private final LazyOptional<IPostCapability> holder = LazyOptional.of(() -> instance);

//    @Override
//    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
//        return capability == POST_CAP;
//    }

    @Nullable
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == POST_CAP ? holder.cast() : null;
    }


    @Override
    public INBTBase serializeNBT() {
        return POST_CAP.getStorage().writeNBT(POST_CAP, this.instance, null);
    }

    @Override
    public void deserializeNBT(INBTBase nbt) {
        POST_CAP.getStorage().readNBT(POST_CAP, this.instance, null, nbt);
    }
}
