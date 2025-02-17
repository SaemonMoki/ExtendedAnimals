package mokiyoki.enhancedanimals.renderer.textures;

import mokiyoki.enhancedanimals.entity.EnhancedHorse;
import mokiyoki.enhancedanimals.renderer.texture.TextureGrouping;
import mokiyoki.enhancedanimals.renderer.texture.TexturingType;

public class HorseTexture {

    public static void calculateHorseTextures(EnhancedHorse horse) {
        int[] gene = horse.getGenes().getAutosomalGenes();
        TextureGrouping parentGroup = new TextureGrouping(TexturingType.MERGE_GROUP);

//        TextureGrouping skinGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
//
//        if (addSkinPattern(horse, skinGroup)) horse.addDelimiter();
//
//        parentGroup.addGrouping(skinGroup);
//
//        TextureGrouping furGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
//
//        if (addRedPattern(horse, furGroup)) horse.addDelimiter();
//
//        if (addPattern(horse, gene, furGroup)) horse.addDelimiter();
//
//        if (createLeopardSpots(horse, gene, furGroup)) horse.addDelimiter();
//
//        parentGroup.addGrouping(furGroup);
//
//        TextureGrouping detailGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
//        horse.addIndividualTextureToAnimalTextureGrouping(detailGroup,TexturingType.APPLY_EYE_LEFT_COLOUR, "eye_left.png");
//        horse.addIndividualTextureToAnimalTextureGrouping(detailGroup,TexturingType.APPLY_EYE_LEFT_COLOUR, "eye_right.png");
        horse.addTextureToAnimalTextureGrouping(parentGroup,"horsebase.png");
        horse.addTextureToAnimalTextureGrouping(parentGroup,"modelassist.png");
        horse.setTextureGrouping(parentGroup);
    }

    private static boolean addSkinPattern(EnhancedHorse horse, TextureGrouping skinGroup) {
        String colour = "grey";
        horse.addTextureToAnimalTextureGrouping(skinGroup, "skin/"+colour+".png");
        return false;
    }

    private static boolean addRedPattern(EnhancedHorse horse, TextureGrouping furGroup) {
        horse.addTextureToAnimalTextureGrouping(furGroup, TexturingType.APPLY_RGBA, "coat/base.png", "b", 7422239);
        return false;
    }

    private static boolean addPattern(EnhancedHorse horse, int[] gene, TextureGrouping furGroup) {
        if (gene[12]==1|| gene[13]==1) {
            String pattern = "self";
            if (gene[14]==1|| gene[15]==1) {
                pattern = "bay";
            }/* else if (gene[14]==2 || gene[15]==2){
                pattern = "bay2";
            } else if (gene[14]==3 || gene[15]==3) {
                pattern = "seal";
            }*/
            horse.addTextureToAnimalTextureGrouping(furGroup, "blackpattern/" + pattern + ".png");
            return false;
        }

            //chestnut
            return true;
    }

    private static boolean createLeopardSpots(EnhancedHorse horse, int[] gene, TextureGrouping furGroup) {

        return true;
    }
}
