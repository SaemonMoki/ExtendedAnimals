package mokiyoki.enhancedanimals.items;

import mokiyoki.enhancedanimals.capability.egg.EggCapabilityProvider;
import mokiyoki.enhancedanimals.capability.turtleegg.EggHolder;
import mokiyoki.enhancedanimals.entity.EnhancedEntityEgg;
import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import net.minecraft.world.item.Item.Properties;

/**
 * Created by moki on 24/08/2018.
 */
public class EnhancedEgg extends Item {

    public EnhancedEgg(Properties properties) { super(properties); }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn){
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (!playerIn.abilities.instabuild) {
            itemstack.shrink(1);
        }

        worldIn.playSound((Player)null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.EGG_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));

        if (!worldIn.isClientSide) {
            EggHolder egg = itemstack.getCapability(EggCapabilityProvider.EGG_CAP, null).orElse(null).getEggHolder(itemstack);
            Genes eggGenes = egg.getGenes();
            String sireName = egg.getSire();
            String damName = egg.getDam();
            EnhancedEntityEgg entityegg;
            if (eggGenes != null) {
                entityegg = new EnhancedEntityEgg(worldIn, playerIn, eggGenes, sireName, damName, this.getItem(), this.getHasParents(itemstack));
            } else {
                entityegg = new EnhancedEntityEgg(worldIn, playerIn, null, null, null, this.getItem(), this.getHasParents(itemstack));
            }

            entityegg.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, 0.0F, 1.5F, 1.0F);
            worldIn.addFreshEntity(entityegg);
        }

        playerIn.awardStat(Stats.ITEM_USED.get(this));
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
        EggCapabilityProvider provider = new EggCapabilityProvider();

        return provider;
    }

    public void setHasParents(ItemStack stack, boolean hasParents) {
        stack.getOrCreateTagElement("display").putBoolean("hasParents", hasParents);
    }

    public boolean getHasParents(ItemStack stack) {
        CompoundTag compoundnbt = stack.getTagElement("display");
        return compoundnbt != null && compoundnbt.contains("hasParents", 99) && compoundnbt.getBoolean("hasParents");
    }
}
