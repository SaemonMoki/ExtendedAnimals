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
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.UUID;

@OnlyIn(Dist.CLIENT)
public final class RenderCarriedAnimal {

    private RenderCarriedAnimal() {
    }

    @Nullable
    public static UUID animalIdOf(ItemStack stack) {
        CompoundTag tag = stack.getTagElement("CarriedEntity");
        return tag != null && tag.hasUUID("UUID") ? tag.getUUID("UUID") : null;
    }

    // Builds a client-only copy of the carried animal. Loaded once, so its gene, texture and model caches all
    // belong to this one animal. Never reuse it for a different animal.
    @Nullable
    public static EnhancedAnimalAbstract createProxy(ItemStack stack, Level level) {
        CompoundTag tag = stack.getTagElement("CarriedEntity");
        if (tag == null || level == null) {
            return null;
        }

        EntityType<?> type = EntityType.by(tag).orElse(null);
        if (type == null) {
            return null;
        }

        Entity created = type.create(level);
        if (!(created instanceof EnhancedAnimalAbstract proxy)) {
            return null;
        }
        proxy.load(tag);
        return proxy;
    }

    public static void render(EnhancedAnimalAbstract proxy, PoseStack poseStack, MultiBufferSource buffer, int light, float yaw, boolean flip) {
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
