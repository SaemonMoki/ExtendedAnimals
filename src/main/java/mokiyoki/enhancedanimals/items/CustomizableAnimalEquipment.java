package mokiyoki.enhancedanimals.items;

import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

public class CustomizableAnimalEquipment extends Item implements DyeableLeatherItem {
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
        CompoundTag compoundnbt = stack.getTagElement("display");
        return compoundnbt != null && compoundnbt.contains("color", 99) ? compoundnbt.getInt("color") : getDefaultColour();
    }

}
