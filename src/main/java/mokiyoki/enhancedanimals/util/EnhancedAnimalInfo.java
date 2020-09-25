package mokiyoki.enhancedanimals.util;

public class EnhancedAnimalInfo {

    public int health = 0;
    public int hunger = 0;
    public boolean isFemale = true;
    public int pregnant = 0;
    public String name = "Animal";
    public String agePrefix = "ADULT";
    public Integer age = 0;
    public String sire = "???";
    public String dam = "???";

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
            agePrefix = String.valueOf(splits[5]);
            age = Integer.valueOf(splits[6]);
            sire = String.valueOf(splits[7]);
            dam = String.valueOf(splits[8]);
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
        sb.append(agePrefix).append("|");
        sb.append(age).append("|");
        sb.append(sire).append("|");
        sb.append(dam).append("|");
        return sb.toString();
    }


}
