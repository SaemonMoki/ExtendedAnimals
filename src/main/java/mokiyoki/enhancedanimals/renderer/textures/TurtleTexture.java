package mokiyoki.enhancedanimals.renderer.textures;

import mokiyoki.enhancedanimals.entity.EnhancedTurtle;
import mokiyoki.enhancedanimals.renderer.texture.TextureGrouping;
import mokiyoki.enhancedanimals.renderer.texture.TexturingType;

public class TurtleTexture {

    /*
     * Slot counts per section of the cache key.
     *
     * Every turtle writes the same number of fields regardless of its genes: the key is a flat
     * string whose only structure is the delimiter positions, so a section that writes fewer
     * fields shifts every later section left and lets two different genetics compile to the same
     * key. A branch that skips a section reserves its slots instead.
     */
    private static final int BASE_COLOUR_SLOTS      = 2;
    /** the two countershading masks, then the base colour under them. */
    private static final int COUNTERSHADING_SLOTS   = 2 + BASE_COLOUR_SLOTS;
    private static final int PATTERN_CUTOUT_SLOTS   = 2;
    /** the pigment, its melanin overlay, and the charcoal overlay. */
    private static final int PATTERN_COLOUR_SLOTS   = 3;
    /** the widest of the two brindle colour paths: a base colour plus a two layer tint. */
    private static final int TORTISHELL_COLOUR_SLOTS = BASE_COLOUR_SLOTS + 2;
    private static final int TORTISHELL_SLOTS       = 1 + TORTISHELL_COLOUR_SLOTS;
    /** everything a non albino turtle writes, including the eye marker and albino overlay. */
    private static final int COLOURED_SLOTS         = BASE_COLOUR_SLOTS + COUNTERSHADING_SLOTS
            + PATTERN_CUTOUT_SLOTS + PATTERN_COLOUR_SLOTS + TORTISHELL_SLOTS + 2;
    private static final int PIEBALD_SLOTS          = 2;

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
            } else {
                turtle.addSkippedSlots(COUNTERSHADING_SLOTS);
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
                turtle.layer(patternCutoutGroup).texture("pattern/" + pattern + ".png").keyedAs(pattern).add();
                if (gene[32]!=1 || gene[33]!=1) {
                    if (gene[32]==2 || gene[33]==2) {
                        pattern = tortishell ? "tortishell_scale1" : "scale";
                    } else {
                        pattern = "clown";
                    }
                    turtle.layer(patternCutoutGroup).texture("pattern/" + pattern + ".png").keyedAs(pattern).add();
                } else {
                    turtle.addSkippedSlot();
                }
                patternGroup.addGrouping(patternCutoutGroup);
            } else {
                turtle.addDelimiter("s");
                turtle.addSkippedSlots(PATTERN_CUTOUT_SLOTS - 1);
            }

            boolean pigmentFlag = false;
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

            createPatternColour(turtle, patternColourGroup, nonaxanthic, pigmentType, pigmentHueMod, gene, pigmentFlag, false);

            if (tortishell) {
                TextureGrouping brindleGroup = new TextureGrouping(TexturingType.MASK_GROUP);
                TextureGrouping brindleColourGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                turtle.layer(brindleGroup).texture("tortishell/tortishell.png").as(TexturingType.MERGE_GROUP).add();

                if (pigmentHueMod-5 < 0) {
                    createBaseColour(turtle, gene, nonaxanthic, pigmentHueMod+2, brindleColourGroup);
                    if (pigmentHueMod!=0) {
                        float a = (pigmentHueMod/5.0F) * 200F;
                        int argb = ((int)a) << 24 | 255 << 16 | 255 << 8 | 255;
                        TextureGrouping colour = new TextureGrouping(TexturingType.APPLY_RGBA);
                        if (nonaxanthic) {
                            turtle.layer(colour).texture("pattern/colour/nonaxanthic/"+ pigmentType + "0.png").tinted(TexturingType.APPLY_RGBA, argb).keyedAs("a"+ pigmentType + pigmentHueMod).add();
                        } else {
                            turtle.layer(colour).texture("pattern/colour/axanthic/" + pigmentType + "0.png").tinted(TexturingType.APPLY_RGBA, argb).keyedAs("ax"+ pigmentType + pigmentHueMod).add();
                        }
                        if (pigmentFlag) {
                            if (nonaxanthic) {
                                turtle.layer(colour).texture("pattern/colour/nonaxanthic/melanin0.png").tinted(TexturingType.APPLY_RGBA, argb).keyedAs("a"+ pigmentType + pigmentHueMod).add();
                            } else {
                                turtle.layer(colour).texture("pattern/colour/axanthic/melanin0.png").tinted(TexturingType.APPLY_RGBA, argb).keyedAs("ax"+ pigmentType + pigmentHueMod).add();
                            }
                        } else {
                            turtle.addSkippedSlot();
                        }
                        brindleColourGroup.addGrouping(colour);
                    } else {
                        turtle.addSkippedSlots(TORTISHELL_COLOUR_SLOTS - BASE_COLOUR_SLOTS);
                    }
                } else {
                    createPatternColour(turtle, brindleColourGroup, nonaxanthic, pigmentType, gene[4]==1&&gene[5]==1 ? 0 : pigmentHueMod, gene, pigmentFlag, true);
                    turtle.addSkippedSlots(TORTISHELL_COLOUR_SLOTS - PATTERN_COLOUR_SLOTS);
                }
                brindleGroup.addGrouping(brindleColourGroup);

                patternColourGroup.addGrouping(brindleGroup);
            } else {
                turtle.addSkippedSlots(TORTISHELL_SLOTS);
            }

            if (gene[4]==2 || gene[5]==2) {
                eyeColour = "black";
                turtle.addSkippedSlot();
            } else {
                turtle.addDelimiter("nch");
                eyeColour = nonaxanthic?"grey":"navy";
            }

            patternGroup.addGrouping(patternColourGroup);

            parentGroup.addGrouping(patternGroup);

            if (gene[70]==2 && gene[71]==2) {
                turtle.layer(parentGroup).texture("base/axanthic/albino.png").tinted(TexturingType.APPLY_RGBA, 100 << 24 | 255 << 16 | 255 << 8 | 255).keyedAs("l").add();
            } else {
                turtle.addSkippedSlot();
            }
        } else {
            turtle.layer(baseColour).texture("base/" + axanthic + "albino.png").keyedAs(nonaxanthic?"anx":"aax").add();
            turtle.addSkippedSlots(COLOURED_SLOTS - 1);
            eyeColour = nonaxanthic ? "blue" : "pink";
        }

        if (gene[6] == 2 && gene[7] == 2) {
            piebald(turtle, gene, uuid, parentGroup);
        } else {
            turtle.addSkippedSlots(PIEBALD_SLOTS);
        }

        TextureGrouping detailGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        turtle.layer(detailGroup).texture("eyes/" + eyeColour + ".png").keyedAs(eyeColour).add();
        if (gene[70]==2 && gene[71]==2) {
            turtle.layer(detailGroup).texture("eyes/pink.png").tinted(TexturingType.APPLY_RGBA, 160 << 24 | 255 << 16 | 255 << 8 | 255).keyedAs("l").add();
        } else {
            turtle.addSkippedSlot();
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

        turtle.layer(spots).texture("spots/piebald/" + piebald + ".png").keyedAs(String.valueOf(piebald)).add();

        turtle.layer(spots).texture("spots/white1.png").add();

        parentGroup.addGrouping(spots);
    }

    private static void createCountershading(EnhancedTurtle turtle, int[] gene, boolean nonaxanthic, boolean tortishell, TextureGrouping baseColour) {
        TextureGrouping group = new TextureGrouping(TexturingType.MASK_GROUP);
        TextureGrouping mask = new TextureGrouping(TexturingType.MERGE_GROUP);
        TextureGrouping colour = new TextureGrouping(TexturingType.MERGE_GROUP);

        //scale and crisp used to share the key "r" while loading different textures
        switch (gene[72]) {
            case 3 -> turtle.layer(mask).texture("base/countershaded.png").keyedAs("c").add();
            case 4 -> turtle.layer(mask).texture("base/scalecountershaded.png").keyedAs("s").add();
            default -> turtle.layer(mask).texture("base/crispcountershaded.png").keyedAs("r").add();
        }

        if (gene[72] != gene[73]) {
            mask.setTexturingType(TexturingType.AVERAGE_GROUP);
            switch (gene[73]) {
                case 3 -> turtle.layer(mask).texture("base/countershaded.png").keyedAs("c").add();
                case 4 -> turtle.layer(mask).texture("base/scalecountershaded.png").keyedAs("s").add();
                default -> turtle.layer(mask).texture("base/crispcountershaded.png").keyedAs("r").add();
            }
        } else {
            turtle.addSkippedSlot();
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

            turtle.layer(colour).texture("base/nonaxanthic/yellow" + hueModifier/2 + ".png").keyedAs("nx" + hueModifier/2).add();
            if (hueModifier%2!=0) {
                turtle.layer(colour).texture("base/nonaxanthic/yellow" + ((hueModifier/2)+1) + ".png").tinted(TexturingType.APPLY_RGBA, 128 << 24 | 255 << 16 | 255 << 8 | 255).keyedAs("h").add();
            } else {
                turtle.addSkippedSlot();
            }
        } else {
            if (boost==-2) {
                turtle.layer(colour).texture("base/axanthic/white0.png").keyedAs("w0").add();
                turtle.addSkippedSlot();
            } else {
                turtle.layer(colour).texture("base/axanthic/white1.png").keyedAs("w1").add();
                if (boost == -1) {
                    turtle.layer(colour).texture("base/axanthic/white0.png").tinted(TexturingType.APPLY_RGBA, 128 << 24 | 255 << 16 | 255 << 8 | 255).keyedAs("h").add();
                } else {
                    turtle.addSkippedSlot();
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

    private static void createPatternColour(EnhancedTurtle turtle, TextureGrouping patternColourGroup, boolean nonaxanthic, String pigmentType, int hueMod, int[] gene, boolean pigmentFlag, boolean secondaryColour) {
        TextureGrouping colour = new TextureGrouping(TexturingType.MERGE_GROUP);

        if (gene[4]==1 || gene[5]==1 || secondaryColour) {
            if (hueMod > 7) hueMod = 7;
            if (gene[4]==2 || gene[5]==2) {
                if (gene[4]==gene[5]) {
                    hueMod = (hueMod+1)/2;
                } else {
                    hueMod = (hueMod+1)/3;
                }
            }
            if (nonaxanthic) {
                turtle.layer(colour).texture("pattern/colour/nonaxanthic/" + pigmentType + hueMod + ".png").keyedAs("a" + pigmentType + hueMod).add();
            } else {
                turtle.layer(colour).texture("pattern/colour/axanthic/" + pigmentType + hueMod + ".png").keyedAs("ax" + pigmentType + hueMod).add();
            }
            if (pigmentFlag) {
                if (nonaxanthic) {
                    turtle.layer(colour).texture("pattern/colour/nonaxanthic/melanin" + hueMod + ".png").tinted(TexturingType.APPLY_RGBA, 128 << 24 | 255 << 16 | 255 << 8 | 255).keyedAs("a" + pigmentType + hueMod).add();
                } else {
                    turtle.layer(colour).texture("pattern/colour/axanthic/melanin" + hueMod + ".png").tinted(TexturingType.APPLY_RGBA, 128 << 24 | 255 << 16 | 255 << 8 | 255).keyedAs("ax" + pigmentType + hueMod).add();
                }
            } else {
                turtle.addSkippedSlot();
            }

            if (gene[4] != gene[5]) {
                pigmentType = getCharcoalPigmentType(hueMod);
                turtle.layer(colour).texture("pattern/colour/" + pigmentType + ".png").tinted(TexturingType.APPLY_RGBA, 128 << 24 | 255 << 16 | 255 << 8 | 255).keyedAs(pigmentType).add();
            } else {
                turtle.addSkippedSlot();
            }
        } else {
            pigmentType = getCharcoalPigmentType(getPigmentHueMod(gene));
            turtle.layer(colour).texture("pattern/colour/"+ pigmentType + ".png").keyedAs("c"+pigmentType).add();
            turtle.addSkippedSlots(PATTERN_COLOUR_SLOTS - 1);
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
