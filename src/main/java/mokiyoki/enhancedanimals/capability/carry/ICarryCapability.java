package mokiyoki.enhancedanimals.capability.carry;

import net.minecraft.world.item.ItemStack;

public interface ICarryCapability {

    ItemStack getCarried();

    void setCarried(ItemStack stack);

    boolean isCarrying();
}
