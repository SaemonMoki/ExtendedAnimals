package mokiyoki.enhancedanimals.capability.turtlescute;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;

public class stackProvider implements IStackableCapability {

    public static Capability<IStackableCapability> STACK_CAP = CapabilityManager.get(new CapabilityToken<>() {});

    private final LazyOptional<IStackableCapability> holder = LazyOptional.of(() -> this);

    @Override
    public void createStackableData(ItemStack stack, int basecolour) {

    }
}
