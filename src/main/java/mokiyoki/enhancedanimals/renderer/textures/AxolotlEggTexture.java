package mokiyoki.enhancedanimals.renderer.textures;

import mokiyoki.enhancedanimals.entity.EnhancedAxolotlEgg;
import mokiyoki.enhancedanimals.renderer.texture.TextureGrouping;
import mokiyoki.enhancedanimals.renderer.texture.TexturingType;

import java.util.concurrent.ThreadLocalRandom;

public class AxolotlEggTexture {
    private static final String[] base = {
        "white", "gold", ""
    };

    public static void calculateAxolotlEggTextures(EnhancedAxolotlEgg egg, String[] g) {
        TextureGrouping parentGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        String[] d = {""};

        int[] gene = new int[g.length];
        for (int i=0; i<g.length; i++) gene[i] = Integer.parseInt(g[i]);

        egg.addTexturetoTextureGroup(parentGroup, baseColourMutation(gene, d)+".png", d[0]);

        if (gene[12]>1 && gene[13]>1) {
            //pied
            int piedStrength = (int) ((gene[14] + gene[15] - 2) * 0.3F);
            int r = ThreadLocalRandom.current().nextInt(4);
            egg.addTexturetoTextureGroup(parentGroup, "piebald/"+r+".png", String.valueOf(r));
        } else {
            egg.addDelimiter();
        }

        egg.addTexturetoTextureGroup(parentGroup, "shell.png", String.valueOf(0));

        egg.setTextureGrouping(parentGroup);
    }

    private static String baseColourMutation(int[] g, String[] d) {
        if (dominantRecessive(d, 8, g)) {
            return "white";
        } else {
            if (dominantRecessive(d, 0, g)) {
                //albino
                if (dominantRecessive(d, 2, g)) {
                    return "white";
                } else {
                    return "gold";
                }
            } else {
                if (dominantRecessive(d, 2, g)) {
                    //axanthic
                    if (dominantRecessive(d, 6, g)) {
                        //lavender
                        return "lavender";
                    } else {
                        return "axanthic";
                    }
                } else {
                    if (dominantRecessive(d, 6, g)) {
                        //copper
                        return "copper";
                    } else {
                        return "wildtype";
                    }
                }
            }
        }
    }

    // when allele 1 is dominant and allele 2 is recessive
    protected static boolean dominantRecessive(String[] d, int locus, int[] genes) {
        d[0] += locus*0.5;
        if (genes[locus]+genes[locus+1]==4) {
            d[0] += locus*0.5 + ".r-";
            return true;
        } else {
            d[0] += locus*0.5 + ".d-";
            return false;
        }
    }

    //when allele 1 is recessive and allele 2 is dominant
    protected static boolean recessiveDominant(String[] d, int locus, int[] genes) {
        d[0] += locus*0.5;
        if (genes[locus]+genes[locus+1]==2) {
            d[0] += locus*0.5 + ".r-";
            return true;
        } else {
            d[0] += locus*0.5 + ".d-";
            return false;
        }
    }

    //when a gene has unique expressions for a heterozygous state
    protected static int heterozygous(String[] d, int locus, int[] genes) {
        int a = genes[locus];
        int b = genes[locus+1];

        int c = getGeneCode(a, b);
        d[0] += locus*0.5 + "." + c;

        return c;
    }

    protected static int getGeneCode(int a, int b) {
        int n = Math.max(a, b);
        return (n * (n - 1)) / 2 + Math.min(a, b);
    }
}
