package mokiyoki.enhancedanimals.model.modeldata;

public class ChickenPhenotypeEnums {

public enum Crested {
    BIG_CREST,
    BIG_FORWARDCREST,
    SMALL_CREST,
    SMALL_FORWARDCREST,
    NONE
}

public enum FootFeathers {
    BIG_TOEFEATHERS,
    TOEFEATHERS,
    FOOTFEATHERS,
    LEGFEATHERS,
    NONE
}

public enum Comb {
    SINGLE (false),
    ROSE_ONE (false),
    ROSE_TWO (false),
    PEA (true),
    WALNUT (true),
    V (false),
    HET_ROSE_ONE (false),
    HET_ROSE_TWO (false),
    BREDA_COMBLESS (false),
    NONE (true);

    boolean containsPeaComb;

    Comb(boolean effectsWaddleSize) {
        this.containsPeaComb = effectsWaddleSize;
    }

    public boolean hasPeaComb() {
        return this.containsPeaComb;
    }
}

public enum Beard {
    BIG_BEARD,
    SMALL_BEARD,
    NN_BEARD,
    NONE
}

public enum NakedNeckType {
    NAKED_NECK,
    BOWTIE_NECK,
    NONE
}

}