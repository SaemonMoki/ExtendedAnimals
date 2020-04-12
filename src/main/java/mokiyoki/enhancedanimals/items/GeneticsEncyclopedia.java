package mokiyoki.enhancedanimals.items;

import mokiyoki.enhancedanimals.EnhancedAnimals;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.WritableBookItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class GeneticsEncyclopedia extends Item {

    public String testString;

    public GeneticsEncyclopedia(Item.Properties builder) {
        super(builder);
    }

    public static boolean validBookTagContents(@Nullable CompoundNBT nbt) {
        if (!WritableBookItem.isNBTValid(nbt)) {
            return false;
        } else if (!nbt.contains("title", 8)) {
            return false;
        } else {
            String s = nbt.getString("title");
            return s.length() > 32 ? false : nbt.contains("author", 8);
        }
    }

    public ITextComponent getDisplayName(ItemStack stack) {
        if (stack.hasTag()) {
            CompoundNBT compoundnbt = stack.getTag();
            String s = compoundnbt.getString("title");
            if (!StringUtils.isNullOrEmpty(s)) {
                return new StringTextComponent(s);
            }
        }

        return super.getDisplayName(stack);
    }

    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (stack.hasTag()) {
            CompoundNBT compoundnbt = stack.getTag();
            String s = compoundnbt.getString("author");
            if (!StringUtils.isNullOrEmpty(s)) {
                tooltip.add((new TranslationTextComponent("book.byAuthor", s)).applyTextStyle(TextFormatting.GRAY));
            }
        }

    }

    /**
     * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
     * {@link #onItemUse}.
     */
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        testString = "SUCCESS";

        EnhancedAnimals.proxy.setEncylopediaInfo(itemstack);

        if(worldIn.isRemote) {
            EnhancedAnimals.proxy.openEncyclodepia();
        }

        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }

    public static boolean resolveContents(ItemStack stack, @Nullable CommandSource resolvingSource, @Nullable PlayerEntity resolvingPlayer) {
        CompoundNBT compoundnbt = stack.getTag();
        if (compoundnbt != null && !compoundnbt.getBoolean("resolved")) {
            compoundnbt.putBoolean("resolved", true);
            if (!validBookTagContents(compoundnbt)) {
                return false;
            } else {
                ListNBT listnbt = compoundnbt.getList("pages", 8);

                for(int i = 0; i < listnbt.size(); ++i) {
                    String s = listnbt.getString(i);

                    ITextComponent itextcomponent;
                    try {
                        itextcomponent = ITextComponent.Serializer.fromJsonLenient(s);
                        itextcomponent = TextComponentUtils.updateForEntity(resolvingSource, itextcomponent, resolvingPlayer, 0);
                    } catch (Exception var9) {
                        itextcomponent = new StringTextComponent(s);
                    }

                    listnbt.set(i, (StringNBT.valueOf(ITextComponent.Serializer.toJson(itextcomponent))));
                }

                compoundnbt.put("pages", listnbt);
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundNBT nbt) {

    }
}
