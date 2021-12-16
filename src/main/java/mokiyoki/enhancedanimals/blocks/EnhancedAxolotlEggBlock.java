package mokiyoki.enhancedanimals.blocks;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class EnhancedAxolotlEggBlock extends NestBlock {
    public static final IntegerProperty HATCH = BlockStateProperties.HATCH;
    public static final IntegerProperty EGGS = BlockStateProperties.EGGS;

    public EnhancedAxolotlEggBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

}
