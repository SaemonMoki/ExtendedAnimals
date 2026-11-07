package mokiyoki.enhancedanimals.neoforge.mixin;

import mokiyoki.enhancedanimals.blocks.PatchyMyceliumBlock;
import mokiyoki.enhancedanimals.blocks.PlantSustainer;
import mokiyoki.enhancedanimals.blocks.SparseGrassBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.extensions.IBlockExtension;
import net.neoforged.neoforge.common.util.TriState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({SparseGrassBlock.class, PatchyMyceliumBlock.class})
public abstract class PlantSustainerMixin extends Block implements IBlockExtension {

    private PlantSustainerMixin(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public TriState canSustainPlant(BlockState state, BlockGetter level, BlockPos soilPosition, Direction facing, BlockState plant) {
        return switch (((PlantSustainer) this).canSustainGeneticPlant(state, level, soilPosition, facing, plant)) {
            case TRUE -> TriState.TRUE;
            case FALSE -> TriState.FALSE;
            case DEFAULT -> TriState.DEFAULT;
        };
    }
}
