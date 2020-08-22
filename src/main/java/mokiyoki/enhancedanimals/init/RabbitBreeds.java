package mokiyoki.enhancedanimals.init;

import mokiyoki.enhancedanimals.util.Breed;
import mokiyoki.enhancedanimals.util.GeneSketch;
import net.minecraft.world.biome.Biomes;

public class RabbitBreeds {
    public static final Breed ANGORA = new Breed(new Breed.Properties().setData("Angora", Biomes.MOUNTAINS, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch()));
}
