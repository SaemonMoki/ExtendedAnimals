//package mokiyoki.enhancedanimals.ai.rabbit;
//
//import mokiyoki.enhancedanimals.entity.EnhancedRabbit;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.block.CarrotBlock;
//import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
//import net.minecraft.core.BlockPos;
//import net.minecraft.world.level.LevelReader;
//import net.minecraft.world.level.Level;
//
//public class EnhancedRabbitRaidFarmGoal extends MoveToBlockGoal {
//
//    private final EnhancedRabbit rabbit;
//    private boolean wantsToRaid;
//    private boolean canRaid;
//
//    public EnhancedRabbitRaidFarmGoal(EnhancedRabbit rabbitIn) {
//        super(rabbitIn, (double)0.7F, 16);
//        this.rabbit = rabbitIn;
//    }
//
//    public boolean canUse() {
//        if (this.nextStartTick <= 0) {
//            if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.rabbit.level, this.rabbit)) {
//                return false;
//            }
//
//            this.canRaid = false;
//            this.wantsToRaid = this.rabbit.isCarrotEaten();
//            this.wantsToRaid = true;
//        }
//
//        return super.canUse();
//    }
//
//    public boolean canContinueToUse() {
//        return this.canRaid && super.canContinueToUse();
//    }
//
//    public void tick() {
//        super.tick();
//        this.rabbit.getLookControl().setLookAt((double)this.blockPos.getX() + 0.5D, (double)(this.blockPos.getY() + 1), (double)this.blockPos.getZ() + 0.5D, 10.0F, (float)this.rabbit.getMaxHeadXRot());
//        if (this.isReachedTarget()) {
//            Level world = this.rabbit.level;
//            BlockPos blockpos = this.blockPos.above();
//            BlockState iblockstate = world.getBlockState(blockpos);
//            Block block = iblockstate.getBlock();
//            if (this.canRaid && block instanceof CarrotBlock) {
//                Integer integer = iblockstate.getValue(CarrotBlock.AGE);
//                if (integer == 0) {
//                    world.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 2);
//                    world.destroyBlock(blockpos, true);
//                } else {
//                    world.setBlock(blockpos, iblockstate.setValue(CarrotBlock.AGE, Integer.valueOf(integer - 1)), 2);
//                    world.levelEvent(2001, blockpos, Block.getId(iblockstate));
//                }
//                this.rabbit.decreaseHunger(750);
//                this.rabbit.carrotTicks = 40;
//            }
//
//            this.canRaid = false;
//            this.nextStartTick = 10;
//        }
//
//    }
//
//    protected boolean isValidTarget(LevelReader worldIn, BlockPos pos) {
//        Block block = worldIn.getBlockState(pos).getBlock();
//        if (block == Blocks.FARMLAND && this.wantsToRaid && !this.canRaid) {
//            pos = pos.above();
//            BlockState iblockstate = worldIn.getBlockState(pos);
//            block = iblockstate.getBlock();
//            if (block instanceof CarrotBlock && ((CarrotBlock)block).isMaxAge(iblockstate)) {
//                this.canRaid = true;
//                return true;
//            }
//        }
//
//        return false;
//    }
//
//}
