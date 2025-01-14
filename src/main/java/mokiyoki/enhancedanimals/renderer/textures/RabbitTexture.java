package mokiyoki.enhancedanimals.renderer.textures;

import mokiyoki.enhancedanimals.entity.EnhancedRabbit;
import mokiyoki.enhancedanimals.renderer.texture.TextureGrouping;
import mokiyoki.enhancedanimals.renderer.texture.TexturingType;

public class RabbitTexture {
    private static final String[] RABBIT_TEXTURES_UNDER = new String[] {
            "under_cream.png", "under_grey.png", "under_white.png"
    };

    // 1 7 13
    private static final String[] RABBIT_TEXTURES_LOWER = new String[] {
            "", "middle_orange.png", "middle_orangetan.png", "middle_orangeagouti.png",
    };

    private static final String[] RABBIT_TEXTURES_MIDDLE = new String[] {
            "", "middle_orange.png", "middle_orangetan.png", "middle_orangeagouti.png",
            "under_cream.png", "middle_creamtan.png", "middle_creamagouti.png",
            "under_white.png", "middle_whitetan.png", "middle_whiteagouti.png"
    };

    private static final String[] RABBIT_TEXTURES_HIGHER = new String[] {
            "", "higher_agouti.png", "higher_tan.png", "higher_self.png",
            "higher_agouti_blue.png", "higher_tan_blue.png", "higher_self_blue.png",
            "higher_agouti_choc.png", "higher_tan_choc.png", "higher_self_choc.png",
            "higher_agouti_lilac.png", "higher_tan_lilac.png", "higher_self_lilac.png",
            "higher_agoutiseal.png", "higher_tanseal.png", "higher_selfseal.png",
            "higher_agoutiseal_blue.png", "higher_tanseal_blue.png", "higher_selfseal_blue.png",
            "higher_agoutiseal_choc.png", "higher_tanseal_choc.png", "higher_selfseal_choc.png",
            "higher_agoutiseal_lilac.png", "higher_tanseal_lilac.png", "higher_selfseal_lilac.png",
            "higher_agoutisable.png", "higher_tansable.png", "higher_selfsable.png",
            "higher_agoutisable_blue.png", "higher_tansable_blue.png", "higher_selfsable_blue.png",
            "higher_agoutisable_choc.png", "higher_tansable_choc.png", "higher_selfsable_choc.png",
            "higher_agoutisable_lilac.png", "higher_tansable_lilac.png", "higher_selfsable_lilac.png",
            "higher_agoutihimy.png", "higher_tanhimy.png", "higher_selfhimy.png",
            "higher_agoutihimy_blue.png", "higher_tanhimy_blue.png", "higher_selfhimy_blue.png",
            "higher_agoutihimy_choc.png", "higher_tanhimy_choc.png", "higher_selfhimy_choc.png",
            "higher_agoutihimy_lilac.png", "higher_tanhimy_lilac.png", "higher_selfhimy_lilac.png",
            "higher_agouti_lutino.png", "higher_tan_lutino.png", "higher_self_lutino.png",
            "higher_agouti_blue_lutino.png", "higher_tan_blue_lutino.png", "higher_self_blue_lutino.png",
            "higher_agouti_choc_lutino.png", "higher_tan_choc_lutino.png", "higher_self_choc_lutino.png",
            "higher_agouti_lilac_lutino.png", "higher_tan_lilac_lutino.png", "higher_self_lilac_lutino.png",
            "higher_agoutiseal_lutino.png", "higher_tanseal_lutino.png", "higher_selfseal_lutino.png",
            "higher_agoutiseal_blue_lutino.png", "higher_tanseal_blue_lutino.png", "higher_selfseal_blue_lutino.png",
            "higher_agoutiseal_choc_lutino.png", "higher_tanseal_choc_lutino.png", "higher_selfseal_choc_lutino.png",
            "higher_agoutiseal_lilac_lutino.png", "higher_tanseal_lilac_lutino.png", "higher_selfseal_lilac_lutino.png",
            "higher_agoutisable_lutino.png", "higher_tansable_lutino.png", "higher_selfsable_lutino.png",
            "higher_agoutisable_blue_lutino.png", "higher_tansable_blue_lutino.png", "higher_selfsable_blue_lutino.png",
            "higher_agoutisable_choc_lutino.png", "higher_tansable_choc_lutino.png", "higher_selfsable_choc_lutino.png",
            "higher_agoutisable_lilac_lutino.png", "higher_tansable_lilac_lutino.png", "higher_selfsable_lilac_lutino.png",
            "higher_agoutihimy_lutino.png", "higher_tanhimy_lutino.png", "higher_selfhimy_lutino.png",
            "higher_agoutihimy_blue_lutino.png", "higher_tanhimy_blue_lutino.png", "higher_selfhimy_blue_lutino.png",
            "higher_agoutihimy_choc_lutino.png", "higher_tanhimy_choc_lutino.png", "higher_selfhimy_choc_lutino.png",
            "higher_agoutihimy_lilac_lutino.png", "higher_tanhimy_lilac_lutino.png", "higher_selfhimy_lilac_lutino.png"
    };

    private static final String[] RABBIT_TEXTURES_TOP = new String[] {
            "", "top_steal.png", "top_stealdark.png", "top_tan.png", "top_self.png",
            "top_steal_blue.png", "top_stealdark_blue.png", "top_tan_blue.png", "top_self_blue.png",
            "top_steal_choc.png", "top_stealdark_choc.png", "top_tan_choc.png", "top_self_choc.png",
            "top_steal_lilac.png", "top_stealdark_lilac.png", "top_tan_lilac.png", "top_self_lilac.png",
            "top_steal_lutino.png", "top_stealdark_lutino.png", "top_tan_lutino.png", "top_self_lutino.png",
            "top_steal_blue_lutino.png", "top_stealdark_blue_lutino.png", "top_tan_blue_lutino.png", "top_self_blue_lutino.png",
            "top_steal_choc_lutino.png", "top_stealdark_choc_lutino.png", "top_tan_choc_lutino.png", "top_self_choc_lutino.png",
            "top_steal_lilac_lutino.png", "top_stealdark_lilac_lutino.png", "top_tan_lilac_lutino.png", "top_self_lilac_lutino.png",
            "top_harly0.png", "top_harly1.png", "top_harly2.png", "top_harly3.png", "top_harly4.png", "top_harly5.png", "top_harly6.png", "top_harly7.png", "top_harly8.png", "top_harly9.png", "top_harlya.png", "top_harlyb.png", "top_harlyc.png", "top_harlyd.png", "top_harlye.png", "top_harlyf.png",
            "top_harly0_blue.png", "top_harly1_blue.png", "top_harly2_blue.png", "top_harly3_blue.png", "top_harly4_blue.png", "top_harly5_blue.png", "top_harly6_blue.png", "top_harly7_blue.png", "top_harly8_blue.png", "top_harly9_blue.png", "top_harlya_blue.png", "top_harlyb_blue.png", "top_harlyc_blue.png", "top_harlyd_blue.png", "top_harlye_blue.png", "top_harlyf_blue.png",
            "top_harly0_choc.png", "top_harly1_choc.png", "top_harly2_choc.png", "top_harly3_choc.png", "top_harly4_choc.png", "top_harly5_choc.png", "top_harly6_choc.png", "top_harly7_choc.png", "top_harly8_choc.png", "top_harly9_choc.png", "top_harlya_choc.png", "top_harlyb_choc.png", "top_harlyc_choc.png", "top_harlyd_choc.png", "top_harlye_choc.png", "top_harlyf_choc.png",
            "top_harly0_lilac.png", "top_harly1_lilac.png", "top_harly2_lilac.png", "top_harly3_lilac.png", "top_harly4_lilac.png", "top_harly5_lilac.png", "top_harly6_lilac.png", "top_harly7_lilac.png", "top_harly8_lilac.png", "top_harly9_lilac.png", "top_harlya_lilac.png", "top_harlyb_lilac.png", "top_harlyc_lilac.png", "top_harlyd_lilac.png", "top_harlye_lilac.png", "top_harlyf_lilac.png",
            "top_japbrindle0.png", "top_japbrindle1.png", "top_japbrindle2.png", "top_japbrindle3.png", "top_japbrindle4.png", "top_japbrindle5.png", "top_japbrindle6.png", "top_japbrindle7.png", "top_japbrindle8.png", "top_japbrindle9.png", "top_japbrindlea.png", "top_japbrindleb.png", "top_japbrindlec.png", "top_japbrindled.png", "top_japbrindlee.png", "top_japbrindlef.png",
            "top_japbrindle0_blue.png", "top_japbrindle1_blue.png", "top_japbrindle2_blue.png", "top_japbrindle3_blue.png", "top_japbrindle4_blue.png", "top_japbrindle5_blue.png", "top_japbrindle6_blue.png", "top_japbrindle7_blue.png", "top_japbrindle8_blue.png", "top_japbrindle9_blue.png", "top_japbrindlea_blue.png", "top_japbrindleb_blue.png", "top_japbrindlec_blue.png", "top_japbrindled_blue.png", "top_japbrindlee_blue.png", "top_japbrindlef_blue.png",
            "top_japbrindle0_choc.png", "top_japbrindle1_choc.png", "top_japbrindle2_choc.png", "top_japbrindle3_choc.png", "top_japbrindle4_choc.png", "top_japbrindle5_choc.png", "top_japbrindle6_choc.png", "top_japbrindle7_choc.png", "top_japbrindle8_choc.png", "top_japbrindle9_choc.png", "top_japbrindlea_choc.png", "top_japbrindleb_choc.png", "top_japbrindlec_choc.png", "top_japbrindled_choc.png", "top_japbrindlee_choc.png", "top_japbrindlef_choc.png",
            "top_japbrindle0_lilac.png", "top_japbrindle1_lilac.png", "top_japbrindle2_lilac.png", "top_japbrindle3_lilac.png", "top_japbrindle4_lilac.png", "top_japbrindle5_lilac.png", "top_japbrindle6_lilac.png", "top_japbrindle7_lilac.png", "top_japbrindle8_lilac.png", "top_japbrindle9_lilac.png", "top_japbrindlea_lilac.png", "top_japbrindleb_lilac.png", "top_japbrindlec_lilac.png", "top_japbrindled_lilac.png", "top_japbrindlee_lilac.png", "top_japbrindlef_lilac.png",
            "top_harly0_lutino.png", "top_harly1_lutino.png", "top_harly2_lutino.png", "top_harly3_lutino.png", "top_harly4_lutino.png", "top_harly5_lutino.png", "top_harly6_lutino.png", "top_harly7_lutino.png", "top_harly8_lutino.png", "top_harly9_lutino.png", "top_harlya_lutino.png", "top_harlyb_lutino.png", "top_harlyc_lutino.png", "top_harlyd_lutino.png", "top_harlye_lutino.png", "top_harlyf_lutino.png",
            "top_harly0_blue_lutino.png", "top_harly1_blue_lutino.png", "top_harly2_blue_lutino.png", "top_harly3_blue_lutino.png", "top_harly4_blue_lutino.png", "top_harly5_blue_lutino.png", "top_harly6_blue_lutino.png", "top_harly7_blue_lutino.png", "top_harly8_blue_lutino.png", "top_harly9_blue_lutino.png", "top_harlya_blue_lutino.png", "top_harlyb_blue_lutino.png", "top_harlyc_blue_lutino.png", "top_harlyd_blue_lutino.png", "top_harlye_blue_lutino.png", "top_harlyf_blue_lutino.png",
            "top_harly0_choc_lutino.png", "top_harly1_choc_lutino.png", "top_harly2_choc_lutino.png", "top_harly3_choc_lutino.png", "top_harly4_choc_lutino.png", "top_harly5_choc_lutino.png", "top_harly6_choc_lutino.png", "top_harly7_choc_lutino.png", "top_harly8_choc_lutino.png", "top_harly9_choc_lutino.png", "top_harlya_choc_lutino.png", "top_harlyb_choc_lutino.png", "top_harlyc_choc_lutino.png", "top_harlyd_choc_lutino.png", "top_harlye_choc_lutino.png", "top_harlyf_choc_lutino.png",
            "top_harly0_lilac_lutino.png", "top_harly1_lilac_lutino.png", "top_harly2_lilac_lutino.png", "top_harly3_lilac_lutino.png", "top_harly4_lilac_lutino.png", "top_harly5_lilac_lutino.png", "top_harly6_lilac_lutino.png", "top_harly7_lilac_lutino.png", "top_harly8_lilac_lutino.png", "top_harly9_lilac_lutino.png", "top_harlya_lilac_lutino.png", "top_harlyb_lilac_lutino.png", "top_harlyc_lilac_lutino.png", "top_harlyd_lilac_lutino.png", "top_harlye_lilac_lutino.png", "top_harlyf_lilac_lutino.png",
            "top_japbrindle0_lutino.png", "top_japbrindle1_lutino.png", "top_japbrindle2_lutino.png", "top_japbrindle3_lutino.png", "top_japbrindle4_lutino.png", "top_japbrindle5_lutino.png", "top_japbrindle6_lutino.png", "top_japbrindle7_lutino.png", "top_japbrindle8_lutino.png", "top_japbrindle9_lutino.png", "top_japbrindlea_lutino.png", "top_japbrindleb_lutino.png", "top_japbrindlec_lutino.png", "top_japbrindled_lutino.png", "top_japbrindlee_lutino.png", "top_japbrindlef_lutino.png",
            "top_japbrindle0_blue_lutino.png", "top_japbrindle1_blue_lutino.png", "top_japbrindle2_blue_lutino.png", "top_japbrindle3_blue_lutino.png", "top_japbrindle4_blue_lutino.png", "top_japbrindle5_blue_lutino.png", "top_japbrindle6_blue_lutino.png", "top_japbrindle7_blue_lutino.png", "top_japbrindle8_blue_lutino.png", "top_japbrindle9_blue_lutino.png", "top_japbrindlea_blue_lutino.png", "top_japbrindleb_blue_lutino.png", "top_japbrindlec_blue_lutino.png", "top_japbrindled_blue_lutino.png", "top_japbrindlee_blue_lutino.png", "top_japbrindlef_blue_lutino.png",
            "top_japbrindle0_choc_lutino.png", "top_japbrindle1_choc_lutino.png", "top_japbrindle2_choc_lutino.png", "top_japbrindle3_choc_lutino.png", "top_japbrindle4_choc_lutino.png", "top_japbrindle5_choc_lutino.png", "top_japbrindle6_choc_lutino.png", "top_japbrindle7_choc_lutino.png", "top_japbrindle8_choc_lutino.png", "top_japbrindle9_choc_lutino.png", "top_japbrindlea_choc_lutino.png", "top_japbrindleb_choc_lutino.png", "top_japbrindlec_choc_lutino.png", "top_japbrindled_choc_lutino.png", "top_japbrindlee_choc_lutino.png", "top_japbrindlef_choc_lutino.png",
            "top_japbrindle0_lilac_lutino.png", "top_japbrindle1_lilac_lutino.png", "top_japbrindle2_lilac_lutino.png", "top_japbrindle3_lilac_lutino.png", "top_japbrindle4_lilac_lutino.png", "top_japbrindle5_lilac_lutino.png", "top_japbrindle6_lilac_lutino.png", "top_japbrindle7_lilac_lutino.png", "top_japbrindle8_lilac_lutino.png", "top_japbrindle9_lilac_lutino.png", "top_japbrindlea_lilac_lutino.png", "top_japbrindleb_lilac_lutino.png", "top_japbrindlec_lilac_lutino.png", "top_japbrindled_lilac_lutino.png", "top_japbrindlee_lilac_lutino.png", "top_japbrindlef_lilac_lutino.png"
    };

    private static final String[] RABBIT_TEXTURES_DUTCH = new String[] {
            "", "dutch0.png", "dutch1.png", "dutch2.png", "dutch3.png", "dutch4.png", "dutch5.png", "dutch6.png", "dutch7.png", "dutch8.png", "dutch9.png", "dutcha.png", "dutchb.png", "dutchc.png", "dutchd.png", "dutche.png", "dutchf.png"
    };

    private static final String[] RABBIT_TEXTURES_BROKEN = new String[] {
            "", "broken0.png", "broken1.png", "broken2.png", "broken3.png", "broken4.png", "broken5.png", "broken6.png", "broken7.png", "broken8.png", "broken9.png", "brokena.png", "brokenb.png", "brokenc.png", "brokend.png", "brokene.png", "brokenf.png",
            "charlie0.png", "charlie1.png", "charlie2.png", "charlie3.png", "charlie4.png", "charlie5.png", "charlie6.png", "charlie7.png", "charlie8.png", "charlie9.png", "charliea.png", "charlieb.png", "charliec.png", "charlied.png", "charliee.png", "charlief.png",
    };

    private static final String[] RABBIT_TEXTURES_VIENNA = new String[] {
            "", "vienna0.png", "vienna1.png", "vienna2.png", "vienna3.png", "vienna4.png", "vienna5.png", "vienna6.png", "vienna7.png", "vienna8.png", "vienna9.png", "viennaa.png", "viennab.png", "viennac.png", "viennad.png", "viennae.png", "viennaf.png"
    };

    private static final String[] RABBIT_TEXTURES_FUR = new String[] {
            "", "fur_normal.png", "fur_satin.png", "fur_angora1.png" , "fur_angora2.png", "fur_angora4.png", "fur_angora4.png"
    };

    private static final String[] RABBIT_TEXTURES_EYES = new String[] {
            "eyes_black.png", "eyes_grey.png", "eyes_albino.png", "eyes_pink.png"
    };

    private static final String[] RABBIT_TEXTURES_VIENNAEYES = new String[] {
            "", "", "", "", "", "", "", "", "eyes_blue.png", "eyes_blue.png", "eyes_blue.png", "eyes_blue.png", "eyes_bluel.png", "eyes_bluel.png", "eyes_bluel.png", "eyes_bluer.png", "eyes_bluer.png", "eyes_bluer.png",
            "", "", "", "", "", "", "", "eyes_albino.png", "eyes_albino.png", "eyes_albino.png", "eyes_albino.png", "eyes_redl.png", "eyes_redl.png", "eyes_redl.png", "eyes_redr.png", "eyes_redr.png", "eyes_redr.png"
    };

    private static final String[] RABBIT_TEXTURES_SKIN = new String[] {
            "skin_pink.png", "skin_brown.png", "skin_white.png"
    };

    public static void calculateRabbitTextures(EnhancedRabbit rabbit, int[] genesForText, char[] uuidArry) {
        int under = 0;
        int lower = 0;
        int middle = 0;
        int higher = 0;
        int top = 0;
        int dutch = 0;
        int broken = 0;
        int vienna = 0;
        int eyes = 0;
        int vieye = 0;
        int skin = 0;

        int HIGH = 3;
        int TOPS = 4;
        int shade = 0; // [ 0 = black, 1 = blue, 2 = choc, 3 = lilac ]
        int c = 0; // [ 0 = normal or chinchilla, 1 = seal, 2 = sable, 3 = himilayan ]
        // i is a random modifier


        if (genesForText[4] == 5 && genesForText[5] == 5) {
            //Red Eyed White (albino)
            under = 2;
            eyes = 2;

        } else if(genesForText[14] == 2 && genesForText[15] == 2) {
            //Blue Eyed White
            under = 2;
            vieye = 8;

        } else {
            if (genesForText[2] == 2 && genesForText[3] == 2) {
                if (genesForText[6] == 2 && genesForText[7] == 2) {
                    //lilac
                    shade = 3;
                    eyes = 1;
                } else {
                    //chocolate
                    shade = 2;
                }
            } else {
                if (genesForText[6] == 2 && genesForText[7] == 2) {
                    //blue
                    shade = 1;
                    eyes = 1;
                }
            }

            if(genesForText[8] == 1 || genesForText[9] == 1){
                //steal
                if(genesForText[8] == 1 && genesForText[9] == 1){
                    top = 2 + ( TOPS * shade);
                }else {
                    top = 1 + (TOPS * shade);
                }
                if (genesForText[4] == 1 || genesForText[5] == 1){
                    middle = 1;
                }else{
                    middle = 7;
                }
            }else if (genesForText[8] != 2 && genesForText[9] != 2 && (genesForText[8] == 3 || genesForText[9] == 3)){
                //harlequin
                if (genesForText[10] == 1 && genesForText[11] == 1){
                    //stripy harlequin
                    top = 33 + ( 16 * shade);
                }else{
                    //spotty harlequin
                    top = 97 + ( 16 * shade);
                }
                //TODO add harlequin variations here
                if (genesForText[4] == 1 || genesForText[5] == 1){
                    middle = 1;
                }else{
                    middle = 7;
                }
            }else {

                if (genesForText[4] > 2 && genesForText[5] > 2){
                    if (genesForText[4] == 3 && genesForText[5] == 3){
                        //seal
                        c = 13;
                    }else if (genesForText[4] == 3 || genesForText[5] == 3){
                        //sable
                        c = 26;
                    }else{
                        //himilayan
                        c = 39;
                    }
                }

                if (genesForText[0] == 1 || genesForText[1] == 1) {
                    //agouti
                    if (genesForText[8] == 4 && genesForText[9] == 4) {
                        //orange extension wide band
                        higher = 0;
                        top = 0;
                        if (genesForText[4] == 1 || genesForText[5] == 1) {
                            //orange
                            middle = 1;
                        } else {
                            //white
                            middle = 7;
                        }
                    } else {
                        higher = 1;
                        if (genesForText[4] == 1 || genesForText[5] == 1) {
                            //orange
                            middle = 3;
                        } else {
                            //white
                            middle = 9;
                        }
                    }
                } else if (genesForText[0] == 2 || genesForText[1] == 2) {
                    //tan
                    if (genesForText[8] == 4 && genesForText[9] == 4) {
                        //orange extension wide band
                        top = 3 + (TOPS * shade);
                        if (genesForText[4] == 1 || genesForText[5] == 1) {
                            //orange
                            middle = 1;
                        } else {
                            //white
                            middle = 2;
                        }
                    } else {
                        higher = 2;
                        if (genesForText[4] == 1 || genesForText[5] == 1) {
                            //orange
                            middle = 2;
                        } else {
                            //white
                            middle = 8;
                        }
                    }
                } else {
                    //self
                    if (genesForText[8] == 4 && genesForText[9] == 4) {
                        //wide band orange rabbits
                        top = 4 + (TOPS * shade);
                        if (genesForText[4] == 1 || genesForText[5] == 1) {
                            //orange
                            middle = 1;
                        } else {
                            //white
                            middle = 2;
                        }
                    } else {
                        //self
                        higher = 3;
                        if (genesForText[4] == 1 || genesForText[5] == 1) {
                            //orange
                            middle = 1;
                        } else {
                            //white
                            middle = 7;
                        }
                    }
                }
            }

            if (higher != 0){
                //this sets the black to the correct black coverage and black shade
                higher = higher + (HIGH * shade) + c;
            }

            //vieye Eyes and Spots
            if (genesForText[14] == 2 || genesForText[15] == 2) {
                //Random variants of vienna marked
                //eyes
                if (genesForText[4] < 4 && genesForText[5] < 4) {
                    if (Character.isDigit(uuidArry[1])) {
                        vieye = 1 + (uuidArry[1] - 48);
                    } else {
                        char d = uuidArry[1];

                        switch (d) {
                            case 'a':
                                vieye = 11;
                                break;
                            case 'b':
                                vieye = 12;
                                break;
                            case 'c':
                                vieye = 13;
                                break;
                            case 'd':
                                vieye = 14;
                                break;
                            case 'e':
                                vieye = 15;
                                break;
                            case 'f':
                                vieye = 16;
                                break;
                            default:
                                vieye = 0;
                        }
                    }
                }

                //spots
                if ( Character.isDigit(uuidArry[2]) ){
                    if ( Character.isDigit(uuidArry[3]) ){
                        vienna = 1 + (uuidArry[3]-48);
                    }else{
                        char d = uuidArry[3];

                        switch (d) {
                            case 'a':
                                vienna = 11;
                                break;
                            case 'b':
                                vienna = 12;
                                break;
                            case 'c':
                                vienna = 13;
                                break;
                            case 'd':
                                vienna = 14;
                                break;
                            case 'e':
                                vienna = 15;
                                break;
                            case 'f':
                                vienna = 16;
                                break;
                            default:
                                vienna = 0;
                        }
                    }
                }
            }

            if (genesForText[4] >= 4 && genesForText[5] >= 4){
                eyes = 2;
                vieye = 0;
            }else if (genesForText[20] == 2 && genesForText[21] == 2){
                //lutino variations
                if (higher != 0){
                    higher = higher + 48;
                }

                if (top != 0){
                    if (top <= 16){
                        //sets standard variation layers to their lutino version
                        top = top + 16;
                    }else{
                        //sets random variation layers to their lutino version
                        top = top + 128;
                    }
                }

                if(genesForText[4] == 1 || genesForText[5] == 1){
                    if (genesForText[0] == 1 || genesForText[1] == 1){
                        lower = 2;
                    }else if (genesForText[0] == 2 || genesForText[1] == 2){
                        lower = 2;
                    }else{
                        lower = 3;
                    }
                }

                //lutino eye colour variations including vienna versions
                if (eyes == 1){
                    eyes = 2;
                }else{
                    eyes = 3;
                }

                if (vieye != 0) {
                    vieye = vieye + 16;
                }

            }

            if (genesForText[10] == 2 || genesForText[11] == 2) {
                //broken patterned
                if ( Character.isDigit(uuidArry[4]) ){
                    broken = 1 + (uuidArry[4]-48);
                } else {
                    char d = uuidArry[4];

                    switch (d) {
                        case 'a':
                            broken = 11;
                            break;
                        case 'b':
                            broken = 12;
                            break;
                        case 'c':
                            broken = 13;
                            break;
                        case 'd':
                            broken = 14;
                            break;
                        case 'e':
                            broken = 15;
                            break;
                        case 'f':
                            broken = 16;
                            break;
                        default:
                            broken = 0;
                    }
                }

                if (genesForText[10] == 2 && genesForText[11] == 2){
                    //charlie patterned
                    broken = broken +16;
                }

            }
            if (genesForText[12] == 2 && genesForText[13] == 2) {
                //dutch patterned
                //TODO add dutch textures with 16 variations
                if ( Character.isDigit(uuidArry[5]) ){
                    dutch = 1 + (uuidArry[5]-48);
                } else {
                    char d = uuidArry[5];

                    switch (d) {
                        case 'a':
                            dutch = 11;
                            break;
                        case 'b':
                            dutch = 12;
                            break;
                        case 'c':
                            dutch = 13;
                            break;
                        case 'd':
                            dutch = 14;
                            break;
                        case 'e':
                            dutch = 15;
                            break;
                        case 'f':
                            dutch = 16;
                            break;
                        default:
                            dutch = 0;
                    }
                }
            }

        }

        TextureGrouping parentGroup = new TextureGrouping(TexturingType.MERGE_GROUP);

        createUnderCoat(rabbit, parentGroup, under, lower);

        createMidCoat(rabbit, parentGroup, middle, higher);

        createTopCoat(rabbit, parentGroup, top);

        addSpotting(rabbit, parentGroup, dutch, broken);

        addVienna(rabbit, parentGroup, vienna);

        addFurTexture(rabbit, parentGroup, genesForText);

        addEyeTexture(rabbit, parentGroup, eyes, vieye);

        addSkinDetails(rabbit, parentGroup, skin);

        rabbit.setTextureGrouping(parentGroup);
    }

    private static void addSkinDetails(EnhancedRabbit rabbit, TextureGrouping parentGroup, int skin) {
        TextureGrouping detailGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        rabbit.addTextureToAnimalTextureGrouping(detailGroup, RABBIT_TEXTURES_EYES[skin], String.valueOf(skin));
        parentGroup.addGrouping(detailGroup);
    }

    private static void addEyeTexture(EnhancedRabbit rabbit, TextureGrouping parentGroup, int eyes, int vieye) {
        TextureGrouping eyeGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        rabbit.addTextureToAnimalTextureGrouping(eyeGroup, RABBIT_TEXTURES_EYES[eyes], String.valueOf(eyes));
        if (vieye > 7 && (vieye <= 17 || vieye >= 25)) {
            rabbit.addTextureToAnimalTextureGrouping(eyeGroup, RABBIT_TEXTURES_VIENNAEYES[vieye], String.valueOf(vieye));
        } else {
            rabbit.addDelimiter("nv");
        }

        parentGroup.addGrouping(eyeGroup);
    }

    private static void addFurTexture(EnhancedRabbit rabbit, TextureGrouping parentGroup, int[] gene) {
        int fur = 0;
        if (gene[26] == 2 && gene[27] == 2) {
            //angora
            if (gene[50] == 1 && gene[51] == 1 || gene[50] == 3 && gene[51] == 3) {
                fur = 3;
            } else if ( gene[50] == 1 || gene[51] == 1 || gene[50] == 3 || gene[51] == 3) {
                fur = 4;
            } else {
                fur = 5;
            }

            if ( gene[52] >= 2 && gene[53] >= 2) {
                fur = fur + 1;
                if ( gene[52] == 3 && gene[53] == 3 && fur <= 5) {
                    fur = fur + 1;
                }
            }

            if ( gene[54] == 1 || gene[55] == 1 && fur >= 4) {
                fur = fur - 1;
                if ( gene[54] == 1 && gene[55] == 1 && fur >= 4) {
                    fur = fur - 1;
                }
            }
        } else if (gene[28] == 1 || gene[29] == 1) {
            if (gene[30] == 2 && gene[31] == 2) {
                //satin
                fur = 2;
            } else {
                //normal
                fur = 1;
            }
        }

        if (fur != 0) {
            TextureGrouping coat = new TextureGrouping(TexturingType.MERGE_GROUP);
            rabbit.addTextureToAnimalTextureGrouping(coat, RABBIT_TEXTURES_FUR[fur], String.valueOf(fur));
            parentGroup.addGrouping(coat);
        } else {
            rabbit.addDelimiter("rex");
        }
    }

    private static void addVienna(EnhancedRabbit rabbit, TextureGrouping parentGroup, int vienna) {
        if (vienna != 0) {
            TextureGrouping coat = new TextureGrouping(TexturingType.MERGE_GROUP);
            rabbit.addTextureToAnimalTextureGrouping(coat, RABBIT_TEXTURES_DUTCH[vienna], String.valueOf(vienna));
            parentGroup.addGrouping(coat);
        } else {
            rabbit.addDelimiter();
        }
    }

    private static void addSpotting(EnhancedRabbit rabbit, TextureGrouping parentGroup, int dutch, int broken) {
        //TODO Hotot Spotting if dutch and homozygous broken

        if (dutch != 0) {
            TextureGrouping coat = new TextureGrouping(TexturingType.MERGE_GROUP);
            rabbit.addTextureToAnimalTextureGrouping(coat, RABBIT_TEXTURES_DUTCH[dutch], String.valueOf(dutch));
            parentGroup.addGrouping(coat);
        } else {
            rabbit.addDelimiter();
        }
        if (broken != 0) {
            //TODO split out broken spots from charlie spots
            //TODO make broken/charlie genetics
            TextureGrouping coat = new TextureGrouping(TexturingType.MERGE_GROUP);
            rabbit.addTextureToAnimalTextureGrouping(coat, RABBIT_TEXTURES_BROKEN[broken], String.valueOf(broken));
            parentGroup.addGrouping(coat);
        } else {
            rabbit.addDelimiter();
        }
    }

    private static void createTopCoat(EnhancedRabbit rabbit, TextureGrouping parentGroup, int top) {
        if (top != 0) {
            TextureGrouping coat = new TextureGrouping(TexturingType.MERGE_GROUP);
            rabbit.addTextureToAnimalTextureGrouping(coat, RABBIT_TEXTURES_TOP[top], String.valueOf(top));
            parentGroup.addGrouping(coat);
        } else {
            rabbit.addDelimiter();
        }
    }

    private static void createMidCoat(EnhancedRabbit rabbit, TextureGrouping parentGroup, int middle, int higher) {
        TextureGrouping coat = new TextureGrouping(TexturingType.MERGE_GROUP);

        if (middle != 0) {
            rabbit.addTextureToAnimalTextureGrouping(coat, RABBIT_TEXTURES_MIDDLE[middle], String.valueOf(middle));
        } else {
            rabbit.addDelimiter();
        }

        if (higher != 0) {
            rabbit.addTextureToAnimalTextureGrouping(coat, RABBIT_TEXTURES_HIGHER[higher], String.valueOf(higher));
        } else {
            rabbit.addDelimiter();
        }

        parentGroup.addGrouping(coat);
    }

    private static void createUnderCoat(EnhancedRabbit rabbit, TextureGrouping parentGroup, int under, int lower) {
        TextureGrouping coat = new TextureGrouping(TexturingType.MERGE_GROUP);
        rabbit.addTextureToAnimalTextureGrouping(coat, RABBIT_TEXTURES_UNDER[under], String.valueOf(under));
        if (lower != 0) {
            rabbit.addTextureToAnimalTextureGrouping(coat, RABBIT_TEXTURES_LOWER[lower], String.valueOf(lower));
        } else {
            rabbit.addDelimiter();
        }

        parentGroup.addGrouping(coat);
    }
}
