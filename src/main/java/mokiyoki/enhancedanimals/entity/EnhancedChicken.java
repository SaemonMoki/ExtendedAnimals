package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.ai.ECRoost;
import mokiyoki.enhancedanimals.ai.general.EnhancedAvoidEntityGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedBreedGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedLookAtGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedLookRandomlyGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedPanicGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedTemptGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedWanderingGoal;
import mokiyoki.enhancedanimals.ai.general.GrazingGoal;
import mokiyoki.enhancedanimals.ai.general.SeekShelterGoal;
import mokiyoki.enhancedanimals.ai.general.StayShelteredGoal;
import mokiyoki.enhancedanimals.ai.general.chicken.ECWanderAvoidWater;
import mokiyoki.enhancedanimals.ai.general.chicken.GrazingGoalChicken;
import mokiyoki.enhancedanimals.capability.egg.EggCapabilityProvider;
import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
import mokiyoki.enhancedanimals.entity.genetics.ChickenGeneticsInitialiser;
import mokiyoki.enhancedanimals.init.FoodSerialiser;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.items.EnhancedEgg;
import mokiyoki.enhancedanimals.util.Genes;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

import static mokiyoki.enhancedanimals.init.FoodSerialiser.chickenFoodMap;

import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;

/**
 * Created by saemon and moki on 30/08/2018.
 */
public class EnhancedChicken extends EnhancedAnimalAbstract {

    //avalible UUID spaces : [ S 1 2 3 4 5 6 7 - 8 9 10 11 - 12 13 14 15 - 16 17 18 19 - 20 21 22 23 24 25 26 27 28 29 30 31 ]

    private static final EntityDataAccessor<Boolean> ROOSTING = SynchedEntityData.<Boolean>defineId(EnhancedChicken.class, EntityDataSerializers.BOOLEAN);

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
        "pattern_partridge.png", "pattern_partridge_blue.png", "pattern_partridge_splash.png", "pattern_partridge_splashlav.png", "pattern_partridge_splashdun.png",    //350
        "pattern_partridge_splashchoc.png",  "pattern_partridge_lav.png", "pattern_partridge_white.png", "pattern_partridge_dun.png", "pattern_partridge_choc.png",     //355
        "pattern_bluelaced.png",    //special case number 360
        "patternless",                     //special case patternless 361
        "48.png"                //test a texture 352
    };
    private static final String[] CHICKEN_TEXTURES_MOORHEAD = new String[] {
        "", "moorhead_black.png", "moorhead_blue.png", "moorhead_splash.png", "moorhead_splashlav.png", "moorhead_splashdun.png",
            "moorhead_splashchoc.png", "moorhead_lav.png", "moorhead_white.png", "moorhead_dun.png",    "moorhead_choc.png",
    };

    private static final String[] CHICKEN_TEXTURES_WHITE = new String[] {
        "","white_darkbarred.png","white_barred.png","white_crested.png","white_mottles.png", "white_crestedmottled.png"
    };

    private static final String[] CHICKEN_TEXTURES_MOTTLEMARKINGS = new String[] {
            "", "pattern_mottles.png", "pattern_mottles_blue.png", "pattern_mottles_splash.png", "pattern_mottles_splashlav.png", "pattern_mottles_splashdun.png",
            "pattern_mottles_splashchoc.png",  "pattern_mottles_lav.png", "pattern_mottles_white.png", "pattern_mottles_dun.png", "pattern_mottles_choc.png"
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
            "","baby_mottled.png", "baby_barred.png"
    };

    private static final String[] CHICKEN_TEXTURES_SHANKS = new String[] {
        "shanks_verywhite.png","shanks_lightwhite.png","shanks_white.png","shanks_grey.png", "shanks_slate.png", "shanks_black.png",
        "shanks_horn.png","shanks_lightyellow.png","shanks_yellow.png","shanks_darkyellow.png","shanks_willow.png","shanks_black.png",
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
        "eyes_albino.png", "eyes_black.png", "eyes_blue.png"
    };

    public float wingRotation;
    public float destPos;
    public float oFlapSpeed;
    public float oFlap;
    private float wingRotDelta = 1.0F;
    private int timeUntilNextEgg;
    protected GrazingGoal grazingGoal;

    private String dropMeatType;
    public boolean chickenJockey;

    public EnhancedChicken(EntityType<? extends EnhancedChicken> entityType, Level worldIn) {
        super(entityType, worldIn, Reference.CHICKEN_SEXLINKED_GENES_LENGTH, Reference.CHICKEN_AUTOSOMAL_GENES_LENGTH, false);
//        this.setSize(0.4F, 0.7F); //I think its the height and width of a chicken
        this.timeUntilNextEgg = this.random.nextInt(this.random.nextInt(6000) + 6000); //TODO make some genes to alter these numbers
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
    }

//    private Map<Block, EnhancedEatPlantsGoal.EatValues> createGrazingMap() {
//        Map<Block, EnhancedEatPlantsGoal.EatValues> ediblePlants = new HashMap<>();
//        ediblePlants.put(Blocks.CARROTS, new EnhancedEatPlantsGoal.EatValues(7, 1, 750));
//        ediblePlants.put(Blocks.BEETROOTS, new EnhancedEatPlantsGoal.EatValues(3, 1, 750));
//        ediblePlants.put(Blocks.WHEAT, new EnhancedEatPlantsGoal.EatValues(2, 1, 750));
//        ediblePlants.put(Blocks.AZURE_BLUET, new EnhancedEatPlantsGoal.EatValues(3, 2, 750));
//        ediblePlants.put(ModBlocks.GROWABLE_AZURE_BLUET.get(), new EnhancedEatPlantsGoal.EatValues(3, 2, 750));
//        ediblePlants.put(Blocks.ALLIUM, new EnhancedEatPlantsGoal.EatValues(3, 3, 750));
//        ediblePlants.put(ModBlocks.GROWABLE_ALLIUM.get(), new EnhancedEatPlantsGoal.EatValues(3, 2, 750));
//        ediblePlants.put(Blocks.BLUE_ORCHID, new EnhancedEatPlantsGoal.EatValues(7, 3, 375));
//        ediblePlants.put(ModBlocks.GROWABLE_BLUE_ORCHID.get(), new EnhancedEatPlantsGoal.EatValues(7, 2, 375));
//        ediblePlants.put(Blocks.CORNFLOWER, new EnhancedEatPlantsGoal.EatValues(7, 3, 375));
//        ediblePlants.put(ModBlocks.GROWABLE_CORNFLOWER.get(), new EnhancedEatPlantsGoal.EatValues(7, 2, 375));
//        ediblePlants.put(Blocks.DANDELION, new EnhancedEatPlantsGoal.EatValues(3, 2, 750));
//        ediblePlants.put(ModBlocks.GROWABLE_DANDELION.get(), new EnhancedEatPlantsGoal.EatValues(3, 2, 750));
//        ediblePlants.put(Blocks.ROSE_BUSH, new EnhancedEatPlantsGoal.EatValues(4, 3, 375));
//        ediblePlants.put(ModBlocks.GROWABLE_ROSE_BUSH.get(), new EnhancedEatPlantsGoal.EatValues(4, 2, 375));
//        ediblePlants.put(Blocks.SUNFLOWER, new EnhancedEatPlantsGoal.EatValues(4, 3, 375));
//        ediblePlants.put(ModBlocks.GROWABLE_SUNFLOWER.get(), new EnhancedEatPlantsGoal.EatValues(4, 2, 375));
//        ediblePlants.put(Blocks.GRASS, new EnhancedEatPlantsGoal.EatValues(1, 1, 750));
//        ediblePlants.put(ModBlocks.GROWABLE_GRASS.get(), new EnhancedEatPlantsGoal.EatValues(1, 1, 750));
//        ediblePlants.put(Blocks.TALL_GRASS, new EnhancedEatPlantsGoal.EatValues(1, 1, 750));
//        ediblePlants.put(ModBlocks.GROWABLE_TALL_GRASS.get(), new EnhancedEatPlantsGoal.EatValues(1, 1, 750));
//        ediblePlants.put(Blocks.FERN, new EnhancedEatPlantsGoal.EatValues(1, 1, 750));
//        ediblePlants.put(ModBlocks.GROWABLE_FERN.get(), new EnhancedEatPlantsGoal.EatValues(1, 1, 750));
//        ediblePlants.put(Blocks.LARGE_FERN, new EnhancedEatPlantsGoal.EatValues(1, 1, 750));
//        ediblePlants.put(ModBlocks.GROWABLE_LARGE_FERN.get(), new EnhancedEatPlantsGoal.EatValues(1, 1, 750));
//        ediblePlants.put(Blocks.SWEET_BERRY_BUSH, new EnhancedEatPlantsGoal.EatValues(1, 1, 1000));
//        ediblePlants.put(Blocks.CACTUS, new EnhancedEatPlantsGoal.EatValues(1, 1, 3000));
//
//        return ediblePlants;
//    }

    @Override
    protected void registerGoals() {
        int napmod = this.random.nextInt(1200);
        this.grazingGoal = new GrazingGoalChicken(this, 1.0D);
//        this.ecSandBath = new ECSandBath(this);
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new EnhancedPanicGoal(this, 1.4D));
        this.goalSelector.addGoal(3, new EnhancedAvoidEntityGoal<>(this, Wolf.class, 10.0F, 1.0D, 2.0D, null));
        this.goalSelector.addGoal(3, new EnhancedAvoidEntityGoal<>(this, Fox.class, 10.0F, 1.0D, 2.0D, null));
        this.goalSelector.addGoal(3, new EnhancedAvoidEntityGoal<>(this, Ocelot.class, 10.0F, 1.0D, 2.0D, null));
        this.goalSelector.addGoal(3, new EnhancedAvoidEntityGoal<>(this, EnhancedPig.class, 4.0F, 1.0D, 1.8D, null));
        this.goalSelector.addGoal(3, new EnhancedAvoidEntityGoal<>(this, Monster.class, 4.0F, 1.0D, 2.0D, null));
        this.goalSelector.addGoal(4, new EnhancedBreedGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new EnhancedTemptGoal(this, 1.0D, 1.3D, false, Items.AIR));
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(7, new ECRoost(this));
        this.goalSelector.addGoal(8, new StayShelteredGoal(this, 6000, 7500, napmod));
        this.goalSelector.addGoal(9, new SeekShelterGoal(this, 1.0D, 6000, 7500, napmod));
        this.goalSelector.addGoal(10, new ECWanderAvoidWater(this, 1.0D));
//        this.goalSelector.addGoal(11, new EnhancedEatPlantsGoal(this, createGrazingMap()));
        this.goalSelector.addGoal(12, this.grazingGoal);
        this.goalSelector.addGoal(14, new EnhancedWanderingGoal(this, 1.0D));
        this.goalSelector.addGoal(15, new EnhancedLookAtGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(16, new EnhancedLookAtGoal(this, EnhancedChicken.class, 6.0F));
        this.goalSelector.addGoal(17, new EnhancedLookRandomlyGoal(this));

    }

    protected void customServerAiStep() {
        this.animalEatingTimer = this.grazingGoal.getEatingGrassTimer();
//        this.sandBathTimer = this.ecSandBath.getSandBathTimer();
        super.customServerAiStep();
    }

    @Override
    protected FoodSerialiser.AnimalFoodMap getAnimalFoodType() {
        return chickenFoodMap();
    }

    @Override
    public EntityDimensions getDimensions(Pose poseIn) {
        return EntityDimensions.scalable(0.4F, 0.7F).scale(this.getScale());
    }

    @Override
    public float getScale() {
        float size = this.getAnimalSize() > 0.0F ? this.getAnimalSize() : 1.0F;
        float nbSize = 0.2F;
        return this.isGrowing() ? (nbSize + ((size-nbSize) * (this.growthAmount()))) : size;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ROOSTING, Boolean.FALSE);
    }

    @Override
    protected String getSpecies() {
        return "entity.eanimod.enhanced_chicken";
    }

    @Override
    protected int getAdultAge() { return EanimodCommonConfig.COMMON.adultAgeChicken.get();}

    @Override
    protected int gestationConfig() {
        return EanimodCommonConfig.COMMON.incubationDaysChicken.get();
    }

    @Override
    public InteractionResult mobInteract(Player entityPlayer, InteractionHand hand) {
        ItemStack itemStack = entityPlayer.getItemInHand(hand);
        Item item = itemStack.getItem();

        if (item instanceof EnhancedEgg && hunger >= 6000) {
            //enhancedegg egg eating
            decreaseHunger(200);
            if (!entityPlayer.getAbilities().instabuild) {
                itemStack.shrink(1);
            } else {
                if (itemStack.getCount() > 1) {
                    itemStack.shrink(1);
                }
            }
        }

        return super.mobInteract(entityPlayer, hand);
    }

    public void setSharedGenesFromEntityEgg(String genes) {
        this.entityData.set(SHARED_GENES, genes);
    }

    public boolean isRoosting() {
        return this.entityData.get(ROOSTING);
    }

    public void setRoosting(boolean isRoosting) {
        this.entityData.set(ROOSTING, isRoosting);
    }

    protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return this.isBaby() ? sizeIn.height * 0.85F : sizeIn.height * 0.92F;
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 3.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    public void positionRider(Entity passenger) {
        super.positionRider(passenger);
        float f = Mth.sin(this.yBodyRot * ((float)Math.PI / 180F));
        float f1 = Mth.cos(this.yBodyRot * ((float)Math.PI / 180F));
        float f2 = 0.1F;
        float f3 = 0.0F;
        passenger.setPos(this.getX() + (double)(0.1F * f), this.getY(0.5D) + passenger.getMyRidingOffset() + 0.0D, this.getZ() - (double)(0.1F * f1));
        if (passenger instanceof LivingEntity) {
            ((LivingEntity)passenger).yBodyRot = this.yBodyRot;
        }

    }

    /**
     * Determines if this chicken is a jokey with a zombie riding it.
     */
    public boolean isChickenJockey() {
        return this.chickenJockey;
    }

    /**
     * Sets whether this chicken is a jockey or not.
     */
    public void setChickenJockey(boolean jockey) {
        this.chickenJockey = jockey;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.oFlap = this.wingRotation;
        this.oFlapSpeed = this.destPos;
        this.destPos = (float)((double)this.destPos + (double)(this.onGround ? -1 : 4) * 0.3D);
        this.destPos = Mth.clamp(this.destPos, 0.0F, 1.0F);

        if (!this.onGround && this.wingRotDelta < 1.0F) {
            this.wingRotDelta = 1.0F;
        }

        this.wingRotDelta = (float)((double)this.wingRotDelta * 0.9D);
        Vec3 vec3d = this.getDeltaMovement();
        if (!this.onGround && vec3d.y < 0.0D) {
            this.setDeltaMovement(vec3d.multiply(1.0D, this.genetics.isHomozygousFor(106,   2) ? 1.0D : 0.6D, 1.0D));
        }

        this.wingRotation += this.wingRotDelta * 2.0F;
       //TODO if "is child" and parent is sitting go under parent, possibly turn off ability to collide.

        //TODO if "is child" and parent is 1 block over or less and doesn't have a passenger ride on parent's back

        //TODO if it is daytime and if this chicken can crow and (it randomly wants to crow OR another chicken near by is crowing) then crow.

    }

    protected void incrementHunger() {
        if (this.sleeping) {
            hunger = hunger + (0.25F*getHungerModifier());
        } else {
            hunger = hunger + (0.5F*getHungerModifier());
        }
    }

    @Override
    protected void runExtraIdleTimeTick() {
        if (EanimodCommonConfig.COMMON.omnigenders.get() || this.getOrSetIsFemale()) {
            if (this.gestationTimer > 0) {
                --this.gestationTimer;
            }

            if (hunger <= 24000) {
                --this.timeUntilNextEgg;
            } else if (hunger >= 48000) {
                this.timeUntilNextEgg = this.random.nextInt(6000) + 6000;
            }
        }
    }

    @Override
    protected void runPregnancyTick() {
        if (!this.isBaby() && this.timeUntilNextEgg <= 0) {
            this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            ItemStack eggItem = new ItemStack(getEggColour(resolveEggColour()), 1, null);
            ((EnhancedEgg) eggItem.getItem()).setHasParents(eggItem, true);
            if (this.gestationTimer > 0) {
                String damName = "???";
                if (this.getCustomName()!=null) {
                    damName = this.getCustomName().getString();
                }
                eggItem.getCapability(EggCapabilityProvider.EGG_CAP, null).orElse(new EggCapabilityProvider()).setEggData(new Genes(this.mateGenetics).makeChild(!this.mateGender, this.genetics, !this.getOrSetIsFemale(), Genes.Species.CHICKEN), this.mateName, damName);
                CompoundTag nbtTagCompound = eggItem.serializeNBT();
                eggItem.deserializeNBT(nbtTagCompound);
                if (this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
                    int i = 1;
                    while (i > 0) {
                        int j = ExperienceOrb.getExperienceValue(i);
                        i -= j;
                        this.level.addFreshEntity(new ExperienceOrb(this.level, this.getX(), this.getY(), this.getZ(), j));
                    }
                }
            }
            this.spawnAtLocation(eggItem, 1);
            this.timeUntilNextEgg = this.random.nextInt(6000) + 6000;
        }
    }

    @Override
    public boolean causeFallDamage(float distance, float damageMultiplier, DamageSource damageSource) {
        if (this.genetics.isHomozygousFor(106, 2)) {
            return super.causeFallDamage(distance, damageMultiplier, damageSource);
        }
            return false;
    }

    @Override
    protected void lethalGenes() {
        int[] genes = this.genetics.getAutosomalGenes();
        if(genes[70] == 2 && genes[71] == 2) {
                this.remove(RemovalReason.DISCARDED);
        } else if(genes[72] == 2 && genes[73] == 2){
                this.remove(RemovalReason.DISCARDED);
        } else if(genes[104] == 2 && genes[105] == 2){
                this.remove(RemovalReason.DISCARDED);
        } else if (genes[150] == 2 && genes[151] == 2){
                this.remove(RemovalReason.DISCARDED);
        }
    }


    @Override
    protected SoundEvent getAmbientSound() {
        if (isAnimalSleeping()) {
            return null;
        }
        return SoundEvents.CHICKEN_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.CHICKEN_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.CHICKEN_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        if (!this.isSilent() && this.getBells()) {
            this.playSound(SoundEvents.NOTE_BLOCK_CHIME, 1.5F, 2.0F);
        }
    }

    /**
     * Chicken grass eating and sand bathing
    */
    //TODO make the sand bathing actions

    //also provides sand bath bonus
    public void ate() {
//        if (this.isChild())
//        {
//            this.addGrowth(60);
//        }
    }

    @Override
    protected void handlePartnerBreeding(AgeableMob ageable) {
        if (EanimodCommonConfig.COMMON.omnigenders.get()) {
            this.mateGenetics = ((EnhancedChicken)ageable).getGenes();
            this.setFertile();
            this.setMateGender(((EnhancedChicken)ageable).getOrSetIsFemale());
            if (((EnhancedChicken)ageable).hasCustomName()) {
                this.setMateName(((EnhancedChicken) ageable).getCustomName().getString());
            }
            ((EnhancedChicken)ageable).setMateGenes(this.genetics);
            ((EnhancedChicken)ageable).setFertile();
            ((EnhancedChicken)ageable).setMateGender(this.getOrSetIsFemale());
            if (this.hasCustomName()) {
                ((EnhancedChicken)ageable).setMateName(this.getCustomName().getString());
            }
        } else if (this.getOrSetIsFemale()) {
            this.mateGenetics = ((EnhancedChicken)ageable).getGenes();
            this.setFertile();
            this.setMateGender(false);
            if (((EnhancedChicken)ageable).hasCustomName()) {
                this.setMateName(((EnhancedChicken) ageable).getCustomName().getString());
            }
        } else {
            ((EnhancedChicken)ageable).setMateGenes(this.genetics);
            ((EnhancedChicken)ageable).setFertile();
            ((EnhancedChicken)ageable).setMateGender(false);
            if (this.hasCustomName()) {
                ((EnhancedAnimalAbstract)ageable).setMateName(this.getCustomName().getString());
            }
        }
    }


    private int resolveEggColour(){
        int eggColour = 0;

        if (this.genetics!=null) {
            int[] sexlinkedGenes = this.genetics.getSexlinkedGenes();
            int[] genes = this.genetics.getAutosomalGenes();

        if(sexlinkedGenes[10] == 1 || (!this.getOrSetIsFemale() && sexlinkedGenes[11] == 1)) {

            if (genes[64] == 1 || genes[65] == 1 || genes[66] == 1 || genes[67] == 1) {
                //egg is brown
                eggColour = 13;
            } else if ((genes[64] == 2 || genes[65] == 2) && (genes[66] == 2 || genes[67] == 2)) {
                //egg is brown
                eggColour = 13;
            } else if (genes[66] == 2 || genes[67] == 2) {
                //egg is cream
                eggColour = 1;
            } else if (genes[64] == 2 || genes[65] == 2) {
                //egg is pink
                eggColour = 7;
            } else if (genes[64] == 3 || genes[65] == 3 || genes[66] == 3 || genes[67] == 3) {
                //egg is white
                eggColour = 0;
            }

            int shade = 0;
            int markings = 0;

            //darkens egg if already brown shade
            if (genes[68] == 1 || genes[69] == 1) {
                if (eggColour != 0) {
                    shade = +1;
                }
            }

            if (genes[172] == 2 || genes[173] == 2) {
                //darkens egg by 1
                shade = +1;
            }

            if ((eggColour != 3 && eggColour != 7 && eggColour != 11) && genes[174] == 2 || genes[175] == 2) {
                //darkens egg by 1
                shade = +1;
            }

            if (genes[176] == 3 || genes[177] == 3) {
                //darkens egg by 1
                shade = +1;
            } else if ((eggColour != 3 && eggColour != 7 && eggColour != 11) && genes[176] == 2 || genes[177] == 2) {
                //darkens egg by 1
                shade = +1;
            }

            if (genes[178] == 2 || genes[179] == 2) {
                //has speckles
                shade = -1;
                markings = 1;
            } else if (genes[178] == 3 || genes[179] == 3) {
                markings = 1;
            }

            if (genes[180] == 2 && genes[181] == 2) {
                if (markings == 1) {
                    markings = 2;
                } else {
                    shade = +1;
                }
            }

            if (genes[182] == 2 || genes[183] == 2) {
                if (markings == 1) {
                    markings = 3;
                } else if (markings == 2) {
                    shade = +1;
                }
            }

            if (shade > 6) {
                shade = 6;
            } else if (shade < 0) {
                shade = 0;
            }

            if (eggColour == 0 && shade != 0) {
                //gives egg a brown tint if its white
                eggColour = shade + 13 - 1;
            } else {
                eggColour = eggColour + shade;
            }

            if (markings == 1) {
                eggColour = eggColour + 76;
            } else if (markings == 2) {
                eggColour = eggColour + 152;
            } else if (markings == 3) {
                eggColour = eggColour + 228;
            }

        }

        //toggles blue egg version
        if(genes[62] == 4 || genes[63] == 4){
            //paleblue range
            eggColour = eggColour + 57;
        } else if (genes[62] == 3 || genes[63] == 3) {
            //powderblue range
            eggColour = eggColour + 38;
        } else if(genes[62] == 1 || genes[63] == 1){
            //blue range
            eggColour = eggColour + 19;
        }

        }

        return eggColour;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public String getTexture() {
        if (this.enhancedAnimalTextures.isEmpty()) {
            this.setTexturePaths();
        } else if (this.reload && this.getEnhancedAnimalAge() >= (int)(this.getFullSizeAge()*0.3331F)) {
            this.reload = false;
            this.reloadTextures();
        }

        return getCompiledTextures("enhanced_chicken");
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void setTexturePaths() {
        if(this.getSharedGenes()!=null) {
            int[] sexlinkedGenes = getSharedGenes().getSexlinkedGenes();
            int[] autosomalGenes = getSharedGenes().getAutosomalGenes();
            boolean isFemale = this.getOrSetIsFemale();
            if (this.getEnhancedAnimalAge() >= (int)(this.getFullSizeAge()*0.333F)) {
                int ground = 0;
                int pattern = 0;
                int moorhead = 0;
                int white = 0;
                int mottles = 0;
                int shanks = 3;
                int comb = 2;
                int eyes = 1;
                int ptrncolours = 10; //number of pattern colours

                int moorheadtoggle = 0;
                int melanin = 0;

                int face = 0;
                int ears = 0;
                int earsW = 0;

                //TODO fix up columbian type patterns to look more varried
                //TODO add in heterozygous pattern variations
                //TODO redo ground colours to use autosomal red and more fleshed out

                boolean isAlbino = false;

                if (autosomalGenes[20] != 1 && autosomalGenes[21] != 1) {                                                                       //checks if not wildtype
                    if (autosomalGenes[20] == 2 || autosomalGenes[21] == 2) {                                                                   //sets recessive white or albino
                        //recessive white
                        ground = 15;
                        pattern = 361;
                    } else {
                        //albino
                        ground = 15;
                        pattern = 361;
                        white = 0;
                        shanks = 6;
                        comb = 2;
                        eyes = 0;
                        isAlbino = true;
                    }
                } else {
                    if (autosomalGenes[24] == 5 || autosomalGenes[25] == 5) {
                        //extended black tree
                        if (autosomalGenes[24] == 5 && autosomalGenes[25] == 5) {
                            if (autosomalGenes[28] == 1 && autosomalGenes[29] == 1 && autosomalGenes[98] == 1 && autosomalGenes[99] == 1) {
                                //xtradark birchen
                                pattern = 17;
                                ground = 0;
                            } else {
                                //solid black
                                pattern = 0;
                                ground = 15;
                            }
                        } else if (autosomalGenes[24] == 1 || autosomalGenes[25] == 1) {
                            //xtradark birchen
                            pattern = 17;
                            ground = 0;
                        } else {
                            if (autosomalGenes[28] == 1 && autosomalGenes[29] == 1 && autosomalGenes[98] == 1 && autosomalGenes[99] == 1) {
                                //leaky black
                                pattern = 18;
                                ground = 0;
                            } else {
                                //xtradark birchen
                                pattern = 17;
                                ground = 0;
                            }
                        }
                    } else if (autosomalGenes[24] == 1 || autosomalGenes[25] == 1) {
                        //birchen tree
                        if (autosomalGenes[28] == 1 && autosomalGenes[29] == 1) {
                            if (autosomalGenes[98] == 1 && autosomalGenes[99] == 1) {
                                if (autosomalGenes[30] == 1 && autosomalGenes[31] == 1) {
                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
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
                                } else if (autosomalGenes[30] == 1 || autosomalGenes[31] == 1) {
                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
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
                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
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
                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
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
                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
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
                            } else if (autosomalGenes[98] == 1 || autosomalGenes[99] == 1) {
                                if (autosomalGenes[30] == 1 || autosomalGenes[31] == 1) {
                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
                                        //dark doublehalfspangle
                                        pattern = 21;
                                        ground = 15;
                                    } else {
                                        //dark messy quail
                                        pattern = 22;
                                        ground = 15;
                                    }
                                } else {
                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
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
                                if (autosomalGenes[30] == 1 || autosomalGenes[31] == 1) {
                                    if (autosomalGenes[100] == 2 || autosomalGenes[101] == 2) {
                                        //solid black
                                        pattern = 0;
                                        ground = 15;
                                    } else {
                                        // leaky black
                                        pattern = 18;
                                        ground = 0;
                                    }
                                } else {
                                    if (autosomalGenes[100] == 1 && autosomalGenes[101] == 1) {
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
                        } else if (autosomalGenes[28] == 1 || autosomalGenes[29] == 1) {
                            if (autosomalGenes[98] == 1 || autosomalGenes[99] == 1) {
                                if (autosomalGenes[30] == 1 || autosomalGenes[31] == 1) {
                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
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
                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
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
                                if (autosomalGenes[30] == 1 || autosomalGenes[31] == 1) {
                                    if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
                                        //solid black
                                        pattern = 0;
                                        ground = 15;
                                    } else {
                                        //leaky black
                                        pattern = 18;
                                        ground = 15;
                                    }
                                } else {
                                    if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
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
                            if (autosomalGenes[98] == 1 || autosomalGenes[99] == 1) {
                                if (autosomalGenes[30] == 1 || autosomalGenes[31] == 1) {
                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
                                            //extended patterned spangled
                                            pattern = 14;
                                            ground = 15;
                                        } else {
                                            // spangled
                                            pattern = 16;
                                            ground = 15;
                                        }
                                    } else {
                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
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
                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
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
                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
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
                                if (autosomalGenes[30] == 1 || autosomalGenes[31] == 1) {
                                    //solid black
                                    pattern = 0;
                                    ground = 15;
                                } else {
                                    if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
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

                    } else if (autosomalGenes[24] == 2 || autosomalGenes[25] == 2) {
                        //duckwing tree
                        if (autosomalGenes[28] == 1 || autosomalGenes[29] == 1) {
                            if (autosomalGenes[98] == 1 || autosomalGenes[99] == 1) {
                                if (autosomalGenes[30] == 1 || autosomalGenes[31] == 1) {
                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
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
                                    if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
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
                                if (autosomalGenes[30] == 1 || autosomalGenes[31] == 1) {
                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
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
                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
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
                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
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
                            if (autosomalGenes[98] == 1 || autosomalGenes[99] == 1) {
                                if (autosomalGenes[30] == 1 || autosomalGenes[31] == 1) {
                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
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
                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
                                            // extended patterned transverse pencilled
                                            pattern = 34;
                                            ground = 5;
                                        } else {
                                            //  transverse pencilled
                                            pattern = 10;
                                            ground = 5;
                                        }
                                    } else {
                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
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
                                if (autosomalGenes[30] == 1 || autosomalGenes[31] == 1) {
                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
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
                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
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
                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
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


                    } else if (autosomalGenes[24] == 3 || autosomalGenes[25] == 3) {
                        //wheaten tree
                        if (autosomalGenes[28] == 1 || autosomalGenes[29] == 1) {
                            if (autosomalGenes[98] == 1 || autosomalGenes[99] == 1) {
                                if (autosomalGenes[30] == 1 || autosomalGenes[31] == 1) {
                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
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
                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
                                            // moorhead incomplete columbian w/ less hackle markings
                                            pattern = 361;
                                            ground = 15;
                                            moorhead = 1;
                                        } else {
                                            // nearly buff
                                            pattern = 9;
                                            ground = 15;
                                        }
                                    } else {
                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
                                            // moorhead incomplete columbian w/ less hackle markings
                                            pattern = 8;
                                            ground = 15;
                                        } else {
                                            // buff
                                            pattern = 361;
                                            ground = 15;
                                        }
                                    }
                                }
                            } else {
                                if (autosomalGenes[30] == 1 || autosomalGenes[31] == 1) {
                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
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
                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
                                            // extended patterned incomplete laced
                                            pattern = 20;
                                            ground = 5;
                                        } else {
                                            //  columbian
                                            pattern = 5;
                                            ground = 15;
                                        }
                                    } else {
                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
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
                            if (autosomalGenes[98] == 1 || autosomalGenes[99] == 1) {
                                if (autosomalGenes[30] == 1 || autosomalGenes[31] == 1) {
                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
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
                                    if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
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
                                if (autosomalGenes[30] == 1 || autosomalGenes[31] == 1) {
                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
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
                                    if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
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

                    } else if (autosomalGenes[24] == 4 || autosomalGenes[25] == 4) {
                        //partidge tree
                        if (autosomalGenes[28] == 1 || autosomalGenes[29] == 1) {
                            if (autosomalGenes[98] == 1 || autosomalGenes[99] == 1) {
                                if (autosomalGenes[30] == 1 || autosomalGenes[31] == 1) {
                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
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
                                    if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
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
                                if (autosomalGenes[30] == 1 || autosomalGenes[31] == 1) {
                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
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
                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
                                            // extended patterned incomplete single laced
                                            pattern = 32;
                                            ground = 15;
                                        } else {
                                            // columbian
                                            pattern = 5;
                                            ground = 15;
                                        }
                                    } else {
                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
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
                            if (autosomalGenes[98] == 1 || autosomalGenes[99] == 1) {
                                if (autosomalGenes[30] == 1 || autosomalGenes[31] == 1) {
                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
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
                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
                                            // extended patterned transverse penciled
                                            pattern = 34;
                                            ground = 5;
                                        } else {
                                            // transverse penciled
                                            pattern = 10;
                                            ground = 5;
                                        }
                                    } else {
                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
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
                                if (autosomalGenes[30] == 1 || autosomalGenes[31] == 1) {
                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
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
                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
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
                                    if (autosomalGenes[26] == 1 || autosomalGenes[27] == 1) {
                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
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
                                        if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
                                            // extended patterned partridge
                                            pattern = 35;
                                            ground = 5;
                                            moorhead = 1;
                                        } else {
                                            // partridge
                                            pattern = 35;
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
                    if (isFemale) {
                        if (sexlinkedGenes[0] == 1){
                            //gold
                            groundMod = groundMod + 2;
                        }
                    } else {
                        if (sexlinkedGenes[0] == 1 && sexlinkedGenes[1] == 1) {
                            //gold
                            groundMod = groundMod + 2;
                        } else if (sexlinkedGenes[0] == 1 || sexlinkedGenes[1] == 1) {
                            //lemon
                            groundMod = groundMod + 1;
                        }
                    }
                    if (groundMod != 0 && (autosomalGenes[32] == 3 && autosomalGenes[33] == 3)) {
                        //lemon or cream but backwards
                        groundMod = groundMod + 1;
                    }
                    if (autosomalGenes[34] == 1 || autosomalGenes[35] == 1) {
                        //mahogany or lemon cream counter
                        groundMod = groundMod + 1;
                    }
                    if (autosomalGenes[36] == 2 && autosomalGenes[37] == 2) {
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
                        if (autosomalGenes[38] == 1 && autosomalGenes[39] == 1) {
                            //domwhite
                            pattern = pattern + 7;
                            moorhead = 8;
                        } else if (autosomalGenes[38] == 1 || autosomalGenes[39] == 1) {
                            // spotted domwhite
                            pattern = pattern + 7;
                            moorhead = 8;
                        } else {
                            //if chocolate
                            if (sexlinkedGenes[2] == 2 && (getOrSetIsFemale() || sexlinkedGenes[3] == 2)) {
                                //if lavender
                                if (autosomalGenes[36] == 2 && autosomalGenes[37] == 2) {
                                    //is a dun variety
                                    //if it is splash
                                    if (autosomalGenes[40] == 2 && autosomalGenes[41] == 2) {
                                        //splash dun
                                        pattern = pattern + 4;
                                        moorhead = 5;
                                    } else {
                                        //dun
                                        pattern = pattern + 8;
                                        moorhead = 9;
                                    }
                                } else {
                                    //is a chocolate variety
                                    if (autosomalGenes[40] == 2 && autosomalGenes[41] == 2) {
                                        //splash choc
                                        pattern = pattern + 5;
                                        moorhead = 6;
                                    } else if (autosomalGenes[40] != 1 || autosomalGenes[41] != 1) {
                                        //dun
                                        pattern = pattern + 8;
                                        moorhead = 9;
                                    } else {
                                        //chocolate
                                        pattern = pattern + 9;
                                        moorhead = 10;
                                    }
                                }
                            } else {
                                //if lavender
                                if (autosomalGenes[36] == 2 && autosomalGenes[37] == 2) {
                                    //is a lavender variety
                                    //if it is splash
                                    if (autosomalGenes[40] == 2 && autosomalGenes[41] == 2) {
                                        //splash lavender
                                        pattern = pattern + 3;
                                        moorhead = 4;
                                    } else {
                                        //lavender
                                        pattern = pattern + 6;
                                        moorhead = 7;
                                    }
                                } else {
                                    //is a black variety
                                    if (autosomalGenes[40] == 2 && autosomalGenes[41] == 2) {
                                        //splash
                                        pattern = pattern + 2;
                                        moorhead = 3;
                                    } else if (autosomalGenes[40] == 2 || autosomalGenes[41] == 2) {
                                        //blue
                                        if ((autosomalGenes[26] == 1 || autosomalGenes[27] == 1) && (autosomalGenes[24] == 5 || autosomalGenes[25] == 5)) {
                                            //blue laced ... super special genes combo for blue andalusian type pattern
                                            pattern = 360;
                                            if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
                                                if (autosomalGenes[30] == 1 || autosomalGenes[31] == 1) {
                                                    moorhead = 1;
                                                }
                                            } else {
                                                moorhead = 2;
                                            }
                                        } else {
                                            //blue
                                            pattern = pattern + 1;
                                            moorhead = 2;
                                        }

                                    }
                                }
                            }
                        }
                    }
                    mottles = moorhead == 0 ? 1 : moorhead;
                    if (moorheadtoggle == 0) {
                        moorhead = 0;
                    }
                }

                //white marking autosomalGenes
                if (!this.getOrSetIsFemale() && sexlinkedGenes[6] == 2 && sexlinkedGenes[7] == 2) {
                    //Light Barred
                    white = 2;
                } else if ((this.getOrSetIsFemale() && sexlinkedGenes[6] == 2) || (!this.getOrSetIsFemale() && (sexlinkedGenes[6] == 2 ^ sexlinkedGenes[7] == 2))) {
                    //Dark Barred
                    white = 1;
                } else {
                    if (autosomalGenes[22] >= 2 && autosomalGenes[23] >= 2) {
                        if (autosomalGenes[22] == 2 && autosomalGenes[23] == 2) {
                            //mottled
                            white = 4;
                        } else if (autosomalGenes[22] == 3 && autosomalGenes[23] == 3) {
                            //white crest
                            white = 3;
                        } else {
                            //mottled and white crest
                            white = 5;
                        }
                    }
                }

                // figures out the shank, comb, and skin colour if its not albino
                if (!isAlbino) {
                    //gets comb colour
                    if (((isFemale && sexlinkedGenes[8] == 1) || (!isFemale && (sexlinkedGenes[8] == 1 || sexlinkedGenes[9] == 1))) && (autosomalGenes[42] == 1 || autosomalGenes[43] == 1)) {
                        //comb and shanks are fibro black
                        comb = -1;
                        shanks = 6;
                    } else {
                        comb = 5;
                    }
                    if (autosomalGenes[24] == 5 || autosomalGenes[25] == 5) {
                        shanks = 5;
//                        if (comb != 0) {
//                            comb = 5;
//                        }
//                        // makes mulbery comb
//                        if (autosomalGenes[30] == 2) {
//                            comb = comb + 1;
//                        }
                    } else if (autosomalGenes[24] == 1 && autosomalGenes[25] == 1) {
                        shanks = 5;
//                        if (comb != 0) {
//                            comb = 5;
//                        }
                    } else if (autosomalGenes[24] == 1 || autosomalGenes[25] == 1) {
                        shanks = 5;
//                        if (comb != 0) {
//                            comb = 5;
//                        }
                    }
                    //shanks starts at 3 btw

                    if (shanks > 2) {
                        // if splash or blue lighten by 1 shade
                        if (autosomalGenes[40] == 2 || autosomalGenes[41] == 2) {
                            shanks--;
                        }
                    }
                    if (shanks > 2) {
                        //if barred or mottled lighten by 1 shade
                        if ((autosomalGenes[22] == 2 && autosomalGenes[23] == 2) || (sexlinkedGenes[6] == 2 || (!isFemale && sexlinkedGenes[7] == 2))) {
                            shanks--;
                        }
                    }
                    if (shanks > 2) {
                        //if lavender lighten by 1 shade
                        if ((autosomalGenes[36] == 1 && autosomalGenes[37] == 1)) {
                            shanks--;
                        }
                    }

                    // if Dilute is Dilute and the shanks arnt darkened by extened black lighten by 1 shade
                    if ((autosomalGenes[24] != 5 && autosomalGenes[25] != 5) && (autosomalGenes[32] == 1 || autosomalGenes[33] == 1)) {
                        shanks--;
                    }

                    // if dominant white lighten by 1 shade
                    if (autosomalGenes[38] == 1 && autosomalGenes[39] == 1) {
                        shanks--;
                        if (comb != 2) {
                            comb++;
                        }
                    }

                    //if its charcoal
                    if (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) {
                        shanks++;
                        if (comb != 3)
                        comb--;
                    }

                    //lightens comb to mulberry if lightness is extreme enough
                    if (shanks < 4 && comb != 2) {
                        comb = comb + 1;
                    }

                    if (autosomalGenes[166] == 2 && autosomalGenes[167] == 2) {
                        //ressesive dark shanks
                        shanks = shanks + 2;
                    }

                    //makes sure its not off the chart
                    if (shanks < 0) {
                        shanks = 0;
                    } else if (shanks > 5) {
                        shanks = 5;
                    }



                    if (comb > 5) {
                        comb = 5;
                    } else if (comb < 0) {
                        comb = 0;
                    }

                    //makes the shanks and beak their white or yellow varient
                    if (autosomalGenes[44] == 3 && autosomalGenes[45] == 3) {
                        shanks = shanks + 11;
                    } else if (autosomalGenes[44] != 1 && autosomalGenes[45] != 1) {
                        shanks = shanks + 5;
                    }

                }

                //face and ear size stuff
                if (isFemale) {
                    if (sexlinkedGenes[4] >= 2) {
                        ears = 1;
                    } else {
                        ears = -1;
                    }
                } else {
                    if (sexlinkedGenes[4] >= 2 && sexlinkedGenes[5] >= 2) {
                        ears = 1;
                    } else {
                        ears = -1;
                    }
                }

                if (autosomalGenes[162] == 163){
                    if (autosomalGenes[162] >= 9 && autosomalGenes[162] <=16) {
                        ears = ears +1;
                    } else if (autosomalGenes[162] >= 17 && autosomalGenes[162] <=24) {
                        ears = ears + 2;
                    }
                    if ((autosomalGenes[162] & 1) == 0) {
                        earsW = earsW + 2;
                    }
                } else {
//                    ears = ears - 1;
                    if ((autosomalGenes[162] & 1) == 0) {
                        if ((autosomalGenes[163] & 1) == 0) {
                            earsW = earsW + 1;
                        }
                    } else if ((autosomalGenes[163] & 1) != 0) {
                        earsW = earsW - 1;
                    }
                }

                if (autosomalGenes[152] <= 4 || autosomalGenes[153] <= 4) {
//                    ears = ears - 1;
                } else {
                    if (autosomalGenes[152] == autosomalGenes[153]) {
                        if (autosomalGenes[152] >= 9 || autosomalGenes[153] >= 9) {
                            ears = ears + 1;
                        }
                    } else {
//                        ears = ears - 1;
                    }

                    if ((autosomalGenes[152] & 1) == 0) {
                        earsW = earsW + 1;
                    }
                }

                if (autosomalGenes[160] == autosomalGenes[161]) {
                    if (autosomalGenes[160] >= 9) {
                        if (autosomalGenes[160] >= 17) {
                            ears = ears + 2;
                        } else {
                            ears = ears + 1;
                        }
                        earsW = earsW + 1;
                    }
                }

                if (((autosomalGenes[160] & 1) == 0) && ((autosomalGenes[161] & 1) == 0)) {
                    if (autosomalGenes[160] == autosomalGenes[161]) {
                        earsW = earsW + 2;
                    } else {
                        earsW = earsW + 1;
                    }
                }

                if (autosomalGenes[82] == 1) {
                    ears = ears + 1;
                }
                if (autosomalGenes[83] == 1) {
                    ears = ears + 1;
                }

                if (autosomalGenes[48] == 1) {
                    ears = ears - 3;
                }
                if (autosomalGenes[49] == 1) {
                    ears = ears - 3;
                }

                if (autosomalGenes[56] == 1 || autosomalGenes[57] == 1) {
                    ears = ears - 2;
                }

                if (autosomalGenes[80] == 1 || autosomalGenes[81] == 1) {
                    ears = ears - 1;
                }

                if (autosomalGenes[158] == 1 || autosomalGenes[159] == 1) {
//                    ears = ears - 1;
                    earsW = earsW - 1;
                } else if (autosomalGenes[158] == 2 || autosomalGenes[159] == 2) {
//                    ears = ears - 1;
                } else if (autosomalGenes[158] == 3 || autosomalGenes[159] == 3) {
                    if (autosomalGenes[158] == 4 || autosomalGenes[159] == 4) {
                        ears = ears + 1;
                        earsW = earsW + 1;
                    } else {
                        ears = ears + 1;
                    }
                } else if (autosomalGenes[158] == 4 || autosomalGenes[159] == 4) {
                    earsW = earsW + 1;
                } else {
                    ears = ears + 1;
                    earsW = earsW + 1;
                }


                //at max adds 2 if less than 8
                if (autosomalGenes[154] <= 3 || autosomalGenes[155] <= 3) {
                    ears = ears - 1;
                } else if (autosomalGenes[154] == autosomalGenes[155]) {
                    if (autosomalGenes[154] >= 7) {
                        if (autosomalGenes[154] >= 10) {
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
                if (!(autosomalGenes[156] <= 3 || autosomalGenes[157] <= 3) && (autosomalGenes[156] == autosomalGenes[157])) {
                    if (autosomalGenes[156] >= 6) {
                        if (autosomalGenes[156] >= 10) {
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

                if (autosomalGenes[84] == 1 || autosomalGenes[85] == 1) {
                    ears = ears/2;
                }


                if ((autosomalGenes[154] & 1) == 0 && (autosomalGenes[155]) == 0) {
                    earsW = earsW + 1;
                } else if ((autosomalGenes[154] & 1) != 0 && (autosomalGenes[155]) != 0) {
                    earsW = earsW - 1;
                }

                if (autosomalGenes[164] == 1 || autosomalGenes[165] == 1) {
                    earsW = earsW - 3;
                } else if (autosomalGenes[164] == 2 || autosomalGenes[165] == 2) {
                    if (autosomalGenes[164] >= 4 || autosomalGenes[165] >= 4) {
                        earsW = earsW - 1;
                    }
                } else if (autosomalGenes[164] == 4 || autosomalGenes[165] == 4) {
                    if (autosomalGenes[164] >= 5 || autosomalGenes[165] >= 5) {
                        earsW = earsW + 1;
                    }
                } else if (autosomalGenes[164] == autosomalGenes[165]) {
                    earsW = earsW + 2;
                }

                if (sexlinkedGenes[18] == 2 || (!isFemale && sexlinkedGenes[19] == 2)) {
                    earsW = earsW/2;
                }

                if (isFemale) {
                    if (sexlinkedGenes[12] == 5) {
                        if (earsW <= 3) {
                            earsW = earsW + 1;
                        } else if (earsW >= 5) {
                            earsW = earsW - 1;
                        }
                    } else if (sexlinkedGenes[12] == 6) {
                        earsW = earsW + 1;
                    }
                } else {
                    if (sexlinkedGenes[12] >= 5 && sexlinkedGenes[13] >= 5) {
                        earsW = earsW + 1;
                    } else if (sexlinkedGenes[12] >= 4 && sexlinkedGenes[13] >= 4) {
                        if (earsW <= 3) {
                            earsW = earsW + 1;
                        } else if (earsW >= 5) {
                            earsW = earsW - 1;
                        }
                    }
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
                            if ((autosomalGenes[44] != 1 && autosomalGenes[45] != 1) && (autosomalGenes[44] == 3 || autosomalGenes[45] == 3)) {
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
                            if ((autosomalGenes[44] != 1 && autosomalGenes[45] != 1) && (autosomalGenes[44] == 3 || autosomalGenes[45] == 3)) {
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
                            if (((isFemale && sexlinkedGenes[8] == 1) || (!isFemale && (sexlinkedGenes[8] == 1 || sexlinkedGenes[9] == 1))) && (autosomalGenes[42] == 1 || autosomalGenes[43] == 1)) {
                                if ((autosomalGenes[44] != 1 && autosomalGenes[45] != 1) && (autosomalGenes[44] == 3 || autosomalGenes[45] == 3)) {
                                    //light green
                                    ears = ears + 100;
                                } else {
                                    //ear is light blue
                                    ears = ears + 70;
                                }
                            } else {
                                if ((autosomalGenes[44] != 1 && autosomalGenes[45] != 1) && (autosomalGenes[44] == 3 || autosomalGenes[45] == 3)) {
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
                            if ((autosomalGenes[44] != 1 && autosomalGenes[45] != 1) && (autosomalGenes[44] == 3 || autosomalGenes[45] == 3)) {
                                //yellow
                                ears = ears + 90;
                            } else {
                                //white ears
                                ears = ears + 60;
                            }
                        } else if (earsW == 1) {
                            //mottled red ears
                            ears = ears + 30;
                        }
                    }
                }

                if (autosomalGenes[0] == 2) {
                    eyes = 2;
                }

                if (!(white == 3 || white == 5) || (autosomalGenes[106] == 2 && autosomalGenes[107] == 2)) {
                    mottles = 0;
                }

                //after finished autosomalGenes
//                addTextureToAnimal("new_ground.png");
//                addTextureToAnimal("new_lace.png");
//                addTextureToAnimal("new_mottles.png");
                addTextureToAnimal(CHICKEN_TEXTURES_GROUND, ground, null);
                addTextureToAnimal(CHICKEN_TEXTURES_PATTERN, pattern, p -> p <= 350);
                addTextureToAnimal(CHICKEN_TEXTURES_MOORHEAD, moorhead, m -> m != 0);
                addTextureToAnimal(CHICKEN_TEXTURES_MOTTLEMARKINGS, mottles, m -> m != 0);
                addTextureToAnimal(CHICKEN_TEXTURES_WHITE, white, w -> w != 0);

                addTextureToAnimal(CHICKEN_TEXTURES_SHANKS, shanks, null);
                addTextureToAnimal(CHICKEN_TEXTURES_FACE, face, f -> f >= 1);
                addTextureToAnimal(CHICKEN_TEXTURES_EARS, ears, e -> e >= 1);
                addTextureToAnimal(CHICKEN_TEXTURES_COMB, comb, null);
                addTextureToAnimal(CHICKEN_TEXTURES_EYES, eyes, null);

            }else{
                int shanks = 4;
                int comb = 2;
                int eyes = 1;
                int downBase = 1;
                int red = 0;
                int black = 0;
                int white = 0;


                boolean isAlbino = false;

                if (autosomalGenes[20] != 1 && autosomalGenes[21] != 1) {
                    downBase = 0;
                    if (autosomalGenes[20] == 3 && autosomalGenes[21] == 3) {
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
                    if (autosomalGenes[24] == 1 && autosomalGenes[25] == 1) {
                        shanks = 3;
                        // makes mulbery comb
                        if (autosomalGenes[30] == 2) {
                            comb = 1;
                        }
                    }
                    if (((isFemale && sexlinkedGenes[8] == 1) || (!isFemale && (sexlinkedGenes[8] == 1 || sexlinkedGenes[9] == 1))) && (autosomalGenes[42] == 1 || autosomalGenes[43] == 1)) {
                        //comb and shanks are black
                        comb = 0;
                        shanks = 3;
                    }
                    //shanks starts at 3 btw
                    // if Dilute is Dilute and the shanks arnt darkened by extened black lighten by 1 shade
                    if ((autosomalGenes[24] != 1 && autosomalGenes[25] != 1) && (autosomalGenes[32] == 1 || autosomalGenes[33] == 1)) {
                        shanks--;
                    }

                    //if barred or mottled lighten by 1 shade
                    if ((autosomalGenes[22] == 2 && autosomalGenes[23] == 2) || (sexlinkedGenes[6] == 2 || (!isFemale && sexlinkedGenes[7] == 2))) {
                        shanks--;
                    }

                    // if dominant white or lavender lighten by 1 shade
                    if ((autosomalGenes[38] == 1 && autosomalGenes[39] == 1) || (autosomalGenes[36] == 1 && autosomalGenes[37] == 1)) {
                        shanks--;
                    }

                    // if splash or blue lighten by 1 shade
                    if (autosomalGenes[40] == 2 || autosomalGenes[41] == 2) {
                        shanks--;
                    }

                    //if its melanized
//                    if (Melanin == 2) {
//                        shanks++;
//                    }

                    if (autosomalGenes[166] == 2 && autosomalGenes[167] == 2) {
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
                    if (autosomalGenes[44] != 1 && autosomalGenes[45] != 1) {
                        shanks = shanks + 5;
                    }

                    if (downBase != 0) {

                        if (sexlinkedGenes[0] == 2 && (isFemale || (!isFemale && sexlinkedGenes[1] == 2))) {
                            downBase = 0;
                        }

                        if (autosomalGenes[24] == 5 || autosomalGenes[25] == 5) {
                            // is black
                            if ((autosomalGenes[30] == 1 || autosomalGenes[31] == 1) && (autosomalGenes[100] == 2 && autosomalGenes[101] == 2) && (autosomalGenes[28] != 1 && autosomalGenes[28] != 1)) {
                                black = 13;
                            } else {
                                black = 7;
                            }

                        } else if (autosomalGenes[24] == 1 || autosomalGenes[25] == 1) {
                            //is birchen
                            if (((autosomalGenes[30] == 1 || autosomalGenes[31] == 1) || (autosomalGenes[100] == 2 && autosomalGenes[101] == 2)) && (autosomalGenes[28] != 1 && autosomalGenes[28] != 1)) {
                                black = 13;
                            } else {
                                black = 7;
                            }
                        } else if (autosomalGenes[24] == 2 || autosomalGenes[25] == 2) {
                            //is duckwing
                            red = 3;
                            black = 1;
                        } else if (autosomalGenes[24] == 3 || autosomalGenes[25] == 3) {
                            //is wheaten
                            if (autosomalGenes[34] == 1 && autosomalGenes[35] == 1) {
                                red = 1;
                            } else if (autosomalGenes[34] == 1 || autosomalGenes[35] == 1) {
                                red = 2;
                            }
                        } else {
                            //is partridge
                            red = 2;
                            black = 1;
                        }

                        //white marking genes
                        if (sexlinkedGenes[6] == 2 || (!isFemale && sexlinkedGenes[7] == 2)) {
                            //Barred
                            white = 2;
                            if (!isFemale && sexlinkedGenes[6] == 2 && sexlinkedGenes[7] == 2) {
                                white = 2;
                            }
                        } else {
                            if (autosomalGenes[22] == 2 && autosomalGenes[23] == 2) {
                                //mottled
                                white = 1;
                            }
                        }

                        if (black != 0) {
                            if ((autosomalGenes[38] == 1 || autosomalGenes[39] == 1) || (autosomalGenes[40] == 2 && autosomalGenes[41] == 2)) {
                                //white or splash
                                black = black + 1;
                            } else if (autosomalGenes[36] == 2 && autosomalGenes[37] == 2) {
                                if (sexlinkedGenes[2] == 2 && (isFemale || (!isFemale && sexlinkedGenes[3] == 2))) {
                                    //dun
                                    black = black + 5;
                                } else {
                                    //lav
                                    black = black + 2;
                                }
                            } else if (autosomalGenes[40] == 2 || autosomalGenes[41] == 2) {
                                if (sexlinkedGenes[2] == 2 && (isFemale || (!isFemale && sexlinkedGenes[3] == 2))) {
                                    //dun
                                    black = black + 5;
                                } else {
                                    //blue
                                    black = black + 3;
                                }
                            } else if (sexlinkedGenes[2] == 2 && (isFemale || (!isFemale && sexlinkedGenes[3] == 2))) {
                                //choc
                                black = black + 4;
                            }

                        }
                    }
                }

                if (autosomalGenes[0] == 2) {
                    eyes = 2;
                }

                addTextureToAnimal(CHICKEN_TEXTURES_CHICKBASE, downBase, null);
                addTextureToAnimal(CHICKEN_TEXTURES_CHICKRED, red, r -> r != 0);
                addTextureToAnimal(CHICKEN_TEXTURES_CHICKBLACK, black, b -> b != 0);
                addTextureToAnimal(CHICKEN_TEXTURES_CHICKWHITE, white, w -> w != 0);
                addTextureToAnimal(CHICKEN_TEXTURES_SHANKS, shanks, null);
                addTextureToAnimal(CHICKEN_TEXTURES_COMB, comb, null);
                addTextureToAnimal(CHICKEN_TEXTURES_EYES, eyes, null);
            }
        }
    }

    @Override
    protected void setAlphaTexturePaths() {
    }

    @Override
    protected boolean shouldDropExperience() {
        if (this.getEnhancedAnimalAge() > 10000) {
            return true;
        } else {
            return false;
        }
    }

    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropCustomDeathLoot(source, looting, recentlyHitIn);
        int[] genes = this.genetics.getAutosomalGenes();
        int age = this.getEnhancedAnimalAge();
        int bodyType;
        int meatSize;
        int featherCount = random.nextInt(4+looting)-1;

        if (genes[146] == 2 && genes[147] == 2) {
            if (genes[148] == 2 && genes[149] == 2) {
                //normal body
                bodyType = 0;
            } else {
                //big body
                bodyType = 1;
            }
        } else if (genes[148] == 2 && genes[149] == 2) {
            if (genes[146] == 2 || genes[147] == 2) {
                //normal body
                bodyType = 0;
            } else {
                //small body
                bodyType = -1;
            }
        } else {
            //normal body
            bodyType = 0;
        }

        if (age < 60000) {
            if (age > 40000) {
                bodyType = bodyType - 1;
                featherCount = featherCount - 1;
            } else if (age > 20000){
                bodyType = bodyType - (random.nextInt(2)+1);
                featherCount = featherCount - 2;
            } else {
                bodyType = bodyType - 2;
                featherCount = featherCount - 3;
            }
        }

        // size is [ 0.5076 - 1.0F]
        if (this.getAnimalSize() < 0.67) {
            meatSize = bodyType + 1;
        } else if (this.getAnimalSize() < 0.89) {
            meatSize = bodyType + 2;
        } else {
            meatSize = bodyType + 3;
        }

        if (meatSize > 0) {
            ItemStack meatStack = new ItemStack(Items.CHICKEN, 1 + looting);

            if (genes[4] == 1 && genes[20] != 3 && genes[21] != 3 && (genes[42] == 1 || genes[43] == 1)) {
                if (meatSize == 1) {
                    if (this.isOnFire()) {
                        meatStack = new ItemStack(ModItems.COOKEDCHICKEN_DARKSMALL.get(), 1 + looting);
                    } else {
                        meatStack = new ItemStack(ModItems.RAWCHICKEN_DARKSMALL.get(), 1 + looting);
                    }
                } else if (meatSize == 2) {
                    dropMeatType = "rawchicken_darkbig";
                    if (this.isOnFire()) {
                        meatStack = new ItemStack(ModItems.COOKEDCHICKEN_DARKBIG.get(), 1 + looting);
                    } else {
                        meatStack = new ItemStack(ModItems.RAWCHICKEN_DARKBIG.get(), 1 + looting);
                    }
                } else {
                    dropMeatType = "rawchicken_dark";
                    if (this.isOnFire()) {
                        meatStack = new ItemStack(ModItems.COOKEDCHICKEN_DARK.get(), 1 + looting);
                    } else {
                        meatStack = new ItemStack(ModItems.RAWCHICKEN_DARK.get(), 1 + looting);
                    }
                }
            } else {
                if (meatSize == 1) {
                    dropMeatType = "rawchicken_palesmall";
                    if (this.isOnFire()) {
                        meatStack = new ItemStack(ModItems.COOKEDCHICKEN_PALESMALL.get(), 1 + looting);
                    } else {
                        meatStack = new ItemStack(ModItems.RAWCHICKEN_PALESMALL.get(), 1 + looting);
                    }
                } else if (meatSize == 2) {
                    dropMeatType = "rawchicken";
                    if (this.isOnFire()) {
                        meatStack = new ItemStack(ModItems.COOKEDCHICKEN_PALE.get(), 1 + looting);
                    } else {
                        meatStack = new ItemStack(ModItems.RAWCHICKEN_PALE.get(), 1 + looting);
                    }
                } else {
                    dropMeatType = "rawchicken_pale";
                    if (this.isOnFire()) {
                        meatStack = new ItemStack(Items.COOKED_CHICKEN, 1 + looting);
                    }
                }
            }

            this.spawnAtLocation(meatStack);
        }

        if (featherCount > 0) {
            ItemStack featherStack = new ItemStack(Items.FEATHER, featherCount);
            this.spawnAtLocation(featherStack);
        }
    }

    @Override
    protected void readLegacyGenes(ListTag geneList, Genes genetics) {
        if (geneList.getCompound(0).contains("Sgene")) {
            super.readLegacyGenes(geneList, genetics);
        } else {
            for (int i = 0; i < 10; ++i) {
                int gene = geneList.getCompound(i).getInt("Gene");
                genetics.setSexlinkedGene(i * 2, gene);
                genetics.setSexlinkedGene((i * 2) + 1, gene);
            }

            for (int i = 0; i < geneList.size(); ++i) {
                if (i < 20) {
                    genetics.setAutosomalGene(i, 1);
                } else {
                    genetics.setAutosomalGene(i, geneList.getCompound(i).getInt("Gene"));
                }
            }
        }
    }

    @Override
    protected void createAndSpawnEnhancedChild(Level world) {}

    @Override
    protected boolean canBePregnant() {
        return false;
    }

    @Override
    protected boolean canLactate() {
        return false;
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);

        compound.putBoolean("IsChickenJockey", this.isChickenJockey());

    }

    /**
     * (abstract) Protected helper method to read subclass entity assets from NBT.
     */
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);

        setChickenJockey(compound.getBoolean("IsChickenJockey"));

    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor inWorld, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag itemNbt) {
        livingdata = commonInitialSpawnSetup(inWorld, livingdata, getAdultAge(), 10000, 120000, spawnReason);
        if (this.mateGenetics != null && this.mateGenetics.getSexlinkedGene(0) != 0) {
            this.setFertile();
        }
        return livingdata;
    }

    @Override
    protected Genes createInitialGenes(LevelAccessor world, BlockPos pos, boolean isDomestic) {
        return new ChickenGeneticsInitialiser().generateNewGenetics(world, pos, isDomestic);
    }

    @Override
    public Genes createInitialBreedGenes(LevelAccessor world, BlockPos pos, String breed) {
        return new ChickenGeneticsInitialiser().generateWithBreed(world, pos, breed);
    }

    @Override
    protected void initializeHealth(EnhancedAnimalAbstract animal, float health) {
        int[] genes = animal.genetics.getAutosomalGenes();
        if (genes[54] != 3 && genes[55] != 3 && genes[184] == 2 && genes[185] == 2) {
            health = 0.5F;
        } else {
            health = (2.0F * animal.getAnimalSize()) + 2.0F;
        }

        super.initializeHealth(animal, health);
    }

    @Override
    public void initilizeAnimalSize(){
        float size = 1.0F;

        int[] genes = this.genetics.getAutosomalGenes();
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


        genes = this.genetics.getSexlinkedGenes();
        if (this.getOrSetIsFemale()) {
            if(genes[14] == 2){
                size = size * 0.9F;
            }

            if(genes[16] == 2){
                size = size * 0.75F;
            }
        } else {
            if (genes[14] == 2 || genes[15] == 2) {
                if (genes[14] == 2 && genes[15] == 2) {
                    size = size * 0.9F;
                } else {
                    size = size * 0.99F;
                }
            }

            if (genes[16] == 2 || genes[17] == 2) {
                if (genes[16] == 2 && genes[17] == 2) {
                    size = size * 0.75F;
                } else {
                    size = size * 0.99F;
                }
            }
        }


        // size is [ 0.5076 - 1.0F]
        this.setAnimalSize(size);
    }

    private Item getEggColour(int eggColourGene){

        switch (eggColourGene) {
            case 0:
                return ModItems.EGG_WHITE.get();
            case 1:
                return ModItems.EGG_CREAMLIGHT.get();
            case 2:
                return ModItems.EGG_CREAM.get();
            case 3:
                return ModItems.EGG_CREAMDARK.get();
            case 4:
                return ModItems.EGG_CREAMDARKEST.get();
            case 5:
                return ModItems.EGG_CARMELDARK.get();
            case 6:
                return ModItems.EGG_GARNET.get();
            case 7:
                return ModItems.EGG_PINKLIGHT.get();
            case 8:
                return ModItems.EGG_PINK.get();
            case 9:
                return ModItems.EGG_PINKDARK.get();
            case 10:
                return ModItems.EGG_PINKDARKEST.get();
            case 11:
                return ModItems.EGG_CHERRYDARK.get();
            case 12:
                return ModItems.EGG_PLUM.get();
            case 13:
                return ModItems.EGG_BROWNLIGHT.get();
            case 14:
                return ModItems.EGG_BROWN.get();
            case 15:
                return ModItems.EGG_BROWNDARK.get();
            case 16:
                return ModItems.EGG_CHOCOLATE.get();
            case 17:
                return ModItems.EGG_CHOCOLATEDARK.get();
            case 18:
                return ModItems.EGG_CHOCOLATECOSMOS.get();
            case 19:
                return ModItems.EGG_BLUE.get();
            case 20:
                return ModItems.EGG_GREENLIGHT.get();
            case 21:
                return ModItems.EGG_GREENYELLOW.get();
            case 22:
                return ModItems.EGG_OLIVELIGHT.get();
            case 23:
                return ModItems.EGG_OLIVE.get();
            case 24:
                return ModItems.EGG_OLIVEDARK.get();
            case 25:
                return ModItems.EGG_ARMY.get();
            case 26:
                return ModItems.EGG_BLUEGREY.get();
            case 27:
                return ModItems.EGG_GREY.get();
            case 28:
                return ModItems.EGG_GREYGREEN.get();
            case 29:
                return ModItems.EGG_AVOCADO.get();
            case 30:
                return ModItems.EGG_AVOCADODARK.get();
            case 31:
                return ModItems.EGG_FELDGRAU.get();
            case 32:
                return ModItems.EGG_MINT.get();
            case 33:
                return ModItems.EGG_GREEN.get();
            case 34:
                return ModItems.EGG_GREENDARK.get();
            case 35:
                return ModItems.EGG_PINE.get();
            case 36:
                return ModItems.EGG_PINEDARK.get();
            case 37:
                return ModItems.EGG_PINEBLACK.get();
            case 38:
                return ModItems.EGG_POWDERBLUE.get();
            case 39:
                return ModItems.EGG_TEA.get();
            case 40:
                return ModItems.EGG_MATCHA.get();
            case 41:
                return ModItems.EGG_MATCHADARK.get();
            case 42:
                return ModItems.EGG_MOSS.get();
            case 43:
                return ModItems.EGG_MOSSDARK.get();
            case 44:
                return ModItems.EGG_GREENUMBER.get();
            case 45:
                return ModItems.EGG_GREYBLUE.get();
            case 46:
                return ModItems.EGG_GREYNEUTRAL.get();
            case 47:
                return ModItems.EGG_LAUREL.get();
            case 48:
                return ModItems.EGG_RESEDA.get();
            case 49:
                return ModItems.EGG_GREENPEWTER.get();
            case 50:
                return ModItems.EGG_GREYDARK.get();
            case 51:
                return ModItems.EGG_CELADON.get();
            case 52:
                return ModItems.EGG_FERN.get();
            case 53:
                return ModItems.EGG_ASPARAGUS.get();
            case 54:
                return ModItems.EGG_HUNTER.get();
            case 55:
                return ModItems.EGG_HUNTERDARK.get();
            case 56:
                return ModItems.EGG_TREEDARK.get();
            case 57:
                return ModItems.EGG_PALEBLUE.get();
            case 58:
                return ModItems.EGG_HONEYDEW.get();
            case 59:
                return ModItems.EGG_EARTH.get();
            case 60:
                return ModItems.EGG_KHAKI.get();
            case 61:
                return ModItems.EGG_GRULLO.get();
            case 62:
                return ModItems.EGG_KHAKIDARK.get();
            case 63:
                return ModItems.EGG_CAROB.get();
            case 64:
                return ModItems.EGG_COOLGREY.get();
            case 65:
                return ModItems.EGG_PINKGREY.get();
            case 66:
                return ModItems.EGG_WARMGREY.get();
            case 67:
                return ModItems.EGG_ARTICHOKE.get();
            case 68:
                return ModItems.EGG_MYRTLEGREY.get();
            case 69:
                return ModItems.EGG_RIFLE.get();
            case 70:
                return ModItems.EGG_JADE.get();
            case 71:
                return ModItems.EGG_PISTACHIO.get();
            case 72:
                return ModItems.EGG_SAGE.get();
            case 73:
                return ModItems.EGG_ROSEMARY.get();
            case 74:
                return ModItems.EGG_GREENBROWN.get();
            case 75:
                return ModItems.EGG_UMBER.get();
            case 76:
                return ModItems.EGG_WHITE.get();
            case 77:
                return ModItems.EGG_CREAMLIGHT.get();
            case 78:
                return ModItems.EGG_CREAM_SPECKLE.get();
            case 79:
                return ModItems.EGG_CREAMDARK_SPECKLE.get();
            case 80:
                return ModItems.EGG_CARMEL_SPECKLE.get();
            case 81:
                return ModItems.EGG_CARMELDARK_SPECKLE.get();
            case 82:
                return ModItems.EGG_GARNET_SPECKLE.get();
            case 83:
                return ModItems.EGG_PINKLIGHT.get();
            case 84:
                return ModItems.EGG_PINK_SPECKLE.get();
            case 85:
                return ModItems.EGG_PINKDARK_SPECKLE.get();
            case 86:
                return ModItems.EGG_CHERRY_SPECKLE.get();
            case 87:
                return ModItems.EGG_CHERRYDARK_SPECKLE.get();
            case 88:
                return ModItems.EGG_PLUM_SPECKLE.get();
            case 89:
                return ModItems.EGG_BROWNLIGHT_SPECKLE.get();
            case 90:
                return ModItems.EGG_BROWN_SPECKLE.get();
            case 91:
                return ModItems.EGG_BROWNDARK_SPECKLE.get();
            case 92:
                return ModItems.EGG_CHOCOLATE_SPECKLE.get();
            case 93:
                return ModItems.EGG_CHOCOLATEDARK_SPECKLE.get();
            case 94:
                return ModItems.EGG_CHOCOLATECOSMOS.get();
            case 95:
                return ModItems.EGG_BLUE.get();
            case 96:
                return ModItems.EGG_GREENLIGHT.get();
            case 97:
                return ModItems.EGG_GREENYELLOW_SPECKLE.get();
            case 98:
                return ModItems.EGG_OLIVELIGHT_SPECKLE.get();
            case 99:
                return ModItems.EGG_OLIVE_SPECKLE.get();
            case 100:
                return ModItems.EGG_OLIVEDARK_SPECKLE.get();
            case 101:
                return ModItems.EGG_ARMY_SPECKLE.get();
            case 102:
                return ModItems.EGG_BLUEGREY.get();
            case 103:
                return ModItems.EGG_GREY_SPECKLE.get();
            case 104:
                return ModItems.EGG_GREYGREEN_SPECKLE.get();
            case 105:
                return ModItems.EGG_AVOCADO_SPECKLE.get();
            case 106:
                return ModItems.EGG_AVOCADODARK_SPECKLE.get();
            case 107:
                return ModItems.EGG_FELDGRAU_SPECKLE.get();
            case 108:
                return ModItems.EGG_MINT_SPECKLE.get();
            case 109:
                return ModItems.EGG_GREEN_SPECKLE.get();
            case 110:
                return ModItems.EGG_GREENDARK_SPECKLE.get();
            case 111:
                return ModItems.EGG_PINE_SPECKLE.get();
            case 112:
                return ModItems.EGG_PINEDARK_SPECKLE.get();
            case 113:
                return ModItems.EGG_PINEBLACK_SPECKLE.get();
            case 114:
                return ModItems.EGG_POWDERBLUE.get();
            case 115:
                return ModItems.EGG_TEA.get();
            case 116:
                return ModItems.EGG_MATCHA_SPECKLE.get();
            case 117:
                return ModItems.EGG_MATCHADARK_SPECKLE.get();
            case 118:
                return ModItems.EGG_MOSS_SPECKLE.get();
            case 119:
                return ModItems.EGG_MOSSDARK_SPECKLE.get();
            case 120:
                return ModItems.EGG_GREENUMBER_SPECKLE.get();
            case 121:
                return ModItems.EGG_GREYBLUE.get();
            case 122:
                return ModItems.EGG_GREYNEUTRAL_SPECKLE.get();
            case 123:
                return ModItems.EGG_LAUREL_SPECKLE.get();
            case 124:
                return ModItems.EGG_RESEDA_SPECKLE.get();
            case 125:
                return ModItems.EGG_GREENPEWTER_SPECKLE.get();
            case 126:
                return ModItems.EGG_GREYDARK_SPECKLE.get();
            case 127:
                return ModItems.EGG_CELADON_SPECKLE.get();
            case 128:
                return ModItems.EGG_FERN_SPECKLE.get();
            case 129:
                return ModItems.EGG_ASPARAGUS_SPECKLE.get();
            case 130:
                return ModItems.EGG_HUNTER_SPECKLE.get();
            case 131:
                return ModItems.EGG_HUNTERDARK_SPECKLE.get();
            case 132:
                return ModItems.EGG_TREEDARK_SPECKLE.get();
            case 133:
                return ModItems.EGG_PALEBLUE.get();
            case 134:
                return ModItems.EGG_HONEYDEW.get();
            case 135:
                return ModItems.EGG_EARTH_SPECKLE.get();
            case 136:
                return ModItems.EGG_KHAKI_SPECKLE.get();
            case 137:
                return ModItems.EGG_GRULLO_SPECKLE.get();
            case 138:
                return ModItems.EGG_KHAKIDARK_SPECKLE.get();
            case 139:
                return ModItems.EGG_CAROB_SPECKLE.get();
            case 140:
                return ModItems.EGG_COOLGREY.get();
            case 141:
                return ModItems.EGG_PINKGREY_SPECKLE.get();
            case 142:
                return ModItems.EGG_WARMGREY_SPECKLE.get();
            case 143:
                return ModItems.EGG_ARTICHOKE_SPECKLE.get();
            case 144:
                return ModItems.EGG_MYRTLEGREY_SPECKLE.get();
            case 145:
                return ModItems.EGG_RIFLE_SPECKLE.get();
            case 146:
                return ModItems.EGG_JADE_SPECKLE.get();
            case 147:
                return ModItems.EGG_PISTACHIO_SPECKLE.get();
            case 148:
                return ModItems.EGG_SAGE_SPECKLE.get();
            case 149:
                return ModItems.EGG_ROSEMARY_SPECKLE.get();
            case 150:
                return ModItems.EGG_GREENBROWN_SPECKLE.get();
            case 151:
                return ModItems.EGG_UMBER_SPECKLE.get();
            case 152:
                return ModItems.EGG_WHITE.get();
            case 153:
                return ModItems.EGG_CREAMLIGHT.get();
            case 154:
                return ModItems.EGG_CREAM_SPATTER.get();
            case 155:
                return ModItems.EGG_CREAMDARK_SPATTER.get();
            case 156:
                return ModItems.EGG_CARMEL_SPATTER.get();
            case 157:
                return ModItems.EGG_CARMELDARK_SPATTER.get();
            case 158:
                return ModItems.EGG_GARNET_SPATTER.get();
            case 159:
                return ModItems.EGG_PINKLIGHT.get();
            case 160:
                return ModItems.EGG_PINK_SPATTER.get();
            case 161:
                return ModItems.EGG_PINKDARK_SPATTER.get();
            case 162:
                return ModItems.EGG_CHERRY_SPATTER.get();
            case 163:
                return ModItems.EGG_CHERRYDARK_SPATTER.get();
            case 164:
                return ModItems.EGG_PLUM_SPATTER.get();
            case 165:
                return ModItems.EGG_BROWNLIGHT_SPATTER.get();
            case 166:
                return ModItems.EGG_BROWN_SPATTER.get();
            case 167:
                return ModItems.EGG_BROWNDARK_SPATTER.get();
            case 168:
                return ModItems.EGG_CHOCOLATE_SPATTER.get();
            case 169:
                return ModItems.EGG_CHOCOLATEDARK_SPATTER.get();
            case 170:
                return ModItems.EGG_CHOCOLATECOSMOS.get();
            case 171:
                return ModItems.EGG_BLUE.get();
            case 172:
                return ModItems.EGG_GREENLIGHT.get();
            case 173:
                return ModItems.EGG_GREENYELLOW_SPATTER.get();
            case 174:
                return ModItems.EGG_OLIVELIGHT_SPATTER.get();
            case 175:
                return ModItems.EGG_OLIVE_SPATTER.get();
            case 176:
                return ModItems.EGG_OLIVEDARK_SPATTER.get();
            case 177:
                return ModItems.EGG_ARMY_SPATTER.get();
            case 178:
                return ModItems.EGG_BLUEGREY.get();
            case 179:
                return ModItems.EGG_GREY_SPATTER.get();
            case 180:
                return ModItems.EGG_GREYGREEN_SPATTER.get();
            case 181:
                return ModItems.EGG_AVOCADO_SPATTER.get();
            case 182:
                return ModItems.EGG_AVOCADODARK_SPATTER.get();
            case 183:
                return ModItems.EGG_FELDGRAU_SPATTER.get();
            case 184:
                return ModItems.EGG_MINT_SPATTER.get();
            case 185:
                return ModItems.EGG_GREEN_SPATTER.get();
            case 186:
                return ModItems.EGG_GREENDARK_SPATTER.get();
            case 187:
                return ModItems.EGG_PINE_SPATTER.get();
            case 188:
                return ModItems.EGG_PINEDARK_SPATTER.get();
            case 189:
                return ModItems.EGG_PINEBLACK_SPATTER.get();
            case 190:
                return ModItems.EGG_POWDERBLUE.get();
            case 191:
                return ModItems.EGG_TEA.get();
            case 192:
                return ModItems.EGG_MATCHA_SPATTER.get();
            case 193:
                return ModItems.EGG_MATCHADARK_SPATTER.get();
            case 194:
                return ModItems.EGG_MOSS_SPATTER.get();
            case 195:
                return ModItems.EGG_MOSSDARK_SPATTER.get();
            case 196:
                return ModItems.EGG_GREENUMBER_SPATTER.get();
            case 197:
                return ModItems.EGG_GREYBLUE.get();
            case 198:
                return ModItems.EGG_GREYNEUTRAL_SPATTER.get();
            case 199:
                return ModItems.EGG_LAUREL_SPATTER.get();
            case 200:
                return ModItems.EGG_RESEDA_SPATTER.get();
            case 201:
                return ModItems.EGG_GREENPEWTER_SPATTER.get();
            case 202:
                return ModItems.EGG_GREYDARK_SPATTER.get();
            case 203:
                return ModItems.EGG_CELADON_SPATTER.get();
            case 204:
                return ModItems.EGG_FERN_SPATTER.get();
            case 205:
                return ModItems.EGG_ASPARAGUS_SPATTER.get();
            case 206:
                return ModItems.EGG_HUNTER_SPATTER.get();
            case 207:
                return ModItems.EGG_HUNTERDARK_SPATTER.get();
            case 208:
                return ModItems.EGG_TREEDARK_SPATTER.get();
            case 209:
                return ModItems.EGG_PALEBLUE.get();
            case 210:
                return ModItems.EGG_HONEYDEW.get();
            case 211:
                return ModItems.EGG_EARTH_SPATTER.get();
            case 212:
                return ModItems.EGG_KHAKI_SPATTER.get();
            case 213:
                return ModItems.EGG_GRULLO_SPATTER.get();
            case 214:
                return ModItems.EGG_KHAKIDARK_SPATTER.get();
            case 215:
                return ModItems.EGG_CAROB_SPATTER.get();
            case 216:
                return ModItems.EGG_COOLGREY.get();
            case 217:
                return ModItems.EGG_PINKGREY_SPATTER.get();
            case 218:
                return ModItems.EGG_WARMGREY_SPATTER.get();
            case 219:
                return ModItems.EGG_ARTICHOKE_SPATTER.get();
            case 220:
                return ModItems.EGG_MYRTLEGREY_SPATTER.get();
            case 221:
                return ModItems.EGG_RIFLE_SPATTER.get();
            case 222:
                return ModItems.EGG_JADE_SPATTER.get();
            case 223:
                return ModItems.EGG_PISTACHIO_SPATTER.get();
            case 224:
                return ModItems.EGG_SAGE_SPATTER.get();
            case 225:
                return ModItems.EGG_ROSEMARY_SPATTER.get();
            case 226:
                return ModItems.EGG_GREENBROWN_SPATTER.get();
            case 227:
                return ModItems.EGG_UMBER_SPATTER.get();
            case 228:
                return ModItems.EGG_WHITE.get();
            case 229:
                return ModItems.EGG_CREAMLIGHT.get();
            case 230:
                return ModItems.EGG_CREAM_SPOT.get();
            case 231:
                return ModItems.EGG_CREAMDARK_SPOT.get();
            case 232:
                return ModItems.EGG_CARMEL_SPOT.get();
            case 233:
                return ModItems.EGG_CARMELDARK_SPOT.get();
            case 234:
                return ModItems.EGG_GARNET_SPOT.get();
            case 235:
                return ModItems.EGG_PINKLIGHT.get();
            case 236:
                return ModItems.EGG_PINK_SPOT.get();
            case 237:
                return ModItems.EGG_PINKDARK_SPOT.get();
            case 238:
                return ModItems.EGG_CHERRY_SPOT.get();
            case 239:
                return ModItems.EGG_CHERRYDARK_SPOT.get();
            case 240:
                return ModItems.EGG_PLUM_SPOT.get();
            case 241:
                return ModItems.EGG_BROWNLIGHT_SPOT.get();
            case 242:
                return ModItems.EGG_BROWN_SPOT.get();
            case 243:
                return ModItems.EGG_BROWNDARK_SPOT.get();
            case 244:
                return ModItems.EGG_CHOCOLATE_SPOT.get();
            case 245:
                return ModItems.EGG_CHOCOLATEDARK_SPOT.get();
            case 246:
                return ModItems.EGG_CHOCOLATECOSMOS.get();
            case 247:
                return ModItems.EGG_BLUE.get();
            case 248:
                return ModItems.EGG_GREENLIGHT.get();
            case 249:
                return ModItems.EGG_GREENYELLOW_SPOT.get();
            case 250:
                return ModItems.EGG_OLIVELIGHT_SPOT.get();
            case 251:
                return ModItems.EGG_OLIVE_SPOT.get();
            case 252:
                return ModItems.EGG_OLIVEDARK_SPOT.get();
            case 253:
                return ModItems.EGG_ARMY_SPOT.get();
            case 254:
                return ModItems.EGG_BLUEGREY.get();
            case 255:
                return ModItems.EGG_GREY_SPOT.get();
            case 256:
                return ModItems.EGG_GREYGREEN_SPOT.get();
            case 257:
                return ModItems.EGG_AVOCADO_SPOT.get();
            case 258:
                return ModItems.EGG_AVOCADODARK_SPOT.get();
            case 259:
                return ModItems.EGG_FELDGRAU_SPOT.get();
            case 260:
                return ModItems.EGG_MINT_SPOT.get();
            case 261:
                return ModItems.EGG_GREEN_SPOT.get();
            case 262:
                return ModItems.EGG_GREENDARK_SPOT.get();
            case 263:
                return ModItems.EGG_PINE_SPOT.get();
            case 264:
                return ModItems.EGG_PINEDARK_SPOT.get();
            case 265:
                return ModItems.EGG_PINEBLACK_SPOT.get();
            case 266:
                return ModItems.EGG_POWDERBLUE.get();
            case 267:
                return ModItems.EGG_TEA.get();
            case 268:
                return ModItems.Egg_Matcha_Spot.get();
            case 269:
                return ModItems.Egg_MatchaDark_Spot.get();
            case 270:
                return ModItems.EGG_MOSS_SPOT.get();
            case 271:
                return ModItems.EGG_MOSSDARK_SPOT.get();
            case 272:
                return ModItems.EGG_GREENUMBER_SPOT.get();
            case 273:
                return ModItems.EGG_GREYBLUE.get();
            case 274:
                return ModItems.EGG_GREYNEUTRAL_SPOT.get();
            case 275:
                return ModItems.EGG_LAUREL_SPOT.get();
            case 276:
                return ModItems.EGG_RESEDA_SPOT.get();
            case 277:
                return ModItems.EGG_GREENPEWTER_SPOT.get();
            case 278:
                return ModItems.EGG_GREYDARK_SPOT.get();
            case 279:
                return ModItems.EGG_CELADON_SPOT.get();
            case 280:
                return ModItems.EGG_FERN_SPOT.get();
            case 281:
                return ModItems.EGG_ASPARAGUS_SPOT.get();
            case 282:
                return ModItems.EGG_HUNTER_SPOT.get();
            case 283:
                return ModItems.EGG_HUNTERDARK_SPOT.get();
            case 284:
                return ModItems.EGG_TREEDARK_SPOT.get();
            case 285:
                return ModItems.EGG_PALEBLUE.get();
            case 286:
                return ModItems.EGG_HONEYDEW.get();
            case 287:
                return ModItems.EGG_EARTH_SPOT.get();
            case 288:
                return ModItems.EGG_KHAKI_SPOT.get();
            case 289:
                return ModItems.EGG_GRULLO_SPOT.get();
            case 290:
                return ModItems.EGG_KHAKIDARK_SPOT.get();
            case 291:
                return ModItems.EGG_CAROB_SPOT.get();
            case 292:
                return ModItems.EGG_COOLGREY.get();
            case 293:
                return ModItems.EGG_PINKGREY_SPOT.get();
            case 294:
                return ModItems.EGG_WARMGREY_SPOT.get();
            case 295:
                return ModItems.EGG_ARTICHOKE_SPOT.get();
            case 296:
                return ModItems.EGG_MYRTLEGREY_SPOT.get();
            case 297:
                return ModItems.EGG_RIFLE_SPOT.get();
            case 298:
                return ModItems.EGG_JADE_SPOT.get();
            case 299:
                return ModItems.EGG_PISTACHIO_SPOT.get();
            case 300:
                return ModItems.EGG_SAGE_SPOT.get();
            case 301:
                return ModItems.EGG_ROSEMARY_SPOT.get();
            case 302:
                return ModItems.EGG_GREENBROWN_SPOT.get();
            case 303:
                return ModItems.EGG_UMBER_SPOT.get();
        }

        //TODO set up exception handling and put an exception here we should NEVER generate here.
        return null;
    }

    public void setFertile(){
        this.gestationTimer = 96000;
    }

}

