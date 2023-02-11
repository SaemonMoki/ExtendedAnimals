package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import mokiyoki.enhancedanimals.items.CustomizableCollar;
import mokiyoki.enhancedanimals.items.CustomizableSaddleEnglish;
import mokiyoki.enhancedanimals.items.CustomizableSaddleWestern;
import mokiyoki.enhancedanimals.model.modeldata.AnimalModelData;
import mokiyoki.enhancedanimals.model.modeldata.Phenotype;
import mokiyoki.enhancedanimals.model.modeldata.SaddleType;
import mokiyoki.enhancedanimals.model.util.GAModel;
import mokiyoki.enhancedanimals.model.util.WrappedModelPart;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LerpingModel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public abstract class EnhancedAnimalModel<T extends EnhancedAnimalAbstract & LerpingModel> extends GAModel<T> {
    protected static final float PI_FRACTION = 1.0F/Mth.PI;
    protected static final float HALF_PI_FRACTION = 1.0F/Mth.HALF_PI;

    protected WrappedModelPart eyes;
    protected WrappedModelPart chests;
    protected WrappedModelPart chestsR;

    protected WrappedModelPart collar;
    protected WrappedModelPart collarHardware;
    protected WrappedModelPart saddleVanilla;
    protected WrappedModelPart saddleEnglish;
    protected WrappedModelPart saddleWestern;
    protected WrappedModelPart bridle;
    protected WrappedModelPart bridleNose;
    protected WrappedModelPart blanket;

    protected WrappedModelPart saddlePad;
    protected WrappedModelPart saddleHorn;
    protected WrappedModelPart saddlePomel;
    protected WrappedModelPart saddleSideLeft;
    protected WrappedModelPart saddleSideRight;
    protected WrappedModelPart stirrupWideLeft;
    protected WrappedModelPart stirrupWideRight;
    protected WrappedModelPart stirrupNarrowLeft;
    protected WrappedModelPart stirrupNarrowRight;
    protected WrappedModelPart stirrup;

    public EnhancedAnimalModel(ModelPart modelPart) {

    }

    public EnhancedAnimalModel(ModelPart modelPart, Function<ResourceLocation, RenderType> p_102015_) {
        super(p_102015_);
    }

    protected Vector3f getRotationVector(WrappedModelPart part) {
        return new Vector3f(part.getXRot(), part.getYRot(), part.getZRot());
    }

    protected Vector3f getPosVector(WrappedModelPart part) {
        return new Vector3f(part.getX(), part.getY(), part.getZ());
    }

    protected void setRotationFromVector(WrappedModelPart part, Vector3f v3f) {
        part.setRotation(v3f.x(), v3f.y(), v3f.z());
    }

    protected void setOffsetFromVector(WrappedModelPart part, Vector3f v3f) {
        part.setPos(v3f.x(), v3f.y(), v3f.z());
    }

    protected void setOffsetAndRotationFromVector(WrappedModelPart part, Vector3f offsets, Vector3f rotations) {
        part.setPos(offsets.x(), offsets.y(), offsets.z());
        part.setRotation(rotations.x(), rotations.y(), rotations.z());
    }

    protected float lerpTo(float currentRot, float goalRot) {
        return this.lerpTo(0.05F, currentRot, goalRot);
    }

    protected float lerpTo(float speed, float currentRot, float goalRot) {
        return Mth.rotLerp(speed, currentRot, goalRot);
    }

    protected void lerpPart(WrappedModelPart part, float xGoalRot, float yGoalRot, float zGoalRot) {
        part.setRotation(this.lerpTo(part.getXRot(), xGoalRot), this.lerpTo(part.getYRot(), yGoalRot), this.lerpTo(part.getZRot(), zGoalRot));
    }

    protected float limit(float value, float limit) {
        return limit(value, limit, -limit);
    }

    protected float limit(float value, float valueA, float valueB) {
        return value >= valueB ? Math.max(valueB, (Math.min(value, valueA))) : Math.max(valueA, (Math.min(value, valueB)));
    }

    public void renderToBuffer(AnimalModelData animalModelData, PoseStack p_103111_, VertexConsumer p_103112_, int p_103113_, int p_103114_, float p_103115_, float p_103116_, float p_103117_, float p_103118_) {

        if (animalModelData != null) {
            if (this.eyes != null) {
                this.eyes.show(animalModelData.blink);
            }
            if (this.collar != null) {
                this.collar.show(animalModelData.collar);
            }
            if (this.bridle != null) {
                this.bridle.show(animalModelData.bridle);
            }
            if (this.bridleNose != null) {
                this.bridleNose.show(animalModelData.bridle);
            }
            if (this.chests != null) {
                this.chests.show(animalModelData.chests);
            }
            if (this.blanket != null) {
                this.blanket.show(animalModelData.blanket);
            }
            if (this.saddleVanilla!=null) { this.saddleVanilla.show(animalModelData.saddle == SaddleType.VANILLA); }
            if (this.saddleEnglish!=null) { this.saddleEnglish.show(animalModelData.saddle == SaddleType.ENGLISH); }
            if (this.saddleWestern!=null) { this.saddleWestern.show(animalModelData.saddle == SaddleType.WESTERN); }
            if (this.saddlePomel!=null) { this.saddlePomel.show(animalModelData.saddle == SaddleType.WESTERN); }
        }

    }



    protected AnimalModelData getCreateAnimalModelData(T enhancedAnimal) {
        if (enhancedAnimal.getModelData() != null) {
            updateModelData(enhancedAnimal);
        } else {
            setInitialModelData(enhancedAnimal);
        }

        return enhancedAnimal.getModelData();
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
            animalModelData.random = enhancedAnimal.getRandom().nextFloat();

            additionalModelDataInfo(animalModelData, enhancedAnimal);

            enhancedAnimal.setModelData(animalModelData);
        }
    }

    protected void additionalModelDataInfo(AnimalModelData animalModelData, T enhancedAnimal) { }

    protected void additionalUpdateModelDataInfo(AnimalModelData animalModelData, T enhancedAnimal) { }

    protected void setInitialModelData(T enhancedAnimal) {}

    protected void updateModelData(T enhancedAnimal) {
        AnimalModelData animalModelData = enhancedAnimal.getModelData();
        animalModelData.clientGameTime = (((ClientLevel)enhancedAnimal.level).getLevelData()).getGameTime();
        if (animalModelData.growthAmount != 1.0F) {
            animalModelData.growthAmount = enhancedAnimal.growthAmount();
        }
        animalModelData.sleeping = enhancedAnimal.isAnimalSleeping();
        animalModelData.blink = animalModelData.sleepDelay == -1.0F ? enhancedAnimal.getBlink() : animalModelData.sleepDelay != 0;
        animalModelData.collar = hasCollar(enhancedAnimal.getEnhancedInventory());
        if (animalModelData.isEating == 0 && enhancedAnimal.isGrazing()) {
            animalModelData.isEating = -1;
        }
        additionalUpdateModelDataInfo(animalModelData, enhancedAnimal);
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
    SaddleType getSaddle(SimpleContainer inventory) {
        Item equipment = inventory.getItem(1).getItem();
        if (!equipment.equals(Items.AIR)) {
            if (equipment instanceof CustomizableSaddleWestern) {
                return SaddleType.WESTERN;
            } else if (equipment instanceof CustomizableSaddleEnglish) {
                return SaddleType.ENGLISH;
            } else if (!(equipment instanceof CustomizableCollar)) {
                return SaddleType.VANILLA;
            }
        }
        return SaddleType.NONE;
    }

    static class Animation {
        float length;
        Map<String, List<Frame>> frames = new HashMap<>();

        Animation(float length, Frame... frames) {
            this.length = length;
            int i = 1;
            for (Frame frame : frames) {
                frame.setLength(i >= frames.length ? this.length : frames[i++].start);
                List<Frame> list = this.frames.getOrDefault(frame.getName(), new ArrayList<>());
                list.add(frame);
                this.frames.put(frame.getName(), list);
            }
        }

        public List<Frame> getFramesFor(String partName) {
            return this.frames.get(partName);
        }
    }

    static class Frame {
        private static final float degree = Mth.PI/180;
        String name;
        float start;
        float length = 5.0F;
        Float x;
        Float y;
        Float z;

        Frame(String name, float start, Float targetX, Float targetY, Float z){
            this.name = name;
            this.start = start;
            if (targetX!=null) this.x = targetX * degree;
            if (targetY!=null) this.y = targetY * degree;
            if (z !=null) this.z = z * degree;
        }

        Frame(String name, float start) {
            this.name = name;
            this.start = start;
        }

        public String getName() {
            return name;
        }

        public float getStart() {
            return this.start;
        }

        public void setLength(float nextFrameStart) {
            this.length = nextFrameStart-this.start;
        }

        public float getLength() {
            return this.length;
        }

        public Frame x(float target) {
            this.x = target * degree;
            return this;
        }
        public Frame y(float target) {
            this.y = target * degree;
            return this;
        }
        public Frame z(float target) {
            this.z = target * degree;
            return this;
        }

        public float xOrElse(float alt) {
            return this.x == null ? alt : this.x;
        }

        public float yOrElse(float alt) {
            return this.y == null ? alt : this.y;
        }

        public float zOrElse(float alt) {
            return this.z == null ? alt : this.z;
        }

        public boolean getRunning(float time) {
            return time >= this.start && time < this.start+this.length;
        }
    }
}
