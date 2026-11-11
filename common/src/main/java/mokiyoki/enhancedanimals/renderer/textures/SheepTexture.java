package mokiyoki.enhancedanimals.renderer.textures;

import mokiyoki.enhancedanimals.entity.EnhancedSheep;
import mokiyoki.enhancedanimals.renderer.texture.Mask;
import mokiyoki.enhancedanimals.renderer.texture.TextureBuilder;
import mokiyoki.enhancedanimals.renderer.texture.TextureGroup;

import static mokiyoki.enhancedanimals.entity.util.VariationKeys.*;

public class SheepTexture {
    private static final int[] headCubes = new int[] {
        1, 12, 10, 0, 10, 1, 1, 2, //eyes
        0, 0, 0, 5, 4, 4, //head
        0, 0, 9, 5, 3, 4, //nose
        0, 0, 55, 5, 5, 4, //headW
        0, 14, 50, 5, 2, 2, //cheekW
        0, 0, 50, 4, 2, 3, //noseW
        0, 50, 1, 3, 2, 4, //jaw
        1, 18, 54, 24, 54, 2, 3, 1, //ear
        0, 34, 0, 4, 9, 4, //neck
    };

    private static final int[] bodyCubes = new int[] {
        0, 2, 0, 8, 16, 16, //bodyWithFluff
        0, 70, 48, 8, 0, 16, //tummy
        0, 86, 43, 2, 3, 2, //tail0
        0, 94, 43, 2, 3, 2, //tail1
        0, 102, 43, 2, 3, 2, //tail2
        0, 110, 43, 2, 3, 2, //tail3
        0, 46, 55, 4, 4, 5, //udder
        0, 46, 55, 1, 3, 1, //nippleL
        0, 60, 55, 1, 3, 1, //nippleR
    };

    private static final int[] legCubes = new int[] {
        1, 0, 32, 12, 32, 3, 5, 3, //legF
        1, 24, 32, 36, 32, 3, 5, 3, //legB
        1, 0, 41, 12, 41, 3, 5, 3, //legBF
        1, 24, 41, 36, 41, 3, 5, 3, //legBB
    };

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

    public static void calculateSheepTexture(EnhancedSheep sheep, int[] gene) {
        buildSheepTexture(TextureBuilder.of(sheep, gene));
    }

    static void buildSheepTexture(TextureBuilder sheepTexture) {
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

        if (sheepTexture.eitherAllelesHave(4, 1)){
            //black sheep
            pattern1 = 14;
        } else if (!sheepTexture.eitherAllelesHave(0, 1)) {
            if (sheepTexture.isHomoFor(0, 6)) {
                pattern1 = 14;
            } else {
                if (sheepTexture.eitherAllelesHave(0, 2)) {
                    int maxCoatLength = 0;

                    if (sheepTexture.gene(20) == 2) {
                        maxCoatLength = 1;
                    }
                    if (sheepTexture.gene(21) == 2) {
                        maxCoatLength = maxCoatLength + 1;
                    }
                    if (sheepTexture.gene(22) == 2) {
                        maxCoatLength = maxCoatLength + 1;
                    }
                    if (sheepTexture.gene(23) == 2) {
                        maxCoatLength = maxCoatLength + 1;
                    }
                    if (sheepTexture.gene(24) == 2) {
                        maxCoatLength = maxCoatLength + 1;
                    }
                    if (sheepTexture.gene(25) == 2) {
                        maxCoatLength = maxCoatLength + 1;
                    }
                    if (sheepTexture.gene(26) == 2) {
                        maxCoatLength = maxCoatLength + 1;
                    }
                    if (sheepTexture.gene(27) == 2) {
                        maxCoatLength = maxCoatLength + 1;
                    }
                    if (sheepTexture.gene(28) == 2) {
                        maxCoatLength = maxCoatLength + 1;
                    }
                    if (sheepTexture.gene(29) == 2) {
                        maxCoatLength = maxCoatLength + 1;
                    }
                    if (sheepTexture.gene(30) == 2) {
                        maxCoatLength = maxCoatLength + 1;
                    }
                    if (sheepTexture.gene(31) == 2) {
                        maxCoatLength = maxCoatLength + 1;
                    }
                    if (sheepTexture.gene(32) == 2) {
                        maxCoatLength = maxCoatLength + 1;
                    }
                    if (sheepTexture.gene(33) == 2) {
                        maxCoatLength = maxCoatLength + 1;
                    }
                    if (sheepTexture.isHomoFor(34, 2)) {
                        maxCoatLength = maxCoatLength + 1;
                    }

                    int faceWool = 0;
                    if (sheepTexture.eitherAllelesHave(42, 1)) {
                        if (sheepTexture.gene(40) == 1) {
                            faceWool++;
                        }
                        if (sheepTexture.gene(41) == 1) {
                            faceWool++;
                        }
                        if (sheepTexture.eitherAllelesHave(38, 1)) {
                            faceWool++;
                        } else if (sheepTexture.isHomoFor(38, 3)) {
                            faceWool--;
                        }
                    }

                    grey = maxCoatLength < 3 ? 0 : 1+faceWool;
                    pattern1 = sheepTexture.gene(0) == 2 ? 14 : 0;
                    pattern2 = sheepTexture.gene(1) == 2 ? 14 : 0;
                }

                if (sheepTexture.gene(0) == 6) {
                    pattern1 = 14;
                } else if (sheepTexture.gene(1) == 6) {
                    pattern2 = 14;
                }

                if (pattern1 == 0 || pattern2 == 0) {
                    if (pattern1 == 0) {
                        pattern1 = sheepTexture.gene(0) <= 2 ? 0 : sheepTexture.gene(0) - 2;
                        if (pattern1 > 3) {
                            pattern1 = pattern1 == 4 ? 14 : pattern1 - 1;
                        }
                    }
                    if (pattern2 == 0) {
                        pattern2 = sheepTexture.gene(1) <= 2 ? 0 : sheepTexture.gene(1) - 2;
                        if (pattern2 > 3) {
                            pattern2 = pattern2 == 4 ? 14 : pattern2 - 1;
                        }
                    }

                    if (sheepTexture.eitherAllelesHave(90, 1)) {
                        mealy = (pattern1 == 3 || (pattern1 < 14 && pattern1 > 6)) || (pattern2 == 3 || (pattern2 < 14 && pattern2 > 6));
                    }
                }

                if (sheepTexture.gene(0) == 6) {
                    pattern1 = 0;
                } else if (sheepTexture.gene(1) == 6) {
                    pattern2 = 0;
                }
            }
        }

        //basic spots
        if (sheepTexture.isHomoFor(8, 2)){
//                if (Character.isDigit(uuid[1])){
//                    spots = 2;
//                }else {
            spots = 1;
//                }
        }

        //pigmented head
        if (sheepTexture.eitherAllelesHave(68, 2)) {
            if (sheepTexture.eitherAllelesHave(68, 1)) {
                //het afghan
                pigmentedHeadCategory = 2;
            } else {
                //white afghan
                pigmentedHeadCategory = 1;
            }
        } else if (sheepTexture.eitherAllelesHave(68, 3)) {
            if (sheepTexture.gene(68) == sheepTexture.gene(69)) {
                //homozygous turkish
                pigmentedHeadCategory = 3;
            } else if (sheepTexture.eitherAllelesHave(68, 4)) {
                //het turkish/pigmented head
                pigmentedHeadCategory = 5;
            } else {
                //het turkish (speckled)
                pigmentedHeadCategory = 4;
            }
        } else if (sheepTexture.isHomoFor(68, 4)) {
            // pigmented head
            pigmentedHeadCategory = 6;
        }

        if (pigmentedHeadCategory==6) {
            pigmentedHead = sheepTexture.max(18) - 1;
        }

        if (sheepTexture.eitherAllelesHave(100, 2)) {
            roan = 1;
        }

        if (!sheepTexture.eitherAllelesHave(102, 1)) {
            if (sheepTexture.eitherAllelesHave(102, 2)) {
                blaze = 1;
            } else {
                blaze = sheepTexture.eitherAllelesHave(102, 3) ? 2 : 3;
            }
        }

        TextureGroup root = sheepTexture.root();

        TextureGroup hairGroup = root.addDyeGroup();
        TextureGroup foundationGroup = hairGroup.addMergeGroup();
        foundationGroup.addTexture("r_solid_white.png").tintRed();
        foundationGroup.addTextureIf(mealy, SHEEP_TEXTURES_MEALY[1]);

        if (pattern1 == 14 || (!sheepTexture.eitherAllelesHave(0, 1) && (pattern1 != 0 || pattern2 != 0))) {
            TextureGroup patternAverageGroup = hairGroup.addAverageGroup();
            patternAverageGroup.addTexture(SHEEP_TEXTURES_PATTERN[pattern1]).tintBlack();
            patternAverageGroup.addTexture(SHEEP_TEXTURES_PATTERN[pattern2]).tintBlack();
        }

        if (mealy || roan != 0 || blaze != 0 || pigmentedHeadCategory != 0 || spots != 0) {
            boolean ticked = !sheepTexture.ifIsBaby() && sheepTexture.eitherAllelesHave(70, 2) && (spots != 0 || pigmentedHeadCategory != 0);
            TextureGroup whiteSpotGroup;
            if (ticked) {
                Mask tickedGroup = hairGroup.addMaskGroup();
                tickedGroup.maskTexture().addTexture(SHEEP_TEXTURES_TICKED[1]);
                whiteSpotGroup = tickedGroup.content();
            } else {
                whiteSpotGroup = hairGroup.addMergeGroup();
            }
            if (mealy) whiteSpotGroup.addTexture(SHEEP_TEXTURES_MEALY[sheepTexture.ifIsFemale() ? 3 : 2]);
            whiteSpotGroup.addTexture(SHEEP_TEXTURES_ROAN[roan]).flipIf(sheepTexture.randomChance(SHEEP_ROAN_FLIP, 50), headCubes, bodyCubes, legCubes);
            whiteSpotGroup.addTexture(SHEEP_TEXTURES_BLAZE[blaze]).flipIf(sheepTexture.randomChance(SHEEP_BLAZE_FLIP, 50), headCubes, bodyCubes, legCubes);
            whiteSpotGroup.addTexture(SHEEP_TEXTURES_SPOTS[spots]).flipIf(sheepTexture.randomChance(SHEEP_SPOTS_FLIP, 50), headCubes, bodyCubes, legCubes);
            whiteSpotGroup.addTexture(SHEEP_TEXTURES_PIGMENTEDHEAD[pigmentedHeadCategory][pigmentedHead]).flipIf(sheepTexture.randomChance(SHEEP_PIGMENTEDHEAD_FLIP, 50), headCubes, bodyCubes, legCubes);
        }
        hairGroup.addTexture(SHEEP_TEXTURES_GREY[grey]);

        root.addMergeGroup(SHEEP_TEXTURES_FUR[fur], SHEEP_TEXTURES_SKIN[skin], "hooves_black.png", "eyes_black.png", "chests.png");

        sheepTexture.finishTexture();
    }
}
