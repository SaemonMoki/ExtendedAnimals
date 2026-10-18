package mokiyoki.enhancedanimals.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mokiyoki.enhancedanimals.util.handlers.CarryClientHandler;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CarriedAnimalLayer<T extends AbstractClientPlayer, M extends PlayerModel<T>> extends RenderLayer<T, M> {

    public CarriedAnimalLayer(RenderLayerParent<T, M> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int light, T player, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        ItemStack carried = CarryClientHandler.getCarried(player.getUUID());
        if (carried == null) {
            return;
        }
        renderOutstretchedArms(poseStack, buffer, light, player);

        EntityType<?> type = RenderCarriedAnimal.typeOf(carried);
        if (type == null) {
            return;
        }
        CarryTransform t = CarryTransforms.thirdPerson(type);

        poseStack.pushPose();
        //anchor to the body (not the arm) so it doesn't swing with the walk/attack animation
        getParentModel().body.translateAndRotate(poseStack);
        poseStack.translate(t.sideways(), t.vertical(), t.forward());
        RenderCarriedAnimal.render(carried, poseStack, buffer, light, t.yaw(), t.flip());
        poseStack.popPose();
    }

    private void renderOutstretchedArms(PoseStack poseStack, MultiBufferSource buffer, int light, T player) {
        PlayerModel<T> model = getParentModel();
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(player.getSkinTextureLocation()));

        poseArm(model.rightArm, -0.1F);
        model.rightArm.render(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY);
        poseArm(model.rightSleeve, -0.1F);
        model.rightSleeve.render(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY);
        poseArm(model.leftArm, 0.1F);
        model.leftArm.render(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY);
        poseArm(model.leftSleeve, 0.1F);
        model.leftSleeve.render(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY);
    }

    private static void poseArm(ModelPart arm, float zRot) {
        arm.visible = true;
        arm.xRot = -1.8F;
        arm.zRot = zRot;
    }
}
