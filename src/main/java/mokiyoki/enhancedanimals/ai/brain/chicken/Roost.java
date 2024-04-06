package mokiyoki.enhancedanimals.ai.brain.chicken;

import com.google.common.collect.ImmutableMap;
import mokiyoki.enhancedanimals.ai.general.AIStatus;
import mokiyoki.enhancedanimals.capability.post.PostCapabilityProvider;
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.init.ModMemoryModuleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

import java.util.List;

public class Roost extends Behavior<EnhancedChicken> {

    private static final int MAX_DISTANCE = 1024;
    private BlockPos postPos;

    public Roost() {
        super(ImmutableMap.of(
                ModMemoryModuleTypes.FOCUS_BRAIN.get(), MemoryStatus.VALUE_ABSENT,
                ModMemoryModuleTypes.BROODING.get(), MemoryStatus.VALUE_ABSENT
            )
        );
    }

    protected boolean checkExtraStartConditions(ServerLevel serverLevel, EnhancedChicken chicken) {
        if (chicken.level.getDayTime()%24000 > 10500) {
            if (!chicken.isRoosting() && chicken.getAIStatus() != AIStatus.FOCUSED) {
                List<BlockPos> allPostPos = chicken.level.getCapability(PostCapabilityProvider.POST_CAP, null).orElseGet(null).getAllPostPos();
                if (allPostPos != null && !allPostPos.isEmpty()) {
                    BlockPos blockPosToGoTo = calculateClosestPost(allPostPos, chicken);
                    postPos = blockPosToGoTo;
                }
                return postPos != null;
            }
            return false;
        }
        if (chicken.isRoosting()){
            chicken.setRoosting(false);
        }
        return false;
    }

    protected void start(ServerLevel serverLevel, EnhancedChicken chicken, long p_24696_) {
        if(postPos!=null) {
            BehaviorUtils.setWalkAndLookTargetMemories(chicken, postPos.above(), 1F, 0);
        }

    }

    protected boolean canStillUse(ServerLevel serverLevel, EnhancedChicken chicken, long p_22547_) {
        if (!chicken.getBrain().getMemory(MemoryModuleType.WALK_TARGET).isPresent()) {
            chicken.setRoosting(true);
            chicken.getBrain().setMemory(ModMemoryModuleTypes.ROOSTING.get(), true);
            return false;
        }
        return true;
    }


    protected void stop(ServerLevel serverLevel, EnhancedChicken chicken, long p_22440_) {
        chicken.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
        chicken.getBrain().eraseMemory(MemoryModuleType.LOOK_TARGET);
        chicken.setRoosting(true);
        this.postPos = null;
    }

    private BlockPos calculateClosestPost(List<BlockPos> blockPosList, EnhancedChicken chicken) {
        BlockPos nearestPostPos = null;
        int i = Integer.MAX_VALUE;

        for (BlockPos postPos : blockPosList)
        {
            int j = (int)postPos.distSqr(chicken.blockPosition());

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
}
