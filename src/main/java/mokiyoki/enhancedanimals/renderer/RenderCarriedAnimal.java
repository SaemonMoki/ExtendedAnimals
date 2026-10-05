package mokiyoki.enhancedanimals.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public final class RenderCarriedAnimal {

    private static final Map<EntityType<?>, EnhancedAnimalAbstract> proxies = new HashMap<>();

    private RenderCarriedAnimal() {
    }

    @Nullable
    public static EntityType<?> typeOf(ItemStack stack) {
        CompoundTag tag = stack.getTagElement("CarriedEntity");
        return tag == null ? null : EntityType.by(tag).orElse(null);
    }

    public static void render(ItemStack stack, PoseStack poseStack, MultiBufferSource buffer, int light, float yaw, boolean flip) {
        CompoundTag tag = stack.getTagElement("CarriedEntity");
        if (tag == null) {
            return;
        }

        EntityType<?> type = EntityType.by(tag).orElse(null);
        if (type == null) {
            return;
        }

        EnhancedAnimalAbstract proxy = proxies.computeIfAbsent(type, t -> {
            Entity created = t.create(Minecraft.getInstance().level);
            return created instanceof EnhancedAnimalAbstract enhancedAnimal ? enhancedAnimal : null;
        });
        if (proxy == null) {
            return;
        }

        proxy.load(tag);
        //avoid stale animation state from before pickup, and feed the caller's yaw through cleanly (no interpolation spin)
        proxy.hurtTime = 0;
        proxy.setYRot(yaw);
        proxy.yRotO = yaw;
        proxy.xRotO = proxy.getXRot();
        proxy.yBodyRot = yaw;
        proxy.yBodyRotO = yaw;
        proxy.setYHeadRot(yaw);
        proxy.yHeadRotO = yaw;

        poseStack.pushPose();
        if (flip) {
            poseStack.scale(-1.0F, -1.0F, 1.0F);
        }
        EntityRenderDispatcher dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        dispatcher.setRenderShadow(false);
        dispatcher.render(proxy, 0.0D, 0.0D, 0.0D, yaw, 1.0F, poseStack, buffer, light);
        dispatcher.setRenderShadow(true);
        poseStack.popPose();
    }
}
