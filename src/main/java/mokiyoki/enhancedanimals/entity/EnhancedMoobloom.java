package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.entity.Genetics.CowGeneticsInitialiser;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.UUID;

import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_COW;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_MOOBLOOM;

public class EnhancedMoobloom extends EnhancedCow implements net.minecraftforge.common.IShearable {
    private static final DataParameter<String> MOOBLOOM_TYPE = EntityDataManager.createKey(EnhancedMoobloom.class, DataSerializers.STRING);

    private static final String[] COW_TEXTURES_RED = new String[] {
            "", "r_solid.png", "r_shaded.png"
    };

    private static final String[] COW_TEXTURES_BLACK = new String[] {
            "", "b_shoulders.png", "b_wildtype.png", "b_wildtype_darker1.png", "b_wildtype_dark.png", "b_solid.png", "b_brindle.png"
    };

    private static final String[] MOOBLOOM_FLOWER = new String[] {
            "yellow_flower.png"
    };

    private Effect hasStewEffect;
    private int effectDuration;
    /** Stores the UUID of the most recent lightning bolt to strike */
    private UUID lightningUUID;

    public EnhancedMoobloom(EntityType<? extends EnhancedCow> entityType, World worldIn) {
        super(entityType, worldIn);
    }

    protected void registerData() {
        super.registerData();
    }

    @Override
    protected String getSpecies() {
        return "Moobloom";
    }

    @Override
    protected void setTexturePaths() {
        super.setTexturePaths();
        this.enhancedAnimalTextures.add(MOOBLOOM_FLOWER[0]);
    }

    @Override
    protected void createAndSpawnEnhancedChild(World inWorld) {
        EnhancedMoobloom enhancedmoobloom = ENHANCED_MOOBLOOM.create(this.world);
        Genes babyGenes = new Genes(this.genetics).makeChild(this.getIsFemale(), this.mateGender, this.mateGenetics);
//        int[] babyGenes = getCalfGenes(this.mitosisGenes, this.mateMitosisGenes);

        defaultCreateAndSpawn(enhancedmoobloom, inWorld, babyGenes, -84000);

        enhancedmoobloom.setMotherUUID(this.getUniqueID().toString());
        enhancedmoobloom.configureAI();
        this.world.addEntity(enhancedmoobloom);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
    }

    //TODO do I want to add more flower types?
//    private void setMoobloomType(EnhancedMoobloom.Type typeIn) {
//        this.dataManager.set(MOOBLOOM_TYPE, typeIn.name);
//    }

//    public EnhancedMoobloom.Type getMoobloomType() {
//        return EnhancedMoobloom.Type.getTypeByName(this.dataManager.get(MOOBLOOM_TYPE));
//    }

    public EnhancedMoobloom createChild(AgeableEntity ageable) {
        super.createChild(ageable);
        return null;
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
            for(int i = 0; i < 5; ++i) {
                if (rand.nextInt(5) == 0) {
                    ret.add(new ItemStack(Blocks.SUNFLOWER));
                } else {
                    ret.add(new ItemStack(Blocks.DANDELION));
                }
            }
            this.playSound(SoundEvents.ENTITY_MOOSHROOM_SHEAR, 1.0F, 1.0F);
        }
        return ret;
    }
    
    @OnlyIn(Dist.CLIENT)
    @Override
    public Colouration getRgb() {
        if (this.colouration.getPheomelaninColour() == -1 || this.colouration.getMelaninColour() == -1) {
            this.colouration = super.getRgb();

            float[] melanin = Colouration.getHSBFromABGR(this.colouration.getMelaninColour());
            float[] pheomelanin = Colouration.getHSBFromABGR(this.colouration.getPheomelaninColour());

                melanin[0] = Colouration.mixColours(melanin[0], 0.22F, 0.5F);
                melanin[1] = 1.0F;

                pheomelanin[0] = Colouration.mixColours(pheomelanin[0], 0.20F, 0.5F);
                pheomelanin[1] = pheomelanin[1] + 0.25F;

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
        Genes moobloomGenetics = new CowGeneticsInitialiser().generateNewGenetics(world, pos, isDomestic);
        moobloomGenetics.setAutosomalGene(118, 2);
        moobloomGenetics.setAutosomalGene(119, 2);
        return moobloomGenetics;
    }
}
