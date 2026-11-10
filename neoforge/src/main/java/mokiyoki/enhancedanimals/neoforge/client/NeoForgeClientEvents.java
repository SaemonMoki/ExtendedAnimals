package mokiyoki.enhancedanimals.neoforge.client;

import mokiyoki.enhancedanimals.util.Reference;
import mokiyoki.enhancedanimals.util.handlers.ClientEventRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(modid = Reference.MODID, value = Dist.CLIENT)
public class NeoForgeClientEvents {

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        ClientEventRegistry.registerItemRenderers((item, renderer) -> event.registerItem(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return renderer.get();
            }
        }, item));
    }

    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        ClientEventRegistry.registerParticleFactories(new ClientEventRegistry.ParticleProviderRegistrar() {
            @Override
            public <T extends ParticleOptions> void register(ParticleType<T> type, ParticleProvider<T> provider) {
                event.registerSpecial(type, provider);
            }
        });
    }

    @SubscribeEvent
    public static void registerMenuScreens(RegisterMenuScreensEvent event) {
        ClientEventRegistry.registerMenuScreens(new ClientEventRegistry.MenuScreenRegistrar() {
            @Override
            public <M extends AbstractContainerMenu, U extends Screen & MenuAccess<M>> void register(MenuType<? extends M> menuType, MenuScreens.ScreenConstructor<M, U> screenConstructor) {
                event.register(menuType, screenConstructor);
            }
        });
    }

    @SubscribeEvent
    @SuppressWarnings("deprecation") // Same call the mod used before; block models do not set render types.
    public static void initClientSetup(FMLClientSetupEvent event) {
        ClientEventRegistry.registerRenderLayers(ItemBlockRenderTypes::setRenderLayer);
    }

    @SubscribeEvent
    public static void onBlockColouredEvent(RegisterColorHandlersEvent.Block event) {
        ClientEventRegistry.onBlockColouredEvent(event::register);
    }

    @SubscribeEvent
    public static void onItemColouredEvent(RegisterColorHandlersEvent.Item event) {
        ClientEventRegistry.onItemColouredEvent(event::register);
    }

    @SubscribeEvent
    public static void onEntityRenderersRegistry(EntityRenderersEvent.RegisterRenderers event) {
        ClientEventRegistry.onEntityRenderersRegistry(new ClientEventRegistry.EntityRendererRegistrar() {
            @Override
            public <E extends Entity> void registerEntityRenderer(EntityType<? extends E> entityType, EntityRendererProvider<E> provider) {
                event.registerEntityRenderer(entityType, provider);
            }

            @Override
            public <T extends BlockEntity> void registerBlockEntityRenderer(BlockEntityType<? extends T> blockEntityType, BlockEntityRendererProvider<T> provider) {
                event.registerBlockEntityRenderer(blockEntityType, provider);
            }
        });
    }

    @SubscribeEvent
    public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
        ClientEventRegistry.registerLayerDefinition(event::registerLayerDefinition);
    }
}
