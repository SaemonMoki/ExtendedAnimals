package mokiyoki.enhancedanimals.capability.post;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
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
    public INBT writeNBT(Capability<IPostCapability> capability, IPostCapability instance, Direction side) {
        CompoundNBT compound = new CompoundNBT();
        List<BlockPos> allPostBlockPos = instance.getAllPostPos();

        ListNBT nbttaglist = new ListNBT();

        for (BlockPos blockPos : allPostBlockPos)
        {
            CompoundNBT nbttagcompound = new CompoundNBT();
            nbttagcompound.putInt("X", blockPos.getX());
            nbttagcompound.putInt("Y", blockPos.getY());
            nbttagcompound.putInt("Z", blockPos.getZ());
            nbttaglist.add(nbttagcompound);
        }

        compound.put("PostsPos", nbttaglist);

        return compound;
    }

    @Override
    public void readNBT(Capability<IPostCapability> capability, IPostCapability instance, Direction side, INBT nbt) {
        CompoundNBT compound = (CompoundNBT) nbt;
        List<BlockPos> allPostBlockPos = new ArrayList<BlockPos>();
        ListNBT nbttaglist = compound.getList("PostsPos", 10);

        for (int i = 0; i < nbttaglist.size(); ++i) {
            CompoundNBT nbttagcompound = nbttaglist.getCompound(i);
            BlockPos blockPosOfPost = new BlockPos(nbttagcompound.getInt("X"), nbttagcompound.getInt("Y"), nbttagcompound.getInt("Z"));
            allPostBlockPos.add(blockPosOfPost);
        }

        instance.setAllPostPos(allPostBlockPos);
    }
}
