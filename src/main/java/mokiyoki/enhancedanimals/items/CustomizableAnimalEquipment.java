package mokiyoki.enhancedanimals.items;

import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class CustomizableAnimalEquipment extends Item implements IDyeableArmorItem {
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

    @Override
    public int getColor(ItemStack stack) {
        CompoundNBT compoundnbt = stack.getChildTag("display");
        return compoundnbt != null && compoundnbt.contains("color", 99) ? compoundnbt.getInt("color") : getDefaultColour();
    }

}
