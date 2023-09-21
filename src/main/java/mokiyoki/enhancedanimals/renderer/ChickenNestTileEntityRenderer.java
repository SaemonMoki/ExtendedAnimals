package mokiyoki.enhancedanimals.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mokiyoki.enhancedanimals.items.EnhancedEgg;
import mokiyoki.enhancedanimals.tileentity.ChickenNestTileEntity;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

public class ChickenNestTileEntityRenderer<T extends BlockEntity> implements BlockEntityRenderer<T> {
    public static final ResourceLocation EGG_PLAIN_TEXTURE = new ResourceLocation(Reference.MODID, "block/chicken_nest/plain_egg");
    public static final ResourceLocation EGG_SPECKLE_TEXTURE = new ResourceLocation(Reference.MODID, "block/chicken_nest/speckle_egg");
    public static final ResourceLocation EGG_SPATTER_TEXTURE = new ResourceLocation(Reference.MODID, "block/chicken_nest/splotch_egg");
    public static final ResourceLocation EGG_SPOT_TEXTURE = new ResourceLocation(Reference.MODID, "block/chicken_nest/spot_egg");
    public static final ModelLayerLocation CHICKEN_NEST = new ModelLayerLocation(new ResourceLocation(Reference.MODID, "chicken_nest"), "main");

    private final ModelPart[] egg = new ModelPart[12];
    public ChickenNestTileEntityRenderer(BlockEntityRendererProvider.Context context) {
        ModelPart root = context.bakeLayer(CHICKEN_NEST);
        for (int i = 0; i < 12; i++) {
            this.egg[i] = root.getChild("egg"+i);
        }
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("egg0", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -2.0F, -1.5F, 3, 4, 3), PartPose.offsetAndRotation(8.5F, 3.25F, 6.0F, 0.0F, Mth.PI*0.25F, 0.0F));
        partdefinition.addOrReplaceChild("egg1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -2.0F, -1.5F, 3, 4, 3), PartPose.offsetAndRotation(9.0F, 3.0F, 9.0F, 0.0F, Mth.PI*-0.125F, 0.0F));
        partdefinition.addOrReplaceChild("egg2", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -2.0F, -1.5F, 3, 4, 3), PartPose.offsetAndRotation(5.5F, 2.5F, 8.5F, 0.0F, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("egg3", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -2.0F, -1.5F, 3, 4, 3), PartPose.offsetAndRotation(11.5F, 2.5F, 9.25F, -Mth.PI*0.5F, Mth.PI*-0.125F, 0.0F));
        partdefinition.addOrReplaceChild("egg4", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -2.0F, -1.5F, 3, 4, 3), PartPose.offsetAndRotation(8.5F, 3.0F, 13.0F, -Mth.PI*0.625F, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("egg5", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -2.0F, -1.5F, 3, 4, 3), PartPose.offsetAndRotation(5.25F, 3.0F, 13.0F, 0.0F, 0.0F, -Mth.PI*0.625F));
        partdefinition.addOrReplaceChild("egg6", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -2.0F, -1.5F, 3, 4, 3), PartPose.offsetAndRotation(3.25F, 2.5F, 10.5F, -Mth.PI*0.5F, Mth.PI*0.125F, 0.0F));
        partdefinition.addOrReplaceChild("egg7", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -2.0F, -1.5F, 3, 4, 3), PartPose.offsetAndRotation(3.25F, 2.5F, 5.5F, 0.0F, 0.0F, -Mth.PI*0.625F));
        partdefinition.addOrReplaceChild("egg8", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -2.0F, -1.5F, 3, 4, 3), PartPose.offsetAndRotation(6.0F, 3.0F, 3.0F, -Mth.PI*0.375F, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("egg9", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -2.0F, -1.5F, 3, 4, 3), PartPose.offsetAndRotation(10.25F, 3.0F, 3.75F, 0.0F, 0.0F, -Mth.PI*0.375F));
        partdefinition.addOrReplaceChild("egg10", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -2.0F, -1.5F, 3, 4, 3), PartPose.offsetAndRotation(12.75F, 2.5F, 6.0F, -Mth.PI*0.375F, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("egg11", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -2.0F, -1.5F, 3, 4, 3), PartPose.offsetAndRotation(12.0F, 3.0F, 11.75F, 0.0F, Mth.PI * 0.25F, 0.0F));
        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    @Override
    public void render(T tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        ChickenNestTileEntity nest = (ChickenNestTileEntity) tileEntityIn;

        if (!nest.isEmpty()) {
            for (int i = 0; i < 12;i++) {
                ItemStack egg = nest.getItem(i);
                if (!egg.isEmpty()) {
                    Item eggItem = egg.getItem();
                    renderEggs(this.egg[i], getResourceLocation(eggItem), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, ((EnhancedEgg)eggItem).getColour());
                }
            }
        }
    }

    @NotNull
    private static ResourceLocation getResourceLocation(Item egg) {
        String eggName = egg.toString();
        ResourceLocation eggTexture = EGG_PLAIN_TEXTURE;
        if (eggName.endsWith("spot")) {
            eggTexture = EGG_SPOT_TEXTURE;
        } else if (eggName.endsWith("spatter")) {
            eggTexture = EGG_SPATTER_TEXTURE;
        } else if (eggName.endsWith("speckle")) {
            eggTexture = EGG_SPECKLE_TEXTURE;
        }
        return eggTexture;
    }

    private void renderEggs(ModelPart part, ResourceLocation texture, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn, int rgb) {
        float r = ((rgb & 16711680) >> 16)/255F;
        float g = ((rgb & '\uff00') >> 8)/255F;
        float b = ((rgb  & 255) >> 0)/255F;
        Material rendermaterial = new Material(Sheets.CHEST_SHEET, texture);
        VertexConsumer ivertexbuilder = rendermaterial.buffer(bufferIn, RenderType::entityCutout);
        part.render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn,r,g,b,1.0F);
    }
}
