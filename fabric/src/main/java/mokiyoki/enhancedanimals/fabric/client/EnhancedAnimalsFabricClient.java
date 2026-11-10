package mokiyoki.enhancedanimals.fabric.client;

import mokiyoki.enhancedanimals.fabric.EnhancedAnimalsFabricAccess;
import mokiyoki.enhancedanimals.util.handlers.ClientEventRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class EnhancedAnimalsFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        FabricClientNetworking.registerReceivers(EnhancedAnimalsFabricAccess.packets());

        ClientEventRegistry.onEntityRenderersRegistry(new ClientEventRegistry.EntityRendererRegistrar() {
            @Override
            public <E extends Entity> void registerEntityRenderer(EntityType<? extends E> entityType, EntityRendererProvider<E> provider) {
                EntityRendererRegistry.register(entityType, provider);
            }

            @Override
            public <T extends BlockEntity> void registerBlockEntityRenderer(BlockEntityType<? extends T> blockEntityType, BlockEntityRendererProvider<T> provider) {
                BlockEntityRenderers.register(blockEntityType, provider);
            }
        });
        ClientEventRegistry.registerLayerDefinition((layer, definition) -> EntityModelLayerRegistry.registerModelLayer(layer, definition::get));
        ClientEventRegistry.registerParticleFactories(new ClientEventRegistry.ParticleProviderRegistrar() {
            @Override
            public <T extends ParticleOptions> void register(ParticleType<T> type, ParticleProvider<T> provider) {
                ParticleFactoryRegistry.getInstance().register(type, provider);
            }
        });
        ClientEventRegistry.registerMenuScreens(new ClientEventRegistry.MenuScreenRegistrar() {
            @Override
            public <M extends AbstractContainerMenu, U extends Screen & MenuAccess<M>> void register(MenuType<? extends M> menuType, MenuScreens.ScreenConstructor<M, U> screenConstructor) {
                MenuScreens.register(menuType, screenConstructor);
            }
        });
        ClientEventRegistry.registerRenderLayers(BlockRenderLayerMap.INSTANCE::putBlock);
        ClientEventRegistry.onBlockColouredEvent(ColorProviderRegistry.BLOCK::register);
        ClientEventRegistry.onItemColouredEvent(ColorProviderRegistry.ITEM::register);
        ClientEventRegistry.registerItemRenderers((item, renderer) -> BuiltinItemRendererRegistry.INSTANCE.register(item,
                (stack, displayContext, poseStack, bufferSource, light, overlay) ->
                        renderer.get().renderByItem(stack, displayContext, poseStack, bufferSource, light, overlay)));
    }
}
