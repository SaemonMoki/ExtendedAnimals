package mokiyoki.enhancedanimals.init;

import mokiyoki.enhancedanimals.ai.brain.sensor.EnhancedTemptingSensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Predicate;

import static mokiyoki.enhancedanimals.init.FoodSerialiser.axolotlFoodMap;
import static mokiyoki.enhancedanimals.init.FoodSerialiser.cowFoodMap;
import static mokiyoki.enhancedanimals.util.Reference.MODID;

public class ModSensorTypes {

    private static final Predicate<ItemStack> AXOLOTL_FOOD = itemStack -> axolotlFoodMap().isFoodItem(itemStack.getItem());
    private static final Predicate<ItemStack> COW_FOOD = itemStack -> cowFoodMap().isFoodItem(itemStack.getItem());

    public static final DeferredRegister<SensorType<?>> MOD_SENSOR_TYPES_DEFERRED_REGISTRY = DeferredRegister.create(ForgeRegistries.SENSOR_TYPES, MODID);

    public static final RegistryObject<SensorType<EnhancedTemptingSensor>> AXOLOTL_FOOD_TEMPTATIONS = MOD_SENSOR_TYPES_DEFERRED_REGISTRY.register("axolotl_food_temptations",
            () -> new SensorType<>(() -> new EnhancedTemptingSensor(AXOLOTL_FOOD)));

    public static final RegistryObject<SensorType<EnhancedTemptingSensor>> COW_FOOD_TEMPTATIONS = MOD_SENSOR_TYPES_DEFERRED_REGISTRY.register("cow_food_temptations",
            () -> new SensorType<>(() -> new EnhancedTemptingSensor(COW_FOOD)));

    public static void register(IEventBus modEventBus) {
        MOD_SENSOR_TYPES_DEFERRED_REGISTRY.register(modEventBus);
    }

}
