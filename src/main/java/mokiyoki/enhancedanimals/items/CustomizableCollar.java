package mokiyoki.enhancedanimals.items;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;

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
        CompoundNBT compoundnbt = stack.getChildTag("display");
        if (compoundnbt != null && compoundnbt.contains("Name", 8)) {
            return ITextComponent.Serializer.fromJson(compoundnbt.getString("Name")).getString();
        }
        return "";
    }

}
