package mokiyoki.enhancedanimals.ai;

import mokiyoki.enhancedanimals.capability.post.PostCapabilityProvider;
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.core.BlockPos;

import java.util.List;

public class ECRoost extends Goal {

    private static final int MAX_DISTANCE = 256;
    private final EnhancedChicken enhancedChicken;
    private BlockPos postPos;



    public ECRoost(PathfinderMob entityIn) {
        this.enhancedChicken = (EnhancedChicken) entityIn;
    }

    public boolean canUse() {
        if (!this.enhancedChicken.level().isDay()) {
            if (!this.enhancedChicken.isRoosting()) {
                List<BlockPos> allPostPos = this.enhancedChicken.level().getCapability(PostCapabilityProvider.POST_CAP, null).orElseGet(null).getAllPostPos();
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
        return (int)pos.distSqr(this.enhancedChicken.blockPosition());
    }

    public boolean canContinueToUse() {
        if (this.enhancedChicken.getNavigation().isDone()) {
            this.enhancedChicken.setRoosting(true);
            return false;
        }
        return true;
    }

    public void start()
    {
        if(postPos!=null) {
            int i = postPos.getX();
            int j = postPos.getY();
            int k = postPos.getZ();
            this.enhancedChicken.getNavigation().moveTo((double)i, (double)j+1, (double)k, 1.0D);
        }
    }

    public void stop()
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
