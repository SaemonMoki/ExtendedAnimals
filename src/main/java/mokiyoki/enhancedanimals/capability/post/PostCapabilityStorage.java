package mokiyoki.enhancedanimals.capability.post;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.ListTag;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by saemon on 29/09/2018.
 */
public class PostCapabilityStorage implements IStorage<IPostCapability> {

    @Nullable
    @Override
    public Tag writeNBT(Capability<IPostCapability> capability, IPostCapability instance, Direction side) {
        CompoundTag compound = new CompoundTag();
        List<BlockPos> allPostBlockPos = instance.getAllPostPos();

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
    public void readNBT(Capability<IPostCapability> capability, IPostCapability instance, Direction side, Tag nbt) {
        CompoundTag compound = (CompoundTag) nbt;
        List<BlockPos> allPostBlockPos = new ArrayList<BlockPos>();
        ListTag nbttaglist = compound.getList("PostsPos", 10);

        for (int i = 0; i < nbttaglist.size(); ++i) {
            CompoundTag nbttagcompound = nbttaglist.getCompound(i);
            BlockPos blockPosOfPost = new BlockPos(nbttagcompound.getInt("X"), nbttagcompound.getInt("Y"), nbttagcompound.getInt("Z"));
            allPostBlockPos.add(blockPosOfPost);
        }

        instance.setAllPostPos(allPostBlockPos);
    }
}
