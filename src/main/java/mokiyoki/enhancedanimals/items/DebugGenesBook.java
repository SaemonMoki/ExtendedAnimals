package mokiyoki.enhancedanimals.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;


public class DebugGenesBook extends Item {

    String genes;

    public DebugGenesBook(Item.Properties builder) {
        super(builder);
    }

    /**
     * Returns true if the item can be used on the given entity, e.g. shears on sheep.
     */
    public InteractionResult interactLivingEntity(ItemStack stack, Player playerIn, LivingEntity target, InteractionHand hand) {
        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.translatable("item.eanimod.debug_gene_book_hover").withStyle(ChatFormatting.GRAY));
    }
}
