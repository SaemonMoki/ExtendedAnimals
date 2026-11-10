package mokiyoki.enhancedanimals.util.handlers;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import mokiyoki.enhancedanimals.network.CarryTogglePacket;
import mokiyoki.enhancedanimals.platform.Services;
import mokiyoki.enhancedanimals.renderer.CarryTransform;
import mokiyoki.enhancedanimals.renderer.CarryTransforms;
import mokiyoki.enhancedanimals.renderer.RenderCarriedAnimal;
import net.minecraft.client.Camera;
import net.minecraft.client.CameraType;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CarryClientHandler {

    public static final KeyMapping CARRY_KEY = new KeyMapping("key.eanimod.carry", InputConstants.Type.KEYSYM, InputConstants.KEY_GRAVE, "key.categories.eanimod");

    // One entry per carrying player this client knows about, each with its own render copy of the animal
    private record Carried(ItemStack stack, @Nullable UUID animalId, @Nullable EnhancedAnimalAbstract proxy) {
    }

    private static final Map<UUID, Carried> CARRIED = new HashMap<>();

    public static void setCarried(UUID playerId, ItemStack stack) {
        if (stack.isEmpty()) {
            CARRIED.remove(playerId);
            return;
        }

        // A resync can deliver a different animal without us ever seeing the drop (picked up out of view),
        // so rebuild unless it's the exact same animal we already have
        UUID animalId = RenderCarriedAnimal.animalIdOf(stack);
        Carried existing = CARRIED.get(playerId);
        if (existing != null && existing.proxy() != null && animalId != null && animalId.equals(existing.animalId())) {
            CARRIED.put(playerId, new Carried(stack, animalId, existing.proxy()));
            return;
        }
        CARRIED.put(playerId, new Carried(stack, animalId, RenderCarriedAnimal.createProxy(stack, Minecraft.getInstance().level)));
    }

    @Nullable
    public static ItemStack getCarried(UUID playerId) {
        Carried carried = CARRIED.get(playerId);
        return carried == null ? null : carried.stack();
    }

    @Nullable
    public static EnhancedAnimalAbstract getProxy(UUID playerId) {
        Carried carried = CARRIED.get(playerId);
        return carried == null ? null : carried.proxy();
    }

    // A player leaving view: drop their entry, the server resends their state when they're tracked again
    public static void onEntityLeaveWorld(Entity entity, Level level) {
        if (level.isClientSide() && entity instanceof Player player) {
            CARRIED.remove(player.getUUID());
        }
    }

    // Respawn or dimension change: the old level is thrown away without its entities "leaving", so clear everything
    // here or the proxies keep the old level alive. The server resends our own state right after (PlayerRespawnEvent /
    // PlayerChangedDimensionEvent) and other players' when they're tracked in the new level
    public static void onRespawn() {
        CARRIED.clear();
    }

    public static void onLoggedOut() {
        CARRIED.clear();
    }

    public static void onInput() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) {
            return;
        }
        boolean carrying = CARRIED.containsKey(minecraft.player.getUUID());

        if (carrying) {
            for (KeyMapping key : minecraft.options.keyHotbarSlots) {
                key.consumeClick();
            }
        }

        while (CARRY_KEY.consumeClick()) {
            if (!carrying) {
                if (minecraft.hitResult instanceof EntityHitResult entityHitResult && entityHitResult.getEntity() instanceof EnhancedAnimalAbstract target) {
                    Services.NETWORK.sendToServer(new CarryTogglePacket(target.getId()));
                }
            } else {
                Services.NETWORK.sendToServer(new CarryTogglePacket(-1));
            }
        }
    }

    public static boolean onMouseScroll() {
        Minecraft minecraft = Minecraft.getInstance();
        return minecraft.player != null && CARRIED.containsKey(minecraft.player.getUUID());
    }

    public static void onRenderPlayerPre(Player player, PlayerModel<AbstractClientPlayer> model) {
        if (getCarried(player.getUUID()) == null) {
            return;
        }
        model.rightArm.visible = false;
        model.rightSleeve.visible = false;
        model.leftArm.visible = false;
        model.leftSleeve.visible = false;
    }

    public static void onRenderHand(InteractionHand hand, ItemStack itemStack, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null || getCarried(minecraft.player.getUUID()) == null) {
            return;
        }
        if (hand != InteractionHand.OFF_HAND || !itemStack.isEmpty()) {
            return;
        }

        HumanoidArm side = minecraft.player.getMainArm().getOpposite();
        float sideSign = side == HumanoidArm.RIGHT ? 1.0F : -1.0F;

        poseStack.pushPose();
        poseStack.translate(sideSign * 0.64000005D, -0.6D, -0.71999997D);
        poseStack.mulPose(Axis.YP.rotationDegrees(sideSign * 45.0F));
        poseStack.translate(sideSign * -1.0D, 3.6D, 3.5D);
        poseStack.mulPose(Axis.ZP.rotationDegrees(sideSign * 120.0F));
        poseStack.mulPose(Axis.XP.rotationDegrees(200.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(sideSign * -135.0F));
        poseStack.translate(sideSign * 5.6D, 0.0D, 0.0D);

        PlayerRenderer renderer = (PlayerRenderer) minecraft.getEntityRenderDispatcher().getRenderer(minecraft.player);
        if (side == HumanoidArm.RIGHT) {
            renderer.renderRightHand(poseStack, multiBufferSource, packedLight, minecraft.player);
        } else {
            renderer.renderLeftHand(poseStack, multiBufferSource, packedLight, minecraft.player);
        }
        poseStack.popPose();
    }

    //third person (CarriedAnimalLayer) draws through the player's own normally-rendered body and works for
    //every viewer. In first person the local player's own body isn't rendered at all, so this renders the
    //local player's own carried item separately, positioned relative to the camera.
    //yes the fact I have to do this is bullshit
    public static void onRenderLevelStage() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null || minecraft.options.getCameraType() != CameraType.FIRST_PERSON) {
            return;
        }
        EnhancedAnimalAbstract proxy = getProxy(minecraft.player.getUUID());
        if (proxy == null) {
            return;
        }
        CarryTransform t = CarryTransforms.firstPerson(proxy.getType());

        Camera camera = minecraft.gameRenderer.getMainCamera();
        Vec3 camPos = camera.getPosition();
        Vector3f lookVec = camera.getLookVector();
        Vec3 look = new Vec3(lookVec.x(), lookVec.y(), lookVec.z());
        float yawRad = (float) Math.toRadians(camera.getYRot());
        Vec3 horizontalForward = new Vec3(-Mth.sin(yawRad), 0.0D, Mth.cos(yawRad));
        Vec3 right = horizontalForward.cross(new Vec3(0.0D, 1.0D, 0.0D)).normalize();
        Vector3f upVec = camera.getUpVector();
        Vec3 up = new Vec3(upVec.x(), upVec.y(), upVec.z());

        Vec3 worldPos = camPos.add(look.scale(t.forward())).add(right.scale(t.sideways())).add(up.scale(t.vertical()));

        PoseStack poseStack = new PoseStack();
        poseStack.pushPose();
        poseStack.translate(worldPos.x - camPos.x, worldPos.y - camPos.y, worldPos.z - camPos.z);
        float pitch = camera.getXRot();
        float tiltAngle = pitch > 0.0F ? pitch * 0.4F : pitch;
        poseStack.mulPose(Axis.of(new Vector3f((float) right.x, (float) right.y, (float) right.z)).rotationDegrees(-tiltAngle));

        MultiBufferSource.BufferSource buffer = minecraft.renderBuffers().bufferSource();
        int light = LevelRenderer.getLightColor(minecraft.level, BlockPos.containing(worldPos));
        float yaw = camera.getYRot() + t.yaw();
        RenderCarriedAnimal.render(proxy, poseStack, buffer, light, yaw, t.flip());
        buffer.endBatch();

        poseStack.popPose();
    }
}
