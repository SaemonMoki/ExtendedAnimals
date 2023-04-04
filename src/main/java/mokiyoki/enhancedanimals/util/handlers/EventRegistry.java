package mokiyoki.enhancedanimals.util.handlers;

import mokiyoki.enhancedanimals.blocks.EggCartonContainer;
import mokiyoki.enhancedanimals.capability.egg.IEggCapability;
import mokiyoki.enhancedanimals.capability.hay.IHayCapability;
import mokiyoki.enhancedanimals.capability.post.IPostCapability;
import mokiyoki.enhancedanimals.capability.nestegg.INestEggCapability;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import mokiyoki.enhancedanimals.entity.EnhancedAxolotl;
import mokiyoki.enhancedanimals.entity.EnhancedAxolotlEgg;
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.entity.EnhancedHorse;
import mokiyoki.enhancedanimals.entity.EnhancedMoobloom;
import mokiyoki.enhancedanimals.entity.EnhancedMooshroom;
import mokiyoki.enhancedanimals.entity.EnhancedRabbit;
import mokiyoki.enhancedanimals.entity.EnhancedLlama;
import mokiyoki.enhancedanimals.entity.EnhancedCow;
import mokiyoki.enhancedanimals.entity.EnhancedPig;
import mokiyoki.enhancedanimals.entity.EnhancedSheep;
import mokiyoki.enhancedanimals.entity.EnhancedTurtle;
import mokiyoki.enhancedanimals.gui.EnhancedAnimalContainer;
import mokiyoki.enhancedanimals.util.EnhancedAnimalInfo;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_AXOLOTL;
import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_AXOLOTL_EGG;
import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_CHICKEN;
import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_COW;
import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_HORSE;
import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_LLAMA;
import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_MOOBLOOM;
import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_MOOSHROOM;
import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_PIG;
import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_RABBIT;
import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_SHEEP;
import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_TURTLE;

//import static mokiyoki.enhancedanimals.capability.woolcolour.WoolColourCapabilityProvider.WOOL_COLOUR_CAP;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

/**
 * Created by moki on 24/08/2018.
 */
@Mod.EventBusSubscriber(modid = Reference.MODID, bus= Mod.EventBusSubscriber.Bus.MOD)
public class EventRegistry {

    public static final MenuType<EggCartonContainer> EGG_CARTON_CONTAINER = IForgeMenuType.create(EggCartonContainer::new);
    public static final MenuType<EnhancedAnimalContainer> ENHANCED_ANIMAL_CONTAINER = IForgeMenuType.create((windowId, inv, data) -> {
        Entity entity = inv.player.level.getEntity(data.readInt());
        EnhancedAnimalInfo animalInfo = new EnhancedAnimalInfo(data.readUtf());
        if(entity instanceof EnhancedAnimalAbstract) {
            return new EnhancedAnimalContainer(windowId, inv, (EnhancedAnimalAbstract) entity, animalInfo);
        } else {
            return null;
        }
    });

    @SubscribeEvent
    public static void onCapabilitiesRegistry(RegisterCapabilitiesEvent event) {
        event.register(IPostCapability.class);
        event.register(IHayCapability.class);
        event.register(IEggCapability.class);
        event.register(INestEggCapability.class);
    }

    @SubscribeEvent
    public static void onEntityAttributeCreationRegistry(EntityAttributeCreationEvent event) {
        event.put(ENHANCED_AXOLOTL.get(), EnhancedAxolotl.prepareAttributes().build());
        event.put(ENHANCED_CHICKEN.get(), EnhancedChicken.prepareAttributes().build());
        event.put(ENHANCED_RABBIT.get(), EnhancedRabbit.prepareAttributes().build());
        event.put(ENHANCED_SHEEP.get(), EnhancedSheep.prepareAttributes().build());
        event.put(ENHANCED_LLAMA.get(), EnhancedLlama.prepareAttributes().build());
        event.put(ENHANCED_COW.get(), EnhancedCow.prepareAttributes().build());
        event.put(ENHANCED_MOOSHROOM.get(), EnhancedMooshroom.prepareAttributes().build());
        event.put(ENHANCED_MOOBLOOM.get(), EnhancedMoobloom.prepareAttributes().build());
        event.put(ENHANCED_PIG.get(), EnhancedPig.prepareAttributes().build());
        event.put(ENHANCED_HORSE.get(), EnhancedHorse.prepareAttributes().build());
        event.put(ENHANCED_TURTLE.get(), EnhancedTurtle.prepareAttributes().build());

    }

//    @SubscribeEvent
//    public static void onTileEntitiesRegistry(final RegistryEvent.Register<BlockEntityType<?>> event) {
//        event.getRegistry().register(ModTileEntities.EGG_CARTON_TILE_ENTITY.get().setRegistryName("egg_carton_tile_entity"));
//    }

    @SubscribeEvent
    public static void onContainerTypeRegistry(final RegisterEvent event) {
        event.register(ForgeRegistries.Keys.MENU_TYPES,
            helper -> {
                helper.register(new ResourceLocation("egg_carton_container_box"), EGG_CARTON_CONTAINER);
                helper.register(new ResourceLocation("enhanced_animal_container"), ENHANCED_ANIMAL_CONTAINER);
            }
        );

    }

//    @SubscribeEvent
//    public static void registerBlockColors(final ColorHandlerEvent.Block event)
//    {
//        final BlockColors blockColors = event.getBlockColors();


//        blockColors.register((p_210239_0_, p_210239_1_) -> {
//            return p_210239_1_ > 0 ? -1 : ((ItemArmorDyeable)p_210239_0_.getItem()).getColor(p_210239_0_);
//        }, Items.LEATHER_HELMET, Items.LEATHER_CHESTPLATE, Items.LEATHER_LEGGINGS, Items.LEATHER_BOOTS);

//        blockColors.register((p_210225_0_, p_210225_1_, p_210225_2_, p_210225_3_) ->
//        {
//            return p_210225_1_ != null && p_210225_2_ != null ? BiomeColors.getGrassColor(p_210225_1_, p_210225_2_) : GrassColors.get(0.5D, 1.0D);
//        }, MubbleBlocks.GREEN_HILL_GRASS_BLOCK);
//    }

//    @SubscribeEvent
//    public static void registerItemColors(final ColorHandlerEvent.Item event)
//    {
//        final ItemColors itemColors = event.getItemColors();
//
//        itemColors.register((itemStack, integer) -> {
//            int colour = itemStack.getCapability(WOOL_COLOUR_CAP).orElse(new WoolColourCapabilityProvider()).getColour();
//            return colour;
//        }, Blocks.WHITE_WOOL);
//    }


    @SubscribeEvent
    public static void onEntitiesRegistry(final SpawnPlacementRegisterEvent event) {

        event.register(ENHANCED_AXOLOTL.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EnhancedAxolotl::checkAxolotlSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(ENHANCED_PIG.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(ENHANCED_SHEEP.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(ENHANCED_COW.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(ENHANCED_LLAMA.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(ENHANCED_CHICKEN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(ENHANCED_RABBIT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EnhancedRabbit::checkRabbitSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(ENHANCED_MOOSHROOM.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EnhancedMooshroom::canMooshroomSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(ENHANCED_TURTLE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EnhancedTurtle::canTurtleSpawn, SpawnPlacementRegisterEvent.Operation.OR);
    }

}
