package mokiyoki.enhancedanimals.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import mokiyoki.enhancedanimals.util.handlers.CarryClientHandler;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
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
}
