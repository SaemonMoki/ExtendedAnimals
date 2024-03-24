package mokiyoki.enhancedanimals.ai.brain.chicken;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import mokiyoki.enhancedanimals.ai.brain.BabyFollowParent;
import mokiyoki.enhancedanimals.ai.brain.Grazing;
import mokiyoki.enhancedanimals.ai.brain.SeekShelter;
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.init.ModActivities;
import mokiyoki.enhancedanimals.init.ModEntities;
import mokiyoki.enhancedanimals.init.ModMemoryModuleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class ChickenBrain {
    private static final UniformInt ADULT_FOLLOW_RANGE = UniformInt.of(5, 16);

    public static Brain<?> makeBrain(Brain<EnhancedChicken> chickenBrain) {
        initCoreActivity(chickenBrain);
        initIdleActivity(chickenBrain);
        initPanicActivity(chickenBrain);
//        initFightActivity(chickenBrain);
        initPauseBrainActivity(chickenBrain);
        chickenBrain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        chickenBrain.setDefaultActivity(Activity.IDLE);
        chickenBrain.useDefaultActivity();
        return chickenBrain;
    }

    private static void initPauseBrainActivity(Brain<EnhancedChicken> p_149297_) {
        p_149297_.addActivityAndRemoveMemoriesWhenStopped(
                ModActivities.PAUSE_BRAIN.get(),
                ImmutableList.of(
                        Pair.of(0, new PauseBrain())
//                        Pair.of(1, new EraseMemoryIf<>(ChickenBrain::isBreeding, ModMemoryModuleTypes.BROODING.get()))
                ),
                ImmutableSet.of(
                        Pair.of(ModMemoryModuleTypes.PAUSE_BRAIN.get(), MemoryStatus.VALUE_PRESENT)
                ),
                ImmutableSet.of(
                        ModMemoryModuleTypes.PAUSE_BRAIN.get()
                )
        );
    }

    private static void initPanicActivity(Brain<EnhancedChicken> chickenBrain) {
        chickenBrain.addActivity(
                Activity.PANIC,
                ImmutableList.of(
                        Pair.of(0, new CalmDown()),
                        Pair.of(1, SetWalkTargetAwayFrom.entity(MemoryModuleType.NEAREST_HOSTILE, 2.5F, 10, false)),
                        Pair.of(1, SetWalkTargetAwayFrom.entity(MemoryModuleType.HURT_BY_ENTITY, 2.5F, 10, false))
                )
        );
    }



//    private static void initFightActivity(Brain<EnhancedChicken> chickenBrain) {
//        chickenBrain.addActivityAndRemoveMemoryWhenStopped(
//                Activity.FIGHT,
//                0,
//                ImmutableList.of(
//                        new StopAttackingIfTargetInvalid<>(EnhancedChicken::onStopAttacking),
//                        new SetWalkTargetFromAttackTargetIfTargetOutOfReach(ChickenBrain::getSpeedModifierChasing),
//                        new MeleeAttack(20),
//                        new EraseMemoryIf<EnhancedChicken>(ChickenBrain::isBreeding, MemoryModuleType.ATTACK_TARGET)
//                ),
//                MemoryModuleType.ATTACK_TARGET
//        );
//    }

    private static void initCoreActivity(Brain<EnhancedChicken> chickenBrain) {
        chickenBrain.addActivity(Activity.CORE, 0, ImmutableList.of(
                new LookAtTargetSink(45, 90),
                new MoveToTargetSink(),
                new Swim(0.8F),
                new SeekShelter(),
                new Roost(),
                new GrazingChicken(),
                new Nesting(),
                new ChickenPanicTrigger(),
                new ValidatePauseBrain(),
                new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS),
                new CountDownCooldownTicks(ModMemoryModuleTypes.PAUSE_BETWEEN_EATING.get()))
        );
    }

    private static void initIdleActivity(Brain<EnhancedChicken> brain) {
        brain.addActivity(Activity.IDLE, ImmutableList.of(
                Pair.of(0, new RunSometimes<>(new RunIf<>(ChickenBrain::canMoveOrLookAround, new SetEntityLookTarget(EntityType.PLAYER, 6.0F)), UniformInt.of(30, 60))),
                Pair.of(1, new RunSometimes<>(new RunIf<>(ChickenBrain::canMoveOrLookAround, new SetEntityLookTarget(ModEntities.ENHANCED_CHICKEN.get(), 6.0F)), UniformInt.of(30, 60))),
                Pair.of(1, new AnimalMakeLove(ModEntities.ENHANCED_CHICKEN.get(), 1.0F)),
                Pair.of(2, new RunOne<>(ImmutableList.of(
                        Pair.of(new FollowTemptation(ChickenBrain::getSpeedModifier), 1),
                        Pair.of(new BabyFollowParent<>(ADULT_FOLLOW_RANGE, ChickenBrain::getSpeedModifierFollowingAdult), 1)))
                ),
                Pair.of(3, new StartAttacking<>(ChickenBrain::findNearestValidAttackTarget)),
                Pair.of(4, new GateBehavior<>(
                        ImmutableMap.of(
                                MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT,
                                ModMemoryModuleTypes.PAUSE_BRAIN.get(), MemoryStatus.VALUE_ABSENT,
                                ModMemoryModuleTypes.PAUSE_WALKING.get(), MemoryStatus.VALUE_ABSENT,
                                ModMemoryModuleTypes.SEEKING_SHELTER.get(), MemoryStatus.VALUE_ABSENT,
                                ModMemoryModuleTypes.ROOSTING.get(), MemoryStatus.VALUE_ABSENT,
                                ModMemoryModuleTypes.BROODING.get(), MemoryStatus.VALUE_ABSENT
                        ),
                        ImmutableSet.of(),
                        GateBehavior.OrderPolicy.SHUFFLED,
                        GateBehavior.RunningPolicy.RUN_ONE,
                        ImmutableList.of(
                                Pair.of(new RunSometimes<>(new RandomStroll(1.0F), UniformInt.of(30, 60)), 2),
                                Pair.of(new SetWalkTargetFromLookTarget(ChickenBrain::canSetWalkTargetFromLookTarget, ChickenBrain::getSpeedModifier, 3), 3),
                                Pair.of(new RunIf<>(Entity::isOnGround, new DoNothing(300, 600)), 1))
                        )
                )
        ));
    }

    private static boolean canSetWalkTargetFromLookTarget(LivingEntity livingEntity) {
        Level level = livingEntity.level;
        Optional<PositionTracker> optional = livingEntity.getBrain().getMemory(MemoryModuleType.LOOK_TARGET);
        if (optional.isPresent()) {
            BlockPos blockpos = optional.get().currentBlockPosition();
            return true;
        } else {
            return false;
        }
    }

    public static void updateActivity(EnhancedChicken chicken) {
        Brain<EnhancedChicken> brain = chicken.getBrain();
        Activity activity = brain.getActiveNonCoreActivity().orElse((Activity) null);
        if (activity != ModActivities.PAUSE_BRAIN.get()) {
            brain.setActiveActivityToFirstValid(ImmutableList.of(ModActivities.PAUSE_BRAIN.get(), Activity.PANIC, Activity.FIGHT, Activity.IDLE));
            if (activity == Activity.FIGHT && brain.getActiveNonCoreActivity().orElse((Activity) null) != Activity.FIGHT) {
                brain.setMemoryWithExpiry(MemoryModuleType.HAS_HUNTING_COOLDOWN, true, 2400L);
            }
        }

    }

    private static float getSpeedModifierChasing(LivingEntity livingEntity) {
        return 1.4F;
    }

    private static float getSpeedModifierFollowingAdult(LivingEntity livingEntity) {
        return 1.4F;
    }

    private static float getSpeedModifier(LivingEntity livingEntity) {
        return 1.0F;
    }

    private static Optional<? extends LivingEntity> findNearestValidAttackTarget(EnhancedChicken chicken) {
        return isBreeding(chicken) ? Optional.empty() : chicken.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE);
    }

    private static boolean isBreeding(EnhancedChicken chicken) {
        return chicken.getBrain().hasMemoryValue(MemoryModuleType.BREED_TARGET);
    }

    private static boolean isPaused(EnhancedChicken chicken) {
        return chicken.getBrain().hasMemoryValue(ModMemoryModuleTypes.PAUSE_BRAIN.get());
    }

    private static boolean canMoveOrLookAround(EnhancedChicken chicken) {
        return !chicken.getBrain().hasMemoryValue(ModMemoryModuleTypes.PAUSE_BRAIN.get()) &&
               !chicken.getBrain().hasMemoryValue(ModMemoryModuleTypes.ROOSTING.get()) &&
               !chicken.getBrain().hasMemoryValue(ModMemoryModuleTypes.PAUSE_WALKING.get()) &&
               !chicken.getBrain().hasMemoryValue(ModMemoryModuleTypes.BROODING.get()) &&
               !chicken.getBrain().hasMemoryValue(ModMemoryModuleTypes.SLEEPING.get());
    }
}
