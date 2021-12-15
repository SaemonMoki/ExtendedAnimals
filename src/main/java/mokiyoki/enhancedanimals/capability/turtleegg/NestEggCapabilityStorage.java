package mokiyoki.enhancedanimals.capability.turtleegg;

import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.ListTag;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NestEggCapabilityStorage implements Capability.IStorage<INestEggCapability> {

    @Nullable
    @Override
    public Tag writeNBT(Capability<INestEggCapability> capability, INestEggCapability instance, Direction side) {
        CompoundTag compound = new CompoundTag();
        Map<BlockPos, List<EggHolder>> allTurtleEggPos = instance.getAllNestEggPos();
        ListTag nbttaglist = new ListTag();
        Set<BlockPos> nestPos = allTurtleEggPos.keySet();
        for (BlockPos blockPos : nestPos) {
            List<EggHolder> listOfEggs = allTurtleEggPos.get(blockPos);
            for (EggHolder egg : listOfEggs) {
                if (egg.getGenes().isComplete()) {
                    CompoundTag nbttagcompound = new CompoundTag();
                    nbttagcompound.putInt("X", blockPos.getX());
                    nbttagcompound.putInt("Y", blockPos.getY());
                    nbttagcompound.putInt("Z", blockPos.getZ());

                    nbttagcompound.putString("SireName", egg.getSire());
                    nbttagcompound.putString("DamName", egg.getDam());
                    nbttagcompound.putIntArray("SGenes", egg.getGenes().getSexlinkedGenes());
                    nbttagcompound.putIntArray("AGenes", egg.getGenes().getAutosomalGenes());
                    nbttagcompound.putBoolean("hasParents", egg.hasParents());

                    nbttaglist.add(nbttagcompound);
                }
            }
        }

        compound.put("NestPos", nbttaglist);

        return compound;
    }

    @Override
    public void readNBT(Capability<INestEggCapability> capability, INestEggCapability instance, Direction side, Tag nbt) {
        CompoundTag compound = (CompoundTag) nbt;
        Map<BlockPos, List<EggHolder>> allNestBlockPos = new HashMap<>();
        ListTag nbttaglist = compound.getList("NestPos", 10);

        for (int i = 0; i < nbttaglist.size(); ++i) {
            CompoundTag nbttagcompound = nbttaglist.getCompound(i);
            BlockPos blockPosOfNest = new BlockPos(nbttagcompound.getInt("X"), nbttagcompound.getInt("Y"), nbttagcompound.getInt("Z"));
            EggHolder egg = new EggHolder(nbttagcompound.getString("SireName"), nbttagcompound.getString("DamName"), new Genes(nbttagcompound.getIntArray("SGenes"),nbttagcompound.getIntArray("AGenes")), nbttagcompound.getBoolean("hasParents"));

            if (allNestBlockPos.containsKey(blockPosOfNest)) {
                allNestBlockPos.get(blockPosOfNest).add(egg);
            } else {
                List<EggHolder> eggHolderList = new ArrayList<>();
                eggHolderList.add(egg);
                allNestBlockPos.put(blockPosOfNest, eggHolderList);
            }
        }

        instance.setAllNestEggPos(allNestBlockPos);
    }

}
