package mokiyoki.enhancedanimals.model.modeldata;

import com.google.common.collect.Maps;
import org.joml.Vector3f;

import java.util.Map;

public class AnimalModelData {
    public Map<String, Vector3f> offsets = Maps.newHashMap();
    public Phenotype phenotype;
    public float growthAmount;
    public float size = 1.0F;
    public boolean sleeping = false;
    public boolean blink = false;
    public boolean collar = false;
    public boolean bridle = false;
    public boolean blanket = false;
    public SaddleType saddle = SaddleType.NONE;
    public boolean chests = false;
    public long clientGameTime = 0;
    public float random;
    public int isEating = 0;
    public boolean earTwitchSide = true;
    public int earTwitchTimer = 0;
    public boolean tailSwishSide = true;
    public int tailSwishTimer = 0;
    public int sleepDelay = -1;
}
