package mokiyoki.enhancedanimals.capability.turtleegg;

import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
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
    public INBT writeNBT(Capability<INestEggCapability> capability, INestEggCapability instance, Direction side) {
        CompoundNBT compound = new CompoundNBT();
        Map<BlockPos, List<EggHolder>> allTurtleEggPos = instance.getAllNestEggPos();
        ListNBT nbttaglist = new ListNBT();
        Set<BlockPos> nestPos = allTurtleEggPos.keySet();
        for (BlockPos blockPos : nestPos) {
            List<EggHolder> listOfEggs = allTurtleEggPos.get(blockPos);
            for (EggHolder egg : listOfEggs) {
                CompoundNBT nbttagcompound = new CompoundNBT();
                nbttagcompound.putInt("X", blockPos.getX());
                nbttagcompound.putInt("Y", blockPos.getY());
                nbttagcompound.putInt("Z", blockPos.getZ());

                nbttagcompound.putString("SireName", egg.getSire());
                nbttagcompound.putString("DamName", egg.getDam());
                nbttagcompound.putIntArray("SGenes", egg.getGenes().getSexlinkedGenes());
                nbttagcompound.putIntArray("AGenes", egg.getGenes().getAutosomalGenes());

                nbttaglist.add(nbttagcompound);
            }
        }

        compound.put("NestPos", nbttaglist);

        return compound;
    }

    @Override
    public void readNBT(Capability<INestEggCapability> capability, INestEggCapability instance, Direction side, INBT nbt) {
        CompoundNBT compound = (CompoundNBT) nbt;
        Map<BlockPos, List<EggHolder>> allNestBlockPos = new HashMap<>();
        ListNBT nbttaglist = compound.getList("NestPos", 10);

        for (int i = 0; i < nbttaglist.size(); ++i) {
            CompoundNBT nbttagcompound = nbttaglist.getCompound(i);
            BlockPos blockPosOfNest = new BlockPos(nbttagcompound.getInt("X"), nbttagcompound.getInt("Y"), nbttagcompound.getInt("Z"));
            EggHolder egg = new EggHolder(nbttagcompound.getString("SireName"), nbttagcompound.getString("DamName"), new Genes(nbttagcompound.getIntArray("SGenes"),nbttagcompound.getIntArray("AGenes")));

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
