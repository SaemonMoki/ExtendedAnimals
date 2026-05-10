package mokiyoki.enhancedanimals.renderer.textures;

import mokiyoki.enhancedanimals.entity.EnhancedAxolotlEgg;
import mokiyoki.enhancedanimals.renderer.texture.TextureGrouping;
import mokiyoki.enhancedanimals.renderer.texture.TexturingType;

public class AxolotlEggTexture {

    public static void calculateAxolotlEggTextures(EnhancedAxolotlEgg egg, String genes) {
        String[] splitGenes = genes.split("\\+");
        String[] g = splitGenes[1].split(",");

        baseColourMutation(g);

        if (gene(g, 12)>1 && gene(g, 13)>1) {
            //pied
            int piedStrength = (int) ((gene(g, 14) + gene(g, 15) - 2) * 0.3F);
        }

        TextureGrouping parentGroup = new TextureGrouping(TexturingType.MERGE_GROUP);

//        egg.setTextureGrouping(parentGroup);
    }

    private static int gene(String[] g, int locus) {
        return Integer.parseInt(g[locus]);
    }

    private static String baseColourMutation(String[] g) {
        if (gene(g, 0)+ gene(g, 1)==4) {
            //albino
            if (gene(g, 2)+ gene(g, 3)==4) {
                return "albino";
            } else {
                return "gold";
            }
        } else {
            if (gene(g, 2)+ gene(g, 3)==4) {
                //axanthic
                if (gene(g, 6)+ gene(g, 7)==4) {
                    //lavender
                    return "lavender";
                } else {
                    return "axanthic";
                }
            } else {
                if (gene(g, 6)+ gene(g, 7)==4) {
                    //copper
                    return "copper";
                } else {
                    return "wildtype";
                }
            }
        }
    }
}
