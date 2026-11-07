package mokiyoki.enhancedanimals.platform.attachment;

import net.minecraft.world.level.Level;

@FunctionalInterface
public interface LevelAttachment<T extends LevelSerializable> {

    T get(Level level);
}
