package mokiyoki.enhancedanimals.ai.general;

import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

import java.util.function.Predicate;

public class EnhancedWaterAvoidingRandomWalkingGoal extends WaterAvoidingRandomStrollGoal {

    private static final Predicate<BlockState> IS_GRASSBLOCK = BlockStatePredicate.forBlock(Blocks.GRASS);
//    private static final Predicate<BlockState> IS_GRASS = BlockStateMatcher.forBlock(Blocks.GRASS);
//    private static final Predicate<BlockState> IS_TALLGRASS = BlockStateMatcher.forBlock(Blocks.TALL_GRASS);

    public EnhancedWaterAvoidingRandomWalkingGoal(PathfinderMob p_i47301_1_, double p_i47301_2_) {
        super(p_i47301_1_, p_i47301_2_);
    }

    public EnhancedWaterAvoidingRandomWalkingGoal(PathfinderMob p_i47302_1_, double p_i47302_2_, float p_i47302_4_) {
        super(p_i47302_1_, p_i47302_2_, p_i47302_4_);
    }

    @Override
    public boolean canUse() {
        if (this.mob.isVehicle() || ((EnhancedAnimalAbstract)this.mob).getAIStatus() == AIStatus.FOCUSED) {
            return false;
        } else {
            if (!this.forceTrigger) {
                //Todo make this use Temperaments\
                if(((EnhancedAnimalAbstract)this.mob).getHunger() > 12000 && checkForFood()) {
                    return false;
                }

                float hungerModifier = ((EnhancedAnimalAbstract)this.mob).getHunger()/100;
                if (hungerModifier >= this.interval) {
                    hungerModifier = this.interval - 1;
                }


                if (this.mob.getNoActionTime() >= 100) {
                    return false;
                }

                if (this.mob.getRandom().nextInt(this.interval - Math.round(hungerModifier)) != 0) {
                    return false;
                }
            }

            Vec3 lvt_1_1_ = this.getPosition();
            if (lvt_1_1_ == null) {
                return false;
            } else {
                this.wantedX = lvt_1_1_.x;
                this.wantedY = lvt_1_1_.y;
                this.wantedZ = lvt_1_1_.z;
                this.forceTrigger = false;
                return true;
            }
        }
    }

    private boolean checkForFood() {
        BlockPos blockpos = new BlockPos(this.mob.blockPosition());

        //TODO add the predicate for different blocks to eat based on temperaments and animal type.
//        if (IS_TALLGRASS.test(this.creature.world.getBlockState(blockpos))) {
//            return true;
//        } else if (IS_GRASS.test(this.creature.world.getBlockState(blockpos))) {
//            return true;
//        } else
            if (IS_GRASSBLOCK.test(this.mob.level.getBlockState(blockpos))) {
            return true;
        } else {
            return this.mob.level.getBlockState(blockpos.below()).getBlock() == Blocks.GRASS_BLOCK;
        }
    }




}
