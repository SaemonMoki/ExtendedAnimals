package mokiyoki.enhancedanimals.neoforge;

import com.mojang.serialization.MapCodec;
import mokiyoki.enhancedanimals.util.Reference;
import mokiyoki.enhancedanimals.util.handlers.SpawnRegistry;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class NeoForgeBiomeModifiers {

    private static final DeferredRegister<MapCodec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, Reference.MODID);

    public static final DeferredHolder<MapCodec<? extends BiomeModifier>, MapCodec<BiomeSpawnModifier>> BIOME_SPAWN_MODIFIER = BIOME_MODIFIER_SERIALIZERS.register("eanimod_spawn", () -> MapCodec.unit(BiomeSpawnModifier::new));

    private NeoForgeBiomeModifiers() {
    }

    public static void register(IEventBus modEventBus) {
        BIOME_MODIFIER_SERIALIZERS.register(modEventBus);
    }

    public record BiomeSpawnModifier() implements BiomeModifier {
        @Override
        public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
            if (phase == Phase.ADD) {
                SpawnRegistry.addSpawns(new Editor(biome, builder));
            } else if (phase == Phase.REMOVE) {
                SpawnRegistry.removeSpawns(new Editor(biome, builder));
            }
        }

        @Override
        public MapCodec<? extends BiomeModifier> codec() {
            return BIOME_SPAWN_MODIFIER.get();
        }
    }

    private record Editor(Holder<Biome> biome, ModifiableBiomeInfo.BiomeInfo.Builder builder) implements SpawnRegistry.BiomeSpawnEditor {
        @Override
        public @Nullable ResourceKey<Biome> biomeKey() {
            return this.biome.unwrapKey().orElse(null);
        }

        @Override
        public List<MobSpawnSettings.SpawnerData> getSpawns(MobCategory category) {
            return this.builder.getMobSpawnSettings().getSpawner(category);
        }

        @Override
        public void addSpawn(MobCategory category, MobSpawnSettings.SpawnerData spawn) {
            this.builder.getMobSpawnSettings().getSpawner(category).add(spawn);
        }

        @Override
        public void removeSpawn(MobCategory category, MobSpawnSettings.SpawnerData spawn) {
            this.builder.getMobSpawnSettings().getSpawner(category).remove(spawn);
        }
    }
}
