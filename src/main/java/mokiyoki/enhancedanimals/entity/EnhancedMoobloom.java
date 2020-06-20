package mokiyoki.enhancedanimals.entity;

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
    private float[] cowColouration = null;

    public EnhancedMoobloom(EntityType<? extends EnhancedCow> entityType, World worldIn) {
        super(entityType, worldIn);
    }

    protected void registerData() {
        super.registerData();
    }

    @Override
    protected void setTexturePaths() {
        super.setTexturePaths();

        this.cowColouration = null;

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
    public float[] getRgb() {
        if (cowColouration == null) {
            int[] genesForText = getSharedGenes();
            String extention = "wildtype";

            float blackHue = 0.0F;
            float blackSaturation = 0.05F;
            float blackBrightness = 0.05F;

            float redHue = 0.05F;
            float redSaturation = 0.57F;
            float redBrightness = 0.55F;

            if (genesForText[0] == 1 || genesForText[1] == 1) {
                //black cow
                redHue = blackHue;
                redSaturation = blackSaturation;
                redBrightness = blackBrightness;
                extention = "black";
            } else if (genesForText[0] != 2 && genesForText[1] != 2){
                if (genesForText[0] == 3 || genesForText[1] == 3) {
                    if (genesForText[0] != 4 && genesForText[1] != 4) {
                        //red cow as in red angus, red hereford
                        blackHue = mixColours(redHue, 0.0F, 0.5F);
                        blackSaturation = mixColours(redSaturation, 1.0F, 0.5F);
                        blackBrightness = mixColours(redBrightness, blackBrightness, 0.75F);
                        extention = "red";
                    } //else red and black wildtype colouration
                } else if (genesForText[0] == 4 || genesForText[1] == 4) {
                    //cow is grey as in brahman, guzerat, and probably hungarian grey
                        redHue = 0.1F;
                        redSaturation = 0.075F;
                        redBrightness = 0.9F;
//                    if (genesForText[0] == 4 && genesForText[1] == 4) {
//                        //cow is indus white
//                        blackSaturation = redSaturation;
//                        blackBrightness = mixColours(redBrightness, blackBrightness, 0.25F);
//                        //TODO homozygous indus white needs to restrict the spread of black pigment to tips.
//                    }
                    //else its "blue" possibly carrot top..
                    //TODO do something about carrot top
                } else if (genesForText[0] == 5 && genesForText[1] == 5) {
                    //red cow as in red brahman and red gyr, indistinguishable from taros red
                    blackHue = mixColours(redHue, 0.0F, 0.5F);
                    blackSaturation = mixColours(redSaturation, 1.0F, 0.5F);
                    blackBrightness = mixColours(redBrightness, blackBrightness, 0.75F);
                    extention = "red";
                }
            } //else red and black wildtype colouration

            if (genesForText[120] == 2 && genesForText[121] == 2) {
                //indus dilution
                blackHue = redHue;
                blackSaturation = mixColours(blackSaturation, redSaturation, 0.5F);
                redHue = redHue + 0.01F;
                redSaturation = mixColours(redSaturation, 0.0F, 0.48F);
                redBrightness = mixColours(redBrightness, 1.0F, 0.55F);
                blackBrightness = mixColours(blackBrightness, redBrightness, 0.25F);
            }

            if (genesForText[2] == 2 || genesForText[3] == 2) {
                //typical bos taros dilution in murray grey and highland cattle
                redHue = mixColours(redHue, 0.1F, 0.75F);
                redSaturation = mixColours(redSaturation, 0.0F, 0.1F);
                redBrightness = mixColours(redBrightness, 1.0F, 0.4F);

                if (extention.equals("black")) {
                    blackSaturation = mixColours(blackSaturation, 1.0F, 0.05F);
                    blackBrightness = mixColours(blackBrightness, 1.0F, 0.45F);
                } else if (!extention.equals("red")) {
                    blackHue = mixColours(blackHue, redHue, 0.5F);
                    blackSaturation = mixColours(blackSaturation, redSaturation, 0.5F);
                    blackBrightness = mixColours(blackBrightness, redBrightness, 0.45F);
                }
            }


            //puts final values into array for processing
            cowColouration = new float[]{blackHue, blackSaturation, blackBrightness, redHue, redSaturation, redBrightness};

            //checks that numbers are within the valid range
            for (int i = 0; i <= 5; i++) {
                if (cowColouration[i] > 1.0F) {
                    cowColouration[i] = 1.0F;
                } else if (cowColouration[i] < 0.0F) {
                    cowColouration[i] = 0.0F;
                }
            }

            //changes cow melanin from HSB to RGB
            int rgb = Color.HSBtoRGB(cowColouration[0], cowColouration[1], cowColouration[2]);
            cowColouration[0] = (rgb >> 16) & 0xFF;
            cowColouration[1] = (rgb >> 8) & 0xFF;
            cowColouration[2] = rgb & 0xFF;

            //changes cow pheomelanin from HSB to RGB
            rgb = Color.HSBtoRGB(cowColouration[3], cowColouration[4], cowColouration[5]);
            cowColouration[3] = (rgb >> 16) & 0xFF;
            cowColouration[4] = (rgb >> 8) & 0xFF;
            cowColouration[5] = rgb & 0xFF;

            for (int i = 0; i <= 5; i++) {
                cowColouration[i] = cowColouration[i] / 255.0F;
            }

        }
        return cowColouration;
    }

}
