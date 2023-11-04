package mokiyoki.enhancedanimals.ai.brain.axolotl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import mokiyoki.enhancedanimals.entity.EnhancedAxolotl;
import mokiyoki.enhancedanimals.init.ModActivities;
import mokiyoki.enhancedanimals.init.ModMemoryModuleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class AxolotlBrain {
    private static final UniformInt ADULT_FOLLOW_RANGE = UniformInt.of(5, 16);
    private static final float SPEED_MULTIPLIER_WHEN_MAKING_LOVE = 0.2F;
    private static final float SPEED_MULTIPLIER_ON_LAND = 0.15F;
    private static final float SPEED_MULTIPLIER_WHEN_IDLING_IN_WATER = 0.5F;
    private static final float SPEED_MULTIPLIER_WHEN_CHASING_IN_WATER = 0.6F;
    private static final float SPEED_MULTIPLIER_WHEN_FOLLOWING_ADULT_IN_WATER = 0.6F;

    public static Brain<?> makeBrain(Brain<EnhancedAxolotl> axolotlBrain) {
        initCoreActivity(axolotlBrain);
        initIdleActivity(axolotlBrain);
        initFightActivity(axolotlBrain);
        initPlayDeadActivity(axolotlBrain);
        initPauseBrainActivity(axolotlBrain);
        axolotlBrain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        axolotlBrain.setDefaultActivity(Activity.IDLE);
        axolotlBrain.useDefaultActivity();
        return axolotlBrain;
    }

    private static void initPlayDeadActivity(Brain<EnhancedAxolotl> p_149297_) {
        p_149297_.addActivityAndRemoveMemoriesWhenStopped(
                Activity.PLAY_DEAD,
                ImmutableList.of(
                        Pair.of(0, new PlayDead()),
                        Pair.of(1, EraseMemoryIf.create(AxolotlBrain::isBreeding, MemoryModuleType.PLAY_DEAD_TICKS))
                ),
                ImmutableSet.of(
                        Pair.of(MemoryModuleType.PLAY_DEAD_TICKS, MemoryStatus.VALUE_PRESENT)
                ),
                ImmutableSet.of(MemoryModuleType.PLAY_DEAD_TICKS)
        );
    }

    private static void initPauseBrainActivity(Brain<EnhancedAxolotl> p_149297_) {
        p_149297_.addActivityAndRemoveMemoriesWhenStopped(
                ModActivities.PAUSE_BRAIN.get(),
                ImmutableList.of(
                        Pair.of(0, new PauseBrain()),
                        Pair.of(1, EraseMemoryIf.create(AxolotlBrain::isBreeding, ModMemoryModuleTypes.HAS_EGG.get()))
                ),
                ImmutableSet.of(
                        Pair.of(ModMemoryModuleTypes.HAS_EGG.get(), MemoryStatus.VALUE_PRESENT)
                ),
                ImmutableSet.of(ModMemoryModuleTypes.HAS_EGG.get())
        );
    }

    private static void initFightActivity(Brain<EnhancedAxolotl> p_149303_) {
        p_149303_.addActivityAndRemoveMemoryWhenStopped(
                Activity.FIGHT,
                0,
                ImmutableList.of(
                        StopAttackingIfTargetInvalid.create(EnhancedAxolotl::onStopAttacking),
                        SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(AxolotlBrain::getSpeedModifierChasing),
                        MeleeAttack.create(20),
                        EraseMemoryIf.<EnhancedAxolotl>create(AxolotlBrain::isBreeding, MemoryModuleType.ATTACK_TARGET)
                ),
                MemoryModuleType.ATTACK_TARGET
        );
    }

    private static void initCoreActivity(Brain<EnhancedAxolotl> p_149307_) {
        p_149307_.addActivity(Activity.CORE, 0, ImmutableList.of(
                new LookAtTargetSink(45, 90),
                new MoveToTargetSink(),
                new ValidatePlayDead(),
                new ValidatePauseBrain(),
                new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS))
        );
    }

    private static void initIdleActivity(Brain<EnhancedAxolotl> brain) {
        brain.addActivity(Activity.IDLE, ImmutableList.of(
                Pair.of(0, SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6.0F, UniformInt.of(30, 60))),
                Pair.of(1, new AnimalMakeLove(EntityType.AXOLOTL, 0.2F)),
                Pair.of(2, new RunOne<>(ImmutableList.of(
                        Pair.of(new FollowTemptation(AxolotlBrain::getSpeedModifier), 1),
                        Pair.of(BabyFollowAdult.create(ADULT_FOLLOW_RANGE, AxolotlBrain::getSpeedModifierFollowingAdult), 1)))
                ),
                Pair.of(3, StartAttacking.create(AxolotlBrain::findNearestValidAttackTarget)),
                Pair.of(3, TryFindWater.create(6, 0.15F)),
                Pair.of(4, new GateBehavior<>(
                        ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT),
                        ImmutableSet.of(),
                        GateBehavior.OrderPolicy.ORDERED,
                        GateBehavior.RunningPolicy.TRY_ALL,
                        ImmutableList.of(
                                Pair.of(RandomStroll.swim(0.5F), 2),
                                Pair.of(RandomStroll.stroll(0.15F, false), 2),
                                Pair.of(SetWalkTargetFromLookTarget.create(AxolotlBrain::canSetWalkTargetFromLookTarget, AxolotlBrain::getSpeedModifier, 3), 3),
                                Pair.of(BehaviorBuilder.triggerIf(Entity::isInWaterOrBubble), 5),
                                Pair.of(BehaviorBuilder.triggerIf(Entity::onGround), 5))
                        )
                )
        ));
    }

    private static boolean canSetWalkTargetFromLookTarget(LivingEntity livingEntity) {
        Level level = livingEntity.level();
        Optional<PositionTracker> optional = livingEntity.getBrain().getMemory(MemoryModuleType.LOOK_TARGET);
        if (optional.isPresent()) {
            BlockPos blockpos = optional.get().currentBlockPosition();
            return level.isWaterAt(blockpos) == livingEntity.isInWaterOrBubble();
        } else {
            return false;
        }
    }

    public static void updateActivity(EnhancedAxolotl axolotl) {
        Brain<EnhancedAxolotl> brain = axolotl.getBrain();
        Activity activity = brain.getActiveNonCoreActivity().orElse((Activity) null);
        if (activity != Activity.PLAY_DEAD && activity != ModActivities.PAUSE_BRAIN.get()) {
            brain.setActiveActivityToFirstValid(ImmutableList.of(Activity.PLAY_DEAD, ModActivities.PAUSE_BRAIN.get(), Activity.FIGHT, Activity.IDLE));
            if (activity == Activity.FIGHT && brain.getActiveNonCoreActivity().orElse((Activity) null) != Activity.FIGHT) {
                brain.setMemoryWithExpiry(MemoryModuleType.HAS_HUNTING_COOLDOWN, true, 2400L);
            }
        }

    }

    private static float getSpeedModifierChasing(LivingEntity livingEntity) {
        return livingEntity.isInWaterOrBubble() ? 0.6F : 0.15F;
    }

    private static float getSpeedModifierFollowingAdult(LivingEntity livingEntity) {
        return livingEntity.isInWaterOrBubble() ? 0.6F : 0.15F;
    }

    private static float getSpeedModifier(LivingEntity livingEntity) {
        return livingEntity.isInWaterOrBubble() ? 0.5F : 0.15F;
    }

    private static Optional<? extends LivingEntity> findNearestValidAttackTarget(EnhancedAxolotl axolotl) {
        return isBreeding(axolotl) ? Optional.empty() : axolotl.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE);
    }

    private static boolean isBreeding(EnhancedAxolotl axolotl) {
        return axolotl.getBrain().hasMemoryValue(MemoryModuleType.BREED_TARGET);
    }
}
