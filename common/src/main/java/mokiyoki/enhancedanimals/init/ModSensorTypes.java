package mokiyoki.enhancedanimals.init;

import mokiyoki.enhancedanimals.ai.brain.chicken.ChickenHostilesSensor;
import mokiyoki.enhancedanimals.ai.brain.sensor.EnhancedTemptingSensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.item.ItemStack;
import mokiyoki.enhancedanimals.platform.registry.RegistrationProvider;
import net.minecraft.core.registries.Registries;
import mokiyoki.enhancedanimals.platform.registry.RegistryEntry;

import java.util.function.Predicate;

import static mokiyoki.enhancedanimals.init.FoodSerialiser.*;
import static mokiyoki.enhancedanimals.util.Reference.MODID;

public class ModSensorTypes {

    private static final Predicate<ItemStack> AXOLOTL_FOOD = itemStack -> axolotlFoodMap().isFoodItem(itemStack.getItem());
    private static final Predicate<ItemStack> CHICKEN_FOOD = itemStack -> chickenFoodMap().isFoodItem(itemStack.getItem());
    private static final Predicate<ItemStack> COW_FOOD = itemStack -> cowFoodMap().isFoodItem(itemStack.getItem());

    public static final RegistrationProvider<SensorType<?>> MOD_SENSOR_TYPES_DEFERRED_REGISTRY = RegistrationProvider.get(Registries.SENSOR_TYPE, MODID);

    public static final RegistryEntry<SensorType<?>, SensorType<EnhancedTemptingSensor>> AXOLOTL_FOOD_TEMPTATIONS = MOD_SENSOR_TYPES_DEFERRED_REGISTRY.register("axolotl_food_temptations",
            () -> new SensorType<>(() -> new EnhancedTemptingSensor(AXOLOTL_FOOD)));

    public static final RegistryEntry<SensorType<?>, SensorType<EnhancedTemptingSensor>> CHICKEN_FOOD_TEMPTATIONS = MOD_SENSOR_TYPES_DEFERRED_REGISTRY.register("chicken_food_temptations",
            () -> new SensorType<>(() -> new EnhancedTemptingSensor(CHICKEN_FOOD)));

    public static final RegistryEntry<SensorType<?>, SensorType<EnhancedTemptingSensor>> COW_FOOD_TEMPTATIONS = MOD_SENSOR_TYPES_DEFERRED_REGISTRY.register("cow_food_temptations",
            () -> new SensorType<>(() -> new EnhancedTemptingSensor(COW_FOOD)));

    public static final RegistryEntry<SensorType<?>, SensorType<ChickenHostilesSensor>> CHICKEN_HOSTILES_SENSOR = MOD_SENSOR_TYPES_DEFERRED_REGISTRY.register("chicken_hostiles_sensor",
            () -> new SensorType<>(ChickenHostilesSensor::new));

    public static void init() {
    }

}
