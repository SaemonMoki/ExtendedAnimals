package mokiyoki.enhancedanimals.renderer.textures;

import mokiyoki.enhancedanimals.entity.EnhancedAxolotl;
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
        body.content().addTexture(AXOLOTL_TEXTURES_BASE[base]).dye();
        if (axolotlTexture.eitherAllelesHave(0, 1)) {
            Cutout brindle = body.content().addCutoutGroup();
            if (!axolotlTexture.isHomoFor(42, 1)) addBrindle(axolotlTexture, piedStrength, brindle.cutoutTexture());
            brindle.content().addTexture(AXOLOTL_TEXTURES_MELANIN[copper][pattern][melanoid]);
        }
        if (pied != 0) {
            body.content().addTexture(AXOLOTL_TEXTURES_PIED[pied - 1][piedStrength][piedSplotchy]).flipIf(axolotlTexture.randomChance(AXOLOTL_PIED, 50), headCubes, bodyCubes, tailCubes);
        }
        if (!axolotlTexture.eitherAllelesHave(18, 1)) {
            body.content().pickRandomTexture(AXOLOTL_TEXTURES_BLAZE, AXOLOTL_BLAZE[0]).flipIf(axolotlTexture.randomChance(AXOLOTL_BLAZE[1], 50), headCubes);
        }

        if (axolotlTexture.eitherAllelesHave(44, 2)) rootTextureGroup.addAverageGroup(CHEEK_SPOTS[gillsColour], CHEEK_SPOTS[gillsColour2]);

        TextureGroup eyes = rootTextureGroup.addMergeGroup();
        eyes.addTexture("eye_left.png").leftEyeColour();
        eyes.addTexture("eye_right.png").rightEyeColour();

        axolotlTexture.finishTexture();
    }

    private static void addBrindle(TextureBuilder axolotlTexture, int piedStrength, TextureGroup parts) {
        int strength = piedStrength == 1 ? 2 : piedStrength == 3 ? 4 : piedStrength;
        boolean flipSolidParts = axolotlTexture.randomChance(AXOLOTL_BRINDLE[0], 50);

        // TODO axolotls need to maintain which side the dark/light part is on

        if (axolotlTexture.randomChance(AXOLOTL_BRINDLE[1], 50)) {
            int first = addBrindlePart(axolotlTexture, parts, strength, 3, "head_left", headCubes, 0, flipSolidParts);
            addBrindlePart(axolotlTexture, parts, strength, 6, "head_right", headCubes, first, flipSolidParts);
        } else {
            int first = addBrindlePart(axolotlTexture, parts, strength, 6, "head_right", headCubes, 0, flipSolidParts);
            addBrindlePart(axolotlTexture, parts, strength, 3, "head_left", headCubes, first, flipSolidParts);
        }

        if (axolotlTexture.randomChance(AXOLOTL_BRINDLE[2], 50)) {
            int first = addBrindlePart(axolotlTexture, parts, strength, 9, "body_left", bodyCubes, 0, flipSolidParts);
            addBrindlePart(axolotlTexture, parts, strength, 12, "body_right", bodyCubes, first, flipSolidParts);
        } else {
            int first = addBrindlePart(axolotlTexture, parts, strength, 12, "body_right", bodyCubes, 0, flipSolidParts);
            addBrindlePart(axolotlTexture, parts, strength, 9, "body_left", bodyCubes, first, flipSolidParts);
        }

        addBrindlePart(axolotlTexture, parts, strength, 15, "tail", tailCubes, 0, flipSolidParts);
    }

    private static int addBrindlePart(TextureBuilder axolotlTexture, TextureGroup parts, int strength, int key, String location, int[] cubes, int previous, boolean flipSolidParts) {
        int texture = axolotlTexture.randomChance(AXOLOTL_BRINDLE[key], 50) ? 0 : axolotlTexture.randomBoundedInt(AXOLOTL_BRINDLE[key + 1], 4) + 1;
        boolean mirror = axolotlTexture.randomChance(AXOLOTL_BRINDLE[key + 2], 50);
        if (texture != 0 && previous != 0 && (mirror ? texture + 1 : -(texture + 1)) == previous) {
            mirror = !mirror;
        }

        Cutout part = parts.addCutoutGroup();
        part.cutoutTexture().addTexture("brindle/" + strength + "/" + texture + ".png").flipIf(texture == 0 ? flipSolidParts : mirror, cubes);
        part.content().addTexture("brindle/" + location + ".png");

        return mirror ? -(texture + 1) : texture + 1;
    }
}
