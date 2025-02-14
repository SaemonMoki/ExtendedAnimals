package mokiyoki.enhancedanimals.capability.turtlescute;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public interface IStackableCapability {
    String BASE_COLOUR = "baseColour";

    void createStackableData(ItemStack stack, int basecolour);

    default int getDisplayColor(ItemStack itemStack) {
        CompoundTag tag = getStackInfo(itemStack);
        if (!tag.contains(BASE_COLOUR)) {
            if (itemStack.getCount() == 1) {
                int colour = generateInfoForCreative();
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

    private static CompoundTag getStackInfo(ItemStack stack) {
        stack.removeTagKey("t");
        return stack.getOrCreateTagElement("stackInfo");
    }

    default int generateInfoForCreative() {
        return 6842986/*switch (ThreadLocalRandom.current().nextInt(5)) {
                    case 1 -> 16776960;
                    case 2 -> 16753920;
                    case 3 -> 16777215;
                    case 4 -> 1052688;
                    case 5 -> 32768;
                    case 6 -> 9983008;
                    default -> 4702026;
                }*/;
    }
}