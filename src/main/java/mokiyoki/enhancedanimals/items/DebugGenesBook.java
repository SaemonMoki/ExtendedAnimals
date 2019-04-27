package mokiyoki.enhancedanimals.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;


public class DebugGenesBook extends Item {

    String genes;

    public DebugGenesBook(Item.Properties builder) {
        super(builder);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if(genes!= null) {
            if (worldIn.isRemote) {
                playerIn.sendMessage(new TextComponentString(genes));
            }
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
    }

    /**
     * Returns true if the item can be used on the given entity, e.g. shears on sheep.
     */
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        return true;
    }

    public void displayGenes(String sharedGenes) {
        this.genes = sharedGenes;
    }
}
