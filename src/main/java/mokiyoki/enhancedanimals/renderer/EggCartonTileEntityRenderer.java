//package mokiyoki.enhancedanimals.renderer;
//
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.blaze3d.vertex.VertexConsumer;
//import mokiyoki.enhancedanimals.blocks.EggCartonBlock;
//import mokiyoki.enhancedanimals.tileentity.EggCartonTileEntity;
//import mokiyoki.enhancedanimals.util.Reference;
//import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.block.ChestBlock;
//import net.minecraft.client.renderer.Sheets;
//import net.minecraft.client.renderer.MultiBufferSource;
//import net.minecraft.client.renderer.RenderType;
//import net.minecraft.client.model.geom.ModelPart;
//import net.minecraft.client.resources.model.Material;
//import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
//import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
//import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
//import net.minecraft.world.level.block.state.properties.ChestType;
//import net.minecraft.world.level.block.entity.LidBlockEntity;
//import net.minecraft.world.level.block.entity.BlockEntity;
//import net.minecraft.world.level.block.DoubleBlockCombiner;
//import net.minecraft.core.Direction;
//import net.minecraft.resources.ResourceLocation;
//import com.mojang.math.Vector3f;
//import net.minecraft.world.level.Level;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//
//@OnlyIn(Dist.CLIENT)
//public class EggCartonTileEntityRenderer<T extends BlockEntity & LidBlockEntity> implements BlockEntityRenderer<T> {
//    public static final ResourceLocation EGG_CARTON_TEXTURE = new ResourceLocation(Reference.MODID, "block/egg_carton");
//
//    private final ModelPart base;
//    private final ModelPart inside;
//    private final ModelPart lid;
//
//    public EggCartonTileEntityRenderer(BlockEntityRendererProvider.Context tileEntityRendererDispatcher) {
//
//        base = new ModelPart(64, 64, 0, 0);
//        base.addBox(1.0F, -5.0F, -14.0F, 14, 5, 12, 0.0F);
////        base.setRotationPoint(0.0F, 0.0F, 0.0F);
//
//        inside = new ModelPart(64, 64, 0, 35);
//        inside.addBox( 0.5F, -6.0F, -14.0F, 15, 3, 12, -2.0F);
//
//        lid = new ModelPart(64, 64, 0, 19);
//        lid.addBox(1.0F, -12.0F, 0.0F, 14, 12, 4, 0.0F);
//        lid.setPos(0.0F, 4.0F, 2.0F);
////        this.setLidRotationAngle(this.lid, 0.0F, 0.0F, 0.0F);
//
//    }
//
//    @Override
//    public void render(T tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
//        EggCartonTileEntity tileEntity = (EggCartonTileEntity) tileEntityIn;
//
//        Level world = tileEntityIn.getLevel();
//        boolean flag = world != null;
//
//        BlockState blockstate = flag ? tileEntityIn.getBlockState() : Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.SOUTH);
//        Block block = blockstate.getBlock();
//        ChestType chestType = ChestType.SINGLE;
//
//        EggCartonBlock eggCartonBlock = (EggCartonBlock) block;
//
//        matrixStackIn.pushPose();
//        float f = blockstate.getValue(EggCartonBlock.FACING).toYRot();
//        matrixStackIn.translate(0.5D, 0.5D, 0.5D);
//        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(-f));
//        matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
//
//        DoubleBlockCombiner.NeighborCombineResult<? extends EggCartonTileEntity> iCallbackWrapper;
//        if (flag) {
//            iCallbackWrapper = eggCartonBlock.getWrapper(blockstate, world, tileEntity.getBlockPos(), true);
//        } else {
//            iCallbackWrapper = DoubleBlockCombiner.Combiner::acceptNone;
//        }
//
//        float f1 = iCallbackWrapper.apply(EggCartonBlock.getLid((LidBlockEntity) tileEntity)).get(partialTicks);
//        f1 = 1.0F - f1;
//        f1 = 1.0F - f1 * f1 * f1;
//        int i = iCallbackWrapper.apply(new BrightnessCombiner<>()).applyAsInt(combinedLightIn);
//
//        Material rendermaterial = new Material(Sheets.CHEST_SHEET, EGG_CARTON_TEXTURE);
//        VertexConsumer ivertexbuilder = rendermaterial.buffer(bufferIn, RenderType::entityCutoutNoCull);
//        this.handleModelRender(matrixStackIn, ivertexbuilder, f1, i, combinedOverlayIn);
//
//        matrixStackIn.popPose();
//    }
//
//
//    private void handleModelRender(PoseStack matrixStackIn, VertexConsumer iVertexBuilder, float f1, int p_228871_7_, int p_228871_8_) {
//        this.lid.xRot = (f1 * ((float)Math.PI/2.0F)) + (float)Math.PI/2.0F;
//        this.lid.zRot = (float)Math.PI;
//        this.lid.yRot = (float)Math.PI;
//        this.inside.xRot = (float)Math.PI;
//        this.base.xRot = (float)Math.PI;
//        //Math.PI / 2F
////        secondModel.rotateAngleX = firstModel.rotateAngleX;
//        this.lid.render(matrixStackIn, iVertexBuilder, p_228871_7_, p_228871_8_);
//        this.inside.render(matrixStackIn, iVertexBuilder, p_228871_7_, p_228871_8_);
//        this.base.render(matrixStackIn, iVertexBuilder, p_228871_7_, p_228871_8_);
//    }
//
//    public void setLidRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
//        modelRenderer.xRot = modelRenderer.xRot + x;
//        modelRenderer.yRot = modelRenderer.yRot + y;
//        modelRenderer.zRot = modelRenderer.zRot + z;
//    }
//
//}