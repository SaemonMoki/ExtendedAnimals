package mokiyoki.enhancedanimals.renderer.textures;

import mokiyoki.enhancedanimals.entity.EnhancedTurtle;
import mokiyoki.enhancedanimals.renderer.texture.TextureGrouping;
import mokiyoki.enhancedanimals.renderer.texture.TexturingType;

public class TurtleTexture {

    public static void calculateTurtleTextures(EnhancedTurtle turtle, int[] gene, char[] uuid) {
        boolean tortishell = gene[10]==2 || gene[11]==2;
        boolean nonaxanthic = gene[2] == 1 || gene[3] == 1;
        String axanthic = nonaxanthic ? "nonaxanthic/" : "axanthic/";
        String eyeColour;

        TextureGrouping parentGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        TextureGrouping baseColour = new TextureGrouping(TexturingType.MERGE_GROUP);
        parentGroup.addGrouping(baseColour);

        if (gene[0]==1 || gene[1]==1) {
            createBaseColour(turtle, gene, nonaxanthic, tortishell ? -1 : 0, baseColour);

            if (gene[72]>=2 && gene[73]>=2) {
                createCountershading(turtle, gene, nonaxanthic, tortishell, baseColour);
            }

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
                    if (gene[32]==2 || gene[33]==2) {
                        pattern = tortishell ? "tortishell_scale1" : "scale";
                    } else {
                        pattern = "clown";
                    }
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

                int pigmentHueMod = getPigmentHueMod(gene);

                createPatternColour(turtle, nonaxanthic, patternColourGroup, pigmentType, pigmentHueMod, pigmentFlag);

                if (tortishell) {
                    TextureGrouping brindleGroup = new TextureGrouping(TexturingType.MASK_GROUP);
                    TextureGrouping brindleShapeGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                    TextureGrouping brindleColourGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                    turtle.addIndividualTextureToAnimalTextureGrouping(brindleShapeGroup, TexturingType.MERGE_GROUP, "tortishell/tortishell.png");
                    brindleGroup.addGrouping(brindleShapeGroup);

                    if (pigmentHueMod-5 < 0) {
                        createBaseColour(turtle, gene, nonaxanthic, pigmentHueMod+2, brindleColourGroup);
                        if (pigmentHueMod!=0) {
                            float a = (pigmentHueMod/5.0F) * 200F;
                            int argb = ((int)a) << 24 | 255 << 16 | 255 << 8 | 255;
                            TextureGrouping colour = new TextureGrouping(TexturingType.APPLY_RGBA);
                            if (nonaxanthic) {
                                turtle.addTextureToAnimalTextureGrouping(colour, TexturingType.APPLY_RGBA, "pattern/colour/nonaxanthic/"+ pigmentType + "0.png", "a"+ pigmentType + pigmentHueMod, argb);
                            } else {
                                turtle.addTextureToAnimalTextureGrouping(colour, TexturingType.APPLY_RGBA, "pattern/colour/axanthic/" + pigmentType + "0.png", "ax"+ pigmentType + pigmentHueMod, argb);
                            }
                            if (pigmentFlag) {
                                if (nonaxanthic) {
                                    turtle.addTextureToAnimalTextureGrouping(colour, TexturingType.APPLY_RGBA, "pattern/colour/nonaxanthic/melanin0.png", "a"+ pigmentType + pigmentHueMod, argb);
                                } else {
                                    turtle.addTextureToAnimalTextureGrouping(colour, TexturingType.APPLY_RGBA, "pattern/colour/axanthic/melanin0.png", "ax"+ pigmentType + pigmentHueMod, argb);
                                }
                            }
                            brindleColourGroup.addGrouping(colour);
                        }
                    } else {
                        createPatternColour(turtle, nonaxanthic, brindleColourGroup, pigmentType, 0, pigmentFlag);
                    }
                    brindleGroup.addGrouping(brindleColourGroup);

                    patternColourGroup.addGrouping(brindleGroup);
                }

                if (gene[4]!=gene[5]) {
                    pigmentType = getCharcoalPigmentType(pigmentHueMod);
                    turtle.addTextureToAnimalTextureGrouping(patternColourGroup, TexturingType.APPLY_RGBA, "pattern/colour/" + pigmentType + ".png", pigmentType, 128 << 24 | 255 << 16 | 255 << 8 | 255);
                    eyeColour = "black";
                } else {
                    turtle.addDelimiter("nc");
                    eyeColour = nonaxanthic?"grey":"navy";
                }
            } else {
                String pigmentType = getCharcoalPigmentType(getPigmentHueMod(gene));
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
            piebald(turtle, gene, uuid, parentGroup);
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

    private static void piebald(EnhancedTurtle turtle, int[] gene, char[] uuid, TextureGrouping parentGroup) {
        int piebald;
        TextureGrouping spots = new TextureGrouping(TexturingType.MASK_GROUP);

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
            piebald = piebald%11;
        }

        turtle.addTextureToAnimalTextureGrouping(spots, "spots/piebald/" + piebald + ".png", String.valueOf(piebald));

        turtle.addTextureToAnimalTextureGrouping(spots, "spots/white1.png");

        parentGroup.addGrouping(spots);
    }

    private static void createCountershading(EnhancedTurtle turtle, int[] gene, boolean nonaxanthic, boolean tortishell, TextureGrouping baseColour) {
        TextureGrouping group = new TextureGrouping(TexturingType.MASK_GROUP);
        TextureGrouping mask = new TextureGrouping(TexturingType.MERGE_GROUP);
        TextureGrouping colour = new TextureGrouping(TexturingType.MERGE_GROUP);

        switch (gene[72]) {
            case 3 -> turtle.addTextureToAnimalTextureGrouping(mask, "base/countershaded.png", "c");
            case 4 -> turtle.addTextureToAnimalTextureGrouping(mask, "base/scalecountershaded.png", "r");
            default -> turtle.addTextureToAnimalTextureGrouping(mask, "base/crispcountershaded.png", "r");
        }

        if (gene[72] != gene[73]) {
            mask.setTexturingType(TexturingType.AVERAGE_GROUP);
            switch (gene[73]) {
                case 3 -> turtle.addTextureToAnimalTextureGrouping(mask, "base/countershaded.png", "c");
                case 4 -> turtle.addTextureToAnimalTextureGrouping(mask, "base/scalecountershaded.png", "r");
                default -> turtle.addTextureToAnimalTextureGrouping(mask, "base/crispcountershaded.png", "r");
            }
        }

        createBaseColour(turtle, gene, nonaxanthic, tortishell ? -1 : 0, colour);

        group.addGrouping(mask);
        group.addGrouping(colour);
        baseColour.addGrouping(group);
    }

    private static void createBaseColour(EnhancedTurtle turtle, int[] gene, boolean nonaxanthic, int boost, TextureGrouping baseColour) {
        TextureGrouping colour = new TextureGrouping(TexturingType.MERGE_GROUP);
        if (nonaxanthic) {
            int hueModifier = getBaseHueModifier(gene);

            if (hueModifier+boost <= 4) {
                hueModifier = Math.min(Math.max(hueModifier + boost, 0), 10);
            } else {
                hueModifier = 10;
            }

            turtle.addTextureToAnimalTextureGrouping(colour, "base/nonaxanthic/yellow" + hueModifier/2 + ".png", "nx" + hueModifier/2);
            if (hueModifier%2!=0) {
                turtle.addTextureToAnimalTextureGrouping(colour, TexturingType.APPLY_RGBA, "base/nonaxanthic/yellow" + ((hueModifier/2)+1) + ".png", "h", 128 << 24 | 255 << 16 | 255 << 8 | 255);
            }
        } else {
            if (boost==-2) {
                turtle.addTextureToAnimalTextureGrouping(colour, "base/axanthic/white0.png", "w0");
            } else {
                turtle.addTextureToAnimalTextureGrouping(colour, "base/axanthic/white1.png", "w1");
                if (boost == -1) {
                    turtle.addTextureToAnimalTextureGrouping(colour, TexturingType.APPLY_RGBA, "base/axanthic/white0.png", "h", 128 << 24 | 255 << 16 | 255 << 8 | 255);
                }
            }
        }
        baseColour.addGrouping(colour);
    }

    private static int getBaseHueModifier(int[] gene) {
        int hueModifier = 2;
        for (int i = 34; i < 78; i+=2) {
            if (i==50) i = 74;
            if (gene[i]==2 && gene[i+1]==2) {
                hueModifier++;
            }
        }
        return hueModifier;
    }

    private static void createPatternColour(EnhancedTurtle turtle, boolean nonaxanthic, TextureGrouping patternColourGroup, String pigmentType, int hueMod, boolean pigmentFlag) {
        TextureGrouping colour = new TextureGrouping(TexturingType.MERGE_GROUP);
        if (hueMod > 7) hueMod = 7;
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

    private static int getPigmentHueMod(int[] gene) {
        int hueMod = 0;
        for (int i = 50; i < 66; i+=2) {
            if (gene[i]==2 && gene[i+1]==2) {
                hueMod++;
            }
        }
        return hueMod;
    }
}
