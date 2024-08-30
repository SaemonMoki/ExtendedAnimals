package mokiyoki.enhancedanimals.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static mokiyoki.enhancedanimals.GeneticAnimals.MODID;
import static mokiyoki.enhancedanimals.init.ModItems.ITEMS_DEFERRED_REGISTRY;

public class ModCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB_DEFERRED_REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final RegistryObject<CreativeModeTab> CREATIVE_MODE_TAB = CREATIVE_MODE_TAB_DEFERRED_REGISTRY.register(MODID, () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + MODID))
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .icon(() -> new ItemStack(ModItems.EGG_BLUE.get()))
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .displayItems((itemDisplayParameters, output) -> {
                for(RegistryObject<Item> item : ITEMS_DEFERRED_REGISTRY.getEntries()){
                    output.accept(item.get());
                }
            })
            .build()
    );

    public static void register(IEventBus modEventBus) {
        CREATIVE_MODE_TAB_DEFERRED_REGISTRY.register(modEventBus);
    }
}
