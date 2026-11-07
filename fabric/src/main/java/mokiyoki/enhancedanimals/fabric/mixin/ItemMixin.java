package mokiyoki.enhancedanimals.fabric.mixin;

import mokiyoki.enhancedanimals.entity.GeneticShearable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// NeoForge: IShearable handling in ShearsItem
@Mixin(Item.class)
public abstract class ItemMixin {

    @Inject(method = "interactLivingEntity", at = @At("HEAD"), cancellable = true)
    private void eanimod$shearGeneticAnimal(ItemStack stack, Player player, LivingEntity entity, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if ((Object) this instanceof ShearsItem) {
            InteractionResult result = GeneticShearable.shearWithItem(stack, player, entity, hand);
            if (result != InteractionResult.PASS) {
                cir.setReturnValue(result);
            }
        }
    }
}
