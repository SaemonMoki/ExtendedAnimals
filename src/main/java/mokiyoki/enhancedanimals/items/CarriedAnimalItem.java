package mokiyoki.enhancedanimals.items;

import net.minecraft.world.item.Item;

//pure NBT vessel for a carried animal it doesn't actually exist in a slot(to prevent other mods using it like an item)
public class CarriedAnimalItem extends Item {

    public CarriedAnimalItem(Properties properties) {
        super(properties);
    }
}
