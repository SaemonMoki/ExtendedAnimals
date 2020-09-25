package mokiyoki.enhancedanimals.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;


public class EanimodCommonConfig implements IEanimodConfig{

    public static final CommonConfig COMMON;
    public static final ForgeConfigSpec COMMON_SPEC;
    static {
        final Pair<CommonConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    @Override
    public String getFileName() {
        return "genetic-animals-common";
    }

    @Override
    public ForgeConfigSpec getConfigSpec() {
        return COMMON_SPEC;
    }

    @Override
    public ModConfig.Type getConfigType() {
        return ModConfig.Type.SERVER;
    }

    public static class CommonConfig {
        public final ForgeConfigSpec.BooleanValue omnigenders;
        public final ForgeConfigSpec.BooleanValue feedGrowth;
        public final ForgeConfigSpec.BooleanValue onlyEatsWhenHungry;
        public final ForgeConfigSpec.EnumValue<HungerConfigEnum> hungerScaling;
        public final ForgeConfigSpec.IntValue wildTypeChance;
        public final ForgeConfigSpec.BooleanValue tabsOnTop;

        public final ForgeConfigSpec.BooleanValue spawnVanillaPigs;
        public final ForgeConfigSpec.BooleanValue spawnGeneticPigs;
        public final ForgeConfigSpec.IntValue gestationDaysPig;

        public final ForgeConfigSpec.BooleanValue spawnVanillaCows;
        public final ForgeConfigSpec.BooleanValue spawnVanillaMooshroom;
        public final ForgeConfigSpec.BooleanValue spawnGeneticCows;
        public final ForgeConfigSpec.BooleanValue spawnGeneticMooshroom;
        public final ForgeConfigSpec.IntValue gestationDaysCow;

        public final ForgeConfigSpec.BooleanValue spawnVanillaLlamas;
        public final ForgeConfigSpec.BooleanValue spawnGeneticLlamas;
        public final ForgeConfigSpec.IntValue gestationDaysLlama;

        public final ForgeConfigSpec.BooleanValue spawnVanillaSheep;
        public final ForgeConfigSpec.BooleanValue spawnGeneticSheep;
        public final ForgeConfigSpec.IntValue gestationDaysSheep;

        public final ForgeConfigSpec.BooleanValue spawnVanillaChickens;
        public final ForgeConfigSpec.BooleanValue spawnGeneticChickens;

        public final ForgeConfigSpec.BooleanValue spawnVanillaRabbits;
        public final ForgeConfigSpec.BooleanValue spawnGeneticRabbits;
        public final ForgeConfigSpec.IntValue gestationDaysRabbit;

        public CommonConfig(ForgeConfigSpec.Builder builder) {
            builder.push("general");
            omnigenders = builder
                    .comment("Set this to true to enable omnigenders, meaning the gender of the animal is irrelevant to who can fall pregnant, lay eggs, milk or sire young. False results in gendered animals. Visually nothing changes.")
                    .define("Allow Omnigenders:", false);
            feedGrowth = builder
                    .define("Allow feeding to age/grow animals:", false);
            onlyEatsWhenHungry = builder
                    .define("Animals only eat from players when hungry:", true);
            hungerScaling = builder
                    .defineEnum("How fast the animals get hungry, Values: RAVENOUS, MORE_HUNGRY, STANDARD, LESS_HUNGRY, NEVER_HUNGRY", HungerConfigEnum.STANDARD);
            wildTypeChance = builder
                    .defineInRange("How random the genes should be, 100 is all wildtype animals, 0 is completely random:", 90, 0, 100);
            tabsOnTop = builder
                    .define("Animal inventory tabs will be on the top instead of side:", true);
            builder.pop();

            builder.push("pig");
            gestationDaysPig = builder
                .defineInRange("How many ticks it takes for a Pig to give birth, 24000 = 1 Minecraft Day:", 48000, 1000, Integer.MAX_VALUE);
            spawnVanillaPigs = builder
                .define("Allow vanilla minecraft Pigs to spawn/exist:", false);
            spawnGeneticPigs = builder
                .define("Allow Genetic Pigs to continue to spawn/exist:", true);
            builder.pop();

            builder.push("cow");
            gestationDaysCow = builder
                .defineInRange("How many ticks it takes for a Cow to give birth, 24000 = 1 Minecraft Day:", 48000, 1000, Integer.MAX_VALUE);
            spawnVanillaCows = builder
                .define("Allow vanilla minecraft Cows to spawn/exist:", false);
            spawnGeneticCows = builder
                .define("Allow Genetic Cows to continue to spawn/exist:", true);
            spawnVanillaMooshroom = builder
                .define("Allow vanilla minecraft Mooshrooms to spawn/exist:", false);
            spawnGeneticMooshroom = builder
                .define("Allow Genetic Mooshrooms to continue to spawn/exist:", true);
            builder.pop();

            builder.push("llama");
            gestationDaysLlama = builder
                    .defineInRange("How many ticks it takes for a Llama to give birth, 24000 = 1 Minecraft Day:", 48000, 1000, Integer.MAX_VALUE);
            spawnVanillaLlamas = builder
                    .define("Allow vanilla minecraft Llamas to spawn/exist:", false);
            spawnGeneticLlamas = builder
                    .define("Allow Genetic Llamas to continue to spawn/exist:", true);
            builder.pop();

            builder.push("sheep");
            gestationDaysSheep = builder
                    .defineInRange("How many ticks it takes for a Rabbit to give birth, 24000 = 1 Minecraft Day:", 48000, 1000, Integer.MAX_VALUE);
            spawnVanillaSheep = builder
                    .define("Allow vanilla minecraft Sheep to spawn/exist:", false);
            spawnGeneticSheep = builder
                    .define("Allow Genetic Sheep to continue to spawn/exist:", true);
            builder.pop();

            builder.push("chicken");
            spawnVanillaChickens = builder
                    .define("Allow vanilla minecraft Chickens to spawn/exist:", false);
            spawnGeneticChickens = builder
                    .define("Allow Genetic Chickens to continue to spawn/exist:", true);
            builder.pop();

            builder.push("rabbit");
            gestationDaysRabbit = builder
                    .defineInRange("How many ticks it takes for a rabbit to give birth, 24000 = 1 Minecraft Day:", 24000, 1000, Integer.MAX_VALUE);
            spawnVanillaRabbits = builder
                    .define("Allow vanilla minecraft Rabbits to spawn/exist:", false);
            spawnGeneticRabbits = builder
                    .define("Allow Genetic Rabbits to continue to spawn/exist:", true);
            builder.pop();
        }
    }

}
