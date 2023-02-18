package mokiyoki.enhancedanimals.renderer.textures;

import mokiyoki.enhancedanimals.entity.EnhancedCow;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import mokiyoki.enhancedanimals.util.Genes;

public class CowTexture {

    public static final String[] COW_TEXTURES_BASE = new String[] {
            "solid_white.png", "solid_lightcream.png", "solid_cream.png", "solid_silver.png"
    };

    public static final String[] COW_TEXTURES_UDDER = new String[] {
            "udder_black.png", "udder_brown.png", "udder_pink.png"
    };

    public static final String[] COW_TEXTURES_RED = new String[] {
            "", "r_solid.png", "r_shaded.png", "r_shaded_indus.png"
    };

    public static final String[] COW_TEXTURES_BLACK = new String[] {
            "", "b_shoulders.png", "b_agoutiwildtype.png", "b_wildtype_darker1.png", "b_wildtype_dark.png", "b_solid.png", "b_brindle0.png", "b_whitebelly.png", "b_fawn.png", "b_gloucester1.png", "b_mask.png"
    };

    public static final String[] COW_TEXTURES_EELSTRIPE = new String[] {
            "", "r_eelstripe.png"
    };

    public static final String[] COW_TEXTURES_MEALY = new String[] {
            "", "mealy0.png", "mealy1.png", "mealy2.png"
    };

    public static final String[] COW_TEXTURES_SKIN = new String[] {
            "skin_black.png", "skin_brown.png", "skin_pink.png"
    };

    public static final String[] COW_TEXTURES_ROAN = new String[] {
            "", "spot_roan0.png",
            "solid_white.png"
    };

    public static final String[] COW_TEXTURES_SPECKLED = new String[] {
            "", "spot_speckled0.png",
            "spot_whitespeckled0.png"
    };

    public static final String[] COW_TEXTURES_WHITEFACE = new String[] {
            "", "spot_whiteface0.png",
            "spot_hetwhiteface0.png",
            "spot_wfcoloursided0.png",
            "spot_gloucester.png", "spot_pingauzer.png", "spot_pingauzer1.png",
            "spot_pibald0.png", "spot_pibald1.png", "spot_pibald2.png", "spot_pibald3.png", "spot_pibald4.png", "spot_pibald5.png", "spot_pibald6.png", "spot_pibald7.png", "spot_pibald8.png", "spot_pibald9.png","spot_pibalda.png", "spot_pibaldb.png", "spot_pibaldc.png", "spot_pibaldd.png", "spot_pibalde.png", "spot_pibaldf.png",
    };

    public static final String[] COW_TEXTURES_WHITEFACEHEAD = new String[] {
            "", "",
            "",
            "",
            "spot_pibald_head0.png", "spot_pibald_head1.png", "spot_pibald_head2.png", "spot_pibald_head3.png", "spot_pibald_head4.png","spot_pibald_head5.png", "spot_pibald_head6.png", "spot_pibald_head7.png", "spot_pibald_head8.png", "spot_pibald_head9.png","spot_pibald_heada.png", "spot_pibald_headb.png", "spot_pibald_headc.png", "spot_pibald_headd.png", "spot_pibald_heade.png", "spot_pibald_headf.png",
    };

    public static final String[] COW_TEXTURES_BROCKLING = new String[] {
            "", "b_spot_brockling0.png", "r_spot_brockling0.png"
    };

    public static final String[] COW_TEXTURES_BELTED = new String[] {
            "", "spot_belt0.png", "spot_belt1.png", "spot_belt2.png", "spot_belt3.png", "spot_belt4.png", "spot_belt5.png", "spot_belt6.png", "spot_belt7.png", "spot_belt8.png", "spot_belt9.png", "spot_belta.png", "spot_beltb.png", "spot_beltc.png", "spot_beltd.png", "spot_belte.png", "spot_beltf.png"
    };

    public static final String[] COW_TEXTURES_BLAZE = new String[] {
            "", "spot_doubleblaze0.png", "spot_doubleblaze1.png", "spot_doubleblaze2.png", "spot_doubleblaze0.png", "spot_doubleblaze1.png", "spot_doubleblaze2.png", "spot_doubleblaze0.png", "spot_doubleblaze1.png", "spot_doubleblaze2.png", "spot_doubleblaze0.png", "spot_doubleblaze1.png", "spot_doubleblaze2.png", "spot_doubleblaze0.png", "spot_doubleblaze1.png", "spot_doubleblaze2.png", "spot_doubleblaze2.png",
            "spot_blaze0.png", "spot_blaze1.png", "spot_blaze2.png", "spot_blaze3.png", "spot_blaze4.png", "spot_blaze5.png", "spot_blaze6.png", "spot_blaze0.png", "spot_blaze1.png", "spot_blaze2.png", "spot_blaze3.png", "spot_blaze4.png", "spot_blaze5.png", "spot_blaze6.png", "spot_blaze5.png", "spot_blaze6.png"
    };

    public static final String[] COW_TEXTURES_COLOURSIDED = new String[] {
            "", "spot_coloursided0.png"
    };

    public static final String[] COW_TEXTURES_HOOVES = new String[] {
            "hooves_black.png", "hooves_black_dwarf.png"
    };

    public static final String[] COW_TEXTURES_EYES = new String[] {
            "eyes_black.png"
    };

    public static final String[] COW_TEXTURES_HORNS = new String[] {
            "", "horns_black.png"
    };

    public static final String[] COW_TEXTURES_COAT = new String[] {
            "coat_normal.png", "coat_smooth.png", "coat_furry.png"
    };

    public static void calculateCowTextures(EnhancedCow cow) {
        if (cow.getSharedGenes() != null) {
            int[] gene = cow.getSharedGenes().getAutosomalGenes();

            int base = 0;
            int red = 1;
            int black = 0;
            int roan = 0;
            int speckled = 0;
            int whiteface = 0;
            int whitefacehead = 0;
            int brockling = 0;
            int belted = 0;
            int blaze = 0;
            int coloursided = 0;
            int skin = 0;
            int hooves = 0;
            int horn = 1;
            int coat = 0;
            int eelstripe = 0;
            int mealy = 0;
            char[] uuidArry;

            String mooshroomUUIDForTexture = cow.getEntityData().get(cow.getMooshroomEntityData());

            if (mooshroomUUIDForTexture.equals("0")) {
                uuidArry = cow.getStringUUID().toCharArray();
            } else {
                uuidArry = mooshroomUUIDForTexture.toCharArray();
            }

            //dominant red
            if (gene[6] == 1 || gene[7] == 1){
                //make red instead maybe flip dominant red toggle?
//                red = 1;
                skin = 1;
            }else {
                if (gene[0] == 1 || gene[1] == 1) {
                    //dominant black
                    black = 5;
                } else if (gene[0] == 4 || gene[1] == 4) {
                    if ((gene[4] == 3 || gene[4] == 5) && (gene[5] == 3 || gene[5] == 5)) {
                        black = cow.getOrSetIsFemale() ? 9 : 5;
                    } else {
                        black = 5;
                    }
                } else if (gene[0] == 3 && gene[1] == 3) {
                    // red
                    black = 0;
                    skin=1;
                } else {
                    //Agouti
                    if (gene[4] == 4 || gene[5] == 4) {
                        //brindle
                        black = 6;
                    } else if (gene[4] == 1 || gene[5] == 1) {
                        if (gene[4] == 2 || gene[5] == 2) {
                            //darker wildtype
                            //or other incomplete dominance
                            black = cow.getOrSetIsFemale() ? 4 : 5;
                        } else {
                            //complete dominance of black enhancer
                            black = cow.getOrSetIsFemale() ? 4 : 5;
                        }
                    } else if (gene[4] == 2 || gene[5] == 2) {
                        //wildtype
                        black = cow.getOrSetIsFemale() ? 2 : 4;
                    } else if (gene[4] == 3 || gene[5] == 3) {
                        //white bellied fawn more blured markings?
                        if (gene[0] == 5 || gene[1] == 5) {
                            black = cow.getOrSetIsFemale() ? 7 : 2;
                        } else {
                            black = cow.getOrSetIsFemale() ? 8 : 1;
                        }
                        red = 2;
                    } else if (gene[4] == 5 || gene[5] == 5){
                        //fawn
                        red = 3;
                        if (gene[0] == 5 || gene[1] == 5) {
                            black = cow.getOrSetIsFemale() ? 10 : 2;
                        } else {
                            black = cow.getOrSetIsFemale() ? 8 : 3;
                        }
                    } else {
                        //recessive black
                        black = 5;
                    }
                }
            }

            //mealy
            if (gene[0] != 1 && gene[1] != 1) {
                if (gene[24] != 1 && gene[25] != 1) {
                    if (!cow.getOrSetIsFemale() || gene[24] == 2 || gene[25] == 2) {
                        mealy = 2;
                    } else {
                        mealy = 1;
                    }
                    if (red < 3) {
                        red++;
                    }
                    if (gene[120] == 1 && gene[121] == 1) {
                        eelstripe = 1;
                    }
                }
            }

//            //standard dilution
//            if (gene[2] == 2 || gene[3] == 2){
//                if (gene[2] == 2 && gene[3] == 2){
//                    //full dilute
//                    skin = 2;
//                }
//            } //not dilute

            //roan
            if (gene[8] == 2 || gene[9] == 2){
                //is roan
                if (gene[8] == 2 && gene[9] == 2) {
                    //white roan
                    roan = 2;
//                if ( uuidArry[0]-48 == 0){
//                    //makes all cows with roan and uuid of 0 infertile
//                }
                }else{
                    roan = 1;
                }
            }

            //speckled
            if (gene[14] == 1 || gene[15] == 1){
                if (gene[14] == 1 && gene[15] == 1){
                    speckled = 2;
                    //pointed white
                }else{
                    speckled = 1;
                    //speckled
                }
            } //not speckled

            //colour sided
            if (gene[20] == 1 || gene[21] == 1){
                //coloursided
                coloursided = 1;
            }

            if (gene[16] == 1 || gene[17] == 1){
                if (gene[16] == 2 || gene[17] == 2){
                    //white face with border spots(Pinzgauer)
                    whiteface = 3;
                } else if (gene[16] == gene[17]){
                    //whiteface
                    whiteface = 1;
                } else {
                    //het whiteface
                    whiteface = 2;
                }
            }else if (gene[16] == 2 || gene[17] == 2){
                //border spots (Pinzgauer) this genes might be incomplete dominant with wildtype but I dont see it
                if (gene[22] != 4 || gene[23] != 4) {
                    if (gene[22] == 2 || gene[23] == 2) {
                        whiteface = 5;
                    } else if (gene[22] != gene[23]){
                        whiteface = 5;
                    } else {
                        whiteface = gene[22] + gene[23] == 2 ? 6 : 4;
                    }
                } else {
                    whiteface = 5;
                }
            }else if (gene[16] == 4 && gene[17] == 4){
                //piebald
                whiteface = 7;
            }

            // Legacy belt/blaze
            // Brockling gene
            if (gene[18] == 1 || gene[19] == 1){
                //belted
                brockling = -1;
            }else if (gene[18] == 2 || gene[19] == 2){
                //blaze
                brockling = -2;
            }

            //Belted
            if (brockling == -1) {
                belted = -1;
                brockling = 0;
            } else if (gene[250] == 2 || gene[251] == 2) {
                belted = 1;
            }

            //Blaze
            if (brockling == -2) {
                blaze = -1;
                brockling = 0;
            } else if (gene[252] == 2 || gene[253] == 2) {
                if (gene[252] == 2 && gene[253] == 2) {
                    blaze = 1;
                } else {
                    blaze = 2;
                }
            }

            if (gene[18] == 3 || gene[19] == 3){
                if (whiteface != 0 || coloursided != 0 || blaze != 0) {
                    if (black == 4 || black == 5 || black == 6 || black == 10 || black == 11 || black == 12) {
                        //brockling
                        brockling = 1;
                    } else {
                        brockling = 2;
                    }
                }
            }

            if (whiteface == 7){
                //selects body piebalding texture
                if (Character.isDigit(uuidArry[1])) {
                    whiteface = whiteface + (1 + (uuidArry[1] - 48));
                } else {
                    char d = uuidArry[1];

                    switch (d) {
                        case 'a':
                            whiteface = whiteface + 10;
                            break;
                        case 'b':
                            whiteface = whiteface + 11;
                            break;
                        case 'c':
                            whiteface = whiteface + 12;
                            break;
                        case 'd':
                            whiteface = whiteface + 13;
                            break;
                        case 'e':
                            whiteface = whiteface + 14;
                            break;
                        case 'f':
                            whiteface = whiteface + 15;
                            break;
                        default:
                            whiteface = 6;
                    }
                }

                //selects face piebalding texture
                if (uuidArry[0] != uuidArry[1]) {
                    whitefacehead = 4;
                    if (Character.isDigit(uuidArry[2])) {
                        whitefacehead = whitefacehead + (1 + uuidArry[2] - 48);
                    } else {
                        char d = uuidArry[2];

                        switch (d) {
                            case 'a':
                                whitefacehead = whitefacehead + 10;
                                break;
                            case 'b':
                                whitefacehead = whitefacehead + 11;
                                break;
                            case 'c':
                                whitefacehead = whitefacehead + 12;
                                break;
                            case 'd':
                                whitefacehead = whitefacehead + 13;
                                break;
                            case 'e':
                                whitefacehead = whitefacehead + 14;
                                break;
                            case 'f':
                                whitefacehead = whitefacehead + 15;
                                break;
                            default:
                                whitefacehead = 0;
                        }
                    }
                }

            }

            //belt picker belt==-1 is legacy belt==1 is updated
            if (belted != 0) {
                belted = 1;
                if (Character.isDigit(uuidArry[3])) {
                    belted = belted + (1 + (uuidArry[3] - 48));
                } else {
                    char d = uuidArry[3];

                    switch (d) {
                        case 'a':
                            belted = belted + 10;
                            break;
                        case 'b':
                            belted = belted + 11;
                            break;
                        case 'c':
                            belted = belted + 12;
                            break;
                        case 'd':
                            belted = belted + 13;
                            break;
                        case 'e':
                            belted = belted + 14;
                            break;
                        case 'f':
                            belted = belted + 15;
                            break;
                        default:
                            belted = 0;
                    }
                }
            }

            //blaze variations
            if (blaze != 0) {
                if (blaze != 1) {
                    blaze = 17;
                }
                if (Character.isDigit(uuidArry[3])) {
                    blaze = blaze + (1 + (uuidArry[3] - 48));
                } else {
                    char d = uuidArry[3];

                    switch (d) {
                        case 'a':
                            blaze = blaze + 0;
                            break;
                        case 'b':
                            blaze = blaze + 1;
                            break;
                        case 'c':
                            blaze = blaze + 2;
                            break;
                        case 'd':
                            blaze = blaze + 3;
                            break;
                        case 'e':
                            blaze = blaze + 4;
                            break;
                        case 'f':
                            blaze = blaze + 5;
                            break;
                        default:
                            belted = 0;
                    }
                }
            }

            //these alter texture to fit model changes
            if(gene[26] == 1 || gene[27] == 1) {
                hooves = 1;
            }

            if (gene[48] == 1 || gene[49] == 1){
                coat = 1;
            }else{
                if (gene[50] == 2 && gene[51] == 2) {
                    coat = 2;
                } else if (gene[52] == 2 && gene[53] == 2) {
                    coat = 2;
                }else if ((gene[50] == 2 || gene[51] == 2) && (gene[52] == 2 || gene[53] == 2)){
                    coat = 2;
                }
            }

            cow.addTextureToAnimal(COW_TEXTURES_BASE, 0, null);
            cow.addTextureToAnimal(COW_TEXTURES_UDDER, skin, null);
            cow.addTextureToAnimal(COW_TEXTURES_RED, red, r -> r != 0);
            cow.addTextureToAnimal(COW_TEXTURES_BLACK, black, b -> b != 0);
            cow.addTextureToAnimal(COW_TEXTURES_MEALY, mealy, m -> m != 0);
            cow.addTextureToAnimal(COW_TEXTURES_EELSTRIPE, eelstripe, e -> e != 0);
            cow.addTextureToAnimal(COW_TEXTURES_SKIN, skin, null);
            cow.addTextureToAnimal(COW_TEXTURES_WHITEFACE, whiteface, w -> w != 0);
            cow.addTextureToAnimal(COW_TEXTURES_WHITEFACEHEAD, whitefacehead, w -> w >= 4);
            cow.addTextureToAnimal(COW_TEXTURES_COLOURSIDED, coloursided, c -> c != 0);
            cow.addTextureToAnimal(COW_TEXTURES_BROCKLING, brockling, b -> b != 0);
            cow.addTextureToAnimal(COW_TEXTURES_BELTED, belted, b -> b != 0);
            cow.addTextureToAnimal(COW_TEXTURES_BLAZE, blaze, b -> b != 0);
            cow.addTextureToAnimal(COW_TEXTURES_ROAN, roan, r -> r != 0);
            cow.addTextureToAnimal(COW_TEXTURES_SPECKLED, speckled, r -> r != 0);
            cow.addTextureToAnimal(COW_TEXTURES_HOOVES, hooves, null);
            cow.addTextureToAnimal(COW_TEXTURES_EYES, 0, null);
            cow.addTextureToAnimal(COW_TEXTURES_HORNS, horn, null);
            cow.addTextureToAnimal(COW_TEXTURES_COAT, coat, null);
        }
    }

    public static void calculateCowRGB(Colouration colouration, Genes genes, boolean isFemale) {
        if (colouration.getPheomelaninColour() == -1 || colouration.getMelaninColour() == -1) {
            int[] gene = genes.getAutosomalGenes();

            float blackHue = 0.0F;
            float blackSaturation = 0.05F;
            float blackBrightness = 0.05F;

            float redHue = 0.05F;
            float redSaturation = 0.57F;
            float redBrightness = 0.55F;

            if (gene[0] == 3 && gene[1] == 3) {
                //cow is red
                blackHue = redHue;
                blackSaturation = redSaturation;
                blackBrightness = (redBrightness + blackBrightness) * 0.5F;
            }

            for (int i = 130; i < 150; i++) {
                if (gene[i] == 2) {
                    redHue = redHue + ((0.1F - redHue) * 0.05F);
                    redBrightness = redBrightness + 0.01F;
                }
            }

            for (int i = 150; i < 170; i++) {
                if (gene[i] == 2) {
                    redHue = redHue - 0.00225F;
                    if (redHue <= 0.0F) {
                        redHue = 1.0F + redHue;
                    }
                    redSaturation = redSaturation + 0.00175F;
                    redBrightness = redBrightness - 0.0155F;

                    blackHue = blackHue - 0.00225F;
                    if (blackHue <= 0.0F) {
                        blackHue = 1.0F + blackHue;
                    }
                    blackHue = blackHue + 0.002F;
                }
            }

            float shadeIntensity = 0.5F;
            for (int i = 170; i < 185; i++) {
                if (gene[i] == 2) {
                    redBrightness = redBrightness + 0.005F;
                    shadeIntensity = shadeIntensity - 0.005F;
                }
            }

            for (int i = 185; i < 200; i++) {
                if (gene[i] == 2) {
                    redBrightness = redBrightness - 0.005F;
                    shadeIntensity = shadeIntensity + 0.005F;
                }
            }

            for (int i = 200; i <= 224; i++) {
                if (gene[i] == 2) {
                    shadeIntensity = shadeIntensity - 0.015F;
                }
            }

            for (int i = 226; i < 250; i++) {
                if (gene[i] == 2) {
                    shadeIntensity = shadeIntensity + 0.02F;
                }
            }

            /**
             *  Dun - Mostly effects red pigment. Looks like what I would call silver. Responsible for grey brahmans, guzerat, and hungarian grey.
             */
            if (gene[128] == 2 || gene[129] == 2) {
                if (gene[128] == 2 && gene[129] == 2) {
                    //homo dun
                    redSaturation = 0.0F;
                    redBrightness = Colouration.mixColourComponent(redBrightness, 1.0F, 0.9F);
                    blackSaturation = Colouration.mixColourComponent(blackBrightness, 1.0F, 0.2F);
                    blackBrightness = Colouration.mixColourComponent(blackBrightness, 1.0F, 0.05F);
                } else {
                    //het dun
                    redHue = Colouration.mixHueComponent(redHue, 0.1F, 0.1F);
                    redSaturation = Colouration.mixColourComponent(redSaturation, 0.0F, 0.45F);
                    redBrightness = Colouration.mixColourComponent(redBrightness, 1.0F, 0.35F);
                }
            }

            /**
             *  Dexter Dun - chocolate
             */
            if (gene[10] == 2 && gene[11] == 2) {
                blackHue = Colouration.mixHueComponent(blackHue, 0.1F, 0.3F);
                blackSaturation = blackSaturation + ((1.0F - blackSaturation) * 0.45F);
                blackBrightness = blackBrightness + ((1.0F - blackBrightness) * 0.25F);
            }

            /**
             *  Dilution. What you usually see in highlands, murrey grey, simmental and other such dun/yellow/grey cattle
             */
            if (gene[2] == 2 || gene[3] == 2) {
                // simmental or highland dilution
                if (gene[2] == 2 && gene[3] == 2) {
                    //homo highland dun
//                        redHue = Colouration.mixHueComponent(redHue, 0.1F, 0.85F);
//                        redSaturation = Colouration.mixColourComponent(redSaturation, 0.0F, 0.9F);
//                        redBrightness = Colouration.mixColourComponent(redBrightness, 1.0F, 0.6F);
//                        blackHue = Colouration.mixHueComponent(redHue, 0.1F, 0.9F);
//                        blackSaturation = Colouration.mixColourComponent(redSaturation, 0.0F, 0.9F);
//                        blackBrightness = Colouration.mixColourComponent(redBrightness * blackBrightness, 1.0F, 0.6F);
                    blackHue = Colouration.mixHueComponent(blackHue, 0.1F, 0.3F);
                    blackSaturation = blackSaturation + ((1.0F - blackSaturation) * 0.45F);
                    blackBrightness = blackBrightness + ((1.0F - blackBrightness) * 0.25F);
                    redHue = Colouration.mixHueComponent(redHue, 0.1F, 0.8F);
                    redSaturation = Colouration.mixColourComponent(redSaturation, 0.0F, 0.5F);
                    redBrightness = Colouration.mixColourComponent(redBrightness, 1.0F, 0.5F);
                    blackHue = Colouration.mixHueComponent(redHue, 0.0F, 0.5F);
                    blackSaturation = Colouration.mixColourComponent(blackSaturation*0.65F, redSaturation*0.25F, 0.4F);
                    blackBrightness = Colouration.mixColourComponent(redBrightness * blackBrightness, 1.0F, 0.45F);
                } else if (gene[2] == 1 || gene[3] == 1) {
                    //het dun
                    if (gene[0] == 1 || gene[1] == 1) {
                        blackHue = Colouration.mixHueComponent(blackHue, 0.1F, 0.15F);
                        blackSaturation = blackSaturation + ((1.0F - blackSaturation) * 0.225F);
                        blackBrightness = blackBrightness + ((1.0F - blackBrightness) * 0.125F);
                        redHue = Colouration.mixHueComponent(redHue, 0.1F, 0.7F);
                        redSaturation = Colouration.mixColourComponent(redSaturation, 0.0F, 0.5F);
                        redBrightness = Colouration.mixColourComponent(redBrightness, 1.0F, 0.4F);
//                            blackHue = Colouration.mixHueComponent(blackHue, redHue, 0.5F);
                        blackSaturation = Colouration.mixColourComponent(blackSaturation, redSaturation, 0.5F);
                        blackBrightness = Colouration.mixColourComponent(blackBrightness, redBrightness, 0.45F);
                    } else if (gene[0] == 3 || gene[1] == 3) {
                        redSaturation = Colouration.mixColourComponent(redSaturation, 0.0F, 0.1F);
                        redBrightness = Colouration.mixColourComponent(redBrightness, 1.0F, 0.4F);
                        blackHue = redHue;
                        redHue = Colouration.mixHueComponent(redHue, 0.1F, 0.75F);
                        blackSaturation = redSaturation;
                        blackBrightness = Colouration.mixColourComponent(blackBrightness, redBrightness, 0.25F);
                    } else {
//                    blackHue = mixColourComponent(blackHue, redHue, 0.5F);
//                    blackSaturation = mixColourComponent(blackSaturation, redSaturation, 0.5F);
                        redHue = Colouration.mixHueComponent(redHue, 0.1F, 0.8F);
                        redSaturation = Colouration.mixColourComponent(redSaturation, 0.0F, 0.1F);
                        redBrightness = Colouration.mixColourComponent(redBrightness, 1.0F, 0.4F);
                        blackHue = Colouration.mixHueComponent(blackHue, redHue, 0.6F);
                        blackSaturation = Colouration.mixColourComponent(blackSaturation, redSaturation, 0.5F);
                        blackBrightness = Colouration.mixColourComponent(blackBrightness, redBrightness, 0.4F);
                    }
                } else {
                    redHue = Colouration.mixHueComponent(redHue, 0.1F, 0.85F);
                    redSaturation = Colouration.mixColourComponent(redSaturation, 0.0F, 0.85F);
                    redBrightness = Colouration.mixColourComponent(redBrightness, 1.0F, 0.84F);
//                        blackSaturation = Colouration.mixColourComponent(blackBrightness, 1.0F, 0.1F);
                    blackBrightness = Colouration.mixColourComponent(blackBrightness, 1.0F, 0.75F);
                }
            } else if (gene[2] == 3 || gene[3] == 3) {
                //charolais dilution very harsh
                if (gene[2] == 3 && gene[3] == 3) {
                    redHue = 0.1F;
                    redSaturation = 0.0F;
                    redBrightness = 1.0F;
                    blackHue = 0.1F;
                    blackSaturation = 0.0F;
                    blackBrightness = 1.0F;
                } else {
                    redHue = Colouration.mixHueComponent(redHue, 0.1F, 0.80F);
                    redSaturation = Colouration.mixColourComponent(redSaturation, 0.0F, 0.25F);
                    redBrightness = Colouration.mixColourComponent(redBrightness, 1.0F, 0.8F);
                    blackHue = Colouration.mixHueComponent(blackHue, 0.1F, 0.80F);
                    blackSaturation = Colouration.mixColourComponent(blackBrightness, 0.0F, 0.2F);
                    blackBrightness = Colouration.mixColourComponent(blackBrightness, 1.0F, 0.8F);
                }
            }

            //puts final values into array for processing
            float[] melanin = {blackHue, blackSaturation, blackBrightness};
            float[] pheomelanin = {redHue, redSaturation, redBrightness};

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
            }

            if (!isFemale) {
                shadeIntensity = 0.5F * (shadeIntensity + 1.0F);
            }

            if (shadeIntensity <= 0.0F) {
                shadeIntensity = 0.00001F;
            } else if (shadeIntensity >= 1.0F) {
                shadeIntensity = 0.99999F;
            }

            colouration.setMelaninColour(Colouration.HSBAtoABGR(melanin[0], melanin[1], melanin[2], shadeIntensity));
            colouration.setPheomelaninColour(Colouration.HSBtoABGR(pheomelanin[0], pheomelanin[1], pheomelanin[2]));

        }
    }

}
