package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mokiyoki.enhancedanimals.entity.EnhancedPig;
import mokiyoki.enhancedanimals.renderer.EnhancedRendererModelNew;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class ModelEnhancedPig <T extends EnhancedPig> extends EntityModel<T> {

    private Map<Integer, PigModelData> pigModelDataCache = new HashMap<>();
    private int clearCacheTimer = 0;


    private final EnhancedRendererModelNew pig;
    private final EnhancedRendererModelNew head;
    private final EnhancedRendererModelNew cheeks;
    private final EnhancedRendererModelNew snout;
    private final EnhancedRendererModelNew mouth;
    private final EnhancedRendererModelNew tuskTL;
    private final EnhancedRendererModelNew tuskTR;
    private final EnhancedRendererModelNew tuskBL;
    private final EnhancedRendererModelNew tuskBR;
    private final EnhancedRendererModelNew earSmallL;
    private final EnhancedRendererModelNew earSmallR;
    private final EnhancedRendererModelNew earMediumL;
    private final EnhancedRendererModelNew earMediumR;
    private final EnhancedRendererModelNew neck;
    private final EnhancedRendererModelNew neckBigger;
    private final EnhancedRendererModelNew body;
    private final EnhancedRendererModelNew butt;
    private final EnhancedRendererModelNew tail0;
    private final EnhancedRendererModelNew tail1;
    private final EnhancedRendererModelNew tail2;
    private final EnhancedRendererModelNew tail3;
    private final EnhancedRendererModelNew tail4;
    private final EnhancedRendererModelNew tail5;
    private final EnhancedRendererModelNew leg1;
    private final EnhancedRendererModelNew leg2;
    private final EnhancedRendererModelNew leg3;
    private final EnhancedRendererModelNew leg4;

    private Integer currentPig = null;

    public ModelEnhancedPig() {

        this.textureWidth = 80;
        this.textureHeight = 80;

        this.pig = new EnhancedRendererModelNew(this, 49, 0);
        this.pig.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.head = new EnhancedRendererModelNew(this, 49, 0);
        this.head.addBox(-3.5F, -5.0F, -4.0F, 7, 6, 7);
        this.head.setRotationPoint(0.0F, -5.0F, -4.0F);

        this.cheeks = new EnhancedRendererModelNew(this, 49, 13);
        this.cheeks.addBox(-4.0F, 0.0F, 0.0F, 8, 5, 4, 0.25F);
        this.cheeks.setRotationPoint(0.0F, -5.5F, -4.0F);

        this.snout = new EnhancedRendererModelNew(this, 49, 22);
        this.snout.addBox(-2.0F, -5.0F, -3.0F, 4, 7, 3);
        this.snout.setRotationPoint(0.0F, -5.0F, 0.0F);

        this.mouth = new EnhancedRendererModelNew(this, 63, 22);
        this.mouth.addBox(-1.0F, -5.0F, 0.0F, 2, 6, 1);
        this.mouth.setRotationPoint(0.0F, 1.0F, -4.0F);

        this.tuskTL = new EnhancedRendererModelNew(this, 69, 22);
        this.tuskTL.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, -0.1F);
        this.tuskTL.setRotationPoint(0.5F, 0.0F, -2.0F);

        this.tuskTR = new EnhancedRendererModelNew(this, 69, 22);
        this.tuskTR.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, -0.1F);
        this.tuskTR.setRotationPoint(-0.5F, 0.0F, -2.0F);

        this.tuskBL = new EnhancedRendererModelNew(this, 69, 22);
        this.tuskBL.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, -0.1F);
        this.tuskBL.addBox(-0.5F, -2.8F, -0.5F, 1, 1, 2, -0.1F);
        this.tuskBL.setRotationPoint(0.5F, 0.0F, 1F);

        this.tuskBR = new EnhancedRendererModelNew(this, 69, 22);
        this.tuskBR.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, -0.1F);
        this.tuskBR.addBox(-0.5F, -2.8F, -0.5F, 1, 1, 2, -0.1F);
        this.tuskBR.setRotationPoint(-0.5F, 0.0F, 1F);

        this.earSmallL = new EnhancedRendererModelNew(this, 46, 0);
        this.earSmallL.addBox(0.0F, -3.0F, 0.0F, 4, 3, 1);
        this.earSmallL.setRotationPoint(3.5F, -3.0F, 0.0F);

        this.earSmallR = new EnhancedRendererModelNew(this, 70, 0);
        this.earSmallR.addBox(-4.0F, -3.0F, 0.0F, 4, 3, 1);
        this.earSmallR.setRotationPoint(-3.5F, -3.0F, 0.0F);

        this.earMediumL = new EnhancedRendererModelNew(this, 46, 0);
        this.earMediumL.addBox(0.0F, -3.0F, 0.0F, 4, 3, 1);
        this.earMediumL.setRotationPoint(2.0F, -2.0F, 0.0F);

        this.earMediumR = new EnhancedRendererModelNew(this, 70, 0);
        this.earMediumR.addBox(-4.0F, -3.0F, 0.0F, 4, 3, 1);
        this.earMediumR.setRotationPoint(-2.0F, -2.0F, 0.0F);

        this.neck = new EnhancedRendererModelNew(this, 0, 0);
        this.neck.addBox(-4.5F, -6.75F, -9.0F, 9, 7, 9);
        this.neck.setRotationPoint(0.0F, 0.0F, 9.1F);

        this.neckBigger = new EnhancedRendererModelNew(this, 0, 0);
        this.neckBigger.addBox(-4.5F, -5.75F, -9.0F, 9, 7, 9, 1.0F);
        this.neckBigger.setRotationPoint(0.0F, 0.0F, 9.1F);

        this.body = new EnhancedRendererModelNew(this, 0, 23);
        this.body.addBox(-5.0F, 0.0F, 0.0F, 10, 11, 10);
        this.body.setRotationPoint(0.0F, 18.1F, -4.0F);

        this.butt = new EnhancedRendererModelNew(this, 0, 53);
        this.butt.addBox(-4.5F, 0.0F, 0.0F, 9, 5, 9);
        this.butt.setRotationPoint(0.0F, 18.0F, 5.5F);

        this.tail0 = new EnhancedRendererModelNew(this, 36, 0);
        this.tail0.addBox(-0.5F, 0.0F, 0.0F, 1, 2, 1, -0.05F);
        this.tail0.setRotationPoint(0.0F, 5.0F, 7.5F);

        this.tail1 = new EnhancedRendererModelNew(this, 36, 0);
        this.tail1.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, -0.1F);
        this.tail1.setRotationPoint(0.0F, 1.9F, 0.5F);

        this.tail2 = new EnhancedRendererModelNew(this, 36, 3);
        this.tail2.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, -0.15F);
        this.tail2.setRotationPoint(0.0F, 1.8F, 0.0F);

        this.tail3 = new EnhancedRendererModelNew(this, 36, 6);
        this.tail3.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, -0.2F);
        this.tail3.setRotationPoint(0.0F, 1.7F, 0.0F);

        this.tail4 = new EnhancedRendererModelNew(this, 36, 9);
        this.tail4.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, -0.25F);
        this.tail4.setRotationPoint(0.0F, 1.6F, 0.0F);

        this.tail5 = new EnhancedRendererModelNew(this, 36, 12);
        this.tail5.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, -0.3F);
        this.tail5.setRotationPoint(0.0F, 1.5F, 0.0F);

        this.leg1 = new EnhancedRendererModelNew(this, 49, 32);
        this.leg1.addBox(-3.0F, 0.0F, 0.0F, 3, 8, 3);
        this.leg1.setRotationPoint(-2.0F, 16.0F, -7.0F);

        this.leg2 = new EnhancedRendererModelNew(this, 61, 32);
        this.leg2.addBox(0.0F, 0.0F, 0.0F, 3, 8, 3);
        this.leg2.setRotationPoint(2.0F, 16.0F, -7.0F);

        this.leg3 = new EnhancedRendererModelNew(this, 49, 44);
        this.leg3.addBox(-3.0F, 0.0F, 0.0F, 3, 8, 3);
        this.leg3.setRotationPoint(-2.0F, 16.0F, 7.0F);

        this.leg4 = new EnhancedRendererModelNew(this, 61, 44);
        this.leg4.addBox(0.0F, 0.0F, 0.0F, 3, 8, 3);
        this.leg4.setRotationPoint(2.0F, 16.0F, 7.0F);

        this.body.addChild(this.neckBigger);
//        this.neck.addChild(this.head);
        this.neckBigger.addChild(this.head);
        this.head.addChild(this.cheeks);
        this.head.addChild(this.snout);
        this.snout.addChild(this.tuskTL);
        this.snout.addChild(this.tuskTR);
        this.snout.addChild(this.mouth);
        this.mouth.addChild(this.tuskBL);
        this.mouth.addChild(this.tuskBR);
        this.head.addChild(this.earSmallL);
        this.head.addChild(this.earSmallR);
//        this.earSmallL.addChild(this.earMediumL);
//        this.earSmallR.addChild(this.earMediumR);
        this.butt.addChild(this.tail0);
        this.tail0.addChild(this.tail1);
        this.tail1.addChild(this.tail2);
        this.tail2.addChild(this.tail3);
        this.tail3.addChild(this.tail4);
        this.tail4.addChild(this.tail5);

        this.pig.addChild(this.body);
        this.pig.addChild(this.butt);
        this.pig.addChild(this.leg1);
        this.pig.addChild(this.leg2);
        this.pig.addChild(this.leg3);
        this.pig.addChild(this.leg4);

    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {

        PigModelData pigModelData = getPigModelData();
        float size = pigModelData.size;
        boolean sleeping = pigModelData.sleeping;

        float age = 1.0F;
        if (!(pigModelData.birthTime == null) && !pigModelData.birthTime.equals("") && !pigModelData.birthTime.equals("0")) {
            int ageTime = (int)(pigModelData.clientGameTime - Long.parseLong(pigModelData.birthTime));
            if (ageTime <= 108000) {
                age = ageTime/108000.0F;
            }
        }

        float finalPigSize = (( 3.0F * size * age) + size) / 4.0F;
        matrixStackIn.push();
        matrixStackIn.scale(finalPigSize, finalPigSize, finalPigSize);
        matrixStackIn.translate(0.0F, -1.5F + 1.5F/finalPigSize, 0.0F);

        this.pig.render(matrixStackIn, bufferIn, null, new ArrayList<>(), false, packedLightIn, packedOverlayIn, red, green, blue, alpha);

        matrixStackIn.pop();
    }

    @Override
    public void setLivingAnimations(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
        super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);

        PigModelData pigModelData = getCreatePigModelData(entitylivingbaseIn);
        this.currentPig = entitylivingbaseIn.getEntityId();

        int[] sharedGenes = pigModelData.pigGenes;
        char[] uuidArry = pigModelData.uuidArray;
        float onGround;

        boolean sleeping = pigModelData.sleeping;

        if (sleeping) {
            onGround = sleepingAnimation();
        } else {
            onGround = standingAnimation();
        }

        //snoutLength
        float snoutLength1 = -0.065F;
        float snoutLength2 = -0.065F;
        float snoutLength;

        for (int i = 1; i < sharedGenes[18];i++){
            snoutLength1 = snoutLength1 + 0.03F;
        }

        for (int i = 1; i < sharedGenes[19];i++){
            snoutLength2 = snoutLength2 + 0.03F;
        }

        if (snoutLength1 > snoutLength2){
            snoutLength = (snoutLength1*0.75F) + (snoutLength2*0.25F);
        }else if (snoutLength1 < snoutLength2){
            snoutLength = (snoutLength1*0.25F) + (snoutLength2*0.75F);
        }else{
            snoutLength = snoutLength1 + snoutLength2;
        }

        if (sharedGenes[20] == 1 || sharedGenes[21] == 1){
            if (snoutLength >= -0.12F){
                snoutLength = snoutLength - 0.01F;
            }
        }else if (sharedGenes[20] != 2 && sharedGenes[21] != 2){
            if (sharedGenes[20] == 3 || sharedGenes[21] == 3){
                snoutLength = snoutLength + 0.01F;
            }else{
                snoutLength = snoutLength + 0.02F;
            }
        }

        if (isChild){
            snoutLength = (snoutLength + 0.11F) / 2.0F;
        }



        //TODO FIX THIS
//        this.snout.offsetY = snoutLength;
        this.snout.rotateAngleX = -snoutLength;

        this.tuskTL.rotationPointY = -3.5F + ((snoutLength - 0.11F)*-10F);
        this.tuskTR.rotationPointY = -3.5F + ((snoutLength - 0.11F)*-10F);
        this.tuskBL.rotationPointY = -4.0F + ((snoutLength - 0.11F)*-10F);
        this.tuskBR.rotationPointY = -4.0F + ((snoutLength - 0.11F)*-10F);
//        this.tuskBL.rotateAngleY = snoutLength;

        float inbreedingFactor = 0.0F;

        if (sharedGenes[20] == sharedGenes[21]){
            inbreedingFactor = 0.1667F;
        }
        if (sharedGenes[22] == sharedGenes[23]){
            inbreedingFactor = inbreedingFactor + 0.1667F;
        }
        if (sharedGenes[24] == sharedGenes[25]){
            inbreedingFactor = inbreedingFactor + 0.1667F;
        }
        if (sharedGenes[26] == sharedGenes[27]){
            inbreedingFactor = inbreedingFactor + 0.1667F;
        }
        if (sharedGenes[28] == sharedGenes[29]){
            inbreedingFactor = inbreedingFactor + 0.1667F;
        }
        if (sharedGenes[30] == sharedGenes[31]){
            inbreedingFactor = inbreedingFactor + 0.1667F;
        }

        this.tail0.rotateAngleX = 1.5F * inbreedingFactor;
        this.tail1.rotateAngleX = 1.1111F * inbreedingFactor;
        this.tail2.rotateAngleX = 1.2222F * inbreedingFactor;
        this.tail3.rotateAngleX = 1.3333F * inbreedingFactor;
        this.tail4.rotateAngleX = 1.5F * inbreedingFactor;
        this.tail5.rotateAngleX = 0.1F * inbreedingFactor;

        if (Character.isLetter(uuidArry[1])){
            this.tail0.rotateAngleY = 0.3555F * inbreedingFactor;
            this.tail1.rotateAngleY = 0.3555F * inbreedingFactor;
            this.tail2.rotateAngleY = 0.3555F * inbreedingFactor;
            this.tail3.rotateAngleY = 0.3555F * inbreedingFactor;
        }else{
            this.tail0.rotateAngleY = -0.3555F * inbreedingFactor;
            this.tail1.rotateAngleY = -0.3555F * inbreedingFactor;
            this.tail2.rotateAngleY = -0.3555F * inbreedingFactor;
            this.tail3.rotateAngleY = -0.3555F * inbreedingFactor;
        }

    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        PigModelData pigModelData = getPigModelData();
//        List<String> unrenderedModels = new ArrayList<>();

        this.neck.rotateAngleX = ((float)Math.PI * 2.0F);
        this.body.rotateAngleX = ((float)Math.PI / 2F);
        this.butt.rotateAngleX = ((float)Math.PI / 2F);

        this.earSmallL.rotateAngleX = -((float)Math.PI / 2F);
        this.earSmallR.rotateAngleX = -((float)Math.PI / 2F);

        this.earSmallL.rotateAngleY = -((float)Math.PI / 4F);
        this.earSmallR.rotateAngleY = ((float)Math.PI / 4F);

        this.earSmallL.rotateAngleZ = -((float)Math.PI / 16F);
        this.earSmallR.rotateAngleZ = ((float)Math.PI / 16F);

        this.earMediumL.rotateAngleX = ((float)Math.PI / 3F);
        this.earMediumR.rotateAngleX = ((float)Math.PI / 3F);

        this.neck.rotateAngleX = this.neck.rotateAngleX + ((headPitch * 0.017453292F) / 2.0F);
        this.head.rotateAngleX = (headPitch * 0.017453292F)/2.0F;

        this.head.rotateAngleZ = -(netHeadYaw * 0.017453292F)/2.0F;

        if (!pigModelData.sleeping) {
            this.neck.rotateAngleZ = -(netHeadYaw * 0.017453292F)/2.0F;
            this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
            this.leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
            this.leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        }

        this.mouth.rotateAngleX = -0.12F;

        this.tuskTL.rotateAngleZ = 1.3F;
        this.tuskTR.rotateAngleZ = -1.3F;

        this.tuskBL.rotateAngleZ = 1.5F;
        this.tuskBR.rotateAngleZ = -1.5F;

        copyModelAngles(neck, neckBigger);

        this.tail0.rotateAngleX = -((float)Math.PI / 2F);

    }

    private float sleepingAnimation() {
        float onGround;

        onGround = 9.80F;

        this.pig.rotateAngleZ = (float)Math.PI / 2.0F;
        this.pig.setRotationPoint(15.0F, 19.0F, 0.0F);
        this.leg1.rotateAngleZ = -0.8F;
        this.leg1.rotateAngleX = 0.3F;
        this.leg3.rotateAngleZ = -0.8F;
        this.leg3.rotateAngleX = -0.3F;
        this.neck.rotateAngleZ = 0.2F;

        return onGround;
    }

    private float standingAnimation() {
        float onGround;
        onGround = 2.75F;

        this.pig.rotateAngleZ = 0.0F;
        this.pig.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.leg1.rotateAngleZ = 0.0F;
        this.leg3.rotateAngleZ = 0.0F;

        return onGround;
    }

    public static void copyModelAngles(ModelRenderer source, ModelRenderer dest) {
        dest.rotateAngleX = source.rotateAngleX;
        dest.rotateAngleY = source.rotateAngleY;
        dest.rotateAngleZ = source.rotateAngleZ;
        dest.rotationPointX = source.rotationPointX;
        dest.rotationPointY = source.rotationPointY;
        dest.rotationPointZ = source.rotationPointZ;
    }


    private class PigModelData {
        int[] pigGenes;
        char[] uuidArray;
        String birthTime;
        float size;
        boolean sleeping;
        int lastAccessed = 0;
        long clientGameTime = 0;
//        int dataReset = 0;
    }

    private PigModelData getPigModelData() {
        if (this.currentPig == null) {
            return null;
        }
        PigModelData cowModelData = pigModelDataCache.get(this.currentPig);
        return cowModelData;
    }

    private PigModelData getCreatePigModelData(T enhancedPig) {
        clearCacheTimer++;
        if(clearCacheTimer > 100000) {
            pigModelDataCache.values().removeIf(value -> value.lastAccessed==1);
            for (PigModelData pigModelData : pigModelDataCache.values()){
                pigModelData.lastAccessed = 1;
            }
            clearCacheTimer = 0;
        }

        if (pigModelDataCache.containsKey(enhancedPig.getEntityId())) {
            PigModelData pigModelData = pigModelDataCache.get(enhancedPig.getEntityId());
            pigModelData.lastAccessed = 0;
//            pigModelData.dataReset++;
//            if (pigModelData.dataReset > 5000) {

//                pigModelData.dataReset = 0;
//            }
            pigModelData.sleeping = enhancedPig.isAnimalSleeping();
            pigModelData.clientGameTime = (((WorldInfo)((ClientWorld)enhancedPig.world).getWorldInfo()).getGameTime());

            return pigModelData;
        } else {
            PigModelData pigModelData = new PigModelData();
            pigModelData.pigGenes = enhancedPig.getSharedGenes();
            pigModelData.size = enhancedPig.getSize();
            pigModelData.sleeping = enhancedPig.isAnimalSleeping();
            pigModelData.uuidArray = enhancedPig.getCachedUniqueIdString().toCharArray();
            pigModelData.birthTime = enhancedPig.getBirthTime();
            pigModelData.clientGameTime = (((WorldInfo)((ClientWorld)enhancedPig.world).getWorldInfo()).getGameTime());

            pigModelDataCache.put(enhancedPig.getEntityId(), pigModelData);

            return pigModelData;
        }
    }

}
