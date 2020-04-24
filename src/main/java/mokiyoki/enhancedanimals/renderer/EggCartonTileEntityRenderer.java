package mokiyoki.enhancedanimals.renderer;


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mokiyoki.enhancedanimals.blocks.EggCartonBlock;
import mokiyoki.enhancedanimals.tileentity.EggCartonTileEntity;
import mokiyoki.enhancedanimals.util.Reference;
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
    public static final ResourceLocation EGG_CARTON_TEXTURE = new ResourceLocation(Reference.MODID, "block/egg_carton");

    private final ModelRenderer base;
    private final ModelRenderer inside;
    private final ModelRenderer lid;

    public EggCartonTileEntityRenderer(TileEntityRendererDispatcher tileEntityRendererDispatcher) {
        super(tileEntityRendererDispatcher);

        base = new ModelRenderer(64, 64, 0, 0);
        base.addBox(1.0F, 0.0F, 2.0F, 14, 5, 12, 0.0F);
//        base.setRotationPoint(0.0F, 0.0F, 0.0F);

        inside = new ModelRenderer(64, 64, 0, 47);
        inside.addBox( 0.5F, 0.0F, 2.0F, 15, 3, 12, -2.0F);

        lid = new ModelRenderer(64, 64, 0, 19);
        lid.addBox(1.0F, -12.0F, 0.0F, 14, 12, 4, 0.0F);
        lid.setRotationPoint(0.0F, 12.0F, 14.0F);
//        this.setLidRotationAngle(this.lid, 0.0F, 0.0F, 0.0F);

    }

    @Override
    public void render(T tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        EggCartonTileEntity tileEntity = (EggCartonTileEntity) tileEntityIn;

        World world = tileEntityIn.getWorld();
        boolean flag = world != null;

        BlockState blockstate = flag ? tileEntityIn.getBlockState() : Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, Direction.SOUTH);
        Block block = blockstate.getBlock();
        ChestType chestType = ChestType.SINGLE;

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

        this.handleModelRender(matrixStackIn, ivertexbuilder, f1, i, combinedOverlayIn);

        matrixStackIn.pop();
    }


    private void handleModelRender(MatrixStack matrixStackIn, IVertexBuilder iVertexBuilder, float f1, int p_228871_7_, int p_228871_8_) {
        this.lid.rotateAngleX = -(f1 * ((float) Math.PI / 2F));
//        secondModel.rotateAngleX = firstModel.rotateAngleX;
        this.lid.render(matrixStackIn, iVertexBuilder, p_228871_7_, p_228871_8_);
        this.inside.render(matrixStackIn, iVertexBuilder, p_228871_7_, p_228871_8_);
        this.base.render(matrixStackIn, iVertexBuilder, p_228871_7_, p_228871_8_);
    }

    public void setLidRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = modelRenderer.rotateAngleX + x;
        modelRenderer.rotateAngleY = modelRenderer.rotateAngleY + y;
        modelRenderer.rotateAngleZ = modelRenderer.rotateAngleZ + z;
    }

}