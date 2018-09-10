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
    public ModelRenderer head;
    public ModelRenderer body;
    public ModelRenderer rightLeg;
    public ModelRenderer leftLeg;
    public ModelRenderer rightWing;
    public ModelRenderer leftWing;
    public ModelRenderer bill;
    public ModelRenderer chin;

    public ModelEnhancedChicken(){
        this.textureWidth = 64;
        this.textureHeight = 64;

        //TODO create the following below for EVERY piece. Can have multiple boxes to a single piece.
        //the below is the default from regular chicken

        this.bill = new ModelRenderer(this, 30, 0);
        this.bill.addBox(-1F, 11F, -7F, 2, 2, 2);
        //main body
        this.body = new ModelRenderer(this, 6, 0);
        this.body.addBox(-3F, 13F, -3F, 6, 6, 8);
        this.body.setTextureOffset(34,10);
        this.body.addBox(-0.5F, 12F, 3F, 1, 4, 5);
        this.body.setTextureOffset(35, 11);
        this.body.addBox(-0.5F, 11F, 4F, 1, 1, 4);
        //right shank
        this.rightLeg = new ModelRenderer(this, 26, 0);
        this.rightLeg.addBox(1F, 18.5F, 1F, 1, 5, 1);
        this.rightLeg.setTextureOffset(4,5);
        this.rightLeg.addBox(0F, 23F, -1F, 3, 1, 2);
        this.rightLeg.setTextureOffset(7,6);
        this.rightLeg.addBox(1F, 23F, -2F, 1, 1, 1);
        //left shank
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
    }


    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale){
        EnhancedChicken enhancedChicken = (EnhancedChicken)entityIn;

        int[] genes = enhancedChicken.getGenes();
        boolean Waddles = false;

        //TODO do the below render call for EVERY box. Use the 'genes' array from above to decide on which boxes you want to render and which to not render.
        //you might be able to use the same stuff from above where it calls '.addBox' for each thing. ie: the head could have the comb added to it.
        //or the leg could have the shorter box added to it and/or the feather box.
        //ie: which comb to render
        //this.head.render(scale);
        if(genes[52] ==2 && genes[53] == 2){
            this.head = new ModelRenderer(this, 6, 14);
            this.head.addBox(-2F, 9F, -5F, 4, 6, 3);
        }else{
            this.head = new ModelRenderer(this, 6, 14);
            this.head.addBox(-2F, 9F, -5F, 4, 4, 3);
            this.head.setTextureOffset(5,0);
            this.head.addBox(-1F, 13F, -4F, 2, 3, 2);
        }

        if(genes[50] == 1 || genes[51] == 1){
            if((genes[46] == 1 || genes[47] == 1) && (genes[48] == 1 || genes[49] == 1)){
                //walnut comb
                this.head.setTextureOffset(0,15);
                this.head.addBox(-0.5F, 8.5F, -5.5F, 1, 1, 1, 0.5F);
            }else if(genes[46] == 1 || genes[47] == 1){
                //rose comb
                this.head.setTextureOffset(0,15);
                this.head.addBox(-0.5F, 9F, -6F, 1, 1, 1, 0.5F);
                this.head.addBox(-0.5F, 8F, -5F, 1, 1, 1, 0.25F);
                if(genes[54] == 3 && genes[55] == 3) {
                    this.head.addBox(-0.5F, 7F, -4F, 1, 1, 1);
                }
                Waddles = true;
            }else if(genes[48] == 1 || genes[49] == 1){
                //pea comb
                this.head.setTextureOffset(0,15);
                this.head.addBox(-0.5F, 9F, -6F, 1, 1, 2, -0.2F);
                this.head.addBox(-0.5F, 8.5F, -5.5F, 1, 1, 1);
                this.head.addBox(-0.5F, 8F, -5F, 1, 2, 1,-0.2F);
            }else{
                //single comb
                this.head.setTextureOffset(0,15);
                this.head.addBox(-0.5F, 9F, -6F, 1, 1, 1);
                this.head.addBox(-0.5F, 8.5F, -6F, 1, 1, 1);
                this.head.addBox(-0.5F, 7.5F, -5F, 1, 2, 1);
                if(genes[54] == 3 && genes[55] == 3) {
                    this.head.addBox(-0.5F, 8F, -4F, 1, 1, 1);
                    this.head.addBox(-0.5F, 7F, -3F, 1, 2, 1);
                    this.head.addBox(-0.5F, 8F, -2F, 1, 1, 1);
                }
                Waddles = true;
            }
        }else{
            if(genes[46] == 2 && genes[47] == 2 && genes[48] == 2 && genes[49] == 2){
                //v comb
                this.head.setTextureOffset(0,15);
                this.head.addBox(-0.5F, 8.5F, -5.5F, 1, 1, 1);
                this.head.addBox(0F, 8F, -5.25F, 1, 1, 1, -0.2F);
                this.head.addBox(-1F, 8F, -5.25F, 1, 1, 1, -0.2F);
                this.head.addBox(.1F, 7.7F, -5F, 1, 1, 1, -0.3F);
                this.head.addBox(-1.1F, 7.7F, -5F, 1, 1, 1, -0.3F);
                Waddles = true;
            }else{
                if(genes[48] == 2 && genes[49] == 2){
                    //only waddles
                    Waddles = true;
                }
            }
        }

        //bearded
        if (genes[56] == 1 || genes[57] == 1){
            //is bearded
            this.head.setTextureOffset(22,14);
            this.head.addBox(-2.5F, 11.5F, -5.5F, 2, 3, 3, 0.1F);
            this.head.setTextureOffset(22,14);
            this.head.addBox(0.5F, 11.5F, -5.5F, 2, 3, 3, 0.1F);
            this.head.setTextureOffset(20,14);
            this.head.addBox(-2F, 12.5F, -6.5F, 4, 3, 3, 0.1F);
            Waddles = false;
        }

        if(Waddles){
            this.chin = new ModelRenderer(this, 0, 15);
            this.chin.addBox(-1F, 13F, -6F, 2, 2, 1);
        }

        //crestedness
        if((genes[54] == 2 || genes[55] == 2) && (genes[54] != 1 && genes[55] != 1)){
                //forward crest
            this.head.setTextureOffset(45,18);
            this.head.addBox(-1.5F, 7F, -6F, 3, 3, 3, 0.2F);
        }else if(genes[54] == 1 || genes[55] == 1){
            if(genes[54] == genes[55]){
                //big crest
                this.head.setTextureOffset(43,17);
                this.head.addBox(-2F, 6F, -5.5F, 4, 4, 4, 0.4F);
            }else{
                //small crest
                this.head.setTextureOffset(45,18);
                this.head.addBox(-1.5F, 6.5F, -5F, 3, 3, 3, 0.1F);
            }
        }

        //feather feets
        if(genes[58] == 1 || genes[59] == 1){
            if(genes[60] ==1 || genes[61] == 1){
                //2nd level foot feathers
                //shanks
                this.rightLeg.setTextureOffset(44,0);
                this.rightLeg.addBox(1.1F, 19F, 0F, 2, 3, 3);

                this.leftLeg.setTextureOffset(44,0);
                this.leftLeg.addBox(-3.1F, 19F, 0F, 2, 3, 3);
                //feets
                this.rightLeg.setTextureOffset(46,10);
                this.rightLeg.addBox(0.5F, 22F, -2.5F, 3, 2, 5);

                this.leftLeg.setTextureOffset(46,10);
                this.leftLeg.addBox(-6.5F, 22F, -2.5F, 3, 2, 5);
            } else {
                //1st level foot feathers
                //shanks
                this.rightLeg.setTextureOffset(44,0);
                this.rightLeg.addBox(1.1F, 19F, 0F, 2, 3, 3);

                this.leftLeg.setTextureOffset(44,0);
                this.leftLeg.addBox(-3.1F, 19F, 0F, 2, 3, 3);
            }
        } else if(genes[58] == 2 || genes[59] == 2){
            if(genes[60] ==1 || genes[61] == 1){
                //3rd level foot feathers
               //shanks
                this.rightLeg.setTextureOffset(44,0);
                this.rightLeg.addBox(1.1F, 19F, 0F, 2, 3, 3);

                this.leftLeg.setTextureOffset(44,0);
                this.leftLeg.addBox(-3.1F, 19F, 0F, 2, 3, 3);
                //feets
                this.rightLeg.setTextureOffset(46,10);
                this.rightLeg.addBox(0.5F, 22F, -2.5F, 3, 2, 5);

                this.leftLeg.setTextureOffset(46,10);
                this.leftLeg.addBox(-3.5F, 22F, -2.5F, 3, 2, 5);
                //toes
                this.rightLeg.setTextureOffset(17,3);
                this.rightLeg.addBox(4.5F, 23.9F, -2.5F, 4, 0, 5);

                this.leftLeg.setTextureOffset(17,3);
                this.leftLeg.addBox(-6.5F, 23.9F, -2.5F, 4, 0, 5);
            } else {
                //2nd level foot feathers
                this.rightLeg.setTextureOffset(44,0);
                this.rightLeg.addBox(1.1F, 19F, 0F, 2, 3, 3);

                this.leftLeg.setTextureOffset(44,0);
                this.leftLeg.addBox(-3.1F, 19F, 0F, 2, 3, 3);
                //feets
                this.rightLeg.setTextureOffset(46,10);
                this.rightLeg.addBox(0.5F, 22F, -2.5F, 3, 2, 5);

                this.leftLeg.setTextureOffset(46,10);
                this.leftLeg.addBox(-3.5F, 22F, -2.5F, 3, 2, 5);
            }
        } else {
            if (genes[60] == 1 || genes[61] == 1) {
                //1st level foot feathers
                //shanks
                this.rightLeg.setTextureOffset(44, 0);
                this.rightLeg.addBox(1.1F, 19F, 0F, 2, 3, 3);

                this.leftLeg.setTextureOffset(44, 0);
                this.leftLeg.addBox(-3.1F, 19F, 0F, 2, 3, 3);
            }
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
            this.head.render(scale);
            this.bill.render(scale);
            if(Waddles) {
                this.chin.render(scale);
            }
            this.body.render(scale);
            this.rightLeg.render(scale);
            this.leftLeg.render(scale);
            this.rightWing.render(scale);
            this.leftWing.render(scale);
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
