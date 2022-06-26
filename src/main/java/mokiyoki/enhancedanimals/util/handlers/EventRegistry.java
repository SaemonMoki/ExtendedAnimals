package mokiyoki.enhancedanimals.util.handlers;


import mokiyoki.enhancedanimals.EnhancedAnimals;
import mokiyoki.enhancedanimals.blocks.EggCartonContainer;
import mokiyoki.enhancedanimals.capability.egg.EggCapabilityProvider;
//import mokiyoki.enhancedanimals.capability.woolcolour.WoolColourCapabilityProvider;
import mokiyoki.enhancedanimals.capability.egg.IEggCapability;
import mokiyoki.enhancedanimals.capability.hay.IHayCapability;
import mokiyoki.enhancedanimals.capability.post.IPostCapability;
import mokiyoki.enhancedanimals.capability.nestegg.INestEggCapability;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
//import mokiyoki.enhancedanimals.entity.EnhancedBee;
//import mokiyoki.enhancedanimals.entity.EnhancedCat;
import mokiyoki.enhancedanimals.entity.EnhancedAxolotl;
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.entity.EnhancedEntityEgg;
import mokiyoki.enhancedanimals.entity.EnhancedEntityLlamaSpit;
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
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.init.ModTileEntities;
//import mokiyoki.enhancedanimals.renderer.EggCartonTileEntityRenderer;
//import mokiyoki.enhancedanimals.renderer.EnhancedLlamaSpitRenderer;
import mokiyoki.enhancedanimals.renderer.RenderEnhancedChicken;
//import mokiyoki.enhancedanimals.renderer.RenderEnhancedCow;
//import mokiyoki.enhancedanimals.renderer.RenderEnhancedHorse;
//import mokiyoki.enhancedanimals.renderer.RenderEnhancedLlama;
//import mokiyoki.enhancedanimals.renderer.RenderEnhancedMoobloom;
//import mokiyoki.enhancedanimals.renderer.RenderEnhancedMooshroom;
//import mokiyoki.enhancedanimals.renderer.RenderEnhancedPig;
//import mokiyoki.enhancedanimals.renderer.RenderEnhancedRabbit;
//import mokiyoki.enhancedanimals.renderer.RenderEnhancedSheep;
//import mokiyoki.enhancedanimals.renderer.RenderEnhancedTurtle;
import mokiyoki.enhancedanimals.renderer.RenderEnhancedAxolotl;
import mokiyoki.enhancedanimals.util.EnhancedAnimalInfo;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.core.dispenser.ShearsDispenseItemBehavior;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.IForgeShearable;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static mokiyoki.enhancedanimals.init.ModBlocks.EGG_CARTON;
import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_AXOLOTL;
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

//    @SubscribeEvent
//    public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
//        final Item[] itemEggs = {ModItems.EGG_WHITE.get(), ModItems.EGG_CREAMLIGHT.get(), ModItems.EGG_CREAM.get(), ModItems.EGG_CREAMDARK.get(), ModItems.EGG_CREAMDARKEST.get(), ModItems.EGG_CARMELDARK.get(), ModItems.EGG_GARNET.get(), ModItems.EGG_PINKLIGHT.get(), ModItems.EGG_PINK.get(), ModItems.EGG_PINKDARK.get(), ModItems.EGG_PINKDARKEST.get(), ModItems.EGG_CHERRYDARK.get(), ModItems.EGG_PLUM.get(), ModItems.EGG_BROWNLIGHT.get(), ModItems.EGG_BROWN.get(), ModItems.EGG_BROWNDARK.get(), ModItems.EGG_CHOCOLATE.get(), ModItems.EGG_CHOCOLATEDARK.get(), ModItems.EGG_CHOCOLATECOSMOS.get(),
//                 ModItems.EGG_BLUE.get(), ModItems.EGG_GREENLIGHT.get(), ModItems.EGG_GREENYELLOW.get(), ModItems.EGG_OLIVELIGHT.get(), ModItems.EGG_OLIVE.get(), ModItems.EGG_OLIVEDARK.get(), ModItems.EGG_ARMY.get(), ModItems.EGG_MINT.get(), ModItems.EGG_GREEN.get(), ModItems.EGG_GREENDARK.get(), ModItems.EGG_PINE.get(), ModItems.EGG_PINEDARK.get(), ModItems.EGG_PINEBLACK.get(), ModItems.EGG_BLUEGREY.get(), ModItems.EGG_GREY.get(), ModItems.EGG_GREYGREEN.get(), ModItems.EGG_AVOCADO.get(), ModItems.EGG_AVOCADODARK.get(), ModItems.EGG_FELDGRAU.get(),
//                 ModItems.EGG_POWDERBLUE.get(), ModItems.EGG_TEA.get(), ModItems.EGG_MATCHA.get(), ModItems.EGG_MATCHADARK.get(), ModItems.EGG_MOSS.get(), ModItems.EGG_MOSSDARK.get(), ModItems.EGG_GREENUMBER.get(), ModItems.EGG_CELADON.get(), ModItems.EGG_FERN.get(), ModItems.EGG_ASPARAGUS.get(), ModItems.EGG_HUNTER.get(), ModItems.EGG_HUNTERDARK.get(), ModItems.EGG_TREEDARK.get(), ModItems.EGG_GREYBLUE.get(), ModItems.EGG_GREYNEUTRAL.get(), ModItems.EGG_LAUREL.get(), ModItems.EGG_RESEDA.get(), ModItems.EGG_GREENPEWTER.get(), ModItems.EGG_GREYDARK.get(),
//                 ModItems.EGG_PALEBLUE.get(), ModItems.EGG_HONEYDEW.get(), ModItems.EGG_EARTH.get(), ModItems.EGG_KHAKI.get(), ModItems.EGG_GRULLO.get(), ModItems.EGG_KHAKIDARK.get(), ModItems.EGG_CAROB.get(), ModItems.EGG_JADE.get(), ModItems.EGG_PISTACHIO.get(), ModItems.EGG_SAGE.get(), ModItems.EGG_ROSEMARY.get(), ModItems.EGG_GREENBROWN.get(), ModItems.EGG_UMBER.get(), ModItems.EGG_COOLGREY.get(), ModItems.EGG_PINKGREY.get(), ModItems.EGG_WARMGREY.get(), ModItems.EGG_ARTICHOKE.get(), ModItems.EGG_MYRTLEGREY.get(), ModItems.EGG_RIFLE.get(),
//                 ModItems.EGG_CREAM_SPOT.get(), ModItems.EGG_CREAMDARK_SPOT.get(), ModItems.EGG_CARMEL_SPOT.get(), ModItems.EGG_CARMELDARK_SPOT.get(), ModItems.EGG_GARNET_SPOT.get(), ModItems.EGG_PINK_SPOT.get(), ModItems.EGG_PINKDARK_SPOT.get(), ModItems.EGG_CHERRY_SPOT.get(), ModItems.EGG_CHERRYDARK_SPOT.get(), ModItems.EGG_PLUM_SPOT.get(), ModItems.EGG_BROWNLIGHT_SPOT.get(), ModItems.EGG_BROWN_SPOT.get(), ModItems.EGG_BROWNDARK_SPOT.get(), ModItems.EGG_CHOCOLATE_SPOT.get(), ModItems.EGG_CHOCOLATEDARK_SPOT.get(),
//                 ModItems.EGG_GREENYELLOW_SPOT.get(), ModItems.EGG_OLIVELIGHT_SPOT.get(), ModItems.EGG_OLIVE_SPOT.get(), ModItems.EGG_OLIVEDARK_SPOT.get(), ModItems.EGG_ARMY_SPOT.get(), ModItems.EGG_MINT_SPOT.get(), ModItems.EGG_GREEN_SPOT.get(), ModItems.EGG_GREENDARK_SPOT.get(), ModItems.EGG_PINE_SPOT.get(), ModItems.EGG_PINEDARK_SPOT.get(), ModItems.EGG_PINEBLACK_SPOT.get(), ModItems.EGG_GREY_SPOT.get(), ModItems.EGG_GREYGREEN_SPOT.get(), ModItems.EGG_AVOCADO_SPOT.get(), ModItems.EGG_AVOCADODARK_SPOT.get(), ModItems.EGG_FELDGRAU_SPOT.get(),
//                 ModItems.Egg_Matcha_Spot.get(), ModItems.Egg_MatchaDark_Spot.get(), ModItems.EGG_MOSS_SPOT.get(), ModItems.EGG_MOSSDARK_SPOT.get(), ModItems.EGG_GREENUMBER_SPOT.get(), ModItems.EGG_CELADON_SPOT.get(), ModItems.EGG_FERN_SPOT.get(), ModItems.EGG_ASPARAGUS_SPOT.get(), ModItems.EGG_HUNTER_SPOT.get(), ModItems.EGG_HUNTERDARK_SPOT.get(), ModItems.EGG_TREEDARK_SPOT.get(), ModItems.EGG_GREYNEUTRAL_SPOT.get(), ModItems.EGG_LAUREL_SPOT.get(), ModItems.EGG_RESEDA_SPOT.get(), ModItems.EGG_GREENPEWTER_SPOT.get(), ModItems.EGG_GREYDARK_SPOT.get(),
//                 ModItems.EGG_EARTH_SPOT.get(), ModItems.EGG_KHAKI_SPOT.get(), ModItems.EGG_GRULLO_SPOT.get(), ModItems.EGG_KHAKIDARK_SPOT.get(), ModItems.EGG_CAROB_SPOT.get(), ModItems.EGG_JADE_SPOT.get(), ModItems.EGG_PISTACHIO_SPOT.get(), ModItems.EGG_SAGE_SPOT.get(), ModItems.EGG_ROSEMARY_SPOT.get(), ModItems.EGG_GREENBROWN_SPOT.get(), ModItems.EGG_UMBER_SPOT.get(), ModItems.EGG_PINKGREY_SPOT.get(), ModItems.EGG_WARMGREY_SPOT.get(), ModItems.EGG_ARTICHOKE_SPOT.get(), ModItems.EGG_MYRTLEGREY_SPOT.get(), ModItems.EGG_RIFLE_SPOT.get(),
//                 ModItems.EGG_CREAM_SPECKLE.get(), ModItems.EGG_CREAMDARK_SPECKLE.get(), ModItems.EGG_CARMEL_SPECKLE.get(), ModItems.EGG_CARMELDARK_SPECKLE.get(), ModItems.EGG_GARNET_SPECKLE.get(), ModItems.EGG_PINK_SPECKLE.get(), ModItems.EGG_PINKDARK_SPECKLE.get(), ModItems.EGG_CHERRY_SPECKLE.get(), ModItems.EGG_CHERRYDARK_SPECKLE.get(), ModItems.EGG_PLUM_SPECKLE.get(), ModItems.EGG_BROWNLIGHT_SPECKLE.get(), ModItems.EGG_BROWN_SPECKLE.get(), ModItems.EGG_BROWNDARK_SPECKLE.get(), ModItems.EGG_CHOCOLATE_SPECKLE.get(), ModItems.EGG_CHOCOLATEDARK_SPECKLE.get(),
//                 ModItems.EGG_GREENYELLOW_SPECKLE.get(), ModItems.EGG_OLIVELIGHT_SPECKLE.get(), ModItems.EGG_OLIVE_SPECKLE.get(), ModItems.EGG_OLIVEDARK_SPECKLE.get(), ModItems.EGG_ARMY_SPECKLE.get(), ModItems.EGG_MINT_SPECKLE.get(), ModItems.EGG_GREEN_SPECKLE.get(), ModItems.EGG_GREENDARK_SPECKLE.get(), ModItems.EGG_PINE_SPECKLE.get(), ModItems.EGG_PINEDARK_SPECKLE.get(), ModItems.EGG_PINEBLACK_SPECKLE.get(), ModItems.EGG_GREY_SPECKLE.get(), ModItems.EGG_GREYGREEN_SPECKLE.get(), ModItems.EGG_AVOCADO_SPECKLE.get(), ModItems.EGG_AVOCADODARK_SPECKLE.get(), ModItems.EGG_FELDGRAU_SPECKLE.get(),
//                 ModItems.EGG_MATCHA_SPECKLE.get(), ModItems.EGG_MATCHADARK_SPECKLE.get(), ModItems.EGG_MOSS_SPECKLE.get(), ModItems.EGG_MOSSDARK_SPECKLE.get(), ModItems.EGG_GREENUMBER_SPECKLE.get(), ModItems.EGG_CELADON_SPECKLE.get(), ModItems.EGG_FERN_SPECKLE.get(), ModItems.EGG_ASPARAGUS_SPECKLE.get(), ModItems.EGG_HUNTER_SPECKLE.get(), ModItems.EGG_HUNTERDARK_SPECKLE.get(), ModItems.EGG_TREEDARK_SPECKLE.get(), ModItems.EGG_GREYNEUTRAL_SPECKLE.get(), ModItems.EGG_LAUREL_SPECKLE.get(), ModItems.EGG_RESEDA_SPECKLE.get(), ModItems.EGG_GREENPEWTER_SPECKLE.get(), ModItems.EGG_GREYDARK_SPECKLE.get(),
//                 ModItems.EGG_EARTH_SPECKLE.get(), ModItems.EGG_KHAKI_SPECKLE.get(), ModItems.EGG_GRULLO_SPECKLE.get(), ModItems.EGG_KHAKIDARK_SPECKLE.get(), ModItems.EGG_CAROB_SPECKLE.get(), ModItems.EGG_JADE_SPECKLE.get(), ModItems.EGG_PISTACHIO_SPECKLE.get(), ModItems.EGG_SAGE_SPECKLE.get(), ModItems.EGG_ROSEMARY_SPECKLE.get(), ModItems.EGG_GREENBROWN_SPECKLE.get(), ModItems.EGG_UMBER_SPECKLE.get(), ModItems.EGG_PINKGREY_SPECKLE.get(), ModItems.EGG_WARMGREY_SPECKLE.get(), ModItems.EGG_ARTICHOKE_SPECKLE.get(), ModItems.EGG_MYRTLEGREY_SPECKLE.get(), ModItems.EGG_RIFLE_SPECKLE.get(),
//                 ModItems.EGG_CREAM_SPATTER.get(), ModItems.EGG_CREAMDARK_SPATTER.get(), ModItems.EGG_CARMEL_SPATTER.get(), ModItems.EGG_CARMELDARK_SPATTER.get(), ModItems.EGG_GARNET_SPATTER.get(), ModItems.EGG_PINK_SPATTER.get(), ModItems.EGG_PINKDARK_SPATTER.get(), ModItems.EGG_CHERRY_SPATTER.get(), ModItems.EGG_CHERRYDARK_SPATTER.get(), ModItems.EGG_PLUM_SPATTER.get(), ModItems.EGG_BROWNLIGHT_SPATTER.get(), ModItems.EGG_BROWN_SPATTER.get(), ModItems.EGG_BROWNDARK_SPATTER.get(), ModItems.EGG_CHOCOLATE_SPATTER.get(), ModItems.EGG_CHOCOLATEDARK_SPATTER.get(),
//                 ModItems.EGG_GREENYELLOW_SPATTER.get(), ModItems.EGG_OLIVELIGHT_SPATTER.get(), ModItems.EGG_OLIVE_SPATTER.get(), ModItems.EGG_OLIVEDARK_SPATTER.get(), ModItems.EGG_ARMY_SPATTER.get(), ModItems.EGG_MINT_SPATTER.get(), ModItems.EGG_GREEN_SPATTER.get(), ModItems.EGG_GREENDARK_SPATTER.get(), ModItems.EGG_PINE_SPATTER.get(), ModItems.EGG_PINEDARK_SPATTER.get(), ModItems.EGG_PINEBLACK_SPATTER.get(), ModItems.EGG_GREY_SPATTER.get(), ModItems.EGG_GREYGREEN_SPATTER.get(), ModItems.EGG_AVOCADO_SPATTER.get(), ModItems.EGG_AVOCADODARK_SPATTER.get(), ModItems.EGG_FELDGRAU_SPATTER.get(),
//                 ModItems.EGG_MATCHA_SPATTER.get(), ModItems.EGG_MATCHADARK_SPATTER.get(), ModItems.EGG_MOSS_SPATTER.get(), ModItems.EGG_MOSSDARK_SPATTER.get(), ModItems.EGG_GREENUMBER_SPATTER.get(), ModItems.EGG_CELADON_SPATTER.get(), ModItems.EGG_FERN_SPATTER.get(), ModItems.EGG_ASPARAGUS_SPATTER.get(), ModItems.EGG_HUNTER_SPATTER.get(), ModItems.EGG_HUNTERDARK_SPATTER.get(), ModItems.EGG_TREEDARK_SPATTER.get(), ModItems.EGG_GREYNEUTRAL_SPATTER.get(), ModItems.EGG_LAUREL_SPATTER.get(), ModItems.EGG_RESEDA_SPATTER.get(), ModItems.EGG_GREENPEWTER_SPATTER.get(), ModItems.EGG_GREYDARK_SPATTER.get(),
//                 ModItems.EGG_EARTH_SPATTER.get(), ModItems.EGG_KHAKI_SPATTER.get(), ModItems.EGG_GRULLO_SPATTER.get(), ModItems.EGG_KHAKIDARK_SPATTER.get(), ModItems.EGG_CAROB_SPATTER.get(), ModItems.EGG_JADE_SPATTER.get(), ModItems.EGG_PISTACHIO_SPATTER.get(), ModItems.EGG_SAGE_SPATTER.get(), ModItems.EGG_ROSEMARY_SPATTER.get(), ModItems.EGG_GREENBROWN_SPATTER.get(), ModItems.EGG_UMBER_SPATTER.get(), ModItems.EGG_PINKGREY_SPATTER.get(), ModItems.EGG_WARMGREY_SPATTER.get(), ModItems.EGG_ARTICHOKE_SPATTER.get(), ModItems.EGG_MYRTLEGREY_SPATTER.get(), ModItems.EGG_RIFLE_SPATTER.get()};
//
//
//        for (Item egg : itemEggs) {
//            DispenserBlock.registerBehavior(egg,  new AbstractProjectileDispenseBehavior() {
//                /**
//                 * Return the projectile entity spawned by this dispense behavior.
//                 */
//                protected Projectile getProjectile(Level worldIn, Position position, ItemStack stackIn) {
//                    EnhancedEntityEgg eggItem = new EnhancedEntityEgg(worldIn, position.x(), position.y(), position.z(), egg);
//                    eggItem.setEggData(stackIn.getCapability(EggCapabilityProvider.EGG_CAP, null).orElse(null).getEggHolder(stackIn));
//                    return eggItem;
//                }
//            });
//        }
//
//        DispenserBlock.registerBehavior(Items.SHEARS.asItem(), new GeneticShearDispenseBehavior());
//
//        //TODO dispensers should be able to turn hay to unbound hay if they contain a sharp tool and are facing a hay block
//    }

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
    public static void onContainerTypeRegistry(final RegistryEvent.Register<MenuType<?>> event) {
        event.getRegistry().register(EGG_CARTON_CONTAINER.setRegistryName("egg_carton_container_box"));
        event.getRegistry().register(ENHANCED_ANIMAL_CONTAINER.setRegistryName("enhanced_animal_container"));
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
    public static void onEntitiesRegistry(RegistryEvent.Register<EntityType<?>> event) {
        SpawnPlacements.register(ENHANCED_AXOLOTL.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(ENHANCED_PIG.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(ENHANCED_SHEEP.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(ENHANCED_COW.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(ENHANCED_LLAMA.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(ENHANCED_CHICKEN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(ENHANCED_RABBIT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(ENHANCED_MOOSHROOM.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EnhancedMooshroom::canMooshroomSpawn);
        SpawnPlacements.register(ENHANCED_TURTLE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EnhancedTurtle::canTurtleSpawn);
    }

    private static class GeneticShearDispenseBehavior extends ShearsDispenseItemBehavior {

        @Override
        protected ItemStack execute(BlockSource source, ItemStack stack) {
            Level world = source.getLevel();
            if (!world.isClientSide()) {
                BlockPos blockpos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
                this.setSuccess(shearGeneticAnimals((ServerLevel)world, blockpos));
                if (this.isSuccess() && stack.hurt(1, world.getRandom(), (ServerPlayer)null)) {
                    stack.setCount(0);
                }
                if (this.isSuccess()) {
                    return stack;
                }
            }

            return super.execute(source, stack);
        }

        private boolean shearGeneticAnimals(ServerLevel world, BlockPos pos) {
            for(LivingEntity livingentity : world.getEntitiesOfClass(LivingEntity.class, new AABB(pos), EntitySelector.NO_SPECTATORS)) {
                if (livingentity instanceof EnhancedAnimalAbstract && livingentity instanceof IForgeShearable) {
                    IForgeShearable ishearable = (IForgeShearable)livingentity;
                    if (ishearable.isShearable(ItemStack.EMPTY, world, pos)) {
                        List<ItemStack> wool = ishearable.onSheared(null, ItemStack.EMPTY, world, pos, 0);
                        wool.forEach(d -> {
                            net.minecraft.world.entity.item.ItemEntity ent = livingentity.spawnAtLocation(d, 1.0F);
                            ent.setDeltaMovement(ent.getDeltaMovement().add((double)(ThreadLocalRandom.current().nextFloat() * 0.1F), (double)(ThreadLocalRandom.current().nextFloat() * 0.05F), (double)((ThreadLocalRandom.current().nextFloat()) * 0.1F)));
                        });
                        return true;
                    }
                }
            }

            return false;
        }

    }

}
