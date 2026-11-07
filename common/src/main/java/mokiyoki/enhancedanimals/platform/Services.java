package mokiyoki.enhancedanimals.platform;

import mokiyoki.enhancedanimals.platform.client.IClientPlatformHelper;
import mokiyoki.enhancedanimals.platform.network.INetworkHelper;
import mokiyoki.enhancedanimals.platform.registry.RegistrationProvider;

import java.util.ServiceLoader;

public final class Services {

    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);
    public static final INetworkHelper NETWORK = load(INetworkHelper.class);
    public static final RegistrationProvider.Factory REGISTRATION = load(RegistrationProvider.Factory.class);

    private Services() {
    }

    public static IClientPlatformHelper client() {
        return ClientHolder.CLIENT;
    }

    public static <T> T load(Class<T> clazz) {
        return ServiceLoader.load(clazz, Services.class.getClassLoader())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No platform implementation found for " + clazz.getName()));
    }

    private static final class ClientHolder {
        private static final IClientPlatformHelper CLIENT = load(IClientPlatformHelper.class);
    }
}
