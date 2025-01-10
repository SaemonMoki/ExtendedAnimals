package mokiyoki.enhancedanimals.renderer.textures;

import mokiyoki.enhancedanimals.entity.EnhancedTurtle;
import mokiyoki.enhancedanimals.renderer.texture.TextureGrouping;
import mokiyoki.enhancedanimals.renderer.texture.TexturingType;

public class TurtleTexture {
    private static final String[] TURTLE_TEXTURES_BASE = new String[] {
            "normal_turtle.png", "albino_turtle.png", "axanthic_turtle.png", "axanthic_albino_turtle.png", "black_turtle.png", "het_melanised_normal.png", "axanthic_black_turtle.png", "het_melanised_axanthic.png"
    };

    public static void calculateTurtleTextures(EnhancedTurtle turtle, int[] gene, char[] uuid) {
        TextureGrouping parentGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        int base = 0;

        if (gene[0] == 1 || gene[1] == 1) {
            //non-albino
            if (gene[2] == 1 || gene[3] == 1 ) {
                //non-axanthic
                if (gene[4] == 2 || gene[5] == 2) {
                    //melanized
                    if (gene[4] == gene[5]) {
                        base = 4;
                    } else {
                        base = 5;
                    }
                }
            } else {
                //axanthic
                if (gene[4] == 2 || gene[5] == 2) {
                    //melanized
                    if (gene[4] == gene[5]) {
                        base = 6;
                    } else {
                        base = 7;
                    }
                } else {
                    base = 2;
                }
            }
        } else {
            //albino
            if (gene[2] == 1 || gene[3] == 1 ) {
                //non-axanthic
                base = 1;
            } else {
                //axanthic
                base = 3;
            }
        }

        TextureGrouping baseTexture = new TextureGrouping(TexturingType.MERGE_GROUP);
        turtle.addTextureToAnimalTextureGrouping(baseTexture, TURTLE_TEXTURES_BASE[base], String.valueOf(base));
        parentGroup.addGrouping(baseTexture);

        if (gene[6] == 2 && gene[7] == 2) {
            piebald(turtle, gene, uuid, parentGroup);
        } else {
            turtle.addDelimiter();
        }

        turtle.setTextureGrouping(parentGroup);
    }

    private static void piebald(EnhancedTurtle turtle, int[] gene, char[] uuid, TextureGrouping parentGroup) {
        int piebald;
        TextureGrouping spots = new TextureGrouping(TexturingType.MASK_GROUP);

        if ( Character.isDigit(uuid[5]) ){
            piebald = 1 + (uuid[5]-48);
        } else {
            char d = uuid[5];

            switch (d) {
                case 'a':
                    piebald = 11;
                    break;
                case 'b':
                    piebald = 12;
                    break;
                case 'c':
                    piebald = 13;
                    break;
                case 'd':
                    piebald = 14;
                    break;
                case 'e':
                    piebald = 15;
                    break;
                case 'f':
                    piebald = 16;
                    break;
                default:
                    piebald = 0;
            }
        }

        if (gene[8] == 2 && gene[9] == 2) {
            piebald = 1;
        } else {
            piebald = piebald%11;
        }

        turtle.addTextureToAnimalTextureGrouping(spots, "spots/piebald/" + piebald + ".png", String.valueOf(piebald));

        turtle.addTextureToAnimalTextureGrouping(spots, "spots/white1.png");

        parentGroup.addGrouping(spots);
    }
}
