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
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_MOOSHROOM;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_PIG;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_RABBIT;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_SHEEP;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class SpawnRegistry {

    //must be higher priority than removing
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void addBiomeSpawns(BiomeLoadingEvent event) {
        ArrayList<MobSpawnInfo.Spawners> addSpawns = new ArrayList<>();

        Iterator<MobSpawnInfo.Spawners> currentSpawns = event.getSpawns().getSpawner(EntityClassification.CREATURE).iterator();
        while (currentSpawns.hasNext()) {
            MobSpawnInfo.Spawners entry = currentSpawns.next();

            //add pigs
            if (entry.type == EntityType.PIG) {
                if(EanimodCommonConfig.COMMON.spawnGeneticPigs.get()) {
                    addSpawns.add(new MobSpawnInfo.Spawners(ENHANCED_PIG, 6, 2, 3));
                }
            }
            //add sheep
            if (entry.type == EntityType.SHEEP) {
                if (EanimodCommonConfig.COMMON.spawnGeneticSheep.get()) {
                    addSpawns.add(new MobSpawnInfo.Spawners(ENHANCED_SHEEP, 12, 4, 4));
                }
            }
            //add cow
            if (entry.type == EntityType.COW) {
                if(EanimodCommonConfig.COMMON.spawnGeneticCows.get()) {
                    addSpawns.add(new MobSpawnInfo.Spawners(ENHANCED_COW, 8, 4, 4));
                }
            }
            //add llama
            if (entry.type == EntityType.LLAMA) {
                if(EanimodCommonConfig.COMMON.spawnGeneticLlamas.get()) {
                    addSpawns.add(new MobSpawnInfo.Spawners(ENHANCED_LLAMA, 4, 2, 3));
                }
            }
            //add chicken
            if (entry.type == EntityType.CHICKEN) {
                if (EanimodCommonConfig.COMMON.spawnGeneticChickens.get()) {
                    addSpawns.add(new MobSpawnInfo.Spawners(ENHANCED_CHICKEN, 10, 4, 4));
                }
            }
            //add rabbit
            if (entry.type == EntityType.RABBIT) {
                if (EanimodCommonConfig.COMMON.spawnGeneticRabbits.get()) {
                    addSpawns.add(new MobSpawnInfo.Spawners(ENHANCED_RABBIT, 4, 2, 3));
                }
            }
            //add mooshroom
            if (entry.type == EntityType.MOOSHROOM) {
                if (EanimodCommonConfig.COMMON.spawnGeneticMooshroom.get()) {
                    addSpawns.add(new MobSpawnInfo.Spawners(ENHANCED_MOOSHROOM, 8, 4, 4));
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
                spawns.add(new MobSpawnInfo.Spawners(ENHANCED_RABBIT, 4, 2, 3));
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
            if (entry.type == EntityType.PIG) {
                if(!EanimodCommonConfig.COMMON.spawnVanillaPigs.get()) {
                    removeSpawns.add(entry);
                }
            }
            //remove vanilla sheep
            if (entry.type == EntityType.SHEEP) {
                if (!EanimodCommonConfig.COMMON.spawnVanillaSheep.get()) {
                    removeSpawns.add(entry);
                }
            }
            //remove vanilla cow
            if (entry.type == EntityType.COW) {
                if(!EanimodCommonConfig.COMMON.spawnVanillaCows.get()) {
                    removeSpawns.add(entry);
                }
            }
            //add and remove llama
            if (entry.type == EntityType.LLAMA) {
                if(!EanimodCommonConfig.COMMON.spawnVanillaLlamas.get()) {
                    removeSpawns.add(entry);
                }
            }
            //remove vanilla chicken
            if (entry.type == EntityType.CHICKEN) {
                if (!EanimodCommonConfig.COMMON.spawnVanillaChickens.get()) {
                    removeSpawns.add(entry);
                }
            }
            //remove vanilla rabbit
            if (entry.type == EntityType.RABBIT) {
                if (!EanimodCommonConfig.COMMON.spawnVanillaRabbits.get()) {
                    removeSpawns.add(entry);
                }
            }
            //remove vanilla mooshroom
            if (entry.type == EntityType.MOOSHROOM) {
                if (!EanimodCommonConfig.COMMON.spawnVanillaMooshroom.get()) {
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
