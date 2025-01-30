package mokiyoki.enhancedanimals.items;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.concurrent.ThreadLocalRandom;

public class TurtleScute extends Item {
    private static final String BASE_COLOUR = "baseColour";

    public TurtleScute(Properties properties) {
        super(properties);
    }

    public int getDisplayColor(ItemStack itemStack) {
        CompoundTag tag = getStackInfo(itemStack);
        if (!tag.contains(BASE_COLOUR)) {
            if (itemStack.getCount() == 1) {
                int colour = 6842986/*switch (ThreadLocalRandom.current().nextInt(5)) {
                    case 1 -> 16776960;
                    case 2 -> 16753920;
                    case 3 -> 16777215;
                    case 4 -> 1052688;
                    case 5 -> 32768;
                    case 6 -> 9983008;
                    default -> 4702026;
                }*/;
                tag.putIntArray(BASE_COLOUR, new int[] {colour});
            } else {
                int[] colours = new int[itemStack.getCount()];
                for (int i = 0; i < itemStack.getCount(); i++) {
                    colours[i] = i%2 == 0 ? 32768 : 16776960;
                }
                tag.putIntArray(BASE_COLOUR, colours);
            }
        }
        return tag.getIntArray(BASE_COLOUR)[0];
    }

    public void createScuteItem(ItemStack stack, int baseColour) {
        CompoundTag tag = getStackInfo(stack);
        tag.putIntArray(BASE_COLOUR, new int[] {baseColour});
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction clickAction, Player player) {
        if (clickAction == ClickAction.PRIMARY) {
            if (slot.getItem().is(this)) {
                combineStackInfo(stack, slot.getItem());
                System.out.println("COMBINED STACKED ON OTHER");
            }
        } else if (clickAction == ClickAction.SECONDARY) {
            if (stack.getCount() > 1) {
                CompoundTag tag = getStackInfo(stack);
                int[] baseColour = tag.getIntArray(BASE_COLOUR);

                if (baseColour.length == stack.getCount()) {
                    int[] updatedBaseColour = new int[baseColour.length - 1];

                    for (int i = 0; i < updatedBaseColour.length; i++) {
                        updatedBaseColour[i] = baseColour[i + 1];
                    }

                    updateItemStack(tag, updatedBaseColour);
                    stack.shrink(1);

                    System.out.println("UPDATED TOP STACK DATA");
                }

                if (slot.getItem().is(this)) {
                    ItemStack slotStack = slot.getItem();
                    CompoundTag slotTag = getStackInfo(slotStack);
                    int[] slotBaseColour = slotTag.getIntArray(BASE_COLOUR);
                    int[] updatedSlotBaseColour = new int[slotBaseColour.length + 1];

                    updatedSlotBaseColour[0] = baseColour[0];
                    for (int i = 0; i < slotBaseColour.length; i++) {
                        updatedSlotBaseColour[i + 1] = slotBaseColour[i];
                    }
                    updateItemStack(slotTag, updatedSlotBaseColour);
                    slotStack.grow(1);

                    System.out.println("UPDATED BOTTOM STACK DATA");

                    System.out.println("TOP DROP ONE ON BOTTOM");
                } else {
                    ItemStack slotStack = new ItemStack(this);
                    createScuteItem(slotStack, baseColour[0]);
                    slot.set(slotStack);

                    System.out.println("TOP DROP ONE");
                }
                return true;
            }
        }

        return super.overrideStackedOnOther(stack, slot, clickAction, player);
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack bottom, ItemStack top, Slot p_150894_, ClickAction clickAction, Player p_150896_, SlotAccess slotAccess) {

        if (clickAction == ClickAction.SECONDARY) {
            if (top.isEmpty()) {
                if (bottom.getCount() > 1) {
                    int split = bottom.getCount()/2;
                    slotAccess.set(bottom.split(split));
                    top.setCount(split);

                    removeBottomHalfData(top);
                    removeTopHalfData(bottom);

                    System.out.println("BOTTOM SPLIT");
                }
            } else if (top.is(this)) {
                System.out.println("BOTTOM GET ONE FROM TOP");
            }
        } else if (clickAction == ClickAction.PRIMARY) {
            if (bottom.is(this) && top.is(this)) {
                combineStackInfo(top, bottom);
                System.out.println("COMBINED STACKED ON ME");
            }
        }

        return super.overrideOtherStackedOnMe(bottom, top, p_150894_, clickAction, p_150896_, slotAccess);
    }

    private void combineStackInfo(ItemStack top, ItemStack bottom) {
        int size = bottom.getCount()+top.getCount();
        CompoundTag tagTop = getStackInfo(bottom);
        CompoundTag tagBottom = getStackInfo(top);
        if (bottom.getCount() == tagTop.getIntArray(BASE_COLOUR).length && top.getCount() == tagBottom.getIntArray(BASE_COLOUR).length) {
            int[] colours = new int[size];

            for (int i = 0; i < size; i++) {
                if (i < top.getCount()) {
                    colours[i] = tagBottom.getIntArray(BASE_COLOUR)[i];
                } else {
                    colours[i] = tagTop.getIntArray(BASE_COLOUR)[i - top.getCount()];
                }
            }

            updateItemStack(tagTop, colours);
            updateItemStack(tagBottom, colours);
        }
    }

    private void removeBottomHalfData(ItemStack top) {
        CompoundTag tag = getStackInfo(top);
        int size = top.getCount();
        int[] baseColours = tag.getIntArray(BASE_COLOUR);
        int[] updatedBaseColours = new int[size];

        for (int i = 0; i < size; i++) {
            updatedBaseColours[i] = baseColours[i];
        }

        tag.remove(BASE_COLOUR);
        tag.putIntArray(BASE_COLOUR, updatedBaseColours);
        top.getOrCreateTagElement("t");
    }

    private void removeTopHalfData(ItemStack bottom) {
        CompoundTag tag = getStackInfo(bottom);
        int size = bottom.getCount();
        int[] baseColours = tag.getIntArray(BASE_COLOUR);
        int sizeDif = baseColours.length - size;
        int[] updatedBaseColours = new int[size];

        for (int i = 0; i < size; i++) {
            updatedBaseColours[i] = baseColours[i+sizeDif];
        }

        tag.remove(BASE_COLOUR);
        tag.putIntArray(BASE_COLOUR, updatedBaseColours);
    }

    protected CompoundTag getStackInfo(ItemStack stack) {
        stack.removeTagKey("t");
        return stack.getOrCreateTagElement("stackInfo");
    }

    private static void updateItemStack(CompoundTag slotTag, int[] updatedSlotBaseColour) {
        slotTag.remove(BASE_COLOUR);
        slotTag.putIntArray(BASE_COLOUR, updatedSlotBaseColour);
    }



}