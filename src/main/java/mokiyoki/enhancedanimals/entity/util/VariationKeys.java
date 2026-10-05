package mokiyoki.enhancedanimals.entity.util;

public final class VariationKeys {
    private VariationKeys() {}

    //uuid[0]
    public static final VariationKey SEX = VariationKey.legacy("sex", 0);

    //Cow, mooshroom and moobloom
    //uuid[1]
    public static final VariationKey COW_PIEBALD_BODY = VariationKey.legacy("cow.piebald_body", 1);
    //uuid[2]
    public static final VariationKey COW_PIEBALD_HEAD = VariationKey.legacy("cow.piebald_head", 2);
    //uuid[3]
    public static final VariationKey COW_BLAZE = VariationKey.legacy("cow.blaze", 3);
    //uuid[4]
    public static final VariationKey COW_HORNS = VariationKey.legacy("cow.horns", 4);
    //uuid[5]
    public static final VariationKey COW_BELT = VariationKey.legacy("cow.belt", 5);
    //uuid[6]
    public static final VariationKey COW_COLOURSIDED = VariationKey.legacy("cow.coloursided", 6);
    //uuid[7]
    public static final VariationKey COW_ROAN = VariationKey.legacy("cow.roan", 7);
    //uuid[8]
    public static final VariationKey COW_SPECKLED = VariationKey.legacy("cow.speckled", 8);
    //uuid[20-35]
    public static final VariationKey[] COW_MUSHROOMS = VariationKey.legacySeries("cow.mushroom", 16, 20);

    //Pig
    //uuid[1]
    public static final VariationKey PIG_TAIL_CURL = VariationKey.legacy("pig.tail_curl", 1);
    //uuid[2]
    public static final VariationKey PIG_WHITE_FACE = VariationKey.legacy("pig.white_face", 2);
    //uuid[3]
    public static final VariationKey PIG_PATTERN = VariationKey.legacy("pig.pattern", 3);
    //uuid[4]
    public static final VariationKey PIG_HETEROCHROMIA = VariationKey.legacy("pig.heterochromia", 4);
    //uuid[5]
    public static final VariationKey PIG_WHITE_POINTS = VariationKey.legacy("pig.white_points", 5);

    //Rabbit
    //uuid[1]
    public static final VariationKey RABBIT_VIENNA_EYES = VariationKey.legacy("rabbit.vienna_eyes", 1);
    //uuid[2]
    public static final VariationKey RABBIT_VIENNA_SPOTTED = VariationKey.legacy("rabbit.vienna_spotted", 2);
    //uuid[3]
    public static final VariationKey RABBIT_VIENNA_SPOTS = VariationKey.legacy("rabbit.vienna_spots", 3);
    //uuid[4]
    public static final VariationKey RABBIT_BROKEN = VariationKey.legacy("rabbit.broken", 4);
    //uuid[5]
    public static final VariationKey RABBIT_DUTCH = VariationKey.legacy("rabbit.dutch", 5);
    //uuid[6]
    public static final VariationKey RABBIT_LOP = VariationKey.legacy("rabbit.lop", 6);

    //Llama
    //uuid[1]
    public static final VariationKey LLAMA_DOMINANT_WHITE = VariationKey.legacy("llama.dominant_white", 1);
    //uuid[2]
    public static final VariationKey LLAMA_ROAN = VariationKey.legacy("llama.roan", 2);
    //uuid[4]
    public static final VariationKey LLAMA_PIEBALD = VariationKey.legacy("llama.piebald", 4);
    //uuid[6]
    public static final VariationKey LLAMA_TUXEDO = VariationKey.legacy("llama.tuxedo", 6);

    //Sheep
    //uuid[2]
    public static final VariationKey SHEEP_POLY_HORNS = VariationKey.legacy("sheep.poly_horns", 2);
    //uuid[4]
    public static final VariationKey SHEEP_HORNS = VariationKey.legacy("sheep.horns", 4);

    //Chicken
    //uuid[1-3]
    public static final VariationKey[] CHICKEN_SPLASH = VariationKey.legacySeries("chicken.splash", 3, 1);

    //Turtle
    //uuid[5]
    public static final VariationKey TURTLE_PIEBALD = VariationKey.legacy("turtle.piebald", 5);
}

