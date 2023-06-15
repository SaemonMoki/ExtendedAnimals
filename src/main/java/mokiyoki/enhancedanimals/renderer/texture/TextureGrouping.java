package mokiyoki.enhancedanimals.renderer.texture;

import mokiyoki.enhancedanimals.entity.util.Colouration;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.server.packs.resources.ResourceManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static mokiyoki.enhancedanimals.renderer.texture.TexturingUtils.*;

public class TextureGrouping {

    private List<TextureGrouping> textureGroupings = new ArrayList<>();
    private List<TextureLayer> textureLayers = new ArrayList<>();

    private TexturingType texturingType;

    public TextureGrouping(TexturingType texturingType) {
        this.texturingType = texturingType;
    }

    public NativeImage processGrouping(String modLocation, ResourceManager manager, Colouration colouration, int x, int y) {
        try {
            List<NativeImage> groupImages = new ArrayList<>();

            for (TextureGrouping group : textureGroupings) {
                NativeImage groupCompiledImage = group.processGrouping(modLocation, manager, colouration, x, y);
                if (groupCompiledImage != null) groupImages.add(groupCompiledImage);
            }

            for (TextureLayer layer : textureLayers) {
                if (!layer.getTexture().isEmpty()) {
                    createTexture(layer, modLocation, manager, x, y);
                    applyLayerSpecifics(layer, colouration);
                    groupImages.add(layer.getTextureImage());
                }
            }

            return applyGroupMerging(groupImages, colouration);
        } catch (Exception e) {
            throw new IllegalStateException(
                    "Exception occurred in Texturing Grouping" + textureGroupings.toString(), e);
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
                case SHADE_FEATHERS -> shadeFeathers(baseImage, groupImages);
            }

            return baseImage;
        }

        return null;
    }

    private void shadeFeathers(NativeImage baseImage, List<NativeImage> groupImages) {
        if (groupImages.size() > 1) {
            int[][] map = getFeatherColour(groupImages.remove(0));
            int[][] featherColour = getFeatherColour(baseImage);

            int[][] colourMap = new int[256][256];
            for (int[] i:colourMap) {
                Arrays.fill(i,16711935);
            }

            for (int y=0; y < 25; y++) {
                for (int x=0; x < 128; x++) {
                    int colour = map[x][y];
                    int red = colour & 255;
                    int green = colour >> 8 & 255;
                    int blue = colour >> 16 & 255;
                    colourMap[green][red!=0?0:blue] = featherColour[x][y];
                }
            }

            boolean cleared = true;
            for (int x=0; x < 256;x++) {
                for (int y=0; y < 256;y++) {
                    int colour = colourMap[x][y];
                    if (colour!=16711935) {
                        if (y > 0) {
                            int val = colourMap[x][y-1];
                            if (val==16711935) {
                                colourMap[x][y-1] = colour;
                            }
                            if (x > 0) {
                                val = colourMap[x-1][y-1];
                                if (val==16711935) {
                                    colourMap[x-1][y-1] = colour;
                                }
                            }
                        }
                        if (x > 0) {
                            int val = colourMap[x-1][y];
                            if (val==16711935) {
                                colourMap[x-1][y] = colour;
                            }
                        }
                        if (y < 255) {
                            int val = colourMap[x][y+1];
                            if (val==16711935) {
                                colourMap[x][y+1] = colour;
                            }
                            if (x < 255) {
                                val = colourMap[x+1][y+1];
                                if (val==16711935) {
                                    colourMap[x+1][y+1] = colour;
                                }
                            }
                        }
                        if (x < 255) {
                            int val = colourMap[x+1][y];
                            if (val==16711935) {
                                colourMap[x+1][y] = colour;
                            }
                        }
                    } else {
                        if (cleared) cleared = false;
                    }
                    if (x==255 && y==255) {
                        if (!cleared) {
                            x=0;
                            y=0;
                        }
                    }
                }
            }

            NativeImage feathers = groupImages.remove(0);
            layerGroups(feathers, groupImages);

            int w = baseImage.getWidth();
            int h = baseImage.getWidth();
            for (int x=0; x < w; x++) {
                for (int y=0; y < h; y++) {
                    int coords = feathers.getPixelRGBA(x, y);
                    if (coords!=0) {
                        int colour = colourMap[coords >> 8 & 255][coords >> 16 & 25];
                        if (colour!=0) {
                            baseImage.setPixelRGBA(x, y, colour);
                        }
                    }
                }
            }
        }
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
            case APPLY_RED:
                layer.setTextureImage(applyRGBBlend(layer.getTextureImage(), colouration.getPheomelaninColour()));
                break;
            case APPLY_BLACK:
                layer.setTextureImage(applyRGBBlend(layer.getTextureImage(), colouration.getMelaninColour()));
                break;
            case APPLY_COLLAR_COLOUR:
                layer.setTextureImage(applyRGBBlend(layer.getTextureImage(), colouration.getCollarColour()));
                break;
            case APPLY_BRIDLE_COLOUR:
                layer.setTextureImage(applyRGBBlend(layer.getTextureImage(), colouration.getBridleColour()));
                break;
            case APPLY_SADDLE_COLOUR:
                layer.setTextureImage(applyRGBBlend(layer.getTextureImage(), colouration.getSaddleColour()));
                break;
            case APPLY_DYE:
                layer.setTextureImage(applyBGRBlend(layer.getTextureImage(), colouration.getDyeColour()));
                break;
            case APPLY_EYE_LEFT_COLOUR:
                layer.setTextureImage(applyBGRBlend(layer.getTextureImage(), colouration.getLeftEyeColour()));
                break;
            case APPLY_EYE_RIGHT_COLOUR:
                layer.setTextureImage(applyBGRBlend(layer.getTextureImage(), colouration.getRightEyeColour()));
                break;
            case APPLY_RGB:
                layer.setTextureImage(applyBGRBlend(layer.getTextureImage(), layer.getRGB()));
                break;
            case APPLY_SHIFT:
                layer.setTextureImage(applyHueShift(layer.getTextureImage(), layer.getRGB()));
        }
    }

    public void addGrouping(TextureGrouping textureGrouping) {
            this.textureGroupings.add(textureGrouping);
    }

    public void addTextureLayers(TextureLayer textureLayer) {
        this.textureLayers.add(textureLayer);
    }

    public void setTexturingType(TexturingType texturingType) {
        this.texturingType = texturingType;
    }

    public boolean isPopulated() {
        if (this.textureGroupings.size() > 0 || this.textureLayers.size() > 0) {
            return true;
        }
        return false;
    }
}