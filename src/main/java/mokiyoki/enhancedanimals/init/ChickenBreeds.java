package mokiyoki.enhancedanimals.init;

import mokiyoki.enhancedanimals.util.Breed;
import net.minecraft.world.biome.Biomes;

public class ChickenBreeds {
    public static final Breed LEGHORN = new Breed(new Breed.Properties().setData("Leghorn", Biomes.PLAINS, Breed.Rarity.ORDINARY)
            .setVarieties("White,Brown,Buff,GoldDuckwing,SilverDuckwing,Exchequer,Blue,RoseComb")
            .setGenes(new Breed.Genes().setGenes("", "")));
    public static final Breed WYANDOTTE = new Breed(new Breed.Properties().setData("WYANDOTTE", Biomes.BIRCH_FOREST, Breed.Rarity.ORDINARY)
            .setVarieties("SilverLaced,GoldLaced,Blue,SilverPenciled")
            .setGenes(new Breed.Genes().setGenes("", "")));
    public static final Breed RHODE_ISLAND_RED = new Breed(new Breed.Properties().setData("RHODE_ISLAND_RED", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setGenes(new Breed.Genes().setGenes("", "")));
    public static final Breed PLYMOUTH_ROCK = new Breed(new Breed.Properties().setData("PLYMOUTH_ROCK", Biomes.TAIGA, Breed.Rarity.ORDINARY)
            .setGenes(new Breed.Genes().setGenes("", "")));
    public static final Breed ORPINGTON = new Breed(new Breed.Properties().setData("PLYMOUTH_ROCK", Biomes.SWAMP, Breed.Rarity.ORDINARY)
            .setVarieties("Chocolate,Blue,Buff,White,Lavender,Platinum,GoldLaced,Black")
            .setGenes(new Breed.Genes().setGenes("", "")));
}
