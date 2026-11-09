package mokiyoki.enhancedanimals.renderer.textures;

import mokiyoki.enhancedanimals.entity.EnhancedCow;
import mokiyoki.enhancedanimals.entity.util.VariationKey;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import mokiyoki.enhancedanimals.renderer.texture.Cutout;
import mokiyoki.enhancedanimals.renderer.texture.TextureGroup;
import mokiyoki.enhancedanimals.renderer.texture.AddedTexture;
import mokiyoki.enhancedanimals.renderer.texture.Mask;
import mokiyoki.enhancedanimals.renderer.texture.TextureBuilder;
import mokiyoki.enhancedanimals.util.Genes;


import static mokiyoki.enhancedanimals.entity.util.VariationKeys.*;

public class CowTexture {

    private static final int[] headCubes = new int[] {
            0, 0, 38, 8, 7, 6,
            0, 28, 38, 4, 5, 4,
            0, 16, 51, 5, 4, 3,
            0, 25, 51, 3, 3, 7,
            0, 44, 38, 4, 6, 2,
            0, 51, 50, 10, 8, 8,
            1, 0, 51, 8, 51, 3, 7, 1
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

    public static final String[] COW_TEXTURES_HOOVES = new String[] {
            "hooves_black.png", "hooves_black_dwarf.png"
    };

    public static final String[] COW_TEXTURES_HORNS = new String[] {
            "", "horns_black.png"
    };

    public static final String[] COW_TEXTURES_COAT = new String[] {
            "coat_normal.png", "coat_smooth.png", "coat_furry.png"
    };

    public static void calculateCowTextures(EnhancedCow cow, int[] gene, String variantTexture) {
        buildCowTexture(TextureBuilder.of(cow, gene), variantTexture);
    }

    static void buildCowTexture(TextureBuilder cowTexture, String variantTexture) {
        int red = 1;
        int black = 0;
        int skin = 0;
        int horn = 1;
        int eelstripe = 0;
        int mealy = 0;

        //dominant red
        if (cowTexture.eitherAllelesHave(6, 1)) {
            skin = 1;
        } else if (cowTexture.eitherAllelesHave(0, 1)) {
            //dominant black
            black = 5;
        } else if (cowTexture.eitherAllelesHave(0, 4)) {
            black = cowTexture.bothAllelesHaveAnyOf(4, 3, 5) && cowTexture.ifIsFemale() ? 9 : 5;
        } else if (cowTexture.isHomoFor(0, 3)) {
            // red
            skin = 1;
        } else if (cowTexture.eitherAllelesHave(4, 4)) {
            //brindle
            black = 6;
        } else if (cowTexture.eitherAllelesHave(4, 1)) {
            black = cowTexture.ifIsFemale() ? 4 : 5;
        } else if (cowTexture.eitherAllelesHave(4, 2)) {
            //wildtype
            black = cowTexture.ifIsFemale() ? 2 : 4;
        } else if (cowTexture.eitherAllelesHave(4, 3)) {
            //white bellied fawn more blured markings?
            if (cowTexture.eitherAllelesHave(0, 5)) {
                black = cowTexture.ifIsFemale() ? 7 : 2;
            } else {
                black = cowTexture.ifIsFemale() ? 8 : 1;
            }
            red = 2;
        } else if (cowTexture.eitherAllelesHave(4, 5)) {
            //fawn
            red = 3;
            if (cowTexture.eitherAllelesHave(0, 5)) {
                black = cowTexture.ifIsFemale() ? 10 : 2;
            } else {
                black = cowTexture.ifIsFemale() ? 8 : 3;
            }
        } else {
            //recessive black
            black = 5;
        }

        //mealy
        if (!cowTexture.eitherAllelesHave(0, 1) && !cowTexture.eitherAllelesHave(24, 1)) {
            mealy = !cowTexture.ifIsFemale() || cowTexture.eitherAllelesHave(24, 2) ? 2 : 1;
            if (red < 3) {
                red++;
            }
            if (cowTexture.isHomoFor(120, 1)) {
                eelstripe = 1;
            }
        }

        //these alter texture to fit model changes
        int hooves = cowTexture.eitherAllelesHave(26, 1) ? 1 : 0;
        int coat = 0;
        if (cowTexture.eitherAllelesHave(48, 1)) {
            coat = 1;
        } else if (cowTexture.isHomoFor(50, 2) || cowTexture.isHomoFor(52, 2) || (cowTexture.eitherAllelesHave(50, 2) && cowTexture.eitherAllelesHave(52, 2))) {
            coat = 2;
        }
        TextureGroup base = cowTexture.root();

        Cutout cow = base.addCutoutGroup();
        cow.cutoutTexture().addTexture("base.png");
        TextureGroup body = cow.content();

        body.addMergeGroup("solid.png", COW_TEXTURES_UDDER[skin]);
        body.addTexture(COW_TEXTURES_RED[red]).tintRed();
        body.addTexture(COW_TEXTURES_BLACK[black]).shadeMelanin();

        TextureGroup counterShading = body.addMergeGroup();
        counterShading.addTexture(COW_TEXTURES_MEALY[mealy]);
        counterShading.addTexture(COW_TEXTURES_EELSTRIPE[eelstripe]).tintRed();

        body.addTexture(COW_TEXTURES_SKIN[skin]);

        addWhiteSpots(cowTexture, body, black);

        body.addMergeGroup(COW_TEXTURES_HOOVES[hooves], "eyes_black.png", COW_TEXTURES_HORNS[horn], COW_TEXTURES_COAT[coat]);

        base.addTexture(variantTexture);

        cowTexture.finishTexture();
    }

    private static boolean hasLegacyBrockling(TextureBuilder cowTexture) {
        if (!cowTexture.eitherAllelesHave(18, 3)) return false;
        boolean whiteface = cowTexture.eitherAllelesHave(16, 1) || cowTexture.eitherAllelesHave(16, 2) || cowTexture.isHomoFor(16, 4);
        return whiteface || cowTexture.eitherAllelesHave(20, 1) || cowTexture.eitherAllelesHave(252, 2) || cowTexture.eitherAllelesHave(18, 2);
    }

    private static void addLegacyBrockling(TextureGroup body, int black) {
        boolean shaded = black == 4 || black == 5 || black == 6 || black == 10 || black == 11 || black == 12;
        AddedTexture brockling = body.addTexture("spots/brockling/0.png");
        if (shaded) brockling.shadeMelanin();
        else brockling.tintRed();
    }

    private static TextureGroup addWhiteMask(TextureBuilder cowTexture, TextureGroup body, boolean brockling) {
        Mask spots = body.addMaskGroup();
        Cutout white = spots.maskTexture().addCutoutGroup();
        if (brockling) white.cutoutTexture().addTexture("spots/brockling/0.png");
        white.content().addTexture("spots/white.png");
        return spots.content();
    }

    private static void addWhiteSpots(TextureBuilder cowTexture, TextureGroup body, int black) {
        boolean legacyBrockling = hasLegacyBrockling(cowTexture);
        boolean newBrockling = !legacyBrockling && cowTexture.eitherAllelesHave(254, 2);
        TextureGroup faceSpots = addWhiteMask(cowTexture, body, newBrockling);
        TextureGroup beltSpots = faceSpots;
        TextureGroup roanSpots = faceSpots;
        if (legacyBrockling) {
            addLegacyBrockling(body, black);
            beltSpots = addWhiteMask(cowTexture, body, false);
            roanSpots = beltSpots;
        } else if (newBrockling) {
            roanSpots = addWhiteMask(cowTexture, body, false);
        }

        //roan
        if (cowTexture.isHomoFor(8, 2)) {
            roanSpots.addTexture("spots/roan/solid.png");
        } else if (cowTexture.eitherAllelesHave(8, 2)) {
            roanSpots.addRandomHexTexture("spots/roan/%s.png", COW_ROAN);
        }

        //speckled
        if (cowTexture.isHomoFor(14, 1)) {
            //pointed white
            roanSpots.addTexture("spots/speckled/homozygous/0.png");
        } else if (cowTexture.eitherAllelesHave(14, 1)) {
            roanSpots.pickRandomTexture("spots/speckled/heterozygous/%d.png", COW_SPECKLED, 4);
        }

        //colour sided
        if (cowTexture.eitherAllelesHave(20, 1)) faceSpots.pickRandomTexture("spots/coloursided/%d.png", COW_COLOURSIDED, 4);

        if (cowTexture.eitherAllelesHave(16, 1)) {
            if (cowTexture.eitherAllelesHave(16, 2)) {
                //white face with border spots(Pinzgauer)
                faceSpots.addTexture("spots/hereford_coloursided/0.png");
            } else if (cowTexture.isHomoFor(16, 1)) {
                faceSpots.addTexture("spots/hereford/homozygous/0.png");
            } else {
                faceSpots.addTexture("spots/hereford/heterozygous/0.png");
            }
        } else if (cowTexture.eitherAllelesHave(16, 2)) {
            //border spots (Pinzgauer) this genes might be incomplete dominant with wildtype but I dont see it
            int pingauzer = 1;
            if (cowTexture.gene(22) == cowTexture.gene(23)) {
                if (cowTexture.gene(22) == 1) {
                    pingauzer = 2;
                } else if (cowTexture.gene(22) == 3) {
                    pingauzer = 0;
                }
            }
            faceSpots.addTexture("spots/pingauzer/" + pingauzer + ".png");
        } else if (cowTexture.isHomoFor(16, 4)) {
            //piebald
            faceSpots.addTexture("spots/piebald/body/" + spotHex(cowTexture, COW_PIEBALD_BODY) + ".png");
            if (cowTexture.sexVariation() != cowTexture.randomHex(COW_PIEBALD_BODY)) {
                faceSpots.addTexture("spots/piebald/head/" + spotHex(cowTexture, COW_PIEBALD_HEAD) + ".png");
            }
        }

        boolean legacyBelt = cowTexture.eitherAllelesHave(18, 1);
        boolean legacyBlaze = !legacyBelt && cowTexture.eitherAllelesHave(18, 2);

        if (!legacyBelt && cowTexture.eitherAllelesHave(250, 2)) {
            beltSpots.addTexture("spots/belt/" + spotHex(cowTexture, cowTexture.variation().isLegacy() ? COW_BLAZE : COW_BELT) + ".png");
        }

        if (!legacyBlaze) {
            if (cowTexture.isHomoFor(252, 2)) {
                beltSpots.addTexture("spots/doubleblaze/" + doubleBlaze(cowTexture) + ".png");
            } else if (cowTexture.eitherAllelesHave(252, 2)) {
                beltSpots.addTexture("spots/blaze/" + singleBlaze(cowTexture) + ".png");
            }
        }

        //Legacy Genes
        if (legacyBelt) {
            beltSpots.addTexture("spots/belt/" + spotHex(cowTexture, COW_BLAZE) + ".png");
        } else if (legacyBlaze) {
            beltSpots.addTexture("spots/blaze/" + singleBlaze(cowTexture) + ".png");
        }
    }

    private static char spotHex(TextureBuilder cowTexture, VariationKey key) {
        char hex = cowTexture.randomHexChar(key);
        if (!cowTexture.variation().isLegacy()) return hex;
        if (hex == '9') return 'a';
        return Character.isDigit(hex) ? (char) (hex + 1) : hex;
    }

    private static int singleBlaze(TextureBuilder cowTexture) {
        if (!cowTexture.variation().isLegacy()) return cowTexture.randomBoundedInt(COW_BLAZE, 7);
        int hex = cowTexture.randomHex(COW_BLAZE);
        return hex < 10 ? (hex + 1) % 7 : hex - 10;
    }

    private static int doubleBlaze(TextureBuilder cowTexture) {
        if (!cowTexture.variation().isLegacy()) return cowTexture.randomBoundedInt(COW_BLAZE, 3);
        int hex = cowTexture.randomHex(COW_BLAZE);
        return hex < 10 ? (hex + 1) % 3 : (hex - 10) % 3;
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

            // Dun - Mostly effects red pigment. Looks like what I would call silver. Responsible for grey brahmans, guzerat, and hungarian grey.
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

            // Dexter Dun - chocolate
            if (gene[10] == 2 && gene[11] == 2) {
                blackHue = Colouration.mixHueComponent(blackHue, 0.1F, 0.3F);
                blackSaturation = blackSaturation + ((1.0F - blackSaturation) * 0.45F);
                blackBrightness = blackBrightness + ((1.0F - blackBrightness) * 0.25F);
            }

            // Dilution. What you usually see in highlands, murrey grey, simmental and other such dun/yellow/grey cattle
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
