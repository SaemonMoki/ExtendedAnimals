package mokiyoki.enhancedanimals.capability.post;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
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

    public static Capability<IPostCapability> POST_CAP = CapabilityManager.get(new CapabilityToken<>() {});

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
        CompoundTag compound = new CompoundTag();
        List<BlockPos> allPostBlockPos = this.getAllPostPos();

        ListTag nbttaglist = new ListTag();

        for (BlockPos blockPos : allPostBlockPos)
        {
            CompoundTag nbttagcompound = new CompoundTag();
            nbttagcompound.putInt("X", blockPos.getX());
            nbttagcompound.putInt("Y", blockPos.getY());
            nbttagcompound.putInt("Z", blockPos.getZ());
            nbttaglist.add(nbttagcompound);
        }

        compound.put("PostsPos", nbttaglist);

        return compound;
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        CompoundTag compound = (CompoundTag) nbt;
        List<BlockPos> allPostBlockPos = new ArrayList<BlockPos>();
        ListTag nbttaglist = compound.getList("PostsPos", 10);

        for (int i = 0; i < nbttaglist.size(); ++i) {
            CompoundTag nbttagcompound = nbttaglist.getCompound(i);
            BlockPos blockPosOfPost = new BlockPos(nbttagcompound.getInt("X"), nbttagcompound.getInt("Y"), nbttagcompound.getInt("Z"));
            allPostBlockPos.add(blockPosOfPost);
        }

        this.setAllPostPos(allPostBlockPos);
    }
}
