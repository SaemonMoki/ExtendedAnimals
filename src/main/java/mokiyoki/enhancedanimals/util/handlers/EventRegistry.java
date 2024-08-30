package mokiyoki.enhancedanimals.util.handlers;


import mokiyoki.enhancedanimals.blocks.EggCartonContainer;
import mokiyoki.enhancedanimals.capability.egg.IEggCapability;
import mokiyoki.enhancedanimals.capability.hay.IHayCapability;
import mokiyoki.enhancedanimals.capability.post.IPostCapability;
import mokiyoki.enhancedanimals.capability.nestegg.INestEggCapability;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import mokiyoki.enhancedanimals.entity.EnhancedAxolotl;
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
import mokiyoki.enhancedanimals.init.datagen.GASpriteSourceProvider;
import mokiyoki.enhancedanimals.util.EnhancedAnimalInfo;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.core.dispenser.ShearsDispenseItemBehavior;
import net.minecraft.core.BlockSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.IForgeShearable;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static mokiyoki.enhancedanimals.GeneticAnimals.MODID;
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
@Mod.EventBusSubscriber(modid = MODID, bus= Mod.EventBusSubscriber.Bus.MOD)
public class EventRegistry {

    public static final MenuType<EggCartonContainer> EGG_CARTON_CONTAINER = IForgeMenuType.create(EggCartonContainer::new);
    public static final MenuType<EnhancedAnimalContainer> ENHANCED_ANIMAL_CONTAINER = IForgeMenuType.create((windowId, inv, data) -> {
        Entity entity = inv.player.level().getEntity(data.readInt());
        EnhancedAnimalInfo animalInfo = new EnhancedAnimalInfo(data.readUtf());
        if(entity instanceof EnhancedAnimalAbstract) {
            return new EnhancedAnimalContainer(windowId, inv, (EnhancedAnimalAbstract) entity, animalInfo);
        } else {
            return null;
        }
    });

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        boolean includeClient = event.includeClient();
        boolean includeServer = event.includeServer();
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        generator.addProvider(includeClient, new GASpriteSourceProvider(packOutput, fileHelper));
    }

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

    @SubscribeEvent
    public static void onContainerTypeRegistry(final RegisterEvent event) {
        event.register(ForgeRegistries.Keys.MENU_TYPES,
            helper -> {
                helper.register(new ResourceLocation("egg_carton_container_box"), EGG_CARTON_CONTAINER);
                helper.register(new ResourceLocation("enhanced_animal_container"), ENHANCED_ANIMAL_CONTAINER);
            }
        );
    }


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
