package mokiyoki.enhancedanimals.model;

import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by saemon on 8/09/2018.
 */
@SideOnly(Side.CLIENT)
public class ModelEnhancedChicken extends ModelBase {

//TODO create a ModelRenderer for EVERY box.
    //the below is the default from regular chicken
    private ModelRenderer head;
    private ModelRenderer headNakedNeck;
    private ModelRenderer bigCrest;
    private ModelRenderer smallCrest;
    private ModelRenderer forwardCrest;
    private ModelRenderer combSingle;
    private ModelRenderer combRose;
    private ModelRenderer combPea;
    private ModelRenderer combWalnut;
    private ModelRenderer combV;
    private ModelRenderer body;
    private ModelRenderer rightLeg;
    private ModelRenderer rightFeather1;
    private ModelRenderer rightFeather2;
    private ModelRenderer rightFeather3;
    private ModelRenderer leftLeg;
    private ModelRenderer leftFeather1;
    private ModelRenderer leftFeather2;
    private ModelRenderer leftFeather3;
    private ModelRenderer rightWing;
    private ModelRenderer leftWing;
    private ModelRenderer bill;
    private ModelRenderer chin;
    private ModelRenderer beard;

    public ModelEnhancedChicken(){
        this.textureWidth = 64;
        this.textureHeight = 64;

        this.head = new ModelRenderer(this, 6, 14);
        this.head.addBox(-2F, 9F, -5F, 4, 6, 3);

        this.headNakedNeck = new ModelRenderer(this, 6, 14);
        this.headNakedNeck.addBox(-2F, 9F, -5F, 4, 4, 3);
        this.headNakedNeck.setTextureOffset(5,0);
        this.headNakedNeck.addBox(-1F, 13F, -4F, 2, 3, 2);

        this.bigCrest = new ModelRenderer(this,43,17);
        this.bigCrest.addBox(-2F, 6F, -5.5F, 4, 4, 4, 0.4F);

        this.smallCrest = new ModelRenderer(this,45,18);
        this.smallCrest.addBox(-1.5F, 6.5F, -5F, 3, 3, 3, 0.1F);

        this.forwardCrest = new ModelRenderer(this,45,18);
        this.forwardCrest.addBox(-1.5F, 7F, -6F, 3, 3, 3, 0.2F);

        this.combSingle = new ModelRenderer(this,0,15);
        this.combSingle.addBox(-0.5F, 9F, -6F, 1, 1, 1);
        this.combSingle.addBox(-0.5F, 8.5F, -6F, 1, 1, 1);
        this.combSingle.addBox(-0.5F, 7.5F, -5F, 1, 2, 1);
        this.combSingle.addBox(-0.5F, 8F, -4F, 1, 1, 1);
        this.combSingle.addBox(-0.5F, 7F, -3F, 1, 2, 1);
        this.combSingle.addBox(-0.5F, 8F, -2F, 1, 1, 1);

        this.combRose = new ModelRenderer(this,0,15);
        this.combRose.addBox(-0.5F, 9F, -6F, 1, 1, 1, 0.5F);
        this.combRose.addBox(-0.5F, 8F, -5F, 1, 1, 1, 0.25F);
        this.combRose.addBox(-0.5F, 7F, -4F, 1, 1, 1);

        this.combPea = new ModelRenderer(this,0,15);
        this.combPea.addBox(-0.5F, 9F, -6F, 1, 1, 2, -0.2F);
        this.combPea.addBox(-0.5F, 8.5F, -5.5F, 1, 1, 1);
        this.combPea.addBox(-0.5F, 8F, -5F, 1, 2, 1,-0.2F);

        this.combWalnut = new ModelRenderer(this,0,15);
        this.combWalnut.addBox(-0.5F, 8.5F, -5.5F, 1, 1, 1, 0.5F);

        this.combV = new ModelRenderer(this,0,15);
        this.combV.addBox(-0.5F, 8.5F, -5.5F, 1, 1, 1);
        this.combV.addBox(0F, 8F, -5.25F, 1, 1, 1, -0.2F);
        this.combV.addBox(-1F, 8F, -5.25F, 1, 1, 1, -0.2F);
        this.combV.addBox(.1F, 7.7F, -5F, 1, 1, 1, -0.3F);
        this.combV.addBox(-1.1F, 7.7F, -5F, 1, 1, 1, -0.3F);

        this.body = new ModelRenderer(this, 6, 0);
        this.body.addBox(-3F, 13F, -3F, 6, 6, 8);
        this.body.setTextureOffset(34,10);
        this.body.addBox(-0.5F, 12F, 3F, 1, 4, 5);
        this.body.setTextureOffset(35, 11);
        this.body.addBox(-0.5F, 11F, 4F, 1, 1, 4);

        this.rightLeg = new ModelRenderer(this, 26, 0);
        this.rightLeg.addBox(1F, 18.5F, 1F, 1, 5, 1);
        this.rightLeg.setTextureOffset(4,5);
        this.rightLeg.addBox(0F, 23F, -1F, 3, 1, 2);
        this.rightLeg.setTextureOffset(7,6);
        this.rightLeg.addBox(1F, 23F, -2F, 1, 1, 1);

        this.rightFeather1 = new ModelRenderer(this,44,0);
        this.rightFeather1.addBox(1.1F, 19F, 0F, 2, 3, 3);

        this.leftFeather1 = new ModelRenderer(this,44,0);
        this.leftFeather1.addBox(-2.9F, 19F, 0F, 2, 3, 3);

        this.rightFeather2 = new ModelRenderer(this,46,10);
        this.rightFeather2.addBox(0.5F, 22F, -2.5F, 3, 2, 5);

        this.leftFeather2 = new ModelRenderer(this,46,10);
        this.leftFeather2.addBox(-3.5F, 22F, -2.5F, 3, 2, 5);

        this.rightFeather3 = new ModelRenderer(this,17,3);
        this.rightFeather3.addBox(4.5F, 23.9F, -2.5F, 4, 0, 5);

        this.leftFeather3 = new ModelRenderer(this,17,3);
        this.leftFeather3.addBox(-6.5F, 23.9F, -2.5F, 4, 0, 5);

        this.leftLeg = new ModelRenderer(this, 26, 0);
        this.leftLeg.addBox(-2F, 18.5F, 1F, 1, 5, 1);
        this.leftLeg.setTextureOffset(4,5);
        this.leftLeg.addBox(-3F, 23F, -1F, 3, 1, 2);
        this.leftLeg.setTextureOffset(7,6);
        this.leftLeg.addBox(-2F, 23F, -2F, 1, 1, 1);

        this.rightWing = new ModelRenderer(this, 35, 0);
        this.rightWing.addBox(-4F, 13F, -1F, 1, 4, 6);

        this.leftWing = new ModelRenderer(this, 49, 0);
        this.leftWing.addBox(3F, 13F, -1F, 1, 4, 6);

        this.bill = new ModelRenderer(this, 30, 0);
        this.bill.addBox(-1F, 11F, -7F, 2, 2, 2);

        this.chin = new ModelRenderer(this, 0, 15);
        this.chin.addBox(-1F, 13F, -6F, 2, 2, 1);

        this.beard = new ModelRenderer(this,22,14);
        this.beard.addBox(-2.5F, 11.5F, -5.5F, 2, 3, 3, 0.1F);
        this.beard.setTextureOffset(22,14);
        this.beard.addBox(0.5F, 11.5F, -5.5F, 2, 3, 3, 0.1F);
        this.beard.setTextureOffset(20,14);
        this.beard.addBox(-2F, 12.5F, -6.5F, 4, 3, 3, 0.1F);

    }


    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale){
        EnhancedChicken enhancedChicken = (EnhancedChicken)entityIn;

        int[] genes = enhancedChicken.getGenes();
        boolean nakedNeck = false;
        int crest = 0; // [0, 1, 2, 3]      [none, small, forward, big]
        int fFeet = 0; // [0, 1, 2, 3]      [none, 1, 1&2, 1&2&3]
        int comb = 0; // [0, 1, 2, 3, 4, 5] [none, single, rose, pea, walnut, v]
        int chin = 0; // [0, 1, 2]          [none, waddles, beard]

        if(genes[52] ==1 || genes[53] == 1){
            nakedNeck = true;
        }

        if(genes[50] == 1 || genes[51] == 1){
            if((genes[46] == 1 || genes[47] == 1) && (genes[48] == 1 || genes[49] == 1)){
                //walnut
                comb = 4;
            }else if(genes[46] == 1 || genes[47] == 1){
                //rose
                comb = 2;
                chin = 1;
            }else if(genes[48] == 1 || genes[49] == 1){
                //pea
                comb = 3;
            }else {
                //single
                comb = 1;
                chin = 1;
            }
        }else{
            if(genes[46] == 2 && genes[47] == 2 && genes[48] == 2 && genes[49] == 2){
                //v comb
                comb = 5;
                chin = 1;
            }else{
                if(genes[48] == 2 && genes[49] == 2){
                    //only waddles
                    chin = 1;
                }
            }
        }

        //bearded
        if (genes[56] == 1 || genes[57] == 1){
            chin = 2;
        }

        //crestedness
        if((genes[54] == 2 || genes[55] == 2) && (genes[54] != 1 && genes[55] != 1)){
                crest = 2;
        }else if(genes[54] == 1 || genes[55] == 1){
            if(genes[54] == genes[55]){
                crest = 3;
            }else{
                crest = 1;
            }
        }

        //feather feets
        if(genes[58] == 1 || genes[59] == 1) {
            fFeet = fFeet + 1;
        }else if(genes[58] == 2 || genes[59] == 2){
            fFeet = fFeet + 2;
        }
        if (genes[60] == 1 || genes[61] == 1) {
                fFeet = fFeet + 1;
        }


        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);

//        if (this.isChild)
//        {
//            GlStateManager.pushMatrix();
//            GlStateManager.translate(0.0F, 5.0F * scale, 2.0F * scale);
//            this.head.render(scale);
//            this.bill.render(scale);
//            this.chin.render(scale);
//            GlStateManager.popMatrix();
//            GlStateManager.pushMatrix();
//            GlStateManager.scale(0.5F, 0.5F, 0.5F);
//            GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
//            this.body.render(scale);
//            this.rightLeg.render(scale);
//            this.leftLeg.render(scale);
//            this.rightWing.render(scale);
//            this.leftWing.render(scale);
//            GlStateManager.popMatrix();
//        }
//        else
//        {
            if(nakedNeck){
                this.headNakedNeck.render(scale);;
            }else{
                this.head.render(scale);
            }
            if(chin == 1) {
                this.chin.render(scale);
            }else if(chin == 2){
                this.beard.render(scale);
            }
            if(fFeet >= 1){
                this.leftFeather1.render(scale);
                this.rightFeather1.render(scale);
                if(fFeet >= 2){
                    this.leftFeather2.render(scale);
                    this.rightFeather2.render(scale);
                    if(fFeet == 3){
                        this.leftFeather3.render(scale);
                        this.rightFeather3.render(scale);
                    }
                }
            }
            if(comb == 1){
                this.combSingle.render(scale);
            }else if(comb == 2){
                this.combRose.render(scale);
            }else if(comb == 3){
                this.combPea.render(scale);
            }else if(comb == 4){
                this.combWalnut.render(scale);
            }else if(comb == 5){
                this.combV.render(scale);
            }
            if(crest == 1){
                this.smallCrest.render(scale);
            }else if(crest == 2){
                this.forwardCrest.render(scale);
            }else if(crest == 3){
                this.bigCrest.render(scale);
            }
            this.body.render(scale);
            this.rightLeg.render(scale);
            this.leftLeg.render(scale);
            this.rightWing.render(scale);
            this.leftWing.render(scale);
            this.bill.render(scale);
 //       }

  }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
/*    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        this.head.rotateAngleX = headPitch * 0.017453292F;
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.bill.rotateAngleX = this.head.rotateAngleX;
        this.bill.rotateAngleY = this.head.rotateAngleY;
        this.chin.rotateAngleX = this.head.rotateAngleX;
        this.chin.rotateAngleY = this.head.rotateAngleY;
        this.body.rotateAngleX = ((float)Math.PI / 1F);
        this.rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.rightWing.rotateAngleZ = ageInTicks;
        this.leftWing.rotateAngleZ = -ageInTicks;
    }
*/
}
