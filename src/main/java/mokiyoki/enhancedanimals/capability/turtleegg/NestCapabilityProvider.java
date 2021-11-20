package mokiyoki.enhancedanimals.capability.turtleegg;

import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
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

public class NestCapabilityProvider implements INestEggCapability, ICapabilitySerializable<INBT> {

    @CapabilityInject(INestEggCapability.class)
    public static final Capability<INestEggCapability> NEST_CAP = null;

    private final LazyOptional<INestEggCapability> holder = LazyOptional.of(() -> this);

    Map<BlockPos, List<EggHolder>> eggsInNests = new HashMap<>();

    @Override
    public Map<BlockPos, List<EggHolder>> getAllNestEggPos() {
        return this.eggsInNests;
    }

    @Override
    public void addNestEggPos(BlockPos blockPos, String sire, String dam, Genes genes) {
        EggHolder egg = new EggHolder(sire, dam, genes);
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
        return eggsInNest.remove(0);
    }

    @Override
    public List<EggHolder> removeEggsFromNest(BlockPos blockPos) {
        List<EggHolder> eggsInNest = this.eggsInNests.remove(blockPos);
        return eggsInNest;
    }

    @Nullable
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
        return NEST_CAP.orEmpty(capability, holder);
    }


    @Override
    public INBT serializeNBT() {
        return NEST_CAP.getStorage().writeNBT(NEST_CAP, this, null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        NEST_CAP.getStorage().readNBT(NEST_CAP, this, null, nbt);
    }

}
