package mokiyoki.enhancedanimals.init;

import com.mojang.serialization.Codec;
import mokiyoki.enhancedanimals.config.GeneticAnimalsConfig;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static mokiyoki.enhancedanimals.GeneticAnimals.MODID;
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

@Mod.EventBusSubscriber(modid = MODID)
public class ModSpawns {
    public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_DEFERRED_REGISTRY = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, MODID);

    private static final RegistryObject<Codec<BiomeModifier>> BIOME_MODIFIER_SERIALIZER_REGISTRY = BIOME_MODIFIER_DEFERRED_REGISTRY.register("eanimod_spawn", () -> Codec.unit(BiomeSpawnModifier::new));

    public record BiomeSpawnModifier() implements BiomeModifier {
        @Override
        public void modify(Holder<Biome> biomeHolder, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
            if (phase == Phase.ADD) {
                addGeneticAnimals(biomeHolder, builder);
            }
            else if (phase == Phase.REMOVE) {
                removeVanillaAnimals(builder);
            }
        }

        private void addGeneticAnimals(Holder<Biome> biomeHolder, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
            ArrayList<SpawnerData> addSpawns = new ArrayList<>();

            Iterator<SpawnerData> currentSpawns = builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).iterator();
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
            List<MobSpawnSettings.SpawnerData> spawns = builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE);

            if (!addSpawns.isEmpty()) {
                spawns.addAll(addSpawns);
            }

            ArrayList<MobSpawnSettings.SpawnerData> addAxolotlSpawns = new ArrayList<>();

            Iterator<MobSpawnSettings.SpawnerData> currentAxolotlSpawns = builder.getMobSpawnSettings().getSpawner(MobCategory.AXOLOTLS).iterator();
            while (currentAxolotlSpawns.hasNext()) {
                MobSpawnSettings.SpawnerData entry = currentAxolotlSpawns.next();

                GeneticAnimalsConfig.CommonConfig config = GeneticAnimalsConfig.COMMON;

                if (entry.type == EntityType.AXOLOTL) {
                    if(config.spawnGeneticAxolotls.get()) {
                        addAxolotlSpawns.add(new MobSpawnSettings.SpawnerData(ENHANCED_AXOLOTL.get(), config.spawnWeightAxolotls.get(), config.minimumAxolotlGroup.get(), config.maximumAxolotlGroup.get()));
                    }
                }
            }

            List<MobSpawnSettings.SpawnerData> axolotlSpawns = builder.getMobSpawnSettings().getSpawner(MobCategory.AXOLOTLS);

            if (!addAxolotlSpawns.isEmpty()) {
                axolotlSpawns.addAll(addAxolotlSpawns);
            }

            if (biomeHolder.containsTag(Tags.Biomes.IS_SNOWY)) {
                spawns.add(new MobSpawnSettings.SpawnerData(ENHANCED_RABBIT.get(), GeneticAnimalsConfig.COMMON.spawnWeightRabbits.get(), GeneticAnimalsConfig.COMMON.minimumRabbitGroup.get(), GeneticAnimalsConfig.COMMON.maximumRabbitGroup.get()));
            }
        }

        private void removeVanillaAnimals(ModifiableBiomeInfo.BiomeInfo.Builder builder) {
            ArrayList<MobSpawnSettings.SpawnerData> removeSpawns = new ArrayList<>();

            Iterator<MobSpawnSettings.SpawnerData> currentSpawns = builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).iterator();
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

            List<MobSpawnSettings.SpawnerData> spawns = builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE);
            if (!removeSpawns.isEmpty()) {
                spawns.removeAll(removeSpawns);
            }

            ArrayList<MobSpawnSettings.SpawnerData> removeAxolotlSpawns = new ArrayList<>();

            Iterator<MobSpawnSettings.SpawnerData> currentAxolotlSpawns = builder.getMobSpawnSettings().getSpawner(MobCategory.AXOLOTLS).iterator();
            while (currentAxolotlSpawns.hasNext()) {
                MobSpawnSettings.SpawnerData entry = currentAxolotlSpawns.next();

                //remove axolotls
                if (entry.type == EntityType.AXOLOTL && entry.type.toString().contains("axolotl")) {
                    if (!GeneticAnimalsConfig.COMMON.spawnVanillaAxolotls.get()) {
                        removeAxolotlSpawns.add(entry);
                    }
                }
            }

            List<MobSpawnSettings.SpawnerData> axolotlSpawns = builder.getMobSpawnSettings().getSpawner(MobCategory.AXOLOTLS);
            if (!removeAxolotlSpawns.isEmpty()) {
                axolotlSpawns.removeAll(removeAxolotlSpawns);
            }
        }

        @Override
        public Codec<? extends BiomeModifier> codec() {
            return BIOME_MODIFIER_SERIALIZER_REGISTRY.get();
        }
    }

    public static void register(IEventBus modEventBus) {
        BIOME_MODIFIER_DEFERRED_REGISTRY.register(modEventBus);
    }
}
