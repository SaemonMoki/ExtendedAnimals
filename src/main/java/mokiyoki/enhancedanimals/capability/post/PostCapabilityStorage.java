package mokiyoki.enhancedanimals.capability.post;

import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
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
    public INBTBase writeNBT(Capability<IPostCapability> capability, IPostCapability instance, EnumFacing side) {
        NBTTagCompound compound = new NBTTagCompound();
        List<BlockPos> allPostBlockPos = instance.getAllPostPos();

        NBTTagList nbttaglist = new NBTTagList();

        for (BlockPos blockPos : allPostBlockPos)
        {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setInt("X", blockPos.getX());
            nbttagcompound.setInt("Y", blockPos.getY());
            nbttagcompound.setInt("Z", blockPos.getZ());
            nbttaglist.add(nbttagcompound);
        }

        compound.setTag("PostsPos", nbttaglist);

        return compound;
    }

    @Override
    public void readNBT(Capability<IPostCapability> capability, IPostCapability instance, EnumFacing side, INBTBase nbt) {
        NBTTagCompound compound = (NBTTagCompound) nbt;
        List<BlockPos> allPostBlockPos = new ArrayList<BlockPos>();
        NBTTagList nbttaglist = compound.getList("PostsPos", 10);

        for (int i = 0; i < nbttaglist.size(); ++i) {
            NBTTagCompound nbttagcompound = nbttaglist.getCompound(i);
            BlockPos blockPosOfPost = new BlockPos(nbttagcompound.getInt("X"), nbttagcompound.getInt("Y"), nbttagcompound.getInt("Z"));
            allPostBlockPos.add(blockPosOfPost);
        }

        instance.setAllPostPos(allPostBlockPos);
    }
}
