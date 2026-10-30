package mokiyoki.enhancedanimals.init;

import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> AXOLOTL_NESTABLE = tag("axolotl_nestable");
        public static final TagKey<Block> AXOLOTL_NESTABLE_OVER = tag("axolotl_nestable_over");
        public static final TagKey<Block> AXOLOTL_NESTABLE_UNDER = tag("axolotl_nestable_under");
        public static final TagKey<Block> TURTLE_NESTABLE = tag("turtle_nestable");

        private static TagKey<Block> tag(String name) {
            return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Reference.MODID, name));
        }
        private static TagKey<Block> forgeTag(String name) {
            return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", name));
        }
    }

    public static class Items {
        private static TagKey<Item> tag(String name) {
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Reference.MODID, name));
        }
        private static TagKey<Item> forgeTag(String name) {
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", name));
        }
    }

}