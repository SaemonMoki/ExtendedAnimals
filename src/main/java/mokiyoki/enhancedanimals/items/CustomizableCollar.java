package mokiyoki.enhancedanimals.items;

import net.minecraft.world.item.ItemStack;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;

import net.minecraft.world.item.Item.Properties;

public class CustomizableCollar extends CustomizableAnimalEquipment{
    private boolean isBellCollar = false;
    public CustomizableCollar(Properties builder, int originalColour, boolean hasBells) {
        super(builder, originalColour);
        setHasBells(hasBells);
    }

    private void setHasBells(boolean hasBells) {
        this.isBellCollar = hasBells;
    }

    public boolean getHasBells() {
        return this.isBellCollar;
    }

    public String getCollarName(ItemStack stack) {
        Component customName = stack.get(DataComponents.CUSTOM_NAME);
        if (customName != null) {
            return customName.getString();
        }
        return "";
    }

}
