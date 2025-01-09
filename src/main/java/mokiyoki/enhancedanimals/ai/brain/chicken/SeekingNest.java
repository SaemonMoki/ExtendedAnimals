package mokiyoki.enhancedanimals.ai.brain.chicken;

import com.google.common.collect.ImmutableMap;
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.init.ModMemoryModuleTypes;
import mokiyoki.enhancedanimals.tileentity.ChickenNestTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.phys.Vec3;

public class SeekingNest extends Behavior<EnhancedChicken> {

    private boolean stuck = false;
    private boolean hasReset = false;
    private boolean hasNest = false;
    private boolean onNest = false;
    private int notReachedNestTicks = 0;
    private int stayAtNestTicks = 0;
    private int nextPossibleStart = 0;

    public SeekingNest() {
        super(ImmutableMap.of(
                ModMemoryModuleTypes.PAUSE_BRAIN.get(), MemoryStatus.VALUE_ABSENT,
                ModMemoryModuleTypes.FOCUS_BRAIN.get(), MemoryStatus.VALUE_ABSENT,
                ModMemoryModuleTypes.SLEEPING.get(), MemoryStatus.VALUE_ABSENT,
                ModMemoryModuleTypes.ROOSTING.get(), MemoryStatus.VALUE_ABSENT,
                ModMemoryModuleTypes.SEEKING_NEST.get(), MemoryStatus.VALUE_PRESENT
        ), 120, 1000);
    }

    protected boolean checkExtraStartConditions(ServerLevel serverLevel, EnhancedChicken chicken) {
        return this.nextPossibleStart < (serverLevel.getGameTime() + serverLevel.random.nextInt(2000));

    }

    public void start(ServerLevel serverLevel, EnhancedChicken chicken, long gameTime) {
        chicken.getBrain().setMemory(ModMemoryModuleTypes.FOCUS_BRAIN.get(), true);
        this.hasNest = hasOrFindNest(chicken);
        this.stuck = false;
        this.hasReset = false;
        this.notReachedNestTicks = 0;
        this.stayAtNestTicks = 0;
    }

    private boolean hasOrFindNest(EnhancedChicken chicken) {
        if (chicken.getNest() != null && chicken.getNest() != BlockPos.ZERO) {
            return true;
        }
        chicken.findNestAroundSelf(false, true);
        return (chicken.getNest() != null && chicken.getNest() != BlockPos.ZERO);
    }

    @Override
    protected void stop(ServerLevel serverLevel, EnhancedChicken chicken, long gameTime) {
        chicken.getBrain().eraseMemory(ModMemoryModuleTypes.FOCUS_BRAIN.get());
        chicken.getBrain().eraseMemory(ModMemoryModuleTypes.SEEKING_NEST.get());
    }

    @Override
    protected boolean canStillUse(ServerLevel serverLevel, EnhancedChicken chicken, long gameTime) {
        return !stuck && stayAtNestTicks < 600 && hasNest;
    }


    public void tick(ServerLevel serverLevel, EnhancedChicken chicken, long gameTime) {
        BlockPos blockPos = chicken.getNest();
        if (!onNest) {
            ++this.notReachedNestTicks;
        } else {
            ++this.stayAtNestTicks;
        }
        if (notReachedNestTicks > 400) {
            chicken.setNest(BlockPos.ZERO);
            chicken.currentNestScore = 0.0F;
            if (hasReset) {
                stuck = true;
            } else {
                hasReset = true;
                notReachedNestTicks = 0;
                chicken.findNestAroundSelf(false, true); //A single attempt to find a new nest
            }
        }

        if (!blockPos.closerToCenterThan(chicken.position(), 0.75D)) {
            setWalkAndLookTargetMemories(chicken, new Vec3(blockPos.getX()+0.5D, blockPos.getY()+0.0625D, blockPos.getZ()+0.5D), 1.0F, 0);
        } else {
            chicken.setPos(new Vec3(blockPos.getX()+0.5D, blockPos.getY()+0.0625D, blockPos.getZ()+0.5D));
            if (!(serverLevel.getBlockEntity(chicken.getNest()) instanceof ChickenNestTileEntity)) {
                chicken.createNest();
            }
            this.onNest = true;
        }
    }

    public static void setWalkAndLookTargetMemories(LivingEntity p_22618_, Vec3 vec3, float p_22620_, int p_22621_) {
        WalkTarget walktarget = new WalkTarget(vec3, p_22620_, p_22621_);
        p_22618_.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new BlockPosTracker(new BlockPos(vec3)));
        p_22618_.getBrain().setMemory(MemoryModuleType.WALK_TARGET, walktarget);
    }
}
