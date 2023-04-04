package mokiyoki.enhancedanimals.renderer.texture;

public enum TexturingType {

    //Group texturing types

    MERGE_GROUP, //the default for groups, simply merges each image into each other via pixel blending
    ALPHA_GROUP, //the grouping used for alpha masking layering. The first image(grouped images) in the group is treated as the mask the other group will be applied against it
    AVERAGE_GROUP, //this grouping will average the individual pixels for all textures in the group
    DYE_GROUP,

    /*******/

    //Layer texturing types

    APPLY_RED, //a layer specific type, used to apply a red blend to an individual texture from global rgb
    APPLY_BLACK, //a layer specific type, used to apply a red blend to an individual texture from global rgb
    APPLY_COLLAR_COLOUR, //a layer specific type, used to apply the collar colour to an individual texture from global rgb
    APPLY_BRIDLE_COLOUR, //a layer specific type, used to apply the bridle colour to an individual texture from global rgb
    APPLY_SADDLE_COLOUR, //a layer specific type, used to apply the saddle colour to an individual texture from global rgb
    APPLY_EYE_LEFT_COLOUR, //a layer specific type, used to apply the left eye colour to an individual texture from global rgb
    APPLY_EYE_RIGHT_COLOUR, //a layer specific type, used to apply the right eye colour to an individual texture from global rgb
    APPLY_DYE, //a layer specific type, used to apply a dye blend to an individual texture from dye rgb

    /*******/

    NONE //no special changes
}
