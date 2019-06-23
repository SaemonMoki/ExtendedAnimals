package mokiyoki.enhancedanimals.model;

import mokiyoki.enhancedanimals.entity.EnhancedCow;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.model.ModelBase;
import net.minecraft.client.renderer.entity.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class ModelEnhancedCow extends ModelBase {

    private float headRotationAngleX;
    private float size;
    private float bagSize;

    private final ModelRenderer actualHead;
    private final ModelRenderer mouth;
    private final ModelRenderer earSmallestL;
    private final ModelRenderer earSmallL;
    private final ModelRenderer earMediumL;
    private final ModelRenderer earLongL;
    private final ModelRenderer earLongestL;
    private final ModelRenderer earSmallestR;
    private final ModelRenderer earSmallR;
    private final ModelRenderer earMediumR;
    private final ModelRenderer earLongR;
    private final ModelRenderer earLongestR;
    private final ModelRenderer hornNub1;
    private final ModelRenderer hornNub2;
    private final ModelRenderer hornNub3;
    private final ModelRenderer hornNub4;
    private final ModelRenderer hornNub5;
    private final ModelRenderer hornL0;
    private final ModelRenderer hornR0;
    private final ModelRenderer head; //this is the neck not the head
    private final ModelRenderer bodyChonk;
    private final ModelRenderer bodyBig;
    private final ModelRenderer bodyMedium;
    private final ModelRenderer bodySlim;
    private final ModelRenderer bodyThin;
    private final ModelRenderer udder;
    private final ModelRenderer nipples;
    private final ModelRenderer humpXSmall;
    private final ModelRenderer humpSmall;
    private final ModelRenderer humpSmallish;
    private final ModelRenderer humpMedium;
    private final ModelRenderer humpLargeish;
    private final ModelRenderer humpLarge;
    private final ModelRenderer humpXLarge;
    private final ModelRenderer tail0;
    private final ModelRenderer tail1;
    private final ModelRenderer tail2;
    private final ModelRenderer tailBrush;
    private final ModelRenderer leg1;
    private final ModelRenderer leg2;
    private final ModelRenderer leg3;
    private final ModelRenderer leg4;
    private final ModelRenderer chonkLeg1;
    private final ModelRenderer chonkLeg2;
    private final ModelRenderer chonkLeg3;
    private final ModelRenderer chonkLeg4;
    private final ModelRenderer shortLeg1;
    private final ModelRenderer shortLeg2;
    private final ModelRenderer shortLeg3;
    private final ModelRenderer shortLeg4;
    private final ModelRenderer miniChonkLeg1;
    private final ModelRenderer miniChonkLeg2;
    private final ModelRenderer miniChonkLeg3;
    private final ModelRenderer miniChonkLeg4;


    public ModelEnhancedCow() {

        this.textureWidth = 80;
        this.textureHeight = 80;

        float xMove = -10.0F;

        this.actualHead = new ModelRenderer(this, 0, 33);
        this.actualHead.addBox(-4.0F, 0.0F, -7.0F, 8, 7, 6, 0.0F);
        this.actualHead.setTextureOffset(28, 33);
        this.actualHead.addBox(-2.0F, 0.1F, -11.0F, 4, 5, 4, 0.0F);
        this.actualHead.setTextureOffset(16, 46);
        this.actualHead.addBox(-2.5F, 0.2F, -13.0F, 5, 4, 3, 0.0F);
        this.actualHead.setRotationPoint(0.0F, 0.0F, -7.0F);

        this.mouth = new ModelRenderer(this, 25, 46);
        this.mouth.addBox(-1.5F, 1.0F, -10.0F, 3, 3, 7, 0.1F);
        this.mouth.setTextureOffset(37, 46);
        this.mouth.addBox(-1.5F, 3.0F, -10.0F, 3, 1, 6, -0.1F);
        this.mouth.setRotationPoint(0.0F, 4.0F, -2.0F);

        this.earSmallestL = new ModelRenderer(this, 8, 46);
        this.earSmallestL.addBox(0.0F, -3.0F, -0.5F, 3, 3, 1);
        
        this.earSmallL = new ModelRenderer(this, 8, 46);
        this.earSmallL.addBox(0.0F, -4.0F, -0.5F, 3, 4, 1);

        this.earMediumL = new ModelRenderer(this, 8, 46);
        this.earMediumL.addBox(0.0F, -5.0F, -0.5F, 3, 5, 1);

        this.earLongL = new ModelRenderer(this, 8, 46);
        this.earLongL.addBox(0.0F, -6.0F, -0.5F, 3, 6, 1, 0.15F);

        this.earLongestL = new ModelRenderer(this, 8, 46);
        this.earLongestL.addBox(0.0F, -7.0F, -0.5F, 3, 7, 1, 0.3F);

        this.earSmallestR = new ModelRenderer(this, 0, 46);
        this.earSmallestR.addBox(-3.0F, -3.0F, -0.5F, 3, 3, 1);

        this.earSmallR = new ModelRenderer(this, 0, 46);
        this.earSmallR.addBox(-3.0F, -4.0F, -0.5F, 3, 4, 1);

        this.earMediumR = new ModelRenderer(this, 0, 46);
        this.earMediumR.addBox(-3.0F, -5.0F, -0.5F, 3, 5, 1);

        this.earLongR = new ModelRenderer(this, 0, 46);
        this.earLongR.addBox(-3.0F, -6.0F, -0.5F, 3, 6, 1, 0.15F);

        this.earLongestR = new ModelRenderer(this, 0, 46);
        this.earLongestR.addBox(-3.0F, -7.0F, -0.5F, 3, 7, 1, 0.3F);

        this.hornNub1 = new ModelRenderer(this, 44, 33);
        this.hornNub1.addBox(-2.0F, 0.0F, 0.0F, 4, 2, 2);
        this.hornNub1.setRotationPoint(0.0F, 1.0F, -1.0F);

        this.hornNub2 = new ModelRenderer(this, 44, 33);
        this.hornNub2.addBox(-2.0F, 0.0F, 0.0F, 4, 3, 2);
        this.hornNub2.setRotationPoint(0.0F, 1.0F, -1.0F);

        this.hornNub3 = new ModelRenderer(this, 44, 33);
        this.hornNub3.addBox(-2.0F, 0.0F, 0.0F, 4, 4, 2);
        this.hornNub3.setRotationPoint(0.0F, 1.0F, -1.0F);

        this.hornNub4 = new ModelRenderer(this, 44, 33);
        this.hornNub4.addBox(-2.0F, 0.0F, 0.0F, 4, 5, 2);
        this.hornNub4.setRotationPoint(0.0F, 1.0F, -1.0F);

        this.hornNub5 = new ModelRenderer(this, 44, 33);
        this.hornNub5.addBox(-2.0F, 0.0F, 0.0F, 4, 6, 2);
        this.hornNub5.setRotationPoint(0.0F, 1.0F, -1.0F);

        this.hornL0 = new ModelRenderer(this, 64, 34);
        this.hornL0.addBox(0.0F, 0.0F, 0.0F, 4, 4, 4);
        this.hornL0.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.hornR0 = new ModelRenderer(this, 64, 34);
        this.hornR0.addBox(0.0F, 0.0F, 0.0F, 4, 4, 4);
        this.hornR0.setRotationPoint(0.0F, 0.0F, 0.0F);

        // head is the neck cause thats how this works
        this.head = new ModelRenderer(this, 46, 0);
        this.head.addBox(-3.0F, 0.0F, -8.0F, 6, 8, 11, 0.0F);
        this.head.setRotationPoint(0.0F, 0.0F, 0.0F + xMove);

        this.bodyChonk = new ModelRenderer(this, 0, 0);
        this.bodyChonk.addBox(-6.0F, 0.0F, 0.0F, 12, 11, 22, 1.0F);
        this.bodyChonk.setRotationPoint(0.0F, 2.5F, 0.0F + xMove);

        this.bodyBig = new ModelRenderer(this, 0, 0);
        this.bodyBig.addBox(-6.0F, 0.0F, 0.0F, 12, 11, 22, 0.5F);
        this.bodyBig.setRotationPoint(0.0F, 2.5F, 0.0F + xMove);

        this.bodyMedium = new ModelRenderer(this, 0, 0);
        this.bodyMedium.addBox(-6.0F, 0.0F, 0.0F, 12, 11, 22, 0.0F);
        this.bodyMedium.setRotationPoint(0.0F, 2.5F, 0.0F + xMove);

        this.bodySlim = new ModelRenderer(this, 0, 0);
        this.bodySlim.addBox(-6.0F, 0.0F, 0.0F, 12, 11, 22, -0.5F);
        this.bodySlim.setRotationPoint(0.0F, 2.5F, 0.0F + xMove);

        this.bodyThin = new ModelRenderer(this, 0, 0);
        this.bodyThin.addBox(-5.0F, 0.0F, 0.0F, 10, 11, 22, 0.0F);
        this.bodyThin.setRotationPoint(0.0F, 2.5F, 0.0F + xMove);

        this.udder = new ModelRenderer(this, 24, 67);
        this.udder.addBox(-2.5F, 0.0F, 0.0F, 5, 5, 5, 0.0F);
        this.udder.setRotationPoint(0.0F, 9.5F, 17.25F + xMove);

        this.nipples = new ModelRenderer(this, 24, 77);
        this.nipples.addBox(-2.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
        this.nipples.setTextureOffset(29, 77);
        this.nipples.addBox(1.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
        this.nipples.setTextureOffset(35, 77);
        this.nipples.addBox(-2.0F, 0.0F, 2.0F, 1, 2, 1, 0.0F);
        this.nipples.setTextureOffset(40, 77);
        this.nipples.addBox(1.0F, 0.0F, 2.0F, 1, 2, 1, 0.0F);
        this.nipples.setRotationPoint(0.0F, 13.5F, 18.0F + xMove);

        this.humpXSmall = new ModelRenderer(this, 0, 8);
        this.humpXSmall.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, -1.0F);
        this.humpXSmall.setRotationPoint(0.0F, 0.0F, 0.0F + xMove);
        
        this.humpSmall = new ModelRenderer(this, 0, 8);
        this.humpSmall.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, -0.5F);
        this.humpSmall.setRotationPoint(0.0F, 0.0F, 0.0F + xMove);

        this.humpSmallish = new ModelRenderer(this, 0, 8);
        this.humpSmallish.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, -0.25F);
        this.humpSmallish.setRotationPoint(0.0F, 0.0F, 0.0F + xMove);

        this.humpMedium = new ModelRenderer(this, 0, 8);
        this.humpMedium.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, 0.0F);
        this.humpMedium.setRotationPoint(0.0F, 0.0F, 0.0F + xMove);

        this.humpLargeish = new ModelRenderer(this, 0, 8);
        this.humpLargeish.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, 0.5F);
        this.humpLargeish.setRotationPoint(0.0F, 0.0F, 0.0F + xMove);

        this.humpLarge = new ModelRenderer(this, 0, 8);
        this.humpLarge.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, 1.0F);
        this.humpLarge.setRotationPoint(0.0F, 0.0F, 0.0F + xMove);

        this.humpXLarge = new ModelRenderer(this, 0, 8);
        this.humpXLarge.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, 1.5F);
        this.humpXLarge.setRotationPoint(0.0F, 0.0F, 0.0F + xMove);

        this.tail0 = new ModelRenderer(this, 0,0);
        this.tail0.addBox(-1.0F, 0.0F, 0.0F, 2, 4, 1);
        this.tail0.setRotationPoint(0.0F, 2.5F, 12.0F);

        this.tail1 = new ModelRenderer(this, 6,0);
        this.tail1.addBox(-0.5F, 0.0F, 0.0F, 1, 4, 1);
        this.tail1.setRotationPoint(0.0F, 4.0F, 0.0F);

        this.tail2 = new ModelRenderer(this, 10,0);
        this.tail2.addBox(-0.5F, 0.0F, 0.0F, 1, 3, 1);
        this.tail2.setRotationPoint(0.0F, 3.0F, 0.0F);

        this.tailBrush = new ModelRenderer(this, 14,0);
        this.tailBrush.addBox(-1.0F, 0.0F, -0.5F, 2, 3, 2);
        this.tailBrush.setRotationPoint(0.0F, 3.0F, 0.0F);

        this.leg1 = new ModelRenderer(this, 0, 54);
        this.leg1.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3, 0.0F);
        this.leg1.setRotationPoint(-6.0F, 13.5F, 0.0F + xMove);

        this.leg2 = new ModelRenderer(this, 12, 54);
        this.leg2.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3, 0.0F);
        this.leg2.setRotationPoint(3.0F, 13.5F, 0.0F + xMove);

        this.leg3 = new ModelRenderer(this, 0, 67);
        this.leg3.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3, 0.0F);
        this.leg3.setRotationPoint(-6.0F, 13.5F, 19.0F + xMove);

        this.leg4 = new ModelRenderer(this, 12, 67);
        this.leg4.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3, 0.0F);
        this.leg4.setRotationPoint(3.0F, 13.5F, 19.0F + xMove);

        this.chonkLeg1 = new ModelRenderer(this, 0, 54);
        this.chonkLeg1.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3, 0.5F);

        this.chonkLeg2 = new ModelRenderer(this, 12, 54);
        this.chonkLeg2.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3, 0.5F);

        this.chonkLeg3 = new ModelRenderer(this, 0, 67);
        this.chonkLeg3.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3, 0.5F);

        this.chonkLeg4 = new ModelRenderer(this, 12, 67);
        this.chonkLeg4.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3, 0.5F);

        this.shortLeg1 = new ModelRenderer(this, 0, 54);
        this.shortLeg1.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 0.0F);
        this.shortLeg1.setRotationPoint(-6.0F, 13.5F, 0.0F + xMove);

        this.shortLeg2 = new ModelRenderer(this, 12, 54);
        this.shortLeg2.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 0.0F);
        this.shortLeg2.setRotationPoint(3.0F, 13.5F, 0.0F + xMove);

        this.shortLeg3 = new ModelRenderer(this, 0, 67);
        this.shortLeg3.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 0.0F);
        this.shortLeg3.setRotationPoint(-6.0F, 13.5F, 19.0F + xMove);

        this.shortLeg4 = new ModelRenderer(this, 12, 67);
        this.shortLeg4.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 0.0F);
        this.shortLeg4.setRotationPoint(3.0F, 13.5F, 19.0F + xMove);

        this.miniChonkLeg1 = new ModelRenderer(this, 0, 54);
        this.miniChonkLeg1.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 0.5F);

        this.miniChonkLeg2 = new ModelRenderer(this, 12, 54);
        this.miniChonkLeg2.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 0.5F);

        this.miniChonkLeg3 = new ModelRenderer(this, 0, 67);
        this.miniChonkLeg3.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 0.5F);

        this.miniChonkLeg4 = new ModelRenderer(this, 12, 67);
        this.miniChonkLeg4.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 0.5F);

        this.head.addChild(this.actualHead);
        this.actualHead.addChild(this.earLongestL);
        this.actualHead.addChild(this.earLongL);
        this.actualHead.addChild(this.earMediumL);
        this.actualHead.addChild(this.earSmallL);
        this.actualHead.addChild(this.earSmallestL);
        this.actualHead.addChild(this.earLongestR);
        this.actualHead.addChild(this.earLongR);
        this.actualHead.addChild(this.earMediumR);
        this.actualHead.addChild(this.earSmallR);
        this.actualHead.addChild(this.earSmallestR);
        this.actualHead.addChild(this.hornNub1);
        this.actualHead.addChild(this.hornNub2);
        this.actualHead.addChild(this.hornNub3);
        this.actualHead.addChild(this.hornNub4);
        this.actualHead.addChild(this.hornNub5);
        this.actualHead.addChild(this.hornL0);
        this.actualHead.addChild(this.hornR0);
        this.actualHead.addChild(this.mouth);
        this.tail0.addChild(this.tail1);
        this.tail1.addChild(this.tail2);
        this.tail2.addChild(this.tailBrush);

    }


    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);

        EnhancedCow enhancedCow = (EnhancedCow) entityIn;

        int[] genes = enhancedCow.getSharedGenes();
        this.size = enhancedCow.getSize();
        this.bagSize = enhancedCow.getBagSize();
        float dwarf = 0.0F;
        int bodyShape = 0;
        int hump = 0;


        if (genes[26] == 1 || genes[27] == 1){
            //dwarf
            dwarf = 0.2F;
        }

        for (int i = 1; i < genes[54]; i++){
            bodyShape++;
        }
        for (int i = 1; i < genes[55]; i++){
            bodyShape++;
        }

        for (int i = 1; i < genes[38]; i++){
            hump++;
        }

        for (int i = 1; i < genes[39]; i++){
            hump++;
        }
        
        if (this.isChild) {
            GlStateManager.pushMatrix();
            GlStateManager.scalef(0.6F, 0.6F, 0.6F);
            GlStateManager.translatef(0.0F, 15.0F * scale, 0.0F);

            this.head.render(scale);

            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scalef(0.5F, 0.5F, 0.5F);
            GlStateManager.translatef(0.0F, 20.0F * scale, 0.0F);
            if (bodyShape >= 3) {
                this.bodySlim.render(scale);
            }else{
                this.bodyThin.render(scale);
            }

            if (hump >= 12){
                this.humpLargeish.render(scale);
            }else if (hump >= 10){
                this.humpMedium.render(scale);
            }else if (hump >= 8){
                this.humpSmallish.render(scale);
            }else if (hump >= 6){
                this.humpSmall.render(scale);
            }else if (hump >= 4){
                this.humpXSmall.render(scale);
            }
            this.tail0.render(scale);

            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scalef(0.5F, 0.7F, 0.5F);
            GlStateManager.translatef(0.0F, 10.0F * scale, 0.0F);

            if (dwarf != 0.0F){
                this.shortLeg1.render(scale);
                this.shortLeg2.render(scale);
                this.shortLeg3.render(scale);
                this.shortLeg4.render(scale);
            }else {
                this.leg1.render(scale);
                this.leg2.render(scale);
                this.leg3.render(scale);
                this.leg4.render(scale);
            }

            GlStateManager.popMatrix();

        }else {

            GlStateManager.pushMatrix();
            GlStateManager.scalef(size, size, size);
            GlStateManager.translatef(0.0F, (-1.5F+dwarf) + 1.5F / size, 0.0F);

            this.head.render(scale);

            if (bodyShape == 4){
                this.bodyChonk.render(scale);
            }else if (bodyShape == 3){
                this.bodyBig.render(scale);
            }else if (bodyShape == 2){
                this.bodyMedium.render(scale);
            }else if (bodyShape == 1){
                this.bodySlim.render(scale);
            }else{
                this.bodyThin.render(scale);
            }

            if(hump == 12){
                this.humpXLarge.render(scale);
            }else if (hump >= 10){
                this.humpLarge.render(scale);
            }else if (hump >= 8){
                this.humpLargeish.render(scale);
            }else if (hump >= 6){
                this.humpMedium.render(scale);
            }else if (hump >= 4){
                this.humpSmallish.render(scale);
            }else if (hump >= 2){
                this.humpSmall.render(scale);
            }else if (hump == 1){
                this.humpXSmall.render(scale);
            }

            if (dwarf != 0.0F){
                if (bodyShape >= 3){
                    this.miniChonkLeg1.render(scale);
                    this.miniChonkLeg2.render(scale);
                    this.miniChonkLeg3.render(scale);
                    this.miniChonkLeg4.render(scale);
                }else {
                    this.shortLeg1.render(scale);
                    this.shortLeg2.render(scale);
                    this.shortLeg3.render(scale);
                    this.shortLeg4.render(scale);
                }
            }else {
                if (bodyShape >= 3){
                    this.chonkLeg1.render(scale);
                    this.chonkLeg2.render(scale);
                    this.chonkLeg3.render(scale);
                    this.chonkLeg4.render(scale);
                }else {
                    this.leg1.render(scale);
                    this.leg2.render(scale);
                    this.leg3.render(scale);
                    this.leg4.render(scale);
                }
            }

            this.tail0.render(scale);

//            this.nipples.render(scale);

            GlStateManager.popMatrix();

//            GlStateManager.pushMatrix();
//            GlStateManager.scalef(size + bagSize, size + bagSize, size + bagSize);
////            GlStateManager.translatef(0.0F, (-1.5F + 1.5F / (size+bagSize)) + dwarf*5, 0.0F);
////            GlStateManager.translatef(0.0F, ((-1.5F+dwarf) + 1.5F / (size+bagSize*5)), ((-1.5F+dwarf) + 1.5F / (size+bagSize*5)));
//            GlStateManager.translatef(0.0F, -1.5F + 1.5F/bagSize, 0.0F);
//            this.udder.render(scale);
//            GlStateManager.popMatrix();
            
        }
    }

    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {

        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);

        this.head.rotateAngleX = (headPitch * 0.017453292F);
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;

        this.humpXLarge.rotateAngleX = ((headPitch * 0.017453292F)/2.5F) - 0.2F;
        this.humpXLarge.rotateAngleY = (netHeadYaw * 0.017453292F)/2.5F;
        
        copyModelAngles(humpXLarge, humpLarge);
        copyModelAngles(humpXLarge, humpLargeish);
        copyModelAngles(humpXLarge, humpMedium);
        copyModelAngles(humpXLarge, humpSmallish);
        copyModelAngles(humpXLarge, humpSmall);
        copyModelAngles(humpXLarge, humpXSmall);

        //        this.body.rotateAngleX = ((float)Math.PI / 2F);
        this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;

        this.chonkLeg1.rotateAngleX = this.leg1.rotateAngleX;
        this.chonkLeg2.rotateAngleX = this.leg2.rotateAngleX;
        this.chonkLeg3.rotateAngleX = this.leg3.rotateAngleX;
        this.chonkLeg4.rotateAngleX = this.leg4.rotateAngleX;

        this.miniChonkLeg1.rotateAngleX = this.leg1.rotateAngleX;
        this.miniChonkLeg2.rotateAngleX = this.leg2.rotateAngleX;
        this.miniChonkLeg3.rotateAngleX = this.leg3.rotateAngleX;
        this.miniChonkLeg4.rotateAngleX = this.leg4.rotateAngleX;

        copyModelAngles(leg1, shortLeg1);
        copyModelAngles(leg2, shortLeg2);
        copyModelAngles(leg3, shortLeg3);
        copyModelAngles(leg4, shortLeg4);

        this.head.rotateAngleX = this.headRotationAngleX;   //might need to merge this with another line
        this.actualHead.rotateAngleX = 0.5F;   //might need to merge this with another line
        this.mouth.rotateAngleX = -0.3F;   //might need to merge this with another line

//        this.actualHead.rotateAngleX = 0.05F + this.actualHead.rotateAngleX;   //might need to merge this with another line

        this.tail0.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 0.4F * limbSwingAmount;
        this.tail1.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 0.4F * limbSwingAmount;
        this.tail2.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 0.45F * limbSwingAmount;
        this.tailBrush.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 0.6F * limbSwingAmount;

        this.tail0.rotateAngleX = 0.4F;
        this.tail1.rotateAngleX = -0.2F;
        this.tail2.rotateAngleX = -0.2F;
        this.tailBrush.rotateAngleX = 0F;

        this.hornNub1.rotateAngleX = ((float)Math.PI / -2F);
        this.hornNub2.rotateAngleX = ((float)Math.PI / -2F);
        this.hornNub3.rotateAngleX = ((float)Math.PI / -2F);
        this.hornNub4.rotateAngleX = ((float)Math.PI / -2F);
        this.hornNub5.rotateAngleX = ((float)Math.PI / -2F);

    }

    public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
        super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);

        this.head.rotationPointY = 2.75F + ((EnhancedCow) entitylivingbaseIn).getHeadRotationPointY(partialTickTime) * 9.0F;
        this.headRotationAngleX = ((EnhancedCow) entitylivingbaseIn).getHeadRotationAngleX(partialTickTime);

        EnhancedCow enhancedCow = (EnhancedCow) entitylivingbaseIn;

        int[] sharedGenes = ((EnhancedCow) entitylivingbaseIn).getSharedGenes();
        char[] uuidArry = enhancedCow.getCachedUniqueIdString().toCharArray();

        if (sharedGenes[40] != 1 && sharedGenes[41] != 1) {
            int child = 1;
            if (this.isChild){
                child = 2;
            }
            float hump = 2.0F;
            for (int i = 1; i < sharedGenes[40]; i++) {
                hump = hump - 0.5F;
            }
            for (int i = 1; i < sharedGenes[41]; i++) {
                hump = hump - 0.5F;
            }

            this.humpXLarge.rotationPointY = hump/child;
            this.humpLarge.rotationPointY = hump/child;
            this.humpLargeish.rotationPointY = hump/child;
            this.humpMedium.rotationPointY = hump/child;
            this.humpSmallish.rotationPointY = hump/child;
            this.humpSmall.rotationPointY = hump/child;
            this.humpXSmall.rotationPointY = hump/child;

        }else{
            int child = 1;
            if (this.isChild){
                child = 2;
            }

            this.humpXLarge.rotationPointY = 2.0F/child;
            this.humpLarge.rotationPointY = 2.0F/child;
            this.humpLargeish.rotationPointY = 2.0F/child;
            this.humpMedium.rotationPointY = 2.0F/child;
            this.humpSmallish.rotationPointY = 2.0F/child;
            this.humpSmall.rotationPointY = 2.0F/child;
            this.humpXSmall.rotationPointY = 2.0F/child;
        }

        int bodyShape = 0;
        for (int i = 1; i < sharedGenes[54]; i++){
            bodyShape++;
        }
        for (int i = 1; i < sharedGenes[55]; i++){
            bodyShape++;
        }

        if (isChild && bodyShape != 0){
            bodyShape = 1;
        }

        if (bodyShape == 4){
            this.leg1.rotationPointX = -7.0F;
            this.leg3.rotationPointX = -7.0F;
            this.leg2.rotationPointX = 4.0F;
            this.leg4.rotationPointX = 4.0F;
            this.chonkLeg1.setRotationPoint(-6.7F, 13.5F, -10.7F);
            this.chonkLeg2.setRotationPoint(3.7F, 13.5F, -10.7F);
            this.chonkLeg3.setRotationPoint(-6.7F, 13.5F, 9.7F);
            this.chonkLeg4.setRotationPoint(3.7F, 13.5F, 9.7F);
            this.miniChonkLeg1.setRotationPoint(-6.7F, 13.5F, -10.7F);
            this.miniChonkLeg2.setRotationPoint(3.7F, 13.5F, -10.7F);
            this.miniChonkLeg3.setRotationPoint(-6.7F, 13.5F, 9.7F);
            this.miniChonkLeg4.setRotationPoint(3.7F, 13.5F, 9.7F);
            this.tail0.rotationPointZ = 13.0F;

        }else if (bodyShape == 3){
            this.leg1.rotationPointX = -6.5F;
            this.leg3.rotationPointX = -6.5F;
            this.leg2.rotationPointX = 3.5F;
            this.leg4.rotationPointX = 3.5F;
            this.tail0.rotationPointZ = 12.5F;
            this.chonkLeg1.setRotationPoint(-6.1F, 13.5F, -10.1F);
            this.chonkLeg2.setRotationPoint(3.1F, 13.5F, -10.1F);
            this.chonkLeg3.setRotationPoint(-6.1F, 13.5F, 9.1F);
            this.chonkLeg4.setRotationPoint(3.1F, 13.5F, 9.1F);
            this.miniChonkLeg1.setRotationPoint(-6.1F, 13.5F, -10.1F);
            this.miniChonkLeg2.setRotationPoint(3.1F, 13.5F, -10.1F);
            this.miniChonkLeg3.setRotationPoint(-6.1F, 13.5F, 9.1F);
            this.miniChonkLeg4.setRotationPoint(3.1F, 13.5F, 9.1F);
        }else if (bodyShape == 1){
            this.leg1.rotationPointX = -5.5F;
            this.leg3.rotationPointX = -5.5F;
            this.leg2.rotationPointX = 2.5F;
            this.leg4.rotationPointX = 2.5F;
            this.tail0.rotationPointZ = 11.5F;
        }else if (bodyShape == 0){
            this.leg1.rotationPointX = -5.0F;
            this.leg3.rotationPointX = -5.0F;
            this.leg2.rotationPointX = 2.0F;
            this.leg4.rotationPointX = 2.0F;
            this.tail0.rotationPointZ = 12.0F;
        }else{
            this.leg1.rotationPointX = -6.0F;
            this.leg3.rotationPointX = -6.0F;
            this.leg2.rotationPointX = 3.0F;
            this.leg4.rotationPointX = 3.0F;
            this.tail0.rotationPointZ = 12.0F;
        }

        this.earSmallestL.setRotationPoint(-3.9F, 4.0F, -2.5F);
        this.earSmallL.setRotationPoint(-3.9F, 4.0F, -2.5F);
        this.earMediumL.setRotationPoint(-3.9F, 4.0F, -2.5F);
        this.earLongL.setRotationPoint(-3.5F, 4.0F, -2.5F);
        this.earLongestL.setRotationPoint(-3.5F, 4.0F, -2.5F);
        this.earSmallestR.setRotationPoint(-3.9F, 4.0F, -2.5F);
        this.earSmallR.setRotationPoint(-3.9F, 4.0F, -2.5F);
        this.earMediumR.setRotationPoint(-3.9F, 4.0F, -2.5F);
        this.earLongR.setRotationPoint(-3.5F, 4.0F, -2.5F);
        this.earLongestR.setRotationPoint(-3.5F, 4.0F, -2.5F);

        this.earSmallestL.rotateAngleZ = ((float)Math.PI / 2F);
        this.earSmallL.rotateAngleZ = ((float)Math.PI / 2F);
        this.earMediumL.rotateAngleZ = ((float)Math.PI / 2F);
        this.earLongL.rotateAngleZ = ((float)Math.PI / 2F);
        this.earLongestL.rotateAngleZ = ((float)Math.PI / 2F);
        this.earSmallestR.rotateAngleZ = ((float)Math.PI / 2F);
        this.earSmallR.rotateAngleZ = ((float)Math.PI / 2F);
        this.earMediumR.rotateAngleZ = ((float)Math.PI / 2F);
        this.earLongR.rotateAngleZ = ((float)Math.PI / 2F);
        this.earLongestR.rotateAngleZ = ((float)Math.PI / 2F);

        if (sharedGenes[46] == 2 && sharedGenes[47] == 2){
//        if (true){
            float earSize = 0;
            for (int i = 1; i < sharedGenes[42]; i++){
                earSize = earSize + 1.0F;
            }

            for (int i = 1; i < sharedGenes[43]; i++){
                earSize = earSize + 1.0F;
            }

            if (sharedGenes[44] == 1 || sharedGenes[45] == 1) {
                earSize = earSize - earSize / 3.0F;
            }

            float floppiness = (earSize/6.25F) + 0.2F;

            if (earSize <= 1){
//            if (true){
                this.earSmallestL.setRotationPoint(3.0F, 0.5F, -2.5F);
                this.earSmallestL.rotateAngleZ = 1.1F;
                this.earSmallestR.setRotationPoint(-3.0F, 0.5F, -2.5F);
                this.earSmallestR.rotateAngleZ = -1.1F;
            }else if (earSize <= 3){
                this.earSmallL.setRotationPoint(3.0F, 0.75F, -2.5F);
                this.earSmallL.rotateAngleZ = 1.1F + (earSize/6.25F);
                this.earSmallR.setRotationPoint(-3.0F, 0.75F, -2.5F);
                this.earSmallR.rotateAngleZ = -(1.1F + (earSize/6.25F));
            }else if (earSize <= 5){
                this.earMediumL.setRotationPoint(3.0F + (floppiness/3), 1.0F, -2.5F);
                this.earMediumL.rotateAngleZ = 1.1F + (earSize/6.25F);
                this.earMediumR.setRotationPoint(-(3.0F + (floppiness/3)), 1.0F, -2.5F);
                this.earMediumR.rotateAngleZ = -(1.1F + (earSize/6.25F));
            }else if (earSize <= 7){
                this.earLongL.setRotationPoint(3.0F + (floppiness/2), 1.0F, -2.5F);
                this.earLongL.rotateAngleZ = 1.1F + (earSize/6.25F);
                this.earLongR.setRotationPoint(-(3.0F + (floppiness/2)), 1.0F, -2.5F);
                this.earLongR.rotateAngleZ = -(1.1F + (earSize/6.25F));
            }else{
                this.earLongestL.setRotationPoint(3.0F + floppiness, 1.0F, -2.5F);
                this.earLongestL.rotateAngleZ = 1.1F + (earSize/6.25F);
                this.earLongestR.setRotationPoint(-(3.0F + floppiness), 1.0F, -2.5F);
                this.earLongestR.rotateAngleZ = -(1.1F + (earSize/6.25F));
            }

        }else{
            float floppiness = 0.9F;
            float earSize = 0;

            for (int i = 1; i < sharedGenes[42]; i++){
                floppiness = floppiness + 0.16F;
                earSize = earSize + 1.0F;
            }

            for (int i = 1; i < sharedGenes[43]; i++){
                floppiness = floppiness + 0.16F;
                earSize = earSize + 1.0F;
            }

            if (sharedGenes[44] == 1 || sharedGenes[45] == 1) {
                floppiness = floppiness - floppiness / 3.0F;
                earSize = earSize - earSize / 3.0F;
            }

            for (int i = 1; i < sharedGenes[46]; i++){
                floppiness = floppiness + 0.1F;
            }
            for (int i = 1; i < sharedGenes[47]; i++){
                floppiness = floppiness + 0.1F;
            }


            if (earSize <= 1){
                this.earSmallestL.setRotationPoint(3.0F, 0.5F, -2.5F);
                this.earSmallestL.rotateAngleZ = floppiness;
                this.earSmallestR.setRotationPoint(-3.0F, 0.5F, -2.5F);
                this.earSmallestR.rotateAngleZ = -floppiness;
            }else if (earSize <= 3){
                this.earSmallL.setRotationPoint(3.0F, 0.75F, -2.5F);
                this.earSmallL.rotateAngleZ = floppiness;
                this.earSmallR.setRotationPoint(-3.0F, 0.75F, -2.5F);
                this.earSmallR.rotateAngleZ = -floppiness;
            }else if (earSize <= 5){
                this.earMediumL.setRotationPoint(3.0F + (floppiness/3), 1.0F, -2.5F);
                this.earMediumL.rotateAngleZ = floppiness;
                this.earMediumR.setRotationPoint(-(3.0F + (floppiness/3)), 1.0F, -2.5F);
                this.earMediumR.rotateAngleZ = -floppiness;
            }else if (earSize <= 7){
                this.earLongL.setRotationPoint(3.0F + (floppiness/2), 1.0F, -2.5F);
                this.earLongL.rotateAngleZ = floppiness;
                this.earLongR.setRotationPoint(-(3.0F + (floppiness/2)), 1.0F, -2.5F);
                this.earLongR.rotateAngleZ = -floppiness;
            }else{
                this.earLongestL.setRotationPoint(3.0F + floppiness, 1.0F, -2.5F);
                this.earLongestL.rotateAngleZ = floppiness;
                this.earLongestR.setRotationPoint(-(3.0F + floppiness), 1.0F, -2.5F);
                this.earLongestR.rotateAngleZ = -floppiness;
            }

        }

        int hornNubLength = 4;
        if (sharedGenes[70] == 1 || sharedGenes[71] == 1){
            hornNubLength--;
        }

        if (sharedGenes[72] == 1 || sharedGenes[73] == 1){
            hornNubLength = hornNubLength - 2;
        }else if (sharedGenes[72] == 2 || sharedGenes[73] == 2){
            hornNubLength--;
        }

        if (sharedGenes[74] == 1 || sharedGenes[75] == 1) {
            hornNubLength++;
        }

        this.hornNub1.rotationPointY = 1.0F;
        this.hornNub2.rotationPointY = 1.0F;
        this.hornNub3.rotationPointY = 1.0F;
        this.hornNub4.rotationPointY = 1.0F;
        this.hornNub5.rotationPointY = 1.0F;

        if (hornNubLength == 1){
            this.hornNub1.rotationPointY = -1.0F;
        }else if (hornNubLength == 2){
            this.hornNub2.rotationPointY = -1.0F;
        }else if (hornNubLength == 3){
            this.hornNub3.rotationPointY = -1.0F;
        }else if (hornNubLength == 4){
            this.hornNub4.rotationPointY = -1.0F;
        }else if (hornNubLength == 5){
            this.hornNub5.rotationPointY = -1.0F;
        }

        if (sharedGenes[13] == 1 || sharedGenes[14] == 1){
            if (sharedGenes[76] == 1 && sharedGenes[77] == 1){
                //horned
                this.hornL0.rotationPointX = 8.0F;
            }else if (sharedGenes[76] == 1 || sharedGenes[77] == 1){
                //sex determined horned
                if ( Character.isLetter(uuidArry[0]) || uuidArry[0]-48 >= 8 ){
                    //horned
                    this.hornL0.rotationPointX = 8.0F;
                }else{
                    if (sharedGenes[78] == 1 && sharedGenes[79] == 1){
                        //scured
                    }else{
                        //polled
                    }
                }
            }else{
                //polled
                if (sharedGenes[78] == 1 && sharedGenes[79] == 1){
                    //scured
                }else if (sharedGenes[78] == 1 || sharedGenes[79] == 1){
                    //sex determined scured
                    if ( Character.isLetter(uuidArry[0]) || uuidArry[0]-48 >= 8 ){
                        //scurred
                    }
                }else{
                    //polled
                }
            }
        }else{
            //horned
            this.hornL0.rotationPointX = 8.0F;
        }


    }
    
}
