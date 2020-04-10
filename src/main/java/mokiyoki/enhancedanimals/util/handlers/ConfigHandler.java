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
        public final ForgeConfigSpec.BooleanValue spawnVanillaChickens;
        public final ForgeConfigSpec.BooleanValue spawnVanillaRabbits;
        public final ForgeConfigSpec.BooleanValue spawnVanillaSheep;
        public final ForgeConfigSpec.BooleanValue spawnVanillaLlamas;
        public final ForgeConfigSpec.BooleanValue spawnVanillaCows;
        public final ForgeConfigSpec.BooleanValue spawnVanillaMooshroom;
        public final ForgeConfigSpec.BooleanValue spawnVanillaPigs;
        public final ForgeConfigSpec.BooleanValue spawnVanillaHorses;
        public final ForgeConfigSpec.BooleanValue spawnVanillaCats;
        public final ForgeConfigSpec.BooleanValue spawnGeneticChickens;
        public final ForgeConfigSpec.BooleanValue spawnGeneticRabbits;
        public final ForgeConfigSpec.BooleanValue spawnGeneticSheep;
        public final ForgeConfigSpec.BooleanValue spawnGeneticLlamas;
        public final ForgeConfigSpec.BooleanValue spawnGeneticCows;
        public final ForgeConfigSpec.BooleanValue spawnGeneticMooshroom;
        public final ForgeConfigSpec.BooleanValue spawnGeneticPigs;
        public final ForgeConfigSpec.IntValue wildTypeChance;
//        public final ForgeConfigSpec.IntValue incubationDaysChicken;
        public final ForgeConfigSpec.IntValue gestationDaysRabbit;
        public final ForgeConfigSpec.IntValue gestationDaysCow;
        public final ForgeConfigSpec.IntValue gestationDaysSheep;
        public final ForgeConfigSpec.IntValue gestationDaysLlama;
        public final ForgeConfigSpec.IntValue gestationDaysPig;
        public final ForgeConfigSpec.IntValue gestationDaysHorse;
        public final ForgeConfigSpec.IntValue gestationDaysCat;

        public CommonConfig(ForgeConfigSpec.Builder builder) {
            builder.push("general");
            spawnVanillaChickens = builder
                    .comment("Set this to true to allow vanilla minecraft chickens to continue to spawn/exist. False will prevent vanilla chicken spawning and delete existing ones from the world.")
                    .define("spawnVanillaChickens.enabled", false);

            spawnVanillaRabbits = builder
                    .comment("Set this to true to allow vanilla minecraft rabbits to continue to spawn/exist. False will prevent vanilla rabbit spawning and delete existing ones from the world.")
                    .define("spawnVanillaRabbits.enabled", false);

            spawnVanillaSheep = builder
                    .comment("Set this to true to allow vanilla minecraft sheep to continue to spawn/exist. False will prevent vanilla sheep spawning and delete existing ones from the world.")
                    .define("spawnVanillaSheep.enabled", false);

            spawnVanillaLlamas = builder
                    .comment("Set this to true to allow vanilla minecraft llamas to continue to spawn/exist. False will prevent vanilla llama spawning and delete existing ones from the world, except for trader llamas")
                    .define("spawnVanillaLlamas.enabled", false);

            spawnVanillaCows = builder
                    .comment("Set this to true to allow vanilla minecraft cows to continue to spawn/exist. False will prevent vanilla cow spawning and delete existing ones from the world.")
                    .define("spawnVanillaCows.enabled", false);

            spawnVanillaMooshroom = builder
                    .comment("Set this to true to allow vanilla minecraft mooshroom to continue to spawn/exist. False will prevent vanilla mooshroom spawning and delete existing ones from the world.")
                    .define("spawnVanillaMooshroom.enabled", false);

            spawnVanillaPigs = builder
                    .comment("Set this to true to allow vanilla minecraft pigs to continue to spawn/exist. False will prevent vanilla pig spawning and delete existing ones from the world.")
                    .define("spawnVanillaPigs.enabled", false);

            spawnVanillaHorses = builder
                    .comment("Set this to true to allow vanilla minecraft horse to continue to spawn/exist. False will prevent vanilla horse spawning and delete existing ones from the world.")
                    .define("spawnVanillaHorse.enabled", true);

            spawnVanillaCats = builder
                    .comment("Set this to true to allow vanilla minecraft cats to continue to spawn/exist. False will prevent vanilla cat spawning and delete existing ones from the world.")
                    .define("spawnVanillaCats.enabled", true);

            spawnGeneticChickens = builder
                    .comment("Set this to true to allow genetic minecraft chickens to continue to spawn/exist. False will prevent genetic chicken spawning and delete existing ones from the world.")
                    .define("spawnGeneticChickens.enabled", true);

            spawnGeneticRabbits = builder
                    .comment("Set this to true to allow genetic minecraft rabbits to continue to spawn/exist. False will prevent genetic rabbit spawning and delete existing ones from the world.")
                    .define("spawnGeneticRabbits.enabled", true);

            spawnGeneticSheep = builder
                    .comment("Set this to true to allow genetic minecraft sheep to continue to spawn/exist. False will prevent genetic sheep spawning and delete existing ones from the world.")
                    .define("spawnGeneticSheep.enabled", true);

            spawnGeneticLlamas = builder
                    .comment("Set this to true to allow genetic minecraft llamas to continue to spawn/exist. False will prevent genetic llama spawning and delete existing ones from the world.")
                    .define("spawnGeneticLlamas.enabled", true);

            spawnGeneticCows = builder
                    .comment("Set this to true to allow genetic minecraft cows to continue to spawn/exist. False will prevent genetic cow spawning and delete existing ones from the world, if this is false genetic mooshroom will disappear when sheared")
                    .define("spawnGeneticCows.enabled", true);

            spawnGeneticMooshroom = builder
                    .comment("Set this to true to allow genetic minecraft mooshroom to continue to spawn/exist. False will prevent genetic mooshroom spawning and delete existing ones from the world.")
                    .define("spawnGeneticMooshroom.enabled", true);

            spawnGeneticPigs = builder
                    .comment("Set this to true to allow genetic minecraft pigs to continue to spawn/exist. False will prevent genetic pig spawning and delete existing ones from the world.")
                    .define("spawnGeneticPigs.enabled", true);

//            spawnGeneticHorses = builder
//                    .comment("Set this to true to allow genetic minecraft horse to continue to spawn/exist. False will prevent genetic horse spawning and delete existing ones from the world.")
//                    .define("spawnGeneticHorses.enabled", false);
//
//            spawnGeneticCats = builder
//                    .comment("Set this to true to allow genetic minecraft cats to continue to spawn/exist. False will prevent genetic cat spawning and delete existing ones from the world.")
//                    .define("spawnGeneticCats.enabled", false);

            wildTypeChance = builder
                    .comment(" 100 will make all animals spawn as wildtype for their biome, some mutations may be impossible to get. 0 makes animals spawn with almost completely random genes. higher numbers are best since you only need a few mutations to make a big difference")
                    .defineInRange("wildType.chance", 90, 0, 100);

//            incubationDaysChicken = builder
//                    .comment("Number of ticks for chickens to hatch, does nothing yet. 24000 = 1 Minecraft Day")
//                    .defineInRange("gestation.days", 24000, 1, Integer.MAX_VALUE);

            gestationDaysRabbit = builder
                    .comment("Number of ticks for rabbit gestation. Minimum time is 50 seconds. 24000 = 1 Minecraft Day")
                    .defineInRange("rabbitGestation.days", 24000, 1000, Integer.MAX_VALUE);

            gestationDaysCow = builder
                    .comment("Number of ticks for cow and mooshroom gestation. Minimum time is 50 seconds. 48000 = 2 Minecraft Days")
                    .defineInRange("cowGestation.days", 48000, 1000, Integer.MAX_VALUE);

            gestationDaysSheep = builder
                    .comment("Number of ticks for sheep gestation. Minimum time is 50 seconds. 48000 = 2 Minecraft Daysy")
                    .defineInRange("sheepGestation.days", 48000, 1000, Integer.MAX_VALUE);

            gestationDaysLlama = builder
                    .comment("Number of ticks for llama gestation. Minimum time is 50 seconds. 48000 = 2 Minecraft Days")
                    .defineInRange("llamaGestation.days", 48000, 1000, Integer.MAX_VALUE);

            gestationDaysPig = builder
                    .comment("Number of ticks for pig gestation. Minimum time is 50 seconds. 48000 = 2 Minecraft Days")
                    .defineInRange("pigGestation.days", 48000, 1000, Integer.MAX_VALUE);

            gestationDaysHorse = builder
                    .comment("Number of ticks for pig gestation. Minimum time is 50 seconds. 48000 = 2 Minecraft Days")
                    .defineInRange("horseGestation.days", 48000, 1000, Integer.MAX_VALUE);

            gestationDaysCat = builder
                    .comment("Number of ticks for pig gestation. Minimum time is 50 seconds. 48000 = 2 Minecraft Days")
                    .defineInRange("catGestation.days", 48000, 1000, Integer.MAX_VALUE);

            builder.pop();
        }
    }

}
