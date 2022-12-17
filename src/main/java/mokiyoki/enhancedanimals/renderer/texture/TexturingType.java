package mokiyoki.enhancedanimals.renderer.texture;

public enum TexturingType {

    //Group texturing types

    MERGE_GROUP, //the default for groups, simply merges each image into each other via pixel blending
    ALPHA_GROUP, //the grouping used for alpha masking layering. The first image(grouped images) in the group is treated as the mask the other group will be applied against it
    AVERAGE_GROUP, //this grouping will average the individual pixels for all textures in the group

    /*******/

    //Layer texturing types

    APPLY_RED, //a layer specific type, used to apply a red blend to an individual texture from global rgb
    APPLY_BLACK, //a layer specific type, used to apply a red blend to an individual texture from global rgb
    APPLY_DYE, //a layer specific type, used to apply a dye blend to an individual texture from dye rgb

    /*******/

    NONE //no special changes
}
