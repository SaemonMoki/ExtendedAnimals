package mokiyoki.enhancedanimals.fabric;

import mokiyoki.enhancedanimals.EnhancedAnimals;
import mokiyoki.enhancedanimals.fabric.platform.FabricNetworkHelper;
import mokiyoki.enhancedanimals.network.ModPackets;
import mokiyoki.enhancedanimals.util.Reference;
import mokiyoki.enhancedanimals.util.handlers.EventRegistry;
import mokiyoki.enhancedanimals.util.handlers.EventSubscriber;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.networking.v1.EntityTrackingEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class EnhancedAnimalsFabric implements ModInitializer {

    static final FabricNetworkHelper.Registrar PACKETS = new FabricNetworkHelper.Registrar();

    @Override
    public void onInitialize() {
        EnhancedAnimals.init();
        ModPackets.register(PACKETS);

        EventRegistry.registerAttributes(FabricDefaultAttributeRegistry::register);
        EventRegistry.registerSpawnPlacements(new EventRegistry.SpawnPlacementRegistrar() {
            @Override
            public <T extends Mob> void register(net.minecraft.world.entity.EntityType<T> entityType, net.minecraft.world.entity.SpawnPlacementType placementType, net.minecraft.world.level.levelgen.Heightmap.Types heightmap, SpawnPlacements.SpawnPredicate<T> predicate) {
                SpawnPlacements.register(entityType, placementType, heightmap, predicate);
            }
        });
        registerBiomeSpawns();

        EnhancedAnimals.commonSetup();

        registerEvents();
    }

    private static void registerBiomeSpawns() {
        // Runs after other mods have added their spawns.
        BiomeModifications.create(ResourceLocation.fromNamespaceAndPath(Reference.MODID, "eanimod_spawn"))
                .add(ModificationPhase.REPLACEMENTS, BiomeSelectors.all(), (selection, modification) -> {
                    FabricBiomeSpawnEditor editor = new FabricBiomeSpawnEditor(selection, modification.getSpawnSettings());
                    mokiyoki.enhancedanimals.util.handlers.SpawnRegistry.addSpawns(editor);
                    mokiyoki.enhancedanimals.util.handlers.SpawnRegistry.removeSpawns(editor);
                });
    }

    private static void registerEvents() {
        EventSubscriber handler = EventSubscriber.INSTANCE;

        // Entity join, entity tick, baby spawn and finalize spawn come from the mixins.
        ServerEntityEvents.ENTITY_UNLOAD.register(handler::onEntityLeaveLevel);
        EntityTrackingEvents.START_TRACKING.register(handler::onStartTracking);
        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> !handler.shouldCancelIncomingDamage(entity, source));
        UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> {
            handler.onRightClickBlock(player, level, hitResult.getBlockPos(), player.getItemInHand(hand));
            return InteractionResult.PASS;
        });
        ServerLifecycleEvents.SERVER_STARTING.register(EnhancedAnimals::onServerAboutToStart);

        handler.createServerReloadListeners().forEach((id, listener) ->
                ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new IdentifiedListener(id, listener)));
    }

    private record IdentifiedListener(ResourceLocation id, PreparableReloadListener listener) implements IdentifiableResourceReloadListener {
        @Override
        public ResourceLocation getFabricId() {
            return this.id;
        }

        @Override
        public CompletableFuture<Void> reload(PreparationBarrier barrier, ResourceManager manager, ProfilerFiller preparationsProfiler, ProfilerFiller reloadProfiler, Executor backgroundExecutor, Executor gameExecutor) {
            return this.listener.reload(barrier, manager, preparationsProfiler, reloadProfiler, backgroundExecutor, gameExecutor);
        }

        @Override
        public String getName() {
            return this.listener.getName();
        }
    }
}
