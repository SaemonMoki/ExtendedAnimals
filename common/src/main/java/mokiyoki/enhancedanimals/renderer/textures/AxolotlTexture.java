package mokiyoki.enhancedanimals.renderer.textures;

import mokiyoki.enhancedanimals.entity.EnhancedAxolotl;
import mokiyoki.enhancedanimals.renderer.texture.TextureGrouping;
import mokiyoki.enhancedanimals.renderer.texture.TexturingType;

import java.util.concurrent.ThreadLocalRandom;

public class AxolotlTexture {

    //avalible UUID spaces : [ S 1 2 3 4 5 6 7 - 8 9 10 11 - 12 13 14 15 - 16 17 18 19 - 20 21 22 23 24 25 26 27 28 29 30 31 ]

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
                            /*weak*/
                            "spot/whitebelly/weak_splotch.png","spot/whitebelly/weak_hardspeckle.png","spot/whitebelly/weak_softspeckle.png"
                    }, {
                    /*medium-weak*/
                    "spot/whitebelly/mediumweak_splotch.png","spot/whitebelly/mediumweak_hardspeckle.png","spot/whitebelly/mediumweak_softspeckle.png"
            }, {
                    /*medium*/
                    "spot/whitebelly/medium_splotch.png","spot/whitebelly/medium_splotch.png","spot/whitebelly/medium_splotch.png"
            }, {
                    /*medium-high*/
                    "spot/whitebelly/mediumhigh_splotch.png","spot/whitebelly/mediumhigh_splotch.png","spot/whitebelly/mediumhigh_splotch.png"
            },{
                    /*high*/
                    "spot/whitebelly/high_splotch.png","spot/whitebelly/high_hardspeckle.png","spot/whitebelly/high_softspeckle.png"
            }
            },{
            //pied belly
            {
                    /*weak*/
                    "spot/piedbelly/weak_splotch.png","spot/piedbelly/weak_hardspeckle.png","spot/piedbelly/weak_softspeckle.png"
            }, {
            /*medium-weak*/
            "spot/piedbelly/mediumweak_splotch.png","spot/piedbelly/mediumweak_hardspeckle.png","spot/piedbelly/mediumweak_softspeckle.png"
    }, {
            /*medium*/
            "spot/piedbelly/medium_splotch.png","spot/piedbelly/medium_hardspeckle.png","spot/piedbelly/medium_softspeckle.png"
    }, {
            /*medium-high*/
            "spot/piedbelly/mediumhigh_splotch.png","spot/piedbelly/mediumhigh_hardspeckle.png","spot/piedbelly/mediumhigh_softspeckle.png"
    }, {
            /*high*/
            "spot/piedbelly/high_splotch.png","spot/piedbelly/high_hardspeckle.png","spot/piedbelly/high_softspeckle.png"
    }
    },{
            //pied
            {
                    /*weak*/
                    "spot/pied/weak_splotch.png","spot/pied/weak_hardspeckle.png","spot/pied/weak_softspeckle.png"
            }, {
            /*medium-weak*/
            "spot/pied/mediumweak_splotch.png","spot/pied/mediumweak_hardspeckle.png","spot/pied/mediumweak_softspeckle.png"
    }, {
            /*medium*/
            "spot/pied/medium_splotch.png","spot/pied/medium_hardspeckle.png","spot/pied/medium_softspeckle.png"
    }, {
            /*medium-high*/
            "spot/pied/mediumhigh_splotch.png","spot/pied/mediumhigh_hardspeckle.png","spot/pied/mediumhigh_softspeckle.png"
    }, {
            /*high*/
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

    private static final String[] AXOLOTL_TEXTURES_BRINDLE = new String[] {
            "brindle/0.png", "brindle/1.png", "brindle/2.png",  "brindle/3.png"
    };

    public static void calculateAxolotlTextures(EnhancedAxolotl axolotl, int[] gene) {
        char[] uuidArry = axolotl.getStringUUID().toCharArray();
        int gills = 0;
        int gillsColour = 0;
        int gillsColour2 = 0;
        int base = 0;
        int copper = gene[6] == 1 || gene[7] == 1 ? 0 : 1;
        int pattern = 0;
        int blaze = -1;
        int brindle = -1;

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
            switch (uuidArry[1]) {
                case 0,8 -> blaze = 0;
                case 1,9 -> blaze = 1;
                case 2,'a' -> blaze = 2;
                case 3,'b' -> blaze = 3;
                case 4,'c' -> blaze = 4;
                case 5,'d' -> blaze = 5;
                case 6,'e' -> blaze = 6;
                case 7 -> blaze = 7;
                case 'f' -> blaze = 8;
            }
        }

        if (gene[42] + gene[43] != 2) {
            switch (uuidArry[2]) {
                case 0,4,8,'c' -> brindle = 0;
                case 1,5,9,'d' -> brindle = 1;
                case 2,6,'a','e' -> brindle = 2;
                case 3,7,'b','f' -> brindle = 3;
            }
        }

        TextureGrouping parentGroup = new TextureGrouping(TexturingType.MERGE_GROUP);

        if (gillsColour < 0) gillsColour = 0;
        if (gillsColour2 < 0) gillsColour2 = 0;
        TextureGrouping gillsGroup = new TextureGrouping(TexturingType.AVERAGE_GROUP);
        axolotl.addTextureToAnimalTextureGrouping(gillsGroup, AXOLOTL_TEXTURES_GILLS, gillsColour, gills, true);
        axolotl.addTextureToAnimalTextureGrouping(gillsGroup, AXOLOTL_TEXTURES_GILLS, gillsColour2, gills, true);
        parentGroup.addGrouping(gillsGroup);

        TextureGrouping bodyGroup = new TextureGrouping(TexturingType.MASK_GROUP);
        TextureGrouping baseAlphaGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        axolotl.addTextureToAnimalTextureGrouping(baseAlphaGroup, "alpha_mask.png");
        bodyGroup.addGrouping(baseAlphaGroup);

        TextureGrouping baseDyeGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        axolotl.addTextureToAnimalTextureGrouping(baseDyeGroup, TexturingType.APPLY_DYE, AXOLOTL_TEXTURES_BASE, base, null);
        bodyGroup.addGrouping(baseDyeGroup);

        if (gene[0] == 1 || gene[1] == 1) {
            TextureGrouping brindleGroup = new TextureGrouping(TexturingType.CUTOUT_GROUP);
                TextureGrouping brindleCutoutGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                axolotl.addTextureToAnimalTextureGrouping(brindleCutoutGroup, AXOLOTL_TEXTURES_BRINDLE, brindle, brindle!=-1);
            brindleGroup.addGrouping(brindleCutoutGroup);
            TextureGrouping melaninGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                axolotl.addTextureToAnimalTextureGrouping(melaninGroup, AXOLOTL_TEXTURES_MELANIN, copper, pattern, melanoid, gene[0] == 1 || gene[1] == 1);
            brindleGroup.addGrouping(melaninGroup);

            bodyGroup.addGrouping(brindleGroup);
        }

        TextureGrouping piedGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        if (pied < 0) pied = 0;
        axolotl.addTextureToAnimalTextureGrouping(piedGroup, AXOLOTL_TEXTURES_PIED, pied-1, piedStrength, piedSplotchy, pied!=0);
        axolotl.addTextureToAnimalTextureGrouping(piedGroup, AXOLOTL_TEXTURES_BLAZE, blaze-1, blaze>0);
        bodyGroup.addGrouping(piedGroup);

        parentGroup.addGrouping(bodyGroup);

        if (gene[44] == 2 || gene[45] == 2) {
            TextureGrouping cheekGroup = new TextureGrouping(TexturingType.AVERAGE_GROUP);
            axolotl.addTextureToAnimalTextureGrouping(cheekGroup, CHEEK_SPOTS, gillsColour, gene[44] == 2 || gene[45] == 2);
            axolotl.addTextureToAnimalTextureGrouping(cheekGroup, CHEEK_SPOTS, gillsColour2, gene[44] == 2 || gene[45] == 2);
            parentGroup.addGrouping(cheekGroup);
        }

        TextureGrouping detailsGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        axolotl.addTextureToAnimalTextureGrouping(detailsGroup, TexturingType.APPLY_EYE_LEFT_COLOUR, "eye_left.png");
        axolotl.addTextureToAnimalTextureGrouping(detailsGroup, TexturingType.APPLY_EYE_RIGHT_COLOUR, "eye_right.png");
        parentGroup.addGrouping(detailsGroup);

        axolotl.setTextureGrouping(parentGroup);
    }
}
