package mokiyoki.enhancedanimals.renderer.textures;

import mokiyoki.enhancedanimals.entity.EnhancedTurtle;
import mokiyoki.enhancedanimals.renderer.texture.TextureGrouping;
import mokiyoki.enhancedanimals.renderer.texture.TexturingType;
import org.jetbrains.annotations.NotNull;

public class TurtleTexture {
    private static final String[] TURTLE_TEXTURES_BASE = new String[] {
            "normal_turtle.png", "albino_turtle.png", "axanthic_turtle.png", "axanthic_albino_turtle.png", "black_turtle.png", "het_melanised_normal.png", "axanthic_black_turtle.png", "het_melanised_axanthic.png"
    };

    public static void calculateTurtleTextures(EnhancedTurtle turtle, int[] gene, char[] uuid) {
        int piebald = 0;

        boolean nonaxanthic = gene[2] == 1 || gene[3] == 1;
        String axanthic = nonaxanthic ? "nonaxanthic/" : "axanthic/";
        String eyeColour;

        TextureGrouping parentGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        TextureGrouping baseColour = new TextureGrouping(TexturingType.MERGE_GROUP);
        parentGroup.addGrouping(baseColour);

        if (gene[0]==1 || gene[1]==1) {
            createBaseColour(turtle, gene, nonaxanthic, gene[72]==2 || gene[73]==2 ? -1 : 0, baseColour);

            TextureGrouping patternGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
            TextureGrouping patternColourGroup = new TextureGrouping(TexturingType.MERGE_GROUP);

            if (gene[30] != 2 && gene[31] != 2) {
                patternGroup.setTexturingType(TexturingType.CUTOUT_GROUP);
                TextureGrouping patternCutoutGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                String pattern = "patternless";
                if (gene[30]==1 || gene[31]==1) {
                    pattern = "light_belly";
                } else if (gene[30]==3 || gene[31]==3) {
                    pattern = "flame";
                }
                turtle.addTextureToAnimalTextureGrouping(patternCutoutGroup, "pattern/" + pattern + ".png", pattern);
                if (gene[32]!=1 || gene[33]!=1) {
                    pattern = gene[32]==2 || gene[33]==2 ? "scale" : "clown";
                    turtle.addTextureToAnimalTextureGrouping(patternCutoutGroup, "pattern/" + pattern + ".png", pattern);
                }
                patternGroup.addGrouping(patternCutoutGroup);
            } else {
                turtle.addDelimiter("s");
            }

            boolean pigmentFlag = false;
            if (gene[4] == 1 || gene[5] == 1) {
                String pigmentType = "green";
                if (gene[66] == 2 || gene[67] == 2) {
                    if (gene[68] == 2 || gene[69] == 2) {
                        pigmentType = "melanin";
                    } else {
                        pigmentFlag = true;
                    }
                } else if (gene[68] == 2 || gene[69] == 2) {
                    pigmentFlag = true;
                }

                int hueMod = getHueMod(gene);

                createPatternColour(turtle, nonaxanthic, patternColourGroup, pigmentType, hueMod, pigmentFlag);

                if (gene[72]==2 || gene[73]==2) {
                    TextureGrouping brindleGroup = new TextureGrouping(TexturingType.MASK_GROUP);
                    TextureGrouping brindleShapeGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                    TextureGrouping brindleColourGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                    turtle.addIndividualTextureToAnimalTextureGrouping(brindleShapeGroup, TexturingType.MERGE_GROUP, "tortishell/tortishell.png");
                    brindleGroup.addGrouping(brindleShapeGroup);

                    if (hueMod-3 < 0) {
                        createBaseColour(turtle, gene, nonaxanthic, hueMod+1, brindleColourGroup);
                    } else {
                        createPatternColour(turtle, nonaxanthic, brindleColourGroup, pigmentType, 0, pigmentFlag);
                    }
                    brindleGroup.addGrouping(brindleColourGroup);

                    patternColourGroup.addGrouping(brindleGroup);
                }

                if (gene[4]!=gene[5]) {
                    pigmentType = getCharcoalPigmentType(hueMod);
                    turtle.addTextureToAnimalTextureGrouping(patternColourGroup, TexturingType.APPLY_RGBA, "pattern/colour/" + pigmentType + ".png", pigmentType, 128 << 24 | 255 << 16 | 255 << 8 | 255);
                    eyeColour = "black";
                } else {
                    turtle.addDelimiter("nc");
                    eyeColour = nonaxanthic?"grey":"navy";
                }
            } else {
                String pigmentType = getCharcoalPigmentType(getHueMod(gene));
                turtle.addTextureToAnimalTextureGrouping(patternColourGroup, "pattern/colour/"+ pigmentType + ".png", "c"+pigmentType);
                eyeColour = "black";
            }

            patternGroup.addGrouping(patternColourGroup);

            parentGroup.addGrouping(patternGroup);

            if (gene[70]==2 && gene[71]==2) {
                turtle.addTextureToAnimalTextureGrouping(parentGroup, TexturingType.APPLY_RGBA, "base/axanthic/albino.png", "l", 100 << 24 | 255 << 16 | 255 << 8 | 255);
            }
        } else {
            turtle.addTextureToAnimalTextureGrouping(baseColour, "base/" + axanthic + "albino.png", nonaxanthic?"anx":"aax");
            eyeColour = nonaxanthic ? "blue" : "pink";
        }

        if (gene[6] == 2 && gene[7] == 2) {
            TextureGrouping spots = new TextureGrouping(TexturingType.MERGE_GROUP);
            if ( Character.isDigit(uuid[5]) ){
                piebald = 1 + (uuid[5]-48);
            } else {
                char d = uuid[5];

                switch (d) {
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
                    default:
                        piebald = 0;
                }
            }

            if (gene[8] == 2 && gene[9] == 2) {
                piebald = 1;
            } else {
                piebald = piebald%4;
            }


            turtle.addTextureToAnimalTextureGrouping(spots, "spots/piebald/" + piebald + ".png", String.valueOf(piebald));
            parentGroup.addGrouping(spots);
        } else {
            turtle.addDelimiter();
        }

        TextureGrouping detailGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        turtle.addTextureToAnimalTextureGrouping(detailGroup, "eyes/" + eyeColour + ".png", eyeColour);
        if (gene[70]==2 && gene[71]==2) {
            turtle.addTextureToAnimalTextureGrouping(detailGroup, TexturingType.APPLY_RGBA, "eyes/pink.png", "l", 160 << 24 | 255 << 16 | 255 << 8 | 255);
        }
        parentGroup.addGrouping(detailGroup);
        turtle.setTextureGrouping(parentGroup);
    }

    private static void createBaseColour(EnhancedTurtle turtle, int[] gene, boolean nonaxanthic, int boost, TextureGrouping baseColour) {
        if (nonaxanthic) {
            int hueModifier = 0;
            for (int i = 34; i < 50; i+=2) {
                if (gene[i]==2 && gene[i+1]==2) {
                    hueModifier++;
                }
            }

            if (hueModifier+boost <= 4) {
                hueModifier = Math.max(hueModifier + boost, 0);
            } else {
                hueModifier = 8;
            }

            turtle.addTextureToAnimalTextureGrouping(baseColour, "base/nonaxanthic/yellow" + hueModifier/2 + ".png", "nx" + hueModifier/2);
            if (hueModifier%2!=0) {
                turtle.addTextureToAnimalTextureGrouping(baseColour, TexturingType.APPLY_RGBA, "base/nonaxanthic/yellow" + ((hueModifier/2)+1) + ".png", "h", 128 << 24 | 255 << 16 | 255 << 8 | 255);
            }
        } else {
            turtle.addTextureToAnimalTextureGrouping(baseColour, "base/axanthic/white.png", "w");
        }
    }

    private static void createPatternColour(EnhancedTurtle turtle, boolean nonaxanthic, TextureGrouping patternColourGroup, String pigmentType, int hueMod, boolean pigmentFlag) {
        TextureGrouping colour = new TextureGrouping(TexturingType.MERGE_GROUP);
        if (nonaxanthic) {
            turtle.addTextureToAnimalTextureGrouping(colour, "pattern/colour/nonaxanthic/"+ pigmentType + hueMod + ".png", "a"+ pigmentType + hueMod);
        } else {
            turtle.addTextureToAnimalTextureGrouping(colour, "pattern/colour/axanthic/" + pigmentType + hueMod + ".png", "ax"+ pigmentType + hueMod);
        }
        if (pigmentFlag) {
            if (nonaxanthic) {
                turtle.addTextureToAnimalTextureGrouping(colour, TexturingType.APPLY_RGBA, "pattern/colour/nonaxanthic/melanin" + hueMod + ".png", "a"+ pigmentType + hueMod, 128 << 24 | 255 << 16 | 255 << 8 | 255);
            } else {
                turtle.addTextureToAnimalTextureGrouping(colour, TexturingType.APPLY_RGBA, "pattern/colour/axanthic/melanin" + hueMod + ".png", "ax"+ pigmentType + hueMod, 128 << 24 | 255 << 16 | 255 << 8 | 255);
            }
        }
        patternColourGroup.addGrouping(colour);
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

    private static int getHueMod(int[] gene) {
        int hueMod = 0;
        for (int i = 50; i < 66; i+=2) {
            if (gene[i]==2 && gene[i+1]==2) {
                hueMod++;
            }
        }
        return hueMod;
    }
}
