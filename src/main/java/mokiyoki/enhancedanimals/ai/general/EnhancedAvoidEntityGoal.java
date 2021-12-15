package mokiyoki.enhancedanimals.ai.general;

import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import mokiyoki.enhancedanimals.entity.Temperament;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.ai.util.RandomPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.Map;
import java.util.function.Predicate;

public class EnhancedAvoidEntityGoal<T extends LivingEntity> extends Goal {
    protected final PathfinderMob entity;
    private final double farSpeed;
    private final double nearSpeed;
    protected T toAvoid;
    protected final float avoidDistance;
    protected Path path;
    protected final PathNavigation navigation;
    protected final Class<T> classToAvoid;
    protected final Predicate<LivingEntity> avoidTargetSelector;
    protected final Predicate<LivingEntity> predicateOnAvoidEntity;
    private final TargetingConditions avoidEntityTargeting;
    Map<Temperament, Integer> temperaments;

    public EnhancedAvoidEntityGoal(PathfinderMob entityIn, Class<T> classToAvoidIn, float avoidDistanceIn, double farSpeedIn, double nearSpeedIn, Map<Temperament, Integer> temperaments) {
        this(entityIn, classToAvoidIn, (p_200828_0_) -> {
            return true;
        }, avoidDistanceIn, farSpeedIn, nearSpeedIn, EntitySelector.NO_CREATIVE_OR_SPECTATOR::test, temperaments);
    }

    public EnhancedAvoidEntityGoal(PathfinderMob entityIn, Class<T> avoidClass, Predicate<LivingEntity> targetPredicate, float distance, double nearSpeedIn, double farSpeedIn, Predicate<LivingEntity> p_i48859_9_, Map<Temperament, Integer> temperaments) {
        this.entity = entityIn;
        this.classToAvoid = avoidClass;
        this.avoidTargetSelector = targetPredicate;
        this.avoidDistance = distance;
        this.farSpeed = nearSpeedIn;
        this.nearSpeed = farSpeedIn;
        this.predicateOnAvoidEntity = p_i48859_9_;
        this.navigation = entityIn.getNavigation();
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        this.avoidEntityTargeting = TargetingConditions.forCombat().range((double)distance).selector(p_i48859_9_.and(targetPredicate));
    }

    public EnhancedAvoidEntityGoal(PathfinderMob p_i48860_1_, Class<T> p_i48860_2_, float p_i48860_3_, double p_i48860_4_, double p_i48860_6_, Predicate<LivingEntity> p_i48860_8_, Map<Temperament, Integer> temperaments) {
        this(p_i48860_1_, p_i48860_2_, (p_203782_0_) -> {
            return true;
        }, p_i48860_3_, p_i48860_4_, p_i48860_6_, p_i48860_8_, temperaments);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean canUse() {
        this.toAvoid = this.entity.level.getNearestEntity(this.classToAvoid, this.avoidEntityTargeting, this.entity, this.entity.getX(), this.entity.getY(), this.entity.getZ(), this.entity.getBoundingBox().inflate((double)this.avoidDistance, 3.0D, (double)this.avoidDistance));
        if (this.toAvoid == null) {
            return false;
        } else {
            Vec3 vec3d = DefaultRandomPos.getPosAway(this.entity, 16, 7, new Vec3(this.toAvoid.getX(), this.toAvoid.getY(), this.toAvoid.getZ()));
            if (vec3d == null) {
                return false;
            } else if (this.toAvoid.distanceToSqr(vec3d.x, vec3d.y, vec3d.z) < this.toAvoid.distanceToSqr(this.entity)) {
                return false;
            } else {
                ((EnhancedAnimalAbstract)this.entity).awaken();
                this.path = this.navigation.createPath(vec3d.x, vec3d.y, vec3d.z, 0);
                return this.path != null;
            }
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean canContinueToUse() {
        return !this.navigation.isDone();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start() {
        this.navigation.moveTo(this.path, this.farSpeed);
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void stop() {
        this.toAvoid = null;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        if (this.entity.distanceToSqr(this.toAvoid) < 49.0D) {
            this.entity.getNavigation().setSpeedModifier(this.nearSpeed);
        } else {
            this.entity.getNavigation().setSpeedModifier(this.farSpeed);
        }

    }


}