package mokiyoki.enhancedanimals.renderer;


import com.mojang.blaze3d.platform.GlStateManager;
import mokiyoki.enhancedanimals.blocks.EggCartonBlock;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.model.ModelEggCarton;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EggCartonTileEntityRenderer <T extends TileEntity & IChestLid> extends TileEntityRenderer<T> {
    private static final ResourceLocation EGG_CARTON_TEXTURE = new ResourceLocation("eanimod:textures/block/egg_carton.png");

    private final ModelEggCarton modelEggCarton = new ModelEggCarton();

    public EggCartonTileEntityRenderer() {
    }

    public void render(T tileEntityIn, double x, double y, double z, float partialTicks, int destroyStage) {
        GlStateManager.enableDepthTest();
        GlStateManager.depthFunc(515);
        GlStateManager.depthMask(true);
        BlockState blockstate = tileEntityIn.hasWorld() ? tileEntityIn.getBlockState() : ModBlocks.Egg_Carton.getDefaultState().with(EggCartonBlock.FACING, Direction.SOUTH);

        ResourceLocation resourcelocation = EGG_CARTON_TEXTURE;
        this.bindTexture(resourcelocation);

        if (destroyStage >= 0) {
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scalef(4.0F, 4.0F, 1.0F); //TODO first number might need to be 8.0F
            GlStateManager.translatef(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(5888);
        } else {
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        }

        GlStateManager.pushMatrix();
        GlStateManager.enableRescaleNormal();
        GlStateManager.translatef((float)x, (float)y + 1.0F, (float)z + 1.0F);
        GlStateManager.scalef(1.0F, -1.0F, -1.0F);
        float f = blockstate.get(EggCartonBlock.FACING).getHorizontalAngle();
        if ((double)Math.abs(f) > 1.0E-5D) {
            GlStateManager.translatef(0.5F, 0.5F, 0.5F);
            GlStateManager.rotatef(f, 0.0F, 1.0F, 0.0F);
            GlStateManager.translatef(-0.5F, -0.5F, -0.5F);
        }

        this.applyLidRotation(tileEntityIn, partialTicks, modelEggCarton);
        modelEggCarton.renderAll();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        if (destroyStage >= 0) {
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        }
    }

    private void applyLidRotation(T p_199346_1_, float p_199346_2_, ModelEggCarton p_199346_3_) {
        float f = ((IChestLid)p_199346_1_).getLidAngle(p_199346_2_);
        f = 1.0F - f;
        f = 1.0F - f * f * f;
        p_199346_3_.getLid().rotateAngleX = -(f * ((float)Math.PI / 2F));
    }
}