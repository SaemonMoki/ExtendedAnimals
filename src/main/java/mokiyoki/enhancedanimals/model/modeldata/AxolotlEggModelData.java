package mokiyoki.enhancedanimals.model.modeldata;

import com.google.common.collect.Maps;
import com.mojang.math.Vector3f;

import java.util.Map;

public class AxolotlEggModelData {
    public Map<String, Vector3f> offsets = Maps.newHashMap();
    public float random;
    public int wiggleTimer = 0;
    public int wiggleIntensity = 0;
    public int hatchTime = -1;
    public float rotationY;
    public float rotationZ;
    public float wiggleRate = 1.0F;
}
