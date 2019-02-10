package mokiyoki.enhancedanimals.renderer.layers;

import com.google.common.collect.Maps;
import mokiyoki.enhancedanimals.entity.EnhancedSheep;
import mokiyoki.enhancedanimals.model.ModelEnhancedSheepWithWool;
import mokiyoki.enhancedanimals.renderer.EnhancedLayeredTexture;
import mokiyoki.enhancedanimals.renderer.RenderEnhancedSheep;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;

@SideOnly(Side.CLIENT)
public class LayerEnhancedSheepWool implements LayerRenderer<EnhancedSheep> {

    private static final Map<String, ResourceLocation> LAYERED_LOCATION_CACHE = Maps.<String, ResourceLocation>newHashMap();
    private static final String ENHANCED_SHEEP_TEXTURE_LOCATION = "eanimod:textures/entities/sheep/";
    private final RenderEnhancedSheep enhancedSheepRenderer;
    private final ModelEnhancedSheepWithWool enhancedSheepModel = new ModelEnhancedSheepWithWool();

    public LayerEnhancedSheepWool(RenderEnhancedSheep enhancedSheepRendererIn) {
        this.enhancedSheepRenderer = enhancedSheepRendererIn;
    }

    public void doRenderLayer(EnhancedSheep entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (!entitylivingbaseIn.getSheared() && !entitylivingbaseIn.isInvisible())
        {
            this.enhancedSheepRenderer.bindTexture(getLayeredTexture(entitylivingbaseIn));

            if (entitylivingbaseIn.hasCustomName() && "jeb_".equals(entitylivingbaseIn.getCustomNameTag()))
            {
                int i1 = 25;
                int i = entitylivingbaseIn.ticksExisted / 25 + entitylivingbaseIn.getEntityId();
                int j = EnumDyeColor.values().length;
                int k = i % j;
                int l = (i + 1) % j;
                float f = ((float)(entitylivingbaseIn.ticksExisted % 25) + partialTicks) / 25.0F;
                float[] afloat1 = EnhancedSheep.getDyeRgb(EnumDyeColor.byMetadata(k));
                float[] afloat2 = EnhancedSheep.getDyeRgb(EnumDyeColor.byMetadata(l));
                GlStateManager.color(afloat1[0] * (1.0F - f) + afloat2[0] * f, afloat1[1] * (1.0F - f) + afloat2[1] * f, afloat1[2] * (1.0F - f) + afloat2[2] * f);
            }
            else
            {
                float[] afloat = EnhancedSheep.getDyeRgb(entitylivingbaseIn.getFleeceColor());
                GlStateManager.color(afloat[0], afloat[1], afloat[2]);
            }

            this.enhancedSheepModel.setModelAttributes(this.enhancedSheepRenderer.getMainModel());
            this.enhancedSheepModel.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
            this.enhancedSheepModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }

    protected ResourceLocation getLayeredTexture(EnhancedSheep entity)
    {
        String s = entity.getSheepFleeceTexture();
        ResourceLocation resourcelocation = LAYERED_LOCATION_CACHE.get(s);

        if (resourcelocation == null)
        {
            resourcelocation = new ResourceLocation(s);
            Minecraft.getMinecraft().getTextureManager().loadTexture(resourcelocation, new EnhancedLayeredTexture(ENHANCED_SHEEP_TEXTURE_LOCATION, entity.getVariantFleeceTexturePaths()));
            LAYERED_LOCATION_CACHE.put(s, resourcelocation);
        }

        return resourcelocation;
    }

    public boolean shouldCombineTextures() {
        return true;
    }
}
