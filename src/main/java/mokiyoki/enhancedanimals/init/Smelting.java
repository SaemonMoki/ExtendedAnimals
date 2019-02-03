package mokiyoki.enhancedanimals.init;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Smelting {

    public static void init() {
        GameRegistry.addSmelting(ModItems.RawChickenDark, new ItemStack(ModItems.CookedChickenDark, 1), 0.35f);
    }
}
