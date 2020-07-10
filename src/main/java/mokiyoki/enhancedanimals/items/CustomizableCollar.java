package mokiyoki.enhancedanimals.items;

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

}
