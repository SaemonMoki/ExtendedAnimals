package mokiyoki.enhancedanimals.items;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;

public class CustomizableAnimalEquipment extends Item {
    private int defaultColour;

    public CustomizableAnimalEquipment(Properties builder, int originalColour) {
        super(builder);
        setDefaultColour(originalColour);
    }

    private void setDefaultColour(int originalColour) {
        this.defaultColour = originalColour;
    }

    public int getDefaultColour() {
        return this.defaultColour;
    }

    public boolean hasCustomColor(ItemStack stack) {
        return stack.has(DataComponents.DYED_COLOR);
    }

    public int getColor(ItemStack stack) {
        DyedItemColor dyedItemColor = stack.get(DataComponents.DYED_COLOR);
        return dyedItemColor != null ? dyedItemColor.rgb() : getDefaultColour();
    }

    public void clearColor(ItemStack stack) {
        stack.remove(DataComponents.DYED_COLOR);
    }

    public void setColor(ItemStack stack, int colour) {
        stack.set(DataComponents.DYED_COLOR, new DyedItemColor(colour, true));
    }

}
