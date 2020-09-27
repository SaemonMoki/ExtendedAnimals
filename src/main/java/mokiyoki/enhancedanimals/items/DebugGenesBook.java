package mokiyoki.enhancedanimals.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;


public class DebugGenesBook extends Item {

    String genes;

    public DebugGenesBook(Item.Properties builder) {
        super(builder);
    }

    /**
     * Returns true if the item can be used on the given entity, e.g. shears on sheep.
     */
    public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        return ActionResultType.SUCCESS;
    }
}
