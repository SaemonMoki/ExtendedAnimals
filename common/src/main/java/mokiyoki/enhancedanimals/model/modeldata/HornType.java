package mokiyoki.enhancedanimals.model.modeldata;

public enum HornType {
    HORNED (-1.0F),
    SCURRED (-0.75F),
    POLLED (0.0F);
    private float placement;
    HornType(float placement) {
        this.placement = placement;
    }

    public float getPlacement(){
        return this.placement;
    }
}