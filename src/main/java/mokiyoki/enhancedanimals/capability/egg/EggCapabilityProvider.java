package mokiyoki.enhancedanimals.capability.egg;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by saemon on 30/09/2018.
 */
public class EggCapabilityProvider implements ICapabilitySerializable<NBTBase> {

    @CapabilityInject(IEggCapability.class)
    public static final Capability<IEggCapability> EGG_CAP = null;

    private IEggCapability instance = EGG_CAP.getDefaultInstance();

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == EGG_CAP;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == EGG_CAP ? EGG_CAP.<T> cast(this.instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return EGG_CAP.getStorage().writeNBT(EGG_CAP, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        EGG_CAP.getStorage().readNBT(EGG_CAP, this.instance, null, nbt);
    }
}
