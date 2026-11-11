package mokiyoki.enhancedanimals.renderer.textures;

import mokiyoki.enhancedanimals.entity.EnhancedTurtle;
import mokiyoki.enhancedanimals.renderer.texture.Cutout;
import mokiyoki.enhancedanimals.renderer.texture.Mask;
import mokiyoki.enhancedanimals.renderer.texture.TextureBuilder;
import mokiyoki.enhancedanimals.renderer.texture.TextureGroup;

import static mokiyoki.enhancedanimals.entity.util.VariationKeys.*;

public class TurtleTexture {

    private static final int HALF_ALPHA = 128 << 24 | 255 << 16 | 255 << 8 | 255;

    private static final int[] turtleCubes = new int[] {
            1, 3, 3, 21, 3, 1, 1, 2,
            0, 3, 0, 6, 5, 6,
            0, 7, 37, 19, 20, 6,
            0, 31, 1, 11, 18, 3,
            0, 70, 33, 9, 18, 1,
            1, 27, 30, 27, 30, 13, 1, 5,
            1, 1, 12, 1, 23, 4, 1, 10
    };

    public static void calculateTurtleTextures(EnhancedTurtle turtle, int[] gene) {
        buildTurtleTexture(TextureBuilder.of(turtle, gene));
    }

    static void buildTurtleTexture(TextureBuilder turtleTexture) {
        boolean tortishell = turtleTexture.eitherAllelesHave(10, 2);
        boolean nonaxanthic = turtleTexture.eitherAllelesHave(2, 1);
        String axanthic = nonaxanthic ? "nonaxanthic/" : "axanthic/";
        String eyeColour;

        TextureGroup root = turtleTexture.root();
        TextureGroup baseColour = root.addMergeGroup();

        if (turtleTexture.eitherAllelesHave(0, 1)) {
            createBaseColour(turtleTexture, nonaxanthic, tortishell ? -1 : 0, baseColour);

            if (turtleTexture.gene(72) >= 2 && turtleTexture.gene(73) >= 2) {
                createCountershading(turtleTexture, nonaxanthic, tortishell, baseColour);
            }

            TextureGroup patternColourGroup;
            if (!turtleTexture.eitherAllelesHave(30, 2)) {
                Cutout patternGroup = root.addCutoutGroup();
                String pattern = "patternless";
                if (turtleTexture.eitherAllelesHave(30, 1)) {
                    pattern = "light_belly";
                } else if (turtleTexture.eitherAllelesHave(30, 3)) {
                    pattern = "flame";
                }
                patternGroup.cutoutTexture().addTexture("pattern/" + pattern + ".png").flipIf(turtleTexture.randomChance(TURTLE_PATTERN_FLIP, 50));
                if (!turtleTexture.isHomoFor(32, 1)) {
                    if (turtleTexture.eitherAllelesHave(32, 2)) {
                        pattern = tortishell ? "tortishell_scale1" : "scale";
                    } else {
                        pattern = "clown";
                    }
                    patternGroup.cutoutTexture().addTexture("pattern/" + pattern + ".png").flipIf(turtleTexture.randomChance(TURTLE_CLOWN_FLIP, 50));
                }
                patternColourGroup = patternGroup.content();
            } else {
                patternColourGroup = root.addMergeGroup();
            }

            boolean pigmentFlag = false;
            String pigmentType = "green";
            if (turtleTexture.eitherAllelesHave(66, 2)) {
                if (turtleTexture.eitherAllelesHave(68, 2)) {
                    pigmentType = "melanin";
                } else {
                    pigmentFlag = true;
                }
            } else if (turtleTexture.eitherAllelesHave(68, 2)) {
                pigmentFlag = true;
            }

            int pigmentHueMod = getPigmentHueMod(turtleTexture);

            createPatternColour(turtleTexture, patternColourGroup, nonaxanthic, pigmentType, pigmentHueMod, pigmentFlag, false);

            if (tortishell) {
                Mask brindleGroup = patternColourGroup.addMaskGroup();
                brindleGroup.maskTexture().addTexture("tortishell/tortishell.png").flipIf(turtleTexture.randomChance(TURTLE_TORTI_FLIP, 50));
                TextureGroup brindleColourGroup = brindleGroup.content();

                if (pigmentHueMod-5 < 0) {
                    createBaseColour(turtleTexture, nonaxanthic, pigmentHueMod+2, brindleColourGroup);
                    if (pigmentHueMod!=0) {
                        float a = (pigmentHueMod/5.0F) * 200F;
                        int argb = ((int)a) << 24 | 255 << 16 | 255 << 8 | 255;
                        brindleColourGroup.addTexture("pattern/colour/" + axanthic + pigmentType + "0.png").rgba(argb);
                    }
                } else {
                    createPatternColour(turtleTexture, brindleColourGroup, nonaxanthic, pigmentType, turtleTexture.isHomoFor(4, 1) ? 0 : pigmentHueMod, pigmentFlag, true);
                }
            }

            if (turtleTexture.eitherAllelesHave(4, 2)) {
                eyeColour = "black";
            } else {
                eyeColour = nonaxanthic?"grey":"navy";
            }

            root.addTextureIf(turtleTexture.isHomoFor(70, 2), "base/axanthic/albino.png").rgba(100 << 24 | 255 << 16 | 255 << 8 | 255);
        } else {
            baseColour.addTexture("base/" + axanthic + "albino.png");
            eyeColour = nonaxanthic ? "blue" : "pink";
        }

        if (turtleTexture.isHomoFor(6, 2)) {
            piebald(turtleTexture, root);
        }

        TextureGroup detailGroup = root.addMergeGroup("eyes/" + eyeColour + ".png");
        detailGroup.addTextureIf(turtleTexture.isHomoFor(70, 2), "eyes/pink.png").rgba(160 << 24 | 255 << 16 | 255 << 8 | 255);

        turtleTexture.finishTexture();
    }

    private static void piebald(TextureBuilder turtleTexture, TextureGroup root) {
        int piebald = 1 + turtleTexture.randomHex(TURTLE_PIEBALD);

        if (turtleTexture.variation().isLegacy()) {
            if (turtleTexture.isHomoFor(8, 2)) {
                piebald = 0;
            } else {
                if ((piebald - 1) % 4 == 0) piebald = (int) (piebald * 1.25F);
                piebald = (piebald - 1) % 4;
            }
        } else if (turtleTexture.isHomoFor(8, 2)) {
            piebald = 1;
        } else {
            piebald = piebald%11;
        }

        Mask spots = root.addMaskGroup();
        spots.maskTexture().addTexture("spots/piebald/" + piebald + ".png").flipIf(turtleTexture.randomChance(TURTLE_PIEBALD_FLIP, 50));
        spots.content().addTexture("spots/white1.png");
    }

    private static void createCountershading(TextureBuilder turtleTexture, boolean nonaxanthic, boolean tortishell, TextureGroup baseColour) {
        Mask group = baseColour.addMaskGroup();

        if (turtleTexture.gene(72) != turtleTexture.gene(73)) {
            group.maskTexture().addAverageGroup(countershading(turtleTexture.gene(72)), countershading(turtleTexture.gene(73)));
        } else {
            group.maskTexture().addTexture(countershading(turtleTexture.gene(72)));
        }

        createBaseColour(turtleTexture, nonaxanthic, tortishell ? -1 : 0, group.content());
    }

    private static String countershading(int allele) {
        return switch (allele) {
            case 3 -> "base/countershaded.png";
            case 4 -> "base/scalecountershaded.png";
            default -> "base/crispcountershaded.png";
        };
    }

    private static void createBaseColour(TextureBuilder turtleTexture, boolean nonaxanthic, int boost, TextureGroup baseColour) {
        TextureGroup colour = baseColour.addMergeGroup();
        if (nonaxanthic) {
            int hueModifier = getBaseHueModifier(turtleTexture);

            if (hueModifier+boost <= 4) {
                hueModifier = Math.min(Math.max(hueModifier + boost, 0), 10);
            } else {
                hueModifier = 10;
            }

            colour.addTexture("base/nonaxanthic/yellow" + hueModifier/2 + ".png");
            colour.addTextureIf(hueModifier%2!=0, "base/nonaxanthic/yellow" + ((hueModifier/2)+1) + ".png").rgba(HALF_ALPHA);
        } else {
            if (boost==-2) {
                colour.addTexture("base/axanthic/white0.png");
            } else {
                colour.addTexture("base/axanthic/white1.png");
                colour.addTextureIf(boost == -1, "base/axanthic/white0.png").rgba(HALF_ALPHA);
            }
        }
    }

    private static int getBaseHueModifier(TextureBuilder turtleTexture) {
        int hueModifier = 2;
        for (int i = 34; i < 78; i+=2) {
            if (i==50) i = 74;
            if (turtleTexture.isHomoFor(i, 2)) {
                hueModifier++;
            }
        }
        return hueModifier;
    }

    private static void createPatternColour(TextureBuilder turtleTexture, TextureGroup patternColourGroup, boolean nonaxanthic, String pigmentType, int hueMod, boolean pigmentFlag, boolean secondaryColour) {
        TextureGroup colour = patternColourGroup.addMergeGroup();
        String axanthic = nonaxanthic ? "nonaxanthic/" : "axanthic/";

        if (turtleTexture.eitherAllelesHave(4, 1) || secondaryColour) {
            if (hueMod > 7) hueMod = 7;
            if (turtleTexture.eitherAllelesHave(4, 2)) {
                if (turtleTexture.gene(4) == turtleTexture.gene(5)) {
                    hueMod = (hueMod+1)/2;
                } else {
                    hueMod = (hueMod+1)/3;
                }
            }
            colour.addTexture("pattern/colour/" + axanthic + pigmentType + hueMod + ".png");
            colour.addTextureIf(pigmentFlag, "pattern/colour/" + axanthic + "melanin" + hueMod + ".png").rgba(HALF_ALPHA);

            if (turtleTexture.gene(4) != turtleTexture.gene(5)) {
                colour.addTexture("pattern/colour/" + getCharcoalPigmentType(hueMod) + ".png").rgba(HALF_ALPHA);
            }
        } else {
            colour.addTexture("pattern/colour/" + getCharcoalPigmentType(getPigmentHueMod(turtleTexture)) + ".png");
        }
    }

    private static String getCharcoalPigmentType(int hueMod) {
        String pigmentType = "charcoal";
        if (hueMod >= 6) {
            pigmentType = "super_black";
        } else if (hueMod >= 3) {
            pigmentType = "black";
        }
        return pigmentType;
    }

    private static int getPigmentHueMod(TextureBuilder turtleTexture) {
        int hueMod = 0;
        for (int i = 50; i < 66; i+=2) {
            if (turtleTexture.isHomoFor(i, 2)) {
                hueMod++;
            }
        }
        return hueMod;
    }
}
