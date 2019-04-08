package mokiyoki.enhancedanimals.capability.egg;

import net.minecraft.nbt.INBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by saemon on 30/09/2018.
 */
public class EggCapabilityProvider implements IEggCapability, ICapabilitySerializable<INBTBase> {

    @CapabilityInject(IEggCapability.class)
    public static final Capability<IEggCapability> EGG_CAP = null;

//    private IEggCapability instance = EGG_CAP.getDefaultInstance();

    private final LazyOptional<IEggCapability> holder = LazyOptional.of(() -> this);

    private int[] genes;
    @Override
    public int[] getGenes() {
        return this.genes;
    }

    @Override
    public void setGenes(int[] genes) {
        this.genes = genes;
    }

    @Nullable
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return EGG_CAP.orEmpty(capability, holder);
    }

    @Override
    public INBTBase serializeNBT() {
        return EGG_CAP.getStorage().writeNBT(EGG_CAP, this, null);
    }

    @Override
    public void deserializeNBT(INBTBase nbt) {
        EGG_CAP.getStorage().readNBT(EGG_CAP, this, null, nbt);
    }
}
