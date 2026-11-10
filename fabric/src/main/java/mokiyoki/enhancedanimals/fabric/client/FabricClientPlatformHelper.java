package mokiyoki.enhancedanimals.fabric.client;

import com.mojang.blaze3d.platform.NativeImage;
import mokiyoki.enhancedanimals.platform.client.IClientPlatformHelper;

import java.io.File;

public class FabricClientPlatformHelper implements IClientPlatformHelper {

    @Override
    public ScreenshotResult onScreenshot(NativeImage image, File screenshotFile) {
        // Fabric has no screenshot event.
        return new ScreenshotResult(screenshotFile, null, null);
    }
}
