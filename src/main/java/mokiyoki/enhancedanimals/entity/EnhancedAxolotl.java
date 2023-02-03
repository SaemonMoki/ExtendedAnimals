package mokiyoki.enhancedanimals.entity;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.serialization.Dynamic;
import mokiyoki.enhancedanimals.ai.brain.axolotl.AxolotlBrain;
import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
import mokiyoki.enhancedanimals.entity.genetics.AxolotlGeneticsInitialiser;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import mokiyoki.enhancedanimals.init.FoodSerialiser;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.init.ModMemoryModuleTypes;
import mokiyoki.enhancedanimals.init.ModSensorTypes;
import mokiyoki.enhancedanimals.items.EnhancedAxolotlBucket;
import mokiyoki.enhancedanimals.network.axolotl.AxolotlBucketTexturePacket;
import mokiyoki.enhancedanimals.renderer.texture.EnhancedLayeredTexturer;
import mokiyoki.enhancedanimals.renderer.texture.TextureGrouping;
import mokiyoki.enhancedanimals.renderer.texture.TexturingType;
import mokiyoki.enhancedanimals.util.Genes;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.decoration.LeashFenceKnotEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.AmphibiousNodeEvaluator;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static mokiyoki.enhancedanimals.EnhancedAnimals.channel;
import static mokiyoki.enhancedanimals.init.FoodSerialiser.axolotlFoodMap;
import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_AXOLOTL_EGG;
import static net.minecraft.world.entity.ai.attributes.AttributeSupplier.*;

public class EnhancedAxolotl extends EnhancedAnimalAbstract implements Bucketable {
    private static final EntityDataAccessor<Boolean> HAS_EGG = SynchedEntityData.defineId(EnhancedAxolotl.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_PLAYING_DEAD = SynchedEntityData.defineId(EnhancedAxolotl.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(EnhancedAxolotl.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<String> BUCKET_IMG = SynchedEntityData.defineId(EnhancedAxolotl.class, EntityDataSerializers.STRING);
    private static final int AXOLOTL_TOTAL_AIR_SUPPLY = 6000;
    protected static final ImmutableList<? extends SensorType<? extends Sensor<? super EnhancedAxolotl>>> SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_ADULT, SensorType.HURT_BY, SensorType.AXOLOTL_ATTACKABLES, ModSensorTypes.AXOLOTL_FOOD_TEMPTATIONS.get());
    protected static final ImmutableList<? extends MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.BREED_TARGET, ModMemoryModuleTypes.HAS_EGG.get(), MemoryModuleType.NEAREST_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, MemoryModuleType.LOOK_TARGET, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.ATTACK_TARGET, MemoryModuleType.ATTACK_COOLING_DOWN, MemoryModuleType.NEAREST_VISIBLE_ADULT, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.PLAY_DEAD_TICKS, MemoryModuleType.NEAREST_ATTACKABLE, MemoryModuleType.TEMPTING_PLAYER, MemoryModuleType.TEMPTATION_COOLDOWN_TICKS, MemoryModuleType.IS_TEMPTED, MemoryModuleType.HAS_HUNTING_COOLDOWN);
    private boolean isTempted = false;
    private int eggLayingTimer = -1;

    private static final String[] AXOLOTL_TEXTURES_BASE = new String[] {
            "natural.png", "natural_xanthic.png", "highgold_xanthic.png"
    };

    private static final String[][] AXOLOTL_TEXTURES_GILLS = new String[][] {
            {"gills_base.png", "gillslong_base.png", "gillsgreater_base.png"},
            {"gills_base_white.png", "gillslong_base_white.png", "gillsgreater_base_white.png"},
            {"gills_base_lightgrey.png", "gillslong_base_lightgrey.png", "gillsgreater_base_lightgrey.png"},
            {"gills_base_grey.png", "gillslong_base_grey.png", "gillsgreater_base_grey.png"},
            {"gills_base_black.png", "gillslong_base_black.png", "gillsgreater_base_black.png"},
            {"gills_base_brown.png", "gillslong_base_brown.png", "gillsgreater_base_brown.png"},
            {"gills_base_pink.png", "gillslong_base_pink.png", "gillsgreater_base_pink.png"},
            {"gills_base_red.png", "gillslong_base_red.png", "gillsgreater_base_red.png"},
            {"gills_base_orange.png", "gillslong_base_orange.png", "gillsgreater_base_orange.png"},
            {"gills_base_yellow.png", "gillslong_base_yellow.png", "gillsgreater_base_yellow.png"},
            {"gills_base_lime.png", "gillslong_base_lime.png", "gillsgreater_base_lime.png"},
            {"gills_base_green.png", "gillslong_base_green.png", "gillsgreater_base_green.png"},
            {"gills_base_cyan.png", "gillslong_base_cyan.png", "gillsgreater_base_cyan.png"},
            {"gills_base_lightblue.png", "gillslong_base_lightblue.png", "gillsgreater_base_lightblue.png"},
            {"gills_base_blue.png", "gillslong_base_blue.png", "gillsgreater_base_blue.png"},
            {"gills_base_purple.png", "gillslong_base_purple.png", "gillsgreater_base_purple.png"},
            {"gills_base_magenta.png", "gillslong_base_magenta.png", "gillsgreater_base_magenta.png"}
    };

    private static final String[] AXOLOTL_TEXTURES_XANTHIN = new String[] {
            "", "low_xanthophores.png", "natural_xanthophores.png", "high_xanthophores.png"
    };

    private static final String[][][] AXOLOTL_TEXTURES_MELANIN = new String[][][] {
        {
            {"natural_melanin.png", "natural_melaninotic.png"},
            {"leutistic0.png", "leutistic0.png"}
        }, {
            {"copper_melanin.png", "copper_melaninotic.png"},
            {"copper_leutistic0.png", "copper_leutistic0.png"}
        }
    };

    private static final String[] AXOLOTL_TEXTURES_IRIDESCENCE = new String[] {
            "", "low_iridophores.png", "natural_iridophores.png", "high_iridophores.png"
    };

    private static final String[][][] AXOLOTL_TEXTURES_PIED = new String[][][] {
            {
                    //white belly
                    {
                        /*weak*/
                        "spot/whitebelly/weak_splotch.png","spot/whitebelly/weak_hardspeckle.png","spot/whitebelly/weak_softspeckle.png"
                    }, {
                        /*medium-weak*/
                        "spot/whitebelly/mediumweak_splotch.png","spot/whitebelly/mediumweak_hardspeckle.png","spot/whitebelly/mediumweak_softspeckle.png"
                    }, {
                        /*medium*/
                        "spot/whitebelly/medium_splotch.png","spot/whitebelly/medium_splotch.png","spot/whitebelly/medium_splotch.png"
                    }, {
                        /*medium-high*/
                        "spot/whitebelly/mediumhigh_splotch.png","spot/whitebelly/mediumhigh_splotch.png","spot/whitebelly/mediumhigh_splotch.png"
                    },{
                        /*high*/
                        "spot/whitebelly/high_splotch.png","spot/whitebelly/high_hardspeckle.png","spot/whitebelly/high_softspeckle.png"
                    }
            },{
                    //pied belly
                    {
                            /*weak*/
                            "spot/piedbelly/weak_splotch.png","spot/piedbelly/weak_hardspeckle.png","spot/piedbelly/weak_softspeckle.png"
                    }, {
                            /*medium-weak*/
                            "spot/piedbelly/mediumweak_splotch.png","spot/piedbelly/mediumweak_hardspeckle.png","spot/piedbelly/mediumweak_softspeckle.png"
                    }, {
                            /*medium*/
                            "spot/piedbelly/medium_splotch.png","spot/piedbelly/medium_hardspeckle.png","spot/piedbelly/medium_softspeckle.png"
                    }, {
                            /*medium-high*/
                            "spot/piedbelly/mediumhigh_splotch.png","spot/piedbelly/mediumhigh_hardspeckle.png","spot/piedbelly/mediumhigh_softspeckle.png"
                    }, {
                            /*high*/
                            "spot/piedbelly/high_splotch.png","spot/piedbelly/high_hardspeckle.png","spot/piedbelly/high_softspeckle.png"
                    }
            },{
                    //pied
                    {
                            /*weak*/
                            "spot/pied/weak_splotch.png","spot/pied/weak_hardspeckle.png","spot/pied/weak_softspeckle.png"
                    }, {
                            /*medium-weak*/
                            "spot/pied/mediumweak_splotch.png","spot/pied/mediumweak_hardspeckle.png","spot/pied/mediumweak_softspeckle.png"
                    }, {
                            /*medium*/
                            "spot/pied/medium_splotch.png","spot/pied/medium_hardspeckle.png","spot/pied/medium_softspeckle.png"
                    }, {
                            /*medium-high*/
                            "spot/pied/mediumhigh_splotch.png","spot/pied/mediumhigh_hardspeckle.png","spot/pied/mediumhigh_softspeckle.png"
                    }, {
                            /*high*/
                            "spot/pied/high_splotch.png","spot/pied/high_hardspeckle.png","spot/pied/high_softspeckle.png"
                    }
            }
    };

    private static final String[][][] AXOLOTL_TEXTURES_BERKSHIRE = new String[][][] {
        {
            {"star.png", "snip.png"},
            {"blaze1.png"},
            {"blaze2.png"},
            {"blaze3.png"},
            {"blaze4.png"},
            {"blaze5.png"},
            {"blaze6.png"},
            {"blaze7.png"},
            {"blaze8.png"},
            {"baldface9.png"},
        }, {
            {"berkshire0.png"},
            {"berkshire1.png"},
            {"berkshire2.png"},
            {"berkshire3.png"},
            {"berkshire4.png"},
            {"berkshire5.png"},
            {"berkshire6.png"},
            {"berkshire7.png"},
            {"berkshire8.png"},
            {"berkshire9.png"},
        }
    };

    private static final String[] CHEEK_SPOTS = new String[] {
            "cheeks.png", "cheeks_white.png", "cheeks_lightgrey.png", "cheeks_grey.png", "cheeks_black.png", "cheeks_brown.png", "cheeks_pink.png", "cheeks_red.png", "cheeks_orange.png", "cheeks_yellow.png", "cheeks_lime.png", "cheeks_green.png", "cheeks_cyan.png", "cheeks_lightblue.png", "cheeks_blue.png", "cheeks_purple.png", "cheeks_magenta.png",
    };

    public EnhancedAxolotl(EntityType<? extends EnhancedAxolotl> type, Level worldIn) {
        super(type, worldIn, 2, Reference.AXOLOTL_AUTOSOMAL_GENES_LENGTH, false);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.moveControl = new EnhancedAxolotl.AxolotlMoveControl(this);
        this.lookControl = new EnhancedAxolotl.AxolotlLookControl(this, 20);
        this.maxUpStep = 1.0F;
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new EnhancedAxolotl.LayEggGoal(this, 0.3D));
        this.goalSelector.addGoal(1, new EnhancedAxolotl.MateGoal(this, 0.5D));
    }

    public static Builder prepareAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 14.0D)
                .add(Attributes.MOVEMENT_SPEED, 1.0D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D);
    }

    protected void customServerAiStep() {
        this.getBrain().tick((ServerLevel)this.level, this);
        AxolotlBrain.updateActivity(this);
        if (!this.isNoAi()) {
            Optional<Integer> optional = this.getBrain().getMemory(MemoryModuleType.PLAY_DEAD_TICKS);
            this.setPlayingDead(optional.isPresent() && optional.get() > 0);

            if (this.hasEgg()) {
                this.getBrain().setMemory(ModMemoryModuleTypes.HAS_EGG.get(), true);
            }
        }

    }

    @Override
    public EntityDimensions getDimensions(Pose poseIn) {
        return EntityDimensions.scalable(0.75F, 0.42F).scale(this.getScale());
    }

    @Override
    public float getScale() {
        float size = this.getAnimalSize() > 0.0F ? this.getAnimalSize() : 1.0F;
        float nbSize = 0.1F;
        return this.isGrowing() ? (nbSize + ((size-nbSize) * (this.growthAmount()))) : size;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HAS_EGG, false);
        this.entityData.define(DATA_PLAYING_DEAD, false);
        this.entityData.define(FROM_BUCKET, false);
        this.entityData.define(BUCKET_IMG, "");
    }

    public void setBucketImageData(String imageArray) {
        this.entityData.set(BUCKET_IMG, imageArray);
    }

    public void setBucketImageData(int[] imageArray) {
        this.entityData.set(BUCKET_IMG, getImageString(imageArray));
    }

    public String getBucketImage() {
        return this.entityData.get(BUCKET_IMG);
    }

    public boolean isTempted(boolean tempted) {
        this.isTempted = tempted;
        return tempted;
    }

    public boolean getIsTempted() {
        return this.isTempted;
    }

    public boolean hasEgg() {
        return this.entityData.get(HAS_EGG) || this.pregnant;
    }

    private void setHasEgg(boolean hasEgg) {
        this.entityData.set(HAS_EGG, hasEgg);
    }

    @Override
    public Boolean isAnimalSleeping() {
        if (!this.isInWaterRainOrBubble() || this.hasEgg()) {
            return false;
        } else if (!(this.getLeashHolder() instanceof LeashFenceKnotEntity) && this.getLeashHolder() != null) {
            return false;
        } else {
            this.sleeping = this.entityData.get(SLEEPING);
            return this.sleeping;
        }
    }

    @Override
    public boolean sleepingConditional() {
        return this.awokenTimer == 0 && !this.sleeping;
    }

    @Override
    protected boolean ableToMoveWhileLeashed() {
        return true;
    }

    @Override
    protected String getSpecies() {
        return "entity.eanimod.enhanced_axolotl";
    }

    @Override
    protected int getAdultAge() { return EanimodCommonConfig.COMMON.adultAgeAxolotl.get();}

    @Override
    protected int gestationConfig() {
        return 24000;
    }

    @Override
    protected void incrementHunger() {
        if(this.sleeping) {
            this.hunger = this.hunger + (0.5F*getHungerModifier());
        } else {
            this.hunger = this.hunger + (getHungerModifier());
        }
    }

    @Override
    public void baseTick() {
        int i = this.getAirSupply();
        super.baseTick();
        if (!this.isNoAi()) {
            this.handleAirSupply(i);
        }
    }

    @Override
    protected void runExtraIdleTimeTick() {}

    @Override
    protected void lethalGenes() {

    }

    @Override
    public void initilizeAnimalSize() {
        int[] gene = this.genetics.getAutosomalGenes();
        float size = 1.0F;

        for (int i = 5; i > gene[28]; i--) {
            size = size - 0.01F;
        }
        for (int i = 5; i > gene[29]; i--) {
            size = size - 0.01F;
        }

        switch (Math.min(gene[30], gene[31])) {
            case 10: size = size * 0.5F; break;
            case 9: size = size * 0.55F; break;
            case 8: size = size * 0.6F; break;
            case 7: size = size * 0.65F; break;
            case 6: size = size * 0.7F; break;
            case 5: size = size * 0.75F; break;
            case 4: size = size * 0.8F; break;
            case 3: size = size * 0.85F; break;
            case 2: size = size * 0.9F; break;
            default:
        }

        this.setAnimalSize(size);
    }

    @Override
    protected void createAndSpawnEnhancedChild(Level world) {}

    public int getHungerRestored(ItemStack stack) {
        return 8000;
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
        return axolotlFoodMap();
    }

    public boolean isPushedByFluid() {
        return false;
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    public MobType getMobType() {
        return MobType.WATER;
    }

    public void setPlayingDead(boolean p_149199_) {
        this.entityData.set(DATA_PLAYING_DEAD, p_149199_);
    }

    public boolean isPlayingDead() {
        return this.entityData.get(DATA_PLAYING_DEAD);
    }

    @Override
    public boolean fromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    @Override
    public void setFromBucket(boolean p_149196_) {
        this.entityData.set(FROM_BUCKET, p_149196_);
    }

    protected void playSwimSound(float volume) {
        super.playSwimSound(volume * 1.5F);
    }

    @Nullable
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.AXOLOTL_HURT;
    }

    @Nullable
    protected SoundEvent getDeathSound() {
        return SoundEvents.AXOLOTL_DEATH;
    }

    @Nullable
    protected SoundEvent getAmbientSound() {
        return this.isInWater() ? SoundEvents.AXOLOTL_IDLE_WATER : SoundEvents.AXOLOTL_IDLE_AIR;
    }

    protected SoundEvent getSwimSplashSound() {
        return SoundEvents.AXOLOTL_SPLASH;
    }

    protected SoundEvent getSwimSound() {
        return SoundEvents.AXOLOTL_SWIM;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        if (!this.isSilent() && this.getBells() && this.random.nextBoolean()) {
            this.playSound(SoundEvents.NOTE_BLOCK_IRON_XYLOPHONE, 0.5F, 0.2F);
            this.playSound(SoundEvents.NOTE_BLOCK_CHIME, 1.4F, 0.155F);
        }
        super.playStepSound(pos, blockIn);
    }
    @Override
    protected void usePlayerItem(Player player, InteractionHand hand, ItemStack itemStack) {
        if (itemStack.getItem() instanceof MobBucketItem) {
            if (!player.isCreative()) {
                player.setItemInHand(hand, new ItemStack(Items.WATER_BUCKET));
            }
        } else {
            super.usePlayerItem(player, hand, itemStack);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public String getTexture() {
        if (this.enhancedAnimalTextureGrouping == null) {
            this.setTexturePaths();
        } else if (this.getReloadTexture() ^ this.reload) {
            this.reload=!this.reload;
            this.reloadTextures();
        }

        return getCompiledTextures("enhanced_axolotl");

    }

    @OnlyIn(Dist.CLIENT)
    protected void setTexturePaths() {
        if (this.getSharedGenes() != null) {
            int[] gene = getSharedGenes().getAutosomalGenes();
            int gills = 0;
            int gillsColour = 0;
            int gillsColour2 = 0;
            int base = 0;
            int copper = gene[6] == 1 || gene[7] == 1 ? 0 : 1;
            int pattern = 0;
//            char[] uuidArry = getCachedUniqueIdString().toCharArray();

            if (gene[34] == 2 && gene[35] == 2) {
                gills += 1;
            }

            if (gene[36] == 2 && gene[37] == 2) {
                gills += 1;
            }

            if (gene[8] == 1 || gene[9] == 1) {
                //Non-Leucistic (wildtype)
                base = gene[2] == 1 || gene[3] == 1 ? 1 : 0;
            } else if (gene[0] == 1 || gene[1] == 1) {
                //Leucistic
                pattern = 1;
            }

            int melanoid = 0;
            if (gene[4] == 2 && gene[5] == 2) {
                melanoid = 1;
            }

            int pied = 0;
            int piedStrength = 0;
            int piedSplotchy = 0;
            if (gene[12] !=1 && gene[13] != 1) {
                pied = (gene[12] + gene[13])-3;
                piedStrength = (int)((gene[14] + gene[15] - 2) * 0.3);
                if (piedStrength >= 5) {
                    piedStrength = 4;
                }
                if (gene[16] >= 5 || gene[17] >= 5) {
                    piedSplotchy = 2;
                } else if (gene[16] >= 3 || gene[17] >= 3) {
                    piedSplotchy = 1;
                }
            }

            if (gene[44] == 2 || gene[45] == 2) {
                gillsColour = gene[40] - 1;
                gillsColour2 = gene[41] - 1;
            }

            TextureGrouping parentGroup = new TextureGrouping(TexturingType.MERGE_GROUP);

            TextureGrouping gillsGroup = new TextureGrouping(TexturingType.AVERAGE_GROUP);
            addTextureToAnimalTextureGrouping(gillsGroup, AXOLOTL_TEXTURES_GILLS, gillsColour, gills, true);
            addTextureToAnimalTextureGrouping(gillsGroup, AXOLOTL_TEXTURES_GILLS, gillsColour2, gills, true);
            parentGroup.addGrouping(gillsGroup);

            TextureGrouping bodyGroup = new TextureGrouping(TexturingType.ALPHA_GROUP);
            addTextureToAnimalTextureGrouping(bodyGroup, "alpha_mask.png");
            addTextureToAnimalTextureGrouping(bodyGroup, TexturingType.APPLY_DYE, AXOLOTL_TEXTURES_BASE, base, null);
            addTextureToAnimalTextureGrouping(bodyGroup, AXOLOTL_TEXTURES_MELANIN, copper, pattern, melanoid, gene[0] == 1 || gene[1] == 1);
            addTextureToAnimalTextureGrouping(bodyGroup, AXOLOTL_TEXTURES_PIED, pied-1, piedStrength, piedSplotchy, pied!=0);
            parentGroup.addGrouping(bodyGroup);

            if (gene[44] == 2 || gene[45] == 2) {
                TextureGrouping cheekGroup = new TextureGrouping(TexturingType.AVERAGE_GROUP);
                addTextureToAnimalTextureGrouping(cheekGroup, CHEEK_SPOTS, gillsColour, gene[44] == 2 || gene[45] == 2);
                addTextureToAnimalTextureGrouping(cheekGroup, CHEEK_SPOTS, gillsColour2, gene[44] == 2 || gene[45] == 2);
                parentGroup.addGrouping(cheekGroup);
            }

            TextureGrouping detailsGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
            addTextureToAnimalTextureGrouping(detailsGroup, TexturingType.APPLY_EYE_LEFT_COLOUR, "eye_left.png");
            addTextureToAnimalTextureGrouping(detailsGroup, TexturingType.APPLY_EYE_RIGHT_COLOUR, "eye_right.png");
            parentGroup.addGrouping(detailsGroup);

            this.setTextureGrouping(parentGroup);
        }
    }

    @Override
    protected void setAlphaTexturePaths() {
        this.enhancedAnimalAlphaTextures.add("alpha_mask.png");
    }

    @OnlyIn(Dist.CLIENT)
    public Colouration getRgb() {
        this.colouration = super.getRgb();
        Genes genes = getSharedGenes();
        if (genes != null) {
            if (this.colouration.getDyeColour() == -1 || this.colouration.getLeftEyeColour() == -1 || this.colouration.getRightEyeColour() == -1 || this.colouration.getBridleColour() == -1) {
                int[] gene = genes.getAutosomalGenes();

                if (gene[10] != 1 || gene[11] != 1) {
                    this.colouration.setDyeColour(Colouration.mixAxolotlHue((float) (gene[24]-1) / 255, (float) (gene[25]-1) / 255));
                }

                float eyeHue = 0.75F;
                float eyeSaturation = 0.5F;
                float eyeBrightness = 0.25F;

                if (gene[20] == 1 && gene[21] == 1) {
                    if (gene[0] == 2 && gene[1] == 2) {
                        if (gene[2] == 2 && gene[3] == 2) {
                            eyeHue = 0.95F;
                            eyeBrightness = 0.8F;
                        } else {
                            eyeHue = 0.09F;
                            eyeSaturation = 0.75F;
                            eyeBrightness = 0.8F;
                        }
                    }
                } else {
                    if (gene[20] == 4 || gene[21] == 4) {
                        int genenum = gene[20] == 4 ? 21 : 20;
                        //light eyes
                        float[] lightEyes = Colouration.getAxolotlLightEyes((float) (gene[22]-1) / 255, (float) (gene[23]-1) / 255);

                        eyeHue = lightEyes[0];

                        if (gene[genenum] == 2) {
                            //dark eyes
                            eyeSaturation = (lightEyes[1] + 1.0F) * 0.5F;
                            eyeBrightness = lightEyes[2] * 0.5F;
                        } else if (gene[genenum] == 3) {
                            //pigmented eyes
                            eyeSaturation = (lightEyes[1] + 1.0F) * 0.5F;
                            eyeBrightness = (lightEyes[2] + 0.75F) * 0.5F;
                        } else if (gene[genenum] == 5) {
                            //light-pastel eyes
                            eyeSaturation = (lightEyes[1] + 0.5F) * 0.5F;
                            eyeBrightness = (lightEyes[1] + 0.75F) * 0.5F;
                        } else {
                            //light-glow eyes
                            //light eyes
                            eyeSaturation = lightEyes[1];
                            eyeBrightness = lightEyes[2];
                        }
                    } else {
                        eyeHue = Colouration.mixHueComponent((float) (gene[22]-1) / 255, (float) (gene[23]-1) / 255, 0.5F);

                        if (gene[20] == 2) {
                            //dark eyes
                            if (gene[21] == 3) {
                                //dark-pigmented eyes
                                eyeSaturation = 1.0F;
                                eyeBrightness = 0.5F;
                            } else if (gene[21] == 5) {
                                //dark-pastel eyes
                                eyeBrightness = 0.4F;
                            } else {
                                //dark-glow eyes
                                //dark eyes
                                eyeSaturation = 1.0F;
                            }
                        } else if (gene[20] == 3 || gene[20] == 6) {
                            //glow eyes
                            //pigmented eyes
                            if (gene[21] == 5) {
                                //pigmented-pastel eyes
                                eyeSaturation = 0.75F;
                                eyeBrightness = 0.75F;
                            } else {
                                //pigmented-glow eyes
                                //pigmented eyes
                                eyeSaturation = 1.0F;
                                eyeBrightness = 0.75F;
                            }
                        } else {
                            //pastel eyes
                            eyeBrightness = 0.75F;
                        }
                    }
                }

                this.colouration.setLeftEyeColour(Colouration.HSBtoARGB(eyeHue, eyeSaturation, eyeBrightness));
                this.colouration.setRightEyeColour(Colouration.HSBtoARGB(eyeHue, eyeSaturation, eyeBrightness));
            }
        }

        return this.colouration;
    }

    /*
NBT read/write
*/
    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("BucketImage", this.getBucketImage());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setBucketImageData(compound.getString("BucketImage"));
    }

    @Override
    protected int getPregnancyProgression() {
        return this.hasEgg() ? 10 : 0;
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor inWorld, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag itemNbt) {
        return commonInitialSpawnSetup(inWorld, livingdata, getAdultAge(), 48000, 800000, spawnReason);
    }

    @Override
    public void setInitialDefaults() {
        super.setInitialDefaults();
    }

    @Override
    protected Genes createInitialGenes(LevelAccessor inWorld, BlockPos pos, boolean isDomestic) {
        return new AxolotlGeneticsInitialiser().generateNewGenetics(this.level, pos, isDomestic);
    }

    @Override
    protected void initializeHealth(EnhancedAnimalAbstract animal, float health) {
        health = 14.0F;
        super.initializeHealth(animal, health);
    }

    @Override
    public Genes createInitialBreedGenes(LevelAccessor world, BlockPos pos, String breed) {
        return new AxolotlGeneticsInitialiser().generateWithBreed(this.level, pos, breed);
    }

    @OnlyIn(Dist.CLIENT)
    public void setBucketImageData(EnhancedLayeredTexturer texture) {
        if (this.isAlive()) {
            if (this.getSharedGenes() != null && texture.hasImage()) {
                boolean g = this.getSharedGenes().isHomozygousFor(34, 2) ^ this.getSharedGenes().isHomozygousFor(36, 2);
                boolean l = this.getSharedGenes().isHomozygousFor(32, 2);
                int[] axolotlBucketImage = new int[86];
                int[] x = new int[]{
                        g?40:39, 40, 41, 46, 47, g?47:48,
                        37, 38, 41, 41, 46, 46, 49, 50,
                        38, 40, 1, 6, 7, 8, 9, 10, 11, 16, 47, 49,
                        g?36:37, 2, 5, 6, 7, 8, 9, 10, 11, 12, 15, g?51:50,
                        38, 39, 5, 6, 7, 8, 9, 10, 11, 12, 48, 49,
                        36, 37, 5, 6, 7, 8, 9, 10, 11, 12, 50, 51,
                        59, -1, -1, -1, -1, 59,
                        59, -1, -1, 59,
                        l?-1:58, 59, l?-1:60, l?-1:58, 59, l?-1:60,
                        l?58:-1, 59, l?60:-1, l?58:-1, 59, l?60:-1,
                        l?59:-1, l?59:-1
                };
                int[] y = new int[]{
                        1, 2, 1, 1, 2, 1,
                        g?6:5, 6, 3, 4, 4, 3, 6, g?6:5, 
                        7, 8, 5, 1, 1, 0, 0, 1, 1, 5, 8, 7,
                        10, 6, 2, 2, 2, 2, 2, 2, 2, 2, 6, 10,
                        10, 11, 3, 3, 3, 3, 3, 3, 3, 3, 11, 10, 
                        10, 12, 4, 6, 6, 5, 5, 6, 6, 4, 12, 10,
                        l?2:3, 62, 62, 62, 62, l?10:11,
                        l?3:4, 39, 39, l?10:11,
                        5, l?4:5, 5, 12, l?11:12, 12,
                        5, l?5:6, 5, 12, l?12:13, 12,
                        6, 13
                };
                NativeImage image = texture.getImage();
                int rgba;
                for (int i = 0; i < 86; i++) {
                    if (x[i] == -1) {
                        axolotlBucketImage[i] = -2;
                    } else {
                        axolotlBucketImage[i] = image.getPixelRGBA(x[i], y[i]);
                    }
                }

                rgba = image.getPixelRGBA(41,62);
                if ((rgba >> 24 & 255) != 0) {
                    axolotlBucketImage[62] = rgba;
                    axolotlBucketImage[63] = rgba;
                    axolotlBucketImage[64] = rgba;
                    axolotlBucketImage[65] = rgba;
                    axolotlBucketImage[66] = rgba;
                    axolotlBucketImage[67] = rgba;
                }
                rgba = image.getPixelRGBA(15,38);
                if ((rgba >> 24 & 255) != 0) {
                    axolotlBucketImage[64] = rgba;
                    axolotlBucketImage[65] = rgba;
                    axolotlBucketImage[69] = rgba;
                    axolotlBucketImage[70] = rgba;
                }

                sendBucketTextureToServer(axolotlBucketImage);
                texture.closeImage();
            }
        }
    }

    private static String getImageString(int[] imageArray) {
        StringBuilder sb = new StringBuilder().append(imageArray[0]);
        for (int i = 1, l = imageArray.length; i < l; i++){
            sb.append(",");
            sb.append(imageArray[i]);
        }
        return sb.toString();
    };

    private static int[] getImageArrayFromString(String imageString) {
        String[] imageStrings = imageString.split(",");
        int[] image = new int[imageStrings.length];
        int i = 0;
        for (String color:imageStrings) {
            image[i] = color.isEmpty()? -1 : Integer.parseInt(color);
            i++;
        }
        return image;
    };

    public void sendBucketTextureToServer(int[] bucketImageTexture) {
        channel.sendToServer(new AxolotlBucketTexturePacket(this.getId(), bucketImageTexture));
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        Item item = itemStack.getItem();

        if (item == ModItems.ENHANCED_AXOLOTL_EGG.get()) {
            return InteractionResult.SUCCESS;
        }

        return Bucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
    }

    @Override
    public void saveToBucketTag(ItemStack stack) {
        Bucketable.saveDefaultDataToBucketTag(this, stack);
        if (stack.getItem() instanceof EnhancedAxolotlBucket) {
            EnhancedAxolotlBucket.setImage(stack, getImageArrayFromString(this.getBucketImage()));
            EnhancedAxolotlBucket.setGenes(stack, this.genetics!=null? this.genetics : getSharedGenes());
            EnhancedAxolotlBucket.setParentNames(stack, this.sireName, this.damName);
            EnhancedAxolotlBucket.setEquipment(stack, this.animalInventory.getItem(1));
            if (this.mateGenetics != null) {
                EnhancedAxolotlBucket.setMateGenes(stack, this.mateGenetics);
                EnhancedAxolotlBucket.setMateIsFemale(stack, this.mateGender);
            }
            EnhancedAxolotlBucket.setAxolotlUUID(stack, this.getUUID().toString());
            EnhancedAxolotlBucket.setBirthTime(stack, this.getBirthTime());
        }
    }

    @Override
    public void loadFromBucketTag(CompoundTag tag) {
        Bucketable.loadDefaultDataFromBucketTag(this, tag);
        this.setIsFemale(tag.getCompound("Genetics"));
        this.toggleReloadTexture();
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(ModItems.ENHANCED_AXOLOTL_BUCKET.get());
    }

    @Override
    public SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_FILL_AXOLOTL;
    }

    /**
     *      vanilla-ish features
     */

    public float getWalkTargetValue(BlockPos p_149140_, LevelReader p_149141_) {
        return 0.0F;
    }

    protected void handleAirSupply(int p_149194_) {
        if (this.isAlive() && !this.isInWaterRainOrBubble()) {
            this.setAirSupply(p_149194_ - 1);
            if (this.getAirSupply() == -20) {
                this.setAirSupply(0);
                this.hurt(DamageSource.DRY_OUT, 2.0F);
            }
        } else {
            this.setAirSupply(this.getMaxAirSupply());
        }

    }

    @Override
    public int getMaxAirSupply() {
        return AXOLOTL_TOTAL_AIR_SUPPLY;
    }

    public void rehydrate() {
        int i = this.getAirSupply() + 1800;
        this.setAirSupply(Math.min(i, this.getMaxAirSupply()));
    }

    public double getMeleeAttackRangeSqr(LivingEntity p_149185_) {
        return 1.5D + (double)p_149185_.getBbWidth() * 2.0D;
    }

    protected PathNavigation createNavigation(Level p_149128_) {
        return new EnhancedAxolotl.AxolotlPathNavigation(this, p_149128_);
    }

    public boolean doHurtTarget(Entity p_149201_) {
        boolean flag = p_149201_.hurt(DamageSource.mobAttack(this), (float)((int)this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if (flag) {
            this.doEnchantDamageEffects(this, p_149201_);
            this.playSound(SoundEvents.AXOLOTL_ATTACK, 1.0F, 1.0F);
        }

        return flag;
    }

    public boolean hurt(DamageSource p_149115_, float p_149116_) {
        float f = this.getHealth();
        if (!this.level.isClientSide && !this.isNoAi() && this.level.random.nextInt(3) == 0 && ((float)this.level.random.nextInt(3) < p_149116_ || f / this.getMaxHealth() < 0.5F) && p_149116_ < f && this.isInWater() && (p_149115_.getEntity() != null || p_149115_.getDirectEntity() != null) && !this.isPlayingDead()) {
            this.brain.setMemory(MemoryModuleType.PLAY_DEAD_TICKS, 200);
        }

        return super.hurt(p_149115_, p_149116_);
    }

    public boolean canBeSeenAsEnemy() {
        return !this.isPlayingDead() && super.canBeSeenAsEnemy();
    }

    public static void onStopAttacking(EnhancedAxolotl p_149120_) {
        Optional<LivingEntity> optional = p_149120_.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET);
        if (optional.isPresent()) {
            Level level = p_149120_.level;
            LivingEntity livingentity = optional.get();
            if (livingentity.isDeadOrDying()) {
                DamageSource damagesource = livingentity.getLastDamageSource();
                if (damagesource != null) {
                    Entity entity = damagesource.getEntity();
                    if (entity != null && entity.getType() == EntityType.PLAYER) {
                        Player player = (Player)entity;
                        List<Player> list = level.getEntitiesOfClass(Player.class, p_149120_.getBoundingBox().inflate(20.0D));
                        if (list.contains(player)) {
                            p_149120_.applySupportingEffects(player);
                        }
                    }
                }
            }

        }
    }

    public void applySupportingEffects(Player p_149174_) {
        MobEffectInstance mobeffectinstance = p_149174_.getEffect(MobEffects.REGENERATION);
        int i = mobeffectinstance != null ? mobeffectinstance.getDuration() : 0;
        if (i < 2400) {
            i = Math.min(2400, 100 + i);
            p_149174_.addEffect(new MobEffectInstance(MobEffects.REGENERATION, i, 0), this);
        }

        p_149174_.removeEffect(MobEffects.DIG_SLOWDOWN);
    }

    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.fromBucket();
    }

    /**
     *      vanilla-ish axolotl AI
     */

    protected Brain.Provider<EnhancedAxolotl> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    protected Brain<?> makeBrain(Dynamic<?> p_149138_) {
        return AxolotlBrain.makeBrain(this.brainProvider().makeBrain(p_149138_));
    }

    public Brain<EnhancedAxolotl> getBrain() {
        return (Brain<EnhancedAxolotl>)super.getBrain();
    }

    public void travel(Vec3 p_149181_) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), p_149181_);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
        } else {
            super.travel(p_149181_);
        }

    }

    public boolean removeWhenFarAway(double p_149183_) {
        return false;
    }

    public static boolean checkAxolotlSpawnRules(EntityType<? extends LivingEntity> p_186250_, ServerLevelAccessor p_186251_, MobSpawnType p_186252_, BlockPos p_186253_, Random p_186254_) {
        return p_186251_.getBlockState(p_186253_.below()).is(BlockTags.AXOLOTLS_SPAWNABLE_ON);
    }

    public boolean checkSpawnObstruction(LevelReader levelReader) {
        return levelReader.isUnobstructed(this);
    }

    class AxolotlLookControl extends SmoothSwimmingLookControl {
        public AxolotlLookControl(EnhancedAxolotl p_149210_, int p_149211_) {
            super(p_149210_, p_149211_);
        }

        public void tick() {
            if (!EnhancedAxolotl.this.isPlayingDead()) {
                super.tick();
            }

        }
    }

    static class AxolotlMoveControl extends SmoothSwimmingMoveControl {
        private final EnhancedAxolotl axolotl;

        public AxolotlMoveControl(EnhancedAxolotl p_149215_) {
            super(p_149215_, 85, 10, 0.1F, 0.5F, false);
            this.axolotl = p_149215_;
        }

        public void tick() {
            if (!this.axolotl.isPlayingDead()) {
                super.tick();
            }

        }
    }

    static class AxolotlPathNavigation extends WaterBoundPathNavigation {
        AxolotlPathNavigation(EnhancedAxolotl p_149218_, Level p_149219_) {
            super(p_149218_, p_149219_);
        }

        protected boolean canUpdatePath() {
            return true;
        }

        protected PathFinder createPathFinder(int p_149222_) {
            this.nodeEvaluator = new AmphibiousNodeEvaluator(false);
            return new PathFinder(this.nodeEvaluator, p_149222_);
        }

        public boolean isStableDestination(BlockPos p_149224_) {
            return !this.level.getBlockState(p_149224_.below()).isAir();
        }
    }

    /**
     *      Breeding/Egglaying
     */

    static class LayEggGoal extends MoveToBlockGoal {
        private final EnhancedAxolotl axolotl;

        LayEggGoal(EnhancedAxolotl axolotl, double speedIn) {
            super(axolotl, speedIn, 16, 5);
            this.axolotl = axolotl;
        }

        @Override
        public double acceptedDistance() {
            return 2.0D;
        }

        public boolean canUse() {
            if (this.axolotl.mateGenetics==null) {
                this.axolotl.setHasEgg(false);
                return false;
            }
            return this.axolotl.hasEgg() && super.canUse();
        }

        public boolean canContinueToUse() {
            if (this.axolotl.mateGenetics==null) {
                this.axolotl.setHasEgg(false);
                return false;
            }
            return super.canContinueToUse() && this.axolotl.hasEgg();
        }

        public void tick() {
            super.tick();
            BlockPos blockpos = this.axolotl.blockPosition();
            if (this.axolotl.isInWater() && this.isReachedTarget()) {
                if (this.axolotl.eggLayingTimer == -1) {
                    this.axolotl.eggLayingTimer = 0;
                } else if (this.axolotl.eggLayingTimer >= 200) {
                    if (this.axolotl.eggLayingTimer == 200) {
                        this.axolotl.eggLayingTimer = 200 + (this.axolotl.random.nextInt(4) * 40);
                    }

                    if (this.axolotl.eggLayingTimer%40 == 0) {
                        Level level = this.axolotl.getLevel();
                        BlockPos pos = this.blockPos;
                        String mateName = this.axolotl.mateName.isEmpty() ? "???" : this.axolotl.mateName;
                        String name = this.axolotl.hasCustomName() ? this.axolotl.getName().getString() : "???";
                        level.playSound((Player)null, blockpos, SoundEvents.TURTLE_LAY_EGG, SoundSource.BLOCKS, 0.3F, 0.9F + level.random.nextFloat() * 0.2F);
                        Genes eggGenes = new Genes(this.axolotl.getGenes()).makeChild(true, true, this.axolotl.mateGenetics);
                        EnhancedAxolotlEgg egg = ENHANCED_AXOLOTL_EGG.get().create(level);
                        egg.setParentNames(mateName, name);
                        egg.setGenes(eggGenes);
                        egg.moveTo(pos.getX()+0.5D+(ThreadLocalRandom.current().nextFloat(-0.375F, 0.375F)), axolotl.getY(), pos.getZ()+0.5D+(ThreadLocalRandom.current().nextFloat(-0.375F, 0.375F)), ThreadLocalRandom.current().nextInt(4)* (Mth.HALF_PI*0.5F), 0.0F);
                        level.addFreshEntity(egg);
                    }

                    if (this.axolotl.eggLayingTimer > 320) {
                        this.axolotl.setHasEgg(false);
                        this.axolotl.pregnant = false;
                        this.axolotl.eggLayingTimer = -1;
                    }
//                    this.axolotl.setInLove(600);
                }

                if (this.axolotl.eggLayingTimer != -1) {
                    this.axolotl.eggLayingTimer++;
                }
            }

        }

        /**
         * Return true to set given position as destination
         */
        protected boolean isValidTarget(LevelReader worldIn, BlockPos pos) {
            return EnhancedAxolotlEgg.isEggLayableBlock(worldIn.isWaterAt(pos.below()), worldIn.getBlockState(pos.below()));
        }
    }

    static class MateGoal extends BreedGoal {
        private final EnhancedAxolotl axolotl;

        MateGoal(EnhancedAxolotl axolotl, double speedIn) {
            super(axolotl, speedIn);
            this.axolotl = axolotl;
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return super.canUse() && !this.axolotl.hasEgg();
        }

        /**
         * Spawns a baby animal of the same type.
         */
        protected void breed() {
            if (this.axolotl.getOrSetIsFemale()) {
                this.axolotl.handlePartnerBreeding(this.partner);
            } else {
                ((EnhancedAxolotl) this.partner).handlePartnerBreeding(this.axolotl);
            }

            if (this.axolotl.pregnant) {
                this.axolotl.setHasEgg(true);
                this.axolotl.pregnant = false;
            }
            if (((EnhancedAxolotl) this.partner).pregnant) {
                ((EnhancedAxolotl) this.partner).setHasEgg(true);
                ((EnhancedAxolotl) this.partner).pregnant = false;
            }
            this.axolotl.setAge(10);
            this.axolotl.resetLove();
            this.partner.setAge(10);
            this.partner.resetLove();

            ServerPlayer entityplayermp = this.axolotl.getLoveCause();
            if (entityplayermp == null && this.partner.getLoveCause() != null) {
                entityplayermp = this.partner.getLoveCause();
            }

            if (entityplayermp != null) {
                entityplayermp.awardStat(Stats.ANIMALS_BRED);
                CriteriaTriggers.BRED_ANIMALS.trigger(entityplayermp, this.axolotl, ((EnhancedAnimalAbstract) this.partner), (AgeableMob) null);
            }

            Random random = this.animal.getRandom();
            if (this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
                this.level.addFreshEntity(new ExperienceOrb(this.level, this.animal.getX(), this.animal.getY(), this.animal.getZ(), random.nextInt(7) + 1));
            }
        }
    }
}
