package mokiyoki.enhancedanimals.capability.nestegg;

import mokiyoki.enhancedanimals.util.Genes;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NestCapabilityProvider implements INestEggCapability, ICapabilitySerializable<Tag> {

    public static Capability<INestEggCapability> NEST_CAP = CapabilityManager.get(new CapabilityToken<>() {});

    private final LazyOptional<INestEggCapability> holder = LazyOptional.of(() -> this);

    Map<BlockPos, List<EggHolder>> eggsInNests = new HashMap<>();

    @Override
    public Map<BlockPos, List<EggHolder>> getAllNestEggPos() {
        return this.eggsInNests;
    }

    @Override
    public void addNestEggPos(BlockPos blockPos, String sire, String dam, Genes genes, boolean hasParents) {
        EggHolder egg = new EggHolder(sire, dam, genes, hasParents);
        List<EggHolder> eggList = this.eggsInNests.containsKey(blockPos) ? this.eggsInNests.get(blockPos) : new ArrayList<>();
        eggList.add(egg);
        this.eggsInNests.put(blockPos, eggList);
    }

    @Override
    public void removeNestPos(BlockPos blockPos) {
        this.eggsInNests.remove(blockPos);
    }

    @Override
    public void setAllNestEggPos(Map<BlockPos, List<EggHolder>> blockPosList) {
        this.eggsInNests = blockPosList;
    }

    @Override
    public EggHolder removeEggFromNest(BlockPos blockPos) {
        List<EggHolder> eggsInNest = this.eggsInNests.get(blockPos);
        if (eggsInNest==null || eggsInNest.isEmpty()) {
            return new EggHolder(null, null, null, false);
        }
        return eggsInNest.remove(0);
    }

    @Override
    public List<EggHolder> removeEggsFromNest(BlockPos blockPos) {
        List<EggHolder> eggsInNest = this.eggsInNests.remove(blockPos);
        return eggsInNest;
    }

    @Override
    public List<EggHolder> getEggsInNest(BlockPos blockPos) {
        List<EggHolder> eggsInNest = this.eggsInNests.get(blockPos);
        if (eggsInNest==null) {
            return null;
        }
        return eggsInNest;
    }

    @Override
    public EggHolder getEggInNest(BlockPos blockPos) {
        List<EggHolder> eggsInNest = this.eggsInNests.get(blockPos);
        if (eggsInNest==null) {
            return null;
        }
        return eggsInNest.get(0);
    }

    @Nullable
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
        return NEST_CAP.orEmpty(capability, holder);
    }


    @Override
    public Tag serializeNBT() {
        CompoundTag compound = new CompoundTag();
        Map<BlockPos, List<EggHolder>> allTurtleEggPos = this.getAllNestEggPos();
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
    public void deserializeNBT(Tag nbt) {
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

        this.setAllNestEggPos(allNestBlockPos);
    }

}
