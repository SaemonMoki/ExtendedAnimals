package mokiyoki.enhancedanimals.renderer.textures;

import mokiyoki.enhancedanimals.config.GeneticAnimalsConfig;
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import mokiyoki.enhancedanimals.renderer.texture.TextureGrouping;
import mokiyoki.enhancedanimals.renderer.texture.TexturingType;
import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
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

    /*
     * Slot counts per section of the cache key.
     *
     * Every chicken must write the same number of fields regardless of its genes: the key is a
     * flat string whose only structure is the delimiter positions, so a section that writes
     * fewer fields shifts every later section left and lets two different genetics compile to
     * the same key. Each branch that skips a section reserves its slots instead.
     *
     * If you add a texture inside one of these sections, bump its constant and add the matching
     * reservation to the branch that skips it.
     */
    private static final int EAR_SHAPE_SLOTS      = 1;
    private static final int EAR_COLOUR_SLOTS     = 2;
    private static final int FEATHER_COVER_SLOTS  = 4;
    private static final int BASE_COLOUR_SLOTS    = 4;
    /** three uuid-driven splash spots plus the splash base. */
    private static final int SPLASH_SLOTS         = 4;
    private static final int PAINT_SPOT_SLOTS     = 5;
    /** the paint spots plus the feather base under them. */
    private static final int PAINT_SLOTS          = PAINT_SPOT_SLOTS + 1;
    /** patterned blue, base melanin, iridescence, splash, paint. */
    private static final int PATTERN_RGB_SLOTS    = 3 + SPLASH_SLOTS + PAINT_SLOTS;

    /** mottles + pattern + charcoal, then the RGB pattern section. */
    private static final int PATTERN_BODY_SLOTS   = 3 + PATTERN_RGB_SLOTS;
    /** the above, plus the two mottle slots and barred. */
    private static final int PATTERN_COLOUR_SLOTS = PATTERN_BODY_SLOTS + 3;

    /** chick pattern texture, then the RGB pattern section. */
    private static final int CHICK_PATTERN_SLOTS  = 1 + PATTERN_RGB_SLOTS;
    /** the above, plus mottles and barred. */
    private static final int CHICK_FEATHER_SLOTS  = CHICK_PATTERN_SLOTS + 2;

    private static final int CHICK_BRANCH_SLOTS   = EAR_SHAPE_SLOTS + FEATHER_COVER_SLOTS + 2 + CHICK_FEATHER_SLOTS + 1;
    private static final int ADULT_FEATHER_SLOTS  = EAR_SHAPE_SLOTS + FEATHER_COVER_SLOTS + BASE_COLOUR_SLOTS + PATTERN_COLOUR_SLOTS + 2;

    public static void calculateChickenTextures(EnhancedChicken chicken, Genes genetics) {
            boolean isFemale = chicken.getOrSetIsFemale();
            int[] sGene = genetics.getSexlinkedGenes();
            int[] gene = genetics.getAutosomalGenes();

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
                chicken.layer(baseFeatherColour).texture("feather_colour/feather_base.png").add();
                chicken.layer(baseFeatherColour).texture("chick/half_solid.png").tinted(TexturingType.APPLY_RGB, calculateGroundRGB(sGene, gene, isFemale)).add();
                featherGroup.addGrouping(baseFeatherColour);

                if (gene[20] == 1 || gene[21] == 1) {
                    if (!pattern.isEmpty() || mottled) {
                        TextureGrouping patternFeatherGroup = new TextureGrouping(TexturingType.MASK_GROUP);
                        TextureGrouping patternCutOutGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                        chicken.layer(patternCutOutGroup).texture("chick/pattern/" + pattern + ".png").keyedAs("chick" + pattern).add();
                        patternFeatherGroup.addGrouping(patternCutOutGroup);
                        calculatePatternWithRGB(chicken, featherGroup, patternFeatherGroup, patternCutOutGroup, false, sGene, gene, isFemale, isNakedNeck);
                    } else {
                        chicken.addSkippedSlots(CHICK_PATTERN_SLOTS);
                    }
                    chicken.layer(featherGroup).texture("chick/mottles.png").onlyIf(mottled).orKey("1").add();
                    if (isFemale ? sGene[6] == 2 : sGene[6] == 2 || sGene[7] == 2) {
                        chicken.layer(featherGroup).texture(isFemale||sGene[6]!=sGene[7]?"chick/barred.png":"chick/doublebarred.png").keyedAs(isFemale||sGene[6]!=sGene[7] ? "s" : "d").add();
                    } else {
                        chicken.addSkippedSlot();
                    }
                } else {
                    chicken.addSkippedSlots(CHICK_FEATHER_SLOTS);
                }

                chicken.layer(featherGroup).texture("feather_colour/feather_noise.png").add();
                parentGroup.addGrouping(featherGroup);

                //the chick branch has no base feather colour and a shorter pattern section than
                //the adult branch, so it reserves the difference to keep both at ADULT_FEATHER_SLOTS
                chicken.addSkippedSlots(ADULT_FEATHER_SLOTS - CHICK_BRANCH_SLOTS);
            } else {
                boolean femFeathers = isFemale || (gene[196] == 2 || gene[197] == 2); //TODO roosters can be het for henny feather and express an intermediate form.
                String tailType;
                String tailSickle = "";
                boolean patternedBlue = false;

                if (gene[20] == 1 || gene[21] == 1) {
                    int patternGene = 4 - (gene[26] + gene[27]);
                    int columbian = 4 - (gene[28] + gene[29]);
                    int darkbrown = 4 - (gene[98] + gene[99]);
                    int melanized = 4 - (gene[30] + gene[31]);
                    int extension = Math.max(gene[24], gene[25]) == 5 ? 5 : Math.min(gene[24], gene[25]);
                    patternedBlue = gene[40] != gene[41] && (gene[24]==5||gene[25]==5) && ((gene[30]==1 || gene[31]==1) && (gene[26]==1 || gene[27]==1));

                    switch (extension) {
                        case 1 -> {
                            pattern = "birchen";
                            ground = femFeathers? "duckwing_female" : "duckwing_male";
                        }
                        case 2 -> {
                            pattern = "duckwing";
                            ground = femFeathers? "duckwing_female" : "duckwing_male";
                        }
                        case 3 -> {
                            pattern = "wheaten";
                            ground = femFeathers? "wheaten_female" : "duckwing_male";
                        }
                        case 4 -> {
                            pattern = "brown";
                            ground = femFeathers? "duckwing_female" : "duckwing_male";
                        }
                        default -> {
                            if (patternedBlue) {
                                pattern = "birchen";
                            } else {
                                pattern = "black";
                            }
                            ground = femFeathers? "duckwing_female" : "duckwing_male";
                        }
                    }

                    switch (columbian) {
                        case 1 -> pattern += "/hetcolumbian";
                        case 2 -> pattern +=    "/columbian";
                        default -> pattern += "/noncolumbian";
                    }
                    switch (darkbrown) {
                        case 1 -> pattern += "/hetdarkbrown";
                        case 2 -> pattern +=    "/darkbrown";
                        default -> pattern += "/nondarkbrown";
                    }

                    if (gene[170]==1 || gene[171]==1) {
                        autosomalRed = pattern.startsWith("brown") ? pattern.replace("brown/", "birchen/") : pattern;
                        autosomalRed = autosomalRed + (femFeathers ? "/female" : "/male");
                    }

                    switch (patternGene) {
                        case 1 -> pattern += "/hetpattern";
                        case 2 -> pattern +=    "/pattern";
                        default -> pattern += "/nonpattern";
                    }
                    switch (melanized) {
                        case 1 -> pattern += "/hetmelanized";
                        case 2 -> pattern +=    "/melanized";
                        default -> pattern += "/nonmelanized";
                    }

                    pattern += femFeathers ? "/female" : "/male";

                    if (gene[24]!=gene[25]) {
                        int e = gene[24] == extension ? gene[25]:gene[24];
                        switch (e) {
                            case 1 -> pattern = "birchenhet" + pattern;
                            case 2 -> pattern = "duckwinghet" + pattern;
                            case 3 -> pattern = "wheatenhet" + pattern;
                            case 4 -> pattern = "brownhet" + pattern;
                            default -> {
                                if ((gene[40] == 2) == (gene[41] == 2)) {
                                    pattern = "blackhet" + pattern;
                                }
                            }
                        }
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
                    earSize *= sGene[18] == 2 && (isFemale || sGene[19] == 2) ? 0.75F : 0.5F;
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
                setPatternColour(chicken, isFemale, sGene, gene, isNakedNeck, patternedBlue, pattern, mottled, charcoal, femFeathers, featherGroup);
                chicken.layer(featherGroup).texture("feather_colour/rooster_fluff.png").onlyIf(!isFemale).orKey("1").add();
                chicken.layer(featherGroup).texture("feather_colour/feather_noise.png").add();
                parentGroup.addGrouping(featherGroup);
            }

            TextureGrouping detailGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
            setSkinColour(chicken, isFemale, sGene, gene, detailGroup, chicken.growthAmount());
            setEarColour(chicken, isFemale, sGene, gene, earColour, detailGroup);
            chicken.layer(detailGroup).texture("eyes.png").tinted(TexturingType.APPLY_RGB, calculateEyeRGB(sGene, gene, isFemale, chicken.growthAmount() < 0.25F)).add();
//            chicken.addIndividualTextureToAnimalTextureGrouping(detailGroup, TexturingType.MERGE_GROUP, "eye_highlight.png");
            parentGroup.addGrouping(detailGroup);

            chicken.setTextureGrouping(parentGroup);
    }

    private static void setSkinColour(EnhancedChicken chicken, boolean isFemale, int[] sGene, int[] gene, TextureGrouping detailGroup, float age) {
        int[] skinColour = calculateSkinRGB(sGene, gene, isFemale);
        chicken.layer(detailGroup).texture("skin/" + (isFemale ? "female" : "male") + ".png").tinted(TexturingType.APPLY_RGB, skinColour[0]).keyedAs(isFemale ? "f" : "m").add();
        chicken.layer(detailGroup).texture("shanks.png").tinted(TexturingType.APPLY_RGB, 255 << 24 | calculateShankRGB(sGene, gene, isFemale)).add();
        chicken.layer(detailGroup).texture("soles.png").tinted(TexturingType.APPLY_RGB, 255 << 24 | calculateShanksRGBUnderColour(sGene, gene, isFemale)).add();
        chicken.layer(detailGroup).texture("skin/comb_" + (isFemale ? "female" : "male") + ".png").tinted(TexturingType.APPLY_RGB, calculateCombRGB(sGene, gene, isFemale)).keyedAs(isFemale ? "f" : "m").add();
        if (age < 0.25F) {
            chicken.layer(detailGroup).texture("skin/baby.png").tinted(TexturingType.APPLY_RGB, skinColour[0]).keyedAs("b").add();
        } else {
            chicken.addSkippedSlot();
        }
//        chicken.addTextureToAnimalTextureGrouping(detailGroup, TexturingType.APPLY_RGB, "skin/top.png", "t", skinColour[1]);
    }

    private static void setEarColour(EnhancedChicken chicken, boolean isFemale, int[] sGene, int[] gene, int earColour, TextureGrouping detailGroup) {
        if (earColour > 0) {
            String ear = "ear";
            if (earColour < 7) ear += "_mottled" + earColour;
            earColour = calculateEarRGB(sGene, gene, isFemale);
            chicken.layer(detailGroup).texture("skin/" + ear + ".png").tinted(TexturingType.APPLY_RGB, earColour).keyedAs(ear).add();

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
                if (face >= 7) { //TODO increment when more faces are added
                    face = 6;
                }
                chicken.layer(detailGroup).texture("skin/face" + face + ".png").tinted(TexturingType.APPLY_RGB, earColour).keyedAs(String.valueOf(face)).add();
            } else {
                chicken.addSkippedSlot();
            }
        } else {
            chicken.addSkippedSlots(EAR_COLOUR_SLOTS);
        }
    }

    private static void setPatternColour(EnhancedChicken chicken, boolean isFemale, int[] sGene, int[] gene, boolean isNakedNeck, boolean patternedBlue, String pattern, boolean mottled, boolean charcoal, boolean femFeathers, TextureGrouping featherGroup) {
        if (gene[20] == 1 || gene[21] == 1) {
            if (!pattern.isEmpty() || mottled || charcoal) {
                TextureGrouping patternFeatherGroup = new TextureGrouping(TexturingType.MASK_GROUP);
                TextureGrouping patternCutOutGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                chicken.layer(patternCutOutGroup).texture("pattern/mottles/mottles.png").onlyIf(mottled && (gene[22]==2 || gene[23]==2)).orKey("1").add();
                if (
                        pattern.contains("het") &&
                                !Minecraft.getInstance().getResourceManager().hasResource( new ResourceLocation("eanimod:textures/entity/chicken/pattern/"+pattern+".png"))
                ) {

                    TextureGrouping patternAverageGroup = new TextureGrouping(TexturingType.AVERAGE_GROUP);
                    String[] patterns = pattern.split("/");
                    List<String> pattern_locations = new ArrayList<>();

                    if (patterns[0].contains("het")) {
                        String[] p = patterns[0].split("het");
                        pattern_locations.add("pattern/"+p[0]);
                        pattern_locations.add("pattern/"+p[1]);
                    } else {
                        pattern_locations.add("pattern/"+patterns[0]);
                    }

                    //Co
                    if (patterns[1].contains("het")) {
                        int size = pattern_locations.size();
                        for (int i = 0; i < size; i++) {
                            String p = patterns[1].split("het")[1];
                            pattern_locations.add(pattern_locations.get(i) + "/non" + p);
                            pattern_locations.set(i, pattern_locations.get(i) + "/" + p);
                        }
                    } else {
                        pattern_locations.replaceAll(s -> s + "/" + patterns[1]);
                    }

                    //Db
                    if (patterns[2].contains("het")) {
                        int size = pattern_locations.size();
                        for (int i = 0; i < size; i++) {
                            String p = patterns[2].split("het")[1];
                            pattern_locations.add(pattern_locations.get(i) + "/non" + p);
                            pattern_locations.set(i, pattern_locations.get(i) + "/" + p);
                        }
                    } else {
                        pattern_locations.replaceAll(s -> s + "/" + patterns[2]);
                    }

                    //Ml
                    if (patterns[3].contains("het")) {
                        int size = pattern_locations.size();
                        for (int i = 0; i < size; i++) {
                            String p = patterns[3].split("het")[1];
                            pattern_locations.add(pattern_locations.get(i) + "/non" + p);
                            pattern_locations.set(i, pattern_locations.get(i) + "/" + p);
                        }
                    } else {
                        pattern_locations.replaceAll(s -> s + "/" + patterns[3]);
                    }

                    //Pg
                    if (patterns[4].contains("het")) {
                        int size = pattern_locations.size();
                        for (int i = 0; i < size; i++) {
                            String p = patterns[4].split("het")[1];
                            pattern_locations.add(pattern_locations.get(i) + "/non" + p);
                            pattern_locations.set(i, pattern_locations.get(i) + "/" + p);
                        }
                    } else {
                        pattern_locations.replaceAll(s -> s + "/" + patterns[4]);
                    }

                    //a het pattern expands into 2..32 locations, so the layers share one slot:
                    //one field per location would make the slot count depend on the genes
                    for (String loc : pattern_locations) {
                        chicken.layer(patternAverageGroup).texture(loc + "/" + patterns[5] + ".png").noKey().add();
                    }
                    chicken.addDelimiter(String.join("+", pattern_locations));

                    patternCutOutGroup.addGrouping(patternAverageGroup);
                } else {
                    chicken.layer(patternCutOutGroup).texture("pattern/" + pattern + ".png").keyedAs(pattern).add();
                }

                if (charcoal) {
                    String charcoalType = "pattern/";
                    charcoalType+= femFeathers ? "cha_female.png" : "cha_male.png";
                    chicken.layer(patternCutOutGroup).texture(charcoalType).keyedAs(charcoalType.substring(17,18)).add();
                } else {
                    chicken.addSkippedSlot();
                }
//                if (patternedBlue || (gene[40]!=gene[41] && (gene[100]==2&&gene[101]==2))) {
//                    TextureGrouping blueUnderlayGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
//                    chicken.addTextureToAnimalTextureGrouping(blueUnderlayGroup, "feather_colour/feather_base.png");
//                }
                patternFeatherGroup.addGrouping(patternCutOutGroup);
                calculatePatternWithRGB(chicken, featherGroup, patternFeatherGroup, patternCutOutGroup, patternedBlue, sGene, gene, isFemale, isNakedNeck);

            } else {
                chicken.addSkippedSlots(PATTERN_BODY_SLOTS);
            }
            //two independent traits: an unpatterned chicken must still reserve both slots,
            //and a chicken with only one of them must reserve the other
            if (mottled) {
                chicken.layer(featherGroup).texture("feather_colour/mottles/mottles.png").onlyIf(gene[22]==2 || gene[23]==2).add();
                chicken.layer(featherGroup).texture("feather_colour/mottles/whitehead.png").onlyIf(gene[22]==3 || gene[23]==3).add();
            } else {
                chicken.addSkippedSlots(2);
            }
            String barred = "";
            if (isFemale) {
                 if (sGene[6] == 2) barred = "barred";
            } else if (sGene[6] == 2 || sGene[7] == 2) {
                barred = sGene[6]==sGene[7]?"barred_double":"barred";
            }
            if (!barred.isEmpty() && (GeneticAnimalsConfig.COMMON.force16x.get() || gene[106]==2&&gene[107]==2)) {
                barred += "_silkie";
            }
            chicken.layer(featherGroup).texture("feather_colour/"+barred+".png").keyedAs(barred).add();
        } else {
            chicken.addSkippedSlots(PATTERN_COLOUR_SLOTS);
        }
    }

    private static void setBaseFeatherColour(EnhancedChicken chicken, boolean isFemale, boolean femfeathers, int[] sGene, int[] gene, String autosomalRed, String ground, TextureGrouping featherGroup) {
        TextureGrouping baseFeatherColour = new TextureGrouping(TexturingType.MERGE_GROUP);
        TextureGrouping groundGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        baseFeatherColour.addGrouping(groundGroup);
        if (ground.isEmpty()) {
            chicken.layer(groundGroup).texture("feather_colour/feather_base.png").tinted(TexturingType.APPLY_RGB, calculateGroundRGB(sGene, gene, isFemale)).add();
            chicken.addSkippedSlot();
        } else {
            chicken.layer(groundGroup).texture("feather_colour/feather_base.png").add();
            chicken.layer(groundGroup).texture("feather_colour/ground/" + ground + ".png").tinted(TexturingType.APPLY_RGB, calculateGroundRGB(sGene, gene, isFemale)).keyedAs(ground).add();
        }
        if (!autosomalRed.isEmpty() && (gene[20] == 1 || gene[21] == 1)) {
            if (gene[170] == 1 || gene[171] == 1) {
                TextureGrouping autosomalRedGroup = new TextureGrouping(TexturingType.APPLY_PHEOMELANIN);
                int red = calculateAutosomalRedRGB(sGene, gene, autosomalRed.split("/"), isFemale);
                    chicken.layer(autosomalRedGroup).texture("feather_colour/autosomal_red/" + autosomalRed + ".png").tinted(TexturingType.APPLY_RGBA, red).keyedAs(autosomalRed).add();
                    if (gene[34] == 1 || gene[35] == 1) {
                        chicken.layer(autosomalRedGroup).texture("feather_colour/autosomal_red/" + autosomalRed + ".png").tinted(TexturingType.APPLY_RGBA, red).keyedAs(autosomalRed).add();
                    } else {
                        chicken.addSkippedSlot();
                    }
                baseFeatherColour.addGrouping(autosomalRedGroup);
            } else {
                chicken.addSkippedSlots(2);
            }
        } else {
            chicken.addSkippedSlots(2);
        }
        featherGroup.addGrouping(baseFeatherColour);
    }
    private static void setFeatherCoverage(EnhancedChicken chicken, int[] gene, boolean isNakedNeck, int facefeathers, String tailType, String tailSickle, TextureGrouping featherGroup, boolean isFemale) {
        TextureGrouping featherMask = new TextureGrouping(TexturingType.MERGE_GROUP);
        if (isNakedNeck || facefeathers != -1) {
            featherMask.setTexturingType(TexturingType.CUTOUT_GROUP);
            TextureGrouping featherCutout = new TextureGrouping(TexturingType.MERGE_GROUP);
            if (isNakedNeck) {
                chicken.layer(featherCutout).texture("feather_type/" + (gene[52] == gene[53] ? "naked" : "bowtie") + "_neck.png").keyedAs(gene[52] == gene[53] ? "bt" : "nn").add();
            } else {
                chicken.addSkippedSlot();
            }
            if (facefeathers != -1) {
                chicken.layer(featherCutout).texture("feather_type/baldface_" + facefeathers + ".png").add();
            } else {
                chicken.addSkippedSlot();
            }
            featherMask.addGrouping(featherCutout);
        } else {
            chicken.addSkippedSlots(2);
        }
        chicken.layer(featherMask).texture("feather_type/feathers.png").add();

        if (!tailType.isEmpty()) {
            int tailLength = 1;
            if (gene[198]==2&&gene[199]==2) tailLength +=1;
            if (gene[280]==2&&gene[281]==2) tailLength +=1;
            if (gene[282]==2&&gene[283]==2) tailLength -=1;

            int tailNumber = gene[278]==1||gene[279]==1?5:(gene[278]==2||gene[279]==2?6:7);
            //tailNumber is gene driven, so the feathers share one slot rather than writing
            //one field each -- otherwise the slot count would vary between 6 and 8
            StringBuilder tailKey = new StringBuilder();
            for (int i = 0; i <= tailNumber; i++) {
                if (i == 0) {
                    /**
                     *      This one controls the sickle feather
                     */
                    chicken.layer(featherMask).texture("tail/"+tailLength+"/" + (isFemale ? "female" : "male") + "/" + i + ".png").noKey().add();
                    tailKey.append("0").append(tailLength).append(tailType).append(tailNumber);
                } else {
                    chicken.layer(featherMask).texture("tail/"+tailLength+"/" + (isFemale ? "female" : "male") + "/" + i + ".png").noKey().add();
                    tailKey.append(i + tailLength).append(tailType).append(tailNumber);
                }
            }
            chicken.addDelimiter(tailKey.toString());
        } else {
            chicken.addSkippedSlot();
        }
        featherGroup.addGrouping(featherMask);
    }

    private static void setEarShape(TextureGrouping parentGroup, EnhancedChicken chicken, int[] gene, int earSize) {
        if (earSize >= 2) {
            parentGroup.setTexturingType(TexturingType.CUTOUT_GROUP);
            String earTexture = "";
            if (earSize > 15) earSize = 15;
            switch (earSize) {
                case 2, 3, 4 -> earTexture = "tiny.png";
                case 5, 6 -> earTexture = "small.png";
                case 7, 8, 9 -> earTexture = "medium.png";
                case 10, 11, 12 -> earTexture = "large.png";
                case 13, 14, 15 -> earTexture = "xlarge.png";
            }

            String earKey = String.valueOf(earTexture.charAt(0));

            earSize = 0;
            for (int i = 152; i < 163; i++) {
                if (i < 158 || i > 159) earSize += gene[i] % 2 == 0 ? 1 : -1;
            }

            if (earSize>0) {
                earTexture = "round_" + earTexture;
                earKey += "r";
            } else {
                earTexture = "long_" + earTexture;
                earKey += "l";
            }

            chicken.layer(parentGroup).texture("ear/" + earTexture).keyedAs(earKey).add();
        } else {
            chicken.addSkippedSlot();
        }
    }

    private static Integer calculateEarRGB(int[] sGene, int[] gene, boolean isFemale) {
        float value = 1.0F;
        float saturation = 0.0F;
        float hue = -1.0F;

        if (gene[20] == 3 && gene[21] == 3) return 16777215;

        if ((sGene[8]==1 || (!isFemale && sGene[9]==1)) && (gene[42]==1 || gene[43]==1)) {
            value = gene[42]==gene[43]?0.7F:0.8F;
            saturation = gene[42]==gene[43]?0.7F:0.35F;
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

    private static int calculateEyeRGB(int[] sGene, int[] gene, boolean isFemale, boolean isBaby) {
        if (gene[20]==3&&gene[21]==3) {
            return 14560322;
        } else {
            float h = 34F;
            float s = 0.8F;
            float b = 0.15F;

            if (gene[170] == 1 || gene[171] == 1) {
                if (gene[34]==1 || gene[35]==1) {
                    h *= gene[170]==gene[171] ? 0.7F : 0.8F;
                } else {
                    h *= gene[170]==gene[171] ? 0.85F : 0.95F;
                }
            }

            if (gene[24] == 5 || gene[25] == 5) {
                h *= 0.8F;
                s += (1.0F - s) * 0.5F;
                b *= gene[24] == gene[25] ? 0.25F : 0.3F;
            } else if (gene[24] == 1 || gene[25] == 1) {
                h *= 0.8F;
                s += (1.0F - s) * 0.5F;
                b *= gene[24] == gene[25] ? 0.3F : 0.4F;
            }

            if (gene[30] == 1 || gene[31] == 1) {
                //melanized
                h *= 0.8F;
                s += (1.0F - s) * 0.5F;
                b *= 0.5F;
            }

            if (gene[42] == 1 || gene[43] == 1) {
                //fibromelanin
                h *= 0.8F;
                s += (1.0F - s) * 0.5F;
                b *= 0.3F;
            }

            if (gene[100] == 2 && gene[101] == 2) {
                //charcoal
                h *= 0.8F;
                s += (1.0F - s) * 0.5F;
                b *= 0.5F;
            }

            if (sGene[2] == 2 && (isFemale || sGene[3] == 2)) {
                //chocolate //2100488
                h += (40F-h) * 0.2F;
                s += 0.1F;
                b += 0.1F;
            }

            if (gene[38]==1 && gene[39]==1) {
                if (isBaby) {
                    h = 200 - h;
                    s *= 0.5F;
                    b += (1.0F-b)*0.75F;
                } else {
                    h += (60F-h) * 0.4F;
                    b += (1.0F-b) * 0.75F;
                }
            }

            if (gene[296]==2 && gene[297]==2) {
                h += (60F-h) * 0.5F;
                s *= 0.3F;
                b = 0.8F + ((1.0F-b)*0.1F);
            }

            return Colouration.HSBtoARGB(h/360F, Math.max(Math.min(s, 1.0F), 0.0F), Math.max(Math.min(b, 1.0F), 0.0F));
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
                s = 0.6F;
            }
        }

        if (gene[28]==1||gene[29]==1) {
            h *= 0.9F;
            s *= 0.95F;
            b *= 0.97F;
        }

        //mahogany
        if (gene[34]==1||gene[35]==1) {
            h *= gene[34]==gene[35]?0.75F:0.8F;
            s += (1.0F-s)*0.25F;
        }

        //autosomal red
        if (gene[170]==1||gene[171]==1) {
            h *= gene[170]==gene[171] ? 0.92F : 0.95F;
            if (s != 1.0F) {
                s += (1.0F-s) * (gene[170]==gene[171]?0.75F:0.5F);
            }
        } else if (!isFemale) {
            h *= 0.85F;
        }

        //Lavender
        if (gene[36]==2&&gene[37]==2) {
            s *= 0.5F;
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

        float a = gene[170] == gene[171] ? 1.0F : 0.75F;

        return Colouration.HSBAtoARGB(h, s, b, a);
    }

    private static int calculateAutosomalRedRGB(int[] sGene, int[] gene, String[] autosomalRed, boolean isFemale) {
        float h;
        float s;
        float b;
        float a = gene[170] == gene[171] ? 1.0F : (isFemale?0.66F:0.85F);

        if (autosomalRed[1].equals("columbian")) {
            if (autosomalRed[2].equals("darkbrown")) {
                if (isFemale) { h = 0.066F; s = 0.77F; b = 0.65F; } else { h = 0.064F; s = 0.8F; b = 0.68F; }
            } else {
//                switch (autosomalRed[0]) {
//                    default -> {
//                        if (isFemale) { h = 0.05F; s = 0.53F; b = 0.41F; } else { h = 0.05F; s = 0.53F; b = 0.41F; }
//                    }
//                    case "birchen", "brown", "duckwing" -> {
//                        if (isFemale) { h = 0.05F; s = 0.7F; b = 0.63F; } else { h = 7F/360F; s = 0.88F; b = 0.5F; }
//                    }
//                    case "wheaten" -> {
                        if (isFemale) {
                            h = 0.05F; s = 0.75F; b = 0.6F; a=gene[170]==gene[171]?1.0F:0.8F;
                        } else {
                            s = 0.88F; b = 0.5F;
                            if (sGene[0]==2 || sGene[1]==2) {
                                if (sGene[0]==sGene[1]) {
                                    h = 22F/360F;
                                    a*=0.9F;
                                } else {
                                    h = 16F/360F;
                                    a*=0.92F;
                                }
//                                 s = 0.8F; b = 0.6F;
                            } else {
                                h = 9F/360F;
                            }
                        }
//                    }
//                }
            }
        } else {
            if (autosomalRed[2].equals("darkbrown")) {
                if (isFemale) { h = 0.066F; s = 0.77F; b = 0.65F; } else { h = 0.064F; s = 0.8F; b = 0.68F; }
//                switch (autosomalRed[0]) {
//                    default -> {
//                        if (isFemale) { h = 0.05F; s = 0.53F; b = 0.41F; } else { h = 0.05F; s = 0.53F; b = 0.41F; }
//                    }
//                    case "birchen" -> {
//                        if (isFemale) { h = 0.05F; s = 0.53F; b = 0.41F; } else { h = 0.05F; s = 0.53F; b = 0.41F; }
//                    }
//                    case "brown" -> {
//                        if (isFemale) { h = 0.05F; s = 0.53F; b = 0.41F; } else { h = 0.05F; s = 0.53F; b = 0.41F; }
//                    }
//                    case "duckwing" -> {
//                        if (isFemale) { h = 0.05F; s = 0.53F; b = 0.41F; } else { h = 0.05F; s = 0.53F; b = 0.41F; }
//                    }
//                    case "wheaten" -> {
//                        if (isFemale) { h = 0.05F; s = 0.53F; b = 0.41F; } else { h = 0.05F; s = 0.53F; b = 0.41F; }
//                    }
//                }
            } else {
                switch (autosomalRed[0]) {
                    case "birchen", "brown", "duckwing" -> {
                        if (isFemale) { h = 0.05F; s = 0.7F; b = 0.63F; } else { h = 7F/360F; s = 0.88F; b = 0.5F; }
                    }
                    case "wheaten" -> {
                        if (isFemale) { h = 0.05F; s = 0.75F; b = 0.6F; a=gene[170]==gene[171]?1.0F:0.8F; } else { h = 7F/360F; s = 0.88F; b = 0.5F; }
                    }
                    default -> {
                        if (isFemale) { h = 0.05F; s = 0.53F; b = 0.41F; } else { h = 0.05F; s = 0.53F; b = 0.41F; }
                    }
                }
            }
        }

        if (gene[34]==1 || gene[35]==1) {
            if (autosomalRed[0].equals("wheaten")) {
                h*=0.4F;
                s*=0.8F;
            } else {
                h*=0.5F;
                b*=0.6F;
            }
        }

        if (gene[40]==2||gene[41]==2) {
            a*=gene[40]==gene[41]?0.95F:0.9F;
        }

        if (gene[36]==2&&gene[37]==2) {
            a*=0.5F;
        }

        return Colouration.HSBAtoARGB(h, s, b, a);
    }

//    private static int calculateAutosomalRedRGB(int[] sGene, int[] gene, boolean isFemale) {
//        float h = 0.0F;
//        float s = 1.0F;
//        float b = 1.0F;
//
//        if (isFemale) {
//            if (gene[170] == 1 || gene[171] == 1) {
////                if (gene[170]==gene[171]) {
//                    //homoautosomal red
//                    if (gene[34] == 1 || gene[35] == 1) {
//                        //mahogany
//                        if (sGene[0] == 1) {
//                            // 10503750
//                            s = 0.39F;
//                            b = 0.45F;
//                        } else {
//                            // 10774594
//                            h = 0.0638F;
//                            s = 0.43F;
//                            b = 0.45F;
//                        }
//                    } else {
//                        if (sGene[0] == 1) {
//                            // 10774594
//                            h = 0.0638F;
//                            s = 0.43F;
//                            b = 0.45F;
//                        } else {
//                            // 10051657
//                            h = 0.0472F;
//                            s = 0.35F;
//                            b = 0.44F;
//                        }
//                    }
//                /*} else {
//                    //hetautosomal red
//                    if (gene[34]==1 || gene[35]==1) {
//                        //mahogany
//                        colour = sGene[0] == 1 ? 10503750 : 10503750;
//                    } else {
//                        colour = sGene[0] == 1 ? 12426889 : 12425353;
//                    }
//                }*/
//            }
//        } else {
////            if (gene[170] == 1 || gene[171] == 1) {
////                if (gene[170]==gene[171]) {
////                    //gold
//            if (gene[34]==1 || gene[35]==1) {
//                // 5639947
//                h = 0.0083F;
//                s = 0.52F;
//                b = 0.60F;
//
//            } else {
//                // 7541259
//                h = 0.0111F;
//                s = 0.90F;
//                b = 0.45F;
//
//            }
////                    colour = gene[34]==1 || gene[35]==1 ? 5639947 : 7541259;
////                } else {
////                    //lemon
////                    colour = gene[34]==1 || gene[35]==1 ? 8658186 : 10769441;
////                }
////            }
//        }
//
//        float a = gene[170] == gene[171] ? 1.0F : 0.75F;
//
//        if (b!=1.0F) {
//            //Lavender
//            if (gene[36] == 2 && gene[37] == 2) {
//                a *= 0.33F;
////                s *= 0.5F;
////                b += (1.0F-b)*0.5F;
//
////                r += (int) ((255 - r) * 0.70F);
////                g += (int) ((255 - g) * 0.70F);
////                b += (int) ((255 - b) * 0.65F);
//            }
//        }
//
//        return Colouration.HSBAtoARGB(h, s, b, a);
//    }

    private static void calculatePatternWithRGB(EnhancedChicken chicken, TextureGrouping featherGroup, TextureGrouping patternFeatherGroup, TextureGrouping patternCutOutGroup, boolean patternedBlue, int[] sGene, int[] gene, boolean isFemale, boolean isNakedNeck) {
        boolean choc = sGene[2] == 2 && (isFemale || sGene[3] == 2);
        boolean lav = gene[36] == 2 && gene[37] == 2;
        boolean splash = gene[40]==2 && gene[41]==2;
        boolean blue = gene[40] != gene[41];
        boolean paint = gene[38] == 1 || gene[39] == 1;

        if (patternedBlue) {
            float[] blueBase = getPatternRGB(choc, lav, true, false, false, paint, gene, chicken.growthAmount());
            chicken.layer(featherGroup).texture("feather_colour/feather_base.png").tinted(TexturingType.APPLY_RGB, Colouration.HSBtoARGB(blueBase[0], blueBase[1], blueBase[2])).add();
        } else {
            chicken.addSkippedSlot();
        }

        float[] colours = getPatternRGB(choc, lav, blue, splash, patternedBlue, paint, gene, chicken.growthAmount());

        chicken.layer(patternFeatherGroup).texture("feather_colour/feather_base.png").tinted(TexturingType.APPLY_RGB, Colouration.HSBtoARGB(colours[0], colours[1], colours[2])).add();

        if (colours[3]!=0.0F && (gene[106]==1||gene[107]==1) && chicken.growthAmount()>0.35F) {
            TextureGrouping iriFeatherGroup = new TextureGrouping(TexturingType.MASK_GROUP);
            iriFeatherGroup.addGrouping(patternCutOutGroup);
            chicken.layer(iriFeatherGroup).texture("feather_colour/" + (isFemale ? "iridescence_female" : (isNakedNeck ? "nakedneck_iridescence_male" : "iridescence_male")) + ".png").tinted(TexturingType.APPLY_SHIFT, (int) ((1.0F - colours[3]) * 255) << 8 | (int) (colours[4] * 255)).keyedAs(isFemale ? "f" : (isNakedNeck ? "nm" : "m")).add();
            patternFeatherGroup.addGrouping(iriFeatherGroup);
        } else {
            chicken.addSkippedSlot();
        }

            if (splash) {
                char[] uuid = chicken.getStringUUID().toCharArray();
                TextureGrouping spots = new TextureGrouping(TexturingType.CUTOUT_GROUP);
                TextureGrouping spotsCutout = new TextureGrouping(TexturingType.MERGE_GROUP);
                int spotVal = Integer.parseInt(String.valueOf(uuid[1]),16);
                chicken.layer(spotsCutout).texture("feather_colour/spots/splash" + spotVal + ".png").keyedAs(String.valueOf(spotVal)).add();
                spotVal = Integer.parseInt(String.valueOf(uuid[2]),16);
                chicken.layer(spotsCutout).texture("feather_colour/spots/splash" + spotVal + ".png").keyedAs(String.valueOf(spotVal)).add();
                spotVal = Integer.parseInt(String.valueOf(uuid[3]),16);
                chicken.layer(spotsCutout).texture("feather_colour/spots/splash" + spotVal + ".png").keyedAs(String.valueOf(spotVal)).add();

                spots.addGrouping(spotsCutout);
                chicken.layer(spots).texture("feather_colour/spots/splash_base.png").add();
                patternFeatherGroup.addGrouping(spots);
            } else {
                chicken.addSkippedSlots(SPLASH_SLOTS);
            }

            if (paint) {
                TextureGrouping spots = new TextureGrouping(TexturingType.MERGE_GROUP);
                if (!(gene[38] == 1 && gene[39] == 1)) {
                    spots.setTexturingType(TexturingType.CUTOUT_GROUP);
                    TextureGrouping spotsCutout = new TextureGrouping(TexturingType.MERGE_GROUP);
                    for (int i=0; i<5;i++) {
                        int spotVal = ThreadLocalRandom.current().nextInt(9);
                        chicken.layer(spotsCutout).texture("feather_colour/spots/paint" + spotVal + ".png").keyedAs(String.valueOf(spotVal)).add();
                    }
                    spots.addGrouping(spotsCutout);
                } else {
                    chicken.addSkippedSlots(PAINT_SPOT_SLOTS);
                }
                chicken.layer(spots).texture("feather_colour/feather_base.png").add();
                patternFeatherGroup.addGrouping(spots);
            } else {
                chicken.addSkippedSlots(PAINT_SLOTS);
            }
        featherGroup.addGrouping(patternFeatherGroup);
    }

    private static float[] getPatternRGB(boolean choc, boolean lav, boolean blue, boolean splash, boolean patternedblue, boolean paint, int[] gene, float growthamount) {
        float patternHue = 0.07F;
        float patternSaturation = 0.05F;
        float patternValue = 0.075F;

        float iridescenceAlpha = 0.75F;
        float iridescenceHueShift = 0.0F;

        if (patternedblue) blue = false;

        if (choc) {
            if (lav) {
                if (blue || splash) {
                    switch (Math.max(gene[38], gene[39])) {
                        case 3 -> {
                            if (paint) {
                                // White
                                patternValue = 1.0F;
                                iridescenceAlpha = 0.0F;
                            } else {
                                if (gene[38]==gene[39]) {
                                    // Choc Lavender Khaki
                                    patternHue = 0.0416F;
                                    patternSaturation = 0.1F;
                                    patternValue = 0.92F;
                                    iridescenceAlpha = 0.05F;
                                    iridescenceHueShift = 0.05F;
                                } else {
                                    // Choc Lavender Dun
                                    patternHue = 0.0222F;
                                    patternSaturation = 0.12F;
                                    patternValue = 0.85F;
                                    iridescenceAlpha = 0.05F;
                                    iridescenceHueShift = 0.05F;
                                }
                            }
                        }
                        case 4 -> {
                            // Choc Lavender Smokey
                            if (gene[38]==gene[39] || paint || growthamount<0.5F) {
                                patternHue = 0.0222F;
                                patternSaturation = 0.0F;
                                patternValue = 0.9F;
                                iridescenceAlpha = 0.0F;
                            } else {
                                patternHue = 0.0222F;
                                patternSaturation = 0.09F;
                                patternValue = 0.85F;
                                iridescenceAlpha = 0.0F;
                            }
                        }
                        default -> {
                            // Choc Lavender Blue
                            patternHue = 0.0222F;
                            patternSaturation = 0.09F;
                            patternValue = 0.85F;
                            iridescenceAlpha = 0.05F;
                            iridescenceHueShift = 0.05F;
                        }
                    }
                } else {
                    // Choc Lavender
                    switch (Math.max(gene[38], gene[39])) {
                        case 3 -> {
                            if (paint) {
                                // White
                                patternValue = 1.0F;
                                iridescenceAlpha = 0.0F;
                            } else {
                                if (gene[38]==gene[39]) {
                                    // Choc Lavender Khaki
                                    patternHue = 0.0416F;
                                    patternSaturation = 0.1F;
                                    patternValue = 0.75F;
                                    iridescenceAlpha = 0.05F;
                                    iridescenceHueShift = 0.05F;
                                } else {
                                    // Choc Lavender Dun
                                    patternHue = 0.0222F;
                                    patternSaturation = 0.12F;
                                    patternValue = 0.62F;
                                    iridescenceAlpha = 0.05F;
                                    iridescenceHueShift = 0.05F;
                                }
                            }
                        }
                        case 4 -> {
                            // Choc Lavender Smokey
                            if (gene[38]==gene[39] || paint || growthamount<0.5F) {
                                patternHue = 0.0222F;
                                patternSaturation = 0.0F;
                                patternValue = 0.7F;
                                iridescenceAlpha = 0.0F;
                            } else {
                                patternHue = 0.0222F;
                                patternSaturation = 0.09F;
                                patternValue = 0.65F;
                                iridescenceAlpha = 0.0F;
                            }
                        }
                        default -> {
                            // Choc Lavender
                            patternHue = 0.0222F;
                            patternSaturation = 0.09F;
                            patternValue = 0.65F;
                            iridescenceAlpha = 0.05F;
                            iridescenceHueShift = 0.05F;
                        }
                    }
                }
            } else {
                if (blue || splash) {
                    switch (Math.max(gene[38], gene[39])) {
                        case 3 -> {
                            if (paint) {
                                // White
                                patternValue = 1.0F;
                                iridescenceAlpha = 0.0F;
                            } else {
                                if (gene[38]==gene[39]) {
                                    // Choc Blue Khaki
                                    patternHue = 0.0944F;
                                    patternSaturation = 0.22F;
                                    patternValue = 0.67F;
                                    iridescenceAlpha = 0.05F;
                                    iridescenceHueShift = 0.05F;
                                } else {
                                    // Choc Blue Dun
                                    patternHue = 0.0611F;
                                    patternSaturation = 0.39F;
                                    patternValue = 0.52F;
                                    iridescenceAlpha = 0.05F;
                                    iridescenceHueShift = 0.05F;
                                }
                            }
                        }
                        case 4 -> {
                            // Choc Blue Smokey
                            if (gene[38]==gene[39] || paint || growthamount<0.5F) {
                                patternHue = 0.0694F;
                                patternSaturation = 0.16F;
                                patternValue = 0.56F;
                                iridescenceAlpha = 0.0F;
                            } else {
                                patternHue = 0.0694F;
                                patternSaturation = 0.37F;
                                patternValue = 0.51F;
                                iridescenceAlpha = 0.0F;
                            }
                        }
                        default -> {
                            // Choc Blue
                            patternHue = 0.0694F;
                            patternSaturation = 0.37F;
                            patternValue = 0.51F;
                            iridescenceAlpha = 0.05F;
                            iridescenceHueShift = 0.05F;
                        }
                    }
                } else {
                    // Choc
                    switch (Math.max(gene[38], gene[39])) {
                        case 3 -> {
                            if (paint) {
                                // White
                                patternValue = 1.0F;
                                iridescenceAlpha = 0.0F;
                            } else {
                                if (gene[38]==gene[39]) {
                                    // Choc Khaki
                                    patternHue = 0.0833F;
                                    patternSaturation = 0.38F;
                                    patternValue = 0.65F;
                                    iridescenceAlpha = 0.05F;
                                    iridescenceHueShift = 0.05F;
                                } else {
                                    // Choc Dun
                                    patternHue = 0.0472F;
                                    patternSaturation = 0.56F;
                                    patternValue = 0.39F;
                                    iridescenceAlpha = 0.05F;
                                    iridescenceHueShift = 0.05F;
                                }
                            }
                        }
                        case 4 -> {
                            // Choc Smokey
                            if (gene[38]==gene[39] || paint || growthamount<0.5F) {
                                patternHue = 0.0472F;
                                patternSaturation = 0.23F;
                                patternValue = 0.44F;
                                iridescenceAlpha = 0.0F;
                            } else {
                                patternHue = 0.0472F;
                                patternSaturation = 0.56F;
                                patternValue = 0.28F;
                                iridescenceAlpha = 0.0F;
                            }
                        }
                        default -> {
                            // Choc
                            patternHue = 0.0472F;
                            patternSaturation = 0.56F;
                            patternValue = 0.28F;
                            iridescenceAlpha = 0.1F;
                            iridescenceHueShift = 0.05F;
                        }
                    }
                }
            }
        } else {
            if (lav) {
                if (blue || splash) {
                    switch (Math.max(gene[38], gene[39])) {
                        case 3 -> {
                            if (paint) {
                                // White
                                patternValue = 1.0F;
                                iridescenceAlpha = 0.0F;
                            } else {
                                if (gene[38]==gene[39]) {
                                    // Lavender Blue Khaki
                                    patternHue = 0.5F;
                                    patternSaturation = 0.01F;
                                    patternValue = 0.9F;
                                    iridescenceAlpha = 0.05F;
                                } else {
                                    // Lavender Blue Dun
                                    patternHue = 0.75F;
                                    patternSaturation = 0.065F;
                                    patternValue = 0.87F;
                                    iridescenceAlpha = 0.05F;
                                }
                            }
                        }
                        case 4 -> {
                            // Lavender Blue Smokey
                            if (gene[38]==gene[39] || paint || growthamount<0.5F) {
                                patternHue = 0.6666F;
                                patternSaturation = 0.01F;
                                patternValue = 0.9F;
                                iridescenceAlpha = 0.0F;
                            } else {
                                //het
                                patternHue = 0.6666F;
                                patternSaturation = 0.055F;
                                patternValue = 0.85F;
                                iridescenceAlpha = 0.0F;
                            }
                        }
                        default -> {
                            // Lavender Blue
                            patternHue = 0.6666F;
                            patternSaturation = 0.06F;
                            patternValue = 0.85F;
                            iridescenceAlpha = 0.05F;
                        }
                    }
                } else {
                    switch (Math.max(gene[38], gene[39])) {
                        case 3 -> {
                            if (paint) {
                                // White
                                patternValue = 1.0F;
                                iridescenceAlpha = 0.0F;
                            } else {
                                if (gene[38]==gene[39]) {
                                    // Lavender Khaki
                                    patternHue = 0.9999F;
                                    patternSaturation = 0.03F;
                                    patternValue = 0.7F;
                                    iridescenceAlpha = 0.0F;
                                } else {
                                    // Lavender Dun
                                    patternHue = 0.9333F;
                                    patternSaturation = 0.035F;
                                    patternValue = 0.6F;
                                    iridescenceAlpha = 0.05F;
                                    iridescenceHueShift = 0.025F;
                                }
                            }
                        }
                        case 4 -> {
                            // Lavender Smokey
                            if (gene[38]==gene[39] || paint || growthamount<0.5F) {
                                //homo
                                patternHue = 0.0722F;
                                patternSaturation = 0.02F;
                                patternValue = 0.67F;
                                iridescenceAlpha = 0.0F;
                            } else {
                                //het
                                patternHue = 0.0722F;
                                patternSaturation = 0.02F;
                                patternValue = 0.6F;
                                iridescenceAlpha = 0.0F;
                            }
                        }
                        default -> {
                            // Lavender
                            patternHue = 0.0722F;
                            patternSaturation = 0.02F;
                            patternValue = 0.6F;
                            iridescenceAlpha = 0.05F;
                            iridescenceHueShift = 0.05F;
                        }
                    }
                }
            } else {
                if (blue || splash) {
                    switch (Math.max(gene[38], gene[39])) {
                        case 3 -> {
                            if (paint) {
                                // White
                                patternValue = 1.0F;
                                iridescenceAlpha = 0.0F;
                            } else {
                                if (gene[38]==gene[39]) {
                                    // Blue Khaki
                                    patternHue = 0.09F;
                                    patternSaturation = 0.15F;
                                    patternValue = 0.65F;
                                    iridescenceAlpha = 0.05F;
                                    iridescenceHueShift = 0.05F;
                                } else {
                                    // Blue Dun
                                    patternHue = 0.08F;
                                    patternSaturation = 0.24F;
                                    patternValue = 0.53F;
                                    iridescenceAlpha = 0.05F;
                                    iridescenceHueShift = 0.05F;
                                }
                            }
                        }
                        case 4 -> {
                            // Blue Smokey
                            if (gene[38]==gene[39] || paint || growthamount<0.5F) {
                                //homo
                                patternHue = 0.6166F;
                                patternSaturation = 0.07F;
                                patternValue = 0.54F;
                                iridescenceAlpha = 0.0F;
                            } else {
                                //het
                                patternHue = 0.61F;
                                patternSaturation = 0.09F;
                                patternValue = 0.45F;
                                iridescenceAlpha = 0.0F;
                            }
                        }
                        default -> {
                            // Blue
                            patternHue = 0.6222F;
                            patternSaturation = 0.1F;
                            patternValue = 0.40F;
                            iridescenceAlpha = 0.05F;
                            iridescenceHueShift = 0.05F;
                        }
                    }
                } else {
                    // Black
                    switch (Math.max(gene[38], gene[39])) {
                        case 3 -> {
                            // Dun
                            if (paint) {
                                patternValue = 1.0F;
                                iridescenceAlpha = 0.0F;
                            } else {
                                if (gene[38]==gene[39]) {
                                    // Khaki
                                    patternHue = 0.0861F;
                                    patternSaturation = 0.3F;
                                    patternValue = 0.53F;
                                    iridescenceAlpha = 0.05F;
                                    iridescenceHueShift = 0.05F;
                                } else {
                                    // Dun
                                    patternHue = 0.0694F;
                                    patternSaturation = 0.36F;
                                    patternValue = 0.26F;
                                    iridescenceAlpha = 0.1F;
                                    iridescenceHueShift = 0.05F;
                                }
                            }
                        }
                        case 4 -> {
                            // Smokey
                            if (gene[38]==gene[39] || paint || growthamount<0.5F) {
                                //homo
                                patternHue = 0.0725F;
                                patternSaturation = 0.07F;
                                patternValue = 0.35F;
                                iridescenceAlpha = 0.0F;
                            } else {
                                //het
                                patternHue = 0.0725F;
                                patternSaturation = 0.07F;
                                patternValue = 0.12F;
                                iridescenceAlpha = 0.0F;
                            }
                        }
                    }
                }
            }
        }

        if (splash) {
            patternSaturation *= 0.8F;
            patternValue *=0.8F;
        }

        return new float[]{patternHue, patternSaturation, patternValue, iridescenceAlpha, iridescenceHueShift};
    }
    private static int calculateShankRGB(int[] sGene, int[] gene, boolean isFemale) {
        if (gene[20] == 3 && gene[21] == 3) return 16777215;

        float hue = 27.0F;
        float sat = 0.18F;
        float val = 0.8F;


        boolean yellow = false;

        if (gene[44]!=1 && gene[45]!=1) {
            yellow = true;
            if (gene[44]==3 && gene[45]==3) {
                //gold legs
                hue = 43.0F;
                sat = 1.0F;
                val = 0.65F;
            } else {
                //yellow legs
                hue = 47.0F;
                sat = 0.6F;
                val = 0.62F;
            }
        }

        if (isFemale?(sGene[8]==1):(sGene[8]==1 && sGene[9]==1)) {
            //Id Gene
            if (gene[42]==1 || gene[43]==1) {
                //fibro
                if (yellow) {
                    hue *= 1.5F;
                    sat *= 0.8F;
                } else {
                    hue = 240.0F;
                }
                if (gene[42]!=gene[43]) {
                    // het fibro
//                    sat *= 0.17F;
                    val *= 0.5F;
                } else {
                    // homozygous fibro
//                    sat *= 0.5F;
                    val *= 0.28F;
                }
            } else {
                //wildtype slate
                if (yellow) {
                    hue *= 1.4F;
                } else {
                    hue = 180.0F;
                }
                sat *= 0.35F;
                val -= 0.15F;
            }


            if (gene[30]==1 || gene[31]==1) {
                if (yellow) {
                    hue += 10F;
                    sat *= 0.9F;
                }
                val *= 0.9F;
            }

            if (gene[24]==5 || gene[25]==5) {
                if (yellow) {
                    hue += 16F;
                    sat *= 0.8F;
                }
                val *= 0.4F;
            } else if (gene[24]==1 || gene[25]==1) {
                if (yellow) {
                    hue += 14F;
                    sat *= 0.8F;
                }
                val *= 0.75F;
            } else {
                if (yellow) {
                    hue += 10F;
                    sat *= 0.8F;
                } else {
                    sat *= 0.5F;
                }
                val += 0.1F;
            }
        }

        if (gene[166] == 2 && gene[167] == 2) {
            if (yellow) {
                hue += 10F;
                sat *= 0.9F;
            }
            val *= 0.9F;
        }

        if (gene[38]==1 && gene[39]==1) {
            sat *= 0.5F;
            val += 0.25F;
        }

        return Colouration.HSBtoARGB(hue/360F, sat , val);
    }

    private static int calculateShanksRGBUnderColour(int[] sGene, int[] gene, boolean isFemale) {
        float hue = 28.0F;
        float sat = 0.18F;
        float val = 0.8F;
        boolean yellow = false;

        if (gene[20] == 3 && gene[21] == 3) return 16777215;

        if (gene[44]!=1 && gene[45]!=1) {
            yellow = true;
            if (gene[44]==3 && gene[45]==3) {
                //gold legs
                hue = 43.0F;
                sat = 1.0F;
                val = 0.65F;
            } else {
                //yellow legs
                hue = 47.0F;
                sat = 0.6F;
                val = 0.62F;
            }
        }

        if (isFemale?(sGene[8]==1):(sGene[8]==1 && sGene[9]==1)) {
            //Id Gene
            if (gene[42]==1 || gene[43]==1) {
                //fibro
                if (yellow) {
                    hue *= 1.5F;
                    sat *= 0.8F;
                } else {
                    hue = 240.0F;
                }
                if (gene[42]!=gene[43]) {
                    // het fibro
//                    sat *= 0.17F;
                    val *= 0.5F;
                } else {
                    // homozygous fibro
//                    sat *= 0.5F;
                    val *= 0.28F;
                }
            } else {
                //wildtype slate
                if (yellow) {
                    hue *= 1.4F;
                } else {
                    hue = 180.0F;
                }
                sat *= 0.35F;
                val -= 0.15F;
            }
        }

        if (gene[38]==1 && gene[39]==1) {
            sat *= 0.5F;
            val += 0.25F;
        }

        return Colouration.HSBtoARGB(hue/360F, sat, val);
    }

    private static int[] calculateSkinRGB(int[] sGene, int[] gene, boolean isFemale) {
        int colour = 16777215;
        int highlight = 12655875;
        if (gene[20] == 3 && gene[21] == 3) return new int[] {colour, highlight};
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

        if (colour!=16777215) {
            float[] highlightHSB = Colouration.getHSBFromABGR(12655875);
            float[] colourHSB = Colouration.getHSBFromABGR(colour);

            if (gene[38]==1 && gene[39]==1) {
                colourHSB[1] *= 0.5F;
                colourHSB[2] += 0.25F;

                highlightHSB[1] *= 0.5F;
                highlightHSB[2] += 0.25F;
            }

            highlight = Colouration.HSBAtoARGB(colourHSB[0], highlightHSB[1] + ((1.0F-highlightHSB[1])*0.25F), colourHSB[2] + ((1.0F-highlightHSB[2])*0.5F), 0.0F);
        }

        return new int[] {colour, highlight};
    }

    private static int calculateCombRGB(int[] sGene, int[] gene, boolean isFemale) {
        float hue = 0.0F;
        float sat = 0.0F;
        float val = 1.0F;

        if (gene[20] == 3 && gene[21] == 3) return 16777215;

        if (isFemale?(sGene[8]==1):(sGene[8]==1 && sGene[9]==1)) {
            //Id Gene
            if (gene[42]==1 || gene[43]==1) {
                //fibro
                if (gene[42]!=gene[43]) {
                    // het fibro
                    hue = isFemale? 260.0F : 300.0F;
                    sat = 0.5F;
                    val *= isFemale? 0.20F : 0.4F;
                } else {
                    // homozygous fibro
                    hue = 240.0F;
                    sat = 0.5F;
                    val *= isFemale? 0.15F : 0.22F;
                }
            }

            if (gene[30]==1 || gene[31]==1) {
                //melanized
                val *= 0.9F;
            }

            if (gene[100] == 2 && gene[101] == 2) {
                if (gene[24]==5 || gene[25]==5) {
                    val *= 0.9F;
                }
            }
        }


//        if (isFemale?(sGene[8]==1):(sGene[8]==1 && sGene[9]==1)) {
//            if (gene[42]==1 || gene[43]==1) {
//                return gene[42]==gene[43]? 3289655 : 6579303;
//            }
//        }
        return Colouration.HSBtoARGB(hue/360F, sat, val);
    }
}
