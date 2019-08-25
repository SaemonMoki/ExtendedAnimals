package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.items.DebugGenesBook;
import mokiyoki.enhancedanimals.util.Reference;
import mokiyoki.enhancedanimals.util.handlers.ConfigHandler;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.EatGrassGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.stats.Stats;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTables;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static mokiyoki.enhancedanimals.util.handlers.RegistryHandler.ENHANCED_PIG;

public class EnhancedPig extends AnimalEntity {

    //avalible UUID spaces : [ S X 2 3 4 5 6 7 - 8 9 10 11 - 12 13 14 15 - 16 17 18 19 - 20 21 22 23 24 25 26 27 28 29 30 31 ]

    private static final DataParameter<String> SHARED_GENES = EntityDataManager.<String>createKey(EnhancedPig.class, DataSerializers.STRING);
    private static final DataParameter<Float> PIG_SIZE = EntityDataManager.createKey(EnhancedPig.class, DataSerializers.FLOAT);

    private static final String[] PIG_TEXTURES_COATRED = new String[] {
            "pigbase.png", "solid_white.png", "solid_milk.png", "solid_cream.png", "solid_carmel.png", "solid_orange.png", "solid_ginger.png", "solid_red.png", "solid_brown.png"
    };
    private static final String[] PIG_TEXTURES_COATBLACK = new String[] {
            "red", "black_solid.png", "black_wildtype.png", "black_brindle.png", "black_brindlesmall.png",
                   "choc_solid.png", "choc_wildtype.png", "choc_brindle.png", "choc_brindlesmall.png",
                   "lightchoc_wildtype.png"
    };

    private static final String[] PIG_TEXTURES_SPOT_SPOTS = new String[] {
            "", "spot_spots.png", "spot_roanspots.png"
    };

    private static final String[] PIG_TEXTURES_SPOT_BELTED = new String[] {
            "", "spot_white.png", "spot_belt.png"
    };

    private static final String[] PIG_TEXTURES_SPOT_BERKSHIRE = new String[] {
            "", "spot_tux.png", "spot_berkshire.png"
    };

    private static final String[] PIG_TEXTURES_COAT = new String[] {
            "coat_normal.png", "coat_wooly.png"
    };

    private static final String[] PIG_TEXTURES_SKINBASE = new String[] {
            "", "skin_pink_bald.png", "skin_grey_bald.png", "skin_black_bald.png",
                "skin_pink_sparse.png", "skin_grey_sparse.png", "skin_black_sparse.png",
                "skin_pink_medium.png", "skin_grey_medium.png", "skin_black_medium.png",
                "skin_pink_furry.png", "skin_grey_furry.png", "skin_black_furry.png",
                "skin_pink_wooly.png", "skin_grey_wooly.png", "skin_black_wooly.png"
    };

    private static final String[] PIG_TEXTURES_SKINMARKINGS = new String[] {
            "", "spots"
    };

    private static final String[] PIG_TEXTURES_EYES = new String[] {
            "eyes_black.png", "eyes_brown.png", "eyes_blue.png"
    };

    private static final String[] PIG_TEXTURES_HOOVES = new String[] {
            "hooves_black.png", "hooves_brown.png", "hooves_pink.png"
    };

    private static final String[] PIG_TEXTURES_TUSKS = new String[] {
            "", "tusks.png"
    };

    private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Blocks.MELON, Blocks.PUMPKIN, Blocks.GRASS, Blocks.HAY_BLOCK, Items.CARROT, Items.POTATO, Items.WHEAT, Items.BEETROOT, Items.ROTTEN_FLESH, Items.APPLE, Items.COOKED_CHICKEN, Items.COOKED_BEEF, Items.COOKED_MUTTON, Items.COOKED_RABBIT, Items.COOKED_SALMON, Items.COOKED_COD, Blocks.BROWN_MUSHROOM, Blocks.DARK_OAK_SAPLING, Blocks.OAK_SAPLING, Items.MILK_BUCKET, Items.BREAD);
    private static final Ingredient BREED_ITEMS = Ingredient.fromItems(Items.CARROT, Items.BEETROOT, Items.POTATO);

    private static final int WTC = 90;
    private final List<String> pigTextures = new ArrayList<>();
    private static final int GENES_LENGTH = 44;
    private int[] genes = new int[GENES_LENGTH];
    private int[] mateGenes = new int[GENES_LENGTH];
    private int[] mitosisGenes = new int[GENES_LENGTH];
    private int[] mateMitosisGenes = new int[GENES_LENGTH];

    private UUID angerTargetUUID;
    private int angerLevel;
    private float pigSize;

    public EnhancedPig(EntityType<? extends EnhancedPig> entityType, World worldIn) {
        super(entityType, worldIn);
        //TODO why doesnt this change from pig to pig
        this.setPigSize();
//        this.setSize(0.9F, pigSize*0.9F);
    }

    private int pigTimer;
    private EatGrassGoal entityAIEatGrass;

    private int gestationTimer = 0;
    private boolean pregnant = false;

    @Override
    protected void registerGoals() {
        this.entityAIEatGrass = new EatGrassGoal(this);
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
//        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.fromItems(Items.CARROT_ON_A_STICK), false));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, false, TEMPTATION_ITEMS));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(5, this.entityAIEatGrass);
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new EnhancedPig.HurtByAggressorGoal(this));
        this.targetSelector.addGoal(2, new EnhancedPig.TargetAggressorGoal(this));
    }

    @Override
    protected void updateAITasks() {
        this.pigTimer = this.entityAIEatGrass.getEatingGrassTimer();

        LivingEntity livingentity = this.getRevengeTarget();
        if (this.isAngry()) {

            --this.angerLevel;
            LivingEntity livingentity1 = livingentity != null ? livingentity : this.getAttackTarget();
            if (!this.isAngry() && livingentity1 != null) {
                if (!this.canEntityBeSeen(livingentity1)) {
                    this.setRevengeTarget((LivingEntity)null);
                    this.setAttackTarget((LivingEntity)null);
                } else {
                    this.angerLevel = this.angerAmount();
                }
            }
        }

        if (this.isAngry() && this.angerTargetUUID != null && livingentity == null) {
            PlayerEntity playerentity = this.world.getPlayerByUuid(this.angerTargetUUID);
            this.setRevengeTarget(playerentity);
            this.attackingPlayer = playerentity;
            this.recentlyHit = this.getRevengeTimer();
        }

        super.updateAITasks();
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(SHARED_GENES, new String());
        this.dataManager.register(PIG_SIZE, 0.0F);
    }

    private void setPigSize(float size) {
        this.dataManager.set(PIG_SIZE, size);
    }

    public float getSize() {
        return this.dataManager.get(PIG_SIZE);
    }

    @Override
    public boolean processInteract(PlayerEntity entityPlayer, Hand hand) {
        ItemStack itemStack = entityPlayer.getHeldItem(hand);
        Item item = itemStack.getItem();
        if (item instanceof DebugGenesBook) {
            ((DebugGenesBook)item).displayGenes(this.dataManager.get(SHARED_GENES));
        }
//        else if (TEMPTATION_ITEMS.test(itemStack)) {
//            decreaseHunger();
//            itemStack.shrink(1);
//        }
        return super.processInteract(entityPlayer, hand);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_PIG_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_PIG_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PIG_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.15F, 1.0F);
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        char[] uuidArry = getCachedUniqueIdString().toCharArray();
        double tusks = 2.0D;
        if (!isChild()) {
            if ((Character.isLetter(uuidArry[0]) || uuidArry[0] - 48 >= 8)) {
                //tusks if "male"
                tusks = 3.0D;
            }
        }
        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(tusks);
    }

    public void livingTick() {
        super.livingTick();

        if (this.world.isRemote) {
            this.pigTimer = Math.max(0, this.pigTimer - 1);
        }

        if (!this.world.isRemote) {
            if(pregnant) {
                gestationTimer++;
                int days = ConfigHandler.COMMON.gestationDays.get();
                if (gestationTimer >= days) {
                    pregnant = false;
                    gestationTimer = 0;

                    int pigletAverage = 8;
                    int pigletRange = 6;

                    int numberOfPiglets = ThreadLocalRandom.current().nextInt(pigletRange)+1+pigletAverage;
                    
                    for (int i = 0; i <= numberOfPiglets; i++) {
                        mixMateMitosisGenes();
                        mixMitosisGenes();
                        EnhancedPig enhancedpig = ENHANCED_PIG.create(this.world);
                        enhancedpig.setGrowingAge(0);
                        int[] babyGenes = getPigletGenes();
                        enhancedpig.setGenes(babyGenes);
                        enhancedpig.setSharedGenes(babyGenes);
                        enhancedpig.setPigSize();
                        enhancedpig.setGrowingAge(-24000);
                        enhancedpig.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
                        this.world.addEntity(enhancedpig);
                    }

                }
            }
        }

        //TODO what lethal genes do pigs have?
        if (!this.world.isRemote){
            lethalGenes();
        }

    }


    static class HurtByAggressorGoal extends HurtByTargetGoal {
        public HurtByAggressorGoal(EnhancedPig entity) {
            super(entity);
            this.setCallsForHelp(new Class[]{EnhancedPig.class});
        }

        protected void setAttackTarget(MobEntity mobIn, LivingEntity targetIn) {
            if (mobIn instanceof EnhancedPig && this.goalOwner.canEntityBeSeen(targetIn) && ((EnhancedPig)mobIn).becomeAngryAt(targetIn)) {
                mobIn.setAttackTarget(targetIn);
            }

        }
    }

    static class TargetAggressorGoal extends NearestAttackableTargetGoal<PlayerEntity> {
        public TargetAggressorGoal(EnhancedPig entity) {
            super(entity, PlayerEntity.class, true);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            return ((EnhancedPig)this.goalOwner).isAngry() && super.shouldExecute();
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)((int)this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getValue()));
        if (flag) {
            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            Entity entity = source.getTrueSource();
            if (entity instanceof PlayerEntity && !((PlayerEntity)entity).isCreative() && this.canEntityBeSeen(entity)) {
                this.becomeAngryAt(entity);
            }

            return super.attackEntityFrom(source, amount);
        }
    }

    private boolean becomeAngryAt(Entity entity) {
        this.angerLevel = this.angerAmount();
//        this.randomSoundDelay = this.rand.nextInt(40);
        if (entity instanceof LivingEntity) {
            this.setRevengeTarget((LivingEntity)entity);
        }

        return true;
    }

    @Override
    public void setRevengeTarget(@Nullable LivingEntity livingBase) {
        super.setRevengeTarget(livingBase);
        if (livingBase != null) {
            this.angerTargetUUID = livingBase.getUniqueID();
        }

    }

    private int angerAmount() {
        return 400 + this.rand.nextInt(400);
    }

    private boolean isAngry() {
        return this.angerLevel > 0;
    }

    private void setPigSize(){
        float size = 1.0F;

        //TODO size calculations go here

        //        0.6F <= size <= 1.5F
        this.pigSize = size;
        this.setPigSize(size);
    }

    protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropSpecialItems(source, looting, recentlyHitIn);

        this.isBurning();

//        float pigSize = this.getSize();

//        int meatDrop;
//        float meatChanceMod;

        //pig size
//        meatChanceMod = ((pigSize - 0.6F) * 4.445F) + 1;
//        meatDrop = Math.round(meatChanceMod);
//        if (meatDrop >= 5) {
//            meatChanceMod = 0;
//        } else {
//            meatChanceMod = (meatChanceMod - meatDrop) * 100;
//        }
//
//
//        if (meatChanceMod  != 0) {
//            int i = this.rand.nextInt(100);
//            if (meatChanceMod > i) {
//                meatDrop++;
//            }
//        }

        //TODO add pig sizes and qualities here and remove the vanilla mimic

        int meatDrop = ThreadLocalRandom.current().nextInt(3) + 1;

        if (this.isBurning()){
            ItemStack cookedBeefStack = new ItemStack(Items.COOKED_PORKCHOP, meatDrop);
            this.entityDropItem(cookedBeefStack);
        }else {
            ItemStack beefStack = new ItemStack(Items.PORKCHOP, meatDrop);
            this.entityDropItem(beefStack);
        }
    }

    public void lethalGenes(){

        //TODO lethal genes go here
        
    }

    public void eatGrassBonus() {
        if (this.isChild()) {
            this.addGrowth(60);
        }
    }


    public void setSharedGenes(int[] genes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < genes.length; i++) {
            sb.append(genes[i]);
            if (i != genes.length - 1) {
                sb.append(",");
            }
        }
        this.dataManager.set(SHARED_GENES, sb.toString());
    }

    public int[] getSharedGenes() {
        String sharedGenes = ((String) this.dataManager.get(SHARED_GENES)).toString();
        if (sharedGenes.isEmpty()) {
            return null;
        }
        String[] genesToSplit = sharedGenes.split(",");
        int[] sharedGenesArray = new int[genesToSplit.length];

        for (int i = 0; i < sharedGenesArray.length; i++) {
            //parse and store each value into int[] to be returned
            sharedGenesArray[i] = Integer.parseInt(genesToSplit[i]);
        }
        return sharedGenesArray;
    }

    public boolean isBreedingItem(ItemStack stack) {
        //TODO set this to a separate item or type of item for force breeding
        return BREED_ITEMS.test(stack);
    }

    public AgeableEntity createChild(AgeableEntity ageable) {
        this.mateGenes = ((EnhancedPig) ageable).getGenes();
        mixMateMitosisGenes();
        mixMitosisGenes();

        pregnant = true;

        this.setGrowingAge(10);
        this.resetInLove();
        ageable.setGrowingAge(10);
        ((EnhancedPig)ageable).resetInLove();

        ServerPlayerEntity entityplayermp = this.getLoveCause();
        if (entityplayermp == null && ((EnhancedPig)ageable).getLoveCause() != null) {
            entityplayermp = ((EnhancedPig)ageable).getLoveCause();
        }

        if (entityplayermp != null) {
            entityplayermp.addStat(Stats.ANIMALS_BRED);
            CriteriaTriggers.BRED_ANIMALS.trigger(entityplayermp, this, ((EnhancedPig)ageable), (AgeableEntity)null);
        }

        return null;
    }

    @OnlyIn(Dist.CLIENT)
    public String getPigTexture() {
        if (this.pigTextures.isEmpty()) {
            this.setTexturePaths();
        }
        return this.pigTextures.stream().collect(Collectors.joining("/","enhanced_pig/",""));

    }

    @OnlyIn(Dist.CLIENT)
    public String[] getVariantTexturePaths()
    {
        if (this.pigTextures.isEmpty()) {
            this.setTexturePaths();
        }

        return this.pigTextures.stream().toArray(String[]::new);
    }

    @OnlyIn(Dist.CLIENT)
    private void setTexturePaths() {
        int[] genesForText = getSharedGenes();
        if (genesForText != null) {
            boolean agouti = false;
            int spotMod = 0;
            int red = 5;
            int black = 0;
            int spot = 0;
            int belt = 0;
            int berk = 0;
            int coat = 0;
            int skin = 0;
            boolean tusks = false;

            char[] uuidArry = getCachedUniqueIdString().toCharArray();

            if (genesForText[0] == 1 || genesForText[1] == 1){
                //solid black
                black = 1;
            }else if (genesForText[0] == 2 || genesForText[1] == 2){
                agouti = true;
                black = 2;
            }else if (genesForText[0] == 3 || genesForText[1] == 3){
                //red with spots
                if (genesForText[0] == 3 && genesForText[1] == 3){
                    //big spots
                    black = 3;
                }else{
                    //small spots
                    black = 4;
                }
            }

            if (agouti){
                if (genesForText[2] == 1 || genesForText[3] == 1){
                    //shaded black
                    red = 8;
                }else if (genesForText[2] == 2 || genesForText[3] == 2){
                    //shaded brown
                    black = 6;
                }else if (genesForText[2] == 4 && genesForText[3] == 4) {
                    //recessive black
                    black = 1;
                }
            }

            if (genesForText[4] == 1 || genesForText[5] == 1){
                // reduce red in coat colour
                red = red - 2;
            }

            if (genesForText[6] == 2 && genesForText[7] == 2){
                //black turns to dark chocolate
                //reduce red in coat slightly
                if (black != 6) {
                    black = black + 4;
                }else{
                    black = 9;
                }
                red = red - 1;

            }

//TODO change this one

//            if (genesForText[8] == 1 || genesForText[9] == 1){
//                //turns black to blue/roan blue
//
//            }

            if (genesForText[10] != 1 && genesForText[11] != 1) {
                if (genesForText[10] == 2 || genesForText[11] == 2){
                    //spotted
                    spot = 1;
                } else {
                    //roan spotted
                    spot = 2;
                }
            }


            if (genesForText[12] == 1 || genesForText[13] == 1){
                //dominant white
                belt = 1;
            }else if (genesForText[12] == 2 || genesForText[13] == 2){
                //belted
                belt = 2;
            }

            if (genesForText[14] != 1 && genesForText[15] != 1){
                if (genesForText[14] == 2 || genesForText[15] == 2){
                    //tuxedo
                    berk = 1;
                }else{
                    //berkshire
                    berk = 2;
                }
            }

            //TODO make textures for this
//            if (genesForText[16] == 1 || genesForText[17] == 1){
//                spotMod = 2;
//            }else if (genesForText[16] == 2 || genesForText[17] == 2){
//                spotMod = 1;
//            }

            if (black == 1 || black == 2) {
                skin = 3;
            } else if (belt == 1) {
                skin = 1;
            }else{
                skin = 2;
            }

            if (genesForText[36] != 1 && genesForText[37] != 1) {
                if ((genesForText[34] == 1 || genesForText[35] == 1) && (genesForText[34] != 3 && genesForText[35] != 3)) {
                    //furry
                    skin = skin + 9;
                }else if (genesForText[34] == 2 || genesForText[35] == 2) {
                    //normal
                    skin = skin + 6;
                }else{
                    //sparse
                    skin = skin + 3;
                }
            }

            if (genesForText[38] == 1 || genesForText[39] == 1) {
                skin = skin + 3;
            }

            if ((genesForText[38] == 1 || genesForText[39] == 1) && skin >= 10) {
                //wooly
                coat = 1;
            }

            if (!isChild()) {
                if ((Character.isLetter(uuidArry[0]) || uuidArry[0] - 48 >= 8)) {
                    //tusks if "male"
                    tusks = true;
                }
            }

            this.pigTextures.add(PIG_TEXTURES_COATRED[red]);
            if (black != 0) {
                this.pigTextures.add(PIG_TEXTURES_COATBLACK[black]);
            }
            if (spot != 0) {
                this.pigTextures.add(PIG_TEXTURES_SPOT_SPOTS[spot]);
            }
            if (belt != 0) {
                this.pigTextures.add(PIG_TEXTURES_SPOT_BELTED[belt]);
            }
            if (berk != 0) {
                this.pigTextures.add(PIG_TEXTURES_SPOT_BERKSHIRE[berk]);
            }
            this.pigTextures.add(PIG_TEXTURES_COAT[coat]);
            this.pigTextures.add(PIG_TEXTURES_SKINBASE[skin]);
            this.pigTextures.add(PIG_TEXTURES_EYES[0]);
            this.pigTextures.add(PIG_TEXTURES_HOOVES[0]);
            if (tusks){
                this.pigTextures.add(PIG_TEXTURES_TUSKS[1]);
            }

        }
    }
    
    //TODO put item interactable stuff here like saddling pigs


    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);

        //store this pigs's genes
        ListNBT geneList = new ListNBT();
        for (int i = 0; i < genes.length; i++) {
            CompoundNBT nbttagcompound = new CompoundNBT();
            nbttagcompound.putInt("Gene", genes[i]);
            geneList.add(nbttagcompound);
        }
        compound.put("Genes", geneList);

        //store this pigs's mate's genes
        ListNBT mateGeneList = new ListNBT();
        for (int i = 0; i < mateGenes.length; i++) {
            CompoundNBT nbttagcompound = new CompoundNBT();
            nbttagcompound.putInt("Gene", mateGenes[i]);
            mateGeneList.add(nbttagcompound);
        }
        compound.put("FatherGenes", mateGeneList);

        compound.putBoolean("Pregnant", this.pregnant);
        compound.putInt("Gestation", this.gestationTimer);

        compound.putShort("Anger", (short)this.angerLevel);
        if (this.angerTargetUUID != null) {
            compound.putString("HurtBy", this.angerTargetUUID.toString());
        } else {
            compound.putString("HurtBy", "");
        }


    }

    /**
     * (abstract) Protected helper method to read subclass entity assets from NBT.
     */
    public void readAdditional(CompoundNBT compound) {

        super.readAdditional(compound);

        ListNBT geneList = compound.getList("Genes", 10);
        for (int i = 0; i < geneList.size(); ++i) {
            CompoundNBT nbttagcompound = geneList.getCompound(i);
            int gene = nbttagcompound.getInt("Gene");
            genes[i] = gene;
        }

        ListNBT mateGeneList = compound.getList("FatherGenes", 10);
        for (int i = 0; i < mateGeneList.size(); ++i) {
            CompoundNBT nbttagcompound = mateGeneList.getCompound(i);
            int gene = nbttagcompound.getInt("Gene");
            mateGenes[i] = gene;
        }

        this.pregnant = compound.getBoolean("Pregnant");
        this.gestationTimer = compound.getInt("Gestation");

        setSharedGenes(genes);
        setPigSize();

        this.angerLevel = compound.getShort("Anger");
        String s = compound.getString("HurtBy");
        if (!s.isEmpty()) {
            this.angerTargetUUID = UUID.fromString(s);
            PlayerEntity playerentity = this.world.getPlayerByUuid(this.angerTargetUUID);
            this.setRevengeTarget(playerentity);
            if (playerentity != null) {
                this.attackingPlayer = playerentity;
                this.recentlyHit = this.getRevengeTimer();
            }
        }

    }

    public void mixMitosisGenes() {
        punnetSquare(mitosisGenes, genes);
    }

    public void mixMateMitosisGenes() {
        punnetSquare(mateMitosisGenes, mateGenes);
    }

    public void punnetSquare(int[] mitosis, int[] parentGenes) {
        Random rand = new Random();
        for (int i = 0; i < genes.length; i = (i + 2)) {
            boolean mateOddOrEven = rand.nextBoolean();
            if (mateOddOrEven) {
                mitosis[i] = parentGenes[i + 1];
                mitosis[i + 1] = parentGenes[i];
            } else {
                mitosis[i] = parentGenes[i];
                mitosis[i + 1] = parentGenes[i + 1];
            }
        }
    }


    public int[] getPigletGenes() {
        Random rand = new Random();
        int[] pigletGenes = new int[GENES_LENGTH];

        for (int i = 0; i < genes.length; i = (i + 2)) {
            boolean thisOrMate = rand.nextBoolean();
            if (thisOrMate) {
                pigletGenes[i] = mitosisGenes[i];
                pigletGenes[i+1] = mateMitosisGenes[i+1];
            } else {
                pigletGenes[i] = mateMitosisGenes[i];
                pigletGenes[i+1] = mitosisGenes[i+1];
            }
        }

        return pigletGenes;
    }


    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld inWorld, DifficultyInstance difficulty, SpawnReason spawnReason, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT itemNbt) {
        livingdata = super.onInitialSpawn(inWorld, difficulty, spawnReason, livingdata, itemNbt);
        int[] spawnGenes;

        if (livingdata instanceof GroupData) {
            spawnGenes = ((GroupData) livingdata).groupGenes;
        } else {
            spawnGenes = createInitialGenes(inWorld);
            livingdata = new GroupData(spawnGenes);
        }

        this.genes = spawnGenes;
        setSharedGenes(genes);
        setPigSize();

        return livingdata;
    }

    private int[] createInitialGenes(IWorld inWorld) {
        int[] initialGenes = new int[GENES_LENGTH];
        //TODO create biome WTC variable [hot and dry biomes, hot and wet biomes, cold biomes] WTC is all others
        int wildType = 2;
        Biome biome = inWorld.getBiome(new BlockPos(this));

        if (biome.getDefaultTemperature() >= 0.9F) // hot and wet (jungle)
        {
            wildType = 1;
        }


        if (false) {
            return new int[] {4,3,3,3,2,2,1,2,1,1,1,1,3,3,1,1,3,3,1,1,12,27,5,31,20,28,19,20,14,39,3,28,2,2,2,2,2,2,2,2,1,1,2,1};
        } else {

            //Extension [ Dom.Black, Wildtype+, brindle, red ]
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[0] = (ThreadLocalRandom.current().nextInt(4) + 1);

            } else {
                initialGenes[0] = (2);
            }
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[1] = (ThreadLocalRandom.current().nextInt(4) + 1);

            } else {
                initialGenes[1] = (2);
            }

            //Agouti [ Black Enhancer, brown, wildtype? ]
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[2] = (ThreadLocalRandom.current().nextInt(1) + 1);

            } else {
                initialGenes[2] = (3);
            }
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[3] = (ThreadLocalRandom.current().nextInt(1) + 1);

            } else {
                initialGenes[3] = (3);
            }

            //Chinchilla [ chinchilla, wildtype ]
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[4] = (ThreadLocalRandom.current().nextInt(2) + 1);

            } else {
                initialGenes[4] = (2);
            }
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[5] = (ThreadLocalRandom.current().nextInt(2) + 1);

            } else {
                initialGenes[5] = (2);
            }

            //Subtle Dilute [ Wildtype+, dilute ]
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[6] = (ThreadLocalRandom.current().nextInt(2) + 1);

            } else {
                initialGenes[6] = (1);
            }
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[7] = (ThreadLocalRandom.current().nextInt(2) + 1);

            } else {
                initialGenes[7] = (1);
            }

            //Blue Dilute [ Dilute, wildtype+ ]
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[8] = (ThreadLocalRandom.current().nextInt(2) + 1);

            } else {
                initialGenes[8] = (1);
            }
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[9] = (ThreadLocalRandom.current().nextInt(2) + 1);

            } else {
                initialGenes[9] = (1);
            }

            //White Spots [ Wildtype+, spotted, roanSpots ]
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[10] = (ThreadLocalRandom.current().nextInt(3) + 1);

            } else {
                initialGenes[10] = (1);
            }
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[11] = (ThreadLocalRandom.current().nextInt(3) + 1);

            } else {
                initialGenes[11] = (1);
            }

            //Dom.White and Belted [ Dom.White, Belted, Wildtype+ ]
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[12] = (ThreadLocalRandom.current().nextInt(3) + 1);
                initialGenes[13] = (3);

            } else {
                initialGenes[12] = (3);
                initialGenes[13] = (3);
            }

            //Berkshire spots [ Wildtype+, tuxedo, berkshire ]
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[14] = (ThreadLocalRandom.current().nextInt(3) + 1);
                initialGenes[15] = (1);

            } else {
                initialGenes[14] = (1);
                initialGenes[15] = (1);
            }

            //White Extension [ Over marked, Medium, undermarked+ ]
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[16] = (ThreadLocalRandom.current().nextInt(3) + 1);

            } else {
                initialGenes[16] = (3);
            }
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[17] = (ThreadLocalRandom.current().nextInt(3) + 1);

            } else {
                initialGenes[17] = (3);
            }

            //face squash gene 1 [ Wildtype+, long, medium, short, squashed ]
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[18] = (ThreadLocalRandom.current().nextInt(5) + 1);

            } else {
                initialGenes[18] = (1);
            }
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[19] = (ThreadLocalRandom.current().nextInt(5) + 1);

            } else {
                initialGenes[19] = (1);
            }

            //inbreeding detector A
            initialGenes[20] = (ThreadLocalRandom.current().nextInt(20) + 1);
            initialGenes[21] = (ThreadLocalRandom.current().nextInt(20) + 20);

            //inbreeding detector B
            initialGenes[22] = (ThreadLocalRandom.current().nextInt(20) + 1);
            initialGenes[23] = (ThreadLocalRandom.current().nextInt(20) + 20);

            //inbreeding detector C
            initialGenes[24] = (ThreadLocalRandom.current().nextInt(20) + 1);
            initialGenes[25] = (ThreadLocalRandom.current().nextInt(20) + 20);

            //inbreeding detector D
            initialGenes[26] = (ThreadLocalRandom.current().nextInt(20) + 1);
            initialGenes[27] = (ThreadLocalRandom.current().nextInt(20) + 20);

            //inbreeding detector E
            initialGenes[28] = (ThreadLocalRandom.current().nextInt(20) + 1);
            initialGenes[29] = (ThreadLocalRandom.current().nextInt(20) + 20);

            //inbreeding detector F
            initialGenes[30] = (ThreadLocalRandom.current().nextInt(20) + 1);
            initialGenes[31] = (ThreadLocalRandom.current().nextInt(20) + 20);


            //Waddles [ waddles, wildtype ]
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[32] = (ThreadLocalRandom.current().nextInt(2) + 1);

            } else {
                initialGenes[32] = (2);
            }
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[33] = (ThreadLocalRandom.current().nextInt(2) + 1);

            } else {
                initialGenes[33] = (2);
            }

            //hair density [ furry, wildtype, sparse ]
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[34] = (ThreadLocalRandom.current().nextInt(3) + 1);

            } else {
                initialGenes[34] = (2);
            }
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[35] = (ThreadLocalRandom.current().nextInt(3) + 1);

            } else {
                initialGenes[35] = (2);
            }

            //baldness [ Bald, wildtype ]
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[36] = (ThreadLocalRandom.current().nextInt(2) + 1);

            } else {
                initialGenes[36] = (2);
            }
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[37] = (ThreadLocalRandom.current().nextInt(2) + 1);

            } else {
                initialGenes[37] = (2);
            }

            //wooly [ wooly, wildtype ]
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[38] = (ThreadLocalRandom.current().nextInt(2) + 1);

            } else {
                initialGenes[38] = (2);
            }
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[39] = (ThreadLocalRandom.current().nextInt(2) + 1);

            } else {
                initialGenes[39] = (2);
            }

            //thick hair [ thick hair+, less hair ]
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[40] = (ThreadLocalRandom.current().nextInt(2) + 1);

            } else {
                initialGenes[40] = (1);
            }
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[41] = (ThreadLocalRandom.current().nextInt(2) + 1);

            } else {
                initialGenes[41] = (1);
            }

            //face squash gene 2 [ longest, normal, short, shortest]
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[42] = (ThreadLocalRandom.current().nextInt(4) + 1);

            } else {
                initialGenes[42] = (1);
            }
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[43] = (ThreadLocalRandom.current().nextInt(4) + 1);

            } else {
                initialGenes[43] = (1);
            }
        }



        return initialGenes;
    }


    public void setGenes(int[] genes) {
        this.genes = genes;
    }

    public int[] getGenes() {
        return this.genes;
    }

    public static class GroupData implements ILivingEntityData {

        public int[] groupGenes;

        public GroupData(int[] groupGenes) {
            this.groupGenes = groupGenes;
        }

    }

}
