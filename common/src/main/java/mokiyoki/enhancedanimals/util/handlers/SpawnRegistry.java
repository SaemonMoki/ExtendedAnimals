package mokiyoki.enhancedanimals.util.handlers;

import org.jetbrains.annotations.Nullable;

import mokiyoki.enhancedanimals.config.GeneticAnimalsConfig;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import mokiyoki.enhancedanimals.platform.registry.RegistryEntry;
import mokiyoki.enhancedanimals.platform.registry.RegistrationProvider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_AXOLOTL;
import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_CHICKEN;
import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_COW;
import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_LLAMA;
import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_MOOBLOOM;
import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_MOOSHROOM;
import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_PIG;
import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_RABBIT;
import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_SHEEP;
import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_TURTLE;

public class SpawnRegistry {

    public interface BiomeSpawnEditor {
        @Nullable
        ResourceKey<Biome> biomeKey();

        List<MobSpawnSettings.SpawnerData> getSpawns(MobCategory category);

        void addSpawn(MobCategory category, MobSpawnSettings.SpawnerData spawn);

        void removeSpawn(MobCategory category, MobSpawnSettings.SpawnerData spawn);
    }

    public static void addSpawns(BiomeSpawnEditor builder) {
        Rules.addBiomeSpawns(builder);
    }

    public static void removeSpawns(BiomeSpawnEditor builder) {
        Rules.removeBiomeSpawns(builder);
    }

    private static final class Rules {
        private static void addBiomeSpawns(BiomeSpawnEditor builder) {
            ArrayList<MobSpawnSettings.SpawnerData> addSpawns = new ArrayList<>();

            Iterator<MobSpawnSettings.SpawnerData> currentSpawns = List.copyOf(builder.getSpawns(MobCategory.CREATURE)).iterator();
            while (currentSpawns.hasNext()) {
                MobSpawnSettings.SpawnerData entry = currentSpawns.next();

                GeneticAnimalsConfig.CommonConfig config = GeneticAnimalsConfig.COMMON;

                //add pigs
                if (entry.type == EntityType.PIG) {
                    if(config.spawnGeneticPigs.get()) {
                        addSpawns.add(new MobSpawnSettings.SpawnerData(ENHANCED_PIG.get(), config.spawnWeightPigs.get(), config.minimumPigGroup.get(), config.maximumPigGroup.get()));
                    }
                }
                //add sheep
                if (entry.type == EntityType.SHEEP) {
                    if (config.spawnGeneticSheep.get()) {
                        addSpawns.add(new MobSpawnSettings.SpawnerData(ENHANCED_SHEEP.get(), config.spawnWeightSheep.get(), config.minimumSheepGroup.get(), config.maximumSheepGroup.get()));
                    }
                }
                //add cow
                if (entry.type == EntityType.COW) {
                    if(config.spawnGeneticCows.get()) {
                        addSpawns.add(new MobSpawnSettings.SpawnerData(ENHANCED_COW.get(),config.spawnWeightCows.get(), config.minimumCowGroup.get(), config.maximumCowGroup.get()));
                    }
                }
                //add llama
                if (entry.type == EntityType.LLAMA) {
                    if(config.spawnGeneticLlamas.get()) {
                        addSpawns.add(new MobSpawnSettings.SpawnerData(ENHANCED_LLAMA.get(), config.spawnWeightLlamas.get(), config.minimumLlamaGroup.get(), config.maximumLlamaGroup.get()));
                    }
                }
                //add chicken
                if (entry.type == EntityType.CHICKEN) {
                    if (config.spawnGeneticChickens.get()) {
                        addSpawns.add(new MobSpawnSettings.SpawnerData(ENHANCED_CHICKEN.get(), config.spawnWeightChickens.get(), config.minimumChickenGroup.get(), config.maximumChickenGroup.get()));
                    }
                }
                //add rabbit
                if (entry.type == EntityType.RABBIT) {
                    if (config.spawnGeneticRabbits.get()) {
                        addSpawns.add(new MobSpawnSettings.SpawnerData(ENHANCED_RABBIT.get(), config.spawnWeightRabbits.get(), config.minimumRabbitGroup.get(), config.maximumRabbitGroup.get()));
                    }
                }
                //add mooshroom
                if (entry.type == EntityType.MOOSHROOM) {
                    if (config.spawnGeneticMooshroom.get()) {
                        addSpawns.add(new MobSpawnSettings.SpawnerData(ENHANCED_MOOSHROOM.get(), config.spawnWeightMooshrooms.get(), config.minimumMooshroomGroup.get(), config.maximumMooshroomGroup.get()));
                    }
                }
                //add moobloom
                if (entry.type.toString().contains("moobloom")) {
                    if (config.spawnGeneticMoobloom.get()) {
                        addSpawns.add(new MobSpawnSettings.SpawnerData(ENHANCED_MOOBLOOM.get(), entry.getWeight(), entry.minCount, entry.maxCount));
                    }
                }
                if (entry.type == EntityType.TURTLE) {
                    if(config.spawnGeneticTurtles.get()) {
                        addSpawns.add(new MobSpawnSettings.SpawnerData(ENHANCED_TURTLE.get(), config.spawnWeightTurtles.get(), config.minimumTurtleGroup.get(), config.maximumTurtleGroup.get()));
                    }
                }
                if (entry.type == EntityType.AXOLOTL) {
                    if(config.spawnGeneticAxolotls.get()) {
                        addSpawns.add(new MobSpawnSettings.SpawnerData(ENHANCED_AXOLOTL.get(), config.spawnWeightAxolotls.get(), config.minimumAxolotlGroup.get(), config.maximumAxolotlGroup.get()));
                    }
                }
            }

            // Add all to spawn
            addSpawns.forEach(spawn -> builder.addSpawn(MobCategory.CREATURE, spawn));

            ArrayList<MobSpawnSettings.SpawnerData> addAxolotlSpawns = new ArrayList<>();

            Iterator<MobSpawnSettings.SpawnerData> currentAxolotlSpawns = List.copyOf(builder.getSpawns(MobCategory.AXOLOTLS)).iterator();
            while (currentAxolotlSpawns.hasNext()) {
                MobSpawnSettings.SpawnerData entry = currentAxolotlSpawns.next();

                GeneticAnimalsConfig.CommonConfig config = GeneticAnimalsConfig.COMMON;

                if (entry.type == EntityType.AXOLOTL) {
                    if(config.spawnGeneticAxolotls.get()) {
                        addAxolotlSpawns.add(new MobSpawnSettings.SpawnerData(ENHANCED_AXOLOTL.get(), config.spawnWeightAxolotls.get(), config.minimumAxolotlGroup.get(), config.maximumAxolotlGroup.get()));
                    }
                }
            }

            addAxolotlSpawns.forEach(spawn -> builder.addSpawn(MobCategory.AXOLOTLS, spawn));

            ResourceLocation biomeName = builder.biomeKey() == null ? null : builder.biomeKey().location();
            //documentation says this miiiiight be null on super rare occurances
            if (biomeName != null) {
                if (GeneticAnimalsConfig.COMMON.spawnGeneticRabbits.get() && (biomeName.equals(Biomes.SNOWY_TAIGA.registry()) || biomeName.equals(Biomes.WINDSWEPT_HILLS.registry()))) {
                    builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ENHANCED_RABBIT.get(), GeneticAnimalsConfig.COMMON.spawnWeightRabbits.get(), GeneticAnimalsConfig.COMMON.minimumRabbitGroup.get(), GeneticAnimalsConfig.COMMON.maximumRabbitGroup.get()));
                }
            }

        }

        private static void removeBiomeSpawns(BiomeSpawnEditor builder) {
            ArrayList<MobSpawnSettings.SpawnerData> removeSpawns = new ArrayList<>();

            Iterator<MobSpawnSettings.SpawnerData> currentSpawns = List.copyOf(builder.getSpawns(MobCategory.CREATURE)).iterator();
            while (currentSpawns.hasNext()) {
                MobSpawnSettings.SpawnerData entry = currentSpawns.next();

                //remove vanilla pig
                if (entry.type == EntityType.PIG && entry.type.toString().contains("pig")) {
                    if(!GeneticAnimalsConfig.COMMON.spawnVanillaPigs.get()) {
                        removeSpawns.add(entry);
                    }
                }
                //remove vanilla sheep
                if (entry.type == EntityType.SHEEP && entry.type.toString().contains("sheep")) {
                    if (!GeneticAnimalsConfig.COMMON.spawnVanillaSheep.get()) {
                        removeSpawns.add(entry);
                    }
                }
                //remove vanilla cow
                if (entry.type == EntityType.COW && entry.type.toString().contains("cow")) {
                    if(!GeneticAnimalsConfig.COMMON.spawnVanillaCows.get()) {
                        removeSpawns.add(entry);
                    }
                }
                //add and remove llama
                if (entry.type == EntityType.LLAMA && entry.type.toString().contains("llama")) {
                    if(!GeneticAnimalsConfig.COMMON.spawnVanillaLlamas.get()) {
                        removeSpawns.add(entry);
                    }
                }
                //remove vanilla chicken
                if (entry.type == EntityType.CHICKEN && entry.type.toString().contains("chicken")) {
                    if (!GeneticAnimalsConfig.COMMON.spawnVanillaChickens.get()) {
                        removeSpawns.add(entry);
                    }
                }
                //remove vanilla rabbit
                if (entry.type == EntityType.RABBIT && entry.type.toString().contains("rabbit")) {
                    if (!GeneticAnimalsConfig.COMMON.spawnVanillaRabbits.get()) {
                        removeSpawns.add(entry);
                    }
                }
                //remove vanilla mooshroom
                if (entry.type == EntityType.MOOSHROOM && entry.type.toString().contains("mooshroom")) {
                    if (!GeneticAnimalsConfig.COMMON.spawnVanillaMooshroom.get()) {
                        removeSpawns.add(entry);
                    }
                }
                //remove modded mooblooms?
                if (entry.type.toString().contains("moobloom")) {
                    if (GeneticAnimalsConfig.COMMON.spawnGeneticMoobloom.get()) {
                        removeSpawns.add(entry);
                    }
                }

                //remove turtles?
                if (entry.type == EntityType.TURTLE && entry.type.toString().contains("turtle")) {
                    if (!GeneticAnimalsConfig.COMMON.spawnVanillaTurtles.get()) {
                        removeSpawns.add(entry);
                    }
                }
            }

            removeSpawns.forEach(spawn -> builder.removeSpawn(MobCategory.CREATURE, spawn));

            ArrayList<MobSpawnSettings.SpawnerData> removeAxolotlSpawns = new ArrayList<>();

            Iterator<MobSpawnSettings.SpawnerData> currentAxolotlSpawns = List.copyOf(builder.getSpawns(MobCategory.AXOLOTLS)).iterator();
            while (currentAxolotlSpawns.hasNext()) {
                MobSpawnSettings.SpawnerData entry = currentAxolotlSpawns.next();

                //remove axolotls
                if (entry.type == EntityType.AXOLOTL && entry.type.toString().contains("axolotl")) {
                    if (!GeneticAnimalsConfig.COMMON.spawnVanillaAxolotls.get()) {
                        removeAxolotlSpawns.add(entry);
                    }
                }
            }

            removeAxolotlSpawns.forEach(spawn -> builder.removeSpawn(MobCategory.AXOLOTLS, spawn));
        }
    }
}
