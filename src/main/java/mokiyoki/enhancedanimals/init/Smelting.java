package mokiyoki.enhancedanimals.init;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.stats.RecipeBook;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Smelting {

    public static void init() {
        GameRegistry.addSmelting(ModItems.RawChickenDarkSmall, new ItemStack(ModItems.CookedChickenDarkSmall, 1), 0.35f);
        GameRegistry.addSmelting(ModItems.RawChickenDark, new ItemStack(ModItems.CookedChickenDark, 1), 0.35f);
        GameRegistry.addSmelting(ModItems.RawChickenDarkBig, new ItemStack(ModItems.CookedChickenDarkBig, 1), 0.35f);
        GameRegistry.addSmelting(ModItems.RawChickenPaleSmall, new ItemStack(ModItems.CookedChickenPaleSmall, 1), 0.35f);
        GameRegistry.addSmelting(ModItems.RawChickenPaleBig, new ItemStack(ModItems.CookedChickenPaleBig, 1), 0.35f);
    }
}
