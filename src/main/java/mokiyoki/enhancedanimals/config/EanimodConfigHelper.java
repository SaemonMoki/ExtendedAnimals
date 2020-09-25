package mokiyoki.enhancedanimals.config;

import mokiyoki.enhancedanimals.util.Reference;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class EanimodConfigHelper {
    public static final Path CONFIG_DIR;

    static {
        CONFIG_DIR = FMLPaths.getOrCreateGameRelativePath(FMLPaths.CONFIGDIR.get(), Reference.MODID);
    }

    /**
     * Creates a mod config so that {@link net.minecraftforge.fml.config.ConfigTracker} will track it and sync server configs from server to client.
     */
    public static void registerConfig(ModContainer modContainer, IEanimodConfig config) {
        EanimodConfig modConfig = new EanimodConfig(modContainer, config);
        if (config.addToContainer()) {
            modContainer.addConfig(modConfig);
        }
    }
}
