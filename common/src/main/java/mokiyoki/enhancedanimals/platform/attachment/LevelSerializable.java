package mokiyoki.enhancedanimals.platform.attachment;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.Tag;

public interface LevelSerializable {

    Tag serializeNBT(HolderLookup.Provider provider);

    void deserializeNBT(HolderLookup.Provider provider, Tag nbt);
}
