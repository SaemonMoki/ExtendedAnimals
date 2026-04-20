//package mokiyoki.enhancedanimals.renderer;
//
//import com.google.common.collect.Maps;
//import mokiyoki.enhancedanimals.entity.EnhancedCat;
//import mokiyoki.enhancedanimals.model.ModelEnhancedCat;
//import mokiyoki.enhancedanimals.renderer.texture.EnhancedLayeredTexture;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.renderer.entity.EntityRendererManager;
//import net.minecraft.client.renderer.entity.MobRenderer;
//import net.minecraft.util.ResourceLocation;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//
//import java.util.Map;
//
//@OnlyIn(Dist.CLIENT)
//public class RenderEnhancedCat extends MobRenderer<EnhancedCat, ModelEnhancedCat<EnhancedCat>>
//{
//    private static final Map<String, ResourceLocation> LAYERED_LOCATION_CACHE = Maps.<String, ResourceLocation>newHashMap();
//    private static final String ENHANCED_CAT_TEXTURE_LOCATION = "eanimod:textures/entity/cat/";
//    private static final ResourceLocation ERROR_TEXTURE_LOCATION = new ResourceLocation("eanimod:textures/entity/cat/catbase.png");
//
//    public RenderEnhancedCat(EntityRendererManager render)
//    {
//        super(render, new ModelEnhancedCat<>(), 0.35F);
//    }
//
//    /**
//     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
//     */
//    public ResourceLocation getEntityTexture(EnhancedCat entity) {
//        String s = entity.getCatTexture();
//
//        if (s == null || s.isEmpty()) {
//            return ERROR_TEXTURE_LOCATION;
//        }
//
//        ResourceLocation resourcelocation = LAYERED_LOCATION_CACHE.get(s);
//
//        if (resourcelocation == null) {
//            String[] textures = entity.getVariantTexturePaths();
//
//            if (textures == null || textures.length == 0) {
//                return ERROR_TEXTURE_LOCATION;
//            }
//
//            try {
//                resourcelocation = new ResourceLocation(s);
//                Minecraft.getInstance().getTextureManager().loadTexture(resourcelocation, new EnhancedLayeredTexture(ENHANCED_CAT_TEXTURE_LOCATION, textures, null, null));
//                LAYERED_LOCATION_CACHE.put(s, resourcelocation);
//            } catch (IllegalStateException e) {
//                return ERROR_TEXTURE_LOCATION;
//            }
//
//        }
//
//        return resourcelocation;
//    }
//}
