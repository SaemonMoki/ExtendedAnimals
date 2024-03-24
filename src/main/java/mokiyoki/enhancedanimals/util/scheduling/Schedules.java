package mokiyoki.enhancedanimals.util.scheduling;

import mokiyoki.enhancedanimals.ai.general.AIStatus;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.init.ModMemoryModuleTypes;
import mokiyoki.enhancedanimals.tileentity.ChickenNestTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public enum Schedules {

    RESIZE_AND_REFRESH_TEXTURE_SCHEDULE("ResizeAndRefreshSchedule", (ticks) ->
            new AnimalScheduledFunction(ticks, (eaa) -> {
            if (eaa.getEnhancedAnimalAge() > 0 && eaa.level.getLevelData().getGameTime() > 0) {
                eaa.refreshDimensions();
                eaa.updateColouration = true;
            }
        }, EnhancedAnimalAbstract::isGrowing)
    ),

    DESPAWN_SCHEDULE("DespawnSchedule", (ticks) -> new AnimalScheduledFunction(ticks, EnhancedAnimalAbstract::despawn)),

    DESPAWN_NO_PASSENGER_SCHEDULE("DespawnNoPassengerSchedule", (ticks) ->
        new AnimalScheduledFunction(ticks, (eaa) -> eaa.despawn(), (eaa) -> !eaa.getPassengers().isEmpty())),

    CROW_SCHEDULE("CrowSchedule", (ticks) ->
        new AnimalScheduledFunction(ticks, (eaa) -> {
            if (eaa instanceof EnhancedChicken) {
                eaa.level.broadcastEntityEvent(eaa, (byte)11);
                ((EnhancedChicken)eaa).crowTick = 120;
                eaa.setAIStatus(AIStatus.FOCUSED);
            }
        })),

    START_PREEN_SCHEDULE("StartPreenSchedule", (ticks) ->
            new AnimalScheduledFunction(ticks, (eaa) -> {
                if (eaa instanceof EnhancedChicken) {
                    eaa.level.broadcastEntityEvent(eaa, (byte)12);
                    eaa.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
                    eaa.getBrain().eraseMemory(MemoryModuleType.LOOK_TARGET);
                    eaa.getBrain().setMemory(ModMemoryModuleTypes.PAUSE_WALKING.get(), true);
                }
            })),
    STOP_PREEN_SCHEDULE("StopPreenSchedule", (ticks) ->
            new AnimalScheduledFunction(ticks, (eaa) -> {
                if (eaa instanceof EnhancedChicken) {
                    eaa.level.broadcastEntityEvent(eaa, (byte)13);
                    eaa.setAIStatus(AIStatus.NONE);
                }
            })),

    LOOK_FOR_NEST_SCHEDULE("LookForNestSchedule", (ticks) ->
            new AnimalScheduledFunction(12000, (eaa) -> {
                if (eaa instanceof EnhancedChicken chicken) {
                    for (int x = -1; x <= 1; x++) {
                        for (int z = -1; z <= 1; z++) {
                            BlockPos pos = eaa.blockPosition().offset(x, 0, z);
                            if (eaa.level.getBlockEntity(pos) instanceof ChickenNestTileEntity nestTileEntity) {
                                if (nestTileEntity.isFull()) {
                                    continue;
                                }
                                chicken.rateNest(pos);
                            }

                            BlockState state = eaa.level.getBlockState(pos);
                            if (state.isAir()) {
                                if (eaa.level.isEmptyBlock(pos.below())) {
                                    pos = pos.below();
                                }
                            } else {
                                if (eaa.level.isEmptyBlock(pos.above())) {
                                    pos = pos.above();
                                }
                            }
                            if (chicken.isGoodNestSite(pos)) {
                                chicken.rateNest(pos);
                            }
                        }
                    }
                }
            }, LivingEntity::isAlive)
        ),

    CHECK_RAIN_STOPPED_SCHEDULE("CheckRainStoppedSchedule", (ticks) ->
            new AnimalScheduledFunction(ticks, (eaa) -> {
                if (!eaa.getLevel().getLevelData().isRaining()) {
                    eaa.getBrain().eraseMemory(ModMemoryModuleTypes.SEEKING_SHELTER.get());
                }
            }, EnhancedAnimalAbstract::isRainingInLevel)
    ),

    DISMOUNT_SCHEDULE("DismountSchedule", (ticks) -> new AnimalScheduledFunction(ticks, LivingEntity::stopRiding)),

    RIDE_MOTHER_HEN_SCHEDULE("RideMotherHenSchedule", (ticks) -> new AnimalScheduledFunction(ticks, (eaa) -> {
        if (eaa instanceof EnhancedChicken) {
            EnhancedAnimalAbstract parent = ((EnhancedChicken) eaa).followParentGoal.getParent();
            if (parent != null) {
                eaa.startRiding(parent);
            }
        }
    }));

    public final Function<Integer, AnimalScheduledFunction> function;

    public final String funcName;

    private static final Map<String, Schedules> lookup = new HashMap<String, Schedules>();

    static {
        for (Schedules d : Schedules.values()) {
            lookup.put(d.funcName, d);
        }
    }
    Schedules(String functionName, Function<Integer, AnimalScheduledFunction> function) {
        this.funcName = functionName;
        this.function = function;
    }

    public static AnimalScheduledFunction getScheduledFunction(String functionName, int ticks) {
        return lookup.get(functionName).function.apply(ticks);
    }

}
