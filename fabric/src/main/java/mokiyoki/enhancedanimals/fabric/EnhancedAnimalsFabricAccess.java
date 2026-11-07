package mokiyoki.enhancedanimals.fabric;

import mokiyoki.enhancedanimals.fabric.platform.FabricNetworkHelper;

public final class EnhancedAnimalsFabricAccess {

    private EnhancedAnimalsFabricAccess() {
    }

    public static FabricNetworkHelper.Registrar packets() {
        return EnhancedAnimalsFabric.PACKETS;
    }
}
