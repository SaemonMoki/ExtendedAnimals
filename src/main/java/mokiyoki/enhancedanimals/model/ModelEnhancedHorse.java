package mokiyoki.enhancedanimals.model;

import mokiyoki.enhancedanimals.entity.EnhancedHorse;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModelEnhancedHorse <T extends EnhancedHorse> extends EntityModel<T> {

    private final RendererModel head;
    private final RendererModel earL;
    private final RendererModel earR;
    private final RendererModel jaw;
    private final RendererModel maneJoiner;
    private final RendererModel neck;
    private final RendererModel body;
    private final RendererModel tail;
    private final RendererModel leg1;
    private final RendererModel leg2;
    private final RendererModel leg3;
    private final RendererModel leg4;
    private final RendererModel hock3;
    private final RendererModel hock4;
    private final RendererModel hoof1;
    private final RendererModel hoof2;
    private final RendererModel hoof3;
    private final RendererModel hoof4;

    public ModelEnhancedHorse() {
        this.textureWidth = 124;
        this.textureHeight = 124;

        this.head = new RendererModel(this, 82, 111);
        this.head.addBox(-3.0F, 0.0F, -6.0F, 6, 6, 6, 0.0F);
        this.head.setTextureOffset(0, 0);
        this.head.addBox(-2.0F, 0.0F, -9.0F, 4, 3, 3, 0.0F);
        this.head.addBox(-1.5F, -1.5F, -4.5F, 3, 3, 6, -0.5F);
        this.head.setTextureOffset(0, 118);
        this.head.addBox(-2.0F, 0.1F, -12.0F, 4, 3, 3, 0.1F);
        this.head.setRotationPoint(0.0F, -14.0F, -1.0F);

        this.earL = new RendererModel(this, 19, 120);
        this.earL.addBox(-2.0F, -3.0F, -0.5F, 2, 3, 1);
        this.earL.setRotationPoint(-1.0F, 0.0F, -1.0F);

        this.earR = new RendererModel(this, 25, 120);
        this.earR.addBox(0.0F, -3.0F, -0.5F, 2, 3, 1);
        this.earR.setRotationPoint(1.0F, 0.0F, -1.0F);

        this.jaw = new RendererModel(this, 0,0);
        this.jaw.addBox(-2.0F, 0.0F, -9.0F, 4, 4, 7, -0.1F);
        this.jaw.addBox(-2.0F, 2.25F, -9.0F, 4, 1, 7, -0.1F);
        this.jaw.setRotationPoint(0.0F, 2.5F, -2.0F);

        this.maneJoiner = new RendererModel(this, 0, 0);
        this.maneJoiner.addBox(-1.5F, -1.5F, -0.5F, 3, 2, 2, -0.505F);
        this.maneJoiner.setRotationPoint(0.0F, -13.0F, 0.0F);

        this.neck = new RendererModel(this, 0, 0);
        this.neck.addBox(-2.0F, -13.0F, -7.0F, 4, 16, 7, 0.0F);
        this.neck.addBox(-1.5F, -13.5F, -0.5F, 3, 18, 3, -0.5F);
        this.neck.setRotationPoint(0.0F, 0.0F, -5.0F);

        this.body = new RendererModel(this, 0, 0);
        this.body.addBox(-5.0F, 0.0F, -10.0F, 10, 10, 22, 0.0F);

        this.tail = new RendererModel(this, 0, 0);
        this.tail.addBox(-2.0F, 0.0F, -4.0F, 4, 15, 4, 0.0F);
        this.tail.setRotationPoint(0.0F, 0.0F, 12.0F);

        this.leg1 = new RendererModel(this, 0, 0);
        this.leg1.addBox(0.0F, 0.0F, 0.0F, 5, 13, 5, -1.0F);
        this.leg1.setRotationPoint(-6.0F, 9.0F, -9.0F);

        this.leg2 = new RendererModel(this, 0, 0);
        this.leg2.addBox(0.0F, 0.0F, 0.0F, 5, 13, 5, -1.0F);
        this.leg2.setRotationPoint(1.0F, 9.0F, -9.0F);

        this.hock3 = new RendererModel(this, 0, 0);
        this.hock3.addBox(0.75F, -4.0F, -0.75F, 4, 7, 5, 0.0F);
        this.hock3.setRotationPoint(-6.0F, 9.0F, 8.0F);

        this.leg3 = new RendererModel(this, 0, 0);
        this.leg3.addBox(0.0F, 0.0F, 0.0F, 5, 12, 5, -1.0F);
        this.leg3.setRotationPoint(-6.0F, 10.0F, 8.0F);

        this.hock4 = new RendererModel(this, 0, 0);
        this.hock4.addBox(0.25F, -4.0F, -0.75F, 4, 7, 5, 0.0F);
        this.hock4.setRotationPoint(1.0F, 9.0F, 8.0F);

        this.leg4 = new RendererModel(this, 0, 0);
        this.leg4.addBox(0.0F, 0.0F, 0.0F, 5, 12, 5, -1.0F);
        this.leg4.setRotationPoint(1.0F, 10.0F, 8.0F);

        this.hoof1 = new RendererModel(this, 106, 117);
        this.hoof1.addBox(0.0F, 11.0F, 0.1F, 5, 3, 4, -0.5F);

        this.hoof2 = new RendererModel(this, 106, 117);
        this.hoof2.addBox(0.0F, 11.0F, 0.1F, 5, 3, 4, -0.5F);

        this.hoof3 = new RendererModel(this, 106, 117);
        this.hoof3.addBox(0.0F, 11.0F, 0.1F, 5, 3, 4, -0.5F);

        this.hoof4 = new RendererModel(this, 106, 117);
        this.hoof4.addBox(0.0F, 11.0F, 0.1F, 5, 3, 4, -0.5F);


        this.neck.addChild(head);
        this.neck.addChild(maneJoiner);
        this.head.addChild(earL);
        this.head.addChild(earR);
        this.head.addChild(jaw);

    }


    @Override
    public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

//        this.head.render(scale);
        this.neck.render(scale);
        this.body.render(scale);
        this.tail.render(scale);
        this.leg1.render(scale);
        this.leg2.render(scale);
        this.leg3.render(scale);
        this.leg4.render(scale);
        this.hock3.render(scale);
        this.hock4.render(scale);
        this.hoof1.render(scale);
        this.hoof2.render(scale);
        this.hoof3.render(scale);
        this.hoof4.render(scale);


        
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
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

    @Override
    public void setLivingAnimations(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
//        int[] sharedGenes = ((EnhancedHorse) entitylivingbaseIn).getSharedGenes();
        char[] uuidArry = ((EnhancedHorse) entitylivingbaseIn).getCachedUniqueIdString().toCharArray();

        if (Character.isDigit(uuidArry[16])){
            if (uuidArry[16] - 48 == 0) {
                this.hock3.rotationPointZ = 8.5F;
                this.hock4.rotationPointZ = 8.5F;
            } else if (uuidArry[16] - 48 == 1) {
                this.hock3.rotationPointZ = 8.0F;
                this.hock4.rotationPointZ = 8.0F;
            } else if (uuidArry[16] - 48 == 2) {
                this.hock3.rotationPointZ = 7.5F;
                this.hock4.rotationPointZ = 7.5F;
            } else if (uuidArry[16] - 48 == 3) {
                this.hock3.rotationPointZ = 7.0F;
                this.hock4.rotationPointZ = 7.0F;
            } else if (uuidArry[16] - 48 == 4) {
                this.hock3.rotationPointZ = 6.5F;
                this.hock4.rotationPointZ = 6.5F;
            } else if (uuidArry[16] - 48 == 5) {
                this.hock3.rotationPointZ = 6.0F;
                this.hock4.rotationPointZ = 6.0F;
            } else if (uuidArry[16] - 48 == 6) {
                this.hock3.rotationPointZ = 5.5F;
                this.hock4.rotationPointZ = 5.5F;
            } else if (uuidArry[16] - 48 == 7) {
                this.hock3.rotationPointZ = 5.0F;
                this.hock4.rotationPointZ = 5.0F;
            } else if (uuidArry[16] - 48 == 8) {
                this.hock3.rotationPointZ = 8.5F;
                this.hock4.rotationPointZ = 8.5F;
            } else {
                this.hock3.rotationPointZ = 8.0F;
                this.hock4.rotationPointZ = 8.0F;
            }
        } else {
            char test = uuidArry[16];
            switch (test){
                case 'a':
                    this.hock3.rotationPointZ = 7.5F;
                    this.hock4.rotationPointZ = 7.5F;
                    break;
                case 'b':
                    this.hock3.rotationPointZ = 7.0F;
                    this.hock4.rotationPointZ = 7.0F;
                    break;
                case 'c':
                    this.hock3.rotationPointZ = 6.5F;
                    this.hock4.rotationPointZ = 6.5F;
                    break;
                case 'd':
                    this.hock3.rotationPointZ = 6.0F;
                    this.hock4.rotationPointZ = 6.0F;
                    break;
                case 'e':
                    this.hock3.rotationPointZ = 6.5F;
                    this.hock4.rotationPointZ = 6.5F;
                    break;
                case 'f':
                    this.hock3.rotationPointZ = 5.0F;
                    this.hock4.rotationPointZ = 5.0F;
                    break;
            }
        }

        if (Character.isDigit(uuidArry[17])){
            if (uuidArry[17] - 48 == 0) {
                this.leg3.rotationPointZ = this.hock3.rotationPointZ + 1.5F;
                this.leg4.rotationPointZ = this.hock4.rotationPointZ + 1.5F;
            } else if (uuidArry[17] - 48 == 1) {
                this.leg3.rotationPointZ = this.hock3.rotationPointZ + 1.25F;
                this.leg4.rotationPointZ = this.hock4.rotationPointZ + 1.25F;
            } else if (uuidArry[17] - 48 == 2) {
                this.leg3.rotationPointZ = this.hock3.rotationPointZ + 1.0F;
                this.leg4.rotationPointZ = this.hock4.rotationPointZ + 1.0F;
            } else if (uuidArry[17] - 48 == 3) {
                this.leg3.rotationPointZ = this.hock3.rotationPointZ + 0.75F;
                this.leg4.rotationPointZ = this.hock4.rotationPointZ + 0.75F;
            } else if (uuidArry[17] - 48 == 4) {
                this.leg3.rotationPointZ = this.hock3.rotationPointZ + 0.5F;
                this.leg4.rotationPointZ = this.hock4.rotationPointZ + 0.5F;
            } else if (uuidArry[17] - 48 == 5) {
                this.leg3.rotationPointZ = this.hock3.rotationPointZ + 0.25F;
                this.leg4.rotationPointZ = this.hock4.rotationPointZ + 0.25F;
            } else if (uuidArry[17] - 48 == 6) {
                this.leg3.rotationPointZ = this.hock3.rotationPointZ;
                this.leg4.rotationPointZ = this.hock4.rotationPointZ;
            } else if (uuidArry[17] - 48 == 7) {
                this.leg3.rotationPointZ = this.hock3.rotationPointZ - 0.25F;
                this.leg4.rotationPointZ = this.hock4.rotationPointZ - 0.25F;
            } else if (uuidArry[17] - 48 == 8) {
                this.leg3.rotationPointZ = this.hock3.rotationPointZ + 1.5F;
                this.leg4.rotationPointZ = this.hock4.rotationPointZ + 1.5F;
            } else {
                this.leg3.rotationPointZ = this.hock3.rotationPointZ + 1.25F;
                this.leg4.rotationPointZ = this.hock4.rotationPointZ + 1.25F;
            }
        } else {
            char test = uuidArry[17];
            switch (test){
                case 'a':
                    this.leg3.rotationPointZ = this.hock3.rotationPointZ + 1.0F;
                    this.leg4.rotationPointZ = this.hock4.rotationPointZ + 1.0F;
                    break;
                case 'b':
                    this.leg3.rotationPointZ = this.hock3.rotationPointZ + 0.75F;
                    this.leg4.rotationPointZ = this.hock4.rotationPointZ + 0.75F;
                    break;
                case 'c':
                    this.leg3.rotationPointZ = this.hock3.rotationPointZ + 0.5F;
                    this.leg4.rotationPointZ = this.hock4.rotationPointZ + 0.5F;
                    break;
                case 'd':
                    this.leg3.rotationPointZ = this.hock3.rotationPointZ + 0.25F;
                    this.leg4.rotationPointZ = this.hock4.rotationPointZ + 0.25F;
                    break;
                case 'e':
                    this.leg3.rotationPointZ = this.hock3.rotationPointZ;
                    this.leg4.rotationPointZ = this.hock4.rotationPointZ;
                    break;
                case 'f':
                    this.leg3.rotationPointZ = this.hock3.rotationPointZ - 0.25F;
                    this.leg4.rotationPointZ = this.hock4.rotationPointZ - 0.25F;
                    break;
            }
        }

    }

    /**
     * An intricate method that adds rotations onto a current box's angle of an axis; useful for complex animations
     *
     * @param box{RendererModel} - The box to rotate
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
    public void advancedRotateAngle(RendererModel box, boolean[] doAxis, boolean reverse, float scale, float speed, float degree, float offset, float weight, float distance, float distanceSpeed) {
        int neg = reverse ? -1 : 1;
        box.rotateAngleX = doAxis[0] ? box.rotateAngleX + neg * MathHelper.cos(distance * (speed * scale) + offset) * (degree * scale) * distanceSpeed : box.rotateAngleX;
        box.rotateAngleY = doAxis[1] ? box.rotateAngleY + neg * MathHelper.cos(distance * (speed * scale) + offset) * (degree * scale) * distanceSpeed : box.rotateAngleY;
        box.rotateAngleZ = doAxis[2] ? box.rotateAngleZ + neg * MathHelper.cos(distance * (speed * scale) + offset) * (degree * scale) * distanceSpeed : box.rotateAngleZ;
    }

    /**
     * Much like `advancedRotateAngle` above but takes in an array of boxes which it will rotate those boxes in correlation with each other making a chain effect; good for tails for example
     * @param boxes{RendererModel} - The box to rotate
     * @param doAxis{boolean[]} - An array of booleans used to determine if x, y, and z should be rotated. Array should have an index of [2]
     * @param reverse{boolean} - Boolean to determine if the rotation should be inverted
     * @param scale{float} - A multiplier to the rotation
     * @param speed{float} - The speed of the rotation
     * @param degree{float} - The degree of the angle
     * @param chainOffset{float} - Offsets the time of the angle, will chain per index of boxes
     * @param distance{float} - Distance of the angle
     * @param distanceSpeed{float} - The angle distance speed\
     */

    public void chainAdvancedRotateAngle(RendererModel[] boxes, boolean[] doAxis, boolean reverse, float scale, float chainOffset, float speed, float degree, float distance, float distanceSpeed) {
        int neg = reverse ? -1 : 1;
        int length = boxes.length;
        for (int i = 0; i < length; i++) {
            boxes[i].rotateAngleX = doAxis[0] ? boxes[i].rotateAngleX + neg * MathHelper.cos((distance * (speed * scale) + (float) ((chainOffset * Math.PI) / (length * 2) * i))) * (degree * scale) * distanceSpeed : boxes[i].rotateAngleX;
            boxes[i].rotateAngleY = doAxis[0] ? boxes[i].rotateAngleY + neg * MathHelper.cos((distance * (speed * scale) + (float) ((chainOffset * Math.PI) / (length * 2) * i))) * (degree * scale) * distanceSpeed : boxes[i].rotateAngleY;
            boxes[i].rotateAngleZ = doAxis[0] ? boxes[i].rotateAngleZ + neg * MathHelper.cos((distance * (speed * scale) + (float) ((chainOffset * Math.PI) / (length * 2) * i))) * (degree * scale) * distanceSpeed : boxes[i].rotateAngleZ;
        }
    }

    public static void copyModelAngles(RendererModel source, RendererModel dest) {
        dest.rotateAngleX = source.rotateAngleX;
        dest.rotateAngleY = source.rotateAngleY;
        dest.rotateAngleZ = source.rotateAngleZ;
        dest.rotationPointX = source.rotationPointX;
        dest.rotationPointY = source.rotationPointY;
        dest.rotationPointZ = source.rotationPointZ;
    }

}
