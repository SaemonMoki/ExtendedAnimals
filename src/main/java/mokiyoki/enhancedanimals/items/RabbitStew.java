package mokiyoki.enhancedanimals.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RabbitStew extends ItemFood {

    public RabbitStew(Properties props, int amount, float saturation, boolean isMeat) {
        super(amount, saturation, isMeat, props);
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {

    }

}