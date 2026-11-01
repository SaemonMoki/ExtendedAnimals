package mokiyoki.enhancedanimals.renderer.texture;

import mokiyoki.enhancedanimals.entity.util.Colouration;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.server.packs.resources.ResourceManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static mokiyoki.enhancedanimals.renderer.texture.TexturingUtils.*;

public class TextureGrouping {

    private interface Child {
        NativeImage resolve(TextureGrouping parent, String modLocation, ResourceManager manager, Colouration colouration, int x, int y);
    }

    private static final class GroupChild implements Child {
        private final TextureGrouping group;

        private GroupChild(TextureGrouping group) {
            this.group = group;
        }

        @Override
        public NativeImage resolve(TextureGrouping parent, String modLocation, ResourceManager manager, Colouration colouration, int x, int y) {
            return group.processGrouping(modLocation, manager, colouration, x, y);
        }
    }

    private static final class LayerChild implements Child {
        private final TextureLayer layer;

        private LayerChild(TextureLayer layer) {
            this.layer = layer;
        }

        @Override
        public NativeImage resolve(TextureGrouping parent, String modLocation, ResourceManager manager, Colouration colouration, int x, int y) {
            if (layer.getTexture().isEmpty()) return null;
            createTexture(layer, modLocation, manager, x, y);
            parent.applyLayerSpecifics(layer, colouration);
            return layer.getTextureImage();
        }
    }

    private final List<Child> children = new ArrayList<>();

    private TexturingType texturingType;

    public TextureGrouping(TexturingType texturingType) {
        this.texturingType = texturingType;
    }

    public NativeImage processGrouping(String modLocation, ResourceManager manager, Colouration colouration, int x, int y) {
        try {
            List<NativeImage> groupImages = new ArrayList<>();

            for (Child child : children) {
                NativeImage childImage = child.resolve(this, modLocation, manager, colouration, x, y);
                if (childImage != null) groupImages.add(childImage);
            }

            return applyGroupMerging(groupImages, colouration);
        } catch (Exception e) {
            throw new IllegalStateException(
                    "Exception occurred in Texturing Grouping" + children.toString(), e);
        }
    }

    private NativeImage applyGroupMerging(List<NativeImage> groupImages, Colouration colouration) {
        if (!groupImages.isEmpty()) {
            NativeImage baseImage = groupImages.remove(0);

            switch(texturingType) {
                case MERGE_GROUP -> layerGroups(baseImage, groupImages);
                case MASK_GROUP -> maskAlpha(baseImage, groupImages);
                case AVERAGE_GROUP -> blendAverage(baseImage, groupImages);
                case CUTOUT_GROUP -> cutoutTextures(baseImage, groupImages);
                case APPLY_SHADING -> applyShading(baseImage, groupImages);
                case DYE_GROUP -> blendGroupDye(baseImage, groupImages, colouration.getDyeColour());
                case APPLY_PHEOMELANIN -> generatePheomelanin(baseImage, groupImages);
            }

            return baseImage;
        }

        return null;
    }

    private void generatePheomelanin(NativeImage baseImage, List<NativeImage> groupImages) {
        applyPheomelanin(baseImage, groupImages);
    }

    private void blendGroupDye(NativeImage baseImage, List<NativeImage> groupImages, int dyeColour) {
        layerGroups(baseImage, groupImages);
        applyBGRBlend(baseImage, dyeColour);
    }

    private void applyShading(NativeImage baseImage, List<NativeImage> groupImages) {
        NativeImage shading = groupImages.remove(groupImages.size()-1);
        layerGroups(baseImage, groupImages);
        for(int i = 0; i < baseImage.getHeight(); ++i) {
            for(int j = 0; j < baseImage.getWidth(); ++j) {
                int shadingARGB = shading.getPixelRGBA(j, i);
                int shadingA = shadingARGB >> 24 & 255;
                if (shadingA != 255) {

                }
            }
        }
    }

    private void cutoutTextures(NativeImage cutoutImage, List<NativeImage> groupImages) {
        if (!groupImages.isEmpty()) {
            NativeImage image = groupImages.remove(0);
            if (!groupImages.isEmpty()) {
                layerGroups(image, groupImages);
            }
            int h = cutoutImage.getHeight();
            int w = cutoutImage.getWidth();
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    cutoutAlpha(j, i, cutoutImage, image);
                }
            }
        }
    }

    private void layerGroups(NativeImage compiledImage, List<NativeImage> groupImages) {
        for (NativeImage applyImage : groupImages) {
            applyPixelLayer(compiledImage, applyImage);
        }
    }

    private void multiplyGroups(NativeImage compiledImage, List<NativeImage> groupImages) {
        for (NativeImage applyImage : groupImages) {
            multiplyPixelLayer(compiledImage, applyImage);
        }
    }

    private void maskAlpha(NativeImage alphaMaskImage, List<NativeImage> groupImages) {
        if (!groupImages.isEmpty()) {
            NativeImage mergeGroup = groupImages.get(0);
            //First merge image groups
            if (groupImages.size() > 1) {
                NativeImage baseImage = groupImages.get(0);
                for (int i = 1; i < groupImages.size(); i++) {
                    applyPixelLayer(baseImage, groupImages.get(i));
                }
                mergeGroup = baseImage;
            }

            applyAlphaMaskBlend(alphaMaskImage, mergeGroup);
        }
    }

    private void blendAverage(NativeImage compiledImage, List<NativeImage> groupImages) {
        applyAverageBlend(compiledImage, groupImages);
    }

    private void applyLayerSpecifics(TextureLayer layer, Colouration colouration) {
        switch(layer.getTexturingType()) {
            case APPLY_RED -> layer.setTextureImage(applySetRGB(layer.getTextureImage(), colouration.getPheomelaninColour()));
            case APPLY_BLACK -> layer.setTextureImage(applySetRGB(layer.getTextureImage(), colouration.getMelaninColour()));
            case APPLY_SHADE_MELANIN -> layer.setTextureImage(applyShadeMelanin(layer.getTextureImage(), colouration.getMelaninColour()));
            case APPLY_COLLAR_COLOUR -> layer.setTextureImage(applyRGBBlend(layer.getTextureImage(), colouration.getCollarColour()));
            case APPLY_BRIDLE_COLOUR -> layer.setTextureImage(applyRGBBlend(layer.getTextureImage(), colouration.getBridleColour()));
            case APPLY_SADDLE_COLOUR -> layer.setTextureImage(applyRGBBlend(layer.getTextureImage(), colouration.getSaddleColour()));
            case APPLY_DYE -> layer.setTextureImage(applyBGRBlend(layer.getTextureImage(), colouration.getDyeColour()));
            case APPLY_EYE_LEFT_COLOUR -> layer.setTextureImage(applyBGRBlend(layer.getTextureImage(), colouration.getLeftEyeColour()));
            case APPLY_EYE_RIGHT_COLOUR -> layer.setTextureImage(applyBGRBlend(layer.getTextureImage(), colouration.getRightEyeColour()));
            case APPLY_RGB -> layer.setTextureImage(applyBGRBlend(layer.getTextureImage(), layer.getRGB()));
            case APPLY_RGBA -> layer.setTextureImage(applyBGRABlend(layer.getTextureImage(), layer.getRGB()));
            case APPLY_SHIFT -> layer.setTextureImage(applyHueShift(layer.getTextureImage(), layer.getRGB()));
            case APPLY_FLIP -> layer.setTextureImage(mirrorTexture(layer.getTextureImage(), layer.getCubes()));
        }
    }

    public void addGrouping(TextureGrouping textureGrouping) {
        this.children.add(new GroupChild(textureGrouping));
    }

    public void addTextureLayers(TextureLayer textureLayer) {
        this.children.add(new LayerChild(textureLayer));
    }

    public void setTexturingType(TexturingType texturingType) {
        this.texturingType = texturingType;
    }

    public boolean isPopulated() {
        return !this.children.isEmpty();
    }
}