package mokiyoki.enhancedanimals.entity;

import net.minecraft.inventory.Inventory;

public interface EnhancedAnimal {

    int getHunger();

    void decreaseHunger(int decreaseAmount);

    Boolean isAnimalSleeping();

    void awaken();

    Inventory getEnhancedInventory();

}
