package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.util.EnhancedAnimalInfo;
import net.minecraft.inventory.Inventory;

public interface EnhancedAnimal {

    float getHunger();

    void decreaseHunger(float decreaseAmount);

    Boolean isAnimalSleeping();

    void awaken();

    Inventory getEnhancedInventory();

    EnhancedAnimalInfo getAnimalInfo();

}
