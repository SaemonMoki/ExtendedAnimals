package mokiyoki.enhancedanimals.neoforge.mixin;

import mokiyoki.enhancedanimals.entity.EnhancedLlama;
import mokiyoki.enhancedanimals.entity.EnhancedMoobloom;
import mokiyoki.enhancedanimals.entity.EnhancedMooshroom;
import mokiyoki.enhancedanimals.entity.EnhancedRabbit;
import mokiyoki.enhancedanimals.entity.EnhancedSheep;
import net.neoforged.neoforge.common.IShearable;
import org.spongepowered.asm.mixin.Mixin;

// GeneticShearable already has IShearable's method signatures.
@Mixin({EnhancedSheep.class, EnhancedLlama.class, EnhancedRabbit.class, EnhancedMooshroom.class, EnhancedMoobloom.class})
public abstract class GeneticShearableMixin implements IShearable {
}
