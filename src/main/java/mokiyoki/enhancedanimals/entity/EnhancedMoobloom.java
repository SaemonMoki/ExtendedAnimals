package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
import mokiyoki.enhancedanimals.entity.Genetics.CowGeneticsInitialiser;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_COW;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_MOOBLOOM;

public class EnhancedMoobloom extends EnhancedCow implements net.minecraftforge.common.IForgeShearable {
    private static final EntityDataAccessor<String> MOOBLOOM_TYPE = SynchedEntityData.defineId(EnhancedMoobloom.class, EntityDataSerializers.STRING);

    private static final String[] COW_TEXTURES_RED = new String[] {
            "", "r_solid.png", "r_shaded.png"
    };

    private static final String[] COW_TEXTURES_BLACK = new String[] {
            "", "b_shoulders.png", "b_wildtype.png", "b_wildtype_darker1.png", "b_wildtype_dark.png", "b_solid.png", "b_brindle.png"
    };

    private static final String[] MOOBLOOM_FLOWER = new String[] {
            "yellow_flower.png"
    };

    public EnhancedMoobloom(EntityType<? extends EnhancedCow> entityType, Level worldIn) {
        super(entityType, worldIn);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    @Override
    protected String getSpecies() {
        return "entity.eanimod.enhanced_moobloom";
    }

    @Override
    protected int getAdultAge() { return EanimodCommonConfig.COMMON.adultAgeMoobloom.get();}

    @Override
    protected int gestationConfig() {
        return EanimodCommonConfig.COMMON.gestationDaysMoobloom.get();
    }

    @Override
    protected void setTexturePaths() {
        super.setTexturePaths();
        this.enhancedAnimalTextures.add(MOOBLOOM_FLOWER[0]);
    }

    @Override
    protected void createAndSpawnEnhancedChild(Level inWorld) {
        EnhancedMoobloom enhancedmoobloom = ENHANCED_MOOBLOOM.create(this.level);
        Genes babyGenes = new Genes(this.genetics).makeChild(this.getOrSetIsFemale(), this.mateGender, this.mateGenetics);
        defaultCreateAndSpawn(enhancedmoobloom, inWorld, babyGenes, -this.getAdultAge());
        enhancedmoobloom.configureAI();
        this.level.addFreshEntity(enhancedmoobloom);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
    }

    //TODO do I want to add more flower types?
//    private void setMoobloomType(EnhancedMoobloom.Type typeIn) {
//        this.dataManager.set(MOOBLOOM_TYPE, typeIn.name);
//    }

//    public EnhancedMoobloom.Type getMoobloomType() {
//        return EnhancedMoobloom.Type.getTypeByName(this.dataManager.get(MOOBLOOM_TYPE));
//    }

    @Override
    public EnhancedMoobloom getBreedOffspring(ServerLevel serverWorld, AgeableMob ageable) {
        super.getBreedOffspring(serverWorld, ageable);
        return null;
    }

    @Override
    public boolean isShearable(ItemStack item, Level world, net.minecraft.core.BlockPos pos) {
        return !this.isChild();
    }

    @Override
    public java.util.List<ItemStack> onSheared(Player playerEntity, ItemStack item, Level world, net.minecraft.core.BlockPos pos, int fortune) {
        java.util.List<ItemStack> ret = new java.util.ArrayList<>();
        this.level.addParticle(ParticleTypes.EXPLOSION, this.getX(), this.getY() + (double)(this.getBbHeight() / 2.0F), this.getZ(), 0.0D, 0.0D, 0.0D);
        if (!this.level.isClientSide) {
            this.remove(RemovalReason.DISCARDED);
            EnhancedCow enhancedcow = ENHANCED_COW.create(this.level);
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
            for(int i = 0; i < 5; ++i) {
                if (random.nextInt(5) == 0) {
                    ret.add(new ItemStack(Blocks.SUNFLOWER));
                } else {
                    ret.add(new ItemStack(Blocks.DANDELION));
                }
            }
            this.playSound(SoundEvents.MOOSHROOM_SHEAR, 1.0F, 1.0F);
        }
        return ret;
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

            if (melanin[0] > 0.1F) {
                melanin[0] = melanin[0] - 1.0F;
            }
                melanin[0] = 0.4F - melanin[0];
            melanin[1] = Colouration.mixColourComponent(melanin[1], 0.75F, 0.8F);
            melanin[2] = Colouration.mixColourComponent(melanin[2], pheomelanin[2], 0.25F);

            pheomelanin[0] = pheomelanin[0] + 0.1F;
            pheomelanin[2] = Colouration.mixColourComponent(pheomelanin[2]*pheomelanin[1], 1.0F, 0.75F);
            pheomelanin[1] = Colouration.mixColourComponent(pheomelanin[1], 1.0F, 0.75F);

            if (melanin[0] > 1.0F) {
                melanin[0] = 1.0F - melanin[0];
            } else if (melanin[0] < 0.0F) {
                melanin[0] = melanin[0] + 1.0F;
            }
            if (melanin[0] < 0.15F) {
                melanin[0] = 0.15F;
            }

            if (pheomelanin[0] > 1.0F) {
                pheomelanin[0] = pheomelanin[0] - 1.0F;
            }
            if (pheomelanin[0] > 0.1375F) {
                pheomelanin[0] = 0.1375F;
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
        Genes moobloomGenetics = new CowGeneticsInitialiser().generateNewGenetics(world, pos, isDomestic);
        moobloomGenetics.setAutosomalGene(118, 2);
        moobloomGenetics.setAutosomalGene(119, 2);
        return moobloomGenetics;
    }
}
