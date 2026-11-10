package mokiyoki.enhancedanimals.renderer.textures;

import mokiyoki.enhancedanimals.entity.EnhancedLlama;
import mokiyoki.enhancedanimals.renderer.texture.Mask;
import mokiyoki.enhancedanimals.renderer.texture.TextureBuilder;
import mokiyoki.enhancedanimals.renderer.texture.TextureGroup;

import static mokiyoki.enhancedanimals.entity.util.VariationKeys.*;

public class LlamaTexture {
    private static final String[] LLAMA_TEXTURES_GROUND = new String[] {
            "brokenlogic.png", "ground_paleshaded.png", "ground_shaded.png", "ground_blacktan.png", "ground_bay.png", "ground_mahogany.png", "ground_blacktan.png", "black.png", "fawn.png"
    };

    private static final String[] LLAMA_TEXTURES_PATTERN = new String[] {
            "", "pattern_paleshaded.png", "pattern_shaded.png", "pattern_blackred.png", "pattern_bay.png", "pattern_mahogany.png", "pattern_blacktan.png"
    };

    private static final String[] LLAMA_TEXTURES_ROAN = new String[] {
            "", "roan_0.png", "roan_1.png", "roan_2.png", "roan_3.png", "roan_4.png", "roan_5.png", "roan_6.png", "roan_7.png", "roan_8.png", "roan_9.png", "roan_a.png", "roan_b.png", "roan_c.png", "roan_d.png", "roan_e.png", "roan_f.png"
    };

    // higher numbers are more white
    private static final String[] LLAMA_TEXTURES_TUXEDO = new String[] {
            "", "tuxedo_0.png", "tuxedo_1.png", "tuxedo_2.png", "tuxedo_3.png", "tuxedo_4.png", "tuxedo_5.png", "tuxedo_6.png", "tuxedo_7.png", "tuxedo_8.png", "tuxedo_9.png", "tuxedo_a.png", "tuxedo_b.png", "tuxedo_c.png", "tuxedo_d.png", "tuxedo_e.png", "tuxedo_f.png"
    };

    // higher numbers are more white
    private static final String[] LLAMA_TEXTURES_PIEBALD = new String[] {
            "", "piebald_0.png", "piebald_1.png", "piebald_2.png", "piebald_3.png", "piebald_4.png", "piebald_5.png", "piebald_6.png", "piebald_7.png", "piebald_8.png", "piebald_9.png", "piebald_a.png", "piebald_b.png", "piebald_c.png", "piebald_d.png", "piebald_e.png", "piebald_f.png"
    };

    // higher numbers are more white
    private static final String[] LLAMA_TEXTURES_DOMWHITE = new String[] {
            "", "domwhite_leaky.png", "domwhite_tinted.png", "domwhite_solid.png"
    };

    private static final String[] LLAMA_TEXTURES_EYES = new String[] {
            "eyes_black.png", "eyes_blue.png", "eyes_iceblue.png"
    };

    private static final String[] LLAMA_TEXTURES_SKIN = new String[] {
            "skin_black.png", "skin_pink.png"
    };

    public static void calculateLlamaTextures(EnhancedLlama llama, int[] gene) {
        buildLlamaTexture(TextureBuilder.of(llama, gene));
    }

    static void buildLlamaTexture(TextureBuilder llamaTexture) {
        int ground;
        int pattern = 0;
        int roan = 0;
        int tux = 0;
        int piebald = 0;
        int domwhite = 0;
        int eyes = 0;
        int skin = 0;
        // i is a random modifier

        if (llamaTexture.eitherAllelesHave(14, 1)) {
            //Dominant Black
            ground = 7;
        } else if (llamaTexture.eitherAllelesHave(14, 3)) {
            //fawn self
            ground = 8;
        } else if (llamaTexture.eitherAllelesHave(16, 1)) {
            //pale shaded fawn
            ground = 1;
            pattern = 1;
        } else if (llamaTexture.eitherAllelesHave(16, 2)) {
            //shaded fawn
            ground = 2;
            pattern = 2;
        } else if (llamaTexture.eitherAllelesHave(16, 3)) {
            //black trimmed red
            ground = 3;
            pattern = 3;
        } else if (llamaTexture.eitherAllelesHave(16, 4)) {
            //bay
            ground = 4;
            pattern = 4;
        } else if (llamaTexture.eitherAllelesHave(16, 5)) {
            //mahogany
            ground = 5;
            pattern = 5;
        } else if (llamaTexture.eitherAllelesHave(16, 6)) {
            //black and tan
            ground = 6;
            pattern = 6;
        } else {
            //black
            ground = 7;
        }

        if (llamaTexture.eitherAllelesHave(6, 1)) {
            //dominant white   0 1 2 3 4 5 6 7 8 9 a b c d e f
            int d = llamaTexture.randomHex(LLAMA_DOMINANT_WHITE);
            domwhite = d < 5 ? 1 : d < 10 ? 2 : 3;
        }

        if (llamaTexture.eitherAllelesHave(8, 1)) {
            //roan
            roan = 1 + llamaTexture.randomHex(LLAMA_ROAN);
        }

        if (llamaTexture.isHomoFor(10, 2)) {
            //piebald
            piebald = 1 + llamaTexture.randomHex(LLAMA_PIEBALD);
        }

        if (llamaTexture.eitherAllelesHave(12, 1)) {
            //tuxedo
            tux = 1 + llamaTexture.randomHex(LLAMA_TUXEDO);
        }

        if (domwhite > 0) {
            skin = 1;
            if (piebald > 0) {
                eyes = 1;
            }
        }

        TextureGroup root = llamaTexture.root();
        root.addTexture(LLAMA_TEXTURES_GROUND[ground]);
        root.addTexture(LLAMA_TEXTURES_PATTERN[pattern]);

        Mask whiteSpots = root.addMaskGroup();
        whiteSpots.maskTexture().addTexture("domwhite_solid.png");
        whiteSpots.content().addTexture(LLAMA_TEXTURES_ROAN[roan]);
        whiteSpots.content().addTexture(LLAMA_TEXTURES_TUXEDO[tux]);
        whiteSpots.content().addTexture(LLAMA_TEXTURES_PIEBALD[piebald]);
        whiteSpots.content().addTexture(LLAMA_TEXTURES_DOMWHITE[domwhite]);

        //suri coat texture
        root.addTextureIf(llamaTexture.isHomoFor(20, 2), "fur_suri.png");

        root.addMergeGroup(LLAMA_TEXTURES_EYES[eyes], LLAMA_TEXTURES_SKIN[skin]);

        llamaTexture.finishTexture();
    }
}
