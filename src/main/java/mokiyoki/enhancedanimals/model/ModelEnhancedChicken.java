package mokiyoki.enhancedanimals.model;

import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by saemon on 8/09/2018.
 */
@SideOnly(Side.CLIENT)
public class ModelEnhancedChicken extends ModelBase {

    private boolean nesting = false; //TODO actually make some nesting ai
    private boolean roosting = true; //TODO actually make some roosting ai
    private int pose = 0;
    private int mutation = 0;

    //the below is all chicken parts
    private final ModelRenderer head;
    private final ModelRenderer headNakedNeck;
    private final ModelRenderer bigCrest;
    private final ModelRenderer smallCrest;
    private final ModelRenderer forwardCrest;
    private final ModelRenderer combXtraSmallSingle;
    private final ModelRenderer combSmallSingle;
    private final ModelRenderer combSingle;
    private final ModelRenderer combLargeSingle;
    private final ModelRenderer combXtraLargeSingle;
    private final ModelRenderer combRose;
    private final ModelRenderer combRose2;
    private final ModelRenderer combPea;
    private final ModelRenderer combWalnut;
    private final ModelRenderer combV;
    private final ModelRenderer body;
    private final ModelRenderer tail;
    private final ModelRenderer rightLeg;
    private final ModelRenderer rightFeather1;
    private final ModelRenderer rightFeather2;
    private final ModelRenderer rightFeather3;
    private final ModelRenderer leftLeg;
    private final ModelRenderer leftFeather1;
    private final ModelRenderer leftFeather2;
    private final ModelRenderer leftFeather3;
    private final ModelRenderer rightWing;
    private final ModelRenderer leftWing;
    private final ModelRenderer bill;
    private final ModelRenderer smallChin;
    private final ModelRenderer chin;
    private final ModelRenderer bigChin;
    private final ModelRenderer beardChin;
    private final ModelRenderer peaChin;
    private final ModelRenderer beard;
    private final ModelRenderer beardNN;


    public ModelEnhancedChicken(){
        this.textureWidth = 64;
        this.textureHeight = 64;

        int combRy = -15;
        int combRz = 3;

        this.head = new ModelRenderer(this, 6, 14);
        this.head.addBox(-2.0F, -6.0F, -2.0F, 4, 6, 3, 0.0F);

        this.headNakedNeck = new ModelRenderer(this, 6, 14);
        this.headNakedNeck.addBox(-2.0F, -6.0F, -2.0F, 4, 4, 3, 0.0F);
        this.headNakedNeck.setTextureOffset(5,0);
        this.headNakedNeck.addBox(-1F, (13F+combRy), (-4F+combRz), 2, 3, 2);

        this.bigCrest = new ModelRenderer(this,43,17);
        this.bigCrest.addBox(-2F, (6F+combRy), (-5.5F+combRz), 4, 4, 4, 0.4F);

        this.smallCrest = new ModelRenderer(this,45,18);
        this.smallCrest.addBox(-1.5F, (6.5F+combRy), (-5F+combRz), 3, 3, 3, 0.1F);

        this.forwardCrest = new ModelRenderer(this,45,18);
        this.forwardCrest.addBox(-1.5F, (7F+combRy), (-6F+combRz), 3, 3, 3, 0.2F);

        int combSy = -3;
        int combSz = 3;

        this.combXtraSmallSingle = new ModelRenderer(this,0,14);
        this.combXtraSmallSingle.addBox(-0.5F, (-3.5F+combSy), (-5.5F+combSz), 1, 2, 1, -0.25F);
        this.combXtraSmallSingle.addBox(-0.5F, (-4F+combSy), (-5F+combSz), 1, 2, 1,-0.25F);
        this.combXtraSmallSingle.addBox(-0.5F, (-3.75F+combSy), (-4.5F+combSz), 1, 1, 1,-0.25F);
        this.combXtraSmallSingle.addBox(-0.5F, (-4.25F+combSy), (-4F+combSz), 1, 2, 1,-0.25F);
        this.combXtraSmallSingle.addBox(-0.5F, (-3.75F+combSy), (-3.5F+combSz), 1, 1, 1,-0.25F);
        this.combXtraSmallSingle.addBox(-0.5F, (-4F+combSy), (-3.25F+combSz), 1, 1, 1,-0.25F);

        this.combSmallSingle = new ModelRenderer(this,0,13);
        this.combSmallSingle.addBox(-0.5F, -6.75F, -2.25F, 1, 2, 1, -0.125F);
        this.combSmallSingle.addBox(-0.5F, -7.5F, -1.75F, 1, 1, 1, -0.125F);
        this.combSmallSingle.addBox(-0.5F, -6.75F, -1.5F, 1, 1, 2, -0.125F);
        this.combSmallSingle.addBox(-0.5F, -7.5F, -0.5F, 1, 1, 1, -0.125F);
        this.combSmallSingle.addBox(-0.5F, -6.75F, 0F, 1, 1, 1, -0.125F);
        this.combSmallSingle.addBox(-0.5F, -7F, 0.75F, 1, 1, 1, -0.125F);

        this.combSingle = new ModelRenderer(this,0,13);
        this.combSingle.addBox(-0.5F, (-3.0F+combSy), (-6F+combSz), 1, 1, 1);
        this.combSingle.addBox(-0.5F, (-3.5F+combSy), (-6F+combSz), 1, 1, 1);
        this.combSingle.addBox(-0.5F, (-4.5F+combSy), (-5F+combSz), 1, 2, 1);
        this.combSingle.addBox(-0.5F, (-4F+combSy), (-4F+combSz), 1, 1, 1);
        this.combSingle.addBox(-0.5F, (-4.5F+combSy), (-3F+combSz), 1, 2, 1);
        this.combSingle.addBox(-0.5F, (-3.5F+combSy), (-2F+combSz), 1, 1, 1);

        this.combLargeSingle = new ModelRenderer(this,0,14);
        this.combLargeSingle.addBox(-0.5F, (-4.5F+combSy), (-6.5F+combSz), 1, 1, 1);
        this.combLargeSingle.addBox(-0.5F, (-3.5F+combSy), (-6F+combSz), 1, 2, 1);
        this.combLargeSingle.addBox(-0.5F, (-5.5F+combSy), (-5F+combSz), 1, 3, 1);
        this.combLargeSingle.addBox(-0.5F, (-4.5F+combSy), (-4F+combSz), 1, 2, 1);
        this.combLargeSingle.addBox(-0.5F, (-6F+combSy), (-3F+combSz), 1, 3, 1);
        this.combLargeSingle.addBox(-0.5F, (-4.5F+combSy), (-2F+combSz), 1, 2, 1);
        this.combLargeSingle.addBox(-0.5F, (-5.5F+combSy), (-1F+combSz), 1, 3, 1);
        this.combLargeSingle.addBox(-0.5F, (-4F+combSy), (0F+combSz), 1, 1, 1);

        this.combXtraLargeSingle = new ModelRenderer(this,0,13);
        this.combXtraLargeSingle.addBox(-0.5F, (-5.5F+combSy), (-6.5F+combSz), 1, 2, 1);
        this.combXtraLargeSingle.addBox(-0.5F, (-4.5F+combSy), (-6F+combSz), 1, 3, 1);
        this.combXtraLargeSingle.addBox(-0.5F, (-7F+combSy), (-5F+combSz), 1, 4, 1);
        this.combXtraLargeSingle.addBox(-0.5F, (-5.5F+combSy), (-4F+combSz), 1, 3, 1);
        this.combXtraLargeSingle.addBox(-0.5F, (-6.5F+combSy), (-3F+combSz), 1, 4, 1);
        this.combXtraLargeSingle.addBox(-0.5F, (-7.5F+combSy), (-2.5F+combSz), 1, 1, 1);
        this.combXtraLargeSingle.addBox(-0.5F, (-5.5F+combSy), (-2F+combSz), 1, 3, 1);
        this.combXtraLargeSingle.addBox(-0.5F, (-6.5F+combSy), (-1F+combSz), 1, 4, 1);
        this.combXtraLargeSingle.addBox(-0.5F, (-7F+combSy), (-0.5F+combSz), 1, 1, 1);
        this.combXtraLargeSingle.addBox(-0.5F, (-5F+combSy), (0F+combSz), 1, 2, 1);
        this.combXtraLargeSingle.addBox(-0.5F, (-5.5F+combSy), (1F+combSz), 1, 2, 1);

        this.combRose = new ModelRenderer(this,0,15);
        this.combRose.addBox(-0.5F, (9F+combRy), (-6F+combRz), 1, 1, 1, 0.5F);
        this.combRose.addBox(-0.5F, (8F+combRy), (-5F+combRz), 1, 1, 1, 0.25F);
        this.combRose.addBox(-0.5F, (7F+combRy), (-4F+combRz), 1, 1, 1);

        this.combRose2 = new ModelRenderer(this,0,15);
        this.combRose2.addBox(-0.5F, (9F+combRy), (-6F+combRz), 1, 1, 1, 0.5F);
        this.combRose2.addBox(-0.5F, (8F+combRy), (-5F+combRz), 1, 1, 1, 0.25F);
        this.combRose2.addBox(-0.5F, (8F+combRy), (-4F+combRz), 1, 1, 1);

        int combPy = -15;
        int combPz = 3;
        this.combPea = new ModelRenderer(this,0,15);
        this.combPea.addBox(-0.5F, (9F+combPy), (-6F+combPz), 1, 1, 2, -0.2F);
        this.combPea.addBox(-0.5F, (8.5F+combPy), (-5.5F+combPz), 1, 1, 1);
        this.combPea.addBox(-0.5F, (8F+combPy), (-5F+combPz), 1, 2, 1,-0.2F);

        this.combWalnut = new ModelRenderer(this,0,15);
        this.combPea.addBox(-0.5F, (8.5F+combPy), (-5.5F+combPz), 1, 1, 1);

        this.combV = new ModelRenderer(this,0,15);
        this.combV.addBox(-0.5F, (8.5F+combRy), (-5.5F+combRz), 1, 1, 1);
        this.combV.addBox(0F, (8F+combRy), (-5.25F+combRz), 1, 1, 1, -0.2F);
        this.combV.addBox(-1F, (8F+combRy), (-5.25F+combRz), 1, 1, 1, -0.2F);
        this.combV.addBox(.1F, (7.7F+combRy), (-5F+combRz), 1, 1, 1, -0.3F);
        this.combV.addBox(-1.1F, (7.7F+combRy), (-5F+combRz), 1, 1, 1, -0.3F);

        this.body = new ModelRenderer(this, 6, 0);
        this.body.addBox(-3F, 13F, -3F, 6, 6, 8);

        this.tail = new ModelRenderer(this,34,10);
        this.tail.addBox(-0.5F, 12F, 3F, 1, 4, 5);
        this.tail.setTextureOffset(35, 11);
        this.tail.addBox(-0.5F, 11F, 4F, 1, 1, 4);

        this.rightLeg = new ModelRenderer(this, 26, 0);
        this.rightLeg.addBox(1F, (18.5F+combPy), 1F, 1, 5, 1);
        this.rightLeg.setTextureOffset(4,5);
        this.rightLeg.addBox(0F, (23F+combPy), -1F, 3, 1, 2);
        this.rightLeg.setTextureOffset(7,6);
        this.rightLeg.addBox(1F, (23F+combPy), -2F, 1, 1, 1);

        this.rightFeather1 = new ModelRenderer(this,44,0);
        this.rightFeather1.addBox(1.1F, (19F+combPy), 0F, 2, 3, 3);

        this.leftFeather1 = new ModelRenderer(this,44,0);
        this.leftFeather1.addBox(-3.1F, (19F+combPy), 0F, 2, 3, 3);

        this.rightFeather2 = new ModelRenderer(this,46,10);
        this.rightFeather2.addBox(1.5F, (22F+combPy), -2.5F, 2, 2, 5);

        this.leftFeather2 = new ModelRenderer(this,46,10);
        this.leftFeather2.addBox(-3.5F, (22F+combPy), -2.5F, 2, 2, 5);

        this.rightFeather3 = new ModelRenderer(this,42,10);
        this.rightFeather3.addBox(3.5F, (23.9F+combPy), -2.5F, 4, 0, 5);

        this.leftFeather3 = new ModelRenderer(this,42,10);
        this.leftFeather3.mirror = true;
        this.leftFeather3.addBox(-7.5F, (23.9F+combPy), -2.5F, 4, 0, 5);

        this.leftLeg = new ModelRenderer(this, 26, 0);
        this.leftLeg.addBox(-2F, (18.5F+combPy), 1F, 1, 5, 1);
        this.leftLeg.setTextureOffset(4,5);
        this.leftLeg.addBox(-3F, (23F+combPy), -1F, 3, 1, 2);
        this.leftLeg.setTextureOffset(7,6);
        this.leftLeg.addBox(-2F, (23F+combPy), -2F, 1, 1, 1);

        this.rightWing = new ModelRenderer(this, 35, 0);
        this.rightWing.addBox(0.0F, 0.0F, -3.0F, 1, 4, 6);
        this.rightWing.setRotationPoint(-4.0F, 13.0F, 1.0F);

        this.leftWing = new ModelRenderer(this, 49, 0);
        this.leftWing.addBox(-1.0F, 0.0F, -3.0F, 1, 4, 6);
        this.leftWing.setRotationPoint(4.0F, 13.0F, 1.0F);

        this.bill = new ModelRenderer(this, 30, 0);
        this.bill.addBox(-1.0F, -4.0F, -4.0F, 2, 2, 2, 0.0F);
        this.bill.setRotationPoint(0.0F, 15.0F, -4.0F);

        this.smallChin = new ModelRenderer(this, 0, 13);
        this.smallChin.addBox(-1.0F, -2.0F, -3.0F, 2, 1, 2, 0.0F);

        this.chin = new ModelRenderer(this, 0, 13);
        this.chin.addBox(-1.0F, -2.0F, -3.0F, 2, 2, 1, 0.0F);

        this.bigChin = new ModelRenderer(this, 0, 13);
        this.bigChin.addBox(-1.0F, -2.0F, -3.0F, 2, 3, 1, 0.0F);

        this.peaChin = new ModelRenderer(this, 0, 13);
        this.peaChin.addBox(-0.5F, -2.0F, -3.0F, 1, 1, 2, 0.0F);

        this.beardChin = new ModelRenderer(this, 0, 13);
        this.beardChin.addBox(-1.5F, -2.5F, -3.0F, 3, 1, 1, 0.0F);

        this.beard = new ModelRenderer(this,22,14);
        this.beard.addBox(-2.5F, (11.5F+combRy), (-5.5F+combRz), 2, 3, 3, 0.1F);
        this.beard.setTextureOffset(22,14);
        this.beard.addBox(0.5F, (11.5F+combRy), (-5.5F+combRz), 2, 3, 3, 0.1F);
        this.beard.setTextureOffset(20,14);
        this.beard.addBox(-2F, (12.5F+combRy), (-6.5F+combRz), 4, 3, 3, 0.1F);

        this.beardNN = new ModelRenderer(this,22,14);
        this.beardNN.addBox(-2.5F, (11.5F+combRy), (-5.5F+combRz), 2, 3, 3, 0.1F);
        this.beardNN.setTextureOffset(22,14);
        this.beardNN.addBox(0.5F, (11.5F+combRy), (-5.5F+combRz), 2, 3, 3, 0.1F);
        this.beardNN.setTextureOffset(20,14);
        this.beardNN.addBox(-2F, (12.5F+combRy), (-6.5F+combRz), 4, 2, 3, 0.1F);


        this.body.addChild(rightWing);
        this.body.addChild(leftWing);

    }


    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale){
        EnhancedChicken enhancedChicken = (EnhancedChicken)entityIn;

        this.roosting = enhancedChicken.isRoosting();

        int[] genes = enhancedChicken.getSharedGenes();
        boolean nakedNeck = false;
        int crest = 0; // [0, 1, 2, 3]          [none, small, forward, big]
        int fFeet = 0; // [0, 1, 2, 3]          [none, 1, 1&2, 1&2&3]
        int comb = 0;  // [0, 1, 2, 3, 4, 5, 6] [none, single, rose, rose2, pea, walnut, v]
        int chin = 0;  // [0, 1, 2, 3, 4, 5]    [none, small waddles, waddles, large waddles, peacomb waddles, beard waddles]
        int beard = 0; // [0, 1, 2 ]            [none, beard, naked neck beard]
        float size = 1;  // [] [
        float height = 0; //this just puts the chicken on the ground
        int cSize = 0; // [0, 1, 2, 3, 4]

        if(genes[52] ==1 || genes[53] == 1){
            nakedNeck = true;
        }

        if(genes[50] == 1 && genes[51] == 1){
            if (genes[48] == 1 || genes[49] == 1){
                chin = 0;
                if(genes[46] == 3 && genes[47] == 3){
                    //peacomb
                    comb = 4;
                    if(genes[80] == 2 && genes[81] == 2 && (genes[82] == 1 || genes[83] == 1) && genes[84] == 2 && genes[85] == 2){
                        chin = 4;
                    }
                }else{
                    //walnut
                    comb = 5;
                }
            }else{
                chin = 1;
                if(genes[46] == 3 && genes[47] == 3) {
                    //single comb
                    comb = 1;
                }else if(genes[46] == 1 || genes[47] == 1){
                    //rose comb
                    comb = 2;
                }else{
                    //rose comb2
                    comb = 3;
                }
            }
        }else{
            if(genes[46] == 3 && genes[47] == 3 && genes[48] == 2 && genes[49] == 2){
                //v comb
                comb = 6;
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

        //chicken size
        if (genes[74] == 1){
            size = size - 0.1F;
        } else if (genes[74] == 3){
            size = size + 0.1F;
        }
        if (genes[75] == 1){
            size = size - 0.1F;
        } else if (genes[75] == 3){
            size = size + 0.1F;
        }
        if (genes[76] != 1 && genes[77] != 1){
            if (genes[76] == 2 || genes[77] == 2){
                size = size + 0.1F;
            }else if (genes[76] == 3 && genes[77] == 3){
                size = size + 0.2F;
            }
        }
        if (genes[78] == 2 && genes[79] == 2){
            size = size + 0.1F;
        }
        if (genes[7] == 2){
            size = size - 0.2F;
        }
        if (genes[8] == 2){
            if (size < 0.8){
                size = size - 0.1F;
            }else if(size < 1.4){
                size = size - 0.2F;
            }else{
                size = size - 0.3F;
            }
        }
        if (genes[20] != 1 && genes[21] != 1 && size > 0.9){
            size = size - 0.1F;
        }

        if (size < 0.5){
            size = 0.5F;
        }

        if (Float.compare(size, (float) 0.5)==0) {
            height = (float) 1.5;
        } else if (Float.compare(size, (float) 0.6)==0) {
            height = (float) 1.0;
        } else if (Float.compare(size, (float) 0.7)==0) {
            height = (float) 0.65;
        } else if (Float.compare(size, (float) 0.8)==0) {
            height = (float) 0.4;
        } else if (Float.compare(size, (float) 0.9)==0) {
            height = (float) 0.165;
        } else if (Float.compare(size, (float) 1.0)==0) {
            height = (float) 0.0;
        } else if (Float.compare(size, (float) 1.1)==0) {
            height = (float) -0.15;
        } else if (Float.compare(size, (float) 1.2)==0) {
            height = (float) -0.25;
        } else if (Float.compare(size, (float) 1.3)==0) {
           height = (float) -0.35;
        } else if (Float.compare(size, (float) 1.4)==0) {
            height = (float) -0.425;
        } else {
            //for size 1.5
            height = (float) -0.5;
        }

          //comb size [80 and 81 small / 82 and 83 large]
        if (genes[80] == 2){
            cSize = cSize + 1;
        }
        if (genes[81] == 2){
            cSize = cSize +1;
        }
        if (genes[82] == 1){
            cSize = cSize +1;
        }
        if (genes[83] == 1){
            cSize = cSize +1;
        }

        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);

        if (this.isChild)
        {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.6F, 0.6F, 0.6F);
            GlStateManager.translate(0.0F, 19.0F * scale, 0.1F);
            if(nakedNeck){
                this.headNakedNeck.render(scale);
            }else{
                this.head.render(scale);
            }
            this.bill.render(scale);
            if(chin == 2){
                this.beard.render(scale);
            }
            if(crest >= 1){
                this.forwardCrest.render(scale);
            }
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.4F, 0.4F, 0.4F);
            GlStateManager.translate(0.0F, 36.0F * scale, 0.0F);
            this.body.render(scale);
            this.rightLeg.render(scale);
            this.leftLeg.render(scale);
            if(fFeet >= 1){
                this.leftFeather1.render(scale);
                this.rightFeather1.render(scale);
                if(fFeet >= 2){
                    this.leftFeather2.render(scale);
                    this.rightFeather2.render(scale);
                }
            }
            GlStateManager.popMatrix();
        }
        else
        {
            GlStateManager.pushMatrix();
            GlStateManager.scale(size, size, size);
            GlStateManager.translate(0.0F, height, 0.0F);
            if(nakedNeck){
                this.headNakedNeck.render(scale);
            }else{
                this.head.render(scale);
            }
            if(chin == 1) {
                this.chin.render(scale);
            }else if(chin == 2){
                this.beard.render(scale);
            }
            if(fFeet >= 1) {
                this.leftFeather1.render(scale);
                this.rightFeather1.render(scale);
                if (fFeet >= 2) {
                    this.leftFeather2.render(scale);
                    this.rightFeather2.render(scale);
                    if (fFeet == 3) {
                        this.leftFeather3.render(scale);
                        this.rightFeather3.render(scale);
                    }
                }
            }
            if(comb == 1 && crest == 0){
                if (cSize == 0) {
                    this.combXtraSmallSingle.render(scale);
                } else if (cSize == 1) {
                    this.combSmallSingle.render(scale);
                } else if (cSize == 2) {
                    this.combSingle.render(scale);
                } else if (cSize == 3) {
                    this.combLargeSingle.render(scale);
                } else {
                    this.combXtraLargeSingle.render(scale);
                }
            }else if(comb == 2 && crest == 0){
                this.combRose.render(scale);
            }else if(comb == 3){
                this.combRose2.render(scale);
            }else if(comb == 4 || (comb == 1 && crest != 0)){
                this.combPea.render(scale);
            }else if(comb == 5 || ((comb == 2 || comb == 3) && crest != 0)){
                this.combWalnut.render(scale);
            }else if(comb == 6){
                this.combV.render(scale);
            }
            if(crest == 1){
                this.smallCrest.render(scale);
            }else if(crest == 2){
                this.forwardCrest.render(scale);
            }else if(crest == 3){
                this.bigCrest.render(scale);
            }
            if(genes[72] != 2 && genes[73] != 2){
                this.tail.render(scale);
            }
            this.body.render(scale);
            this.rightLeg.render(scale);
            this.leftLeg.render(scale);
            this.bill.render(scale);
            GlStateManager.popMatrix();
        }

  }


    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */

    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        //head stuff

        if(this.pose == 1){
            this.head.rotationPointY = 22F;
        } else if (this.pose == 2){
            this.head.rotationPointY = 21F;
        } else{
            if(this.mutation == 1){
                this.head.rotationPointY = 18F;
            }else{
                this.head.rotationPointY = 15F;
            }
        }
        this.head.rotationPointZ = -4F;
        this.head.rotateAngleX = headPitch * 0.017453292F;
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;

        copyModelAngles(head, headNakedNeck);

        copyModelAngles(head, bill);
        copyModelAngles(head, smallChin);
        copyModelAngles(head, chin);
        copyModelAngles(head, bigChin);
        copyModelAngles(head, beardChin);
        copyModelAngles(head, peaChin);

        copyModelAngles(head, smallCrest);
        copyModelAngles(head, bigCrest);
        copyModelAngles(head, forwardCrest);

        copyModelAngles(head, combXtraSmallSingle);
        copyModelAngles(head, combSmallSingle);
        copyModelAngles(head, combSingle);
        copyModelAngles(head, combLargeSingle);
        copyModelAngles(head, combXtraLargeSingle);
        copyModelAngles(head, combRose);
        copyModelAngles(head, combRose2);
        copyModelAngles(head, combPea);
        copyModelAngles(head, combWalnut);
        copyModelAngles(head, combV);

        copyModelAngles(head, beard);

        //leg stuff
        this.rightLeg.rotationPointY = 15F;
        this.leftLeg.rotationPointY = 15F;
        this.rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        copyModelAngles(rightLeg, rightFeather1);
        copyModelAngles(rightLeg, rightFeather2);
        copyModelAngles(rightLeg, rightFeather3);
        copyModelAngles(leftLeg, leftFeather1);
        copyModelAngles(leftLeg, leftFeather2);
        copyModelAngles(leftLeg, leftFeather3);

        //wing stuff
        this.rightWing.rotateAngleZ = ageInTicks;
        this.leftWing.rotateAngleZ = -ageInTicks;

    }

    public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime)
    {
        int[] sharedGenes = ((EnhancedChicken)entitylivingbaseIn).getSharedGenes();

            this.rightLeg.rotationPointX = 0F;
            this.leftLeg.rotationPointX = 0F;
            this.rightLeg.rotationPointY = 0F;
            this.leftLeg.rotationPointY = 0F;

        //gene variations
        if ((sharedGenes[70] == 2 || sharedGenes [71] == 2) && (!nesting && !roosting)) {
            this.body.rotationPointY = 3F;
            this.tail.rotationPointY = 3F;
            this.mutation = 1;
        } else {
            this.body.rotationPointY = 0F;
            this.tail.rotationPointY = 0F;
            this.mutation = 0;
        }
        //behaviour animations
        //nesting "moves legs together to remove clipping"
        if(nesting){
            this.body.rotationPointY = 5F;
            this.tail.rotationPointY = 5F;
            this.rightLeg.rotationPointX = this.rightLeg.rotationPointX - 0.1F;
            this.leftLeg.rotationPointX = this.leftLeg.rotationPointX + 0.1F;
            this.pose = 1;
        } else {
            if(roosting){
                this.body.rotationPointY = 5F;
                this.tail.rotationPointY = 5F;
                this.rightLeg.rotationPointY = 1F;
                this.leftLeg.rotationPointY = 1F;
                this.pose = 2;
            } else {
                this.pose = 0;
            }
        }
        //pecking ground

        //scratching (eating grass)

        //crowing

    }

}
