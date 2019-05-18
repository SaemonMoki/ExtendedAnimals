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
        public final ForgeConfigSpec.IntValue gestationDays;

        public CommonConfig(ForgeConfigSpec.Builder builder) {
            builder.push("general");
            spawnVanillaMobs = builder
                    .comment("Set this to true to allow vanilla minecraft mobs to continue to spawn. False will prevent their spawning and only spawn Eanimod versions")
                    .define("spawnVanillaMobs.enabled", false);

            wildTypeChance = builder
                    .comment(" 100 will make all animals spawn as wildtype for their biome, some mutations may be impossible to get. 0 makes animals spawn with almost completely random genes. higher numbers are best since you only need a few mutations to make a big difference")
                    .defineInRange("wildType.chance", 90, 1, Integer.MAX_VALUE);

            gestationDays = builder
                    .comment("Number of ticks for animal gestation. 24000 = 1 Minecraft Day")
                    .defineInRange("wildType.chance", 24000, 1, Integer.MAX_VALUE);
            builder.pop();
        }
    }

}
