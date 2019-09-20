package mokiyoki.enhancedanimals.capability.hay;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by saemon on 29/09/2018.
 */
public class HayCapabilityStorage implements IStorage<IHayCapability> {

    @Nullable
    @Override
    public INBT writeNBT(Capability<IHayCapability> capability, IHayCapability instance, Direction side) {
        CompoundNBT compound = new CompoundNBT();
        Set<BlockPos> allHayBlockPos = instance.getAllHayPos();

        ListNBT nbttaglist = new ListNBT();

        for (BlockPos blockPos : allHayBlockPos)
        {
            CompoundNBT nbttagcompound = new CompoundNBT();
            nbttagcompound.putInt("X", blockPos.getX());
            nbttagcompound.putInt("Y", blockPos.getY());
            nbttagcompound.putInt("Z", blockPos.getZ());
            nbttaglist.add(nbttagcompound);
        }

        compound.put("HaysPos", nbttaglist);

        return compound;
    }

    @Override
    public void readNBT(Capability<IHayCapability> capability, IHayCapability instance, Direction side, INBT nbt) {
        CompoundNBT compound = (CompoundNBT) nbt;
        Set<BlockPos> allHayBlockPos = new HashSet<>();
        ListNBT nbttaglist = compound.getList("HaysPos", 10);

        for (int i = 0; i < nbttaglist.size(); ++i) {
            CompoundNBT nbttagcompound = nbttaglist.getCompound(i);
            BlockPos blockPosOfPost = new BlockPos(nbttagcompound.getInt("X"), nbttagcompound.getInt("Y"), nbttagcompound.getInt("Z"));
            allHayBlockPos.add(blockPosOfPost);
        }

        instance.setAllHayPos(allHayBlockPos);
    }
}
