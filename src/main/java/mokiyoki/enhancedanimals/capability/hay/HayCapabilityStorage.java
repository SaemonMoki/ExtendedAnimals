package mokiyoki.enhancedanimals.capability.hay;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.ListTag;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
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
    public Tag writeNBT(Capability<IHayCapability> capability, IHayCapability instance, Direction side) {
        CompoundTag compound = new CompoundTag();
        Set<BlockPos> allHayBlockPos = instance.getAllHayPos();

        ListTag nbttaglist = new ListTag();

        for (BlockPos blockPos : allHayBlockPos)
        {
            CompoundTag nbttagcompound = new CompoundTag();
            nbttagcompound.putInt("X", blockPos.getX());
            nbttagcompound.putInt("Y", blockPos.getY());
            nbttagcompound.putInt("Z", blockPos.getZ());
            nbttaglist.add(nbttagcompound);
        }

        compound.put("HaysPos", nbttaglist);

        return compound;
    }

    @Override
    public void readNBT(Capability<IHayCapability> capability, IHayCapability instance, Direction side, Tag nbt) {
        CompoundTag compound = (CompoundTag) nbt;
        Set<BlockPos> allHayBlockPos = new HashSet<>();
        ListTag nbttaglist = compound.getList("HaysPos", 10);

        for (int i = 0; i < nbttaglist.size(); ++i) {
            CompoundTag nbttagcompound = nbttaglist.getCompound(i);
            BlockPos blockPosOfHay = new BlockPos(nbttagcompound.getInt("X"), nbttagcompound.getInt("Y"), nbttagcompound.getInt("Z"));
            allHayBlockPos.add(blockPosOfHay);
        }

        instance.setAllHayPos(allHayBlockPos);
    }
}
