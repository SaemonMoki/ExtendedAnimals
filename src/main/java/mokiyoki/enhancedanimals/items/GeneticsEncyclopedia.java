package mokiyoki.enhancedanimals.items;

import mokiyoki.enhancedanimals.EnhancedAnimals;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.WritableBookItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.util.StringUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class GeneticsEncyclopedia extends Item {

    public String testString;

    public GeneticsEncyclopedia(Item.Properties builder) {
        super(builder);
    }

    public static boolean validBookTagContents(@Nullable CompoundTag nbt) {
        if (!WritableBookItem.makeSureTagIsValid(nbt)) {
            return false;
        } else if (!nbt.contains("title", 8)) {
            return false;
        } else {
            String s = nbt.getString("title");
            return s.length() > 32 ? false : nbt.contains("author", 8);
        }
    }

    public Component getName(ItemStack stack) {
        if (stack.hasTag()) {
            CompoundTag compoundnbt = stack.getTag();
            String s = compoundnbt.getString("title");
            if (!StringUtil.isNullOrEmpty(s)) {
                return new TextComponent(s);
            }
        }

        return super.getName(stack);
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        if (stack.hasTag()) {
            CompoundTag compoundnbt = stack.getTag();
            String s = compoundnbt.getString("author");
            if (!StringUtil.isNullOrEmpty(s)) {
                tooltip.add((new TranslatableComponent("book.byAuthor", s)).withStyle(ChatFormatting.GRAY));
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

    public static boolean resolveContents(ItemStack stack, @Nullable CommandSourceStack resolvingSource, @Nullable Player resolvingPlayer) {
        CompoundTag compoundnbt = stack.getTag();
        if (compoundnbt != null && !compoundnbt.getBoolean("resolved")) {
            compoundnbt.putBoolean("resolved", true);
            if (!validBookTagContents(compoundnbt)) {
                return false;
            } else {
                ListTag listnbt = compoundnbt.getList("pages", 8);

                for(int i = 0; i < listnbt.size(); ++i) {
                    String s = listnbt.getString(i);

                    Component itextcomponent;
                    try {
                        itextcomponent = Component.Serializer.fromJsonLenient(s);
                        itextcomponent = ComponentUtils.updateForEntity(resolvingSource, itextcomponent, resolvingPlayer, 0);
                    } catch (Exception var9) {
                        itextcomponent = new TextComponent(s);
                    }

                    listnbt.set(i, (StringTag.valueOf(Component.Serializer.toJson(itextcomponent))));
                }

                compoundnbt.put("pages", listnbt);
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundTag nbt) {

    }
}
