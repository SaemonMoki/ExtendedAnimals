package mokiyoki.enhancedanimals.capability.turtleegg;

import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.nbt.Tag;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NestCapabilityProvider implements INestEggCapability, ICapabilitySerializable<Tag> {

    @CapabilityInject(INestEggCapability.class)
    public static final Capability<INestEggCapability> NEST_CAP = null;

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
        if (eggsInNest.isEmpty()) {
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
        return NEST_CAP.getStorage().writeNBT(NEST_CAP, this, null);
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        NEST_CAP.getStorage().readNBT(NEST_CAP, this, null, nbt);
    }

}
