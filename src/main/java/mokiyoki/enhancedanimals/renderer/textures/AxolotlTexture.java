package mokiyoki.enhancedanimals.renderer.textures;

import mokiyoki.enhancedanimals.entity.EnhancedAxolotl;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import mokiyoki.enhancedanimals.renderer.texture.Cutout;
import mokiyoki.enhancedanimals.renderer.texture.TextureGroup;
import mokiyoki.enhancedanimals.renderer.texture.Mask;
import mokiyoki.enhancedanimals.renderer.texture.TextureBuilder;

import static mokiyoki.enhancedanimals.entity.util.VariationKeys.*;

public class AxolotlTexture {

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
        buildAxolotlTexture(TextureBuilder.of(axolotl, gene));
    }

    static void buildAxolotlTexture(TextureBuilder axolotlTexture) {
        int gills = (axolotlTexture.isHomoFor(34, 2) ? 1 : 0) + (axolotlTexture.isHomoFor(36, 2) ? 1 : 0);
        int base = axolotlTexture.eitherAllelesHave(8, 1) && axolotlTexture.eitherAllelesHave(2, 1) ? 1 : 0;
        int pattern = !axolotlTexture.eitherAllelesHave(8, 1) && axolotlTexture.eitherAllelesHave(0, 1) ? 1 : 0;
        int copper = axolotlTexture.eitherAllelesHave(6, 1) ? 0 : 1;
        int melanoid = axolotlTexture.isHomoFor(4, 2) ? 1 : 0;

        boolean hasPied = !axolotlTexture.eitherAllelesHave(12, 1);
        int pied = hasPied ? Math.max(0, axolotlTexture.sum(12) - 3) : 0;
        int piedStrength = hasPied ? Math.min(4, (int) ((axolotlTexture.sum(14) - 2) * 0.3)) : 0;
        int piedSplotchy = !hasPied ? 0 : axolotlTexture.max(16) >= 5 ? 2 : axolotlTexture.max(16) >= 3 ? 1 : 0;

        int gillsColour = axolotlTexture.isHomoFor(38, 1) ? 0 : Math.max(0, axolotlTexture.gene(40) - 1);
        int gillsColour2 = axolotlTexture.isHomoFor(38, 1) ? 0 : Math.max(0, axolotlTexture.gene(41) - 1);

        TextureGroup rootTextureGroup = axolotlTexture.root();
        rootTextureGroup.addAverageGroup(AXOLOTL_TEXTURES_GILLS[gillsColour][gills], AXOLOTL_TEXTURES_GILLS[gillsColour2][gills]);

        Mask body = rootTextureGroup.addMaskGroup();
        body.maskTexture().addTexture("alpha_mask.png");

        int eyeColour = calculateEyeColour(axolotlTexture, 2);

        createBaseLayer(axolotlTexture, body, eyeColour);

        if (axolotlTexture.eitherAllelesHave(0, 1)) {
            createMelaninLayer(axolotlTexture, body, piedStrength, copper, pattern, melanoid);
        }

        if (pied != 0 || !axolotlTexture.isHomoFor(18, 1) || axolotlTexture.eitherAllelesHave(42, 3)) {
            Mask spots = body.content().addMaskGroup();

            if (pied != 0) {
                spots.maskTexture().addTexture(AXOLOTL_TEXTURES_PIED[pied - 1][piedStrength][piedSplotchy]).flipIf(axolotlTexture.randomChance(AXOLOTL_PIED_FLIP, 50), headCubes, bodyCubes, tailCubes);
            }
            if (!axolotlTexture.eitherAllelesHave(18, 1)) {
                spots.maskTexture().pickRandomTexture(AXOLOTL_TEXTURES_BLAZE, AXOLOTL_BLAZE).flipIf(axolotlTexture.randomChance(AXOLOTL_BLAZE_FLIP, 50), headCubes);
            }

            if (!axolotlTexture.isHomoFor(42, 1)) {
                if (axolotlTexture.isHomoFor(42, 3)) {
                    addBrindle(axolotlTexture, piedStrength, spots.maskTexture(), true);
                } else {
                    addBrindle(axolotlTexture, piedStrength, spots.maskTexture(), false);
                }
            }

            spots.content().addTexture("natural.png");

            eyeColour = calculateEyeColour(axolotlTexture, 1);
            spots.content().addTexture("eye_left.png").rgb(eyeColour);
            spots.content().addTexture("eye_right.png").rgb(eyeColour);
        }

        if (axolotlTexture.eitherAllelesHave(44, 2)) rootTextureGroup.addAverageGroup(CHEEK_SPOTS[gillsColour], CHEEK_SPOTS[gillsColour2]);

        axolotlTexture.finishTexture();
    }

    private static void createBaseLayer(TextureBuilder axolotlTexture, Mask body, int eyeColour) {
        body.content().addTexture("patterning/colour/natural.png").dye();

        Mask yellowPigment = body.content().addMaskGroup();

        if (axolotlTexture.isHomoFor(2, 2)) {
            //axanthic
            if (axolotlTexture.isHomoFor(8, 2)) {
                yellowPigment.content().addTexture("patterning/colour/natural.png").dye();
            } else {
                yellowPigment.content().addTexture("patterning/colour/natural.png").dye();
            }
        } else if (axolotlTexture.isHomoFor(8, 2)) {
            //leucistic
            yellowPigment.content().addTexture("patterning/colour/natural.png").dye();
        } else {
            if (axolotlTexture.gene(46) == axolotlTexture.gene(47)) {
                yellowPigment.maskTexture().addTexture("patterning/base/"+getPatternShade(axolotlTexture, 46)+".png");
            } else {
                String textureA = getPatternShade(axolotlTexture, 46);
                String textureB = getPatternShade(axolotlTexture, 47);
                yellowPigment.maskTexture().addAverageGroup("patterning/base/"+textureA+".png", "patterning/base/"+textureB+".png");
            }

            float speckleStrength = 1.0F;
            for (int i = 48; i <= 53; i++) {
                if (axolotlTexture.gene(i)!= 2) speckleStrength = axolotlTexture.gene(i)== 3 ? speckleStrength - 0.1F : speckleStrength * 0.8F;
            }
            yellowPigment.maskTexture().addTexture("patterning/speckles/"+axolotlTexture.randomBoundedInt(AXOLOTL_PATTERNING, 3)+".png").flipIf(axolotlTexture.randomChance(AXOLOTL_PATTERNING_FLIP, 50), headCubes, bodyCubes, tailCubes).alpha(speckleStrength);

            yellowPigment.content().addTexture("patterning/colour/natural_xanthic.png").dye();
        }


        body.content().addTexture("eye_left.png").rgb(eyeColour);
        body.content().addTexture("eye_right.png").rgb(eyeColour);
    }

    private static String getPatternShade(TextureBuilder axolotlTexture, int gene) {
        return switch (axolotlTexture.gene(gene)) {
            case 2 -> "light_belly";
            case 3 -> "dilute";
            case 4 -> "light_belly_dilute";
            default -> "solid";
        };
    }

    private static void createMelaninLayer(TextureBuilder axolotlTexture, Mask body, int piedStrength, int copper, int pattern, int melanoid) {
        Cutout brindle = body.content().addCutoutGroup();
        if (!axolotlTexture.isHomoFor(42, 1)) addBrindle(axolotlTexture, piedStrength, brindle.cutoutTexture(), true);
        createNaturalShading(axolotlTexture, copper, pattern, melanoid, brindle);
        int eyeColour = calculateEyeColour(axolotlTexture, 3);
        brindle.content().addTexture("eye_left.png").rgb(eyeColour);
        brindle.content().addTexture("eye_right.png").rgb(eyeColour);
    }

    private static void createNaturalShading(TextureBuilder axolotlTexture, int copper, int pattern, int melanoid, Cutout brindle) {
        Mask eumelaninPigment = brindle.content().addMaskGroup();
        if (axolotlTexture.gene(46) == axolotlTexture.gene(47)) {
            eumelaninPigment.maskTexture().addTexture("patterning/base/"+getPatternShade(axolotlTexture, 46)+".png");
        } else {
            String textureA = getPatternShade(axolotlTexture, 46);
            String textureB = getPatternShade(axolotlTexture, 47);
            eumelaninPigment.maskTexture().addAverageGroup("patterning/base/"+textureA+".png", "patterning/base/"+textureB+".png");
        }
        eumelaninPigment.maskTexture().addTexture("patterning/speckles/"+axolotlTexture.randomBoundedInt(AXOLOTL_PATTERNING, 3)+".png").flipIf(axolotlTexture.randomChance(AXOLOTL_PATTERNING_FLIP, 50), headCubes, bodyCubes, tailCubes);
        eumelaninPigment.content().addTexture(AXOLOTL_TEXTURES_MELANIN[copper][pattern][melanoid]);
        createDarkSpeckles(axolotlTexture, copper, pattern, brindle);
    }

    private static void createDarkSpeckles(TextureBuilder axolotlTexture, int copper, int pattern, Cutout cutout) {
        Mask speckles = cutout.content().addMaskGroup();
        Mask speckleMask = speckles.maskTexture().addMaskGroup();
        if (axolotlTexture.gene(46) == axolotlTexture.gene(47)) {
            speckleMask.maskTexture().addTexture("patterning/base/"+getPatternShade(axolotlTexture, 46)+".png");
        } else {
            String textureA = getPatternShade(axolotlTexture, 46);
            String textureB = getPatternShade(axolotlTexture, 47);
            speckleMask.maskTexture().addAverageGroup("patterning/base/"+textureA+".png", "patterning/base/"+textureB+".png");
        }
        float speckleStrength = 1.0F;
        for (int i = 50; i <= 53; i++) {
            if (axolotlTexture.gene(i)!= 2) speckleStrength = axolotlTexture.gene(i)== 1 ? speckleStrength - 0.25F : speckleStrength * 0.5F;
        }
        speckleMask.content().addTexture("patterning/speckles/"+axolotlTexture.randomBoundedInt(AXOLOTL_PATTERNING, 3)+".png").flipIf(axolotlTexture.randomChance(AXOLOTL_PATTERNING_FLIP, 50), headCubes, bodyCubes, tailCubes).alpha(speckleStrength);

        speckles.content().addTexture(AXOLOTL_TEXTURES_MELANIN[copper][pattern][1]);
    }

    private static void addBrindle(TextureBuilder axolotlTexture, int piedStrength, TextureGroup parts, boolean complete) {
        int strength = piedStrength == 1 ? 2 : piedStrength == 3 ? 4 : piedStrength;
        boolean flipSolidParts = axolotlTexture.randomChance(AXOLOTL_BRINDLE[0], 50);

        if (axolotlTexture.randomChance(AXOLOTL_BRINDLE[1], 50)) {
            int first = addBrindlePart(axolotlTexture, parts, strength, 3, "head_left", headCubes, 0, flipSolidParts, complete);
            addBrindlePart(axolotlTexture, parts, strength, 6, "head_right", headCubes, first, flipSolidParts, complete);
        } else {
            int first = addBrindlePart(axolotlTexture, parts, strength, 6, "head_right", headCubes, 0, flipSolidParts, complete);
            addBrindlePart(axolotlTexture, parts, strength, 3, "head_left", headCubes, first, flipSolidParts, complete);
        }

        if (axolotlTexture.randomChance(AXOLOTL_BRINDLE[2], 50)) {
            int first = addBrindlePart(axolotlTexture, parts, strength, 9, "body_left", bodyCubes, 0, flipSolidParts, complete);
            addBrindlePart(axolotlTexture, parts, strength, 12, "body_right", bodyCubes, first, flipSolidParts, complete);
        } else {
            int first = addBrindlePart(axolotlTexture, parts, strength, 12, "body_right", bodyCubes, 0, flipSolidParts, complete);
            addBrindlePart(axolotlTexture, parts, strength, 9, "body_left", bodyCubes, first, flipSolidParts, complete);
        }

        addBrindlePart(axolotlTexture, parts, strength, 15, "tail", tailCubes, 0, flipSolidParts, complete);
    }

    private static int addBrindlePart(TextureBuilder axolotlTexture, TextureGroup parts, int strength, int key, String location, int[] cubes, int previous, boolean flipSolidParts, boolean complete) {
        int texture = axolotlTexture.randomChance(AXOLOTL_BRINDLE[key], 50) ? 0 : axolotlTexture.randomBoundedInt(AXOLOTL_BRINDLE[key + 1], 4) + 1;
        boolean mirror = axolotlTexture.randomChance(AXOLOTL_BRINDLE[key + 2], 50);
        if (texture != 0 && previous != 0 && (mirror ? texture + 1 : -(texture + 1)) == previous) {
            mirror = !mirror;
        }

        if (complete || texture == 0) {
            Cutout part = parts.addCutoutGroup();
            part.cutoutTexture().addTexture("brindle/" + strength + "/" + texture + ".png").flipIf(texture == 0 ? flipSolidParts : mirror, cubes);
            part.content().addTexture("brindle/" + location + ".png");
        }

        return mirror ? -(texture + 1) : texture + 1;
    }

    private static int calculateEyeColour(TextureBuilder textureBuilder, int layer) {
        return Colouration.getAxolotlEyes(calculateEyeColorPart(textureBuilder, 20, layer), calculateEyeColorPart(textureBuilder,21, layer));
    }

    private static float[] calculateEyeColorPart(TextureBuilder textureBuilder, int eyeVersion, int layer) {
        /**
         *      0 -> albino
         *      1 -> white spots
         *      2 -> yellow
         *      3 -> full colour
         */

        eyeVersion = textureBuilder.gene(eyeVersion);
        int colourA = textureBuilder.gene(22);
        int colourB = textureBuilder.gene(23);
        boolean isAxanthic = textureBuilder.isHomoFor(2, 2);

        //NormalEyes
        float[] eyeHSB = {0.75F, 0.5F, 0.25F};

        if (layer <= 2) {
            if (isAxanthic || layer == 0) {
                //Axanthic Albino (pinkish white)
                eyeHSB[0] = 0.95F;
                eyeHSB[2] = 0.8F;
            } else {
                if (layer<2) {
                    //Blue
                    eyeHSB[0] = 0.55F;
                    eyeHSB[1] = 0.75F;
                    eyeHSB[2] = 0.8F;
                } else {
                    //Gold Albino
                    eyeHSB[0] = 0.09F;
                    eyeHSB[1] = 0.75F;
                    eyeHSB[2] = 0.8F;
                }
            }

            return eyeHSB;
        }

        switch (eyeVersion) {
            case 2 -> {
                //DarkEyes
                eyeHSB = Colouration.mixAxolotlHue((float) (colourA-1) / 255, (float) (colourB-1) / 255);
                eyeHSB[2] *= 0.5F;
            }
            case 3 -> {
                //PigmentedEyes
                eyeHSB = Colouration.mixAxolotlHue((float) (colourA-1) / 255, (float) (colourB-1) / 255);
            }
            case 4 -> {
                //LightEyes
                eyeHSB = Colouration.mixAxolotlHue((float) (colourA-1) / 255, (float) (colourB-1) / 255);
                eyeHSB[2] = 1.0F;
            }
            case 5 -> {
                //PastelEyes
                eyeHSB = Colouration.mixAxolotlHue((float) (colourA-1) / 255, (float) (colourB-1) / 255);
                eyeHSB[1] *= 0.5F;
                eyeHSB[2] = 1.0F;
            }
            case 6 -> {
                //GlowEyes
                eyeHSB = Colouration.mixAxolotlHue((float) (colourA-1) / 255, (float) (colourB-1) / 255);
            }
            default -> {}


        }
        return eyeHSB;
    }
}
