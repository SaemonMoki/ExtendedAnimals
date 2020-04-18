package mokiyoki.enhancedanimals.renderer;


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mokiyoki.enhancedanimals.blocks.EggCartonBlock;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.model.ModelEggCarton;
import mokiyoki.enhancedanimals.tileentity.EggCartonTileEntity;
import net.minecraft.block.AbstractChestBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.DualBrightnessCallback;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMerger;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EggCartonTileEntityRenderer<T extends TileEntity & IChestLid> extends TileEntityRenderer<T> {
    private static final ResourceLocation EGG_CARTON_TEXTURE = new ResourceLocation("eanimod:textures/block/egg_carton.png");

    private final ModelRenderer base;
    private final ModelRenderer lid;

    public EggCartonTileEntityRenderer(TileEntityRendererDispatcher tileEntityRendererDispatcher) {
        super(tileEntityRendererDispatcher);

        base = new ModelRenderer(64, 64, 0, 19);
        base.setRotationPoint(0.0F, 0.0F, 0.0F);
        base.addBox(0, 0, 1.0F, 11.0F, 2.0F, 14, 5, 12, 0.0F);
        base.addBox(0, 35, 0.5F, 10.0F, 2.0F, 15, 3, 12, -2.0F);

        lid = new ModelRenderer(64, 64, 0, 0);
        lid.setRotationPoint(0.0F, 12.0F, 14.0F);
        lid.addBox(0, 19, 1.0F, -12.0F, 0.0F, 14, 12, 4, 0.0F);
        this.setLidRotationAngle(this.lid, 0.0F, 0.0F, 0.0F);

    }

    public void render(T tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        EggCartonTileEntity tileEntity = (EggCartonTileEntity) tileEntityIn;

        World world = tileEntityIn.getWorld();
        boolean flag = world != null;

        BlockState blockstate = flag ? tileEntityIn.getBlockState() : Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, Direction.SOUTH);
        ChestType chesttype = blockstate.has(ChestBlock.TYPE) ? blockstate.get(ChestBlock.TYPE) : ChestType.SINGLE;
        Block block = blockstate.getBlock();


        EggCartonBlock eggCartonBlock = (EggCartonBlock) block;

        matrixStackIn.push();
        float f = blockstate.get(EggCartonBlock.FACING).getHorizontalAngle();
        matrixStackIn.translate(0.5D, 0.5D, 0.5D);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-f));
        matrixStackIn.translate(-0.5D, -0.5D, -0.5D);

        TileEntityMerger.ICallbackWrapper<? extends EggCartonTileEntity> iCallbackWrapper;
        if (flag) {
            iCallbackWrapper = eggCartonBlock.getWrapper(blockstate, world, tileEntity.getPos(), true);
        } else {
            iCallbackWrapper = TileEntityMerger.ICallback::func_225537_b_;
        }

        float f1 = iCallbackWrapper.apply(EggCartonBlock.getLid((IChestLid) tileEntity)).get(partialTicks);
        f1 = 1.0F - f1;
        f1 = 1.0F - f1 * f1 * f1;
        int i = iCallbackWrapper.apply(new DualBrightnessCallback<>()).applyAsInt(combinedLightIn);

        Material material = new Material(Atlases.CHEST_ATLAS, EGG_CARTON_TEXTURE);
        IVertexBuilder ivertexbuilder = material.getBuffer(bufferIn, RenderType::getEntityCutout);

        this.handleModelRender(matrixStackIn, ivertexbuilder, this.lid, this.base, f1, i, combinedOverlayIn);

        matrixStackIn.pop();
    }


//    public void render(T tileEntityIn, double x, double y, double z, float partialTicks, int destroyStage) {
//        GlStateManager.enableDepthTest();
//        GlStateManager.depthFunc(515);
//        GlStateManager.depthMask(true);
//        BlockState blockstate = tileEntityIn.hasWorld() ? tileEntityIn.getBlockState() : ModBlocks.Egg_Carton.getDefaultState().with(EggCartonBlock.FACING, Direction.SOUTH);
//
//        ResourceLocation resourcelocation = EGG_CARTON_TEXTURE;
//        this.bindTexture(resourcelocation);
//
//        if (destroyStage >= 0) {
//            GlStateManager.matrixMode(5890);
//            GlStateManager.pushMatrix();
//            GlStateManager.scalef(4.0F, 4.0F, 1.0F); //TODO first number might need to be 8.0F
//            GlStateManager.translatef(0.0625F, 0.0625F, 0.0625F);
//            GlStateManager.matrixMode(5888);
//        } else {
//            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
//        }
//
//        GlStateManager.pushMatrix();
//        GlStateManager.enableRescaleNormal();
//        GlStateManager.translatef((float)x, (float)y + 1.0F, (float)z + 1.0F);
//        GlStateManager.scalef(1.0F, -1.0F, -1.0F);
//        float f = blockstate.get(EggCartonBlock.FACING).getHorizontalAngle();
//        if ((double)Math.abs(f) > 1.0E-5D) {
//            GlStateManager.translatef(0.5F, 0.5F, 0.5F);
//            GlStateManager.rotatef(f, 0.0F, 1.0F, 0.0F);
//            GlStateManager.translatef(-0.5F, -0.5F, -0.5F);
//        }
//
//        this.applyLidRotation(tileEntityIn, partialTicks, modelEggCarton);
//        modelEggCarton.renderAll();
//        GlStateManager.disableRescaleNormal();
//        GlStateManager.popMatrix();
//        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
//        if (destroyStage >= 0) {
//            GlStateManager.matrixMode(5890);
//            GlStateManager.popMatrix();
//            GlStateManager.matrixMode(5888);
//        }
//    }

    private void handleModelRender(MatrixStack matrixStackIn, IVertexBuilder iVertexBuilder, ModelRenderer firstModel, ModelRenderer secondModel, float f1, int p_228871_7_, int p_228871_8_) {
        this.lid.rotateAngleX = -(f1 * ((float) Math.PI / 2F));
//        secondModel.rotateAngleX = firstModel.rotateAngleX;
        this.lid.render(matrixStackIn, iVertexBuilder, p_228871_7_, p_228871_8_);
        this.base.render(matrixStackIn, iVertexBuilder, p_228871_7_, p_228871_8_);
    }

    public void setLidRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = modelRenderer.rotateAngleX + x;
        modelRenderer.rotateAngleY = modelRenderer.rotateAngleY + y;
        modelRenderer.rotateAngleZ = modelRenderer.rotateAngleZ + z;
    }

}

//    private void applyLidRotation(T p_199346_1_, float p_199346_2_, ModelEggCarton p_199346_3_) {
//        float f = ((IChestLid)p_199346_1_).getLidAngle(p_199346_2_);
//        f = 1.0F - f;
//        f = 2.0F - f * f * f;
//        p_199346_3_.getLid().rotateAngleX = (f * ((float)Math.PI / 2F));
//    }
//}