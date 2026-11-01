package mokiyoki.enhancedanimals.renderer.textures;

import mokiyoki.enhancedanimals.entity.EnhancedLlama;
import mokiyoki.enhancedanimals.renderer.texture.TextureGrouping;
import mokiyoki.enhancedanimals.renderer.texture.TexturingType;

public class LlamaTexture {
    private static final String[] LLAMA_TEXTURES_GROUND = new String[] {
            "brokenlogic.png", "ground_paleshaded.png", "ground_shaded.png", "ground_blacktan.png", "ground_bay.png", "ground_mahogany.png", "ground_blacktan.png", "black.png", "fawn.png"
    };

    private static final String[] LLAMA_TEXTURES_PATTERN = new String[] {
            "", "pattern_paleshaded.png", "pattern_shaded.png", "pattern_blackred.png", "pattern_bay.png", "pattern_mahogany.png", "pattern_blacktan.png"
    };

    private static final String[] LLAMA_TEXTURES_ROAN = new String[] {
            "", "roan_0.png", "roan_1.png", "roan_2.png", "roan_3.png", "roan_4.png", "roan_5.png", "roan_6.png", "roan_7.png", "roan_8.png", "roan_9.png", "roan_a.png", "roan_b.png", "roan_c.png", "roan_d.png", "roan_e.png", "roan_f.png"
    };

    // higher numbers are more white
    private static final String[] LLAMA_TEXTURES_TUXEDO = new String[] {
            "", "tuxedo_0.png", "tuxedo_1.png", "tuxedo_2.png", "tuxedo_3.png", "tuxedo_4.png", "tuxedo_5.png", "tuxedo_6.png", "tuxedo_7.png", "tuxedo_8.png", "tuxedo_9.png", "tuxedo_a.png", "tuxedo_b.png", "tuxedo_c.png", "tuxedo_d.png", "tuxedo_e.png", "tuxedo_f.png"
    };

    // higher numbers are more white
    private static final String[] LLAMA_TEXTURES_PIEBALD = new String[] {
            "", "piebald_0.png", "piebald_1.png", "piebald_2.png", "piebald_3.png", "piebald_4.png", "piebald_5.png", "piebald_6.png", "piebald_7.png", "piebald_8.png", "piebald_9.png", "piebald_a.png", "piebald_b.png", "piebald_c.png", "piebald_d.png", "piebald_e.png", "piebald_f.png"
    };

    // higher numbers are more white
    private static final String[] LLAMA_TEXTURES_DOMWHITE = new String[] {
            "", "domwhite_leaky.png", "domwhite_tinted.png", "domwhite_solid.png"
    };

    private static final String[] LLAMA_TEXTURES_FUR = new String[] {
            "", "fur_suri.png"
    };

    private static final String[] LLAMA_TEXTURES_EYES = new String[] {
            "eyes_black.png", "eyes_blue.png", "eyes_iceblue.png"
    };

    private static final String[] LLAMA_TEXTURES_SKIN = new String[] {
            "skin_black.png", "skin_pink.png"
    };

    public static void calculateLlamaTextures(EnhancedLlama llama, int[] gene, char[] uuid) {
        int ground;
        int pattern = 0;
        int roan = 0;
        int tux = 0;
        int piebald = 0;
        int domwhite = 0;
        int fur = 0;
        int eyes = 0;
        int skin = 0;
        // i is a random modifier

        if ( gene[14] == 1 || gene[15] == 1 ){
            //Dominant Black
            ground = 7;
        } else if ( gene[14] == 3 || gene[15] == 3 ){
            //fawn self
            ground = 8;
        }else{
            if ( gene[16] == 1 || gene[17] == 1 ){
                //pale shaded fawn
                ground = 1;
                pattern = 1;
            } else if ( gene[16] == 2 || gene[17] == 2 ){
                //shaded fawn
                ground = 2;
                pattern = 2;
            } else if ( gene[16] == 3 || gene[17] == 3 ){
                //black trimmed red
                ground = 3;
                pattern = 3;
            } else if ( gene[16] == 4 || gene[17] == 4 ){
                //bay
                ground = 4;
                pattern = 4;
            } else if ( gene[16] == 5 || gene[17] == 5 ){
                //mahogany
                ground = 5;
                pattern = 5;
            }else if ( gene[16] == 6 || gene[17] == 6 ){
                //black and tan
                ground = 6;
                pattern = 6;
            }else{
                //black
                ground = 7;
            }
        }

        if ( gene[6] == 1 || gene[7] == 1){
            //dominant white   0 1 2 3 4 5 6 7 8 9 a b c d e f

            if ( Character.isDigit(uuid[1]) ){
                if ((uuid[1]-48) < 5 ){
                    domwhite = 1;
                } else {
                    domwhite = 2;
                }
            }else{
                domwhite = 3;
            }

        }

        if ( gene[8] == 1 || gene[9] == 1){
            //roan

            if (Character.isDigit(uuid[2])){
                roan = 1 + (uuid[2]-48);
            } else {
                char d = uuid[2];

                switch (d){
                    case 'a':
                        roan = 11;
                        break;
                    case 'b':
                        roan = 12;
                        break;
                    case 'c':
                        roan = 13;
                        break;
                    case 'd':
                        roan = 14;
                        break;
                    case 'e':
                        roan = 15;
                        break;
                    case 'f':
                        roan = 16;
                        break;
                    //TODO add debugging default option
                }
            }
        }

        if ( gene[10] == 2 && gene[11] == 2){
            //piebald

            if ( Character.isDigit(uuid[4]) ){
                piebald = 1 + (uuid[4] - 48);
            } else {
                char d = uuid[4];

                switch (d){
                    case 'a':
                        piebald = 11;
                        break;
                    case 'b':
                        piebald = 12;
                        break;
                    case 'c':
                        piebald = 13;
                        break;
                    case 'd':
                        piebald = 14;
                        break;
                    case 'e':
                        piebald = 15;
                        break;
                    case 'f':
                        piebald = 16;
                        break;
                    //TODO add debugging default option
                }
            }
        }

        if ( gene[12] == 1 || gene[13] == 1){
            //tuxedo

            if ( Character.isDigit(uuid[6]) ){
                tux = 1 + (uuid[6]-48);
            } else {
                char d = uuid[6];

                switch (d){
                    case 'a':
                        tux = 11;
                        break;
                    case 'b':
                        tux = 12;
                        break;
                    case 'c':
                        tux = 13;
                        break;
                    case 'd':
                        tux = 14;
                        break;
                    case 'e':
                        tux = 15;
                        break;
                    case 'f':
                        tux = 16;
                        break;
                    default:
                        ground = 0;
                        pattern = 0;
                        tux = 0;
                        //TODO add debugging default option
                }
            }
        }

        if (domwhite > 0){
            skin = 1;
            if (piebald > 0){
                eyes = 1;
            }
        }

        //suri coat texture
        if (gene[20] == 2 && gene[21] == 2){
            fur = 1;
        }

        TextureGrouping parentGroup = new TextureGrouping(TexturingType.MERGE_GROUP);

        addGroundTexture(llama, parentGroup, ground);

        addPatternTexture(llama, parentGroup, pattern);

        createWhiteSpots(llama, parentGroup, roan, tux, piebald, domwhite);

        addFurTexture(llama, parentGroup, fur);

        addDetails(llama, parentGroup, eyes, skin);

        llama.setTextureGrouping(parentGroup);
    }

    private static void createWhiteSpots(EnhancedLlama llama, TextureGrouping parentGroup, int roan, int tux, int piebald, int domwhite) {
        TextureGrouping spots = new TextureGrouping(TexturingType.MERGE_GROUP);

        addRoanTexture(llama, spots, roan);
        addTuxTexture(llama, spots, tux);
        addPiebaldTexture(llama, spots, piebald);
        addDomWhiteTexture(llama, spots, domwhite);

        if (spots.isPopulated()) {
            TextureGrouping grouping = new TextureGrouping(TexturingType.MASK_GROUP);
            llama.layer(grouping).texture("domwhite_solid.png").add();
            grouping.addGrouping(spots);
            parentGroup.addGrouping(grouping);
        } else {
            llama.addSkippedSlot();
        }
    }

    private static void addDetails(EnhancedLlama llama, TextureGrouping parentGroup, int eyes, int skin) {
        TextureGrouping grouping = new TextureGrouping(TexturingType.MERGE_GROUP);
        llama.layer(grouping).textureTableSelector(LLAMA_TEXTURES_EYES, eyes).add();
        llama.layer(grouping).textureTableSelector(LLAMA_TEXTURES_SKIN, skin).add();
        parentGroup.addGrouping(grouping);
    }


    private static void addFurTexture(EnhancedLlama llama, TextureGrouping parentGroup, int fur) {
        llama.layer(parentGroup).textureTableSelector(LLAMA_TEXTURES_FUR, fur).onlyIf(f -> f != 0).orKey("sf").add();
    }

    private static void addDomWhiteTexture(EnhancedLlama llama, TextureGrouping parentGroup, int domwhite) {
        llama.layer(parentGroup).textureTableSelector(LLAMA_TEXTURES_DOMWHITE, domwhite).onlyIf(d -> d != 0).orKey("nw").add();
    }

    private static void addPiebaldTexture(EnhancedLlama llama, TextureGrouping parentGroup, int piebald) {
        llama.layer(parentGroup).textureTableSelector(LLAMA_TEXTURES_PIEBALD, piebald).onlyIf(p -> p != 0).orKey("np").add();
    }

    private static void addTuxTexture(EnhancedLlama llama, TextureGrouping parentGroup, int tux) {
        llama.layer(parentGroup).textureTableSelector(LLAMA_TEXTURES_TUXEDO, tux).onlyIf(t -> t != 0).orKey("nt").add();
    }

    private static void addRoanTexture(EnhancedLlama llama, TextureGrouping parentGroup, int roan) {
        llama.layer(parentGroup).textureTableSelector(LLAMA_TEXTURES_ROAN, roan).onlyIf(r -> r != 0).orKey("nr").add();
    }

    private static void addPatternTexture(EnhancedLlama llama, TextureGrouping parentGroup, int pattern) {
        llama.layer(parentGroup).textureTableSelector(LLAMA_TEXTURES_PATTERN, pattern).onlyIf(p -> p != 0).orKey("np").add();
    }

    private static void addGroundTexture(EnhancedLlama llama, TextureGrouping parentGroup, int ground) {
        llama.layer(parentGroup).textureTableSelector(LLAMA_TEXTURES_GROUND, ground).add();
    }
}
