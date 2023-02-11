package mokiyoki.enhancedanimals.model.modeldata;
public enum SaddleType {
    NONE(""),
    VANILLA("saddle"),
    ENGLISH("saddleE"),
    WESTERN("saddleW");
    final String name;
    SaddleType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
