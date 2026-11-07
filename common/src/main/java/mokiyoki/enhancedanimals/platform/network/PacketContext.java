package mokiyoki.enhancedanimals.platform.network;

import net.minecraft.world.entity.player.Player;

public interface PacketContext {

    Player player();

    void enqueueWork(Runnable task);
}
