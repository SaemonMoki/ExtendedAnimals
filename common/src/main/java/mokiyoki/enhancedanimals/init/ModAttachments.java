package mokiyoki.enhancedanimals.init;

import mokiyoki.enhancedanimals.capability.hay.HayCapabilityProvider;
import mokiyoki.enhancedanimals.capability.nestegg.NestCapabilityProvider;
import mokiyoki.enhancedanimals.capability.post.PostCapabilityProvider;
import mokiyoki.enhancedanimals.platform.Services;
import mokiyoki.enhancedanimals.platform.attachment.LevelAttachment;

public class ModAttachments {

    public static final LevelAttachment<PostCapabilityProvider> POST_CAP = Services.PLATFORM.createLevelAttachment("postcap", PostCapabilityProvider::new);
    public static final LevelAttachment<HayCapabilityProvider> HAY_CAP = Services.PLATFORM.createLevelAttachment("haycap", HayCapabilityProvider::new);
    public static final LevelAttachment<NestCapabilityProvider> NEST_CAP = Services.PLATFORM.createLevelAttachment("nestcap", NestCapabilityProvider::new);

    public static void init() {
    }
}
