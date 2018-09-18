package mokiyoki.enhancedanimals.init;

import mokiyoki.enhancedanimals.items.EnhancedEgg;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

/**
 * Created by moki on 24/08/2018.
 */
@ObjectHolder(Reference.MODID)
public class ModItems {
    public static final Item EggWhite = new EnhancedEgg("eggWhite", "egg_white");
    public static final Item EggCream = new EnhancedEgg ("eggCream", "egg_cream");
    public static final Item EggCreamDark = new EnhancedEgg ("eggCreamDark", "egg_creamdark");
    public static final Item EggPink = new EnhancedEgg ("eggPink", "egg_pink");
    public static final Item EggPinkDark = new EnhancedEgg ("eggPinkDark", "egg_pinkdark");
    public static final Item EggBrown = new EnhancedEgg ("eggBrown", "egg_brown");
    public static final Item EggBrownDark = new EnhancedEgg ("eggBrownDark", "egg_browndark");
    public static final Item EggBlue = new EnhancedEgg ("eggBlue", "egg_blue");
    public static final Item EggGreenLight = new EnhancedEgg ("eggGreenLight", "egg_greenlight");
    public static final Item EggGreen = new EnhancedEgg ("eggGreen", "egg_green");
    public static final Item EggGrey = new EnhancedEgg ("eggGrey", "egg_grey");
    public static final Item EggGreyGreen = new EnhancedEgg ("eggGreyGreen", "egg_greygreen");
    public static final Item EggOlive = new EnhancedEgg ("eggOlive", "egg_olive");
    public static final Item EggGreenDark = new EnhancedEgg ("eggGreenDark", "egg_greendark");
}

//CHICKEN_COOKED_MELANIZED = null;
//CHICKEN_RAW_MELANIZED = null;