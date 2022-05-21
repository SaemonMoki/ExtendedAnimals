package mokiyoki.enhancedanimals.entity;

import com.mojang.blaze3d.platform.NativeImage;
import mokiyoki.enhancedanimals.entity.genetics.AxolotlGeneticsInitialiser;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import mokiyoki.enhancedanimals.init.FoodSerialiser;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.items.EnhancedAxolotlBucket;
import mokiyoki.enhancedanimals.renderer.texture.EnhancedLayeredTexture;
import mokiyoki.enhancedanimals.util.Genes;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.decoration.LeashFenceKnotEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

import static mokiyoki.enhancedanimals.init.FoodSerialiser.axolotlFoodMap;
import static net.minecraft.world.entity.ai.attributes.AttributeSupplier.*;

public class EnhancedAxolotl extends EnhancedAnimalAbstract implements Bucketable {
    private static final EntityDataAccessor<Boolean> HAS_EGG = SynchedEntityData.defineId(EnhancedAxolotl.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_PLAYING_DEAD = SynchedEntityData.defineId(EnhancedAxolotl.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(EnhancedAxolotl.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<String> BUCKET_IMG = SynchedEntityData.defineId(EnhancedAxolotl.class, EntityDataSerializers.STRING);
    private static final int AXOLOTL_TOTAL_AIR_SUPPLY = 6000;
    private int sleepTimer;
    private boolean isTempted = false;

    private static final String[] AXOLOTL_TEXTURES_BASE = new String[] {
            "c_extrapigment.png", "c_natural_xanthic.png", "natural.png", "natural_xanthic.png"
    };

    private static final String[] AXOLOTL_TEXTURES_GILLS = new String[] {
            "gills_base.png", "longgills_base.png", "greatergills_base.png"
    };

    private static final String[] AXOLOTL_TEXTURES_XANTHIN = new String[] {
            "", "low_xanthophores.png", "natural_xanthophores.png", "high_xanthophores.png"
    };

    private static final String[][][] AXOLOTL_TEXTURES_MELANIN = new String[][][] {
        {
                {"natural_melanin.png"},
                {"leutistic0.png"}
        }, {
            {"copper_melanin.png"},
            {"copper_leutistic0.png"}
        }
    };

    private static final String[] AXOLOTL_TEXTURES_IRIDESCENCE = new String[] {
            "", "low_iridophores.png", "natural_iridophores.png", "high_iridophores.png"
    };

    private static final String[] AXOLOTL_TEXTURES_SPOTS = new String[] {
            "",
            "speckled_whitebelly.png", "speckled_pibaldbelly.png", "speckled_pibald.png",
            "varigated_whitebelly.png", "varigated_pibaldbelly.png", "varigated_pibald.png",
            "painted_whitebelly.png", "painted_pibaldbelly.png", "painted_pibald.png"
    };

    private static final String[][][] AXOLOTL_TEXTURES_BERKSHIRE = new String[][][] {
        {
            {"star.png", "snip.png", },
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

    public EnhancedAxolotl(EntityType<? extends EnhancedAxolotl> type, Level worldIn) {
        super(type, worldIn, 2, Reference.AXOLOTL_AUTOSOMAL_GENES_LENGTH, false);
    }

    protected void registerGoals() {
    }

    public static Builder prepareAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 14.0D)
                .add(Attributes.MOVEMENT_SPEED, 1.0D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D);
    }

//    protected void customServerAiStep() {
//        this.level.getProfiler().push("axolotlBrain");
//        this.getBrain().tick((ServerLevel)this.level, this);
//        this.level.getProfiler().pop();
//        this.level.getProfiler().push("axolotlActivityUpdate");
//        AxolotlAi.updateActivity(this);
//        this.level.getProfiler().pop();
//        if (!this.isNoAi()) {
//            Optional<Integer> optional = this.getBrain().getMemory(MemoryModuleType.PLAY_DEAD_TICKS);
//            this.setPlayingDead(optional.isPresent() && optional.get() > 0);
//        }
//
//    }

    @Override
    public EntityDimensions getDimensions(Pose poseIn) {
        return EntityDimensions.scalable(1.2F, 0.4F).scale(this.getRenderScale());
    }

    public float getRenderScale() {
        return this.isGrowing() ? (0.1F + (0.9F * (this.growthAmount()))) : 1.0F;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HAS_EGG, false);
        this.entityData.define(DATA_PLAYING_DEAD, false);
        this.entityData.define(FROM_BUCKET, false);
        this.entityData.define(BUCKET_IMG, "");
    }

    public void setBucketImage(String image) {
        this.entityData.set(BUCKET_IMG, image);
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
        if (!this.isInWater() || this.hasEgg()) {
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
        this.sleepTimer = this.sleepTimer > 0 ? this.sleepTimer-- : this.sleepTimer++;
        if (this.sleepTimer == 0) {
            //finished sleeping
            this.sleepTimer = -(this.random.nextInt(6000)+8000);
        } else if (this.sleepTimer==-1 && !(this.hasEgg())) {
            //is tired
            this.sleepTimer = this.random.nextInt(6000)+1200;
        }
        return (this.sleepTimer > 0) && this.awokenTimer == 0 && !this.sleeping;
    }

    @Override
    protected String getSpecies() {
        return "entity.eanimod.enhanced_axolotl";
    }

    @Override
    protected int getAdultAge() { return 24000;}

    @Override
    protected int gestationConfig() {
        return 24000;
    }

    @Override
    protected void incrementHunger() {

    }

    @Override
    protected void runExtraIdleTimeTick() {

    }

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
            case 6: size = size * 0.5F; break;
            case 5: size = size * 0.6F; break;
            case 4: size = size * 0.7F; break;
            case 3: size = size * 0.8F; break;
            case 2: size = size * 0.9F; break;
            default:
        }

        this.setAnimalSize(size);
    }

    @Override
    protected void createAndSpawnEnhancedChild(Level world) {

    }

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

    public boolean isPushedByWater() {
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

    @Nullable
    protected SoundEvent getAmbientSound() {
        return this.isInWater() ? SoundEvents.AXOLOTL_IDLE_WATER : SoundEvents.AXOLOTL_IDLE_AIR;
    }

    protected void playSwimSound(float volume) {
        super.playSwimSound(volume * 1.5F);
    }

    protected SoundEvent getSwimSound() {
        return SoundEvents.AXOLOTL_SWIM;
    }

    @Nullable
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.AXOLOTL_HURT;
    }

    @Nullable
    protected SoundEvent getDeathSound() {
        return SoundEvents.AXOLOTL_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        if (!this.isSilent() && this.getBells() && this.random.nextBoolean()) {
            this.playSound(SoundEvents.NOTE_BLOCK_IRON_XYLOPHONE, 0.5F, 0.2F);
            this.playSound(SoundEvents.NOTE_BLOCK_CHIME, 1.4F, 0.155F);
        }
        super.playStepSound(pos, blockIn);
    }

    @OnlyIn(Dist.CLIENT)
    public String getTexture() {
        if (this.enhancedAnimalTextures.isEmpty()) {
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
                if (gene[2] == 1 || gene[3] == 1) {
                    //xanthic (wildtype)
                    base = gene[10] == 1 || gene[11] == 1 ? 3 : 1;
                } else {
                    //axanthic
                    base = gene[10] == 1 || gene[11] == 1 ? 2 : 0;
                }

            } else if (gene[0] == 1 || gene[1] == 1) {
                //Leucistic
                base = gene[10] == 1 || gene[11] == 1 ? 2 : 0;
                pattern = 1;
            }

            addTextureToAnimal(AXOLOTL_TEXTURES_GILLS, gills, null);
            addTextureToAnimal(AXOLOTL_TEXTURES_BASE, base, null);
            addTextureToAnimal(AXOLOTL_TEXTURES_MELANIN, copper, pattern, 0, gene[0] == 1 || gene[1] == 1);
            addTextureToAnimal("eyel_.png");
            addTextureToAnimal("eyer_.png");
        }
    }

    @Override
    protected void setAlphaTexturePaths() {

    }

    @OnlyIn(Dist.CLIENT)
    public Colouration getRgb() {
        this.colouration = super.getRgb();
        Genes genes = getSharedGenes();
        if (genes != null) {
            if (this.colouration.getDyeColour() == -1 || this.colouration.getLeftEyeColour() == -1 || this.colouration.getRightEyeColour() == -1) {
                int[] gene = genes.getAutosomalGenes();

                if (gene[10] != 1 || gene[11] != 1) {
                    this.colouration.setDyeColour(Colouration.mixAxolotlHue((float) gene[24] / 255, (float) gene[25] / 255));
                }

                float eyeHue = 0.75F;
                float eyeSaturation = 0.5F;
                float eyeBrightness = 0.25F;

                if (gene[20] != 1 && gene[21] != 1) {
                    if (gene[20] == 4 || gene[21] == 4) {
                        int genenum = gene[20] == 4 ? 21 : 20;
                        //light eyes
                        float[] lightEyes = Colouration.getAxolotlLightEyes((float) gene[22] / 255, (float) gene[23] / 255);

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
                        eyeHue = Colouration.mixHueComponent((float) gene[22] / 255, (float) gene[23] / 255, 0.5F);

                        if (gene[20] == 2) {
                            //dark eyes
                            if (gene[21] == 3) {
                                //dark-pigmented eyes
                                eyeSaturation = 1.0F;
                                eyeBrightness = 0.5F;
                            } else if (gene[21] == 5) {
                                //dark-pastel eyes
                                eyeSaturation = 0.5F;
                                eyeBrightness = 0.4F;
                            } else {
                                //dark-glow eyes
                                //dark eyes
                                eyeSaturation = 1.0F;
                                eyeBrightness = 0.25F;
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
                            eyeSaturation = 0.5F;
                            eyeBrightness = 0.75F;
                        }
                    }
                } else if (gene[0] == 2 && gene[1] == 2) {
                    eyeHue = 0.96F;
                    eyeSaturation = 0.6F;
                    eyeBrightness = 0.7F;
                }

                this.colouration.setLeftEyeColour(Colouration.HSBtoABGR(eyeHue, eyeSaturation, eyeBrightness));
                this.colouration.setRightEyeColour(Colouration.HSBtoABGR(eyeHue, eyeSaturation, eyeBrightness));
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
        this.setBucketImage(compound.getString("BucketImage"));
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
    public Genes createInitialBreedGenes(LevelAccessor world, BlockPos pos, String breed) {
        return new AxolotlGeneticsInitialiser().generateWithBreed(this.level, pos, breed);
    }

    @OnlyIn(Dist.CLIENT)
    public void setBucketImage(EnhancedLayeredTexture texture) {
        if (this.getBucketImage().isEmpty() && this.isAlive()) {
            if (this.genetics != null && texture.hasImage()) {
                boolean longGills = this.genetics.isHomozygousFor(34, 2) ^ this.genetics.isHomozygousFor(36, 2);
                boolean longLegs = this.genetics.isHomozygousFor(32, 2);
                boolean hasCollar = this.hasCollar();
                boolean hasBell = this.getBells();
                int[] axolotlBucketImage = new int[84];
                int[][] axolotlBucketImageMap = new int[][]{
                        {longGills ? 40 : 39, 1}, {40, 2}, {41, 1}, {46, 1}, {47, 2}, {longGills ? 47 : 48, 1},
                        {37, longGills ? 6 : 5}, {38, 6}, {41, 3}, {41, 4}, {46, 2}, {46, 3}, {49, 6}, {50, longGills ? 6 : 5},
                        {38, 7}, {40, 8}, {2, 6}, {6, 1}, {7, 1}, {8, 0}, {9, 0}, {10, 1}, {11, 1}, {15, 6}, {47, 8}, {49, 7},
                        {longGills ? 56 : 57, 10}, {39, 12}, {5, 2}, {6, 2}, {7, 2}, {8, 2}, {9, 2}, {10, 2}, {11, 2}, {12, 2}, {48, 12}, {longGills ? 51 : 50, 10},
                        {38, 10}, {39, 11}, {5, 3}, {6, 3}, {7, 3}, {8, 3}, {9, 3}, {10, 3}, {11, 3}, {12, 3}, {48, 11}, {49, 10},
                        {37, 12}, {5, 4}, {6, 6}, {7, 6}, {8, 5}, {9, 5}, {10, 6}, {10, 6}, {12, 4}, {50, 12},
                        {hasCollar ? 42 : 59, hasCollar ? 62 : (longLegs ? 2 : 3)}, {hasCollar ? 44 : -1, 62}, {hasCollar?(hasBell?15:45):-1,(hasBell?18:62)}, {hasCollar?(hasBell?16:47):-1,(hasBell?37:62)}, {hasCollar ? 48 : -1, 63}, {hasCollar ? 49 : 59, hasCollar ? 62 : (longLegs ? 10 : 11)},
                        {59, longLegs ? 3 : 4},{hasBell?15:-1,39},{hasBell?17:-1,39}, {59, longLegs ? 10 : 11},
                        {longLegs ? -1 : 58, 6}, {59, longLegs ? 4 : 5}, {longLegs ? -1 : 60, 6}, {longLegs ? -1 : 58, 12}, {59, longLegs ? 11 : 12}, {longLegs ? -1 : 60, 12},
                        {longLegs ? 58 : -1, 6}, {59, longLegs ? 5 : 6}, {longLegs ? 60 : -1, 6}, {longLegs ? 58 : -1, 12}, {59, longLegs ? 12 : 13}, {longLegs ? 60 : -1, 12},
                        {longLegs ? 59 : -1, 6}, {longLegs ? 59 : -1, 13}
                };
                NativeImage image = texture.getImage();
                for (int i = 0; i < 84; i++) {
                    int[] xy = axolotlBucketImageMap[i];
                    if (xy[0] == -1) {
                        axolotlBucketImage[i] = -1;
                    } else {
                        int rgba = image.getPixelRGBA(xy[0], xy[1]);
                        axolotlBucketImage[i] = (rgba >> 24 & 255) == 0 ? -1 : rgba;
                    }
                }
                this.setBucketImage(getImageString(axolotlBucketImage));
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
            image[i] = Integer.parseInt(color);
            i++;
        }
        return image;
    };

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (getSharedGenes().isValid()/* && !this.getBucketImage().isEmpty()*/) {
            return Bucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
        } else {
            return super.mobInteract(player, hand);
        }
    }

    @Override
    public void saveToBucketTag(ItemStack stack) {
        Bucketable.saveDefaultDataToBucketTag(this, stack);
        if (stack.getItem() instanceof EnhancedAxolotlBucket) {
            EnhancedAxolotlBucket.setImage(stack, getImageArrayFromString(this.getBucketImage()));
            EnhancedAxolotlBucket.setGenes(stack, getSharedGenes());
        }
    }

    @Override
    public void loadFromBucketTag(CompoundTag tag) {
        Bucketable.loadDefaultDataFromBucketTag(this, tag);
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(ModItems.ENHANCED_AXOLOTL_BUCKET.get());
    }

    @Override
    public SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_FILL_AXOLOTL;
    }
}
