package mokiyoki.enhancedanimals.fabric;

import mokiyoki.enhancedanimals.util.handlers.SpawnRegistry;
import net.fabricmc.fabric.api.biome.v1.BiomeModificationContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

final class FabricBiomeSpawnEditor implements SpawnRegistry.BiomeSpawnEditor {

    private final BiomeSelectionContext selection;
    private final BiomeModificationContext.SpawnSettingsContext spawnSettings;
    private final Map<MobCategory, List<MobSpawnSettings.SpawnerData>> spawns = new EnumMap<>(MobCategory.class);

    FabricBiomeSpawnEditor(BiomeSelectionContext selection, BiomeModificationContext.SpawnSettingsContext spawnSettings) {
        this.selection = selection;
        this.spawnSettings = spawnSettings;
    }

    private List<MobSpawnSettings.SpawnerData> spawnList(MobCategory category) {
        return this.spawns.computeIfAbsent(category,
                c -> new ArrayList<>(this.selection.getBiome().getMobSettings().getMobs(c).unwrap()));
    }

    @Override
    public @Nullable ResourceKey<Biome> biomeKey() {
        return this.selection.getBiomeKey();
    }

    @Override
    public List<MobSpawnSettings.SpawnerData> getSpawns(MobCategory category) {
        return Collections.unmodifiableList(spawnList(category));
    }

    @Override
    public void addSpawn(MobCategory category, MobSpawnSettings.SpawnerData spawn) {
        spawnList(category).add(spawn);
        this.spawnSettings.addSpawn(category, spawn);
    }

    @Override
    public void removeSpawn(MobCategory category, MobSpawnSettings.SpawnerData spawn) {
        spawnList(category).remove(spawn);
        this.spawnSettings.removeSpawns((spawnCategory, data) -> spawnCategory == category && data == spawn);
    }
}
