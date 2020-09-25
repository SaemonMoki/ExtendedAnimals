package mokiyoki.enhancedanimals.network;

import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

public class EAPacketHandler {

    public static void handleAnimalInventorySync(final EAEquipmentPacket message)
    {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.execute(() -> {
            Entity entity = minecraft.world.getEntityByID(message.getEntityID());
            if (!(entity instanceof EnhancedAnimalAbstract))
                return;
            EnhancedAnimalAbstract enhancedAnimal = (EnhancedAnimalAbstract) entity;
            enhancedAnimal.getEnhancedInventory().setInventorySlotContents(message.getEquipmentSlot(), message.getItemStack());
        });
    }

}
