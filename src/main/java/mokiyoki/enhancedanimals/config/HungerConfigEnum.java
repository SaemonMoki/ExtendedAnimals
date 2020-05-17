package mokiyoki.enhancedanimals.config;

public enum HungerConfigEnum {
    RAVENOUS(2.0F),
    MORE_HUNGRY(1.5F),
    STANDARD(1.0F),
    LESS_HUNGRY(0.5F),
    NEVER_HUNGRY(0.0F);

    public float hungerScalingValue;

    private HungerConfigEnum(float hungerScalingValue) {
        this.hungerScalingValue = hungerScalingValue;
    }

}
