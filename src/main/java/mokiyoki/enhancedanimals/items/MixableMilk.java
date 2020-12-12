//package mokiyoki.enhancedanimals.items;
//
//import mokiyoki.enhancedanimals.EnhancedAnimals;
//import mokiyoki.enhancedanimals.entity.util.Colouration;
//import mokiyoki.enhancedanimals.util.Reference;
//import net.minecraft.advancements.CriteriaTriggers;
//import net.minecraft.entity.LivingEntity;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.entity.player.ServerPlayerEntity;
//import net.minecraft.item.BucketItem;
//import net.minecraft.item.Food;
//import net.minecraft.item.GlassBottleItem;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.item.Items;
//import net.minecraft.item.UseAction;
//import net.minecraft.nbt.CompoundNBT;
//import net.minecraft.stats.Stats;
//import net.minecraft.util.ActionResult;
//import net.minecraft.util.Hand;
//import net.minecraft.world.World;
//
//import java.util.List;
//import java.util.concurrent.ThreadLocalRandom;
//
//public class MixableMilk extends Item {
//    private Item containerItem;
//    private float capacity;
//
//    public MixableMilk(String registryname, Item containerItem) {
//        super(new Item.Properties().group(EnhancedAnimals.GENETICS_ANIMALS_GROUP).maxStackSize(1).food((new Food.Builder()).setAlwaysEdible().hunger(0).saturation(0F).build()));
//        this.setRegistryName(Reference.MODID, registryname);
//        this.containerItem = containerItem;
//        this.capacity = containerItem instanceof GlassBottleItem ? 1F/3F : 1F;
//    }
//
//    public float getCapacity(ItemStack stack) {
//        return this.capacity;
//    }
//
//    public float getFillableSpace(ItemStack stack) {
//        return getCapacity(stack) - getFilledSpace(stack);
//    }
//
//    public float getFilledSpace(ItemStack stack) {
//        return getCapacity(stack) * getFullness(stack);
//    }
//
//    public float make(ItemStack stack, float milk, int color, float cream, float protein, float sugar, MilkType type, InoculationType inoculationType) {
//        CompoundNBT newMixableMilk = stack.getOrCreateChildTag("display");
//        newMixableMilk.putInt("color", color);
//        newMixableMilk.putFloat("cream", cream);
//        newMixableMilk.putFloat("protein", protein);
//        newMixableMilk.putFloat("sugar", sugar);
//        newMixableMilk.putFloat(type.toString(), 1F);
//        if (inoculationType != InoculationType.NONE) newMixableMilk.putFloat(inoculationType.toString(), ThreadLocalRandom.current().nextFloat());
//        return setFullness(stack, milk);
//    }
//
//    public float mixMilk(ItemStack stack, float milk, int color, float cream, float protein, float sugar, MilkType type, InoculationType inoculationType) {
//        if (stack.getItem() instanceof MixableMilk) {
//            float currentMilk = getFullness(stack);
//
//            mixColour(stack, color, currentMilk, milk);
//            mixCream(stack, cream, currentMilk, milk);
//            mixProtein(stack, protein, currentMilk, milk);
//            mixSugar(stack, sugar, currentMilk, milk);
//            mixMilkType(stack, currentMilk, milk, type);
//            innoculateWith(stack, currentMilk, milk, inoculationType);
//            return setFullness(stack, milk);
//        } else {
//            return make(stack, milk, color, cream, protein, sugar, type, inoculationType);
//        }
//    }
//
//    public float mixMilk(ItemStack thisMilk, ItemStack[] otherMilk) {
//        if (thisMilk.getItem() instanceof MixableMilk) {
//        float newMilk = getCapacity(thisMilk) - (getCapacity(thisMilk) * getFullness(thisMilk));
//        float smallestOtherMilkAmount = 1F;
//        for (int i = 0; i < otherMilk.length; i++) {
//            if (otherMilk[i].getItem() instanceof MixableMilk) {
//                if (smallestOtherMilkAmount < getFullness(otherMilk[i])) {
//                    smallestOtherMilkAmount = getFullness(otherMilk[i]);
//                }
//            } else {
//                return -1F;
//            }
//        }
//
//            if (otherMilk.getItem() instanceof MixableMilk) {
//                MixableMilk thatMilk = ((MixableMilk) otherMilk.getItem());
//                float currentMilk = getFullness(thisMilk);
//                float newMilk = getFullness(otherMilk);
//
//                mixColour(thisMilk, getColor(otherMilk), currentMilk, newMilk);
//                mixCream(thisMilk, getCream(otherMilk), currentMilk, newMilk);
//                mixProtein(thisMilk, getProtein(otherMilk), currentMilk, newMilk);
//                mixSugar(thisMilk, getSugar(otherMilk), currentMilk, newMilk);
//                mixMilkType(thisMilk, currentMilk, newMilk, getMilkType(otherMilk));
//                innoculateWith(thisMilk, currentMilk, newMilk, getInoculationType(otherMilk));
//
//                return setFullness(thisMilk, ((MixableMilk) otherMilk.getItem()).getFullness(otherMilk));
//            } else {
//                return setFullness(thisMilk, (((MixableMilk) thisMilk.getItem()).getFullness(thisMilk)) * -0.5F);
//            }
//        } else {
//
//        }
//    }
//
//
//
//    public float setFullness(ItemStack stack, float milk) {
//        //sets fullness as percent filled
//        CompoundNBT compoundnbt = stack.getChildTag("display");
//        if (milk > 0F) {
//            if (compoundnbt != null && compoundnbt.contains("fullness")) {
//                float fullness = compoundnbt.getFloat("fullness");
//                    stack.getOrCreateChildTag("display").putFloat("fullness", milk >= this.capacity ? 1F : (this.capacity-milk)/this.capacity);
//                    return milk - (this.capacity * (1F - fullness));
//            } else if (milk >= this.capacity) {
//                stack.getOrCreateChildTag("display").putFloat("fullness", 1F);
//                return milk - this.capacity;
//            } else {
//                stack.getOrCreateChildTag("display").putFloat("fullness", milk);
//                return 0F;
//            }
//        } else {
//            if (compoundnbt != null && compoundnbt.contains("fullness")) {
//                milk = milk + (this.capacity * compoundnbt.getFloat("fullness"));
//                stack.getOrCreateChildTag("display").putFloat("fullness", milk >= 0F ? milk/this.capacity : 0F);
//                return milk <= 0F ? milk : 0F;
//            } else {
//                return milk;
//            }
//        }
//    }
//
//    public boolean isFull(ItemStack stack) {
//        return getFullness(stack) >= this.capacity;
//    }
//
//    private float getFullness(ItemStack stack) {
//        CompoundNBT compoundnbt = stack.getChildTag("display");
//        return compoundnbt != null && compoundnbt.contains("fullness") ? compoundnbt.getInt("color") : 0F;
//    }
//
//    private void mixColour(ItemStack stack, int milkColour, float currentMilk, float newMilk) {
//        float[] oldColour = Colouration.getHSBFromABGR(getColor(stack));
//        float[] newColour = Colouration.getHSBFromABGR(milkColour);
//
//        newColour[0] = oldColour[0]*currentMilk + newColour[0]*newMilk;
//        newColour[1] = oldColour[1]*currentMilk + newColour[1]*newMilk;
//        newColour[2] = oldColour[2]*currentMilk + newColour[2]*newMilk;
//
//        stack.getOrCreateChildTag("display").putInt("color", Colouration.HSBtoABGR(newColour[0], newColour[1], newColour[2]));
//    }
//
//    public int getColor(ItemStack stack) {
//        CompoundNBT compoundnbt = stack.getChildTag("display");
//        return compoundnbt != null && compoundnbt.contains("color", 99) ? compoundnbt.getInt("color") : 16777215;
//    }
//
//    private void mixCream(ItemStack stack, float cream, float oldMilk, float newMilk) {
//        float totalMilk = oldMilk + newMilk;
//        oldMilk = oldMilk/totalMilk;
//        newMilk = newMilk/totalMilk;
//        stack.getOrCreateChildTag("display").putFloat("cream", (oldMilk * getCream(stack)) + (newMilk * newMilk));
//    }
//
//    private float getCream(ItemStack stack) {
//        CompoundNBT compoundnbt = stack.getChildTag("display");
//        return compoundnbt != null && compoundnbt.contains("cream") ? compoundnbt.getInt("cream") : 0;
//    }
//
//    private void mixProtein(ItemStack stack, float protein, float oldMilk, float newMilk) {
//        float totalMilk = oldMilk + newMilk;
//        oldMilk = oldMilk/totalMilk;
//        newMilk = newMilk/totalMilk;
//        stack.getOrCreateChildTag("display").putFloat("protein", (oldMilk * getProtein(stack)) + (newMilk * protein));
//    }
//
//    private float getProtein(ItemStack stack) {
//        CompoundNBT compoundnbt = stack.getChildTag("display");
//        return compoundnbt != null && compoundnbt.contains("protein") ? compoundnbt.getInt("protein") : 0;
//    }
//
//    private void mixSugar(ItemStack stack, float sugar, float oldMilk, float newMilk) {
//        float totalMilk = oldMilk + newMilk;
//        oldMilk = oldMilk/totalMilk;
//        newMilk = newMilk/totalMilk;
//        stack.getOrCreateChildTag("display").putFloat("sugar", (oldMilk * getSugar(stack)) + (newMilk * sugar));
//    }
//
//    private float getSugar(ItemStack stack) {
//        CompoundNBT compoundnbt = stack.getChildTag("display");
//        return compoundnbt != null && compoundnbt.contains("sugar") ? compoundnbt.getInt("sugar") : 0;
//    }
//
//    private void mixMilkType(ItemStack stack, float currentMilk, float newMilk, MilkType type) {
//        this.mixMilkType(stack, currentMilk, newMilk, type.equals(MilkType.COW) ? 1F : 0F, type.equals(MilkType.SHEEP) ? 1F : 0F, type.equals(MilkType.GOAT) ? 1F : 0F, type.equals(MilkType.RED_MOOSHROOM) ? 1F : 0F, type.equals(MilkType.BROWN_MOOSHROOM) ? 1F : 0F, type.equals(MilkType.MOOBLOOM) ? 1F : 0F);
//    }
//
//    private void mixMilkType(ItemStack stack, float currentMilk, float newMilk, float[] milkTypes) {
//        this.mixMilkType(stack, currentMilk, newMilk, milkTypes[0], milkTypes[1], milkTypes[2], milkTypes[3], milkTypes[4], milkTypes[5]);
//    }
//
//    private void mixMilkType(ItemStack stack, float currentMilk, float newMilk, float cow, float sheep, float goat, float redmooshroom, float brownmooshroom, float moobloom) {
//        float totalMilk = currentMilk + newMilk;
//        currentMilk = currentMilk / totalMilk;
//        newMilk = newMilk / totalMilk;
//
//        CompoundNBT compoundnbt = stack.getChildTag("display");
//        if (compoundnbt != null) {
//            compoundnbt.putFloat("cow", (compoundnbt.contains("cow") ? (compoundnbt.getFloat("cow") * currentMilk) + (cow * newMilk) : cow * newMilk));
//            compoundnbt.putFloat("sheep", (compoundnbt.contains("sheep") ? (compoundnbt.getFloat("sheep") * currentMilk) + (sheep * newMilk) : sheep * newMilk));
//            compoundnbt.putFloat("goat", (compoundnbt.contains("goat") ? (compoundnbt.getFloat("goat") * currentMilk) + (goat * newMilk) : goat * newMilk));
//            compoundnbt.putFloat("redmooshroom", (compoundnbt.contains("redmooshroom") ? (compoundnbt.getFloat("redmooshroom") * currentMilk) + (redmooshroom * newMilk) : redmooshroom * newMilk));
//            compoundnbt.putFloat("brownmooshroom", (compoundnbt.contains("brownmooshroom") ? (compoundnbt.getFloat("brownmooshroom") * currentMilk) + (brownmooshroom * newMilk) : brownmooshroom * newMilk));
//            compoundnbt.putFloat("moobloom", (compoundnbt.contains("moobloom") ? (compoundnbt.getFloat("moobloom") * currentMilk) + (moobloom * newMilk) : moobloom * newMilk));
//        }
//    }
//
//    private float[] getMilkType(ItemStack stack) {
//        CompoundNBT compoundnbt = stack.getChildTag("display");
//        if (compoundnbt != null) {
//            float[] currentMilkTypes = {
//                    compoundnbt.contains("cow") ? compoundnbt.getFloat("cow") : 0F,
//                    compoundnbt.contains("sheep") ? compoundnbt.getFloat("sheep") : 0F,
//                    compoundnbt.contains("goat") ? compoundnbt.getFloat("goat") : 0F,
//                    compoundnbt.contains("redmooshroom") ? compoundnbt.getFloat("redmooshroom") : 0F,
//                    compoundnbt.contains("brownmooshroom") ? compoundnbt.getFloat("brownmooshroom") : 0F,
//                    compoundnbt.contains("moobloom") ? compoundnbt.getFloat("moobloom") : 0F};
//            return currentMilkTypes;
//        } else {
//            return new float[6];
//        }
//    }
//
//    private void innoculateWith(ItemStack stack, float currentMilk, float newMilk, InoculationType type) {
//        float amount = ThreadLocalRandom.current().nextFloat();
//        innoculateWith(stack, currentMilk, newMilk, type.equals(InoculationType.RED_MUSHROOM) ? amount : 0F, type.equals(InoculationType.BROWN_MUSHROOM) ? amount : 0F, type.equals(InoculationType.YELLOW_FLOWER) ? amount : 0F);
//    }
//
//    private void innoculateWith(ItemStack stack, float currentMilk, float newMilk, float[] types) {
//        innoculateWith(stack, currentMilk, newMilk, types[0], types[1], types[2]);
//    }
//
//    private void innoculateWith(ItemStack stack, float currentMilk, float newMilk, float redMushroom, float brownMushroom, float yellowflower) {
//        float totalMilk = currentMilk + newMilk;
//        currentMilk = currentMilk / totalMilk;
//        newMilk = newMilk / totalMilk;
//
//        CompoundNBT compoundnbt = stack.getChildTag("display");
//        if (compoundnbt != null) {
//            compoundnbt.putFloat("redmush", (compoundnbt.contains("redmush") ? (compoundnbt.getFloat("redmush") * currentMilk) + (redMushroom * newMilk) : redMushroom * newMilk));
//            compoundnbt.putFloat("brownmush", (compoundnbt.contains("brownmush") ? (compoundnbt.getFloat("brownmush") * currentMilk) + (brownMushroom * newMilk) : brownMushroom * newMilk));
//            compoundnbt.putFloat("yellowflower", (compoundnbt.contains("yellowflower") ? (compoundnbt.getFloat("yellowflower") * currentMilk) + (yellowflower * newMilk) : yellowflower * newMilk));
//        }
//    }
//
//    private float[] getInoculationType(ItemStack stack) {
//        CompoundNBT compoundnbt = stack.getChildTag("display");
//        if (compoundnbt != null) {
//            float[] InoculationTypes = {
//                    compoundnbt.contains("redmush") ? compoundnbt.getFloat("redmush") : 0F,
//                    compoundnbt.contains("brownmush") ? compoundnbt.getFloat("brownmush") : 0F,
//                    compoundnbt.contains("yellowflower") ? compoundnbt.getFloat("yellowflower") : 0F};
//            return InoculationTypes;
//        } else {
//            return new float[3];
//        }
//    }
//
//    private float getRedMushroom(ItemStack stack) {
//        CompoundNBT compoundnbt = stack.getChildTag("display");
//        return compoundnbt != null && compoundnbt.contains("redmush") ? compoundnbt.getInt("redmush") : 0;
//    }
//
//    private float getBrownMushroom(ItemStack stack) {
//        CompoundNBT compoundnbt = stack.getChildTag("display");
//        return compoundnbt != null && compoundnbt.contains("brownmush") ? compoundnbt.getInt("brownmush") : 0;
//    }
//
//    private float getYellowFlower(ItemStack stack) {
//        CompoundNBT compoundnbt = stack.getChildTag("display");
//        return compoundnbt != null && compoundnbt.contains("yellowflower") ? compoundnbt.getInt("yellowflower") : 0;
//    }
//
//    public InoculationType infectBaby(ItemStack stack, float age) {
//        float random = ThreadLocalRandom.current().nextFloat();
//        if (random <= age*this.getRedMushroom(stack)) {
//            return InoculationType.RED_MUSHROOM;
//        } else if (random <= age*this.getBrownMushroom(stack)) {
//            return InoculationType.BROWN_MUSHROOM;
//        } else if (random <= age*this.getYellowFlower(stack)) {
//            return InoculationType.YELLOW_FLOWER;
//        }
//
//        return InoculationType.NONE;
//    }
//
//    /**
//     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
//     * the Item before the action is complete.
//     */
//    @Override
//    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
//        float i = ThreadLocalRandom.current().nextFloat();
//        if (!worldIn.isRemote && i == 0) entityLiving.curePotionEffects(stack); // FORGE - move up so stack.shrink does not turn stack into air
//
//        if (entityLiving instanceof ServerPlayerEntity) {
//            ServerPlayerEntity serverplayerentity = (ServerPlayerEntity)entityLiving;
//            CriteriaTriggers.CONSUME_ITEM.trigger(serverplayerentity, stack);
//            serverplayerentity.addStat(Stats.ITEM_USED.get(this));
//        }
//
//        if (entityLiving instanceof PlayerEntity && !((PlayerEntity)entityLiving).abilities.isCreativeMode) {
//            stack.shrink(1);
//        }
//
//        if (i < this.capacity*this.getFullness(stack)) {
//            if (!worldIn.isRemote) {
//                entityLiving.clearActivePotions();
//            }
//        }
//
//        return stack.isEmpty() ? new ItemStack(this.containerItem) : stack;
//    }
//
//    /**
//     * How long it takes to use or consume an item
//     */
//    public int getUseDuration(ItemStack stack) {
//        return 32*(int)((this.getFullness(stack)*this.capacity)/6);
//    }
//
//    /**
//     * returns the action that specifies what animation to play when the items is being used
//     */
//    public UseAction getUseAction(ItemStack stack) {
//        return UseAction.DRINK;
//    }
//
//    /**
//     * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
//     * {@link #onItemUse}.
//     */
//    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
//        playerIn.setActiveHand(handIn);
//        return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
//    }
//
//    public enum MilkType {
//        COW,
//        SHEEP,
//        GOAT,
//        RED_MOOSHROOM ("redmoo"),
//        BROWN_MOOSHROOM ("brownmoo"),
//        MOOBLOOM ("yellowbloom");
//
//        String name;
//
//        MilkType() {
//            this.name = this.name();
//        }
//
//        MilkType(String name) {
//            this.name = name;
//        }
//
//        @Override
//        public String toString() {
//            return this.name;
//        }
//    }
//
//    public enum InoculationType {
//        RED_MUSHROOM ("redmush"),
//        BROWN_MUSHROOM ("brownmush"),
//        YELLOW_FLOWER ("yellowflower"),
//        NONE ("");
//
//        String name;
//
//        InoculationType(String name) {
//            this.name = name;
//        }
//
//        @Override
//        public String toString() {
//            return this.name;
//        }
//    }
//}
