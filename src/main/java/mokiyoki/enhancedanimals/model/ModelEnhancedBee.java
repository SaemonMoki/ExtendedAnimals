package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mokiyoki.enhancedanimals.entity.EnhancedBee;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class ModelEnhancedBee<T extends EnhancedBee> extends EntityModel<T> {

    private Map<Integer, BeeModelData> beeModelDataCache = new HashMap<>();
    private int clearCacheTimer = 0;

    private final ModelRenderer body;
    private final ModelRenderer wingLeft;
    private final ModelRenderer wingRight;
    private final ModelRenderer antennaLeft;
    private final ModelRenderer antennaRight;
    private final ModelRenderer legs1;
    private final ModelRenderer legs2;
    private final ModelRenderer legs3;
    private final ModelRenderer stinger;

    private Integer currentBee = null;

    public ModelEnhancedBee() {
        this.textureWidth = 64;
        this.textureHeight = 64;

        this.body = new ModelRenderer(this, 0, 0);
        this.body.addBox(-3.5F, 0.0F, 0.0F, 7, 7, 10);

        this.wingLeft = new ModelRenderer(this, 0, 0);
        this.wingLeft.addBox(-3.5F, 0.0F, 0.0F, 9, 9, 0, 0.001F);

        this.wingRight = new ModelRenderer(this, 0, 0);
        this.wingRight.addBox(-3.5F, 0.0F, 0.0F, 9, 9, 0, 0.001F);

        this.antennaLeft = new ModelRenderer(this, 0, 0);
        this.antennaLeft.addBox(-3.5F, 0.0F, 0.0F, 0, 5, 5);

        this.antennaRight = new ModelRenderer(this, 0, 0);
        this.antennaRight.addBox(-3.5F, 0.0F, 0.0F, 0, 5, 5);

        this.legs1 = new ModelRenderer(this, 0, 0);
        this.legs1.addBox(-3.5F, 0.0F, 0.0F, 5, 3, 0);

        this.legs2 = new ModelRenderer(this, 0, 0);
        this.legs2.addBox(-3.5F, 0.0F, 0.0F, 5, 3, 0);

        this.legs3 = new ModelRenderer(this, 0, 0);
        this.legs3.addBox(-3.5F, 0.0F, 0.0F, 5, 3, 0);

        this.stinger = new ModelRenderer(this, 0, 0);
        this.stinger.addBox(-3.5F, 0.0F, 0.0F, 0, 1, 3);

        this.body.addChild(wingLeft);
        this.body.addChild(wingRight);
        this.body.addChild(antennaLeft);
        this.body.addChild(antennaRight);
        this.body.addChild(legs1);
        this.body.addChild(legs2);
        this.body.addChild(legs3);
        this.body.addChild(stinger);

    }


    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        boolean isFemale = true;

        this.body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

        if (isFemale) {
            this.stinger.showModel = true;
        } else {
            this.stinger.showModel = false;
        }
        
    }

    @Override
    public void setLivingAnimations(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
        BeeModelData beeModelData = getCreateBeeModelData(entitylivingbaseIn);
        this.currentBee = entitylivingbaseIn.getEntityId();

        char[] uuidArry = beeModelData.uuidArray;
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }


    public static void copyModelAngles(ModelRenderer source, ModelRenderer dest) {
        dest.rotateAngleX = source.rotateAngleX;
        dest.rotateAngleY = source.rotateAngleY;
        dest.rotateAngleZ = source.rotateAngleZ;
        dest.rotationPointX = source.rotationPointX;
        dest.rotationPointY = source.rotationPointY;
        dest.rotationPointZ = source.rotationPointZ;
    }

    private class BeeModelData {
        int[] beeGenes;
        char[] uuidArray;
        String birthTime;
        float size = 1.0F;
        boolean sleeping = false;
        int lastAccessed = 0;
        long clientGameTime = 0;
//        int dataReset = 0;
    }

    private BeeModelData getBeeModelData() {
        if (this.currentBee == null || !beeModelDataCache.containsKey(this.currentBee)) {
            return new BeeModelData();
        }
        return beeModelDataCache.get(this.currentBee);
    }

    private BeeModelData getCreateBeeModelData(T enhancedBee) {
        clearCacheTimer++;
        if(clearCacheTimer > 50000) {
            beeModelDataCache.values().removeIf(value -> value.lastAccessed==1);
            for (BeeModelData beeModelData : beeModelDataCache.values()){
                beeModelData.lastAccessed = 1;
            }
            clearCacheTimer = 0;
        }

        if (beeModelDataCache.containsKey(enhancedBee.getEntityId())) {
            BeeModelData beeModelData = beeModelDataCache.get(enhancedBee.getEntityId());
            beeModelData.lastAccessed = 0;
//            pigModelData.dataReset++;
//            if (pigModelData.dataReset > 5000) {

//                pigModelData.dataReset = 0;
//            }
            beeModelData.sleeping = enhancedBee.isAnimalSleeping();
            beeModelData.clientGameTime = (((WorldInfo)((ClientWorld)enhancedBee.world).getWorldInfo()).getGameTime());

            return beeModelData;
        } else {
            BeeModelData beeModelData = new BeeModelData();
            beeModelData.beeGenes = enhancedBee.getSharedGenes();
            beeModelData.size = enhancedBee.getSize();
            beeModelData.sleeping = enhancedBee.isAnimalSleeping();
            beeModelData.uuidArray = enhancedBee.getCachedUniqueIdString().toCharArray();
            beeModelData.birthTime = enhancedBee.getBirthTime();
            beeModelData.clientGameTime = (((WorldInfo)((ClientWorld)enhancedBee.world).getWorldInfo()).getGameTime());

            if(beeModelData.beeGenes != null) {
                beeModelDataCache.put(enhancedBee.getEntityId(), beeModelData);
            }

            return beeModelData;
        }
    }

}
