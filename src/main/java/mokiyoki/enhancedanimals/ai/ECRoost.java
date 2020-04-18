package mokiyoki.enhancedanimals.ai;

import mokiyoki.enhancedanimals.capability.post.PostCapabilityProvider;
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class ECRoost extends Goal {

    private static final int MAX_DISTANCE = 256;
    private final EnhancedChicken enhancedChicken;
    private BlockPos postPos;



    public ECRoost(CreatureEntity entityIn) {
        this.enhancedChicken = (EnhancedChicken) entityIn;
    }

    public boolean shouldExecute() {
        if (!this.enhancedChicken.world.isDaytime()) {
            if (!this.enhancedChicken.isRoosting()) {
                List<BlockPos> allPostPos = this.enhancedChicken.world.getCapability(PostCapabilityProvider.POST_CAP, null).orElseGet(null).getAllPostPos();
                if (allPostPos != null && !allPostPos.isEmpty()) {
                    BlockPos blockPosToGoTo = calculateClosestPost(allPostPos);
                    postPos = blockPosToGoTo;
                }
                return true;
            }
            return false;
        }
        if (this.enhancedChicken.isRoosting()){
            this.enhancedChicken.setRoosting(false);
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

    public boolean shouldContinueExecuting() {
        if (this.enhancedChicken.getNavigator().noPath()) {
            this.enhancedChicken.setRoosting(true);
            return false;
        }
        return true;
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
//        this.enhancedChicken.setRoosting(false);
//
//        if (!this.getIsAboveDestination())
//        {
//            this.enhancedChicken.setRoosting(false);
//        }
//        else if (!this.enhancedChicken.isRoosting())
//        {
//            this.enhancedChicken.setRoosting(true);
//        }
//    }
}
