package mokiyoki.enhancedanimals.fabric.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import mokiyoki.enhancedanimals.util.handlers.EventSubscriber;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.animal.Animal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// NeoForge: BabyEntitySpawnEvent
@Mixin(Animal.class)
public abstract class AnimalMixin {

    @Inject(method = "spawnChildFromBreeding",
            at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/entity/animal/Animal;getBreedOffspring(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/AgeableMob;)Lnet/minecraft/world/entity/AgeableMob;"),
            cancellable = true)
    private void eanimod$onBabyEntitySpawn(ServerLevel level, Animal mate, CallbackInfo ci, @Local AgeableMob child) {
        Animal self = (Animal) (Object) this;
        if (EventSubscriber.INSTANCE.shouldCancelBabySpawn(self, child)) {
            self.setAge(6000);
            mate.setAge(6000);
            self.resetLove();
            mate.resetLove();
            ci.cancel();
        }
    }
}
