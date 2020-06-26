package mokiyoki.enhancedanimals.items;

import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class CustomizableAnimalEquipment extends Item implements IDyeableArmorItem {

    private int defaultColour = 16777215;

    public CustomizableAnimalEquipment(Properties builder, int originalColour) {
        super(builder);
        setDefaultColour(originalColour);
    }

    @Override
    public int getColor(ItemStack stack) {
        CompoundNBT compoundnbt = stack.getChildTag("display");
        return compoundnbt != null && compoundnbt.contains("color", 99) ? compoundnbt.getInt("color") : this.defaultColour;
    }

    private void setDefaultColour(int originalColour) {
        this.defaultColour = originalColour;
    }

}
