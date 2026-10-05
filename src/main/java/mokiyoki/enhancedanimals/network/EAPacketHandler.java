package mokiyoki.enhancedanimals.network;

import mokiyoki.enhancedanimals.capability.carry.CarryCapabilityProvider;
import mokiyoki.enhancedanimals.capability.carry.ICarryCapability;
import mokiyoki.enhancedanimals.config.GeneticAnimalsConfig;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import mokiyoki.enhancedanimals.entity.EnhancedAxolotl;
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.entity.EnhancedCow;
import mokiyoki.enhancedanimals.entity.EnhancedHorse;
import mokiyoki.enhancedanimals.entity.EnhancedLlama;
import mokiyoki.enhancedanimals.entity.EnhancedPig;
import mokiyoki.enhancedanimals.entity.EnhancedRabbit;
import mokiyoki.enhancedanimals.entity.EnhancedSheep;
import mokiyoki.enhancedanimals.entity.EnhancedTurtle;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.util.handlers.CarryClientHandler;
import mokiyoki.enhancedanimals.EnhancedAnimals;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;

public class EAPacketHandler {

    private static final double MAX_CARRY_REACH_SQ = 36.0D;

    public static void handleAnimalInventorySync(final EAEquipmentPacket message)
    {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.execute(() -> {
            Entity entity = minecraft.level.getEntity(message.getEntityID());
            if (!(entity instanceof EnhancedAnimalAbstract))
                return;
            EnhancedAnimalAbstract enhancedAnimal = (EnhancedAnimalAbstract) entity;
            enhancedAnimal.getEnhancedInventory().setItem(message.getEquipmentSlot(), message.getItemStack());
        });
    }

    public static void handleCarryToggle(ServerPlayer sender, int targetEntityId) {
        if (sender == null) {
            return;
        }
        sender.getCapability(CarryCapabilityProvider.CARRY_CAP).ifPresent(cap -> {
            if (cap.isCarrying()) {
                dropCarried(sender, cap);
            } else {
                pickUpCarried(sender, cap, targetEntityId);
            }
        });
    }

    private static void pickUpCarried(ServerPlayer player, ICarryCapability cap, int targetEntityId) {
        if (!player.getMainHandItem().isEmpty() || !player.getOffhandItem().isEmpty()) {
            return;
        }
        Entity target = player.level.getEntity(targetEntityId);
        if (!(target instanceof EnhancedAnimalAbstract animal)) {
            return;
        }
        if (player.distanceToSqr(target) > MAX_CARRY_REACH_SQ) {
            return;
        }
        if (animal.getBbWidth() * animal.getBbWidth() * animal.getBbHeight() > GeneticAnimalsConfig.COMMON.maxCarryVolume.get()) {
            return;
        }
        if (animal.isVehicle() || animal.isPassenger() || animal.getLeashHolder() != null) {
            return;
        }
        if (!canBeCarried(animal)) {
            return;
        }
        if (carryBabiesOnly(animal) && !animal.isBaby()) {
            return;
        }

        CompoundTag tag = new CompoundTag();
        if (!animal.save(tag)) {
            return;
        }
        animal.discard();

        ItemStack stack = new ItemStack(ModItems.CARRIED_ANIMAL.get());
        stack.getOrCreateTag().put("CarriedEntity", tag);
        cap.setCarried(stack);

        broadcastCarryState(player, stack);
    }

    private static boolean canBeCarried(EnhancedAnimalAbstract animal) {
        GeneticAnimalsConfig.CommonConfig config = GeneticAnimalsConfig.COMMON;
        if (animal instanceof EnhancedCow) return config.cowCanBeCarried.get();
        if (animal instanceof EnhancedPig) return config.pigCanBeCarried.get();
        if (animal instanceof EnhancedHorse) return config.horseCanBeCarried.get();
        if (animal instanceof EnhancedLlama) return config.llamaCanBeCarried.get();
        if (animal instanceof EnhancedSheep) return config.sheepCanBeCarried.get();
        if (animal instanceof EnhancedChicken) return config.chickenCanBeCarried.get();
        if (animal instanceof EnhancedRabbit) return config.rabbitCanBeCarried.get();
        if (animal instanceof EnhancedTurtle) return config.turtleCanBeCarried.get();
        if (animal instanceof EnhancedAxolotl) return config.axolotlCanBeCarried.get();
        return true;
    }

    private static boolean carryBabiesOnly(EnhancedAnimalAbstract animal) {
        GeneticAnimalsConfig.CommonConfig config = GeneticAnimalsConfig.COMMON;
        if (animal instanceof EnhancedCow) return config.cowCarryBabiesOnly.get();
        if (animal instanceof EnhancedPig) return config.pigCarryBabiesOnly.get();
        if (animal instanceof EnhancedHorse) return config.horseCarryBabiesOnly.get();
        if (animal instanceof EnhancedLlama) return config.llamaCarryBabiesOnly.get();
        if (animal instanceof EnhancedSheep) return config.sheepCarryBabiesOnly.get();
        if (animal instanceof EnhancedChicken) return config.chickenCarryBabiesOnly.get();
        if (animal instanceof EnhancedRabbit) return config.rabbitCarryBabiesOnly.get();
        if (animal instanceof EnhancedTurtle) return config.turtleCarryBabiesOnly.get();
        if (animal instanceof EnhancedAxolotl) return config.axolotlCarryBabiesOnly.get();
        return false;
    }

    private static void dropCarried(ServerPlayer player, ICarryCapability cap) {
        ItemStack stack = cap.getCarried();
        CompoundTag tag = stack.getTagElement("CarriedEntity");
        cap.setCarried(ItemStack.EMPTY);
        broadcastCarryState(player, ItemStack.EMPTY);

        if (tag == null) {
            return;
        }

        Vec3 look = player.getLookAngle();
        double dropX = player.getX() + look.x * 2.0D;
        double dropZ = player.getZ() + look.z * 2.0D;
        EntityType.create(tag, player.level).ifPresent(created -> {
            created.moveTo(dropX, player.getY(), dropZ, player.getYRot(), 0.0F);
            player.level.addFreshEntity(created);
        });
    }

    private static void broadcastCarryState(ServerPlayer player, ItemStack carried) {
        EnhancedAnimals.channel.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), new CarrySyncPacket(player.getId(), carried));
    }

    public static void handleCarrySync(final CarrySyncPacket message) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.execute(() -> {
            Entity entity = minecraft.level.getEntity(message.getCarrierId());
            if (!(entity instanceof net.minecraft.world.entity.player.Player)) {
                return;
            }
            CarryClientHandler.setCarried(entity.getUUID(), message.getCarried());
        });
    }

}
