package mokiyoki.enhancedanimals.items;

import mokiyoki.enhancedanimals.util.CustomDataHelper;
import net.minecraft.core.HolderLookup;
import mokiyoki.enhancedanimals.EnhancedAnimals;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.util.StringUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class GeneticsEncyclopedia extends Item {

    public String testString;

    public GeneticsEncyclopedia(Item.Properties builder) {
        super(builder);
    }

    public static boolean validBookTagContents(@Nullable CompoundTag nbt) {
        if (!makeSureTagIsValid(nbt)) {
            return false;
        } else if (!nbt.contains("title", 8)) {
            return false;
        } else {
            String s = nbt.getString("title");
            return s.length() > 32 ? false : nbt.contains("author", 8);
        }
    }

    private static boolean makeSureTagIsValid(@Nullable CompoundTag nbt) {
        if (nbt == null) {
            return false;
        } else if (!nbt.contains("pages", 9)) {
            return false;
        } else {
            ListTag listnbt = nbt.getList("pages", 8);
            for (int i = 0; i < listnbt.size(); ++i) {
                String s = listnbt.getString(i);
                if (s.length() > 32767) {
                    return false;
                }
            }
            return true;
        }
    }

    public Component getName(ItemStack stack) {
        if (CustomDataHelper.hasTag(stack)) {
            CompoundTag compoundnbt = CustomDataHelper.getTag(stack);
            String s = compoundnbt.getString("title");
            if (!StringUtil.isNullOrEmpty(s)) {
                return Component.literal(s);
            }
        }

        return super.getName(stack);
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Item.TooltipContext worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        if (CustomDataHelper.hasTag(stack)) {
            CompoundTag compoundnbt = CustomDataHelper.getTag(stack);
            String s = compoundnbt.getString("author");
            if (!StringUtil.isNullOrEmpty(s)) {
                tooltip.add((Component.translatable("book.byAuthor", s)).withStyle(ChatFormatting.GRAY));
            }
        }

    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);

        testString = "SUCCESS";

//        EnhancedAnimals.proxy.setEncylopediaInfo(itemstack);

        if(worldIn.isClientSide) {
//            EnhancedAnimals.proxy.openEncyclodepia();
        }

        return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
    }

    public static boolean resolveContents(ItemStack stack, @Nullable CommandSourceStack resolvingSource, @Nullable Player resolvingPlayer, HolderLookup.Provider provider) {
        CompoundTag compoundnbt = CustomDataHelper.getTag(stack);
        if (compoundnbt != null && !compoundnbt.getBoolean("resolved")) {
            compoundnbt.putBoolean("resolved", true);
            CustomDataHelper.updateTag(stack, tag -> tag.putBoolean("resolved", true));
            if (!validBookTagContents(compoundnbt)) {
                return false;
            } else {
                ListTag listnbt = compoundnbt.getList("pages", 8);

                for(int i = 0; i < listnbt.size(); ++i) {
                    String s = listnbt.getString(i);

                    Component itextcomponent;
                    try {
                        itextcomponent = Component.Serializer.fromJsonLenient(s, provider);
                        itextcomponent = ComponentUtils.updateForEntity(resolvingSource, itextcomponent, resolvingPlayer, 0);
                    } catch (Exception var9) {
                        itextcomponent = Component.literal(s);
                    }

                    listnbt.set(i, (StringTag.valueOf(Component.Serializer.toJson(itextcomponent, provider))));
                }

                compoundnbt.put("pages", listnbt);
                CustomDataHelper.updateTag(stack, tag -> tag.merge(compoundnbt));
                return true;
            }
        } else {
            return false;
        }
    }
}
