package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.ai.general.cow.EnhancedAINurseFromMotherGoal;
import mokiyoki.enhancedanimals.ai.general.mooshroom.EnhancedWaterAvoidingRandomWalkingEatingGoalMooshroom;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SuspiciousStewItem;
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

    private Effect hasStewEffect;
    private int effectDuration;
    /** Stores the UUID of the most recent lightning bolt to strike */
    private UUID lightningUUID;
    private float[] cowColouration = null;

    public EnhancedMooshroom(EntityType<? extends EnhancedCow> entityType, World worldIn) {
        super(entityType, worldIn);
    }

    public void onStruckByLightning(LightningBoltEntity lightningBolt) {
        UUID uuid = lightningBolt.getUniqueID();
        if (!uuid.equals(this.lightningUUID)) {
            this.setMooshroomType(this.getMooshroomType() == EnhancedMooshroom.Type.RED ? EnhancedMooshroom.Type.BROWN : EnhancedMooshroom.Type.RED);
            this.lightningUUID = uuid;
            this.playSound(SoundEvents.ENTITY_MOOSHROOM_CONVERT, 2.0F, 1.0F);
        }

    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(MOOSHROOM_TYPE, EnhancedMooshroom.Type.RED.name);
    }

    @Override
    protected void setTexturePaths() {
        super.setTexturePaths();
        int mushroomType = 0;

        if (getMooshroomType().name.equals("brown")) {
            mushroomType = 1;
        }

        this.cowTextures.add(MOOSHROOM_MUSHROOM[0]);
    }

    @Override
    protected void createAndSpawnEnhancedChild(World inWorld) {
        EnhancedMooshroom enhancedmooshroom = ENHANCED_MOOSHROOM.create(this.world);
        int[] babyGenes = getCalfGenes(this.mitosisGenes, this.mateMitosisGenes);
        enhancedmooshroom.setGenes(babyGenes);
        enhancedmooshroom.setSharedGenes(babyGenes);
        enhancedmooshroom.setCowSize();
        enhancedmooshroom.setGrowingAge(-84000);

        enhancedmooshroom.setBirthTime(String.valueOf(inWorld.getGameTime()));
        enhancedmooshroom.setCowStatus(EntityState.CHILD_STAGE_ONE.toString());
        enhancedmooshroom.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, 0.0F);
        enhancedmooshroom.setMotherUUID(this.getUniqueID().toString());
        enhancedmooshroom.configureAI();
        this.world.addEntity(enhancedmooshroom);
    }

    public boolean processInteract(PlayerEntity entityPlayer, Hand hand) {
        ItemStack itemstack = entityPlayer.getHeldItem(hand);
        if (itemstack.getItem() == Items.BOWL && this.getGrowingAge() >= 0 && !entityPlayer.abilities.isCreativeMode && getCowStatus().equals(EntityState.MOTHER.toString())) {
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
            this.goalSelector.addGoal(3, new TemptGoal(this, speed*1.25D, TEMPTATION_ITEMS, false));
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
//        EnhancedMooshroom EnhancedMooshroom = ENHANCED_MOOSHROOM.create(this.world);
//        EnhancedMooshroom.setMooshroomType(this.func_213445_a((EnhancedMooshroom)ageable));
        return null;
    }

    private EnhancedMooshroom.Type func_213445_a(EnhancedMooshroom p_213445_1_) {
        EnhancedMooshroom.Type EnhancedMooshroom$type = this.getMooshroomType();
        EnhancedMooshroom.Type EnhancedMooshroom$type1 = p_213445_1_.getMooshroomType();
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
            enhancedcow.setCowStatus(this.getCowStatus());
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
    public float[] getRgb() {
        if (cowColouration == null) {
            cowColouration = new float[6];
            int[] genesForText = getSharedGenes();

            float blackR = 148.0F;
            float blackG = 14.0F;
            float blackB = 15.0F;

            float redR = 126.0F;
            float redG = 96.0F;
            float redB = 96.0F;

            if (getMooshroomType().name.equals("red")) {

                int tint;

                if ((genesForText[6] == 1 || genesForText[7] == 1) || (genesForText[0] == 3 && genesForText[1] == 3)){
                    //red
                    tint = 4;
                }else {
                    if (genesForText[0] == 1 || genesForText[1] == 1) {
                        //black
                        tint = 2;
                    } else {
                        //wildtype
                        tint = 3;
                    }
                }

                //standard dilution
                if (genesForText[2] == 2 || genesForText[3] == 2) {
//            if (true) {
                    if (genesForText[2] == 2 && genesForText[3] == 2) {
//                if (false) {

                        blackR = (blackR + (255F * tint)) / (tint+1);
                        blackG = (blackG + (245F * tint)) / (tint+1);
                        blackB = (blackB + (235F * tint)) / (tint+1);

                        if (tint != 2) {
                            redR = (redR + (255F * tint)) / (tint + 1);
                            redG = (redG + (255F * tint)) / (tint + 1);
                            redB = (redB + (255F * tint)) / (tint + 1);
                        }
                    }else{
                        if (tint == 3) {
                            //wildtype
                            redR = 126.0F;
                            // 160.5
                            redG = 96.0F;
                            // 119
                            redB = 96.0F;
                            // 67

                            if (genesForText[4] == 1 || genesForText[5] == 1) {
                                if (genesForText[4] == 1 && genesForText[5] == 1) {
                                    blackR = 236.0F;
                                    blackG = 6.0F;
                                    blackB = 6.0F;
                                } else {
                                    blackR = 236.0F;
                                    blackG = 6.0F;
                                    blackB = 6.0F;
                                }
                            } else if (genesForText[4] == 4 && genesForText[5] == 4) {
                                blackR = 185.0F;
                                blackG = 40.0F;
                                blackB = 40.0F;
                            }

                        } else if (tint == 4){
                            //red
                            redR = (redR*0.5F) + (187.0F*0.5F);
                            redG = (redG*0.5F) + (180.0F*0.5F);
                            redB = (redB*0.5F) + (166.0F*0.5F);
                        }else {
                            //black
                            blackR = 236.0F;
                            blackG = 6.0F;
                            blackB = 6.0F;
                        }
                    }
                }

            } else {

                blackR = 106.0F;
                blackG = 78.0F;
                blackB = 59.0F;

                redR = 204.0F;
                redG = 153.0F;
                redB = 120.0F;

                int tint;

                if ((genesForText[6] == 1 || genesForText[7] == 1) || (genesForText[0] == 3 && genesForText[1] == 3)){
                    //red
                    tint = 4;
                }else {
                    if (genesForText[0] == 1 || genesForText[1] == 1) {
                        //black
                        tint = 2;
                    } else {
                        //wildtype
                        tint = 3;
                    }
                }

                //standard dilution
                if (genesForText[2] == 2 || genesForText[3] == 2) {
//            if (true) {
                    if (genesForText[2] == 2 && genesForText[3] == 2) {
//                if (false) {

                        blackR = (blackR + (255F * tint)) / (tint+1);
                        blackG = (blackG + (245F * tint)) / (tint+1);
                        blackB = (blackB + (235F * tint)) / (tint+1);

                        if (tint != 2) {
                            redR = (redR + (255F * tint)) / (tint + 1);
                            redG = (redG + (255F * tint)) / (tint + 1);
                            redB = (redB + (255F * tint)) / (tint + 1);
                        }
                    }else{
                        if (tint == 3) {
                            //wildtype
                            redR = 170.5F;
                            // 160.5
                            redG = 140.0F;
                            // 119
                            redB = 132.0F;
                            // 67

                            if (genesForText[4] == 1 || genesForText[5] == 1) {
                                if (genesForText[4] == 1 && genesForText[5] == 1) {
                                    blackR = 236.0F;
                                    blackG = 6.0F;
                                    blackB = 6.0F;
                                } else {
                                    blackR = 236.0F;
                                    blackG = 6.0F;
                                    blackB = 6.0F;
                                }
                            } else if (genesForText[4] == 4 && genesForText[5] == 4) {
                                blackR = 185.0F;
                                blackG = 40.0F;
                                blackB = 40.0F;
                            }

                        } else if (tint == 4){
                            //red
                            redR = (redR*0.5F) + (187.0F*0.5F);
                            redG = (redG*0.5F) + (180.0F*0.5F);
                            redB = (redB*0.5F) + (166.0F*0.5F);
                        }else {
                            //black
                            blackR = 236.0F;
                            blackG = 6.0F;
                            blackB = 6.0F;
                        }
                    }
                }

            }

            if (genesForText[4] == 3 || genesForText[5] == 3) {
                redR = (redR + 245F) / 2;
                redG = (redG + 237F) / 2;
                redB = (redB + 222F) / 2;
            }

            //chocolate
            if (genesForText[10] == 2 && genesForText[11] == 2){
                blackR = blackR + 25F;
                blackG = blackG + 15F;
                blackB = blackB + 9F;

                redR = redR + 25F;
                redG = redG + 15F;
                redB = redB + 9F;
            }

            if (this.isChild()) {
                if (getCowStatus().equals(EntityState.CHILD_STAGE_ONE.toString())) {
                    blackR = redR;
                    blackG = redG;
                    blackB = redB;
                }else if (getCowStatus().equals(EntityState.CHILD_STAGE_TWO.toString())) {
                    blackR = (blackR + redR)/2F;
                    blackG = (blackG + redG)/2F;
                    blackB = (blackB + redB)/2F;
                } else {
                    blackR = blackR*0.75F + redR*0.25F;
                    blackG = blackG*0.75F + redG*0.25F;
                    blackB = blackB*0.75F + redB*0.25F;
                }
            }

            //TODO TEMP AF
            //black
            cowColouration[0] = blackR;
            cowColouration[1] = blackG;
            cowColouration[2] = blackB;

            //red
            cowColouration[3] = redR;
            cowColouration[4] = redG;
            cowColouration[5] = redB;

            for (int i = 0; i <= 5; i++) {
                if (cowColouration[i] > 255.0F) {
                    cowColouration[i] = 255.0F;
                }
                cowColouration[i] = cowColouration[i] / 255.0F;
            }

        }
        return cowColouration;
    }
}
