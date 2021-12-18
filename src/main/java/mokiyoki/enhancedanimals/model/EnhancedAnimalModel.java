package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import mokiyoki.enhancedanimals.items.CustomizableCollar;
import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public abstract class EnhancedAnimalModel<T extends EnhancedAnimalAbstract> extends EntityModel<T> {

    private Map<Integer, AnimalModelData> animalModelDataCache = new HashMap<>();
    private int clearCacheTimer = 0;
    private Integer currentAnimal = null;

    public EnhancedAnimalModel(Function<ResourceLocation, RenderType> p_103110_) {
        super(p_103110_);
    }

    @Override
    public void renderToBuffer(PoseStack p_103111_, VertexConsumer p_103112_, int p_103113_, int p_103114_, float p_103115_, float p_103116_, float p_103117_, float p_103118_) {

    }

    protected class AnimalModelData {
        Phenotype phenotype;
        float growthAmount;
        boolean sleeping = false;
        int blink = 0;
        boolean collar = false;
        int lastAccessed = 0;
        int dataReset = 0;
        long clientGameTime = 0;
    }

    protected AnimalModelData getAnimalModelData() {
        if (this.currentAnimal == null || !animalModelDataCache.containsKey(this.currentAnimal)) {
            return new AnimalModelData();
        }
        return animalModelDataCache.get(this.currentAnimal);
    }

    protected AnimalModelData getCreateAnimalModelData(T enhancedAnimal) {
        clearCacheTimer++;
        if(clearCacheTimer > 50000) {
            animalModelDataCache.values().removeIf(value -> value.lastAccessed==1);
            for (AnimalModelData rabbitModelData : animalModelDataCache.values()){
                rabbitModelData.lastAccessed = 1;
            }
            clearCacheTimer = 0;
        }

        if (animalModelDataCache.containsKey(enhancedAnimal.getId())) {
            AnimalModelData animalModelData = animalModelDataCache.get(enhancedAnimal.getId());
            animalModelData.lastAccessed = 0;
            animalModelData.dataReset++;
            animalModelData.clientGameTime = (((ClientLevel)enhancedAnimal.level).getLevelData()).getGameTime();
            animalModelData.growthAmount = enhancedAnimal.growthAmount();
            animalModelData.sleeping = enhancedAnimal.isAnimalSleeping();
            animalModelData.blink = enhancedAnimal.getBlink();
            animalModelData.collar = hasCollar(enhancedAnimal.getEnhancedInventory());

            return animalModelData;
        } else {
            AnimalModelData animalModelData = new AnimalModelData();
            if (enhancedAnimal.getSharedGenes()!=null) {
                animalModelData.phenotype = new Phenotype(enhancedAnimal.getSharedGenes());
            }
            animalModelData.growthAmount = enhancedAnimal.growthAmount();
            animalModelData.sleeping = enhancedAnimal.isAnimalSleeping();
            animalModelData.blink = enhancedAnimal.getBlink();
            animalModelData.collar = hasCollar(enhancedAnimal.getEnhancedInventory());
            animalModelData.clientGameTime = (((ClientLevel)enhancedAnimal.level).getLevelData()).getGameTime();

            if(animalModelData.phenotype != null) {
                animalModelDataCache.put(enhancedAnimal.getId(), animalModelData);
            }

            return animalModelData;
        }
    }

    private boolean hasCollar(SimpleContainer inventory) {
        for (int i = 1; i < 6; i++) {
            if (inventory.getItem(i).getItem() instanceof CustomizableCollar) {
                return true;
            }
        }
        return false;
    }

    private class Phenotype {

        Phenotype(Genes genes) {

        }
    }
}
