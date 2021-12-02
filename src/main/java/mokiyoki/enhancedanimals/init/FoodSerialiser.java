package mokiyoki.enhancedanimals.init;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class FoodSerialiser {

    public static final AnimalFoodMap chickenFoodMap = createFoodMap("Chicken");
    public static final AnimalFoodMap llamaFoodMap = createFoodMap("Llama");
    public static final AnimalFoodMap rabbitFoodMap = createFoodMap("Rabbit");
    public static final AnimalFoodMap sheepFoodMap = createFoodMap("Sheep");
    public static final AnimalFoodMap cowFoodMap = createFoodMap("Cow");
    public static final AnimalFoodMap pigFoodMap = createFoodMap("Pig");
    public static final AnimalFoodMap horseFoodMap = createFoodMap("Horse");
    public static final AnimalFoodMap turtleFoodMap = createFoodMap("Turtle");

    public static AnimalFoodMap createFoodMap(String animal) {
        AnimalFoodMap animalFoodMap = new AnimalFoodMap();
        Gson gson = new Gson();

        checkFoodConfigFile();

        loadFoodMappingFile(animal, animalFoodMap, gson);

        return animalFoodMap;
    }

    protected static void loadFoodMappingFile(String animal, AnimalFoodMap animalFoodMap, Gson gson) {
        try {
            FileInputStream configPath = new FileInputStream(FMLPaths.CONFIGDIR.get().toString()+"\\genetic_animals\\food_items.json");

            InputStreamReader in_strm = new InputStreamReader(configPath);

            BufferedReader reader = new BufferedReader(in_strm);
            JsonElement je = gson.fromJson(reader, JsonElement.class);
            JsonObject json = je.getAsJsonObject();

            for (JsonElement animalElement : json.getAsJsonArray("animals")) {
                if (animalElement.getAsJsonObject().get("type").getAsString().equals(animal)) {

                    List<AnimalFoodMap.FoodMap> foodMapList = new ArrayList<>();

                    for (JsonElement foodElement : animalElement.getAsJsonObject().getAsJsonArray("food")) {
                        JsonObject foodObject = foodElement.getAsJsonObject();
                        AnimalFoodMap.FoodMap foodMap = new AnimalFoodMap.FoodMap();

                        if (foodObject.get("tag") != null) {
                            foodMap.tag = foodObject.get("tag").getAsString();
                        }
                        if (foodObject.get("registryName") != null) {
                            foodMap.registryName = foodObject.get("registryName").getAsString();
                        }
                        foodMap.hungerRestored = foodObject.get("hungerRestored").getAsInt();
                        foodMap.isBreedingItem = foodObject.get("isBreedingItem").getAsBoolean();

                        foodMapList.add(foodMap);
                    }

                    animalFoodMap.setFoodMapList(foodMapList);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void checkFoodConfigFile() {
        if (!new File(FMLPaths.CONFIGDIR.get().toString()+"\\genetic_animals\\food_items.json").isFile()) {
            try {
                InputStream inputStream = Minecraft.getInstance().getResourceManager().getResource(new ResourceLocation("eanimod:genetic_animals/food_items.json")).getInputStream();
                File directory = new File(FMLPaths.CONFIGDIR.get().toString()+"\\genetic_animals\\");

                if (! directory.exists()){
                    directory.mkdir();
                }

                File outputFile = new File(FMLPaths.CONFIGDIR.get().toString()+"\\genetic_animals\\food_items.json");

                java.nio.file.Files.copy(
                        inputStream,
                        outputFile.toPath(),
                        StandardCopyOption.REPLACE_EXISTING);

                try {
                    ((Closeable)inputStream).close();
                } catch (final IOException ioe) {
                    // ignore
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static class AnimalFoodMap {

        private List<FoodMap> foodMapList = new ArrayList<>();

        void setFoodMapList(List<FoodMap> foodMapList) {
            this.foodMapList = foodMapList;
        }


        public static class FoodMap {
            public String tag = "";
            public String registryName = "";
            public int hungerRestored;
            public Boolean isBreedingItem;
        }

        public boolean isFoodItem(Item item) {
            return foodMapList.stream()
                    .anyMatch(f -> f.registryName.equals(item.getRegistryName().toString())
                            || findTags(item, f.tag));
        }

        public boolean isBreedingItem(Item item) {
            return foodMapList.stream()
                    .filter(f -> f.registryName.equals(item.getRegistryName().toString())
                            || findTags(item, f.tag))
                    .anyMatch(f -> f.isBreedingItem);
        }

        public int getHungerRestored(Item item) {
            if (item.getRegistryName() != null) {
                FoodMap foodMap = foodMapList.stream()
                        .filter(f -> f.registryName.equals(item.getRegistryName().toString()))
                        .findFirst().orElse(null);
                if (foodMap != null) {
                    return foodMap.hungerRestored;
                }
            }
            FoodMap foodMapTag = foodMapList.stream()
                    .filter(f -> findTags(item, f.tag))
                    .findFirst().orElse(null);
            if (foodMapTag != null) {
                return foodMapTag.hungerRestored;
            } else {
                return 0;
            }
        }

        private boolean findTags(Item item, String foodTag) {
            return item.getTags().stream().anyMatch(tag -> tag.toString().equals(foodTag));
        }
    }
}
