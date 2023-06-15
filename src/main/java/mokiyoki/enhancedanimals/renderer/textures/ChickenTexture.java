package mokiyoki.enhancedanimals.renderer.textures;

import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import mokiyoki.enhancedanimals.renderer.texture.TextureGrouping;
import mokiyoki.enhancedanimals.renderer.texture.TexturingType;
import mokiyoki.enhancedanimals.util.Genes;

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
            String tailType = "";
            String tailSickle = "";
            String pattern = "";
            String autosomalRed = "";
            String ground = "";
            boolean mottled = gene[22] != 1 && gene[23] != 1;
            boolean charcoal = gene[100] == 2 && gene[101] == 2;
            boolean femFeathers = isFemale || (gene[197]==2||gene[198]==2); //TODO roosters can be het for henny feather and express an intermediate form.

            if (gene[20] == 1 || gene[21] == 1) {
                int patternGene = 4-(gene[26]+gene[27]);
                int columbian = 4-(gene[28]+gene[29]);
                int darkbrown = 4-(gene[98]+gene[99]);
                int melanized = 4-(gene[30]+gene[31]);
                int extension = Math.max(gene[24],gene[25])==5? 5 : Math.min(gene[24],gene[25]);
                //this is for het extension but that's for later
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
                                        case 1 -> {pattern = isNakedNeck? "nakedneck_birchen_female":"birchen_female"; autosomalRed = "duckwing_female";}
                                        case 2 -> {ground = isNakedNeck? "nakedneck_duckwing_female" : "duckwing_female"; pattern = ground; autosomalRed = "duckwing_female";}
                                        case 3 -> {ground = isNakedNeck?"nakedneck_wheaten_female":"wheaten_female"; pattern = "wheaten_female"; autosomalRed = ground;}
                                        case 4 -> {pattern = isNakedNeck ? "nakedneck_brown_female" : "brown_female"; autosomalRed = pattern;}
                                    }
                                } else {
                                    switch (extension) {
                                        case 5 -> pattern = "solid";
                                        case 1 -> {pattern = isNakedNeck? "nakedneck_birchen_male":"birchen_male"; autosomalRed = "duckwing_male";}
                                        case 2,4 -> {pattern ="duckwing_male"; autosomalRed = pattern;}
                                        case 3 -> {pattern = "duckwing_male"; autosomalRed = "wheaten_male";}
                                    }
                                }
                            } else {
                                //melanized gene //TODO het melanized forms
                                if (femFeathers) {
                                    switch (extension) {
                                        case 5,1 -> {pattern = "solid"; autosomalRed = "duckwing_female";}
                                        case 2 -> {ground = isNakedNeck? "nakedneck_duckwing_female" : "duckwing_female"; pattern = isNakedNeck? "nakedneck_ml2duckwing_female" : "ml2duckwing_female"; autosomalRed = "brown_female";} //TODO quail pattern
                                        case 3 -> {ground = isNakedNeck?"nakedneck_wheaten_female":"wheaten_female"; pattern = isNakedNeck?"nakedneck_ml2wheaten_female":"ml2wheaten_female"; autosomalRed = ground;} //TODO furnace wheaten
                                        case 4 -> {pattern = isNakedNeck ? "nakedneck_ml2brown_female" : "ml2brown_female"; autosomalRed = isNakedNeck ? "nakedneck_brown_female" : "brown_female";} //TODO melanized birchen
                                    }
                                } else {
                                    switch (extension) {
                                        case 5 -> pattern = "solid";
                                        case 1 -> pattern=isNakedNeck? "nakedneck_ml2birchen_male":"ml2birchen_male";
                                        case 2,4 -> {autosomalRed = "duckwing_male"; pattern="ml2duckwing_male";}
                                        case 3 -> {pattern = "ml2duckwing_male"; autosomalRed = "wheaten_male";}
                                    }
                                }
                            }
                        } else {
                            //dark brown //TODO het dark brown forms
                            if (melanized==0) {
                                if (femFeathers) {
                                    switch (extension) {
                                        case 5 -> {pattern = "solid"; autosomalRed = "duckwing_female";} //TODO leaky black
                                        case 1 -> {pattern = isNakedNeck?"nakedneck_ml2duckwing_female" : "ml2duckwing_female"; autosomalRed = "brown_female";} //TODO birchen quail
                                        case 2,3,4 -> {ground = isNakedNeck? "nakedneck_duckwing_female" : "duckwing_female"; pattern = "blacktail"; autosomalRed = "duckwing_female";} //TODO
                                    }
                                } else {
                                    switch (extension) {
                                        case 5 -> pattern = "solid"; //TODO should be a leaky black.
                                        case 1,2,3,4 -> pattern = "blacktail"; //TODO investigate how this should actually look
                                    }
                                }
                            } else {
                                //melanized
                                if (femFeathers) {
                                    switch (extension) {
                                        case 5 -> pattern = "solid";//TODO leaky black
                                        case 1,2,4 -> {pattern = isNakedNeck?"nakedneck_ml2duckwing_female" : "ml2duckwing_female"; autosomalRed = "brown_female";} //TODO dark birchen quail
                                        case 3 -> {ground = isNakedNeck? "nakedneck_duckwing_female" : "duckwing_female"; pattern = "blacktail"; autosomalRed = "duckwing_female";} //TODO
                                    }
                                } else {
                                    switch (extension) {
                                        case 5 -> pattern = "solid"; //TODO should be a leaky black.
                                        case 1,2,3,4 -> {pattern = "blacktail"; autosomalRed="duckwing_male";} //TODO investigate how this should actually look
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
                                            case 5,1 -> pattern = "solid";
                                            case 2 -> {pattern = "quail_female"; autosomalRed="duckwing_female";}
                                            case 3 -> pattern = isNakedNeck?"nakedneck_columbian_female":"columbian_female";
                                            case 4 -> pattern = "lakenvelder_female";
                                        }
                                    } else {
                                        switch (extension) {
                                            case 5,1 -> pattern = "solid";
                                            case 2 -> pattern = "quail";
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
                                            case 2, 3, 4 -> pattern = isNakedNeck?"nakedneck_columbian_female":"columbian_female";
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
                                //melanized
                                if (femFeathers) {
                                    switch (extension) {
                                        case 5 -> {pattern = "solid"; autosomalRed = "duckwing_female";}
                                        case 1 -> {pattern = isNakedNeck? "nakedneck_birchen_female":"birchen_female"; autosomalRed = "duckwing_female";}
                                        case 2,3,4 -> pattern = isNakedNeck?"nakedneck_columbian_female":"columbian_female";
                                    }
                                } else {
                                    switch (extension) {
                                        case 5 -> pattern = "solid";
                                        case 1 -> {pattern = isNakedNeck? "nakedneck_birchen_male":"birchen_male"; autosomalRed = "duckwing_male";}
                                        case 2,3,4 -> {pattern = "blacktail"; autosomalRed="duckwing_male";}
                                    }
                                }
                            }
                        } else {
                            //columbian + darkbrown
                            if (melanized == 0) {
                                if (femFeathers) {
                                    switch (extension) {
                                        case 5 -> {pattern = "solid";}
                                        case 1,2,4 -> {pattern = isNakedNeck?"nakedneck_columbian_female":"columbian_female"; autosomalRed="solid";}
                                        case 3 -> {pattern=""; autosomalRed="solid";}
                                    }
                                } else {
                                    switch (extension) {
                                        case 5 -> {pattern = "solid";}
                                        case 1,2,4 -> {pattern = "blacktail"; autosomalRed="solid";}
                                        case 3 -> {pattern=""; autosomalRed="solid";}
                                    }
                                }
                            } else {
                                //columbian + darkbrown + melanized
                                if (femFeathers) {
                                    switch (extension) {
                                        case 1,5 -> pattern = "solid";
                                        case 2 -> {pattern="co2db2ml2duckwing_female"; ground="duckwing_female";autosomalRed="duckwing_female";}
                                        case 3 -> {pattern="co2db2ml2duckwing_female"; ground="wheaten_female";autosomalRed="wheaten_female";}
                                        case 4 -> {pattern="co2db2ml2duckwing_female"; ground="duckwing_female";autosomalRed="brown_female";}
                                    }
                                } else {
                                    switch (extension) {
                                        case 1,5 -> pattern = "solid";
                                        case 2,3,4 -> pattern = "quail";
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
                                        case 1 -> {pattern = isNakedNeck? "nakedneck_birchen_female":"birchen_female"; autosomalRed = "duckwing_female";}
                                        case 2 -> {ground = isNakedNeck? "nakedneck_duckwing_female" : "duckwing_female"; pattern = ground; autosomalRed = "duckwing_female";}
                                        case 3 -> {ground = isNakedNeck?"nakedneck_wheaten_female":"wheaten_female"; pattern = "wheaten_female"; autosomalRed = ground;}
                                        case 4 -> {pattern = isNakedNeck ? "nakedneck_brown_female" : "brown_female"; autosomalRed = pattern;}
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
                                        case 5,1 -> pattern = "solid";
                                        case 2 -> {ground = isNakedNeck? "nakedneck_duckwing_female" : "duckwing_female"; pattern=isNakedNeck?"nakedneck_doublelace_female":"doublelace_female"; autosomalRed = "duckwing_female";}
                                        case 3 -> {ground = isNakedNeck?"nakedneck_wheaten_female":"wheaten_female"; pattern=isNakedNeck?"nakedneck_doublelace_female":"doublelace_female"; autosomalRed = ground;}
                                        case 4 -> {pattern=isNakedNeck?"nakedneck_doublelace_female":"doublelace_female"; autosomalRed = "solid";}
                                    }
                                } else {
                                    switch (extension) {
                                        case 5,1 -> pattern = "solid";
                                        case 2, 3, 4 -> {pattern = "doublelace_male";autosomalRed = "duckwing_male";}
                                    }
                                }
                            }
                        } else {
                            //pattern gene + darkbrown
                            if (melanized == 0) {
                                if (femFeathers) {
                                    switch (extension) {
                                        case 5 -> {pattern="solid";}
                                        case 1,2,4 -> {pattern=isNakedNeck?"nakedneck_autosomalbarred_female":"autosomalbarred_female";ground="duckwing_female";autosomalRed="solid";}
                                        case 3 -> {pattern = isNakedNeck?"nakedneck_columbian_female":"columbian_female"; autosomalRed="solid";}
                                    }
                                } else {
                                    switch (extension) {
                                        case 5 -> {pattern="solid";}
                                        case 1 -> {pattern="autosomalbarred_male";ground="duckwing_male";autosomalRed="duckwing_male";}
                                        case 2,4 -> {pattern="pg2db2duckwing_male";autosomalRed="duckwing_male";} //TODO seems to be a penciled tail only
                                        case 3 -> {pattern="blacktail";autosomalRed="solid";}
                                    }
                                }
                            } else {
                                //pattern gene + darkbrown + melanized
                                switch (extension) {
                                    case 5 -> {pattern="solid";}
                                    case 1,2,3,4 -> {pattern=femFeathers?"spangled_female":(isNakedNeck?"nakedneck_spangled_male":"spangled_male");autosomalRed=femFeathers?"solid":"duckwing_male";}
                                }
                            }
                        }
                    } else {
                        if (darkbrown == 0) {
                            if (melanized == 0) {
                                if (femFeathers) {
                                    //pattern gene + columbian
                                    switch (extension) {
                                        case 5 -> {pattern="solid";}
                                        case 1 -> {pattern = isNakedNeck? "nakedneck_birchen_female":"birchen_female"; autosomalRed = "duckwing_female";}
                                        case 2,3,4 -> {pattern = isNakedNeck?"nakedneck_columbian_female":"columbian_female"; autosomalRed="solid";}
                                    }
                                } else {
                                    switch (extension) {
                                        case 5 -> {pattern="solid";}
                                        case 1 -> {pattern = isNakedNeck? "nakedneck_birchen_male":"birchen_male"; autosomalRed = "duckwing_male";}
                                        case 2,3,4 -> {pattern= isNakedNeck?"nakedneck_columbian_male":"columbian_male";autosomalRed="duckwing_male";}
                                    }
                                }
                            } else {
                                //pattern gene + columbian + melanized
                                if (femFeathers) {
                                    switch (extension) {
                                        case 5,1 -> {pattern="solid";}
                                        case 2,3,4 -> {pattern="singlelace_female";autosomalRed="solid";}
                                    }
                                } else {
                                    switch (extension) {
                                        case 5,1 -> {pattern="solid";}
                                        case 2,3,4 -> {pattern="singlelace_male";autosomalRed="solid";}
                                    }
                                }
                            }
                        } else {
                            //pattern gene + columbian + darkbrown
                            if (melanized == 0) {
                                if (femFeathers) {
                                    switch (extension) {
                                        case 5 -> {pattern="solid";}
                                        case 1 -> {pattern="autosomalbarred_female";ground="duckwing_female";autosomalRed="solid";}
                                        case 2,4 -> {pattern="blacktail";autosomalRed="solid";}
                                        case 3 -> {pattern="";autosomalRed="solid";}
                                    }
                                } else {
                                    switch (extension) {
                                        case 5 -> {pattern="solid";}
                                        case 1 -> {pattern="autosomalbarred_male";ground="duckwing_male";autosomalRed="duckwing_male";}
                                        case 2,4 -> {pattern="blacktail";autosomalRed="duckwing_male";}
                                        case 3 -> {pattern="";autosomalRed="solid";}
                                    }
                                }
                            } else {
                                //pattern gene + columbian + darkbrown + melanized
                                if (femFeathers) {
                                    switch (extension) {
                                        case 5 -> {pattern="solid";}
                                        case 1 -> {pattern="singlelacebirchen_female";autosomalRed="solid";}
                                        case 2,3,4 -> {pattern="spangled_female";autosomalRed="solid";}
                                    }
                                } else {
                                    switch (extension) {
                                        case 5 -> {pattern="solid";}
                                        case 1 -> {pattern="singlelacebirchen_male";autosomalRed="solid";}
                                        case 2,3,4 -> {pattern=isNakedNeck?"nakedneck_spangled_male":"spangled_male";autosomalRed="solid";}
                                    }
                                }
                            }
                        }
                    }
                }
                if (isNakedNeck) {
                    if (ground.startsWith("duckwing")) {
                        ground = "nakedneck_" + ground;
                    }
                    if (!femFeathers && !autosomalRed.equals("solid")) {
                        autosomalRed = autosomalRed.startsWith("wheaten")?"nakedneck_wheaten_male":"nakedneck_duckwing_male";
                    }
                }

            }

            if (isFemale || (gene[196] == 2 || gene[197] == 2)) {
                tailType = "0_female";
            } else {
                tailType = "0_male";
                tailSickle = "0";
            }

            TextureGrouping featherGenerator = new TextureGrouping(TexturingType.SHADE_FEATHERS);
            TextureGrouping parentGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
            TextureGrouping featherGroup = new TextureGrouping(TexturingType.MASK_GROUP);
            TextureGrouping featherMask = new TextureGrouping(TexturingType.MERGE_GROUP);
            if (isNakedNeck) {
                featherMask.setTexturingType(TexturingType.CUTOUT_GROUP);
                TextureGrouping featherCutout = new TextureGrouping(TexturingType.MERGE_GROUP);
                chicken.addTextureToAnimalTextureGrouping(featherCutout, "feather_type/" + (gene[52]==gene[53]? "naked":"bowtie") + "_neck.png", gene[52]==gene[53]?"bt":"nn");
                featherMask.addGrouping(featherCutout);
            }
            chicken.addTextureToAnimalTextureGrouping(featherMask, "feather_type/feathers.png");
            chicken.addTextureToAnimalTextureGrouping(featherMask, "feather_type/tail_" + tailType + ".png", tailType);
            if (!tailSickle.isEmpty()) {
                chicken.addTextureToAnimalTextureGrouping(featherMask, "feather_type/tailsickle_" + tailSickle + ".png", tailSickle);
            } else {
                chicken.addTextureToAnimalTextureGrouping(featherMask,"", false);
            }
            featherGroup.addGrouping(featherMask);
            TextureGrouping baseFeatherColour = new TextureGrouping(TexturingType.MERGE_GROUP);
            if (ground.isEmpty()) {
                chicken.addIndividualTextureToAnimalTextureGrouping(baseFeatherColour, TexturingType.APPLY_RGB, "feather_colour/feather_base.png", calculateGroundRGB(sGene, gene, isFemale));
            } else {
                chicken.addTextureToAnimalTextureGrouping(baseFeatherColour, "feather_colour/feather_base.png");
                chicken.addTextureToAnimalTextureGrouping(baseFeatherColour, TexturingType.APPLY_RGB, "feather_colour/ground/"+ground+".png", ground, calculateGroundRGB(sGene, gene, isFemale));
            }
            if (!autosomalRed.isEmpty() && (gene[20] == 1 || gene[21] == 1)) {
                if (gene[170] == 1 || gene[171] == 1) {
                    chicken.addTextureToAnimalTextureGrouping(baseFeatherColour, TexturingType.APPLY_RGB, "feather_colour/autosomal_red/" + autosomalRed + ".png", autosomalRed, calculateAutosomalRedRGB(gene, isFemale));
                    if (gene[30] == 1 || gene[31] == 1) {
                        chicken.addTextureToAnimalTextureGrouping(baseFeatherColour, TexturingType.APPLY_RGB, "feather_colour/autosomal_red/" + autosomalRed + ".png", autosomalRed, calculateAutosomalRedRGB(gene, isFemale));
                    }
                }
            }
            featherGroup.addGrouping(baseFeatherColour);
            if (gene[20] == 1 || gene[21] == 1) {
                if (!pattern.isEmpty() || mottled || charcoal) {
                    TextureGrouping patternFeatherGroup = new TextureGrouping(TexturingType.MASK_GROUP);
                    TextureGrouping patternCutOutGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                    chicken.addTextureToAnimalTextureGrouping(patternCutOutGroup, "pattern/mottles.png", mottled);
                    chicken.addTextureToAnimalTextureGrouping(patternCutOutGroup, "pattern/" + pattern + ".png", pattern);
                    if (charcoal) {
                        chicken.addTextureToAnimalTextureGrouping(patternCutOutGroup, "pattern/charcoal_" + (femFeathers?"female":"male") + ".png", femFeathers?"f":"m");
                    } else {
                        chicken.addTextureToAnimalTextureGrouping(patternCutOutGroup,"", false);
                    }
                    patternFeatherGroup.addGrouping(patternCutOutGroup);
                    calculatePatternRGB(chicken, patternFeatherGroup, sGene, gene, isFemale, mottled, isNakedNeck);
                    featherGroup.addGrouping(patternFeatherGroup);
                }
                chicken.addTextureToAnimalTextureGrouping(featherGroup, "feather_colour/mottles.png", mottled);
                chicken.addTextureToAnimalTextureGrouping(featherGroup, "feather_colour/barred.png", isFemale ? gene[6] == 2 : gene[6] == 2 || gene[7] == 2);
            } else {
                chicken.addTextureToAnimalTextureGrouping(featherGroup,"", false);
            }
            chicken.addTextureToAnimalTextureGrouping(featherGroup, "feather_colour/feather_noise.png");
            parentGroup.addGrouping(featherGroup);
            TextureGrouping detailGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
            chicken.addIndividualTextureToAnimalTextureGrouping(detailGroup, TexturingType.APPLY_RGB, "skin.png", calculateSkinRGB(sGene, gene, isFemale));
            chicken.addIndividualTextureToAnimalTextureGrouping(detailGroup, TexturingType.APPLY_RGB, "shanks.png", calculateShanksRGB(sGene, gene, isFemale));
            chicken.addIndividualTextureToAnimalTextureGrouping(detailGroup, TexturingType.APPLY_RGB,"comb_wattles.png", calculateCombRGB(sGene, gene, isFemale));
            //            chicken.addTextureToAnimalTextureGrouping(detailGroup, TexturingType.APPLY_DYE, "ear.png");
            //            chicken.addTextureToAnimalTextureGrouping(detailGroup, TexturingType.APPLY_EYE_RIGHT_COLOUR, "ear.png");
            chicken.addIndividualTextureToAnimalTextureGrouping(detailGroup, TexturingType.APPLY_RGB, "eyes.png", calculateEyeRGB(sGene, gene, isFemale));

            chicken.addTextureToAnimalTextureGrouping(detailGroup, "map/bars.png");

            parentGroup.addGrouping(detailGroup);

            featherGenerator.addGrouping(parentGroup);
            chicken.addTextureToAnimalTextureGrouping(featherGenerator, "map/feather_map.png");
            chicken.addTextureToAnimalTextureGrouping(featherGenerator, "map/feathers/feather_0.png");
            chicken.setTextureGrouping(featherGenerator);
        }
    }

    private static int calculateEyeRGB(int[] sGene, int[] gene, boolean isFemale) {
        if (gene[20]==3&&gene[21]==3) {
            return 14560322;
        } else {
            return 0;
        }
    }

    private static int calculateGroundRGB(int[] sGene, int[] gene, boolean isFemale) {
        int colour = 16777215;
        if (gene[20] != 1 && gene[21] != 1) return 16777215;
        if (isFemale) {
            if (sGene[0] == 1) {
                colour = gene[31]==1 || gene[32]==1 ? 12475671 : 14066221;
            }
        } else {
            if (sGene[0] == 1 || sGene[1] == 1) {
                if (sGene[0]==sGene[1]) {
                    colour = gene[31]==1 || gene[32]==1 ? 12475671 : 14066221;
                } else {
                    colour = gene[31]==1 || gene[32]==1 ? 14066221 : 13808740;
                }
            }

            if (gene[170]==1 || gene[171]==1) {
                int b = colour & 255;
                int g = colour >> 8 & 255;
                int r = colour >> 16 & 255;

                b -= (int)(b*0.25F);

                colour = r << 16 | g << 8 | Math.max(b,0);
            }
        }

        if (colour!=16777215) {
            //Dilute
            if (gene[32] != 3 || gene[33] != 3) {
                int b = colour & 255;
                int g = colour >> 8 & 255;
                int r = colour >> 16 & 255;

                b += (int) ((255 - b) * 0.1F);
                g += (int) ((255 - g) * 0.2F);
                r += (int) ((255 - r) * (gene[32]==1||gene[33]==1?0.2F:0.3F));

                colour = Math.min(r, 255) << 16 | Math.min(g, 255) << 8 | Math.min(b, 255);
            }

            //Lavender
            if (gene[36] == 2 && gene[37] == 2) {
                int b = colour & 255;
                int g = colour >> 8 & 255;
                int r = colour >> 16 & 255;

                b += (int) ((255 - b) * 0.65F);
                g += (int) ((255 - g) * 0.7F);
                r += (int) ((255 - r) * 0.7F);

                colour = r << 16 | g << 8 | b;
            }
        }

        return colour;
    }

    private static int calculateAutosomalRedRGB(int[] gene, boolean isFemale) {
        int colour = 16777215;
        if (isFemale) {
            if (gene[170] == 1 || gene[171] == 1) {
                colour = gene[170]==gene[171]?10769441:16760196;
            }
        } else {
            if (gene[170] == 1 || gene[171] == 1) {
                if (gene[170]==gene[171]) {
                    //gold
                    colour = gene[31]==1 || gene[32]==1 ? 5639947 : 8658186;
                } else {
                    //lemon
                    colour = gene[31]==1 || gene[32]==1 ? 8658186 : 10769441;
                }
            }
        }

        if (colour!=16777215) {
            //Lavender
            if (gene[36] == 2 && gene[37] == 2) {
                int r = colour & 255;
                int g = colour >> 8 & 255;
                int b = colour >> 16 & 255;

                r += (int) ((255 - r) * 0.70F);
                g += (int) ((255 - g) * 0.70F);
                b += (int) ((255 - b) * 0.65F);

                colour = b << 16 | g << 8 | r;
            }
        }

        return colour;
    }

    private static void calculatePatternRGB(EnhancedChicken chicken, TextureGrouping patternFeatherGroup, int[] sGene, int[] gene, boolean isFemale, boolean mottled, boolean isNakedNeck) {
        float patternHue = 0.7F;
        float patternSaturation = 0.0F;
        float patternValue = 0.01F;

        float iridescenceAlpha = 1.0F;
        float iridescenceHueShift = 0.0F;

        boolean splash = false;
        boolean paint = gene[38] == 1 || gene[39] == 1;

        if (gene[40] == 2 || gene[41] == 2) {
            if (gene[40] == gene[41]) {
                splash = true;
            }
                //Blue
                patternHue = 0.6F;
                patternSaturation = 0.2F;
                patternValue = 0.4F;
                iridescenceAlpha = 0.0F;
        }

        if (sGene[2] == 2 && (isFemale || sGene[3] == 2)) {
            //Choc
            patternHue = 0.05F;
            patternSaturation += patternSaturation + ((1.0F-patternSaturation)*0.75F);
            patternValue += (patternValue + 1.0F) * 0.25F;
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
            patternValue += (1.0F - patternValue) * (0.75F + (0.5 * (Math.min(patternSaturation,0.5F))));
            iridescenceAlpha = 0.0F;
        }

        TextureGrouping baseMelanin = new TextureGrouping(TexturingType.MERGE_GROUP);
        chicken.addIndividualTextureToAnimalTextureGrouping(baseMelanin, TexturingType.APPLY_RGB, "feather_colour/feather_base.png", Colouration.HSBtoARGB(patternHue, patternSaturation, patternValue));
        chicken.addTextureToAnimalTextureGrouping(baseMelanin, TexturingType.APPLY_SHIFT, "feather_colour/"+ (isFemale?"iridescence_female":(isNakedNeck?"nakedneck_iridescence_male":"iridescence_male")) +".png", isFemale? "f" : (isNakedNeck?"nm":"m"), (int)((1.0F-iridescenceAlpha)*255) << 8 | (int)(iridescenceHueShift*255));
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

            b -= (b * 0.2F);

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

    public static int calculateChickenEyeRGB(int[] sexlinkGenes, int[] autosomalGenes, boolean isFemale) {
        return 0;
    }

    public static void calculateChickenRGB(Colouration colouration, Genes genes, boolean isFemale) {
        if (genes != null) {
            if (colouration.getPheomelaninColour() == -1 || colouration.getMelaninColour() == -1) {
                int[] sGene = genes.getAutosomalGenes();
                int[] gene = genes.getAutosomalGenes();




            }
        }
    }


//
//    @OnlyIn(Dist.CLIENT)
//    protected void setTexturePaths() {
//        if(this.getSharedGenes()!=null) {
//            int[] sexlinkedGenes = getSharedGenes().getSexlinkedGenes();
//            int[] autosomalGenes = getSharedGenes().getAutosomalGenes();
//            boolean isFemale = this.getOrSetIsFemale();
//            if (this.getEnhancedAnimalAge() >= (int)(this.getFullSizeAge()*0.333F)) {
//                int ground = 0;
//                int pattern = 0;
//                int moorhead = 0;
//                int white = 0;
//                int mottles = 0;
//                int shanks = 3;
//                int comb = 2;
//                int eyes = 1;
//                int ptrncolours = 10; //number of pattern colours
//
//                int moorheadtoggle = 0;
//                int melanin = 0;
//
//                int face = 0;
//                int ears = 0;
//                int earsW = 0;
//
//                //TODO fix up columbian type patterns to look more varried
//                //TODO add in heterozygous pattern variations
//                //TODO redo ground colours to use autosomal red and more fleshed out
//
//                boolean isAlbino = false;
//
//                if (autosomalGenes[20] != 1 && autosomalGenes[21] != 1) {                                                                       //checks if not wildtype
//                    if (autosomalGenes[20] == 2 || autosomalGenes[21] == 2) {                                                                   //sets recessive white or albino
//                        //recessive white
//                        ground = 15;
//                        pattern = 361;
//                    } else {
//                        //albino
//                        ground = 15;
//                        pattern = 361;
//                        white = 0;
//                        shanks = 6;
//                        comb = 2;
//                        eyes = 0;
//                        isAlbino = true;
//                    }
//                } else {
//                    if (autosomalGenes[24] == 5 || autosomalGenes[25] == 5) {
//                        //extended black tree
//                        if (autosomalGenes[24] == 5 && autosomalGenes[25] == 5) {
//                            if (autosomalGenes[28] == 1 && autosomalGenes[29] == 1 && autosomalGenes[98] == 1 && autosomalGenes[99] == 1) {
//                                //xtradark birchen
//                                pattern = 17;
//                                ground = 0;
//                            } else {
//                                //solid black
//                                pattern = 0;
//                                ground = 15;
//                            }
//                        } else if (autosomalGenes[24] == 1 || autosomalGenes[25] == 1) {
//                            //xtradark birchen
//                            pattern = 17;
//                            ground = 0;
//                        } else {
//                            if (autosomalGenes[28] == 1 && autosomalGenes[29] == 1 && autosomalGenes[98] == 1 && autosomalGenes[99] == 1) {
//                                //leaky black
//                                pattern = 18;
//                                ground = 0;
//                            } else {
//                                //xtradark birchen
//                                pattern = 17;
//                                ground = 0;
//                            }
//                        }
//                    } else if (autosomalGenes[24] == 1 || autosomalGenes[25] == 1) {
//                        //birchen tree
//                        if (autosomalGenes[28] == 1 && autosomalGenes[29] == 1) {
//                            if (autosomalGenes[98] == 1 && autosomalGenes[99] == 1) {
//                                if (autosomalGenes[30] == 1 && autosomalGenes[31] == 1) {
//                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
//                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                            //xtra dark birchen single lace
//                                            pattern = 19;
//                                            ground = 15;
//                                        } else {
//                                            //birchen single laced
//                                            pattern = 11;
//                                            ground = 15;
//                                        }
//                                    } else {
//                                        //extended patterned columbian
//                                        pattern = 5;
//                                        ground = 15;
//                                    }
//                                } else if (autosomalGenes[30] == 1 || autosomalGenes[31] == 1) {
//                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
//                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                            //moorhead doublehalfspangled
//                                            pattern = 20;
//                                            ground = 15;
//                                            moorhead = 1;
//                                        } else {
//                                            //doublehalfspangle
//                                            pattern = 20;
//                                            ground = 15;
//                                        }
//
//                                    } else {
//                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                            //overly dark columbian
//                                            pattern = 5;
//                                            ground = 15;
//                                        } else {
//                                            //moorheaded columbian
//                                            pattern = 5;
//                                            ground = 15;
//                                            moorhead = 1;
//                                        }
//                                    }
//                                } else {
//                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
//                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                            //moorhead doublehalfspangled
//                                            pattern = 20;
//                                            ground = 15;
//                                            moorhead = 1;
//                                        } else {
//                                            //doublehalfspangle
//                                            pattern = 20;
//                                            ground = 15;
//                                        }
//
//                                    } else {
//                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                            //moorhead transverse penciled
//                                            pattern = 34;
//                                            ground = 15;
//                                            moorhead = 1;
//                                        } else {
//                                            //transverse penciled
//                                            pattern = 10;
//                                            ground = 15;
//                                        }
//                                    }
//                                }
//                            } else if (autosomalGenes[98] == 1 || autosomalGenes[99] == 1) {
//                                if (autosomalGenes[30] == 1 || autosomalGenes[31] == 1) {
//                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
//                                        //dark doublehalfspangle
//                                        pattern = 21;
//                                        ground = 15;
//                                    } else {
//                                        //dark messy quail
//                                        pattern = 22;
//                                        ground = 15;
//                                    }
//                                } else {
//                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
//                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                            //dark transverse penciled
//                                            //TODO what are the different qualities of transverse penciled
//                                            pattern = 34;
//                                            ground = 5;
//                                            moorhead = 1;
//                                        } else {
//                                            //incomplete penciled
//                                            //TODO make incomplete transverse penciled
//                                            pattern = 10;
//                                            ground = 5;
//                                        }
//                                    } else {
//                                        //dark quail mealy
//                                        pattern = 23;
//                                        ground = 5;
//                                    }
//
//                                }
//                            } else {
//                                if (autosomalGenes[30] == 1 || autosomalGenes[31] == 1) {
//                                    if (autosomalGenes[100] == 2 || autosomalGenes[101] == 2) {
//                                        //solid black
//                                        pattern = 0;
//                                        ground = 15;
//                                    } else {
//                                        // leaky black
//                                        pattern = 18;
//                                        ground = 0;
//                                    }
//                                } else {
//                                    if (autosomalGenes[100] == 1 && autosomalGenes[101] == 1) {
//                                        //leaky black
//                                        pattern = 18;
//                                        ground = 5;
//                                    } else {
//                                        //birchen
//                                        pattern = 1;
//                                        ground = 5;
//                                    }
//                                }
//                            }
//                        } else if (autosomalGenes[28] == 1 || autosomalGenes[29] == 1) {
//                            if (autosomalGenes[98] == 1 || autosomalGenes[99] == 1) {
//                                if (autosomalGenes[30] == 1 || autosomalGenes[31] == 1) {
//                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
//                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                            //extended patterned halfspangle
//                                            //TODO what is this pattern really?
//                                            pattern = 16;
//                                            ground = 15;
//                                            moorhead = 1;
//                                        } else {
//                                            //halfspangle
//                                            pattern = 16;
//                                            ground = 15;
//                                        }
//                                    } else {
//                                        //incomplete columbian
//                                        pattern = 6;
//                                        ground = 15;
//                                    }
//                                } else {
//                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
//                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                            //extended patterned transverse penciled
//                                            pattern = 34;
//                                            ground = 15;
//                                            moorhead = 1;
//                                        } else {
//                                            // transverse penciled
//                                            pattern = 10;
//                                            ground = 5;
//                                        }
//                                    } else {
//                                        // columbian
//                                        pattern = 5;
//                                        ground = 15;
//
//                                    }
//                                }
//                            } else {
//                                if (autosomalGenes[30] == 1 || autosomalGenes[31] == 1) {
//                                    if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                        //solid black
//                                        pattern = 0;
//                                        ground = 15;
//                                    } else {
//                                        //leaky black
//                                        pattern = 18;
//                                        ground = 15;
//                                    }
//                                } else {
//                                    if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                        //leaky black
//                                        pattern = 18;
//                                        ground = 15;
//                                    } else {
//                                        //birchen
//                                        pattern = 1;
//                                        ground = 0;
//                                    }
//                                }
//                            }
//                        } else {
//                            if (autosomalGenes[98] == 1 || autosomalGenes[99] == 1) {
//                                if (autosomalGenes[30] == 1 || autosomalGenes[31] == 1) {
//                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
//                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                            //extended patterned spangled
//                                            pattern = 14;
//                                            ground = 15;
//                                        } else {
//                                            // spangled
//                                            pattern = 16;
//                                            ground = 15;
//                                        }
//                                    } else {
//                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                            //extended patterned incomplete quail
//                                            pattern = 24;
//                                            ground = 15;
//                                        } else {
//                                            // incomplete quail
//                                            pattern = 29;
//                                            ground = 5;
//                                        }
//                                    }
//                                } else {
//                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
//                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                            //extended traverse penciled
//                                            pattern = 34;
//                                            ground = 15;
//                                            moorhead = 1;
//                                        } else {
//                                            //traverse penciled
//                                            pattern = 10;
//                                            ground = 5;
//                                        }
//                                    } else {
//                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                            //extended patterned incomplete quail
//                                            pattern = 24;
//                                            ground = 5;
//                                            moorhead = 1;
//                                        } else {
//                                            // incomplete quail
//                                            pattern = 29;
//                                            ground = 5;
//                                        }
//                                    }
//                                }
//                            } else {
//                                if (autosomalGenes[30] == 1 || autosomalGenes[31] == 1) {
//                                    //solid black
//                                    pattern = 0;
//                                    ground = 15;
//                                } else {
//                                    if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                        //leaky black
//                                        pattern = 18;
//                                        ground = 5;
//                                    } else {
//                                        //birchen
//                                        pattern = 1;
//                                        ground = 5;
//                                    }
//                                }
//                            }
//                        }
//
//                    } else if (autosomalGenes[24] == 2 || autosomalGenes[25] == 2) {
//                        //duckwing tree
//                        if (autosomalGenes[28] == 1 || autosomalGenes[29] == 1) {
//                            if (autosomalGenes[98] == 1 || autosomalGenes[99] == 1) {
//                                if (autosomalGenes[30] == 1 || autosomalGenes[31] == 1) {
//                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
//                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                            // extended patterned halfspangled
//                                            pattern = 16;
//                                            ground = 0;
//                                        } else {
//                                            //  halfspangled
//                                            pattern = 25;
//                                            ground = 0;
//                                        }
//                                    } else {
//                                        //  incomplete quail
//                                        pattern = 29;
//                                        ground = 10;
//
//                                    }
//                                } else {
//                                    if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                        //  moorhead columbian w/ less hackle markings
//                                        pattern = 6;
//                                        ground = 0;
//                                        moorhead = 1;
//                                    } else {
//                                        //  columbian w/ less hackle markings
//                                        pattern = 6;
//                                        ground = 0;
//                                    }
//                                }
//                            } else {
//                                if (autosomalGenes[30] == 1 || autosomalGenes[31] == 1) {
//                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
//                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                            // extended patterned incomplete laced
//                                            pattern = 27;
//                                            ground = 5;
//                                            moorhead = 1;
//                                        } else {
//                                            //  incomplete laced
//                                            pattern = 27;
//                                            ground = 5;
//                                        }
//                                    } else {
//                                        //  quail
//                                        pattern = 4;
//                                        ground = 5;
//                                    }
//                                } else {
//                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
//                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                            // extended patterned incomplete laced?
//                                            pattern = 27;
//                                            ground = 15;
//                                            moorhead = 1;
//                                        } else {
//                                            //  columbian
//                                            pattern = 5;
//                                            ground = 15;
//                                        }
//                                    } else {
//                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                            //  incomplete quail
//                                            pattern = 29;
//                                            ground = 15;
//                                        } else {
//                                            //  columbian
//                                            pattern = 5;
//                                            ground = 15;
//                                        }
//                                    }
//                                }
//                            }
//                        } else {
//                            if (autosomalGenes[98] == 1 || autosomalGenes[99] == 1) {
//                                if (autosomalGenes[30] == 1 || autosomalGenes[31] == 1) {
//                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
//                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                            // extended patterned spangled
//                                            pattern = 14;
//                                            ground = 15;
//                                        } else {
//                                            //  spangled
//                                            pattern = 16;
//                                            ground = 15;
//                                        }
//                                    } else {
//                                        //  incomplete quail
//                                        pattern = 29;
//                                        ground = 15;
//                                    }
//                                } else {
//                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
//                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                            // extended patterned transverse pencilled
//                                            pattern = 34;
//                                            ground = 5;
//                                        } else {
//                                            //  transverse pencilled
//                                            pattern = 10;
//                                            ground = 5;
//                                        }
//                                    } else {
//                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                            //  incomplete quail
//                                            pattern = 29;
//                                            ground = 15;
//                                        } else {
//                                            //  incomplete columbian w/ less hackle markings
//                                            pattern = 30;
//                                            ground = 15;
//
//                                        }
//                                    }
//                                }
//                            } else {
//                                if (autosomalGenes[30] == 1 || autosomalGenes[31] == 1) {
//                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
//                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                            // extended patterned incomplete doublelaced
//                                            pattern = 21;
//                                            ground = 5;
//                                            moorhead = 1;
//                                        } else {
//                                            //  incomplete doublelaced
//                                            pattern = 21;
//                                            ground = 5;
//                                        }
//                                    } else {
//                                        //  incomplete quail
//                                        pattern = 29;
//                                        ground = 5;
//
//                                    }
//                                } else {
//                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
//                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                            // extended patterned multiple laced duckwing
//                                            pattern = 26;
//                                            ground = 0;
//                                            moorhead = 1;
//                                        } else {
//                                            //  multiple laced duckwing
//                                            pattern = 15;
//                                            ground = 0;
//                                        }
//                                    } else {
//                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                            //  incomplete quail
//                                            pattern = 29;
//                                            ground = 0;
//                                        } else {
//                                            // duckwing
//                                            pattern = 2;
//                                            ground = 0;
//                                        }
//                                    }
//                                }
//                            }
//                        }
//
//
//                    } else if (autosomalGenes[24] == 3 || autosomalGenes[25] == 3) {
//                        //wheaten tree
//                        if (autosomalGenes[28] == 1 || autosomalGenes[29] == 1) {
//                            if (autosomalGenes[98] == 1 || autosomalGenes[99] == 1) {
//                                if (autosomalGenes[30] == 1 || autosomalGenes[31] == 1) {
//                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
//                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                            // extended patterned halfspangled
//                                            pattern = 25;
//                                            ground = 5;
//                                        } else {
//                                            //  halfspangled
//                                            pattern = 25;
//                                            ground = 5;
//                                        }
//                                    } else {
//                                        // extended patterened incomplete columbian w/ less hackle markings
//                                        pattern = 30;
//                                        ground = 15;
//                                    }
//                                } else {
//                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
//                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                            // moorhead incomplete columbian w/ less hackle markings
//                                            pattern = 361;
//                                            ground = 15;
//                                            moorhead = 1;
//                                        } else {
//                                            // nearly buff
//                                            pattern = 9;
//                                            ground = 15;
//                                        }
//                                    } else {
//                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                            // moorhead incomplete columbian w/ less hackle markings
//                                            pattern = 8;
//                                            ground = 15;
//                                        } else {
//                                            // buff
//                                            pattern = 361;
//                                            ground = 15;
//                                        }
//                                    }
//                                }
//                            } else {
//                                if (autosomalGenes[30] == 1 || autosomalGenes[31] == 1) {
//                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
//                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                            // extended patterned incomplete laced
//                                            pattern = 20;
//                                            ground = 5;
//                                        } else {
//                                            //  incomplete laced
//                                            pattern = 27;
//                                            ground = 5;
//                                        }
//                                    } else {
//                                        //  quail
//                                        pattern = 4;
//                                        ground = 5;
//                                    }
//                                } else {
//                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
//                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                            // extended patterned incomplete laced
//                                            pattern = 20;
//                                            ground = 5;
//                                        } else {
//                                            //  columbian
//                                            pattern = 5;
//                                            ground = 15;
//                                        }
//                                    } else {
//                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                            // extended patterned columbian
//                                            pattern = 5;
//                                            ground = 15;
//                                            moorhead = 1;
//                                        } else {
//                                            // columbian
//                                            pattern = 5;
//                                            ground = 15;
//                                        }
//                                    }
//                                }
//                            }
//                        } else {
//                            if (autosomalGenes[98] == 1 || autosomalGenes[99] == 1) {
//                                if (autosomalGenes[30] == 1 || autosomalGenes[31] == 1) {
//                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
//                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                            // extended patterned spangled
//                                            pattern = 14;
//                                            ground = 15;
//                                        } else {
//                                            // spangled
//                                            pattern = 16;
//                                            ground = 15;
//                                        }
//                                    } else {
//                                        // extended patterned incomplete columbian w/ less hackle markings
//                                        pattern = 31;
//                                        ground = 15;
//                                    }
//                                } else {
//                                    if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                        // extended patterned incomplete columbian w/ less hackle markings
//                                        pattern = 30;
//                                        ground = 15;
//                                    } else {
//                                        // incomplete columbian w/ less hackle markings
//                                        pattern = 3;
//                                        ground = 15;
//                                    }
//                                }
//                            } else {
//                                if (autosomalGenes[30] == 1 || autosomalGenes[31] == 1) {
//                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
//                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                            // extended patterned doublelaced
//                                            pattern = 13;
//                                            ground = 15;
//                                            moorhead = 1;
//                                        } else {
//                                            // double laced
//                                            pattern = 13;
//                                            ground = 15;
//                                        }
//                                    } else {
//                                        // extended patterned wheaten
//                                        pattern = 28;
//                                        ground = 10;
//                                    }
//                                } else {
//                                    if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                        // extended patterned wheaten
//                                        pattern = 28;
//                                        ground = 10;
//                                    } else {
//                                        // wheaten
//                                        pattern = 3;
//                                        ground = 10;
//                                    }
//                                }
//                            }
//                        }
//
//                    } else if (autosomalGenes[24] == 4 || autosomalGenes[25] == 4) {
//                        //partidge tree
//                        if (autosomalGenes[28] == 1 || autosomalGenes[29] == 1) {
//                            if (autosomalGenes[98] == 1 || autosomalGenes[99] == 1) {
//                                if (autosomalGenes[30] == 1 || autosomalGenes[31] == 1) {
//                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
//                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                            // extended patterened halfspangled
//                                            pattern = 16;
//                                            ground = 15;
//                                            moorhead = 1;
//                                        } else {
//                                            // halfspangled
//                                            pattern = 16;
//                                            ground = 15;
//                                        }
//                                    } else {
//                                        // extended patterened incomplete quail
//                                        pattern = 24;
//                                        ground = 5;
//                                    }
//                                } else {
//                                    if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                        // moorhead
//                                        pattern = 8;
//                                        ground = 15;
//                                    } else {
//                                        // incomplete columbian w/ less hackle markings
//                                        pattern = 30;
//                                        ground = 15;
//                                    }
//                                }
//                            } else {
//                                if (autosomalGenes[30] == 1 || autosomalGenes[31] == 1) {
//                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
//                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                            // extended patterned single laced
//                                            pattern = 33;
//                                            ground = 15;
//                                            moorhead = 1;
//                                        } else {
//                                            // single laced
//                                            pattern = 12;
//                                            ground = 15;
//                                        }
//                                    } else {
//                                        // quail
//                                        pattern = 4;
//                                        ground = 5;
//                                    }
//                                } else {
//                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
//                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                            // extended patterned incomplete single laced
//                                            pattern = 32;
//                                            ground = 15;
//                                        } else {
//                                            // columbian
//                                            pattern = 5;
//                                            ground = 15;
//                                        }
//                                    } else {
//                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                            // lakenvelder
//                                            pattern = 7;
//                                            ground = 15;
//                                        } else {
//                                            // columbian
//                                            pattern = 5;
//                                            ground = 15;
//                                        }
//                                    }
//                                }
//                            }
//                        } else {
//                            if (autosomalGenes[98] == 1 || autosomalGenes[99] == 1) {
//                                if (autosomalGenes[30] == 1 || autosomalGenes[31] == 1) {
//                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
//                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                            // extended patterned spangled
//                                            pattern = 14;
//                                            ground = 15;
//                                        } else {
//                                            // spangled
//                                            pattern = 16;
//                                            ground = 15;
//                                        }
//                                    } else {
//                                        // incomplete quail
//                                        pattern = 29;
//                                        ground = 15;
//                                    }
//                                } else {
//                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
//                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                            // extended patterned transverse penciled
//                                            pattern = 34;
//                                            ground = 5;
//                                        } else {
//                                            // transverse penciled
//                                            pattern = 10;
//                                            ground = 5;
//                                        }
//                                    } else {
//                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                            // incomplete quail
//                                            pattern = 29;
//                                            ground = 15;
//
//                                        } else {
//                                            // incomplete columbian w/ less hackle markings
//                                            pattern = 30;
//                                            ground = 15;
//                                        }
//                                    }
//                                }
//                            } else {
//                                if (autosomalGenes[30] == 1 || autosomalGenes[31] == 1) {
//                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
//                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                            // extended patterned doublelaced
//                                            pattern = 13;
//                                            ground = 15;
//                                            moorhead = 1;
//                                        } else {
//                                            // doublelaced
//                                            pattern = 13;
//                                            ground = 15;
//                                        }
//                                    } else {
//                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                            // extended patterned partridge/brown halfspangled/laced? but darker head?
//                                            pattern = 20;
//                                            ground = 5;
//                                            moorhead = 1;
//                                        } else {
//                                            // extended patterned partridge/brown halfspangled/laced?
//                                            pattern = 20;
//                                            ground = 5;
//                                        }
//                                    }
//                                } else {
//                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
//                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                            // extended patterned multiple laced partridge
//                                            pattern = 26;
//                                            ground = 5;
//                                            moorhead = 1;
//                                        } else {
//                                            // multiple laced partridge
//                                            pattern = 15;
//                                            ground = 5;
//                                        }
//                                    } else {
//                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                            // extended patterned partridge
//                                            pattern = 35;
//                                            ground = 5;
//                                            moorhead = 1;
//                                        } else {
//                                            // partridge
//                                            pattern = 35;
//                                            ground = 5;
//                                        }
//                                    }
//                                }
//                            }
//                        }
//
//                    } else {
//                        eyes = 0;
//                    }
//
//
//                    int groundMod = 0;
//                    //ground colour tint
//                    if (isFemale) {
//                        if (sexlinkedGenes[0] == 1){
//                            //gold
//                            groundMod = groundMod + 2;
//                        }
//                    } else {
//                        if (sexlinkedGenes[0] == 1 && sexlinkedGenes[1] == 1) {
//                            //gold
//                            groundMod = groundMod + 2;
//                        } else if (sexlinkedGenes[0] == 1 || sexlinkedGenes[1] == 1) {
//                            //lemon
//                            groundMod = groundMod + 1;
//                        }
//                    }
//                    if (groundMod != 0 && (autosomalGenes[32] == 3 && autosomalGenes[33] == 3)) {
//                        //lemon or cream but backwards
//                        groundMod = groundMod + 1;
//                    }
//                    if (autosomalGenes[34] == 1 || autosomalGenes[35] == 1) {
//                        //mahogany or lemon cream counter
//                        groundMod = groundMod + 1;
//                    }
//                    if (autosomalGenes[36] == 2 && autosomalGenes[37] == 2) {
//                        groundMod = groundMod - 1;
//                    }
//
//                    if (groundMod < 0) {
//                        groundMod = 0;
//                    } else if (groundMod > 4) {
//                        groundMod = 4;
//                    }
//
//                    ground = ground + groundMod;
//
//                    if (pattern <= 340) {
//                        if (moorhead == 1) {
//                            moorheadtoggle = 1;
//                        }
//                        //black pattern shade genes
//                        //sets pattern to correct positioning pre:variation
//                        pattern = (pattern * ptrncolours);
//                        if (autosomalGenes[38] == 1 && autosomalGenes[39] == 1) {
//                            //domwhite
//                            pattern = pattern + 7;
//                            moorhead = 8;
//                        } else if (autosomalGenes[38] == 1 || autosomalGenes[39] == 1) {
//                            // spotted domwhite
//                            pattern = pattern + 7;
//                            moorhead = 8;
//                        } else {
//                            //if chocolate
//                            if (sexlinkedGenes[2] == 2 && (getOrSetIsFemale() || sexlinkedGenes[3] == 2)) {
//                                //if lavender
//                                if (autosomalGenes[36] == 2 && autosomalGenes[37] == 2) {
//                                    //is a dun variety
//                                    //if it is splash
//                                    if (autosomalGenes[40] == 2 && autosomalGenes[41] == 2) {
//                                        //splash dun
//                                        pattern = pattern + 4;
//                                        moorhead = 5;
//                                    } else {
//                                        //dun
//                                        pattern = pattern + 8;
//                                        moorhead = 9;
//                                    }
//                                } else {
//                                    //is a chocolate variety
//                                    if (autosomalGenes[40] == 2 && autosomalGenes[41] == 2) {
//                                        //splash choc
//                                        pattern = pattern + 5;
//                                        moorhead = 6;
//                                    } else if (autosomalGenes[40] != 1 || autosomalGenes[41] != 1) {
//                                        //dun
//                                        pattern = pattern + 8;
//                                        moorhead = 9;
//                                    } else {
//                                        //chocolate
//                                        pattern = pattern + 9;
//                                        moorhead = 10;
//                                    }
//                                }
//                            } else {
//                                //if lavender
//                                if (autosomalGenes[36] == 2 && autosomalGenes[37] == 2) {
//                                    //is a lavender variety
//                                    //if it is splash
//                                    if (autosomalGenes[40] == 2 && autosomalGenes[41] == 2) {
//                                        //splash lavender
//                                        pattern = pattern + 3;
//                                        moorhead = 4;
//                                    } else {
//                                        //lavender
//                                        pattern = pattern + 6;
//                                        moorhead = 7;
//                                    }
//                                } else {
//                                    //is a black variety
//                                    if (autosomalGenes[40] == 2 && autosomalGenes[41] == 2) {
//                                        //splash
//                                        pattern = pattern + 2;
//                                        moorhead = 3;
//                                    } else if (autosomalGenes[40] == 2 || autosomalGenes[41] == 2) {
//                                        //blue
//                                        if ((autosomalGenes[26] == 1 || autosomalGenes[27] == 1) && (autosomalGenes[24] == 5 || autosomalGenes[25] == 5)) {
//                                            //blue laced ... super special genes combo for blue andalusian type pattern
//                                            pattern = 360;
//                                            if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                                                if (autosomalGenes[30] == 1 || autosomalGenes[31] == 1) {
//                                                    moorhead = 1;
//                                                }
//                                            } else {
//                                                moorhead = 2;
//                                            }
//                                        } else {
//                                            //blue
//                                            pattern = pattern + 1;
//                                            moorhead = 2;
//                                        }
//
//                                    }
//                                }
//                            }
//                        }
//                    }
//                    mottles = moorhead == 0 ? 1 : moorhead;
//                    if (moorheadtoggle == 0) {
//                        moorhead = 0;
//                    }
//                }
//
//                //white marking autosomalGenes
//                if (!this.getOrSetIsFemale() && sexlinkedGenes[6] == 2 && sexlinkedGenes[7] == 2) {
//                    //Light Barred
//                    white = 2;
//                } else if ((this.getOrSetIsFemale() && sexlinkedGenes[6] == 2) || (!this.getOrSetIsFemale() && (sexlinkedGenes[6] == 2 ^ sexlinkedGenes[7] == 2))) {
//                    //Dark Barred
//                    white = 1;
//                } else {
//                    if (autosomalGenes[22] >= 2 && autosomalGenes[23] >= 2) {
//                        if (autosomalGenes[22] == 2 && autosomalGenes[23] == 2) {
//                            //mottled
//                            white = 4;
//                        } else if (autosomalGenes[22] == 3 && autosomalGenes[23] == 3) {
//                            //white crest
//                            white = 3;
//                        } else {
//                            //mottled and white crest
//                            white = 5;
//                        }
//                    }
//                }
//
//                // figures out the shank, comb, and skin colour if its not albino
//                if (!isAlbino) {
//                    //gets comb colour
//                    if (((isFemale && sexlinkedGenes[8] == 1) || (!isFemale && (sexlinkedGenes[8] == 1 || sexlinkedGenes[9] == 1))) && (autosomalGenes[42] == 1 || autosomalGenes[43] == 1)) {
//                        //comb and shanks are fibro black
//                        comb = -1;
//                        shanks = 6;
//                    } else {
//                        comb = 5;
//                    }
//                    if (autosomalGenes[24] == 5 || autosomalGenes[25] == 5) {
//                        shanks = 5;
////                        if (comb != 0) {
////                            comb = 5;
////                        }
////                        // makes mulbery comb
////                        if (autosomalGenes[30] == 2) {
////                            comb = comb + 1;
////                        }
//                    } else if (autosomalGenes[24] == 1 && autosomalGenes[25] == 1) {
//                        shanks = 5;
////                        if (comb != 0) {
////                            comb = 5;
////                        }
//                    } else if (autosomalGenes[24] == 1 || autosomalGenes[25] == 1) {
//                        shanks = 5;
////                        if (comb != 0) {
////                            comb = 5;
////                        }
//                    }
//                    //shanks starts at 3 btw
//
//                    if (shanks > 2) {
//                        // if splash or blue lighten by 1 shade
//                        if (autosomalGenes[40] == 2 || autosomalGenes[41] == 2) {
//                            shanks--;
//                        }
//                    }
//                    if (shanks > 2) {
//                        //if barred or mottled lighten by 1 shade
//                        if ((autosomalGenes[22] == 2 && autosomalGenes[23] == 2) || (sexlinkedGenes[6] == 2 || (!isFemale && sexlinkedGenes[7] == 2))) {
//                            shanks--;
//                        }
//                    }
//                    if (shanks > 2) {
//                        //if lavender lighten by 1 shade
//                        if ((autosomalGenes[36] == 1 && autosomalGenes[37] == 1)) {
//                            shanks--;
//                        }
//                    }
//
//                    // if Dilute is Dilute and the shanks arnt darkened by extened black lighten by 1 shade
//                    if ((autosomalGenes[24] != 5 && autosomalGenes[25] != 5) && (autosomalGenes[32] == 1 || autosomalGenes[33] == 1)) {
//                        shanks--;
//                    }
//
//                    // if dominant white lighten by 1 shade
//                    if (autosomalGenes[38] == 1 && autosomalGenes[39] == 1) {
//                        shanks--;
//                        if (comb != 2) {
//                            comb++;
//                        }
//                    }
//
//                    //if its charcoal
//                    if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
//                        shanks++;
//                        if (comb != 3)
//                            comb--;
//                    }
//
//                    //lightens comb to mulberry if lightness is extreme enough
//                    if (shanks < 4 && comb != 2) {
//                        comb = comb + 1;
//                    }
//
//                    if (autosomalGenes[166] == 2 && autosomalGenes[167] == 2) {
//                        //ressesive dark shanks
//                        shanks = shanks + 2;
//                    }
//
//                    //makes sure its not off the chart
//                    if (shanks < 0) {
//                        shanks = 0;
//                    } else if (shanks > 5) {
//                        shanks = 5;
//                    }
//
//
//
//                    if (comb > 5) {
//                        comb = 5;
//                    } else if (comb < 0) {
//                        comb = 0;
//                    }
//
//                    //makes the shanks and beak their white or yellow varient
//                    if (autosomalGenes[44] == 3 && autosomalGenes[45] == 3) {
//                        shanks = shanks + 11;
//                    } else if (autosomalGenes[44] != 1 && autosomalGenes[45] != 1) {
//                        shanks = shanks + 5;
//                    }
//
//                }
//
//                //face and ear size stuff
//                if (isFemale) {
//                    if (sexlinkedGenes[4] >= 2) {
//                        ears = 1;
//                    } else {
//                        ears = -1;
//                    }
//                } else {
//                    if (sexlinkedGenes[4] >= 2 && sexlinkedGenes[5] >= 2) {
//                        ears = 1;
//                    } else {
//                        ears = -1;
//                    }
//                }
//
//                if (autosomalGenes[162] == 163){
//                    if (autosomalGenes[162] >= 9 && autosomalGenes[162] <=16) {
//                        ears = ears +1;
//                    } else if (autosomalGenes[162] >= 17 && autosomalGenes[162] <=24) {
//                        ears = ears + 2;
//                    }
//                    if ((autosomalGenes[162] & 1) == 0) {
//                        earsW = earsW + 2;
//                    }
//                } else {
////                    ears = ears - 1;
//                    if ((autosomalGenes[162] & 1) == 0) {
//                        if ((autosomalGenes[163] & 1) == 0) {
//                            earsW = earsW + 1;
//                        }
//                    } else if ((autosomalGenes[163] & 1) != 0) {
//                        earsW = earsW - 1;
//                    }
//                }
//
//                if (autosomalGenes[152] <= 4 || autosomalGenes[153] <= 4) {
////                    ears = ears - 1;
//                } else {
//                    if (autosomalGenes[152] == autosomalGenes[153]) {
//                        if (autosomalGenes[152] >= 9 || autosomalGenes[153] >= 9) {
//                            ears = ears + 1;
//                        }
//                    } else {
////                        ears = ears - 1;
//                    }
//
//                    if ((autosomalGenes[152] & 1) == 0) {
//                        earsW = earsW + 1;
//                    }
//                }
//
//                if (autosomalGenes[160] == autosomalGenes[161]) {
//                    if (autosomalGenes[160] >= 9) {
//                        if (autosomalGenes[160] >= 17) {
//                            ears = ears + 2;
//                        } else {
//                            ears = ears + 1;
//                        }
//                        earsW = earsW + 1;
//                    }
//                }
//
//                if (((autosomalGenes[160] & 1) == 0) && ((autosomalGenes[161] & 1) == 0)) {
//                    if (autosomalGenes[160] == autosomalGenes[161]) {
//                        earsW = earsW + 2;
//                    } else {
//                        earsW = earsW + 1;
//                    }
//                }
//
//                if (autosomalGenes[82] == 1) {
//                    ears = ears + 1;
//                }
//                if (autosomalGenes[83] == 1) {
//                    ears = ears + 1;
//                }
//
//                if (autosomalGenes[48] == 1) {
//                    ears = ears - 3;
//                }
//                if (autosomalGenes[49] == 1) {
//                    ears = ears - 3;
//                }
//
//                if (autosomalGenes[56] == 1 || autosomalGenes[57] == 1) {
//                    ears = ears - 2;
//                }
//
//                if (autosomalGenes[80] == 1 || autosomalGenes[81] == 1) {
//                    ears = ears - 1;
//                }
//
//                if (autosomalGenes[158] == 1 || autosomalGenes[159] == 1) {
////                    ears = ears - 1;
//                    earsW = earsW - 1;
//                } else if (autosomalGenes[158] == 2 || autosomalGenes[159] == 2) {
////                    ears = ears - 1;
//                } else if (autosomalGenes[158] == 3 || autosomalGenes[159] == 3) {
//                    if (autosomalGenes[158] == 4 || autosomalGenes[159] == 4) {
//                        ears = ears + 1;
//                        earsW = earsW + 1;
//                    } else {
//                        ears = ears + 1;
//                    }
//                } else if (autosomalGenes[158] == 4 || autosomalGenes[159] == 4) {
//                    earsW = earsW + 1;
//                } else {
//                    ears = ears + 1;
//                    earsW = earsW + 1;
//                }
//
//
//                //at max adds 2 if less than 8
//                if (autosomalGenes[154] <= 3 || autosomalGenes[155] <= 3) {
//                    ears = ears - 1;
//                } else if (autosomalGenes[154] == autosomalGenes[155]) {
//                    if (autosomalGenes[154] >= 7) {
//                        if (autosomalGenes[154] >= 10) {
//                            if (ears <= 7) {
//                                ears = ears + 2;
//                            } else {
//                                ears = ears + 1;
//                            }
//                        } else {
//                            ears = ears + 1;
//                        }
//                    }
//                }
//
//
//
//                //at max adds 2 if less than 8
//                if (!(autosomalGenes[156] <= 3 || autosomalGenes[157] <= 3) && (autosomalGenes[156] == autosomalGenes[157])) {
//                    if (autosomalGenes[156] >= 6) {
//                        if (autosomalGenes[156] >= 10) {
//                            if (ears <= 7) {
//                                ears = ears + 2;
//                            } else {
//                                ears = ears + 1;
//                            }
//                        } else {
//                            ears = ears + 1;
//                        }
//                    }
//                }
//
//                if (autosomalGenes[84] == 1 || autosomalGenes[85] == 1) {
//                    ears = ears/2;
//                }
//
//
//                if ((autosomalGenes[154] & 1) == 0 && (autosomalGenes[155]) == 0) {
//                    earsW = earsW + 1;
//                } else if ((autosomalGenes[154] & 1) != 0 && (autosomalGenes[155]) != 0) {
//                    earsW = earsW - 1;
//                }
//
//                if (autosomalGenes[164] == 1 || autosomalGenes[165] == 1) {
//                    earsW = earsW - 3;
//                } else if (autosomalGenes[164] == 2 || autosomalGenes[165] == 2) {
//                    if (autosomalGenes[164] >= 4 || autosomalGenes[165] >= 4) {
//                        earsW = earsW - 1;
//                    }
//                } else if (autosomalGenes[164] == 4 || autosomalGenes[165] == 4) {
//                    if (autosomalGenes[164] >= 5 || autosomalGenes[165] >= 5) {
//                        earsW = earsW + 1;
//                    }
//                } else if (autosomalGenes[164] == autosomalGenes[165]) {
//                    earsW = earsW + 2;
//                }
//
//                if (sexlinkedGenes[18] == 2 || (!isFemale && sexlinkedGenes[19] == 2)) {
//                    earsW = earsW/2;
//                }
//
//                if (isFemale) {
//                    if (sexlinkedGenes[12] == 5) {
//                        if (earsW <= 3) {
//                            earsW = earsW + 1;
//                        } else if (earsW >= 5) {
//                            earsW = earsW - 1;
//                        }
//                    } else if (sexlinkedGenes[12] == 6) {
//                        earsW = earsW + 1;
//                    }
//                } else {
//                    if (sexlinkedGenes[12] >= 5 && sexlinkedGenes[13] >= 5) {
//                        earsW = earsW + 1;
//                    } else if (sexlinkedGenes[12] >= 4 && sexlinkedGenes[13] >= 4) {
//                        if (earsW <= 3) {
//                            earsW = earsW + 1;
//                        } else if (earsW >= 5) {
//                            earsW = earsW - 1;
//                        }
//                    }
//                }
//
//                // this sets ear whiteness to actual value
//                if (earsW <= 2) {
//                    earsW = 0;
//                    //red
//                } else if (earsW >= 6) {
//                    earsW = 2;
//                    //white
//                } else {
//                    earsW = 1;
//                    //mottled
//                }
//
//                //ear size fixer
//                if (ears < 0) {
//                    ears = 0;
//                } else if (ears > 9) {
//                    ears = 9;
//                }
//
//                //ear colour calculation
//                if (ears != 0) {
//                    if (comb == 0) {
//                        //face is fibro
//                        if (ears >= 3) {
//                            face = 7;
//                        }
//                        if (earsW == 2) {
//                            //blue
//                            if ((autosomalGenes[44] != 1 && autosomalGenes[45] != 1) && (autosomalGenes[44] == 3 || autosomalGenes[45] == 3)) {
//                                //green
//                                ears = ears + 110;
//                            } else {
//                                //blue
//                                ears = ears + 80;
//                            }
//                        } else if (earsW == 1) {
//                            //mottled black
//                            ears = ears + 50;
//                        } else {
//                            //black
//                            ears = ears + 20;
//                        }
//                    } else if (comb == 3) {
//                        //face is moorish
//                        face = 7;
//                        if (earsW == 2) {
//                            if ((autosomalGenes[44] != 1 && autosomalGenes[45] != 1) && (autosomalGenes[44] == 3 || autosomalGenes[45] == 3)) {
//                                //yellow
//                                ears = ears + 90;
//                            } else {
//                                //white ears
//                                ears = ears + 60;
//                            }
//                        } else if (earsW == 1) {
//                            //mottled moorish ears (black and white)
//                            ears = ears + 120;
//                        } else {
//                            //black
//                            ears = ears + 20;
//                        }
//                    } else if (comb == 1 || comb == 4) {
//                        //face is mulberry based
//                        face = 4;
//                        if (earsW == 2) {
//                            //ear is white
//                            if (((isFemale && sexlinkedGenes[8] == 1) || (!isFemale && (sexlinkedGenes[8] == 1 || sexlinkedGenes[9] == 1))) && (autosomalGenes[42] == 1 || autosomalGenes[43] == 1)) {
//                                if ((autosomalGenes[44] != 1 && autosomalGenes[45] != 1) && (autosomalGenes[44] == 3 || autosomalGenes[45] == 3)) {
//                                    //light green
//                                    ears = ears + 100;
//                                } else {
//                                    //ear is light blue
//                                    ears = ears + 70;
//                                }
//                            } else {
//                                if ((autosomalGenes[44] != 1 && autosomalGenes[45] != 1) && (autosomalGenes[44] == 3 || autosomalGenes[45] == 3)) {
//                                    //yellow
//                                    ears = ears + 90;
//                                } else {
//                                    //white ears
//                                    ears = ears + 60;
//                                }
//                            }
//                        } else if (earsW == 1) {
//                            ears = ears + 40;
//                        } else {
//                            //ears mulberry
//                            ears = ears + 10;
//                        }
//                    } else {
//                        //face is red
//                        face = 1;
//                        if (earsW == 2) {
//                            if ((autosomalGenes[44] != 1 && autosomalGenes[45] != 1) && (autosomalGenes[44] == 3 || autosomalGenes[45] == 3)) {
//                                //yellow
//                                ears = ears + 90;
//                            } else {
//                                //white ears
//                                ears = ears + 60;
//                            }
//                        } else if (earsW == 1) {
//                            //mottled red ears
//                            ears = ears + 30;
//                        }
//                    }
//                }
//
//                if (autosomalGenes[0] == 2) {
//                    eyes = 2;
//                }
//
//                if (!(white == 3 || white == 5) || (autosomalGenes[106] == 2 && autosomalGenes[107] == 2)) {
//                    mottles = 0;
//                }
//
//                //after finished autosomalGenes
////                addTextureToAnimal("new_ground.png");
////                addTextureToAnimal("new_lace.png");
////                addTextureToAnimal("new_mottles.png");
//                addTextureToAnimal(CHICKEN_TEXTURES_GROUND, ground, null);
//                addTextureToAnimal(CHICKEN_TEXTURES_PATTERN, pattern, p -> p <= 350);
//                addTextureToAnimal(CHICKEN_TEXTURES_MOORHEAD, moorhead, m -> m != 0);
//                addTextureToAnimal(CHICKEN_TEXTURES_MOTTLEMARKINGS, mottles, m -> m != 0);
//                addTextureToAnimal(CHICKEN_TEXTURES_WHITE, white, w -> w != 0);
//
//
//
//            }else{
//                int shanks = 4;
//                int comb = 2;
//                int eyes = 1;
//                int downBase = 1;
//                int red = 0;
//                int black = 0;
//                int white = 0;
//
//
//                boolean isAlbino = false;
//
//                if (autosomalGenes[20] != 1 && autosomalGenes[21] != 1) {
//                    downBase = 0;
//                    if (autosomalGenes[20] == 3 && autosomalGenes[21] == 3) {
//                        //albino
//                        shanks = 6;
//                        comb = 2;
//                        eyes = 0;
//                        isAlbino = true;
//                    }
//                }
//
//                // figures out the shank, comb, and skin colour if its not albino
//                if (!isAlbino) {
//                    //gets comb colour
//                    if (autosomalGenes[24] == 1 && autosomalGenes[25] == 1) {
//                        shanks = 3;
//                        // makes mulbery comb
//                        if (autosomalGenes[30] == 2) {
//                            comb = 1;
//                        }
//                    }
//                    if (((isFemale && sexlinkedGenes[8] == 1) || (!isFemale && (sexlinkedGenes[8] == 1 || sexlinkedGenes[9] == 1))) && (autosomalGenes[42] == 1 || autosomalGenes[43] == 1)) {
//                        //comb and shanks are black
//                        comb = 0;
//                        shanks = 3;
//                    }
//                    //shanks starts at 3 btw
//                    // if Dilute is Dilute and the shanks arnt darkened by extened black lighten by 1 shade
//                    if ((autosomalGenes[24] != 1 && autosomalGenes[25] != 1) && (autosomalGenes[32] == 1 || autosomalGenes[33] == 1)) {
//                        shanks--;
//                    }
//
//                    //if barred or mottled lighten by 1 shade
//                    if ((autosomalGenes[22] == 2 && autosomalGenes[23] == 2) || (sexlinkedGenes[6] == 2 || (!isFemale && sexlinkedGenes[7] == 2))) {
//                        shanks--;
//                    }
//
//                    // if dominant white or lavender lighten by 1 shade
//                    if ((autosomalGenes[38] == 1 && autosomalGenes[39] == 1) || (autosomalGenes[36] == 1 && autosomalGenes[37] == 1)) {
//                        shanks--;
//                    }
//
//                    // if splash or blue lighten by 1 shade
//                    if (autosomalGenes[40] == 2 || autosomalGenes[41] == 2) {
//                        shanks--;
//                    }
//
//                    //if its melanized
////                    if (Melanin == 2) {
////                        shanks++;
////                    }
//
//                    if (autosomalGenes[166] == 2 && autosomalGenes[167] == 2) {
//                        shanks++;
//                    }
//
//                    //makes sure its not off the chart
//                    if (shanks < 0) {
//                        shanks = 0;
//                    } else if (shanks > 3) {
//                        shanks = 3;
//                    }
//
//                    //lightens comb to mulberry if lightness is extreme enough
//                    if (shanks < 2 && comb == 0) {
//                        comb = 1;
//                    }
//
//                    //makes the shanks and beak their white or yellow varient
//                    if (autosomalGenes[44] != 1 && autosomalGenes[45] != 1) {
//                        shanks = shanks + 5;
//                    }
//
//                    if (downBase != 0) {
//
//                        if (sexlinkedGenes[0] == 2 && (isFemale || (!isFemale && sexlinkedGenes[1] == 2))) {
//                            downBase = 0;
//                        }
//
//                        if (autosomalGenes[24] == 5 || autosomalGenes[25] == 5) {
//                            // is black
//                            if ((autosomalGenes[30] == 1 || autosomalGenes[31] == 1) && (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) && (autosomalGenes[28] != 1 && autosomalGenes[28] != 1)) {
//                                black = 13;
//                            } else {
//                                black = 7;
//                            }
//
//                        } else if (autosomalGenes[24] == 1 || autosomalGenes[25] == 1) {
//                            //is birchen
//                            if (((autosomalGenes[30] == 1 || autosomalGenes[31] == 1) || (autosomalGenes[100] == 2 && autosomalGenes[101] == 2)) && (autosomalGenes[28] != 1 && autosomalGenes[28] != 1)) {
//                                black = 13;
//                            } else {
//                                black = 7;
//                            }
//                        } else if (autosomalGenes[24] == 2 || autosomalGenes[25] == 2) {
//                            //is duckwing
//                            red = 3;
//                            black = 1;
//                        } else if (autosomalGenes[24] == 3 || autosomalGenes[25] == 3) {
//                            //is wheaten
//                            if (autosomalGenes[34] == 1 && autosomalGenes[35] == 1) {
//                                red = 1;
//                            } else if (autosomalGenes[34] == 1 || autosomalGenes[35] == 1) {
//                                red = 2;
//                            }
//                        } else {
//                            //is partridge
//                            red = 2;
//                            black = 1;
//                        }
//
//                        //white marking genes
//                        if (sexlinkedGenes[6] == 2 || (!isFemale && sexlinkedGenes[7] == 2)) {
//                            //Barred
//                            white = 2;
//                            if (!isFemale && sexlinkedGenes[6] == 2 && sexlinkedGenes[7] == 2) {
//                                white = 2;
//                            }
//                        } else {
//                            if (autosomalGenes[22] == 2 && autosomalGenes[23] == 2) {
//                                //mottled
//                                white = 1;
//                            }
//                        }
//
//                        if (black != 0) {
//                            if ((autosomalGenes[38] == 1 || autosomalGenes[39] == 1) || (autosomalGenes[40] == 2 && autosomalGenes[41] == 2)) {
//                                //white or splash
//                                black = black + 1;
//                            } else if (autosomalGenes[36] == 2 && autosomalGenes[37] == 2) {
//                                if (sexlinkedGenes[2] == 2 && (isFemale || (!isFemale && sexlinkedGenes[3] == 2))) {
//                                    //dun
//                                    black = black + 5;
//                                } else {
//                                    //lav
//                                    black = black + 2;
//                                }
//                            } else if (autosomalGenes[40] == 2 || autosomalGenes[41] == 2) {
//                                if (sexlinkedGenes[2] == 2 && (isFemale || (!isFemale && sexlinkedGenes[3] == 2))) {
//                                    //dun
//                                    black = black + 5;
//                                } else {
//                                    //blue
//                                    black = black + 3;
//                                }
//                            } else if (sexlinkedGenes[2] == 2 && (isFemale || (!isFemale && sexlinkedGenes[3] == 2))) {
//                                //choc
//                                black = black + 4;
//                            }
//
//                        }
//                    }
//                }
//
//                if (autosomalGenes[0] == 2) {
//                    eyes = 2;
//                }
//
//                addTextureToAnimal(CHICKEN_TEXTURES_CHICKBASE, downBase, null);
//                addTextureToAnimal(CHICKEN_TEXTURES_CHICKRED, red, r -> r != 0);
//                addTextureToAnimal(CHICKEN_TEXTURES_CHICKBLACK, black, b -> b != 0);
//                addTextureToAnimal(CHICKEN_TEXTURES_CHICKWHITE, white, w -> w != 0);
//                addTextureToAnimal(CHICKEN_TEXTURES_SHANKS, shanks, null);
//                addTextureToAnimal(CHICKEN_TEXTURES_COMB, comb, null);
//                addTextureToAnimal(CHICKEN_TEXTURES_EYES, eyes, null);
//            }
//        }
//    }
}
