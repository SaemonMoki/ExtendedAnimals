package mokiyoki.enhancedanimals.init;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.world.item.Item;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.IReverseTag;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class FoodSerialiser extends SimpleJsonResourceReloadListener {

    private static Map<String, AnimalFoodMap> compiledAnimalFoodMap = new HashMap<>();

    public FoodSerialiser() {
        super((new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create(), "animal_food");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> dataMap, ResourceManager resourceManagerIn, ProfilerFiller profilerIn) {
        loadFoodMappingFile(dataMap);
    }

    protected static void loadFoodMappingFile(Map<ResourceLocation, JsonElement> dataMap) {
        dataMap.forEach((file, jsonElement) -> {
            JsonObject jsonFromData = jsonElement.getAsJsonObject();

            createOrReplaceConfig(file, jsonFromData);

            readFoodConfig(file);
        });
    }

    private static void createOrReplaceConfig(ResourceLocation file, JsonObject jsonFromData) {
        Boolean replaceConfig = jsonFromData.get("replaceDefault").getAsBoolean();
        if (replaceConfig || (!new File(FMLPaths.CONFIGDIR.get().toString() + File.separator + "genetic_animals" + File.separator + file.getPath() + ".json").isFile())) {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            try {
                File newFile = new File(FMLPaths.CONFIGDIR.get().toString() + File.separator + "genetic_animals" + File.separator + file.getPath() + ".json");
                if (!newFile.exists()) {
                    newFile.getParentFile().mkdir();
                    newFile.createNewFile();
                }

                FileWriter fileWriter = new FileWriter(newFile);

                gson.toJson(jsonFromData, fileWriter);
                fileWriter.flush();
                fileWriter.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void readFoodConfig(ResourceLocation file) {
        try {
            FileInputStream configPath = new FileInputStream(FMLPaths.CONFIGDIR.get().toString() + File.separator + "genetic_animals" + File.separator + file.getPath() + ".json");

            InputStreamReader in_strm = new InputStreamReader(configPath);

            Gson gson = new Gson();

            BufferedReader reader = new BufferedReader(in_strm);
            JsonElement je = gson.fromJson(reader, JsonElement.class);
            JsonObject json = je.getAsJsonObject();

            AnimalFoodMap animalFoodMap = new AnimalFoodMap();
            List<AnimalFoodMap.FoodMap> foodMapList = new ArrayList<>();
            for (JsonElement foodElement : json.getAsJsonArray("values")) {

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

                animalFoodMap.setFoodMapList(foodMapList);
            }

            compiledAnimalFoodMap.put(file.getPath(), animalFoodMap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static AnimalFoodMap chickenFoodMap() {
        return compiledAnimalFoodMap.get("chicken");
    }

    public static AnimalFoodMap llamaFoodMap() {
        return compiledAnimalFoodMap.get("llama");
    }

    public static AnimalFoodMap rabbitFoodMap() {
        return compiledAnimalFoodMap.get("rabbit");
    }

    public static AnimalFoodMap sheepFoodMap() {
        return compiledAnimalFoodMap.get("sheep");
    }

    public static AnimalFoodMap cowFoodMap() {
        return compiledAnimalFoodMap.get("cow");
    }

    public static AnimalFoodMap pigFoodMap() {
        return compiledAnimalFoodMap.get("pig");
    }

    public static AnimalFoodMap horseFoodMap() {
        return compiledAnimalFoodMap.get("horse");
    }

    public static AnimalFoodMap turtleFoodMap() {
        return compiledAnimalFoodMap.get("turtle");
    }

    public static AnimalFoodMap axolotlFoodMap() {
        return compiledAnimalFoodMap.get("axolotl");
    }

    public static AnimalFoodMap catFoodMap() {
        return compiledAnimalFoodMap.get("cat");
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
            return ForgeRegistries.ITEMS.tags().getReverseTag(item).map(IReverseTag::getTagKeys).orElseGet(Stream::of).anyMatch(tag -> tag.toString().equals(foodTag));
        }
    }
}
