package mokiyoki.enhancedanimals.platform.client;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public interface IClientPlatformHelper {

    ScreenshotResult onScreenshot(NativeImage image, File screenshotFile);

    record ScreenshotResult(File screenshotFile, @Nullable Component cancelMessage, @Nullable Component resultMessage) {
        public boolean isCancelled() {
            return this.cancelMessage != null;
        }
    }
}
