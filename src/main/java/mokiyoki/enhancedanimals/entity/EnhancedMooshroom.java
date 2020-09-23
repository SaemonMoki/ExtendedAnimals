package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.ai.general.EnhancedTemptGoal;
import mokiyoki.enhancedanimals.ai.general.cow.EnhancedAINurseFromMotherGoal;
import mokiyoki.enhancedanimals.ai.general.mooshroom.EnhancedWaterAvoidingRandomWalkingEatingGoalMooshroom;
import mokiyoki.enhancedanimals.entity.Genetics.CowGeneticsInitialiser;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SuspiciousStewItem;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.tuple.Pair;

import java.util.UUID;

import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_COW;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_MOOSHROOM;

public class EnhancedMooshroom extends EnhancedCow implements net.minecraftforge.common.IShearable {
    private static final DataParameter<String> MOOSHROOM_TYPE = EntityDataManager.createKey(EnhancedMooshroom.class, DataSerializers.STRING);

    private static final String[] MOOSHROOM_MUSHROOM = new String[] {
            "red_mushroom.png", "brown_mushroom.png", "yellow_flower.png"
    };

    protected EnhancedMooshroom.Type mateMushroomType;
    private Effect hasStewEffect;
    private int effectDuration;
    /** Stores the UUID of the most recent lightning bolt to strike */
    private UUID lightningUUID;

    public EnhancedMooshroom(EntityType<? extends EnhancedCow> entityType, World worldIn) {
        super(entityType, worldIn);
        this.mateMushroomType = Type.RED;
    }

    public void onStruckByLightning(LightningBoltEntity lightningBolt) {
        UUID uuid = lightningBolt.getUniqueID();
        if (!uuid.equals(this.lightningUUID)) {
            this.setMooshroomType(this.getMooshroomType() == EnhancedMooshroom.Type.RED ? EnhancedMooshroom.Type.BROWN : EnhancedMooshroom.Type.RED);
            this.lightningUUID = uuid;
            this.playSound(SoundEvents.ENTITY_MOOSHROOM_CONVERT, 2.0F, 1.0F);
            this.toggleReloadTexture();
        }

    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(MOOSHROOM_TYPE, EnhancedMooshroom.Type.RED.name);
    }

    @Override
    protected String getSpecies() {
        return I18n.format("entity.eanimod.enhanced_mooshroom");
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
    protected void createAndSpawnEnhancedChild(World inWorld) {
        EnhancedMooshroom enhancedmooshroom = ENHANCED_MOOSHROOM.create(this.world);
        Genes babyGenes = new Genes(this.genetics).makeChild(this.getIsFemale(), this.mateGender, this.mateGenetics);
        enhancedmooshroom.setMooshroomType(this.setChildMushroomType((enhancedmooshroom)));
        defaultCreateAndSpawn(enhancedmooshroom, inWorld, babyGenes, -84000);
        enhancedmooshroom.setMotherUUID(this.getUniqueID().toString());
        enhancedmooshroom.configureAI();
        this.world.addEntity(enhancedmooshroom);
    }

    public boolean processInteract(PlayerEntity entityPlayer, Hand hand) {
        ItemStack itemstack = entityPlayer.getHeldItem(hand);
        if (itemstack.getItem() == Items.BOWL && this.getGrowingAge() >= 0 && !entityPlayer.abilities.isCreativeMode && getEntityStatus().equals(EntityState.MOTHER.toString())) {
            int milk = getMilkAmount();
            if (milk <= 3) {
                entityPlayer.playSound(SoundEvents.ENTITY_COW_HURT, 1.0F, 1.0F);
            } else {
                itemstack.shrink(1);
                setMilkAmount(milk - 3);
                boolean flag = false;
                ItemStack itemstack1;
                if (this.hasStewEffect != null) {
                    flag = true;
                    itemstack1 = new ItemStack(Items.SUSPICIOUS_STEW);
                    SuspiciousStewItem.addEffect(itemstack1, this.hasStewEffect, this.effectDuration);
                    this.hasStewEffect = null;
                    this.effectDuration = 0;
                } else {
                    itemstack1 = new ItemStack(Items.MUSHROOM_STEW);
                }

                if (itemstack.isEmpty()) {
                    entityPlayer.setHeldItem(hand, itemstack1);
                } else if (!entityPlayer.inventory.addItemStackToInventory(itemstack1)) {
                    entityPlayer.dropItem(itemstack1, false);
                }

                SoundEvent soundevent;
                if (flag) {
                    soundevent = SoundEvents.ENTITY_MOOSHROOM_SUSPICIOUS_MILK;
                } else {
                    soundevent = SoundEvents.ENTITY_MOOSHROOM_MILK;
                }
                this.playSound(soundevent, 1.0F, 1.0F);
            }
                return true;

        } else {
            if (this.getMooshroomType() == EnhancedMooshroom.Type.BROWN && itemstack.getItem().isIn(ItemTags.SMALL_FLOWERS)) {
                if (this.hasStewEffect != null) {
                    for(int i = 0; i < 2; ++i) {
                        this.world.addParticle(ParticleTypes.SMOKE, this.getPosX() + (double)(this.rand.nextFloat() / 2.0F), this.getPosY() + (double)(this.getHeight() / 2.0F), this.getPosZ() + (double)(this.rand.nextFloat() / 2.0F), 0.0D, (double)(this.rand.nextFloat() / 5.0F), 0.0D);
                    }
                } else {
                    Pair<Effect, Integer> pair = this.getStewEffect(itemstack);
                    if (!entityPlayer.abilities.isCreativeMode) {
                        itemstack.shrink(1);
                    }

                    for(int j = 0; j < 4; ++j) {
                        this.world.addParticle(ParticleTypes.EFFECT, this.getPosX() + (double)(this.rand.nextFloat() / 2.0F), this.getPosY() + (double)(this.getHeight() / 2.0F), this.getPosZ() + (double)(this.rand.nextFloat() / 2.0F), 0.0D, (double)(this.rand.nextFloat() / 5.0F), 0.0D);
                    }

                    this.hasStewEffect = pair.getLeft();
                    this.effectDuration = pair.getRight();
                    this.playSound(SoundEvents.ENTITY_MOOSHROOM_EAT, 2.0F, 1.0F);
                }
            }

            return super.processInteract(entityPlayer, hand);
        }
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putString("Type", this.getMooshroomType().name);
        compound.putString("MateType", this.mateMushroomType.name);
        if (this.hasStewEffect != null) {
            compound.putByte("EffectId", (byte)Effect.getId(this.hasStewEffect));
            compound.putInt("EffectDuration", this.effectDuration);
        }

    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setMooshroomType(EnhancedMooshroom.Type.getTypeByName(compound.getString("Type")));
        this.mateMushroomType = (EnhancedMooshroom.Type.getTypeByName(compound.getString("MateType")));
        if (compound.contains("EffectId", 1)) {
            this.hasStewEffect = Effect.get(compound.getByte("EffectId"));
        }

        if (compound.contains("EffectDuration", 3)) {
            this.effectDuration = compound.getInt("EffectDuration");
        }

    }

    @Override
    protected void configureAI() {
        if (!aiConfigured) {
            Double speed = 1.0D;
            this.goalSelector.addGoal(1, new PanicGoal(this, speed*1.5D));
            this.goalSelector.addGoal(2, new BreedGoal(this, speed));
            this.goalSelector.addGoal(3, new EnhancedTemptGoal(this, speed*1.25D, false, Ingredient.fromItems(Items.CARROT_ON_A_STICK)));
            this.goalSelector.addGoal(3, new EnhancedTemptGoal(this, speed*1.25D,false, TEMPTATION_ITEMS));
            this.goalSelector.addGoal(4, new FollowParentGoal(this, speed*1.25D));
            this.goalSelector.addGoal(4, new EnhancedAINurseFromMotherGoal(this, motherUUID, speed*1.25D));
            wanderEatingGoal = new EnhancedWaterAvoidingRandomWalkingEatingGoalMooshroom(this, speed, 7, 0.001F, 120, 2, 20);
            this.goalSelector.addGoal(6, wanderEatingGoal);
        }
        aiConfigured = true;
    }


    private Pair<Effect, Integer> getStewEffect(ItemStack p_213443_1_) {
        FlowerBlock flowerblock = (FlowerBlock)((BlockItem)p_213443_1_.getItem()).getBlock();
        return Pair.of(flowerblock.getStewEffect(), flowerblock.getStewEffectDuration());
    }

    private void setMooshroomType(EnhancedMooshroom.Type typeIn) {
        this.dataManager.set(MOOSHROOM_TYPE, typeIn.name);
    }

    public EnhancedMooshroom.Type getMooshroomType() {
        return EnhancedMooshroom.Type.getTypeByName(this.dataManager.get(MOOSHROOM_TYPE));
    }

    public EnhancedMooshroom createChild(AgeableEntity ageable) {
        super.createChild(ageable);
        return null;
    }

    private EnhancedMooshroom.Type setChildMushroomType(EnhancedMooshroom fatherMooshroom) {
        EnhancedMooshroom.Type EnhancedMooshroom$type = this.getMooshroomType();
        EnhancedMooshroom.Type EnhancedMooshroom$type1 = fatherMooshroom.getMooshroomType();
        EnhancedMooshroom.Type EnhancedMooshroom$type2;
        if (EnhancedMooshroom$type == EnhancedMooshroom$type1 && this.rand.nextInt(1024) == 0) {
            EnhancedMooshroom$type2 = EnhancedMooshroom$type == EnhancedMooshroom.Type.BROWN ? EnhancedMooshroom.Type.RED : EnhancedMooshroom.Type.BROWN;
        } else {
            EnhancedMooshroom$type2 = this.rand.nextBoolean() ? EnhancedMooshroom$type : EnhancedMooshroom$type1;
        }

        return EnhancedMooshroom$type2;
    }

    @Override
    public boolean isShearable(ItemStack item, net.minecraft.world.IWorldReader world, net.minecraft.util.math.BlockPos pos) {
        return this.getGrowingAge() >= 0;
    }

    @Override
    public java.util.List<ItemStack> onSheared(ItemStack item, net.minecraft.world.IWorld world, net.minecraft.util.math.BlockPos pos, int fortune) {
        java.util.List<ItemStack> ret = new java.util.ArrayList<>();
        this.world.addParticle(ParticleTypes.EXPLOSION, this.getPosX(), this.getPosY() + (double)(this.getHeight() / 2.0F), this.getPosZ(), 0.0D, 0.0D, 0.0D);
        if (!this.world.isRemote) {
            this.remove();
            EnhancedCow enhancedcow = ENHANCED_COW.create(this.world);
            enhancedcow.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), (this.rotationYaw), this.rotationPitch);
            enhancedcow.setHealth(this.getHealth());
            enhancedcow.renderYawOffset = this.renderYawOffset;

            enhancedcow.setGenes(this.getGenes());
            enhancedcow.setSharedGenes(this.getGenes());
            enhancedcow.setCowSize();
            enhancedcow.setGrowingAge(this.growingAge);
            enhancedcow.setEntityStatus(this.getEntityStatus());
            enhancedcow.configureAI();
            enhancedcow.setMooshroomUUID(this.getCachedUniqueIdString());

            if (this.hasCustomName()) {
                enhancedcow.setCustomName(this.getCustomName());
            }
            this.world.addEntity(enhancedcow);
            if (this.getMooshroomType() == Type.RED) {
                for(int i = 0; i < 5; ++i) {
                    ret.add(new ItemStack(Blocks.RED_MUSHROOM));
                }
            } else {
                for(int i = 0; i < 5; ++i) {
                    ret.add(new ItemStack(Blocks.BROWN_MUSHROOM));
                }
            }
            this.playSound(SoundEvents.ENTITY_MOOSHROOM_SHEAR, 1.0F, 1.0F);
        }
        return ret;
    }

    public static enum Type {
        RED("red", Blocks.RED_MUSHROOM.getDefaultState()),
        BROWN("brown", Blocks.BROWN_MUSHROOM.getDefaultState());

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
        if (this.colouration.getPheomelaninColour() == -1 || this.colouration.getMelaninColour() == -1) {
            this.colouration = super.getRgb();

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
    protected Genes createInitialGenes(IWorld world, BlockPos pos, boolean isDomestic) {
        Genes mooshroomGenetics = new CowGeneticsInitialiser().generateNewGenetics(world, pos, isDomestic);
        mooshroomGenetics.setAutosomalGene(118, 2);
        mooshroomGenetics.setAutosomalGene(119, 2);
        return mooshroomGenetics;
    }
}
