package mokiyoki.enhancedanimals.init;

import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> AXOLOTL_NESTABLE = tag("axolotl_nestable");
        public static final TagKey<Block> AXOLOTL_NESTABLE_OVER = tag("axolotl_nestable_over");
        public static final TagKey<Block> AXOLOTL_NESTABLE_UNDER = tag("axolotl_nestable_under");
        public static final TagKey<Block> TURTLE_NESTABLE = tag("turtle_nestable");
        public static final TagKey<Block> HAY_BLOCKS = forgeTag("hay");

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(Reference.MODID, name));
        }
        private static TagKey<Block> forgeTag(String name) {
            return BlockTags.create(new ResourceLocation("forge", name));
        }
    }

    public static class Items {
        public static final TagKey<Item> HAY_CUTTING_TOOLS = tag("hay_cutting_tools");
        public static final TagKey<Item> BUTCHER_TOOLS = tag("butcher_tools");
        public static final TagKey<Item> MILK_BUCKETS = tag("milk_buckets");
        public static final TagKey<Item> MILK_BOTTLES = tag("milk_bottles");
        public static final TagKey<Item> MILK = tag("milks");
        public static final TagKey<Item> COOKED_CHICKEN = tag("cooked_chickens");
        public static final TagKey<Item> RAW_CHICKEN = tag("raw_chickens");
        public static final TagKey<Item> FULLSIZE_COOKED_CHICKEN = forgeTag("cooked_chickens");
        public static final TagKey<Item> FULLSIZE_RAW_CHICKEN = forgeTag("raw_chickens");
        public static final TagKey<Item> SADDLES = forgeTag("saddles");
        public static final TagKey<Item> AXES = forgeTag("axes");
        public static final TagKey<Item> SWORDS = forgeTag("swords");
        public static final TagKey<Item> EGGS = forgeTag("eggs");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(Reference.MODID, name));
        }
        private static TagKey<Item> forgeTag(String name) {
            return ItemTags.create(new ResourceLocation("forge", name));
        }
    }

}