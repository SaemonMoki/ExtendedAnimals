package mokiyoki.enhancedanimals.util.handlers;

import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_CHICKEN;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_COW;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_LLAMA;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_MOOBLOOM;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_MOOSHROOM;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_PIG;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_RABBIT;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_SHEEP;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_TURTLE;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class SpawnRegistry {

    //must be higher priority than removing
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void addBiomeSpawns(BiomeLoadingEvent event) {
        ArrayList<MobSpawnInfo.Spawners> addSpawns = new ArrayList<>();

        Iterator<MobSpawnInfo.Spawners> currentSpawns = event.getSpawns().getSpawner(EntityClassification.CREATURE).iterator();
        while (currentSpawns.hasNext()) {
            MobSpawnInfo.Spawners entry = currentSpawns.next();

            EanimodCommonConfig.CommonConfig config = EanimodCommonConfig.COMMON;

            //add pigs
            if (entry.type == EntityType.PIG) {
                if(config.spawnGeneticPigs.get()) {
                    addSpawns.add(new MobSpawnInfo.Spawners(ENHANCED_PIG, config.spawnWeightPigs.get(), config.minimumPigGroup.get(), config.maximumPigGroup.get()));
                }
            }
            //add sheep
            if (entry.type == EntityType.SHEEP) {
                if (config.spawnGeneticSheep.get()) {
                    addSpawns.add(new MobSpawnInfo.Spawners(ENHANCED_SHEEP, config.spawnWeightSheep.get(), config.minimumSheepGroup.get(), config.maximumSheepGroup.get()));
                }
            }
            //add cow
            if (entry.type == EntityType.COW) {
                if(config.spawnGeneticCows.get()) {
                    addSpawns.add(new MobSpawnInfo.Spawners(ENHANCED_COW,config.spawnWeightCows.get(), config.minimumCowGroup.get(), config.maximumCowGroup.get()));
                }
            }
            //add llama
            if (entry.type == EntityType.LLAMA) {
                if(config.spawnGeneticLlamas.get()) {
                    addSpawns.add(new MobSpawnInfo.Spawners(ENHANCED_LLAMA, config.spawnWeightLlamas.get(), config.minimumLlamaGroup.get(), config.maximumLlamaGroup.get()));
                }
            }
            //add chicken
            if (entry.type == EntityType.CHICKEN) {
                if (config.spawnGeneticChickens.get()) {
                    addSpawns.add(new MobSpawnInfo.Spawners(ENHANCED_CHICKEN, config.spawnWeightChickens.get(), config.minimumChickenGroup.get(), config.maximumChickenGroup.get()));
                }
            }
            //add rabbit
            if (entry.type == EntityType.RABBIT) {
                if (config.spawnGeneticRabbits.get()) {
                    addSpawns.add(new MobSpawnInfo.Spawners(ENHANCED_RABBIT, config.spawnWeightRabbits.get(), config.minimumRabbitGroup.get(), config.maximumRabbitGroup.get()));
                }
            }
            //add mooshroom
            if (entry.type == EntityType.MOOSHROOM) {
                if (config.spawnGeneticMooshroom.get()) {
                    addSpawns.add(new MobSpawnInfo.Spawners(ENHANCED_MOOSHROOM, config.spawnWeightMooshrooms.get(), config.minimumMooshroomGroup.get(), config.maximumMooshroomGroup.get()));
                }
            }
            //add moobloom
            if (entry.type.toString().contains("moobloom")) {
                if (config.spawnGeneticMoobloom.get()) {
                    addSpawns.add(new MobSpawnInfo.Spawners(ENHANCED_MOOBLOOM, entry.minCount, entry.maxCount, entry.itemWeight));
                }
            }
            if (entry.type == EntityType.TURTLE) {
                if(config.spawnGeneticTurtles.get()) {
                    addSpawns.add(new MobSpawnInfo.Spawners(ENHANCED_TURTLE, config.spawnWeightTurtles.get(), config.minimumTurtleGroup.get(), config.maximumTurtleGroup.get()));
                }
            }
        }

        // Add all to spawn
        List<MobSpawnInfo.Spawners> spawns = event.getSpawns().getSpawner(EntityClassification.CREATURE);

        if (!addSpawns.isEmpty()) {
            spawns.addAll(addSpawns);
        }

        //documentation says this miiiiight be null on super rare occurances
        if (event.getName() != null) {
            if (EanimodCommonConfig.COMMON.spawnGeneticRabbits.get() && (event.getName().equals(Biomes.SNOWY_MOUNTAINS.getRegistryName()) || event.getName().equals(Biomes.SNOWY_TAIGA_HILLS.getRegistryName()) || event.getName().equals(Biomes.SNOWY_TAIGA_MOUNTAINS.getRegistryName()) || event.getName().equals(Biomes.TAIGA_HILLS.getRegistryName()) || event.getName().equals(Biomes.TAIGA_MOUNTAINS.getRegistryName()) || event.getName().equals(Biomes.GIANT_TREE_TAIGA_HILLS.getRegistryName()))) {
                spawns.add(new MobSpawnInfo.Spawners(ENHANCED_RABBIT, EanimodCommonConfig.COMMON.spawnWeightRabbits.get(), EanimodCommonConfig.COMMON.minimumRabbitGroup.get(), EanimodCommonConfig.COMMON.maximumRabbitGroup.get()));
            }
        }

    }

    // And removing entity spawns should use NORMAL priority
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void removeBiomeSpawns(BiomeLoadingEvent event) {
        ArrayList<MobSpawnInfo.Spawners> removeSpawns = new ArrayList<>();

        Iterator<MobSpawnInfo.Spawners> currentSpawns = event.getSpawns().getSpawner(EntityClassification.CREATURE).iterator();
        while (currentSpawns.hasNext()) {
            MobSpawnInfo.Spawners entry = currentSpawns.next();

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

            //remove modded turtles?
            if (entry.type == EntityType.TURTLE && entry.type.toString().contains("turtle")) {
                if (!EanimodCommonConfig.COMMON.spawnVanillaTurtles.get()) {
                    removeSpawns.add(entry);
                }
            }
        }

        List<MobSpawnInfo.Spawners> spawns = event.getSpawns().getSpawner(EntityClassification.CREATURE);
        if (!removeSpawns.isEmpty()) {
            spawns.removeAll(removeSpawns);
        }
    }
}
