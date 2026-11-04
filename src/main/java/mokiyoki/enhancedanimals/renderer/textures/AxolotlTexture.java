package mokiyoki.enhancedanimals.renderer.textures;

import mokiyoki.enhancedanimals.entity.EnhancedAxolotl;
import mokiyoki.enhancedanimals.entity.util.Variation;
import mokiyoki.enhancedanimals.entity.util.VariationKeys;
import mokiyoki.enhancedanimals.renderer.texture.TextureGrouping;
import mokiyoki.enhancedanimals.renderer.texture.TexturingType;

import java.util.concurrent.ThreadLocalRandom;

import static mokiyoki.enhancedanimals.entity.util.VariationKeys.*;

public class AxolotlTexture {

    private static final int CHEEK_SLOTS = 2;

    private static final int[] headCubes = new int[] {
            0, 0, 0, 8, 6, 5,
            0, 39, 0, 10, 5, 0,
            1, 36, 0, 47, 0, 0, 8, 5
    };
    private static final int[] bodyCubes = new int[] {
            0, 0, 12, 8, 6, 4,
            0, 0, 22, 8, 8, 4,
            1, 55, 2, 55, 9, 3, 5, 0,
            1, 55, 16, 55, 23, 3, 5, 0,
            1, 26, 9, 26, 36, 0, 9, 5
    };
    private static final int[] tailCubes = new int[] {
            2, 26, -5, 26, 20, 0, 16, 5
    };

    private static final String[] AXOLOTL_TEXTURES_BASE = new String[] {
            "natural.png", "natural_xanthic.png", "highgold_xanthic.png"
    };

    private static final String[][] AXOLOTL_TEXTURES_GILLS = new String[][] {
            {"gills/base/natural.png",      "gills/long/natural.png",   "gills/greater/natural.png"},
            {"gills/base/white.png",        "gills/long/white.png",     "gills/greater/white.png"},
            {"gills/base/lightgrey.png",    "gills/long/lightgrey.png", "gills/greater/lightgrey.png"},
            {"gills/base/grey.png",         "gills/long/grey.png",      "gills/greater/grey.png"},
            {"gills/base/black.png",        "gills/long/black.png",     "gills/greater/black.png"},
            {"gills/base/brown.png",        "gills/long/brown.png",     "gills/greater/brown.png"},
            {"gills/base/pink.png",         "gills/long/pink.png",      "gills/greater/pink.png"},
            {"gills/base/red.png",          "gills/long/red.png",       "gills/greater/red.png"},
            {"gills/base/orange.png",       "gills/long/orange.png",    "gills/greater/orange.png"},
            {"gills/base/yellow.png",       "gills/long/yellow.png",    "gills/greater/yellow.png"},
            {"gills/base/lime.png",         "gills/long/lime.png",      "gills/greater/lime.png"},
            {"gills/base/green.png",        "gills/long/green.png",     "gills/greater/green.png"},
            {"gills/base/cyan.png",         "gills/long/cyan.png",      "gills/greater/cyan.png"},
            {"gills/base/lightblue.png",    "gills/long/lightblue.png", "gills/greater/lightblue.png"},
            {"gills/base/blue.png",         "gills/long/blue.png",      "gills/greater/blue.png"},
            {"gills/base/purple.png",       "gills/long/purple.png",    "gills/greater/purple.png"},
            {"gills/base/magenta.png",      "gills/long/magenta.png",   "gills/greater/magenta.png"}
    };

    private static final String[][][] AXOLOTL_TEXTURES_MELANIN = new String[][][] {
            {
                    {"natural_melanin.png", "natural_melaninotic.png"},
                    {"leutistic0.png", "leutistic0.png"}
            }, {
            {"copper_melanin.png", "copper_melaninotic.png"},
            {"copper_leutistic0.png", "copper_leutistic0.png"}
    }
    };

    private static final String[] AXOLOTL_TEXTURES_IRIDESCENCE = new String[] {
            "", "low_iridophores.png", "natural_iridophores.png", "high_iridophores.png"
    };

    private static final String[][][] AXOLOTL_TEXTURES_PIED = new String[][][] {
            {
                    //white belly
                    {
                            // weak
                            "spot/whitebelly/weak_splotch.png","spot/whitebelly/weak_hardspeckle.png","spot/whitebelly/weak_softspeckle.png"
                    }, {
                    // medium-weak
                    "spot/whitebelly/mediumweak_splotch.png","spot/whitebelly/mediumweak_hardspeckle.png","spot/whitebelly/mediumweak_softspeckle.png"
            }, {
                    // medium
                    "spot/whitebelly/medium_splotch.png","spot/whitebelly/medium_splotch.png","spot/whitebelly/medium_splotch.png"
            }, {
                    // medium-high
                    "spot/whitebelly/mediumhigh_splotch.png","spot/whitebelly/mediumhigh_splotch.png","spot/whitebelly/mediumhigh_splotch.png"
            },{
                    // high
                    "spot/whitebelly/high_splotch.png","spot/whitebelly/high_hardspeckle.png","spot/whitebelly/high_softspeckle.png"
            }
            },{
            //pied belly
            {
                    // weak
                    "spot/piedbelly/weak_splotch.png","spot/piedbelly/weak_hardspeckle.png","spot/piedbelly/weak_softspeckle.png"
            }, {
            // medium-weak
            "spot/piedbelly/mediumweak_splotch.png","spot/piedbelly/mediumweak_hardspeckle.png","spot/piedbelly/mediumweak_softspeckle.png"
    }, {
            // medium
            "spot/piedbelly/medium_splotch.png","spot/piedbelly/medium_hardspeckle.png","spot/piedbelly/medium_softspeckle.png"
    }, {
            // medium-high
            "spot/piedbelly/mediumhigh_splotch.png","spot/piedbelly/mediumhigh_hardspeckle.png","spot/piedbelly/mediumhigh_softspeckle.png"
    }, {
            // high
            "spot/piedbelly/high_splotch.png","spot/piedbelly/high_hardspeckle.png","spot/piedbelly/high_softspeckle.png"
    }
    },{
            //pied
            {
                    // weak
                    "spot/pied/weak_splotch.png","spot/pied/weak_hardspeckle.png","spot/pied/weak_softspeckle.png"
            }, {
            // medium-weak
            "spot/pied/mediumweak_splotch.png","spot/pied/mediumweak_hardspeckle.png","spot/pied/mediumweak_softspeckle.png"
    }, {
            // medium
            "spot/pied/medium_splotch.png","spot/pied/medium_hardspeckle.png","spot/pied/medium_softspeckle.png"
    }, {
            // medium-high
            "spot/pied/mediumhigh_splotch.png","spot/pied/mediumhigh_hardspeckle.png","spot/pied/mediumhigh_softspeckle.png"
    }, {
            // high
            "spot/pied/high_splotch.png","spot/pied/high_hardspeckle.png","spot/pied/high_softspeckle.png"
    }
    }
    };

    private static final String[] AXOLOTL_TEXTURES_BLAZE = new String[] {
            "spot/blaze/0.png", "spot/blaze/1.png", "spot/blaze/2.png", "spot/blaze/3.png", "spot/blaze/4.png", "spot/blaze/5.png", "spot/blaze/6.png", "spot/blaze/7.png", "spot/blaze/8.png"
    };

    private static final String[] CHEEK_SPOTS = new String[] {
            "cheek_spots/natural.png", "cheek_spots/white.png", "cheek_spots/lightgrey.png", "cheek_spots/grey.png", "cheek_spots/black.png", "cheek_spots/brown.png", "cheek_spots/pink.png", "cheek_spots/red.png", "cheek_spots/orange.png", "cheek_spots/yellow.png", "cheek_spots/lime.png", "cheek_spots/green.png", "cheek_spots/cyan.png", "cheek_spots/lightblue.png", "cheek_spots/blue.png", "cheek_spots/purple.png", "cheek_spots/magenta.png"
    };

    public static void calculateAxolotlTextures(EnhancedAxolotl axolotl, int[] gene) {
        Variation variation = axolotl.getVariation();
        int gills = 0;
        int gillsColour = 0;
        int gillsColour2 = 0;
        int base = 0;
        int copper = gene[6] == 1 || gene[7] == 1 ? 0 : 1;
        int pattern = 0;
        int blaze = -1;

        if (gene[34] == 2 && gene[35] == 2) {
            gills += 1;
        }

        if (gene[36] == 2 && gene[37] == 2) {
            gills += 1;
        }

        if (gene[8] == 1 || gene[9] == 1) {
            //Non-Leucistic (wildtype)
            base = gene[2] == 1 || gene[3] == 1 ? 1 : 0;
        } else if (gene[0] == 1 || gene[1] == 1) {
            //Leucistic
            pattern = 1;
        }

        int melanoid = 0;
        if (gene[4] == 2 && gene[5] == 2) {
            melanoid = 1;
        }

        int pied = 0;
        int piedStrength = 0;
        int piedSplotchy = 0;
        if (gene[12] !=1 && gene[13] != 1) {
            pied = (gene[12] + gene[13])-3;
            piedStrength = (int)((gene[14] + gene[15] - 2) * 0.3);
            if (piedStrength >= 5) {
                piedStrength = 4;
            }
            if (gene[16] >= 5 || gene[17] >= 5) {
                piedSplotchy = 2;
            } else if (gene[16] >= 3 || gene[17] >= 3) {
                piedSplotchy = 1;
            }
        }

        if (gene[38] != 1 || gene[39] != 1) {
            gillsColour = gene[40] - 1;
            gillsColour2 = gene[41] - 1;
        }

        if (gene[18] + gene[19] != 2) {
            blaze = variation.get(AXOLOTL_BLAZE[0], 9)+1;
        }

        TextureGrouping parentGroup = new TextureGrouping(TexturingType.MERGE_GROUP);

        if (gillsColour < 0) gillsColour = 0;
        if (gillsColour2 < 0) gillsColour2 = 0;
        TextureGrouping gillsGroup = new TextureGrouping(TexturingType.AVERAGE_GROUP);
        axolotl.layer(gillsGroup).textureTableSelector(AXOLOTL_TEXTURES_GILLS, gillsColour, gills).add();
        axolotl.layer(gillsGroup).textureTableSelector(AXOLOTL_TEXTURES_GILLS, gillsColour2, gills).add();
        parentGroup.addGrouping(gillsGroup);

        TextureGrouping bodyGroup = new TextureGrouping(TexturingType.MASK_GROUP);
        TextureGrouping baseAlphaGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        axolotl.layer(bodyGroup).texture("alpha_mask.png").add();
        bodyGroup.addGrouping(baseAlphaGroup);

        TextureGrouping baseDyeGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        axolotl.layer(bodyGroup).textureTableSelector(AXOLOTL_TEXTURES_BASE, base).asType(TexturingType.APPLY_DYE).add();
        bodyGroup.addGrouping(baseDyeGroup);

        if (gene[0] == 1 || gene[1] == 1) {
            TextureGrouping brindleGroup = new TextureGrouping(TexturingType.CUTOUT_GROUP);
            if (gene[42] + gene[43] != 2) {
                makeBrindleTexture(axolotl, piedStrength, brindleGroup, variation);
            } else {
                axolotl.addSkippedSlots(1);
            }

            axolotl.layer(brindleGroup).textureTableSelector(AXOLOTL_TEXTURES_MELANIN, copper, pattern, melanoid).onlyIf(gene[0] == 1 || gene[1] == 1).add();

            bodyGroup.addGrouping(brindleGroup);
        }

        TextureGrouping piedGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        if (pied < 0) pied = 0;
        boolean pieFlip = variation.get(AXOLOTL_PIED, 2) == 0;
        axolotl.layer(bodyGroup).textureTableSelector(AXOLOTL_TEXTURES_PIED, pied-1, piedStrength, piedSplotchy).onlyIf(pied != 0).flipped(pieFlip, headCubes, bodyCubes, tailCubes).add();
        boolean blazeFlip = variation.get(AXOLOTL_BLAZE[1], 2) == 0;
        axolotl.layer(piedGroup).textureTableSelector(AXOLOTL_TEXTURES_BLAZE, blaze-1).flipped(blazeFlip, headCubes).onlyIf(blaze>0);
        parentGroup.addGrouping(bodyGroup);

        if (gene[44] == 2 || gene[45] == 2) {
            TextureGrouping cheekGroup = new TextureGrouping(TexturingType.AVERAGE_GROUP);
            axolotl.layer(cheekGroup).textureTableSelector(CHEEK_SPOTS, gillsColour).add();
            axolotl.layer(cheekGroup).textureTableSelector(CHEEK_SPOTS, gillsColour2).add();
            parentGroup.addGrouping(cheekGroup);
        } else {
            axolotl.addSkippedSlots(CHEEK_SLOTS);
        }

        TextureGrouping detailsGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        axolotl.layer(detailsGroup).texture("eye_left.png").asType(TexturingType.APPLY_EYE_LEFT_COLOUR).add();
        axolotl.layer(detailsGroup).texture("eye_right.png").asType(TexturingType.APPLY_EYE_RIGHT_COLOUR).add();
        parentGroup.addGrouping(detailsGroup);

        axolotl.setTextureGrouping(parentGroup);
    }

    private static void makeBrindleTexture(EnhancedAxolotl axolotl, int piedStrength, TextureGrouping brindleGroup, Variation variation) {
        TextureGrouping brindleCutoutGroup = new TextureGrouping(TexturingType.MERGE_GROUP);

        /**
         *   head left
         *   head right
         *   body left
         *   body right
         *   tail
         */

        if (piedStrength == 1) piedStrength = 2;
        if (piedStrength == 3) piedStrength = 4;

        //Head Left
        TextureGrouping cutoutHeadLeftGroup = new TextureGrouping(TexturingType.CUTOUT_GROUP);
        int brindleVariation = variation.get(VariationKeys.AXOLOTL_BRINDLE[0], 2);
        if (brindleVariation != 0) brindleVariation = variation.get(VariationKeys.AXOLOTL_BRINDLE[1], 4)+1;
        boolean mirror = variation.get(VariationKeys.AXOLOTL_BRINDLE[1], 2) == 0;
        axolotl.layer(cutoutHeadLeftGroup).texture("brindle/"+piedStrength+"/"+brindleVariation+".png").flipped(mirror, tailCubes).keyedAs(String.valueOf(piedStrength)+brindleVariation).add();
        axolotl.layer(cutoutHeadLeftGroup).texture("brindle/head_left.png").add();

        brindleCutoutGroup.addGrouping(cutoutHeadLeftGroup);

        //Head Right
        TextureGrouping cutoutHeadRightGroup = new TextureGrouping(TexturingType.CUTOUT_GROUP);
        brindleVariation = variation.get(VariationKeys.AXOLOTL_BRINDLE[2], 2);
        if (brindleVariation != 0) brindleVariation = variation.get(VariationKeys.AXOLOTL_BRINDLE[1], 4)+1;
        mirror = variation.get(VariationKeys.AXOLOTL_BRINDLE[3], 2) == 0;
        axolotl.layer(cutoutHeadRightGroup).texture("brindle/"+piedStrength+"/"+brindleVariation+".png").flipped(mirror, tailCubes).keyedAs(String.valueOf(piedStrength)+brindleVariation+mirror).add();
        axolotl.layer(cutoutHeadRightGroup).texture("brindle/head_right.png").add();

        brindleCutoutGroup.addGrouping(cutoutHeadRightGroup);

        //Body Left
        TextureGrouping cutoutBodyLeftGroup = new TextureGrouping(TexturingType.CUTOUT_GROUP);
        brindleVariation = variation.get(VariationKeys.AXOLOTL_BRINDLE[4], 2);
        if (brindleVariation != 0) brindleVariation = variation.get(VariationKeys.AXOLOTL_BRINDLE[1], 4)+1;
        mirror = variation.get(VariationKeys.AXOLOTL_BRINDLE[5], 2) == 0;
        axolotl.layer(cutoutBodyLeftGroup).texture("brindle/"+piedStrength+"/"+brindleVariation+".png").flipped(mirror, tailCubes).keyedAs(String.valueOf(piedStrength)+brindleVariation+mirror).add();
        axolotl.layer(cutoutBodyLeftGroup).texture("brindle/body_left.png").add();

        brindleCutoutGroup.addGrouping(cutoutBodyLeftGroup);

        //Body Right
        TextureGrouping cutoutBodyRightGroup = new TextureGrouping(TexturingType.CUTOUT_GROUP);
        brindleVariation = variation.get(VariationKeys.AXOLOTL_BRINDLE[6], 2);
        if (brindleVariation != 0) brindleVariation = variation.get(VariationKeys.AXOLOTL_BRINDLE[1], 4)+1;
        mirror = variation.get(VariationKeys.AXOLOTL_BRINDLE[7], 2) == 0;
        axolotl.layer(cutoutBodyRightGroup).texture("brindle/"+piedStrength+"/"+brindleVariation+".png").flipped(mirror, tailCubes).keyedAs(String.valueOf(piedStrength)+brindleVariation+mirror).add();
        axolotl.layer(cutoutBodyRightGroup).texture("brindle/body_right.png").add();

        brindleCutoutGroup.addGrouping(cutoutBodyRightGroup);

        //Tail
        TextureGrouping cutoutTailGroup = new TextureGrouping(TexturingType.CUTOUT_GROUP);
        brindleVariation = variation.get(VariationKeys.AXOLOTL_BRINDLE[8], 2);
        if (brindleVariation != 0) brindleVariation = variation.get(VariationKeys.AXOLOTL_BRINDLE[2], 4)+1;
        mirror = variation.get(VariationKeys.AXOLOTL_BRINDLE[9], 2) == 0;
        axolotl.layer(cutoutTailGroup).texture("brindle/"+piedStrength+"/"+brindleVariation+".png").flipped(mirror, tailCubes).keyedAs(String.valueOf(piedStrength)+brindleVariation+mirror).add();
        axolotl.layer(cutoutTailGroup).texture("brindle/tail.png").add();

        brindleCutoutGroup.addGrouping(cutoutTailGroup);

        // TODO make brindle texture groups which isolate the brindle texture to the head, body, tail for the texture to be selected and flipped at random

        brindleGroup.addGrouping(brindleCutoutGroup);
    }
}
