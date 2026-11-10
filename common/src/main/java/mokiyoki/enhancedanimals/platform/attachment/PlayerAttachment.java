package mokiyoki.enhancedanimals.platform.attachment;

import net.minecraft.world.entity.player.Player;

public interface PlayerAttachment<T> {

    T get(Player player);

    void set(Player player, T value);
}
