package mokiyoki.enhancedanimals.capability.post;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by saemon on 29/09/2018.
 */
public class PostCapabilityProvider implements ICapabilitySerializable<NBTBase> {

    @CapabilityInject(IPostCapability.class)
    public static final Capability<IPostCapability> POST_CAP = null;

    private IPostCapability instance = POST_CAP.getDefaultInstance();

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == POST_CAP;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == POST_CAP ? POST_CAP.<T> cast(this.instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return POST_CAP.getStorage().writeNBT(POST_CAP, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        POST_CAP.getStorage().readNBT(POST_CAP, this.instance, null, nbt);
    }
}
