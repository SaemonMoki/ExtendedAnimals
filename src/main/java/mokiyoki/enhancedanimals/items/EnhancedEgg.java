package mokiyoki.enhancedanimals.items;

import mokiyoki.enhancedanimals.capability.egg.EggCapabilityProvider;
import mokiyoki.enhancedanimals.capability.nestegg.EggHolder;
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

/**
 * Created by moki on 24/08/2018.
 */
public class EnhancedEgg extends Item {

    private final int colour;

    public EnhancedEgg(Properties properties, int colour) {
        super(properties);
        this.colour = colour;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand){
        ItemStack itemstack = player.getItemInHand(hand);
        level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.EGG_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));

        if (!level.isClientSide) {
            EggHolder egg = itemstack.getCapability(EggCapabilityProvider.EGG_CAP, null).orElse(null).getEggHolder(itemstack);
            Genes eggGenes = egg.getGenes();
            String sireName = egg.getSire();
            String damName = egg.getDam();
            EnhancedEntityEgg entityegg;
            if (eggGenes != null) {
                entityegg = new EnhancedEntityEgg(level, player, eggGenes, sireName, damName, this.asItem(), this.getHasParents(itemstack));
            } else {
                entityegg = new EnhancedEntityEgg(level, player, null, null, null, this.asItem(), this.getHasParents(itemstack));
            }

            entityegg.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
            level.addFreshEntity(entityegg);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.getAbilities().instabuild) {
            itemstack.shrink(1);
        }

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

    public int getColour() {
        return this.colour;
    }
}
