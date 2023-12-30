package mokiyoki.enhancedanimals.util.handlers;

import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_FOX;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class SpawnRegistry {

    //must be higher priority than removing
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void addBiomeSpawns(BiomeLoadingEvent event) {
        ArrayList<MobSpawnSettings.SpawnerData> addSpawns = new ArrayList<>();

        Iterator<MobSpawnSettings.SpawnerData> currentSpawns = event.getSpawns().getSpawner(MobCategory.CREATURE).iterator();
        while (currentSpawns.hasNext()) {
            MobSpawnSettings.SpawnerData entry = currentSpawns.next();

            EanimodCommonConfig.CommonConfig config = EanimodCommonConfig.COMMON;

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
            //add fox
            if (entry.type == EntityType.FOX) {
                if(config.spawnGeneticFoxes.get()) {
                    addSpawns.add(new MobSpawnSettings.SpawnerData(ENHANCED_FOX.get(), config.spawnWeightFoxes.get(), config.minimumFoxGroup.get(), config.maximumFoxGroup.get()));
                }
            }
            //add axolotl
            if (entry.type == EntityType.AXOLOTL) {
                if(config.spawnGeneticAxolotls.get()) {
                    addSpawns.add(new MobSpawnSettings.SpawnerData(ENHANCED_AXOLOTL.get(), config.spawnWeightAxolotls.get(), config.minimumAxolotlGroup.get(), config.maximumAxolotlGroup.get()));
                }
            }
        }

        // Add all to spawn
        List<MobSpawnSettings.SpawnerData> spawns = event.getSpawns().getSpawner(MobCategory.CREATURE);

        if (!addSpawns.isEmpty()) {
            spawns.addAll(addSpawns);
        }

        ArrayList<MobSpawnSettings.SpawnerData> addAxolotlSpawns = new ArrayList<>();

        Iterator<MobSpawnSettings.SpawnerData> currentAxolotlSpawns = event.getSpawns().getSpawner(MobCategory.AXOLOTLS).iterator();
        while (currentAxolotlSpawns.hasNext()) {
            MobSpawnSettings.SpawnerData entry = currentAxolotlSpawns.next();

            EanimodCommonConfig.CommonConfig config = EanimodCommonConfig.COMMON;

            if (entry.type == EntityType.AXOLOTL) {
                if(config.spawnGeneticAxolotls.get()) {
                    addAxolotlSpawns.add(new MobSpawnSettings.SpawnerData(ENHANCED_AXOLOTL.get(), config.spawnWeightAxolotls.get(), config.minimumAxolotlGroup.get(), config.maximumAxolotlGroup.get()));
                }
            }
        }

        List<MobSpawnSettings.SpawnerData> axolotlSpawns = event.getSpawns().getSpawner(MobCategory.AXOLOTLS);

        if (!addAxolotlSpawns.isEmpty()) {
            axolotlSpawns.addAll(addAxolotlSpawns);
        }

        //documentation says this miiiiight be null on super rare occurances
        if (event.getName() != null) {
            if (EanimodCommonConfig.COMMON.spawnGeneticRabbits.get() && (event.getName().equals(Biomes.SNOWY_TAIGA.getRegistryName()) || event.getName().equals(Biomes.WINDSWEPT_HILLS.getRegistryName()))) {
                spawns.add(new MobSpawnSettings.SpawnerData(ENHANCED_RABBIT.get(), EanimodCommonConfig.COMMON.spawnWeightRabbits.get(), EanimodCommonConfig.COMMON.minimumRabbitGroup.get(), EanimodCommonConfig.COMMON.maximumRabbitGroup.get()));
            }
        }

    }

    // And removing entity spawns should use NORMAL priority
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void removeBiomeSpawns(BiomeLoadingEvent event) {
        ArrayList<MobSpawnSettings.SpawnerData> removeSpawns = new ArrayList<>();

        Iterator<MobSpawnSettings.SpawnerData> currentSpawns = event.getSpawns().getSpawner(MobCategory.CREATURE).iterator();
        while (currentSpawns.hasNext()) {
            MobSpawnSettings.SpawnerData entry = currentSpawns.next();

            //remove vanilla pig
            if (entry.type == EntityType.PIG && entry.type.toString().contains("pig")) {
                if(!EanimodCommonConfig.COMMON.spawnVanillaPigs.get()) {
                    removeSpawns.add(entry);
                }
            }
            //remove vanilla sheep
            if (entry.type == EntityType.SHEEP && entry.type.toString().contains("sheep")) {
                if (!EanimodCommonConfig.COMMON.spawnVanillaSheep.get()) {
                    removeSpawns.add(entry);
                }
            }
            //remove vanilla cow
            if (entry.type == EntityType.COW && entry.type.toString().contains("cow")) {
                if(!EanimodCommonConfig.COMMON.spawnVanillaCows.get()) {
                    removeSpawns.add(entry);
                }
            }
            //add and remove llama
            if (entry.type == EntityType.LLAMA && entry.type.toString().contains("llama")) {
                if(!EanimodCommonConfig.COMMON.spawnVanillaLlamas.get()) {
                    removeSpawns.add(entry);
                }
            }
            //remove vanilla chicken
            if (entry.type == EntityType.CHICKEN && entry.type.toString().contains("chicken")) {
                if (!EanimodCommonConfig.COMMON.spawnVanillaChickens.get()) {
                    removeSpawns.add(entry);
                }
            }
            //remove vanilla rabbit
            if (entry.type == EntityType.RABBIT && entry.type.toString().contains("rabbit")) {
                if (!EanimodCommonConfig.COMMON.spawnVanillaRabbits.get()) {
                    removeSpawns.add(entry);
                }
            }
            //remove vanilla mooshroom
            if (entry.type == EntityType.MOOSHROOM && entry.type.toString().contains("mooshroom")) {
                if (!EanimodCommonConfig.COMMON.spawnVanillaMooshroom.get()) {
                    removeSpawns.add(entry);
                }
            }
            //remove modded mooblooms?
            if (entry.type.toString().contains("moobloom")) {
                if (EanimodCommonConfig.COMMON.spawnGeneticMoobloom.get()) {
                    removeSpawns.add(entry);
                }
            }
            //remove vanilla fox
            if (entry.type == EntityType.FOX&& entry.type.toString().contains("fox")) {
                if(!EanimodCommonConfig.COMMON.spawnVanillaFoxes.get()) {
                    removeSpawns.add(entry);
                }
            }
            //remove turtles?
            if (entry.type == EntityType.TURTLE && entry.type.toString().contains("turtle")) {
                if (!EanimodCommonConfig.COMMON.spawnVanillaTurtles.get()) {
                    removeSpawns.add(entry);
                }
            }
        }

        List<MobSpawnSettings.SpawnerData> spawns = event.getSpawns().getSpawner(MobCategory.CREATURE);
        if (!removeSpawns.isEmpty()) {
            spawns.removeAll(removeSpawns);
        }

        ArrayList<MobSpawnSettings.SpawnerData> removeAxolotlSpawns = new ArrayList<>();

        Iterator<MobSpawnSettings.SpawnerData> currentAxolotlSpawns = event.getSpawns().getSpawner(MobCategory.AXOLOTLS).iterator();
        while (currentAxolotlSpawns.hasNext()) {
            MobSpawnSettings.SpawnerData entry = currentAxolotlSpawns.next();

            //remove axolotls
            if (entry.type == EntityType.AXOLOTL && entry.type.toString().contains("axolotl")) {
                if (!EanimodCommonConfig.COMMON.spawnVanillaAxolotls.get()) {
                    removeAxolotlSpawns.add(entry);
                }
            }
        }

        List<MobSpawnSettings.SpawnerData> axolotlSpawns = event.getSpawns().getSpawner(MobCategory.AXOLOTLS);
        if (!removeAxolotlSpawns.isEmpty()) {
            axolotlSpawns.removeAll(removeAxolotlSpawns);
        }
    }
}
