package mokiyoki.enhancedanimals.ai.pathfinding;

import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EnhancedGroundPathNavigator extends GroundPathNavigator {
    private boolean shouldSeekShelter;

    public EnhancedGroundPathNavigator(MobEntity entitylivingIn, World worldIn) {
        super(entitylivingIn, worldIn);
    }

    protected void trimPath() {
        super.trimPath();
        if (this.shouldSeekShelter) {
            if (this.world.canSeeSky(new BlockPos(this.entity.getPosX(), this.entity.getPosY() + 0.5D, this.entity.getPosZ())) && this.world.hasWater(this.entity.getPosition())) {
                return;
            }

            for(int i = 0; i < this.currentPath.getCurrentPathLength(); ++i) {
                PathPoint pathpoint = this.currentPath.getPathPointFromIndex(i);
                if (this.world.canSeeSky(new BlockPos(pathpoint.x, pathpoint.y, pathpoint.z)) && this.world.hasWater(this.entity.getPosition())) {
                    this.currentPath.setCurrentPathLength(i);
                    return;
                }
            }
        }
    }

    public void setSeekShelter(boolean findShelter) {
        this.shouldSeekShelter = findShelter;
    }
}
