package mokiyoki.enhancedanimals.renderer.textures;

import mokiyoki.enhancedanimals.entity.EnhancedPig;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import mokiyoki.enhancedanimals.renderer.texture.Mask;
import mokiyoki.enhancedanimals.renderer.texture.TextureBuilder;
import mokiyoki.enhancedanimals.renderer.texture.TextureGroup;

import static mokiyoki.enhancedanimals.entity.util.VariationKeys.*;

public class PigTexture {
    // red, swallowbelly, whitebelly, black, white, coat, coat alpha
    private static final int[] headCubes = new int[] {
        1, 69, 15, 49, 15, 1, 1, 1, //eyes
        0, 49, 0, 7, 6, 7, //head
        0, 49, 13, 8, 5, 4, //cheeks
        0, 49, 22, 4, 6, 3, //snout
        0, 63, 22, 2, 6, 1, //jaw
        1, 46, 0, 70, 0, 4, 6, 1, //ears
        0, 0, 0, 9, 8, 9, //neck
        1, 64, 57, 64, 63, 2, 4, 2, //waddles
    };

    private static final int[] bodyCubes = new int[] {
        0, 0, 23, 10, 15, 10, //body
        0, 0, 53, 9, 8, 9, //butt
        0, 36, 0, 1, 2, 1, //tail0
        0, 36, 3, 1, 2, 1, //tail1
        0, 36, 6, 1, 2, 1, //tail2
        0, 36, 7, 1, 2, 1, //tail3
        0, 36, 12, 1, 2, 1, //tail4
        0, 36, 15, 1, 2, 1, //tail5
    };

    private static final int[] legCubes = new int[] {
        1, 49, 32, 61, 32, 3, 8, 3, //legF
        1, 49, 44, 61, 44, 3, 6, 3, //legB
    };

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
    
    public static void calculatePigTexture(EnhancedPig pig, int[] gene) {
        buildPigTexture(TextureBuilder.of(pig, gene), pig.colouration);
    }

    static void buildPigTexture(TextureBuilder pigTexture, Colouration colouration) {

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
        int spotPower = (pigTexture.gene(64) + pigTexture.gene(65)) - (pigTexture.gene(62) + pigTexture.gene(63));
        int roan = 0;
        boolean whitePoints = false;
        boolean tusks = false;
        boolean agouti = true;
        boolean agoutiBlack = false;
        boolean wideband = pigTexture.gene(164) == 2 && pigTexture.gene(165) == 2;
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
        if (pigTexture.eitherAllelesHave(0, 1) || pigTexture.eitherAllelesHave(0, 5)) {
            agouti = false;
        } else if (pigTexture.eitherAllelesHave(0, 2)) {
            agoutiBlack = true;
        }

        if (pigTexture.eitherAllelesHave(2, 3) && (pigTexture.gene(2) == pigTexture.gene(3) || pigTexture.gene(2) == 4 || pigTexture.gene(3) == 4)) {
            //Either nonagouti or het nonagouti/het swallowbelly, since nonagouti is more dominant
            agouti = false;
        }


        if (agouti) {
            if (pigTexture.eitherAllelesHave(2, 2)) {
                //legacy brown agouti
                melanin[0] = 0.033F;
                melanin[1] = 0.517F;
                melanin[2] = 0.114F;
            } else if (pigTexture.eitherAllelesHave(0, 2)) {
                pheomelanin[0] += 0.021F;
                pheomelanin[1] -= 0.07F;
                melanin[0] += 0.016F;
                melanin[1] -= 0.035F;
            }
        }

//            if (pigTexture.isHomoFor(12, 1)) {
//            	pheomelanin[0] = 0.10F;
//            	pheomelanin[1] = 0.02F;
//            	pheomelanin[2] = 0.96F;
//            }
        //brindle
        if (pigTexture.isHomoFor(0, 3)) {
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
        if (pigTexture.isHomoFor(12, 4)) {
            //legacy patch
            pheomelanin[0] = 0.133F;
            pheomelanin[1] = 0.02F;
            pheomelanin[2] = 0.961F;
        } else if (pigTexture.eitherAllelesHave(12, 4) && pigTexture.eitherAllelesHave(12, 3)) {
            // legacy patch / wildtype
            pheomelanin[0] = 0.086F;
            pheomelanin[1] = 0.534F;
            pheomelanin[2] = 0.69F;
        }

        // chinchilla dilute
        if (pigTexture.eitherAllelesHave(4, 1)) {
            melanin[0] += 0.003F;
            melanin[1] += 0.034F;
            melanin[2] += 0.092F;
            pheomelanin[0] += 0.034F;
            pheomelanin[1] += -0.555F;
            pheomelanin[2] += 0.385F;
        }

        //blonde
        if (pigTexture.isHomoFor(158, 2)) {
            pheomelanin[0] += 0.0458F;
            pheomelanin[1] += -0.381F;
            pheomelanin[2] += 0.539F;
        } else if (pigTexture.eitherAllelesHave(158, 2)) {
            pheomelanin[0] += 0.029F;
            pheomelanin[1] += -0.109F;
            pheomelanin[2] += 0.120F;
        }

        // subtle dilute
        if (pigTexture.isHomoFor(6, 2)) {
            melanin[0] += 0.003F;
            melanin[1] += 0.015F;
            melanin[2] += 0.045F;
            pheomelanin[0] += 0.032F;
            pheomelanin[1] += -0.129F;
            pheomelanin[2] += 0.120F;
        }

        // silver-brown
        if (pigTexture.isHomoFor(8, 2)) {
            melanin[0] += 0.002F;
            melanin[1] += -0.355F;
            melanin[2] += 0.675F;
        } else if (pigTexture.eitherAllelesHave(8, 2)) {
            melanin[0] += 0.001F;
            melanin[1] += -0.245F;
            melanin[2] += 0.555F;
            //chocolate
        } else if (pigTexture.isHomoFor(8, 3)) {
            melanin[0] += 0.002F;
            melanin[1] += 0.084F;
            melanin[2] += 0.245F;
        }

        int s = 0;

        for (int i = 192; i < 202; i++) {
            if (pigTexture.gene(i) == 2) {
                s -= 1;
            }
        }

        //wideband increases saturation
        if (wideband) {
            s += 2;
        }

        int r = 0;
        for (int i = 120; i < 148; i++) {
            if (pigTexture.gene(i) == 2) {
                r = i < 134 ? r - 1 : r + 1;
            }
        }

        int darkness = 0;
        for (int i = 150; i < 158; i++) {
            if (pigTexture.gene(i) == 2) {
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

        if (pigTexture.isHomoFor(8, 2)) {
            darkAgoutiColor[2] -= 0.4F;
        } else if (pigTexture.eitherAllelesHave(8, 2)) {
            darkAgoutiColor[2] -= 0.3F;
        }

        if (pigTexture.ifIsBaby()) {
            if (agoutiBlack && pigTexture.eitherAllelesHave(158, 2)) { //Blonde piglets are paled out
                melanin[1] -= 0.5F;
                melanin[2] += 0.5F;
                swallowbellyColor[1] -= 0.5F;
            }
        }
        //float[] lightAgoutiColor = {0, 0, 1};
        //float[] darkAgoutiColor = {(melanin[0]*0.2F)+(pheomelanin[0]*0.8F), (r*0.02F)+melanin[1]+0.1F, melanin[2]-0.40F};


        if (agouti) {
            if (pigTexture.isHomoFor(2, 4)) {
                //swallowbelly
            } else if (pigTexture.eitherAllelesHave(0, 2)) {
                //wildtype Extension
                if (wideband) {
                    //wideband
                    for (int i = 0; i <= 2; i++) {
                        melanin[i] = (pheomelanin[i]);
                        if (pigTexture.eitherAllelesHave(158, 1)) {
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

        colouration.setMelaninColour(melaninRGB);
        colouration.setPheomelaninColour(pheomelaninRGB);

        //textures

        //white extension
        if (pigTexture.eitherAllelesHave(16, 1)) {
            whiteExtension = 0;
        } else if (pigTexture.eitherAllelesHave(16, 2)) {
            whiteExtension = 1;
        } else if (pigTexture.isHomoFor(16, 3)) {
            whiteExtension = 2;
        }
        // check white points locus first since it affects some white patterns
        if (pigTexture.eitherAllelesHave(14, 2) && !pigTexture.eitherAllelesHave(14, 1)) {
            berk = 1;
        } else if (pigTexture.eitherAllelesHave(14, 3)) {
            //white points
            if (pigTexture.isHomoFor(14, 3)) {
                whiteExtension += 1;
            }
            whitePoints = true;
            whiteFace = idx_whitepoints_min;
        }

        if (whiteExtension > 2) {
            whiteExtension = 2;
        }

        if (pigTexture.eitherAllelesHave(0, 1) || pigTexture.eitherAllelesHave(0, 5)) {
            //solid black
            black = 1;
        } else if (pigTexture.eitherAllelesHave(0, 2)) {
            //black = 2;
            black = 1;
        } else if (pigTexture.eitherAllelesHave(0, 3)) {
            //brindle
            brindle = true;
            if (pigTexture.isHomoFor(0, 3)) {
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
        } else if (pigTexture.isHomoFor(0, 4)) {
            black = 0;
        }

        //A locus
        if (pigTexture.eitherAllelesHave(2, 5)) {
            //whitebelly
            if (wideband) {
                whitebelly = 2;
            } else {
                whitebelly = 1;
            }
        } else if (pigTexture.eitherAllelesHave(2, 1)) {
            // agouti
        } else if (pigTexture.eitherAllelesHave(2, 2)) {
        } else if (pigTexture.eitherAllelesHave(2, 3)) {
            // non-agouti
            red = 0;
            agouti = false;
        } else if (pigTexture.gene(2) == 4 && pigTexture.gene(3) == 4 && agouti) {
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

        if (!pigTexture.eitherAllelesHave(10, 1)) {
            if (pigTexture.eitherAllelesHave(10, 2)) {
                //spotted
                spot = 1;
            } else {
                //roan spotted
                spot = 2;
            }
        }

        //MITF
        if (pigTexture.eitherAllelesHave(190, 2)) {
            //splash aka hereford
            if (pigTexture.gene(190) == pigTexture.gene(191)) {
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
        if (pigTexture.eitherAllelesHave(12, 6)) {
            //dom white
            white = 1;
        } else if (pigTexture.eitherAllelesHave(12, 1) && !pigTexture.eitherAllelesHave(12, 3)) {
            //legacy dom white
            white = 1;
        } else if (pigTexture.isHomoFor(12, 3)) {
            //wildtype
            white = 0;
        } else if (pigTexture.eitherAllelesHave(12, 12)) {
            //lethal
            white = idx_lethal;
        } else if (pigTexture.eitherAllelesHave(12, 11)) {
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
            } else if (pigTexture.eitherAllelesHave(12, 2)) {
                //het belt
                white = idx_patch_belt;
            } else if (pigTexture.eitherAllelesHave(12, 9)) {
                //het belt 2
                white = idx_patch_bigbelt;
            } else if (whitePoints && pigTexture.eitherAllelesHave(12, 10)) {
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
        } else if (pigTexture.eitherAllelesHave(12, 9)) {
            //irregular belted
            white = idx_hugebelt;
        } else if (pigTexture.eitherAllelesHave(12, 2)) {
            //belted
            white = idx_belt;
        }
        //white spots 2 aka tux
        else if (pigTexture.eitherAllelesHave(12, 10)) {
            //produce tuxedo when combined with white points
            if (whitePoints) {
                white = idx_tuxmed;
            } else {
                white = idx_tuxmin;
            }
            whiteFace = idx_tuxface_min;
        }

        //roan
        if (pigTexture.eitherAllelesHave(12, 5)) {
            roan = pigTexture.gene(12) == pigTexture.gene(13) ? 1 : 3;
        }

        // skin color

        if (pigTexture.isHomoFor(12, 4)) {
            skinBlack = 1;
        }
        if (pigTexture.eitherAllelesHave(12, 5)) {
            //roan causes grey skin
            skinBlack = 2;
        } else if (pigTexture.gene(4) == 1 || pigTexture.gene(5) == 1 || pigTexture.gene(8) == 2 || pigTexture.gene(9) == 2 || pigTexture.isHomoFor(8, 3)) {
            //chinchilla, chocolate and silver-brown dilute skin
            skinBlack = 5;
        }

        if (white == 1) {
            //pink
            skin = 1;
        } else if (pigTexture.isHomoFor(0, 4)) {
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
        if (pigTexture.eitherAllelesHave(62, 2)) { //tamworth causes pink skin, even on black pigs
            skin = 1;
            if (spotPower < 0) {
                skinBlack = 1;
            }
        }

        //random brindle
        if (black == idx_brindle) {
            int d = pigTexture.randomBoundedInt(PIG_PATTERN, 5);
            black = idx_brindle + d;
        }
        //random big brindle
        else if (black == idx_brindle_med) {
            int d = pigTexture.randomBoundedInt(PIG_PATTERN, 4);
            black = idx_brindle_med + d;
        }
        //random big brindle
        else if (black == idx_brindle_berkshire) {
            int d = pigTexture.randomBoundedInt(PIG_PATTERN, 8);
            black = idx_brindle_berkshire + d;
        }
        //random het brindle
        else if (black == idx_brindle_het) {
            int d = pigTexture.randomBoundedInt(PIG_PATTERN, 6);
            black = idx_brindle_het + d;
        }
        //random big het brindle
        else if (black == idx_brindle_het_med) {
            int d = pigTexture.randomBoundedInt(PIG_PATTERN, 5);
            black = idx_brindle_het_med + d;
        }
        //random patch + white points aka spotted
        else if (white == idx_spottedpatch) {
            whiteFace = 0;
            int d = pigTexture.randomBoundedInt(PIG_PATTERN, 5);
            white = idx_spottedpatch + d;
        }
        //random patch
        else if (white == idx_patch) {
            int d = pigTexture.randomBoundedInt(PIG_PATTERN, 11);
            white = idx_patch + d;
        }
        //random patch brindle (pietrain)
        else if (white == idx_brindlepatch) {
            int d = pigTexture.randomBoundedInt(PIG_PATTERN, 5);
            white = idx_brindlepatch + d;
        }
        //random patch brindle (oldspot aka extended white)
        else if (white == idx_brindlepatch_ext) {
            int d = pigTexture.randomBoundedInt(PIG_PATTERN, 6);
            white = idx_brindlepatch_ext + d;
        }
        //random patch big brindle
        else if (white == idx_brindlepatch_med) {
            int d = pigTexture.randomBoundedInt(PIG_PATTERN, 4);
            white = idx_brindlepatch_med + d;
        }
        //random big/irregular belt
        else if (white == idx_hugebelt) {
            int d = pigTexture.randomBoundedInt(PIG_PATTERN, 7);
            white = idx_hugebelt + d;
        }
        //random lethal
        else if (white == idx_lethal) {
            int d = pigTexture.randomBoundedInt(PIG_PATTERN, 5);
            white = idx_lethal + d;
        }
        //random min tux
        else if (white == idx_tuxmin) {
            if (whiteExtension == 2) {
                int d3 = pigTexture.randomBoundedInt(PIG_PATTERN, 6);
                int d2 = pigTexture.randomBoundedInt(PIG_WHITE_SPOT, 6);
                white = idx_tuxmed + d3;
                whiteFace = idx_tuxface_med + d2;
            } else {
                int d3 = pigTexture.randomBoundedInt(PIG_PATTERN, 3);
                int d2 = pigTexture.randomBoundedInt(PIG_WHITE_SPOT, 3);
                white = idx_tuxmin + d3;
                whiteFace = idx_tuxface_min + d2;
            }
        }
        // random med-high tux
        else if (white == idx_tuxmed) {
            if (whiteExtension == 2) {
                int d3 = pigTexture.randomBoundedInt(PIG_PATTERN, 4);
                int d2 = pigTexture.randomBoundedInt(PIG_WHITE_SPOT, 4);
                white = idx_tuxhigh + d3;
                whiteFace = idx_tuxface_high + d2;
            } else {
                int d3 = pigTexture.randomBoundedInt(PIG_PATTERN, 6);
                int d2 = pigTexture.randomBoundedInt(PIG_WHITE_SPOT, 6);
                white = idx_tuxmed + d3;
                whiteFace = idx_tuxface_med + d2;
            }
        } else if (white == idx_belt) {
            //random belt
            if (whiteExtension == 2) {
                int d = pigTexture.randomBoundedInt(PIG_PATTERN, 3);
                white = idx_belt + 3 + d;
            } else if (whiteExtension == 1) {
                int d = pigTexture.randomBoundedInt(PIG_PATTERN, 3);
                white = idx_belt + 2 + d;
            } else {
                int d = pigTexture.randomBoundedInt(PIG_PATTERN, 3);
                white = idx_belt + d;
            }
        } else if (white == idx_patch_belt) {
            //random patch belt
            int d3 = pigTexture.randomBoundedInt(PIG_PATTERN, 2);
            white = idx_patch_belt + d3;
        } else if (white == idx_patch_bigbelt) {
            //random patch bigbelt
            int d3 = pigTexture.randomBoundedInt(PIG_PATTERN, 4);
            white = idx_patch_bigbelt + d3;
        } else if (white == idx_patch_hereford) {
            //random patch hereford
            int d3 = pigTexture.randomBoundedInt(PIG_PATTERN, 5);
            white = idx_patch_hereford + d3;
        }

        if (whiteSplash == idx_hereford_belly_min) {
            //random min hereford
            int d4 = pigTexture.randomBoundedInt(PIG_WHITE_POINTS, 2);
            int d3 = pigTexture.randomBoundedInt(PIG_PATTERN, 4);
            int d2 = pigTexture.randomBoundedInt(PIG_WHITE_SPOT, 4);
            whiteFace = idx_whitehead_med + d2;
            whiteSplash = idx_hereford_belly_min + d3;
            whiteLeg = 2 + d4;
            whiteTail = 1 + d4;
        } else if (whiteSplash == idx_hereford_belly_med) {
            //random med hereford
            int d4 = pigTexture.randomBoundedInt(PIG_WHITE_POINTS, 2);
            int d3 = pigTexture.randomBoundedInt(PIG_PATTERN, 3);
            int d2 = pigTexture.randomBoundedInt(PIG_WHITE_SPOT, 4);
            whiteFace = idx_whitehead_med + d2;
            whiteSplash = idx_hereford_belly_med + d3;
            whiteLeg = 5;
            whiteTail = 1 + d4;
        } else if (whiteSplash == idx_hereford_belly_high) {
            //random high hereford
            int d4 = pigTexture.randomBoundedInt(PIG_WHITE_POINTS, 2);
            int d3 = pigTexture.randomBoundedInt(PIG_PATTERN, 5);
            int d2 = pigTexture.randomBoundedInt(PIG_WHITE_SPOT, 6);
            whiteFace = idx_whitehead_high + d2;
            whiteSplash = idx_hereford_belly_high + d3;
            whiteLeg = 5;
            whiteTail = 1 + d4;
        }
        //random white points
        if (whiteFace == 1) {
            int d4 = pigTexture.randomBoundedInt(PIG_WHITE_POINTS, 2);
            switch (whiteExtension) {
                case 0 -> {
                    whiteFace = 1 + pigTexture.randomBoundedInt(PIG_WHITE_SPOT, 4);
                    whiteLeg = 1 + pigTexture.randomBoundedInt(PIG_WHITE_SPOT, 2);
                    whiteTail = 1;
                }
                case 1 -> {
                    whiteFace = 5 + pigTexture.randomBoundedInt(PIG_WHITE_SPOT, 5);
                    whiteLeg = 2 + pigTexture.randomBoundedInt(PIG_WHITE_SPOT, 2);
                    whiteTail = 1 + d4;
                }
                case 2 -> {
                    whiteFace = 7 + pigTexture.randomBoundedInt(PIG_WHITE_SPOT, 5);
                    whiteLeg = 2 + pigTexture.randomBoundedInt(PIG_WHITE_SPOT, 3);
                    whiteTail = 1 + d4;
                }
            }
        }

        //heterochromia
        int heterochromia = 0;
        if (pigTexture.isHomoFor(160, 2)) {
            heterochromia = 2;
        } else if (pigTexture.eitherAllelesHave(160, 2)) {
            heterochromia = 1;
        }

        if (heterochromia == 2 || white == 1 || pigTexture.gene(12) == 1 || pigTexture.gene(13) == 1) {
            eyes = 2;
        } else if (heterochromia == 1) {
            int d4 = pigTexture.randomBoundedInt(PIG_HETEROCHROMIA, 2);
            eyes = (d4 == 1) ? 3 : 4;
        }

        if (!pigTexture.ifIsBaby()) {
            if (pigTexture.sexVariation() >= 8) {
                //tusks if "male"
                tusks = true;
            }
        }

        if (pigTexture.isHomoFor(60, 2)) {
            hooves = 1;
        }

        //black agouti and swallowbelly only show up if there's any agouti
        agoutiBlack = agoutiBlack && agouti;

        boolean flipWhiteSpots = pigTexture.randomChance(PIG_WHITE_SPOT_FLIP, 50);

        TextureGroup root = pigTexture.root();

        TextureGroup skinGroup = root.addMergeGroup(PIG_TEXTURES_SKINBASE[skin]);
        skinGroup.addTextureIf(spot == 1, PIG_TEXTURES_SKINMARKINGS_SPOTS[spot]);

        if (black != 0) {
            Mask blackSkinAlpha = root.addMaskGroup();
            blackSkinAlpha.maskTexture().addTexture(PIG_TEXTURES_SKINBRINDLE_SPOTS[black]);
            blackSkinAlpha.content().addTexture(PIG_TEXTURES_SKINBASE[skinBlack]);
        }

        boolean hasWhite = whiteFace != 0 || white != 0 || berk != 0 || whiteSplash != 0;
        if (hasWhite) {
            Mask whiteSkinAlpha = root.addMaskGroup();
            TextureGroup whiteSkinMask = whiteSkinAlpha.maskTexture();
            whiteSkinMask.addTextureIf(whiteFace != 0, PIG_TEXTURES_WHITE_FACE[whiteFace]);
            whiteSkinMask.addTextureIf(whiteLeg != 0, PIG_TEXTURES_WHITE_LEG[whiteLeg]);
            whiteSkinMask.addTextureIf(whiteTail != 0, PIG_TEXTURES_WHITE_TAIL[whiteTail]);
            whiteSkinMask.addTextureIf(white != 0, PIG_TEXTURES_SKINMARKINGS_WHITE[white]);
            whiteSkinMask.addTextureIf(whiteSplash != 0, PIG_TEXTURES_SKINMARKINGS_WHITE[whiteSplash]);
            whiteSkinMask.addTextureIf(berk != 0, PIG_TEXTURES_SKINMARKINGS_BERKSHIRE[berk]);
            whiteSkinAlpha.content().addTexture(PIG_TEXTURES_SKINBASE[1]);
        }

        if (!pigTexture.isHomoFor(36, 1)) {
            if (pigTexture.eitherAllelesHave(34, 1) && !pigTexture.eitherAllelesHave(34, 3)) {
                //furry
                coat_alpha = 3;
            } else if (pigTexture.eitherAllelesHave(34, 2)) {
                //normal
                coat_alpha = 2;
            } else {
                //sparse
                coat_alpha = 1;
            }

            if (pigTexture.eitherAllelesHave(38, 3)) {
                coat_alpha = 4;
                coat_texture = 2;
            } else if (pigTexture.eitherAllelesHave(38, 1)) {
                coat_alpha = coat_alpha + 1;
                coat_texture = 1;
            }
        }
        if (!pigTexture.isHomoFor(36, 1)) {
            boolean baby = pigTexture.ifIsBaby();
            int darkAgouti = baby ? 2 : 1;
            int roanTexture = baby ? roan + 1 : roan;

            Mask hairGroup = root.addMaskGroup();
            hairGroup.maskTexture().addTextureIf(coat_alpha != 0, PIG_TEXTURES_ALPHA[coat_alpha]);
            TextureGroup hairTexGroup = hairGroup.content();

            TextureGroup redGroup = hairTexGroup.addMergeGroup();
            redGroup.addTexture(PIG_TEXTURES_AGOUTI[red]).tintRed();
            redGroup.addTextureIf(baby && agouti && black == 0, PIG_TEXTURES_AGOUTI_DARK[darkAgouti]).rgb(darkAgoutiRedRGB);
            redGroup.addTextureIf(roan != 0, PIG_TEXTURES_ROAN_RED[roanTexture]);

            int agoutiTex = 0;
            if (!agoutiBlack) {
                addSwallowbelly(hairTexGroup, whitebelly, swallowbelly, swallowbellyRGB, roan, roanTexture);
                if (brindle && agouti) {
                    agoutiTex = wideband ? 2 : 1;
                }
            } else {
                agoutiTex = wideband ? 2 : 1;
            }

            if (black != 0) {
                Mask blackGroup = hairTexGroup.addMaskGroup();
                blackGroup.maskTexture().addTexture(PIG_TEXTURES_COATBLACK[black]);
                TextureGroup blackTexGroup = blackGroup.content();
                blackTexGroup.addTexture(PIG_TEXTURES_AGOUTI[agoutiTex]).tintBlack();
                if (agoutiBlack && (swallowbelly == 0 || baby)) {
                    if (!wideband || baby) { // Dark agouti
                        int camoColor = (wideband && swallowbelly == 0) ? darkAgoutiRedRGB : darkAgoutiRGB;
                        blackTexGroup.addTexture(PIG_TEXTURES_AGOUTI_DARK[darkAgouti]).rgb(camoColor);
                    }
                    if (!baby) { // Light agouti layer
                        blackTexGroup.addTexture(PIG_TEXTURES_AGOUTI_LIGHT[1]).rgb(lightAgoutiRGB);
                    }
                }
                blackTexGroup.addTextureIf(roan != 0 && !brindle, PIG_TEXTURES_ROAN_BLACK[roanTexture]);
            }

            if (agoutiBlack) {
                addSwallowbelly(hairTexGroup, whitebelly, swallowbelly, swallowbellyRGB, roan, roanTexture);
            }

            if (hasWhite) {
                Mask whiteGroup = hairTexGroup.addMaskGroup();
                TextureGroup whiteAlphaGroup = whiteGroup.maskTexture();
                whiteAlphaGroup.addTextureIf(white != 0, PIG_TEXTURES_COATWHITE[white]);
                whiteAlphaGroup.addTextureIf(whiteFace != 0, PIG_TEXTURES_WHITE_FACE[whiteFace]);
                whiteAlphaGroup.addTextureIf(whiteLeg != 0, PIG_TEXTURES_WHITE_LEG[whiteLeg]);
                whiteAlphaGroup.addTextureIf(whiteTail != 0, PIG_TEXTURES_WHITE_TAIL[whiteTail]);
                whiteAlphaGroup.addTextureIf(whiteSplash != 0, PIG_TEXTURES_COATWHITE[whiteSplash]);
                whiteAlphaGroup.addTextureIf(berk != 0, PIG_TEXTURES_SPOT_BERKSHIRE[berk]);
                whiteGroup.content().addTexture(PIG_TEXTURES_COATWHITE[1]);
            }
            hairTexGroup.addTexture(PIG_TEXTURES_COAT[coat_texture]);
        }

        root.addTexture(PIG_TEXTURES_EYES[eyes]);
        root.addTexture(PIG_TEXTURES_HOOVES[hooves]);
        root.addTextureIf(tusks, PIG_TEXTURES_TUSKS[1]);
        pigTexture.finishTexture();
    }

    private static void addSwallowbelly(TextureGroup hairTexGroup, int whitebelly, int swallowbelly, int swallowbellyRGB, int roan, int roanTexture) {
        if (whitebelly == 0 && swallowbelly != 0) {
            Mask swallowbellyGroup = hairTexGroup.addMaskGroup();
            swallowbellyGroup.maskTexture().addTexture(PIG_TEXTURES_SWALLOWBELLY[swallowbelly]);
            swallowbellyGroup.content().addTexture(PIG_TEXTURES_AGOUTI[0]).rgb(swallowbellyRGB);
            swallowbellyGroup.content().addTextureIf(roan != 0, PIG_TEXTURES_ROAN_RED[roanTexture]);
        }
        hairTexGroup.addTextureIf(whitebelly != 0, PIG_TEXTURES_WHITEBELLY[whitebelly]);
    }
}
