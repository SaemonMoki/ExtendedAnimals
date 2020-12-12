//package mokiyoki.enhancedanimals.items.crafting;
//
//import mokiyoki.enhancedanimals.items.MixableMilk;
//import net.minecraft.inventory.CraftingInventory;
//import net.minecraft.item.BucketItem;
//import net.minecraft.item.GlassBottleItem;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.item.Items;
//import net.minecraft.item.crafting.SpecialRecipe;
//import net.minecraft.util.ResourceLocation;
//import net.minecraft.world.World;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MixableMilkRecipe extends SpecialRecipe {
//    public MixableMilkRecipe(ResourceLocation idIn) {super(idIn);}
//
//    public boolean matches(CraftingInventory inv, World worldIn) {
//        boolean containsMilk = false;
//        boolean containsOnlyMixables = true;
//        int count = 0;
//        for (int i = 0; i < inv.getSizeInventory(); i++) {
//            ItemStack itemstack = inv.getStackInSlot(i);
//            if (!itemstack.isEmpty()) {
//                Item item = itemstack.getItem();
//                if (item instanceof MixableMilk) {
//                    containsMilk = true;
//                    count++;
//                } else if (item instanceof BucketItem || item instanceof GlassBottleItem) {
//                    count++;
//                } else {
//                    containsOnlyMixables = false;
//                }
//            }
//        }
//        return containsMilk && containsOnlyMixables && count >= 2;
//    }
//
//    public ItemStack getCraftingResult(CraftingInventory inv) {
//        int fillableItemSlot = this.getBottomRightItemSlot(inv);
//        if (fillableItemSlot == -1) {
//            return ItemStack.EMPTY;
//        } else {
//            MixableMilk resultItem;
//            float fillableSpace;
//            ItemStack fillableItemStack = inv.getStackInSlot(fillableItemSlot);
//            Item fillableItem = fillableItemStack.getItem();
//            if (fillableItem instanceof MixableMilk) {
//                fillableSpace = ((MixableMilk) fillableItem).getFillableSpace(fillableItemStack);
//                resultItem = ((MixableMilk) fillableItem);
//            } else if (fillableItem instanceof GlassBottleItem) {
//                fillableSpace = 1F/3F;
//                resultItem = new MixableMilk("mixablemilk_bottle", Items.GLASS_BOTTLE);
//            } else if (fillableItem instanceof BucketItem) {
//                fillableSpace = 1F;
//                resultItem = new MixableMilk("mixablemilk_bucket", Items.BUCKET);
//            }
//
//            List<ItemStack> listOfItems = new ArrayList<ItemStack>();
//            for (int i = 0; i <= fillableItemSlot; i++) {
//                if (inv.getStackInSlot(i).getItem() instanceof MixableMilk) {
//                    listOfItems.add(inv.getStackInSlot(i));
//                }
//            }
//
//        }
//
//
////        ItemStack itemstack = inv.getStackInSlot(1 + inv.getWidth());
////        if (itemstack.getItem() != Items.LINGERING_POTION) {
////            return ItemStack.EMPTY;
////        } else {
////            ItemStack itemstack1 = new ItemStack(Items.TIPPED_ARROW, 8);
////            PotionUtils.addPotionToItemStack(itemstack1, PotionUtils.getPotionFromItem(itemstack));
////            PotionUtils.appendEffects(itemstack1, PotionUtils.getFullEffectsFromItem(itemstack));
////            return itemstack1;
////        }
//    }
//
//    private int getBottomRightItemSlot(CraftingInventory inv) {
//        for (int h = inv.getHeight(); h >= 1; h--) {
//            for (int w = inv.getWidth(); w >= 1; w--) {
//                ItemStack itemStack = inv.getStackInSlot((h*w)-1);
//                if (!itemStack.isEmpty()) {
//                    Item item = itemStack.getItem();
//                    if (item instanceof MixableMilk) {
//                        if (!((MixableMilk) item).isFull(itemStack)) {
//                            return (h*w)-1;
//                        }
//                    } else if (item instanceof BucketItem || item instanceof GlassBottleItem) {
//                        return (h*w-1);
//                    }
//                }
//            }
//        }
//        return -1;
//    }
//}
