package mokiyoki.enhancedanimals.model;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Vector3f;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import mokiyoki.enhancedanimals.items.CustomizableCollar;
import mokiyoki.enhancedanimals.model.util.GAModel;
import mokiyoki.enhancedanimals.model.util.WrappedModelPart;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LerpingModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public abstract class EnhancedAnimalModel<T extends EnhancedAnimalAbstract & LerpingModel> extends GAModel<T> {

    protected WrappedModelPart eyes;
    protected WrappedModelPart chests;
    protected WrappedModelPart collar;
    protected WrappedModelPart saddleVanilla;
    protected WrappedModelPart saddleEnglish;
    protected WrappedModelPart saddleWestern;
    protected WrappedModelPart bridle;
    protected WrappedModelPart blanket;

    private Map<Integer, AnimalModelData> animalModelDataCache = new HashMap<>();
    private int clearCacheTimer = 0;
    protected Integer currentAnimal = null;

    public EnhancedAnimalModel(ModelPart modelPart) {

    }

    public EnhancedAnimalModel(ModelPart modelPart, Function<ResourceLocation, RenderType> p_102015_) {
        super(p_102015_);
    }

    protected void setRotationOffset(ModelPart renderer, float x, float y, float z) {
        renderer.xRot = x;
        renderer.yRot = y;
        renderer.zRot = z;
    }

    protected Vector3f getRotationVector(WrappedModelPart part) {
        return new Vector3f(part.getXRot(), part.getYRot(), part.getZRot());
    }

    protected Vector3f getOffsetVector(WrappedModelPart part) {
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

    protected void animatePart(WrappedModelPart part,Map<String, Vector3f> rotationMap, float driver, Animation animation) {
        animatePart(part, rotationMap.get(part.boxName), (driver%animation.length)*0.05F, animation.getFramesFor(part.boxName));
    }

    protected void animatePart(WrappedModelPart part,Vector3f baseRot, float driver, List<Frame> animation) {
        if (animation!=null) {
            boolean flag = true;
            for (int i = 0, animationSize = animation.size(); i < animationSize; i++) {
                if (animation.get(i).getRunning(driver)) {
                    Frame frame = animation.get(i);
                    Vector3f pRot = baseRot.copy();
                    if (i != 0) {
                        Frame pf = animation.get(i - 1);
                        if (pf.x != null) pRot.setX(pf.x);
                        if (pf.y != null) pRot.setY(pf.y);
                        if (pf.z != null) pRot.setZ(pf.z);
                    }
                    float time = (driver - frame.getStart()) / frame.getLength();
                    time = (float) Math.pow(time, 1.5D);
                    part.setXRot((pRot.x()) + ((frame.xOrElse(baseRot.x()) - pRot.x()) * time));
                    part.setYRot((pRot.y()) + ((frame.yOrElse(baseRot.y()) - pRot.y()) * time));
                    part.setZRot((pRot.z()) + ((frame.zOrElse(baseRot.z()) - pRot.z()) * time));
                    flag = false;
                    break;
                }
            }

            if (flag) {
                part.setRotation(baseRot.x(), baseRot.y(), baseRot.z(), 0.05F);
            }
        } else {
            part.setRotation(baseRot.x(), baseRot.y(), baseRot.z(), 0.05F);
        }
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
            if (this.bridle != null) {
                this.bridle.show(animalModelData.bridle);
            }
            if (this.chests != null) {
                this.chests.show(animalModelData.chests);
            }
            if (this.blanket != null) {
                this.blanket.show(animalModelData.blanket);
            }
            if (animalModelData.saddle != SaddleType.NONE) {
                if (this.saddleVanilla!=null) { this.saddleVanilla.show(animalModelData.saddle == SaddleType.VANILLA); }
                if (this.saddleEnglish!=null) { this.saddleEnglish.show(animalModelData.saddle == SaddleType.ENGLISH); }
                if (this.saddleWestern!=null) { this.saddleWestern.show(animalModelData.saddle == SaddleType.WESTERN); }
            }
        }

    }

    protected class AnimalModelData {
        Map<String, Vector3f> offsets = Maps.newHashMap();
        public Phenotype phenotype;
        float growthAmount;
        float size = 1.0F;
        boolean sleeping = false;
        boolean blink = false;
        boolean collar = false;
        boolean bridle = false;
        boolean blanket = false;
        SaddleType saddle = SaddleType.NONE;
        boolean chests = false;
        int lastAccessed = 0;
        int dataReset = 0;
        long clientGameTime = 0;
        float random;
        int isEating = 0;
        boolean earTwitchSide = true;
        int earTwitchTimer = 0;
        boolean tailSwishSide = true;
        int tailSwishTimer = 0;
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
            animalModelData.random = enhancedAnimal.getRandom().nextFloat();

            additionalModelDataInfo(animalModelData, enhancedAnimal);

            animalModelDataCache.put(enhancedAnimal.getId(), animalModelData);
        }
    }

    protected void additionalModelDataInfo(AnimalModelData animalModelData, T enhancedAnimal) { }

    protected void additionalUpdateModelDataInfo(AnimalModelData animalModelData, T enhancedAnimal) { }

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

    protected enum SaddleType {
        NONE,
        VANILLA,
        ENGLISH,
        WESTERN
    }
}
