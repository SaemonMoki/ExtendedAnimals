package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.ai.ECRoost;
import mokiyoki.enhancedanimals.ai.ECSandBath;
import mokiyoki.enhancedanimals.ai.general.chicken.ECWanderAvoidWater;
import mokiyoki.enhancedanimals.ai.general.chicken.EnhancedWaterAvoidingRandomWalkingEatingGoalChicken;
import mokiyoki.enhancedanimals.capability.egg.EggCapabilityProvider;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.items.DebugGenesBook;
import mokiyoki.enhancedanimals.util.Reference;
import mokiyoki.enhancedanimals.util.handlers.ConfigHandler;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.AirItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.stats.Stats;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * Created by saemon and moki on 30/08/2018.
 */
public class EnhancedChicken extends AnimalEntity implements EnhancedAnimal {

    //avalible UUID spaces : [ S 1 2 3 4 5 6 7 - 8 9 10 11 - 12 13 14 15 - 16 17 18 19 - 20 21 22 23 24 25 26 27 28 29 30 31 ]

    private static final DataParameter<String> SHARED_GENES = EntityDataManager.<String>createKey(EnhancedChicken.class, DataSerializers.STRING);
    private static final DataParameter<Boolean> ROOSTING = EntityDataManager.<Boolean>createKey(EnhancedChicken.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Float> CHICKEN_SIZE = EntityDataManager.createKey(EnhancedChicken.class, DataSerializers.FLOAT);
    /** [4] duckwing, partridge, wheaten, solid
     [5] silver, salmon, lemon, gold, mahogany */
    private static final String[] CHICKEN_TEXTURES_GROUND = new String[] {
        "ground_duckwing_silver.png",   "ground_duckwing_salmon.png",   "ground_duckwing_lemon.png",
        "ground_duckwing_gold.png",     "ground_duckwing_mahogany.png", "ground_solid_silver.png",
        "ground_solid_silver.png",      "ground_partridge_lemon.png",   "ground_partridge_gold.png",
        "ground_partridge_mahogany.png","ground_wheaten_silver.png",    "ground_wheaten_salmon.png",
        "ground_wheaten_lemon.png",     "ground_wheaten_gold.png",      "ground_wheaten_mahogany.png",
        "ground_solid_silver.png",      "ground_solid_silver.png",      "ground_solid_cream.png",
        "ground_solid_buff.png",        "ground_solid_mahogany.png",    "pattern_solid_lavender.png"
    };
    /** [10]  black,blue,splash,splashLav,splashDun,splashChoc,lavender,white,dun,chocolate
        [18] solid,birchen,duckwing,wheaten,quail,columbian,darkbrown,lakenvelder,moorhead,blacktail,penciled,bsinglelace,singlelace,doublelace,spangled,partridge-penciled, halfmoon spangle, bluelace/none */
    private static final String[] CHICKEN_TEXTURES_PATTERN = new String[] {
        "pattern_solid.png","pattern_solid_blue.png", "pattern_solid_splash.png","pattern_solid_splashlav.png","pattern_solid_splashdun.png",                                   // 0
        "pattern_solid_splashchoc.png", "pattern_solid_lav.png","ground_solid_silver.png", "pattern_solid_dun.png", "pattern_solid_choc.png",                                   // 5
        "pattern_birchen.png", "pattern_birchen_blue.png", "pattern_birchen_splash.png","pattern_birchen_splashlav.png","pattern_birchen_splashdun.png",                        //10
        "pattern_birchen_splashchoc.png","pattern_birchen_lav.png", "pattern_birchen_white.png", "pattern_birchen_dun.png", "pattern_birchen_choc.png",                         //15
        "pattern_duckwing.png","pattern_duckwing_blue.png", "pattern_duckwing_splash.png","pattern_duckwing_splashlav.png","pattern_duckwing_splashdun.png",                    //20
        "pattern_duckwing_splashchoc.png", "pattern_duckwing_lav.png","pattern_duckwing_white.png", "pattern_duckwing_dun.png", "pattern_duckwing_choc.png",                    //25
        "pattern_wheaten.png","pattern_wheaten_blue.png", "pattern_wheaten_splash.png","pattern_wheaten_splashlav.png","pattern_wheaten_splashdun.png",                         //30
        "pattern_wheaten_splashchoc.png","pattern_wheaten_lav.png","pattern_wheaten_white.png", "pattern_wheaten_dun.png", "pattern_wheaten_choc.png",                          //35
        "pattern_quail.png", "pattern_quail_blue.png","pattern_quail_splash.png","pattern_quail_splashlav.png","pattern_quail_splashdun.png",                                   //40
        "pattern_quail_splashchoc.png","pattern_quail_lav.png", "pattern_quail_white.png","pattern_quail_dun.png", "pattern_quail_choc.png",                                    //45
        "pattern_columbian.png","pattern_columbian_blue.png", "pattern_columbian_splash.png","pattern_columbian_splashlav.png","pattern_columbian_splashdun.png",               //50
        "pattern_columbian_splashchoc.png", "pattern_columbian_lav.png","pattern_columbian_white.png", "pattern_columbian_dun.png", "pattern_columbian_choc.png",               //55
        "pattern_darkbrown.png","pattern_darkbrown_blue.png", "pattern_darkbrown_splash.png","pattern_darkbrown_splashlav.png","pattern_darkbrown_splashdun.png",               //60
        "pattern_darkbrown_splashchoc.png","pattern_darkbrown_lav.png","pattern_darkbrown_white.png", "pattern_darkbrown_dun.png", "pattern_darkbrown_choc.png",                //65
        "pattern_lakenvelder.png", "pattern_lakenvelder_blue.png", "pattern_lakenvelder_splash.png","pattern_lakenvelder_splashlav.png","pattern_lakenvelder_splashdun.png",    //70
        "pattern_lakenvelder_splashchoc.png","pattern_lakenvelder_lav.png", "pattern_lakenvelder_white.png", "pattern_lakenvelder_dun.png","pattern_lakenvelder_choc.png",      //75
        "pattern_moorhead.png","pattern_moorhead_blue.png", "pattern_moorhead_splash.png","pattern_moorhead_splashlav.png","pattern_moorhead_splashdun.png",                    //80
        "pattern_moorhead_splashchoc.png", "pattern_moorhead_lav.png","pattern_moorhead_white.png", "pattern_moorhead_dun.png", "pattern_moorhead_choc.png",                    //85
        "pattern_blacktail.png","pattern_blacktail_blue.png", "pattern_blacktail_splash.png","pattern_blacktail_splashlav.png","pattern_blacktail_splashdun.png",               //90
        "pattern_blacktail_splashchoc.png","pattern_blacktail_lav.png","pattern_blacktail_white.png", "pattern_blacktail_dun.png", "pattern_blacktail_choc.png",                //95
        "pattern_penciled.png", "pattern_penciled_blue.png", "pattern_penciled_splash.png","pattern_penciled_splashlav.png","pattern_penciled_splashdun.png",                   //100
        "pattern_penciled_splashchoc.png","pattern_penciled_lav.png", "pattern_penciled_white.png","pattern_penciled_dun.png", "pattern_penciled_choc.png",                     //105
        "pattern_bsinglelace.png","pattern_bsinglelace_blue.png","pattern_bsinglelace_splash.png","pattern_bsinglelace_splashlav.png","pattern_bsinglelace_splashdun.png",      //110
        "pattern_bsinglelace_splashchoc.png", "pattern_bsinglelace_lav.png","pattern_bsinglelace_white.png", "pattern_bsinglelace_dun.png", "pattern_bsinglelace_choc.png",     //115
        "pattern_singlelace.png","pattern_singlelace_blue.png", "pattern_singlelace_splash.png","pattern_singlelace_splashlav.png","pattern_singlelace_splashdun.png",          //120
        "pattern_singlelace_splashchoc.png","pattern_singlelace_lav.png","pattern_singlelace_white.png", "pattern_singlelace_dun.png", "pattern_singlelace_choc.png",           //125
        "pattern_doublelace.png", "pattern_doublelace_blue.png", "pattern_doublelace_splash.png","pattern_doublelace_splashlav.png","pattern_doublelace_splashdun.png",         //130
        "pattern_doublelace_splashchoc.png","pattern_doublelace_lav.png", "pattern_doublelace_white.png","pattern_doublelace_dun.png", "pattern_doublelace_choc.png",           //135
        "pattern_spangled.png","pattern_spangled_blue.png","pattern_spangled_splash.png","pattern_spangled_splashlav.png","pattern_spangled_splashdun.png",                     //140
        "pattern_spangled_splashchoc.png", "pattern_spangled_lav.png","pattern_spangled_white.png", "pattern_spangled_dun.png", "pattern_spangled_choc.png",                    //145
        "pattern_prdgpenciled.png", "pattern_prdgpenciled_blue.png", "pattern_prdgpenciled_splash.png","pattern_prdgpenciled_splashlav.png","pattern_prdgpenciled_splashdun.png",//150
        "pattern_prdgpenciled_splashchoc.png","pattern_prdgpenciled_lav.png", "pattern_prdgpenciled_white.png", "pattern_prdgpenciled_dun.png", "pattern_prdgpenciled_choc.png", //155
        "pattern_spangledhm.png","pattern_spangledhm_blue.png","pattern_spangledhm_splash.png","pattern_spangledhm_splashlav.png","pattern_spangledhm_splashdun.png",            //160
        "pattern_spangledhm_splashchoc.png", "pattern_spangledhm_lav.png","pattern_spangledhm_white.png", "pattern_spangledhm_dun.png", "pattern_spangledhm_choc.png",           //165
        "pattern_xtradarkbirchen.png", "pattern_xtradarkbirchen_blue.png", "pattern_xtradarkbirchen_splash.png", "pattern_xtradarkbirchen_splashlav.png", "pattern_xtradarkbirchen_splashdun.png",  //170
        "pattern_xtradarkbirchen_splashchoc.png",  "pattern_xtradarkbirchen_lav.png", "pattern_xtradarkbirchen_white.png", "pattern_xtradarkbirchen_dun.png", "pattern_xtradarkbirchen_choc.png",   //175
        "pattern_leakyblack.png", "pattern_leakyblack_blue.png", "pattern_leakyblack_splash.png", "pattern_leakyblack_splashlav.png", "pattern_leakyblack_splashdun.png",        //180
        "pattern_leakyblack_splashchoc.png",  "pattern_leakyblack_lav.png", "pattern_leakyblack_white.png", "pattern_leakyblack_dun.png", "pattern_leakyblack_choc.png",         //185
        "pattern_xtradarkbsinglace.png", "pattern_xtradarkbsinglace_blue.png", "pattern_xtradarkbsinglace_splash.png", "pattern_xtradarkbsinglace_splashlav.png", "pattern_xtradarkbsinglace_splashdun.png",    //190
        "pattern_xtradarkbsinglace_splashchoc.png",  "pattern_xtradarkbsinglace_lav.png", "pattern_xtradarkbsinglace_white.png", "pattern_xtradarkbsinglace_dun.png", "pattern_xtradarkbsinglace_choc.png",     //195
        "pattern_doublehalfspangle.png", "pattern_doublehalfspangle_blue.png", "pattern_doublehalfspangle_splash.png", "pattern_doublehalfspangle_splashlav.png", "pattern_doublehalfspangle_splashdun.png",    //200
        "pattern_doublehalfspangle_splashchoc.png",  "pattern_doublehalfspangle_lav.png", "pattern_doublehalfspangle_white.png", "pattern_doublehalfspangle_dun.png", "pattern_doublehalfspangle_choc.png",     //205
        "pattern_xtradarkdoublehalfspangle.png", "pattern_xtradarkdoublehalfspangle_blue.png", "pattern_xtradarkdoublehalfspangle_splash.png", "pattern_xtradarkdoublehalfspangle_splashlav.png", "pattern_xtradarkdoublehalfspangle_splashdun.png",    //210
        "pattern_xtradarkdoublehalfspangle_splashchoc.png",  "pattern_xtradarkdoublehalfspangle_lav.png", "pattern_xtradarkdoublehalfspangle_white.png", "pattern_xtradarkdoublehalfspangle_dun.png", "pattern_xtradarkdoublehalfspangle_choc.png",     //215
        "pattern_xtradarkmessyquail.png", "pattern_xtradarkmessyquail_blue.png", "pattern_xtradarkmessyquail_splash.png", "pattern_xtradarkmessyquail_splashlav.png", "pattern_xtradarkmessyquail_splashdun.png",    //220
        "pattern_xtradarkmessyquail_splashchoc.png",  "pattern_xtradarkmessyquail_lav.png", "pattern_xtradarkmessyquail_white.png", "pattern_xtradarkmessyquail_dun.png", "pattern_xtradarkmessyquail_choc.png",     //225
        "pattern_mealyquail.png", "pattern_mealyquail_blue.png", "pattern_mealyquail_splash.png", "pattern_mealyquail_splashlav.png", "pattern_mealyquail_splashdun.png",    //230
        "pattern_mealyquail_splashchoc.png",  "pattern_mealyquail_lav.png", "pattern_mealyquail_white.png", "pattern_mealyquail_dun.png", "pattern_mealyquail_choc.png",     //235
        "pattern_xtradarkincompletequail.png", "pattern_xtradarkincompletequail_blue.png", "pattern_xtradarkincompletequail_splash.png", "pattern_xtradarkincompletequail_splashlav.png", "pattern_xtradarkincompletequail_splashdun.png",    //240
        "pattern_xtradarkincompletequail_splashchoc.png",  "pattern_xtradarkincompletequail_lav.png", "pattern_xtradarkincompletequail_white.png", "pattern_xtradarkincompletequail_dun.png", "pattern_xtradarkincompletequail_choc.png",     //245
        "pattern_spangledc.png", "pattern_spangledc_blue.png", "pattern_spangledc_splash.png", "pattern_spangledc_splashlav.png", "pattern_spangledc_splashdun.png",    //250
        "pattern_spangledc_splashchoc.png",  "pattern_spangledc_lav.png", "pattern_spangledc_white.png", "pattern_spangledc_dun.png", "pattern_spangledc_choc.png",     //255
        "pattern_multilacedduckwing.png", "pattern_multilacedduckwing_blue.png", "pattern_multilacedduckwing_splash.png", "pattern_multilacedduckwing_splashlav.png", "pattern_multilacedduckwing_splashdun.png",    //260
        "pattern_multilacedduckwing_splashchoc.png",  "pattern_multilacedduckwing_lav.png", "pattern_multilacedduckwing_white.png", "pattern_multilacedduckwing_dun.png", "pattern_multilacedduckwing_choc.png",     //265
        "pattern_incompletelaced.png", "pattern_incompletelaced_blue.png", "pattern_incompletelaced_splash.png", "pattern_incompletelaced_splashlav.png", "pattern_incompletelaced_splashdun.png",    //270
        "pattern_incompletelaced_splashchoc.png",  "pattern_incompletelaced_lav.png", "pattern_incompletelaced_white.png", "pattern_incompletelaced_dun.png", "pattern_incompletelaced_choc.png",     //275
        "pattern_xtradarkwheaten.png", "pattern_xtradarkwheaten_blue.png", "pattern_xtradarkwheaten_splash.png", "pattern_xtradarkwheaten_splashlav.png", "pattern_xtradarkwheaten_splashdun.png",    //280
        "pattern_xtradarkwheaten_splashchoc.png",  "pattern_xtradarkwheaten_lav.png", "pattern_xtradarkwheaten_white.png", "pattern_xtradarkwheaten_dun.png", "pattern_xtradarkwheaten_choc.png",     //285
        "pattern_incompletequail.png", "pattern_incompletequail_blue.png", "pattern_incompletequail_splash.png", "pattern_incompletequail_splashlav.png", "pattern_incompletequail_splashdun.png",    //290
        "pattern_incompletequail_splashchoc.png",  "pattern_incompletequail_lav.png", "pattern_incompletequail_white.png", "pattern_incompletequail_dun.png", "pattern_incompletequail_choc.png",     //295
        "pattern_incompletecolumbian.png", "pattern_incompletecolumbian_blue.png", "pattern_incompletecolumbian_splash.png", "pattern_incompletecolumbian_splashlav.png", "pattern_incompletecolumbian_splashdun.png",    //300
        "pattern_incompletecolumbian_splashchoc.png",  "pattern_incompletecolumbian_lav.png", "pattern_incompletecolumbian_white.png", "pattern_incompletecolumbian_dun.png", "pattern_incompletecolumbian_choc.png",     //305
        "pattern_xtradarkincompletecolumbian.png", "pattern_xtradarkincompletecolumbian_blue.png", "pattern_xtradarkincompletecolumbian_splash.png", "pattern_xtradarkincompletecolumbian_splashlav.png", "pattern_xtradarkincompletecolumbian_splashdun.png",    //310
        "pattern_xtradarkincompletecolumbian_splashchoc.png",  "pattern_xtradarkincompletecolumbian_lav.png", "pattern_xtradarkincompletecolumbian_white.png", "pattern_xtradarkincompletecolumbian_dun.png", "pattern_xtradarkincompletecolumbian_choc.png",     //315
        "pattern_incompletesinglelace.png", "pattern_incompletesinglelace_blue.png", "pattern_incompletesinglelace_splash.png", "pattern_incompletesinglelace_splashlav.png", "pattern_incompletesinglelace_splashdun.png",    //320
        "pattern_incompletesinglelace_splashchoc.png",  "pattern_incompletesinglelace_lav.png", "pattern_incompletesinglelace_white.png", "pattern_incompletesinglelace_dun.png", "pattern_incompletesinglelace_choc.png",     //325
        "pattern_xtradarksinglelace.png", "pattern_xtradarksinglelace_blue.png", "pattern_xtradarksinglelace_splash.png", "pattern_xtradarksinglelace_splashlav.png", "pattern_xtradarksinglelace_splashdun.png",    //330
        "pattern_xtradarksinglelace_splashchoc.png",  "pattern_xtradarksinglelace_lav.png", "pattern_xtradarksinglelace_white.png", "pattern_xtradarksinglelace_dun.png", "pattern_xtradarksinglelace_choc.png",     //335
        "pattern_darkpenciled.png", "pattern_darkpenciled_blue.png", "pattern_darkpenciled_splash.png", "pattern_darkpenciled_splashlav.png", "pattern_darkpenciled_splashdun.png",    //340
        "pattern_darkpenciled_splashchoc.png",  "pattern_darkpenciled_lav.png", "pattern_darkpenciled_white.png", "pattern_darkpenciled_dun.png", "pattern_darkpenciled_choc.png",     //345
        "pattern_bluelaced.png",    //special case number 350
        "patternless",                     //special case patternless 351
        "48.png"                //test a texture 352
    };
    private static final String[] CHICKEN_TEXTURES_MOORHEAD = new String[] {
        "", "moorhead_black.png", "moorhead_blue.png", "moorhead_splash.png", "moorhead_splashlav.png", "moorhead_splash.png", "moorhead_splashdun.png",
            "moorhead_splashchoc.png", "moorhead_lav.png", "moorhead_white.png", "moorhead_dun.png", "moorhead_choc.png",
    };
    private static final String[] CHICKEN_TEXTURES_WHITE = new String[] {
        "","white_barred.png","white_mottles.png","white_crested.png"
    };

    private static final String[] CHICKEN_TEXTURES_CHICKBASE = new String[] {
            "baby_white.png","baby_yellow.png"
    };

    private static final String[] CHICKEN_TEXTURES_CHICKRED = new String[] {
            "","baby_red.png", "baby_redwash.png", "baby_redstripes.png"
    };

    private static final String[] CHICKEN_TEXTURES_CHICKBLACK = new String[] {
            "", "baby_blackstripes.png", "baby_whitestripes.png", "baby_lavstripes.png", "baby_bluestripes.png", "baby_chocstripes.png", "baby_dunstripes.png",
                "baby_blackshaded.png", "baby_whiteshaded.png", "baby_lavshaded.png", "baby_blueshaded.png", "baby_chocshaded.png", "baby_dunshaded.png",
                "baby_black.png", "baby_white.png", "pattern_solid_lav.png", "pattern_solid_blue.png", "pattern_solid_choc.png", "pattern_solid_dun.png"
    };

    private static final String[] CHICKEN_TEXTURES_CHICKWHITE = new String[] {
            "","baby_mottles.png", "baby_barred.png"
    };

    private static final String[] CHICKEN_TEXTURES_SHANKS = new String[] {
        "shanks_horn.png","shanks_lightyellow.png","shanks_yellow.png","shanks_darkyellow.png","shanks_willow.png","shanks_black.png",
        "shanks_verywhite.png","shanks_lightwhite.png","shanks_white.png","shanks_grey.png", "shanks_slate.png", "shanks_black.png",
        "shanks_superhorn.png", "shanks_lightsuperyellow.png", "shanks_superyellow.png", "shanks_darksuperyellow.png","shanks_superwillow.png","shanks_black.png"
    };
    private static final String[] CHICKEN_TEXTURES_COMB = new String[] {
        "comb_black.png","comb_blackleaking.png","comb_red.png",
        "comb_moorish.png", "comb_mulberry.png", "comb_red.png"
    };
    private static final String[] CHICKEN_TEXTURES_FACE = new String[] {
            "", "face_faint_red.png", "face_normal_red.png", "face_extensive_red.png",
                "face_faint_mulberry.png", "face_normal_mulberry.png", "face_extensive_mulberry.png",
                "face_faint_black.png", "face_normal_black.png", "face_extensive_black.png",
                "face_faint_mottledred.png", "face_normal_mottledred.png", "face_extensive_mottledred.png",
                "face_faint_mottledmulberry.png", "face_normal_mottledmulberry.png", "face_extensive_mottledmulberry.png",
                "face_faint_mottledblack.png", "face_normal_mottledblack.png", "face_extensive_mottledblack.png",
                "face_faint_white.png", "face_normal_white.png", "face_extensive_white.png",
                "face_faint_lightblue.png", "face_lightblue_purple.png", "face_lightblue_purple.png",
                "face_faint_blue.png", "face_normal_blue.png", "face_extensive_blue.png"
    };
    private static final String[] CHICKEN_TEXTURES_EARS = new String[] {
            "", "ear_red1.png", "ear_red2.png", "ear_red3.png", "ear_red4.png", "ear_red5.png", "ear_red6.png", "ear_red7.png", "ear_red8.png", "ear_red9.png", "ear_red10.png",
                "ear_mulberry1.png", "ear_mulberry2.png", "ear_mulberry3.png", "ear_mulberry4.png", "ear_mulberry5.png", "ear_mulberry6.png", "ear_mulberry7.png", "ear_mulberry8.png", "ear_mulberry9.png", "ear_mulberry10.png",
                "ear_black1.png", "ear_black2.png", "ear_black3.png", "ear_black4.png", "ear_black5.png", "ear_black6.png", "ear_black7.png", "ear_black8.png", "ear_black9.png", "ear_black10.png",
                "ear_mottledred1.png", "ear_mottledred2.png", "ear_mottledred3.png", "ear_mottledred4.png", "ear_mottledred5.png", "ear_mottledred6.png", "ear_mottledred7.png", "ear_mottledred8.png", "ear_mottledred9.png", "ear_mottledred10.png",
                "ear_mottledmulberry1.png", "ear_mottledmulberry2.png", "ear_mottledmulberry3.png", "ear_mottledmulberry4.png", "ear_mottledmulberry5.png", "ear_mottledmulberry6.png", "ear_mottledmulberry7.png", "ear_mottledmulberry8.png", "ear_mottledmulberry9.png", "ear_mottledmulberry10.png",
                "ear_mottledblack1.png", "ear_mottledblack2.png", "ear_mottledblack3.png", "ear_mottledblack4.png", "ear_mottledblack5.png", "ear_mottledblack6.png", "ear_mottledblack7.png", "ear_mottledblack8.png", "ear_mottledblack9.png", "ear_mottledblack10.png",
                "ear_white1.png", "ear_white2.png", "ear_white3.png", "ear_white4.png", "ear_white5.png", "ear_white6.png", "ear_white7.png", "ear_white8.png", "ear_white9.png", "ear_white10.png",
                "ear_lightblue1.png", "ear_lightblue2.png", "ear_lightblue3.png", "ear_lightblue4.png", "ear_lightblue5.png", "ear_lightblue6.png", "ear_lightblue7.png", "ear_lightblue8.png", "ear_lightblue9.png", "ear_lightblue10.png",
                "ear_blue1.png", "ear_blue2.png", "ear_blue3.png", "ear_blue4.png", "ear_blue5.png", "ear_blue6.png", "ear_blue7.png", "ear_blue8.png", "ear_blue9.png", "ear_blue10.png",
                "ear_yellow1.png", "ear_yellow2.png", "ear_yellow3.png", "ear_yellow4.png", "ear_yellow5.png", "ear_yellow6.png", "ear_yellow7.png", "ear_yellow8.png", "ear_yellow9.png", "ear_yellow10.png",
                "ear_lightgreen1.png", "ear_lightgreen2.png", "ear_lightgreen3.png", "ear_lightgreen4.png", "ear_lightgreen5.png", "ear_lightgreen6.png", "ear_lightgreen7.png", "ear_lightgreen8.png", "ear_lightgreen9.png", "ear_lightgreen10.png",
                "ear_green1.png", "ear_green2.png", "ear_green3.png", "ear_green4.png", "ear_green5.png", "ear_green6.png", "ear_green7.png", "ear_green8.png", "ear_green9.png", "ear_green10.png",
                "ear_mottledmoorish1.png", "ear_mottledmoorish2.png", "ear_mottledmoorish3.png", "ear_mottledmoorish4.png", "ear_mottledmoorish5.png", "ear_mottledmoorish6.png", "ear_mottledmoorish7.png", "ear_mottledmoorish8.png", "ear_mottledmoorish9.png", "ear_mottledmoorish10.png",

    };
    private static final String[] CHICKEN_TEXTURES_EYES = new String[] {
        "eyes_albino.png","eyes_black.png"
    };

    private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Items.WHEAT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS, Items.SWEET_BERRIES, Items.DANDELION, Items.SPIDER_EYE, Items.TALL_GRASS, Items.GRASS, Items.BREAD);
    private static final Ingredient BREED_ITEMS = Ingredient.fromItems(Items.WHEAT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS);

    Map<Item, Integer> foodWeightMap = new HashMap() {{
        put(new ItemStack(Items.TALL_GRASS).getItem(), 6000);
        put(new ItemStack(Items.GRASS).getItem(), 3000);
        put(new ItemStack(Items.WHEAT).getItem(), 6000);
        put(new ItemStack(Items.BREAD).getItem(), 18000);
        put(new ItemStack(Items.WHEAT_SEEDS).getItem(), 4000);
        put(new ItemStack(Items.MELON_SEEDS).getItem(), 4000);
        put(new ItemStack(Items.PUMPKIN_SEEDS).getItem(), 4000);
        put(new ItemStack(Items.BEETROOT_SEEDS).getItem(), 4000);
        put(new ItemStack(Items.SWEET_BERRIES).getItem(), 1500);
        put(new ItemStack(Items.DANDELION).getItem(), 1500);
        put(new ItemStack(Items.SPIDER_EYE).getItem(), 1500);
    }};

    public float wingRotation;
    public float destPos;
    public float oFlapSpeed;
    public float oFlap;
    private float wingRotDelta = 1.0F;
    private int timeUntilNextEgg;
    private int grassTimer;
    private int sandBathTimer;
    private EnhancedWaterAvoidingRandomWalkingEatingGoalChicken entityAIEatGrass;
    private ECSandBath ecSandBath;
    private String dropMeatType;

    private int hunger = 0;

    private boolean resetTexture = true;

    private static final int WTC = ConfigHandler.COMMON.wildTypeChance.get();
    private int broodingCount;
    private final List<String> chickenTextures = new ArrayList<>();
    //'father' gene variables list
    private int[] genes = new int[Reference.CHICKEN_GENES_LENGTH];
    private int[] mateGenes = new int[Reference.CHICKEN_GENES_LENGTH];
    private int[] mitosisGenes = new int[Reference.CHICKEN_GENES_LENGTH];
    private int[] mateMitosisGenes = new int[Reference.CHICKEN_GENES_LENGTH];

    private float chickenSize = 0.0F;

    public EnhancedChicken(EntityType<? extends EnhancedChicken> entityType, World worldIn) {
        super(entityType, worldIn);
        this.setChickenSize();
//        this.setSize(0.4F, 0.7F); //I think its the height and width of a chicken
        this.timeUntilNextEgg = this.rand.nextInt(this.rand.nextInt(6000) + 6000); //TODO make some genes to alter these numbers
        this.setPathPriority(PathNodeType.WATER, 0.0F);
    }

    @Override
    protected void registerGoals() {
        //TODO add temperaments
        this.entityAIEatGrass = new EnhancedWaterAvoidingRandomWalkingEatingGoalChicken(this, 1.0D, 7, 0.001F, 120, 2, 50);
        this.ecSandBath = new ECSandBath(this);
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.4D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0D, false, TEMPTATION_ITEMS));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(5, new ECWanderAvoidWater(this, 1.0D));
        this.goalSelector.addGoal(5, this.entityAIEatGrass);
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(9, new ECRoost(this));

    }

    protected void updateAITasks()
    {
        this.grassTimer = this.entityAIEatGrass.getEatingGrassTimer();
        this.sandBathTimer = this.ecSandBath.getSandBathTimer();
        super.updateAITasks();
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(SHARED_GENES, new String());
        this.dataManager.register(ROOSTING, new Boolean(false));
        this.dataManager.register(CHICKEN_SIZE, 0.0F);
    }

    private void setChickenSize(float size) {
        this.dataManager.set(CHICKEN_SIZE, size);
    }

    public float getSize() {
        return this.dataManager.get(CHICKEN_SIZE);
    }

    public int getHunger(){
        return hunger;
    }

    public void decreaseHunger(int decrease) {
        if (this.hunger - decrease < 0) {
            this.hunger = 0;
        } else {
            this.hunger = this.hunger - decrease;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 10) {
            this.grassTimer = 40;
        } else {
            super.handleStatusUpdate(id);
        }
    }

    //Eating Animation
    @OnlyIn(Dist.CLIENT)
    public float getHeadRotationPointY(float partialTickTime) {
        if (this.grassTimer <= 0)
        {
            return 0.0F;
        }
        else if (this.grassTimer >= 4 && this.grassTimer <= 36)
        {
            return 0.2F;
        }
        else
        {
            return this.grassTimer < 4 ? ((float)this.grassTimer - partialTickTime) / 4.0F : -((float)(this.grassTimer - 40) - partialTickTime) / 4.0F;
        }
    }
    //Eating Animation
    @OnlyIn(Dist.CLIENT)
    public float getHeadRotationAngleX(float partialTickTime) {
        if (this.grassTimer > 4 && this.grassTimer <= 36)
        {
            float f = ((float)(this.grassTimer - 4) - partialTickTime) / 32.0F;
            return ((float)Math.PI / 5F) + ((float)Math.PI * 7F / 100F) * MathHelper.sin(f * 28.7F);
        }
        else
        {
            return this.grassTimer > 0 ? ((float)Math.PI / 5F) : this.rotationPitch * 0.017453292F;
        }
    }

    @Override
    public boolean processInteract(PlayerEntity entityPlayer, Hand hand) {
        ItemStack itemStack = entityPlayer.getHeldItem(hand);
        Item item = itemStack.getItem();

        if (!this.world.isRemote && !hand.equals(Hand.OFF_HAND)) {
            if (item instanceof AirItem) {
                ITextComponent message = getHungerText();
                entityPlayer.sendMessage(message);
            } else if (item instanceof DebugGenesBook) {
                Minecraft.getInstance().keyboardListener.setClipboardString(this.dataManager.get(SHARED_GENES));
            }
            else if (TEMPTATION_ITEMS.test(itemStack) && hunger >= 6000) {
                if (this.foodWeightMap.containsKey(item)) {
                    decreaseHunger(this.foodWeightMap.get(item));
                } else {
                    decreaseHunger(6000);
                }
                if (!entityPlayer.abilities.isCreativeMode) {
                    itemStack.shrink(1);
                }
            }
        }

        return super.processInteract(entityPlayer, hand);
    }

    private ITextComponent getHungerText() {
        String hungerText = "";
        if (this.hunger < 1000) {
            hungerText = "eanimod.hunger.not_hungry";
        } else if (this.hunger < 4000) {
            hungerText = "eanimod.hunger.hungry";
        } else if (this.hunger < 9000) {
            hungerText = "eanimod.hunger.very_hunger";
        } else if (this.hunger < 16000) {
            hungerText = "eanimod.hunger.starving";
        } else if (this.hunger > 24000) {
            hungerText = "eanimod.hunger.dying";
        }
        return new TranslationTextComponent(hungerText);
    }

    public void setSharedGenes(int[] genes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < genes.length; i++){
            sb.append(genes[i]);
            if (i != genes.length -1){
                sb.append(",");
            }
        }
        this.dataManager.set(SHARED_GENES, sb.toString());
    }

    public void setSharedGenesFromEntityEgg(String genes) {
        this.dataManager.set(SHARED_GENES, genes);
    }

    public int[] getSharedGenes() {
        String sharedGenes = ((String)this.dataManager.get(SHARED_GENES)).toString();
        int[] sharedGenesArray;
        if(sharedGenes.isEmpty()){
            return null;
        } else {
            String[] genesToSplit = sharedGenes.split(",");
            sharedGenesArray = new int[genesToSplit.length];
            for (int i = 0; i < sharedGenesArray.length; i++) {
                //parse and store each value into int[] to be returned
                sharedGenesArray[i] = Integer.parseInt(genesToSplit[i]);
            }
        }
        return sharedGenesArray;
    }

    public boolean isRoosting() {
        return this.dataManager.get(ROOSTING);
    }

    public void setRoosting(boolean isRoosting) {
        this.dataManager.set(ROOSTING, isRoosting);
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return this.isChild() ? sizeIn.height * 0.85F : sizeIn.height * 0.92F;
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(3.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    @Override
    public void livingTick() {
        super.livingTick();
        this.oFlap = this.wingRotation;
        this.oFlapSpeed = this.destPos;
        this.destPos = (float)((double)this.destPos + (double)(this.onGround ? -1 : 4) * 0.3D);
        this.destPos = MathHelper.clamp(this.destPos, 0.0F, 1.0F);

        if (!this.onGround && this.wingRotDelta < 1.0F) {
            this.wingRotDelta = 1.0F;
        }

        this.wingRotDelta = (float)((double)this.wingRotDelta * 0.9D);
        Vec3d vec3d = this.getMotion();
        if (!this.onGround && vec3d.y < 0.0D) {
            this.setMotion(vec3d.mul(1.0D, 0.6D, 1.0D));
        }

        this.wingRotation += this.wingRotDelta * 2.0F;

        if (this.world.isRemote) {
            this.grassTimer = Math.max(0, this.grassTimer - 1);
        } else {

            if (this.getIdleTime() < 100) {
                if (hunger <= 72000) {
                    if (ticksExisted % 2 == 0){
                        hunger++;
                    }
                    if (hunger <= 24000) {
                        --this.timeUntilNextEgg;
                    } else if (hunger >= 48000) {
                        this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
                    }
                }
            }

            if (!this.isChild() && this.timeUntilNextEgg <= 0) {
                this.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
                mixMateMitosisGenes();
                mixMitosisGenes();
                ItemStack eggItem = new ItemStack(getEggColour(resolveEggColour()), 1, null);
                eggItem.getCapability(EggCapabilityProvider.EGG_CAP, null).orElse(new EggCapabilityProvider()).setGenes(getEggGenes(this.genes, this.mateGenes, this.mitosisGenes, this.mateMitosisGenes, false));
                CompoundNBT nbtTagCompound = eggItem.serializeNBT();
                eggItem.deserializeNBT(nbtTagCompound);
                this.entityDropItem(eggItem, 1);
                this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
            }

            lethalGenes();

        }

       //TODO if "is child" and parent is sitting go under parent, possibly turn off ability to collide.

        //TODO if "is child" and parent is 1 block over or less and doesn't have a passenger ride on parent's back

        //TODO if it is daytime and if this chicken can crow and (it randomly wants to crow OR another chicken near by is crowing) then crow.

    }

    private void setChickenSize(){

        float size = 1.0F;

        if(genes[74] == 1){
            size = size - 0.05F;
        }else if(genes[74] == 2){
            size = size - 0.025F;
        }
        if(genes[75] == 1){
            size = size - 0.05F;
        }else if(genes[75] == 2){
            size = size - 0.025F;
        }

        if(genes[76] == 1 || genes[77] == 1){
            size = size - 0.05F;
        }else if(genes[76] == 3 && genes[77] == 3){
            size = size - 0.1F;
        }

        if(genes[78] == 1 || genes[79] == 1){
            size = size * 0.94F;
        }

        if(genes[7] == 2){
            size = size * 0.9F;
        }

        if(genes[8] == 2){
            size = size * 0.75F;
        }

        this.chickenSize = size;
        this.setChickenSize(size);

    }

    public void fall(float distance, float damageMultiplier)
    {
    }

    public void lethalGenes(){

        if(genes[70] == 2 && genes[71] == 2) {
                this.remove();
        } else if(genes[72] == 2 && genes[73] == 2){
                this.remove();
        } else if(genes[104] == 2 && genes[105] == 2){
                this.remove();
        } else if (genes[150] == 2 && genes[151] == 2){
                this.remove();
        }
    }



    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_CHICKEN_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_CHICKEN_HURT;
    }

    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_CHICKEN_DEATH;
    }

    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15F, 1.0F);
    }

    /**
     * Chicken grass eating and sand bathing
    */

    //TODO make the grass eating actions
    //TODO make the sand bathing actions

    //also provides sand bath bonus
    public void eatGrassBonus()
    {
//        if (this.isChild())
//        {
//            this.addGrowth(60);
//        }
    }

    public boolean isBreedingItem(ItemStack stack) {
        //TODO set this to a separate item or type of item for force breeding
        return BREED_ITEMS.test(stack);
    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageable) {
        this.mateGenes = ((EnhancedChicken)ageable).getGenes();
        mixMateMitosisGenes();
        mixMitosisGenes();
        ((EnhancedChicken)ageable).setMateGenes(this.genes);
        ((EnhancedChicken)ageable).mixMateMitosisGenes();
        ((EnhancedChicken)ageable).mixMitosisGenes();

        this.setGrowingAge(10);
        this.resetInLove();
        ageable.setGrowingAge(10);
        ((EnhancedChicken)ageable).resetInLove();

        ServerPlayerEntity entityplayermp = this.getLoveCause();
        if (entityplayermp == null && ((EnhancedChicken)ageable).getLoveCause() != null) {
            entityplayermp = ((EnhancedChicken)ageable).getLoveCause();
        }

        if (entityplayermp != null) {
            entityplayermp.addStat(Stats.ANIMALS_BRED);
            CriteriaTriggers.BRED_ANIMALS.trigger(entityplayermp, this, ((EnhancedChicken)ageable), (AgeableEntity)null);
        }

        return null;
    }


    private Item getEggColour(int eggColourGene){

        switch (eggColourGene) {
            case 0:
//                Item item = Item.REGISTRY.getObject(new ResourceLocation("eanimod:egg_white"));
                return ModItems.Egg_White;
//                return new EnhancedEgg("eggWhite", "egg_white");
            case 1:
                return ModItems.Egg_Cream;
//                return new EnhancedEgg ("eggCream", "egg_cream");
            case 2:
                return ModItems.Egg_CreamDark;
//                return new EnhancedEgg ("eggCreamDark", "egg_creamdark");
            case 3:
                return ModItems.Egg_Pink;
//                return new EnhancedEgg ("eggPink", "egg_pink");
            case 4:
                return ModItems.Egg_PinkDark;
//                return new EnhancedEgg ("eggPinkDark", "egg_pinkdark");
            case 5:
                return ModItems.Egg_Brown;
//                return new EnhancedEgg ("eggBrown", "egg_brown");
            case 6:
                return ModItems.Egg_BrownDark;
//                return new EnhancedEgg ("eggBrownDark", "egg_browndark");
            case 7:
                return ModItems.Egg_Blue;
//                return new EnhancedEgg ("eggBlue", "egg_blue");
            case 8:
                return ModItems.Egg_GreenLight;
//                return new EnhancedEgg ("eggGreenLight", "egg_greenlight");
            case 9:
                return ModItems.Egg_Green;
//                return new EnhancedEgg ("eggGreen", "egg_green");
            case 10:
                return ModItems.Egg_Grey;
//                return new EnhancedEgg ("eggGrey", "egg_grey");
            case 11:
                return ModItems.Egg_GreyGreen;
//                return new EnhancedEgg ("eggGreyGreen", "egg_greygreen");
            case 12:
                return ModItems.Egg_Olive;
//                return new EnhancedEgg ("eggOlive", "egg_olive");
            case 13:
                return ModItems.Egg_GreenDark;
//                return new EnhancedEgg ("eggGreenDark", "egg_greendark");
        }

        //TODO set up exception handling and put an exception here we should NEVER get here.
        return null;
    }

    private int resolveEggColour(){
        int eggColour = 0;

        if(genes[5] == 1){

            if(genes[64] == 1 || genes[65] == 1 || genes[66] == 1 || genes[67] == 1){
                //egg is brown
                eggColour = 5;
            }else if((genes[64] == 2 || genes[65] == 2) && (genes[66] == 2 || genes[67] == 2)){
                //egg is brown
                eggColour = 5;
            }else if(genes[66] == 2 || genes[67] == 2){
                //egg is cream
                eggColour = 1;
            }else if(genes[64] == 2 || genes[65] == 2){
                //egg is pink
                eggColour = 3;
            }else if(genes[64] == 3 || genes[65] == 3 || genes[66] == 3 || genes[67] == 3){
                //egg is white
                eggColour = 0;
            }

        }

        //darkens egg if already brown shade
        if(genes[68] == 1 || genes[69] == 1){
            if(eggColour == 0){
                eggColour = 5;
            }else {
                eggColour = eggColour + 1;
            }
        }

        //toggles blue egg version
        if(genes[62] == 1 || genes[63] == 1){
            eggColour = eggColour +7;
        }

        return eggColour;
    }

    @OnlyIn(Dist.CLIENT)
    public String getChickenTexture() {
        if (this.chickenTextures.isEmpty()) {
            this.setTexturePaths();
        } else if (!this.isChild() && resetTexture) {
            resetTexture = false;
            this.setTexturePaths();
        }

        return this.chickenTextures.stream().collect(Collectors.joining("/","enhanced_chicken/",""));

    }



    @OnlyIn(Dist.CLIENT)
    public String[] getVariantTexturePaths() {
        if (this.chickenTextures.isEmpty()) {
            this.setTexturePaths();
        }

        return this.chickenTextures.stream().toArray(String[]::new);
    }

    @OnlyIn(Dist.CLIENT)
    private void setTexturePaths() {
        int[] genesForText = getSharedGenes();
        if(genesForText!=null) {
            if (!isChild()) {
                int ground = 0;
                int pattern = 0;
                int moorhead = 0;
                int white = 0;
                int shanks = 3;
                int comb = 2;
                int eyes = 1;
                int ptrncolours = 10; //number of pattern colours

                int moorheadtoggle = 0;
                int Melanin = 0;

                int face = 0;
                int ears = 0;
                int earsW = 0;

                //TODO fix up columbian type patterns to look more varried
                //TODO add in heterozygous pattern variations
                //TODO redo ground colours to use autosomal red and more fleshed out

                boolean isAlbino = false;

                if (genesForText[20] != 1 && genesForText[21] != 1) {                                                                       //checks if not wildtype
                    if (genesForText[20] == 2 || genesForText[21] == 2) {                                                                   //sets recessive white or albino
                        //recessive white
                        ground = 15;
                        pattern = 351;
                    } else {
                        //albino
                        ground = 15;
                        pattern = 351;
                        white = 0;
                        shanks = 6;
                        comb = 2;
                        eyes = 0;
                        isAlbino = true;
                    }
                } else {
                    if (genesForText[24] == 5 || genesForText[25] == 5) {
                        //extended black tree
                        if (genesForText[24] == 5 && genesForText[25] == 5) {
                            if (genesForText[28] == 1 && genesForText[29] == 1 && genesForText[98] == 1 && genesForText[99] == 1) {
                                //xtradark birchen
                                pattern = 17;
                                ground = 0;
                            } else {
                                //solid black
                                pattern = 0;
                                ground = 15;
                            }
                        } else if (genesForText[24] == 1 || genesForText[25] == 1) {
                            //xtradark birchen
                            pattern = 17;
                            ground = 0;
                        } else {
                            if (genesForText[28] == 1 && genesForText[29] == 1 && genesForText[98] == 1 && genesForText[99] == 1) {
                                //leaky black
                                pattern = 18;
                                ground = 0;
                            } else {
                                //xtradark birchen
                                pattern = 17;
                                ground = 0;
                            }
                        }
                    } else if (genesForText[24] == 1 || genesForText[25] == 1) {
                        //birchen tree
                        if (genesForText[28] == 1 && genesForText[29] == 1) {
                            if (genesForText[98] == 1 && genesForText[99] == 1) {
                                if (genesForText[30] == 1 && genesForText[31] == 1) {
                                    if (genesForText[26] == 1 || genesForText[27] == 1) {
                                        if (genesForText[100] == 2 && genesForText[101] == 2) {
                                            //xtra dark birchen single lace
                                            pattern = 19;
                                            ground = 15;
                                        } else {
                                            //birchen single laced
                                            pattern = 11;
                                            ground = 15;
                                        }
                                    } else {
                                        //extended patterned columbian
                                        pattern = 5;
                                        ground = 15;
                                    }
                                } else if (genesForText[30] == 1 || genesForText[31] == 1) {
                                    if (genesForText[26] == 1 || genesForText[27] == 1) {
                                        if (genesForText[100] == 2 && genesForText[101] == 2) {
                                            //moorhead doublehalfspangled
                                            pattern = 20;
                                            ground = 15;
                                            moorhead = 1;
                                        } else {
                                            //doublehalfspangle
                                            pattern = 20;
                                            ground = 15;
                                        }

                                    } else {
                                        if (genesForText[100] == 2 && genesForText[101] == 2) {
                                            //overly dark columbian
                                            pattern = 5;
                                            ground = 15;
                                        } else {
                                            //moorheaded columbian
                                            pattern = 5;
                                            ground = 15;
                                            moorhead = 1;
                                        }
                                    }
                                } else {
                                    if (genesForText[26] == 1 || genesForText[27] == 1) {
                                        if (genesForText[100] == 2 && genesForText[101] == 2) {
                                            //moorhead doublehalfspangled
                                            pattern = 20;
                                            ground = 15;
                                            moorhead = 1;
                                        } else {
                                            //doublehalfspangle
                                            pattern = 20;
                                            ground = 15;
                                        }

                                    } else {
                                        if (genesForText[100] == 2 && genesForText[101] == 2) {
                                            //moorhead transverse penciled
                                            pattern = 34;
                                            ground = 15;
                                            moorhead = 1;
                                        } else {
                                            //transverse penciled
                                            pattern = 10;
                                            ground = 15;
                                        }
                                    }
                                }
                            } else if (genesForText[98] == 1 || genesForText[99] == 1) {
                                if (genesForText[30] == 1 || genesForText[31] == 1) {
                                    if (genesForText[26] == 1 || genesForText[27] == 1) {
                                        //dark doublehalfspangle
                                        pattern = 21;
                                        ground = 15;
                                    } else {
                                        //dark messy quail
                                        pattern = 22;
                                        ground = 15;
                                    }
                                } else {
                                    if (genesForText[26] == 1 || genesForText[27] == 1) {
                                        if (genesForText[100] == 2 && genesForText[101] == 2) {
                                            //dark transverse penciled
                                            //TODO what are the different qualities of transverse penciled
                                            pattern = 34;
                                            ground = 5;
                                            moorhead = 1;
                                        } else {
                                            //incomplete penciled
                                            //TODO make incomplete transverse penciled
                                            pattern = 10;
                                            ground = 5;
                                        }
                                    } else {
                                        //dark quail mealy
                                        pattern = 23;
                                        ground = 5;
                                    }

                                }
                            } else {
                                if (genesForText[30] == 1 || genesForText[31] == 1) {
                                    if (genesForText[100] == 2 || genesForText[101] == 2) {
                                        //solid black
                                        pattern = 0;
                                        ground = 15;
                                    } else {
                                        // leaky black
                                        pattern = 18;
                                        ground = 0;
                                    }
                                } else {
                                    if (genesForText[100] == 1 && genesForText[101] == 1) {
                                        //leaky black
                                        pattern = 18;
                                        ground = 5;
                                    } else {
                                        //birchen
                                        pattern = 1;
                                        ground = 5;
                                    }
                                }
                            }
                        } else if (genesForText[28] == 1 || genesForText[29] == 1) {
                            if (genesForText[98] == 1 || genesForText[99] == 1) {
                                if (genesForText[30] == 1 || genesForText[31] == 1) {
                                    if (genesForText[26] == 1 || genesForText[27] == 1) {
                                        if (genesForText[100] == 2 && genesForText[101] == 2) {
                                            //extended patterned halfspangle
                                            //TODO what is this pattern really?
                                            pattern = 16;
                                            ground = 15;
                                            moorhead = 1;
                                        } else {
                                            //halfspangle
                                            pattern = 16;
                                            ground = 15;
                                        }
                                    } else {
                                        //incomplete columbian
                                        pattern = 6;
                                        ground = 15;
                                    }
                                } else {
                                    if (genesForText[26] == 1 || genesForText[27] == 1) {
                                        if (genesForText[100] == 2 && genesForText[101] == 2) {
                                            //extended patterned transverse penciled
                                            pattern = 34;
                                            ground = 15;
                                            moorhead = 1;
                                        } else {
                                            // transverse penciled
                                            pattern = 10;
                                            ground = 5;
                                        }
                                    } else {
                                        // columbian
                                        pattern = 5;
                                        ground = 15;

                                    }
                                }
                            } else {
                                if (genesForText[30] == 1 || genesForText[31] == 1) {
                                    if (genesForText[100] == 2 && genesForText[101] == 2) {
                                        //solid black
                                        pattern = 0;
                                        ground = 15;
                                    } else {
                                        //leaky black
                                        pattern = 18;
                                        ground = 15;
                                    }
                                } else {
                                    if (genesForText[100] == 2 && genesForText[101] == 2) {
                                        //leaky black
                                        pattern = 18;
                                        ground = 15;
                                    } else {
                                        //birchen
                                        pattern = 1;
                                        ground = 0;
                                    }
                                }
                            }
                        } else {
                            if (genesForText[98] == 1 || genesForText[99] == 1) {
                                if (genesForText[30] == 1 || genesForText[31] == 1) {
                                    if (genesForText[26] == 1 || genesForText[27] == 1) {
                                        if (genesForText[100] == 2 && genesForText[101] == 2) {
                                            //extended patterned spangled
                                            pattern = 14;
                                            ground = 15;
                                        } else {
                                            // spangled
                                            pattern = 16;
                                            ground = 15;
                                        }
                                    } else {
                                        if (genesForText[100] == 2 && genesForText[101] == 2) {
                                            //extended patterned incomplete quail
                                            pattern = 24;
                                            ground = 15;
                                        } else {
                                            // incomplete quail
                                            pattern = 29;
                                            ground = 5;
                                        }
                                    }
                                } else {
                                    if (genesForText[26] == 1 || genesForText[27] == 1) {
                                        if (genesForText[100] == 2 && genesForText[101] == 2) {
                                            //extended traverse penciled
                                            pattern = 34;
                                            ground = 15;
                                            moorhead = 1;
                                        } else {
                                            //traverse penciled
                                            pattern = 10;
                                            ground = 5;
                                        }
                                    } else {
                                        if (genesForText[100] == 2 && genesForText[101] == 2) {
                                            //extended patterned incomplete quail
                                            pattern = 24;
                                            ground = 5;
                                            moorhead = 1;
                                        } else {
                                            // incomplete quail
                                            pattern = 29;
                                            ground = 5;
                                        }
                                    }
                                }
                            } else {
                                if (genesForText[30] == 1 || genesForText[31] == 1) {
                                    //solid black
                                    pattern = 0;
                                    ground = 15;
                                } else {
                                    if (genesForText[100] == 2 && genesForText[101] == 2) {
                                        //leaky black
                                        pattern = 18;
                                        ground = 5;
                                    } else {
                                        //birchen
                                        pattern = 1;
                                        ground = 5;
                                    }
                                }
                            }
                        }

                    } else if (genesForText[24] == 2 || genesForText[25] == 2) {
                        //duckwing tree
                        if (genesForText[28] == 1 || genesForText[29] == 1) {
                            if (genesForText[98] == 1 || genesForText[99] == 1) {
                                if (genesForText[30] == 1 || genesForText[31] == 1) {
                                    if (genesForText[26] == 1 || genesForText[27] == 1) {
                                        if (genesForText[100] == 2 && genesForText[101] == 2) {
                                            // extended patterned halfspangled
                                            pattern = 16;
                                            ground = 0;
                                        } else {
                                            //  halfspangled
                                            pattern = 25;
                                            ground = 0;
                                        }
                                    } else {
                                        //  incomplete quail
                                        pattern = 29;
                                        ground = 10;

                                    }
                                } else {
                                    if (genesForText[100] == 2 && genesForText[101] == 2) {
                                        //  moorhead columbian w/ less hackle markings
                                        pattern = 6;
                                        ground = 0;
                                        moorhead = 1;
                                    } else {
                                        //  columbian w/ less hackle markings
                                        pattern = 6;
                                        ground = 0;
                                    }
                                }
                            } else {
                                if (genesForText[30] == 1 || genesForText[31] == 1) {
                                    if (genesForText[26] == 1 || genesForText[27] == 1) {
                                        if (genesForText[100] == 2 && genesForText[101] == 2) {
                                            // extended patterned incomplete laced
                                            pattern = 27;
                                            ground = 5;
                                            moorhead = 1;
                                        } else {
                                            //  incomplete laced
                                            pattern = 27;
                                            ground = 5;
                                        }
                                    } else {
                                        //  quail
                                        pattern = 4;
                                        ground = 5;
                                    }
                                } else {
                                    if (genesForText[26] == 1 || genesForText[27] == 1) {
                                        if (genesForText[100] == 2 && genesForText[101] == 2) {
                                            // extended patterned incomplete laced?
                                            pattern = 27;
                                            ground = 15;
                                            moorhead = 1;
                                        } else {
                                            //  columbian
                                            pattern = 5;
                                            ground = 15;
                                        }
                                    } else {
                                        if (genesForText[100] == 2 && genesForText[101] == 2) {
                                            //  incomplete quail
                                            pattern = 29;
                                            ground = 15;
                                        } else {
                                            //  columbian
                                            pattern = 5;
                                            ground = 15;
                                        }
                                    }
                                }
                            }
                        } else {
                            if (genesForText[98] == 1 || genesForText[99] == 1) {
                                if (genesForText[30] == 1 || genesForText[31] == 1) {
                                    if (genesForText[26] == 1 || genesForText[27] == 1) {
                                        if (genesForText[100] == 2 && genesForText[101] == 2) {
                                            // extended patterned spangled
                                            pattern = 14;
                                            ground = 15;
                                        } else {
                                            //  spangled
                                            pattern = 16;
                                            ground = 15;
                                        }
                                    } else {
                                        //  incomplete quail
                                        pattern = 29;
                                        ground = 15;
                                    }
                                } else {
                                    if (genesForText[26] == 1 || genesForText[27] == 1) {
                                        if (genesForText[100] == 2 && genesForText[101] == 2) {
                                            // extended patterned transverse pencilled
                                            pattern = 34;
                                            ground = 5;
                                        } else {
                                            //  transverse pencilled
                                            pattern = 10;
                                            ground = 5;
                                        }
                                    } else {
                                        if (genesForText[100] == 2 && genesForText[101] == 2) {
                                            //  incomplete quail
                                            pattern = 29;
                                            ground = 15;
                                        } else {
                                            //  incomplete columbian w/ less hackle markings
                                            pattern = 30;
                                            ground = 15;

                                        }
                                    }
                                }
                            } else {
                                if (genesForText[30] == 1 || genesForText[31] == 1) {
                                    if (genesForText[26] == 1 || genesForText[27] == 1) {
                                        if (genesForText[100] == 2 && genesForText[101] == 2) {
                                            // extended patterned incomplete doublelaced
                                            pattern = 21;
                                            ground = 5;
                                            moorhead = 1;
                                        } else {
                                            //  incomplete doublelaced
                                            pattern = 21;
                                            ground = 5;
                                        }
                                    } else {
                                        //  incomplete quail
                                        pattern = 29;
                                        ground = 5;

                                    }
                                } else {
                                    if (genesForText[26] == 1 || genesForText[27] == 1) {
                                        if (genesForText[100] == 2 && genesForText[101] == 2) {
                                            // extended patterned multiple laced duckwing
                                            pattern = 26;
                                            ground = 0;
                                            moorhead = 1;
                                        } else {
                                            //  multiple laced duckwing
                                            pattern = 15;
                                            ground = 0;
                                        }
                                    } else {
                                        if (genesForText[100] == 2 && genesForText[101] == 2) {
                                            //  incomplete quail
                                            pattern = 29;
                                            ground = 0;
                                        } else {
                                            // duckwing
                                            pattern = 2;
                                            ground = 0;
                                        }
                                    }
                                }
                            }
                        }


                    } else if (genesForText[24] == 3 || genesForText[25] == 3) {
                        //wheaten tree
                        if (genesForText[28] == 1 || genesForText[29] == 1) {
                            if (genesForText[98] == 1 || genesForText[99] == 1) {
                                if (genesForText[30] == 1 || genesForText[31] == 1) {
                                    if (genesForText[26] == 1 || genesForText[27] == 1) {
                                        if (genesForText[100] == 2 && genesForText[101] == 2) {
                                            // extended patterned halfspangled
                                            pattern = 25;
                                            ground = 5;
                                        } else {
                                            //  halfspangled
                                            pattern = 25;
                                            ground = 5;
                                        }
                                    } else {
                                        // extended patterened incomplete columbian w/ less hackle markings
                                        pattern = 30;
                                        ground = 15;
                                    }
                                } else {
                                    if (genesForText[26] == 1 || genesForText[27] == 1) {
                                        if (genesForText[100] == 2 && genesForText[101] == 2) {
                                            // moorhead incomplete columbian w/ less hackle markings
                                            pattern = 351;
                                            ground = 15;
                                            moorhead = 1;
                                        } else {
                                            // nearly buff
                                            pattern = 9;
                                            ground = 15;
                                        }
                                    } else {
                                        if (genesForText[100] == 2 && genesForText[101] == 2) {
                                            // moorhead incomplete columbian w/ less hackle markings
                                            pattern = 8;
                                            ground = 15;
                                        } else {
                                            // buff
                                            pattern = 351;
                                            ground = 15;
                                        }
                                    }
                                }
                            } else {
                                if (genesForText[30] == 1 || genesForText[31] == 1) {
                                    if (genesForText[26] == 1 || genesForText[27] == 1) {
                                        if (genesForText[100] == 2 && genesForText[101] == 2) {
                                            // extended patterned incomplete laced
                                            pattern = 20;
                                            ground = 5;
                                        } else {
                                            //  incomplete laced
                                            pattern = 27;
                                            ground = 5;
                                        }
                                    } else {
                                        //  quail
                                        pattern = 4;
                                        ground = 5;
                                    }
                                } else {
                                    if (genesForText[26] == 1 || genesForText[27] == 1) {
                                        if (genesForText[100] == 2 && genesForText[101] == 2) {
                                            // extended patterned incomplete laced
                                            pattern = 20;
                                            ground = 5;
                                        } else {
                                            //  columbian
                                            pattern = 5;
                                            ground = 15;
                                        }
                                    } else {
                                        if (genesForText[100] == 2 && genesForText[101] == 2) {
                                            // extended patterned columbian
                                            pattern = 5;
                                            ground = 15;
                                            moorhead = 1;
                                        } else {
                                            // columbian
                                            pattern = 5;
                                            ground = 15;
                                        }
                                    }
                                }
                            }
                        } else {
                            if (genesForText[98] == 1 || genesForText[99] == 1) {
                                if (genesForText[30] == 1 || genesForText[31] == 1) {
                                    if (genesForText[26] == 1 || genesForText[27] == 1) {
                                        if (genesForText[100] == 2 && genesForText[101] == 2) {
                                            // extended patterned spangled
                                            pattern = 14;
                                            ground = 15;
                                        } else {
                                            // spangled
                                            pattern = 16;
                                            ground = 15;
                                        }
                                    } else {
                                        // extended patterned incomplete columbian w/ less hackle markings
                                        pattern = 31;
                                        ground = 15;
                                    }
                                } else {
                                    if (genesForText[100] == 2 && genesForText[101] == 2) {
                                        // extended patterned incomplete columbian w/ less hackle markings
                                        pattern = 30;
                                        ground = 15;
                                    } else {
                                        // incomplete columbian w/ less hackle markings
                                        pattern = 3;
                                        ground = 15;
                                    }
                                }
                            } else {
                                if (genesForText[30] == 1 || genesForText[31] == 1) {
                                    if (genesForText[26] == 1 || genesForText[27] == 1) {
                                        if (genesForText[100] == 2 && genesForText[101] == 2) {
                                            // extended patterned doublelaced
                                            pattern = 13;
                                            ground = 15;
                                            moorhead = 1;
                                        } else {
                                            // double laced
                                            pattern = 13;
                                            ground = 15;
                                        }
                                    } else {
                                        // extended patterned wheaten
                                        pattern = 28;
                                        ground = 10;
                                    }
                                } else {
                                    if (genesForText[100] == 2 && genesForText[101] == 2) {
                                        // extended patterned wheaten
                                        pattern = 28;
                                        ground = 10;
                                    } else {
                                        // wheaten
                                        pattern = 3;
                                        ground = 10;
                                    }
                                }
                            }
                        }

                    } else if (genesForText[24] == 4 || genesForText[25] == 4) {
                        //partidge tree
                        if (genesForText[28] == 1 || genesForText[29] == 1) {
                            if (genesForText[98] == 1 || genesForText[99] == 1) {
                                if (genesForText[30] == 1 || genesForText[31] == 1) {
                                    if (genesForText[26] == 1 || genesForText[27] == 1) {
                                        if (genesForText[100] == 2 && genesForText[101] == 2) {
                                            // extended patterened halfspangled
                                            pattern = 16;
                                            ground = 15;
                                            moorhead = 1;
                                        } else {
                                            // halfspangled
                                            pattern = 16;
                                            ground = 15;
                                        }
                                    } else {
                                        // extended patterened incomplete quail
                                        pattern = 24;
                                        ground = 5;
                                    }
                                } else {
                                    if (genesForText[100] == 2 && genesForText[101] == 2) {
                                        // moorhead
                                        pattern = 8;
                                        ground = 15;
                                    } else {
                                        // incomplete columbian w/ less hackle markings
                                        pattern = 30;
                                        ground = 15;
                                    }
                                }
                            } else {
                                if (genesForText[30] == 1 || genesForText[31] == 1) {
                                    if (genesForText[26] == 1 || genesForText[27] == 1) {
                                        if (genesForText[100] == 2 && genesForText[101] == 2) {
                                            // extended patterned single laced
                                            pattern = 33;
                                            ground = 15;
                                            moorhead = 1;
                                        } else {
                                            // single laced
                                            pattern = 12;
                                            ground = 15;
                                        }
                                    } else {
                                        // quail
                                        pattern = 4;
                                        ground = 5;
                                    }
                                } else {
                                    if (genesForText[26] == 1 || genesForText[27] == 1) {
                                        if (genesForText[100] == 2 && genesForText[101] == 2) {
                                            // extended patterned incomplete single laced
                                            pattern = 32;
                                            ground = 15;
                                        } else {
                                            // columbian
                                            pattern = 5;
                                            ground = 15;
                                        }
                                    } else {
                                        if (genesForText[100] == 2 && genesForText[101] == 2) {
                                            // lakenvelder
                                            pattern = 7;
                                            ground = 15;
                                        } else {
                                            // columbian
                                            pattern = 5;
                                            ground = 15;
                                        }
                                    }
                                }
                            }
                        } else {
                            if (genesForText[98] == 1 || genesForText[99] == 1) {
                                if (genesForText[30] == 1 || genesForText[31] == 1) {
                                    if (genesForText[26] == 1 || genesForText[27] == 1) {
                                        if (genesForText[100] == 2 && genesForText[101] == 2) {
                                            // extended patterned spangled
                                            pattern = 14;
                                            ground = 15;
                                        } else {
                                            // spangled
                                            pattern = 16;
                                            ground = 15;
                                        }
                                    } else {
                                        // incomplete quail
                                        pattern = 29;
                                        ground = 15;
                                    }
                                } else {
                                    if (genesForText[26] == 1 || genesForText[27] == 1) {
                                        if (genesForText[100] == 2 && genesForText[101] == 2) {
                                            // extended patterned transverse penciled
                                            pattern = 34;
                                            ground = 5;
                                        } else {
                                            // transverse penciled
                                            pattern = 10;
                                            ground = 5;
                                        }
                                    } else {
                                        if (genesForText[100] == 2 && genesForText[101] == 2) {
                                            // incomplete quail
                                            pattern = 29;
                                            ground = 15;

                                        } else {
                                            // incomplete columbian w/ less hackle markings
                                            pattern = 30;
                                            ground = 15;
                                        }
                                    }
                                }
                            } else {
                                if (genesForText[30] == 1 || genesForText[31] == 1) {
                                    if (genesForText[26] == 1 || genesForText[27] == 1) {
                                        if (genesForText[100] == 2 && genesForText[101] == 2) {
                                            // extended patterned doublelaced
                                            pattern = 13;
                                            ground = 15;
                                            moorhead = 1;
                                        } else {
                                            // doublelaced
                                            pattern = 13;
                                            ground = 15;
                                        }
                                    } else {
                                        if (genesForText[100] == 2 && genesForText[101] == 2) {
                                            // extended patterned partridge/brown halfspangled/laced? but darker head?
                                            pattern = 20;
                                            ground = 5;
                                            moorhead = 1;
                                        } else {
                                            // extended patterned partridge/brown halfspangled/laced?
                                            pattern = 20;
                                            ground = 5;
                                        }
                                    }
                                } else {
                                    if (genesForText[26] == 1 || genesForText[27] == 1) {
                                        if (genesForText[100] == 2 && genesForText[101] == 2) {
                                            // extended patterned multiple laced partridge
                                            pattern = 26;
                                            ground = 5;
                                            moorhead = 1;
                                        } else {
                                            // multiple laced partridge
                                            pattern = 15;
                                            ground = 5;
                                        }
                                    } else {
                                        if (genesForText[100] == 2 && genesForText[101] == 2) {
                                            // extended patterned partridge
                                            pattern = 2;
                                            ground = 5;
                                            moorhead = 1;
                                        } else {
                                            // partridge
                                            pattern = 2;
                                            ground = 5;
                                        }
                                    }
                                }
                            }
                        }

                    } else {
                        eyes = 0;
                    }


                    int groundMod = 0;
                    //ground colour tint
                    if (genesForText[0] == 1) {
                        //gold
                        groundMod = groundMod + 2;
                    }
                    if (genesForText[0] == 1 && (genesForText[32] == 3 && genesForText[33] == 3)) {
                        //lemon or cream but backwards
                        groundMod = groundMod + 1;
                    }
                    if (genesForText[34] == 1 || genesForText[35] == 1) {
                        //mahogany or lemon cream counter
                        groundMod = groundMod + 1;
                    }
                    if (genesForText[36] == 2 && genesForText[37] == 2) {
                        groundMod = groundMod - 1;
                    }

                    if (groundMod < 0) {
                        groundMod = 0;
                    } else if (groundMod > 4) {
                        groundMod = 4;
                    }

                    ground = ground + groundMod;

                    if (pattern <= 340) {
                        if (moorhead == 1) {
                            moorheadtoggle = 1;
                        }
                        //black pattern shade genes
                        //sets pattern to correct positioning pre:variation
                        pattern = (pattern * ptrncolours);
                        if (genesForText[38] == 1 && genesForText[39] == 1) {
                            //domwhite
                            pattern = pattern + 7;
                            moorhead = moorhead + 7;
                        } else if (genesForText[38] == 1 || genesForText[39] == 1) {
                            // spotted domwhite
                            pattern = pattern + 7;
                            moorhead = moorhead + 7;
                        } else {
                            //if chocolate
                            if (genesForText[1] == 2) {
                                //if lavender
                                if (genesForText[36] == 2 && genesForText[37] == 2) {
                                    //is a dun variety
                                    //if it is splash
                                    if (genesForText[40] == 2 && genesForText[41] == 2) {
                                        //splash dun
                                        pattern = pattern + 4;
                                        moorhead = moorhead + 4;
                                    } else {
                                        //dun
                                        pattern = pattern + 8;
                                        moorhead = moorhead + 8;
                                    }
                                } else {
                                    //is a chocolate variety
                                    if (genesForText[40] == 2 && genesForText[41] == 2) {
                                        //splash choc
                                        pattern = pattern + 5;
                                        moorhead = moorhead + 5;
                                    } else if (genesForText[40] != 1 || genesForText[41] != 1) {
                                        //dun
                                        pattern = pattern + 8;
                                        moorhead = moorhead + 8;
                                    } else {
                                        //chocolate
                                        pattern = pattern + 9;
                                        moorhead = moorhead + 9;
                                    }
                                }
                            } else {
                                //if lavender
                                if (genesForText[36] == 2 && genesForText[37] == 2) {
                                    //is a lavender variety
                                    //if it is splash
                                    if (genesForText[40] == 2 && genesForText[41] == 2) {
                                        //splash lavender
                                        pattern = pattern + 3;
                                        moorhead = moorhead + 3;
                                    } else {
                                        //lavender
                                        pattern = pattern + 6;
                                        moorhead = moorhead + 6;
                                    }
                                } else {
                                    //is a black variety
                                    if (genesForText[40] == 2 && genesForText[41] == 2) {
                                        //splash
                                        pattern = pattern + 2;
                                        moorhead = moorhead + 2;
                                    } else if (genesForText[40] == 2 || genesForText[41] == 2) {
                                        //blue
                                        if ((genesForText[26] == 1 || genesForText[27] == 1) && (genesForText[24] == 5 || genesForText[25] == 5)) {
                                            //blue laced ... super special gene combo for blue andalusian type pattern
                                            pattern = 350;
                                            if (genesForText[100] == 2 && genesForText[101] == 2) {
                                                if (genesForText[30] == 1 || genesForText[31] == 1) {
                                                    moorhead = 1;
                                                }
                                            } else {
                                                moorhead = 2;
                                            }
                                        } else {
                                            //blue
                                            pattern = pattern + 1;
                                            moorhead = moorhead + 1;
                                        }

                                    }
                                }
                            }
                        }
                    }
                    if (moorheadtoggle == 0) {
                        moorhead = 0;
                    }
                }

                //white marking genesForText
                if (genesForText[3] == 2) {
                    //Barred
                    white = 1;
                } else {
                    if (genesForText[22] == 2 && genesForText[23] == 2) {
                        //mottled
                        white = 2;
                    } else {
                        if (pattern < 10 && Melanin != 2 && (genesForText[54] != 3 && genesForText[55] != 3) && genesForText[6] == 2) {
                            //white crest
                            white = 3;
                        }
                    }
                }

                // figures out the shank, comb, and skin colour if its not albino
                if (!isAlbino) {
                    //gets comb colour
                    if (genesForText[4] == 1 && (genesForText[42] == 1 || genesForText[43] == 1)) {
                        //comb and shanks are fibro black
                        comb = -1;
                        shanks = 6;
                    } else {
                        comb = 5;
                    }
                    if (genesForText[24] == 5 || genesForText[25] == 5) {
                        shanks = 5;
//                        if (comb != 0) {
//                            comb = 5;
//                        }
//                        // makes mulbery comb
//                        if (genesForText[30] == 2) {
//                            comb = comb + 1;
//                        }
                    } else if (genesForText[24] == 1 && genesForText[25] == 1) {
                        shanks = 5;
//                        if (comb != 0) {
//                            comb = 5;
//                        }
                    } else if (genesForText[24] == 1 || genesForText[25] == 1) {
                        shanks = 5;
//                        if (comb != 0) {
//                            comb = 5;
//                        }
                    }
                    //shanks starts at 3 btw

                    if (shanks > 2) {
                        // if splash or blue lighten by 1 shade
                        if (genesForText[40] == 2 || genesForText[41] == 2) {
                            shanks--;
                        }
                    }
                    if (shanks > 2) {
                        //if barred or mottled lighten by 1 shade
                        if (genesForText[3] == 2 || (genesForText[22] == 2 && genesForText[23] == 2)) {
                            shanks--;
                        }
                    }
                    if (shanks > 2) {
                        //if lavender lighten by 1 shade
                        if ((genesForText[36] == 1 && genesForText[37] == 1)) {
                            shanks--;
                        }
                    }

                    // if Dilute is Dilute and the shanks arnt darkened by extened black lighten by 1 shade
                    if ((genesForText[24] != 5 && genesForText[25] != 5) && (genesForText[32] == 1 || genesForText[33] == 1)) {
                        shanks--;
                    }

                    // if dominant white lighten by 1 shade
                    if (genesForText[38] == 1 && genesForText[39] == 1) {
                        shanks--;
                        if (comb != 2) {
                            comb++;
                        }
                    }

                    //if its charcoal
                    if (genesForText[100] == 2 && genesForText[101] == 2) {
                        shanks++;
                        if (comb != 3)
                        comb--;
                    }

                    //lightens comb to mulberry if lightness is extreme enough
                    if (shanks < 4 && comb != 2) {
                        comb = comb + 1;
                    }

                    if (genesForText[166] == 2 && genesForText[167] == 2) {
                        //ressesive dark shanks
                        shanks = shanks + 2;
                    }

                    //makes sure its not off the chart
                    if (shanks < 0) {
                        shanks = 0;
                    } else if (shanks > 6) {
                        shanks = 6;
                    }



                    if (comb > 5) {
                        comb = 5;
                    } else if (comb < 0) {
                        comb = 0;
                    }

                    //makes the shanks and beak their white or yellow varient
                    if (genesForText[44] == 1 || genesForText[45] == 1) {
                        shanks = shanks + 5;
                    } else if (genesForText[44] == 3 && genesForText[45] == 3) {
                        shanks = shanks + 11;
                    }

                }

                //face and ear size stuff
                if (genesForText[2] == genesForText[6]) {
                    ears = 1;
                } else {
                    ears = -1;
                }

                if (genesForText[162] == 163){
                    if (genesForText[162] >= 9 && genesForText[162] <=16) {
                        ears = ears +1;
                    } else if (genesForText[162] >= 17 && genesForText[162] <=24) {
                        ears = ears + 2;
                    }
                    if ((genesForText[162] & 1) == 0) {
                        earsW = earsW + 2;
                    }
                } else {
//                    ears = ears - 1;
                    if ((genesForText[162] & 1) == 0) {
                        if ((genesForText[163] & 1) == 0) {
                            earsW = earsW + 1;
                        }
                    } else if ((genesForText[163] & 1) != 0) {
                        earsW = earsW - 1;
                    }
                }

                if (genesForText[152] <= 4 || genesForText[153] <= 4) {
//                    ears = ears - 1;
                } else {
                    if (genesForText[152] == genesForText[153]) {
                        if (genesForText[152] >= 9 || genesForText[153] >= 9) {
                            ears = ears + 1;
                        }
                    } else {
//                        ears = ears - 1;
                    }

                    if ((genesForText[152] & 1) == 0) {
                        earsW = earsW + 1;
                    }
                }

                if (genesForText[160] == genesForText[161]) {
                    if (genesForText[160] >= 9) {
                        if (genesForText[160] >= 17) {
                            ears = ears + 2;
                        } else {
                            ears = ears + 1;
                        }
                        earsW = earsW + 1;
                    }
                }

                if (((genesForText[160] & 1) == 0) && ((genesForText[161] & 1) == 0)) {
                    if (genesForText[160] == genesForText[161]) {
                        earsW = earsW + 2;
                    } else {
                        earsW = earsW + 1;
                    }
                }

                if (genesForText[82] == 1) {
                    ears = ears + 1;
                }
                if (genesForText[83] == 1) {
                    ears = ears + 1;
                }

                if (genesForText[48] == 1) {
                    ears = ears - 3;
                }
                if (genesForText[49] == 1) {
                    ears = ears - 3;
                }

                if (genesForText[56] == 1 || genesForText[57] == 1) {
                    ears = ears - 2;
                }

                if (genesForText[80] == 1 || genesForText[81] == 1) {
                    ears = ears - 1;
                }

                if (genesForText[158] == 1 || genesForText[159] == 1) {
//                    ears = ears - 1;
                    earsW = earsW - 1;
                } else if (genesForText[158] == 2 || genesForText[159] == 2) {
//                    ears = ears - 1;
                } else if (genesForText[158] == 3 || genesForText[159] == 3) {
                    if (genesForText[158] == 4 || genesForText[159] == 4) {
                        ears = ears + 1;
                        earsW = earsW + 1;
                    } else {
                        ears = ears + 1;
                    }
                } else if (genesForText[158] == 4 || genesForText[159] == 4) {
                    earsW = earsW + 1;
                } else {
                    ears = ears + 1;
                    earsW = earsW + 1;
                }


                //at max adds 2 if less than 8
                if (genesForText[154] <= 3 || genesForText[155] <= 3) {
                    ears = ears - 1;
                } else if (genesForText[154] == genesForText[155]) {
                    if (genesForText[154] >= 7) {
                        if (genesForText[154] >= 10) {
                            if (ears <= 7) {
                                ears = ears + 2;
                            } else {
                                ears = ears + 1;
                            }
                        } else {
                            ears = ears + 1;
                        }
                    }
                }



                //at max adds 2 if less than 8
                if (!(genesForText[156] <= 3 || genesForText[157] <= 3) && (genesForText[156] == genesForText[157])) {
                    if (genesForText[156] >= 6) {
                        if (genesForText[156] >= 10) {
                            if (ears <= 7) {
                                ears = ears + 2;
                            } else {
                                ears = ears + 1;
                            }
                        } else {
                            ears = ears + 1;
                        }
                    }
                }

                if (genesForText[84] == 1 || genesForText[85] == 1) {
                    ears = ears/2;
                }


                if ((genesForText[154] & 1) == 0 && (genesForText[155]) == 0) {
                    earsW = earsW + 1;
                } else if ((genesForText[154] & 1) != 0 && (genesForText[155]) != 0) {
                    earsW = earsW - 1;
                }

                if (genesForText[164] == 1 || genesForText[165] == 1) {
                    earsW = earsW - 3;
                } else if (genesForText[164] == 2 || genesForText[165] == 2) {
                    if (genesForText[164] >= 4 || genesForText[165] >= 4) {
                        earsW = earsW - 1;
                    }
                } else if (genesForText[164] == 4 || genesForText[165] == 4) {
                    if (genesForText[164] >= 5 || genesForText[165] >= 5) {
                        earsW = earsW + 1;
                    }
                } else if (genesForText[164] == genesForText[165]) {
                    earsW = earsW + 2;
                }

                if (genesForText[9] == 2) {
                    earsW = earsW/2;
                }

                if (genesForText[6] == 5) {
                    if (earsW <= 3) {
                        earsW = earsW + 1;
                    } else if (earsW >=5) {
                        earsW = earsW - 1;
                    }
                } else if (genesForText[6] == 6) {
                    earsW = earsW + 1;
                }

                // this sets ear whiteness to actual value
                if (earsW <= 2) {
                    earsW = 0;
                    //red
                } else if (earsW >= 6) {
                    earsW = 2;
                    //white
                } else {
                    earsW = 1;
                    //mottled
                }

                //ear size fixer
                if (ears < 0) {
                    ears = 0;
                } else if (ears > 9) {
                    ears = 9;
                }

                //ear colour calculation
                if (ears != 0) {
                    if (comb == 0) {
                        //face is fibro
                        if (ears >= 3) {
                            face = 7;
                        }
                        if (earsW == 2) {
                            //blue
                            if ((genesForText[44] != 1 && genesForText[45] != 1) && (genesForText[44] == 3 || genesForText[45] == 3)) {
                                //green
                                ears = ears + 110;
                            } else {
                                //blue
                                ears = ears + 80;
                            }
                        } else if (earsW == 1) {
                            //mottled black
                            ears = ears + 50;
                        } else {
                            //black
                            ears = ears + 20;
                        }
                    } else if (comb == 3) {
                        //face is moorish
                        face = 7;
                        if (earsW == 2) {
                            if ((genesForText[44] != 1 && genesForText[45] != 1) && (genesForText[44] == 3 || genesForText[45] == 3)) {
                                //yellow
                                ears = ears + 90;
                            } else {
                                //white ears
                                ears = ears + 60;
                            }
                        } else if (earsW == 1) {
                            //mottled moorish ears (black and white)
                            ears = ears + 120;
                        } else {
                                //black
                                ears = ears + 20;
                            }
                    } else if (comb == 1 || comb == 4) {
                        //face is mulberry based
                        face = 4;
                        if (earsW == 2) {
                            //ear is white
                            if ((genesForText[4] == 1) && (genesForText[42] == 1 || genesForText[43] == 1)) {
                                if ((genesForText[44] != 1 && genesForText[45] != 1) && (genesForText[44] == 3 || genesForText[45] == 3)) {
                                    //light green
                                    ears = ears + 100;
                                } else {
                                    //ear is light blue
                                    ears = ears + 70;
                                }
                            } else {
                                if ((genesForText[44] != 1 && genesForText[45] != 1) && (genesForText[44] == 3 || genesForText[45] == 3)) {
                                    //yellow
                                    ears = ears + 90;
                                } else {
                                    //white ears
                                    ears = ears + 60;
                                }
                            }
                        } else if (earsW == 1) {
                            ears = ears + 40;
                        } else {
                            //ears mulberry
                            ears = ears + 10;
                        }
                    } else {
                        //face is red
                        face = 1;
                        if (earsW == 2) {
                            if ((genesForText[44] != 1 && genesForText[45] != 1) && (genesForText[44] == 3 || genesForText[45] == 3)) {
                                //yellow
                                ears = ears + 90;
                            } else {
                                //white ears
                                ears = ears + 60;
                            }
                        } else if (earsW == 1) {
                            //mottled red ears
                            ears = ears + 10;
                        }
                    }
                }

//            after finished genesForText
                this.chickenTextures.add(CHICKEN_TEXTURES_GROUND[ground]);
                if (pattern <= 350) {
                    this.chickenTextures.add(CHICKEN_TEXTURES_PATTERN[pattern]);
                }
                if (moorhead != 0) {
                    this.chickenTextures.add(CHICKEN_TEXTURES_MOORHEAD[moorhead]);
                }
                if (white != 0) {
                    this.chickenTextures.add(CHICKEN_TEXTURES_WHITE[white]);
                }
                this.chickenTextures.add(CHICKEN_TEXTURES_SHANKS[shanks]);
                if (face >= 1) {
                    this.chickenTextures.add(CHICKEN_TEXTURES_FACE[face]);
                }
                if (ears >= 1) {
                    this.chickenTextures.add(CHICKEN_TEXTURES_EARS[ears]);
                }
                this.chickenTextures.add(CHICKEN_TEXTURES_COMB[comb]);
                this.chickenTextures.add(CHICKEN_TEXTURES_EYES[eyes]);
            }else{
                int shanks = 4;
                int comb = 2;
                int eyes = 1;
                int downBase = 1;
                int red = 0;
                int black = 0;
                int white = 0;


                boolean isAlbino = false;

                if (genesForText[20] != 1 && genesForText[21] != 1) {
                    downBase = 0;
                    if (genesForText[20] == 3 && genesForText[21] == 3) {
                        //albino
                        shanks = 6;
                        comb = 2;
                        eyes = 0;
                        isAlbino = true;
                    }
                }

                // figures out the shank, comb, and skin colour if its not albino
                if (!isAlbino) {
                    //gets comb colour
                    if (genesForText[4] == 1 && (genesForText[42] == 1 || genesForText[43] == 1)) {
                        //comb and shanks are black
                        comb = 0;
                        shanks = 3;
                    }
                    if (genesForText[24] == 1 && genesForText[25] == 1) {
                        shanks = 3;
                        // makes mulbery comb
                        if (genesForText[30] == 2) {
                            comb = 1;
                        }
                    }
                    //shanks starts at 3 btw
                    // if Dilute is Dilute and the shanks arnt darkened by extened black lighten by 1 shade
                    if ((genesForText[24] != 1 && genesForText[25] != 1) && (genesForText[32] == 1 || genesForText[33] == 1)) {
                        shanks--;
                    }

                    //if barred or mottled lighten by 1 shade
                    if (genesForText[3] == 2 || (genesForText[22] == 2 && genesForText[23] == 2)) {
                        shanks--;
                    }

                    // if dominant white or lavender lighten by 1 shade
                    if ((genesForText[38] == 1 && genesForText[39] == 1) || (genesForText[36] == 1 && genesForText[37] == 1)) {
                        shanks--;
                    }

                    // if splash or blue lighten by 1 shade
                    if (genesForText[40] == 2 || genesForText[41] == 2) {
                        shanks--;
                    }

                    //if its melanized
//                    if (Melanin == 2) {
//                        shanks++;
//                    }

                    if (genesForText[166] == 2 && genesForText[167] == 2) {
                        shanks++;
                    }

                    //makes sure its not off the chart
                    if (shanks < 0) {
                        shanks = 0;
                    } else if (shanks > 3) {
                        shanks = 3;
                    }

                    //lightens comb to mulberry if lightness is extreme enough
                    if (shanks < 2 && comb == 0) {
                        comb = 1;
                    }

                    //makes the shanks and beak their white or yellow varient
                    if (genesForText[44] == 1 || genesForText[45] == 1) {
                        shanks = shanks + 4;
                    }

                    if (downBase != 0) {

                        if (genesForText[0] == 2) {
                            downBase = 0;
                        }

                        if (genesForText[24] == 5 || genesForText[25] == 5) {
                            // is black
                            if ((genesForText[30] == 1 || genesForText[31] == 1) && (genesForText[100] == 2 && genesForText[101] == 2) && (genesForText[28] != 1 && genesForText[28] != 1)) {
                                black = 13;
                            } else {
                                black = 7;
                            }

                        } else if (genesForText[24] == 1 || genesForText[25] == 1) {
                            //is birchen
                            if (((genesForText[30] == 1 || genesForText[31] == 1) || (genesForText[100] == 2 && genesForText[101] == 2)) && (genesForText[28] != 1 && genesForText[28] != 1)) {
                                black = 13;
                            } else {
                                black = 7;
                            }
                        } else if (genesForText[24] == 2 || genesForText[25] == 2) {
                            //is duckwing
                            red = 3;
                            black = 1;
                        } else if (genesForText[24] == 3 || genesForText[25] == 3) {
                            //is wheaten
                            if (genesForText[34] == 1 && genesForText[35] == 1) {
                                red = 1;
                            } else if (genesForText[34] == 1 || genesForText[35] == 1) {
                                red = 2;
                            }
                        } else {
                            //is partridge
                            red = 2;
                            black = 1;
                        }

                        //white marking genesForText
                        if (genesForText[3] == 2) {
                            //Barred
                            white = 2;
                        } else {
                            if (genesForText[22] == 2 && genesForText[23] == 2) {
                                //mottled
                                white = 1;
                            }
                        }

                        if (black != 0) {
                            if ((genesForText[38] == 1 || genesForText[39] == 1) || (genesForText[40] == 2 && genesForText[41] == 2)) {
                                //white or splash
                                black = black + 1;
                            } else if (genesForText[36] == 2 && genesForText[37] == 2) {
                                if (genesForText[1] == 2) {
                                    //dun
                                    black = black + 5;
                                } else {
                                    //lav
                                    black = black + 2;
                                }
                            } else if (genesForText[40] == 2 || genesForText[41] == 2) {
                                if (genesForText[1] == 2) {
                                    //dun
                                    black = black + 5;
                                } else {
                                    //blue
                                    black = black + 3;
                                }
                            } else if (genesForText[1] == 2) {
                                //choc
                                black = black + 4;
                            }

                        }
                    }
                }

                this.chickenTextures.add(CHICKEN_TEXTURES_CHICKBASE[downBase]);
                if (red != 0) {
                    this.chickenTextures.add(CHICKEN_TEXTURES_CHICKRED[red]);
                }
                if (black != 0) {
                    this.chickenTextures.add(CHICKEN_TEXTURES_CHICKBLACK[black]);
                }
                if (white != 0) {
                    this.chickenTextures.add(CHICKEN_TEXTURES_CHICKWHITE[white]);
                }
                this.chickenTextures.add(CHICKEN_TEXTURES_SHANKS[shanks]);
                this.chickenTextures.add(CHICKEN_TEXTURES_COMB[comb]);
                this.chickenTextures.add(CHICKEN_TEXTURES_EYES[eyes]);
            }
        }
    }


    @Override
    @Nullable
    protected ResourceLocation getLootTable() {

        if (!this.world.isRemote) {

            float bodyType;

            if (genes[146] == 2 && genes[147] == 2) {
                if (genes[148] == 2 && genes[149] == 2) {
                    //normal body
                    bodyType = 0;
                } else {
                    //big body
                    bodyType = 0.18F;
                }
            } else if (genes[148] == 2 && genes[149] == 2) {
                if (genes[146] == 2 || genes[147] == 2) {
                    //normal body
                    bodyType = 0;
                } else {
                    //small body
                    bodyType = -0.18F;
                }
            } else {
                //normal body
                bodyType = 0;
            }

            if (genes[4] == 1 && genes[20] != 3 && genes[21] != 3 && (genes[42] == 1 || genes[43] == 1)) {

                if (chickenSize + bodyType <= 0.7F) {
                    dropMeatType = "rawchicken_darksmall";
                } else if (chickenSize + bodyType >= 0.9F) {
                    dropMeatType = "rawchicken_darkbig";
                } else {
                    dropMeatType = "rawchicken_dark";
                }

            } else {

                if (chickenSize + bodyType <= 0.7F) {
                    dropMeatType = "rawchicken_palesmall";
                } else if (chickenSize + bodyType >= 0.9F) {
                    dropMeatType = "rawchicken";
                } else {
                    dropMeatType = "rawchicken_pale";
                }
            }
        }

        return new ResourceLocation(Reference.MODID, "enhanced_chicken");
    }

    public String getDropMeatType() {
        return dropMeatType;
    }


    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);

        //store this chicken's genes
        ListNBT geneList = new ListNBT();
        for(int i = 0; i< genes.length; i++){
            CompoundNBT nbttagcompound = new CompoundNBT();
            nbttagcompound.putInt("Gene", genes[i]);
            geneList.add(nbttagcompound);
        }
        compound.put("Genes", geneList);

        //store this chicken's mate's genes
        ListNBT mateGeneList = new ListNBT();
        for(int i = 0; i< mateGenes.length; i++){
            CompoundNBT nbttagcompound = new CompoundNBT();
            nbttagcompound.putInt("Gene", mateGenes[i]);
            mateGeneList.add(nbttagcompound);
        }
        compound.put("FatherGenes", mateGeneList);

        compound.putInt("Hunger", hunger);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);

        ListNBT geneList = compound.getList("Genes", 10);
        for (int i = 0; i < geneList.size(); ++i) {
            CompoundNBT nbttagcompound = geneList.getCompound(i);
            int gene = nbttagcompound.getInt("Gene");
            genes[i] = gene;
        }

        ListNBT mateGeneList = compound.getList("FatherGenes", 10);
        for (int i = 0; i < mateGeneList.size(); ++i) {
            CompoundNBT nbttagcompound = mateGeneList.getCompound(i);
            int gene = nbttagcompound.getInt("Gene");
            mateGenes[i] = gene;
        }

        hunger = compound.getInt("Hunger");

        for (int i = 0; i < genes.length; i++) {
            if (genes[i] == 0) {
                genes[i] = 1;
            }
        }
        if (mateGenes[0] != 0) {
            for (int i = 0; i < mateGenes.length; i++) {
                if (mateGenes[i] == 0) {
                    mateGenes[i] = 1;
                }
            }
        }

        setSharedGenes(genes);
        setChickenSize();

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
        //part one of attaching pea comb and blue eggs
        if (this.rand.nextInt(100) <= 97) {
            boolean mateOddOrEven = rand.nextBoolean();
            if (mateOddOrEven) {
                mitosis[48] = parentGenes[49];
                mitosis[49] = parentGenes[48];
                mitosis[62] = parentGenes[63];
                mitosis[63] = parentGenes[62];
            } else {
                mitosis[48] = parentGenes[48];
                mitosis[49] = parentGenes[49];
                mitosis[62] = parentGenes[62];
                mitosis[63] = parentGenes[63];
            }
        }
        int testInt = 0;
    }


    public int[] getEggGenes(int[] parentGenes, int[] mateParentGenes, int[] mitosis, int[] mateMitosis, boolean ignoreInfertility){
        if (!infertile() || ignoreInfertility) {
            Random rand = new Random();
            int[] eggGenes = new int[Reference.CHICKEN_GENES_LENGTH];

            for(int i =0; i< 20; i++) {
                boolean thisOrMate = rand.nextBoolean();
                if (thisOrMate){
                    eggGenes[i] = parentGenes[i];
                } else {
                    eggGenes[i] = mateParentGenes[i];
                }
            }

            for(int i =20; i< genes.length; i = (i+2)) {
                boolean thisOrMate = rand.nextBoolean();
                if (thisOrMate){
                    eggGenes[i] = mitosis[i];
                    eggGenes[i+1] = mateMitosis[i+1];
                } else {
                    eggGenes[i] = mateMitosis[i];
                    eggGenes[i+1] = mitosis[i+1];
                }
            }

            //part two of attaching pea comb and blue eggs
            if (this.rand.nextInt(100) <= 97) {
                boolean thisOrMate = rand.nextBoolean();
                if (thisOrMate){
                    eggGenes[48] = mitosis[48];
                    eggGenes[62] = mitosis[62];
                    eggGenes[49] = mateMitosis[49];
                    eggGenes[63] = mateMitosis[63];
                } else {
                    eggGenes[48] = mateMitosis[48];
                    eggGenes[62] = mateMitosis[62];
                    eggGenes[49] = mitosis[49];
                    eggGenes[63] = mitosis[63];
                }
            }

            return eggGenes;
        } else {
            return null;
        }
    }

    private boolean infertile() {
        if (mateGenes == null || mateGenes.length == 0) {
            return true;
        }
        for (int i = 0; i< mateGenes.length; i++) {
            if (mateGenes[i] == 0) {
                return true;
            }
        }
        return false;
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld inWorld, DifficultyInstance difficulty, SpawnReason spawnReason, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT itemNbt) {
        livingdata = super.onInitialSpawn(inWorld, difficulty, spawnReason, livingdata, itemNbt);
        int[] spawnGenes;

        if (livingdata instanceof EnhancedChicken.GroupData) {
            int[] spawnGenes1 = ((GroupData) livingdata).groupGenes;
            int[] mitosis = new int[Reference.CHICKEN_GENES_LENGTH];
            punnetSquare(mitosis, spawnGenes1);

            int[] spawnGenes2 = ((GroupData) livingdata).groupGenes;
            int[] mateMitosis = new int[Reference.CHICKEN_GENES_LENGTH];
            punnetSquare(mateMitosis, spawnGenes2);
            spawnGenes = getEggGenes(spawnGenes1, spawnGenes2, mitosis, mateMitosis, true);
        } else {
            spawnGenes = createInitialGenes(inWorld);
            livingdata = new GroupData(spawnGenes);
        }

        this.genes = spawnGenes;
        setSharedGenes(genes);
        setChickenSize();
        return livingdata;
    }

    private int[] createInitialGenes(IWorld inWorld) {
        int[] initialGenes = new int[Reference.CHICKEN_GENES_LENGTH];

            //[ 0=minecraft wildtype, 1=jungle wildtype, 2=savanna wildtype, 3=cold wildtype, 4=swamp wildtype ]
            int wildType = 0;
            Biome biome = inWorld.getBiome(new BlockPos(this));

            if (biome.getDefaultTemperature() >= 0.9F && biome.getDownfall() > 0.8F) // hot and wet (jungle)
            {
                wildType  = 1;
            }
            else if (biome.getDefaultTemperature() >= 0.9F && biome.getDownfall() < 0.3F) // hot and dry (savanna)
            {
                wildType = 2;
            }
            else if (biome.getDefaultTemperature() < 0.3F ) // cold (mountains)
            {
                wildType = 3;
            }
            else if (biome.getDefaultTemperature() >= 0.8F && biome.getDownfall() > 0.8F)
            {
                wildType = 4;
            }

/**
 * parent linked genes
 */


if (false){
    //THE DNA TESTER-5069 !!!!!
    return new int[] {1,1,3,1,1,1,1,1,1,1,10,10,10,10,10,10,10,10,10,10,1,1,1,1,4,4,1,1,1,1,1,1,3,3,2,2,1,1,2,2,1,1,2,2,3,3,2,2,2,2,1,1,2,2,3,3,2,2,3,3,2,2,2,2,3,3,3,3,1,1,1,1,1,1,3,3,2,2,2,1,2,2,2,2,2,2,2,2,1,1,2,2,1,1,1,1,1,1,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,1,1,1,1,5,5,5,5,11,11,3,3,5,5,21,21,2,2,1,1,1,1

    };

}else {
    //Gold [ gold, silver ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[0] = (ThreadLocalRandom.current().nextInt(2) + 1);

    } else {
        if (wildType == 3) {
            //cold biome silver variation
            initialGenes[0] = (2);
        } else {
            initialGenes[0] = (1);
        }
    }

    //Chocolate [ wildtype, chocolate ]
    if (ThreadLocalRandom.current().nextInt(100) > (WTC + ((100 - WTC) / 1.2))) {
        initialGenes[1] = (ThreadLocalRandom.current().nextInt(2) + 1);

    } else {
        initialGenes[1] = (1);
    }

    //ear size setting gene
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[2] = (ThreadLocalRandom.current().nextInt(6) + 1);
    } else {
        initialGenes[2] = (1);
    }

    //Barred [ wildtype, barred ] //exclusive to savanna
    if (ThreadLocalRandom.current().nextInt(100) > WTC && wildType == 2) {
        initialGenes[3] = (ThreadLocalRandom.current().nextInt(2) + 1);
    } else {
        initialGenes[3] = (1);
    }

    //Fibromelanin Suppressor [ wildtype, suppressor ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[4] = (ThreadLocalRandom.current().nextInt(2) + 1);
    } else {
        initialGenes[4] = (1);
    }

    //Brown egg gene suppressor [ wildtype, suppressor ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[5] = (ThreadLocalRandom.current().nextInt(2) + 1);

    } else {
        initialGenes[5] = (1);
    }

    //white face
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[6] = (ThreadLocalRandom.current().nextInt(6) + 1);

    } else {
        initialGenes[6] = (6);
    }

    //dwarf [ normal, slight dwarf ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[7] = (ThreadLocalRandom.current().nextInt(3) + 1);

    } else {
        initialGenes[7] = (1);
    }

    //dwarf 2 [ normal, very dwarf ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[8] = (ThreadLocalRandom.current().nextInt(3) + 1);

    } else {
        initialGenes[8] = (1);
    }

    //large ear inhibitor [ no inhibitor, halving inhibitor ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[9] = (ThreadLocalRandom.current().nextInt(3) + 1);

    } else {
        initialGenes[9] = (1);
    }

/**
 * unused parent linked genes
 */

    for (int i = 10; i < 20; i++) {
        initialGenes[i] = (10);
    }

/**
 * normal genes start with 20
 */

    //Recessive white [ wild, recessive white, albino ]  //mutation common in temperate areas and swamps
    if (ThreadLocalRandom.current().nextInt(100) > WTC || wildType == 4) {
        if (ThreadLocalRandom.current().nextInt(200) > 199) {
            initialGenes[20] = (3);
        } else {
            initialGenes[20] = (ThreadLocalRandom.current().nextInt(2) + 1);
        }
    } else {
        if (wildType == 0) {
            initialGenes[20] = (2);
        } else {
            initialGenes[20] = (1);
        }
    }
    if (ThreadLocalRandom.current().nextInt(100) > WTC || wildType == 4) {
        if (ThreadLocalRandom.current().nextInt(200) > 199) {
            initialGenes[21] = (3);
        } else {
            initialGenes[21] = (ThreadLocalRandom.current().nextInt(2) + 1);
        }
    } else {
        if (wildType == 0) {
            initialGenes[21] = (2);
        } else {
            initialGenes[21] = (1);
        }
    }

    //Mottled [ wildtype, mottled ]  // cold biome exclusive
    if (ThreadLocalRandom.current().nextInt(100) > WTC && wildType == 3) {
        initialGenes[22] = (ThreadLocalRandom.current().nextInt(2) + 1);
    } else {
        initialGenes[22] = (1);
    }
    if (ThreadLocalRandom.current().nextInt(100) > WTC && wildType == 3) {
        initialGenes[23] = (ThreadLocalRandom.current().nextInt(2) + 1);
    } else {
        initialGenes[23] = (1);
    }

    //Dlocus [ birchen, duckwing, wheaten, partridge, extended black ]
    //swamps have random Dlocus genes
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[24] = (ThreadLocalRandom.current().nextInt(5) + 1);
    } else {
        // swamps have a mixture but no black
        if (wildType == 4) {
            initialGenes[24] = (ThreadLocalRandom.current().nextInt(3) + 2);
        }
        // partridge is savanna wild type
        else if (wildType == 2) {
            initialGenes[24] = (4);
            // birchen and extended black is cold biome wildtype
        } else if (wildType == 3) {
            if (ThreadLocalRandom.current().nextInt(3) == 0) {
                initialGenes[24] = (5);
            } else {
                initialGenes[24] = (1);
            }
            // duckwing is jungle "true" wildtype
        } else {
            initialGenes[24] = (2);
        }
    }
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[25] = (ThreadLocalRandom.current().nextInt(4) + 1);
    } else {
        // swamps have a mixture but no black
        if (wildType == 4) {
            initialGenes[25] = (ThreadLocalRandom.current().nextInt(3) + 2);
        }
        // partridge is savanna wild type
        else if (wildType == 2) {
            initialGenes[25] = (4);
            // birchen is cold biome wildtype
        } else if (wildType == 3) {
            if (ThreadLocalRandom.current().nextInt(3) == 0) {
                initialGenes[25] = (5);
            } else {
                initialGenes[25] = (1);
            }
            // duckwing is jungle "true" wildtype
        } else {
            initialGenes[25] = (2);
        }
    }

    //Pattern Gene [ pattern, wildtype ] pattern gene is common in savannas
    if (wildType == 2) {
        if (ThreadLocalRandom.current().nextInt(100) > (WTC / 2)) {
            initialGenes[26] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            initialGenes[26] = (2);
        }
    } else {
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[26] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[26] = (2);
        }
    }
    if (wildType == 2) {
        if (ThreadLocalRandom.current().nextInt(100) > (WTC / 2)) {
            initialGenes[27] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            initialGenes[27] = (2);
        }
    } else {
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[27] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            initialGenes[27] = (2);
        }
    }


    //Colombian [ colombian, wildtype ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC || wildType == 3) {
        initialGenes[28] = (ThreadLocalRandom.current().nextInt(2) + 1);

    } else {
        initialGenes[28] = (3);
    }
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[29] = (ThreadLocalRandom.current().nextInt(2) + 1);

    } else {
        initialGenes[29] = (3);
    }

    //Melanized [melanized, wildtype ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[30] = (ThreadLocalRandom.current().nextInt(2) + 1);

    } else {
        initialGenes[30] = (3);
    }
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[31] = (ThreadLocalRandom.current().nextInt(2) + 1);

    } else {
        initialGenes[31] = (3);
    }

    //Dilute [ dilute, cream, wildtype ] // more common in swamps
    if (ThreadLocalRandom.current().nextInt(100) > WTC || wildType == 4) {
        initialGenes[32] = (ThreadLocalRandom.current().nextInt(3) + 1);

    } else {
        initialGenes[32] = (3);
    }
    if (ThreadLocalRandom.current().nextInt(100) > WTC || wildType == 4) {
        initialGenes[33] = (ThreadLocalRandom.current().nextInt(3) + 1);

    } else {
        initialGenes[33] = (3);
    }

    //Mahogany [ mahogany, wildtype ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[34] = (ThreadLocalRandom.current().nextInt(2) + 1);

    } else {
        if (wildType == 2) {
            initialGenes[34] = (1);
        } else {
            initialGenes[34] = (2);
        }
    }
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[35] = (ThreadLocalRandom.current().nextInt(2) + 1);

    } else {
        initialGenes[35] = (2);
    }

    //Lavender [ wildtype, lavender ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[36] = (ThreadLocalRandom.current().nextInt(2) + 1);

    } else {
        initialGenes[36] = (1);
    }
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[37] = (ThreadLocalRandom.current().nextInt(2) + 1);

    } else {
        initialGenes[37] = (1);
    }

    //Dominant White [ dominant white, wildtype ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC || (wildType == 3)) {
        initialGenes[38] = (ThreadLocalRandom.current().nextInt(2) + 1);
    } else {
        initialGenes[38] = (2);
    }
    if (ThreadLocalRandom.current().nextInt(100) > WTC || (wildType == 3)) {
        initialGenes[39] = (ThreadLocalRandom.current().nextInt(2) + 1);
    } else {
        initialGenes[39] = (2);
    }

    //Splash [ black, splash ]
    if (ThreadLocalRandom.current().nextInt(100) > (WTC + ((100 - WTC) / 2))) {
        initialGenes[40] = (ThreadLocalRandom.current().nextInt(2) + 1);

    } else {
        initialGenes[40] = (1);
    }
    if (ThreadLocalRandom.current().nextInt(100) > (WTC + ((100 - WTC) / 2))) {
        initialGenes[41] = (ThreadLocalRandom.current().nextInt(2) + 1);

    } else {
        initialGenes[41] = (1);
    }

    //Fibromelanin [ fibromelanin, wildtype ] // fibro is more common in savannas but still rare
    if (wildType == 2) {
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[42] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            initialGenes[42] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[43] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            initialGenes[43] = (2);
        }
    } else {
        if (ThreadLocalRandom.current().nextInt(100) > (WTC + ((100 - WTC) / 1.1))) {
            initialGenes[42] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            initialGenes[42] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > (WTC + ((100 - WTC) / 1.1))) {
            initialGenes[43] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            initialGenes[43] = (2);
        }
    }

    //yellow shanks [ white, yellow, superyellow ]
    if ((ThreadLocalRandom.current().nextInt(100) > (WTC + ((100 - WTC) / 2)) && wildType != 0) || wildType == 4) {
        initialGenes[44] = (ThreadLocalRandom.current().nextInt(3) + 1);

    } else {
        if (wildType == 1) {
            initialGenes[44] = (1);
        } else {
            initialGenes[44] = (2);
        }
    }       //homozygous white legs only in jungle
    if (ThreadLocalRandom.current().nextInt(100) > (WTC + ((100 - WTC) / 2))) {
        if (wildType == 1) {
            initialGenes[45] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            initialGenes[45] = (ThreadLocalRandom.current().nextInt(2) + 2);
        }
    } else {
        if (wildType == 1) {
            initialGenes[45] = (1);
        } else {
            initialGenes[45] = (2);
        }
    }

    //Rose [ rose, rose2, wildtype ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[46] = (ThreadLocalRandom.current().nextInt(3) + 1);

    } else {
        initialGenes[46] = (3);
    }
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[47] = (ThreadLocalRandom.current().nextInt(2) + 1);

    } else {
        initialGenes[47] = (3);
    }

    //Pea [ pea, wildtype ]
    if ((ThreadLocalRandom.current().nextInt(100) > WTC && (wildType == 0 || wildType == 3))) {
        initialGenes[48] = (ThreadLocalRandom.current().nextInt(2) + 1);

    } else {
        if (wildType == 3) {
            initialGenes[48] = (1);
        } else {
            initialGenes[48] = (2);
        }
    }
    if ((ThreadLocalRandom.current().nextInt(100) > WTC && (wildType == 0 || wildType == 3))) {
        initialGenes[49] = (ThreadLocalRandom.current().nextInt(2) + 1);
    } else {
        if (wildType == 3) {
            initialGenes[49] = (1);
        } else {
            initialGenes[49] = (2);
        }
    }

    //Duplex comb or v comb [ wildtype, duplexV, duplexC ]   // reversed dominance, cold biome exclusive
    if ((ThreadLocalRandom.current().nextInt(100) > WTC) && wildType == 3) {
        initialGenes[50] = (ThreadLocalRandom.current().nextInt(2) + 1);
    } else {
        initialGenes[50] = (1);
    }
    if ((ThreadLocalRandom.current().nextInt(100) > WTC) && wildType == 3) {
        initialGenes[51] = (ThreadLocalRandom.current().nextInt(2) + 1);
    } else {
        initialGenes[51] = (1);
    }

    //Naked neck [ naked neck, wildtype ] // savanna exclusive
    if (ThreadLocalRandom.current().nextInt(100) > WTC && wildType == 2) {
        initialGenes[52] = (ThreadLocalRandom.current().nextInt(2) + 1);
    } else {
        initialGenes[52] = (2);
    }
    //no wild homozygous naked neck
    initialGenes[53] = (2);


    //Crest [ normal crest, forward crest, wildtype ]
    if (wildType == 3 && ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[54] = (ThreadLocalRandom.current().nextInt(3) + 1);
    } else {
        initialGenes[54] = (3);
    }
    if (wildType == 3 && ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[55] = (ThreadLocalRandom.current().nextInt(3) + 1);
    } else {
        initialGenes[55] = (3);
    }

    //beard [ beard, wildtype ]
    if (wildType == 3 && ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[56] = (ThreadLocalRandom.current().nextInt(2) + 1);

    } else {
        initialGenes[56] = (2);
    }
    if (wildType == 3 && ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[57] = (ThreadLocalRandom.current().nextInt(2) + 1);

    } else {
        initialGenes[57] = (2);
    }

    //Foot feather 1 [ small foot feather, big foot feather, wildtype ]
    if (wildType == 3 && ThreadLocalRandom.current().nextInt(100) > (WTC / 2)) {
        initialGenes[58] = (ThreadLocalRandom.current().nextInt(3) + 1);
    } else {
        initialGenes[58] = (3);
    }
    if (wildType == 3 && ThreadLocalRandom.current().nextInt(100) > (WTC / 2)) {
        initialGenes[59] = (ThreadLocalRandom.current().nextInt(3) + 1);
    } else {
        initialGenes[59] = (3);
    }

    //Foot feather enhancer [ enhancer, wildtype ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC || wildType == 3) {
        initialGenes[60] = (ThreadLocalRandom.current().nextInt(2) + 1);

    } else {
        initialGenes[60] = (2);
    }
    if (ThreadLocalRandom.current().nextInt(100) > WTC || wildType == 3) {
        initialGenes[61] = (ThreadLocalRandom.current().nextInt(2) + 1);

    } else {
        initialGenes[61] = (2);
    }

    //Blue eggs [ blue, wildtype ] // swamp exclusive
    if (wildType == 4) {
        initialGenes[62] = (ThreadLocalRandom.current().nextInt(2) + 1);

    } else {
        initialGenes[62] = (2);
    }
    if (wildType == 4) {
        initialGenes[63] = (ThreadLocalRandom.current().nextInt(2) + 1);

    } else {
        initialGenes[63] = (2);
    }

    //Brown Pink eggs [ brown, pink, wildtype ] //pink more likely in savanna
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[64] = (ThreadLocalRandom.current().nextInt(3) + 1);

    } else {
        if (wildType == 2) {
            initialGenes[64] = (2);
        } else {
            initialGenes[64] = (3);
        }
    }
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[65] = (ThreadLocalRandom.current().nextInt(3) + 1);

    } else {
        if (wildType == 2) {
            initialGenes[65] = (2);
        } else {
            initialGenes[65] = (3);
        }
    }

    //Brown Cream eggs [ brown, cream, wildtype ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC || wildType == 4) {
        initialGenes[66] = (ThreadLocalRandom.current().nextInt(3) + 1);

    } else {
        if (wildType == 1 || wildType == 2) {
            initialGenes[66] = (3);
        } else {
            initialGenes[66] = (2);
        }
    }
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[67] = (ThreadLocalRandom.current().nextInt(3) + 1);

    } else {
        if (wildType == 1) {
            initialGenes[67] = (3);
        } else {
            initialGenes[67] = (2);
        }
    }

    //Darker eggs [ darker, wildtype ] // darker is more probable in swamps but still rare
    if (wildType == 4) {
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[68] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            initialGenes[68] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[69] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            initialGenes[69] = (2);
        }
    } else {
        if (ThreadLocalRandom.current().nextInt(100) > (WTC + ((100 - WTC) / 2))) {
            initialGenes[68] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[68] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > (WTC + ((100 - WTC) / 2))) {
            initialGenes[69] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[69] = (2);
        }
    }

    //creeper gene [ wildtype, creeper ] (short legs not exploding bushes)
    if (ThreadLocalRandom.current().nextInt(100) > (WTC + ((100 - WTC) / 2))) {
        initialGenes[70] = (ThreadLocalRandom.current().nextInt(2) + 1);
        initialGenes[71] = (1);
    } else {
        initialGenes[70] = (1);
        initialGenes[71] = (1);
    }

    //rumpless [ wildtype, rumpless ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[72] = (ThreadLocalRandom.current().nextInt(2) + 1);
        initialGenes[73] = (1);
    } else {
        initialGenes[72] = (1);
        initialGenes[73] = (1);
    }

    //base size [ smaller, wildtype, larger ] incomplete dominant
    if (ThreadLocalRandom.current().nextInt(100) > WTC / 4) {
        initialGenes[74] = (ThreadLocalRandom.current().nextInt(3) + 1);
    } else {
        initialGenes[74] = (2);
    }

    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[75] = (ThreadLocalRandom.current().nextInt(3) + 1);
    } else {
        initialGenes[75] = (2);
    }

    //Size subtraction [ smaller, normal+, smallest ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[76] = (ThreadLocalRandom.current().nextInt(3) + 1);
    } else {
        initialGenes[76] = (2);
    }

    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[77] = (ThreadLocalRandom.current().nextInt(3) + 1);
    } else {
        initialGenes[77] = (2);
    }

    //Size multiplier [ normal+, larger ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[78] = (ThreadLocalRandom.current().nextInt(2) + 1);
        initialGenes[79] = (1);
    } else {
        initialGenes[78] = (1);
        initialGenes[79] = (1);
    }

    //small comb [ small, normal+ ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[80] = (ThreadLocalRandom.current().nextInt(2) + 1);
        initialGenes[81] = (2);
    } else {
        initialGenes[80] = (2);
        initialGenes[81] = (2);
    }

    //large comb [ large, normal+ ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[82] = (ThreadLocalRandom.current().nextInt(2) + 1);
        initialGenes[83] = (2);
    } else {
        initialGenes[82] = (2);
        initialGenes[83] = (2);
    }

    //waddle reducer [ small, normal+ ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[84] = (ThreadLocalRandom.current().nextInt(2) + 1);
        initialGenes[85] = (2);
    } else {
        initialGenes[84] = (2);
        initialGenes[85] = (2);
    }

    //wing placement near back [ centered+, up on back, centered2 ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[86] = (ThreadLocalRandom.current().nextInt(3) + 1);
    } else {
        if (wildType == 1) {
            initialGenes[86] = (1);
        } else if (wildType == 2) {
            initialGenes[86] = (3);
        } else {
            initialGenes[86] = (2);
        }
    }
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[87] = (ThreadLocalRandom.current().nextInt(3) + 1);
    } else {
        if (wildType == 1) {
            initialGenes[87] = (1);
        } else if (wildType == 2) {
            initialGenes[87] = (3);
        } else {
            initialGenes[87] = (2);
        }
    }

    //wings down [ centered+, tilted down, pointed down ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[88] = (ThreadLocalRandom.current().nextInt(3) + 1);
        initialGenes[89] = (1);
    } else {
        initialGenes[88] = (1);
        initialGenes[89] = (1);
    }

    //wing length [ normal+, 5 short ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[90] = (ThreadLocalRandom.current().nextInt(2) + 1);
        initialGenes[91] = (1);
    } else {
        initialGenes[90] = (1);
        initialGenes[91] = (1);
    }

    //wing thickness [ normal+, 3 wide ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[92] = (ThreadLocalRandom.current().nextInt(2) + 1);
        initialGenes[93] = (1);
    } else {
        initialGenes[92] = (1);
        initialGenes[93] = (1);
    }

    //wing angle multiplier [none+, 1.1, 1.5]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[94] = (ThreadLocalRandom.current().nextInt(3) + 1);
    } else {
        initialGenes[94] = (1);
    }

    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[95] = (ThreadLocalRandom.current().nextInt(3) + 1);
    } else {
        initialGenes[95] = (1);
    }

    //wing angle multiplier 2 [none+, 1.1, 1.5]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[96] = (ThreadLocalRandom.current().nextInt(3) + 1);
    } else {
        initialGenes[96] = (1);
    }

    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[97] = (ThreadLocalRandom.current().nextInt(3) + 1);
    } else {
        initialGenes[97] = (1);
    }

    // Darkbrown [ darkbrown, wildtype ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[98] = (ThreadLocalRandom.current().nextInt(2) + 1);
    } else {
        initialGenes[98] = (2);
    }

    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[99] = (ThreadLocalRandom.current().nextInt(2) + 1);
    } else {
        initialGenes[99] = (2);
    }

    // Charcoal [ wildtype, charcoal ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[100] = (ThreadLocalRandom.current().nextInt(2) + 1);
    } else {
        initialGenes[100] = (1);
    }

    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[101] = (ThreadLocalRandom.current().nextInt(2) + 1);
    } else {
        initialGenes[101] = (1);
    }

    // Vulture Hocks [ wildtype, vulture hocks ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[102] = (ThreadLocalRandom.current().nextInt(2) + 1);
    } else {
        initialGenes[102] = (1);
    }

    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[103] = (ThreadLocalRandom.current().nextInt(2) + 1);
    } else {
        initialGenes[103] = (1);
    }

    // Frizzle [ wildtype, frizzle ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[104] = (ThreadLocalRandom.current().nextInt(2) + 1);
        initialGenes[105] = (1);
    } else {
        initialGenes[104] = (1);
        initialGenes[105] = (1);
    }

    // Silkie [ wildtype, silkie ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[106] = (ThreadLocalRandom.current().nextInt(2) + 1);
    } else {
        initialGenes[106] = (1);
    }

    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[107] = (ThreadLocalRandom.current().nextInt(2) + 1);
    } else {
        initialGenes[107] = (1);
    }

    // Scaless [ wildtype, scaleless ]
//    if (ThreadLocalRandom.current().nextInt(200) > 199) {
//        initialGenes[108] = (ThreadLocalRandom.current().nextInt(10) + 1);
//        if (initialGenes[108] != 2) {
//            initialGenes[108] = 1;
//        }
//        initialGenes[109] = (1);
//    } else {
        initialGenes[108] = (1);
        initialGenes[109] = (1);
//    }

    // Adrenaline A [ more alert, moderate alertness ,less alert ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[110] = (ThreadLocalRandom.current().nextInt(3) + 1);
    } else {
        initialGenes[110] = (2);
    }

    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[111] = (ThreadLocalRandom.current().nextInt(3) + 1);
    } else {
        initialGenes[111] = (2);
    }

    // Adrenaline B [ more alert, moderate alertness ,less alert ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[112] = (ThreadLocalRandom.current().nextInt(3) + 1);
    } else {
        initialGenes[112] = (2);
    }

    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[113] = (ThreadLocalRandom.current().nextInt(3) + 1);
    } else {
        initialGenes[113] = (2);
    }

    // Adrenaline C [ more alert, moderate alertness ,less alert ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[114] = (ThreadLocalRandom.current().nextInt(3) + 1);
    } else {
        initialGenes[114] = (2);
    }

    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[115] = (ThreadLocalRandom.current().nextInt(3) + 1);
    } else {
        initialGenes[115] = (2);
    }

    // The Dumb [ Dom.Dumb, dumb, normal ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[116] = (ThreadLocalRandom.current().nextInt(3) + 1);
    } else {
        initialGenes[116] = (3);
    }

    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[117] = (ThreadLocalRandom.current().nextInt(3) + 1);
    } else {
        initialGenes[117] = (3);
    }

    // The Clever [ normal, clever, rec. clever ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[118] = (ThreadLocalRandom.current().nextInt(3) + 1);
    } else {
        initialGenes[118] = (1);
    }

    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[119] = (ThreadLocalRandom.current().nextInt(3) + 1);
    } else {
        initialGenes[119] = (1);
    }

    // Anger A [ neutral, grouchy, aggressive ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[120] = (ThreadLocalRandom.current().nextInt(3) + 1);
    } else {
        initialGenes[120] = (1);
    }

    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[121] = (ThreadLocalRandom.current().nextInt(3) + 1);
    } else {
        initialGenes[121] = (1);
    }

    // Anger B [ flighty, neutral, aggressive ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[122] = (ThreadLocalRandom.current().nextInt(3) + 1);
    } else {
        initialGenes[122] = (1);
    }

    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[123] = (ThreadLocalRandom.current().nextInt(3) + 1);
    } else {
        initialGenes[123] = (1);
    }

    // Flightiness A [ flighty, neutral, shit scared(erratic booster) ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[124] = (ThreadLocalRandom.current().nextInt(3) + 1);
    } else {
        initialGenes[124] = (1);
    }

    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[125] = (ThreadLocalRandom.current().nextInt(3) + 1);
    } else {
        initialGenes[125] = (1);
    }

    // Flightiness B [ very flighty, nervous, neutral ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[126] = (ThreadLocalRandom.current().nextInt(3) + 1);
    } else {
        initialGenes[126] = (1);
    }

    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[127] = (ThreadLocalRandom.current().nextInt(3) + 1);
    } else {
        initialGenes[127] = (1);
    }

    // Wildness [ chaotic, wild, moderate, semi-predictable, predictable ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[128] = (ThreadLocalRandom.current().nextInt(5) + 1);
    } else {
        initialGenes[128] = (2);
    }

    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[129] = (ThreadLocalRandom.current().nextInt(5) + 1);
    } else {
        initialGenes[129] = (2);
    }

    // selfishness [ selfish, selfless, normal ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[130] = (ThreadLocalRandom.current().nextInt(3) + 1);
    } else {
        initialGenes[130] = (3);
    }

    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[131] = (ThreadLocalRandom.current().nextInt(3) + 1);
    } else {
        initialGenes[131] = (3);
    }

    // protectiveness [ protective - neutral ] //ups the fear/aggression if self,herd or baby is attacked
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[132] = (ThreadLocalRandom.current().nextInt(7) + 1);
    } else {
        initialGenes[132] = (3);
    }

    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[133] = (ThreadLocalRandom.current().nextInt(7) + 1);
    } else {
        initialGenes[133] = (3);
    }

    // curiosity [ curious - neutral ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[132] = (ThreadLocalRandom.current().nextInt(7) + 1);
    } else {
        initialGenes[132] = (3);
    }

    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[133] = (ThreadLocalRandom.current().nextInt(7) + 1);
    } else {
        initialGenes[133] = (3);
    }

    // sociable [ sociable - neutral ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[132] = (ThreadLocalRandom.current().nextInt(7) + 1);
    } else {
        initialGenes[132] = (3);
    }

    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[133] = (ThreadLocalRandom.current().nextInt(7) + 1);
    } else {
        initialGenes[133] = (3);
    }

    // empathetic/mothering [ mothering - neutral ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[134] = (ThreadLocalRandom.current().nextInt(7) + 1);
    } else {
        initialGenes[134] = (3);
    }

    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[135] = (ThreadLocalRandom.current().nextInt(7) + 1);
    } else {
        initialGenes[135] = (3);
    }

    // confidence [ neutral - confidence] (in the logic it should be partially negated by fearful attributes for some behaviours)
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[136] = (ThreadLocalRandom.current().nextInt(7) + 1);
    } else {
        initialGenes[136] = (3);
    }

    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[137] = (ThreadLocalRandom.current().nextInt(7) + 1);
    } else {
        initialGenes[137] = (3);
    }

    // playfulness [ playful - neutral ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[138] = (ThreadLocalRandom.current().nextInt(7) + 1);
    } else {
        initialGenes[138] = (3);
    }

    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[139] = (ThreadLocalRandom.current().nextInt(7) + 1);
    } else {
        initialGenes[139] = (3);
    }

    // food drive A [ loves food - neutral ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[140] = (ThreadLocalRandom.current().nextInt(6) + 1);
    } else {
        initialGenes[140] = (3);
    }

    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[141] = (ThreadLocalRandom.current().nextInt(6) + 1);
    } else {
        initialGenes[141] = (3);
    }

    // food drive B [ eats to live - neutral ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[142] = (ThreadLocalRandom.current().nextInt(6) + 1);
    } else {
        initialGenes[142] = (3);
    }

    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[143] = (ThreadLocalRandom.current().nextInt(6) + 1);
    } else {
        initialGenes[143] = (3);
    }

    // food drive C [ Always hungry, neutral, under eats ]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[144] = (ThreadLocalRandom.current().nextInt(3) + 1);
    } else {
        initialGenes[144] = (2);
    }

    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[145] = (ThreadLocalRandom.current().nextInt(3) + 1);
    } else {
        initialGenes[145] = (2);
    }

    //BodyBig [normal, big]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[146] = (ThreadLocalRandom.current().nextInt(2) + 1);
    } else {
        initialGenes[146] = (1);
    }

    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[147] = (ThreadLocalRandom.current().nextInt(2) + 1);
    } else {
        initialGenes[147] = (1);
    }

    //BodySmall [normal, small]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[148] = (ThreadLocalRandom.current().nextInt(2) + 1);
    } else {
        initialGenes[148] = (2);
    }

    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[149] = (ThreadLocalRandom.current().nextInt(2) + 1);
    } else {
        initialGenes[149] = (2);
    }

    //EarTuft [normal, Eartuft]
    if (ThreadLocalRandom.current().nextInt(100) > WTC + ((100-WTC)/2)) {
        initialGenes[150] = (ThreadLocalRandom.current().nextInt(2) + 1);
        initialGenes[151] = (1);
    } else {
        initialGenes[150] = (1);
        initialGenes[151] = (1);
    }

    //ear size 1 [1-4 = -1 , 5-8 = 0, 9-12 = +1]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[152] = (ThreadLocalRandom.current().nextInt(6) + 1);
    } else {
        initialGenes[152] = (1);
    }

    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[153] = (ThreadLocalRandom.current().nextInt(6) + 7);
    } else {
        initialGenes[153] = (7);
    }

    //ear size 2 [1-4 = -1 , 5-8 = 0, 9-12 = +1]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[154] = (ThreadLocalRandom.current().nextInt(6) + 1);
    } else {
        initialGenes[154] = (1);
    }

    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[155] = (ThreadLocalRandom.current().nextInt(6) + 7);
    } else {
        initialGenes[155] = (7);
    }

    //ear size 3 [1-4 = -1 , 5 = 0, 6-10 = +1 , 10-12 = +1 || +2]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[156] = (ThreadLocalRandom.current().nextInt(6) + 1);
    } else {
        initialGenes[156] = (1);
    }

    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[157] = (ThreadLocalRandom.current().nextInt(6) + 7);
    } else {
        initialGenes[157] = (7);
    }

    //ear size 4 and whitener [reducer, wildtype, adds size, adds white, adds white and size]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[158] = (ThreadLocalRandom.current().nextInt(6) + 1);
    } else {
        initialGenes[158] = (1);
    }

    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[159] = (ThreadLocalRandom.current().nextInt(5) + 1);
    } else {
        initialGenes[159] = (1);
    }

    //ear size 5 [1-4 = -1 , 5 = 0, 6-10 = +1 , 10-12 = +1 || +2]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[160] = (ThreadLocalRandom.current().nextInt(12) + 1);
    } else {
        initialGenes[160] = (1);
    }

    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[161] = (ThreadLocalRandom.current().nextInt(12) + 13);
    } else {
        initialGenes[161] = (7);
    }

    //ear size 6 [1-4 = -1 , 5 = 0, 6-10 = +1 , 10-12 = +1 || +2]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[162] = (ThreadLocalRandom.current().nextInt(12) + 1);
    } else {
        initialGenes[162] = (1);
    }

    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[163] = (ThreadLocalRandom.current().nextInt(12) + 13);
    } else {
        initialGenes[163] = (7);
    }

    //ear redness
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[164] = (ThreadLocalRandom.current().nextInt(5) + 1);
    } else {
        initialGenes[164] = (1);
    }

    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[165] = (ThreadLocalRandom.current().nextInt(5) + 1);
    } else {
        initialGenes[165] = (1);
    }

    //recessive black shanks [normal, blacker shanks]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[166] = (ThreadLocalRandom.current().nextInt(2) + 1);
    } else {
        initialGenes[166] = (1);
    }

    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[167] = (ThreadLocalRandom.current().nextInt(2) + 1);
    } else {
        initialGenes[167] = (1);
    }

    //long legs [normal, long legs]
    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[168] = (ThreadLocalRandom.current().nextInt(2) + 1);
    } else {
        initialGenes[168] = (1);
    }

    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        initialGenes[169] = (ThreadLocalRandom.current().nextInt(2) + 1);
    } else {
        initialGenes[169] = (1);
    }

    //Quirk ideas:
    //favourite flavours/foods
    //phobias: heights, certain mobs, swords/sticks/axes in hand, fire/lava, things bigger than them running
    //loves: heights, warm places, food,

}

    // TODO here: genes for egg hatch chance when thrown, egg laying rate, and chicken ai modifiers

        return initialGenes;
    }

    public void setGenes(int[] genes) {
        this.genes = genes;
    }

    public int[] getGenes(){
        return this.genes;
    }

    public void setMateGenes(int[] mateGenes){
        this.mateGenes = mateGenes;
    }

    public static class GroupData implements ILivingEntityData
    {

        public int[] groupGenes;
        public GroupData(int[] groupGenes) {
            this.groupGenes = groupGenes;
        }

    }
}

