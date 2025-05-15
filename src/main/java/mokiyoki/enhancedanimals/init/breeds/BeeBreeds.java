package mokiyoki.enhancedanimals.init.breeds;

import mokiyoki.enhancedanimals.util.Breed;
import mokiyoki.enhancedanimals.util.GeneSketch;
import net.minecraft.world.level.biome.Biomes;

public class BeeBreeds {
    public static final Breed CHOCOLATE = new Breed(new Breed.Properties().setData("chocolate", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch())
    );
    public static final Breed YELLOW = new Breed(new Breed.Properties().setData("yellow", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch())
    );
    public static final Breed BLUE = new Breed(new Breed.Properties().setData("blue", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch())
    );
    public static final Breed LONG_GUY = new Breed(new Breed.Properties().setData("longguy", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch().add(0, "4", "1", "4", "1","1","1"), new GeneSketch())
    );
    public static final Breed STUBBEE = new Breed(new Breed.Properties().setData("stubbee", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch().add(0, "1", "3", "1", "2", "2", "2"), new GeneSketch())
    );
}
