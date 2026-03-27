package mokiyoki.enhancedanimals.renderer.textures;

import mokiyoki.enhancedanimals.entity.EnhancedAxolotl;
import mokiyoki.enhancedanimals.renderer.texture.TextureGrouping;
import mokiyoki.enhancedanimals.renderer.texture.TexturingType;

public class AxolotlTexture {
    private static final String[] AXOLOTL_TEXTURES_BASE = new String[] {
            "natural.png", "natural_xanthic.png", "highgold_xanthic.png"
    };

    private static final String[][] AXOLOTL_TEXTURES_GILLS = new String[][] {
            {"gills_base.png", "gillslong_base.png", "gillsgreater_base.png"},
            {"gills_base_white.png", "gillslong_base_white.png", "gillsgreater_base_white.png"},
            {"gills_base_lightgrey.png", "gillslong_base_lightgrey.png", "gillsgreater_base_lightgrey.png"},
            {"gills_base_grey.png", "gillslong_base_grey.png", "gillsgreater_base_grey.png"},
            {"gills_base_black.png", "gillslong_base_black.png", "gillsgreater_base_black.png"},
            {"gills_base_brown.png", "gillslong_base_brown.png", "gillsgreater_base_brown.png"},
            {"gills_base_pink.png", "gillslong_base_pink.png", "gillsgreater_base_pink.png"},
            {"gills_base_red.png", "gillslong_base_red.png", "gillsgreater_base_red.png"},
            {"gills_base_orange.png", "gillslong_base_orange.png", "gillsgreater_base_orange.png"},
            {"gills_base_yellow.png", "gillslong_base_yellow.png", "gillsgreater_base_yellow.png"},
            {"gills_base_lime.png", "gillslong_base_lime.png", "gillsgreater_base_lime.png"},
            {"gills_base_green.png", "gillslong_base_green.png", "gillsgreater_base_green.png"},
            {"gills_base_cyan.png", "gillslong_base_cyan.png", "gillsgreater_base_cyan.png"},
            {"gills_base_lightblue.png", "gillslong_base_lightblue.png", "gillsgreater_base_lightblue.png"},
            {"gills_base_blue.png", "gillslong_base_blue.png", "gillsgreater_base_blue.png"},
            {"gills_base_purple.png", "gillslong_base_purple.png", "gillsgreater_base_purple.png"},
            {"gills_base_magenta.png", "gillslong_base_magenta.png", "gillsgreater_base_magenta.png"}
    };

    private static final String[] AXOLOTL_TEXTURES_XANTHIN = new String[] {
            "", "low_xanthophores.png", "natural_xanthophores.png", "high_xanthophores.png"
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

    private static final String[][][] AXOLOTL_TEXTURES_BERKSHIRE = new String[][][] {
            {
                    {"star.png", "snip.png"},
                    {"blaze1.png"},
                    {"blaze2.png"},
                    {"blaze3.png"},
                    {"blaze4.png"},
                    {"blaze5.png"},
                    {"blaze6.png"},
                    {"blaze7.png"},
                    {"blaze8.png"},
                    {"baldface9.png"},
            }, {
                    {"berkshire0.png"},
                    {"berkshire1.png"},
                    {"berkshire2.png"},
                    {"berkshire3.png"},
                    {"berkshire4.png"},
                    {"berkshire5.png"},
                    {"berkshire6.png"},
                    {"berkshire7.png"},
                    {"berkshire8.png"},
                    {"berkshire9.png"},
            }
    };

    private static final String[] CHEEK_SPOTS = new String[] {
            "cheeks.png", "cheeks_white.png", "cheeks_lightgrey.png", "cheeks_grey.png", "cheeks_black.png", "cheeks_brown.png", "cheeks_pink.png", "cheeks_red.png", "cheeks_orange.png", "cheeks_yellow.png", "cheeks_lime.png", "cheeks_green.png", "cheeks_cyan.png", "cheeks_lightblue.png", "cheeks_blue.png", "cheeks_purple.png", "cheeks_magenta.png",
    };

    public static void calculateAxolotlTextures(EnhancedAxolotl axolotl, int[] gene) {
        int gills = 0;
        int gillsColour = 0;
        int gillsColour2 = 0;
        int base = 0;
        int copper = gene[6] == 1 || gene[7] == 1 ? 0 : 1;
        int pattern = 0;

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

        TextureGrouping parentGroup = new TextureGrouping(TexturingType.MERGE_GROUP);

        if (gillsColour < 0) gillsColour = 0;
        if (gillsColour2 < 0) gillsColour2 = 0;
        TextureGrouping gillsGroup = new TextureGrouping(TexturingType.AVERAGE_GROUP);
        axolotl.addTextureToAnimalTextureGrouping(gillsGroup, AXOLOTL_TEXTURES_GILLS, gillsColour, gills, true);
        axolotl.addTextureToAnimalTextureGrouping(gillsGroup, AXOLOTL_TEXTURES_GILLS, gillsColour2, gills, true);
        parentGroup.addGrouping(gillsGroup);

        TextureGrouping bodyGroup = new TextureGrouping(TexturingType.MASK_GROUP);
        axolotl.addTextureToAnimalTextureGrouping(bodyGroup, "alpha_mask.png");
        axolotl.addTextureToAnimalTextureGrouping(bodyGroup, TexturingType.APPLY_DYE, AXOLOTL_TEXTURES_BASE, base, null);
        axolotl.addTextureToAnimalTextureGrouping(bodyGroup, AXOLOTL_TEXTURES_MELANIN, copper, pattern, melanoid, gene[0] == 1 || gene[1] == 1);
        if (pied < 0) pied = 0;
        axolotl.addTextureToAnimalTextureGrouping(bodyGroup, AXOLOTL_TEXTURES_PIED, pied-1, piedStrength, piedSplotchy, pied!=0);
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
