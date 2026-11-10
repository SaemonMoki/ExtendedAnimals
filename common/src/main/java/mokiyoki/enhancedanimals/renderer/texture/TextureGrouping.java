package mokiyoki.enhancedanimals.renderer.texture;

import mokiyoki.enhancedanimals.entity.util.Colouration;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.server.packs.resources.ResourceManager;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HexFormat;
import java.util.List;

import static mokiyoki.enhancedanimals.renderer.texture.TexturingUtils.*;

public class TextureGrouping {

    private interface Child {
        NativeImage resolve(TextureGrouping parent, String modLocation, ResourceManager manager, Colouration colouration, int x, int y);

        boolean describe(StringBuilder sb);

        Object node();
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

        @Override
        public boolean describe(StringBuilder sb) {
            return group.describe(sb);
        }

        @Override
        public Object node() {
            return group;
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

        @Override
        public boolean describe(StringBuilder sb) {
            if (layer.getTexture().isEmpty()) return false;
            sb.append('"').append(layer.getTexture()).append('"');
            if (layer.getTexturingType() != TexturingType.NONE) sb.append('+').append(layer.getTexturingType().name());
            if (layer.getRGB() != -1) sb.append(':').append(layer.getRGB());
            if (layer.getCubes().length != 1 || layer.getCubes()[0] != -1) sb.append(Arrays.toString(layer.getCubes()));
            for (Effect effect : layer.getEffects()) effect.describe(sb);
            return true;
        }

        @Override
        public Object node() {
            return layer;
        }
    }

    private final List<Child> children = new ArrayList<>();

    private TexturingType texturingType;

    private boolean hasParts;

    public TextureGrouping(TexturingType texturingType) {
        this.texturingType = texturingType;
    }

    public static TextureGrouping merge() {
        return new TextureGrouping(TexturingType.MERGE_GROUP);
    }

    public static TextureGrouping average() {
        return new TextureGrouping(TexturingType.AVERAGE_GROUP);
    }

    static TextureGrouping withParts(TexturingType texturingType) {
        TextureGrouping group = new TextureGrouping(texturingType);
        group.hasParts = true;
        group.addGrouping(merge());
        group.addGrouping(merge());
        return group;
    }

    TextureGrouping part(int index) {
        return ((GroupChild) children.get(index)).group;
    }

    public boolean hasParts() {
        return hasParts;
    }

    public NativeImage processGrouping(String modLocation, ResourceManager manager, Colouration colouration, int x, int y) {
        try {
            if (hasParts) return processParts(modLocation, manager, colouration, x, y);

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

    private NativeImage processParts(String modLocation, ResourceManager manager, Colouration colouration, int x, int y) {
        NativeImage shape = part(0).processGrouping(modLocation, manager, colouration, x, y);
        NativeImage content = part(1).processGrouping(modLocation, manager, colouration, x, y);
        if (content == null) return null;
        if (shape == null) return content;
        List<NativeImage> contents = new ArrayList<>(List.of(content));
        if (texturingType == TexturingType.MASK_GROUP) {
            maskAlpha(shape, contents);
        } else {
            cutoutTextures(shape, contents);
        }
        return shape;
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
        applyEffect(layer, layer.getTexturingType(), layer.getRGB(), layer.getCubes(), colouration);
        for (Effect effect : layer.getEffects()) {
            applyEffect(layer, effect.getType(), effect.getRGB(), effect.getCubes(), colouration);
        }
    }

    private void applyEffect(TextureLayer layer, TexturingType type, int rgb, int[] cubes, Colouration colouration) {
        switch(type) {
            case APPLY_RED -> layer.setTextureImage(applySetRGB(layer.getTextureImage(), colouration.getPheomelaninColour()));
            case APPLY_BLACK -> layer.setTextureImage(applySetRGB(layer.getTextureImage(), colouration.getMelaninColour()));
            case APPLY_SHADE_MELANIN -> layer.setTextureImage(applyShadeMelanin(layer.getTextureImage(), colouration.getMelaninColour()));
            case APPLY_COLLAR_COLOUR -> layer.setTextureImage(applyRGBBlend(layer.getTextureImage(), colouration.getCollarColour()));
            case APPLY_BRIDLE_COLOUR -> layer.setTextureImage(applyRGBBlend(layer.getTextureImage(), colouration.getBridleColour()));
            case APPLY_SADDLE_COLOUR -> layer.setTextureImage(applyRGBBlend(layer.getTextureImage(), colouration.getSaddleColour()));
            case APPLY_DYE -> layer.setTextureImage(applyBGRBlend(layer.getTextureImage(), colouration.getDyeColour()));
            case APPLY_EYE_LEFT_COLOUR -> layer.setTextureImage(applyBGRBlend(layer.getTextureImage(), colouration.getLeftEyeColour()));
            case APPLY_EYE_RIGHT_COLOUR -> layer.setTextureImage(applyBGRBlend(layer.getTextureImage(), colouration.getRightEyeColour()));
            case APPLY_RGB -> layer.setTextureImage(applyBGRBlend(layer.getTextureImage(), rgb));
            case APPLY_RGBA -> layer.setTextureImage(applyBGRABlend(layer.getTextureImage(), rgb));
            case APPLY_SHIFT -> layer.setTextureImage(applyHueShift(layer.getTextureImage(), rgb));
            case APPLY_FLIP -> layer.setTextureImage(mirrorTexture(layer.getTextureImage(), cubes));
        }
    }

    public void addGrouping(TextureGrouping textureGrouping) {
        this.children.add(new GroupChild(textureGrouping));
    }

    public TextureGrouping add(TextureGrouping child) {
        addGrouping(child);
        return child;
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

    public TexturingType getTexturingType() {
        return texturingType;
    }

    public List<Object> getChildren() {
        List<Object> nodes = new ArrayList<>(children.size());
        for (Child child : children) nodes.add(child.node());
        return Collections.unmodifiableList(nodes);
    }

    public String describe() {
        StringBuilder sb = new StringBuilder();
        describe(sb);
        return sb.toString();
    }

    private boolean describe(StringBuilder sb) {
        int start = sb.length();
        if (hasParts) {
            sb.append(texturingType.name()).append('{');
            part(0).describe(sb);
            sb.append('|');
            if (!part(1).describe(sb)) {
                sb.setLength(start);
                return false;
            }
            sb.append('}');
            return true;
        }
        sb.append(texturingType.name()).append('(');
        boolean wroteChild = false;
        for (Child child : children) {
            int before = sb.length();
            if (wroteChild) sb.append(',');
            if (child.describe(sb)) {
                wroteChild = true;
            } else {
                sb.setLength(before);
            }
        }
        if (!wroteChild) {
            sb.setLength(start);
            return false;
        }
        sb.append(')');
        return true;
    }

    public String cacheKey() {
        try {
            byte[] hash = MessageDigest.getInstance("MD5").digest(describe().getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }
}