package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.vertex.PoseStack;
import mokiyoki.enhancedanimals.entity.EnhancedAxolotl;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public class ModelEnhancedAxolotlGlowingLayer extends RenderLayer<EnhancedAxolotl, ModelEnhancedAxolotl<EnhancedAxolotl>> {

    public ModelEnhancedAxolotlGlowingLayer(RenderLayerParent<EnhancedAxolotl, ModelEnhancedAxolotl<EnhancedAxolotl>> p_117346_) {
        super(p_117346_);
    }

    @Override
    public void render(PoseStack p_117349_, MultiBufferSource p_117350_, int p_117351_, EnhancedAxolotl p_117352_, float p_117353_, float p_117354_, float p_117355_, float p_117356_, float p_117357_, float p_117358_) {

    }
}
