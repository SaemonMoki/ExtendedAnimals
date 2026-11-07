package mokiyoki.enhancedanimals.fabric.platform;

import mokiyoki.enhancedanimals.platform.attachment.LevelAttachment;
import mokiyoki.enhancedanimals.platform.attachment.LevelSerializable;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Supplier;

class SavedDataLevelAttachment<T extends LevelSerializable> implements LevelAttachment<T> {

    private final String fileName;
    private final SavedData.Factory<Holder<T>> savedDataFactory;
    private final Supplier<T> factory;
    private final Map<Level, T> clientData = new WeakHashMap<>();

    SavedDataLevelAttachment(String name, Supplier<T> factory) {
        this.fileName = Reference.MODID + "_" + name;
        this.factory = factory;
        this.savedDataFactory = new SavedData.Factory<>(
                () -> new Holder<>(factory.get()),
                (tag, provider) -> {
                    T data = factory.get();
                    Tag saved = tag.get("data");
                    if (saved != null) {
                        data.deserializeNBT(provider, saved);
                    }
                    return new Holder<>(data);
                },
                null);
    }

    @Override
    public T get(Level level) {
        if (level instanceof ServerLevel serverLevel) {
            return serverLevel.getDataStorage().computeIfAbsent(this.savedDataFactory, this.fileName).data;
        }
        synchronized (this.clientData) {
            return this.clientData.computeIfAbsent(level, ignored -> this.factory.get());
        }
    }

    private static final class Holder<T extends LevelSerializable> extends SavedData {
        private final T data;

        private Holder(T data) {
            this.data = data;
        }

        @Override
        public CompoundTag save(CompoundTag tag, HolderLookup.Provider provider) {
            tag.put("data", this.data.serializeNBT(provider));
            return tag;
        }

        // Callers change the data directly, so always save, like NeoForge attachments.
        @Override
        public boolean isDirty() {
            return true;
        }
    }
}
