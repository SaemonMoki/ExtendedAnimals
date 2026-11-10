package mokiyoki.enhancedanimals.neoforge;

import mokiyoki.enhancedanimals.EnhancedAnimals;
import mokiyoki.enhancedanimals.init.ModEntities;
import mokiyoki.enhancedanimals.init.ModTileEntities;
import mokiyoki.enhancedanimals.network.ModPackets;
import mokiyoki.enhancedanimals.neoforge.platform.NeoForgeNetworkHelper;
import mokiyoki.enhancedanimals.util.Reference;
import mokiyoki.enhancedanimals.util.handlers.EventRegistry;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.neoforged.neoforge.items.wrapper.InvWrapper;
import net.neoforged.neoforge.items.wrapper.SidedInvWrapper;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.minecraft.core.Direction;

import java.util.List;

@Mod(Reference.MODID)
public class EnhancedAnimalsNeoForge {

    public EnhancedAnimalsNeoForge(IEventBus modEventBus, ModContainer modContainer) {
        EnhancedAnimals.init();
        NeoForgeBiomeModifiers.register(modEventBus);

        modEventBus.addListener(this::registerPayloads);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::registerAttributes);
        modEventBus.addListener(this::registerSpawnPlacements);
        modEventBus.addListener(this::registerCapabilities);

        NeoForge.EVENT_BUS.register(new NeoForgeGameEvents());
        NeoForge.EVENT_BUS.addListener((ServerAboutToStartEvent event) -> EnhancedAnimals.onServerAboutToStart(event.getServer()));
    }

    private void registerPayloads(RegisterPayloadHandlersEvent event) {
        ModPackets.register(new NeoForgeNetworkHelper.Registrar(event.registrar(ModPackets.PROTOCOL_VERSION)));
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(EnhancedAnimals::commonSetup);
    }

    private void registerAttributes(EntityAttributeCreationEvent event) {
        EventRegistry.registerAttributes(event::put);
    }

    private void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        EventRegistry.registerSpawnPlacements(new EventRegistry.SpawnPlacementRegistrar() {
            @Override
            public <T extends net.minecraft.world.entity.Mob> void register(EntityType<T> entityType, net.minecraft.world.entity.SpawnPlacementType placementType, net.minecraft.world.level.levelgen.Heightmap.Types heightmap, net.minecraft.world.entity.SpawnPlacements.SpawnPredicate<T> predicate) {
                event.register(entityType, placementType, heightmap, predicate, RegisterSpawnPlacementsEvent.Operation.OR);
            }
        });
    }

    // NeoForge only, Fabric has no item handler lookup for entities.
    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        List<EntityType<? extends mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract>> animals = List.of(
                ModEntities.ENHANCED_CHICKEN.get(), ModEntities.ENHANCED_RABBIT.get(), ModEntities.ENHANCED_SHEEP.get(),
                ModEntities.ENHANCED_LLAMA.get(), ModEntities.ENHANCED_COW.get(), ModEntities.ENHANCED_MOOSHROOM.get(),
                ModEntities.ENHANCED_MOOBLOOM.get(), ModEntities.ENHANCED_PIG.get(), ModEntities.ENHANCED_HORSE.get(),
                ModEntities.ENHANCED_TURTLE.get(), ModEntities.ENHANCED_AXOLOTL.get());
        for (EntityType<? extends mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract> animal : animals) {
            event.registerEntity(Capabilities.ItemHandler.ENTITY, animal,
                    (entity, context) -> entity.isAlive() ? new InvWrapper(entity.getEnhancedInventory()) : null);
        }
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModTileEntities.EGG_CARTON_TILE_ENTITY.get(),
                (carton, side) -> new SidedInvWrapper(carton, Direction.UP));
    }
}
