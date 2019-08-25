package mokiyoki.enhancedanimals.items;

import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;


public class DebugGenesBook extends Item {

    String genes;

    public DebugGenesBook(Item.Properties builder) {
        super(builder);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if(genes!= null) {
            if (worldIn.isRemote) {
                ((ClientPlayerEntity)playerIn).sendChatMessage(genes);
            }
        }
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }

    /**
     * Returns true if the item can be used on the given entity, e.g. shears on sheep.
     */
    public boolean itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        return true;
    }

    public void displayGenes(String sharedGenes) {
        this.genes = sharedGenes;
    }
}
