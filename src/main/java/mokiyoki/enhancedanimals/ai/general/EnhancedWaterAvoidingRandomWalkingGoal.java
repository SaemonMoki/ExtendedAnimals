package mokiyoki.enhancedanimals.ai.general;

import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockStateMatcher;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import java.util.function.Predicate;

public class EnhancedWaterAvoidingRandomWalkingGoal extends WaterAvoidingRandomWalkingGoal {

    private static final Predicate<BlockState> IS_GRASSBLOCK = BlockStateMatcher.forBlock(Blocks.GRASS);
//    private static final Predicate<BlockState> IS_GRASS = BlockStateMatcher.forBlock(Blocks.GRASS);
//    private static final Predicate<BlockState> IS_TALLGRASS = BlockStateMatcher.forBlock(Blocks.TALL_GRASS);

    public EnhancedWaterAvoidingRandomWalkingGoal(CreatureEntity p_i47301_1_, double p_i47301_2_) {
        super(p_i47301_1_, p_i47301_2_);
    }

    public EnhancedWaterAvoidingRandomWalkingGoal(CreatureEntity p_i47302_1_, double p_i47302_2_, float p_i47302_4_) {
        super(p_i47302_1_, p_i47302_2_, p_i47302_4_);
    }

    @Override
    public boolean shouldExecute() {
        if (this.creature.isBeingRidden()) {
            return false;
        } else {
            if (!this.mustUpdate) {
                //Todo make this use Temperaments\
                if(((EnhancedAnimalAbstract)this.creature).getHunger() > 12000 && checkForFood()) {
                    return false;
                }

                float hungerModifier = ((EnhancedAnimalAbstract)this.creature).getHunger()/100;
                if (hungerModifier >= this.executionChance) {
                    hungerModifier = this.executionChance - 1;
                }


                if (this.creature.getIdleTime() >= 100) {
                    return false;
                }

                if (this.creature.getRNG().nextInt(this.executionChance - Math.round(hungerModifier)) != 0) {
                    return false;
                }
            }

            Vector3d lvt_1_1_ = this.getPosition();
            if (lvt_1_1_ == null) {
                return false;
            } else {
                this.x = lvt_1_1_.x;
                this.y = lvt_1_1_.y;
                this.z = lvt_1_1_.z;
                this.mustUpdate = false;
                return true;
            }
        }
    }

    private boolean checkForFood() {
        BlockPos blockpos = new BlockPos(this.creature.getPosition());

        //TODO add the predicate for different blocks to eat based on temperaments and animal type.
//        if (IS_TALLGRASS.test(this.creature.world.getBlockState(blockpos))) {
//            return true;
//        } else if (IS_GRASS.test(this.creature.world.getBlockState(blockpos))) {
//            return true;
//        } else
            if (IS_GRASSBLOCK.test(this.creature.world.getBlockState(blockpos))) {
            return true;
        } else {
            return this.creature.world.getBlockState(blockpos.down()).getBlock() == Blocks.GRASS_BLOCK;
        }
    }




}
