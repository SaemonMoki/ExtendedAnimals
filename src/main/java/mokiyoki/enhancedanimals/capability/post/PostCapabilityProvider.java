package mokiyoki.enhancedanimals.capability.post;

import net.minecraft.nbt.INBTBase;
import net.minecraft.util.EnumFacing;
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
public class PostCapabilityProvider implements IPostCapability, ICapabilitySerializable<INBTBase> {

    @CapabilityInject(IPostCapability.class)
    public static final Capability<IPostCapability> POST_CAP = null;

    private final LazyOptional<IPostCapability> holder = LazyOptional.of(() -> this);

    List<BlockPos> postBlockPositions = new ArrayList<>();

    @Override
    public List<BlockPos> getAllPostPos() {
        return postBlockPositions;
    }

    @Override
    public void addPostPos(BlockPos blockPos) {
        postBlockPositions.add(blockPos);
    }

    @Override
    public void removePostPos(BlockPos blockPos) {
        postBlockPositions.remove(blockPos);
    }

    @Override
    public void setAllPostPos(List<BlockPos> blockPosList) {
        this.postBlockPositions = blockPosList;
    }

    @Nullable
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return POST_CAP.orEmpty(capability, holder);
    }


    @Override
    public INBTBase serializeNBT() {
        return POST_CAP.getStorage().writeNBT(POST_CAP, this, null);
    }

    @Override
    public void deserializeNBT(INBTBase nbt) {
        POST_CAP.getStorage().readNBT(POST_CAP, this, null, nbt);
    }
}
