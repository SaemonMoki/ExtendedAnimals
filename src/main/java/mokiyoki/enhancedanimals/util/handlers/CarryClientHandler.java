package mokiyoki.enhancedanimals.util.handlers;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import mokiyoki.enhancedanimals.EnhancedAnimals;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import mokiyoki.enhancedanimals.network.CarryTogglePacket;
import mokiyoki.enhancedanimals.renderer.CarryTransform;
import mokiyoki.enhancedanimals.renderer.CarryTransforms;
import mokiyoki.enhancedanimals.renderer.RenderCarriedAnimal;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.client.Camera;
import net.minecraft.client.CameraType;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = Reference.MODID, value = Dist.CLIENT)
public class CarryClientHandler {

    public static final KeyMapping CARRY_KEY = new KeyMapping("key.eanimod.carry", KeyConflictContext.IN_GAME, InputConstants.Type.MOUSE.getOrCreate(2), "key.categories.eanimod");

    private static final Map<UUID, ItemStack> CARRIED = new HashMap<>();

    public static void setCarried(UUID playerId, ItemStack stack) {
        if (stack.isEmpty()) {
            CARRIED.remove(playerId);
        } else {
            CARRIED.put(playerId, stack);
        }
    }

    public static ItemStack getCarried(UUID playerId) {
        return CARRIED.get(playerId);
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        onInput();
    }

    @SubscribeEvent
    public static void onMouseInput(InputEvent.MouseInputEvent event) {
        onInput();
    }

    private static void onInput() {
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
            if (!Screen.hasShiftDown()) {
                continue;
            }
            if (!carrying) {
                if (minecraft.hitResult instanceof EntityHitResult entityHitResult && entityHitResult.getEntity() instanceof EnhancedAnimalAbstract target) {
                    EnhancedAnimals.channel.send(PacketDistributor.SERVER.noArg(), new CarryTogglePacket(target.getId()));
                }
            } else {
                EnhancedAnimals.channel.send(PacketDistributor.SERVER.noArg(), new CarryTogglePacket(-1));
            }
        }
    }

    @SubscribeEvent
    public static void onMouseScroll(InputEvent.MouseScrollEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player != null && CARRIED.containsKey(minecraft.player.getUUID())) {
            event.setCanceled(true);
        }
    }

    //third person (CarriedAnimalLayer) draws through the player's own normally-rendered body and works for
    //every viewer. In first person the local player's own body isn't rendered at all, so this renders the
    //local player's own carried item separately, positioned relative to the camera.
    //yes the fact I have to do this is bullshit
    @SubscribeEvent
    public static void onRenderLevelStage(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) {
            return;
        }
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null || minecraft.options.getCameraType() != CameraType.FIRST_PERSON) {
            return;
        }
        ItemStack carried = CARRIED.get(minecraft.player.getUUID());
        if (carried == null) {
            return;
        }
        EntityType<?> type = RenderCarriedAnimal.typeOf(carried);
        if (type == null) {
            return;
        }
        CarryTransform t = CarryTransforms.firstPerson(type);

        Camera camera = event.getCamera();
        Vec3 camPos = camera.getPosition();
        Vector3f lookVec = camera.getLookVector();
        Vector3f upVec = camera.getUpVector();
        Vec3 look = new Vec3(lookVec.x(), lookVec.y(), lookVec.z());
        Vec3 up = new Vec3(upVec.x(), upVec.y(), upVec.z());
        Vec3 right = look.cross(up).normalize();

        Vec3 worldPos = camPos.add(look.scale(t.forward())).add(right.scale(t.sideways())).add(up.scale(t.vertical()));

        PoseStack poseStack = event.getPoseStack();
        poseStack.pushPose();
        poseStack.translate(worldPos.x - camPos.x, worldPos.y - camPos.y, worldPos.z - camPos.z);

        MultiBufferSource.BufferSource buffer = minecraft.renderBuffers().bufferSource();
        int light = LevelRenderer.getLightColor(minecraft.level, new BlockPos(worldPos));
        float yaw = camera.getYRot() + t.yaw();
        RenderCarriedAnimal.render(carried, poseStack, buffer, light, yaw, t.flip());
        buffer.endBatch();

        poseStack.popPose();
    }
}
