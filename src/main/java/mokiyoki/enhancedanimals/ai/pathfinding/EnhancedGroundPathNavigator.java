package mokiyoki.enhancedanimals.ai.pathfinding;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

public class EnhancedGroundPathNavigator extends PathNavigation {
    private boolean shouldSeekShelter;
    private boolean isHot;

    public EnhancedGroundPathNavigator(Mob entitylivingIn, Level worldIn) {
        super(entitylivingIn, worldIn);
    }

    protected PathFinder createPathFinder(int p_179679_1_) {
        this.nodeEvaluator = new WalkNodeEvaluator();
        this.nodeEvaluator.setCanPassDoors(true);
        return new PathFinder(this.nodeEvaluator, p_179679_1_);
    }

    protected boolean canUpdatePath() {
        return this.mob.onGround() || this.isInLiquid() || this.mob.isPassenger();
    }

    protected Vec3 getTempMobPos() {
        return new Vec3(this.mob.getX(), (double)this.getPathablePosY(), this.mob.getZ());
    }

    public Path createPath(BlockPos pos, int p_179680_2_) {
        if (this.level.getBlockState(pos).isAir()) {
            BlockPos blockpos;
            for(blockpos = pos.below(); blockpos.getY() > 0 && this.level.getBlockState(blockpos).isAir(); blockpos = blockpos.below()) {
            }

            if (blockpos.getY() > 0) {
                return super.createPath(blockpos.above(), p_179680_2_);
            }

            while(blockpos.getY() < this.level.getMaxBuildHeight() && this.level.getBlockState(blockpos).isAir()) {
                blockpos = blockpos.above();
            }

            pos = blockpos;
        }

        if (!this.level.getBlockState(pos).isSolid()) {
            return super.createPath(pos, p_179680_2_);
        } else {
            BlockPos blockpos1;
            for(blockpos1 = pos.above(); blockpos1.getY() < this.level.getMaxBuildHeight() && this.level.getBlockState(blockpos1).isSolid(); blockpos1 = blockpos1.above()) {
            }

            return super.createPath(blockpos1, p_179680_2_);
        }
    }

    public Path createPath(Entity entityIn, int p_75494_2_) {
        return this.createPath(entityIn.blockPosition(), p_75494_2_);
    }

    private int getPathablePosY() {
        if (this.mob.isInWater() && this.canFloat()) {
            int i = Mth.floor(this.mob.getY());
            Block block = this.level.getBlockState(BlockPos.containing(this.mob.getX(), (double)i, this.mob.getZ())).getBlock();
            int j = 0;

            while(block == Blocks.WATER) {
                ++i;
                block = this.level.getBlockState(BlockPos.containing(this.mob.getX(), (double)i, this.mob.getZ())).getBlock();
                ++j;
                if (j > 16) {
                    return Mth.floor(this.mob.getY());
                }
            }

            return i;
        } else {
            return Mth.floor(this.mob.getY() + 0.5D);
        }
    }

    protected void trimPath() {
        super.trimPath();
        if (this.shouldSeekShelter) {
            if (this.isHot) {
                if (this.level.canSeeSky(BlockPos.containing(this.mob.getX(), this.mob.getY() + 0.5D, this.mob.getZ()))) {
                    return;
                }
            } else {
                if (this.level.canSeeSky(BlockPos.containing(this.mob.getX(), this.mob.getY() + 0.5D, this.mob.getZ())) && this.level.isWaterAt(this.mob.blockPosition())) {
                    return;
                }
            }


            for(int i = 0; i < this.path.getNodeCount(); ++i) {
                Node pathpoint = this.path.getNode(i);
                if (this.level.canSeeSky(new BlockPos(pathpoint.x, pathpoint.y, pathpoint.z))) {
                    if (this.isHot) {
                        this.path.truncateNodes(i);
                        return;
                    } else if (this.level.isWaterAt(this.mob.blockPosition())) {
                        this.path.truncateNodes(i);
                        return;
                    }
                }
            }
        }
    }

    public void setSeekShelter(boolean findShelter, boolean isHot) {
        this.shouldSeekShelter = findShelter;
        this.isHot = isHot;
    }
}
