package mokiyoki.enhancedanimals.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;

public class EnhancedSlot extends Slot {

    boolean enabled = true;

    public EnhancedSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
