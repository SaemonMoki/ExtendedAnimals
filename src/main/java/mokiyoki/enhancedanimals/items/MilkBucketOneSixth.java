package mokiyoki.enhancedanimals.items;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MilkBucketItem;
import net.minecraft.stats.Stats;
import net.minecraft.world.level.Level;

import java.util.concurrent.ThreadLocalRandom;

public class MilkBucketOneSixth extends MilkBucketItem {

    public MilkBucketOneSixth(Item.Properties props) {
        super(props);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
        int i = ThreadLocalRandom.current().nextInt(6);
        if (!worldIn.isClientSide && i == 0) entityLiving.curePotionEffects(stack); // FORGE - move up so stack.shrink does not turn stack into air

        if (entityLiving instanceof ServerPlayer) {
            ServerPlayer serverplayerentity = (ServerPlayer)entityLiving;
            CriteriaTriggers.CONSUME_ITEM.trigger(serverplayerentity, stack);
            serverplayerentity.awardStat(Stats.ITEM_USED.get(this));
        }

        if (entityLiving instanceof Player && !((Player)entityLiving).abilities.instabuild) {
            stack.shrink(1);
        }

        if (i == 0) {
            if (!worldIn.isClientSide) {
                entityLiving.removeAllEffects();
            }
        }

        return stack.isEmpty() ? new ItemStack(Items.BUCKET) : stack;
    }

}
