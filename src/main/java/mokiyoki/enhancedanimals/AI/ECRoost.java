package mokiyoki.enhancedanimals.AI;

import mokiyoki.enhancedanimals.capability.post.PostCapabilityProvider;
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class ECRoost extends EntityAIBase {

    private static final int MAX_DISTANCE = 256;
    private final EnhancedChicken enhancedChicken;
    private BlockPos postPos;



    public ECRoost(EntityCreature entityIn) {
        this.enhancedChicken = (EnhancedChicken) entityIn;
    }

    public boolean shouldExecute()
    {
        if (!this.enhancedChicken.world.isDaytime() && !this.enhancedChicken.isSitting()) {
            List<BlockPos> allPostPos = this.enhancedChicken.world.getCapability(PostCapabilityProvider.POST_CAP, null).getAllPostPos();
            if(allPostPos != null && !allPostPos.isEmpty()) {
                BlockPos blockPosToGoTo = calculateClosestPost(allPostPos);
                postPos = blockPosToGoTo;
                return true;
            }
            return false;
        }
        return false;
    }

    private BlockPos calculateClosestPost(List<BlockPos> blockPosList) {
        BlockPos nearestPostPos = null;
        int i = Integer.MAX_VALUE;

        for (BlockPos postPos : blockPosList)
        {
            int j = getDistanceToPostBlockSq(postPos);

            if (j < MAX_DISTANCE)
            {
                if (j < i) {
                    nearestPostPos = postPos;
                    i = j;
                }
            }
        }

        return nearestPostPos;
    }

    public int getDistanceToPostBlockSq(BlockPos pos)
    {
        return (int)pos.distanceSq(this.enhancedChicken.getPosition());
    }

    public boolean shouldContinueExecuting()
    {
        return !this.enhancedChicken.getNavigator().noPath();
    }

    public void startExecuting()
    {
        if(postPos!=null) {
            int i = postPos.getX();
            int j = postPos.getY();
            int k = postPos.getZ();
            this.enhancedChicken.getNavigator().tryMoveToXYZ((double)i, (double)j+1, (double)k, 1.0D);
        }
    }

    public void resetTask()
    {
        postPos = null;
    }

//    public void updateTask()
//    {
//        super.updateTask();
//        this.enhancedChicken.setSitting(false);
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

//    protected boolean shouldMoveTo(World worldIn, BlockPos pos) {
//            IBlockState iblockstate = worldIn.getBlockState(pos);
//            Block block = iblockstate.getBlock();
//
//            if (block == ModBlocks.PostAcacia || block == ModBlocks.PostBirch || block == ModBlocks.PostDarkOak || block == ModBlocks.PostJungle || block == ModBlocks.PostOak || block == ModBlocks.PostSpruce) {
//                return true;
//            }
//            return false;
//    }
}
