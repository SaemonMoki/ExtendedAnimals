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


    private static final String[] FOX_TEXTURES_BASE = new String[] {   // plain white
            "solid_base.png"
    };

    private static final String[] FOX_TEXTURES_SKINBASE = new String[] {
            "skin_dark.png", "skin_pink.png"
    };

    private static final String[] FOX_TEXTURES_BASECOAT = new String[] {
            "", "red_1.png", "gold_1.png", "cross_1.png", "cross_2.png", "silver_1.png", "silver_2.png", "silver_3.png"
    };

    private final int IDX_BASECOATS_AGOUTI = 0;
    private final int IDX_BASECOATS_SILVER = IDX_BASECOATS_AGOUTI + 3;
    private static final String[] FOX_TEXTURES_AGOUTI = new String[] {
            "red_orange_1.png",
            "alaskan_cross_1.png", "cross_2_test_3.png",
            "silver_standard_test.png", "silver_substandard_test.png", "silver_alaskan_test.png", "silver_subalaskan_test.png", "silver_doublesilver_test.png" // SILVERS; BLACK
    };

    private static final String[] FOX_TEXTURES_SHADING = new String[] {
            "red_highlight_1.png"
    };  // tint?

    private static final String[] FOX_TEXTURES_BLACK = new String[] {
            "", "silver_doublesilver_test.png",
            "wt_black_2.png",
            "black_socks_1.png", "black_socks_2.png",
            "gold_test_2.png"


    };  // black cross patterns, gold pattern black silver_1_test.png
    // ALL black masks

    private static final String[] FOX_TEXTURES_SMOKY = new String[] {
            "", "gold_test_2.png",
    };
    // smoky factor needs some texture alphas

    private static final String[] FOX_TEXTURES_BLACK_SOCKS = new String[] {
            "", "black_socks_1.png", "black_socks_2.png"
    };
    private static final String[] FOX_TEXTURES_BLACK_FUR = new String[] {
            "", "wt_black_2.png"
    };

    private static final String[] FOX_TEXTURES_FUR = new String[] {
            "coat_normal.png", "coat_wooly.png"
    };

    private static final String[] FOX_TEXTURES_TEST = new String[] {
            "", "silver_1_test_2.png"
    };

    private static final String[] FOX_TEXTURES_FUR_SHADING = new String[] {
            "", "red_highlight_1.png"
    };

    private static final String[] FOX_TEXTURES_UNDERBELLY = new String[] {
            "", "underbelly_default.png"
    };

    private static final String[] FOX_TEXTURES_EYE_L = new String[] {
            "eye_left.png"
    };

    private static final String[] FOX_TEXTURES_EYE_R = new String[] {
            "eye_right.png"
    };

    private static final String[] FOX_TEXTURES_PLATINUM = new String[] {
            "", "platinum_1.png", "platinum_2.png", "platinum_3.png", "platinum_5.png", "platinum_5.png"
    };

    private final int IDX_MARBLE_1 = 1; // start at 1, het
    private final int IDX_MARBLE_2 = IDX_MARBLE_1 + 2; // 2 textures after marble_1 start, homo
    private static final String[] FOX_TEXTURES_MARBLE = new String[] {
            "", "marble_het_1.png", "marble_het_2.png", "marble_homo_1.png", "marble_homo_2.png"
    };

    private static final String[] FOX_TEXTURES_GEORGIANWHITE = new String[] {
            "", "georgian_white_1.png"
    };

    private final int IDX_WHITEMARK_1 = 1; // start at 1, het
    private final int IDX_WHITEMARK_2 = IDX_WHITEMARK_1 + 3; // 3 textures after marble_1 start, homo
    private static final String[] FOX_TEXTURES_WHITEMARK = new String[] {
            "", "whitemark_1.png", "whitemark_2.png", "whitemark_3.png", "whitemark_4.png", "whitemark_5.png", "whitemark_6.png"
    };

    private static final String[] FOX_TEXTURES_RINGNECK = new String[] {
            "", "ringneck_0.png", "ringneck_1.png", "ringneck_2.png", "ringneck_3.png",
            "ringneck_4.png", "ringneck_5.png", "ringneck_6.png"
    };

    private static final String[] FOX_TEXTURES_BURGUNDY = new String[] {
            "", "burgundy_testoverlay2.png"
    }; // replace with rgb edit


    private static final String[] FOX_TEXTURES_NOSE = new String[] {
            "nose_base.png"
    };


    // SILVERING guard hairs  - layer on top of base, 40% opacity with screen effect if possible, influenced by phaeomelanin (light pigment)
    private static final String[] FOX_TEXTURES_SILVERING = new String[] {
            "", "silvering_1.png", "silvering_2.png"
    };


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
    public void lethalGenes(){
        int[] genes = this.genetics.getAutosomalGenes();
        if(genes[10] == 2 && genes[11] == 2) {   // georgian white
            this.remove(RemovalReason.KILLED);
        } else if (genes[12] == 2 && genes[13] == 2) {  // plat
            this.remove(RemovalReason.KILLED);
        } else if (genes[12] == 2 && genes[11] == 2 || genes[10] == 2 && genes[13] == 2 ) {  // combo plat georg
            this.remove(RemovalReason.KILLED);
        } else if (genes[8] == 2 && genes[10] == 2 || genes[9] == 2 && genes[11] == 2 || genes[9] == 2 && genes[10] == 2 || genes[8] == 2 && genes[11] == 2) {  // combo wm georg
            this.remove(RemovalReason.KILLED);
        } else if (genes[8] == 2 && genes[12] == 2 || genes[9] == 2 && genes[13] == 2 || genes[9] == 2 && genes[12] == 2 || genes[8] == 2 && genes[13] == 2) {  // combo wm plat
            this.remove(RemovalReason.KILLED);
        }
    }

    @Override
    protected int getNumberOfChildren() {
//        int[] genes = this.genetics.getAutosomalGenes();
        return (ThreadLocalRandom.current().nextInt(3)) + 3;
    }


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

    private void clampRGB(float[] color, boolean clampHue) {
        if (clampHue) {
            float minHue = 0.020F;
            float maxHue = 0.101F;
            if (color[0] < minHue) {
                color[0] = minHue;
            }
            else if (color[0] > maxHue) {
                color[0] = maxHue;
            }
        }
        for (int i = 0; i <= 2; i++) {
            if (color[i] > 1.0F) {
                color[i] = 1.0F;
            } else if (color[i] < 0.0F) {
                color[i] = 0.0F;
            }
        }
    }
    private void clampRGB(float[] color) {
        clampRGB(color, true);
    }


    @Override
    @OnlyIn(Dist.CLIENT)
    protected void setTexturePaths() {
        if (this.getGenes() != null) {
            int[] gene = this.getGenes().getAutosomalGenes();

            int idxBlackSolid = 1;
            int idxBlackwt = idxBlackSolid + 1;
            int idxBlacksocks = idxBlackwt + 1;
            int idxSmoky = idxBlacksocks + 2; // 2 textures after socks start

            int noseRGB = 1;  // default black

            int black = 0;
            int shading = 0;
            
            int agoutiBase = 1;

            int basecoat = 0;

            int skin = 0;
            int skinBlack = 3;

            int underbelly = 0;

            int blacksocks = 1;


            int coat_alpha = 0;
            int coat_texture = 0;
            int coatType = 1;

            int white = 0;

            int marble = 0;
            int georgianwhite = 0;
            int whitemark = 0;
            int platinum = 0;
            int ringneck = 0;

            int silvering = 0;

            int redagouti = IDX_BASECOATS_AGOUTI;

            // BOOLEANS ARE YES/NO or ON/OFF - two values!
            boolean wideband = false;

            boolean agoutiBlack = false;


            char[] uuidArry = getStringUUID().toCharArray();

            /*
             * RANDOM TEXTURES
             *
             * UUID Spaces used:
             * [0] - Gender
             * [1] - Marble
             * [2] - Whitemark
             * [3] - Platinum
             * [4] - Ringneck
             */

            /**
             * SILVER GUARD HAIRS
             *    gene 4 : wildtype: no silvering
             *    gene 5 : more silvering
             */
            //basic silvering - need default to be no silvering
            // should be:  SS = no silver , Ss = faded silver , ss = full silver
            if (gene[4] == 2 || gene[5] == 2) {  // Ss, sS, or ss
                silvering = gene[4]==gene[5]? 1 : 2; // reads as: silvering equals if gene 4 and gene 5 match ss else Ss, or sS
            }

            // Marble - incomplete dominant?
            if (gene[6] == 2 || gene[7] == 2) {  // het
                marble = (gene[6]==gene[7]? 3 : 1) + (uuidArry[1] % 2);   // 1
            }

            // Whitemark - dominant
            if (gene[8] == 2 && gene[9] == 2){  // homo WW
                whitemark = 4 + (uuidArry[2] % 3);
            } else if (gene[8] == 2 || gene[9] == 2) {  // het Ww
                whitemark = 1 + (uuidArry[2] % 3);   // 1
            }

            // Platinum - incomplete dominant
            if (gene[12] == 2 || gene[13] == 2){
                platinum = 1 + (uuidArry[3] % 5);
                noseRGB = 3;
            }

            // Ringneck - incomplete dominant?
            if (gene[14] == 2 || gene[15] == 2) {  // het or homo
                ringneck = gene[14]==gene[15] ? 5 + (uuidArry[4] % 3) : 1 + (uuidArry[4] % 4);   // homo : het
            }

            // black socks
            if (gene[16] == 2 && gene[17] == 2) {  //
                blacksocks = 0;
            }

            // georgian white  - het expressed, homo lethal
            if (gene[10] == 2 || gene[11] == 2){  // gwgw
                georgianwhite = 1;
            }

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
             *
             */

            //fox base coat - extension
            int extension = 1;
            if (gene[0] == 2 || gene[1] == 2) {
                extension = gene[0]==gene[1]? 3 : 2;  // Ee
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
            int agouti = 1;
            if (gene[2] == 2 || gene[3] == 2 ) {   // heterozygous
                agouti = gene[2]==gene[3]? 3 : 2;  // aa : Aa/aA
            }

    // TEXTURE GROUPING

            // merge, alpha, mask
            // groups: parent (add everything at end), skin, hair, foundation, eyes and paws at end in parent


            TextureGrouping parentGroup = new TextureGrouping(TexturingType.MERGE_GROUP);  // create PARENT
            TextureGrouping skinGroup = new TextureGrouping(TexturingType.MERGE_GROUP);  // create SKIN

        // SKIN GROUPS
            if (black != idxBlackSolid) { // change to 0
                TextureGrouping skinRedGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                addTextureToAnimalTextureGrouping(skinRedGroup, FOX_TEXTURES_SKINBASE, 1, l -> true);
                skinGroup.addGrouping(skinRedGroup);
            }
            if (black != 0) {
                TextureGrouping skinBlackGroup = new TextureGrouping(TexturingType.MASK_GROUP);
                TextureGrouping skinBlackMaskGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                addTextureToAnimalTextureGrouping(skinBlackMaskGroup, FOX_TEXTURES_BLACK, black, true);
                skinBlackGroup.addGrouping(skinBlackMaskGroup);
                TextureGrouping skinBlackBaseGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                addTextureToAnimalTextureGrouping(skinBlackBaseGroup, FOX_TEXTURES_SKINBASE, 1, true); // former value 2
                skinBlackGroup.addGrouping(skinBlackBaseGroup);
                if (agouti != 0) {
                    TextureGrouping skinBlackUnderbellyGroup = new TextureGrouping(TexturingType.MASK_GROUP);
                    addTextureToAnimalTextureGrouping(skinBlackUnderbellyGroup, FOX_TEXTURES_UNDERBELLY, 1, true);
                    addTextureToAnimalTextureGrouping(skinBlackUnderbellyGroup, FOX_TEXTURES_SKINBASE, 0, true);
                    skinBlackGroup.addGrouping(skinBlackUnderbellyGroup);
                }
                skinGroup.addGrouping(skinBlackGroup);
            }
            if (white != 0) {   // adds white texture to skin
                TextureGrouping skinWhiteGroup = new TextureGrouping(TexturingType.MASK_GROUP);
                TextureGrouping whiteMaskGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                addTextureToAnimalTextureGrouping(whiteMaskGroup, FOX_TEXTURES_PLATINUM, platinum, l -> l != 0);
                addTextureToAnimalTextureGrouping(whiteMaskGroup, FOX_TEXTURES_MARBLE, marble, l -> l != 0);
                addTextureToAnimalTextureGrouping(whiteMaskGroup, FOX_TEXTURES_GEORGIANWHITE, georgianwhite, l -> l != 0);
                addTextureToAnimalTextureGrouping(whiteMaskGroup, FOX_TEXTURES_WHITEMARK, whitemark, l -> l != 0);
                addTextureToAnimalTextureGrouping(whiteMaskGroup, FOX_TEXTURES_RINGNECK, ringneck, l -> l != 0);
                skinWhiteGroup.addGrouping(whiteMaskGroup);
                TextureGrouping whiteTexGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                addTextureToAnimalTextureGrouping(whiteTexGroup, FOX_TEXTURES_SKINBASE, 0, l -> true);
                skinWhiteGroup.addGrouping(whiteTexGroup);
                skinGroup.addGrouping(skinWhiteGroup);
            }



            // HAIR ALPHAS AND TEXTURES HERE


            //  FUR TEXTURES

                TextureGrouping hairGroup = new TextureGrouping(TexturingType.MASK_GROUP);     // create HAIR
        //        TextureGrouping hairAlphaGroup = new TextureGrouping(TexturingType.MERGE_GROUP); // create HAIR ALPHA
        //        addTextureToAnimalTextureGrouping(hairAlphaGroup, FOX_TEXTURES_FUR, coat_alpha, coat_alpha != 0);  // add fur tex as alpha


        //    hairGroup.addGrouping(hairAlphaGroup);
            TextureGrouping hairTexGroup = new TextureGrouping(TexturingType.MERGE_GROUP); // create HAIR TEX - that red and black groups go into


         // RED LAYER
            if (extension != 0 || agouti != 0) {
                TextureGrouping redGroup = new TextureGrouping(TexturingType.MASK_GROUP);  // create REDGROUP
                TextureGrouping redBaseGroup = new TextureGrouping(TexturingType.MERGE_GROUP); // create REDBASE
                addTextureToAnimalTextureGrouping(redBaseGroup, TexturingType.APPLY_RED, FOX_TEXTURES_BASE, 0, l -> true); // base texture red

                // agouti mask (TEST)
                TextureGrouping redPatternGroup = new TextureGrouping(TexturingType.MASK_GROUP);
                TextureGrouping agoutiMaskGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                addTextureToAnimalTextureGrouping(agoutiMaskGroup, FOX_TEXTURES_TEST, agouti != 0 ? 1 : 0, l -> true); // TEST agouti mask
                redPatternGroup.addGrouping(agoutiMaskGroup); // add Mask to Pattern

                // AGOUTI TEXTURE
                TextureGrouping agoutiTexGroup = new TextureGrouping(TexturingType.MASK_GROUP);

                // 1 = homo Dom, 2 = het, 3 = homo Rec
                if (extension==1) {
                    switch (agouti) {
                        case 1 -> {
                            basecoat = 1;  // AAEE - RED wildtype  // AAEE - red wildtype          red1.png  RED
                            black = idxBlackwt;
                            TextureGrouping redWTGroup = new TextureGrouping(TexturingType.MERGE_GROUP); // creates redWTG
                            //    addTextureToAnimalTextureGrouping(redWTGroup, FOX_TEXTURES_AGOUTI, redagouti, l -> l != 0);
                            //    addTextureToAnimalTextureGrouping(redWTGroup, TexturingType.APPLY_RED, FOX_TEXTURES_AGOUTI, redagouti, l -> true);
                            addTextureToAnimalTextureGrouping(redWTGroup, TexturingType.APPLY_RED, FOX_TEXTURES_AGOUTI,  0, l -> true);
                            agoutiTexGroup.addGrouping(redWTGroup); // add WTG to red group
                        }
                        case 2 -> {
                            basecoat = 2;  // AAEe - gold                  gold1.png  GOLD / SMOKY RED
                            black = idxSmoky;
                        }
                        case 3 -> {
                            basecoat = 3;  // AAee - standard silver       silver2.png  STANDARD SILVER
                            black = 1;
                        }
                    }
                } else if (extension==2) {
                    switch (agouti) {
                        case 1 -> {
                            basecoat = 4;  // AaEE - ALASKAN / GOLDEN CROSS // AaEE - alaskan cross         cross1.png  ALASKAN / GOLDEN CROSS
                            TextureGrouping redACrossGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                            addTextureToAnimalTextureGrouping(redACrossGroup, FOX_TEXTURES_AGOUTI, 1, true);
                            agoutiTexGroup.addGrouping(redACrossGroup); // add alaskan cross to red group
                        }
                        case 2 -> {
                            basecoat = 5;  // AaEe - blended cross         cross2.png  BLENDED / SILVER CROSS
                            TextureGrouping redBlendGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                            addTextureToAnimalTextureGrouping(redBlendGroup, FOX_TEXTURES_AGOUTI, 2, true);
                            agoutiTexGroup.addGrouping(redBlendGroup); // add blended cross to red group
                        }
                        case 3 -> {
                            basecoat = 6;  // Aaee - sub-standard silver   silver2.png  SUB-STANDARD SILVER
                            black = 1;
                        }
                    }
                } else {
                    switch (agouti) {
                        case 1 -> {
                            basecoat = 7;  // aaEE - alaskan silver        silver1.png  ALASKAN SILVER
                            black = 1;
                        }
                        case 2 -> {
                            basecoat = 8;  // aaEe - sub-alaskan silver    silver1.png  SUB-ALASKAN SILVER
                            black = 1;
                        }
                        case 3 -> {
                            basecoat = 9;  // aaee - double silver         silver3.png  DOUBLE SILVER
                            black = 1;
                        }
                    }
                }

                TextureGrouping agoutiInnerMaskGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                addTextureToAnimalTextureGrouping(agoutiInnerMaskGroup, FOX_TEXTURES_BLACK_FUR[1], true); //  ear deets

                agoutiTexGroup.addGrouping(agoutiInnerMaskGroup);

                hairTexGroup.addGrouping(redBaseGroup);
                hairTexGroup.addGrouping(agoutiTexGroup);
            }

            //BLACK LAYER
            // should have textures / rgb changes that apply only to black areas
            if (black != 0) {
                TextureGrouping blackGroup = new TextureGrouping(TexturingType.MASK_GROUP);
                TextureGrouping blackAlphaGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                addTextureToAnimalTextureGrouping(blackAlphaGroup, TexturingType.APPLY_BLACK, FOX_TEXTURES_BLACK, black, l -> l != 0); // hmm
                blackGroup.addGrouping(blackAlphaGroup);

                TextureGrouping blackTexGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                TextureGrouping blackBaseGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                addTextureToAnimalTextureGrouping(blackBaseGroup, TexturingType.APPLY_BLACK, FOX_TEXTURES_BASE, 0, l -> true); // makes base texture black - may not need this? check

                // 1 = homo Dom, 2 = het, 3 = homo Rec
                if (extension == 1 && agouti == 1 ) {
                    basecoat = 1;  // AAEE - RED wildtype
                    TextureGrouping blackWTGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                //    addTextureToAnimalTextureGrouping(blackBaseGroup, TexturingType.APPLY_BLACK, FOX_TEXTURES_BLACK_SOCKS, blacksocks, l -> true); // should maybe be RBG instead? for dilutions etc
                //    addTextureToAnimalTextureGrouping(blackBaseGroup, FOX_TEXTURES_BLACK_SOCKS, blacksocks, l -> l != 0);

                    blackTexGroup.addGrouping(blackWTGroup); // add to black group
                    // black socks, wildtype amount of black

                } else if (extension == 1 && agouti == 2) {
                    // AAEe - GOLD / SMOKY RED
                    TextureGrouping blackSmokyGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                    addTextureToAnimalTextureGrouping(blackBaseGroup, TexturingType.APPLY_BLACK, FOX_TEXTURES_SMOKY, 1, l -> true);
                    blackTexGroup.addGrouping(blackSmokyGroup); // add smoky to black

                } else if (extension == 1 && agouti == 3) {
                    // AAee - STANDARD SILVER
                    TextureGrouping blackStandardGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                    addTextureToAnimalTextureGrouping(blackBaseGroup, TexturingType.APPLY_BLACK, FOX_TEXTURES_AGOUTI, 2, l -> true);
                    blackTexGroup.addGrouping(blackStandardGroup); // add standard to black

                } else if (extension == 2 && agouti == 1) {
                    basecoat = 4;  // AaEE - ALASKAN / GOLDEN CROSS
                    TextureGrouping blackACrossGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                    addTextureToAnimalTextureGrouping(blackACrossGroup, FOX_TEXTURES_AGOUTI, 1, true);
                    blackTexGroup.addGrouping(blackACrossGroup); // add alaskan cross to black group

                } else if (extension == 2 && agouti == 2) {
                    basecoat = 5;  // AaEe - BLENDED / SILVER CROSS
                    TextureGrouping blackBlendGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                    addTextureToAnimalTextureGrouping(blackBlendGroup, FOX_TEXTURES_AGOUTI, 2, true);
                    blackTexGroup.addGrouping(blackBlendGroup); // add blended cross to black group

                } else if (extension == 2 && agouti == 3) {
                    basecoat = 6;  // Aaee - SUB-STANDARD SILVER
                    TextureGrouping blackSubstandardGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                    addTextureToAnimalTextureGrouping(blackSubstandardGroup, FOX_TEXTURES_AGOUTI, 3, true);
                    blackTexGroup.addGrouping(blackSubstandardGroup); // add subStandard to black group

                } else if (extension == 3 && agouti == 1) {
                    basecoat = 7;  // aaEE - ALASKAN SILVER - should have brownish tint along sides and ears
                    TextureGrouping blackAlaskanGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                    addTextureToAnimalTextureGrouping(blackAlaskanGroup, FOX_TEXTURES_AGOUTI, 3, true);
                    blackTexGroup.addGrouping(blackAlaskanGroup); // add Alaskan to black group

                } else if (extension == 3 && agouti == 2) {
                    basecoat = 8;  // aaEe - SUB-ALASKAN SILVER
                    TextureGrouping blackSubalaskanGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                    addTextureToAnimalTextureGrouping(blackSubalaskanGroup, FOX_TEXTURES_AGOUTI, 4, true);
                    blackTexGroup.addGrouping(blackSubalaskanGroup); // add subAlaskan to black group

                } else {
                    basecoat = 9;  // aaee - DOUBLE SILVER  -- FOX_TEXTURES_AGOUTI texture 7
                    TextureGrouping blackDoubleGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                    addTextureToAnimalTextureGrouping(blackDoubleGroup, FOX_TEXTURES_AGOUTI, 7, true);
                //    addTextureToAnimalTextureGrouping(blackDoubleGroup, FOX_TEXTURES_BLACK, black, true);
                    blackTexGroup.addGrouping(blackDoubleGroup); // add double to black group
                }

                blackGroup.addGrouping(blackTexGroup);
                hairTexGroup.addGrouping(blackGroup); // add black to hair
            }

            int whiteRGB = Colouration.HSBtoARGB(0.13F, 0.02F, 0.96F);

            //WHITE LAYER (this seems to work)
            if (georgianwhite != 0 || marble != 0 || whitemark != 0 || platinum != 0 || ringneck != 0) {
                TextureGrouping hairWhiteGroup = new TextureGrouping(TexturingType.MASK_GROUP);  // create white mask group
                TextureGrouping whiteMaskGroup = new TextureGrouping(TexturingType.MERGE_GROUP);  // create white inner mask group

                addTextureToAnimalTextureGrouping(whiteMaskGroup, FOX_TEXTURES_PLATINUM, platinum, l -> l != 0);
                addTextureToAnimalTextureGrouping(whiteMaskGroup, FOX_TEXTURES_MARBLE, marble, l -> l != 0);
                addTextureToAnimalTextureGrouping(whiteMaskGroup, FOX_TEXTURES_GEORGIANWHITE, georgianwhite, l -> l != 0);
                addTextureToAnimalTextureGrouping(whiteMaskGroup, FOX_TEXTURES_WHITEMARK, whitemark, l -> l != 0);
                addTextureToAnimalTextureGrouping(whiteMaskGroup, FOX_TEXTURES_RINGNECK, ringneck, l -> l != 0);

                TextureGrouping whiteTextureGroup = new TextureGrouping(TexturingType.MERGE_GROUP); // create white texture group
                addTextureToAnimalTextureGrouping(whiteTextureGroup, TexturingType.APPLY_RGB, FOX_TEXTURES_BASE[0], "w", whiteRGB); // affect with rgb? unsure if this works

                hairWhiteGroup.addGrouping(whiteMaskGroup);  // add inner mask to hairwhite
                hairWhiteGroup.addGrouping(whiteTextureGroup);  // add Texture Group to hairwhite
                hairTexGroup.addGrouping(hairWhiteGroup);  // add hairwhite to hair tex
           }

            // UNDERBELLY
            if (extension != 0 || agouti != 0) {
                TextureGrouping bellyWhiteGroup = new TextureGrouping(TexturingType.MASK_GROUP);  // create white mask group
                TextureGrouping whitebellyGroup = new TextureGrouping(TexturingType.MERGE_GROUP);  // create white inner mask group

                addTextureToAnimalTextureGrouping(whitebellyGroup, FOX_TEXTURES_UNDERBELLY, 1, true);

                TextureGrouping whiteTextureGroup = new TextureGrouping(TexturingType.MERGE_GROUP); // create white texture group
                addTextureToAnimalTextureGrouping(whiteTextureGroup, TexturingType.APPLY_RGB, FOX_TEXTURES_BASE[0], "w", whiteRGB); // affect with rgb? unsure if this works

                bellyWhiteGroup.addGrouping(whitebellyGroup);  // add inner mask to hairwhite
                bellyWhiteGroup.addGrouping(whiteTextureGroup);  // add Texture Group to hairwhite
                hairTexGroup.addGrouping(bellyWhiteGroup);  // add hairwhite to hair tex
            }

            // HAIR OVERLAY
            TextureGrouping hairOverlayGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
            addTextureToAnimalTextureGrouping(hairOverlayGroup, FOX_TEXTURES_FUR, coatType, true);
            //  if (silvering!=0) {}
            addTextureToAnimalTextureGrouping(hairOverlayGroup, FOX_TEXTURES_SILVERING, silvering, l -> l != 0);
            hairTexGroup.addGrouping(hairOverlayGroup);

            hairGroup.addGrouping(hairTexGroup);




            // DETAILS LAYER
            TextureGrouping detailGroup = new TextureGrouping(TexturingType.MERGE_GROUP); // add detail group as merge

            addTextureToAnimalTextureGrouping(detailGroup, TexturingType.APPLY_RGB, FOX_TEXTURES_NOSE[0], "nose", getNoseRGB(noseRGB));
            addTextureToAnimalTextureGrouping(detailGroup, TexturingType.APPLY_EYE_LEFT_COLOUR, FOX_TEXTURES_EYE_L, 0, l-> true);
            addTextureToAnimalTextureGrouping(detailGroup, TexturingType.APPLY_EYE_RIGHT_COLOUR, FOX_TEXTURES_EYE_R, 0, l -> true);

            parentGroup.addGrouping(skinGroup); // SKIN added to PARENT
            parentGroup.addGrouping(hairGroup);  // hair added to parent
            parentGroup.addGrouping(detailGroup); // detail added to parent

            this.setTextureGrouping(parentGroup);  // finalizes texture grouping

            // skin group; also black or white markings that affect skin
            // red group (red base coat)
            // black group (black coat, black markings i.e. smokey, socks, etc)
            // white group (white markings)
            // hair group (fur texture overlay)
            // detail group (eyes, nose, paws)

        }
    }

    private Integer getNoseRGB(int noseRGB) {
        return switch (noseRGB) {
            case 1 -> Colouration.HSBtoARGB(0.0F, 0.051F, 0.030F);
            case 2 -> Colouration.HSBtoARGB(0.0F, 0.439F, 0.594F);
            case 3 -> Colouration.HSBtoARGB(0.987F, 0.404F, 0.861F);
            case 4 -> Colouration.HSBtoARGB(0.02F, 0.321F, 0.231F);

            default -> Colouration.HSBtoARGB(0.0F, 0.429F, 0.494F);
        };
    }

//
//    @OnlyIn(Dist.CLIENT)
//    protected void setTexturePaths_BU() {
//        if (this.getSharedGenes() != null) {
//            int[] gene = getSharedGenes().getAutosomalGenes();
//
//            // COLORATION
//            // hue, saturation, brightness
//
//            float maxRed = 0.020F;
//            float maxYellow = 0.101F;
//
//            //    float[] swallowbellyColor = {0,0,0};
//
//            float[] eyeColor = { 0.075F, 0.70F, 0.675F };  //0.45F, 0.85F, 0.46F , 0.205F, 0.61F, 0.675F
//
//
//            float[] melanin = {0.036F, 0.5F, 0.071F};
//            float[] blackTintColor = { 0.072F, 0.22F, 0.071F };
//
//            float[] pheomelanin = { 0.049F, 0.683F, 0.558F };
//            float[] redTintColor = {  0.048F, 0.623F, 0.628F };
//
//            float[] redUnderbelly = { pheomelanin[0], pheomelanin[1]-0.1F, pheomelanin[2]+0.1F };
//            float[] redPatternColor = {  0.048F, 0.623F, 0.628F };
//
//
//            int burgundy = 0;
//
//            int noseRGB = 1;  // default black
//
//            int black = 0;
//            int shading = 0;
//
//            int extension = 0;
//            int agouti = 0;
//            //        boolean agouti = true;
//            int agoutiBase = 1;
//
//            int basecoat = 0;
//
//            int skin = 0;
//            int skinBlack = 3;
//
//            int underbelly = 0;
//
//            int blacksocks = 1;
//
//
//            int coat_alpha = 0;
//            int coat_texture = 0;
//            int coatType = 1;
//
//            int white = 0;
//
//            int marble = 0;
//            int georgianwhite = 0;
//            int whitemark = 0;
//            int platinum = 0;
//            int ringneck = 0;
//
//            int silvering = 0;
//
//            //    int swallowbelly = 0;
//
//            int redagouti = IDX_BASECOATS_AGOUTI;
//
//            int redUnderbellyRGB = Colouration.HSBtoARGB(redUnderbelly[0], redUnderbelly[1], redUnderbelly[2]);
//
//
//            // BOOLEANS ARE YES/NO or ON/OFF - two values!
//            boolean wideband = false;
//
//            boolean agoutiBlack = false;
//
//
//            char[] uuidArry = getStringUUID().toCharArray();
//
//            /*
//             * RANDOM TEXTURES
//             *
//             * UUID Spaces used:
//             * [0] - Gender?
//             * [1] - Marble
//             * [2] - Whitemark
//             * [3] - Platinum
//             * [4] - Ringneck
//             */
//
//
//            /**
//             * MC1R - Extension
//             *  1 : E dominant wildtype
//             *  2 : e recessive - more silver
//             *
//             * if (allele1 && allele2) {
//             *    //homozygous form
//             * }
//             * else if (allele1 || allele2) {
//             *   //heterozygous form
//             * }
//             *
//             *  gene 0, gene 1
//             *
//             */
//
//            //fox base coat - extension
//            if (gene[0] == 1 && gene[1] == 1) {
//                extension = 1;  // EE
//            } else if (gene[0] == 1 || gene[1] == 1) {
//                extension = 2;  // Ee
//            } else {
//                extension = 3;  // ee
//            }
//
//            /**
//             * ASIP - Agouti
//             *    1 : A dominant wildtype: red
//             *    2 : a recessive - more silver
//             *
//             *    gene 2, gene 3
//             */
//
//            //fox base coat - agouti
//            // if gene 2 = 1 (dom A) and gene 3 = 1 (dom A), then its homozygous
//            if (gene[2] == 1 && gene[3] == 1 ) {
//                agouti = 1;  // AA
//            } else if (gene[2] == 2 || gene[3] == 2 ) {   // heterozygous
//                agouti = 2;  // Aa
//            } else {
//                agouti = 3;  // aa
//            }
//
//            // 1 = homo Dom, 2 = het, 3 = homo Rec
//            if (extension == 1 && agouti == 1 ) {
//                basecoat = 1;  // AAEE - red wildtype          red1.png  RED
//                pheomelanin[0] += 0.021F;
//                pheomelanin[1] -= 0.07F;
//                melanin[0] += 0.016F;
//                melanin[1] -= 0.035F;
//                black = IDX_BLACKWT;
//
//            } else if (extension == 1 && agouti == 2) {
//                basecoat = 2;  // AAEe - gold                  gold1.png  GOLD / SMOKY RED
//                black = IDX_SMOKY;
//
//            } else if (extension == 1 && agouti == 3) {
//                basecoat = 3;  // AAee - standard silver       silver2.png  STANDARD SILVER
//                black = 1;
//
//            } else if (extension == 2 && agouti == 1) {
//                basecoat = 4;  // AaEE - alaskan cross         cross1.png  ALASKAN / GOLDEN CROSS
//
//            } else if (extension == 2 && agouti == 2) {
//                basecoat = 5;  // AaEe - blended cross         cross2.png  BLENDED / SILVER CROSS
//
//            } else if (extension == 2 && agouti == 3) {
//                basecoat = 6;  // Aaee - sub-standard silver   silver2.png  SUB-STANDARD SILVER
//                black = 1;
//
//            } else if (extension == 3 && agouti == 1) {
//                basecoat = 7;  // aaEE - alaskan silver        silver1.png  ALASKAN SILVER
//                black = 1;
//
//            } else if (extension == 3 && agouti == 2) {
//                basecoat = 8;  // aaEe - sub-alaskan silver    silver1.png  SUB-ALASKAN SILVER
//                black = 1;
//
//            } else {
//                basecoat = 9;  // aaee - double silver         silver3.png  DOUBLE SILVER
//                black = 1;
//
//            }
//
//
//            if (gene[164] == 2 && gene[165] == 2) {
//                wideband = true;
//            }
//
//            // BURGUNDY - recessive, affects black pigment
//            // hue sat bright
//            if (gene[20] == 2 && gene[21] == 2){  // homo rec
//                melanin[0] -= 0.5F;
//                melanin[1] += 1.0F;
//                melanin[2] += 2.5F;
//            }
//
//
//            /**
//             * SILVER GUARD HAIRS
//             *    gene 4 : wildtype: no silvering
//             *    gene 5 : more silvering
//             */
//            //basic silvering - need default to be no silvering
//            // should be:  SS = no silver , Ss = faded silver , ss = full silver
//
//            if (gene[4] == 2 && gene[5] == 2){  // SS?
//                silvering = 1;  //
//            } else if (gene[4] == 1 && gene[5] == 2) {  // Ss?
//                silvering = 2;  //
//            }
//
//            // Marble - incomplete dominant?
//            if (gene[6] == 2 && gene[7] == 2){  // homo
//                marble = IDX_MARBLE_2;   // 3
//            } else if (gene[6] == 2 || gene[7] == 2) {  // het
//                marble = IDX_MARBLE_1;   // 1
//            }
//
//            int randMarble = uuidArry[1];
//
//            // 1 blank, 2 het, 2 homo
//            switch (marble) {
//                case IDX_MARBLE_1:
//                    marble = IDX_MARBLE_1 + (randMarble % 2);   // het
//                    break;
//                case IDX_MARBLE_2:
//                    marble = IDX_MARBLE_2 + (randMarble % 2);   // homo
//                    break;
//            }
//
//            // Whitemark - dominant
//            if (gene[8] == 2 && gene[9] == 2){  // homo WW
//                whitemark = IDX_WHITEMARK_2;   // 3
//            } else if (gene[8] == 2 || gene[9] == 2) {  // het Ww
//                whitemark = IDX_WHITEMARK_1;   // 1
//            }
//
//            int randWhitemark = uuidArry[2];
//
//            // 1 blank, 3 het, 3 homo
//            switch (whitemark) {
//                case IDX_WHITEMARK_1:
//                    whitemark = IDX_WHITEMARK_1 + (randWhitemark % 3);   // het
//                    break;
//                case IDX_WHITEMARK_2:
//                    whitemark = IDX_WHITEMARK_2 + (randWhitemark % 3);   // homo
//                    break;
//            }
//
//            // georgian white  - het expressed, homo lethal
//            if (gene[10] == 2 || gene[11] == 2){  // gwgw
//                georgianwhite = 1;
//            }
//
//            //blueish  = 0.078F, 0.623F, 0.798F
//
//            // Platinum - incomplete dominant
//            if (gene[12] == 2 || gene[13] == 2){
//                platinum = idxPlatinum1;
//                eyeColor[0] = 0.60F;  // hue
//                eyeColor[1] -= 0.08F;  // sat
//                eyeColor[2] += 0.45F;  // lightness .22
//                noseRGB = 3;
//            }
//
//            int randPlatinum = uuidArry[3];
//
//            // 1 blank, 5 het
//            switch (platinum) {
//                case idxPlatinum1:
//                    platinum = idxPlatinum1 + (randPlatinum % 5);
//                    break;
//            }
//
//
//            // Ringneck - incomplete dominant?
//            if (gene[14] == 2 && gene[15] == 2){  // homo
//                ringneck = IDX_RINGNECK_2;   // 3
//            } else if (gene[14] == 2 || gene[15] == 2) {  // het
//                ringneck = IDX_RINGNECK_1;   // 1
//            }
//
//            int randRingneck = uuidArry[4];
//
//            // 1 blank, 4 het, 3 homo
//            switch (ringneck) {
//                case IDX_RINGNECK_1:
//                    ringneck = IDX_RINGNECK_1 + (randRingneck % 4);   // het
//                    break;
//                case IDX_RINGNECK_2:
//                    ringneck = IDX_RINGNECK_2 + (randRingneck % 3);   // homo
//                    break;
//            }
//
//            // black socks
//            if (gene[16] == 2 && gene[17] == 2){  //
//                blacksocks = 0;
//            }
//
//
//            //  Dilute/Blue TEST - both need to =2 to be dilute
//            if (gene[28]==2 && gene[29]==2) {
//                eyeColor[0] = 0.23F;  // hue
//                melanin[1] -= 0.35F;  //sat  1.35F
//                melanin[2] += 0.16F;  //light  1.16F
//                pheomelanin[0] += 0.010F;  //hue
//                pheomelanin[1] -= 0.25F;  //sat  1.25F
//                pheomelanin[2] += 0.52F;  //light .12  1.52F
//                noseRGB = 3;
//            }
//
//
//
//            if (extension==1 && agouti==2) {  // gold foxes TEST
//                eyeColor[0] = 0.10F;  // hue
//                eyeColor[1] += 0.310F;  // sat
//                eyeColor[2] -= 0.32F;  // lightness .22
//
//            }
//
//
//            // COLOR saturation etc TESTING PLACEHOLDER - code from pigs
//
//            int s = 0;
//
//            for (int i = 192; i < 202; i++) {
//                if (gene[i] == 2) {
//                    s -= 1;
//                }
//            }
//
//            //wideband increases saturation - higher opacity texture. lightens /reddens/yellows black patterning too
//            if (wideband) {
//                s += 5;
//            }
//
//            float saturation = s*0.12F;
//
//            int r = 0;
//            for (int i = 120; i < 148; i++) {
//                if (gene[i] == 2) {
//                    r = i < 134 ? r-1 : r+1;
//                }
//            }
//
//            int darkness = 0;
//            for (int i = 150; i < 158; i++) {
//                if (gene[i] == 2) {
//                    darkness += 1;
//                }
//            }
//
//            if (s != 0) {
//                if (saturation < 0) {
//                    maxYellow += (0.0016F * saturation);
//                    //pheomelanin[0] += (0.002F * darkness);
//                }
//                pheomelanin[1] += (0.02F * saturation);
//                melanin[1] += (0.005F * saturation);
//            }
//
//            if (r != 0) {
//                pheomelanin[0] += (0.0029F * -r);
//                pheomelanin[2] += (0.008F * -r);
//                melanin[0] += (0.0025F * -r);
//                melanin[1] += (0.009F * r);
//            }
//
//            if (darkness != 0) {
//                melanin[2] -= (0.01F * darkness);
//                pheomelanin[0] -= (0.002F * darkness);
//                pheomelanin[1] += (0.0015F * darkness);
//                pheomelanin[2] -= (0.03F * darkness);
//                maxYellow -= (0.0021F * darkness);
//            }
//
//            //    if (wideband) {
//            //        swallowbellyColor[0] = pheomelanin[0] + 0.01F;
//            //        swallowbellyColor[1] = pheomelanin[1] - 0.02F;
//            //        swallowbellyColor[2] = pheomelanin[2] + 0.09F;
//            //    } else {
//            //        swallowbellyColor[0] = pheomelanin[0] + 0.02F;
//            //        swallowbellyColor[1] = pheomelanin[1] - 0.15F;
//            //        swallowbellyColor[2] = pheomelanin[2] + 0.23F;
//            //    }
//
//            float[] lightAgoutiColor = {pheomelanin[0], pheomelanin[1], pheomelanin[2]+0.25F};
//            float[] darkAgoutiColor = {melanin[0], melanin[1], melanin[2]-0.1F};
//            float[] darkAgoutiRedColor = {pheomelanin[0], pheomelanin[1]+0.04F, pheomelanin[2]-0.25F};
//
//            if (gene[8] == 2 && gene[9] == 2) {
//                darkAgoutiColor[2] -= 0.4F;
//            }
//            else if (gene[8] == 2 || gene[9] == 2) {
//                darkAgoutiColor[2] -= 0.3F;
//            }
//
//            if (this.isBaby()) {
//                if (agoutiBlack && gene[2] == 4 && gene[3] == 4) {
//                    melanin[1] -= 0.5F;
//                    melanin[2] += 0.5F;
//                }
//                //    swallowbellyColor[1] -= 0.5F;
//            }
//
//
//            //check hue range
//            if (pheomelanin[0] > maxYellow) {
//                pheomelanin[0] = maxYellow;
//            } else if (pheomelanin[0] < maxRed) {
//                pheomelanin[0] = maxRed;
//            }
//
//            if (melanin[0] > maxYellow) {
//                melanin[0] = maxYellow;
//            } else if (melanin[0] < maxRed) {
//                melanin[0] = maxRed;
//            }
//
//            //    if (swallowbellyColor[0] > maxYellow) {
//            //        swallowbellyColor[0] = maxYellow;
//            //    } else if (swallowbellyColor[0] < maxRed) {
//            //        swallowbellyColor[0] = maxRed;
//            //    }
//
//            if (darkAgoutiColor[0] > maxYellow) {
//                darkAgoutiColor[0] = maxYellow;
//            } else if (darkAgoutiColor[0] < maxRed) {
//                darkAgoutiColor[0] = maxRed;
//            }
//
//            if (lightAgoutiColor[0] > maxYellow) {
//                lightAgoutiColor[0] = maxYellow;
//            } else if (lightAgoutiColor[0] < maxRed) {
//                lightAgoutiColor[0] = maxRed;
//            }
//
//            if (darkAgoutiRedColor[0] > maxYellow) {
//                darkAgoutiRedColor[0] = maxYellow;
//            } else if (darkAgoutiRedColor[0] < maxRed) {
//                darkAgoutiRedColor[0] = maxRed;
//            }
//
//            //checks that numbers are within the valid range
//            for (int i = 0; i <= 2; i++) {
//                if (melanin[i] > 1.0F) {
//                    melanin[i] = 1.0F;
//                } else if (melanin[i] < 0.0F) {
//                    melanin[i] = 0.0F;
//                }
//                if (pheomelanin[i] > 1.0F) {
//                    pheomelanin[i] = 1.0F;
//                } else if (pheomelanin[i] < 0.0F) {
//                    pheomelanin[i] = 0.0F;
//                }
//                //    if (swallowbellyColor[i] > 1.0F) {
//                //        swallowbellyColor[i] = 1.0F;
//                //    } else if (swallowbellyColor[i] < 0.0F) {
//                //        swallowbellyColor[i] = 0.0F;
//                //    }
//                if (lightAgoutiColor[i] > 1.0F) {
//                    lightAgoutiColor[i] = 1.0F;
//                } else if (lightAgoutiColor[i] < 0.0F) {
//                    lightAgoutiColor[i] = 0.0F;
//                }
//                if (darkAgoutiColor[i] > 1.0F) {
//                    darkAgoutiColor[i] = 1.0F;
//                } else if (darkAgoutiColor[i] < 0.0F) {
//                    darkAgoutiColor[i] = 0.0F;
//                }
//                if (darkAgoutiRedColor[i] > 1.0F) {
//                    darkAgoutiRedColor[i] = 1.0F;
//                } else if (darkAgoutiRedColor[i] < 0.0F) {
//                    darkAgoutiRedColor[i] = 0.0F;
//                }
//            }
//
//
//
//            int eyeHue = 0; // negative = orange, positive = green
//            for (int i = 50; i < 60; i++) {
//                if (gene[i] == 2) {
//                    eyeHue--;
//                }
//                else if (gene[i] == 3) {
//                    eyeHue++;
//                }
//            }
//
//            int eyeLightness = 0;
//            for (int i = 60; i < 72; i++) {
//                if (gene[i] == 2) {
//                    eyeLightness += i < 66 ? 1 : -1;
//                }
//            }
//
//            int eyeSaturation = 0;
//            for (int i = 72; i < 80; i++) {
//                if (gene[i] == 2) {
//                    eyeSaturation += 1;
//                }
//            }
//
//            eyeColor[0] += eyeHue*0.0130F;
//
//            eyeColor[1] += eyeSaturation*0.025F;
//
//            eyeColor[1] -= eyeLightness*0.005F;
//            eyeColor[2] += eyeLightness*0.0225F;
//
//
//            clampRGB(eyeColor, false);
//
//            clampRGB(melanin);
//            clampRGB(pheomelanin);
//
//            clampRGB(blackTintColor);
//            clampRGB(redTintColor);
//
//            clampRGB(redUnderbelly); // testing
//            //    clampRGB(blackUnderbelly);
//
//
//            int leftEyeRGB = Colouration.HSBtoARGB(eyeColor[0], eyeColor[1], eyeColor[2]);
//            int rightEyeRGB = Colouration.HSBtoARGB(eyeColor[0], eyeColor[1], eyeColor[2]);
//
//            int melaninRGB = Colouration.HSBtoABGR(melanin[0], melanin[1], melanin[2]);
//            int pheomelaninRGB = Colouration.HSBtoABGR(pheomelanin[0], pheomelanin[1], pheomelanin[2]);
//
//            int blackTintRGB = Colouration.HSBtoARGB(blackTintColor[0], blackTintColor[1], blackTintColor[2]);
//            int redTintRGB = Colouration.HSBtoARGB(redTintColor[0], redTintColor[1], redTintColor[2]);
//
//            int whiteRGB = Colouration.HSBtoARGB(0.13F, 0.02F, 0.96F);
//
//            int redPatternRGB = Colouration.HSBtoARGB(redPatternColor[0], redPatternColor[1], redPatternColor[2]); // testing
//
//
//            // testing PLACEHOLDER
//            //    int swallowbellyRGB = Colouration.HSBtoARGB(swallowbellyColor[0], swallowbellyColor[1], swallowbellyColor[2]);
//            int lightAgoutiRGB = Colouration.HSBtoARGB(lightAgoutiColor[0], lightAgoutiColor[1], lightAgoutiColor[2]);
//            int darkAgoutiRGB = Colouration.HSBtoARGB(darkAgoutiColor[0], darkAgoutiColor[1], darkAgoutiColor[2]);
//            int darkAgoutiRedRGB = Colouration.HSBtoARGB(darkAgoutiRedColor[0], darkAgoutiRedColor[1], darkAgoutiRedColor[2]);
//
//
//            // CAT NOSE COLOR example; change later! cats colors as test
//            // Colouration.HSBtoARGB(0.13F [HUE], 0.02F [SATURATION], 0.96F [BRIGHTNESS]);
//            // int whiteRGB = Colouration.HSBtoARGB(0.13F, 0.02F, 0.96F);
//            // hue, saturation, brightness
//            int[] noseColors = {
//                    //Brick 0
//                    Colouration.HSBtoARGB(0.0F, 0.429F, 0.494F),
//                    //Black 1
//                    Colouration.HSBtoARGB(0.0F, 0.051F, 0.030F),
//                    //Light Brick 2
//                    Colouration.HSBtoARGB(0.0F, 0.439F, 0.594F),
//                    //Pink 3
//                    Colouration.HSBtoARGB(0.987F, 0.404F, 0.861F),
//                    //Brown 4
//                    Colouration.HSBtoARGB(0.02F, 0.321F, 0.231F),
//            };
//
//            this.colouration.setLeftEyeColour(leftEyeRGB);
//            this.colouration.setRightEyeColour(rightEyeRGB);
//
//            this.colouration.setMelaninColour(melaninRGB);
//            this.colouration.setPheomelaninColour(pheomelaninRGB);
//
//
//
//            // TEXTURE GROUPING
//
//            // merge, alpha, mask
//            // groups: parent (add everything at end), skin, hair, foundation, eyes and paws at end in parent
//
//
//            TextureGrouping parentGroup = new TextureGrouping(TexturingType.MERGE_GROUP);  // create PARENT
//            TextureGrouping skinGroup = new TextureGrouping(TexturingType.MERGE_GROUP);  // create SKIN
//
//            // SKIN GROUPS
//            if (black != IDX_BLACK_SOLID) { // change to 0
//                TextureGrouping skinRedGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
//                addTextureToAnimalTextureGrouping(skinRedGroup, FOX_TEXTURES_SKINBASE, 1, l -> true);
//                skinGroup.addGrouping(skinRedGroup);
//            }
//            if (black != 0) {
//                TextureGrouping skinBlackGroup = new TextureGrouping(TexturingType.MASK_GROUP);
//                TextureGrouping skinBlackMaskGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
//                addTextureToAnimalTextureGrouping(skinBlackMaskGroup, FOX_TEXTURES_BLACK, black, true);
//                skinBlackGroup.addGrouping(skinBlackMaskGroup);
//                TextureGrouping skinBlackBaseGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
//                addTextureToAnimalTextureGrouping(skinBlackBaseGroup, FOX_TEXTURES_SKINBASE, 1, true); // former value 2
//                skinBlackGroup.addGrouping(skinBlackBaseGroup);
//                if (agouti != 0) {
//                    TextureGrouping skinBlackUnderbellyGroup = new TextureGrouping(TexturingType.MASK_GROUP);
//                    addTextureToAnimalTextureGrouping(skinBlackUnderbellyGroup, FOX_TEXTURES_UNDERBELLY, 1, true);
//                    addTextureToAnimalTextureGrouping(skinBlackUnderbellyGroup, FOX_TEXTURES_SKINBASE, 0, true);
//                    skinBlackGroup.addGrouping(skinBlackUnderbellyGroup);
//                }
//                skinGroup.addGrouping(skinBlackGroup);
//            }
//            if (white != 0) {   // adds white texture to skin
//                TextureGrouping skinWhiteGroup = new TextureGrouping(TexturingType.MASK_GROUP);
//                TextureGrouping whiteMaskGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
//                addTextureToAnimalTextureGrouping(whiteMaskGroup, FOX_TEXTURES_PLATINUM, platinum, l -> l != 0);
//                addTextureToAnimalTextureGrouping(whiteMaskGroup, FOX_TEXTURES_MARBLE, marble, l -> l != 0);
//                addTextureToAnimalTextureGrouping(whiteMaskGroup, FOX_TEXTURES_GEORGIANWHITE, georgianwhite, l -> l != 0);
//                addTextureToAnimalTextureGrouping(whiteMaskGroup, FOX_TEXTURES_WHITEMARK, whitemark, l -> l != 0);
//                addTextureToAnimalTextureGrouping(whiteMaskGroup, FOX_TEXTURES_RINGNECK, ringneck, l -> l != 0);
//                skinWhiteGroup.addGrouping(whiteMaskGroup);
//                TextureGrouping whiteTexGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
//                addTextureToAnimalTextureGrouping(whiteTexGroup, FOX_TEXTURES_SKINBASE, 0, l -> true);
//                skinWhiteGroup.addGrouping(whiteTexGroup);
//                skinGroup.addGrouping(skinWhiteGroup);
//            }
//
//            parentGroup.addGrouping(skinGroup); // SKIN added to PARENT
//
//            // HAIR ALPHAS AND TEXTURES HERE
//
//
//            //  FUR TEXTURES
//
//            TextureGrouping hairGroup = new TextureGrouping(TexturingType.MASK_GROUP);     // create HAIR
//            //        TextureGrouping hairAlphaGroup = new TextureGrouping(TexturingType.MERGE_GROUP); // create HAIR ALPHA
//            //        addTextureToAnimalTextureGrouping(hairAlphaGroup, FOX_TEXTURES_FUR, coat_alpha, coat_alpha != 0);  // add fur tex as alpha
//
//
//            //    hairGroup.addGrouping(hairAlphaGroup);
//            TextureGrouping hairTexGroup = new TextureGrouping(TexturingType.MERGE_GROUP); // create HAIR TEX - that red and black groups go into
//
//
//            // RED LAYER
//            if (extension != 0 || agouti != 0) {
//                TextureGrouping redGroup = new TextureGrouping(TexturingType.MASK_GROUP);  // create REDGROUP
//                TextureGrouping redBaseGroup = new TextureGrouping(TexturingType.MERGE_GROUP); // create REDBASE
//                addTextureToAnimalTextureGrouping(redBaseGroup, TexturingType.APPLY_RED, FOX_TEXTURES_BASE, 0, l -> true); // base texture red
//
//                // agouti mask (TEST)
//                TextureGrouping redPatternGroup = new TextureGrouping(TexturingType.MASK_GROUP);
//                TextureGrouping agoutiMaskGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
//                addTextureToAnimalTextureGrouping(agoutiMaskGroup, FOX_TEXTURES_TEST, agouti != 0 ? 1 : 0, l -> true); // TEST agouti mask
//                redPatternGroup.addGrouping(agoutiMaskGroup); // add Mask to Pattern
//
//                // AGOUTI TEXTURE
//                TextureGrouping agoutiTexGroup = new TextureGrouping(TexturingType.MASK_GROUP);
//
//                //    basecoat = 1;  // AAEE - RED wildtype
//                // 1 = homo Dom, 2 = het, 3 = homo Rec
//                if (extension == 1 && agouti == 1 ) {
//                    basecoat = 1;  // AAEE - RED wildtype
//                    TextureGrouping redWTGroup = new TextureGrouping(TexturingType.MERGE_GROUP); // creates redWTG
//                    //    addTextureToAnimalTextureGrouping(redWTGroup, FOX_TEXTURES_AGOUTI, redagouti, l -> l != 0);
//                    //    addTextureToAnimalTextureGrouping(redWTGroup, TexturingType.APPLY_RED, FOX_TEXTURES_AGOUTI, redagouti, l -> true);
//                    addTextureToAnimalTextureGrouping(redWTGroup, TexturingType.APPLY_RED, FOX_TEXTURES_AGOUTI,  0, l -> true);
//                    agoutiTexGroup.addGrouping(redWTGroup); // add WTG to red group
//
//                } else if (extension == 1 && agouti == 2) {
//                    // AAEe - GOLD / SMOKY RED
//                    //    TextureGrouping redSmokyGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
//                    //    addTextureToAnimalTextureGrouping(redBaseGroup, TexturingType.APPLY_BLACK, FOX_TEXTURES_SMOKY, 1, l -> true);
//                    //    agoutiTexGroup.addGrouping(redSmokyGroup); // add smoky to red
//
//                } else if (extension == 1 && agouti == 3) {
//                    // AAee - STANDARD SILVER
//                    //    TextureGrouping redStandardGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
//                    //    addTextureToAnimalTextureGrouping(redBaseGroup, TexturingType.APPLY_BLACK, FOX_TEXTURES_AGOUTI, 2, l -> true);
//                    //    agoutiTexGroup.addGrouping(redStandardGroup); // add standard to red
//
//                } else if (extension == 2 && agouti == 1) {
//                    basecoat = 4;  // AaEE - ALASKAN / GOLDEN CROSS
//                    TextureGrouping redACrossGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
//                    addTextureToAnimalTextureGrouping(redACrossGroup, FOX_TEXTURES_AGOUTI, 1, true);
//                    agoutiTexGroup.addGrouping(redACrossGroup); // add alaskan cross to red group
//
//                } else if (extension == 2 && agouti == 2) {
//                    basecoat = 5;  // AaEe - BLENDED / SILVER CROSS
//                    TextureGrouping redBlendGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
//                    addTextureToAnimalTextureGrouping(redBlendGroup, FOX_TEXTURES_AGOUTI, 2, true);
//                    agoutiTexGroup.addGrouping(redBlendGroup); // add blended cross to red group
//
//                } else if (extension == 2 && agouti == 3) {
//                    basecoat = 6;  // Aaee - SUB-STANDARD SILVER
//                    //    TextureGrouping redSubstandardGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
//                    //    addTextureToAnimalTextureGrouping(redSubstandardGroup, FOX_TEXTURES_AGOUTI, 3, true);
//                    //    agoutiTexGroup.addGrouping(redSubstandardGroup); // add subStandard to red group
//
//                } else if (extension == 3 && agouti == 1) {
//                    basecoat = 7;  // aaEE - ALASKAN SILVER
//                    //    TextureGrouping redAlaskanGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
//                    //   addTextureToAnimalTextureGrouping(redAlaskanGroup, FOX_TEXTURES_AGOUTI, 3, true);
//                    //    agoutiTexGroup.addGrouping(redAlaskanGroup); // add Alaskan to red group
//
//                } else if (extension == 3 && agouti == 2) {
//                    basecoat = 8;  // aaEe - SUB-ALASKAN SILVER
//                    //    TextureGrouping redSubalaskanGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
//                    //    addTextureToAnimalTextureGrouping(redSubalaskanGroup, FOX_TEXTURES_AGOUTI, 4, true);
//                    //    agoutiTexGroup.addGrouping(redSubalaskanGroup); // add subAlaskan to red group
//
//                } else {
//                    basecoat = 9;  // aaee - DOUBLE SILVER
//                    //    TextureGrouping redDoubleGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
//                    //    addTextureToAnimalTextureGrouping(redDoubleGroup, FOX_TEXTURES_BLACK, black, true);
//                    //    agoutiTexGroup.addGrouping(redDoubleGroup); // add double to red group
//                }
//
//
//                TextureGrouping agoutiInnerMaskGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
//                addTextureToAnimalTextureGrouping(agoutiInnerMaskGroup, FOX_TEXTURES_BLACK_FUR[1], true); //  ear deets
//
//                agoutiTexGroup.addGrouping(agoutiInnerMaskGroup);
//
//                hairTexGroup.addGrouping(redBaseGroup);
//                hairTexGroup.addGrouping(agoutiTexGroup);
//            }
//
//
//
//            //BLACK LAYER
//            // should have textures / rgb changes that apply only to black areas
//            if (black != 0) {
//                TextureGrouping blackGroup = new TextureGrouping(TexturingType.MASK_GROUP);
//                TextureGrouping blackAlphaGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
//                addTextureToAnimalTextureGrouping(blackAlphaGroup, TexturingType.APPLY_BLACK, FOX_TEXTURES_BLACK, black, l -> l != 0); // hmm
//                blackGroup.addGrouping(blackAlphaGroup);
//
//                TextureGrouping blackTexGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
//                TextureGrouping blackBaseGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
//                addTextureToAnimalTextureGrouping(blackBaseGroup, TexturingType.APPLY_BLACK, FOX_TEXTURES_BASE, 0, l -> true); // makes base texture black - may not need this? check
//
//                // 1 = homo Dom, 2 = het, 3 = homo Rec
//                if (extension == 1 && agouti == 1 ) {
//                    basecoat = 1;  // AAEE - RED wildtype
//                    TextureGrouping blackWTGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
//                    //    addTextureToAnimalTextureGrouping(blackBaseGroup, TexturingType.APPLY_BLACK, FOX_TEXTURES_BLACK_SOCKS, blacksocks, l -> true); // should maybe be RBG instead? for dilutions etc
//                    //    addTextureToAnimalTextureGrouping(blackBaseGroup, FOX_TEXTURES_BLACK_SOCKS, blacksocks, l -> l != 0);
//
//                    blackTexGroup.addGrouping(blackWTGroup); // add to black group
//                    // black socks, wildtype amount of black
//
//                } else if (extension == 1 && agouti == 2) {
//                    // AAEe - GOLD / SMOKY RED
//                    TextureGrouping blackSmokyGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
//                    addTextureToAnimalTextureGrouping(blackBaseGroup, TexturingType.APPLY_BLACK, FOX_TEXTURES_SMOKY, 1, l -> true);
//                    blackTexGroup.addGrouping(blackSmokyGroup); // add smoky to black
//
//                } else if (extension == 1 && agouti == 3) {
//                    // AAee - STANDARD SILVER
//                    TextureGrouping blackStandardGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
//                    addTextureToAnimalTextureGrouping(blackBaseGroup, TexturingType.APPLY_BLACK, FOX_TEXTURES_AGOUTI, 2, l -> true);
//                    blackTexGroup.addGrouping(blackStandardGroup); // add standard to black
//
//                } else if (extension == 2 && agouti == 1) {
//                    basecoat = 4;  // AaEE - ALASKAN / GOLDEN CROSS
//                    TextureGrouping blackACrossGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
//                    addTextureToAnimalTextureGrouping(blackACrossGroup, FOX_TEXTURES_AGOUTI, 1, true);
//                    blackTexGroup.addGrouping(blackACrossGroup); // add alaskan cross to black group
//
//                } else if (extension == 2 && agouti == 2) {
//                    basecoat = 5;  // AaEe - BLENDED / SILVER CROSS
//                    TextureGrouping blackBlendGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
//                    addTextureToAnimalTextureGrouping(blackBlendGroup, FOX_TEXTURES_AGOUTI, 2, true);
//                    blackTexGroup.addGrouping(blackBlendGroup); // add blended cross to black group
//
//                } else if (extension == 2 && agouti == 3) {
//                    basecoat = 6;  // Aaee - SUB-STANDARD SILVER
//                    TextureGrouping blackSubstandardGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
//                    addTextureToAnimalTextureGrouping(blackSubstandardGroup, FOX_TEXTURES_AGOUTI, 3, true);
//                    blackTexGroup.addGrouping(blackSubstandardGroup); // add subStandard to black group
//
//                } else if (extension == 3 && agouti == 1) {
//                    basecoat = 7;  // aaEE - ALASKAN SILVER - should have brownish tint along sides and ears
//                    TextureGrouping blackAlaskanGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
//                    addTextureToAnimalTextureGrouping(blackAlaskanGroup, FOX_TEXTURES_AGOUTI, 3, true);
//                    blackTexGroup.addGrouping(blackAlaskanGroup); // add Alaskan to black group
//
//                } else if (extension == 3 && agouti == 2) {
//                    basecoat = 8;  // aaEe - SUB-ALASKAN SILVER
//                    TextureGrouping blackSubalaskanGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
//                    addTextureToAnimalTextureGrouping(blackSubalaskanGroup, FOX_TEXTURES_AGOUTI, 4, true);
//                    blackTexGroup.addGrouping(blackSubalaskanGroup); // add subAlaskan to black group
//
//                } else {
//                    basecoat = 9;  // aaee - DOUBLE SILVER  -- FOX_TEXTURES_AGOUTI texture 7
//                    TextureGrouping blackDoubleGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
//                    addTextureToAnimalTextureGrouping(blackDoubleGroup, FOX_TEXTURES_AGOUTI, 7, true);
//                    //    addTextureToAnimalTextureGrouping(blackDoubleGroup, FOX_TEXTURES_BLACK, black, true);
//                    blackTexGroup.addGrouping(blackDoubleGroup); // add double to black group
//                }
//
//                blackGroup.addGrouping(blackTexGroup);
//                hairTexGroup.addGrouping(blackGroup); // add black to hair
//            }
//
//
//            //WHITE LAYER (this seems to work)
//            if (georgianwhite != 0 || marble != 0 || whitemark != 0 || platinum != 0 || ringneck != 0) {
//                TextureGrouping hairWhiteGroup = new TextureGrouping(TexturingType.MASK_GROUP);  // create white mask group
//                TextureGrouping whiteMaskGroup = new TextureGrouping(TexturingType.MERGE_GROUP);  // create white inner mask group
//
//                addTextureToAnimalTextureGrouping(whiteMaskGroup, FOX_TEXTURES_PLATINUM, platinum, l -> l != 0);
//                addTextureToAnimalTextureGrouping(whiteMaskGroup, FOX_TEXTURES_MARBLE, marble, l -> l != 0);
//                addTextureToAnimalTextureGrouping(whiteMaskGroup, FOX_TEXTURES_GEORGIANWHITE, georgianwhite, l -> l != 0);
//                addTextureToAnimalTextureGrouping(whiteMaskGroup, FOX_TEXTURES_WHITEMARK, whitemark, l -> l != 0);
//                addTextureToAnimalTextureGrouping(whiteMaskGroup, FOX_TEXTURES_RINGNECK, ringneck, l -> l != 0);
//
//                TextureGrouping whiteTextureGroup = new TextureGrouping(TexturingType.MERGE_GROUP); // create white texture group
//                addTextureToAnimalTextureGrouping(whiteTextureGroup, TexturingType.APPLY_RGB, FOX_TEXTURES_BASE[0], "w", whiteRGB); // affect with rgb? unsure if this works
//
//                hairWhiteGroup.addGrouping(whiteMaskGroup);  // add inner mask to hairwhite
//                hairWhiteGroup.addGrouping(whiteTextureGroup);  // add Texture Group to hairwhite
//                hairTexGroup.addGrouping(hairWhiteGroup);  // add hairwhite to hair tex
//            }
//
//            // UNDERBELLY
//            if (extension != 0 || agouti != 0) {
//                TextureGrouping bellyWhiteGroup = new TextureGrouping(TexturingType.MASK_GROUP);  // create white mask group
//                TextureGrouping whitebellyGroup = new TextureGrouping(TexturingType.MERGE_GROUP);  // create white inner mask group
//
//                addTextureToAnimalTextureGrouping(whitebellyGroup, FOX_TEXTURES_UNDERBELLY, 1, true);
//
//                TextureGrouping whiteTextureGroup = new TextureGrouping(TexturingType.MERGE_GROUP); // create white texture group
//                addTextureToAnimalTextureGrouping(whiteTextureGroup, TexturingType.APPLY_RGB, FOX_TEXTURES_BASE[0], "w", whiteRGB); // affect with rgb? unsure if this works
//
//                bellyWhiteGroup.addGrouping(whitebellyGroup);  // add inner mask to hairwhite
//                bellyWhiteGroup.addGrouping(whiteTextureGroup);  // add Texture Group to hairwhite
//                hairTexGroup.addGrouping(bellyWhiteGroup);  // add hairwhite to hair tex
//            }
//
//            // HAIR OVERLAY
//            TextureGrouping hairOverlayGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
//            addTextureToAnimalTextureGrouping(hairOverlayGroup, FOX_TEXTURES_FUR, coatType, true);
//            //  if (silvering!=0) {}
//            addTextureToAnimalTextureGrouping(hairOverlayGroup, FOX_TEXTURES_SILVERING, silvering, l -> l != 0);
//            hairTexGroup.addGrouping(hairOverlayGroup);
//
//            hairGroup.addGrouping(hairTexGroup);
//
//
//            parentGroup.addGrouping(hairGroup);  // hair added to parent
//
//
//            // DETAILS LAYER
//            TextureGrouping detailGroup = new TextureGrouping(TexturingType.MERGE_GROUP); // add detail group as merge
//
//            addTextureToAnimalTextureGrouping(detailGroup, TexturingType.APPLY_RGB, FOX_TEXTURES_NOSE[0], "nose", noseColors[noseRGB]);
//            addTextureToAnimalTextureGrouping(detailGroup, TexturingType.APPLY_EYE_LEFT_COLOUR, FOX_TEXTURES_EYE_L, 0, l-> true);
//            addTextureToAnimalTextureGrouping(detailGroup, TexturingType.APPLY_EYE_RIGHT_COLOUR, FOX_TEXTURES_EYE_R, 0, l -> true);
//
//            parentGroup.addGrouping(detailGroup); // detail added to parent
//
//            this.setTextureGrouping(parentGroup);  // finalizes texture grouping
//
//            // skin group; also black or white markings that affect skin
//            // red group (red base coat)
//            // black group (black coat, black markings i.e. smokey, socks, etc)
//            // white group (white markings)
//            // hair group (fur texture overlay)
//            // detail group (eyes, nose, paws)
//
//        }
//    }

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
        this.colouration = super.getRgb();
        Genes genes = getGenes();

        if ( genes!= null) {
            if (this.colouration.getMelaninColour() == -1 || this.colouration.getPheomelaninColour() == -1 || this.colouration.getLeftEyeColour() == -1 || this.colouration.getRightEyeColour() == -1) {
                int[] aGene = genes.getAutosomalGenes();

                // CAT NOSE COLOR example; change later! cats colors as test
                // Colouration.HSBtoARGB(0.13F [HUE], 0.02F [SATURATION], 0.96F [BRIGHTNESS]);
                // int whiteRGB = Colouration.HSBtoARGB(0.13F, 0.02F, 0.96F);
                // hue, saturation, brightness


                // COLORATION
                // hue, saturation, brightness

                float maxRed = 0.020F;
                float maxYellow = 0.101F;

                float[] eyeColor = { 0.075F, 0.70F, 0.675F };  //0.45F, 0.85F, 0.46F , 0.205F, 0.61F, 0.675F


                float[] melanin = {0.036F, 0.5F, 0.071F};
                float[] blackTintColor = { 0.072F, 0.22F, 0.071F };

                float[] pheomelanin = { 0.049F, 0.683F, 0.558F };
                float[] redTintColor = {  0.048F, 0.623F, 0.628F };

                float[] redUnderbelly = { pheomelanin[0], pheomelanin[1]-0.1F, pheomelanin[2]+0.1F };
                float[] redPatternColor = {  0.048F, 0.623F, 0.628F };

                boolean agoutiBlack = false;

                int burgundy = 0;

                int redUnderbellyRGB = Colouration.HSBtoARGB(redUnderbelly[0], redUnderbelly[1], redUnderbelly[2]);
                
                // BURGUNDY - recessive, affects black pigment
                // hue sat bright
                if (aGene[20] == 2 && aGene[21] == 2){  // homo rec
                    melanin[0] -= 0.5F;
                    melanin[1] += 1.0F;
                    melanin[2] += 2.5F;
                }
                
                //blueish  = 0.078F, 0.623F, 0.798F

                // Platinum - incomplete dominant
                if (aGene[12] == 2 || aGene[13] == 2){
                    eyeColor[0] = 0.60F;  // hue
                    eyeColor[1] -= 0.08F;  // sat
                    eyeColor[2] += 0.45F;  // lightness .22
                }


                //  Dilute/Blue TEST - both need to =2 to be dilute
                if (aGene[18]==2 && aGene[19]==2) {
                    eyeColor[0] = 0.23F;  // hue
                    melanin[1] -= 0.35F;  //sat  1.35F
                    melanin[2] += 0.16F;  //light  1.16F
                    pheomelanin[0] += 0.010F;  //hue
                    pheomelanin[1] -= 0.25F;  //sat  1.25F
                    pheomelanin[2] += 0.52F;  //light .12  1.52F
                }

                if (aGene[0]!=2 && aGene[1]!=2 && aGene[2]!=aGene[3]) {  // gold foxes TEST
                    eyeColor[0] = 0.10F;  // hue
                    eyeColor[1] += 0.310F;  // sat
                    eyeColor[2] -= 0.32F;  // lightness .22

                }


                // COLOR saturation etc TESTING PLACEHOLDER - code from pigs

                int s = 0;

                for (int i = 94; i < 104; i++) {
                    if (aGene[i] == 2) {
                        s -= 1;
                    }
                }

                //wideband increases saturation - higher opacity texture. lightens /reddens/yellows black patterning too
                if (aGene[20] == 2 && aGene[21] == 2) {
                    s += 5;
                }

                float saturation = s*0.12F;

                int r = 0;
                for (int i = 58; i < 86; i++) {
                    if (aGene[i] == 2) {
                        r = i < 72 ? r-1 : r+1;
                    }
                }

                int darkness = 0;
                for (int i = 86; i < 94; i++) {
                    if (aGene[i] == 2) {
                        darkness += 1;
                    }
                }

                if (s != 0) {
                    if (saturation < 0) {
                        maxYellow += (0.0016F * saturation);
                        //pheomelanin[0] += (0.002F * darkness);
                    }
                    pheomelanin[1] += (0.02F * saturation);
                    melanin[1] += (0.005F * saturation);
                }

                if (r != 0) {
                    pheomelanin[0] += (0.0029F * -r);
                    pheomelanin[2] += (0.008F * -r);
                    melanin[0] += (0.0025F * -r);
                    melanin[1] += (0.009F * r);
                }

                if (darkness != 0) {
                    melanin[2] -= (0.01F * darkness);
                    pheomelanin[0] -= (0.002F * darkness);
                    pheomelanin[1] += (0.0015F * darkness);
                    pheomelanin[2] -= (0.03F * darkness);
                    maxYellow -= (0.0021F * darkness);
                }

                float[] lightAgoutiColor = {pheomelanin[0], pheomelanin[1], pheomelanin[2]+0.25F};
                float[] darkAgoutiColor = {melanin[0], melanin[1], melanin[2]-0.1F};
                float[] darkAgoutiRedColor = {pheomelanin[0], pheomelanin[1]+0.04F, pheomelanin[2]-0.25F};

                if (aGene[8] == 2 && aGene[9] == 2) {
                    darkAgoutiColor[2] -= 0.4F;
                }
                else if (aGene[8] == 2 || aGene[9] == 2) {
                    darkAgoutiColor[2] -= 0.3F;
                }

                if (this.isBaby()) {
                    if (agoutiBlack && aGene[2] == 4 && aGene[3] == 4) {
                        melanin[1] -= 0.5F;
                        melanin[2] += 0.5F;
                    }
                    //    swallowbellyColor[1] -= 0.5F;
                }


                //check hue range
                if (pheomelanin[0] > maxYellow) {
                    pheomelanin[0] = maxYellow;
                } else if (pheomelanin[0] < maxRed) {
                    pheomelanin[0] = maxRed;
                }

                if (melanin[0] > maxYellow) {
                    melanin[0] = maxYellow;
                } else if (melanin[0] < maxRed) {
                    melanin[0] = maxRed;
                }

                if (darkAgoutiColor[0] > maxYellow) {
                    darkAgoutiColor[0] = maxYellow;
                } else if (darkAgoutiColor[0] < maxRed) {
                    darkAgoutiColor[0] = maxRed;
                }

                if (lightAgoutiColor[0] > maxYellow) {
                    lightAgoutiColor[0] = maxYellow;
                } else if (lightAgoutiColor[0] < maxRed) {
                    lightAgoutiColor[0] = maxRed;
                }

                if (darkAgoutiRedColor[0] > maxYellow) {
                    darkAgoutiRedColor[0] = maxYellow;
                } else if (darkAgoutiRedColor[0] < maxRed) {
                    darkAgoutiRedColor[0] = maxRed;
                }

                //checks that numbers are within the valid range
                for (int i = 0; i <= 2; i++) {
                    if (melanin[i] > 1.0F) {
                        melanin[i] = 1.0F;
                    } else if (melanin[i] < 0.0F) {
                        melanin[i] = 0.0F;
                    }
                    if (pheomelanin[i] > 1.0F) {
                        pheomelanin[i] = 1.0F;
                    } else if (pheomelanin[i] < 0.0F) {
                        pheomelanin[i] = 0.0F;
                    }
                    //    if (swallowbellyColor[i] > 1.0F) {
                    //        swallowbellyColor[i] = 1.0F;
                    //    } else if (swallowbellyColor[i] < 0.0F) {
                    //        swallowbellyColor[i] = 0.0F;
                    //    }
                    if (lightAgoutiColor[i] > 1.0F) {
                        lightAgoutiColor[i] = 1.0F;
                    } else if (lightAgoutiColor[i] < 0.0F) {
                        lightAgoutiColor[i] = 0.0F;
                    }
                    if (darkAgoutiColor[i] > 1.0F) {
                        darkAgoutiColor[i] = 1.0F;
                    } else if (darkAgoutiColor[i] < 0.0F) {
                        darkAgoutiColor[i] = 0.0F;
                    }
                    if (darkAgoutiRedColor[i] > 1.0F) {
                        darkAgoutiRedColor[i] = 1.0F;
                    } else if (darkAgoutiRedColor[i] < 0.0F) {
                        darkAgoutiRedColor[i] = 0.0F;
                    }
                }



                int eyeHue = 0; // negative = orange, positive = green
                for (int i = 26; i < 36; i++) {
                    if (aGene[i] == 2) {
                        eyeHue--;
                    } else if (aGene[i] == 3) {
                        eyeHue++;
                    }
                }

                int eyeLightness = 0;
                for (int i = 36; i < 48; i++) {
                    if (aGene[i] == 2) {
                        eyeLightness += i < 42 ? 1 : -1;
                    }
                }

                int eyeSaturation = 0;
                for (int i = 48; i < 56; i++) {
                    if (aGene[i] == 2) {
                        eyeSaturation += 1;
                    }
                }

                eyeColor[0] += eyeHue*0.0130F;

                eyeColor[1] += eyeSaturation*0.025F;

                eyeColor[1] -= eyeLightness*0.005F;
                eyeColor[2] += eyeLightness*0.0225F;

                if (aGene[0]!=2 && aGene[1]!=2 && aGene[2]!=2 && aGene[3]!=2 ) {
                    pheomelanin[0] += 0.021F;
                    pheomelanin[1] -= 0.07F;
                    melanin[0] += 0.016F;
                    melanin[1] -= 0.035F;
                }

                clampRGB(eyeColor, false);

                clampRGB(melanin);
                clampRGB(pheomelanin);

                clampRGB(blackTintColor);
                clampRGB(redTintColor);

                clampRGB(redUnderbelly); // testing
                //    clampRGB(blackUnderbelly);


                int leftEyeRGB = Colouration.HSBtoARGB(eyeColor[0], eyeColor[1], eyeColor[2]);
                int rightEyeRGB = Colouration.HSBtoARGB(eyeColor[0], eyeColor[1], eyeColor[2]);

                int melaninRGB = Colouration.HSBtoABGR(melanin[0], melanin[1], melanin[2]);
                int pheomelaninRGB = Colouration.HSBtoABGR(pheomelanin[0], pheomelanin[1], pheomelanin[2]);

                int blackTintRGB = Colouration.HSBtoARGB(blackTintColor[0], blackTintColor[1], blackTintColor[2]);
                int redTintRGB = Colouration.HSBtoARGB(redTintColor[0], redTintColor[1], redTintColor[2]);

                int redPatternRGB = Colouration.HSBtoARGB(redPatternColor[0], redPatternColor[1], redPatternColor[2]); // testing


                // testing PLACEHOLDER
                //    int swallowbellyRGB = Colouration.HSBtoARGB(swallowbellyColor[0], swallowbellyColor[1], swallowbellyColor[2]);
                int lightAgoutiRGB = Colouration.HSBtoARGB(lightAgoutiColor[0], lightAgoutiColor[1], lightAgoutiColor[2]);
                int darkAgoutiRGB = Colouration.HSBtoARGB(darkAgoutiColor[0], darkAgoutiColor[1], darkAgoutiColor[2]);
                int darkAgoutiRedRGB = Colouration.HSBtoARGB(darkAgoutiRedColor[0], darkAgoutiRedColor[1], darkAgoutiRedColor[2]);

                this.colouration.setLeftEyeColour(leftEyeRGB);
                this.colouration.setRightEyeColour(rightEyeRGB);

                this.colouration.setMelaninColour(melaninRGB);
                this.colouration.setPheomelaninColour(pheomelaninRGB);
            }
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

    @Override
    protected void fixGeneLengths() {

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

    @Override
    protected EnhancedAnimalAbstract createEnhancedChild(Level world, EnhancedAnimalAbstract otherParent) {
        return null;
    }


}