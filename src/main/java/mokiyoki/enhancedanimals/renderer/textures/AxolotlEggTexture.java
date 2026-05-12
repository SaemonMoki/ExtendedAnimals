package mokiyoki.enhancedanimals.renderer.textures;

import mokiyoki.enhancedanimals.entity.EnhancedAxolotlEgg;
import mokiyoki.enhancedanimals.renderer.texture.TextureGrouping;
import mokiyoki.enhancedanimals.renderer.texture.TexturingType;

import java.util.concurrent.ThreadLocalRandom;

public class AxolotlEggTexture {
    private String delimiter = "";

    public static void calculateAxolotlEggTextures(EnhancedAxolotlEgg egg, String[] g) {
        TextureGrouping parentGroup = new TextureGrouping(TexturingType.MERGE_GROUP);

        egg.addTexturetoTextureGroup(parentGroup, baseColourMutation(g)+".png", g[0]+g[1]+g[2]+g[3]+g[6]+g[7]);

        if (gene(g, 12)>1 && gene(g, 13)>1) {
            //pied
            int piedStrength = (int) ((gene(g, 14) + gene(g, 15) - 2) * 0.3F);
            int r = ThreadLocalRandom.current().nextInt(3);
            egg.addTexturetoTextureGroup(parentGroup, "piebald/"+r+".png", String.valueOf(r));
        } else {
            egg.addDelimiter();
        }

        egg.addTexturetoTextureGroup(parentGroup, "shell.png", String.valueOf(0));

        egg.setTextureGrouping(parentGroup);
    }

    private static int gene(String[] g, int locus) {
        return Integer.parseInt(g[locus]);
    }

    private static String baseColourMutation(String[] g) {
        if ((gene(g, 8)+ gene(g, 9)==4)) {
            return "white";
        } else {
            if (gene(g, 0) + gene(g, 1) == 4) {
                //albino
                if (gene(g, 2) + gene(g, 3) == 4) {
                    return "white";
                } else {
                    return "gold";
                }
            } else {
                if (gene(g, 2) + gene(g, 3) == 4) {
                    //axanthic
                    if (gene(g, 6) + gene(g, 7) == 4) {
                        //lavender
                        return "lavender";
                    } else {
                        return "axanthic";
                    }
                } else {
                    if (gene(g, 6) + gene(g, 7) == 4) {
                        //copper
                        return "copper";
                    } else {
                        return "wildtype";
                    }
                }
            }
        }
    }
}
