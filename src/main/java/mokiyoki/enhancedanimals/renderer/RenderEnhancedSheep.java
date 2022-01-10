//package mokiyoki.enhancedanimals.renderer;
//
//import mokiyoki.enhancedanimals.entity.EnhancedSheep;
//import mokiyoki.enhancedanimals.entity.util.Colouration;
//import mokiyoki.enhancedanimals.model.ModelEnhancedSheep;
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
//public class RenderEnhancedSheep extends MobRenderer<EnhancedSheep, ModelEnhancedSheep<EnhancedSheep>> {
//
//    private static final LayeredTextureCacher textureCache = new LayeredTextureCacher();
//    private static final String ENHANCED_SHEEP_TEXTURE_LOCATION = "eanimod:textures/entities/sheep/";
//    private static final ResourceLocation ERROR_TEXTURE_LOCATION = new ResourceLocation("eanimod:textures/entities/sheep/sheep.png");
//
//    public RenderEnhancedSheep(EntityRendererProvider.Context renderManager) {
//        super(renderManager, new ModelEnhancedSheep<>(), 0.6F);
//    }
//
//    /**
//     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
//     */
//    public ResourceLocation getTextureLocation(EnhancedSheep entity) {
//        String s = entity.getTexture();
//        Colouration colourRGB = entity.getRgb();
//        colourRGB.setDyeColour(EnhancedSheep.getDyeRgb(entity.getFleeceDyeColour()));
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
//
//            String[] textures = entity.getVariantTexturePaths();
//
//            if (textures == null || textures.length == 0) {
//                return ERROR_TEXTURE_LOCATION;
//            }
//
//            try {
//                resourcelocation = new ResourceLocation(s);
//                Minecraft.getInstance().getTextureManager().register(resourcelocation, new EnhancedLayeredTexture(ENHANCED_SHEEP_TEXTURE_LOCATION, textures, entity.getVariantAlphaTexturePaths(), colourRGB));
//
//                textureCache.putInCache(s, resourcelocation);
//            } catch (IllegalStateException e) {
//                return ERROR_TEXTURE_LOCATION;
//            }
//        }
//
//        return resourcelocation;
//    }
//
//}
