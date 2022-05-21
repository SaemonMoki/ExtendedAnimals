package mokiyoki.enhancedanimals.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mokiyoki.enhancedanimals.items.EnhancedAxolotlBucket;
import mokiyoki.enhancedanimals.model.EnhancedAxolotlBucketModel;
import mokiyoki.enhancedanimals.renderer.texture.DrawnTexture;
import mokiyoki.enhancedanimals.renderer.util.LayeredTextureCacher;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.system.CallbackI;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

import static mokiyoki.enhancedanimals.renderer.ModelLayers.AXOLOTL_BUCKET_LAYER;

public class RenderEnhancedAxolotlBucket extends BlockEntityWithoutLevelRenderer {
    public static final BlockEntityWithoutLevelRenderer AXOLOTL_BUCKET_RENDERER = new RenderEnhancedAxolotlBucket(() -> Minecraft.getInstance().getBlockEntityRenderDispatcher(), () -> Minecraft.getInstance().getEntityModels());
    private static final LayeredTextureCacher textureCache = new LayeredTextureCacher();
    private static final ResourceLocation ERROR_TEXTURE_LOCATION = new ResourceLocation("minecraft:textures/item/axolotl_bucket.png");
    private final EnhancedAxolotlBucketModel axolotlBucketModel;

    public RenderEnhancedAxolotlBucket(Supplier<BlockEntityRenderDispatcher> blockEntityRenderDispatcherSupplier, Supplier<EntityModelSet> entityModelSetSupplier) {
        super(blockEntityRenderDispatcherSupplier.get(), entityModelSetSupplier.get());
        this.axolotlBucketModel = new EnhancedAxolotlBucketModel(entityModelSetSupplier.get().bakeLayer(AXOLOTL_BUCKET_LAYER));
    }

    @Override
    public void renderByItem(@Nonnull ItemStack stack, @Nonnull ItemTransforms.TransformType transformType, @Nonnull PoseStack matrix, @Nonnull MultiBufferSource renderer, int light, int overlayLight) {
        matrix.pushPose();
        matrix.scale(1, -1, 1);
        matrix.translate(0, -1, 0);
        VertexConsumer builder = ItemRenderer.getFoilBufferDirect(renderer, axolotlBucketModel.renderType(getTexture(stack)), false, stack.hasFoil());
        axolotlBucketModel.renderToBuffer(matrix, builder, 16777215, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        matrix.popPose();
    }

    @OnlyIn(Dist.CLIENT)
    public ResourceLocation getTexture(ItemStack stack) {
        if (stack.getItem() instanceof EnhancedAxolotlBucket) {
            String s = EnhancedAxolotlBucket.getTexture(stack);

            ResourceLocation resourcelocation = textureCache.getFromCache(s);

            if (resourcelocation == null) {
                int[] image = EnhancedAxolotlBucket.getImage(stack);

                if (image.length == 1) {
                    return ERROR_TEXTURE_LOCATION;
                }

                try {
                    resourcelocation = new ResourceLocation(s);
                    DrawnTexture texture = new DrawnTexture(image, "minecraft:textures/item/axolotl_bucket.png");
                    Minecraft.getInstance().getTextureManager().register(resourcelocation, texture);
                    textureCache.putInCache(s, resourcelocation);
                    resourcelocation = textureCache.getFromCache(s);
                } catch (IllegalStateException e) {
                    return ERROR_TEXTURE_LOCATION;
                }
            }

            return resourcelocation;
        }
            return ERROR_TEXTURE_LOCATION;
    }
}
