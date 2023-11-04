//package mokiyoki.enhancedanimals.renderer;
//
//import mokiyoki.enhancedanimals.entity.EnhancedHorse;
//import mokiyoki.enhancedanimals.entity.util.Colouration;
//import mokiyoki.enhancedanimals.model.ModelEnhancedHorse;
//import mokiyoki.enhancedanimals.renderer.texture.EnhancedLayeredTexture;
//import mokiyoki.enhancedanimals.renderer.util.LayeredTextureCacher;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
//import net.minecraft.client.renderer.entity.EntityRendererProvider;
//import net.minecraft.client.renderer.entity.MobRenderer;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//
//@OnlyIn(Dist.CLIENT)
//public class RenderEnhancedHorse extends MobRenderer<EnhancedHorse, ModelEnhancedHorse<EnhancedHorse>> {
//    private static final LayeredTextureCacher textureCache = new LayeredTextureCacher();
//    private static final String ENHANCED_HORSE_TEXTURE_LOCATION = "eanimod:textures/entity/horse/";
//    private static final ResourceLocation ERROR_TEXTURE_LOCATION = new ResourceLocation("eanimod:textures/entity/horse/horsebase.png");
//
//    public RenderEnhancedHorse(EntityRendererProvider.Context renderManager) {
//        super(renderManager, new ModelEnhancedHorse<>(), 0.8F);
//    }
//
//    /**
//     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
//     */
//    public ResourceLocation getTextureLocation(EnhancedHorse entity) {
//        String s = entity.getTexture();
//        Colouration colourRGB = entity.getRgb();
//
//        if (s == null || s.isEmpty() || colourRGB == null) {
//            return ERROR_TEXTURE_LOCATION;
//        }
//
//        s = s + colourRGB.getRGBStrings();
//
//        ResourceLocation resourcelocation = textureCache.getFromCache(s);
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
//                Minecraft.getInstance().getTextureManager().register(resourcelocation, new EnhancedLayeredTexture(ENHANCED_HORSE_TEXTURE_LOCATION, textures, null, colourRGB));
//
//                textureCache.putInCache(s, resourcelocation);
//            } catch (IllegalStateException e) {
//                return ERROR_TEXTURE_LOCATION;
//            }
//        }
//        return resourcelocation;
//    }
//}
