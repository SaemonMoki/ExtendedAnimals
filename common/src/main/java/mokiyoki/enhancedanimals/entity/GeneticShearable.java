package mokiyoki.enhancedanimals.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface GeneticShearable {

    boolean isShearable(@Nullable Player player, ItemStack item, Level level, BlockPos pos);

    List<ItemStack> onSheared(@Nullable Player player, ItemStack item, Level level, BlockPos pos);

    static InteractionResult shearWithItem(ItemStack stack, @Nullable Player player, LivingEntity entity, InteractionHand hand) {
        if (!(entity instanceof GeneticShearable target)) {
            return InteractionResult.PASS;
        }
        Level level = entity.level();
        BlockPos pos = entity.blockPosition();
        if (!target.isShearable(player, stack, level, pos)) {
            return InteractionResult.PASS;
        }
        List<ItemStack> drops = target.onSheared(player, stack, level, pos);
        if (!level.isClientSide) {
            drops.forEach(drop -> spawnShearedDrop(entity, drop));
        }
        entity.gameEvent(GameEvent.SHEAR, player);
        if (!level.isClientSide && player != null) {
            stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    static void spawnShearedDrop(LivingEntity entity, ItemStack drop) {
        ItemEntity itemEntity = entity.spawnAtLocation(drop, 1.0F);
        if (itemEntity != null) {
            RandomSource rand = entity.getRandom();
            itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().add(
                    (rand.nextFloat() - rand.nextFloat()) * 0.1F,
                    rand.nextFloat() * 0.05F,
                    (rand.nextFloat() - rand.nextFloat()) * 0.1F));
        }
    }
}
