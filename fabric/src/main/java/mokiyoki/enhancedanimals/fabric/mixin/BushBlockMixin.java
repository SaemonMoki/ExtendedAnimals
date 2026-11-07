package mokiyoki.enhancedanimals.fabric.mixin;

import mokiyoki.enhancedanimals.blocks.PlantSustainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// NeoForge: canSustainPlant in BushBlock
@Mixin(BushBlock.class)
public abstract class BushBlockMixin {

    @Inject(method = "canSurvive", at = @At("HEAD"), cancellable = true)
    private void eanimod$askPlantSustainer(BlockState state, LevelReader level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        BlockPos soilPos = pos.below();
        BlockState soil = level.getBlockState(soilPos);
        if (soil.getBlock() instanceof PlantSustainer sustainer) {
            switch (sustainer.canSustainGeneticPlant(soil, level, soilPos, Direction.UP, state)) {
                case TRUE -> cir.setReturnValue(true);
                case FALSE -> cir.setReturnValue(false);
                case DEFAULT -> {
                }
            }
        }
    }
}
