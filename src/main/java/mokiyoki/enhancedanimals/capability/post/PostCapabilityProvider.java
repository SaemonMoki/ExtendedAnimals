package mokiyoki.enhancedanimals.capability.post;

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
import java.util.List;

/**
 * Created by saemon on 29/09/2018.
 */
public class PostCapabilityProvider implements IPostCapability, ICapabilitySerializable<Tag> {

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
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
        return POST_CAP.orEmpty(capability, holder);
    }


    @Override
    public Tag serializeNBT() {
        return POST_CAP.getStorage().writeNBT(POST_CAP, this, null);
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        POST_CAP.getStorage().readNBT(POST_CAP, this, null, nbt);
    }
}
