package mokiyoki.enhancedanimals.util;

public class EnhancedAnimalInfo {

    public float health;

    public int testRandom;

    public boolean created = false;

    public EnhancedAnimalInfo(){}

    public EnhancedAnimalInfo(String serialised) {
        if (serialised != null && !serialised.isEmpty()) {
            String[] splits = serialised.split("\\|");
            health = Float.valueOf(splits[0]);
            testRandom = Integer.valueOf(splits[1]);
            created = true;
        }
    }

    public String serialiseToString() {
        StringBuilder sb = new StringBuilder();
        sb.append(health).append("|");
        sb.append(testRandom);
        return sb.toString();
    }


}
