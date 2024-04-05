package mokiyoki.enhancedanimals.ai.brain.chicken;

import com.google.common.collect.ImmutableMap;
import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.init.ModMemoryModuleTypes;
import mokiyoki.enhancedanimals.tileentity.ChickenNestTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class Nesting extends Behavior<EnhancedChicken> {

    private boolean stuck = false;
    private int notReachedNestTicks = 0;

    public Nesting() {
        super(ImmutableMap.of(
                ModMemoryModuleTypes.PAUSE_BRAIN.get(), MemoryStatus.VALUE_ABSENT,
                ModMemoryModuleTypes.FOCUS_BRAIN.get(), MemoryStatus.VALUE_ABSENT,
                ModMemoryModuleTypes.SLEEPING.get(), MemoryStatus.VALUE_ABSENT,
                ModMemoryModuleTypes.ROOSTING.get(), MemoryStatus.VALUE_ABSENT
        ), 60, 10000);
    }

    protected boolean checkExtraStartConditions(ServerLevel serverLevel, EnhancedChicken chicken) {
        if (chicken.getNest() == BlockPos.ZERO) return false;
        return (chicken.isBrooding() || (chicken.timeUntilNextEgg < 800 && (chicken.getOrSetIsFemale() || EanimodCommonConfig.COMMON.omnigenders.get())));
    }

    public void start(ServerLevel serverLevel, EnhancedChicken chicken, long gameTime) {
        chicken.getBrain().setMemory(ModMemoryModuleTypes.FOCUS_BRAIN.get(), true);
        this.stuck = false;
        this.notReachedNestTicks = 0;
    }

    @Override
    protected void stop(ServerLevel serverLevel, EnhancedChicken chicken, long gameTime) {
        chicken.getBrain().eraseMemory(ModMemoryModuleTypes.FOCUS_BRAIN.get());
    }

    @Override
    protected boolean canStillUse(ServerLevel serverLevel, EnhancedChicken chicken, long gameTime) {
        return !stuck && (chicken.isBrooding() || (chicken.timeUntilNextEgg < 800 && (chicken.getOrSetIsFemale() || EanimodCommonConfig.COMMON.omnigenders.get())));
    }


    public void tick(ServerLevel serverLevel, EnhancedChicken chicken, long gameTime) {
        BlockPos blockPos = chicken.getNest();
        if (!chicken.isBrooding()) {
            ++this.notReachedNestTicks;
            if (notReachedNestTicks > 400) { stuck = true; }

            if (blockPos.closerToCenterThan(chicken.position(), 0.75D)) {
                BehaviorUtils.setWalkAndLookTargetMemories(chicken, new BlockPos(blockPos.getX()+0.5D, blockPos.getY()+0.0625D, blockPos.getZ()+0.5D), 1.0F, 0);

                if (chicken.isGoodNestSite(blockPos)) {
                    if (!chicken.isBrooding()) chicken.setBrooding(true);

                    Level world = chicken.level;
                    if (world.isEmptyBlock(blockPos)) {
                        List<BlockPos> nestList = new ArrayList<>();
                        if (world.getBlockEntity(blockPos.north()) instanceof ChickenNestTileEntity) nestList.add(blockPos.north());
                        if (world.getBlockEntity(blockPos.south()) instanceof ChickenNestTileEntity) nestList.add(blockPos.south());
                        if (world.getBlockEntity(blockPos.east()) instanceof ChickenNestTileEntity) nestList.add(blockPos.east());
                        if (world.getBlockEntity(blockPos.west()) instanceof ChickenNestTileEntity) nestList.add(blockPos.west());
                        if (nestList.isEmpty()) {
                            if (chicken.currentNestScore < 0.0F) chicken.currentNestScore *= 0.75F;
                            world.setBlock(blockPos, ModBlocks.CHICKEN_NEST.get().defaultBlockState(), 3);
                        } else {
                            BlockPos pos = nestList.get(chicken.getRandom().nextInt(nestList.size()));
                            chicken.rateNest(pos);
                            chicken.setNest(pos);
                        }
                    }
                } else {
                    chicken.setNest(BlockPos.ZERO);
                }

            } else if (chicken.getNavigation().isDone()) {
                Vec3 vec3 = new Vec3(blockPos.getX() + 0.5D, blockPos.getY() + 0.0625D, blockPos.getZ() + 0.5D);
                Vec3 vec31 = DefaultRandomPos.getPosTowards(chicken, 16, 3, vec3, (double)((float)Math.PI / 10F));
                if (vec31 == null) {
                    vec31 = DefaultRandomPos.getPosTowards(chicken, 8, 7, vec3, (double)((float)Math.PI / 2F));
                }

                if (vec31 != null && !chicken.level.getBlockState(new BlockPos(vec31)).is(Blocks.WATER)) {
                    vec31 = DefaultRandomPos.getPosTowards(chicken, 16, 5, vec3, (double)((float)Math.PI / 2F));
                }

                if (vec31 == null) {
                    this.stuck = true;
                    return;
                }

                BehaviorUtils.setWalkAndLookTargetMemories(chicken, new BlockPos(vec3), 1.0F, 0);
            }
        } else {
            if (!blockPos.closerToCenterThan(chicken.position(), 0.75D)) {
                setWalkAndLookTargetMemories(chicken, new Vec3(blockPos.getX()+0.5D, blockPos.getY()+0.0625D, blockPos.getZ()+0.5D), 1.0F, 0);
            } else {
                chicken.setPos(new Vec3(blockPos.getX()+0.5D, blockPos.getY()+0.0625D, blockPos.getZ()+0.5D));
            }
        }
    }

    public static void setWalkAndLookTargetMemories(LivingEntity p_22618_, Vec3 vec3, float p_22620_, int p_22621_) {
        WalkTarget walktarget = new WalkTarget(vec3, p_22620_, p_22621_);
        p_22618_.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new BlockPosTracker(new BlockPos(vec3)));
        p_22618_.getBrain().setMemory(MemoryModuleType.WALK_TARGET, walktarget);
    }
}
