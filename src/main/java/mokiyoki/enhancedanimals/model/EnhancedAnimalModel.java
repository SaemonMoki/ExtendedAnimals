package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import mokiyoki.enhancedanimals.items.CustomizableCollar;
import mokiyoki.enhancedanimals.model.util.GAModel;
import mokiyoki.enhancedanimals.model.util.WrappedModelPart;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.SimpleContainer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@OnlyIn(Dist.CLIENT)
public abstract class EnhancedAnimalModel<T extends EnhancedAnimalAbstract> extends GAModel<T> {

    protected WrappedModelPart collar;
    protected WrappedModelPart eyes;

    private Map<Integer, AnimalModelData> animalModelDataCache = new HashMap<>();
    private int clearCacheTimer = 0;
    protected Integer currentAnimal = null;

    public EnhancedAnimalModel(ModelPart modelPart) {
        // can equipment stuff go here??? probably...
    }

    protected void setRotationOffset(ModelPart renderer, float x, float y, float z) {
        renderer.xRot = x;
        renderer.yRot = y;
        renderer.zRot = z;
    }

    @Override
    public void renderToBuffer(PoseStack p_103111_, VertexConsumer p_103112_, int p_103113_, int p_103114_, float p_103115_, float p_103116_, float p_103117_, float p_103118_) {
        AnimalModelData animalModelData = getAnimalModelData();

        if (animalModelData != null) {
            if (this.eyes != null) {
                this.eyes.show(animalModelData.blink);
            }
            if (this.collar != null) {
                this.collar.show(animalModelData.collar);
            }
        }

    }

    protected class AnimalModelData {
        public Phenotype phenotype;
        float growthAmount;
        float size = 1.0F;
        boolean sleeping = false;
        boolean blink = false;
        boolean collar = false;
        int lastAccessed = 0;
        int dataReset = 0;
        long clientGameTime = 0;
        float random;
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
            for (AnimalModelData animalModelData : animalModelDataCache.values()){
                animalModelData.lastAccessed = 1;
            }
            clearCacheTimer = 0;
        }

        if (animalModelDataCache.containsKey(enhancedAnimal.getId())) {
            updateModelData(enhancedAnimal);
        } else {
            setInitialModelData(enhancedAnimal);
        }

        return animalModelDataCache.get(enhancedAnimal.getId());
    }

    protected void setBaseInitialModelData(AnimalModelData animalModelData, T enhancedAnimal) {
        if (enhancedAnimal.getSharedGenes()!=null) {
            animalModelData.phenotype = createPhenotype(enhancedAnimal);
        }

        if(animalModelData.phenotype != null) {
            animalModelData.growthAmount = enhancedAnimal.growthAmount();
            animalModelData.size = enhancedAnimal.getAnimalSize();
            animalModelData.sleeping = enhancedAnimal.isAnimalSleeping();
            animalModelData.blink = enhancedAnimal.getBlink();
            animalModelData.collar = hasCollar(enhancedAnimal.getEnhancedInventory());
            animalModelData.clientGameTime = (((ClientLevel)enhancedAnimal.level).getLevelData()).getGameTime();
            animalModelData.random = ThreadLocalRandom.current().nextFloat();

            additionalModelDataInfo(animalModelData, enhancedAnimal);

            animalModelDataCache.put(enhancedAnimal.getId(), animalModelData);
        }
    }

    protected void additionalModelDataInfo(AnimalModelData animalModelData, T enhancedAnimal) { }

    protected void setInitialModelData(T enhancedAnimal) {
        AnimalModelData animalModelData = new AnimalModelData();
        setBaseInitialModelData(animalModelData, enhancedAnimal);
    }

    protected void updateModelData(T enhancedAnimal) {
        AnimalModelData animalModelData = animalModelDataCache.get(enhancedAnimal.getId());
        animalModelData.lastAccessed = 0;
        animalModelData.dataReset++;
        animalModelData.clientGameTime = (((ClientLevel)enhancedAnimal.level).getLevelData()).getGameTime();
        if (animalModelData.growthAmount != 1.0F) {
            animalModelData.growthAmount = enhancedAnimal.growthAmount();
        }
        animalModelData.sleeping = enhancedAnimal.isAnimalSleeping();
        animalModelData.blink = enhancedAnimal.getBlink();
        animalModelData.collar = hasCollar(enhancedAnimal.getEnhancedInventory());
    }

    protected abstract Phenotype createPhenotype(T enhancedAnimal);

    private boolean hasCollar(SimpleContainer inventory) {
        for (int i = 1; i < 6; i++) {
            if (inventory.getItem(i).getItem() instanceof CustomizableCollar) {
                return true;
            }
        }
        return false;
    }
}
