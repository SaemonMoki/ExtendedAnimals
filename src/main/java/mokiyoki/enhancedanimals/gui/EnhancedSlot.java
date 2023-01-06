package mokiyoki.enhancedanimals.gui;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class EnhancedSlot extends Slot {

    boolean enabled = true;

    public EnhancedSlot(Container inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean isActive() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public ItemStack getItem() {
        if(this.enabled) {
            return super.getItem();
        }
        return ItemStack.EMPTY;
    }
}
