package mokiyoki.enhancedanimals.capability.post;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.VillageDoorInfo;
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
    public NBTBase writeNBT(Capability<IPostCapability> capability, IPostCapability instance, EnumFacing side) {
        NBTTagCompound compound = new NBTTagCompound();
        List<BlockPos> allPostBlockPos = instance.getAllPostPos();

        NBTTagList nbttaglist = new NBTTagList();

        for (BlockPos blockPos : allPostBlockPos)
        {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setInteger("X", blockPos.getX());
            nbttagcompound.setInteger("Y", blockPos.getY());
            nbttagcompound.setInteger("Z", blockPos.getZ());
            nbttaglist.appendTag(nbttagcompound);
        }

        compound.setTag("PostsPos", nbttaglist);

        return compound;
    }

    @Override
    public void readNBT(Capability<IPostCapability> capability, IPostCapability instance, EnumFacing side, NBTBase nbt) {
        NBTTagCompound compound = (NBTTagCompound) nbt;
        List<BlockPos> allPostBlockPos = new ArrayList<BlockPos>();
        NBTTagList nbttaglist = compound.getTagList("PostsPos", 10);

        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            BlockPos blockPosOfPost = new BlockPos(nbttagcompound.getInteger("X"), nbttagcompound.getInteger("Y"), nbttagcompound.getInteger("Z"));
            allPostBlockPos.add(blockPosOfPost);
        }

        instance.setAllPostPos(allPostBlockPos);
    }
}
