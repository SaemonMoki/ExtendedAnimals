package mokiyoki.enhancedanimals.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import mokiyoki.enhancedanimals.entity.EnhancedTurtle;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import mokiyoki.enhancedanimals.model.ModelEnhancedTurtle;
import mokiyoki.enhancedanimals.renderer.texture.EnhancedLayeredTexture;
import mokiyoki.enhancedanimals.renderer.util.LayeredTextureCacher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderEnhancedTurtle extends MobRenderer<EnhancedTurtle, ModelEnhancedTurtle<EnhancedTurtle>> {
    private static final LayeredTextureCacher textureCache = new LayeredTextureCacher();
    private static final String ENHANCED_TURTLE_TEXTURE_LOCATION = "eanimod:textures/entities/turtle/";
    private static final ResourceLocation ERROR_TEXTURE_LOCATION = new ResourceLocation("eanimod:textures/entities/turtle/turtlebase.png");

    public RenderEnhancedTurtle(EntityRendererManager render) {
        super(render, new ModelEnhancedTurtle<>(0.0F), 0.5F);
    }

    public void render(EnhancedTurtle entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        if (entityIn.isChild()) {
            this.shadowSize *= 0.5F;
        }

        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    public ResourceLocation getEntityTexture(EnhancedTurtle entity) {
        String s = entity.getTurtleTexture();
        Colouration colourRGB = entity.getRgb();

        if (s == null || s.isEmpty() || colourRGB == null) {
            return ERROR_TEXTURE_LOCATION;
        }

        s = s + colourRGB.getRGBStrings();

        ResourceLocation resourcelocation = textureCache.getFromCache(s);

        if (resourcelocation == null) {
            String[] textures = entity.getVariantTexturePaths();

            if (textures == null || textures.length == 0) {
                return ERROR_TEXTURE_LOCATION;
            }

            try {
                resourcelocation = new ResourceLocation(s);

                Minecraft.getInstance().getTextureManager().loadTexture(resourcelocation, new EnhancedLayeredTexture(ENHANCED_TURTLE_TEXTURE_LOCATION, textures, null, entity.colouration));

                textureCache.putInCache(s, resourcelocation);
            } catch (IllegalStateException e) {
                return ERROR_TEXTURE_LOCATION;
            }
        }

        return resourcelocation;
    }
}
