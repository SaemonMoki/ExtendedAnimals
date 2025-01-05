package mokiyoki.enhancedanimals.renderer.textures;

import mokiyoki.enhancedanimals.entity.EnhancedTurtle;
import mokiyoki.enhancedanimals.renderer.texture.TextureGrouping;
import mokiyoki.enhancedanimals.renderer.texture.TexturingType;

public class TurtleTexture {
    private static final String[] TURTLE_TEXTURES_BASE = new String[] {
            "normal_turtle.png", "albino_turtle.png", "axanthic_turtle.png", "axanthic_albino_turtle.png", "black_turtle.png", "het_melanised_normal.png", "axanthic_black_turtle.png", "het_melanised_axanthic.png"
    };

    public static void calculateTurtleTextures(EnhancedTurtle turtle, int[] gene, char[] uuid) {
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

        boolean nonaxanthic = gene[2] == 1 || gene[3] == 1;
        String axanthic = nonaxanthic ? "nonaxanthic/" : "axanthic/";
        String eyeColour;

        TextureGrouping parentGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        TextureGrouping baseColour = new TextureGrouping(TexturingType.MERGE_GROUP);
        parentGroup.addGrouping(baseColour);

        if (gene[0]==1 || gene[1]==1) {
            turtle.addTextureToAnimalTextureGrouping(baseColour, "base/" + axanthic + "patternless.png", nonaxanthic?"nx":"ax");
            TextureGrouping patternGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
            String pattern;
            if (gene[4] == 1 || gene[5] == 1) {
                turtle.addTextureToAnimalTextureGrouping(patternGroup, "pattern/colour/" + axanthic + "green.png", nonaxanthic ? "nx" : "ax");
                if (gene[4]!=gene[5]) {
                    turtle.addIndividualTextureToAnimalTextureGrouping(patternGroup, TexturingType.APPLY_RGBA, "pattern/colour/charcoal.png", 128 << 24 | 255 << 16 | 255 << 8 | 255);
                    eyeColour = "black";
                } else {
                    turtle.addDelimiter("nc");
                    eyeColour = nonaxanthic?"grey":"navy";
                }
            } else {
                turtle.addTextureToAnimalTextureGrouping(patternGroup, "pattern/colour/charcoal.png", "c");
                eyeColour = "black";
            }
            parentGroup.addGrouping(patternGroup);
        } else {
            turtle.addTextureToAnimalTextureGrouping(baseColour, "base/" + axanthic + "albino.png", nonaxanthic?"anx":"aax");
            eyeColour = nonaxanthic ? "blue" : "pink";
        }

        if (gene[6] == 2 && gene[7] == 2) {
            TextureGrouping spots = new TextureGrouping(TexturingType.MERGE_GROUP);
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
            } else if ((pibald-1) % 4 == 0) {
                pibald = (int)(pibald + (pibald*0.25F));
            }


            turtle.addTextureToAnimalTextureGrouping(spots, "pibald"+pibald, String.valueOf(pibald));
            parentGroup.addGrouping(spots);
        } else {
            turtle.addDelimiter();
        }

        TextureGrouping detailGroup = new TextureGrouping(TexturingType.MERGE_GROUP);



        parentGroup.addGrouping(detailGroup);
        turtle.addTextureToAnimalTextureGrouping(parentGroup, "eyes/" + eyeColour + ".png", eyeColour);
        turtle.setTextureGrouping(parentGroup);
    }
}
