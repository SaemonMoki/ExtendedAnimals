//package mokiyoki.enhancedanimals.renderer;
//
//import com.google.common.collect.Maps;
//import mokiyoki.enhancedanimals.entity.EnhancedHorse;
//import mokiyoki.enhancedanimals.entity.util.Colouration;
//import mokiyoki.enhancedanimals.model.ModelEnhancedHorse;
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
//public class RenderEnhancedHorse extends MobRenderer<EnhancedHorse, ModelEnhancedHorse<EnhancedHorse>> {
//    private static final Map<String, ResourceLocation> LAYERED_LOCATION_CACHE = Maps.<String, ResourceLocation>newHashMap();
//    private static final String ENHANCED_HORSE_TEXTURE_LOCATION = "eanimod:textures/entities/horse/";
//    private static final ResourceLocation ERROR_TEXTURE_LOCATION = new ResourceLocation("eanimod:textures/entities/horse/horsebase.png");
//
//    public RenderEnhancedHorse(EntityRendererManager render)
//    {
//        super(render, new ModelEnhancedHorse<>(), 0.75F);
//    }
//
//    /**
//     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
//     */
//    public ResourceLocation getEntityTexture(EnhancedHorse entity) {
//        String s = entity.getHorseTexture();
////        Colouration colourRGB = entity.colouration;
//
//        if (s == null || s.isEmpty()/* || colourRGB == null*/) {
//            return ERROR_TEXTURE_LOCATION;
//        }
//
//
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
//                Minecraft.getInstance().getTextureManager().loadTexture(resourcelocation, new EnhancedLayeredTexture(ENHANCED_HORSE_TEXTURE_LOCATION, textures, null, null));
//                LAYERED_LOCATION_CACHE.put(s, resourcelocation);
//            } catch (IllegalStateException e) {
//                return ERROR_TEXTURE_LOCATION;
//            }
//        }
//
//        return resourcelocation;
//    }
//}
