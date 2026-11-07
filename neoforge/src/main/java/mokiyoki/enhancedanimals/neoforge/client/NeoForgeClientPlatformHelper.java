package mokiyoki.enhancedanimals.neoforge.client;

import com.mojang.blaze3d.platform.NativeImage;
import mokiyoki.enhancedanimals.platform.client.IClientPlatformHelper;
import net.neoforged.neoforge.client.ClientHooks;
import net.neoforged.neoforge.client.event.ScreenshotEvent;

import java.io.File;

public class NeoForgeClientPlatformHelper implements IClientPlatformHelper {

    @Override
    public ScreenshotResult onScreenshot(NativeImage image, File screenshotFile) {
        ScreenshotEvent event = ClientHooks.onScreenshot(image, screenshotFile);
        return new ScreenshotResult(event.getScreenshotFile(), event.isCanceled() ? event.getCancelMessage() : null, event.getResultMessage());
    }
}
