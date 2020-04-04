package mokiyoki.enhancedanimals.items;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.stats.Stats;
import net.minecraft.world.World;

import java.util.concurrent.ThreadLocalRandom;

public class MilkBottle extends MilkBucketItem {

    public MilkBottle(Item.Properties props) {
        super(props);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        int i = ThreadLocalRandom.current().nextInt(3);
        if (!worldIn.isRemote && i == 0) entityLiving.curePotionEffects(stack); // FORGE - move up so stack.shrink does not turn stack into air

        if (entityLiving instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverplayerentity = (ServerPlayerEntity)entityLiving;
            CriteriaTriggers.CONSUME_ITEM.trigger(serverplayerentity, stack);
            serverplayerentity.addStat(Stats.ITEM_USED.get(this));
        }

        if (entityLiving instanceof PlayerEntity && !((PlayerEntity)entityLiving).abilities.isCreativeMode) {
            stack.shrink(1);
        }

        if (i == 0) {
            if (!worldIn.isRemote) {
                entityLiving.clearActivePotions();
            }
        }

        return stack.isEmpty() ? new ItemStack(Items.GLASS_BOTTLE) : stack;
    }

}