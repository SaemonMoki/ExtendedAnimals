package mokiyoki.enhancedanimals.renderer.layers;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.model.ModelEnhancedChicken;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class SilkieChickenLayer extends LayerRenderer<EnhancedChicken, ModelEnhancedChicken<EnhancedChicken>> {

    private static final Map<String, ResourceLocation> LAYERED_LOCATION_CACHE = Maps.<String, ResourceLocation>newHashMap();
    private static final ResourceLocation ERROR_TEXTURE_LOCATION = new ResourceLocation("eanimod:textures/entities/chickensilkie/chickenbase.png");

    private static final ResourceLocation[] CHICKEN_SADDLE_TEXTURES = new ResourceLocation[]{new ResourceLocation("eanimod:textures/entities/chickensilkie/ground_solid_silver.png")};
    private final ModelEnhancedChicken<EnhancedChicken> model_2 = new ModelEnhancedChicken<>();

    public SilkieChickenLayer(IEntityRenderer<EnhancedChicken, ModelEnhancedChicken<EnhancedChicken>> p_i50933_1_) {
        super(p_i50933_1_);
    }

    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, EnhancedChicken entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

        ResourceLocation resourcelocation_5;
        resourcelocation_5 = CHICKEN_SADDLE_TEXTURES[0];
        this.getEntityModel().copyModelAttributesTo(this.model_2);
        this.model_2.setRotationAngles(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        IVertexBuilder ivertexbuilder_blanket = bufferIn.getBuffer(RenderType.getEntityTranslucent(resourcelocation_5));
        this.model_2.render(matrixStackIn, ivertexbuilder_blanket, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 0.5F);
    }
}