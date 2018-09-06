package mokiyoki.enhancedanimals.entity;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.Int;

import javax.annotation.Nullable;
import java.util.*;

/**
 * Created by saemon and moki on 30/08/2018.
 */
public class EnhancedChicken extends EntityAnimal {
    // [4] duckwing, partridge, wheaten, solid
    // [5] silver, salmon, lemon, gold, mahogany
    private static final String[] CHICKEN_TEXTURES_GROUND = new String[] {
        "textures/entities/chicken/ground_duckwing_silver",   "textures/entities/chicken/ground_duckwing_salmon",   "textures/entities/chicken/ground_duckwing_lemon",
        "textures/entities/chicken/ground_duckwing_gold",     "textures/entities/chicken/ground_duckwing_mahogany", "textures/entities/chicken/ground_solid_silver",
        "textures/entities/chicken/ground_solid_silver",      "textures/entities/chicken/ground_partridge_lemon",   "textures/entities/chicken/ground_partridge_gold",
        "textures/entities/chicken/ground_partridge_mahogany","textures/entities/chicken/ground_wheaten_silver",    "textures/entities/chicken/ground_wheaten_salmon",
        "textures/entities/chicken/ground_wheaten_lemon",     "textures/entities/chicken/ground_wheaten_gold",      "textures/entities/chicken/ground_wheaten_mahogany",
        "textures/entities/chicken/ground_solid_silver",      "textures/entities/chicken/ground_solid_silver",      "textures/entities/chicken/ground_solid_cream",
        "textures/entities/chicken/ground_solid_buff",        "textures/entities/chicken/ground_solid_mahogany"
    };
    private static final String[] CHICKEN_TEXTURES_PATTERN = new String[] {                                             // [7]  black,blue,splash,lavender,white,dun,chocolate
        "","textures/entities/chicken/pattern_solid.png","textures/entities/chicken/pattern_solid_blue.png",            // [17] none,solid,birchen,duckwing,wheaten,quail,columbian,darkbrown,lakenvelder,moorhead,blacktail,penciled,bsinglelaced,singlelaced,doublelaced,spangled,partridge-penciled
        "textures/entities/chicken/pattern_solid_splash.png", "textures/entities/chicken/pattern_solid_lav.png","textures/entities/chicken/pattern_solid_white.png",
        "textures/entities/chicken/pattern_solid_dun.png", "textures/entities/chicken/pattern_solid_choc.png","textures/entities/chicken/pattern_birchen.png",
        "textures/entities/chicken/pattern_birchen_blue.png", "textures/entities/chicken/pattern_birchen_splash.png","textures/entities/chicken/pattern_birchen_lav.png",
        "textures/entities/chicken/pattern_birchen_white.png", "textures/entities/chicken/pattern_birchen_dun.png", "textures/entities/chicken/pattern_birchen_choc.png",
        "textures/entities/chicken/pattern_duckwing.png","textures/entities/chicken/pattern_duckwing_blue.png", "textures/entities/chicken/pattern_duckwing_splash.png",
        "textures/entities/chicken/pattern_duckwing_lav.png","textures/entities/chicken/pattern_duckwing_white.png", "textures/entities/chicken/pattern_duckwing_dun.png",
        "textures/entities/chicken/pattern_duckwing_choc.png","textures/entities/chicken/pattern_wheaten.png","textures/entities/chicken/pattern_wheaten_blue.png",
        "textures/entities/chicken/pattern_wheaten_splash.png","textures/entities/chicken/pattern_wheaten_lav.png","textures/entities/chicken/pattern_wheaten_white.png",
        "textures/entities/chicken/pattern_wheaten_dun.png", "textures/entities/chicken/pattern_wheaten_choc.png","textures/entities/chicken/pattern_quail.png",
        "textures/entities/chicken/pattern_quail_blue.png","textures/entities/chicken/pattern_quail_splash.png","textures/entities/chicken/pattern_quail_lav.png",
        "textures/entities/chicken/pattern_quail_white.png","textures/entities/chicken/pattern_quail_dun.png", "textures/entities/chicken/pattern_quail_choc.png",
        "textures/entities/chicken/pattern_columbian.png","textures/entities/chicken/pattern_columbian_blue.png", "textures/entities/chicken/pattern_columbian_splash.png",
        "textures/entities/chicken/pattern_columbian_lav.png","textures/entities/chicken/pattern_columbian_white.png", "textures/entities/chicken/pattern_columbian_dun.png",
        "textures/entities/chicken/pattern_columbian_choc.png","textures/entities/chicken/pattern_darkbrown.png","textures/entities/chicken/pattern_darkbrown_blue.png",
        "textures/entities/chicken/pattern_darkbrown_splash.png","textures/entities/chicken/pattern_darkbrown_lav.png","textures/entities/chicken/pattern_darkbrown_white.png",
        "textures/entities/chicken/pattern_darkbrown_dun.png", "textures/entities/chicken/pattern_darkbrown_choc.png","textures/entities/chicken/pattern_lakenvelder.png",
        "textures/entities/chicken/pattern_lakenvelder_blue.png", "textures/entities/chicken/pattern_lakenvelder_splash.png","textures/entities/chicken/pattern_lakenvelder_lav.png",
        "textures/entities/chicken/pattern_lakenvelder_white.png", "textures/entities/chicken/pattern_lakenvelder_dun.png","textures/entities/chicken/pattern_lakenvelder_choc.png",
        "textures/entities/chicken/pattern_moorhead.png","textures/entities/chicken/pattern_moorhead_blue.png", "textures/entities/chicken/pattern_moorhead_splash.png",
        "textures/entities/chicken/pattern_moorhead_lav.png","textures/entities/chicken/pattern_moorhead_white.png", "textures/entities/chicken/pattern_moorhead_dun.png",
        "textures/entities/chicken/pattern_moorhead_choc.png","textures/entities/chicken/pattern_blacktail.png","textures/entities/chicken/pattern_blacktail_blue.png",
        "textures/entities/chicken/pattern_blacktail_splash.png","textures/entities/chicken/pattern_blacktail_lav.png","textures/entities/chicken/pattern_blacktail_white.png",
        "textures/entities/chicken/pattern_blacktail_dun.png", "textures/entities/chicken/pattern_blacktail_choc.png","textures/entities/chicken/pattern_penciled.png",
        "textures/entities/chicken/pattern_penciled_blue.png", "textures/entities/chicken/pattern_penciled_splash.png","textures/entities/chicken/pattern_penciled_lav.png",
        "textures/entities/chicken/pattern_penciled_white.png","textures/entities/chicken/pattern_penciled_dun.png", "textures/entities/chicken/pattern_penciled_choc.png",
        "textures/entities/chicken/pattern_bsinglelaced.png","textures/entities/chicken/pattern_bsinglelaced_blue.png","textures/entities/chicken/pattern_bsinglelaced_splash.png",
        "textures/entities/chicken/pattern_bsinglelaced_lav.png","textures/entities/chicken/pattern_bsinglelaced_white.png", "textures/entities/chicken/pattern_bsinglelaced_dun.png",
        "textures/entities/chicken/pattern_bsinglelaced_choc.png","textures/entities/chicken/pattern_singlelaced.png","textures/entities/chicken/pattern_singlelaced_blue.png",
        "textures/entities/chicken/pattern_singlelaced_splash.png","textures/entities/chicken/pattern_singlelaced_lav.png","textures/entities/chicken/pattern_singlelaced_white.png",
        "textures/entities/chicken/pattern_singlelaced_dun.png", "textures/entities/chicken/pattern_singlelaced_choc.png","textures/entities/chicken/pattern_doublelaced.png",
        "textures/entities/chicken/pattern_doublelaced_blue.png", "textures/entities/chicken/pattern_doublelaced_splash.png","textures/entities/chicken/pattern_doublelaced_lav.png",
        "textures/entities/chicken/pattern_doublelaced_white.png","textures/entities/chicken/pattern_doublelaced_dun.png", "textures/entities/chicken/pattern_doublelaced_choc.png",
        "textures/entities/chicken/pattern_spangled.png","textures/entities/chicken/pattern_spangled_blue.png","textures/entities/chicken/pattern_spangled_splash.png",
        "textures/entities/chicken/pattern_spangled_lav.png","textures/entities/chicken/pattern_spangled_white.png", "textures/entities/chicken/pattern_spangled_dun.png",
        "textures/entities/chicken/pattern_spangled_choc.png","textures/entities/chicken/pattern_prdgpenciled.png", "textures/entities/chicken/pattern_prdgpenciled_blue.png",
        "textures/entities/chicken/pattern_prdgpenciled_splash.png","textures/entities/chicken/pattern_prdgpenciled_lav.png", "textures/entities/chicken/pattern_prdgpenciled_white.png",
        "textures/entities/chicken/pattern_prdgpenciled_dun.png", "textures/entities/chicken/pattern_prdgpenciled_choc.png",
    };
    private static final String[] CHICKEN_TEXTURES_WHITE = new String[] {
        "","textures/entities/chicken/white_barred.png","textures/entities/chicken/white_mottles.png","textures/entities/chicken/white_crested.png"
    };
    private static final String[] CHICKEN_TEXTURES_SHANKS = new String[] {
        "textures/entities/chicken/shanks_horn.png","textures/entities/chicken/shanks_yellow.png","textures/entities/chicken/shanks_willow.png","textures/entities/chicken/shanks_black.png",
        "textures/entities/chicken/shanks_verywhite.png","textures/entities/chicken/shanks_white.png", "textures/entities/chicken/shanks_slate.png", "textures/entities/chicken/shanks_black.png",
    };
    private static final String[] CHICKEN_TEXTURES_COMB = new String[] {
        "textures/entities/chicken/comb_black.png","textures/entities/chicken/comb_mulberry.png","textures/entities/chicken/comb_red.png"
    };
    private static final String[] CHICKEN_TEXTURES_EYES = new String[] {
        "textures/entities/chicken/eyes_albino.png","textures/entities/chicken/eyes_black.png"
    };

    private static final int WTC = 90;
    private int broodingCount;
    private final List<String> chickenTextures = new ArrayList<>();
    //'father' gene variables list
    private int[] genes = new int[70];
    private int[] mateGenes = new int[70];
    private int[] mitosisGenes = new int[70];
    private int[] mateMitosisGenes = new int[70];

    public EnhancedChicken(World worldIn) {
        super(worldIn);
        this.setSize(0.4F, 0.7F); //I think its the height and width of a chicken
        this.setPathPriority(PathNodeType.WATER, 0.0F); //TODO investigate what this do and how/if needed

        //TODO set up here, any defaults of variables you create that you need. ie: All initial genes

    }

    @Nullable
    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
        //create egg item
        //set parent genes for both. I think its this. for mum, ageable for dad.
        //spawn egg in overworld


        return null;
    }

    @SideOnly(Side.CLIENT)
    public String[] getVariantTexturePaths()
    {
        if (this.chickenTextures.isEmpty()) {
            this.setTexturePaths();
        }

        return this.chickenTextures.stream().toArray(String[]::new);
    }

    private void setTexturePaths() {
        int ground = 0;
        int pattern = 0;
        int white = 0;
        int shanks = 2;
        int comb = 0;
        int eyes = 1;

        int Columbian= 3;
        int Dlocus= 0;
        int Melanin= 0;
        int PatternGene= 1;

        boolean isAlbino = false;

        if (genes[20] != 1 && genes[21] != 1) {                                                                       //checks if not wildtype
            if (genes[20] == 2 || genes[21] == 2) {                                                                   //sets recessive white or albino
                ground = 17;
            } else {
                ground = 17;
                pattern = 0;
                white = 0;
                comb = 3;
                eyes = 0;
                isAlbino = true;
            }
        } else {

            ///checks dlocus genes for only dominant gene returns [ 1 2 3 4 ]
            for (int a = 1; genes[24] == a || genes[25] == a || 4 < a; a++) {
                Dlocus = Dlocus++;
            }
            /// figures out if Columbian gene is wildtype and if not what gene is dominant returns [ 1 2 3 ]
            if (genes[28] != 3 && genes[29] != 3) {
                if ((genes[28] == 1 && genes[29] == 1) || genes[2] == 1 && (genes[28] == 1 || genes[29] == 1)) {
                    Columbian = 1;
                } else {
                    Columbian = 2;
                }
            }
            ///checks melanin genes for combination of genes returns [ 1 2 3 ]
            for (int b = 1; genes[30] == b || genes[31] == b || 4 < b; b++) {
                Melanin = Melanin++;
            }
            ///figures out if patterned or not returns [ 1 2 ]
            if (genes[26] == 2 && genes[27] == 2) {
                PatternGene = 2;
            }

            //figures out which pattern and ground colour it needs

            //black based
            if (Dlocus == 1) {
                if (Melanin == 1) {
                    if (Columbian == 1 || Columbian == 3) {
                        pattern = 1;
                    } else {
                        if (PatternGene == 1) {
                            //quail
                            pattern = 29;
                            ground = 5;
                        } else {
                            //penciled
                            pattern = 71;
                            ground = 5;
                        }
                    }
                } else if (Melanin == 2) {
                    if (Columbian == 1) {
                        if (PatternGene == 1) {
                            //quail
                            pattern = 29;
                            ground = 5;
                        } else {
                            //spangled
                            pattern = 99;
                            ground = 15;
                        }
                    } else if (Columbian == 3) {
                        //solid
                        pattern = 1;
                        ground = 15;
                    } else {
                        if (PatternGene == 1) {
                            //quail
                            pattern = 29;
                            ground = 5;
                        } else {
                            //birchen single laced
                            pattern = 78;
                            ground = 15;
                        }
                    }
                } else {
                    if (Columbian == 1) {
                        if (PatternGene == 1) {
                            //quail
                            pattern = 29;
                            ground = 5;
                        } else {
                            //penciled
                            pattern = 71;
                            ground = 5;
                        }
                    } else if (Columbian == 3) {
                        //solid
                        pattern = 8;
                        ground = 0;
                    } else {
                        if (PatternGene == 1) {
                            //columbian
                            pattern = 36;
                            ground = 15;
                        } else {
                            //penciled
                            pattern = 78;
                            ground = 15;
                        }
                    }
                }
            }
            //duckwing based
            else if (Dlocus == 2){
                if (Columbian == 3){
                    if (Melanin == 3) {
                        if (PatternGene == 1) {
                            //wildtype duckwing
                            pattern = 15;
                            ground = 0;
                        } else {
                            //multiple laced duckwing
                            pattern = 106;
                            ground = 0;
                        }
                    } else if (Melanin == 1) {
                        if (PatternGene == 1) {
                            //quail patterned duckwing
                            pattern = 29;
                            ground = 0;
                        } else if(genes[30] == 2){
                            //double laced duckwing
                            pattern = 92;
                            ground = 0;
                        }else{
                            //multiple laced duckwing
                            pattern = 106;
                            ground = 0;
                        }
                    }else{
                        if (PatternGene == 1){
                            // quail duckwing
                            pattern = 29;
                            ground = 0;
                        }else{
                            pattern = 92;
                            ground = 0;
                        }
                    }
                }else if (Columbian == 1){
                    if (Melanin == 3){
                            //Columbian
                            pattern = 36;
                            ground = 15;
                    }else{
                        if(PatternGene == 1){
                            //Quail
                            pattern = 29;
                            ground = 5;
                        }else{
                            //single lace
                            pattern = 85;
                            ground = 15;
                        }
                    }
                }else{
                    if (Melanin == 3){
                            //Columbian
                            pattern = 36;
                            ground = 15;
                    }else{
                        if(PatternGene == 1){
                            //Quail solid
                            pattern = 29;
                            ground = 15;
                        }else{
                            //spangled
                            pattern = 99;
                            ground = 15;
                        }
                    }
                }
            }
            //wheaten based
            else if (Dlocus == 3) {
                if (PatternGene == 1) {
                    if (Columbian == 3) {
                        if (Melanin == 3) {
                            //regular wheaten
                            pattern = 22;
                            ground = 10;
                        } else {
                            //funace wheaten
                            pattern = 15;
                            ground = 10;
                        }
                    } else if (Columbian == 1) {
                        if (Melanin == 3) {
                            //Colombian
                            pattern = 36;
                            ground = 15;
                        } else {
                            //wheaten quail
                            pattern = 29;
                            ground = 10;
                        }
                    } else {
                        if (Melanin == 3) {
                            if (genes[24] == 4 || genes[25] == 4) {
                                // incomplete buff
                                pattern = 22;
                                ground = 15;
                            } else {
                                //Buff
                                pattern = 0;
                                ground = 15;
                            }
                        } else {
                            //incomplete columbian
                            pattern = 43;
                            ground = 15;
                        }
                    }
                    } else {
                    if (Columbian == 3) {
                        if (Melanin == 3) {
                            //regular wheaten
                            pattern = 22;
                            ground = 10;
                        } else if (Melanin == 2) {
                            //regular double laced
                            pattern = 92;
                            ground = 15;
                        } else {
                            //double laced wheaten
                            pattern = 92;
                            ground = 10;
                        }
                    } else if (Columbian == 1) {
                        if (Melanin == 3) {
                            //Colombian
                            pattern = 36;
                            ground = 15;
                        } else {
                            //single laced
                            pattern = 85;
                            ground = 15;
                        }
                    } else {
                        if (Melanin == 3) {
                            if (genes[24] == 4 || genes[25] == 4) {
                                // incomplete buff
                                pattern = 22;
                                ground = 15;
                            } else {
                                //buff
                                pattern = 0;
                                ground = 15;
                            }
                        }else{
                            //spangled
                            pattern = 99;
                            ground = 15;
                        }
                    }
                }
                    }
            //partridge based
            else{
                if (PatternGene == 1){
                    if (Columbian == 3){
                        if (Melanin == 3){
                            //regular partridge
                            pattern = 15;
                            ground = 5;
                        }else{
                            //dark or partial patterned partridge???
                            pattern = 15;
                            ground = 5;
                        }
                    }else if (Columbian == 1){
                        if (Melanin == 3){
                            //Columbian
                            pattern = 43;
                            ground = 15;
                        }else if (Melanin == 1){
                            //Quail
                            pattern = 29;
                            ground = 5;
                        }else{
                            //lakenvelder
                            pattern = 50;
                            ground = 15;
                        }
                    }else{
                        if (Melanin == 3){
                            //blacktail
                            pattern = 43;
                            ground = 15;
                        }else if(Melanin == 1){
                            //incomplete quail
                            pattern = 15;
                            ground = 15;
                        }else{
                            //Moorhead
                            pattern = 50;
                            ground = 15;
                        }
                    }
                }else{
                    if (Columbian == 3){
                        if (Melanin == 3){
                            // partridge penciled
                            pattern = 106;
                            ground = 5;
                        }else{
                            // double laced
                            pattern = 92;
                            ground = 5;
                        }
                    }else if (Columbian == 1){
                        if (Melanin == 3){
                            // penciled
                            pattern = 71;
                            ground = 15;
                        }else{
                            // spangled
                            pattern = 99;
                            ground = 15;
                        }
                    }else{
                        if (Melanin == 3){
                            //darkbrown
                            pattern = 36;
                            ground = 15;
                        }else if (Melanin == 1){
                            //Moorhead
                            pattern = 57;
                            ground = 15;
                        }else{
                            // half moon spangled
                            pattern = 99;
                            ground = 15;
                        }
                    }
                }
            }


            //ground colour tint
            if (genes[0] == 1){
                //gold
                ground = ground +2;
            }
            if (genes[0] == 1 && ((genes[32] != 3 && genes[33] != 3) || (genes[36] == 2 && genes[37] ==2))){
                //lemon or cream but backwards
                ground = ground +1;
            }
            if (genes[34] == 1 || genes[35] == 1){
                //mahogany or lemon cream counter
                ground = ground +1;
            }

            //black pattern shade genes
            if(genes[38] == 1 && genes[39] == 1){
                //domwhite
                pattern = pattern +4;
            }else if(genes[38] == 1 && genes[39] == 1){
                // spotted domwhite
                pattern = pattern +4;
            }else {
                if (genes[36] == 2 && genes[37] == 2) {
                    if (genes[1] == 1) {
                        if (genes[40] == 2 && genes[41] == 2){
                            // lavender + splash = white
                            pattern = pattern +4;
                        }else{
                            // lavender
                            pattern = pattern +3;
                        }
                    }else {
                        // lavender + choc = dun
                        pattern = pattern +5;
                    }
                }else{
                    if (genes[1] == 1){
                        if (genes[40] == 2 && genes[41] == 2) {
                            //splash
                            pattern = pattern +2;
                        }else if(genes[40] == 2 || genes[41] == 2) {
                            //blue
                            pattern = pattern +1;
                        }
                    }else{
                        if (genes[40] == 2 && genes[41] == 2) {
                            //splash + choc = dun splash
                            pattern = pattern +2;
                        }else if (genes[40] == 2 || genes[41] == 2) {
                            //blue + choc = dun
                            pattern = pattern +5;
                        }else{
                            //chocolate
                            pattern = pattern +6;
                        }
                    }
                }
            }

        }

        //white marking genes
        if (genes[3] == 2){
            //Barred
            white = 1;
        }else{
            if (genes[22] == 2 && genes[23] == 2){
                    //mottled
                    white = 2;
                }else{
                if(pattern > 8 && Melanin != 2 && (genes[54] != 3 && genes[55] != 3) && genes[6] == 2){
                    //white crest
                    white = 3;
                }
            }
        }

        // figures out the shank, comb, and skin colour if its not albino
        if(!isAlbino) {
            //gets comb colour
            if (genes[4] == 1 && (genes[42] == 1 || genes[43] == 1)){
                //comb and shanks are black
                comb = 0;
                shanks = 4;
            }
            if (genes[24] == 1 && genes[25] == 1){
                shanks =4;
                // makes mulbery comb
                if (genes[30] == 2){
                    comb = 1;
                }
            }
            //shanks starts at 3 btw
            // if Dilute is Dilute and the shanks arnt darkened by extened black lighten by 1 shade
            if ((genes[24] != 1 && genes[25] != 1) && (genes[32] == 1 || genes[33] == 1)){
                shanks--;
            }

            // if dominant white or lavender lighten by 1 shade
            if ((genes[38] == 1 && genes[39] == 1) || (genes[36] == 1 && genes[37] == 1)){
                shanks--;
            }

            // if splash or blue lighten by 1 shade
            if (genes[40] == 2 || genes[41] == 2){
                shanks--;
            }

            //if barring lighten by 1 shade
            if (genes[3] == 2){
                shanks--;
            }

            //if its melanized
            if (Melanin == 2){
                shanks++;
            }

            // if columbian toggle doesnt matter darken by 1
            if ((genes[2] == 1 && genes[28] == 2 && genes[29] == 2) || (genes[2] == 2 && genes[28] == 1 && genes[29] == 1)){
                shanks++;
            }

            //makes sure its not off the chart
            if (shanks < 0 ){
                shanks = 0;
            }else if (shanks > 4 ){
                shanks = 4;
            }

            //makes the shanks and beak their white or yellow varient
            if (genes[44] == 1 || genes[45] == 1 ){
                shanks = shanks + 4;
            }

        }






        //after finished genes
        this.chickenTextures.add(CHICKEN_TEXTURES_GROUND[ground]);
        this.chickenTextures.add(CHICKEN_TEXTURES_PATTERN[pattern]);
        this.chickenTextures.add(CHICKEN_TEXTURES_WHITE[white]);
        this.chickenTextures.add(CHICKEN_TEXTURES_SHANKS[shanks]);
        this.chickenTextures.add(CHICKEN_TEXTURES_COMB[comb]);
        this.chickenTextures.add(CHICKEN_TEXTURES_EYES[eyes]);


    }




    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);

        //store this chicken's genes
        NBTTagList geneList = new NBTTagList();
        for(int i = 0; i< genes.length; i++){
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setInteger("Gene", genes[i]);
            geneList.appendTag(nbttagcompound);
        }
        compound.setTag("Genes", geneList);

        //store this chicken's mate's genes
        NBTTagList mateGeneList = new NBTTagList();
        for(int i = 0; i< mateGenes.length; i++){
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setInteger("Gene", mateGenes[i]);
            mateGeneList.appendTag(nbttagcompound);
        }
        compound.setTag("FatherGenes", mateGeneList);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);

        NBTTagList geneList = compound.getTagList("Genes", 10);
        for (int i = 0; i < geneList.tagCount(); ++i) {
            NBTTagCompound nbttagcompound = geneList.getCompoundTagAt(i);
            int gene = nbttagcompound.getInteger("Gene");
            genes[i] = gene;
        }

        NBTTagList mateGeneList = compound.getTagList("FatherGenes", 10);
        for (int i = 0; i < mateGeneList.tagCount(); ++i) {
            NBTTagCompound nbttagcompound = mateGeneList.getCompoundTagAt(i);
            int gene = nbttagcompound.getInteger("Gene");
            mateGenes[i] = gene;
        }

    }

    public void mixMitosisGenes() {
        punnetSquare(mitosisGenes, genes);
    }

    public void mixMateMitosisGenes(){
        punnetSquare(mateMitosisGenes, mateGenes);
    }

    public void punnetSquare(int[] mitosis, int[] parentGenes) {
        Random rand = new Random();
        for (int i = 20; i < genes.length; i = (i+2) ) {
            boolean mateOddOrEven = rand.nextBoolean();
            if (mateOddOrEven) {
                mitosis[i] = parentGenes[i + 1];
                mitosis[i + 1] = parentGenes[i];
            } else {
                mitosis[i] = parentGenes[i];
                mitosis[i + 1] = parentGenes[i + 1];
            }
        }
    }


    public int[] getEggGenes() {
        Random rand = new Random();
        int[] eggGenes = new int[70];

        for(int i =0; i< 20; i++) {
            boolean thisOrMate = rand.nextBoolean();
            if (thisOrMate){
                eggGenes[i] = genes[i];
            } else {
                eggGenes[i] = mateGenes[i];
            }
        }

        for(int i =20; i< genes.length; i++) {
            boolean thisOrMate = rand.nextBoolean();
            if (thisOrMate){
                eggGenes[i] = mitosisGenes[i];
            } else {
                eggGenes[i] = mateMitosisGenes[i];
            }
        }

        return eggGenes;
    }

    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        int[] spawnGenes;

        if (livingdata instanceof EnhancedChicken.GroupData) {
            spawnGenes = ((GroupData)livingdata).groupGenes;
        } else {
            spawnGenes = createInitialGenes();
            livingdata = new EnhancedChicken.GroupData(spawnGenes);
        }

        this.genes = spawnGenes;
        return livingdata;
    }

    private int[] createInitialGenes() {
        int[] initialGenes = new int[70];
        Random ran = new Random();

        //TODO create biome WTC variable [hot and dry biomes, hot and wet biomes, cold biomes] WTC is all others

/**
 * parent linked genes
 */
        //Gold [ gold, silver ]
        if(ran.nextInt(100)>WTC){
            initialGenes[0] = (ran.nextInt(2)+1);

        } else {
            initialGenes[0] = (1);
        }

        //Chocolate [ wildtype, chocolate ]
        if(ran.nextInt(100)>WTC){
            initialGenes[1] = (ran.nextInt(2)+1);

        } else {
            initialGenes[1] = (1);
        }

        //Columbian toggle [ dominant columbian, dominant darkbrown ]
        if(ran.nextInt(100)>WTC){
            initialGenes[2] = (ran.nextInt(2)+1);

        } else {
            initialGenes[2] = (1);
        }

        //Barred [ wildtype, barred ]
        if(ran.nextInt(100)>WTC){
            initialGenes[3] = (ran.nextInt(2)+1);

        } else {
            initialGenes[3] = (1);
        }

        //Fibromelanin Suppressor [ wildtype, suppressor ]
        if(ran.nextInt(100)>WTC){
            initialGenes[4] = (ran.nextInt(2)+1);

        } else {
            initialGenes[4] = (1);
        }

        //Brown egg gene suppressor [ wildtype, suppressor ]
        if(ran.nextInt(100)>WTC){
            initialGenes[5] = (ran.nextInt(2)+1);

        } else {
            initialGenes[5] = (1);
        }

        //white head
        if(ran.nextInt(100)>WTC){
            initialGenes[6] = (ran.nextInt(2)+1);

        } else {
            initialGenes[6] = (1);
        }

/**
* unused parent linked genes
*/

        for (int i = 7; i < 20; i = i++ ) {
            initialGenes[i] = (1);
        }

/**
* normal genes start with 20
*/

        //Recessive white [ wild, recessive white, albino ]
        if(ran.nextInt(100)>WTC){
            initialGenes[20] = (ran.nextInt(3)+1);

        } else {
            initialGenes[20] = (1);
        }
            if(ran.nextInt(100)>WTC){
            initialGenes[21] = (ran.nextInt(3)+1);

            } else {
            initialGenes[21] = (1);
        }


        //Mottled [ wildtype, mottled ]
        if(ran.nextInt(100)>WTC){
            initialGenes[22] = (ran.nextInt(2)+1);

        } else {
            initialGenes[22] = (1);
        }
        if(ran.nextInt(100)>WTC){
            initialGenes[23] = (ran.nextInt(2)+1);

        } else {
            initialGenes[23] = (1);
        }

        //Dlocus [ black, duckwing, wheaten, partridge ]
        if(ran.nextInt(100)>WTC){
            initialGenes[24] = (ran.nextInt(4)+1);

        } else {
            initialGenes[24] = (2);
        }
            if(ran.nextInt(100)>WTC){
            initialGenes[25] = (ran.nextInt(4)+1);

             } else {
            initialGenes[25] = (2);
        }

        //Pattern Gene [ wildtype, pattern ]
        if(ran.nextInt(100)>WTC){
            initialGenes[26] = (ran.nextInt(2)+1);

        } else {
            initialGenes[26] = (1);
        }
            if(ran.nextInt(100)>WTC){
            initialGenes[27] = (ran.nextInt(2)+1);

            } else {
            initialGenes[27] = (1);
        }

        //Colombian [ colombian, darkbrown, wildtype ]
        if(ran.nextInt(100)>WTC){
            initialGenes[28] = (ran.nextInt(3)+1);

        } else {
            initialGenes[28] = (3);
        }
        if(ran.nextInt(100)>WTC){
            initialGenes[29] = (ran.nextInt(3)+1);

        } else {
            initialGenes[29] = (3);
        }

        //Melanized [ dominant melanized, recessive melanized, wildtype ]
        if(ran.nextInt(100)>WTC){
            initialGenes[30] = (ran.nextInt(3)+1);

        } else {
            initialGenes[30] = (3);
        }
        if(ran.nextInt(100)>WTC){
            initialGenes[31] = (ran.nextInt(3)+1);

        } else {
            initialGenes[31] = (3);
        }

        //Dilute [ dilute, cream, wildtype ]
        if(ran.nextInt(100)>WTC){
            initialGenes[32] = (ran.nextInt(3)+1);

        } else {
            initialGenes[32] = (3);
        }
        if(ran.nextInt(100)>WTC){
            initialGenes[33] = (ran.nextInt(3)+1);

        } else {
            initialGenes[33] = (3);
        }

        //Mahogany [ mahogany, wildtype ]
        if(ran.nextInt(100)>WTC){
            initialGenes[34] = (ran.nextInt(2)+1);

        } else {
            initialGenes[34] = (2);
        }
        if(ran.nextInt(100)>WTC){
            initialGenes[35] = (ran.nextInt(2)+1);

        } else {
            initialGenes[35] = (2);
        }

        //Lavender [ wildtype, lavender ]
        if(ran.nextInt(100)>WTC){
            initialGenes[36] = (ran.nextInt(2)+1);

        } else {
            initialGenes[36] = (1);
        }
        if(ran.nextInt(100)>WTC){
            initialGenes[37] = (ran.nextInt(2)+1);

        } else {
            initialGenes[37] = (1);
        }

        //Dominant White [ dominant white, wildtype ]
        if(ran.nextInt(100)>WTC){
            initialGenes[38] = (ran.nextInt(2)+1);

        } else {
            initialGenes[38] = (2);
        }
        if(ran.nextInt(100)>WTC){
            initialGenes[39] = (ran.nextInt(2)+1);

        } else {
            initialGenes[39] = (2);
        }

        //Splash [ black, splash ]
        if(ran.nextInt(100)>WTC){
            initialGenes[40] = (ran.nextInt(2)+1);

        } else {
            initialGenes[40] = (1);
        }
        if(ran.nextInt(100)>WTC){
            initialGenes[41] = (ran.nextInt(2)+1);

        } else {
            initialGenes[41] = (1);
        }

        //Fibromelanin [ fibromelanin, wildtype ]
        if(ran.nextInt(100)>WTC){
            initialGenes[42] = (ran.nextInt(2)+1);

        } else {
            initialGenes[42] = (2);
        }
        if(ran.nextInt(100)>WTC){
            initialGenes[43] = (ran.nextInt(2)+1);

        } else {
            initialGenes[43] = (2);
        }

        //yellow shanks [ white, yellow ]
        if(ran.nextInt(100)>WTC){
            initialGenes[44] = (ran.nextInt(2)+1);

        } else {
            initialGenes[44] = (1);
        }
        if(ran.nextInt(100)>WTC){
            initialGenes[45] = (ran.nextInt(2)+1);

        } else {
            initialGenes[45] = (1);
        }

        //Rose [ rose, wildtype ]
        if(ran.nextInt(100)>WTC){
            initialGenes[46] = (ran.nextInt(2)+1);

        } else {
            initialGenes[46] = (2);
        }
        if(ran.nextInt(100)>WTC){
            initialGenes[47] = (ran.nextInt(2)+1);

        } else {
            initialGenes[47] = (2);
        }

        //Pea [ pea, wildtype ]
        if(ran.nextInt(100)>WTC){
            initialGenes[48] = (ran.nextInt(2)+1);

        } else {
            initialGenes[48] = (2);
        }
        if(ran.nextInt(100)>WTC){
            initialGenes[49] = (ran.nextInt(2)+1);

        } else {
            initialGenes[49] = (2);
        }

        //Duplex comb or v comb [ wildtype, duplex ]   v comb is recessive for interest
        if(ran.nextInt(100)>WTC){
            initialGenes[50] = (ran.nextInt(2)+1);

        } else {
            initialGenes[50] = (1);
        }
        if(ran.nextInt(100)>WTC){
            initialGenes[51] = (ran.nextInt(2)+1);

        } else {
            initialGenes[51] = (1);
        }

        //Naked neck [ naked neck, wildtype ]
        if(ran.nextInt(100)>WTC){
            initialGenes[52] = (ran.nextInt(2)+1);

        } else {
            initialGenes[52] = (2);
        }
        if(ran.nextInt(100)>WTC){
            initialGenes[53] = (ran.nextInt(2)+1);

        } else {
            initialGenes[53] = (2);
        }

        //Crest [ normal crest, forward crest, wildtype ]
        if(ran.nextInt(100)>WTC){
            initialGenes[54] = (ran.nextInt(3)+1);

        } else {
            initialGenes[54] = (3);
        }
        if(ran.nextInt(100)>WTC){
            initialGenes[55] = (ran.nextInt(3)+1);

        } else {
            initialGenes[55] = (3);
        }

        //beard [ beard, wildtype ]
        if(ran.nextInt(100)>WTC){
            initialGenes[56] = (ran.nextInt(2)+1);

        } else {
            initialGenes[56] = (2);
        }
        if(ran.nextInt(100)>WTC){
            initialGenes[57] = (ran.nextInt(2)+1);

        } else {
            initialGenes[57] = (2);
        }

        //Foot feather 1 [ small foot feather, big foot feather, wildtype ]
        if(ran.nextInt(100)>WTC){
            initialGenes[58] = (ran.nextInt(3)+1);

        } else {
            initialGenes[58] = (3);
        }
        if(ran.nextInt(100)>WTC){
            initialGenes[59] = (ran.nextInt(3)+1);

        } else {
            initialGenes[59] = (3);
        }

        //Foot feather enhancer [ enhancer, wildtype ]
        if(ran.nextInt(100)>WTC){
            initialGenes[60] = (ran.nextInt(2)+1);

        } else {
            initialGenes[60] = (2);
        }
        if(ran.nextInt(100)>WTC){
            initialGenes[61] = (ran.nextInt(2)+1);

        } else {
            initialGenes[61] = (2);
        }

        //Blue eggs [ blue, wildtype ]
        if(ran.nextInt(100)>WTC){
            initialGenes[62] = (ran.nextInt(2)+1);

        } else {
            initialGenes[62] = (2);
        }
        if(ran.nextInt(100)>WTC){
            initialGenes[63] = (ran.nextInt(2)+1);

        } else {
            initialGenes[63] = (2);
        }

        //Brown Pink eggs [ brown, pink, wildtype ]
        if(ran.nextInt(100)>WTC){
            initialGenes[64] = (ran.nextInt(3)+1);

        } else {
            initialGenes[64] = (3);
        }
        if(ran.nextInt(100)>WTC){
            initialGenes[65] = (ran.nextInt(3)+1);

        } else {
            initialGenes[65] = (3);
        }

        //Brown Cream eggs [ brown, cream, wildtype ]
        if(ran.nextInt(100)>WTC){
            initialGenes[66] = (ran.nextInt(3)+1);

        } else {
            initialGenes[66] = (3);
        }
        if(ran.nextInt(100)>WTC){
            initialGenes[67] = (ran.nextInt(3)+1);

        } else {
            initialGenes[67] = (3);
        }

        //Darker eggs [ wildtype, darker ]
        if(ran.nextInt(100)>WTC){
            initialGenes[68] = (ran.nextInt(2)+1);

        } else {
            initialGenes[68] = (1);
        }
        if(ran.nextInt(100)>WTC){
            initialGenes[69] = (ran.nextInt(2)+1);

        } else {
            initialGenes[69] = (1);
        }

    // TODO here: genes for egg hatch chance when thrown, egg laying rate, and chicken AI modifiers

        return initialGenes;
    }

    public static class GroupData implements IEntityLivingData
    {
        public int[] groupGenes;

        public GroupData(int[] groupGenes)
        {
            this.groupGenes = groupGenes;
        }
    }





}

