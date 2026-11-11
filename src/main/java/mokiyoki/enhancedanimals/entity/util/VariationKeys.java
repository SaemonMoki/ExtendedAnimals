package mokiyoki.enhancedanimals.entity.util;

public final class VariationKeys {
    private VariationKeys() {}

    //uuid[0]
    public static final VariationKey SEX = VariationKey.legacy("sex", 0);

    //Cow, mooshroom and moobloom
    //uuid[1]
    public static final VariationKey COW_PIEBALD_BODY = VariationKey.legacy("cow.piebald.body", 1);
    public static final VariationKey COW_PIEBALD_BODY_FLIP = VariationKey.of("cow.piebald.body.flip");
    //uuid[2]
    public static final VariationKey COW_PIEBALD_HEAD = VariationKey.legacy("cow.piebald.head", 2);
    public static final VariationKey COW_PIEBALD_HEAD_FLIP = VariationKey.of("cow.piebald.head.flip");
    //uuid[3]
    public static final VariationKey COW_BLAZE = VariationKey.legacy("cow.blaze", 3);
    public static final VariationKey COW_BLAZE_FLIP = VariationKey.of("cow.blaze.flip");
    //uuid[4]
    public static final VariationKey COW_HORNS = VariationKey.legacy("cow.horns", 4);
    //uuid[5]
    public static final VariationKey COW_BELT = VariationKey.legacy("cow.belt", 5);
    public static final VariationKey COW_BELT_FLIP = VariationKey.of("cow.belt.flip");
    //uuid[6]
    public static final VariationKey COW_COLOURSIDED = VariationKey.legacy("cow.coloursided", 6);
    public static final VariationKey COW_COLOURSIDED_FLIP = VariationKey.of("cow.coloursided.flip");
    //uuid[7]
    public static final VariationKey COW_ROAN = VariationKey.legacy("cow.roan", 7);
    public static final VariationKey COW_ROAN_FLIP = VariationKey.of("cow.roan.flip");
    //uuid[8]
    public static final VariationKey COW_SPECKLED = VariationKey.legacy("cow.speckled", 8);
    public static final VariationKey COW_SPECKLED_FLIP = VariationKey.of("cow.speckled.flip");
    //uuid[20-35]
    public static final VariationKey[] COW_MUSHROOMS = VariationKey.legacySeries("cow.mushroom", 16, 20);
    //brockling was random per texture build before this upgrade, so no legacy reads to keep
    public static final VariationKey COW_BROCKLING_COUNT = VariationKey.of("cow.brockling.count");
    public static final VariationKey[] COW_BROCKLING_PATCHES = VariationKey.series("cow.brockling.patch", 6);
    public static final VariationKey[] COW_BROCKLING_FLIPS = VariationKey.series("cow.brockling.flip", 6);

    public static final VariationKey BRINDLE_FLIP = VariationKey.of("cow.brindle.flip");

    //Pig
    //uuid[1]
    public static final VariationKey PIG_TAIL_CURL = VariationKey.legacy("pig.tail_curl", 1);
    public static final VariationKey PIG_FUR_FLIP = VariationKey.of("pig.fur_flip");
    //uuid[2]
    public static final VariationKey PIG_WHITE_SPOT = VariationKey.legacy("pig.white_spot", 2);
    public static final VariationKey PIG_WHITE_SPOT_FLIP = VariationKey.of("pig.white_spot_flip");
    //uuid[3]
    public static final VariationKey PIG_PATTERN = VariationKey.legacy("pig.pattern", 3);
    //uuid[4]
    public static final VariationKey PIG_HETEROCHROMIA = VariationKey.legacy("pig.heterochromia", 4);
    //uuid[5]
    public static final VariationKey PIG_WHITE_POINTS = VariationKey.legacy("pig.white_points", 5);

    //Rabbit
    //uuid[1]
    public static final VariationKey RABBIT_VIENNA_EYES = VariationKey.legacy("rabbit.vienna.eyes", 1);
    //uuid[2]
    public static final VariationKey RABBIT_VIENNA_SPOTTED = VariationKey.legacy("rabbit.vienna.spotted", 2);
    //uuid[3]
    public static final VariationKey RABBIT_VIENNA_SPOTS = VariationKey.legacy("rabbit.vienna.spots", 3);
    //uuid[4]
    public static final VariationKey RABBIT_BROKEN = VariationKey.legacy("rabbit.broken", 4);
    //uuid[5]
    public static final VariationKey RABBIT_DUTCH = VariationKey.legacy("rabbit.dutch", 5);
    //uuid[6]
    public static final VariationKey RABBIT_LOP = VariationKey.legacy("rabbit.lop", 6);
    public static final VariationKey RABBIT_VIENNA_FLIP = VariationKey.of("rabbit.vienna.flip");
    public static final VariationKey RABBIT_BROKEN_FLIP = VariationKey.of("rabbit.broken.flip");
    public static final VariationKey RABBIT_DUTCH_FLIP = VariationKey.of("rabbit.dutch.flip");

    //Llama
    //uuid[1]
    public static final VariationKey LLAMA_DOMINANT_WHITE = VariationKey.legacy("llama.dominant_white", 1);
    //uuid[2]
    public static final VariationKey LLAMA_ROAN = VariationKey.legacy("llama.roan", 2);
    //uuid[4]
    public static final VariationKey LLAMA_PIEBALD = VariationKey.legacy("llama.piebald", 4);
    //uuid[6]
    public static final VariationKey LLAMA_TUXEDO = VariationKey.legacy("llama.tuxedo", 6);
    public static final VariationKey LLAMA_TUXEDO_LEGS = VariationKey.legacy("llama.tuxedo.legs", 6);
    public static final VariationKey LLAMA_ROAN_FLIP = VariationKey.of("llama.roan.flip");
    public static final VariationKey LLAMA_PIEBALD_FLIP_HEAD = VariationKey.of("llama.piebald.flip.head");
    public static final VariationKey LLAMA_PIEBALD_FLIP_BODY_LEG = VariationKey.of("llama.piebald.flip.bodyleg");
    public static final VariationKey LLAMA_TUXEDO_FLIP_HEAD = VariationKey.of("llama.tuxedo.fliphead");
    public static final VariationKey LLAMA_TUXEDO_FLIP_BODY = VariationKey.of("llama.tuxedo.flipbody");
    public static final VariationKey LLAMA_TUXEDO_FLIP_LEGS = VariationKey.of("llama.tuxedo.fliplegs");

    //Sheep
    //uuid[2]
    public static final VariationKey SHEEP_POLY_HORNS = VariationKey.legacy("sheep.poly_horns", 2);
    //uuid[4]
    public static final VariationKey SHEEP_HORNS = VariationKey.legacy("sheep.horns", 4);
    public static final VariationKey SHEEP_ROAN_FLIP = VariationKey.of("sheep.roan.flip");
    public static final VariationKey SHEEP_BLAZE_FLIP = VariationKey.of("sheep.blaze.flip");
    public static final VariationKey SHEEP_SPOTS_FLIP = VariationKey.of("sheep.spots.flip");
    public static final VariationKey SHEEP_PIGMENTEDHEAD_FLIP = VariationKey.of("sheep.pigmentedhead.flip");

    //Chicken
    //uuid[1-3]
    public static final VariationKey[] CHICKEN_SPLASH = VariationKey.legacySeries("chicken.splash", 3, 1);
    public static final VariationKey[] CHICKEN_SPLASH_FLIP = VariationKey.series("chicken.splash.flip", 3);
    public static final VariationKey CHICKEN_COMB_FLOP = VariationKey.of("chicken.flop");

    public static final VariationKey[] CHICKEN_PAINT = VariationKey.series("chicken.paint",17);
    public static final VariationKey[] CHICKEN_PAINT_FLIP = VariationKey.series("chicken.paint.flip",17);

    //Turtle
    //uuid[5]
    public static final VariationKey TURTLE_PIEBALD = VariationKey.legacy("turtle.piebald", 5);
    public static final VariationKey TURTLE_PIEBALD_FLIP = VariationKey.of("turtle.piebald.flip");

    public static final VariationKey TURTLE_PATTERN_FLIP = VariationKey.of("turtle.pattern.flip");
    public static final VariationKey TURTLE_CLOWN_FLIP = VariationKey.of("turtle.clown.flip");
    public static final VariationKey TURTLE_TORTI_FLIP = VariationKey.of("turtle.tortie.flip");
}

