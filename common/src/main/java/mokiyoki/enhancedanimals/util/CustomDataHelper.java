package mokiyoki.enhancedanimals.util;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

import org.jetbrains.annotations.Nullable;
import java.util.function.Consumer;

public class CustomDataHelper {

    public static boolean hasTag(ItemStack stack) {
        return stack.has(DataComponents.CUSTOM_DATA);
    }

    @Nullable
    public static CompoundTag getTag(ItemStack stack) {
        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        return customData == null ? null : customData.copyTag();
    }

    public static CompoundTag getTagOrEmpty(ItemStack stack) {
        return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
    }

    @Nullable
    public static CompoundTag getTagElement(ItemStack stack, String key) {
        CompoundTag tag = getTag(stack);
        return tag != null && tag.contains(key, Tag.TAG_COMPOUND) ? tag.getCompound(key) : null;
    }

    public static CompoundTag getTagElementOrEmpty(ItemStack stack, String key) {
        CompoundTag element = getTagElement(stack, key);
        return element == null ? new CompoundTag() : element;
    }

    public static void updateTag(ItemStack stack, Consumer<CompoundTag> updater) {
        CustomData.update(DataComponents.CUSTOM_DATA, stack, updater);
    }

    public static void updateTagElement(ItemStack stack, String key, Consumer<CompoundTag> updater) {
        updateTag(stack, tag -> {
            CompoundTag element = tag.getCompound(key);
            updater.accept(element);
            tag.put(key, element);
        });
    }
}
