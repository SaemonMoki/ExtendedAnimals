package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.config.GeneticAnimalsConfig;
import mokiyoki.enhancedanimals.entity.genetics.BeeGeneticsInitialiser;
import mokiyoki.enhancedanimals.init.FoodSerialiser;
import mokiyoki.enhancedanimals.model.modeldata.AnimalModelData;
import mokiyoki.enhancedanimals.model.modeldata.BeeModelData;
import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import javax.annotation.Nullable;

import java.util.Arrays;
import java.util.UUID;

import static mokiyoki.enhancedanimals.renderer.textures.BeeTexture.calculateBeeTexture;
import static mokiyoki.enhancedanimals.util.Reference.BEE_SEXLINKED_GENES_LENGTH;

public class EnhancedBee extends EnhancedAnimalAbstract implements NeutralMob, FlyingAnimal {
    private static final EntityDataAccessor<Integer> STINGER = SynchedEntityData.defineId(EnhancedBee.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(EnhancedBee.class, EntityDataSerializers.INT);
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);

    @OnlyIn(Dist.CLIENT)
    private BeeModelData beeModelData;

    @Nullable
    private UUID persistentAngerTarget;

    private int stingerCountdown;
    private Gender gender;

    public EnhancedBee(EntityType<? extends EnhancedBee> entityType, Level worldIn) {
        super(entityType, worldIn, BEE_SEXLINKED_GENES_LENGTH, 2, false);
        this.initilizeAnimalSize();
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 14.0D)
                .add(Attributes.MOVEMENT_SPEED, 1.0D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D);
    }

    @Override
    public EntityDimensions getDimensions(Pose poseIn) {
        return EntityDimensions.scalable(0.75F, 0.42F).scale(this.getScale());
    }

    @Override
    public float getScale() {
        float size = this.getAnimalSize() > 0.0F ? this.getAnimalSize() : 1.0F;
        float nbSize = 0.25F;
        return this.isGrowing() ? (nbSize + ((size-nbSize) * (this.growthAmount()))) : size;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(STINGER, -2);
        this.entityData.define(DATA_REMAINING_ANGER_TIME, 0);
    }

    public int getStingerData() {
        return this.entityData.get(STINGER);
    }

    public void setStingerData(int counter) {
        this.entityData.set(STINGER, counter);
    }

    public boolean doHurtTarget(Entity p_27722_) {
        if (this.getStingerData() != -1) return false;

        boolean flag = p_27722_.hurt(DamageSource.sting(this), (float)((int)this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if (flag) {
            this.doEnchantDamageEffects(this, p_27722_);
            if (p_27722_ instanceof LivingEntity) {
                ((LivingEntity)p_27722_).setStingerCount(((LivingEntity)p_27722_).getStingerCount() + 1);
                int i = 0;
                if (this.level.getDifficulty() == Difficulty.NORMAL) {
                    i = 10;
                } else if (this.level.getDifficulty() == Difficulty.HARD) {
                    i = 18;
                }

                if (i > 0) {
                    ((LivingEntity)p_27722_).addEffect(new MobEffectInstance(MobEffects.POISON, i * 20, 0), this);
                }
            }

            if (this.gender == Gender.SACRIFICIAL_WORKER) {
                this.setStingerData(1);
            }
            this.stopBeingAngry();
            this.playSound(SoundEvents.BEE_STING, 1.0F, 1.0F);
        }

        return flag;
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    @Override
    public void setRemainingPersistentAngerTime(int time) {
        this.entityData.set(DATA_REMAINING_ANGER_TIME, time);
    }

    @Override
    public @Nullable UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID target) {
        this.persistentAngerTarget = target;
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    @Override
    public boolean getOrSetIsFemale() {
        if (this.isFemale == null) {
            return this.isFemale = getStringUUID().toCharArray()[0] - 48 < 8;
        }
        return this.isFemale;
    }

    @Override
    protected void setIsFemale(CompoundTag compound) {
        this.isFemale = compound.contains("IsFemale") ? compound.getBoolean("IsFemale") : getStringUUID().toCharArray()[0] - 48 < 8;
    }
    
    @Override
    protected String getSpecies() {
        return "entity.eanimod.enhanced_bee";
    }

    @Override
    protected int getAdultAge() {
        if (this.adultAge != null) return this.adultAge;
        this.adultAge = GeneticAnimalsConfig.COMMON.adultAgeBee.get();
        return this.adultAge;
    }

    @Override
    protected int gestationConfig() {
        return 24000;
    }

    @Override
    protected void incrementHunger() {

    }

    @Override
    protected void runExtraIdleTimeTick() {
        /**
         *      if (this.isAngry) {
         *          do angry bee stuff.
         *          if (this.stingerCountdown == -1) {
         *              sting stuff
         *          }
         *      }
         */
        if (this.gender == Gender.SACRIFICIAL_WORKER && this.stingerCountdown > 0) {
            this.stingerCountdown++;
            if (this.stingerCountdown % 5 == 0 && this.random.nextInt(Mth.clamp(1200 - this.stingerCountdown, 1, 1200)) == 0) {
                this.hurt(DamageSource.GENERIC, this.getHealth());
            }
        }
    }

    @Override
    protected void lethalGenes() {

    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public BeeModelData getModelData() {
        return this.beeModelData;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void setModelData(AnimalModelData animalModelData) {
        this.beeModelData = (BeeModelData) animalModelData;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public String getTexture() {
        if (this.enhancedAnimalTextureGrouping == null) {
            this.setTexturePaths();
        } else if (this.reload) {
            this.reload = false;
            this.reloadTextures();
        }

        return getCompiledTextures("enhanced_bee");
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void reloadTextures() {
        this.texturesIndexes.clear();
        this.enhancedAnimalTextures.clear();
        this.enhancedAnimalTextureGrouping = null;
        this.compiledTexture = null;
        this.setTexturePaths();
    }

    @Override
    protected void setTexturePaths() {
        if (this.getGenes() != null) {
            calculateBeeTexture(this, this.getGenes().getSexlinkedGenes(), this.isAngry());
        }
    }

    @Override
    protected void setAlphaTexturePaths() {}

    @Override
    public void initilizeAnimalSize() {
        int[] genes = this.genetics.getSexlinkedGenes();
        float size = 1.0F;
        float px = 1.0F/16.0F;

        int thoraxSize = 0;
        int abdomenSize = 7;

        if (genes[0] != 1 || genes[1] != 1) {
            thoraxSize = 3;
            if (genes[0] >= 3) {
                thoraxSize+= genes[0]==4 ? 2 : 1;
            }

            if (genes[1] >= 3) {
                thoraxSize+= genes[1]==4 ? 2 : 1;
            }

            if (thoraxSize > 3 && (genes[2]!=1 && genes[3]!=1)) thoraxSize--;
        }

        if (genes[4] != 1 || genes[5] != 1) {
            abdomenSize++;
            if (genes[4] >= 3 || genes[5] >= 3) {
                abdomenSize++;
                if (genes[4] == 4 || genes[5] == 4) {
                    abdomenSize++;
                }
            }
        }
        if (genes[2]==3|| genes[3]==3) abdomenSize--;
        if (genes[6]==2 || genes[7]==2) {
            abdomenSize--;
        }
        if (genes[8]==2 || genes[9]==2) {
            abdomenSize--;
        }
        if (genes[10]==2 || genes[11]==2) {
            abdomenSize--;
        }

        if (abdomenSize < 4) abdomenSize = 4;

        int oversize = thoraxSize + abdomenSize;
        if (oversize > 7) {
            size = 7.0F / oversize;
        }

        this.setAnimalSize(size);
    }

    @Override
    protected EnhancedAnimalAbstract createEnhancedChild(Level world, EnhancedAnimalAbstract otherParent) {
        return null;
    }

    @Override
    protected void createAndSpawnEnhancedChild(Level world) {

    }

    @Override
    protected boolean canBePregnant() {
        return false;
    }

    @Override
    protected boolean canLactate() {
        return false;
    }

    @Override
    protected FoodSerialiser.AnimalFoodMap getAnimalFoodType() {
        return null;
    }

    @Override
    protected void fixGeneLengths() {
        if (this.genetics.getNumberOfSexlinkedGenes() < BEE_SEXLINKED_GENES_LENGTH) {
            this.genetics.setSexlinkedGene(Arrays.copyOf(this.genetics.getSexlinkedGenes(), BEE_SEXLINKED_GENES_LENGTH));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);

        compound.putInt("StingerTimer", this.stingerCountdown);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);

        this.setStingerData(compound.getInt("StingerTimer"));
    }

    @Override
    protected Genes createInitialGenes(LevelAccessor world, BlockPos pos, boolean isDomestic) {
        return new BeeGeneticsInitialiser().generateNewGenetics(world, pos, isDomestic);
    }

    @Override
    public Genes createInitialBreedGenes(LevelAccessor world, BlockPos pos, String breed) {
        return new BeeGeneticsInitialiser().generateWithBreed(world, pos, breed);
    }

    @Override
    public void setInitialDefaults() {
        super.setInitialDefaults();
        this.setStingerData(this.getOrSetIsFemale() ? -1 : 0);
    }

    @Override
    public boolean isFlying() {
        return false;
    }

    private enum Gender {
        QUEEN,
        QUEEN_S,
        SOCIAL_QUEEN,
        SOCIAL_QUEEN_S,
        FEMALE,
        SOCIAL_FEMALE,
        WORKER,
        SACRIFICIAL_WORKER,
        DRONE
    }
}