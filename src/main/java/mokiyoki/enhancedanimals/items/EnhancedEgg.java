package mokiyoki.enhancedanimals.items;

import mokiyoki.enhancedanimals.capability.egg.EggCapabilityProvider;
import mokiyoki.enhancedanimals.entity.EnhancedEntityEgg;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

/**
 * Created by moki on 24/08/2018.
 */
public class EnhancedEgg extends Item {

    private int[] arrayOfDifferences;

    public EnhancedEgg(Properties properties) {
        super(properties);
    }

    public void setDifference(int[] arrayOfDifferences){
        this.arrayOfDifferences = arrayOfDifferences;
    }



    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn){
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (!playerIn.abilities.isCreativeMode) {
            itemstack.shrink(1);
        }

        worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_EGG_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));

        if (!worldIn.isRemote) {
            int[] eggGenes = itemstack.getCapability(EggCapabilityProvider.EGG_CAP, null).orElse(null).getGenes();
            EnhancedEntityEgg entityegg;
            if (eggGenes != null) {
                entityegg = new EnhancedEntityEgg(worldIn, playerIn, eggGenes);
            } else {
                entityegg = new EnhancedEntityEgg(worldIn, playerIn, null);
            }

            entityegg.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
            worldIn.spawnEntity(entityegg);
        }

        playerIn.addStat(StatList.ITEM_USED.get(this));
        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
    }


    @Override
    public boolean getShareTag()
    {
        return false;
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        EggCapabilityProvider provider = new EggCapabilityProvider();

        return provider;
    }
}
