package mokiyoki.enhancedanimals.util.scheduling;

import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public enum Schedules {

    RESIZE_AND_REFRESH_TEXTURE_SCHEDULE("ResizeAndRefreshSchedule", (ticks) -> new AnimalScheduledFunction(ticks, (eaa) -> {
            if (eaa.getEnhancedAnimalAge() > 0 && eaa.level().getLevelData().getGameTime() > 0) {
                eaa.refreshDimensions();
                eaa.updateColouration = true;
            }
        }, (eaa) -> eaa.isGrowing())
    ),

    DESPAWN_SCHEDULE("DespawnSchedule", (ticks) -> new AnimalScheduledFunction(ticks, EnhancedAnimalAbstract::despawn)),

    DESPAWN_NO_PASSENGER_SCHEDULE("DespawnNoPassengerSchedule", (ticks) ->
        new AnimalScheduledFunction(ticks, (eaa) -> eaa.despawn(), (eaa) -> !eaa.getPassengers().isEmpty())
    );

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
