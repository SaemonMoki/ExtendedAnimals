package mokiyoki.enhancedanimals.items;

import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
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
        CompoundTag compoundnbt = stack.getTagElement("display");
        if (compoundnbt != null && compoundnbt.contains("Name", 8)) {
            return Component.Serializer.fromJson(compoundnbt.getString("Name")).getString();
        }
        return "";
    }

}
