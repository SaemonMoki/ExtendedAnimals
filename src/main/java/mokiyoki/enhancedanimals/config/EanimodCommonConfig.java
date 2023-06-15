package mokiyoki.enhancedanimals.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;


public class EanimodCommonConfig {

    public static final CommonConfig COMMON;
    public static final ForgeConfigSpec COMMON_SPEC;
    static {
        final Pair<CommonConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    public static String getFileNameForLoader() {
        return "genetic-animals-common.toml";
    }

    public static ForgeConfigSpec getConfigSpecForLoader() {
        return COMMON_SPEC;
    }

    public static class CommonConfig {
        public final ForgeConfigSpec.BooleanValue omnigenders;
        public final ForgeConfigSpec.BooleanValue feedGrowth;
        public final ForgeConfigSpec.EnumValue<HungerConfigEnum> hungerScaling;
        public final ForgeConfigSpec.IntValue wildTypeChance;
        public final ForgeConfigSpec.BooleanValue tabsOnTop;
        public final ForgeConfigSpec.BooleanValue spawnWithRandomBiome;
        public final ForgeConfigSpec.BooleanValue onlyKilledWithAxe;

        public final ForgeConfigSpec.BooleanValue leatherWorkerTrades;
        public final ForgeConfigSpec.BooleanValue shepardTrades;
        public final ForgeConfigSpec.BooleanValue farmerTrades;
        public final ForgeConfigSpec.BooleanValue wanderingTraderTrades;
        public final ForgeConfigSpec.BooleanValue wanderingTraderChicken;
        public final ForgeConfigSpec.BooleanValue wanderingTraderPig;
        public final ForgeConfigSpec.BooleanValue wanderingTraderCow;
        public final ForgeConfigSpec.BooleanValue wanderingTraderSheep;
        public final ForgeConfigSpec.BooleanValue wanderingTraderRabbit;
        public final ForgeConfigSpec.BooleanValue wanderingTraderLlama;
        public final ForgeConfigSpec.BooleanValue wanderingTraderMooshroom;
        public final ForgeConfigSpec.BooleanValue wanderingTraderMoobloom;
        public final ForgeConfigSpec.BooleanValue wanderingTraderTurtle;
        public final ForgeConfigSpec.BooleanValue wanderingTraderAxolotl;

        public final ForgeConfigSpec.BooleanValue spawnVanillaPigs;
        public final ForgeConfigSpec.BooleanValue spawnGeneticPigs;
        public final ForgeConfigSpec.IntValue gestationDaysPig;
        public final ForgeConfigSpec.IntValue adultAgePig;
        public final ForgeConfigSpec.IntValue spawnWeightPigs;
        public final ForgeConfigSpec.IntValue minimumPigGroup;
        public final ForgeConfigSpec.IntValue maximumPigGroup;

        public final ForgeConfigSpec.BooleanValue spawnVanillaCows;
        public final ForgeConfigSpec.BooleanValue spawnGeneticCows;
        public final ForgeConfigSpec.IntValue gestationDaysCow;
        public final ForgeConfigSpec.IntValue adultAgeCow;
        public final ForgeConfigSpec.IntValue spawnWeightCows;
        public final ForgeConfigSpec.IntValue minimumCowGroup;
        public final ForgeConfigSpec.IntValue maximumCowGroup;

        public final ForgeConfigSpec.BooleanValue spawnVanillaMooshroom;
        public final ForgeConfigSpec.BooleanValue spawnGeneticMooshroom;
        public final ForgeConfigSpec.IntValue gestationDaysMooshroom;
        public final ForgeConfigSpec.IntValue adultAgeMooshroom;
        public final ForgeConfigSpec.IntValue spawnWeightMooshrooms;
        public final ForgeConfigSpec.IntValue minimumMooshroomGroup;
        public final ForgeConfigSpec.IntValue maximumMooshroomGroup;

        public final ForgeConfigSpec.BooleanValue spawnGeneticMoobloom;
        public final ForgeConfigSpec.IntValue gestationDaysMoobloom;
        public final ForgeConfigSpec.IntValue adultAgeMoobloom;

        public final ForgeConfigSpec.BooleanValue spawnVanillaLlamas;
        public final ForgeConfigSpec.BooleanValue spawnGeneticLlamas;
        public final ForgeConfigSpec.IntValue gestationDaysLlama;
        public final ForgeConfigSpec.IntValue adultAgeLlama;
        public final ForgeConfigSpec.IntValue spawnWeightLlamas;
        public final ForgeConfigSpec.IntValue minimumLlamaGroup;
        public final ForgeConfigSpec.IntValue maximumLlamaGroup;

        public final ForgeConfigSpec.BooleanValue spawnVanillaSheep;
        public final ForgeConfigSpec.BooleanValue spawnGeneticSheep;
        public final ForgeConfigSpec.IntValue gestationDaysSheep;
        public final ForgeConfigSpec.IntValue adultAgeSheep;
        public final ForgeConfigSpec.IntValue spawnWeightSheep;
        public final ForgeConfigSpec.IntValue minimumSheepGroup;
        public final ForgeConfigSpec.IntValue maximumSheepGroup;

        public final ForgeConfigSpec.BooleanValue spawnVanillaChickens;
        public final ForgeConfigSpec.BooleanValue spawnGeneticChickens;
        public final ForgeConfigSpec.IntValue incubationDaysChicken;
        public final ForgeConfigSpec.IntValue adultAgeChicken;
        public final ForgeConfigSpec.IntValue spawnWeightChickens;
        public final ForgeConfigSpec.IntValue minimumChickenGroup;
        public final ForgeConfigSpec.IntValue maximumChickenGroup;

        public final ForgeConfigSpec.BooleanValue spawnVanillaRabbits;
        public final ForgeConfigSpec.BooleanValue spawnGeneticRabbits;
        public final ForgeConfigSpec.IntValue gestationDaysRabbit;
        public final ForgeConfigSpec.IntValue adultAgeRabbit;
        public final ForgeConfigSpec.IntValue spawnWeightRabbits;
        public final ForgeConfigSpec.IntValue minimumRabbitGroup;
        public final ForgeConfigSpec.IntValue maximumRabbitGroup;

        public final ForgeConfigSpec.BooleanValue spawnVanillaTurtles;
        public final ForgeConfigSpec.BooleanValue spawnGeneticTurtles;
        public final ForgeConfigSpec.IntValue adultAgeTurtle;
        public final ForgeConfigSpec.IntValue turtleScuteDropAge;
        public final ForgeConfigSpec.IntValue spawnWeightTurtles;
        public final ForgeConfigSpec.IntValue minimumTurtleGroup;
        public final ForgeConfigSpec.IntValue maximumTurtleGroup;

        public final ForgeConfigSpec.BooleanValue spawnVanillaAxolotls;
        public final ForgeConfigSpec.BooleanValue spawnGeneticAxolotls;
        public final ForgeConfigSpec.IntValue adultAgeAxolotl;
        public final ForgeConfigSpec.IntValue axolotlHatchTime;
        public final ForgeConfigSpec.DoubleValue axolotlHatchTemperatureImportance;
        public final ForgeConfigSpec.IntValue spawnWeightAxolotls;
        public final ForgeConfigSpec.IntValue minimumAxolotlGroup;
        public final ForgeConfigSpec.IntValue maximumAxolotlGroup;
        
        public final ForgeConfigSpec.BooleanValue spawnVanillaHorses;
        public final ForgeConfigSpec.BooleanValue spawnGeneticHorses;
        public final ForgeConfigSpec.IntValue gestationDaysHorse;
        public final ForgeConfigSpec.IntValue adultAgeHorse;

        //Multipliers
        public final ForgeConfigSpec.DoubleValue milkMultiplier;
        public final ForgeConfigSpec.DoubleValue mushroomStewMultiplier;
        public final ForgeConfigSpec.DoubleValue woolMultiplierLlama;
        public final ForgeConfigSpec.DoubleValue woolMultiplierSheep;
        public final ForgeConfigSpec.DoubleValue woolMultiplierRabbit;
        public final ForgeConfigSpec.DoubleValue eggMultiplier;

        public CommonConfig(ForgeConfigSpec.Builder builder) {
            builder.push("general");
            omnigenders = builder
                    .comment("Set this to true to enable omnigenders, meaning the gender of the animal is irrelevant to who can fall pregnant, lay eggs, milk or sire young, False results in gendered animals, Visually nothing changes")
                    .define("Allow Omnigenders:", false);
            feedGrowth = builder
                    .define("Allow feeding to age/grow animals:", false);
            hungerScaling = builder
                    .defineEnum("How fast the animals get hungry, Values: RAVENOUS, MORE_HUNGRY, STANDARD, LESS_HUNGRY, NEVER_HUNGRY", HungerConfigEnum.STANDARD);
            wildTypeChance = builder
                    .defineInRange("How random the genes should be, 100 is all wildtype animals, 0 is completely random which often results in all white animals:", 90, 0, 100);
            tabsOnTop = builder
                    .define("Animal inventory tabs will be on the top if true and tabs will be on the side if false:", true);
            spawnWithRandomBiome = builder
                    .define("Animals will spawn with random biome type", false);
            onlyKilledWithAxe = builder
                    .define("Genetic animals are immune to all damage unless attacked with an axe by a player", false);
            builder.pop();

            builder.push("Trading");
            leatherWorkerTrades = builder
                    .comment("If true leather workers will have extra trades for some Genetic Animal's items")
                    .define("Extra leather worker trades", true);
            shepardTrades = builder
                    .comment("If true shepards will have extra trades for some Genetic Animal's items")
                    .define("Extra shepard trades", true);
            farmerTrades = builder
                    .comment("If true farmers will have extra trades for some Genetic Animal's items")
                    .define("Extra farmer trades", true);
            wanderingTraderTrades = builder
                    .comment("If true wandering traders will have extra trades for some Genetic Animal's items")
                    .define("Extra wandering traders trades", true);
            wanderingTraderChicken = builder
                    .comment("If true wandering traders have a chance to bring chickens to trade")
                    .define("Wandering traders sell chickens", true);
            wanderingTraderPig = builder
                    .comment("If true wandering traders have a chance to bring pigs to trade")
                    .define("Wandering traders sell pigs", true);
            wanderingTraderCow = builder
                    .comment("If true wandering traders have a chance to bring cows to trade")
                    .define("Wandering traders sell cows", true);
            wanderingTraderSheep = builder
                    .comment("If true wandering traders have a chance to bring sheep to trade")
                    .define("Wandering traders sell sheep", true);
            wanderingTraderRabbit = builder
                    .comment("If true wandering traders have a chance to bring rabbits to trade")
                    .define("Wandering traders sell rabbits", true);
            wanderingTraderLlama = builder
                    .comment("If true wandering traders have a chance to bring llamas to trade")
                    .define("Wandering traders sell llamas", true);
            wanderingTraderMooshroom = builder
                    .comment("If true wandering traders have a chance to bring mooshrooms to trade")
                    .define("Wandering traders sell mooshrooms", false);
            wanderingTraderMoobloom = builder
                    .comment("If true wandering traders have a chance to bring mooblooms to trade")
                    .define("Wandering traders sell mooblooms", false);
            wanderingTraderTurtle = builder
                    .comment("If true wandering traders have a chance to bring turtles to trade")
                    .define("Wandering traders sell turtles", true);
            wanderingTraderAxolotl = builder
                    .comment("If true wandering traders have a chance to bring axolotls to trade")
                    .define("Wandering traders sell axolotls", true);
            builder.pop();

            builder.push("pig");
            gestationDaysPig = builder
                .defineInRange("How many ticks it takes for a Pig to give birth, 24000 = 1 Minecraft Day:", 48000, 5, Integer.MAX_VALUE);
            adultAgePig = builder
                    .defineInRange("How many ticks it takes for a pig to become an adult, 24000 = 1 Minecraft Day:", 60000, 1, Integer.MAX_VALUE);
            spawnVanillaPigs = builder
                .define("Allow vanilla minecraft Pigs to spawn/exist:", false);
            spawnGeneticPigs = builder
                .define("Allow Genetic Pigs to continue to spawn/exist:", true);
            spawnWeightPigs = builder
                    .defineInRange("How highly pig spawning is weighted, larger numbers spawn more, Default is 6", 6, 1, 20);
            minimumPigGroup = builder
                    .defineInRange("The minimum number of pigs you want to find in a group at spawn, Default is 2", 2, 1, 60);
            maximumPigGroup = builder
                    .defineInRange("The maximum number of pigs you want to find in a group at spawn, Default is 3", 3, 1, 60);
            builder.pop();

            builder.push("cow");
            gestationDaysCow = builder
                .defineInRange("How many ticks it takes for a Cow to give birth, 24000 = 1 Minecraft Day:", 48000, 5, Integer.MAX_VALUE);
            adultAgeCow = builder
                    .defineInRange("How many ticks it takes for a cow to become an adult, 24000 = 1 Minecraft Day:", 84000, 1, Integer.MAX_VALUE);
            spawnVanillaCows = builder
                .define("Allow vanilla minecraft Cows to spawn/exist:", false);
            spawnGeneticCows = builder
                .define("Allow Genetic Cows to continue to spawn/exist:", true);
            spawnWeightCows = builder
                    .defineInRange("How highly cow spawning is weighted, larger numbers spawn more, Default is 8", 8, 1, 20);
            minimumCowGroup = builder
                    .defineInRange("The minimum number of cows you want to find in a group at spawn, Default is 4", 4, 1, 60);
            maximumCowGroup = builder
                    .defineInRange("The maximum number of cows you want to find in a group at spawn, Default is 4", 4, 1, 60);
            milkMultiplier = builder
                    .defineInRange("This number multiplies how fast a cow regains milk, Default is 1 for 1x speed", 1.0, 0.0001, 1000.0);
            builder.pop();

            builder.push("mooshroom");
            gestationDaysMooshroom = builder
                    .defineInRange("How many ticks it takes for a Mooshroom to give birth, 24000 = 1 Minecraft Day:", 48000, 5, Integer.MAX_VALUE);
            adultAgeMooshroom = builder
                    .defineInRange("How many ticks it takes for a Mooshroom to become an adult, 24000 = 1 Minecraft Day:", 84000, 1, Integer.MAX_VALUE);
            spawnVanillaMooshroom = builder
                    .define("Allow vanilla minecraft Mooshrooms to spawn/exist:", false);
            spawnGeneticMooshroom = builder
                    .define("Allow Genetic Mooshrooms to continue to spawn/exist:", true);
            spawnWeightMooshrooms = builder
                    .defineInRange("How highly Mooshroom spawning is weighted, larger numbers spawn more, Default is 8", 8, 1, 20);
            minimumMooshroomGroup = builder
                    .defineInRange("The minimum number of Mooshrooms you want to find in a group at spawn, Default is 4", 4, 1, 60);
            maximumMooshroomGroup = builder
                    .defineInRange("The maximum number of Mooshrooms you want to find in a group at spawn, Default is 4", 4, 1, 60);
            mushroomStewMultiplier = builder
                    .defineInRange("This number multiplies how fast a mooshroom regains mushroom stew, Default is 1 for 1x speed", 1.0, 0.0001, 1000.0);
            builder.pop();

            builder.push("moobloom");
            spawnGeneticMoobloom = builder
                    .define("Allow Genetic Mooblooms to replace Mooblooms added by other mods", false);
            gestationDaysMoobloom = builder
                    .defineInRange("How many ticks it takes for a Mooblooms to give birth, 24000 = 1 Minecraft Day:", 48000, 5, Integer.MAX_VALUE);
            adultAgeMoobloom = builder
                    .defineInRange("How many ticks it takes for a Mooblooms to become an adult, 24000 = 1 Minecraft Day:", 84000, 1, Integer.MAX_VALUE);
            builder.pop();

            builder.push("llama");
            spawnVanillaLlamas = builder
                    .define("Allow vanilla minecraft Llamas to spawn/exist:", false);
            spawnGeneticLlamas = builder
                    .define("Allow Genetic Llamas to continue to spawn/exist:", true);
            gestationDaysLlama = builder
                    .defineInRange("How many ticks it takes for a Llama to give birth, 24000 = 1 Minecraft Day:", 48000, 5, Integer.MAX_VALUE);
            adultAgeLlama = builder
                    .defineInRange("How many ticks it takes for a llama to become an adult, 24000 = 1 Minecraft Day:", 120000, 1, Integer.MAX_VALUE);
            spawnWeightLlamas = builder
                    .defineInRange("How highly llama spawning is weighted, larger numbers spawn more, Default is 4", 4, 1, 20);
            minimumLlamaGroup = builder
                    .defineInRange("The minimum number of llamas you want to find in a group at spawn, Default is 2", 2, 1, 60);
            maximumLlamaGroup = builder
                    .defineInRange("The maximum number of llamas you want to find in a group at spawn, Default is 3", 3, 1, 60);
            woolMultiplierLlama = builder
                    .defineInRange("This number multiplies how fast a llama regains its wool, Default is 1 for 1x speed", 1.0, 0.0001, 1000.0);
            builder.pop();

            builder.push("sheep");
            gestationDaysSheep = builder
                    .defineInRange("How many ticks it takes for a Sheep to give birth, 24000 = 1 Minecraft Day:", 48000, 5, Integer.MAX_VALUE);
            adultAgeSheep = builder
                    .defineInRange("How many ticks it takes for a Sheep to become an adult, 24000 = 1 Minecraft Day:", 72000, 1, Integer.MAX_VALUE);
            spawnVanillaSheep = builder
                    .define("Allow vanilla minecraft Sheep to spawn/exist:", false);
            spawnGeneticSheep = builder
                    .define("Allow Genetic Sheep to continue to spawn/exist:", true);
            spawnWeightSheep = builder
                    .defineInRange("How highly Sheep spawning is weighted, larger numbers spawn more, Default is 12", 12, 1, 20);
            minimumSheepGroup = builder
                    .defineInRange("The minimum number of Sheep you want to find in a group at spawn, Default is 4", 4, 1, 60);
            maximumSheepGroup = builder
                    .defineInRange("The maximum number of Sheep you want to find in a group at spawn, Default is 4", 4, 1, 60);
            woolMultiplierSheep = builder
                    .defineInRange("This number multiplies how fast a llama regains it's wool, Default is 1 for 1x speed", 1.0, 0.0001, 1000.0);
            builder.pop();

            builder.push("chicken");
            spawnVanillaChickens = builder
                    .define("Allow vanilla minecraft Chickens to spawn/exist:", false);
            spawnGeneticChickens = builder
                    .define("Allow Genetic Chickens to continue to spawn/exist:", true);
            incubationDaysChicken = builder
                    .defineInRange("How many ticks it takes for a Chicken egg to hatch, 24000 = 1 Minecraft Day:", 24000, 1, Integer.MAX_VALUE);
            adultAgeChicken = builder
                    .defineInRange("How many ticks it takes for a Chicken to become an adult, 24000 = 1 Minecraft Day:", 60000, 1, Integer.MAX_VALUE);
            spawnWeightChickens = builder
                    .defineInRange("How highly Chicken spawning is weighted, larger numbers spawn more, Default is 10", 10, 1, 20);
            minimumChickenGroup = builder
                    .defineInRange("The minimum number of Chicken you want to find in a group at spawn, Default is 4", 4, 1, 60);
            maximumChickenGroup = builder
                    .defineInRange("The maximum number of Chicken you want to find in a group at spawn, Default is 4", 4, 1, 60);
            eggMultiplier = builder
                    .defineInRange("This number multiplies how fast a chicken prepares to lay an egg, Default is 1 for 1x speed", 1.0, 0.0001, 1000.0);
            builder.pop();

            builder.push("rabbit");
            gestationDaysRabbit = builder
                    .defineInRange("How many ticks it takes for a Rabbit to give birth, 24000 = 1 Minecraft Day:", 24000, 5, Integer.MAX_VALUE);
            adultAgeRabbit = builder
                    .defineInRange("How many ticks it takes for a Rabbit to become an adult, 24000 = 1 Minecraft Day:", 48000, 1, Integer.MAX_VALUE);
            spawnVanillaRabbits = builder
                    .define("Allow vanilla minecraft Rabbits to spawn/exist:", false);
            spawnGeneticRabbits = builder
                    .define("Allow Genetic Rabbits to continue to spawn/exist:", true);
            spawnWeightRabbits = builder
                    .defineInRange("How highly Rabbit spawning is weighted, larger numbers spawn more, Default is 4", 4, 1, 20);
            minimumRabbitGroup = builder
                    .defineInRange("The minimum number of Rabbit you want to find in a group at spawn, Default is 2", 2, 1, 60);
            maximumRabbitGroup = builder
                    .defineInRange("The maximum number of Rabbit you want to find in a group at spawn, Default is 3", 3, 1, 60);
            woolMultiplierRabbit = builder
                    .defineInRange("This number multiplies how fast a rabbit with wool regains it's wool, Default is 1 for 1x speed", 1.0, 0.0001, 1000.0);
            builder.pop();

            builder.push("turtle");
            spawnVanillaTurtles = builder
                    .define("Allow vanilla minecraft Turtles to spawn/exist:", false);
            spawnGeneticTurtles = builder
                    .define("Allow Genetic Turtles to continue to spawn/exist:", true);
            adultAgeTurtle = builder
                    .defineInRange("How many ticks it takes for a Turtle to become an adult, 24000 = 1 Minecraft Day:", 120000, 1, Integer.MAX_VALUE);
            turtleScuteDropAge = builder
                    .defineInRange("How many ticks it takes for a Turtle to drop its scute, 24000 = 1 Minecraft Day:", 24000, 1, Integer.MAX_VALUE);
            spawnWeightTurtles = builder
                    .defineInRange("How highly Turtle spawning is weighted, larger numbers spawn more, Default is 6", 6, 1, 20);
            minimumTurtleGroup = builder
                    .defineInRange("The minimum number of Turtle you want to find in a group at spawn, Default is 1", 1, 1, 60);
            maximumTurtleGroup = builder
                    .defineInRange("The maximum number of Turtle you want to find in a group at spawn, Default is 5", 5, 1, 60);
            builder.pop();

            builder.push("horse");
            gestationDaysHorse = builder
                    .defineInRange("How many ticks it takes for a Horse to give birth, 24000 = 1 Minecraft Day:", 24000, 5, Integer.MAX_VALUE);
            adultAgeHorse = builder
                    .defineInRange("How many ticks it takes for a Horse to become an adult, 24000 = 1 Minecraft Day:", 24000, 1000, Integer.MAX_VALUE);
            spawnVanillaHorses = builder
                    .define("Allow vanilla minecraft Horses to spawn/exist:", false);
            spawnGeneticHorses = builder
                    .define("Allow Genetic Horse to continue to spawn/exist:", true);
            builder.pop();
            
            builder.push("axolotl");
            spawnVanillaAxolotls = builder
                    .define("Allow vanilla minecraft Axolotls to spawn/exist:", false);
            spawnGeneticAxolotls = builder
                    .define("Allow Genetic Axolotls to continue to spawn/exist:", true);
            adultAgeAxolotl = builder
                    .defineInRange("How many ticks it takes for an Axolotl to become an adult, 24000 = 1 Minecraft Day:", 60000, 1, Integer.MAX_VALUE);
            axolotlHatchTime = builder
                    .defineInRange("How long int ticks it takes an axolotl egg to hatch in temperate biomes, Default is 24000", 24000, 1, Integer.MAX_VALUE);
            axolotlHatchTemperatureImportance = builder
                    .defineInRange("How important temperature is to the hatching of axolotl eggs, A value of 0 means they will never hatch unless they are in a temperate or warmer environment, Currently not used", 1.0D, 0.0D, 100.0D);
            spawnWeightAxolotls = builder
                    .defineInRange("How highly Axolotl spawning is weighted, larger numbers spawn more, Default is 10", 10, 1, 20);
            minimumAxolotlGroup = builder
                    .defineInRange("The minimum number of Axolotls you want to find in a group at spawn, Default is 4", 4, 1, 60);
            maximumAxolotlGroup = builder
                    .defineInRange("The maximum number of Axolotls you want to find in a group at spawn, Default is 6", 6, 1, 60);
            builder.pop();
        }
    }

}
