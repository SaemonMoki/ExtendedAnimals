package mokiyoki.enhancedanimals.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class RawChicken extends ItemFood {

    public RawChicken(Properties props, int amount, float saturation, boolean isMeat) {
        super(amount, saturation, isMeat, props);
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
        if(!worldIn.isRemote) {
            player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 30*20, 5, false, true));
        }
    }

}
