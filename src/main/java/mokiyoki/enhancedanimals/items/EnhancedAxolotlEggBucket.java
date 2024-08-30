package mokiyoki.enhancedanimals.items;

import mokiyoki.enhancedanimals.config.GeneticAnimalsConfig;
import mokiyoki.enhancedanimals.entity.EnhancedAxolotlEgg;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_AXOLOTL_EGG;

public class EnhancedAxolotlEggBucket extends MobBucketItem {
    public EnhancedAxolotlEggBucket(Supplier<? extends EntityType<?>> entitySupplier, Supplier<? extends Fluid> fluidSupplier, Supplier<? extends SoundEvent> soundSupplier, Properties properties) {
        super(entitySupplier, fluidSupplier, soundSupplier, properties);
    }

    public static void setData(ItemStack stack, String sireName, String damName, String genes, int hatchTime) {
        CompoundTag data = stack.getOrCreateTagElement("display");
        data.putString("SireName", sireName);
        data.putString("DamName", damName);
        data.putInt("HatchTime", hatchTime);
        data.putString("Genetics", genes);
    }

    @Override
    public void checkExtraContent(@javax.annotation.Nullable Player player, Level level, ItemStack stack, BlockPos pos) {
        if (level instanceof ServerLevel) {
            this.spawnAxolotlEgg((ServerLevel)level, stack, pos);
            level.gameEvent(player, GameEvent.ENTITY_PLACE, pos);
        }
    }

    private void spawnAxolotlEgg(ServerLevel level, ItemStack stack, BlockPos pos) {
        EnhancedAxolotlEgg axolotlEgg = ENHANCED_AXOLOTL_EGG.get().create(level);
        CompoundTag data = stack.getOrCreateTagElement("display");
        axolotlEgg.setParentNames(data.getString("SireName"), data.getString("DamName"));
        axolotlEgg.setGenes(data.getString("Genetics"));
        if (stack.hasCustomHoverName()) {
            axolotlEgg.setCustomName(stack.getHoverName());
        }
        axolotlEgg.setHatchTime(data.contains("HatchTime") ? data.getInt("HatchTime") : GeneticAnimalsConfig.COMMON.axolotlHatchTime.get());
        axolotlEgg.moveTo((double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, 0.0F, 0.0F);
        level.addFreshEntity(axolotlEgg);
    }

    @Override
    public void appendHoverText(ItemStack p_151155_, @Nullable Level p_151156_, List<Component> p_151157_, TooltipFlag p_151158_) {}

}
