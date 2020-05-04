package mokiyoki.enhancedanimals.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.config.ConfigFileTypeHandler;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;
import java.util.function.Function;

public class EanimodConfig extends ModConfig {

    private static final EnhancedConfigFileTypeHandler EANIMOD_TOML = new EnhancedConfigFileTypeHandler();

    public EanimodConfig(ModContainer container, IEanimodConfig config) {
        super(config.getConfigType(), config.getConfigSpec(), container, config.getFileName() + ".toml");
    }

    @Override
    public ConfigFileTypeHandler getHandler() {
        return EANIMOD_TOML;
    }

    private static class EnhancedConfigFileTypeHandler extends ConfigFileTypeHandler {

        @Override
        public Function<ModConfig, CommentedFileConfig> reader(Path configBasePath) {
            //Intercept server config path reading for configs and reroute it to the normal config directory
            if (configBasePath.endsWith("serverconfig")) {
                return super.reader(FMLPaths.CONFIGDIR.get());
            }
            return super.reader(configBasePath);
        }
    }

}
