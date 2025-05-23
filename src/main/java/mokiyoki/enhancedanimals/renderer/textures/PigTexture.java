package mokiyoki.enhancedanimals.renderer.textures;

import mokiyoki.enhancedanimals.entity.EnhancedPig;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import mokiyoki.enhancedanimals.renderer.texture.TextureGrouping;
import mokiyoki.enhancedanimals.renderer.texture.TexturingType;
import mokiyoki.enhancedanimals.util.Genes;

public class PigTexture {
    private static final String[] PIG_TEXTURES_SKINBASE = new String[] {
            "", "skin_pink.png", "skin_grey.png", "skin_black.png", "skin_brown.png", "skin_chocolate.png"
    };

    private static final String[] PIG_TEXTURES_SKINMARKINGS_SPOTS = new String[] {
            "", "skin_spots.png", ""
    };

    private static final String[] PIG_TEXTURES_SKINMARKINGS_WHITE = new String[] {
            "", "skin_pink.png", "skin_belt.png", "skin_patchy.png", "skin_roan.png",
            "skin_bluebuttbelt.png", "skin_bluebuttbelt", "skin_bluebuttbelt.png",
            "brindlepatch_skin_1.png", "brindlepatch_skin_2.png", "brindlepatch_skin_3.png", "brindlepatch_skin_4.png", "brindlepatch_skin_5.png",
            "brindlepatch_med_coat_1.png", "brindlepatch_med_coat_2.png", "brindlepatch_med_coat_3.png", "brindlepatch_med_coat_4.png",
            "patch_coat_1.png", "patch_coat_2.png", "patch_coat_3.png", "patch_coat_4.png", "patch_coat_5.png", "patch_coat_6.png", "patch_coat_7.png", "patch_coat_8.png", "patch_coat_9.png", "patch_coat_10.png", "patch_coat_11.png", "patch_coat_12.png",
            "spotted_patch_coat_1.png", "spotted_patch_coat_2.png", "spotted_patch_coat_3.png", "spotted_patch_coat_4.png", "spotted_patch_coat_5.png",
            "hereford_belly_small_1.png", "hereford_belly_small_2.png", "hereford_belly_small_3.png", "hereford_belly_small_4.png",
            "hereford_belly_med_1.png", "hereford_belly_med_2.png", "hereford_belly_med_3.png",
            "hereford_belly_large_1.png", "hereford_belly_large_2.png", "hereford_belly_large_3.png", "hereford_belly_large_4.png", "hereford_belly_large_5.png",
            "tux_min_1.png", "tux_min_2.png", "tux_min_3.png",
            "tux_med_1.png", "tux_med_2.png", "tux_med_3.png", "tux_med_4.png", "tux_med_5.png", "tux_med_6.png",
            "tux_high_1.png", "tux_high_2.png", "tux_high_3.png", "tux_high_4.png",
            "huge_belt_coat_1.png", "huge_belt_coat_2.png", "huge_belt_coat_3.png", "huge_belt_coat_4.png", "huge_belt_coat_5.png", "huge_belt_coat_6.png", "huge_belt_coat_7.png",
            "belt_1.png", "belt_2.png", "belt_3.png", "belt_4.png", "belt_5.png", "belt_6.png",
            "brindlepatch_extended_skin_1.png", "brindlepatch_extended_skin_2.png", "brindlepatch_extended_skin_3.png", "brindlepatch_extended_skin_4.png", "brindlepatch_extended_skin_5.png", "brindlepatch_extended_skin_6.png",
            "lethal_white_1.png", "lethal_white_2.png", "lethal_white_3.png", "lethal_white_4.png", "lethal_white_5.png",
            "patch_belt_1.png", "patch_belt_2.png",
            "patch_bigbelt_1.png", "patch_bigbelt_2.png", "patch_bigbelt_3.png", "patch_bigbelt_4.png",
            "patch_hereford_1.png", "patch_hereford_2.png", "patch_hereford_3.png", "patch_hereford_4.png", "patch_hereford_5.png",
    };

    private static final String[] PIG_TEXTURES_ROAN_RED = new String[]{
            "", "roan_red.png", "roan_red_piglet.png", "roan_het_red.png", "roan_het_red_piglet.png"
    };

    private static final String[] PIG_TEXTURES_ROAN_BLACK = new String[]{
            "", "roan_black.png", "roan_black_piglet.png", "roan_het_black.png", "roan_het_black_piglet.png"
    };

    private static final int idx_brindlepatch = 8;
    private static final int idx_brindlepatch_med = 13;
    private static final int idx_patch = 17;
    private static final int idx_spottedpatch = 29;
    private static final int idx_hereford_belly_min = 34;
    private static final int idx_hereford_belly_med = 38;
    private static final int idx_hereford_belly_high = 41;
    private static final int idx_tuxmin = 46;
    private static final int idx_tuxmed = 49;
    private static final int idx_tuxhigh = 55;
    private static final int idx_hugebelt = 59;
    private static final int idx_belt = 66;
    private static final int idx_brindlepatch_ext = 72;
    private static final int idx_lethal = 78;
    private static final int idx_patch_belt = 83;
    private static final int idx_patch_bigbelt = 85;
    private static final int idx_patch_hereford = 89;

    private static final String[] PIG_TEXTURES_COATWHITE = new String[] {
            "pigbase.png", "solid_white.png", "spot_belt.png", "spot_patchy.png", "spot_roan.png", "spot_roanbelted.png", "spot_patchyhetred.png", "spot_patchyhetsilver.png",
            "brindlepatch_coat_1.png", "brindlepatch_coat_2.png", "brindlepatch_coat_3.png", "brindlepatch_coat_4.png", "brindlepatch_coat_5.png",
            "brindlepatch_med_coat_1.png", "brindlepatch_med_coat_2.png", "brindlepatch_med_coat_3.png", "brindlepatch_med_coat_4.png",
            "patch_coat_1.png", "patch_coat_2.png", "patch_coat_3.png", "patch_coat_4.png", "patch_coat_5.png", "patch_coat_6.png", "patch_coat_7.png", "patch_coat_8.png", "patch_coat_9.png", "patch_coat_10.png", "patch_coat_11.png", "patch_coat_12.png",
            "spotted_patch_coat_1.png", "spotted_patch_coat_2.png", "spotted_patch_coat_3.png", "spotted_patch_coat_4.png", "spotted_patch_coat_5.png",
            "hereford_belly_small_1.png", "hereford_belly_small_2.png", "hereford_belly_small_3.png", "hereford_belly_small_4.png",
            "hereford_belly_med_1.png", "hereford_belly_med_2.png", "hereford_belly_med_3.png",
            "hereford_belly_large_1.png", "hereford_belly_large_2.png", "hereford_belly_large_3.png", "hereford_belly_large_4.png", "hereford_belly_large_5.png",
            "tux_min_1.png", "tux_min_2.png", "tux_min_3.png",
            "tux_med_1.png", "tux_med_2.png", "tux_med_3.png", "tux_med_4.png", "tux_med_5.png", "tux_med_6.png",
            "tux_high_1.png", "tux_high_2.png", "tux_high_3.png", "tux_high_4.png",
            "huge_belt_coat_1.png", "huge_belt_coat_2.png", "huge_belt_coat_3.png", "huge_belt_coat_4.png", "huge_belt_coat_5.png", "huge_belt_coat_6.png", "huge_belt_coat_7.png",
            "belt_1.png", "belt_2.png", "belt_3.png", "belt_4.png", "belt_5.png", "belt_6.png",
            "brindlepatch_extended_1.png", "brindlepatch_extended_2.png", "brindlepatch_extended_3.png", "brindlepatch_extended_4.png", "brindlepatch_extended_5.png", "brindlepatch_extended_6.png",
            "lethal_white_1.png", "lethal_white_2.png", "lethal_white_3.png", "lethal_white_4.png", "lethal_white_5.png",
            "patch_belt_1.png", "patch_belt_2.png",
            "patch_bigbelt_1.png", "patch_bigbelt_2.png", "patch_bigbelt_3.png", "patch_bigbelt_4.png",
            "patch_hereford_1.png", "patch_hereford_2.png", "patch_hereford_3.png", "patch_hereford_4.png", "patch_hereford_5.png",
    };

    private static final String[] PIG_TEXTURES_SKINMARKINGS_BERKSHIRE = new String[] {
            "", "skin_tux.png", "skin_berkshire.png"
    };

    private static final String[] PIG_TEXTURES_SKINBRINDLE_SPOTS = new String[] {
            "", "", "", "black_berkshirebrindle.png",
            "brindle_coat_1.png", "brindle_coat_2.png", "brindle_coat_3.png", "brindle_coat_4.png", "brindle_coat_5.png",
            "brindle_het_1.png", "brindle_het_2.png", "brindle_het_3.png", "brindle_het_4.png", "brindle_het_5.png", "brindle_het_6.png",
            "brindle_med_coat_1.png", "brindle_med_coat_2.png", "brindle_med_coat_3.png", "brindle_med_coat_4.png",
            "brindle_het_med_1.png", "brindle_het_med_2.png", "brindle_het_med_3.png", "brindle_het_med_4.png", "brindle_het_med_5.png",
            "brindle_berkshire_1.png", "brindle_berkshire_2.png", "brindle_berkshire_3.png", "brindle_berkshire_4.png", "brindle_berkshire_5.png", "brindle_berkshire_6.png", "brindle_berkshire_7.png", "brindle_berkshire_8.png",
    };
    private static final int idx_brindle = 4;
    private static final int idx_brindle_het = 9;
    private static final int idx_brindle_med = 15;
    private static final int idx_brindle_het_med = 19;
    private static final int idx_brindle_berkshire = 24;
    private static final String[] PIG_TEXTURES_COATBLACK = new String[] {
            "pigbase.png", "solid_white.png", "agouti_base.png", "black_berkshirebrindle.png",
            "brindle_coat_1.png", "brindle_coat_2.png", "brindle_coat_3.png", "brindle_coat_4.png", "brindle_coat_5.png",
            "brindle_het_1.png", "brindle_het_2.png", "brindle_het_3.png", "brindle_het_4.png", "brindle_het_5.png", "brindle_het_6.png",
            "brindle_med_coat_1.png", "brindle_med_coat_2.png", "brindle_med_coat_3.png", "brindle_med_coat_4.png",
            "brindle_het_med_1.png", "brindle_het_med_2.png", "brindle_het_med_3.png", "brindle_het_med_4.png", "brindle_het_med_5.png",
            "brindle_berkshire_1.png", "brindle_berkshire_2.png", "brindle_berkshire_3.png", "brindle_berkshire_4.png", "brindle_berkshire_5.png", "brindle_berkshire_6.png", "brindle_berkshire_7.png", "brindle_berkshire_8.png",
    };

    private static final String[] PIG_TEXTURES_SPOT_SPOTS = new String[] {
            //discontinued genes
            "", "spot_spots.png", "spot_roanspots.png"
    };

    private static final String[] PIG_TEXTURES_SPOT_BERKSHIRE = new String[] {
            "", "spot_tux.png", "spot_berkshire.png", "spot_extended_berkshire.png"
    };

    static int idx_whitepoints_min = 1;
    int idx_whitepoints_med = 7;
    static int idx_whitehead_med = 12;
    static int idx_whitehead_high = 16;
    static int idx_tuxface_min = 22;
    static int idx_tuxface_med = 25;
    static int idx_tuxface_high = 30;
    private static final String[] PIG_TEXTURES_WHITE_FACE = new String[] {
            "", "white_face_small_1.png", "white_face_small_2.png", "white_face_small_3.png", "white_face_small_4.png", "white_face_small_5.png", "white_face_small_6.png",
            "white_face_med_1.png", "white_face_med_2.png", "white_face_med_3.png", "white_face_med_4.png", "white_face_med_5.png",
            "whitehead_face_med_1.png", "whitehead_face_med_2.png", "whitehead_face_med_3.png", "whitehead_face_med_4.png",
            "whitehead_face_large_1.png", "whitehead_face_large_2.png", "whitehead_face_large_3.png", "whitehead_face_large_4.png", "whitehead_face_large_5.png", "whitehead_face_large_6.png",
            "tux_face_min_1.png", "tux_face_min_2.png", "tux_face_min_3.png",
            "tux_face_med_1.png", "tux_face_med_2.png", "tux_face_med_3.png", "tux_face_med_4.png", "tux_face_med_5.png", "tux_face_med_6.png",
            "tux_face_high_1.png", "tux_face_high_2.png", "tux_face_high_3.png", "tux_face_high_4.png",
    };

    private static final String[] PIG_TEXTURES_WHITE_TAIL = new String[] {
            "", "white_tail_min.png", "white_tail_med.png", "white_tail_max.png"
    };

    private static final String[] PIG_TEXTURES_WHITE_LEG = new String[] {
            "", "white_leg_1.png", "white_leg_2.png", "white_leg_3.png", "white_leg_4.png", "white_leg_5.png"
    };

    private static final String[] PIG_TEXTURES_COAT = new String[] {
            "coat_normal.png", "coat_wooly.png", "coat_thick_wool.png"
    };

    private static final String[] PIG_TEXTURES_AGOUTI = new String[]{
            "coat_base.png", "agouti_base.png", "agouti_base_wideband.png"
    };
    private static final String[] PIG_TEXTURES_AGOUTI_DARK = new String[]{
            "", "agouti_dark.png", "piglet_camo_dark.png"
    };

    private static final String[] PIG_TEXTURES_AGOUTI_LIGHT = new String[]{
            "", "agouti_light.png"
    };
    private static final String[] PIG_TEXTURES_EYES = new String[] {
            "eyes_black.png", "eyes_brown.png", "eyes_blue.png", "eyes_blue_left.png", "eyes_blue_right.png"
    };

    private static final String[] PIG_TEXTURES_HOOVES = new String[] {
            "hooves_black.png", "hoovesmulefoot_black.png"
    };

    private static final String[] PIG_TEXTURES_TUSKS = new String[] {
            "", "tusks.png"
    };

    private static final String[] PIG_TEXTURES_ALPHA = new String[] {
            "bald", "sparse.png", "medium.png", "furry.png", "wooly.png"
    };

    private static final String[] PIG_TEXTURES_SWALLOWBELLY = new String[] {
            "", "pattern_swallowbelly.png", "pattern_swallowbelly_wideband.png",
    };

    private static final String[] PIG_TEXTURES_WHITEBELLY = new String[] {
            "", "pattern_whitebelly.png", "pattern_whitebelly.png",
    };
    
    public static void calculatePigTexture(EnhancedPig pig, int[] gene, char[] uuidArry) {

        int eyes = 0;
        int red = 1;
        int black = 0;
        int spot = 0;
        int berk = 0;
        int coat_alpha = 0;
        int coat_texture = 0;
        int skin = 0;
        int skinBlack = 3;
        int hooves = 0;
        int swallowbelly = 0;
        int whitebelly = 0;
        int whiteFace = 0;
        int whiteTail = 0;
        int whiteLeg = 0;
        int whiteExtension = 0;
        int whiteSplash = 0;
        int white = 0;
        // if negative, tamworth; if positive, kitlg/allspots
        int spotPower = (gene[64] + gene[65]) - (gene[62] + gene[63]);
        int roan = 0;
        boolean whitePoints = false;
        boolean tusks = false;
        boolean agouti = true;
        boolean agoutiBlack = false;
        boolean wideband = gene[164] == 2 && gene[165] == 2;
        boolean brindle = false;


        //Coloration
        float maxRed = 0.020F;
        float maxYellow = 0.101F;

        float[] melanin = {0.036F, 0.5F, 0.071F};
        float[] pheomelanin = {0.049F, 0.683F, 0.558F};

        float[] swallowbellyColor = {0, 0, 0};
        //0.103F, 0.319F, 0.847F

        //float[] roanColor = {0.133F, 0.0F, 0.961F};

        //dom black
        if (gene[0] == 1 || gene[1] == 1 || gene[0] == 5 || gene[1] == 5) {
            agouti = false;
        } else if (gene[0] == 2 || gene[1] == 2) {
            agoutiBlack = true;
        }

        if ((gene[2] == 3 || gene[3] == 3) && (gene[2] == gene[3] || gene[2] == 4 || gene[3] == 4)) {
            //Either nonagouti or het nonagouti/het swallowbelly, since nonagouti is more dominant
            agouti = false;
        }


        if (agouti) {
            if (gene[2] == 2 || gene[3] == 2) {
                //legacy brown agouti
                melanin[0] = 0.033F;
                melanin[1] = 0.517F;
                melanin[2] = 0.114F;
            } else if (gene[0] == 2 || gene[1] == 2) {
                pheomelanin[0] += 0.021F;
                pheomelanin[1] -= 0.07F;
                melanin[0] += 0.016F;
                melanin[1] -= 0.035F;
            }
        }

//            if (gene[12] == 1 && gene[13] == 1) {
//            	pheomelanin[0] = 0.10F;
//            	pheomelanin[1] = 0.02F;
//            	pheomelanin[2] = 0.96F;
//            }
        //brindle
        if (gene[0] == 3 && gene[1] == 3) {
            // allspots
            if (spotPower == 2) {
                pheomelanin[0] = 0.086F;
                pheomelanin[1] = 0.100F;
                pheomelanin[2] = 0.90F;
            } else if (spotPower == 1) {
                pheomelanin[0] = 0.086F;
                pheomelanin[1] = 0.534F;
                pheomelanin[2] = 0.69F;
            }
        }
        if (gene[12] == 4 && gene[13] == 4) {
            //legacy patch
            pheomelanin[0] = 0.133F;
            pheomelanin[1] = 0.02F;
            pheomelanin[2] = 0.961F;
        } else if ((gene[12] == 4 || gene[13] == 4) && (gene[12] == 3 || gene[13] == 3)) {
            // legacy patch / wildtype
            pheomelanin[0] = 0.086F;
            pheomelanin[1] = 0.534F;
            pheomelanin[2] = 0.69F;
        }

        // chinchilla dilute
        if (gene[4] == 1 || gene[5] == 1) {
            melanin[0] += 0.003F;
            melanin[1] += 0.034F;
            melanin[2] += 0.092F;
            pheomelanin[0] += 0.034F;
            pheomelanin[1] += -0.555F;
            pheomelanin[2] += 0.385F;
        }

        //blonde
        if (gene[158] == 2 && gene[159] == 2) {
            pheomelanin[0] += 0.0458F;
            pheomelanin[1] += -0.381F;
            pheomelanin[2] += 0.539F;
        } else if (gene[158] == 2 || gene[159] == 2) {
            pheomelanin[0] += 0.029F;
            pheomelanin[1] += -0.109F;
            pheomelanin[2] += 0.120F;
        }

        // subtle dilute
        if (gene[6] == 2 && gene[7] == 2) {
            melanin[0] += 0.003F;
            melanin[1] += 0.015F;
            melanin[2] += 0.045F;
            pheomelanin[0] += 0.032F;
            pheomelanin[1] += -0.129F;
            pheomelanin[2] += 0.120F;
        }

        // silver-brown
        if (gene[8] == 2 && gene[9] == 2) {
            melanin[0] += 0.002F;
            melanin[1] += -0.355F;
            melanin[2] += 0.675F;
        } else if (gene[8] == 2 || gene[9] == 2) {
            melanin[0] += 0.001F;
            melanin[1] += -0.245F;
            melanin[2] += 0.555F;
            //chocolate
        } else if (gene[8] == 3 && gene[9] == 3) {
            melanin[0] += 0.002F;
            melanin[1] += 0.084F;
            melanin[2] += 0.245F;
        }

        int s = 0;

        for (int i = 192; i < 202; i++) {
            if (gene[i] == 2) {
                s -= 1;
            }
        }

        //wideband increases saturation
        if (wideband) {
            s += 2;
        }

        int r = 0;
        for (int i = 120; i < 148; i++) {
            if (gene[i] == 2) {
                r = i < 134 ? r - 1 : r + 1;
            }
        }

        int darkness = 0;
        for (int i = 150; i < 158; i++) {
            if (gene[i] == 2) {
                darkness += 1;
            }
        }

        if (s != 0) {
            if (s < 0) {
                maxYellow += (0.001F * s);
            }
            pheomelanin[1] += (0.0075F * s);
            melanin[1] += (0.0025F * s);
        }

        if (r != 0) {
            pheomelanin[0] += (0.0029F * -r);
            pheomelanin[2] += (0.008F * -r);
            melanin[0] += (0.0025F * -r);
            melanin[1] += (0.009F * r);
        }

        if (darkness != 0) {
            melanin[2] -= (0.01F * darkness);
            pheomelanin[0] -= (0.002F * darkness);
            pheomelanin[1] += (0.0015F * darkness);
            pheomelanin[2] -= (0.03F * darkness);
            maxYellow -= (0.0021F * darkness);
        }

        if (wideband) {
            swallowbellyColor[0] = pheomelanin[0] + 0.01F;
            swallowbellyColor[1] = pheomelanin[1] - 0.02F;
            swallowbellyColor[2] = pheomelanin[2] + 0.09F;
        } else {
            swallowbellyColor[0] = pheomelanin[0] + 0.02F;
            swallowbellyColor[1] = pheomelanin[1] - 0.15F;
            swallowbellyColor[2] = pheomelanin[2] + 0.23F;
        }

        float[] lightAgoutiColor = {pheomelanin[0], pheomelanin[1], pheomelanin[2] + 0.25F};
        float[] darkAgoutiColor = {melanin[0], melanin[1], melanin[2] - 0.1F};
        float[] darkAgoutiRedColor = {pheomelanin[0], pheomelanin[1] + 0.04F, pheomelanin[2] - 0.25F};

        if (gene[8] == 2 && gene[9] == 2) {
            darkAgoutiColor[2] -= 0.4F;
        } else if (gene[8] == 2 || gene[9] == 2) {
            darkAgoutiColor[2] -= 0.3F;
        }

        if (pig.isBaby()) {
            if (agoutiBlack && (gene[158] == 2 || gene[159] == 2)) { //Blonde piglets are paled out
                melanin[1] -= 0.5F;
                melanin[2] += 0.5F;
                swallowbellyColor[1] -= 0.5F;
            }
        }
        //float[] lightAgoutiColor = {0, 0, 1};
        //float[] darkAgoutiColor = {(melanin[0]*0.2F)+(pheomelanin[0]*0.8F), (r*0.02F)+melanin[1]+0.1F, melanin[2]-0.40F};


        if (agouti) {
            if (gene[2] == 4 && gene[3] == 4) {
                //swallowbelly
            } else if (gene[0] == 2 || gene[1] == 2) {
                //wildtype Extension
                if (wideband) {
                    //wideband
                    for (int i = 0; i <= 2; i++) {
                        melanin[i] = (pheomelanin[i]);
                        if (gene[158] == 1 || gene[159] == 1) {
                            //not blonde
                            darkAgoutiRedColor[i] = (darkAgoutiRedColor[i] * 0.5F) + (darkAgoutiColor[i] * 0.5F);
                        }
                    }
                } else {
                    //non wideband
                    melanin[0] = (0.6F * melanin[0]) + (0.4F * pheomelanin[0]);
                    melanin[1] = (melanin[1] + pheomelanin[1]) / 2.0F;
                    melanin[2] = (melanin[2] + pheomelanin[2]) / 2.0F;
                }
            }
        }

        //check hue range
        if (pheomelanin[0] > maxYellow) {
            pheomelanin[0] = maxYellow;
        } else if (pheomelanin[0] < maxRed) {
            pheomelanin[0] = maxRed;
        }

        if (melanin[0] > maxYellow) {
            melanin[0] = maxYellow;
        } else if (melanin[0] < maxRed) {
            melanin[0] = maxRed;
        }

        if (swallowbellyColor[0] > maxYellow) {
            swallowbellyColor[0] = maxYellow;
        } else if (swallowbellyColor[0] < maxRed) {
            swallowbellyColor[0] = maxRed;
        }

        if (darkAgoutiColor[0] > maxYellow) {
            darkAgoutiColor[0] = maxYellow;
        } else if (darkAgoutiColor[0] < maxRed) {
            darkAgoutiColor[0] = maxRed;
        }

        if (lightAgoutiColor[0] > maxYellow) {
            lightAgoutiColor[0] = maxYellow;
        } else if (lightAgoutiColor[0] < maxRed) {
            lightAgoutiColor[0] = maxRed;
        }

        if (darkAgoutiRedColor[0] > maxYellow) {
            darkAgoutiRedColor[0] = maxYellow;
        } else if (darkAgoutiRedColor[0] < maxRed) {
            darkAgoutiRedColor[0] = maxRed;
        }

        //checks that numbers are within the valid range
        for (int i = 0; i <= 2; i++) {
            if (melanin[i] > 1.0F) {
                melanin[i] = 1.0F;
            } else if (melanin[i] < 0.0F) {
                melanin[i] = 0.0F;
            }
            if (pheomelanin[i] > 1.0F) {
                pheomelanin[i] = 1.0F;
            } else if (pheomelanin[i] < 0.0F) {
                pheomelanin[i] = 0.0F;
            }
            if (swallowbellyColor[i] > 1.0F) {
                swallowbellyColor[i] = 1.0F;
            } else if (swallowbellyColor[i] < 0.0F) {
                swallowbellyColor[i] = 0.0F;
            }
            if (lightAgoutiColor[i] > 1.0F) {
                lightAgoutiColor[i] = 1.0F;
            } else if (lightAgoutiColor[i] < 0.0F) {
                lightAgoutiColor[i] = 0.0F;
            }
            if (darkAgoutiColor[i] > 1.0F) {
                darkAgoutiColor[i] = 1.0F;
            } else if (darkAgoutiColor[i] < 0.0F) {
                darkAgoutiColor[i] = 0.0F;
            }
            if (darkAgoutiRedColor[i] > 1.0F) {
                darkAgoutiRedColor[i] = 1.0F;
            } else if (darkAgoutiRedColor[i] < 0.0F) {
                darkAgoutiRedColor[i] = 0.0F;
            }
        }

        int pheomelaninRGB = Colouration.HSBtoABGR(pheomelanin[0], pheomelanin[1], pheomelanin[2]);
        int melaninRGB = Colouration.HSBtoABGR(melanin[0], melanin[1], melanin[2]);

        int swallowbellyRGB = Colouration.HSBtoARGB(swallowbellyColor[0], swallowbellyColor[1], swallowbellyColor[2]);
        int lightAgoutiRGB = Colouration.HSBtoARGB(lightAgoutiColor[0], lightAgoutiColor[1], lightAgoutiColor[2]);
        int darkAgoutiRGB = Colouration.HSBtoARGB(darkAgoutiColor[0], darkAgoutiColor[1], darkAgoutiColor[2]);
        int darkAgoutiRedRGB = Colouration.HSBtoARGB(darkAgoutiRedColor[0], darkAgoutiRedColor[1], darkAgoutiRedColor[2]);

//            //roan
//            if (gene[12] == 5 || gene[13] == 5) {
//                roan = true;
//                int roanColor = Colouration.HSBtoABGR(0.133F, 0.02F, 0.961F);
//                melaninRGB = Colouration.mixABGR(melaninRGB, roanColor, 0.6F);
//                pheomelaninRGB = Colouration.mixABGR(pheomelaninRGB, roanColor, 0.8F);
//                darkAgoutiRedRGB = Colouration.mixABGR(darkAgoutiRedRGB, roanColor, 0.8F);
//                darkAgoutiRGB = Colouration.mixABGR(darkAgoutiRGB, roanColor, 0.6F);
//                swallowbellyRGB = Colouration.mixABGR(swallowbellyRGB, roanColor, 0.75F);
//                lightAgoutiRGB = Colouration.mixABGR(lightAgoutiRGB, roanColor, 0.75F);
//            }
        //switch from ABRG to ARGB
//            lightAgoutiRGB = Colouration.getABGRFromARGB(lightAgoutiRGB);
//            swallowbellyRGB = Colouration.getABGRFromARGB(swallowbellyRGB);
//            darkAgoutiRGB = Colouration.getABGRFromARGB(darkAgoutiRGB);
//            darkAgoutiRedRGB = Colouration.getABGRFromARGB(darkAgoutiRedRGB);


        pig.colouration.setMelaninColour(melaninRGB);
        pig.colouration.setPheomelaninColour(pheomelaninRGB);

        //textures

        //white extension
        if (gene[16] == 1 || gene[17] == 1) {
            whiteExtension = 0;
        } else if (gene[16] == 2 || gene[17] == 2) {
            whiteExtension = 1;
        } else if (gene[16] == 3 && gene[17] == 3) {
            whiteExtension = 2;
        }
        // check white points locus first since it affects some white patterns
        if ((gene[14] == 2 || gene[15] == 2) && (gene[14] != 1 && gene[15] != 1)) {
            berk = 1;
        } else if (gene[14] == 3 || gene[15] == 3) {
            //white points
            if (gene[14] == 3 && gene[15] == 3) {
                whiteExtension += 1;
            }
            whitePoints = true;
            whiteFace = idx_whitepoints_min;
        }

        if (whiteExtension > 2) {
            whiteExtension = 2;
        }

        if (gene[0] == 1 || gene[1] == 1 || gene[0] == 5 || gene[1] == 5) {
            //solid black
            black = 1;
        } else if (gene[0] == 2 || gene[1] == 2) {
            //black = 2;
            black = 1;
        } else if (gene[0] == 3 || gene[1] == 3) {
            //brindle
            brindle = true;
            if (gene[0] == 3 && gene[1] == 3) {
                //homozygous brindle
                black = idx_brindle;
                if (spotPower == 2) {
                    //berkshire spots
                    black = idx_brindle_berkshire;
                } else if (spotPower == 1) {
                    // het allspots
                    black = idx_brindle_med;
                } else if (spotPower < 0) {
                    // tamworth
                    black = 0;
                }
            } else {
                //heterozygous brindle
                //minimal spots
                black = idx_brindle_het;
                if (spotPower == 2) {
                    // berkshire spots
                    black = idx_brindle_het_med;
                } else if (spotPower == 1) {
                    // het allspots
                    black = idx_brindle_het_med;
                } else if (spotPower < 0) {
                    // tamworth
                    black = 0;
                }
            }
        } else if (gene[0] == 4 && gene[1] == 4) {
            black = 0;
        }

        //A locus
        if (gene[2] == 5 || gene[3] == 5) {
            //whitebelly
            if (wideband) {
                whitebelly = 2;
            } else {
                whitebelly = 1;
            }
        } else if (gene[2] == 1 || gene[3] == 1) {
            // agouti
        } else if (gene[2] == 2 || gene[3] == 2) {
        } else if (gene[2] == 3 || gene[3] == 3) {
            // non-agouti
            red = 0;
            agouti = false;
        } else if (gene[2] == 4 && gene[3] == 4 && agouti) {
            //swallowbelly
            if (wideband) {
                swallowbelly = 2;
            } else {
                swallowbelly = 1;
            }
        }

        if (agouti && wideband) {
            red = 2;
        }

        if (gene[10] != 1 && gene[11] != 1) {
            if (gene[10] == 2 || gene[11] == 2) {
                //spotted
                spot = 1;
            } else {
                //roan spotted
                spot = 2;
            }
        }

        //MITF
        if (gene[190] == 2 || gene[191] == 2) {
            //splash aka hereford
            if (gene[190] == gene[191]) {
                //homozygous
                switch (whiteExtension) {
                    case 0 -> {
                        whiteSplash = idx_hereford_belly_min;
                    }
                    case 1 -> {
                        whiteSplash = idx_hereford_belly_med;
                    }
                    case 2 -> {
                        whiteSplash = idx_hereford_belly_high;
                    }
                }
            } else {
                //het
                whiteFace = idx_whitepoints_min;
            }
        }

        //I Locus
        if (gene[12] == 6 || gene[13] == 6) {
            //dom white
            white = 1;
        } else if ((gene[12] == 1 || gene[13] == 1) && (gene[12] != 3 && gene[13] != 3)) {
            //legacy dom white
            white = 1;
        } else if (gene[12] == 3 && gene[13] == 3) {
            //wildtype
            white = 0;
        } else if (gene[12] == 12 || gene[13] == 12) {
            //lethal
            white = idx_lethal;
        } else if (gene[12] == 11 || gene[13] == 11) {
            //patch
            //patch seems to override the partial extension spotting pattern when present
            if (black == idx_brindle_berkshire) {
                // i dont think allspots patch does anything different
            }
            else if (black == idx_brindle_med) {
                //het allspots patch
                black = 1;
                white = idx_brindlepatch_med;
            }
            else if (black == idx_brindle) {
                //plain partial extension/patch aka pietrain
                black = 1;
                if (whiteExtension >= 2) {
                    white = idx_brindlepatch_ext;
                } else {
                    white = idx_brindlepatch;
                }
            }
            else if (whiteSplash != 0) {
                //patch + splash/hereford
                white = idx_patch_hereford;
                whiteSplash = 0;
            } else if (gene[12] == 2 || gene[13] == 2) {
                //het belt
                white = idx_patch_belt;
            } else if (gene[12] == 9 || gene[13] == 9) {
                //het belt 2
                white = idx_patch_bigbelt;
            } else if (whitePoints && (gene[12] == 10 || gene[13] == 10)) {
                //het tuxedo with white points - produces solid white
                //if white points is not present, assume patch takes priority due to higher white
                white = 1;
            } else if (whitePoints) {
                if (whiteExtension == 2) {
                    white = 1;
                } else {
                    white = idx_spottedpatch;
                }
            } else {
                white = idx_patch;
            }
        } else if (gene[12] == 9 || gene[13] == 9) {
            //irregular belted
            white = idx_hugebelt;
        } else if (gene[12] == 2 || gene[13] == 2) {
            //belted
            white = idx_belt;
        }
        //white spots 2 aka tux
        else if (gene[12] == 10 || gene[13] == 10) {
            //produce tuxedo when combined with white points
            if (whitePoints) {
                white = idx_tuxmed;
            } else {
                white = idx_tuxmin;
            }
            whiteFace = idx_tuxface_min;
        }

        //roan
        if (gene[12] == 5 || gene[13] == 5) {
            roan = gene[12] == gene[13] ? 1 : 3;
        }

        // skin color

        if (gene[12] == 4 && gene[13] == 4) {
            skinBlack = 1;
        }
        if (gene[12] == 5 || gene[13] == 5) {
            //roan causes grey skin
            skinBlack = 2;
        } else if (gene[4] == 1 || gene[5] == 1 || gene[8] == 2 || gene[9] == 2 || (gene[8] == 3 && gene[9] == 3)) {
            //chinchilla, chocolate and silver-brown dilute skin
            skinBlack = 5;
        }

        if (white == 1) {
            //pink
            skin = 1;
        } else if (gene[0] == 4 && gene[1] == 4) {
            //rec.red causes brownish skin
            skin = 4;
        } else if (black == 1 || black == 2) {
            //black skin
            skin = skinBlack;
        } else if (black == idx_brindle_berkshire) {
            skin = 1;
        } else {
            //grey
            skin = 2;
        }
        if (gene[62] == 2 || gene[63] == 2) { //tamworth causes pink skin, even on black pigs
            skin = 1;
            if (spotPower < 0) {
                skinBlack = 1;
            }
        }

        //random brindle
        if (black == idx_brindle) {
            int d = uuidArry[3] % 5;
            black = idx_brindle + d;
        }
        //random big brindle
        else if (black == idx_brindle_med) {
            int d = uuidArry[3] % 4;
            black = idx_brindle_med + d;
        }
        //random big brindle
        else if (black == idx_brindle_berkshire) {
            int d = uuidArry[3] % 8;
            black = idx_brindle_berkshire + d;
        }
        //random het brindle
        else if (black == idx_brindle_het) {
            int d = uuidArry[3] % 6;
            black = idx_brindle_het + d;
        }
        //random big het brindle
        else if (black == idx_brindle_het_med) {
            int d = uuidArry[3] % 5;
            black = idx_brindle_het_med + d;
        }
        //random patch + white points aka spotted
        else if (white == idx_spottedpatch) {
            whiteFace = 0;
            int d = uuidArry[3] % 5;
            white = idx_spottedpatch + d;
        }
        //random patch
        else if (white == idx_patch) {
            int d = uuidArry[3] % 11;
            white = idx_patch + d;
        }
        //random patch brindle (pietrain)
        else if (white == idx_brindlepatch) {
            int d = uuidArry[3] % 5;
            white = idx_brindlepatch + d;
        }
        //random patch brindle (oldspot aka extended white)
        else if (white == idx_brindlepatch_ext) {
            int d = uuidArry[3] % 6;
            white = idx_brindlepatch_ext + d;
        }
        //random patch big brindle
        else if (white == idx_brindlepatch_med) {
            int d = uuidArry[3] % 4;
            white = idx_brindlepatch_med + d;
        }
        //random big/irregular belt
        else if (white == idx_hugebelt) {
            int d = uuidArry[3] % 7;
            white = idx_hugebelt + d;
        }
        //random lethal
        else if (white == idx_lethal) {
            int d = uuidArry[3] % 5;
            white = idx_lethal + d;
        }
        //random min tux
        else if (white == idx_tuxmin) {
            if (whiteExtension == 2) {
                int d3 = uuidArry[3] % 6;
                int d2 = uuidArry[2] % 6;
                white = idx_tuxmed + d3;
                whiteFace = idx_tuxface_med + d2;
            } else {
                int d3 = uuidArry[3] % 3;
                int d2 = uuidArry[2] % 3;
                white = idx_tuxmin + d3;
                whiteFace = idx_tuxface_min + d2;
            }
        }
        // random med-high tux
        else if (white == idx_tuxmed) {
            if (whiteExtension == 2) {
                int d3 = uuidArry[3] % 4;
                int d2 = uuidArry[2] % 4;
                white = idx_tuxhigh + d3;
                whiteFace = idx_tuxface_high + d2;
            } else {
                int d3 = uuidArry[3] % 6;
                int d2 = uuidArry[2] % 6;
                white = idx_tuxmed + d3;
                whiteFace = idx_tuxface_med + d2;
            }
        } else if (white == idx_belt) {
            //random belt
            if (whiteExtension == 2) {
                int d = uuidArry[3] % 3;
                white = idx_belt + 3 + d;
            } else if (whiteExtension == 1) {
                int d = uuidArry[3] % 3;
                white = idx_belt + 2 + d;
            } else {
                int d = uuidArry[3] % 3;
                white = idx_belt + d;
            }
        } else if (white == idx_patch_belt) {
            //random patch belt
            int d3 = uuidArry[3] % 2;
            white = idx_patch_belt + d3;
        } else if (white == idx_patch_bigbelt) {
            //random patch bigbelt
            int d3 = uuidArry[3] % 4;
            white = idx_patch_bigbelt + d3;
        } else if (white == idx_patch_hereford) {
            //random patch hereford
            int d3 = uuidArry[3] % 5;
            white = idx_patch_hereford + d3;
        }

        if (whiteSplash == idx_hereford_belly_min) {
            //random min hereford
            int d4 = uuidArry[5] % 2;
            int d3 = uuidArry[3] % 4;
            int d2 = uuidArry[2] % 4;
            whiteFace = idx_whitehead_med + d2;
            whiteSplash = idx_hereford_belly_min + d3;
            whiteLeg = 2 + d4;
            whiteTail = 1 + d4;
        } else if (whiteSplash == idx_hereford_belly_med) {
            //random med hereford
            int d4 = uuidArry[5] % 2;
            int d3 = uuidArry[3] % 3;
            int d2 = uuidArry[2] % 4;
            whiteFace = idx_whitehead_med + d2;
            whiteSplash = idx_hereford_belly_med + d3;
            whiteLeg = 5;
            whiteTail = 1 + d4;
        } else if (whiteSplash == idx_hereford_belly_high) {
            //random high hereford
            int d4 = uuidArry[5] % 2;
            int d3 = uuidArry[3] % 5;
            int d2 = uuidArry[2] % 6;
            whiteFace = idx_whitehead_high + d2;
            whiteSplash = idx_hereford_belly_high + d3;
            whiteLeg = 5;
            whiteTail = 1 + d4;
        }
        //random white points
        if (whiteFace == 1) {
            int d2 = uuidArry[2];
            int d4 = uuidArry[5] % 2;
            //int d2 = uuidArry[3];
            switch (whiteExtension) {
                case 0 -> {
                    whiteFace = 1 + (d2 % 4);
                    whiteLeg = 1 + (d2 % 2);
                    whiteTail = 1;
                }
                case 1 -> {
                    whiteFace = 5 + (d2 % 5);
                    whiteLeg = 2 + (d2 % 2);
                    whiteTail = 1 + d4;
                }
                case 2 -> {
                    whiteFace = 7 + (d2 % 5);
                    whiteLeg = 2 + (d2 % 3);
                    whiteTail = 1 + d4;
                }
            }
        }

        //heterochromia
        int heterochromia = 0;
        if (gene[160] == 2 && gene[161] == 2) {
            heterochromia = 2;
        } else if (gene[160] == 2 || gene[161] == 2) {
            heterochromia = 1;
        }

        if (heterochromia == 2 || white == 1 || gene[12] == 1 || gene[13] == 1) {
            eyes = 2;
        } else if (heterochromia == 1) {
            int d4 = uuidArry[4] % 2;
            eyes = (d4 == 1) ? 3 : 4;
        }

        if (!pig.isBaby()) {
            if ((Character.isLetter(uuidArry[0]) || uuidArry[0] - 48 >= 8)) {
                //tusks if "male"
                tusks = true;
            }
        }

        if (gene[60] == 2 && gene[61] == 2) {
            hooves = 1;
        }

        //black agouti and swallowbelly only show up if there's any agouti
        agoutiBlack = agoutiBlack && agouti;

        TextureGrouping parentGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        TextureGrouping skinGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        TextureGrouping hairGroup = new TextureGrouping(TexturingType.MASK_GROUP); //MASK_GROUP

        pig.addTextureToAnimalTextureGrouping(skinGroup, PIG_TEXTURES_SKINBASE, skin, true);
        pig.addTextureToAnimalTextureGrouping(skinGroup, PIG_TEXTURES_SKINMARKINGS_SPOTS, spot, (spot == 1));

        //addTextureToAnimalTextureGrouping(skinGroup, PIG_TEXTURES_SKIN_BRINDLEPATCH, white, p -> p > 1);

        //addTextureToAnimalTextureGrouping(skinGroup, PIG_TEXTURES_SKINMARKINGS_WHITE, white, white != 0);


        //addTextureToAnimalTextureGrouping(skinGroup, PIG_TEXTURES_SKINMARKINGS_BERKSHIRE, berk, b -> b != 0);


        parentGroup.addGrouping(skinGroup);

        if (black != 0) {
            TextureGrouping blackSkinGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
            TextureGrouping blackSkinAlpha = new TextureGrouping(TexturingType.MASK_GROUP);
            pig.addTextureToAnimalTextureGrouping(blackSkinAlpha, PIG_TEXTURES_SKINBRINDLE_SPOTS, black, black != 0);
            pig.addTextureToAnimalTextureGrouping(blackSkinAlpha, PIG_TEXTURES_SKINBASE, skinBlack, black != 0);
            blackSkinGroup.addGrouping(blackSkinAlpha);
            parentGroup.addGrouping(blackSkinGroup);
        }

        if (whiteFace != 0 || white != 0 || berk != 0 || whiteSplash != 0) {
            TextureGrouping whiteSkinGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
            TextureGrouping whiteSkinAlpha = new TextureGrouping(TexturingType.MASK_GROUP); //MASK_GROUP
            TextureGrouping whiteSkinMask = new TextureGrouping(TexturingType.MERGE_GROUP);
            pig.addTextureToAnimalTextureGrouping(whiteSkinMask, PIG_TEXTURES_WHITE_FACE, whiteFace, b -> b != 0);
            pig.addTextureToAnimalTextureGrouping(whiteSkinMask, PIG_TEXTURES_WHITE_LEG, whiteLeg, b -> b != 0);
            pig.addTextureToAnimalTextureGrouping(whiteSkinMask, PIG_TEXTURES_WHITE_TAIL, whiteTail, b -> b != 0);
            pig.addTextureToAnimalTextureGrouping(whiteSkinMask, PIG_TEXTURES_SKINMARKINGS_WHITE, white, white != 0);
            pig.addTextureToAnimalTextureGrouping(whiteSkinMask, PIG_TEXTURES_SKINMARKINGS_WHITE, whiteSplash, whiteSplash != 0);
            pig.addTextureToAnimalTextureGrouping(whiteSkinMask, PIG_TEXTURES_SKINMARKINGS_BERKSHIRE, berk, berk != 0);
            whiteSkinAlpha.addGrouping(whiteSkinMask);
            TextureGrouping whSkinTex = new TextureGrouping(TexturingType.MERGE_GROUP);
            pig.addTextureToAnimalTextureGrouping(whSkinTex, PIG_TEXTURES_SKINBASE, 1, true);
            whiteSkinAlpha.addGrouping(whSkinTex);
            whiteSkinGroup.addGrouping(whiteSkinAlpha);
            parentGroup.addGrouping(whiteSkinGroup);
        }

        if (gene[36] != 1 || gene[37] != 1) {
            if ((gene[34] == 1 || gene[35] == 1) && (gene[34] != 3 && gene[35] != 3)) {
                //furry
                coat_alpha = 3;
            } else if (gene[34] == 2 || gene[35] == 2) {
                //normal
                coat_alpha = 2;
            } else {
                //sparse
                coat_alpha = 1;
            }

            if (gene[38] == 3 || gene[39] == 3) {
                coat_alpha = 4;
                coat_texture = 2;
            } else if (gene[38] == 1 || gene[39] == 1) {
                coat_alpha = coat_alpha + 1;
                coat_texture = 1;
            }
        }
        if (gene[36] != 1 || gene[37] != 1) {
            boolean baby = pig.isBaby();
            int darkAgouti = baby ? 2 : 1;

            TextureGrouping hairAlphaGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
            pig.addTextureToAnimalTextureGrouping(hairAlphaGroup, PIG_TEXTURES_ALPHA, coat_alpha, coat_alpha != 0);
            TextureGrouping hairTexGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
            TextureGrouping redGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
            pig.addTextureToAnimalTextureGrouping(redGroup, TexturingType.APPLY_RED, PIG_TEXTURES_AGOUTI, red, l -> true);
            if ((baby && agouti && black == 0)) {
                pig.addTextureToAnimalTextureGrouping(redGroup, TexturingType.APPLY_RGB, PIG_TEXTURES_AGOUTI_DARK[darkAgouti], "ag-rd", darkAgoutiRedRGB);
            }
            pig.addTextureToAnimalTextureGrouping(redGroup, PIG_TEXTURES_ROAN_RED, baby ? roan + 1 : roan, roan != 0);
            hairTexGroup.addGrouping(redGroup);

            TextureGrouping swallowbellyGroup = new TextureGrouping(TexturingType.MASK_GROUP);
            TextureGrouping whitebellyGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
            if (whitebelly != 0) {
                pig.addTextureToAnimalTextureGrouping(whitebellyGroup, PIG_TEXTURES_WHITEBELLY, whitebelly, l -> true);
            } else if (swallowbelly != 0) {
                pig.addTextureToAnimalTextureGrouping(swallowbellyGroup, PIG_TEXTURES_SWALLOWBELLY, swallowbelly, l -> true);
                pig.addTextureToAnimalTextureGrouping(swallowbellyGroup, TexturingType.APPLY_RGB, PIG_TEXTURES_AGOUTI[0], "sb", swallowbellyRGB);
                pig.addTextureToAnimalTextureGrouping(swallowbellyGroup, PIG_TEXTURES_ROAN_RED, baby ? roan + 1 : roan, roan != 0);
            }

            int agoutiTex = 0;
            if (!agoutiBlack) {
                hairTexGroup.addGrouping(swallowbellyGroup);
                hairTexGroup.addGrouping(whitebellyGroup);
                if (brindle && agouti) {
                    agoutiTex = wideband ? 2 : 1;
                }
            } else {
                agoutiTex = wideband ? 2 : 1;
            }

            if (black != 0) {
                TextureGrouping blackGroup = new TextureGrouping(TexturingType.MASK_GROUP);
                TextureGrouping blackAlphaGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                pig.addTextureToAnimalTextureGrouping(blackAlphaGroup, PIG_TEXTURES_COATBLACK, black, l -> l != 0);
                TextureGrouping blackTexGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                pig.addTextureToAnimalTextureGrouping(blackTexGroup, TexturingType.APPLY_BLACK, PIG_TEXTURES_AGOUTI, agoutiTex, l -> true);
                if (agoutiBlack && (swallowbelly == 0 || baby)) {
                    if (!wideband || baby) { // Dark agouti
                        int camoColor = (wideband && swallowbelly == 0) ? darkAgoutiRedRGB : darkAgoutiRGB;
                        pig.addTextureToAnimalTextureGrouping(blackTexGroup, TexturingType.APPLY_RGB, PIG_TEXTURES_AGOUTI_DARK[darkAgouti], "ag-d", camoColor);
                    }
                    if (!baby) { // Light agouti layer
                        pig.addTextureToAnimalTextureGrouping(blackTexGroup, TexturingType.APPLY_RGB, PIG_TEXTURES_AGOUTI_LIGHT[1], "ag-l", lightAgoutiRGB);
                    }
                }
                pig.addTextureToAnimalTextureGrouping(blackTexGroup, PIG_TEXTURES_ROAN_BLACK, baby ? roan + 1 : roan, roan != 0 && !brindle);
                blackGroup.addGrouping(blackAlphaGroup);
                blackGroup.addGrouping(blackTexGroup);
                hairTexGroup.addGrouping(blackGroup);
            }

            if (agoutiBlack) {
                hairTexGroup.addGrouping(swallowbellyGroup);
                hairTexGroup.addGrouping(whitebellyGroup);
            }

            if (whiteFace != 0 || white != 0 || berk != 0 || whiteSplash != 0) {
                TextureGrouping whiteGroup = new TextureGrouping(TexturingType.MASK_GROUP);
                TextureGrouping whiteAlphaGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                pig.addTextureToAnimalTextureGrouping(whiteAlphaGroup, PIG_TEXTURES_COATWHITE, white, p -> p != 0);
                pig.addTextureToAnimalTextureGrouping(whiteAlphaGroup, PIG_TEXTURES_WHITE_FACE, whiteFace, p -> p != 0);
                pig.addTextureToAnimalTextureGrouping(whiteAlphaGroup, PIG_TEXTURES_WHITE_LEG, whiteLeg, p -> p != 0);
                pig.addTextureToAnimalTextureGrouping(whiteAlphaGroup, PIG_TEXTURES_WHITE_TAIL, whiteTail, p -> p != 0);
                pig.addTextureToAnimalTextureGrouping(whiteAlphaGroup, PIG_TEXTURES_COATWHITE, whiteSplash, p -> p != 0);
                pig.addTextureToAnimalTextureGrouping(whiteAlphaGroup, PIG_TEXTURES_SPOT_BERKSHIRE, berk, p -> p != 0);
                TextureGrouping whiteTextureGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                pig.addTextureToAnimalTextureGrouping(whiteTextureGroup, PIG_TEXTURES_COATWHITE, 1, p -> p != 0);
                whiteGroup.addGrouping(whiteAlphaGroup);
                whiteGroup.addGrouping(whiteTextureGroup);
                hairTexGroup.addGrouping(whiteGroup);
            }
            TextureGrouping overlayGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
            pig.addTextureToAnimalTextureGrouping(overlayGroup, PIG_TEXTURES_COAT, coat_texture, true);

            hairTexGroup.addGrouping(overlayGroup);

            //addTextureToAnimalTextureGrouping(hairTexGroup, PIG_TEXTURES_SPOT_SPOTS, spot, (spot != 0));
            hairGroup.addGrouping(hairAlphaGroup);
            hairGroup.addGrouping(hairTexGroup);

                /*if (belt != 0) {
                    if (genesForText[12] == 1 || genesForText[13] == 1) {
                        belt = 1;
                    }
                    addTextureToAnimalTextureGrouping(hairGroup, PIG_TEXTURES_SPOT_BELTED, belt, true);
                }*/

            //addTextureToAnimalTextureGrouping(hairGroup, PIG_TEXTURES_WHITEHEAD_BELLY, whitePointsBelly, (whitePointsBelly != 0));

            parentGroup.addGrouping(hairGroup);
        }

        pig.addTextureToAnimalTextureGrouping(parentGroup, PIG_TEXTURES_EYES, eyes, true);
        pig.addTextureToAnimalTextureGrouping(parentGroup, PIG_TEXTURES_HOOVES, hooves, true);
        if (tusks) {
            pig.addTextureToAnimalTextureGrouping(parentGroup, PIG_TEXTURES_TUSKS, tusks ? 1 : 0, tusks);
        }
//            addTextureToAnimal("pigbase.png");
        pig.setTextureGrouping(parentGroup);
    }
}
