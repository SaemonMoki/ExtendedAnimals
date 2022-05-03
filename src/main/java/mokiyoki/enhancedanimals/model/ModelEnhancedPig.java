package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import mokiyoki.enhancedanimals.entity.EnhancedMoobloom;
import mokiyoki.enhancedanimals.entity.EnhancedMooshroom;
import mokiyoki.enhancedanimals.entity.EnhancedPig;
import mokiyoki.enhancedanimals.model.util.WrappedModelPart;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class ModelEnhancedPig<T extends EnhancedPig> extends EnhancedAnimalModel<T> {
    protected WrappedModelPart thePig;

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition base = meshdefinition.getRoot().addOrReplaceChild("base", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition bCow = base.addOrReplaceChild("bPig", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    public ModelEnhancedPig(ModelPart modelPart) {
        super(modelPart);
        ModelPart base = modelPart.getChild("base");
    }

    private void resetCubes() {
        
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        PigModelData pigModelData = getPigModelData();
        PigPhenotype pig = pigModelData.getPhenotype();

        resetCubes();
    }

    protected Map<String, Vector3f> saveAnimationValues(T animal, PigPhenotype pig) {
        Map<String, Vector3f> map = animal.getModelRotationValues();
        return map;
    }

    private void setupInitialAnimationValues(T entityIn, PigModelData modelData, PigPhenotype pig) {
        
    }
    
    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.currentAnimal = entityIn.getId();
        PigModelData pigModelData = getCreatePigModelData(entityIn);
        PigPhenotype pig = pigModelData.getPhenotype();
        float drive = ageInTicks + (1000 * pigModelData.random);

        if (pig != null) {

        }

    }

    private class PigModelData extends AnimalModelData {
        public PigPhenotype getPhenotype() {
            return (PigPhenotype) this.phenotype;
        }
    }

    private PigModelData getPigModelData() {
        return (PigModelData) getAnimalModelData();
    }

    private PigModelData getCreatePigModelData(T enhancedPig) {
        return (PigModelData) getCreateAnimalModelData(enhancedPig);
    }

    @Override
    protected PigPhenotype createPhenotype(T enhancedAnimal) {
        return new PigPhenotype(enhancedAnimal.getSharedGenes().getAutosomalGenes(), enhancedAnimal.getOrSetIsFemale());
    }

    protected class PigPhenotype implements Phenotype {

        PigPhenotype(int[] genes, boolean isFemale) {

        }
    }
}
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.blaze3d.vertex.VertexConsumer;
//import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
//import mokiyoki.enhancedanimals.entity.EnhancedPig;
//import mokiyoki.enhancedanimals.items.CustomizableCollar;
//import mokiyoki.enhancedanimals.items.CustomizableSaddleEnglish;
//import mokiyoki.enhancedanimals.items.CustomizableSaddleWestern;
//import mokiyoki.enhancedanimals.model.util.ModelHelper;
//import mokiyoki.enhancedanimals.renderer.EnhancedRendererModelNew;
//import net.minecraft.client.model.EntityModel;
//import net.minecraft.client.model.geom.ModelPart;
//import net.minecraft.client.multiplayer.ClientLevel;
//import net.minecraft.world.SimpleContainer;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.util.Mth;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@OnlyIn(Dist.CLIENT)
//public class ModelEnhancedPig <T extends EnhancedPig> extends EntityModel<T> {
//
//    private Map<Integer, PigModelData> pigModelDataCache = new HashMap<>();
//    private int clearCacheTimer = 0;
//    private float headRotationAngleX;
//
//    private final ModelPart chest1;
//    private final ModelPart chest2;
//
//    private final EnhancedRendererModelNew thePig;
//    private final EnhancedRendererModelNew head;
//    private final EnhancedRendererModelNew cheeks;
//    private final EnhancedRendererModelNew snout;
//    private final EnhancedRendererModelNew mouth;
//    private final EnhancedRendererModelNew tuskTL;
//    private final EnhancedRendererModelNew tuskTR;
//    private final EnhancedRendererModelNew tuskBL;
//    private final EnhancedRendererModelNew tuskBR;
//    private final EnhancedRendererModelNew earSmallL;
//    private final EnhancedRendererModelNew earSmallR;
//    private final EnhancedRendererModelNew earMediumL;
//    private final EnhancedRendererModelNew earMediumR;
//    private final EnhancedRendererModelNew earLargeL;
//    private final EnhancedRendererModelNew earLargeR;
//    private final EnhancedRendererModelNew neck;
//    private final EnhancedRendererModelNew neckBigger;
//    private final EnhancedRendererModelNew neckLong;
//    private final EnhancedRendererModelNew neckBiggerLong;
//    private final EnhancedRendererModelNew wattles;
//    private final EnhancedRendererModelNew body11;
//    private final EnhancedRendererModelNew body12;
//    private final EnhancedRendererModelNew body13;
//    private final EnhancedRendererModelNew body14;
//    private final EnhancedRendererModelNew body15;
//    private final EnhancedRendererModelNew butt5;
//    private final EnhancedRendererModelNew butt6;
//    private final EnhancedRendererModelNew butt7;
//    private final EnhancedRendererModelNew butt8;
//    private final EnhancedRendererModelNew tail0;
//    private final EnhancedRendererModelNew tail1;
//    private final EnhancedRendererModelNew tail2;
//    private final EnhancedRendererModelNew tail3;
//    private final EnhancedRendererModelNew tail4;
//    private final EnhancedRendererModelNew tail5;
//    private final EnhancedRendererModelNew leg1;
//    private final EnhancedRendererModelNew leg2;
//    private final EnhancedRendererModelNew leg3;
//    private final EnhancedRendererModelNew leg4;
//    private final EnhancedRendererModelNew eyeLeft;
//    private final EnhancedRendererModelNew eyeRight;
//
//    private final EnhancedRendererModelNew saddle;
//    private final EnhancedRendererModelNew saddleWestern;
//    private final EnhancedRendererModelNew saddleEnglish;
//    private final EnhancedRendererModelNew saddleHorn;
//    private final EnhancedRendererModelNew saddlePomel;
//    private final EnhancedRendererModelNew saddleSideL;
//    private final EnhancedRendererModelNew stirrup2DWideL;
//    private final EnhancedRendererModelNew stirrup2DWideR;
//    private final EnhancedRendererModelNew stirrup3DNarrowL;
//    private final EnhancedRendererModelNew stirrup3DNarrowR;
//    private final EnhancedRendererModelNew stirrup;
//    private final EnhancedRendererModelNew saddleSideR;
//    private final EnhancedRendererModelNew saddlePad;
//    private final EnhancedRendererModelNew collar;
//
//    private final List<EnhancedRendererModelNew> saddles = new ArrayList<>();
//
//    private Integer currentPig = null;
//
//    public ModelEnhancedPig() {
//
//        this.texWidth = 256;
//        this.texHeight = 256;
//
//        this.thePig = new EnhancedRendererModelNew(this, 49, 0);
//        this.thePig.setPos(0.0F, 0.0F, 0.0F);
//
//        this.head = new EnhancedRendererModelNew(this, 49, 0);
//        this.head.addBox(-3.5F, -5.0F, -4.0F, 7, 6, 7);
//        this.head.setPos(0.0F, -5.0F, -4.0F);
//
//        this.cheeks = new EnhancedRendererModelNew(this, 49, 13);
//        this.cheeks.addBox(-4.0F, 0.0F, 0.0F, 8, 5, 4, 0.25F);
//        this.cheeks.setPos(0.0F, -5.5F, -4.0F);
//
//        this.snout = new EnhancedRendererModelNew(this, 49, 22);
//        this.snout.addBox(-2.0F, -5.0F, -3.0F, 4, 6, 3);
//        this.snout.setPos(0.0F, -6.0F, 0.0F);
//
//        this.mouth = new EnhancedRendererModelNew(this, 63, 22);
//        this.mouth.addBox(-1.0F, -5.0F, 0.0F, 2, 6, 1);
//        this.mouth.setPos(0.0F, 1.0F, -4.0F);
//
//        this.tuskTL = new EnhancedRendererModelNew(this, 69, 22);
//        this.tuskTL.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, -0.1F);
//        this.tuskTL.setPos(0.5F, 0.0F, -2.0F);
//
//        this.tuskTR = new EnhancedRendererModelNew(this, 69, 22);
//        this.tuskTR.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, -0.1F);
//        this.tuskTR.setPos(-0.5F, 0.0F, -2.0F);
//
//        this.tuskBL = new EnhancedRendererModelNew(this, 69, 22);
//        this.tuskBL.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, -0.1F);
//        this.tuskBL.addBox(-0.5F, -2.8F, -0.5F, 1, 1, 2, -0.1F);
//        this.tuskBL.setPos(0.5F, 0.0F, 1F);
//
//        this.tuskBR = new EnhancedRendererModelNew(this, 69, 22);
//        this.tuskBR.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, -0.1F);
//        this.tuskBR.addBox(-0.5F, -2.8F, -0.5F, 1, 1, 2, -0.1F);
//        this.tuskBR.setPos(-0.5F, 0.0F, 1F);
//
//        this.earSmallL = new EnhancedRendererModelNew(this, 46, 0);
//        this.earSmallL.addBox(-1.0F, -2.0F, 0.0F, 3, 4, 1);
//        this.earSmallL.setPos(3.5F, -4.0F, 3.0F);
//
//        this.earSmallR = new EnhancedRendererModelNew(this, 70, 0);
//        this.earSmallR.addBox(-2.0F, -2.0F, 0.0F, 3, 4, 1);
//        this.earSmallR.setPos(-3.5F, -4.0F, 3.0F);
//
//        this.earMediumL = new EnhancedRendererModelNew(this, 46, 0);
//        this.earMediumL.addBox(-1.0F, -3.0F, 0.0F, 3.5F, 5, 1);
//        this.earMediumL.setPos(3.5F, -4.0F, 3.0F);
//
//        this.earMediumR = new EnhancedRendererModelNew(this, 70, 0);
//        this.earMediumR.addBox(-2.5F, -3.0F, 0.0F, 3.5F, 5, 1);
//        this.earMediumR.setPos(-3.0F, -4.0F, 3.0F);
//
//        this.earLargeL = new EnhancedRendererModelNew(this, 46, 0);
//        this.earLargeL.addBox(-1.0F, -4.0F, 0.0F, 4, 6, 1);
//        this.earLargeL.setPos(3.5F, -4.0F, 3.0F);
//
//        this.earLargeR = new EnhancedRendererModelNew(this, 70, 0);
//        this.earLargeR.addBox(-3.0F, -4.0F, 0.0F, 4, 6, 1);
//        this.earLargeR.setPos(-3.0F, -4.0F, 3.0F);
//
//        this.neck = new EnhancedRendererModelNew(this, 0, 0);
//        this.neck.addBox(-4.5F, -6.75F, -9.0F, 9, 7, 9);
//        this.neck.setPos(0.0F, 0.0F, 9.1F);
//
//        this.neckLong = new EnhancedRendererModelNew(this, 0, 0);
//        this.neckLong.addBox(-4.5F, -6.75F, -9.0F, 9, 8, 9);
//        this.neckLong.setPos(0.0F, 0.0F, 9.1F);
//
//        this.neckBigger = new EnhancedRendererModelNew(this, 0, 0);
//        this.neckBigger.addBox(-4.5F, -5.75F, -9.0F, 9, 7, 9, 1.0F);
//        this.neckBigger.setPos(0.0F, 0.0F, 9.1F);
//
//        this.neckBiggerLong = new EnhancedRendererModelNew(this, 0, 0);
//        this.neckBiggerLong.addBox(-4.5F, -6.75F, -9.0F, 9, 8, 9, 1.0F);
//        this.neckBiggerLong.setPos(0.0F, 0.0F, 9.1F);
//
//        this.wattles = new EnhancedRendererModelNew(this, 3, 9);
//        this.wattles.addBox(2.5F, 0.0F, -9.0F, 2, 4, 2);
//        this.wattles.texOffs(25,9);
//        this.wattles.addBox(-4.5F, 0.0F, -9.0F, 2, 4, 2);
//        this.wattles.setPos(0.0F, 2.5F, -9.0F);
//        this.wattles.xRot = (float)Math.PI/-2.0F;
//
//        this.body11 = new EnhancedRendererModelNew(this, 0, 23);
//        this.body11.addBox(-5.0F, 0.0F, 0.0F, 10, 11, 10);
//        this.body11.setPos(0.0F, 18.1F, -4.0F);
//
//        this.body12 = new EnhancedRendererModelNew(this, 0, 23);
//        this.body12.addBox(-5.0F, 0.0F, 0.0F, 10, 12, 10);
//        this.body12.setPos(0.0F, 18.1F, -5.0F);
//
//        this.body13 = new EnhancedRendererModelNew(this, 0, 23);
//        this.body13.addBox(-5.0F, 0.0F, 0.0F, 10, 13, 10);
//        this.body13.setPos(0.0F, 18.1F, -6.0F);
//
//        this.body14 = new EnhancedRendererModelNew(this, 0, 23);
//        this.body14.addBox(-5.0F, 0.0F, 0.0F, 10, 14, 10);
//        this.body14.setPos(0.0F, 18.1F, -7.0F);
//
//        this.body15 = new EnhancedRendererModelNew(this, 0, 23);
//        this.body15.addBox(-5.0F, 0.0F, 0.0F, 10, 15, 10);
//        this.body15.setPos(0.0F, 18.1F, -8.0F);
//
//        this.butt5 = new EnhancedRendererModelNew(this, 0, 53);
//        this.butt5.addBox(-4.5F, 0.0F, 0.0F, 9, 5, 9);
//        this.butt5.setPos(0.0F, 18.0F, 5.5F);
//
//        this.butt6 = new EnhancedRendererModelNew(this, 0, 53);
//        this.butt6.addBox(-4.5F, 0.0F, 0.0F, 9, 6, 9);
//        this.butt6.setPos(0.0F, 18.0F, 6.5F);
//
//        this.butt7 = new EnhancedRendererModelNew(this, 0, 53);
//        this.butt7.addBox(-4.5F, 0.0F, 0.0F, 9, 7, 9);
//        this.butt7.setPos(0.0F, 18.0F, 7.5F);
//
//        this.butt8 = new EnhancedRendererModelNew(this, 0, 53);
//        this.butt8.addBox(-4.5F, 0.0F, 0.0F, 9, 8, 9);
//        this.butt8.setPos(0.0F, 18.0F, 8.5F);
//
//        this.tail0 = new EnhancedRendererModelNew(this, 36, 0);
//        this.tail0.addBox(-0.5F, 0.0F, 0.0F, 1, 2, 1, -0.05F);
//        this.tail0.setPos(0.0F, 5.0F, 7.5F);
//
//        this.tail1 = new EnhancedRendererModelNew(this, 36, 0);
//        this.tail1.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, -0.1F);
//        this.tail1.setPos(0.0F, 1.9F, 0.5F);
//
//        this.tail2 = new EnhancedRendererModelNew(this, 36, 3);
//        this.tail2.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, -0.15F);
//        this.tail2.setPos(0.0F, 1.8F, 0.0F);
//
//        this.tail3 = new EnhancedRendererModelNew(this, 36, 6);
//        this.tail3.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, -0.2F);
//        this.tail3.setPos(0.0F, 1.7F, 0.0F);
//
//        this.tail4 = new EnhancedRendererModelNew(this, 36, 9);
//        this.tail4.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, -0.25F);
//        this.tail4.setPos(0.0F, 1.6F, 0.0F);
//
//        this.tail5 = new EnhancedRendererModelNew(this, 36, 12);
//        this.tail5.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, -0.3F);
//        this.tail5.setPos(0.0F, 1.5F, 0.0F);
//
//        this.leg1 = new EnhancedRendererModelNew(this, 49, 32);
//        this.leg1.addBox(-3.0F, 0.0F, 0.0F, 3, 8, 3);
//        this.leg1.setPos(-2.001F, 16.0F, -7.0F);
//
//        this.leg2 = new EnhancedRendererModelNew(this, 61, 32);
//        this.leg2.addBox(0.0F, 0.0F, 0.0F, 3, 8, 3);
//        this.leg2.setPos(2.001F, 16.0F, -7.0F);
//
//        this.leg3 = new EnhancedRendererModelNew(this, 49, 44);
//        this.leg3.addBox(-3.0F, 0.0F, 0.0F, 3, 8, 3);
//        this.leg3.setPos(-2.001F, 16.0F, 7.0F);
//
//        this.leg4 = new EnhancedRendererModelNew(this, 61, 44);
//        this.leg4.addBox(0.0F, 0.0F, 0.0F, 3, 8, 3);
//        this.leg4.setPos(2.001F, 16.0F, 7.0F);
//
//        this.eyeLeft = new EnhancedRendererModelNew(this, 69, 15);
//        this.eyeLeft.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.01F);
//        this.eyeLeft.setPos(2.5F, -5.0F, 0.0F);
//
//        this.eyeRight = new EnhancedRendererModelNew(this, 49, 15);
//        this.eyeRight.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.01F);
//        this.eyeRight.setPos(-3.5F, -5.0F, 0.0F);
//
//        this.body11.addChild(this.neck);
//        this.body11.addChild(this.neckBigger);
//        this.body11.addChild(this.neckLong);
//        this.body11.addChild(this.neckBiggerLong);
//        this.body12.addChild(this.neck);
//        this.body12.addChild(this.neckLong);
//        this.body12.addChild(this.neckBigger);
//        this.body12.addChild(this.neckBiggerLong);
//        this.body13.addChild(this.neck);
//        this.body13.addChild(this.neckLong);
//        this.body13.addChild(this.neckBigger);
//        this.body13.addChild(this.neckBiggerLong);
//        this.body14.addChild(this.neck);
//        this.body14.addChild(this.neckLong);
//        this.body14.addChild(this.neckBigger);
//        this.body14.addChild(this.neckBiggerLong);
//        this.body15.addChild(this.neck);
//        this.body15.addChild(this.neckBigger);
//        this.body15.addChild(this.neckLong);
//        this.body15.addChild(this.neckBiggerLong);
//        this.neck.addChild(this.head);
//        this.neck.addChild(this.wattles);
//        this.neckBigger.addChild(this.head);
//        this.neckBigger.addChild(this.wattles);
//        this.neckLong.addChild(this.head);
//        this.neckLong.addChild(this.wattles);
//        this.neckBiggerLong.addChild(this.head);
//        this.neckBiggerLong.addChild(this.wattles);
//        this.head.addChild(this.cheeks);
//        this.head.addChild(this.snout);
//        this.snout.addChild(this.tuskTL);
//        this.snout.addChild(this.tuskTR);
//        this.snout.addChild(this.mouth);
//        this.mouth.addChild(this.tuskBL);
//        this.mouth.addChild(this.tuskBR);
//        this.head.addChild(this.earSmallL);
//        this.head.addChild(this.earSmallR);
//        this.head.addChild(this.earMediumL);
//        this.head.addChild(this.earMediumR);
//        this.head.addChild(this.earLargeL);
//        this.head.addChild(this.earLargeR);
//        this.head.addChild(this.eyeLeft);
//        this.head.addChild(this.eyeRight);
//        this.butt5.addChild(this.tail0);
//        this.butt6.addChild(this.tail0);
//        this.butt7.addChild(this.tail0);
//        this.butt8.addChild(this.tail0);
//        this.tail0.addChild(this.tail1);
//        this.tail1.addChild(this.tail2);
//        this.tail2.addChild(this.tail3);
//        this.tail3.addChild(this.tail4);
//        this.tail4.addChild(this.tail5);
//
//        this.thePig.addChild(this.body11);
//        this.thePig.addChild(this.body12);
//        this.thePig.addChild(this.body13);
//        this.thePig.addChild(this.body14);
//        this.thePig.addChild(this.body15);
//        this.thePig.addChild(this.butt5);
//        this.thePig.addChild(this.butt6);
//        this.thePig.addChild(this.butt7);
//        this.thePig.addChild(this.butt8);
//        this.thePig.addChild(this.leg1);
//        this.thePig.addChild(this.leg2);
//        this.thePig.addChild(this.leg3);
//        this.thePig.addChild(this.leg4);
//
//        /**
//         * Equipment stuff
//         */
//
//        this.chest1 = new ModelPart(this, 80, 14);
//        this.chest1.addBox(-3.0F, 0.0F, 0.0F, 8, 8, 3);
//        this.chest1.setPos(-8.0F, 8.0F, 3.0F);
//        this.chest1.yRot = ((float)Math.PI / 2F);
//        this.chest2 = new ModelPart(this, 80, 25);
//        this.chest2.addBox(-3.0F, 0.0F, 0.0F, 8, 8, 3);
//        this.chest2.setPos(5.0F, 8.0F, 3.0F);
//        this.chest2.yRot = ((float)Math.PI / 2F);
//
//        this.saddle = new EnhancedRendererModelNew(this, 0, 0, "Saddle");
//
//        this.saddleWestern = new EnhancedRendererModelNew(this, 210, 0, "WesternSaddle");
//        this.saddleWestern.addBox(-5.0F, -2.0F, -5.0F, 10, 2, 13, 0.0F);
//        this.saddleWestern.texOffs(210, 15);
//        this.saddleWestern.addBox(-4.0F, -3.0F, 5.0F, 8, 2, 4, 0.0F);
//        this.saddleWestern.texOffs(230, 15);
//        this.saddleWestern.addBox(-3.5F, -4.0F, 8.0F, 7, 2, 2, 0.0F);
//
//        this.saddleEnglish = new EnhancedRendererModelNew(this, 211, 1, "EnglishSaddle");
//        this.saddleEnglish.addBox(-5.0F, -1.0F, -4.0F, 10, 2, 12, 0.0F);
//        this.saddleEnglish.texOffs(210, 15);
//        this.saddleEnglish.addBox(-4.0F, -1.5F, 5.0F, 8, 2, 4, 0.0F);
//        this.saddleEnglish.texOffs(230, 15);
//        this.saddleEnglish.addBox(-3.5F, -2.0F, 7.5F, 7, 2, 2, 0.0F);
//
//        this.saddleHorn = new EnhancedRendererModelNew(this, 234, 19, "SaddleHorn");
//        this.saddleHorn.addBox(-4.0F, -2.0F, -3.0F, 8, 2, 3, 0.0F);
//
//        this.saddlePomel = new EnhancedRendererModelNew(this, 243, 0, "SaddlePomel");
//        this.saddlePomel.addBox(-1.0F, -3.0F, -2.0F, 2, 4, 2, -0.25F);
//        this.saddlePomel.setPos(0.0F, -2.0F, -2.0F);
//
//        this.saddleSideL = new EnhancedRendererModelNew(this, 234, 49, "SaddleLeft");
//        this.saddleSideL.addBox(0.0F, 0.0F, 0.0F, 3, 4, 8);
//
//        this.saddleSideR = new EnhancedRendererModelNew(this, 234, 61, "SaddleRight");
//        this.saddleSideR.addBox(-3.0F, 0.0F, 0.0F, 3, 4, 8);
//
//        this.stirrup2DWideL = new EnhancedRendererModelNew(this, 248, 24, "2DStirrupL");
//        this.stirrup2DWideL.addBox(0.0F, 0.0F, 0.0F, 0, 10, 4); // strap
//
//        this.stirrup2DWideR = new EnhancedRendererModelNew(this, 248, 24, "2DStirrupR");
//        this.stirrup2DWideR.addBox(0.0F, 0.0F, 0.0F, 0, 10, 4); // strap
//
//        this.stirrup3DNarrowL = new EnhancedRendererModelNew(this, 249, 27, "3DStirrupL");
//        this.stirrup3DNarrowL.addBox(-1.0F, 0.0F, 0.0F, 1, 10, 1); // strap
//
//        this.stirrup3DNarrowR = new EnhancedRendererModelNew(this, 251, 27, "3DStirrupR");
//        this.stirrup3DNarrowR.addBox(0.0F, 0.0F, 0.0F, 1, 10, 1);
//
//        this.stirrup = new EnhancedRendererModelNew(this, 210, 0, "Stirrup");
//        this.stirrup.addBox(-0.5F, 9.5F, -1.0F, 1, 1, 1);
//        this.stirrup.texOffs(214, 0);
//        this.stirrup.addBox(-0.5F, 9.5F, 1.0F, 1, 1, 1);
//        this.stirrup.texOffs(210, 2);
//        this.stirrup.addBox(-0.5F, 10.5F, -1.5F, 1, 3, 1);
//        this.stirrup.texOffs(214, 2);
//        this.stirrup.addBox(-0.5F, 10.5F, 1.5F, 1, 3, 1);
//        this.stirrup.texOffs(211, 7);
//        this.stirrup.addBox(-0.5F, 12.5F, -0.5F, 1, 1, 2);
//
//        this.saddlePad = new EnhancedRendererModelNew(this, 194, 24, "SaddlePad");
//        this.saddlePad.addBox(-8.0F, -1.0F, -6.0F, 16, 10, 15, -1.0F);
//
//        this.saddleHorn.addChild(this.saddlePomel);
//
//        this.collar = new EnhancedRendererModelNew(this, 80, 1);
//        this.collar.addBox(-5.5F, -5.5F, -3.0F, 11, 2, 11, 0.001F);
//        this.collar.texOffs(80, 4);
//        this.collar.addBox(0.0F, -5.0F, -5.0F, 0, 3, 3, 0.0F);
//        this.collar.texOffs(116, 6);
//        this.collar.addBox(-1.5F, -5.0F, -6.25F, 3, 3, 3, 0.0F);
//        this.collar.setPos(0.0F, -1.0F, -7.5F);
//        this.neck.addChild(this.collar);
//        this.neckLong.addChild(this.collar);
//        this.neckBigger.addChild(this.collar);
//        this.neckBiggerLong.addChild(this.collar);
//
//        //western
//        this.body11.addChild(this.saddleWestern);
//        this.body12.addChild(this.saddleWestern);
//        this.body13.addChild(this.saddleWestern);
//        this.body14.addChild(this.saddleWestern);
//        this.body15.addChild(this.saddleWestern);
//        this.saddleWestern.addChild(this.saddleHorn);
//        this.saddleWestern.addChild(this.saddleSideL);
//        this.saddleWestern.addChild(this.saddleSideR);
//        this.saddleWestern.addChild(this.saddlePad);
//        this.saddleWestern.addChild(this.stirrup2DWideL);
//        this.saddleWestern.addChild(this.stirrup2DWideR);
//        this.stirrup2DWideL.addChild(this.stirrup);
//        this.stirrup2DWideR.addChild(this.stirrup);
//        //english
//        this.body11.addChild(this.saddleEnglish);
//        this.body12.addChild(this.saddleEnglish);
//        this.body13.addChild(this.saddleEnglish);
//        this.body14.addChild(this.saddleEnglish);
//        this.body15.addChild(this.saddleEnglish);
//        this.saddleEnglish.addChild(this.saddleHorn);
//        this.saddleEnglish.addChild(this.saddleSideL);
//        this.saddleEnglish.addChild(this.saddleSideR);
//        this.saddleEnglish.addChild(this.saddlePad);
//        this.saddleEnglish.addChild(this.stirrup3DNarrowL);
//        this.saddleEnglish.addChild(this.stirrup3DNarrowR);
//        this.stirrup3DNarrowL.addChild(this.stirrup);
//        this.stirrup3DNarrowR.addChild(this.stirrup);
//        //vanilla
//        this.body11.addChild(this.saddle);
//        this.body12.addChild(this.saddle);
//        this.body13.addChild(this.saddle);
//        this.body14.addChild(this.saddle);
//        this.body15.addChild(this.saddle);
//        this.saddle.addChild(this.saddlePad);
//        this.saddle.addChild(this.stirrup3DNarrowL);
//        this.saddle.addChild(this.stirrup3DNarrowR);
//    }
//
//    @Override
//    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
//
//        PigModelData pigModelData = getPigModelData();
//        Phenotype pig = pigModelData.phenotype;
//        if (pig != null) {
//            float size = pigModelData.size;
//            size = size < 0 ? 0 : size;
//
//            float age = 1.0F;
//            if (!(pigModelData.birthTime == null) && !pigModelData.birthTime.equals("") && !pigModelData.birthTime.equals("0")) {
//                int ageTime = (int) (pigModelData.clientGameTime - Long.parseLong(pigModelData.birthTime));
//                if (ageTime <= (pigModelData.adultAge * 1.25F)) {
//                    age = ageTime < 0 ? 0 : ageTime / (pigModelData.adultAge * 1.25F);
//                }
//            }
//
//            float finalPigSize = ((3.0F * size * age) + size) / 4.0F;
//            matrixStackIn.pushPose();
//            matrixStackIn.scale(finalPigSize, finalPigSize, finalPigSize);
//            matrixStackIn.translate(0.0F, -1.5F + 1.5F / finalPigSize, 0.0F);
//
//            this.wattles.visible = pig.hasWaddles;
//
//            if (pigModelData.blink >= 6) {
//                this.eyeLeft.visible = true;
//                this.eyeRight.visible = true;
//            } else {
//                this.eyeLeft.visible = false;
//                this.eyeRight.visible = false;
//            }
//
//            this.earSmallL.visible = false;
//            this.earSmallR.visible = false;
//            this.earMediumL.visible = false;
//            this.earMediumR.visible = false;
//            this.earLargeL.visible = false;
//            this.earLargeR.visible = false;
//            switch (pig.earSizeMod) {
//                case 0:
//                    this.earSmallL.visible = true;
//                    this.earSmallR.visible = true;
//                    break;
//                case 1:
//                    this.earMediumL.visible = true;
//                    this.earMediumR.visible = true;
//                    break;
//                case 2:
//                    this.earLargeL.visible = true;
//                    this.earLargeR.visible = true;
//                    break;
//            }
//
//            if (pigModelData.collar) {
//                this.collar.visible = true;
//            } else {
//                this.collar.visible = false;
//            }
//
//            this.chest1.visible = false;
//            this.chest2.visible = false;
//            if (pigModelData.hasChest) {
//                this.chest1.visible = true;
//                this.chest2.visible = true;
//                this.chest1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                this.chest2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//            }
//
//            this.body11.visible = false;
//            this.body12.visible = false;
//            this.body13.visible = false;
//            this.body14.visible = false;
//            this.body15.visible = false;
//            this.butt5.visible = false;
//            this.butt6.visible = false;
//            this.butt7.visible = false;
//            this.butt8.visible = false;
//            if (false) {
//                this.body11.visible = true;
//            } else if (false) {
//                this.body12.visible = true;
//            } else if (false) {
//                this.body13.visible = true;
//            } else if (false) {
//                this.body14.visible = true;
//            } else {
//                this.body15.visible = true;
//            }
//
//            this.neck.visible = false;
//            this.neckLong.visible = false;
//            this.neckBigger.visible = false;
//            this.neckBiggerLong.visible = false;
//            this.neck.visible = true;
//
//            this.butt5.visible = true;
//
//            if (pigModelData.saddle != null) {
//                if (!(pigModelData.saddle.isEmpty() || pigModelData.saddle.getItem() instanceof CustomizableCollar)) {
//                    renderPigandSaddle(pigModelData.saddle, pigModelData.unrenderedModels, matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                } else {
//                    this.thePig.render(matrixStackIn, bufferIn, null, new ArrayList<>(), false, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                }
//            }
//
//            matrixStackIn.popPose();
//        }
//    }
//
//    private void renderPigandSaddle( ItemStack saddleStack,List<String> unrenderedModels, PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
//
//            Map<String, List<Float>> mapOfScale = new HashMap<>();
//            float saddleScale = 0.75F;
//
//            this.saddleWestern.visible = false;
//            this.saddleEnglish.visible = false;
//            this.saddle.visible = false;
//            this.saddlePomel.visible = false;
//
//            if (saddleStack!=null) {
//                if (!saddleStack.isEmpty()) {
//                    List<Float> scalingsForSaddle = ModelHelper.createScalings(saddleScale, saddleScale, saddleScale, 0.0F, -saddleScale * 0.01F, (saddleScale - 1.0F) * 0.04F);
//                    Item saddle = saddleStack.getItem();
//                    if (saddle instanceof CustomizableSaddleWestern) {
//                        this.saddleWestern.visible = true;
//                        this.saddlePomel.visible = true;
//                        mapOfScale.put("WesternSaddle", scalingsForSaddle);
//                    } else if (saddle instanceof CustomizableSaddleEnglish) {
//                        this.saddleEnglish.visible = true;
//                        saddleScale = 1.125F;
//                        List<Float> scalingsForSaddlePad = ModelHelper.createScalings(saddleScale, saddleScale, saddleScale, 0.0F, -saddleScale * 0.01F, (saddleScale - 1.0F) * 0.04F);
//                        mapOfScale.put("SaddlePad", scalingsForSaddlePad);
//                        mapOfScale.put("EnglishSaddle", scalingsForSaddle);
//                    } else if (!(saddle instanceof CustomizableCollar)) {
//                        this.saddle.visible = true;
//                        mapOfScale.put("Saddle", scalingsForSaddle);
//                    }
//                }
//            }
//        this.thePig.render(matrixStackIn, bufferIn , mapOfScale, unrenderedModels, false, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//    }
//
//    @Override
//    public void prepareMobModel(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
//        super.prepareMobModel(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);
//
//        PigModelData pigModelData = getCreatePigModelData(entitylivingbaseIn);
//        Phenotype pig = pigModelData.phenotype;
//
//        if (pig != null) {
//            this.currentPig = entitylivingbaseIn.getId();
//
//            float onGround;
//
//            boolean sleeping = pigModelData.sleeping;
//
//            if (sleeping) {
//                onGround = sleepingAnimation();
//            } else {
//                onGround = standingAnimation();
//            }
//
//            this.neck.z = onGround - (entitylivingbaseIn).getHeadRotationPointY(partialTickTime) * 4.5F;
//            this.neckLong.z = (onGround - (entitylivingbaseIn).getHeadRotationPointY(partialTickTime) * 4.5F) + 1;
//            this.headRotationAngleX = (entitylivingbaseIn).getHeadRotationAngleX(partialTickTime);
//
//            float snoutLength = pig.snoutLength;
//            if (!(pigModelData.birthTime == null) && !pigModelData.birthTime.equals("") && !pigModelData.birthTime.equals("0")) {
//                int ageTime = (int) (pigModelData.clientGameTime - Long.parseLong(pigModelData.birthTime));
//                if (ageTime < pigModelData.adultAge) {
//                    float age = ageTime < 0 ? 0 : ageTime / (float)pigModelData.adultAge;
//                    snoutLength = snoutLength * age;
//                }
//            }
//
//        /*
//            shortest nose Y rotation point:
//                snout = -2.0F
//                topTusks = -4.5F
//                bottomTusks = -5.0F
//
//            longest nose Y rotation point:
//                snout = -7.0F
//                topTusks = -1.0F
//                bottomTusks = -2.0F
//         */
//
//            //TODO check that baby thePig snoots are short and cute
////        this.snout.rotateAngleX = -snoutLength;
//            this.snout.y = -(2.0F + (4.75F * snoutLength));
//            this.tuskTL.y = -(4.5F - (3.5F * snoutLength));
//            this.tuskTR.y = this.tuskTL.y;
//            this.tuskBL.y = -(5.0F - (3.0F * snoutLength));
//            this.tuskBR.y = this.tuskBL.y;
//
//            this.tail0.xRot = 1.5F * pig.tailCurlAmount;
//            this.tail1.xRot = 1.1111F * pig.tailCurlAmount;
//            this.tail2.xRot = 1.2222F * pig.tailCurlAmount;
//            this.tail3.xRot = 1.3333F * pig.tailCurlAmount;
//            this.tail4.xRot = 1.5F * pig.tailCurlAmount;
//            this.tail5.xRot = 0.1F * pig.tailCurlAmount;
//
//            if (pig.tailCurl) {
//                this.tail0.yRot = 0.3555F * pig.tailCurlAmount;
//                this.tail1.yRot = 0.3555F * pig.tailCurlAmount;
//                this.tail2.yRot = 0.3555F * pig.tailCurlAmount;
//                this.tail3.yRot = 0.3555F * pig.tailCurlAmount;
//            } else {
//                this.tail0.yRot = -0.3555F * pig.tailCurlAmount;
//                this.tail1.yRot = -0.3555F * pig.tailCurlAmount;
//                this.tail2.yRot = -0.3555F * pig.tailCurlAmount;
//                this.tail3.yRot = -0.3555F * pig.tailCurlAmount;
//            }
//
//            /**
//             * longer body11 adjustments prototype
//             */
//
//            /**
//             this.body14.rotationPointZ = -6.0F; //-4.0F
//             this.tail0.rotationPointY = 9.0F; //  5.0F
//             this.leg1.rotationPointZ = -9.0F; // -7.0F
//             this.leg2.rotationPointZ = -9.0F; // -7.0F
//             this.leg3.rotationPointZ = 10.5F;  // 7.0F
//             this.leg4.rotationPointZ = 10.5F; //  7.0F
//             */
//            if (this.body11.visible) {
//                this.leg1.z = -7.0F;
//                this.leg2.z = -7.0F;
//            } else if (this.body12.visible) {
//                this.leg1.z = -8.0F;
//                this.leg2.z = -8.0F;
//            } else if (this.body13.visible) {
//                this.leg1.z = -9.0F;
//                this.leg2.z = -9.0F;
//            } else if (this.body14.visible) {
//                this.leg1.z = -10.0F;
//                this.leg2.z = -10.0F;
//            } else if (this.body15.visible) {
//                this.leg1.z = -11.0F;
//                this.leg2.z = -11.0F;
//            }
//
//            float movebutt = 1F;
//
//            //butt selection probs should have mostly to do with shape?
//            if (this.butt5.visible) {
//                this.tail0.y = 5.0F;
//                this.butt5.z = 6.0F + movebutt;
//                this.leg3.z = 7.0F + movebutt;
//                this.leg4.z = 7.0F + movebutt;
//            } else if (this.butt6.visible) {
//                this.tail0.y = 6.0F;
//                this.butt6.z = 6.0F + movebutt;
//                this.leg3.z = 8.0F + movebutt;
//                this.leg4.z = 8.0F + movebutt;
//            } else if (this.butt7.visible) {
//                this.tail0.y = 7.0F;
//                this.butt7.z = 6.0F + movebutt;
//                this.leg3.z = 9.0F + movebutt;
//                this.leg4.z = 9.0F + movebutt;
//            } else if (this.butt8.visible) {
//                this.tail0.y = 8.0F;
//
//                /**
//                 * if body15 butt8 should be from -3 to 1
//                 *
//                 */
//
//                this.butt8.z = 6.0F + movebutt;
//                this.leg3.z = 10.0F + movebutt;
//                this.leg4.z = 10.0F + movebutt;
//            }
//        }
//    }
//
//    @Override
//    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
//        PigModelData pigModelData = getPigModelData();
//        Phenotype pig = pigModelData.phenotype;
//
//        if (pig != null) {
//            ItemStack saddleStack = pigModelData.saddle;
////        List<String> unrenderedModels = new ArrayList<>();
//
//            float twoPi = (float) Math.PI * 2.0F;
//            float halfPi = (float) Math.PI / 2F;
//
////        this.neck.rotateAngleX = twoPi;
////        this.neckLong.rotateAngleX = twoPi;
//            this.body11.xRot = halfPi;
//            this.body12.xRot = halfPi;
//            this.body13.xRot = halfPi;
//            this.body14.xRot = halfPi;
//            this.body15.xRot = halfPi;
//            this.butt5.xRot = halfPi;
//            this.butt6.xRot = halfPi;
//            this.butt7.xRot = halfPi;
//            this.butt8.xRot = halfPi;
//
//            /**
//             * change only earFlopMod for ear effects. If anything is tweaked it needs to be through that.
//             */
//            float earFlopMod = pig.earFlopMod; //[-1.0 = full droop, -0.65 = half droop, -0.25 = over eyes flop, 0 = stiff flop, 0.5F is half flop, 1 is no flop]
//
//            float earFlopContinuationMod;
//
//            if (earFlopMod < -0.25) {
//                earFlopContinuationMod = (1 + earFlopMod) / 0.75F;
//                earFlopMod = -0.25F;
//            } else {
//                earFlopContinuationMod = 1.0F;
//            }
//
//            this.earSmallL.xRot = ((-(float) Math.PI / 2.0F) * earFlopMod * earFlopContinuationMod) + ((((float) Math.PI / 2.2F)) * (1.0F - earFlopContinuationMod));
//            this.earSmallR.xRot = ((-(float) Math.PI / 2.0F) * earFlopMod * earFlopContinuationMod) + ((((float) Math.PI / 2.2F)) * (1.0F - earFlopContinuationMod));
//
//            this.earSmallL.yRot = ((float) Math.PI / 3F) * earFlopContinuationMod;
//            this.earSmallR.yRot = -((float) Math.PI / 3F) * earFlopContinuationMod;
//
//            this.earSmallL.zRot = (-((float) Math.PI / 10F) * earFlopContinuationMod) + (((float) Math.PI / 2.2F) * (1.0F - earFlopContinuationMod));
//            this.earSmallR.zRot = (((float) Math.PI / 10F) * earFlopContinuationMod) - (((float) Math.PI / 2.2F) * (1.0F - earFlopContinuationMod));
//
//            if (earFlopMod == -0.25F) {
//                this.earSmallL.z = 3.0F * earFlopContinuationMod;
//                this.earSmallR.z = 3.0F * earFlopContinuationMod;
//            } else {
//                this.earSmallL.z = 3.0F;
//                this.earSmallR.z = 3.0F;
//            }
//
////            this.earSmallL.rotateAngleX = -((float) Math.PI / 2.0F) * earFlopMod * earFlopContinuationMod;
////            this.earSmallR.rotateAngleX = -((float) Math.PI / 2.0F) * earFlopMod;
////
////            this.earSmallL.rotateAngleY = ((float) Math.PI / 3F) * 1.0F * earFlopContinuationMod;
////            this.earSmallR.rotateAngleY = -((float) Math.PI / 3F);
////
////            this.earSmallL.rotateAngleZ = -((float) Math.PI / 10F) * 1.0F * earFlopContinuationMod;
////            this.earSmallR.rotateAngleZ = ((float) Math.PI / 10F);
////
////
////            this.earSmallR.rotateAngleX = ((float) Math.PI / 2.2F);
////            this.earSmallR.rotateAngleY = 0.0F;
////            this.earSmallR.rotateAngleZ = ((float) Math.PI / -2F);
//
//            ModelHelper.copyModelPositioning(earSmallL, earMediumL);
//            ModelHelper.copyModelPositioning(earSmallR, earMediumR);
//            ModelHelper.copyModelPositioning(earSmallL, earLargeL);
//            ModelHelper.copyModelPositioning(earSmallR, earLargeR);
//
///**
// * these are the cause of the trouble with pigs
// * netHeadYaw must be getting some unusual numbers when in gui form
// * try to get a more reasonable set of numbers somehow
// */
//            this.neck.xRot = twoPi + ((headPitch * 0.017453292F) / 5.0F) + (this.headRotationAngleX / 2.0F);
//            this.head.xRot = ((headPitch * 0.017453292F) / 5.0F) + (this.headRotationAngleX / 2.0F);
//
//            float headYaw = netHeadYaw - Math.round(netHeadYaw / 180) * 180;
//            this.head.zRot = headYaw * 0.017453292F * -0.5F;
//
////        System.out.print(headYaw);
////        System.out.println();
//
//            if (!pigModelData.sleeping) {
//                this.neck.zRot = headYaw * 0.017453292F * -0.5F;
//                this.leg1.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
//                this.leg2.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
//                this.leg3.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
//                this.leg4.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
//            }
//
//            this.mouth.xRot = -0.12F + this.headRotationAngleX;
//
//            this.tuskTL.zRot = 1.3F;
//            this.tuskTR.zRot = -1.3F;
//
//            this.tuskBL.zRot = 1.5F;
//            this.tuskBR.zRot = -1.5F;
//
//            ModelHelper.copyModelRotations(this.neck, this.neckLong);
//            ModelHelper.copyModelPositioning(this.neck, neckBigger);
//
//            this.tail0.xRot = -((float) Math.PI / 2F);
//
//            if (saddleStack != null) {
//                if (!saddleStack.isEmpty()) {
//                    Item saddle = saddleStack.getItem();
//                    if (saddle instanceof CustomizableSaddleWestern) {
//                        this.saddleWestern.xRot = -((float) Math.PI / 2F);
//                        this.saddleWestern.setPos(0.0F, 4.0F, 10.0F);
//                        this.saddleSideL.setPos(5.0F, -1.0F, -5.25F);
//                        this.saddleSideR.setPos(-5.0F, -1.0F, -5.25F);
//                        this.saddleHorn.setPos(0.0F, -2.0F, -2.0F);
//                        this.saddleHorn.xRot = (float) Math.PI / 8.0F;
//                        this.saddlePomel.setPos(0.0F, -1.5F, -0.5F);
//                        this.saddlePomel.xRot = -0.2F;
//                        this.stirrup2DWideL.setPos(7.5F, 0.0F, -3.5F);
//                        this.stirrup2DWideR.setPos(-7.5F, 0.0F, -3.5F);
//                    } else if (saddle instanceof CustomizableSaddleEnglish) {
//                        this.saddleEnglish.xRot = -((float) Math.PI / 2F);
//                        this.saddleEnglish.setPos(0.0F, 4.0F, 10.0F);
//                        this.saddleSideL.setPos(3.25F, -0.5F, -4.0F);
//                        this.saddleSideR.setPos(-3.25F, -0.5F, -4.0F);
//                        this.saddleHorn.setPos(0.0F, -1.0F, -1.0F);
//                        this.saddleHorn.xRot = (float) Math.PI / 4.5F;
//                        this.stirrup3DNarrowL.setPos(8.0F, -0.5F, -1.5F);
//                        this.stirrup3DNarrowR.setPos(-8.0F, -0.5F, -1.5F);
//                    } else if (!(saddle instanceof CustomizableCollar)) {
//                        this.saddle.xRot = -((float) Math.PI / 2F);
//                        this.saddle.setPos(0.0F, 4.0F, 10.0F);
//                        this.stirrup3DNarrowL.setPos(8.0F, 0.0F, 0.0F);
//                        this.stirrup3DNarrowR.setPos(-8.0F, 0.0F, 0.0F);
//                    }
//                }
//            }
//        }
//    }
//
//    private float sleepingAnimation() {
//        float onGround;
//
//        onGround = 9.80F;
//
//        this.thePig.zRot = (float)Math.PI / 2.0F;
//        this.thePig.setPos(15.0F, 19.0F, 0.0F);
//        this.leg1.zRot = -0.8F;
//        this.leg1.xRot = 0.3F;
//        this.leg3.zRot = -0.8F;
//        this.leg3.xRot = -0.3F;
//        this.neck.zRot = 0.2F;
//
//        return onGround;
//    }
//
//    private float standingAnimation() {
//        float onGround;
//        onGround = 8.75F;
//
//        this.thePig.zRot = 0.0F;
//        this.thePig.setPos(0.0F, 0.0F, 0.0F);
//        this.leg1.zRot = 0.0F;
//        this.leg3.zRot = 0.0F;
//
//        return onGround;
//    }
//
//    private class PigModelData {
//        Phenotype phenotype;
//        String birthTime;
//        float size = 1.0F;
//        boolean sleeping = false;
//        int blink = 0;
//        int lastAccessed = 0;
//        long clientGameTime = 0;
//        List<String> unrenderedModels = new ArrayList<>();
//        ItemStack saddle;
//        ItemStack bridle;
//        ItemStack harness;
//        boolean collar = false;
//        boolean hasChest = false;
//        int adultAge;
//    }
//
//    private PigModelData getPigModelData() {
//        if (this.currentPig == null || !pigModelDataCache.containsKey(this.currentPig)) {
//            return new PigModelData();
//        }
//        return pigModelDataCache.get(this.currentPig);
//    }
//
//    private PigModelData getCreatePigModelData(T enhancedPig) {
//        clearCacheTimer++;
//        if(clearCacheTimer > 50000) {
//            pigModelDataCache.values().removeIf(value -> value.lastAccessed==1);
//            for (PigModelData pigModelData : pigModelDataCache.values()){
//                pigModelData.lastAccessed = 1;
//            }
//            clearCacheTimer = 0;
//        }
//
//        if (pigModelDataCache.containsKey(enhancedPig.getId())) {
//            PigModelData pigModelData = pigModelDataCache.get(enhancedPig.getId());
//            pigModelData.lastAccessed = 0;
////            pigModelData.dataReset++;
////            if (pigModelData.dataReset > 5000) {
//
////                pigModelData.dataReset = 0;
////            }
//            pigModelData.sleeping = enhancedPig.isAnimalSleeping();
//            pigModelData.blink = enhancedPig.getBlink();
//            pigModelData.birthTime = enhancedPig.getBirthTime();
//            pigModelData.clientGameTime = (((ClientLevel)enhancedPig.level).getLevelData()).getGameTime();
//            pigModelData.saddle = enhancedPig.getEnhancedInventory().getItem(1);
//            pigModelData.bridle = enhancedPig.getEnhancedInventory().getItem(3);
//            pigModelData.harness = enhancedPig.getEnhancedInventory().getItem(5);
//            pigModelData.collar = hasCollar(enhancedPig.getEnhancedInventory());
//            pigModelData.hasChest = !enhancedPig.getEnhancedInventory().getItem(0).isEmpty();
//
//
//            return pigModelData;
//        } else {
//            PigModelData pigModelData = new PigModelData();
//            if (enhancedPig.getSharedGenes()!=null) {
//                pigModelData.phenotype = new Phenotype(enhancedPig.getSharedGenes().getAutosomalGenes(), enhancedPig.getStringUUID().charAt(1));
//            }
//            pigModelData.size = enhancedPig.getAnimalSize();
//            pigModelData.sleeping = enhancedPig.isAnimalSleeping();
//            pigModelData.blink = enhancedPig.getBlink();
//            pigModelData.birthTime = enhancedPig.getBirthTime();
//            pigModelData.clientGameTime = (((ClientLevel)enhancedPig.level).getLevelData()).getGameTime();
//            pigModelData.saddle = enhancedPig.getEnhancedInventory().getItem(1);
//            pigModelData.bridle = enhancedPig.getEnhancedInventory().getItem(3);
//            pigModelData.harness = enhancedPig.getEnhancedInventory().getItem(5);
//            pigModelData.collar = hasCollar(enhancedPig.getEnhancedInventory());
//            pigModelData.hasChest = !enhancedPig.getEnhancedInventory().getItem(0).isEmpty();
//            pigModelData.adultAge = EanimodCommonConfig.COMMON.adultAgePig.get();
//
//            if(pigModelData.phenotype != null) {
//                pigModelDataCache.put(enhancedPig.getId(), pigModelData);
//            }
//
//            return pigModelData;
//        }
//    }
//
//    private boolean hasCollar(SimpleContainer inventory) {
//        for (int i = 1; i < 6; i++) {
//            if (inventory.getItem(i).getItem() instanceof CustomizableCollar) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private class Phenotype{
//        float earFlopMod;
//        int earSizeMod = 0;
//        boolean tailCurl;
//        boolean hasWaddles;
//        float snoutLength;
//        float tailCurlAmount;
//        int shape;
//
//        Phenotype(int[] gene, char uuid) {
//            this.tailCurl = Character.isLetter(uuid);
//
//            float earSize = 0.0F;
//            float earFlop = 1.0F;
//            //SSC1
//            if (gene[68] == 2 || gene[69] == 2) {
//                earSize = earSize + 0.12F;
//                earFlop = earFlop - 0.44F;
//            } else if (gene[68] == 3 || gene[69] == 3) {
//                earSize = earSize + 0.03F;
//            }
//            //SSC5
//            if (gene[70] == 2 || gene[71] == 2) {
//                earSize = earSize + 0.35F;
//                earFlop = earFlop - 0.48F;
//            } else if (gene[70] == 3 && gene[71] == 3) {
//                earSize = earSize + 0.09F;
//            }
//            //SSC6
//            if (gene[72] == 2 || gene[73] == 2) {
//                earSize = earSize - 0.031F;
//            }
//            //SSC7
//            if (gene[74] == 2 || gene[75] == 2) {
//                earSize = earSize + 0.29F;
//                earFlop = earFlop - 0.62F;
//            } else if (gene[70] == 3 && gene[71] == 3) {
//                earSize = earSize + 0.09F;
//            }
//            //SSC9
//            if (gene[76] == 2 || gene[77] == 2) {
//                earSize = earSize + 0.11F;
//                earFlop = earFlop - 0.32F;
//            } else if (gene[70] == 3 && gene[71] == 3) {
//                earSize = earSize + 0.03F;
//            }
//            //SSC12
//            if (gene[78] == 2 || gene[79] == 2) {
//                earSize = earSize + 0.11F;
//                earFlop = earFlop - 0.14F;
//            } else if (gene[78] == 3 && gene[79] == 3) {
//                earSize = earSize + 0.03F;
//            }
//
//            for (int i = 80; i < 100; i+= 2) {
//                if (gene[i] != 1 && gene[i+1] != 1) {
//                    if (gene[i] == 2 || gene[i+1] == 2) {
//                        earSize = earSize + 0.007F;
//                    } else if (gene[i] == 3 || gene[i+1] == 3){
//                        earSize = earSize + 0.007F;
//                        earFlop = earFlop - 0.004F;
//                    } else {
//                        earSize = earSize + 0.007F;
//                        earFlop = earFlop - 0.007F;
//                    }
//                }
//            }
//            for (int i = 100; i < 120; i+= 2) {
//                if (gene[i] != 1 && gene[i+1] != 1) {
//                    if (gene[i] == 2 || gene[i+1] == 2) {
//                        earSize = earSize - 0.007F;
//                    } else if (gene[i] == 3 || gene[i+1] == 3){
//                        earSize = earSize - 0.007F;
//                        earFlop = earFlop + 0.004F;
//                    } else {
//                        earSize = earSize - 0.007F;
//                        earFlop = earFlop + 0.007F;
//                    }
//                }
//            }
//
//            if (earFlop > 1.0F) {
//                this.earFlopMod = 1.0F;
//            } else {
//                this.earFlopMod = earFlop < -1.0F ? -1.0F : earFlop;
//            }
//
//            if (earSize > 0.2F) {
//                if (earSize > 0.5F) {
//                    this.earSizeMod = 2;
//                } else {
//                    this.earSizeMod = 1;
//                }
//            }
//
//            //snoutLength
//            float snoutlength1 = 1.0F;
//            float snoutlength2 = 1.0F;
//            float snoutlength = 0.0F;
//
//            // 1 - 5, longest - shortest
//            for (int i = 1; i < gene[18];i++){
//                snoutlength1 = snoutlength1 - 0.1F;
//            }
//
//            for (int i = 1; i < gene[19];i++){
//                snoutlength2 = snoutlength2 - 0.1F;
//            }
//
//            for (int i = 1; i < gene[66];i++){
//                snoutlength1 = snoutlength1 - 0.1F;
//            }
//
//            for (int i = 1; i < gene[67];i++){
//                snoutlength2 = snoutlength2 - 0.1F;
//            }
//
//            //causes partial dominance of longer nose over shorter nose.
//            if (snoutlength1 > snoutlength2){
//                snoutlength = (snoutlength1*0.75F) + (snoutlength2*0.25F);
//            }else if (snoutlength1 < snoutlength2){
//                snoutlength = (snoutlength1*0.25F) + (snoutlength2*0.75F);
//            }else{
//                snoutlength = snoutlength1;
//            }
//
//            // 1 - 4, longest - shortest
//            if (gene[42] >= gene[43]) {
//                snoutlength = snoutlength + ((4 - gene[42])/8.0F);
//            } else {
//                snoutlength = snoutlength + ((4 - gene[43])/8.0F);
//            }
//
//            if (gene[44] >= 2 && gene[45] >= 2) {
//                if (gene[44] == 2 || gene[45] == 2) {
//                    snoutlength = snoutlength * 0.9F;
//                } else {
//                    snoutlength = snoutlength * 0.75F;
//                }
//            }
//
//            if (gene[46] == 2 && gene[47] == 2) {
//                snoutlength = snoutlength * 0.6F;
//            }
//
//            if (snoutlength > 1.0F) {
//                this.snoutLength = 1.0F;
//            } else if (snoutlength < 0.0F) {
//                this.snoutLength = 0.0F;
//            } else {
//                this.snoutLength = snoutlength;
//            }
//
//            float inbreedingFactor = 0.0F;
//
//            if (gene[20] == gene[21]) {
//                inbreedingFactor = 0.1667F;
//            }
//            if (gene[22] == gene[23]) {
//                inbreedingFactor = inbreedingFactor + 0.1667F;
//            }
//            if (gene[24] == gene[25]) {
//                inbreedingFactor = inbreedingFactor + 0.1667F;
//            }
//            if (gene[26] == gene[27]) {
//                inbreedingFactor = inbreedingFactor + 0.1667F;
//            }
//            if (gene[28] == gene[29]) {
//                inbreedingFactor = inbreedingFactor + 0.1667F;
//            }
//            if (gene[30] == gene[31]) {
//                inbreedingFactor = inbreedingFactor + 0.1667F;
//            }
//
//            this.tailCurlAmount = inbreedingFactor;
//
//            int shape = 1;
//
//            if (gene != null) {
//                if (gene[56] != 1 && gene[57] != 1) {
//                    if (gene[56] + gene[57] <= 8) {
//                        shape = 0;
//                    } else if (gene[56] == 5 || gene[57] == 5) {
//                        shape = 2;
//                    } else if (gene[56] == 6 || gene[57] == 6) {
//                        shape = 3;
//                    } else if (gene[56] == 7 && gene[57] == 7) {
//
//                    }
//                }
//            }
//            this.shape = shape;
//
//            this.hasWaddles = gene[32] == 1 || gene[33] == 1;
//        }
//    }
//}
