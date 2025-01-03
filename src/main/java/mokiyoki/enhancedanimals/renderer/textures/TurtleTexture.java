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
        int pibald = 0;


        if (gene[0] == 1 || gene[1] == 1) {
            //non-albino
            if (gene[2] == 1 || gene[3] == 1 ) {
                //non-axanthic
                if (gene[4] == 2 || gene[5] == 2) {
                    //melanized
                    base = gene[4] == gene[5] ? 4 : 5;
                }
            } else {
                //axanthic
                if (gene[4] == 2 || gene[5] == 2) {
                    //melanized
                    base = gene[4] == gene[5] ? 6 : 7;
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

        TextureGrouping parentGroup = new TextureGrouping(TexturingType.MERGE_GROUP);

        turtle.addTextureToAnimalTextureGrouping(parentGroup, "albino_turtle.png");

        if (gene[6] == 2 && gene[7] == 2) {
            TextureGrouping spotShapes = new TextureGrouping(TexturingType.MERGE_GROUP);
            if ( Character.isDigit(uuid[5]) ){
                pibald = 1 + (uuid[5]-48);
            } else {
                char d = uuid[5];

                switch (d) {
                    case 'a':
                        pibald = 11;
                        break;
                    case 'b':
                        pibald = 12;
                        break;
                    case 'c':
                        pibald = 13;
                        break;
                    case 'd':
                        pibald = 14;
                        break;
                    case 'e':
                        pibald = 15;
                        break;
                    case 'f':
                        pibald = 16;
                        break;
                    default:
                        pibald = 0;
                }
            }

            if (gene[8] == 2 && gene[9] == 2) {
                pibald = 1;
            } else if ((pibald-1) % 4 == 0){
                pibald = (int)(pibald + (pibald*0.25F));
            }
        }

        turtle.setTextureGrouping(parentGroup);
    }
}
