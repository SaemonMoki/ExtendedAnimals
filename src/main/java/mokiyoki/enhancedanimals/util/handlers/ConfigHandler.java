package mokiyoki.enhancedanimals.util.handlers;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;


public class ConfigHandler {

    public static final CommonConfig COMMON;
    public static final ForgeConfigSpec COMMON_SPEC;
    static {
        final Pair<CommonConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    public static class CommonConfig {
        public final ForgeConfigSpec.BooleanValue spawnVanillaMobs;
        public final ForgeConfigSpec.IntValue wildTypeChance;

        public CommonConfig(ForgeConfigSpec.Builder builder) {
            builder.push("general");
            spawnVanillaMobs = builder
                    .comment("Set this to true to allow vanilla minecraft mobs to continue to spawn. False will prevent their spawning and only spawn Eanimod versions")
                    .define("spawnVanillaMobs.enabled", false);

            wildTypeChance = builder
                    .comment("How many ticks into the future will mana spreaders attempt to predict where mana bursts go? Setting this lower will improve spreader performance, but will cause them to not fire at targets that are too far away.")
                    .defineInRange("wildType.chance", 90, 1, Integer.MAX_VALUE);
            builder.pop();
        }
    }

}
