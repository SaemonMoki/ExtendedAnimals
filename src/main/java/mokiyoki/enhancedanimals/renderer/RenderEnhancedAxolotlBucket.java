package mokiyoki.enhancedanimals.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import mokiyoki.enhancedanimals.items.EnhancedAxolotlBucket;
import mokiyoki.enhancedanimals.model.EnhancedAxolotlBucketModel;
import mokiyoki.enhancedanimals.renderer.texture.DrawnTexture;
import mokiyoki.enhancedanimals.renderer.util.LayeredTextureCacher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

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
    public void renderByItem(@Nonnull ItemStack stack, @Nonnull ItemTransforms.TransformType transformType, @Nonnull PoseStack matrix, @Nonnull MultiBufferSource multiBufferSource, int light, int overlayLight) {
        matrix.pushPose();

        switch (transformType) {
            case GUI, NONE, HEAD, FIXED -> {
                matrix.scale(1F, -1F, 1F);
                matrix.translate(1F, -1F, 0F);
            }
            case FIRST_PERSON_LEFT_HAND, FIRST_PERSON_RIGHT_HAND -> {
                matrix.scale(0.75F, -0.75F, 0.75F);
                matrix.translate(1.5F, -1.5F, 0.5F);
                matrix.mulPose(Vector3f.XP.rotationDegrees(1F));
                matrix.mulPose(Vector3f.YP.rotationDegrees(10F));
            }
            case THIRD_PERSON_LEFT_HAND, THIRD_PERSON_RIGHT_HAND -> {
                matrix.scale(0.6F, -0.6F, 0.6F);
                matrix.translate(1.4F, -1.5F, 0.5F);
            }
            case GROUND -> {
                matrix.scale(0.5F, -0.5F, 0.5F);
                matrix.translate(1.5F, -1.5F, 0.5F);
            }
        }
//        matrix.scale(0.5F, -0.5F, 0.5F);
//        matrix.translate(1.5F, -1.5F, 0);
        VertexConsumer builder = ItemRenderer.getFoilBufferDirect(multiBufferSource, axolotlBucketModel.renderType(getTexture(stack)), false, stack.hasFoil());
        axolotlBucketModel.renderToBuffer(matrix, builder, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
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
                    DrawnTexture texture = new DrawnTexture(image, "eanimod:textures/items/axolotl_bucket_base.png");
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
