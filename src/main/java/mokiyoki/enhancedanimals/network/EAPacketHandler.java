package mokiyoki.enhancedanimals.network;

import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.Entity;

public class EAPacketHandler {

    public static void handleAnimalInventorySync(final EAEquipmentPacket message) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.execute(() -> {
            Entity entity = minecraft.level.getEntity(message.getEntityID());
            if (!(entity instanceof EnhancedAnimalAbstract))
                return;
            EnhancedAnimalAbstract enhancedAnimal = (EnhancedAnimalAbstract) entity;
            enhancedAnimal.getEnhancedInventory().setItem(message.getEquipmentSlot(), message.getItemStack());
        });
    }

    public static void handleParticleSync(final EAParticlePacket message) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.execute(() -> {
            int[] pos = message.getPos();
            ParticleOptions particle = message.getParticle();
            for (int i = 0; i < pos.length; i+= 3) {
                minecraft.level.addParticle(particle, pos[i], pos[i+1], pos[i+2], 0.0F, 0.0F, 10.0F);
            }
        });
    }
}
