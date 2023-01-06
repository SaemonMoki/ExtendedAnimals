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

        RenderType renderType = RenderType.cutoutMipped();
        ResourceLocation texture = getTexture(stack);

        switch (transformType) {
            case NONE, HEAD, FIXED -> {
                matrix.scale(1F, 1F, 1F);
                matrix.translate(1F, 0F, 0F);
                renderType = axolotlBucketModel.renderType(texture);
            }
            case GUI -> {
                matrix.scale(1F, 1F, -1F);
                matrix.translate(1F, 0F, 0F);
                renderType = RenderType.text(texture);
            }
            case FIRST_PERSON_RIGHT_HAND -> {
                matrix.scale(0.68F, 0.68F, 0.68F);
                matrix.translate(1.32F, 0.8-0.01F, 1.53F);
                matrix.mulPose(Vector3f.YP.rotationDegrees(-90));
                matrix.mulPose(Vector3f.ZP.rotationDegrees(25F));
                renderType = axolotlBucketModel.renderType(texture);
            }
            case FIRST_PERSON_LEFT_HAND -> {
                matrix.scale(0.68F, 0.68F, 0.68F);
                matrix.translate(1.31-0.1F, 0.8-0.01F, 1.53F);
                matrix.mulPose(Vector3f.YP.rotationDegrees(-90));
                matrix.mulPose(Vector3f.ZP.rotationDegrees(25F));
                renderType = axolotlBucketModel.renderType(texture);
            }
            case THIRD_PERSON_LEFT_HAND, THIRD_PERSON_RIGHT_HAND -> {
                matrix.scale(0.55F, 0.55F, 0.55F);
                matrix.translate(1.41F, 0.749F, 0.525F);
                renderType = axolotlBucketModel.renderType(texture);
            }
            case GROUND -> {
                matrix.scale(0.5F, 0.5F, 0.5F);
                matrix.translate(1.5F, 0.5F, 0.5F);
                renderType = axolotlBucketModel.renderType(texture);
            }
        }

        VertexConsumer builder = ItemRenderer.getFoilBufferDirect(multiBufferSource, renderType, false, stack.hasFoil());

        axolotlBucketModel.longGills = !EnhancedAxolotlBucket.getGillType(stack).equals(EnhancedAxolotlBucket.GillType.MINOR);
        axolotlBucketModel.greaterGills = EnhancedAxolotlBucket.getGillType(stack).equals(EnhancedAxolotlBucket.GillType.GREATER);

        axolotlBucketModel.renderToBuffer(matrix, builder, light, overlayLight, 1, 1, 1, 1);

        if (stack.getOrCreateTagElement("display").contains("gfp")) {
            multiBufferSource.getBuffer(RenderType.eyes(getGlowingTexture(stack)));
            axolotlBucketModel.renderToBuffer(matrix, builder, light, overlayLight, 1, 1, 1, 1);
        }

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

    @OnlyIn(Dist.CLIENT)
    public ResourceLocation getGlowingTexture(ItemStack stack) {
        if (stack.getItem() instanceof EnhancedAxolotlBucket) {
            String s = EnhancedAxolotlBucket.getTexture(stack);
            s="g"+s;

            ResourceLocation resourcelocation = textureCache.getFromCache(s);

            if (resourcelocation == null) {
                int[] image = EnhancedAxolotlBucket.getGlowingImage(stack);

                if (image.length == 1) {
                    return ERROR_TEXTURE_LOCATION;
                }

                try {
                    resourcelocation = new ResourceLocation(s);
                    DrawnTexture texture = new DrawnTexture(image, "eanimod:textures/items/axolotl_glow_bucket_base.png");
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
