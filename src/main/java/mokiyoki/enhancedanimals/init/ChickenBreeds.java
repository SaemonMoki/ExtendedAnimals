package mokiyoki.enhancedanimals.init;

import mokiyoki.enhancedanimals.util.Breed;
import net.minecraft.world.biome.Biomes;

public class ChickenBreeds {
    public static final Breed Leghorn = new Breed(new Breed.Properties().setData("Leghorn", Biomes.PLAINS, Breed.Rarity.ORDINARY)
            .setVarieties("White,Brown,Buff,GoldDuckwing,SilverDuckwing,Exchequer,Blue,RoseComb")
            .setSexlinkedGenes("")
            .setAutosomalGenes(""));
    public static final Breed Wyandotte = new Breed(new Breed.Properties().setData("Wyandotte", Biomes.BIRCH_FOREST, Breed.Rarity.ORDINARY)
            .setVarieties("SilverLaced,GoldLaced,Blue,SilverPenciled")
            .setSexlinkedGenes("")
            .setAutosomalGenes(""));
    public static final Breed RhodeIslandRed = new Breed(new Breed.Properties().setData("RhodeIslandRed", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setSexlinkedGenes("")
            .setAutosomalGenes(""));
    public static final Breed PlymouthRock = new Breed(new Breed.Properties().setData("PlymouthRock", Biomes.TAIGA, Breed.Rarity.ORDINARY)
            .setSexlinkedGenes("")
            .setAutosomalGenes(""));


}
