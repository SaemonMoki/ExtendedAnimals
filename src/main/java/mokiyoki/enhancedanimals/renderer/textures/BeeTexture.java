package mokiyoki.enhancedanimals.renderer.textures;

import mokiyoki.enhancedanimals.entity.EnhancedBee;
import mokiyoki.enhancedanimals.renderer.texture.TextureGrouping;
import mokiyoki.enhancedanimals.renderer.texture.TexturingType;
import mokiyoki.enhancedanimals.util.Genes;

public class BeeTexture {

    public static void calculateBeeTexture(EnhancedBee bee, Genes gene) {
        TextureGrouping parentGroup = new TextureGrouping(TexturingType.MERGE_GROUP);

        bee.addIndividualTextureToAnimalTextureGrouping(parentGroup, TexturingType.MERGE_GROUP, "test.png");

        bee.setTextureGrouping(parentGroup);
    }
}
