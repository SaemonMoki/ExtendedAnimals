//package mokiyoki.enhancedanimals.entity;
//
//import mokiyoki.enhancedanimals.ai.general.EnhancedBreedGoal;
//import mokiyoki.enhancedanimals.ai.general.EnhancedLookAtGoal;
//import mokiyoki.enhancedanimals.ai.general.EnhancedLookRandomlyGoal;
//import mokiyoki.enhancedanimals.ai.general.EnhancedWanderingGoal;
//import mokiyoki.enhancedanimals.ai.general.GrazingGoal;
//import mokiyoki.enhancedanimals.entity.Genetics.HorseGeneticsInitialiser;
//import mokiyoki.enhancedanimals.entity.util.Colouration;
//import mokiyoki.enhancedanimals.init.FoodSerialiser;
//import mokiyoki.enhancedanimals.init.ModItems;
//import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
//import mokiyoki.enhancedanimals.util.Genes;
//import mokiyoki.enhancedanimals.util.Reference;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.SpawnGroupData;
//import net.minecraft.world.entity.Mob;
//import net.minecraft.world.entity.MobSpawnType;
//import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
//import net.minecraft.world.entity.ai.attributes.Attributes;
//import net.minecraft.world.entity.ai.goal.FollowParentGoal;
//import net.minecraft.world.entity.ai.goal.PanicGoal;
//import net.minecraft.world.entity.ai.goal.FloatGoal;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.Items;
//import net.minecraft.world.item.crafting.Ingredient;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.world.damagesource.DamageSource;
//import net.minecraft.sounds.SoundEvent;
//import net.minecraft.sounds.SoundEvents;
//import net.minecraft.core.BlockPos;
//import net.minecraft.world.DifficultyInstance;
//import net.minecraft.world.level.ServerLevelAccessor;
//import net.minecraft.world.level.LevelAccessor;
//import net.minecraft.world.level.Level;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//
//import javax.annotation.Nullable;
//
//import static mokiyoki.enhancedanimals.init.FoodSerialiser.horseFoodMap;
//import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_HORSE;
//
//public class EnhancedHorse extends EnhancedAnimalRideableAbstract {
//
//    private static final String[] HORSE_TEXTURES_TESTNUMBER = new String[] {
//            "0.png", "1.png", "2.png", "3.png", "4.png", "5.png", "6.png", "7.png", "8.png"
//    };
//
//    private static final String[] HORSE_TEXTURES_TESTLETTER = new String[] {
//            "a.png", "b.png", "c.png", "d.png", "e.png", "f.png", "g.png", "h.png", "i.png"
//    };
//
//    private static final String[] HORSE_TEXTURES_SKIN = new String[] {
//            "skin_black.png", "skin_freckled.png", "skin_rosy.png", "skin_pink.png"
//    };
//
//    private static final String[] HORSE_TEXTURES_SKINSPOT_TOBIANO = new String[] {
//            "",
//            "skinspot_tobiano_0.png", "skinspot_tobiano_1.png", "skinspot_tobiano_2.png", "skinspot_tobiano_3.png", "skinspot_tobiano_4.png", "skinspot_tobiano_5.png", "skinspot_tobiano_6.png", "skinspot_tobiano_7.png", "skinspot_tobiano_8.png", "skinspot_tobiano_9.png", "skinspot_tobiano_a.png", "skinspot_tobiano_b.png", "skinspot_tobiano_c.png", "skinspot_tobiano_d.png", "skinspot_tobiano_e.png", "skinspot_tobiano_f.png"
//    };
//    private static final String[] HORSE_TEXTURES_SKINSPOT_DOMINANTWHITE1 = new String[] {
//            "",
//            "skinspot_domwhite1_0.png", "skinspot_domwhite1_1.png", "skinspot_domwhite1_2.png", "skinspot_domwhite1_3.png", "skinspot_domwhite1_4.png", "skinspot_domwhite1_5.png", "skinspot_domwhite1_6.png", "skinspot_domwhite1_7.png", "skinspot_domwhite1_8.png", "skinspot_domwhite1_9.png", "skinspot_domwhite1_a.png", "skinspot_domwhite1_b.png", "skinspot_domwhite1_c.png", "skinspot_domwhite1_d.png", "skinspot_domwhite1_e.png", "skinspot_domwhite1_f.png"
//    };
//    private static final String[] HORSE_TEXTURES_SKINSPOT_DOMINANTWHITE2 = new String[] {
//            "",
//            "skinspot_domwhite2_0.png", "skinspot_domwhite2_1.png", "skinspot_domwhite2_2.png", "skinspot_domwhite2_3.png", "skinspot_domwhite2_4.png", "skinspot_domwhite2_5.png", "skinspot_domwhite2_6.png", "skinspot_domwhite2_7.png", "skinspot_domwhite2_8.png", "skinspot_domwhite2_9.png", "skinspot_domwhite2_a.png", "skinspot_domwhite2_b.png", "skinspot_domwhite2_c.png", "skinspot_domwhite2_d.png", "skinspot_domwhite2_e.png", "skinspot_domwhite2_f.png"
//    };
//
//    private static final String[] HORSE_TEXTURES_SKINSPOT_APPALOOSA = new String[] {
//            "",
//            "skinspot_appaloosa_0.png", "skinspot_appaloosa_1.png", "skinspot_appaloosa_2.png", "skinspot_appaloosa_3.png", "skinspot_appaloosa_4.png", "skinspot_appaloosa_5.png", "skinspot_appaloosa_6.png", "skinspot_appaloosa_7.png", "skinspot_appaloosa_8.png", "skinspot_appaloosa_9.png", "skinspot_appaloosa_a.png", "skinspot_appaloosa_b.png", "skinspot_appaloosa_c.png", "skinspot_appaloosa_d.png", "skinspot_appaloosa_e.png", "skinspot_appaloosa_f.png"
//    };
//
//    private static final String[] HORSE_TEXTURES_BASE = new String[] {
//            "r_solid_white.png"
//    };
////    private static final String[] HORSE_TEXTURES_RED = new String[] {
////            "", "solid_red.png"
////    };
//    private static final String[] HORSE_TEXTURES_DUN = new String[] {
//            "", "dun_smooth_strong.png", "dun_smooth_medium.png", "dun_smooth_weak.png",
//                "dun_wild_strong.png", "dun_wild_medium.png", "dun_wild_weak.png"
//    };
//    private static final String[] HORSE_TEXTURES_BLACKPATTERN = new String[] {
//            "", "b_bay_smooth_strong.png", "b_sealbrown.png"
//    };
//    private static final String[] HORSE_TEXTURES_SPOT_TOBIANO = new String[] {
//            "",
//            "spot_tobiano_0.png", "spot_tobiano_1.png", "spot_tobiano_2.png", "spot_tobiano_3.png", "spot_tobiano_4.png", "spot_tobiano_5.png", "spot_tobiano_6.png", "spot_tobiano_7.png", "spot_tobiano_8.png", "spot_tobiano_9.png", "spot_tobiano_a.png", "spot_tobiano_b.png", "spot_tobiano_c.png", "spot_tobiano_d.png", "spot_tobiano_e.png", "spot_tobiano_f.png"
//    };
//    private static final String[] HORSE_TEXTURES_SPOT_DOMINANTWHITE = new String[] {
//            "", "spot_domwhite_solid.png", "spot_domwhite_1.png", "spot_domwhite1_2.png", "spot_domwhite1_3.png", "spot_domwhite1_4.png", "spot_domwhite1_5.png", "spot_domwhite1_6.png", "spot_domwhite1_7.png", "spot_domwhite1_8.png", "spot_domwhite1_9.png", "spot_domwhite1_a.png", "spot_domwhite1_b.png", "spot_domwhite1_c.png", "spot_domwhite1_d.png", "spot_domwhite1_e.png", "spot_domwhite1_f.png"
//    };
//
//    private static final String[] HORSE_TEXTURES_SPOT_APPALOOSA = new String[] {
//            "",
//            "spot_appaloosa_0.png", "spot_appaloosa_1.png", "spot_appaloosa_2.png", "spot_appaloosa_3.png", "spot_appaloosa_4.png", "spot_appaloosa_5.png", "spot_appaloosa_6.png", "spot_appaloosa_7.png", "spot_appaloosa_8.png", "spot_appaloosa_9.png", "spot_appaloosa_a.png", "spot_appaloosa_b.png", "spot_appaloosa_c.png", "spot_appaloosa_d.png", "spot_appaloosa_e.png", "spot_appaloosa_f.png"
//    };
//
//    private static final String[] HORSE_TEXTURES_SILVER = new String[]  {
//            "silver_mask.png"
//    };
//
//    private static final String[] HORSE_TEXTURES_MOUTH = new String[]  {
//            "mouth.png"
//    };
//
//    private static final String[] HORSE_TEXTURES_SCLERA = new String[]  {
//            "sclera_black.png", "sclera_white.png"
//    };
//
//    private static final String[] HORSE_TEXTURES_EYES = new String[]  {
//            "eyel_black.png", "eyel_brown.png", "eyel_hazel.png", "eyel_yellow.png", "eyel_blue.png", "eyel_white.png"
//    };
//
//    private static final String[] HORSE_TEXTURES_HOOVES = new String[]  {
//            "hooves_black.png", "hooves_brown.png"
//    };
//
////    private static final String[] HORSE_TEXTURES_BLANKETS = new String[]  {
////            "blanket_trader.png", "blanket_black.png", "blanket_blue.png", "blanket_brown.png", "blanket_cyan.png", "blanket_grey.png", "blanket_green.png", "blanket_lightblue.png", "blanket_lightgrey.png", "blanket_lime.png", "blanket_magenta.png", "blanket_orange.png", "blanket_pink.png", "blanket_purple.png", "blanket_red.png", "blanket_white.png", "blanket_yellow.png"
////    };
////
////    private static final String[] HORSE_TEXTURES_SADDLE = new String[]  {
////            "", "saddle_vanilla.png", "saddle_western_dyeable.png", "saddle_english.png"
////    };
////
////    private static final String[] HORSE_TEXTURES_SADDLE_DECO = new String[]  {
////            "", "c_saddleseat.png"
////    };
//
////    private final List<String> horseTextures = new ArrayList<>();
//
//    private static final Ingredient MILK_ITEMS = Ingredient.of(ModItems.MILK_BOTTLE, ModItems.HALF_MILK_BOTTLE);
//
//    private static final int SEXLINKED_GENES_LENGTH = 2;
//
//    public EnhancedHorse(EntityType<? extends EnhancedHorse> entityType, Level worldIn) {
//        super(entityType, worldIn, SEXLINKED_GENES_LENGTH, Reference.HORSE_AUTOSOMAL_GENES_LENGTH, true);
//    }
//
//    protected boolean aiConfigured = false; //TODO move this up
//    protected String motherUUID = "";
//    protected GrazingGoal grazingGoal;
//
//    @Override
//    protected void registerGoals() {
//        //Todo add the temperamants
//        this.goalSelector.addGoal(0, new FloatGoal(this));
//        this.goalSelector.addGoal(7, new EnhancedLookAtGoal(this, Player.class, 6.0F));
//        this.goalSelector.addGoal(8, new EnhancedLookRandomlyGoal(this));
//    }
//
//    protected void customServerAiStep()
//    {
//        //TODO move this up
//        this.animalEatingTimer = this.grazingGoal.getEatingGrassTimer();
//        super.customServerAiStep();
//    }
//
//    protected void defineSynchedData() {
//        super.defineSynchedData();
//    }
//
//    protected String getSpecies() {
//        return "entity.eanimod.enhanced_horse";
//    }
//
//    @Override
//    protected FoodSerialiser.AnimalFoodMap getAnimalFoodType() {
//        return horseFoodMap();
//    }
//
//    protected int getAdultAge() { return 120000;}
//
//    @Override
//    protected int gestationConfig() {
//        return EanimodCommonConfig.COMMON.gestationDaysHorse.get();
//    }
//
//    @Override
//    protected boolean canBePregnant() {
//        return true;
//    }
//
//    @Override
//    protected boolean canLactate() {
//        return true;
//    }
//
//    public int getShape() {
////        return this.rand.nextInt(8) - 2;
//        return 0;
//    }
//
//    public float getFaceShape() {
////        if (this.rand.nextBoolean()) {
////            return this.rand.nextFloat() * -0.25F;
////        } else {
////            return this.rand.nextFloat() * 0.075F;
////        }
//        return 0.075F;
//    }
//
//    public int getFaceLength() {
////        return this.rand.nextInt(3);
//        return 0;
//    }
//
//    /**
//     * Returns the Y offset from the entity's position for any entity riding this one.
//     */
//    public double getPassengersRidingOffset() {
//        return (double)this.getBbHeight() * 0.725D;
//    }
//
//    @Override
//    protected float getJumpHeight() {
//        if (this.dwarf > 0 || this.getEnhancedInventory().getItem(0).getItem() == Items.CHEST) {
//            return 0.45F;
//        } else {
//            float jump = 0.48F;
//            float size = this.getAnimalSize();
//            if (size < 0.9F) {
//                return jump + (((size - 0.9F) / 0.2F) * 0.1F);
//            }
//            return jump;
//        }
//    }
//
//    @Override
//    protected float getJumpFactorModifier() {
//        return 0.25F;
//    }
//
//    @Override
//    protected float getMovementFactorModifier() {
//        float speedMod = 1.0F;
//        float size = this.getAnimalSize();
//        if (size > 1.05F) {
//            speedMod = 1.05F/size;
//        } else if (size < 1.0F) {
//            speedMod = size/1.0F;
//        }
//
//        if (this.dwarf > 0) {
//            speedMod = speedMod * 0.25F;
//        }
//
//        float chestMod = 0.0F;
//        ItemStack chestSlot = this.getEnhancedInventory().getItem(0);
//        if (chestSlot.getItem() == Items.CHEST) {
//            chestMod = (1.0F-((size-0.7F)*1.25F)) * 0.4F;
//        }
//
//        return 0.4F + (speedMod * 0.4F) - chestMod;
//    }
//
//    protected SoundEvent getAmbientSound() {
//        if (isAnimalSleeping()) {
//            return null;
//        }
//        return SoundEvents.HORSE_AMBIENT;
//    }
//
//    protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return SoundEvents.HORSE_HURT; }
//
//    protected SoundEvent getDeathSound() { return SoundEvents.HORSE_DEATH; }
//
//    protected void playStepSound(BlockPos pos, BlockState blockIn) {
//        super.playStepSound(pos, blockIn);
//        this.playSound(SoundEvents.HORSE_STEP, 0.15F, 1.0F);
//        if (!this.isSilent() && this.getBells()) {
//            this.playSound(SoundEvents.NOTE_BLOCK_CHIME, 1.5F, 0.1F);
//            this.playSound(SoundEvents.NOTE_BLOCK_BELL, 1.0F, 0.1F);
//        }
//    }
//
//    /**
//     * Returns the volume for the sounds this mob makes.
//     */
//    protected float getSoundVolume() {
//        return 0.4F;
//    }
//
//    public static AttributeSupplier.Builder prepareAttributes() {
//        return Mob.createMobAttributes()
//                .add(Attributes.MAX_HEALTH, 8.0D)
//                .add(Attributes.MOVEMENT_SPEED, 0.23D)
//                .add(JUMP_STRENGTH);
//    }
//
//    @Override
//    public boolean sleepingConditional() {
//        return (((this.level.getDayTime()%24000 >= 12600 && this.level.getDayTime()%24000 <= 22000) || this.level.isThundering()) && awokenTimer == 0 && !sleeping);
//    }
//
//    @Override
//    protected void incrementHunger() {
//        if(sleeping) {
//            hunger = hunger + (1.0F*getHungerModifier());
//        } else {
//            hunger = hunger + (2.0F*getHungerModifier());
//        }
//    }
//
//    @Override
//    protected void runExtraIdleTimeTick() {
//    }
//
//    protected void createAndSpawnEnhancedChild(Level inWorld) {
//        EnhancedHorse enhancedhorse = ENHANCED_HORSE.get().create(this.level);
//        Genes babyGenes = new Genes(this.genetics).makeChild(this.getOrSetIsFemale(), this.mateGender, this.mateGenetics);
//        defaultCreateAndSpawn(enhancedhorse, inWorld, babyGenes, -this.getAdultAge());
//        enhancedhorse.configureAI();
//        this.level.addFreshEntity(enhancedhorse);
//    }
//
//    @Override
//    public void initilizeAnimalSize() {
////        int[] genes = this.genetics.getAutosomalGenes();
//        float size = 1.0F;
//
//        this.setAnimalSize(size);
//    }
//
//    public void lethalGenes(){
//        int[] genes = this.genetics.getAutosomalGenes();
//        //put in the lethal combinations of dominant white
//        if((genes[18] != 20 && genes[19] != 20 && genes[18] != 28 && genes[19] != 28 && genes[18] != 29 && genes[19] != 29) || (genes[18] == 12 || genes[19] == 12)) {
//            this.remove(RemovalReason.DISCARDED);
//        } else if (genes[32] == 2 && genes[33] == 2) {
//            //TODO change the foal to a skeleton horse that attacks
//            this.remove(RemovalReason.DISCARDED);
//        }
//    }
//
//    public void setMotherUUID(String motherUUID) {
//        this.motherUUID = motherUUID;
//    }
//
//    public String getMotherUUID() {
//        return this.motherUUID;
//    }
//
//    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn) {
//        super.dropCustomDeathLoot(source, looting, recentlyHitIn);
//
//        int leatherDrop = this.random.nextInt(3);
//
//        if (!this.isOnFire()){
//            ItemStack leatherStack = new ItemStack(Items.LEATHER, leatherDrop);
//            this.spawnAtLocation(leatherStack);
//        }
//    }
//
//    @OnlyIn(Dist.CLIENT)
//    public String getTexture() {
//        if (this.enhancedAnimalTextures.isEmpty()) {
//            this.setTexturePaths();
//        } else if (this.reload) {
//            this.reload = false;
//            this.reloadTextures();
//        }
//
//        return getCompiledTextures("enhanced_horse");
//
//    }
//
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    protected void reloadTextures() {
//        this.texturesIndexes.clear();
//        this.enhancedAnimalTextures.clear();
//        this.setTexturePaths();
//        this.colouration.setMelaninColour(-1);
//        this.colouration.setPheomelaninColour(-1);
//    }
//
//    @OnlyIn(Dist.CLIENT)
//    protected void setTexturePaths() {
//        if (this.getSharedGenes() != null) {
//            int[] genesForText = getSharedGenes().getAutosomalGenes();
//
//            if (genesForText != null) {
//                int dun = 0;
//                int pattern = 0;
//                int number = 0;
//                int letter = 0;
//                int sclera = 0;
//                boolean silver = false;
//                char[] uuidArry = getStringUUID().toCharArray();
//
//                if ((genesForText[18] == 20 || genesForText[18] == 28 || genesForText[18] == 29) && (genesForText[19] == 20 || genesForText[19] == 28 || genesForText[19] == 29)) {
//
//                    if (genesForText[12] == 2 && genesForText[13] == 2) {
//                        //horse is red based
//                    } else {
//                        if (genesForText[14] == 4 && genesForText[15] == 4) {
//                            //horse is black based
//                        } else if (genesForText[14] == 1 || genesForText[15] == 1) {
//                            //wildtype bay
//                            pattern = 1;
//                        } else if (genesForText[14] == 2 || genesForText[15] == 2) {
//                            //heavy marked bay
//                            pattern = 1;
//                        } else {
//                            //seal brown
//                            pattern = 2;
//                        }
//                    }
//
//                    //TODO mushroom
//
//                    if (genesForText[16] == 1 && genesForText[17] == 1) {
//                        //dun
//                        dun = 1;
//                    }
//
//                    if (genesForText[60] == 1 || genesForText[61] == 1) {
//                        //mealy markings
//                    }
//
//                    //TODO sooty
//
//                    //TODO liver
//
//                    if (Character.isDigit(uuidArry[16])) {
//                        number = uuidArry[16] - 48;
//                        if (number >= 8) {
//                            number = number - 8;
//                        }
//                    } else {
//                        char test = uuidArry[16];
//                        switch (test) {
//                            case 'a':
//                                number = 3;
//                                break;
//                            case 'b':
//                                number = 4;
//                                break;
//                            case 'c':
//                                number = 5;
//                                break;
//                            case 'd':
//                                number = 6;
//                                break;
//                            case 'e':
//                                number = 7;
//                                break;
//                            case 'f':
//                                number = 8;
//                                break;
//                        }
//                    }
//
//                    if (Character.isDigit(uuidArry[17])) {
//                        letter = uuidArry[17] - 48;
//                        if (letter >= 8) {
//                            letter = letter - 8;
//                        }
//                    } else {
//                        char test = uuidArry[17];
//                        switch (test) {
//                            case 'a':
//                                letter = 3;
//                                break;
//                            case 'b':
//                                letter = 4;
//                                break;
//                            case 'c':
//                                letter = 5;
//                                break;
//                            case 'd':
//                                letter = 6;
//                                break;
//                            case 'e':
//                                letter = 7;
//                                break;
//                            case 'f':
//                                letter = 8;
//                                break;
//                        }
//                    }
//                }
//
//                if (genesForText[26] == 2 && genesForText[27] == 2) {
//                    silver = true;
//                }
//
//                if (genesForText[36] == 2 || genesForText[37] == 2) {
//                    sclera = 1;
//                }
//
//                addTextureToAnimal(HORSE_TEXTURES_SKIN, 0, null);
//                addTextureToAnimal(HORSE_TEXTURES_BASE, 0, null);
//                addTextureToAnimal(HORSE_TEXTURES_DUN, dun, d -> d != 0);
//                addTextureToAnimal(HORSE_TEXTURES_BLACKPATTERN, pattern, p -> p != 0);
//                addTextureToAnimal(HORSE_TEXTURES_SILVER, silver? 1 : 0, s -> s != 0);
//                addTextureToAnimal(HORSE_TEXTURES_EYES, 1, null);
//                addTextureToAnimal(HORSE_TEXTURES_SCLERA, sclera, null);
//                addTextureToAnimal(HORSE_TEXTURES_HOOVES, 0, null);
//                addTextureToAnimal(HORSE_TEXTURES_MOUTH, 0, null);
//
////                this.horseTextures.add(HORSE_TEXTURES_TESTNUMBER[number]);
////                this.horseTextures.add(HORSE_TEXTURES_TESTLETTER[letter]);
//            }
//        }
//    }
//
//    @Override
//    protected void setAlphaTexturePaths() {
//    }
//
//    @OnlyIn(Dist.CLIENT)
//    public Colouration getRgb() {
//        this.colouration = super.getRgb();
//        if (this.colouration.getPheomelaninColour() == -1 || this.colouration.getMelaninColour() == -1) {
//            Genes genes = getSharedGenes();
//            if (genes != null) {
//                int[] gene = genes.getAutosomalGenes();
//                if ((gene[18] == 20 || gene[18] == 28 || gene[18] == 29) && (gene[19] == 20 || gene[19] == 28 || gene[19] == 29)) {
//
//                    float blackHue = 0.047F;
//                    float blackSaturation = 0.20F;
//                    float blackBrightness = 0.07F;
//
//                    float redHue = 0.047F;
//                    float redSaturation = 0.75F;
//                    float redBrightness = 0.5F;
//
//                    if (gene[12] == 2 && gene[13] == 2) {
//                        //red
//                    } else if (gene[14] == 4 && gene[15] == 4) {
//                        //black
//                    }
//
//
//                    if (gene[22] != 1 || gene[23] != 1) {
//                        if (gene[22] == 2 || gene[23] == 2) {
//                            if (gene[22] == 1 || gene[23] == 1) {
//                                //cream
//                            } else {
//                                //double dilute
//
//                                if (gene[22] == 3 || gene[23] == 3) {
//                                    //pseudo double dilute
//
//                                }
//                            }
//                        } else {
//                            //pearl
//                        }
//                    }
//
//                    //puts final values into array for processing
//                    float[] melanin = {blackHue, blackSaturation, blackBrightness};
//                    float[] pheomelanin = {redHue, redSaturation, redBrightness};
//
//                    //checks that numbers are within the valid range
//                    for (int i = 0; i <= 2; i++) {
//                        if (melanin[i] > 1.0F) {
//                            melanin[i] = 1.0F;
//                        } else if (melanin[i] < 0.0F) {
//                            melanin[i] = 0.0F;
//                        }
//                        if (pheomelanin[i] > 1.0F) {
//                            pheomelanin[i] = 1.0F;
//                        } else if (pheomelanin[i] < 0.0F) {
//                            pheomelanin[i] = 0.0F;
//                        }
//                    }
//
//                    this.colouration.setMelaninColour(Colouration.HSBtoABGR(melanin[0], melanin[1], melanin[2]));
//                    this.colouration.setPheomelaninColour(Colouration.HSBtoABGR(pheomelanin[0], pheomelanin[1], pheomelanin[2]));
//
//                } else {
//                    this.colouration.setMelaninColour(16777215);
//                    this.colouration.setPheomelaninColour(16777215);
//                }
//            }
//        }
//
//        return this.colouration;
//    }
//
////    @Override
////    public ActionResultType mobInteract(PlayerEntity entityPlayer, Hand hand) {
////        ItemStack itemStack = entityPlayer.getHeldItem(hand);
////        Item item = itemStack.getItem();
////
////        if (this.isBeingRidden()) {
////            return super.mobInteract(entityPlayer, hand);
////        }
////        return super.mobInteract(entityPlayer, hand);
////    }
//
//
//    public void addAdditionalSaveData(CompoundTag compound) {
//        super.addAdditionalSaveData(compound);
//    }
//
//    /**
//     * (abstract) Protected helper method to read subclass entity assets from NBT.
//     */
//    public void readAdditionalSaveData(CompoundTag compound) {
//        super.readAdditionalSaveData(compound);
//        configureAI();
//    }
//
//    //Health 15-30
//
//    //Speed 0.1125–0.3375
//
//    //Jump 0.4–1.0
//
//
//    @Nullable
//    @Override
//    public SpawnGroupData finalizeSpawn(ServerLevelAccessor inWorld, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag itemNbt) {
//        return commonInitialSpawnSetup(inWorld, livingdata, getAdultAge(), 30000, 108000, spawnReason);
//    }
//
//    @Override
//    public void setInitialDefaults() {
//        super.setInitialDefaults();
//        configureAI();
//    }
//
//    @Override
//    protected Genes createInitialGenes(LevelAccessor world, BlockPos pos, boolean isDomestic) {
//        return new HorseGeneticsInitialiser().generateNewGenetics(world, pos, isDomestic);
//    }
//
//    @Override
//    public Genes createInitialBreedGenes(LevelAccessor world, BlockPos pos, String breed) {
//        return new HorseGeneticsInitialiser().generateWithBreed(world, pos, breed);
//    }
//
//    protected void configureAI() {
//        if (!aiConfigured) {
//
//            this.goalSelector.addGoal(1, new PanicGoal(this, 1.5D));
//            this.goalSelector.addGoal(2, new EnhancedBreedGoal(this, 1.0D));
////            this.goalSelector.addGoal(3, new EnhancedTemptGoal(this, 1.25D, false, TEMPTATION_ITEMS));
//            this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
////            this.goalSelector.addGoal(4, new EnhancedAINurseFromMotherGoal(this, motherUUID, 1.25D));
//            grazingGoal = new GrazingGoal(this, 1.0D);
//            this.goalSelector.addGoal(6, grazingGoal);
//            this.goalSelector.addGoal(7, new EnhancedWanderingGoal(this, 1.0D));
//
//        }
//        aiConfigured = true;
//    }
//
//    public static class GroupData implements SpawnGroupData {
//
//        public int[] groupGenes;
//
//        public GroupData(int[] groupGenes) {
//            this.groupGenes = groupGenes;
//        }
//
//    }
//
//}
