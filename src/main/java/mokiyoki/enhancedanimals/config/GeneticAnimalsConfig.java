package mokiyoki.enhancedanimals.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;


public class GeneticAnimalsConfig {

    public static final CommonConfig COMMON;
    public static final ModConfigSpec COMMON_SPEC;
    static {
        final Pair<CommonConfig, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(CommonConfig::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    public static String getFileNameForLoader() {
        return "genetic-animals-common.toml";
    }

    public static ModConfigSpec getConfigSpecForLoader() {
        return COMMON_SPEC;
    }

    public static class CommonConfig {
        public final ModConfigSpec.BooleanValue omnigenders;
        public final ModConfigSpec.BooleanValue feedGrowth;
        public final ModConfigSpec.EnumValue<HungerConfigEnum> hungerScaling;
        public final ModConfigSpec.IntValue wildTypeChance;
        public final ModConfigSpec.BooleanValue tabsOnTop;
        public final ModConfigSpec.EnumValue<IconStyleConfigEnum> fullIconStyle;
        public final ModConfigSpec.EnumValue<IconStyleConfigEnum> emptyIconStyle;
        public final ModConfigSpec.BooleanValue spawnWithRandomBiome;
        public final ModConfigSpec.BooleanValue onlyKilledWithAxe;
        public final ModConfigSpec.BooleanValue force16x;

        public final ModConfigSpec.BooleanValue leatherWorkerTrades;
        public final ModConfigSpec.BooleanValue shepardTrades;
        public final ModConfigSpec.BooleanValue farmerTrades;
        public final ModConfigSpec.BooleanValue wanderingTraderTrades;
        public final ModConfigSpec.BooleanValue wanderingTraderChicken;
        public final ModConfigSpec.BooleanValue wanderingTraderPig;
        public final ModConfigSpec.BooleanValue wanderingTraderCow;
        public final ModConfigSpec.BooleanValue wanderingTraderSheep;
        public final ModConfigSpec.BooleanValue wanderingTraderRabbit;
        public final ModConfigSpec.BooleanValue wanderingTraderLlama;
        public final ModConfigSpec.BooleanValue wanderingTraderMooshroom;
        public final ModConfigSpec.BooleanValue wanderingTraderMoobloom;
        public final ModConfigSpec.BooleanValue wanderingTraderTurtle;
        public final ModConfigSpec.BooleanValue wanderingTraderAxolotl;

        public final ModConfigSpec.BooleanValue spawnVanillaPigs;
        public final ModConfigSpec.BooleanValue spawnGeneticPigs;
        public final ModConfigSpec.IntValue gestationDaysPig;
        public final ModConfigSpec.IntValue adultAgePig;
        public final ModConfigSpec.IntValue spawnWeightPigs;
        public final ModConfigSpec.IntValue minimumPigGroup;
        public final ModConfigSpec.IntValue maximumPigGroup;

        public final ModConfigSpec.BooleanValue spawnVanillaCows;
        public final ModConfigSpec.BooleanValue spawnGeneticCows;
        public final ModConfigSpec.IntValue gestationDaysCow;
        public final ModConfigSpec.IntValue adultAgeCow;
        public final ModConfigSpec.IntValue spawnWeightCows;
        public final ModConfigSpec.IntValue minimumCowGroup;
        public final ModConfigSpec.IntValue maximumCowGroup;

        public final ModConfigSpec.BooleanValue spawnVanillaMooshroom;
        public final ModConfigSpec.BooleanValue spawnGeneticMooshroom;
        public final ModConfigSpec.IntValue gestationDaysMooshroom;
        public final ModConfigSpec.IntValue adultAgeMooshroom;
        public final ModConfigSpec.IntValue spawnWeightMooshrooms;
        public final ModConfigSpec.IntValue minimumMooshroomGroup;
        public final ModConfigSpec.IntValue maximumMooshroomGroup;

        public final ModConfigSpec.BooleanValue spawnGeneticMoobloom;
        public final ModConfigSpec.IntValue gestationDaysMoobloom;
        public final ModConfigSpec.IntValue adultAgeMoobloom;

        public final ModConfigSpec.BooleanValue spawnVanillaLlamas;
        public final ModConfigSpec.BooleanValue spawnGeneticLlamas;
        public final ModConfigSpec.IntValue gestationDaysLlama;
        public final ModConfigSpec.IntValue adultAgeLlama;
        public final ModConfigSpec.IntValue spawnWeightLlamas;
        public final ModConfigSpec.IntValue minimumLlamaGroup;
        public final ModConfigSpec.IntValue maximumLlamaGroup;

        public final ModConfigSpec.BooleanValue spawnVanillaSheep;
        public final ModConfigSpec.BooleanValue spawnGeneticSheep;
        public final ModConfigSpec.IntValue gestationDaysSheep;
        public final ModConfigSpec.IntValue adultAgeSheep;
        public final ModConfigSpec.IntValue spawnWeightSheep;
        public final ModConfigSpec.IntValue minimumSheepGroup;
        public final ModConfigSpec.IntValue maximumSheepGroup;

        public final ModConfigSpec.BooleanValue spawnVanillaChickens;
        public final ModConfigSpec.BooleanValue spawnGeneticChickens;
        public final ModConfigSpec.IntValue incubationDaysChicken;
        public final ModConfigSpec.IntValue fertilityTicksChicken;
        public final ModConfigSpec.DoubleValue chickenNestTeleportDistance;
        public final ModConfigSpec.BooleanValue chickensRemainOnNest;
        public final ModConfigSpec.IntValue nestDecayTime;
        public final ModConfigSpec.IntValue adultAgeChicken;
        public final ModConfigSpec.IntValue spawnWeightChickens;
        public final ModConfigSpec.IntValue minimumChickenGroup;
        public final ModConfigSpec.IntValue maximumChickenGroup;
        public final ModConfigSpec.BooleanValue allowRoostersToCrow;
        public final ModConfigSpec.IntValue minimumWaitForCrowTime;
        public final ModConfigSpec.IntValue maximumWaitForCrowTime;

        public final ModConfigSpec.BooleanValue spawnVanillaRabbits;
        public final ModConfigSpec.BooleanValue spawnGeneticRabbits;
        public final ModConfigSpec.IntValue gestationDaysRabbit;
        public final ModConfigSpec.IntValue adultAgeRabbit;
        public final ModConfigSpec.IntValue spawnWeightRabbits;
        public final ModConfigSpec.IntValue minimumRabbitGroup;
        public final ModConfigSpec.IntValue maximumRabbitGroup;

        public final ModConfigSpec.BooleanValue spawnVanillaTurtles;
        public final ModConfigSpec.BooleanValue spawnGeneticTurtles;
        public final ModConfigSpec.IntValue adultAgeTurtle;
        public final ModConfigSpec.IntValue turtleScuteDropAge;
        public final ModConfigSpec.IntValue spawnWeightTurtles;
        public final ModConfigSpec.IntValue minimumTurtleGroup;
        public final ModConfigSpec.IntValue maximumTurtleGroup;
        public final ModConfigSpec.IntValue turtleHatchingWindowStart;
        public final ModConfigSpec.IntValue turtleHatchingWindowEnd;
        public final ModConfigSpec.IntValue turtleDaytimeChanceToNotHatch;

        public final ModConfigSpec.BooleanValue spawnVanillaAxolotls;
        public final ModConfigSpec.BooleanValue spawnGeneticAxolotls;
        public final ModConfigSpec.IntValue adultAgeAxolotl;
        public final ModConfigSpec.IntValue axolotlHatchTime;
        public final ModConfigSpec.DoubleValue axolotlHatchTemperatureImportance;
        public final ModConfigSpec.IntValue spawnWeightAxolotls;
        public final ModConfigSpec.IntValue minimumAxolotlGroup;
        public final ModConfigSpec.IntValue maximumAxolotlGroup;
        
        public final ModConfigSpec.BooleanValue spawnVanillaHorses;
        public final ModConfigSpec.BooleanValue spawnGeneticHorses;
        public final ModConfigSpec.IntValue gestationDaysHorse;
        public final ModConfigSpec.IntValue adultAgeHorse;

        //Multipliers
        public final ModConfigSpec.DoubleValue milkMultiplier;
        public final ModConfigSpec.DoubleValue mushroomStewMultiplier;
        public final ModConfigSpec.DoubleValue woolMultiplierLlama;
        public final ModConfigSpec.DoubleValue woolMultiplierSheep;
        public final ModConfigSpec.DoubleValue woolMultiplierRabbit;
        public final ModConfigSpec.DoubleValue eggMultiplier;
        public final ModConfigSpec.IntValue eggThrowHatchChance;

        //PassageOfTime
        public final ModConfigSpec.BooleanValue passageOfTimeEnabled;
        public final ModConfigSpec.BooleanValue passageOfTimeChickenEnabled;
        public final ModConfigSpec.IntValue passageOfTimeChickenStages;
        public final ModConfigSpec.BooleanValue passageOfTimeChickenNoHatch;


        public CommonConfig(ModConfigSpec.Builder builder) {
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
            fullIconStyle = builder
                    .defineEnum("What icon set is used when an icon is displaying full: ", IconStyleConfigEnum.GREY);
            emptyIconStyle = builder
                    .defineEnum("What icon set is used when an icon is displaying empty: ", IconStyleConfigEnum.SILHOUETTE);
            spawnWithRandomBiome = builder
                    .define("Animals will spawn with random biome type", false);
            onlyKilledWithAxe = builder
                    .define("Genetic animals are immune to all damage unless attacked with an axe by a player", false);
            force16x = builder
                    .define("Force Genetic Animals to use a 16x base texture", false);
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
            builder.comment("Mooshrooms spawn only on Mycylium blocks by default, If you wish to change that you can do so by using a datapack to modify the mooshrooms_spawnable_on block tag");
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
            fertilityTicksChicken = builder
                    .defineInRange("How many ticks a chicken remains fertile for after being bred, 24000 = 1 Minecraft Day:", 96000, 1, Integer.MAX_VALUE);
            chickensRemainOnNest = builder
                    .define("If enabled hens never leave their nests even if they are hungry", false);
            nestDecayTime = builder
                    .defineInRange("How many ticks it takes before a nest could start to decay, this resets every time an eggs is laid or incubated", 24000, 1, Integer.MAX_VALUE);
            chickenNestTeleportDistance = builder
                    .defineInRange("How far away a chicken must be from the centre of the next to start teleporting to guarantee it is aligned, Default is 1 block", 1.0D, 0.5D, 10D);
            adultAgeChicken = builder
                    .defineInRange("How many ticks it takes for a Chicken to become an adult, 24000 = 1 Minecraft Day:", 60000, 1, Integer.MAX_VALUE);
            spawnWeightChickens = builder
                    .defineInRange("How highly Chicken spawning is weighted, larger numbers spawn more, Default is 10", 10, 1, 20);
            minimumChickenGroup = builder
                    .defineInRange("The minimum number of Chicken you want to find in a group at spawn, Default is 4", 4, 1, 60);
            maximumChickenGroup = builder
                    .defineInRange("The maximum number of Chicken you want to find in a group at spawn, Default is 4", 4, 1, 60);
            allowRoostersToCrow = builder
                    .define("Allow roosters to crow:", true);
            minimumWaitForCrowTime = builder
                    .defineInRange("The minimum number of ticks till a rooster might crow, Default is 100ticks", 100, 1, 24000);
            maximumWaitForCrowTime = builder
                    .defineInRange("The maximum number of ticks till a rooster might crow, Default is 6000ticks", 6000, 2, 24000);
            eggMultiplier = builder
                    .defineInRange("This number multiplies how fast a chicken prepares to lay an egg, Default is 1 for 1x speed", 1.0, 0.0001, 1000.0);
            eggThrowHatchChance = builder
                    .defineInRange("This number is a percentage chance for thrown eggs to hatch into chicks, Default is 15%", 15, 0, 100);
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
            turtleHatchingWindowStart = builder
                    .defineInRange("If the eggs are randomly ticked and the time is after this but before hatchingWindowEnd their hatch level will increase", 21600, 0, 24000);
            turtleHatchingWindowEnd = builder
                    .defineInRange("If the eggs are randomly ticked and the time is after hatchingWindowStart but before this their hatch level will increase", 22550, 0, 24000);
            turtleDaytimeChanceToNotHatch = builder
                    .defineInRange("The chance that eggs won't hatch if their block is ticked, default is 500, use 1 to guarantee they increment hatching", 500, 0, Integer.MAX_VALUE);

            builder.comment("Turtles only hatch on blocks in the sand tag by default, If you wish to change that you can do so by using a datapack to modify the sand block tag");
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
                    .defineInRange("How important temperature is to the hatching of axolotl eggs, a value of 0 means they will never hatch unless they are in a temperate or warmer environment, Currently not used", 1.0D, 0.0D, 100.0D);
            spawnWeightAxolotls = builder
                    .defineInRange("How highly Axolotl spawning is weighted, larger numbers spawn more, Default is 10", 10, 1, 20);
            minimumAxolotlGroup = builder
                    .defineInRange("The minimum number of Axolotls you want to find in a group at spawn, Default is 4", 4, 1, 60);
            maximumAxolotlGroup = builder
                    .defineInRange("The maximum number of Axolotls you want to find in a group at spawn, Default is 6", 6, 1, 60);
            builder.pop();

            builder.push("passageOfTime");
            passageOfTimeEnabled = builder
                    .define("Allow 'Passage of Time' feature, which allows animal pregnancy and similar features to progress after player time away from chunk", false);
            passageOfTimeChickenEnabled = builder
                    .define("Allow 'Passage of Time' feature for chickens", false);
            passageOfTimeChickenStages = builder
                    .defineInRange("How many 'stages' can pass for a chicken when it is loaded in, each of these is one stage: Make a nest, lay in the nest, hatch the nest, Default is 2 stages", 2, 1, 3);
            passageOfTimeChickenNoHatch = builder
                    .define("Disable hatching from passage of time regards of stages setting and time passed", false);
            builder.pop();
        }
    }

}
