package mokiyoki.enhancedanimals.items;

import mokiyoki.enhancedanimals.capability.egg.EggCapabilityProvider;
import mokiyoki.enhancedanimals.entity.EnhancedEntityEgg;
import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

/**
 * Created by moki on 24/08/2018.
 */
public class EnhancedEgg extends Item {

    private int[] arrayOfDifferences;

    public EnhancedEgg(Properties properties) { super(properties); }

    public void setDifference(int[] arrayOfDifferences){
        this.arrayOfDifferences = arrayOfDifferences;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn){
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (!playerIn.abilities.isCreativeMode) {
            itemstack.shrink(1);
        }

        worldIn.playSound((PlayerEntity)null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.ENTITY_EGG_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));

        if (!worldIn.isRemote) {
            Genes eggGenes = itemstack.getCapability(EggCapabilityProvider.EGG_CAP, null).orElse(null).getGenes();
            EnhancedEntityEgg entityegg;
            if (eggGenes != null) {
                entityegg = new EnhancedEntityEgg(worldIn, playerIn, eggGenes);
            } else {
                entityegg = new EnhancedEntityEgg(worldIn, playerIn, null);
            }

            entityegg.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
            worldIn.addEntity(entityegg);
        }

        playerIn.addStat(Stats.ITEM_USED.get(this));
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
        EggCapabilityProvider provider = new EggCapabilityProvider();

        return provider;
    }
}
