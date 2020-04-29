package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mokiyoki.enhancedanimals.entity.EnhancedHorse;
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
public class ModelEnhancedHorse <T extends EnhancedHorse> extends EntityModel<T> {

    private Map<Integer, HorseModelData> horseModelDataCache = new HashMap<>();
    private int clearCacheTimer = 0;

    private final ModelRenderer head;
    private final ModelRenderer earL;
    private final ModelRenderer earR;
    private final ModelRenderer jaw;
    private final ModelRenderer maneJoiner;
    private final ModelRenderer neck;
    private final ModelRenderer body;
    private final ModelRenderer tail;
    private final ModelRenderer leg1;
    private final ModelRenderer leg2;
    private final ModelRenderer leg3;
    private final ModelRenderer leg4;
    private final ModelRenderer hock3;
    private final ModelRenderer hock4;
    private final ModelRenderer hoof1;
    private final ModelRenderer hoof2;
    private final ModelRenderer hoof3;
    private final ModelRenderer hoof4;

    private Integer currentHorse = null;

    public ModelEnhancedHorse() {
        this.textureWidth = 124;
        this.textureHeight = 124;

        this.head = new ModelRenderer(this, 0, 35);
        this.head.addBox(-3.5F, -0.5F, -6.51F, 7, 7, 7, -0.5F);
        this.head.setTextureOffset(28, 35);
        this.head.addBox(-2.5F, -0.5F, -9.5F, 5, 4, 4, -0.5F);
        this.head.setTextureOffset(28, 43);
        this.head.addBox(-2.5F, -0.4F, -12.5F, 5, 4, 4, -0.4F);
        this.head.setTextureOffset(0, 0);
        this.head.addBox(-4.01F, 0.0F, -6.0F, 3, 4, 4, -1.0F);
        this.head.addBox(1.01F, 0.0F, -6.0F, 3, 4, 4, -1.0F);

        this.head.setTextureOffset(94, 0);
        this.head.addBox(-1.5F, -1.5F, -4.5F, 3, 3, 6, -0.5F); //mane piece 1
        this.head.setRotationPoint(0.0F, -14.0F, -1.0F);

        this.earL = new ModelRenderer(this, 6, 0);
        this.earL.addBox(-2.0F, -3.0F, -0.5F, 2, 3, 1);
        this.earL.setRotationPoint(-1.0F, 0.0F, -1.0F);

        this.earR = new ModelRenderer(this, 0, 0);
        this.earR.addBox(0.0F, -3.0F, -0.5F, 2, 3, 1);
        this.earR.setRotationPoint(1.0F, 0.0F, -1.0F);

        this.jaw = new ModelRenderer(this, 69,0);
        this.jaw.addBox(-2.0F, 0.0F, -9.0F, 4, 4, 7, -0.1F);
        this.jaw.setTextureOffset(0, 15);
        this.jaw.addBox(-2.0F, 2.25F, -9.0F, 4, 1, 7, -0.11F);
        this.jaw.setRotationPoint(0.0F, 2.5F, -2.0F);

        this.maneJoiner = new ModelRenderer(this, 98, 9);
        this.maneJoiner.addBox(-1.5F, -1.5F, -0.5F, 3, 2, 2, -0.505F); //mane piece 2
        this.maneJoiner.setRotationPoint(0.0F, -13.0F, 0.0F);

        this.neck = new ModelRenderer(this, 69, 15);
        this.neck.addBox(-2.0F, -13.0F, -7.0F, 4, 16, 7, 0.0F);
        this.neck.setTextureOffset(97, 13);
        this.neck.addBox(-1.5F, -13.5F, -0.5F, 3, 18, 3, -0.5F); // mane piece 3
        this.neck.setRotationPoint(0.0F, 1.0F, -5.0F);

        this.body = new ModelRenderer(this, 0, 0);
        this.body.addBox(-5.5F, -0.5F, -10.5F, 11, 11, 23, -0.5F);
        this.body.setRotationPoint(0.0F, 1.0F, 0.0F);

        this.tail = new ModelRenderer(this, 97, 34);
        this.tail.addBox(-2.0F, 0.0F, -4.0F, 4, 15, 4, 0.0F);
        this.tail.setRotationPoint(0.0F, 0.0F, 12.0F);

        this.leg1 = new ModelRenderer(this, 6, 53);
        this.leg1.addBox(0.0F, 0.5F, 0.0F, 5, 13, 5, -1.0F);
        this.leg1.setRotationPoint(-6.0F, 9.5F, -9.0F);

        this.leg2 = new ModelRenderer(this, 26, 53);
        this.leg2.addBox(0.0F, 0.5F, 0.0F, 5, 13, 5, -1.0F);
        this.leg2.setRotationPoint(1.0F, 9.5F, -9.0F);

        this.hock3 = new ModelRenderer(this, 47, 41);
        this.hock3.addBox(0.75F, -4.0F, -0.75F, 4, 7, 5, 0.0F);
        this.hock3.setRotationPoint(-6.0F, 9.5F, 8.0F);

        this.leg3 = new ModelRenderer(this, 46, 53);
        this.leg3.addBox(0.0F, 2.0F, 0.0F, 5, 12, 5, -1.0F);
        this.leg3.setRotationPoint(-6.0F, 9.5F, 8.0F);

        this.hock4 = new ModelRenderer(this, 67, 41);
        this.hock4.addBox(0.25F, -4.0F, -0.75F, 4, 7, 5, 0.0F);
        this.hock4.setRotationPoint(1.0F, 9.5F, 8.0F);

        this.leg4 = new ModelRenderer(this, 66, 53);
        this.leg4.addBox(0.0F, 2.0F, 0.0F, 5, 12, 5, -1.0F);
        this.leg4.setRotationPoint(1.0F, 9.5F, 8.0F);

        this.hoof1 = new ModelRenderer(this, 6, 71);
        this.hoof1.addBox(0.0F, 11.75F, 0.1F, 5, 3, 4, -0.5F);

        this.hoof2 = new ModelRenderer(this, 26, 71);
        this.hoof2.addBox(0.0F, 11.75F, 0.1F, 5, 3, 4, -0.5F);

        this.hoof3 = new ModelRenderer(this, 46, 71);
        this.hoof3.addBox(0.0F, 11.75F, 0.1F, 5, 3, 4, -0.5F);

        this.hoof4 = new ModelRenderer(this, 66, 71);
        this.hoof4.addBox(0.0F, 11.75F, 0.1F, 5, 3, 4, -0.5F);


        this.neck.addChild(head);
        this.neck.addChild(maneJoiner);
        this.head.addChild(earL);
        this.head.addChild(earR);
        this.head.addChild(jaw);

    }


    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {

//        this.head.render(scale);
        this.neck.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.tail.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.leg1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.leg2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.leg3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.leg4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.hock3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.hock4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.hoof1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.hoof2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.hoof3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.hoof4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);


        
    }

    @Override
    public void setLivingAnimations(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
        HorseModelData horseModelData = getCreateHorseModelData(entitylivingbaseIn);
        this.currentHorse = entitylivingbaseIn.getEntityId();

        char[] uuidArry = horseModelData.uuidArray;

        if (Character.isDigit(uuidArry[16])){
            if (uuidArry[16] - 48 == 0) {
                //0
                this.hock3.rotationPointZ = 8.5F;
                this.hock4.rotationPointZ = 8.5F;
            } else if (uuidArry[16] - 48 == 1) {
                //1
                this.hock3.rotationPointZ = 8.4F;
                this.hock4.rotationPointZ = 8.4F;
            } else if (uuidArry[16] - 48 == 2) {
                //2
                this.hock3.rotationPointZ = 8.3F;
                this.hock4.rotationPointZ = 8.3F;
            } else if (uuidArry[16] - 48 == 3) {
                //3
                this.hock3.rotationPointZ = 8.2F;
                this.hock4.rotationPointZ = 8.2F;
            } else if (uuidArry[16] - 48 == 4) {
                //4
                this.hock3.rotationPointZ = 8.1F;
                this.hock4.rotationPointZ = 8.1F;
            } else if (uuidArry[16] - 48 == 5) {
                //5
                this.hock3.rotationPointZ = 8.0F;
                this.hock4.rotationPointZ = 8.0F;
            } else if (uuidArry[16] - 48 == 6) {
                //6
                this.hock3.rotationPointZ = 7.9F;
                this.hock4.rotationPointZ = 7.9F;
            } else if (uuidArry[16] - 48 == 7) {
                //7
                this.hock3.rotationPointZ = 7.8F;
                this.hock4.rotationPointZ = 7.8F;
            } else if (uuidArry[16] - 48 == 8) {
                //0
                this.hock3.rotationPointZ = 8.5F;
                this.hock4.rotationPointZ = 8.5F;
            } else {
                //1
                this.hock3.rotationPointZ = 8.4F;
                this.hock4.rotationPointZ = 8.4F;
            }
        } else {
            char test = uuidArry[16];
            switch (test){
                case 'a':
                    //2
                    this.hock3.rotationPointZ = 8.3F;
                    this.hock4.rotationPointZ = 8.3F;
                    break;
                case 'b':
                    //3
                    this.hock3.rotationPointZ = 8.2F;
                    this.hock4.rotationPointZ = 8.2F;
                    break;
                case 'c':
                    //4
                    this.hock3.rotationPointZ = 8.1F;
                    this.hock4.rotationPointZ = 8.1F;
                    break;
                case 'd':
                    //5
                    this.hock3.rotationPointZ = 8.0F;
                    this.hock4.rotationPointZ = 8.0F;
                    break;
                case 'e':
                    //6
                    this.hock3.rotationPointZ = 7.9F;
                    this.hock4.rotationPointZ = 7.9F;
                    break;
                case 'f':
                    //7
                    this.hock3.rotationPointZ = 7.8F;
                    this.hock4.rotationPointZ = 7.8F;
                    break;
            }
        }

        if (Character.isDigit(uuidArry[17])){
            if (uuidArry[17] - 48 == 0) {
                //a
                this.leg3.rotationPointZ = this.hock3.rotationPointZ + 1.5F;
                this.leg4.rotationPointZ = this.hock4.rotationPointZ + 1.5F;
            } else if (uuidArry[17] - 48 == 1) {
                //b
                this.leg3.rotationPointZ = this.hock3.rotationPointZ + 1.35F;
                this.leg4.rotationPointZ = this.hock4.rotationPointZ + 1.35F;
            } else if (uuidArry[17] - 48 == 2) {
                //c
                this.leg3.rotationPointZ = this.hock3.rotationPointZ + 1.25F;
                this.leg4.rotationPointZ = this.hock4.rotationPointZ + 1.25F;
            } else if (uuidArry[17] - 48 == 3) {
                //d
                this.leg3.rotationPointZ = this.hock3.rotationPointZ + 1.15F;
                this.leg4.rotationPointZ = this.hock4.rotationPointZ + 1.15F;
            } else if (uuidArry[17] - 48 == 4) {
                //e
                this.leg3.rotationPointZ = this.hock3.rotationPointZ + 1.05F;
                this.leg4.rotationPointZ = this.hock4.rotationPointZ + 1.05F;
            } else if (uuidArry[17] - 48 == 5) {
                //f
                this.leg3.rotationPointZ = this.hock3.rotationPointZ + 0.95F;
                this.leg4.rotationPointZ = this.hock4.rotationPointZ + 0.95F;
            } else if (uuidArry[17] - 48 == 6) {
                //g
                this.leg3.rotationPointZ = this.hock3.rotationPointZ + 0.85F;
                this.leg4.rotationPointZ = this.hock4.rotationPointZ + 0.85F;
            } else if (uuidArry[17] - 48 == 7) {
                //h
                this.leg3.rotationPointZ = this.hock3.rotationPointZ + 0.75F;
                this.leg4.rotationPointZ = this.hock4.rotationPointZ + 0.75F;
            } else if (uuidArry[17] - 48 == 8) {
                //a
                this.leg3.rotationPointZ = this.hock3.rotationPointZ + 1.5F;
                this.leg4.rotationPointZ = this.hock4.rotationPointZ + 1.5F;
            } else {
                //b
                this.leg3.rotationPointZ = this.hock3.rotationPointZ + 1.35F;
                this.leg4.rotationPointZ = this.hock4.rotationPointZ + 1.35F;
            }
        } else {
            char test = uuidArry[17];
            switch (test){
                case 'a':
                    //c
                    this.leg3.rotationPointZ = this.hock3.rotationPointZ + 1.25F;
                    this.leg4.rotationPointZ = this.hock4.rotationPointZ + 1.25F;
                    break;
                case 'b':
                    //d
                    this.leg3.rotationPointZ = this.hock3.rotationPointZ + 1.15F;
                    this.leg4.rotationPointZ = this.hock4.rotationPointZ + 1.15F;
                    break;
                case 'c':
                    //e
                    this.leg3.rotationPointZ = this.hock3.rotationPointZ + 1.05F;
                    this.leg4.rotationPointZ = this.hock4.rotationPointZ + 1.05F;
                    break;
                case 'd':
                    //f
                    this.leg3.rotationPointZ = this.hock3.rotationPointZ + 0.95F;
                    this.leg4.rotationPointZ = this.hock4.rotationPointZ + 0.95F;
                    break;
                case 'e':
                    //g
                    this.leg3.rotationPointZ = this.hock3.rotationPointZ + 0.85F;
                    this.leg4.rotationPointZ = this.hock4.rotationPointZ + 0.85F;
                    break;
                case 'f':
                    //h
                    this.leg3.rotationPointZ = this.hock3.rotationPointZ + 0.75F;
                    this.leg4.rotationPointZ = this.hock4.rotationPointZ + 0.75F;
                    break;
            }
        }

    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.neck.rotateAngleX = headPitch * 0.017453292F + 0.8F;
        this.neck.rotateAngleY = ((netHeadYaw * 0.017453292F) * 0.40F);
        this.head.rotateAngleY = ((netHeadYaw * 0.017453292F) * 0.14F);
        this.maneJoiner.rotateAngleY = ((netHeadYaw * 0.017453292F) * 0.07F);

        this.tail.rotateAngleX = 0.6F;

        this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.3332F) * 1.4F * limbSwingAmount;
        this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.3332F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.hock3.rotateAngleX = MathHelper.cos(limbSwing * 0.3332F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.3332F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.hock4.rotateAngleX = MathHelper.cos(limbSwing * 0.3332F) * 1.4F * limbSwingAmount;
        this.leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.3332F) * 1.4F * limbSwingAmount;

        copyModelAngles(leg1, hoof1);
        copyModelAngles(leg2, hoof2);
        copyModelAngles(leg3, hoof3);
        copyModelAngles(leg4, hoof4);

        this.earL.rotateAngleZ = -0.15F;
        this.earR.rotateAngleZ = 0.15F;
        this.jaw.rotateAngleX = -0.15F;

    }

    /**
     * An intricate method that adds rotations onto a current box's angle of an axis; useful for complex animations
     *
     * @param box{ModelRenderer} - The box to rotate
     * @param doAxis{boolean[]} - An array of booleans used to determine if x, y, and z should be rotated. Array should have an index of [2]
     * @param reverse{boolean} - Boolean to determine if the rotation should be inverted
     * @param scale{float} - A multiplier to the rotation
     * @param speed{float} - The speed of the rotation
     * @param degree{float} - The degree of the angle
     * @param offset{float} - Offsets the time of the angle
     * @param distance{float} - Distance of the angle
     * @param distanceSpeed{float} - The angle distance speed
     *
     */
    public void advancedRotateAngle(ModelRenderer box, boolean[] doAxis, boolean reverse, float scale, float speed, float degree, float offset, float weight, float distance, float distanceSpeed) {
        int neg = reverse ? -1 : 1;
        box.rotateAngleX = doAxis[0] ? box.rotateAngleX + neg * MathHelper.cos(distance * (speed * scale) + offset) * (degree * scale) * distanceSpeed : box.rotateAngleX;
        box.rotateAngleY = doAxis[1] ? box.rotateAngleY + neg * MathHelper.cos(distance * (speed * scale) + offset) * (degree * scale) * distanceSpeed : box.rotateAngleY;
        box.rotateAngleZ = doAxis[2] ? box.rotateAngleZ + neg * MathHelper.cos(distance * (speed * scale) + offset) * (degree * scale) * distanceSpeed : box.rotateAngleZ;
    }

    /**
     * Much like `advancedRotateAngle` above but takes in an array of boxes which it will rotate those boxes in correlation with each other making a chain effect; good for tails for example
     * @param boxes{ModelRenderer} - The box to rotate
     * @param doAxis{boolean[]} - An array of booleans used to determine if x, y, and z should be rotated. Array should have an index of [2]
     * @param reverse{boolean} - Boolean to determine if the rotation should be inverted
     * @param scale{float} - A multiplier to the rotation
     * @param speed{float} - The speed of the rotation
     * @param degree{float} - The degree of the angle
     * @param chainOffset{float} - Offsets the time of the angle, will chain per index of boxes
     * @param distance{float} - Distance of the angle
     * @param distanceSpeed{float} - The angle distance speed\
     */

    public void chainAdvancedRotateAngle(ModelRenderer[] boxes, boolean[] doAxis, boolean reverse, float scale, float chainOffset, float speed, float degree, float distance, float distanceSpeed) {
        int neg = reverse ? -1 : 1;
        int length = boxes.length;
        for (int i = 0; i < length; i++) {
            boxes[i].rotateAngleX = doAxis[0] ? boxes[i].rotateAngleX + neg * MathHelper.cos((distance * (speed * scale) + (float) ((chainOffset * Math.PI) / (length * 2) * i))) * (degree * scale) * distanceSpeed : boxes[i].rotateAngleX;
            boxes[i].rotateAngleY = doAxis[0] ? boxes[i].rotateAngleY + neg * MathHelper.cos((distance * (speed * scale) + (float) ((chainOffset * Math.PI) / (length * 2) * i))) * (degree * scale) * distanceSpeed : boxes[i].rotateAngleY;
            boxes[i].rotateAngleZ = doAxis[0] ? boxes[i].rotateAngleZ + neg * MathHelper.cos((distance * (speed * scale) + (float) ((chainOffset * Math.PI) / (length * 2) * i))) * (degree * scale) * distanceSpeed : boxes[i].rotateAngleZ;
        }
    }

    public static void copyModelAngles(ModelRenderer source, ModelRenderer dest) {
        dest.rotateAngleX = source.rotateAngleX;
        dest.rotateAngleY = source.rotateAngleY;
        dest.rotateAngleZ = source.rotateAngleZ;
        dest.rotationPointX = source.rotationPointX;
        dest.rotationPointY = source.rotationPointY;
        dest.rotationPointZ = source.rotationPointZ;
    }

    private class HorseModelData {
        float previousSwing;
        int[] horseGenes;
        char[] uuidArray;
        String birthTime;
        float size;
        boolean sleeping;
        int lastAccessed = 0;
        long clientGameTime = 0;
//        int dataReset = 0;
    }

    private HorseModelData getHorseModelData() {
        if (this.currentHorse == null) {
            return null;
        }
        HorseModelData horseModelData = horseModelDataCache.get(this.currentHorse);
        return horseModelData;
    }

    private HorseModelData getCreateHorseModelData(T enhancedHorse) {
        clearCacheTimer++;
        if(clearCacheTimer > 50000) {
            horseModelDataCache.values().removeIf(value -> value.lastAccessed==1);
            for (HorseModelData pigModelData : horseModelDataCache.values()){
                pigModelData.lastAccessed = 1;
            }
            clearCacheTimer = 0;
        }

        if (horseModelDataCache.containsKey(enhancedHorse.getEntityId())) {
            HorseModelData horseModelData = horseModelDataCache.get(enhancedHorse.getEntityId());
            horseModelData.lastAccessed = 0;
//            pigModelData.dataReset++;
//            if (pigModelData.dataReset > 5000) {

//                pigModelData.dataReset = 0;
//            }
            horseModelData.sleeping = enhancedHorse.isAnimalSleeping();
            horseModelData.clientGameTime = (((WorldInfo)((ClientWorld)enhancedHorse.world).getWorldInfo()).getGameTime());

            return horseModelData;
        } else {
            HorseModelData horseModelData = new HorseModelData();
            horseModelData.horseGenes = enhancedHorse.getSharedGenes();
            horseModelData.size = enhancedHorse.getSize();
            horseModelData.sleeping = enhancedHorse.isAnimalSleeping();
            horseModelData.uuidArray = enhancedHorse.getCachedUniqueIdString().toCharArray();
            horseModelData.birthTime = enhancedHorse.getBirthTime();
            horseModelData.clientGameTime = (((WorldInfo)((ClientWorld)enhancedHorse.world).getWorldInfo()).getGameTime());

            if(horseModelData.horseGenes != null) {
                horseModelDataCache.put(enhancedHorse.getEntityId(), horseModelData);
            }

            return horseModelData;
        }
    }

}
