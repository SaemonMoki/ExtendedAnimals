package mokiyoki.enhancedanimals.capability.hay;

import net.minecraft.nbt.Tag;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by saemon on 29/09/2018.
 */
public class HayCapabilityProvider implements IHayCapability, ICapabilitySerializable<Tag> {

    @CapabilityInject(IHayCapability.class)
    public static final Capability<IHayCapability> HAY_CAP = null;

    private final LazyOptional<IHayCapability> holder = LazyOptional.of(() -> this);

    Set<BlockPos> hayBlockPositions = new HashSet<>();

    @Override
    public Set<BlockPos> getAllHayPos() {
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
    public void setAllHayPos(Set<BlockPos> blockPosList) {
        this.hayBlockPositions = blockPosList;
    }

    @Nullable
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
        return HAY_CAP.orEmpty(capability, holder);
    }


    @Override
    public Tag serializeNBT() {
        return HAY_CAP.getStorage().writeNBT(HAY_CAP, this, null);
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        HAY_CAP.getStorage().readNBT(HAY_CAP, this, null, nbt);
    }
}
