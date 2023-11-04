package mokiyoki.enhancedanimals.ai.pathfinding;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.pathfinder.PathComputationType;
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
            Block block = this.level.getBlockState(new BlockPos(Mth.floor(this.mob.getX()), i, Mth.floor(this.mob.getZ()))).getBlock();
            int j = 0;

            while(block == Blocks.WATER) {
                ++i;
                block = this.level.getBlockState(new BlockPos(Mth.floor(this.mob.getX()), i, Mth.floor(this.mob.getZ()))).getBlock();
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
                if (this.level.canSeeSky(new BlockPos(Mth.floor(this.mob.getX()), Mth.floor(this.mob.getY() + 0.5D), Mth.floor(this.mob.getZ())))) {
                    return;
                }
            } else {
                if (this.level.canSeeSky(new BlockPos(Mth.floor(this.mob.getX()), Mth.floor(this.mob.getY() + 0.5D), Mth.floor(this.mob.getZ()))) && this.level.isWaterAt(this.mob.blockPosition())) {
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

//    /**
//     * Checks if the specified entity can safely walk to the specified location.
//     */
//    protected boolean canMoveDirectly(Vec3 posVec31, Vec3 posVec32, int sizeX, int sizeY, int sizeZ) {
//        int i = Mth.floor(posVec31.x);
//        int j = Mth.floor(posVec31.z);
//        double d0 = posVec32.x - posVec31.x;
//        double d1 = posVec32.z - posVec31.z;
//        double d2 = d0 * d0 + d1 * d1;
//        if (d2 < 1.0E-8D) {
//            return false;
//        } else {
//            double d3 = 1.0D / Math.sqrt(d2);
//            d0 = d0 * d3;
//            d1 = d1 * d3;
//            sizeX = sizeX + 2;
//            sizeZ = sizeZ + 2;
//            if (!this.isSafeToStandAt(i, Mth.floor(posVec31.y), j, sizeX, sizeY, sizeZ, posVec31, d0, d1)) {
//                return false;
//            } else {
//                sizeX = sizeX - 2;
//                sizeZ = sizeZ - 2;
//                double d4 = 1.0D / Math.abs(d0);
//                double d5 = 1.0D / Math.abs(d1);
//                double d6 = (double)i - posVec31.x;
//                double d7 = (double)j - posVec31.z;
//                if (d0 >= 0.0D) {
//                    ++d6;
//                }
//
//                if (d1 >= 0.0D) {
//                    ++d7;
//                }
//
//                d6 = d6 / d0;
//                d7 = d7 / d1;
//                int k = d0 < 0.0D ? -1 : 1;
//                int l = d1 < 0.0D ? -1 : 1;
//                int i1 = Mth.floor(posVec32.x);
//                int j1 = Mth.floor(posVec32.z);
//                int k1 = i1 - i;
//                int l1 = j1 - j;
//
//                while(k1 * k > 0 || l1 * l > 0) {
//                    if (d6 < d7) {
//                        d6 += d4;
//                        i += k;
//                        k1 = i1 - i;
//                    } else {
//                        d7 += d5;
//                        j += l;
//                        l1 = j1 - j;
//                    }
//
//                    if (!this.isSafeToStandAt(i, Mth.floor(posVec31.y), j, sizeX, sizeY, sizeZ, posVec31, d0, d1)) {
//                        return false;
//                    }
//                }
//
//                return true;
//            }
//        }
//    }
//
//    /**
//     * Returns true when an entity could stand at a position, including solid blocks under the entire entity.
//     */
//    private boolean isSafeToStandAt(int x, int y, int z, int sizeX, int sizeY, int sizeZ, Vec3 vec31, double p_179683_8_, double p_179683_10_) {
//        int i = x - sizeX / 2;
//        int j = z - sizeZ / 2;
//        if (!this.isPositionClear(i, y, j, sizeX, sizeY, sizeZ, vec31, p_179683_8_, p_179683_10_)) {
//            return false;
//        } else {
//            for(int k = i; k < i + sizeX; ++k) {
//                for(int l = j; l < j + sizeZ; ++l) {
//                    double d0 = (double)k + 0.5D - vec31.x;
//                    double d1 = (double)l + 0.5D - vec31.z;
//                    if (!(d0 * p_179683_8_ + d1 * p_179683_10_ < 0.0D)) {
//                        BlockPathTypes pathnodetype = this.nodeEvaluator.getBlockPathType(this.level, k, y - 1, l, this.mob, sizeX, sizeY, sizeZ, false, true);
//                        if (!this.hasValidPathType(pathnodetype)) {
//                            return false;
//                        }
//
//                        pathnodetype = this.nodeEvaluator.getBlockPathType(this.level, k, y, l, this.mob, sizeX, sizeY, sizeZ, false, true);
//                        float f = this.mob.getPathfindingMalus(pathnodetype);
//                        if (f < 0.0F || f >= 8.0F) {
//                            return false;
//                        }
//
//                        if (pathnodetype == BlockPathTypes.DAMAGE_FIRE || pathnodetype == BlockPathTypes.DANGER_FIRE || pathnodetype == BlockPathTypes.DAMAGE_OTHER) {
//                            return false;
//                        }
//                    }
//                }
//            }
//
//            return true;
//        }
//    }

    protected boolean hasValidPathType(BlockPathTypes nodeType) {
        if (nodeType == BlockPathTypes.WATER) {
            return false;
        } else if (nodeType == BlockPathTypes.LAVA) {
            return false;
        } else {
            return nodeType != BlockPathTypes.OPEN;
        }
    }

    /**
     * Returns true if an entity does not collide with any solid blocks at the position.
     */
    private boolean isPositionClear(int x, int y, int z, int sizeX, int sizeY, int sizeZ, Vec3 p_179692_7_, double p_179692_8_, double p_179692_10_) {
        for(BlockPos blockpos : BlockPos.betweenClosed(new BlockPos(x, y, z), new BlockPos(x + sizeX - 1, y + sizeY - 1, z + sizeZ - 1))) {
            double d0 = (double)blockpos.getX() + 0.5D - p_179692_7_.x;
            double d1 = (double)blockpos.getZ() + 0.5D - p_179692_7_.z;
            if (!(d0 * p_179692_8_ + d1 * p_179692_10_ < 0.0D) && !this.level.getBlockState(blockpos).isPathfindable(this.level, blockpos, PathComputationType.LAND)) {
                return false;
            }
        }

        return true;
    }

    public void setSeekShelter(boolean findShelter, boolean isHot) {
        this.shouldSeekShelter = findShelter;
        this.isHot = isHot;
    }
}
