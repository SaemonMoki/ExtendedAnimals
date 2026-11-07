package mokiyoki.enhancedanimals.util;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.state.BlockState;

public enum LegacyPlantType {
    PLAINS,
    DESERT,
    BEACH,
    CAVE,
    WATER,
    NETHER,
    CROP;

    public static LegacyPlantType of(BlockState plant) {
        Block block = plant.getBlock();
        if (block instanceof CactusBlock) return DESERT;
        if (block instanceof StemBlock) return CROP;
        if (block instanceof SugarCaneBlock) return BEACH;
        if (block instanceof CropBlock) return CROP;
        if (block instanceof SaplingBlock) return PLAINS;
        if (block instanceof FlowerBlock) return PLAINS;
        if (block == Blocks.DEAD_BUSH) return DESERT;
        if (block == Blocks.LILY_PAD) return WATER;
        if (block == Blocks.RED_MUSHROOM) return CAVE;
        if (block == Blocks.BROWN_MUSHROOM) return CAVE;
        if (block == Blocks.NETHER_WART) return NETHER;
        if (block == Blocks.TALL_GRASS) return PLAINS;
        return PLAINS;
    }
}
