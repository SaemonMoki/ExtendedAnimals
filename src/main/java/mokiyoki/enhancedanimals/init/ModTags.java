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

    public static class EntityTypes {
        public static final TagKey<EntityType<?>> GENETIC_ANIMALS = tag("genetic_animals");
        public static final TagKey<EntityType<?>> AXOLOTLS = forgeTag("axolotls");
        public static final TagKey<EntityType<?>> CHICKENS = forgeTag("chickens");
        public static final TagKey<EntityType<?>> COWS = forgeTag("cows");
        public static final TagKey<EntityType<?>> LLAMAS = forgeTag("llamas");
        public static final TagKey<EntityType<?>> MOOBLOOMS = forgeTag("mooblooms");
        public static final TagKey<EntityType<?>> MOOSHROOMS = forgeTag("mooshrooms");
        public static final TagKey<EntityType<?>> PIGS = forgeTag("pigs");
        public static final TagKey<EntityType<?>> RABBITS = forgeTag("rabbits");
        public static final TagKey<EntityType<?>> SHEEP = forgeTag("sheep");
        public static final TagKey<EntityType<?>> HUNTING_WOLVES = tag("hunting_wolves");
        public static final TagKey<EntityType<?>> WOLF_HUNTABLE = tag("wolf_hunt_targets");
        public static final TagKey<EntityType<?>> WOLF_HUNTABLE_IF_BABY = tag("wolf_baby_hunt_targets");
        public static final TagKey<EntityType<?>> WOLF_HUNTABLE_IF_BABY_ON_LAND = tag("wolf_baby_on_land_hunt_targets");
        public static final TagKey<EntityType<?>> WOLF_REPELLING = tag("wolf_repelling");
        public static final TagKey<EntityType<?>> HUNTING_FOXES = tag("hunting_foxes");
        public static final TagKey<EntityType<?>> FOX_HUNTABLE = tag("fox_hunt_targets");
        public static final TagKey<EntityType<?>> FOX_HUNTABLE_IF_BABY = tag("fox_baby_hunt_targets");
        public static final TagKey<EntityType<?>> CAT_HUNTABLE = tag("cat_hunt_targets");
        public static final TagKey<EntityType<?>> CAT_HUNTABLE_IF_BABY = tag("cat_baby_hunt_targets");
        public static final TagKey<EntityType<?>> UNDEAD_HUNTABLE = tag("undead_hunt_targets");
        public static final TagKey<EntityType<?>> UNDEAD_HUNTABLE_IF_BABY = tag("undead_baby_hunt_targets");

        private static TagKey<EntityType<?>> tag(String name) {
            return TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation(Reference.MODID, name));
        }
        private static TagKey<EntityType<?>> forgeTag(String name) {
            return TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation("forge", name));
        }
    }

}