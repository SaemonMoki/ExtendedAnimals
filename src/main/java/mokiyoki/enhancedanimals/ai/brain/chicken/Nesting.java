package mokiyoki.enhancedanimals.ai.brain.chicken;

import com.google.common.collect.ImmutableMap;
import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

import static mokiyoki.enhancedanimals.ai.brain.ValidatePath.isValidPath;
import static mokiyoki.enhancedanimals.init.ModMemoryModuleTypes.SEEKING_FOOD;

public class Nesting extends Behavior<EnhancedChicken> {

    private boolean stuck = false;
    private int notReachedNestTicks = 0;
    private int resetCount = 0;

    public Nesting() {
        super(ImmutableMap.of(
                ModMemoryModuleTypes.PAUSE_BRAIN.get(), MemoryStatus.VALUE_ABSENT,
                ModMemoryModuleTypes.FOCUS_BRAIN.get(), MemoryStatus.VALUE_ABSENT,
                ModMemoryModuleTypes.SLEEPING.get(), MemoryStatus.VALUE_ABSENT,
                ModMemoryModuleTypes.ROOSTING.get(), MemoryStatus.VALUE_ABSENT,
                ModMemoryModuleTypes.SEEKING_FOOD.get(), MemoryStatus.VALUE_ABSENT
        ), 60, 100000);
    }

    protected boolean checkExtraStartConditions(ServerLevel serverLevel, EnhancedChicken chicken) {
        if (chicken.getNest() == BlockPos.ZERO) return false;
        return ((chicken.isBrooding() || chicken.isBroody()) || (chicken.timeUntilNextEgg < 800 && (chicken.getOrSetIsFemale() || EanimodCommonConfig.COMMON.omnigenders.get())));
    }

    public void start(ServerLevel serverLevel, EnhancedChicken chicken, long gameTime) {
        if (chicken.timeUntilNextEgg < 800 && !chicken.isBroody()) { chicken.getBrain().setMemoryWithExpiry(ModMemoryModuleTypes.EGG_LAYING.get(), true, chicken.timeUntilNextEgg);}
        confirmNotStuckInFenceWall(serverLevel, chicken);
        if (!isValidPath(chicken, chicken.getNest(), 24)) {
            chicken.setNest(BlockPos.ZERO);
            chicken.currentNestScore = 0.0F;
            chicken.findNestAroundSelf(false, true);
        };

        this.stuck = chicken.getNest() == BlockPos.ZERO;
        this.notReachedNestTicks = 0;
        this.resetCount = 0;
    }

    @Override
    protected void stop(ServerLevel serverLevel, EnhancedChicken chicken, long gameTime) {}

    @Override
    protected boolean canStillUse(ServerLevel serverLevel, EnhancedChicken chicken, long gameTime) {
        return !stuck
                && ((chicken.isBrooding() || chicken.isBroody()) || (chicken.timeUntilNextEgg < 800 && (chicken.getOrSetIsFemale() || EanimodCommonConfig.COMMON.omnigenders.get())))
                && !chicken.getBrain().hasMemoryValue(SEEKING_FOOD.get());
    }


    public void tick(ServerLevel serverLevel, EnhancedChicken chicken, long gameTime) {
        BlockPos blockPos = chicken.getNest();

        if ((!chicken.isBrooding())) {
            ++this.notReachedNestTicks;
            checkNotReachedNestTicks(serverLevel, chicken);

            if (blockPos.closerToCenterThan(chicken.position(), 1.5D)) {
                if (chicken.blockPosition().getY() != blockPos.getY() && chicken.isOnGround()) {
                    chicken.setPos(moveCloser(chicken.position(), new Vec3(blockPos.getX()+0.5D, chicken.position().y, blockPos.getZ()+0.5D), 0.01));
                } else {
                    chicken.setPos(moveCloser(chicken.position(), new Vec3(blockPos.getX()+0.5D, blockPos.getY(), blockPos.getZ()+0.5D), 0.01));
                }
            }

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
                            if (chicken.currentNestScore > 0.0F) chicken.currentNestScore *= 0.75F;
                            chicken.createNest();
                        } else {
                            BlockPos pos = nestList.get(chicken.getRandom().nextInt(nestList.size()));
                            chicken.rateAndSetBetterNest(pos);
                            chicken.setNest(pos);
                        }
                    }
                } else {
                    if (!confirmNotStuckInFenceWall(serverLevel, chicken)) {
                        chicken.setNest(BlockPos.ZERO);
                        chicken.currentNestScore = 0.0F;
                    }
                }

            } else if (chicken.getNavigation().isDone()) {
                Vec3 vec3 = new Vec3(blockPos.getX() + 0.5D, blockPos.getY() + 0.0625D, blockPos.getZ() + 0.5D);

                BehaviorUtils.setWalkAndLookTargetMemories(chicken, new BlockPos(vec3), 1.0F, 0);
            }
        } else {
            if (blockPos.closerToCenterThan(chicken.position(), 1.5D)) {
                if (chicken.blockPosition().getY() != blockPos.getY() && chicken.isOnGround()) {
                    chicken.setPos(moveCloser(chicken.position(), new Vec3(blockPos.getX()+0.5D, chicken.position().y+0.0625D, blockPos.getZ()+0.5D), 0.01));
                } else {
                    chicken.setPos(moveCloser(chicken.position(), new Vec3(blockPos.getX()+0.5D, blockPos.getY()+0.0625D, blockPos.getZ()+0.5D), 0.01));
                }
            }
            if (!blockPos.closerToCenterThan(chicken.position(), 0.75D)) {
                setWalkAndLookTargetMemories(chicken, new Vec3(blockPos.getX()+0.5D, blockPos.getY()+0.0625D, blockPos.getZ()+0.5D), 1.0F, 0);
            } else {
                if (chicken.blockPosition().getY() != blockPos.getY() && chicken.isOnGround()) {
                    chicken.setPos(new Vec3(blockPos.getX()+0.5D, chicken.position().y+0.0625D, blockPos.getZ()+0.5D));
                } else {
                    chicken.setPos(new Vec3(blockPos.getX()+0.5D, blockPos.getY()+0.0625D, blockPos.getZ()+0.5D));
                }
            }
        }
    }

    private void checkNotReachedNestTicks(ServerLevel serverLevel, EnhancedChicken chicken) {
        if (notReachedNestTicks > 600) {
            if (!confirmNotStuckInFenceWall(serverLevel, chicken)) {
                stuck = true;
            } else {
                resetCount++;
                if (resetCount > 2) {
                    stuck = true;
                } else {
                    notReachedNestTicks = 0; //reset
                }
            }
        }
    }

    public static void setWalkAndLookTargetMemories(LivingEntity p_22618_, Vec3 vec3, float p_22620_, int p_22621_) {
        WalkTarget walktarget = new WalkTarget(vec3, p_22620_, p_22621_);
        p_22618_.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new BlockPosTracker(new BlockPos(vec3)));
        p_22618_.getBrain().setMemory(MemoryModuleType.WALK_TARGET, walktarget);
    }

    public Vec3 moveCloser(Vec3 vec1, Vec3 vec2, double step) {
        // Calculate the direction vector from vec1 to vec2
        Vec3 direction = vec2.subtract(vec1);

        // Normalize the direction vector to get the unit vector
        Vec3 unitDirection = direction.normalize();

        // Scale the unit vector by the step size
        Vec3 stepVector = unitDirection.scale(step);

        // Add the step vector to vec1 to get the new vector
        Vec3 newVec = vec1.add(stepVector);

        return newVec;
    }

    private boolean confirmNotStuckInFenceWall(ServerLevel serverLevel, EnhancedChicken chicken) {
        BlockState blockState = serverLevel.getBlockState(chicken.blockPosition());

        if (!(blockState.getBlock() instanceof AirBlock) && !chicken.isInWater() && !chicken.isInPowderSnow){
            chicken.teleportTo(chicken.getNest().getX(), chicken.getNest().getY(), chicken.getNest().getZ());
            return true;
        }

        return false;
    }
}
