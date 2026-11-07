package mokiyoki.enhancedanimals.network;

import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;

public class EAPacketHandler {

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

}
