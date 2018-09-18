package mokiyoki.enhancedanimals.AI;

import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

//public class EnhancedChickenRoost extends EntityAIMoveToBlock
//{
//    private final EnhancedChicken enhancedChicken;
//
//    public EnhancedChickenRoost(EntityOcelot ocelotIn, double p_i45315_2_)
//    {
//        super(ocelotIn, p_i45315_2_, 8);
//        this.enhancedChicken = ocelotIn;
//    }
//
//    /**
//     * Returns whether the EntityAIBase should begin execution.
//     */
////    public boolean shouldExecute()
////    {
////        return this.enhancedChicken.isTamed() && !this.enhancedChicken.isSitting() && super.shouldExecute();
////    }
//
//    /**
//     * Execute a one shot task or start executing a continuous task
//     */
//    public void startExecuting()
//    {
//        super.startExecuting();
//        this.enhancedChicken.getAISit().setSitting(false);
//    }
//
//    /**
//     * Reset the task's internal state. Called when this task is interrupted by another one
//     */
//    public void resetTask()
//    {
//        super.resetTask();
//        this.enhancedChicken.setSitting(false);
//    }
//
//    /**
//     * Keep ticking a continuous task that has already been started
//     */
//    public void updateTask()
//    {
//        super.updateTask();
//        this.enhancedChicken.getAISit().setSitting(false);
//
//        if (!this.getIsAboveDestination())
//        {
//            this.enhancedChicken.setSitting(false);
//        }
//        else if (!this.enhancedChicken.isSitting())
//        {
//            this.enhancedChicken.setSitting(true);
//        }
//    }
//
//    /**
//     * Return true to set given position as destination
//     */
//    protected boolean shouldMoveTo(World worldIn, BlockPos pos)
//    {
//        if (!worldIn.isAirBlock(pos.up()))
//        {
//            return false;
//        }
//        else
//        {
//            IBlockState iblockstate = worldIn.getBlockState(pos);
//            Block block = iblockstate.getBlock();
//
//            if (block == Blocks.CHEST)
//            {
//                TileEntity tileentity = worldIn.getTileEntity(pos);
//
//                if (tileentity instanceof TileEntityChest && ((TileEntityChest)tileentity).numPlayersUsing < 1)
//                {
//                    return true;
//                }
//            }
//            else
//            {
//                if (block == Blocks.LIT_FURNACE)
//                {
//                    return true;
//                }
//
//                if (block == Blocks.BED && iblockstate.getValue(BlockBed.PART) != BlockBed.EnumPartType.HEAD)
//                {
//                    return true;
//                }
//            }
//
//            return false;
//        }
//    }
//}