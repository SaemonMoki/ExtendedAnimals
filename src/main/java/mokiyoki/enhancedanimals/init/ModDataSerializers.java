package mokiyoki.enhancedanimals.init;

import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DataSerializerEntry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModDataSerializers {

    public static final DeferredRegister<DataSerializerEntry> DATA_SERIALIZER_DEFERRED_REGISTRY = DeferredRegister.create(ForgeRegistries.Keys.DATA_SERIALIZERS, Reference.MODID);

    /**
     * Vanilla has no long serializer in 1.18.2, but game time based values such as birth times
     * outgrow an int once a world has been running for a while.
     */
    public static final EntityDataSerializer<Long> LONG = new EntityDataSerializer<Long>() {
        @Override
        public void write(FriendlyByteBuf buffer, Long value) {
            buffer.writeLong(value);
        }

        @Override
        public Long read(FriendlyByteBuf buffer) {
            return buffer.readLong();
        }

        @Override
        public Long copy(Long value) {
            return value;
        }
    };

    public static final RegistryObject<DataSerializerEntry> LONG_SERIALIZER = DATA_SERIALIZER_DEFERRED_REGISTRY.register("long", () -> new DataSerializerEntry(LONG));

    public static void register(IEventBus modEventBus) {
        DATA_SERIALIZER_DEFERRED_REGISTRY.register(modEventBus);
    }
}
