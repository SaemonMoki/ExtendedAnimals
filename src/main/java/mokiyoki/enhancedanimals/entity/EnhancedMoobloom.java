package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.entity.util.Colouration;
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
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
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
        this.colouration = null;
        this.enhancedAnimalTextures.add(MOOBLOOM_FLOWER[0]);
    }

    @Override
    protected void createAndSpawnEnhancedChild(World inWorld) {
        EnhancedMoobloom enhancedmoobloom = ENHANCED_MOOBLOOM.create(this.world);
        int[] babyGenes = getCalfGenes(this.mitosisGenes, this.mateMitosisGenes);

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
        if (this.colouration.getPheomelaninColour() == null || this.colouration.getMelaninColour() == null) {
            int[] genesForText = getSharedGenes();

            float blackHue = 0.0F;
            float blackSaturation = 0.05F;
            float blackBrightness = 0.05F;

            float redHue = 0.05F;
            float redSaturation = 0.57F;
            float redBrightness = 0.55F;

            //puts final values into array for processing
            float[] melanin = {blackHue, blackSaturation, blackBrightness};
            float[] pheomelanin = {redHue, redSaturation, redBrightness};

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

            //changes cow melanin from HSB to RGB
            int rgb = Color.HSBtoRGB(melanin[0], melanin[1], melanin[2]);
            melanin[0] = rgb & 0xFF;
            melanin[1] = (rgb >> 8) & 0xFF;
            melanin[2] = (rgb >> 16) & 0xFF;

            //changes cow pheomelanin from HSB to RGB
            rgb = Color.HSBtoRGB(pheomelanin[3], pheomelanin[4], pheomelanin[5]);
            pheomelanin[0] = rgb & 0xFF;
            pheomelanin[1] = (rgb >> 8) & 0xFF;
            pheomelanin[2] = (rgb >> 16) & 0xFF;

            this.colouration.setMelaninColour(melanin);
            this.colouration.setPheomelaninColour(pheomelanin);
        }

        return this.colouration;
    }
}
