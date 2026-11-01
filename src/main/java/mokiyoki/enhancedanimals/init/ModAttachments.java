package mokiyoki.enhancedanimals.init;

import mokiyoki.enhancedanimals.capability.hay.HayCapabilityProvider;
import mokiyoki.enhancedanimals.capability.nestegg.NestCapabilityProvider;
import mokiyoki.enhancedanimals.capability.post.PostCapabilityProvider;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import static mokiyoki.enhancedanimals.util.Reference.MODID;

public class ModAttachments {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES_DEFERRED_REGISTRY = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, MODID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<PostCapabilityProvider>> POST_CAP = ATTACHMENT_TYPES_DEFERRED_REGISTRY.register("postcap", () -> AttachmentType.serializable(PostCapabilityProvider::new).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<HayCapabilityProvider>> HAY_CAP = ATTACHMENT_TYPES_DEFERRED_REGISTRY.register("haycap", () -> AttachmentType.serializable(HayCapabilityProvider::new).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<NestCapabilityProvider>> NEST_CAP = ATTACHMENT_TYPES_DEFERRED_REGISTRY.register("nestcap", () -> AttachmentType.serializable(NestCapabilityProvider::new).build());

    public static void register(IEventBus modEventBus) {
        ATTACHMENT_TYPES_DEFERRED_REGISTRY.register(modEventBus);
    }
}
