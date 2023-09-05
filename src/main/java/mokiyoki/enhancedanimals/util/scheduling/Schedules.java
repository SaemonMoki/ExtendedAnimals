package mokiyoki.enhancedanimals.util.scheduling;

import mokiyoki.enhancedanimals.blocks.NestBlock;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.tileentity.ChickenNestTileEntity;
import net.minecraft.core.BlockPos;
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
        }, (eaa) -> eaa.isGrowing())
    ),

    DESPAWN_SCHEDULE("DespawnSchedule", (ticks) -> new AnimalScheduledFunction(ticks, EnhancedAnimalAbstract::despawn)),

    DESPAWN_NO_PASSENGER_SCHEDULE("DespawnNoPassengerSchedule", (ticks) ->
        new AnimalScheduledFunction(ticks, (eaa) -> eaa.despawn(), (eaa) -> !eaa.getPassengers().isEmpty())),

    LOOK_FOR_NEST_SCHEDULE("LookForNestSchedule", (ticks) ->
            new AnimalScheduledFunction(ticks, (eaa) -> {
                if (eaa instanceof EnhancedChicken chicken) {
                    for (int x = -1; x < 1; x++) {
                        for (int z = -1; z < 1; z++) {
                            BlockPos pos = eaa.blockPosition().offset(x, 0, z);
                            BlockState state = eaa.level.getBlockState(pos);
                            if (eaa.level.getBlockEntity(pos) instanceof ChickenNestTileEntity) {

                            }
                            if (state.isAir()) {
                                if (eaa.level.isEmptyBlock(pos.below())) {
                                    pos = pos.below();
                                }
                            } else {
                                if (eaa.level.isEmptyBlock(pos.above())) {
                                    pos = pos.above();
                                }
                            }
                            chicken.isGoodNestSite(pos);
                        }
                    }
                }
            })
            )
    ;

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
