package mokiyoki.enhancedanimals.util;

public class EnhancedAnimalInfo {

    public int health = 0;
    public int hunger = 0;
    public boolean isFemale = true;
    public int pregnant = 0;
    public String name = "Animal";

    public boolean created = false;

    public EnhancedAnimalInfo(){}

    public EnhancedAnimalInfo(String serialised) {
        if (serialised != null && !serialised.isEmpty()) {
            String[] splits = serialised.split("\\|");
            health = Integer.valueOf(splits[0]);
            hunger = Integer.valueOf(splits[1]);
            isFemale = Boolean.valueOf(splits[2]);
            pregnant = Integer.valueOf(splits[3]);
            name = String.valueOf(splits[4]);
            created = true;
        }
    }

    public String serialiseToString() {
        StringBuilder sb = new StringBuilder();
        sb.append(health).append("|");
        sb.append(hunger).append("|");
        sb.append(isFemale).append("|");
        sb.append(pregnant).append("|");
        sb.append(name).append("|");
        return sb.toString();
    }


}
