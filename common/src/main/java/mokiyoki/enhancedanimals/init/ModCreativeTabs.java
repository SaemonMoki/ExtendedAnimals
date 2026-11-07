package mokiyoki.enhancedanimals.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import mokiyoki.enhancedanimals.platform.Services;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import mokiyoki.enhancedanimals.platform.registry.RegistryEntry;
import mokiyoki.enhancedanimals.platform.registry.RegistrationProvider;

import java.util.Set;

import static mokiyoki.enhancedanimals.init.ModItems.*;
import static mokiyoki.enhancedanimals.util.Reference.MODID;

public class ModCreativeTabs {

    public static final RegistrationProvider<CreativeModeTab> CREATIVE_MODE_TAB_DEFERRED_REGISTRY = RegistrationProvider.get(Registries.CREATIVE_MODE_TAB, MODID);

    public static final RegistryEntry<CreativeModeTab, CreativeModeTab> GENETICS_ANIMALS_GROUP = CREATIVE_MODE_TAB_DEFERRED_REGISTRY.register(MODID, () -> Services.PLATFORM.creativeTabBuilder(ResourceKey.create(Registries.CREATIVE_MODE_TAB, ResourceLocation.withDefaultNamespace("spawn_eggs")))
            .title(Component.translatable("itemGroup." + MODID))
            .icon(() -> new ItemStack(ModItems.EGG_BLUE.get()))
            .displayItems((itemDisplayParameters, output) -> {
                Set<RegistryEntry<Item, ? extends Item>> hiddenItems = Set.of(ENHANCED_AXOLOTL_BUCKET, ENHANCED_AXOLOTL_EGG_BUCKET,
                        GROWABLE_ALLIUM_ITEM, GROWABLE_AZURE_BLUET_ITEM, GROWABLE_BLUE_ORCHID_ITEM, GROWABLE_CORNFLOWER_ITEM,
                        GROWABLE_DANDELION_ITEM, GROWABLE_OXEYE_DAISY_ITEM, GROWABLE_GRASS_ITEM, GROWABLE_FERN_ITEM,
                        GROWABLE_ROSE_BUSH_ITEM, GROWABLE_SUNFLOWER_ITEM, GROWABLE_TALL_GRASS_ITEM, GROWABLE_LARGE_FERN_ITEM);
                for (RegistryEntry<Item, ? extends Item> item : ITEMS_DEFERRED_REGISTRY.getEntries()) {
                    if (!hiddenItems.contains(item)) {
                        output.accept(item.get());
                    }
                }
            })
            .build()
    );

    public static void init() {
    }
}
