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
    private final RendererModel neck;
    private final RendererModel body;
    private final RendererModel leg1;
    private final RendererModel leg2;
    private final RendererModel leg3;
    private final RendererModel leg4;
    private final RendererModel hoof1;
    private final RendererModel hoof2;
    private final RendererModel hoof3;
    private final RendererModel hoof4;

    public ModelEnhancedHorse() {
        this.textureWidth = 124;
        this.textureHeight = 124;

        this.head = new RendererModel(this, 0, 0);
        this.head.addBox(-3.0F, -14.0F, -11.0F, 6, 6, 6, 0.0F);
        this.head.addBox(-2.0F, -14.0F, -8.0F, 4, 3, 3, 0.0F);
        this.head.addBox(-2.0F, -13.9F, -5.0F, 4, 3, 3, 0.1F);

        this.neck = new RendererModel(this, 0, 0);
        this.neck.addBox(-2.0F, -13.0F, -7.0F, 4, 16, 7, 0.0F);
        this.neck.setRotationPoint(0.0F, 0.0F, -4.0F);

        this.body = new RendererModel(this, 0, 0);
        this.body.addBox(-5.0F, 0.0F, -10.0F, 10, 10, 22, 0.0F);

        this.leg1 = new RendererModel(this, 0, 0);
        this.leg1.addBox(0.0F, 0.0F, 0.0F, 5, 13, 5, -1.0F);
        this.leg1.setRotationPoint(-6.0F, 9.0F, -9.0F);

        this.leg2 = new RendererModel(this, 0, 0);
        this.leg2.addBox(0.0F, 0.0F, 0.0F, 5, 13, 5, -1.0F);
        this.leg2.setRotationPoint(1.0F, 9.0F, -9.0F);

        this.leg3 = new RendererModel(this, 0, 0);
        this.leg3.addBox(0.0F, 0.0F, 0.0F, 5, 13, 5, -1.0F);
        this.leg1.setRotationPoint(-6.0F, 9.0F, 8.0F);

        this.leg4 = new RendererModel(this, 0, 0);
        this.leg4.addBox(0.0F, 0.0F, 0.0F, 5, 13, 5, -1.0F);
        this.leg1.setRotationPoint(1.0F, 9.0F, 8.0F);

        this.hoof1 = new RendererModel(this, 0, 0);
        this.hoof1.addBox(0.0F, 11.0F, 0.1F, 5, 3, 4, -0.5F);

        this.hoof2 = new RendererModel(this, 0, 0);
        this.hoof2.addBox(0.0F, 11.0F, 0.1F, 5, 3, 4, -0.5F);

        this.hoof3 = new RendererModel(this, 0, 0);
        this.hoof3.addBox(0.0F, 11.0F, 0.1F, 5, 3, 4, -0.5F);

        this.hoof4 = new RendererModel(this, 0, 0);
        this.hoof4.addBox(0.0F, 11.0F, 0.1F, 5, 3, 4, -0.5F);

        this.neck.addChild(head);

    }


    @Override
    public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

//        this.head.render(scale);
        this.neck.render(scale);
        this.body.render(scale);
        this.leg1.render(scale);
        this.leg2.render(scale);
        this.leg3.render(scale);
        this.leg4.render(scale);
        this.hoof1.render(scale);
        this.hoof2.render(scale);
        this.hoof3.render(scale);
        this.hoof4.render(scale);
        
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
        this.neck.rotateAngleX = headPitch * 0.017453292F;
        this.neck.rotateAngleY = netHeadYaw * 0.017453292F;

        this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;

        copyModelAngles(leg1, hoof1);
        copyModelAngles(leg2, hoof2);
        copyModelAngles(leg3, hoof3);
        copyModelAngles(leg4, hoof4);
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
//     * Much like `advancedRotateAngle` above but takes in an array of boxes which it will rotate those boxes in correlation with each other making a chain effect; good for tails for example
//     * @param box{RendererModel} - The box to rotate
//     * @param doAxis{boolean[]} - An array of booleans used to determine if x, y, and z should be rotated. Array should have an index of [2]
//     * @param reverse{boolean} - Boolean to determine if the rotation should be inverted
//     * @param scale{float} - A multiplier to the rotation
//     * @param speed{float} - The speed of the rotation
//     * @param degree{float} - The degree of the angle
//     * @param chainOffset{float} - Offsets the time of the angle, will chain per index of boxes
//     * @param distance{float} - Distance of the angle
//     * @param distanceSpeed{float} - The angle distance speed\
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
