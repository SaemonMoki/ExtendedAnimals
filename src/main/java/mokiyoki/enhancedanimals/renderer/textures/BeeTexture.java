package mokiyoki.enhancedanimals.renderer.textures;

import mokiyoki.enhancedanimals.entity.EnhancedBee;
import mokiyoki.enhancedanimals.renderer.texture.TextureGrouping;
import mokiyoki.enhancedanimals.renderer.texture.TexturingType;
import mokiyoki.enhancedanimals.util.Genes;

import java.util.concurrent.ThreadLocalRandom;

public class BeeTexture {

    public static void calculateBeeTexture(EnhancedBee bee, int[] gene, boolean isAngry) {
        boolean isFemale = bee.getOrSetIsFemale();
        TextureGrouping parentGroup = new TextureGrouping(TexturingType.MERGE_GROUP);

        TextureGrouping base = new TextureGrouping(TexturingType.MASK_GROUP);

        calculateBeeShape(bee, gene, base, isFemale);
        calculateBeeColour(bee, base, gene);

        parentGroup.addGrouping(base);


        TextureGrouping details = new TextureGrouping(TexturingType.MERGE_GROUP);

        TextureGrouping eyes = new TextureGrouping(TexturingType.MERGE_GROUP);
        if (isAngry) {
            bee.addTextureToAnimalTextureGrouping(eyes, TexturingType.APPLY_RGB, "eyes/angry.png");
            details.addGrouping(eyes);
        } else {
            eyes.setTexturingType(TexturingType.MASK_GROUP);
            TextureGrouping eyeShape = new TextureGrouping(TexturingType.MERGE_GROUP);
            bee.addTextureToAnimalTextureGrouping(eyeShape, isFemale ? "eyes/female.png" : "eyes/male.png", isFemale ? "f" : "m");
            eyes.addGrouping(eyeShape);
            TextureGrouping eyeColor = new TextureGrouping(TexturingType.MERGE_GROUP);

            String eyeColour = "blue";
            if (gene[12] == 2 || gene[13] == 2) {
                if (gene[14] == 2 && gene[15] == 2) {
                    eyeColour = "yellow";
                } else {
                    eyeColour = "green";
                }
            }

            bee.addTextureToAnimalTextureGrouping(eyeColor, "eyes/colour/" + eyeColour + "/0.png", eyeColour);

            eyes.addGrouping(eyeColor);
        }

        details.addGrouping(eyes);

        parentGroup.addGrouping(details);
        bee.setTextureGrouping(parentGroup);
    }

    private static void calculateBeeColour(EnhancedBee bee, TextureGrouping base, int[] gene) {
        TextureGrouping colour = new TextureGrouping(TexturingType.MERGE_GROUP);
        int rng = ThreadLocalRandom.current().nextInt(13) - 6;
        bee.addTextureToAnimalTextureGrouping(colour, "body/colour/"+rng+".png", String.valueOf(rng));


        bee.addIndividualTextureToAnimalTextureGrouping(colour, TexturingType.MERGE_GROUP, "wings/colour/base.png");

        base.addGrouping(colour);

        if (rng!=0) {
            TextureGrouping thorax = new TextureGrouping(TexturingType.MASK_GROUP);
            TextureGrouping thoraxColour = new TextureGrouping(TexturingType.MERGE_GROUP);
            TextureGrouping thoraxShape = new TextureGrouping(TexturingType.MERGE_GROUP);

            rng = rng > 0 ? rng - 1 : rng + 1;
            bee.addTextureToAnimalTextureGrouping(thoraxShape, "body/pattern/thorax.png", "t");
            bee.addTextureToAnimalTextureGrouping(thoraxColour, "body/colour/"+rng+".png", String.valueOf(rng));

            thorax.addGrouping(thoraxShape);
            thorax.addGrouping(thoraxColour);
            base.addGrouping(thorax);
        } else {
            bee.addDelimiter();
        }

        calculateBeePattern(bee, base, gene);
    }

    private static void calculateBeePattern(EnhancedBee bee, TextureGrouping base, int[] gene) {
        TextureGrouping pattern = new TextureGrouping(TexturingType.MASK_GROUP);
        TextureGrouping patternColour = new TextureGrouping(TexturingType.MERGE_GROUP);
        TextureGrouping patternShape = new TextureGrouping(TexturingType.MERGE_GROUP);

        for (int i = 0; i < 33; i++) {
            if (ThreadLocalRandom.current().nextInt(2) == 0) {
                bee.addTextureToAnimalTextureGrouping(patternShape, "body/pattern/stripes/"+i+".png", String.valueOf(i));
                if (i!=32) {
                    i++;
                    bee.addTextureToAnimalTextureGrouping(patternShape, "body/pattern/stripes/" + i + ".png", String.valueOf(i));
                }
            }
        }


        if (gene[24] == 2 || gene[25] == 2) {
            bee.addTextureToAnimalTextureGrouping(patternColour, "body/colour/brown.png", "br");
        } else {
            bee.addTextureToAnimalTextureGrouping(patternColour, "body/colour/black.png", "b");
        }
        bee.addTextureToAnimalTextureGrouping(patternColour, "antenna/colour/dark.png", "d");

        pattern.addGrouping(patternShape);
        pattern.addGrouping(patternColour);
        base.addGrouping(pattern);
    }

    private static void calculateBeeShape(EnhancedBee bee, int[] gene, TextureGrouping base, boolean isFemale) {
        TextureGrouping baseShape = new TextureGrouping(TexturingType.MERGE_GROUP);
        bee.addIndividualTextureToAnimalTextureGrouping(baseShape, TexturingType.MERGE_GROUP, "body.png");

        bee.addTextureToAnimalTextureGrouping(baseShape, "stinger.png", isFemale);

        int wingSize = ThreadLocalRandom.current().nextInt(3);
        if (!isFemale) wingSize++;
        bee.addTextureToAnimalTextureGrouping(baseShape, "wings/shape/"+wingSize+".png", String.valueOf(wingSize));

        calculateLegs(bee, gene, baseShape);

        calculateAntenna(bee, baseShape);

        base.addGrouping(baseShape);
    }

    private static void calculateAntenna(EnhancedBee bee, TextureGrouping baseShape) {
        int shape = ThreadLocalRandom.current().nextInt(16);
        bee.addTextureToAnimalTextureGrouping(baseShape, "antenna/"+shape+".png", String.valueOf(shape));
    }

    private static void calculateLegs(EnhancedBee bee, int[] gene, TextureGrouping baseShape) {
        String legLength = "1";
        if (gene[20] == 2 && gene[21] == 2) {
            if (gene[22] == 1 || gene[23] == 1) {
                legLength = "2";
            }
        } else if (gene[22] == 2 && gene[23] == 2) {
            legLength = "0";
        }
        bee.addTextureToAnimalTextureGrouping(baseShape, "legs/"+legLength+".png", legLength);
    }
}
