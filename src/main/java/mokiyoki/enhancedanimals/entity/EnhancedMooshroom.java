package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.ai.general.EnhancedBreedGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedLookAtGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedLookRandomlyGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedPanicGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedTemptGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedWanderingGoal;
import mokiyoki.enhancedanimals.ai.general.SeekShelterGoal;
import mokiyoki.enhancedanimals.ai.general.StayShelteredGoal;
import mokiyoki.enhancedanimals.ai.general.mooshroom.GrazingGoalMooshroom;
import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
import mokiyoki.enhancedanimals.entity.Genetics.CowGeneticsInitialiser;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SuspiciousStewItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Random;
import java.util.UUID;

import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_COW;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_MOOSHROOM;

public class EnhancedMooshroom extends EnhancedCow implements net.minecraftforge.common.IForgeShearable {
    private static final EntityDataAccessor<String> MOOSHROOM_TYPE = SynchedEntityData.defineId(EnhancedMooshroom.class, EntityDataSerializers.STRING);

    private static final String[] MOOSHROOM_MUSHROOM = new String[] {
            "red_mushroom.png", "brown_mushroom.png", "yellow_flower.png"
    };

    protected EnhancedMooshroom.Type mateMushroomType;
    private MobEffect hasStewEffect;
    private int effectDuration;
    /** Stores the UUID of the most recent lightning bolt to strike */
    private UUID lightningUUID;

    public EnhancedMooshroom(EntityType<? extends EnhancedCow> entityType, Level worldIn) {
        super(entityType, worldIn);
        this.mateMushroomType = Type.RED;
    }

    public static boolean canMooshroomSpawn(EntityType<EnhancedMooshroom> entityType, LevelAccessor world, MobSpawnType reason, BlockPos blockPos, Random random) {
        return world.getBlockState(blockPos.below()).is(Blocks.MYCELIUM) && world.getRawBrightness(blockPos, 0) > 8;
    }

    @Override
    public void thunderHit(ServerLevel serverWorld, LightningBolt lightningBolt) {
        UUID uuid = lightningBolt.getUUID();
        if (!uuid.equals(this.lightningUUID)) {
            this.setMooshroomType(this.getMooshroomType() == EnhancedMooshroom.Type.RED ? EnhancedMooshroom.Type.BROWN : EnhancedMooshroom.Type.RED);
            this.lightningUUID = uuid;
            this.playSound(SoundEvents.MOOSHROOM_CONVERT, 2.0F, 1.0F);
            this.toggleReloadTexture();
        }
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(MOOSHROOM_TYPE, EnhancedMooshroom.Type.RED.name);
    }

    @Override
    protected String getSpecies() {
        return "entity.eanimod.enhanced_mooshroom";
    }

    @Override
    protected int getAdultAge() { return EanimodCommonConfig.COMMON.adultAgeMooshroom.get();}

    @Override
    protected int gestationConfig() {
        return EanimodCommonConfig.COMMON.gestationDaysMooshroom.get();
    }

    @Override
    protected void setTexturePaths() {
        super.setTexturePaths();
        int mushroomType = 0;

        if (getMooshroomType().name.equals("brown")) {
            mushroomType = 1;
        }

        addTextureToAnimal(MOOSHROOM_MUSHROOM, mushroomType, null);
    }

    @Override
    protected void createAndSpawnEnhancedChild(Level inWorld) {
        EnhancedMooshroom enhancedmooshroom = ENHANCED_MOOSHROOM.get().create(this.level);
        Genes babyGenes = new Genes(this.genetics).makeChild(this.getOrSetIsFemale(), this.mateGender, this.mateGenetics);
        enhancedmooshroom.setMooshroomType(this.setChildMushroomType((enhancedmooshroom)));
        defaultCreateAndSpawn(enhancedmooshroom, inWorld, babyGenes, -this.getAdultAge());
        enhancedmooshroom.configureAI();
        this.level.addFreshEntity(enhancedmooshroom);
    }

    @Override
    public InteractionResult mobInteract(Player entityPlayer, InteractionHand hand) {
        ItemStack itemstack = entityPlayer.getItemInHand(hand);
        if (itemstack.getItem() == Items.BOWL && !this.isBaby() && !entityPlayer.getAbilities().instabuild && getEntityStatus().equals(EntityState.MOTHER.toString())) {
            int milk = getMilkAmount();
            if (milk <= 3) {
                entityPlayer.playSound(SoundEvents.COW_HURT, 1.0F, 1.0F);
            } else {
                itemstack.shrink(1);
                setMilkAmount(milk - 3);
                boolean flag = false;
                ItemStack itemstack1;
                if (this.hasStewEffect != null) {
                    flag = true;
                    itemstack1 = new ItemStack(Items.SUSPICIOUS_STEW);
                    SuspiciousStewItem.saveMobEffect(itemstack1, this.hasStewEffect, this.effectDuration);
                    this.hasStewEffect = null;
                    this.effectDuration = 0;
                } else {
                    itemstack1 = new ItemStack(Items.MUSHROOM_STEW);
                }

                if (itemstack.isEmpty()) {
                    entityPlayer.setItemInHand(hand, itemstack1);
                } else if (!entityPlayer.getInventory().add(itemstack1)) {
                    entityPlayer.drop(itemstack1, false);
                }

                SoundEvent soundevent;
                if (flag) {
                    soundevent = SoundEvents.MOOSHROOM_MILK_SUSPICIOUSLY;
                } else {
                    soundevent = SoundEvents.MOOSHROOM_MILK;
                }
                this.playSound(soundevent, 1.0F, 1.0F);
            }
                return InteractionResult.SUCCESS;

        } else {
            if (this.getMooshroomType() == EnhancedMooshroom.Type.BROWN && itemstack.is(ItemTags.SMALL_FLOWERS)) {
                if (this.hasStewEffect != null) {
                    for(int i = 0; i < 2; ++i) {
                        this.level.addParticle(ParticleTypes.SMOKE, this.getX() + (double)(this.random.nextFloat() / 2.0F), this.getY() + (double)(this.getBbHeight() / 2.0F), this.getZ() + (double)(this.random.nextFloat() / 2.0F), 0.0D, (double)(this.random.nextFloat() / 5.0F), 0.0D);
                    }
                } else {
                    Pair<MobEffect, Integer> pair = this.getStewEffect(itemstack);
                    if (!entityPlayer.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }

                    for(int j = 0; j < 4; ++j) {
                        this.level.addParticle(ParticleTypes.EFFECT, this.getX() + (double)(this.random.nextFloat() / 2.0F), this.getY() + (double)(this.getBbHeight() / 2.0F), this.getZ() + (double)(this.random.nextFloat() / 2.0F), 0.0D, (double)(this.random.nextFloat() / 5.0F), 0.0D);
                    }

                    this.hasStewEffect = pair.getLeft();
                    this.effectDuration = pair.getRight();
                    this.playSound(SoundEvents.MOOSHROOM_EAT, 2.0F, 1.0F);
                }
            }

            return super.mobInteract(entityPlayer, hand);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("Type", this.getMooshroomType().name);
        compound.putString("MateType", this.mateMushroomType.name);
        if (this.hasStewEffect != null) {
            compound.putByte("EffectId", (byte)MobEffect.getId(this.hasStewEffect));
            compound.putInt("EffectDuration", this.effectDuration);
        }

    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setMooshroomType(EnhancedMooshroom.Type.getTypeByName(compound.getString("Type")));
        this.mateMushroomType = (EnhancedMooshroom.Type.getTypeByName(compound.getString("MateType")));
        if (compound.contains("EffectId", 1)) {
            this.hasStewEffect = MobEffect.byId(compound.getByte("EffectId"));
        }

        if (compound.contains("EffectDuration", 3)) {
            this.effectDuration = compound.getInt("EffectDuration");
        }

    }

    @Override
    protected void configureAI() {
        if (!aiConfigured) {
            Double speed = 1.0D;
            this.goalSelector.addGoal(0, new FloatGoal(this));
            this.goalSelector.addGoal(1, new EnhancedPanicGoal(this, speed*1.5D));
            this.goalSelector.addGoal(2, new EnhancedBreedGoal(this, speed));
            this.goalSelector.addGoal(3, new EnhancedTemptGoal(this, speed, speed*1.25D, false, Items.CARROT_ON_A_STICK));
            this.goalSelector.addGoal(3, new EnhancedTemptGoal(this, speed,speed*1.25D, false, Items.AIR));
            this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
//            this.goalSelector.addGoal(4, new EnhancedFollowParentGoal(this, this.parent,speed*1.25D));
//            this.goalSelector.addGoal(4, new EnhancedAINurseFromMotherGoal(this, this.parent, speed*1.25D));
            this.goalSelector.addGoal(5, new StayShelteredGoal(this, 5723, 7000, 0));
            this.goalSelector.addGoal(6, new SeekShelterGoal(this, 1.0D, 5723, 7000, 0));
            grazingGoal = new GrazingGoalMooshroom(this, speed);
            this.goalSelector.addGoal(6, grazingGoal);
            this.goalSelector.addGoal(7, new EnhancedWanderingGoal(this, speed));
            this.goalSelector.addGoal(8, new EnhancedLookAtGoal(this, Player.class, 10.0F));
            this.goalSelector.addGoal(9, new EnhancedLookRandomlyGoal(this));
        }
        aiConfigured = true;
    }


    private Pair<MobEffect, Integer> getStewEffect(ItemStack p_213443_1_) {
        FlowerBlock flowerblock = (FlowerBlock)((BlockItem)p_213443_1_.getItem()).getBlock();
        return Pair.of(flowerblock.getSuspiciousStewEffect(), flowerblock.getEffectDuration());
    }

    public void setMooshroomType(EnhancedMooshroom.Type typeIn) {
        this.entityData.set(MOOSHROOM_TYPE, typeIn.name);
    }

    public EnhancedMooshroom.Type getMooshroomType() {
        return EnhancedMooshroom.Type.getTypeByName(this.entityData.get(MOOSHROOM_TYPE));
    }

    @Override
    public EnhancedMooshroom getBreedOffspring(ServerLevel serverWorld, AgeableMob ageable) {
        super.getBreedOffspring(serverWorld, ageable);
        return null;
    }

    private EnhancedMooshroom.Type setChildMushroomType(EnhancedMooshroom fatherMooshroom) {
        EnhancedMooshroom.Type EnhancedMooshroom$type = this.getMooshroomType();
        EnhancedMooshroom.Type EnhancedMooshroom$type1 = fatherMooshroom.getMooshroomType();
        EnhancedMooshroom.Type EnhancedMooshroom$type2;
        if (EnhancedMooshroom$type == EnhancedMooshroom$type1 && this.random.nextInt(1024) == 0) {
            EnhancedMooshroom$type2 = EnhancedMooshroom$type == EnhancedMooshroom.Type.BROWN ? EnhancedMooshroom.Type.RED : EnhancedMooshroom.Type.BROWN;
        } else {
            EnhancedMooshroom$type2 = this.random.nextBoolean() ? EnhancedMooshroom$type : EnhancedMooshroom$type1;
        }

        return EnhancedMooshroom$type2;
    }

    @Override
    public boolean isShearable(ItemStack item, Level world, net.minecraft.core.BlockPos pos) {
        return !this.isBaby();
    }

    @Override
    public java.util.List<ItemStack> onSheared(Player playerEntity, ItemStack item, Level world, net.minecraft.core.BlockPos pos, int fortune) {
        java.util.List<ItemStack> ret = new java.util.ArrayList<>();
        this.level.addParticle(ParticleTypes.EXPLOSION, this.getX(), this.getY() + (double)(this.getBbHeight() / 2.0F), this.getZ(), 0.0D, 0.0D, 0.0D);
        if (!this.level.isClientSide) {
            this.remove(RemovalReason.DISCARDED);
            EnhancedCow enhancedcow = ENHANCED_COW.get().create(this.level);
            enhancedcow.moveTo(this.getX(), this.getY(), this.getZ(), (this.getYRot()), this.getXRot());
            enhancedcow.initializeHealth(this, 0.0F);
            enhancedcow.setHealth(this.getHealth());
            enhancedcow.yBodyRot = this.yBodyRot;

            enhancedcow.setGenes(this.getGenes());
            enhancedcow.setSharedGenes(this.getGenes());
            enhancedcow.initilizeAnimalSize();
            enhancedcow.setAge(this.age);
            enhancedcow.setEntityStatus(this.getEntityStatus());
            enhancedcow.configureAI();
            enhancedcow.setMooshroomUUID(this.getStringUUID());
            enhancedcow.setBirthTime(this.getBirthTime());

            if (this.hasCustomName()) {
                enhancedcow.setCustomName(this.getCustomName());
            }
            this.level.addFreshEntity(enhancedcow);
            if (this.getMooshroomType() == Type.RED) {
                for(int i = 0; i < 5; ++i) {
                    ret.add(new ItemStack(Blocks.RED_MUSHROOM));
                }
            } else {
                for(int i = 0; i < 5; ++i) {
                    ret.add(new ItemStack(Blocks.BROWN_MUSHROOM));
                }
            }
            this.playSound(SoundEvents.MOOSHROOM_SHEAR, 1.0F, 1.0F);
        }
        return ret;
    }

    public static enum Type {
        RED("red", Blocks.RED_MUSHROOM.defaultBlockState()),
        BROWN("brown", Blocks.BROWN_MUSHROOM.defaultBlockState());

        private final String name;
        private final BlockState renderState;

        private Type(String nameIn, BlockState renderStateIn) {
            this.name = nameIn;
            this.renderState = renderStateIn;
        }

        /**
         * A block state that is rendered on the back of the mooshroom.
         */
        @OnlyIn(Dist.CLIENT)
        public BlockState getRenderState() {
            return this.renderState;
        }

        private static EnhancedMooshroom.Type getTypeByName(String nameIn) {
            for(EnhancedMooshroom.Type EnhancedMooshroom$type : values()) {
                if (EnhancedMooshroom$type.name.equals(nameIn)) {
                    return EnhancedMooshroom$type;
                }
            }

            return RED;
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public Colouration getRgb() {
        boolean flag = this.colouration.getPheomelaninColour() == -1 || this.colouration.getMelaninColour() == -1;
        this.colouration = super.getRgb();

        if(this.colouration == null) {
            return null;
        }

        if (flag) {
            float[] melanin = Colouration.getHSBFromABGR(this.colouration.getMelaninColour());
            float[] pheomelanin = Colouration.getHSBFromABGR(this.colouration.getPheomelaninColour());

            if (getMooshroomType() == Type.RED) {
                melanin[0] = 0;
                melanin[1] = 1.0F;

                pheomelanin[0] = (pheomelanin[0] - 0.05F) * 0.5F;
                pheomelanin[1] = pheomelanin[1] + 0.25F;
            } else {
                melanin[1] = melanin[1] + 0.25F;
                melanin[2] = melanin[2] + 0.1F;

                pheomelanin[1] = pheomelanin[1] * 0.75F;
                pheomelanin[2] = pheomelanin[2] + 0.1F;
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
            }

            this.colouration.setMelaninColour(Colouration.HSBtoABGR(melanin[0], melanin[1], melanin[2]));
            this.colouration.setPheomelaninColour(Colouration.HSBtoABGR(pheomelanin[0], pheomelanin[1], pheomelanin[2]));
        }

        return this.colouration;
    }

    @Override
    protected Genes createInitialGenes(LevelAccessor world, BlockPos pos, boolean isDomestic) {
        Genes mooshroomGenetics = new CowGeneticsInitialiser().generateNewGenetics(world, pos, isDomestic);
        mooshroomGenetics.setAutosomalGene(118, 2);
        mooshroomGenetics.setAutosomalGene(119, 2);
        return mooshroomGenetics;
    }
}
