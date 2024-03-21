package mokiyoki.enhancedanimals.renderer.textures;

import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import mokiyoki.enhancedanimals.renderer.texture.TextureGrouping;
import mokiyoki.enhancedanimals.renderer.texture.TexturingType;

import java.util.concurrent.ThreadLocalRandom;

public class ChickenTexture {
    /**
     *          - Chicken Ears, Comb, Wattles, Face, Legs, Skin, Eyes needs to be RGB
     *          - Autosomal red/gold/silver needs to be redone from scratch
     *          - All chicken pattern textures, model textures, and base textures need to be redone
     *          - Patterns need to be RGB shaded
     *          - Base Feather colour needs to be RGB shaded
     *
     */

    public static void calculateChickenTextures(EnhancedChicken chicken) {
        if (chicken.getSharedGenes() != null) {
            boolean isFemale = chicken.getOrSetIsFemale();
            int[] sGene = chicken.getSharedGenes().getSexlinkedGenes();
            int[] gene = chicken.getSharedGenes().getAutosomalGenes();

            boolean isNakedNeck = gene[52] == 1 || gene[53] == 1;
            String pattern = "";
            String autosomalRed = "";
            String ground = "";
            int earSize = 0;
            int earColour = 0;
            int facefeathers=-1;
            boolean mottled = gene[22] != 1 && gene[23] != 1;
            boolean charcoal = gene[100] == 2 && gene[101] == 2;

            TextureGrouping parentGroup = new TextureGrouping(TexturingType.MERGE_GROUP);

            if (chicken.growthAmount() < 0.25F) {
                if (gene[20] == 1 || gene[21] == 1) {
//                    int patternGene = 4 - (gene[26] + gene[27]);
                    int columbian = 4 - (gene[28] + gene[29]);
//                    int darkbrown = 4 - (gene[98] + gene[99]);
                    int melanized = 4 - (gene[30] + gene[31]);
                    int extension = Math.max(gene[24], gene[25]) == 5 ? 5 : Math.min(gene[24], gene[25]);
                    if (columbian==0) {
                        switch (extension) {
                            case 5 -> pattern = "extended_black";
                            case 1 -> pattern = melanized==0?"birchen":"dark";
                            case 2 -> pattern = melanized==0?"duckwing":"melanizedduckwing";
                            case 3 -> pattern = "wheaten";
                            case 4 -> pattern = melanized==0?"partridge":"dark";
                        }
                    } else {
                        switch (extension) {
                            case 5, 1, 2, 4 -> pattern = melanized==0?"columbian":"quail";
                        }
                    }
                }

                setEarShape(parentGroup, chicken, gene, earSize);
                TextureGrouping featherGroup = new TextureGrouping(TexturingType.MASK_GROUP);
                setFeatherCoverage(chicken, gene, isNakedNeck, facefeathers, "", "", featherGroup, isFemale);

                TextureGrouping baseFeatherColour = new TextureGrouping(TexturingType.MERGE_GROUP);
                chicken.addTextureToAnimalTextureGrouping(baseFeatherColour, "feather_colour/feather_base.png");
                chicken.addIndividualTextureToAnimalTextureGrouping(baseFeatherColour, TexturingType.APPLY_RGB, "chick/half_solid.png", calculateGroundRGB(sGene, gene, isFemale));
                featherGroup.addGrouping(baseFeatherColour);

                if (gene[20] == 1 || gene[21] == 1) {
                    if (!pattern.isEmpty() || mottled) {
                        TextureGrouping patternFeatherGroup = new TextureGrouping(TexturingType.MASK_GROUP);
                        TextureGrouping patternCutOutGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                        chicken.addTextureToAnimalTextureGrouping(patternCutOutGroup, "chick/pattern/" + pattern + ".png", pattern);
                        patternFeatherGroup.addGrouping(patternCutOutGroup);
                        calculatePatternRGB(chicken, patternFeatherGroup, pattern, sGene, gene, isFemale, isNakedNeck);
                        featherGroup.addGrouping(patternFeatherGroup);
                    }
                    chicken.addTextureToAnimalTextureGrouping(featherGroup, "chick/mottles.png", mottled);
                    chicken.addTextureToAnimalTextureGrouping(featherGroup, !isFemale&&sGene[6]==2&&sGene[7]==2?"chick/doublebarred.png":"chick/barred.png", isFemale ? sGene[6] == 2 : sGene[6] == 2 || sGene[7] == 2);
                } else {
                    chicken.addTextureToAnimalTextureGrouping(featherGroup, "", false);
                }

                chicken.addTextureToAnimalTextureGrouping(featherGroup, "feather_colour/feather_noise.png");
                parentGroup.addGrouping(featherGroup);
            } else {
                boolean femFeathers = isFemale || (gene[196] == 2 || gene[197] == 2); //TODO roosters can be het for henny feather and express an intermediate form.
                String tailType = "";
                String tailSickle = "";

                if (gene[20] == 1 || gene[21] == 1) {
                    int patternGene = 4 - (gene[26] + gene[27]);
                    int columbian = 4 - (gene[28] + gene[29]);
                    int darkbrown = 4 - (gene[98] + gene[99]);
                    int melanized = 4 - (gene[30] + gene[31]);
                    int extension = Math.max(gene[24], gene[25]) == 5 ? 5 : Math.min(gene[24], gene[25]);

//                    //this is for het extension but that's for later
//                if (gene[24]!=gene[25]) {
//                extension*=10;
//                    extension+=extension==50? Math.min(gene[24],gene[25]) : Math.max(gene[24],gene[25]);
//                }

                    if (patternGene == 0) {
                        if (columbian == 0) {
                            if (darkbrown == 0) {
                                if (melanized == 0) {
                                    if (femFeathers) {
                                        switch (extension) {
                                            case 5 -> pattern = "solid";
                                            case 1 -> {
                                                pattern = isNakedNeck ? "nakedneck_birchen_female" : "birchen_female";
                                                autosomalRed = "duckwing_female";
                                            }
                                            case 2 -> {
                                                ground = isNakedNeck ? "nakedneck_duckwing_female" : "duckwing_female";
                                                pattern = ground;
                                                autosomalRed = "duckwing_female";
                                            }
                                            case 3 -> {
                                                ground = isNakedNeck ? "nakedneck_wheaten_female" : "wheaten_female";
                                                pattern = "wheaten_female";
                                                autosomalRed = ground;
                                            }
                                            case 4 -> {
                                                ground = isNakedNeck ? "nakedneck_duckwing_female" : "duckwing_female";
                                                pattern = isNakedNeck ? "nakedneck_brown_female" : "brown_female";
                                                autosomalRed = pattern;
                                            }
                                        }
                                    } else {
                                        switch (extension) {
                                            case 5 -> pattern = "solid";
                                            case 1 -> {
                                                pattern = isNakedNeck ? "nakedneck_birchen_male" : "birchen_male";
                                                autosomalRed = "duckwing_male";
                                            }
                                            case 2, 4 -> {
                                                pattern = "duckwing_male";
                                                autosomalRed = pattern;
                                            }
                                            case 3 -> {
                                                pattern = "duckwing_male";
                                                autosomalRed = "wheaten_male";
                                            }
                                        }
                                    }
                                } else {
                                    //melanized gene //TODO het melanized forms
                                    if (femFeathers) {
                                        switch (extension) {
                                            case 5, 1 -> {
                                                pattern = "solid";
                                                autosomalRed = "duckwing_female";
                                            }
                                            case 2 -> {
                                                ground = isNakedNeck ? "nakedneck_duckwing_female" : "duckwing_female";
                                                pattern = isNakedNeck ? "nakedneck_ml2duckwing_female" : "ml2duckwing_female";
                                                autosomalRed = "brown_female";
                                            } //TODO quail pattern
                                            case 3 -> {
                                                ground = isNakedNeck ? "nakedneck_wheaten_female" : "wheaten_female";
                                                pattern = isNakedNeck ? "nakedneck_ml2wheaten_female" : "ml2wheaten_female";
                                                autosomalRed = ground;
                                            } //TODO furnace wheaten
                                            case 4 -> {
                                                pattern = isNakedNeck ? "nakedneck_ml2brown_female" : "ml2brown_female";
                                                autosomalRed = isNakedNeck ? "nakedneck_brown_female" : "brown_female";
                                            } //TODO melanized birchen
                                        }
                                    } else {
                                        switch (extension) {
                                            case 5 -> pattern = "solid";
                                            case 1 ->
                                                    pattern = isNakedNeck ? "nakedneck_ml2birchen_male" : "ml2birchen_male";
                                            case 2, 4 -> {
                                                autosomalRed = "duckwing_male";
                                                pattern = "ml2duckwing_male";
                                            }
                                            case 3 -> {
                                                pattern = "ml2duckwing_male";
                                                autosomalRed = "wheaten_male";
                                            }
                                        }
                                    }
                                }
                            } else {
                                //dark brown //TODO het dark brown forms
                                if (melanized == 0) {
                                    if (femFeathers) {
                                        switch (extension) {
                                            case 5 -> {
                                                pattern = "solid";
                                                autosomalRed = "duckwing_female";
                                            } //TODO leaky black
                                            case 1 -> {
                                                pattern = isNakedNeck ? "nakedneck_ml2duckwing_female" : "ml2duckwing_female";
                                                autosomalRed = "brown_female";
                                            } //TODO birchen quail
                                            case 2, 3, 4 -> {
                                                ground = isNakedNeck ? "nakedneck_duckwing_female" : "duckwing_female";
                                                pattern = "blacktail";
                                                autosomalRed = "duckwing_female";
                                            } //TODO
                                        }
                                    } else {
                                        switch (extension) {
                                            case 5 -> pattern = "solid"; //TODO should be a leaky black.
                                            case 1, 2, 3, 4 -> pattern = "blacktail"; //TODO investigate how this should actually look
                                        }
                                        autosomalRed = "duckwing_male";
                                    }
                                } else {
                                    //melanized
                                    if (femFeathers) {
                                        switch (extension) {
                                            case 5 -> pattern = "solid";//TODO leaky black
                                            case 1, 2, 4 -> {
                                                pattern = isNakedNeck ? "nakedneck_ml2duckwing_female" : "ml2duckwing_female";
                                                autosomalRed = "brown_female";
                                            } //TODO dark birchen quail
                                            case 3 -> {
                                                ground = isNakedNeck ? "nakedneck_duckwing_female" : "duckwing_female";
                                                pattern = "blacktail";
                                                autosomalRed = "duckwing_female";
                                            } //TODO
                                        }
                                    } else {
                                        switch (extension) {
                                            case 5 -> pattern = "solid"; //TODO should be a leaky black.
                                            case 1, 2, 3, 4 -> {
                                                pattern = "blacktail";
                                                autosomalRed = "duckwing_male";
                                            } //TODO investigate how this should actually look
                                        }
                                    }
                                }
                            }
                        } else {
                            //columbian
                            if (darkbrown == 0) {
                                if (melanized == 0) {
                                    if (charcoal) {
                                        if (femFeathers) {
                                            switch (extension) {
                                                case 5, 1 -> pattern = "solid";
                                                case 2 -> {
                                                    pattern = "quail_female";
                                                    autosomalRed = "duckwing_female";
                                                }
                                                case 3 ->
                                                        pattern = isNakedNeck ? "nakedneck_columbian_female" : "columbian_female";
                                                case 4 -> pattern = "lakenvelder_female";
                                            }
                                        } else {
                                            switch (extension) {
                                                case 5, 1 -> pattern = "solid";
                                                case 2 -> pattern = "quail_male";
                                                case 3 -> pattern = "blacktail";
                                                case 4 -> pattern = "lakenvelder_male";
                                            }
                                        }
                                    } else {
                                        if (femFeathers) {
                                            switch (extension) {
                                                case 5 -> {
                                                    pattern = "solid";
                                                    autosomalRed = "duckwing_female";
                                                }
                                                case 1 -> {
                                                    pattern = isNakedNeck ? "nakedneck_birchen_female" : "birchen_female";
                                                    autosomalRed = "duckwing_female";
                                                }
                                                case 2, 3, 4 ->
                                                        pattern = isNakedNeck ? "nakedneck_columbian_female" : "columbian_female";
                                            }
                                        } else {
                                            switch (extension) {
                                                case 5 -> pattern = "solid";
                                                case 1 -> {
                                                    pattern = isNakedNeck ? "nakedneck_birchen_male" : "birchen_male";
                                                    autosomalRed = "duckwing_male";
                                                }
                                                case 2, 3, 4 -> {
                                                    pattern = "columbian";
                                                    autosomalRed = "duckwing_male";
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    //melanized
                                    if (femFeathers) {
                                        switch (extension) {
                                            case 5 -> {
                                                pattern = "solid";
                                                autosomalRed = "duckwing_female";
                                            }
                                            case 1 -> {
                                                pattern = isNakedNeck ? "nakedneck_birchen_female" : "birchen_female";
                                                autosomalRed = "duckwing_female";
                                            }
                                            case 2, 3, 4 ->
                                                    pattern = "quail_female";
                                        }
                                    } else {
                                        switch (extension) {
                                            case 5 -> pattern = "solid";
                                            case 1 -> {
                                                pattern = isNakedNeck ? "nakedneck_birchen_male" : "birchen_male";
                                                autosomalRed = "duckwing_male";
                                            }
                                            case 2, 3, 4 -> {
                                                pattern = "blacktail";
                                                autosomalRed = "duckwing_male";
                                            }
                                        }
                                    }
                                }
                            } else {
                                //columbian + darkbrown
                                if (melanized == 0) {
                                    if (femFeathers) {
                                        switch (extension) {
                                            case 5 -> {
                                                pattern = "solid";
                                            }
                                            case 1, 2, 4 -> {
                                                pattern = isNakedNeck ? "nakedneck_columbian_female" : "columbian_female";
                                                autosomalRed = "solid";
                                            }
                                            case 3 -> {
                                                pattern = "";
                                                autosomalRed = "solid";
                                            }
                                        }
                                    } else {
                                        switch (extension) {
                                            case 5 -> {
                                                pattern = "solid";
                                            }
                                            case 1, 2, 4 -> {
                                                pattern = "blacktail";
                                                autosomalRed = "solid";
                                            }
                                            case 3 -> {
                                                pattern = "";
                                                autosomalRed = "solid";
                                            }
                                        }
                                    }
                                } else {
                                    //columbian + darkbrown + melanized
                                    if (femFeathers) {
                                        switch (extension) {
                                            case 1, 5 -> pattern = "solid";
                                            case 2 -> {
                                                pattern = "co2db2ml2duckwing_female";
                                                ground = "duckwing_female";
                                                autosomalRed = "duckwing_female";
                                            }
                                            case 3 -> {
                                                pattern = "co2db2ml2duckwing_female";
                                                ground = "wheaten_female";
                                                autosomalRed = "wheaten_female";
                                            }
                                            case 4 -> {
                                                pattern = "co2db2ml2duckwing_female";
                                                ground = "duckwing_female";
                                                autosomalRed = "brown_female";
                                            }
                                        }
                                    } else {
                                        switch (extension) {
                                            case 1, 5 -> pattern = "solid";
                                            case 2, 3, 4 -> pattern = "quail_male";
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        //pattern gene
                        if (columbian == 0) {
                            if (darkbrown == 0) {
                                if (melanized == 0) {
                                    if (femFeathers) {
                                        switch (extension) {
                                            case 5 -> pattern = "solid";
                                            case 1 -> {
                                                pattern = isNakedNeck ? "nakedneck_birchen_female" : "birchen_female";
                                                autosomalRed = "duckwing_female";
                                            }
                                            case 2 -> {
                                                ground = isNakedNeck ? "nakedneck_duckwing_female" : "duckwing_female";
                                                pattern = ground;
                                                autosomalRed = "duckwing_female";
                                            }
                                            case 3 -> {
                                                ground = isNakedNeck ? "nakedneck_wheaten_female" : "wheaten_female";
                                                pattern = "wheaten_female";
                                                autosomalRed = ground;
                                            }
                                            case 4 -> {
                                                pattern = isNakedNeck ? "nakedneck_brown_female" : "brown_female";
                                                autosomalRed = pattern;
                                            }
                                        }
                                    } else {
                                        switch (extension) {
                                            case 5 -> pattern = "solid";
                                            case 1 -> {
                                                pattern = isNakedNeck ? "nakedneck_birchen_male" : "birchen_male";
                                                autosomalRed = "duckwing_male";
                                            }
                                            case 2, 4 -> {
                                                pattern = "duckwing_male";
                                                autosomalRed = pattern;
                                            }
                                            case 3 -> {
                                                pattern = "duckwing_male";
                                                autosomalRed = "wheaten_male";
                                            }
                                        }
                                    }
                                } else {
                                    //pattern gene + melanized
                                    if (femFeathers) {
                                        switch (extension) {
                                            case 5, 1 -> pattern = "solid";
                                            case 2 -> {
                                                ground = isNakedNeck ? "nakedneck_duckwing_female" : "duckwing_female";
                                                pattern = isNakedNeck ? "nakedneck_doublelace_female" : "doublelace_female";
                                                autosomalRed = "duckwing_female";
                                            }
                                            case 3 -> {
                                                ground = isNakedNeck ? "nakedneck_wheaten_female" : "wheaten_female";
                                                pattern = isNakedNeck ? "nakedneck_doublelace_female" : "doublelace_female";
                                                autosomalRed = ground;
                                            }
                                            case 4 -> {
                                                pattern = isNakedNeck ? "nakedneck_doublelace_female" : "doublelace_female";
                                                autosomalRed = "solid";
                                            }
                                        }
                                    } else {
                                        switch (extension) {
                                            case 5, 1 -> pattern = "solid";
                                            case 2, 3, 4 -> {
                                                pattern = "doublelace_male";
                                                autosomalRed = "duckwing_male";
                                            }
                                        }
                                    }
                                }
                            } else {
                                //pattern gene + darkbrown
                                if (melanized == 0) {
                                    if (femFeathers) {
                                        switch (extension) {
                                            case 5 -> {
                                                pattern = "solid";
                                            }
                                            case 1, 2, 4 -> {
                                                pattern = isNakedNeck ? "nakedneck_autosomalbarred_female" : "autosomalbarred_female";
                                                ground = "duckwing_female";
                                                autosomalRed = "solid";
                                            }
                                            case 3 -> {
                                                pattern = isNakedNeck ? "nakedneck_columbian_female" : "columbian_female";
                                                autosomalRed = "solid";
                                            }
                                        }
                                    } else {
                                        switch (extension) {
                                            case 5 -> {
                                                pattern = "solid";
                                            }
                                            case 1 -> {
                                                pattern = "autosomalbarred_male";
                                                ground = "duckwing_male";
                                                autosomalRed = "duckwing_male";
                                            }
                                            case 2, 4 -> {
                                                pattern = "pg2db2duckwing_male";
                                                autosomalRed = "duckwing_male";
                                            } //TODO seems to be a penciled tail only
                                            case 3 -> {
                                                pattern = "blacktail";
                                                autosomalRed = "solid";
                                            }
                                        }
                                    }
                                } else {
                                    //pattern gene + darkbrown + melanized
                                    switch (extension) {
                                        case 5 -> {
                                            pattern = "solid";
                                        }
                                        case 1, 2, 3, 4 -> {
                                            pattern = femFeathers ? "spangled_female" : (isNakedNeck ? "nakedneck_spangled_male" : "spangled_male");
                                            autosomalRed = femFeathers ? "solid" : "duckwing_male";
                                        }
                                    }
                                }
                            }
                        } else {
                            if (darkbrown == 0) {
                                if (melanized == 0) {
                                    if (femFeathers) {
                                        //pattern gene + columbian
                                        switch (extension) {
                                            case 5 -> {
                                                pattern = "solid";
                                            }
                                            case 1 -> {
                                                pattern = isNakedNeck ? "nakedneck_birchen_female" : "birchen_female";
                                                autosomalRed = "duckwing_female";
                                            }
                                            case 2, 3, 4 -> {
                                                pattern = isNakedNeck ? "nakedneck_columbian_female" : "columbian_female";
                                                autosomalRed = "solid";
                                            }
                                        }
                                    } else {
                                        switch (extension) {
                                            case 5 -> {
                                                pattern = "solid";
                                            }
                                            case 1 -> {
                                                pattern = isNakedNeck ? "nakedneck_birchen_male" : "birchen_male";
                                                autosomalRed = "duckwing_male";
                                            }
                                            case 2, 3, 4 -> {
                                                pattern = isNakedNeck ? "nakedneck_columbian_male" : "columbian_male";
                                                autosomalRed = "duckwing_male";
                                            }
                                        }
                                    }
                                } else {
                                    //pattern gene + columbian + melanized
                                    if (femFeathers) {
                                        switch (extension) {
                                            case 5, 1 -> {
                                                pattern = "solid";
                                            }
                                            case 2, 3, 4 -> {
                                                pattern = "singlelace_female";
                                                autosomalRed = "solid";
                                            }
                                        }
                                    } else {
                                        switch (extension) {
                                            case 5, 1 -> {
                                                pattern = "solid";
                                            }
                                            case 2, 3, 4 -> {
                                                pattern = "singlelace_male";
                                                autosomalRed = "solid";
                                            }
                                        }
                                    }
                                }
                            } else {
                                //pattern gene + columbian + darkbrown
                                if (melanized == 0) {
                                    if (femFeathers) {
                                        switch (extension) {
                                            case 5 -> {
                                                pattern = "solid";
                                            }
                                            case 1 -> {
                                                pattern = "autosomalbarred_female";
                                                ground = "duckwing_female";
                                                autosomalRed = "solid";
                                            }
                                            case 2, 4 -> {
                                                pattern = "blacktail";
                                                autosomalRed = "solid";
                                            }
                                            case 3 -> {
                                                pattern = "";
                                                autosomalRed = "solid";
                                            }
                                        }
                                    } else {
                                        switch (extension) {
                                            case 5 -> {
                                                pattern = "solid";
                                            }
                                            case 1 -> {
                                                pattern = "autosomalbarred_male";
                                                ground = "duckwing_male";
                                                autosomalRed = "duckwing_male";
                                            }
                                            case 2, 4 -> {
                                                pattern = "blacktail";
                                                autosomalRed = "duckwing_male";
                                            }
                                            case 3 -> {
                                                pattern = "";
                                                autosomalRed = "solid";
                                            }
                                        }
                                    }
                                } else {
                                    //pattern gene + columbian + darkbrown + melanized
                                    if (femFeathers) {
                                        switch (extension) {
                                            case 5 -> {
                                                pattern = "solid";
                                            }
                                            case 1 -> {
                                                pattern = "singlelacebirchen_female";
                                                autosomalRed = "solid";
                                            }
                                            case 2, 3, 4 -> {
                                                pattern = "spangled_female";
                                                autosomalRed = "solid";
                                            }
                                        }
                                    } else {
                                        switch (extension) {
                                            case 5 -> {
                                                pattern = "solid";
                                            }
                                            case 1 -> {
                                                pattern = "singlelacebirchen_male";
                                                autosomalRed = "solid";
                                            }
                                            case 2, 3, 4 -> {
                                                pattern = isNakedNeck ? "nakedneck_spangled_male" : "spangled_male";
                                                autosomalRed = "solid";
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
//                    if (isNakedNeck) {
//                        if (ground.startsWith("duckwing")) {
//                            ground = "nakedneck_" + ground;
//                        }
//                        if (!femFeathers && !autosomalRed.equals("solid")) {
//                            autosomalRed = autosomalRed.startsWith("wheaten") ? "nakedneck_wheaten_male" : "nakedneck_duckwing_male";
//                        }
//                    }

                    switch (extension) {
                        case 5 -> {
                            pattern = "black/";
                            ground = femFeathers? "duckwing_female" : "duckwing_male";
                        }
                        case 1 -> {
                            pattern = "birchen/";
                            ground = femFeathers? "duckwing_female" : "duckwing_male";
                        }
                        case 2 -> {
                            pattern = "duckwing/";
                            ground = femFeathers? "duckwing_female" : "duckwing_male";
                        }
                        case 3 -> {
                            pattern = "wheaten/";
                            ground = femFeathers? "wheaten_female" : "duckwing_male";
                        }
                        case 4 -> {
                            pattern = "brown/";
                            ground = femFeathers? "duckwing_female" : "duckwing_male";
                        }
                    }
                    switch (columbian) {
                        case 0 -> pattern += "noncolumbian/";
                        case 1 -> pattern += "hetcolumbian/";
                        case 2 -> pattern +=    "columbian/";
                    }
                    switch (darkbrown) {
                        case 0 -> pattern += "nondarkbrown/";
                        case 1 -> pattern += "hetdarkbrown/";
                        case 2 -> pattern +=    "darkbrown/";
                    }
                    switch (patternGene) {
                        case 0 -> pattern += "nonpattern/";
                        case 1 -> pattern += "hetpattern/";
                        case 2 -> pattern +=    "pattern/";
                    }
                    switch (melanized) {
                        case 0 -> pattern += "nonmelanized/";
                        case 1 -> pattern += "hetmelanized/";
                        case 2 -> pattern +=    "melanized/";
                    }

                    pattern += femFeathers ? "female" : "male";

                    if (gene[170]==1 || gene[171]==1) {
                        autosomalRed = pattern;
                    }

                }


                if (isFemale || (gene[196] == 2 || gene[197] == 2)) {
                    tailType = "0_female";
                } else {
                    tailType = "0_male";
                    tailSickle = "0";
                }

                earSize += isFemale ? sGene[4] - 1 : Math.min(sGene[4], sGene[5]);

                if (gene[80] == 2) earSize++;
                if (gene[81] == 2) earSize++;
                if (gene[82] == 1) earSize++;
                if (gene[83] == 1) earSize++;

                if (gene[152] <= 4 || gene[153] <= 4) {
                    earSize--;
                } else if (gene[152] > 8 && gene[153] > 8) {
                    earSize++;
                }

                if (gene[154] <= 4 || gene[155] <= 4) {
                    if (earSize != -1) earSize--;
                } else if (gene[154] > 8 && gene[155] > 8) {
                    earSize++;
                }

                if (gene[156] <= 4 || gene[157] <= 4) {
                    if (earSize != -1) earSize--;
                } else if (gene[156] > 5 && gene[157] > 5) {
                    earSize++;
                    if (gene[156] == gene[157] && gene[156] >= 10) {
                        earSize++;
                    }
                }

                if (gene[158] == 1 || gene[159] == 1) {
                    earSize--;
                } else if (gene[158] > 2 && gene[159] > 2) {
                    if (gene[158] == 3 || gene[159] == 3) {
                        earSize++;
                    } else if (gene[158] == 5 && gene[159] == 5) {
                        earSize++;
                    }
                }

                if (gene[160] <= 4 || gene[161] <= 4) {
                    if (earSize != -1) earSize--;
                } else if (gene[160] > 5 && gene[161] > 5) {
                    earSize++;
                    if (gene[160] == gene[161] && gene[160] >= 10) {
                        earSize++;
                    }
                }

                if (gene[162] <= 4 || gene[163] <= 4) {
                    if (earSize != -1) earSize--;
                } else if (gene[162] > 5 && gene[163] > 5) {
                    earSize++;
                    if (gene[162] == gene[163] && gene[162] >= 10) {
                        earSize++;
                    }
                }

                if (sGene[18] != 1 && (isFemale || sGene[19] != 1)) {
                    earSize *= sGene[18] == 2 && (isFemale || sGene[19] == 2) ? 0.75 : 0.5F;
                }

                earColour = Math.min(gene[164], gene[165]) - 1;
                if (gene[158] >= 4 && gene[159] >= 4) {
                    earColour++;
                    if (gene[158] == gene[159]) earColour++;
                }

                if (sGene[12] <= 3 && (isFemale || sGene[13] <= 3)) {
                    earColour += isFemale || sGene[12] == sGene[13] ? 2 : 1;
                    facefeathers = 3 - (isFemale ? sGene[12] : Math.max(sGene[12], sGene[13]));
                }

                if (gene[222] == 2 && gene[223] == 2) facefeathers++;
                if (gene[224] == 2 || gene[225] == 2) facefeathers++;
                if (gene[226] == 2 || gene[227] == 2) facefeathers++;

                setEarShape(parentGroup, chicken, gene, earSize);
                TextureGrouping featherGroup = new TextureGrouping(TexturingType.MASK_GROUP);
                setFeatherCoverage(chicken, gene, isNakedNeck, facefeathers, tailType, tailSickle, featherGroup, isFemale);
                setBaseFeatherColour(chicken, isFemale, femFeathers, sGene, gene, autosomalRed, ground, featherGroup);
                setPatternColour(chicken, isFemale, sGene, gene, isNakedNeck, pattern, mottled, charcoal, femFeathers, featherGroup);
                chicken.addTextureToAnimalTextureGrouping(featherGroup, "feather_colour/feather_noise.png");
                parentGroup.addGrouping(featherGroup);
            }

            TextureGrouping detailGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
            setSkinColour(chicken, isFemale, sGene, gene, detailGroup, chicken.growthAmount());
            setEarColour(chicken, isFemale, sGene, gene, earColour, detailGroup);
            chicken.addIndividualTextureToAnimalTextureGrouping(detailGroup, TexturingType.APPLY_RGB, "eyes.png", calculateEyeRGB(sGene, gene, isFemale));
            parentGroup.addGrouping(detailGroup);

            chicken.setTextureGrouping(parentGroup);
        }
    }

    private static void setSkinColour(EnhancedChicken chicken, boolean isFemale, int[] sGene, int[] gene, TextureGrouping detailGroup, float age) {
        chicken.addTextureToAnimalTextureGrouping(detailGroup, TexturingType.APPLY_RGB, "skin/" + (isFemale ? "female" : "male") + ".png", isFemale ? "f" : "m", calculateSkinRGB(sGene, gene, isFemale));
        chicken.addIndividualTextureToAnimalTextureGrouping(detailGroup, TexturingType.APPLY_RGB, "shanks.png", 255 << 24 | calculateShanksRGB(sGene, gene, isFemale));
        chicken.addTextureToAnimalTextureGrouping(detailGroup, TexturingType.APPLY_RGB, "skin/comb_" + (isFemale ? "female" : "male") + ".png", isFemale ? "f" : "m", calculateCombRGB(sGene, gene, isFemale));
        if (age < 0.25F) {
            chicken.addTextureToAnimalTextureGrouping(detailGroup, TexturingType.APPLY_RGB, "skin/baby.png","b", calculateSkinRGB(sGene, gene, isFemale));
        }
    }

    private static void setEarColour(EnhancedChicken chicken, boolean isFemale, int[] sGene, int[] gene, int earColour, TextureGrouping detailGroup) {
        if (earColour > 0) {
            String ear = "ear";
            if (earColour < 7) ear += "_mottled" + earColour;
            earColour = calculateEarRGB(sGene, gene, isFemale);
            chicken.addIndividualTextureToAnimalTextureGrouping(detailGroup, TexturingType.APPLY_RGB, "skin/" + ear + ".png", earColour);

            int face = 6 - (isFemale ? sGene[12] : Math.max(sGene[12], sGene[13]));

            if (face != 0) {
                if (gene[218] != 1 && gene[219] != 1) {
                    face += gene[218] == gene[219] ? 2 : 1;
                }
                if (gene[220] != 1 && gene[221] != 1) {
                    face += gene[220] == gene[221] ? 2 : 1;
                }
            }

            face -= 3;
            if (face >= 0) {
                chicken.addIndividualTextureToAnimalTextureGrouping(detailGroup, TexturingType.APPLY_RGB, "skin/face" + face + ".png", earColour);
            }
        }
    }

    private static void setPatternColour(EnhancedChicken chicken, boolean isFemale, int[] sGene, int[] gene, boolean isNakedNeck, String pattern, boolean mottled, boolean charcoal, boolean femFeathers, TextureGrouping featherGroup) {
        if (gene[20] == 1 || gene[21] == 1) {
            if (!pattern.isEmpty() || mottled || charcoal) {
                TextureGrouping patternFeatherGroup = new TextureGrouping(TexturingType.MASK_GROUP);
                TextureGrouping patternCutOutGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                chicken.addTextureToAnimalTextureGrouping(patternCutOutGroup, "pattern/mottles/mottles.png", mottled);
                chicken.addTextureToAnimalTextureGrouping(patternCutOutGroup, "pattern/" + pattern + ".png", pattern);
                if (charcoal) {
                    String charcoalType = "pattern/";
                    charcoalType+= femFeathers ? "cha_female.png" : "cha_male.png";
                    chicken.addTextureToAnimalTextureGrouping(patternCutOutGroup, charcoalType, charcoalType.substring(17,18));
                } else {
                    chicken.addTextureToAnimalTextureGrouping(patternCutOutGroup, "", false);
                }
                patternFeatherGroup.addGrouping(patternCutOutGroup);
                calculatePatternRGB(chicken, patternFeatherGroup, pattern, sGene, gene, isFemale, isNakedNeck);
                featherGroup.addGrouping(patternFeatherGroup);
            }
            chicken.addTextureToAnimalTextureGrouping(featherGroup, "feather_colour/mottles/mottles.png", mottled);
            chicken.addTextureToAnimalTextureGrouping(featherGroup, "feather_colour/barred.png", isFemale ? sGene[6] == 2 : sGene[6] == 2 || sGene[7] == 2);
        } else {
            chicken.addTextureToAnimalTextureGrouping(featherGroup, "", false);
        }
    }

    private static void setBaseFeatherColour(EnhancedChicken chicken, boolean isFemale, boolean femfeathers, int[] sGene, int[] gene, String autosomalRed, String ground, TextureGrouping featherGroup) {
        TextureGrouping baseFeatherColour = new TextureGrouping(TexturingType.MERGE_GROUP);
        TextureGrouping groundGroup = new TextureGrouping(TexturingType.APPLY_PHEOMELANIN);
        baseFeatherColour.addGrouping(groundGroup);
        if (ground.isEmpty()) {
            chicken.addIndividualTextureToAnimalTextureGrouping(groundGroup, TexturingType.APPLY_RGB, "feather_colour/feather_base.png", calculateGroundRGB(sGene, gene, isFemale));
        } else {
            chicken.addTextureToAnimalTextureGrouping(groundGroup, "feather_colour/feather_base.png");
            chicken.addTextureToAnimalTextureGrouping(groundGroup, TexturingType.APPLY_RGB, "feather_colour/ground/" + ground + ".png", ground, calculateGroundRGB(sGene, gene, isFemale));
        }
        if (!autosomalRed.isEmpty() && (gene[20] == 1 || gene[21] == 1)) {
            if (gene[170] == 1 || gene[171] == 1) {
                TextureGrouping autosomalRedGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                int red = calculateAutosomalRedRGB(sGene, gene, isFemale);
                    chicken.addTextureToAnimalTextureGrouping(autosomalRedGroup, TexturingType.APPLY_RGBA, "feather_colour/autosomal_red/" + autosomalRed + ".png", autosomalRed, red);
                    if (gene[34] == 1 || gene[35] == 1) {
                        chicken.addTextureToAnimalTextureGrouping(autosomalRedGroup, TexturingType.APPLY_RGBA, "feather_colour/autosomal_red/" + autosomalRed + ".png", autosomalRed, red);
                    }
                baseFeatherColour.addGrouping(autosomalRedGroup);
            }
        }
        featherGroup.addGrouping(baseFeatherColour);
    }
    private static void setFeatherCoverage(EnhancedChicken chicken, int[] gene, boolean isNakedNeck, int facefeathers, String tailType, String tailSickle, TextureGrouping featherGroup, boolean isFemale) {
        TextureGrouping featherMask = new TextureGrouping(TexturingType.MERGE_GROUP);
        if (isNakedNeck || facefeathers != -1) {
            featherMask.setTexturingType(TexturingType.CUTOUT_GROUP);
            TextureGrouping featherCutout = new TextureGrouping(TexturingType.MERGE_GROUP);
            if (isNakedNeck) {
                chicken.addTextureToAnimalTextureGrouping(featherCutout, "feather_type/" + (gene[52] == gene[53] ? "naked" : "bowtie") + "_neck.png", gene[52] == gene[53] ? "bt" : "nn");
            }
            if (facefeathers != -1) {
                chicken.addTextureToAnimalTextureGrouping(featherCutout, "feather_type/baldface_" + facefeathers + ".png");
            }
            featherMask.addGrouping(featherCutout);
        }
        chicken.addTextureToAnimalTextureGrouping(featherMask, "feather_type/feathers.png");

            if (!tailType.isEmpty()) {

                int tailLength = 1;
                if (gene[198]==2&&gene[199]==2) tailLength +=1;
                if (gene[180]==2&&gene[181]==2) tailLength +=1;
                if (gene[182]==2&&gene[183]==2) tailLength -=1;

                int tailNumber = gene[278]==1||gene[279]==1?5:(gene[278]==2||gene[279]==2?6:7);
                for (int i = 0; i <= tailNumber; i++) {
                    if (i == 0) {
                        /**
                         *      This one controls the sickle feather
                         */
                        chicken.addTextureToAnimalTextureGrouping(featherMask, "tail/"+tailLength+"/" + (isFemale ? "female" : "male") + "/" + i + ".png", tailType);
                    } else {
                        chicken.addTextureToAnimalTextureGrouping(featherMask, "tail/"+tailLength+"/" + (isFemale ? "female" : "male") + "/" + i + ".png", tailType);
                    }
                }
        }
        featherGroup.addGrouping(featherMask);
    }

    private static void setEarShape(TextureGrouping parentGroup, EnhancedChicken chicken, int[] gene, int earSize) {
        if (earSize >= 2) {
            parentGroup.setTexturingType(TexturingType.CUTOUT_GROUP);
            TextureGrouping earCutout = new TextureGrouping(TexturingType.MERGE_GROUP);

            String ear = "";
            if (earSize > 15) earSize = 15;
            switch (earSize) {
                case 2, 3, 4 -> ear = "tiny.png";
                case 5, 6 -> ear = "small.png";
                case 7, 8, 9 -> ear = "medium.png";
                case 10, 11, 12 -> ear = "large.png";
                case 13, 14, 15 -> ear = "xlarge.png";
            }

            earSize = 0;
            for (int i = 152; i < 163; i++) {
                if (i < 158 || i > 159) earSize += gene[i] % 2 == 0 ? 1 : -1;
            }

            ear = (earSize > 0 ? "round_" : "long_") + ear;

            chicken.addTextureToAnimalTextureGrouping(earCutout, "ear/" + ear, ear);

            parentGroup.addGrouping(earCutout);
        }
    }

    private static Integer calculateEarRGB(int[] sGene, int[] gene, boolean isFemale) {
        float value = 1.0F;
        float saturation = 0.0F;
        float hue = -1.0F;

        if ((sGene[8]==1 || (!isFemale && sGene[9]==1)) && (gene[42]==1 || gene[43]==1)) {
            value = gene[42]==gene[43]?0.55F:0.75F;
            saturation = 0.4F;
            hue = 0.5833F;
        }

        if (gene[44]!=1 && gene[45]!=1 && (gene[44]==3 || gene[45]==3)) {
            if (hue!=-1.0F) {
                value = (value + 1.0F)*0.5F;
                saturation = (saturation + (gene[44]==gene[45]?0.5F:0.3F))*0.5F;
            } else {
                saturation = gene[44]==gene[45]?0.5F:0.3F;
            }
            hue = hue != -1.0F ? (hue + 0.1472F) * 0.5F : 0.1472F;
        }

        return Colouration.HSBtoARGB(hue==-1F?0.0F:hue,saturation,value);
    }

    private static int calculateEyeRGB(int[] sGene, int[] gene, boolean isFemale) {
        if (gene[20]==3&&gene[21]==3) {
            return 14560322;
        } else {
            return sGene[2] == 2 && (isFemale || sGene[3] == 2) ? 2100488 : 0;
        }
    }

    private static int calculateGroundRGB(int[] sGene, int[] gene, boolean isFemale) {
        if (gene[20] != 1 && gene[21] != 1) return 16777215;

        float h = 0.141F;
        float s = isFemale? 0.76F : 1.0F;
        float b = isFemale? 0.9F : 1.0F;

        if (sGene[0]==2||(!isFemale&&sGene[1]==2)) {
            if (isFemale||sGene[0]==sGene[1]) {
                return Colouration.HSBtoARGB(h, 0.0F, b);
            } else {
                s = 0.5F;
            }
        }

        //mahogany
        if (gene[34]==1||gene[35]==1) {
            h *= gene[34]==gene[35]?0.75F:0.8F;
            s += (1.0F-s)*0.25F;
        }

        //Lavender
        if (gene[36]==2&&gene[37]==2) {
            s *= 0.2F;
        }

        //autosomal red
        if (gene[170]==1||gene[171]==1) {
            h *= gene[170]==gene[171] ? 0.92F : 0.95F;
            if (s != 1.0F) {
                s += (1.0F-s) * (gene[170]==gene[171]?0.75F:0.5F);
            }
        }

        //dilute / retired-cream
        if (gene[32]!=3||gene[33]!=3) {
            h += (0.141F-h)*0.5F;
            s *= 0.7F;
        }

        if (gene[284]!=3||gene[285]!=3) {
            s *= 0.65F;
            b *= 0.95F;
        }

        //dominant white
        if (gene[38]==1||gene[39]==1) {
            s *= gene[38]==gene[39] ? 0.8F : 0.9F;
        }

        return Colouration.HSBtoARGB(h, s, b);
    }

    private static int calculateAutosomalRedRGB(int[] sGene, int[] gene, boolean isFemale) {
        int colour = 16777215;
        if (isFemale) {
            if (gene[170] == 1 || gene[171] == 1) {
//                if (gene[170]==gene[171]) {
                    //homoautosomal red
                    if (gene[34] == 1 || gene[35] == 1) {
                        //mahogany
                        colour = sGene[0] == 1 ? 10503750 : 10774594;
                    } else {
                        colour = sGene[0] == 1 ? 10774594 : 10051657;
                    }
                /*} else {
                    //hetautosomal red
                    if (gene[34]==1 || gene[35]==1) {
                        //mahogany
                        colour = sGene[0] == 1 ? 10503750 : 10503750;
                    } else {
                        colour = sGene[0] == 1 ? 12426889 : 12425353;
                    }
                }*/
            }
        } else {
//            if (gene[170] == 1 || gene[171] == 1) {
//                if (gene[170]==gene[171]) {
//                    //gold
                    colour = gene[34]==1 || gene[35]==1 ? 5639947 : 9318930;
//                } else {
//                    //lemon
//                    colour = gene[34]==1 || gene[35]==1 ? 8658186 : 10769441;
//                }
//            }
        }

        int a = gene[170] == gene[171] && gene[170]==1 ? 255 : 200;
        int r = colour & 255;
        int g = colour >> 8 & 255;
        int b = colour >> 16 & 255;

        if (colour!=16777215) {
            //Lavender
            if (gene[36] == 2 && gene[37] == 2) {

                r += (int) ((255 - r) * 0.70F);
                g += (int) ((255 - g) * 0.70F);
                b += (int) ((255 - b) * 0.65F);
            }
        }

        return a << 24 | b << 16 | g << 8 | r;
    }

    private static void calculatePatternRGB(EnhancedChicken chicken, TextureGrouping patternFeatherGroup, String pattern, int[] sGene, int[] gene, boolean isFemale, boolean isNakedNeck) {
        float patternHue = 0.7F;
        float patternSaturation = 0.0F;
        float patternValue = 0.01F;

        float iridescenceAlpha = 1.0F;
        float iridescenceHueShift = 0.0F;

        boolean splash = false;
        boolean paint = gene[38] == 1 || gene[39] == 1;

        if (gene[40] == 2 || gene[41] == 2) {
            if (gene[40] == gene[41]) { splash = true; }
                //Blue
                patternHue = 0.6F;
                patternSaturation = 0.2F;
                patternValue = 0.4F;
                iridescenceAlpha = 0.0F;
        }

        if (sGene[2] == 2 && (isFemale || sGene[3] == 2)) {
            //Choc
            patternHue = 0.05F;
            patternValue += (patternValue + 1.0F) * 0.25F;
            patternSaturation += /*(patternSaturation + ((1.0F-patternSaturation)*0.75F)) * patternValue*/ 0.25F;
            iridescenceAlpha = 0.5F;
            iridescenceHueShift = 0.05F;
        }

        if (!(paint && gene[38]==gene[39])) {
            if (gene[38] == 4 || gene[39] == 4) {
                //het Smokey
                paint = false;
                if (gene[38] != 2 && gene[39] != 2) {
                    //Smokey
                    patternHue = 0.58F;
                    patternSaturation = 0.15F;
                    patternValue = 0.4F;
                    iridescenceAlpha = 0.0F;
                } else {
                    iridescenceAlpha = 0.02F;
                }
            } else if (gene[38] != 1 && gene[39] != 1) {
                if (gene[38] == 3 || gene[39] == 3) {
                    if (gene[38] == gene[39]) {
                        //Khaki
                        patternHue = 0.1F;
                        patternSaturation += (patternSaturation + 1.0F) * 0.5F;
                        patternValue += (patternValue + 1.0F) * 0.5F;
                        iridescenceAlpha = 0.0F;
                    } else {
                        //Dun
                        patternHue = 0.05F;
                        patternSaturation += patternSaturation + ((1.0F-patternSaturation)*0.50F);
                        patternValue += (patternValue + 1.0F) * 0.25F;
                        iridescenceAlpha = 0.3F;
                        iridescenceHueShift = 0.05F;
                    }
                }
            }
        }

        if (gene[36]== 2 && gene[37]==2) {
            //Lavender
            patternSaturation = (Math.min(patternSaturation + 0.2F, 1.0F))*0.25F;
            patternValue += (float) ((1.0F - patternValue) * (0.75F + (0.5 * (Math.min(patternSaturation,0.5F)))));
            iridescenceAlpha = 0.0F;
        }

        TextureGrouping baseMelanin = new TextureGrouping(TexturingType.MERGE_GROUP);
        chicken.addIndividualTextureToAnimalTextureGrouping(baseMelanin, TexturingType.APPLY_RGB, "feather_colour/feather_base.png", Colouration.HSBtoARGB(patternHue, patternSaturation, patternValue));
        if ((gene[106]==1||gene[107]==1)&&chicken.growthAmount()>0.35F) {
            TextureGrouping iridescenceMask = new TextureGrouping(TexturingType.MASK_GROUP);
            TextureGrouping iridescence = new TextureGrouping(TexturingType.MERGE_GROUP);
            chicken.addTextureToAnimalTextureGrouping(iridescence, TexturingType.APPLY_SHIFT, "feather_colour/"+ (isFemale?"iridescence_female":(isNakedNeck?"nakedneck_iridescence_male":"iridescence_male")) +".png", isFemale? "f" : (isNakedNeck?"nm":"m"), (int)((1.0F-iridescenceAlpha)*255) << 8 | (int)(iridescenceHueShift*255));
            iridescenceMask.addGrouping(iridescence);
            chicken.addTextureToAnimalTextureGrouping(iridescenceMask, "pattern/"+pattern+".png");

        }
        patternFeatherGroup.addGrouping(baseMelanin);

            if (splash) {
                TextureGrouping spots = new TextureGrouping(TexturingType.CUTOUT_GROUP);
                TextureGrouping spotsCutout = new TextureGrouping(TexturingType.MERGE_GROUP);
                for (int i=0; i<3;i++) {
                chicken.addTextureToAnimalTextureGrouping(spotsCutout, "feather_colour/spots/splash" + ThreadLocalRandom.current().nextInt(16) + ".png");
                }
                spots.addGrouping(spotsCutout);
                chicken.addTextureToAnimalTextureGrouping(spots, "feather_colour/spots/splash_base.png");
                patternFeatherGroup.addGrouping(spots);
            }
            if (paint) {
                TextureGrouping spots = new TextureGrouping(TexturingType.MERGE_GROUP);
                if (!(gene[38] == 1 && gene[39] == 1)) {
                    spots.setTexturingType(TexturingType.CUTOUT_GROUP);
                    TextureGrouping spotsCutout = new TextureGrouping(TexturingType.MERGE_GROUP);
                    for (int i=0; i<5;i++) {
                        chicken.addTextureToAnimalTextureGrouping(spotsCutout, "feather_colour/spots/paint" + ThreadLocalRandom.current().nextInt(9) + ".png");
                    }
                    spots.addGrouping(spotsCutout);
                }
                chicken.addTextureToAnimalTextureGrouping(spots, "feather_colour/feather_base.png");
                patternFeatherGroup.addGrouping(spots);
            }
    }
    private static int calculateShanksRGB(int[] sGene, int[] gene, boolean isFemale) {
        int colour = 16777215;
        if (isFemale?(sGene[8]==1):(sGene[8]==1 && sGene[9]==1)) {
            if (gene[42]==1 || gene[43]==1) {
                colour = gene[42]==gene[43]? 3289655 : 6579303;
            }
        }

        if (gene[44]!=1 && gene[45]!=1) {
            //yellow legs
            int r = colour >> 16 & 255;
            int g = colour >> 8 & 255;
            int b = colour & 255;

            if (gene[44]==3 && gene[45]==3) {
                g *= 0.8F;
                b *= 0.3F;
            } else {
                r *= 0.85F;
                g *= 0.75F;
                b *= 0.4F;
            }

            colour = r << 16 | g << 8 | b;
        }

        return colour;
    }

    private static int calculateSkinRGB(int[] sGene, int[] gene, boolean isFemale) {
        int colour = 16777215;
        if (isFemale?(sGene[8]==1):(sGene[8]==1 && sGene[9]==1)) {
            if (gene[42]==1 || gene[43]==1) {
                colour = gene[42]==gene[43]? 3289655 : 6579303;
            }
        }

        if (gene[44]!=1 && gene[45]!=1) {
            //yellow legs
            int r = colour >> 16 & 255;
            int g = colour >> 8 & 255;
            int b = colour & 255;

            b -= (int) (b * 0.2F);

            colour = r << 16 | g << 8 | b;
        }

        return colour;
    }

    private static int calculateCombRGB(int[] sexlinkGenes, int[] autosomalGenes, boolean isFemale) {
        if (isFemale?(sexlinkGenes[8]==1):(sexlinkGenes[8]==1 && sexlinkGenes[9]==1)) {
            if (autosomalGenes[42]==1 || autosomalGenes[43]==1) {
                return autosomalGenes[42]==autosomalGenes[43]? 3289655 : 6579303;
            }
        }
        return 16777215;
    }
}
