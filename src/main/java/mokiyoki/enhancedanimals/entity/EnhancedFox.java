package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.ai.EnhancedEatPlantsGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedAvoidEntityGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedBreedGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedLookAtGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedLookRandomlyGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedPanicGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedTemptGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedWaterAvoidingRandomWalkingEatingGoal;
import mokiyoki.enhancedanimals.ai.general.SeekShelterGoal;
import mokiyoki.enhancedanimals.ai.general.StayShelteredGoal;
import mokiyoki.enhancedanimals.entity.genetics.FoxGeneticsInitialiser;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import mokiyoki.enhancedanimals.init.FoodSerialiser;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
import mokiyoki.enhancedanimals.model.modeldata.AnimalModelData;
import mokiyoki.enhancedanimals.model.modeldata.FoxModelData;
import mokiyoki.enhancedanimals.renderer.texture.TextureGrouping;
import mokiyoki.enhancedanimals.renderer.texture.TexturingType;
import mokiyoki.enhancedanimals.util.Genes;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.world.entity.animal.horse.Donkey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static mokiyoki.enhancedanimals.init.FoodSerialiser.foxFoodMap;
import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_FOX;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;

public class EnhancedFox extends EnhancedAnimalAbstract {

    //avalible UUID spaces : [ S 1 2 3 4 5 6 7 - 8 9 10 11 - 12 13 14 15 - 16 17 18 19 - 20 21 22 23 24 25 26 27 28 29 30 31 ]

    // Texture Layers?
    // Base coat - red, gold, cross x2, silver x3
    // white spotting on different body parts? face, legs, body, ears etc.
    // coat texture - thick vs thin
    // Silvering - handle some with code?
    // eyes - blue, shades of orange / reddish, brown, possibly green?
    // paws - pads (pink or dark)

    private static final String[] FOX_TEXTURES_BASECOAT = new String[] {
            "", "red_1.png", "gold_1.png", "cross_1.png", "cross_2.png", "silver_1.png", "silver_2.png", "silver_3.png"
    };

    private static final String[] FOX_TEXTURES_EYE_L = new String[] {
            "eye_left.png"
    };

    private static final String[] FOX_TEXTURES_EYE_R = new String[] {
            "eye_right.png"
    };

 //   private static final String[] FOX_TEXTURES_PATTERN = new String[] {
 //           "", "b_blackbelly_0.png", "b_blackandtan_0.png", "b_english_blue.png",
  //          "b_blackbelly_1.png", "b_blackbelly_2.png", "b_blackbelly_3.png", "b_blackbelly_4.png", "b_blackbelly_5.png",
  //          "b_blackandtan_1.png", "b_blackandtan_2.png",
  //          "b_blue_german.png", "b_light_blue.png", "b_paddington_blue.png", "b_solid.png"
  //  };

  //  private static final String[] FOX_TEXTURES_SKIN = new String[] {
  //          "skin_pink.png"
  //  };

    // SILVERING guard hairs  - layer on top of base, 40% opacity with screen effect if possible, influenced by phaeomelanin (light pigment)
 private static final String[] FOX_TEXTURES_SILVERING = new String[] {
            "", "silvering_1.png", "silvering_2.png"
    };

 //   private static final String[] FOX_TEXTURES_EYES = new String[] {
 //           "eyes_black.png"
 //   };



    // will come back to this once i have more genetic info - sex linked genes?
    private static final int SEXLINKED_GENES_LENGTH = 2;

    @OnlyIn(Dist.CLIENT)
    public FoxModelData foxModelData;

    private int maxCoatLength;
    private int currentCoatLength;
    private int timeForGrowth = 0;

    private boolean resetTexture = true;

    private String motherUUID = "";

    public EnhancedFox(EntityType<? extends EnhancedFox> entityType, Level worldIn) {
        super(entityType, worldIn, SEXLINKED_GENES_LENGTH, Reference.FOX_AUTOSOMAL_GENES_LENGTH, true);
        this.initilizeAnimalSize();
    }

    private Map<Block, EnhancedEatPlantsGoal.EatValues> createGrazingMap() {
        Map<Block, EnhancedEatPlantsGoal.EatValues> ediblePlants = new HashMap<>();
        ediblePlants.put(Blocks.SWEET_BERRY_BUSH, new EnhancedEatPlantsGoal.EatValues(8, 3, 750));

        return ediblePlants;
    }

    @Override
    protected FoodSerialiser.AnimalFoodMap getAnimalFoodType() {
        return foxFoodMap();
    }

    protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return 0.95F * sizeIn.height;
    }


    private EnhancedWaterAvoidingRandomWalkingEatingGoal wanderEatingGoal;

    @Override
    protected void registerGoals() {
        int napmod = this.random.nextInt(1000);
        this.wanderEatingGoal = new EnhancedWaterAvoidingRandomWalkingEatingGoal(this, 1.0D, 7, 0.001F, 120, 2, 50);
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new EnhancedBreedGoal(this, 0.8D));
        this.goalSelector.addGoal(2, new EnhancedPanicGoal(this, 1.25D));
        this.goalSelector.addGoal(3, new EnhancedAvoidEntityGoal<>(this, Wolf.class, 10.0F, 1.25D, 1.25D, null));
        this.goalSelector.addGoal(4, new EnhancedAvoidEntityGoal<>(this, EnhancedLlama.class, 10.0F, 1.25D, 1.25D, null));
        this.goalSelector.addGoal(5, new EnhancedAvoidEntityGoal<>(this, Donkey.class, 10.0F, 1.25D, 1.25D, null));
        this.goalSelector.addGoal(6, new EnhancedBreedGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new EnhancedTemptGoal(this, 1.0D, 1.2D, false, Items.RABBIT));
        this.goalSelector.addGoal(8, new EnhancedTemptGoal(this, 1.0D, 1.2D, false, Items.AIR));
        this.goalSelector.addGoal(9, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(10, new StayShelteredGoal(this, 5723, 7000, napmod));
        this.goalSelector.addGoal(11, new SeekShelterGoal(this, 1.0D, 5723, 7000, napmod));
        this.goalSelector.addGoal(12, new EnhancedEatPlantsGoal(this, createGrazingMap()));
        this.goalSelector.addGoal(13, this.wanderEatingGoal);
        this.goalSelector.addGoal(14, new EnhancedLookAtGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(15, new EnhancedLookAtGoal(this, EnhancedChicken.class, 6.0F));
        this.goalSelector.addGoal(16, new EnhancedLookAtGoal(this, EnhancedRabbit.class, 6.0F));
        this.goalSelector.addGoal(17, new EnhancedLookRandomlyGoal(this));
    }

    // let foxes wear blankets? definitely collars, but not chests
    @Override
    public boolean canHaveBlanket() {
        return false;
    }

    protected void customServerAiStep()
    {
        this.animalEatingTimer = this.wanderEatingGoal.getEatingGrassTimer();
        super.customServerAiStep();
    }

 // check once model renders
    @Override
    public EntityDimensions getDimensions(Pose poseIn) {
        return EntityDimensions.scalable(0.6F, 1.0F).scale(this.getScale()); //originally 0.8, 1.2
    }

    @Override
    public float getScale() {
        float size = this.getAnimalSize() > 0.0F ? this.getAnimalSize() : 1.0F;
        float newbornSize = 0.325F;
        return this.isGrowing() ? (newbornSize + ((size-newbornSize) * (this.growthAmount()))) : size;
    }

    // used for stuff like entity variations (shrooms and blooms), coat length, etc
    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    protected String getSpecies() {
        return "entity.eanimod.enhanced_fox";
    }

    @Override
    protected int getAdultAge() { return EanimodCommonConfig.COMMON.adultAgeFox.get();}


    @Override
    protected int gestationConfig() {
        return EanimodCommonConfig.COMMON.gestationDaysFox.get();
    }


    public static AttributeSupplier.Builder prepareAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.28D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D);
    }

    @Override
    public InteractionResult mobInteract(Player entityPlayer, InteractionHand hand) {
        ItemStack itemStack = entityPlayer.getItemInHand(hand);
        Item item = itemStack.getItem();

        if (item == ModItems.ENHANCED_FOX_EGG.get()) {
            return InteractionResult.SUCCESS;
        }

        if (item == Items.NAME_TAG) {
            itemStack.interactLivingEntity(entityPlayer, this, hand);
            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(entityPlayer, hand);
    }


    @Override
    public void aiStep() {
        super.aiStep();
    }

    @Override
    protected void runExtraIdleTimeTick() {
        if (this.hunger <= 36000) {
            this.timeForGrowth++;
        }
    }

    @Override
    protected void lethalGenes() {
        int[] gene = this.genetics.getAutosomalGenes();
    //        this.remove(RemovalReason.KILLED);
    }

    @Override
    protected int getNumberOfChildren() {
//        int[] genes = this.genetics.getAutosomalGenes();
        return (ThreadLocalRandom.current().nextInt(3)) + 3;
    }


//    @Override
//    protected int getNumberOfChildren() {
    //    int[] genes = this.genetics.getAutosomalGenes();
    //    int kitRange;
    //    int kitAverage = 1;
     //   int numberOfKits;

     //   if (genes[38] == 1 || genes[39] == 1) {
            //1 baby
       //     kitRange = 1;
     //   } else if (genes[38] == 3 && genes[39] == 3) {
     //       // 2-3 babies
     //       kitRange = 2;
    //        kitAverage = 2;
    //    } else if (genes[38] == 2 && genes[39] == 2) {
     //       //1 to 2 babies
    //        kitRange = 2;
    //    } else {
    //        // 1-3 babies
    //        kitRange = 3;
    //        kitAverage = 1;
    //    }

    //    if (kitRange != 1) {
    //        numberOfKits = ThreadLocalRandom.current().nextInt(kitRange) + kitAverage;
    //    } else {
    //        numberOfKits = 1;
   //     }
//
   //     return numberOfKits;
   // }

    @Override
    protected void incrementHunger() {
        if(this.sleeping) {
            this.hunger = this.hunger + (0.5F*getHungerModifier());
        } else {
            this.hunger = this.hunger + (1.0F*getHungerModifier());
        }
    }

    protected void createAndSpawnEnhancedChild(Level inWorld) {
        EnhancedFox enhancedfox = ENHANCED_FOX.get().create(this.level);
        Genes babyGenes = new Genes(this.genetics).makeChild(this.getOrSetIsFemale(), this.mateGender, this.mateGenetics);
        defaultCreateAndSpawn(enhancedfox, inWorld, babyGenes, -this.getAdultAge());
        this.level.addFreshEntity(enhancedfox);
    }

    @Override
    protected boolean canBePregnant() {
        return true;
    }

    @Override
    protected boolean canLactate() {
        return false;
    }

    protected SoundEvent getAmbientSound() {
        if (isAnimalSleeping()) {
            return null;
        }
        return SoundEvents.FOX_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.FOX_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.FOX_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        if (!this.isSilent() && this.getBells()) {
            this.playSound(SoundEvents.NOTE_BLOCK_CHIME, 1.5F, 0.5F);
        }
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

 // dye and wool color was here


    @Override
    protected boolean shouldDropExperience() { return true; }

    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropCustomDeathLoot(source, looting, recentlyHitIn);
    }



    @OnlyIn(Dist.CLIENT)
    @Override
    public FoxModelData getModelData() {
        return this.foxModelData;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void setModelData(AnimalModelData animalModelData) {
        this.foxModelData = (FoxModelData) animalModelData;
    }

    @OnlyIn(Dist.CLIENT)
    public String getTexture() {
        if (this.enhancedAnimalTextureGrouping == null) {
            this.setTexturePaths();
        } else if (this.resetTexture && !this.isBaby()) {
            this.resetTexture = false;
            this.reloadTextures();
        }

        return getCompiledTextures("enhanced_fox");
    }
    @Override
    @OnlyIn(Dist.CLIENT)
    protected void reloadTextures() {
        this.texturesIndexes.clear();
        this.enhancedAnimalTextures.clear();
        this.enhancedAnimalTextureGrouping = null;
        this.compiledTexture = null;
        this.colouration.setMelaninColour(-1);
        this.colouration.setPheomelaninColour(-1);
        this.setTexturePaths();
    }

    private void clampRGB(float[] color) {
        for (int i = 0; i <= 2; i++) {
            if (color[i] > 1.0F) {
                color[i] = 1.0F;
            } else if (color[i] < 0.0F) {
                color[i] = 0.0F;
            }
        }
    }


    @Override
    @OnlyIn(Dist.CLIENT)
    protected void setTexturePaths() {
        if (this.getSharedGenes() != null) {
            int[] gene = getSharedGenes().getAutosomalGenes();
            //int[] aGenes = getSharedGenes().getAutosomalGenes();

            float[] eyeColor = { 0.45F, 0.85F, 0.46F };

            // int skin = 0;
            // int fur = 0;
            int silvering = 0;
            // int eyes = 0;
            int extension = 0;
            int agouti = 0;
            int basecoat = 0;

            char[] uuidArry = getStringUUID().toCharArray();

            /**
             * MC1R - Extension
             *  1 : E dominant wildtype
             *  2 : e recessive - more silver
             *
             * if (allele1 && allele2) {
             *    //homozygous form
             * }
             * else if (allele1 || allele2) {
             *   //heterozygous form
             * }
             *
             *  gene 0, gene 1
             */

            //fox base coat - extension
            if (gene[0] == 1 && gene[1] == 1) {
                extension = 1;  // EE
            } else if (gene[0] == 1 || gene[1] == 1) {
                extension = 2;  // Ee
            } else {
                extension = 3;  // ee
            }

            /**
             * ASIP - Agouti
             *    1 : A dominant wildtype: red
             *    2 : a recessive - more silver
             *
             *    gene 2, gene 3
             */

            //fox base coat - agouti
            // if gene 2 = 1 (dom A) and gene 3 = 1 (dom A), then its homozygous
            if (gene[2] == 1 && gene[3] == 1 ) {
                agouti = 1;  // AA
            } else if (gene[2] == 2 || gene[3] == 2 ) {   // heterozygous
                agouti = 2;  // Aa
            } else {
                agouti = 3;  // aa
            }

            // 1 = homo Dom, 2 = het, 3 = homo Rec
            if (extension == 1 && agouti == 1 ) {
                basecoat = 1;  // AAEE - red wildtype          red1.png
            } else if (extension == 1 && agouti == 2) {
                basecoat = 2;  // AAEe - gold                  gold1.png
            } else if (extension == 1 && agouti == 3) {
                basecoat = 3;  // AAee - standard silver       silver2.png
            } else if (extension == 2 && agouti == 1) {
                basecoat = 4;  // AaEE - alaskan cross         cross1.png
            } else if (extension == 2 && agouti == 2) {
                basecoat = 5;  // AaEe - blended cross         cross2.png
            } else if (extension == 2 && agouti == 3) {
                basecoat = 6;  // Aaee - sub-standard silver   silver2.png
            } else if (extension == 3 && agouti == 1) {
                basecoat = 7;  // aaEE - alaskan silver        silver1.png
            } else if (extension == 3 && agouti == 2) {
                basecoat = 8;  // aaEe - sub-alaskan silver    silver1.png
            } else {
                basecoat = 9;  // aaee - double silver         silver3.png
            }

            /**
             * SILVER GUARD HAIRS
             *    gene 4 : wildtype: no silvering
             *    gene 5 : more silvering
             */
            // SILVERING guard hairs  - layer on top of base
            // [ ]
            //if (gene[4] == 1 && gene[5] == 1) {          // both = 1
            //    silvering = 1;  // ss full silvering
            //} else if ((gene[4] == 1 || gene[5] == 2) || (gene[4] == 2 || gene[5] == 1)) {   // either
            //    silvering = 2;  // Ss faded silvering
            //} else {                                     // if neither = 1
            //    silvering = 3;  //
            //}


            //basic silvering - need default to be no silvering
            // should be:  SS = no silver , Ss = faded silver , ss = full silver
            // right now in the init, [gene] = 2 (or should I use 1?)

            if (gene[4] == 2 && gene[5] == 2){  // SS?
                silvering = 1;  //
            } else if (gene[4] == 1 && gene[5] == 2) {  // Ss?
                silvering = 2;  //
            }


            if (extension==1 && agouti==2) {  // gold foxes
            eyeColor[0] = 0.10F;  // hue
            eyeColor[1] -= 0.215F;  // sat
            eyeColor[2] += 0.22F;  // lightness

             }

        //    if (age < 108000) {
        //        eyeColor[0] = 0.55F;  // hue
        //        eyeColor[1] -= 0.215F;  // sat
        //        eyeColor[2] += 0.22F;  // lightness

        //    }


            int eyeHue = 0; // negative = orange, positive = green
            for (int i = 50; i < 60; i++) {
                if (gene[i] == 2) {
                    eyeHue--;
                }
                else if (gene[i] == 3) {
                    eyeHue++;
                }
            }

            int eyeLightness = 0;
            for (int i = 60; i < 72; i++) {
                if (gene[i] == 2) {
                    eyeLightness += i < 66 ? 1 : -1;
                }
            }

            int eyeSaturation = 0;
            for (int i = 72; i < 80; i++) {
                if (gene[i] == 2) {
                    eyeSaturation += 1;
                }
            }

            eyeColor[0] += eyeHue*0.0030F; // 0.0130F

            eyeColor[1] += eyeSaturation*0.025F; //0.025F

            eyeColor[1] -= eyeLightness*0.005F; // 0.005F
            eyeColor[2] += eyeLightness*0.0225F; // 0.0225F


            clampRGB(eyeColor);


            int leftEyeRGB = Colouration.HSBtoARGB(eyeColor[0], eyeColor[1], eyeColor[2]);
            int rightEyeRGB = Colouration.HSBtoARGB(eyeColor[0], eyeColor[1], eyeColor[2]);

        // CAT NOSE COLOR example
            // Colouration.HSBtoARGB(0.13F [HUE], 0.02F [SATURATION], 0.96F [BRIGHTNESS]);
        //    int whiteRGB = Colouration.HSBtoARGB(0.13F, 0.02F, 0.96F);
        //    int[] noseColors = {
                    //Brick 0
        //            Colouration.HSBtoARGB(0.0F, 0.429F, 0.494F),
                    //Black 1
        //            Colouration.HSBtoARGB(0.0F, 0.051F, 0.231F),
                    //Light Brick 2
        //            Colouration.HSBtoARGB(0.0F, 0.439F, 0.594F),
                    //Pink 3
        //            Colouration.HSBtoARGB(0.987F, 0.404F, 0.861F),
                    //Brown 4
        //            Colouration.HSBtoARGB(0.02F, 0.321F, 0.231F),
        //    };

            this.colouration.setLeftEyeColour(leftEyeRGB);
            this.colouration.setRightEyeColour(rightEyeRGB);



        // FAWN SPOTS
            // 1 - no spots, default and dominant  2 - ear spots, homo?  3 - body spots, homo? 4 - ear and body spots, homo, rare?


            // EXAMPLE for making sure a trait occurs when several others do
            // if (whiteFace != 0 || white != 0 || berk != 0 || whiteSplash != 0) {
            // }


// Texture Grouping
            // apply basecoats as foundation
            // merge, alpha, mask
            // groups: parent (add everything at end), skin, hair, foundation, eyes and paws at end in parent
            // grouping for pigs: skin (merge), hair (merge and mask), white markings, overlay - coat texture,

            TextureGrouping parentGroup = new TextureGrouping(TexturingType.MERGE_GROUP);  // create parent group as merge
            TextureGrouping hairGroup = new TextureGrouping(TexturingType.MASK_GROUP);     // hair group as mask

            TextureGrouping foundationGroup = new TextureGrouping(TexturingType.MERGE_GROUP);  // create foundation group
            addTextureToAnimalTextureGrouping(foundationGroup, FOX_TEXTURES_BASECOAT, basecoat, l -> l != 0);  // create basecoat group
            hairGroup.addGrouping(foundationGroup); // foundation added to hair

            parentGroup.addGrouping(hairGroup);  // hair added to parent

            TextureGrouping detailGroup = new TextureGrouping(TexturingType.MERGE_GROUP); // add detail group as merge
            //  if (silvering!=0) {}
            addTextureToAnimalTextureGrouping(detailGroup, FOX_TEXTURES_SILVERING, silvering, l -> l != 0); // create silvering group - put in hair group later
            //addTextureToAnimalTextureGrouping(detailGroup, FOX_TEXTURES_SKIN, skin, null);
            //addIndividualTextureToAnimalTextureGrouping(detailGroup, TexturingType.APPLY_RGB, "eye_left.png", getColour(this.growthAmount()));
            //addIndividualTextureToAnimalTextureGrouping(detailGroup, TexturingType.APPLY_RGB, "eye_right.png", getColour(this.growthAmount()));

            addTextureToAnimalTextureGrouping(detailGroup, TexturingType.APPLY_EYE_LEFT_COLOUR, FOX_TEXTURES_EYE_L, 0, l-> true);
            addTextureToAnimalTextureGrouping(detailGroup, TexturingType.APPLY_EYE_RIGHT_COLOUR, FOX_TEXTURES_EYE_R, 0, l -> true);

            parentGroup.addGrouping(detailGroup); // detail added to parent

            this.setTextureGrouping(parentGroup);  // finalizes texture grouping
        }
    }

    @Override
    protected void setAlphaTexturePaths() {}

 //   private int getColour(float age) {
 //       return age < 0.2F ? 9608151 : 14712338;  // if age is less than 0.2, execute [if true] : [if false]
 //   }


 //   if (age < 108000) {
 //       eyeColor[0] = 0.55F;  // hue
 //       eyeColor[1] -= 0.215F;  // sat
 //       eyeColor[2] += 0.22F;  // lightness
 //   }

    @Override
    public Colouration getRgb() {
        boolean flag = (this.colouration.getMelaninColour() == -1 || this.colouration.getPheomelaninColour() == -1) && getSharedGenes()!=null;
        this.colouration = super.getRgb();

        if(this.colouration == null) {
            return null;
        }

        return this.colouration;
    }


    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
    }

    /**
     * (abstract) Protected helper method to read subclass entity assets from NBT.
     */
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor inWorld, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag itemNbt) {
        return commonInitialSpawnSetup(inWorld, livingdata, getAdultAge(), 60000, 80000, spawnReason);
    }

    @Override
    public void setInitialDefaults() {
        super.setInitialDefaults();
    }

    @Override
    protected Genes createInitialGenes(LevelAccessor world, BlockPos pos, boolean isDomestic) {
        return new FoxGeneticsInitialiser().generateNewGenetics(world, pos, isDomestic);
    }

    @Override
    public Genes createInitialBreedGenes(LevelAccessor world, BlockPos pos, String breed) {
        return new FoxGeneticsInitialiser().generateWithBreed(world, pos, breed);
    }

    @Override
    protected void initializeHealth(EnhancedAnimalAbstract animal, float health) {
//        int[] genes = animal.genetics.getAutosomalGenes();

        health = 8.0F;

        super.initializeHealth(animal, health);
    }

    // edit later for foxes
    @Override
    public void initilizeAnimalSize() {
        int[] genes = this.genetics.getAutosomalGenes();
        float size = 1.0F;

        //TODO [ range goes here ]
        this.setAnimalSize(size);
    }


}