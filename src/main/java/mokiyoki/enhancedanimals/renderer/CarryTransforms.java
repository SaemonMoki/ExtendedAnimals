package mokiyoki.enhancedanimals.renderer;

import mokiyoki.enhancedanimals.init.ModEntities;
import net.minecraft.world.entity.EntityType;

import java.util.HashMap;
import java.util.Map;

//per-species carry offsets to fix all the crap that can occur with rendering directions
//yes this is bullshit that I need to do this
public final class CarryTransforms {

    private static final CarryTransform DEFAULT_FIRST_PERSON = new CarryTransform(0.3D, -0.4D, 1.3D, 0F, false);
    private static final CarryTransform DEFAULT_THIRD_PERSON = new CarryTransform(-0.1D, 0.8D, -0.85D, 180F, true);

    private static final Map<EntityType<?>, CarryTransform> FIRST_PERSON = new HashMap<>();
    private static final Map<EntityType<?>, CarryTransform> THIRD_PERSON = new HashMap<>();

    static {
        FIRST_PERSON.put(ModEntities.ENHANCED_CHICKEN.get(), new CarryTransform(0.0D, -0.4D, 1.3D, 0F, false));
        FIRST_PERSON.put(ModEntities.ENHANCED_PIG.get(), new CarryTransform(0.6D, -0.4D, 1.4D, 0F, false));
        FIRST_PERSON.put(ModEntities.ENHANCED_COW.get(), new CarryTransform(0.6D, -0.4D, 1.4D, 0F, false));

        THIRD_PERSON.put(ModEntities.ENHANCED_CHICKEN.get(), new CarryTransform(-0.1D, 0.6D, -0.85D, 180F, true));
        THIRD_PERSON.put(ModEntities.ENHANCED_RABBIT.get(), new CarryTransform(-0.1D, 0.6D, -0.85D, 180F, true));
        THIRD_PERSON.put(ModEntities.ENHANCED_AXOLOTL.get(), new CarryTransform(-0.1D, 0.6D, -0.85D, 180F, true));

    }

    public static CarryTransform firstPerson(EntityType<?> type) {
        return FIRST_PERSON.getOrDefault(type, DEFAULT_FIRST_PERSON);
    }

    public static CarryTransform thirdPerson(EntityType<?> type) {
        return THIRD_PERSON.getOrDefault(type, DEFAULT_THIRD_PERSON);
    }

    private CarryTransforms() {
    }
}
