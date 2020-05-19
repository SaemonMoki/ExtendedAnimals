package mokiyoki.enhancedanimals.util;

public class EnhancedAnimalInfo {

    public float health = 0.0F;
    public float hunger = 0.0F;
    public int tameness = 0;
    public boolean isFemale = true;
    public int pregnant = 0;
    public String name = "Animal";
    public boolean canHaveChest = false;
    public boolean canHaveSaddle = false;
    public boolean canHaveBridle = false;
    public boolean canHaveArmour = false;
    public boolean canHaveBlanket = false;
    public boolean canHaveBanner = false;
    public boolean canHaveHarness = false;

    public boolean created = false;

    public EnhancedAnimalInfo(){}

    public EnhancedAnimalInfo(String serialised) {
        if (serialised != null && !serialised.isEmpty()) {
            String[] splits = serialised.split("\\|");
            health = Float.valueOf(splits[0]);
            hunger = Float.valueOf(splits[1]);
            tameness = Integer.valueOf(splits[2]);
            isFemale = Boolean.valueOf(splits[3]);
            pregnant = Integer.valueOf(splits[4]);
            name = String.valueOf(splits[5]);
            canHaveChest = Boolean.valueOf(splits[6]);
            canHaveSaddle = Boolean.valueOf(splits[7]);
            canHaveBridle = Boolean.valueOf(splits[8]);
            canHaveArmour = Boolean.valueOf(splits[9]);
            canHaveBlanket = Boolean.valueOf(splits[10]);
            canHaveBanner = Boolean.valueOf(splits[11]);
            canHaveHarness = Boolean.valueOf(splits[12]);
            created = true;
        }
    }

    public String serialiseToString() {
        StringBuilder sb = new StringBuilder();
        sb.append(health).append("|");
        sb.append(hunger).append("|");
        sb.append(tameness).append("|");
        sb.append(isFemale).append("|");
        sb.append(pregnant).append("|");
        sb.append(name).append("|");
        sb.append(canHaveChest).append("|");
        sb.append(canHaveSaddle).append("|");
        sb.append(canHaveBridle).append("|");
        sb.append(canHaveArmour).append("|");
        sb.append(canHaveBlanket).append("|");
        sb.append(canHaveBanner).append("|");
        sb.append(canHaveHarness).append("|");
        return sb.toString();
    }


}
