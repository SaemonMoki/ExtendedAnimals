package mokiyoki.enhancedanimals.ai.sensor;

import com.google.common.collect.ImmutableSet;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EnhancedTemptingSensor extends Sensor<PathfinderMob> {
    public static final int TEMPTATION_RANGE = 10;
    private static final TargetingConditions TEMPT_TARGETING = TargetingConditions.forNonCombat().range(10.0D).ignoreLineOfSight();
    private final Predicate<ItemStack> temptCheck;

    public EnhancedTemptingSensor(Predicate<ItemStack> temptCheck) {
        this.temptCheck = temptCheck;
    }

    protected void doTick(ServerLevel p_148331_, PathfinderMob entity) {
        Brain<?> brain = entity.getBrain();
        List<Player> list = p_148331_.players().stream().filter(EntitySelector.NO_SPECTATORS).filter((p_148342_) -> {
            return TEMPT_TARGETING.test(entity, p_148342_);
        }).filter((p_148335_) -> {
            return entity.closerThan(p_148335_, 10.0D);
        }).filter(this::playerHoldingTemptation).sorted(Comparator.comparingDouble(entity::distanceToSqr)).collect(Collectors.toList());
        if (!list.isEmpty()) {
            Player player = list.get(0);
            brain.setMemory(MemoryModuleType.TEMPTING_PLAYER, player);
        } else {
            brain.eraseMemory(MemoryModuleType.TEMPTING_PLAYER);
        }

    }

    private boolean playerHoldingTemptation(Player player) {
        return this.isTemptation(player.getMainHandItem()) || this.isTemptation(player.getOffhandItem());
    }

    private boolean isTemptation(ItemStack itemStack) {
        return this.temptCheck.test(itemStack);
    }

    public Set<MemoryModuleType<?>> requires() {
        return ImmutableSet.of(MemoryModuleType.TEMPTING_PLAYER);
    }
}
