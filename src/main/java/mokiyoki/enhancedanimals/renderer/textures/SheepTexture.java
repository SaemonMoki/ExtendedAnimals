package mokiyoki.enhancedanimals.renderer.textures;

import mokiyoki.enhancedanimals.entity.EnhancedSheep;
import mokiyoki.enhancedanimals.renderer.texture.TextureGrouping;
import mokiyoki.enhancedanimals.renderer.texture.TexturingType;

public class SheepTexture {

    /*
     * Slot counts per section of the cache key.
     *
     * Every sheep writes the same number of fields regardless of its genes: the key is a flat
     * string whose only structure is the delimiter positions, so a section that writes fewer
     * fields shifts every later section left and lets two different genetics compile to the same
     * key. A branch that skips a section reserves its slots instead.
     */
    /** the two pattern layers that get averaged together. */
    private static final int PATTERN_SLOTS     = 2;
    /** ticked, mealy, roan, blaze, spots, pigmented head. */
    private static final int WHITE_SPOT_SLOTS  = 6;

    private static final String[] SHEEP_TEXTURES_MEALY = new String[] {
            "", "c_mealy.png", "c_mealy_male.png", "c_mealy_female.png"
    };
    private static final String[] SHEEP_TEXTURES_PATTERN = new String[] {
            "", "b_blackbelly_0.png", "b_blackandtan_0.png", "b_english_blue.png",
            "b_blackbelly_1.png", "b_blackbelly_2.png", "b_blackbelly_3.png", "b_blackbelly_4.png", "b_blackbelly_5.png",
            "b_blackandtan_1.png", "b_blackandtan_2.png",
            "b_blue_german.png", "b_light_blue.png", "b_paddington_blue.png", "b_solid.png"
    };

    private static final String[] SHEEP_TEXTURES_GREY = new String[] {
            "", "grey_0.png", "grey_1.png", "grey_2.png", "grey_3.png", "grey_4.png"
    };

    private static final String[] SHEEP_TEXTURES_SPOTS = new String[] {
            "", "spot_pied.png"
    };

    private static final String[] SHEEP_TEXTURES_BLAZE = new String[] {
            "", "c_najdi.png", "c_whiteextrems.png", "c_blaze.png"
    };

    private static final String[] SHEEP_TEXTURES_ROAN = new String[] {
            "", "c_roan.png"
    };
    private static final String[][] SHEEP_TEXTURES_PIGMENTEDHEAD = new String[][] {
            {""},
            {"c_solid_white.png"},
            {"c_afghanpied.png"},
            {"c_turkishpied.png"},
            {"c_turkishspeckled.png"},
            {"c_turkishpigmentedhead.png"},
            {"c_pigmentedhead_0.png", "c_pigmentedhead_1.png", "c_pigmentedhead_2.png", "c_pigmentedhead_3.png", "c_pigmentedhead_4.png", "c_pigmentedhead_5.png", "c_pigmentedhead_6.png", "c_pigmentedhead_7.png", "c_pigmentedhead_8.png"}
    };

    private static final String[] SHEEP_TEXTURES_TICKED = new String[] {
            "", "ticking.png"
    };

    private static final String[] SHEEP_TEXTURES_SKIN = new String[] {
            "skin_pink.png"
    };

    private static final String[] SHEEP_TEXTURES_HOOVES = new String[] {
            "hooves_black.png"
    };

    private static final String[] SHEEP_TEXTURES_FUR = new String[] {
            "c_fur_wooly.png"
    };

    private static final String[] SHEEP_TEXTURES_EYES = new String[] {
            "eyes_black.png"
    };

    public static void calculateSheepTexture(EnhancedSheep sheep, int[] gene, char[] uuid) {
        boolean mealy = false;
        int pattern1 = 0;
        int pattern2 = 0;
        int grey = 0;
        int spots = 0;
        int roan = 0;
        int blaze = 0;
        int pigmentedHeadCategory = 0;
        int pigmentedHead = 0;
        int skin = 0;
        int hooves = 0;
        int fur = 0;
        int eyes = 0;

        if (gene[4] == 1 || gene[5] == 1){
            //black sheep
            pattern1 = 14;
        } else if (gene[0] != 1 && gene[1] != 1) {
            if (gene[0] == 6 && gene[1] == 6) {
                pattern1 = 14;
            } else {
                if (gene[0] == 2 || gene[1] == 2) {
                    int maxCoatLength = 0;

                    if (gene[20] == 2) {
                        maxCoatLength = 1;
                    }
                    if (gene[21] == 2) {
                        maxCoatLength = maxCoatLength + 1;
                    }
                    if (gene[22] == 2) {
                        maxCoatLength = maxCoatLength + 1;
                    }
                    if (gene[23] == 2) {
                        maxCoatLength = maxCoatLength + 1;
                    }
                    if (gene[24] == 2) {
                        maxCoatLength = maxCoatLength + 1;
                    }
                    if (gene[25] == 2) {
                        maxCoatLength = maxCoatLength + 1;
                    }
                    if (gene[26] == 2) {
                        maxCoatLength = maxCoatLength + 1;
                    }
                    if (gene[27] == 2) {
                        maxCoatLength = maxCoatLength + 1;
                    }
                    if (gene[28] == 2) {
                        maxCoatLength = maxCoatLength + 1;
                    }
                    if (gene[29] == 2) {
                        maxCoatLength = maxCoatLength + 1;
                    }
                    if (gene[30] == 2) {
                        maxCoatLength = maxCoatLength + 1;
                    }
                    if (gene[31] == 2) {
                        maxCoatLength = maxCoatLength + 1;
                    }
                    if (gene[32] == 2) {
                        maxCoatLength = maxCoatLength + 1;
                    }
                    if (gene[33] == 2) {
                        maxCoatLength = maxCoatLength + 1;
                    }
                    if (gene[34] == 2 && gene[35] == 2) {
                        maxCoatLength = maxCoatLength + 1;
                    }

                    int faceWool = 0;
                    if (gene[42] == 1 || gene[43] == 1) {
                        if (gene[40] == 1) {
                            faceWool++;
                        }
                        if (gene[41] == 1) {
                            faceWool++;
                        }
                        if (gene[38] == 1 || gene[39] == 1) {
                            faceWool++;
                        } else if (gene[38] == 3 && gene[39] == 3) {
                            faceWool--;
                        }
                    }

                    grey = maxCoatLength < 3 ? 0 : 1+faceWool;
                    pattern1 = gene[0] == 2 ? 14 : 0;
                    pattern2 = gene[1] == 2 ? 14 : 0;
                }

                if (gene[0] == 6) {
                    pattern1 = 14;
                } else if (gene[1] == 6) {
                    pattern2 = 14;
                }

                if (pattern1 == 0 || pattern2 == 0) {
                    if (pattern1 == 0) {
                        pattern1 = gene[0] <= 2 ? 0 : gene[0] - 2;
                        if (pattern1 > 3) {
                            pattern1 = pattern1 == 4 ? 14 : pattern1 - 1;
                        }
                    }
                    if (pattern2 == 0) {
                        pattern2 = gene[1] <= 2 ? 0 : gene[1] - 2;
                        if (pattern2 > 3) {
                            pattern2 = pattern2 == 4 ? 14 : pattern2 - 1;
                        }
                    }

                    if (gene[90] == 1 || gene[91] == 1) {
                        mealy = (pattern1 == 3 || (pattern1 < 14 && pattern1 > 6)) || (pattern2 == 3 || (pattern2 < 14 && pattern2 > 6));
                    }
                }

                if (gene[0] == 6) {
                    pattern1 = 0;
                } else if (gene[1] == 6) {
                    pattern2 = 0;
                }
            }
        }

        //basic spots
        if (gene[8] == 2 && gene[9] == 2){
//                if (Character.isDigit(uuid[1])){
//                    spots = 2;
//                }else {
            spots = 1;
//                }
        }

        //pigmented head
        if (gene[68] == 2 || gene[69] == 2) {
            if (gene[68] == 1 || gene[69] == 1) {
                //het afghan
                pigmentedHeadCategory = 2;
            } else {
                //white afghan
                pigmentedHeadCategory = 1;
            }
        } else if (gene[68] == 3 || gene[69] == 3) {
            if (gene[68] == gene[69]) {
                //homozygous turkish
                pigmentedHeadCategory = 3;
            } else if (gene[68] == 4 || gene[69] == 4) {
                //het turkish/pigmented head
                pigmentedHeadCategory = 5;
            } else {
                //het turkish (speckled)
                pigmentedHeadCategory = 4;
            }
        } else if (gene[68] == 4 && gene[69] == 4) {
            // pigmented head
            pigmentedHeadCategory = 6;
        }

        if (pigmentedHeadCategory==6) {
            pigmentedHead = Math.max(gene[18]-1, gene[19]-1);
        }

        if (gene[100] == 2 || gene[101] == 2) {
            roan = 1;
        }

        if (gene[102] != 1 && gene[103] != 1) {
            if (gene[102] == 2 || gene[103] == 2) {
                blaze = 1;
            } else {
                blaze = gene[102] == 3 || gene[103] == 3 ? 2 : 3;
            }
        }

        TextureGrouping parentGroup = new TextureGrouping(TexturingType.MERGE_GROUP);

        TextureGrouping hairGroup = new TextureGrouping(TexturingType.DYE_GROUP);
        TextureGrouping foundationGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        sheep.layer(foundationGroup).texture("r_solid_white.png").as(TexturingType.APPLY_RED).add();
        sheep.layer(foundationGroup).variant(SHEEP_TEXTURES_MEALY, mealy ? 1 : 0).onlyIf(l -> l != 0).add();
        hairGroup.addGrouping(foundationGroup);

        if (pattern1 == 14 || (gene[0] != 1 && gene[1] != 1 && (pattern1!=0 || pattern2!=0)) ) {
            TextureGrouping patternAverageGroup = new TextureGrouping(TexturingType.AVERAGE_GROUP);
            sheep.layer(patternAverageGroup).variant(SHEEP_TEXTURES_PATTERN, pattern1).as(TexturingType.APPLY_BLACK).onlyIf(l -> l != 0).add();
            sheep.layer(patternAverageGroup).variant(SHEEP_TEXTURES_PATTERN, pattern2).as(TexturingType.APPLY_BLACK).onlyIf(l -> l != 0).add();
            hairGroup.addGrouping(patternAverageGroup);
        } else {
            sheep.addSkippedSlots(PATTERN_SLOTS);
        }

        if (mealy || roan!=0 || blaze!=0 || pigmentedHeadCategory!=0 || spots!=0) {
            boolean ticked = !sheep.isBaby() && (gene[70] == 2 || gene[71] == 2) && (spots != 0 || pigmentedHeadCategory != 0);
            TextureGrouping whiteSpotGroup = new TextureGrouping(ticked ? TexturingType.MASK_GROUP : TexturingType.MERGE_GROUP);
            sheep.layer(whiteSpotGroup).variant(SHEEP_TEXTURES_TICKED, ticked ? 1 : 0).onlyIf(l -> l != 0).add();
            sheep.layer(whiteSpotGroup).variant(SHEEP_TEXTURES_MEALY, mealy ? (sheep.getOrSetIsFemale() ? 3 : 2) : 0).onlyIf(l -> l != 0).add();
            sheep.layer(whiteSpotGroup).variant(SHEEP_TEXTURES_ROAN, roan).onlyIf(l -> l != 0).add();
            sheep.layer(whiteSpotGroup).variant(SHEEP_TEXTURES_BLAZE, blaze).onlyIf(l -> l != 0).add();
            sheep.layer(whiteSpotGroup).variant(SHEEP_TEXTURES_SPOTS, spots).onlyIf(l -> l != 0).add();
            sheep.layer(whiteSpotGroup).variant(SHEEP_TEXTURES_PIGMENTEDHEAD, pigmentedHeadCategory, pigmentedHead).onlyIf(pigmentedHeadCategory != 0).add();
            hairGroup.addGrouping(whiteSpotGroup);
        } else {
            sheep.addSkippedSlots(WHITE_SPOT_SLOTS);
        }
        sheep.layer(hairGroup).variant(SHEEP_TEXTURES_GREY, grey).onlyIf(l -> l != 0).add();
        parentGroup.addGrouping(hairGroup);

        TextureGrouping detailGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        sheep.layer(detailGroup).variant(SHEEP_TEXTURES_FUR, fur).add();
        sheep.layer(detailGroup).variant(SHEEP_TEXTURES_SKIN, skin).add();
        sheep.layer(detailGroup).texture("hooves_black.png").add();
        sheep.layer(detailGroup).texture("eyes_black.png").add();
        sheep.layer(detailGroup).texture("chests.png").add();
        parentGroup.addGrouping(detailGroup);

        sheep.setTextureGrouping(parentGroup);
    }
}
