package mokiyoki.enhancedanimals.ai.rabbit;

import mokiyoki.enhancedanimals.entity.EnhancedRabbit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarrotBlock;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class EnhancedRabbitRaidFarmGoal extends MoveToBlockGoal {

    private final EnhancedRabbit rabbit;
    private boolean wantsToRaid;
    private boolean canRaid;

    public EnhancedRabbitRaidFarmGoal(EnhancedRabbit rabbitIn) {
        super(rabbitIn, (double)0.7F, 16);
        this.rabbit = rabbitIn;
    }

    public boolean shouldExecute() {
        if (this.runDelay <= 0) {
            if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.rabbit.world, this.rabbit)) {
                return false;
            }

            this.canRaid = false;
            this.wantsToRaid = this.rabbit.isCarrotEaten();
            this.wantsToRaid = true;
        }

        return super.shouldExecute();
    }

    public boolean shouldContinueExecuting() {
        return this.canRaid && super.shouldContinueExecuting();
    }

    public void tick() {
        super.tick();
        this.rabbit.getLookController().setLookPosition((double)this.destinationBlock.getX() + 0.5D, (double)(this.destinationBlock.getY() + 1), (double)this.destinationBlock.getZ() + 0.5D, 10.0F, (float)this.rabbit.getVerticalFaceSpeed());
        if (this.getIsAboveDestination()) {
            World world = this.rabbit.world;
            BlockPos blockpos = this.destinationBlock.up();
            BlockState iblockstate = world.getBlockState(blockpos);
            Block block = iblockstate.getBlock();
            if (this.canRaid && block instanceof CarrotBlock) {
                Integer integer = iblockstate.get(CarrotBlock.AGE);
                if (integer == 0) {
                    world.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 2);
                    world.destroyBlock(blockpos, true);
                } else {
                    world.setBlockState(blockpos, iblockstate.with(CarrotBlock.AGE, Integer.valueOf(integer - 1)), 2);
                    world.playEvent(2001, blockpos, Block.getStateId(iblockstate));
                }
                this.rabbit.decreaseHunger(750);
                this.rabbit.carrotTicks = 40;
            }

            this.canRaid = false;
            this.runDelay = 10;
        }

    }

    protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
        Block block = worldIn.getBlockState(pos).getBlock();
        if (block == Blocks.FARMLAND && this.wantsToRaid && !this.canRaid) {
            pos = pos.up();
            BlockState iblockstate = worldIn.getBlockState(pos);
            block = iblockstate.getBlock();
            if (block instanceof CarrotBlock && ((CarrotBlock)block).isMaxAge(iblockstate)) {
                this.canRaid = true;
                return true;
            }
        }

        return false;
    }

}
