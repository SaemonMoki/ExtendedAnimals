package mokiyoki.enhancedanimals.capability.hay;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by saemon on 29/09/2018.
 */
public class HayCapabilityProvider implements IHayCapability, ICapabilitySerializable<INBT> {

    @CapabilityInject(IHayCapability.class)
    public static final Capability<IHayCapability> HAY_CAP = null;

    private final LazyOptional<IHayCapability> holder = LazyOptional.of(() -> this);

    List<BlockPos> hayBlockPositions = new ArrayList<>();

    @Override
    public List<BlockPos> getAllHayPos() {
        return hayBlockPositions;
    }

    @Override
    public void addHayPos(BlockPos blockPos) {
        hayBlockPositions.add(blockPos);
    }

    @Override
    public void removeHayPos(BlockPos blockPos) {
        hayBlockPositions.remove(blockPos);
    }

    @Override
    public void setAllHayPos(List<BlockPos> blockPosList) {
        this.hayBlockPositions = blockPosList;
    }

    @Nullable
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
        return HAY_CAP.orEmpty(capability, holder);
    }


    @Override
    public INBT serializeNBT() {
        return HAY_CAP.getStorage().writeNBT(HAY_CAP, this, null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        HAY_CAP.getStorage().readNBT(HAY_CAP, this, null, nbt);
    }
}
