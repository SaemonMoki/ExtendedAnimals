package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.ai.EnhancedEatPlantsGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedAvoidEntityGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedBreedGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedLookAtGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedLookRandomlyGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedTemptGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedWanderingGoal;
import mokiyoki.enhancedanimals.ai.general.GrazingGoal;
import mokiyoki.enhancedanimals.ai.general.SeekShelterGoal;
import mokiyoki.enhancedanimals.ai.general.StayShelteredGoal;
import mokiyoki.enhancedanimals.ai.rabbit.EnhancedRabbitPanicGoal;
import mokiyoki.enhancedanimals.entity.genetics.RabbitGeneticsInitialiser;
import mokiyoki.enhancedanimals.init.FoodSerialiser;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.util.Genes;
import mokiyoki.enhancedanimals.util.Reference;
import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.JumpControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static mokiyoki.enhancedanimals.init.FoodSerialiser.rabbitFoodMap;
import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_RABBIT;

public class EnhancedRabbit extends EnhancedAnimalAbstract implements net.minecraftforge.common.IForgeShearable {

    //avalible UUID spaces : [ S X X X X X 6 7 - 8 9 10 11 - 12 13 14 15 - 16 17 18 19 - 20 21 22 23 24 25 26 27 28 29 30 31 ]

    private static final EntityDataAccessor<Integer> COAT_LENGTH = SynchedEntityData.defineId(EnhancedRabbit.class, EntityDataSerializers.INT);

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

    //TODO find broken texture spawns in desert

    private int jumpTicks;
    private int jumpDuration;
    public int[] noseTwitch = {0,0,0,0}; //how long to twitch/not twitch, how fast to twitch, count up or down, twitch cycle
    private boolean wasOnGround;
    private int currentMoveTypeDuration;
    public int carrotTicks;
    private String dropMeatType;

    private int maxCoatLength;
    private int currentCoatLength;
    private int timeForGrowth = 0;

    private static final int SEXLINKED_GENES_LENGTH = 2;

    private GrazingGoal grazingGoal;

    public EnhancedRabbit(EntityType<? extends EnhancedRabbit> entityType, Level worldIn) {
        super(entityType, worldIn,SEXLINKED_GENES_LENGTH, Reference.RABBIT_AUTOSOMAL_GENES_LENGTH, true);
//        this.setSize(0.4F, 0.5F);
        this.jumpControl = new EnhancedRabbit.JumpHelperController(this);
        this.moveControl = new EnhancedRabbit.MoveHelperController(this);
        this.setMovementSpeed(0.0D);
    }

    private Map<Block, EnhancedEatPlantsGoal.EatValues> createGrazingMap() {
        Map<Block, EnhancedEatPlantsGoal.EatValues> ediblePlants = new HashMap<>();
        ediblePlants.put(Blocks.CARROTS, new EnhancedEatPlantsGoal.EatValues(4, 1, 750));
        ediblePlants.put(Blocks.BEETROOTS, new EnhancedEatPlantsGoal.EatValues(3, 1, 750));
        ediblePlants.put(Blocks.WHEAT, new EnhancedEatPlantsGoal.EatValues(2, 1, 750));
        ediblePlants.put(Blocks.AZURE_BLUET, new EnhancedEatPlantsGoal.EatValues(3, 2, 750));
        ediblePlants.put(ModBlocks.GROWABLE_AZURE_BLUET.get(), new EnhancedEatPlantsGoal.EatValues(3, 2, 750));
        ediblePlants.put(Blocks.BLUE_ORCHID, new EnhancedEatPlantsGoal.EatValues(7, 3, 375));
        ediblePlants.put(ModBlocks.GROWABLE_BLUE_ORCHID.get(), new EnhancedEatPlantsGoal.EatValues(7, 2, 375));
        ediblePlants.put(Blocks.CORNFLOWER, new EnhancedEatPlantsGoal.EatValues(7, 3, 375));
        ediblePlants.put(ModBlocks.GROWABLE_CORNFLOWER.get(), new EnhancedEatPlantsGoal.EatValues(7, 2, 375));
        ediblePlants.put(Blocks.DANDELION, new EnhancedEatPlantsGoal.EatValues(3, 2, 750));
        ediblePlants.put(ModBlocks.GROWABLE_DANDELION.get(), new EnhancedEatPlantsGoal.EatValues(3, 2, 750));
        ediblePlants.put(Blocks.OXEYE_DAISY, new EnhancedEatPlantsGoal.EatValues(7, 3, 750));
        ediblePlants.put(ModBlocks.GROWABLE_OXEYE_DAISY.get(), new EnhancedEatPlantsGoal.EatValues(7, 2, 750));
        ediblePlants.put(Blocks.ROSE_BUSH, new EnhancedEatPlantsGoal.EatValues(4, 3, 375));
        ediblePlants.put(ModBlocks.GROWABLE_ROSE_BUSH.get(), new EnhancedEatPlantsGoal.EatValues(4, 2, 375));
        ediblePlants.put(Blocks.SUNFLOWER, new EnhancedEatPlantsGoal.EatValues(4, 3, 375));
        ediblePlants.put(ModBlocks.GROWABLE_SUNFLOWER.get(), new EnhancedEatPlantsGoal.EatValues(4, 2, 375));
        ediblePlants.put(Blocks.GRASS, new EnhancedEatPlantsGoal.EatValues(1, 1, 750));
        ediblePlants.put(ModBlocks.GROWABLE_GRASS.get(), new EnhancedEatPlantsGoal.EatValues(1, 1, 750));
        ediblePlants.put(Blocks.TALL_GRASS, new EnhancedEatPlantsGoal.EatValues(1, 1, 750));
        ediblePlants.put(ModBlocks.GROWABLE_TALL_GRASS.get(), new EnhancedEatPlantsGoal.EatValues(1, 1, 750));
        ediblePlants.put(Blocks.FERN, new EnhancedEatPlantsGoal.EatValues(1, 1, 750));
        ediblePlants.put(ModBlocks.GROWABLE_FERN.get(), new EnhancedEatPlantsGoal.EatValues(1, 1, 750));
        ediblePlants.put(Blocks.LARGE_FERN, new EnhancedEatPlantsGoal.EatValues(1, 1, 750));
        ediblePlants.put(ModBlocks.GROWABLE_LARGE_FERN.get(), new EnhancedEatPlantsGoal.EatValues(1, 1, 750));
        ediblePlants.put(Blocks.SWEET_BERRY_BUSH, new EnhancedEatPlantsGoal.EatValues(1, 1, 1000));
        ediblePlants.put(Blocks.CACTUS, new EnhancedEatPlantsGoal.EatValues(1, 1, 3000));
//        ediblePlants.put(Blocks.PUMPKIN, new EnhancedEatPlantsGoal.EatValues(1, 1, 3000));
//        ediblePlants.put(Blocks.MELON, new EnhancedEatPlantsGoal.EatValues(1, 1, 3000));

        return ediblePlants;
    }

    @Override
    protected void registerGoals() {
        int napmod = this.random.nextInt(1200);
        this.grazingGoal = new GrazingGoal(this, 1.0D);
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new EnhancedRabbitPanicGoal(this, 2.2D));
        this.goalSelector.addGoal(3, new EnhancedAvoidEntityGoal<>(this, Wolf.class, 10.0F, 2.2D, 2.2D, null));
        this.goalSelector.addGoal(3, new EnhancedAvoidEntityGoal<>(this, Fox.class, 10.0F, 2.2D, 2.2D, null));
        this.goalSelector.addGoal(3, new EnhancedAvoidEntityGoal<>(this, EnhancedPig.class, 6.0F, 2.2D, 2.2D, null));
        this.goalSelector.addGoal(3, new EnhancedAvoidEntityGoal<>(this, Monster.class, 4.0F, 2.2D, 2.2D, null));
        this.goalSelector.addGoal(4, new EnhancedBreedGoal(this, 0.8D));
        this.goalSelector.addGoal(5, new EnhancedTemptGoal(this, 1.0D, 1.2D, false, Items.AIR));
        this.goalSelector.addGoal(6, new EnhancedAvoidEntityGoal<>(this, Player.class, 8.0F, 2.2D, 2.2D, null));
        this.goalSelector.addGoal(7, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(8, new StayShelteredGoal(this, 4000, 7500, napmod));
        this.goalSelector.addGoal(9, new SeekShelterGoal(this, 1.0D, 4000, 7500, napmod));
//        this.goalSelector.addGoal(9, new EnhancedRabbitRaidFarmGoal(this));
        this.goalSelector.addGoal(10, new EnhancedEatPlantsGoal(this, createGrazingMap()));
        this.goalSelector.addGoal(11, this.grazingGoal);
//        this.goalSelector.addGoal(5, new EnhancedRabbitEatPlantsGoal(this));
//        this.goalSelector.addGoal(6, new EnhancedWaterAvoidingRandomWalkingGoal(this, 0.6D));
        this.goalSelector.addGoal(12, new EnhancedWanderingGoal(this, 1.0D));
        this.goalSelector.addGoal(13, new EnhancedLookAtGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(13, new EnhancedLookAtGoal(this, Monster.class, 10.0F));
        this.goalSelector.addGoal(14, new EnhancedLookRandomlyGoal(this));
    }

    @Override
    protected FoodSerialiser.AnimalFoodMap getAnimalFoodType() {
        return rabbitFoodMap();
    }

    protected float getJumpPower() {
        if (!this.horizontalCollision && (!this.moveControl.hasWanted() || !(this.moveControl.getWantedY() > this.getY() + 0.5D))) {
            Path path = this.navigation.getPath();
            if (path != null && path.getNextNodeIndex() < path.getNodeCount()) {
                Vec3 vec3d = path.getNextEntityPos(this);
                if (vec3d.y > this.getY() + 0.5D) {
                    return 0.5F;
                }
            }

            return this.moveControl.getSpeedModifier() <= 0.6D ? 0.2F : 0.3F;
        } else {
            return 0.5F;
        }
    }

    /**
     * Causes this entity to do an upwards motion (jumping).
     */
    protected void jumpFromGround() {
        super.jumpFromGround();
        double d0 = this.moveControl.getSpeedModifier();
        if (d0 > 0.0D) {
            double d1 = this.distanceToSqr(this.getDeltaMovement());
            if (d1 < 0.01D) {
                this.moveRelative(0.1F, new Vec3(0.0D, 0.0D, 1.0D));
            }
        }

        if (!this.level.isClientSide) {
            this.level.broadcastEntityEvent(this, (byte)1);
        }

    }

    @OnlyIn(Dist.CLIENT)
    public float getJumpCompletion(float tick) {
        return this.jumpDuration == 0 ? 0.0F : ((float)this.jumpTicks + tick) / (float)this.jumpDuration;
    }

    public void setMovementSpeed(double newSpeed) {
        this.getNavigation().setSpeedModifier(newSpeed);
        this.moveControl.setWantedPosition(this.moveControl.getWantedX(), this.moveControl.getWantedY(), this.moveControl.getWantedZ(), newSpeed);
    }

    public void setJumping(boolean jumping) {
        super.setJumping(jumping);
        if (jumping) {
            this.playSound(this.getJumpSound(), this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * 0.8F);
        }

    }

    public void startJumping() {
        this.setJumping(true);
        this.jumpDuration = 10;
        this.jumpTicks = 0;
    }

    @OnlyIn(Dist.CLIENT)
    public int getNoseTwitch() { return this.noseTwitch[3]; }

    @Override
    public EntityDimensions getDimensions(Pose poseIn) {
        return EntityDimensions.scalable(0.5F, 0.5F).scale(this.getScale());
    }

    @Override
    public float getScale() {
        float size = (this.getAnimalSize() > 0.0F ? this.getAnimalSize() : 1.0F)*1.25F;
        float newbornSize = 0.35F;
        return this.isGrowing() ? (newbornSize + ((size-newbornSize) * (this.growthAmount()))) : size;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(COAT_LENGTH, 0);
//        this.dataManager.register(NOSE_WIGGLING, false);
    }

    @Override
    protected String getSpecies() { return "entity.eanimod.enhanced_rabbit"; }

    @Override
    protected int getAdultAge() { return EanimodCommonConfig.COMMON.adultAgeRabbit.get();}

    @Override
    protected int gestationConfig() {
        return EanimodCommonConfig.COMMON.gestationDaysRabbit.get();
    }

    private void setCoatLength(int coatLength) {
        this.entityData.set(COAT_LENGTH, coatLength);
    }

    public int getCoatLength() {
        return this.entityData.get(COAT_LENGTH);
    }

    public void customServerAiStep() {
        if (this.currentMoveTypeDuration > 0) {
            --this.currentMoveTypeDuration;
        }

        if (this.onGround) {
            if (!this.wasOnGround) {
                this.setJumping(false);
                this.checkLandingDelay();
            }

            EnhancedRabbit.JumpHelperController enhancedRabbit$rabbitjumphelper = (EnhancedRabbit.JumpHelperController)this.jumpControl;
            if (!enhancedRabbit$rabbitjumphelper.getIsJumping()) {
                if (this.moveControl.hasWanted() && this.currentMoveTypeDuration == 0) {
                    Path path = this.navigation.getPath();
                    Vec3 vec3d = new Vec3(this.moveControl.getWantedX(), this.moveControl.getWantedY(), this.moveControl.getWantedZ());
                    if (path != null && path.getNextNodeIndex() < path.getNodeCount()) {
                        vec3d = path.getNextEntityPos(this);
                    }

                    this.calculateRotationYaw(vec3d.x, vec3d.z);
                    this.startJumping();
                }
            } else if (!enhancedRabbit$rabbitjumphelper.canJump()) {
                this.enableJumpControl();
            }
        }

        this.wasOnGround = this.onGround;
    }

    private void calculateRotationYaw(double x, double z) {
        this.setYRot((float)(Mth.atan2(z - this.getZ(), x - this.getX()) * (double)(180F / (float)Math.PI)) - 90.0F);
    }

    private void enableJumpControl() {
        ((EnhancedRabbit.JumpHelperController)this.jumpControl).setCanJump(true);
    }

    private void disableJumpControl() {
        ((EnhancedRabbit.JumpHelperController)this.jumpControl).setCanJump(false);
    }

    private void updateMoveTypeDuration() {
        if (this.moveControl.getSpeedModifier() < 2.2D) {
            this.currentMoveTypeDuration = 10;
        } else {
            this.currentMoveTypeDuration = 1;
        }

    }

    private void checkLandingDelay() {
        this.updateMoveTypeDuration();
        this.disableJumpControl();
    }


    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
        super.handleEntityEvent(id);
        if (id == 1) {
            this.spawnSprintParticle();
            this.jumpDuration = 10;
            this.jumpTicks = 0;
        }
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 3.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D);
    }

    public void aiStep() {
        super.aiStep();
        if (this.jumpTicks != this.jumpDuration) {
            ++this.jumpTicks;
        } else if (this.jumpDuration != 0) {
            this.jumpTicks = 0;
            this.jumpDuration = 0;
            this.setJumping(false);
        }
    }

    @Override
    protected void runLivingTickClient() {
        super.runLivingTickClient();
        //how long to twitch/not twitch, how fast to twitch, twitch cycle
        if (this.sleeping) {
            if (noseTwitch[0] == 0) {
                noseTwitch[0] = -random.nextInt(900); //dont twitch nose for up to 45 seconds
            } else if (noseTwitch[0] == -1){
                noseTwitch[0] = random.nextInt(300); //twitch for up to 15 seconds
            }
            if (noseTwitch[0] > 0) {
                noseTwitch[1] = 1;
            }
        } else {
            if (noseTwitch[0] == 0) {
                noseTwitch[0] = random.nextInt(1500) - 200; //twitch nose continuously for up to a minute, may stop for up to 10 seconds;
            } else if (noseTwitch[0] > 0) {
                if (noseTwitch[0] > 900) {
                    noseTwitch[1] = 1;
                } else {
                    noseTwitch[1] = 2;
                }
            }
        }

        if (noseTwitch[0] > 0) {
            noseTwitch[0] = noseTwitch[0] - 1;
            if (noseTwitch[2] == 0) {
                if (noseTwitch[3] <= -1) {
                    noseTwitch[2] = 1;
                } else {
                    noseTwitch[3]--;
                }
            } else {
                if (noseTwitch[3] >= 1) {
                    noseTwitch[2] = 0;
                } else {
                    noseTwitch[3]++;
                }
            }
        } else {
            noseTwitch[0] = noseTwitch[0] + 1;
        }

    }

    @Override
    protected void incrementHunger() {
        if (sleeping) {
            hunger = hunger + (0.125F*getHungerModifier());
        } else {
            hunger = hunger + (0.25F*getHungerModifier());
        }
    }

    @Override
    protected void runExtraIdleTimeTick() {
        if (hunger <= 36000) {
            timeForGrowth++;
        }

        int age = this.getEnhancedAnimalAge();

        int maxcoat = age >= this.getAdultAge() ? this.maxCoatLength : (int)(this.maxCoatLength*(((float)age/(float)this.getAdultAge())));

        if (maxcoat == 1){
            if (timeForGrowth >= 48000) {
                timeForGrowth = 0;
                if (maxcoat > currentCoatLength) {
                    currentCoatLength++;
                    setCoatLength(currentCoatLength);
                }
            }
        }else if (maxcoat == 2){
            if (timeForGrowth >= 24000) {
                timeForGrowth = 0;
                if (maxcoat > currentCoatLength) {
                    currentCoatLength++;
                    setCoatLength(currentCoatLength);
                }
            }
        }else if (maxcoat == 3){
            if (timeForGrowth >= 16000) {
                timeForGrowth = 0;
                if (maxcoat > currentCoatLength) {
                    currentCoatLength++;
                    setCoatLength(currentCoatLength);
                }
            }
        }else if (maxcoat == 4){
            if (timeForGrowth >= 12000) {
                timeForGrowth = 0;
                if (maxcoat > currentCoatLength) {
                    currentCoatLength++;
                    setCoatLength(currentCoatLength);
                }
            }
        }
    }

    @Override
    protected int getNumberOfChildren() {
        int[] genes = this.genetics.getAutosomalGenes();
        float size = this.getAnimalSize();
        int kitAverage = 1;
        int kitRange = 2;

        if (size <= 0.4 ){
//                        kitAverage = 1;
            kitRange = 1;
        }else if (size <= 0.5 ){
            kitAverage = 2;
            kitRange = 1;
        }else if (size <= 0.6 ){
            kitAverage = 4;
//                        kitRange = 2;
        }else if (size <= 0.7 ){
            kitAverage = 5;
//                        kitRange = 2;
        }else if (size <= 0.8 ){
            kitAverage = 6;
            kitRange = 3;
        }else if (size <= 0.9 ){
            kitAverage = 7;
            kitRange = 3;
        }else{
            kitAverage = 8;
            kitRange = 4;
        }

        if (genes[56] == 2 && genes[57] == 2){
            if (genes[58] == 1 && genes[59] == 1){
                kitRange++;
            }
        }else{
            if (genes[58] == 2 && genes[59] == 2){
                kitRange--;
            }
        }

        if (kitRange<1) {
            kitRange = 1;
        }

        return ThreadLocalRandom.current().nextInt(kitRange)+kitAverage;
    }

    protected void createAndSpawnEnhancedChild(Level level) {
        EnhancedRabbit enhancedrabbit = ENHANCED_RABBIT.get().create(this.level);
        Genes babyGenes = new Genes(this.genetics).makeChild(this.getOrSetIsFemale(), this.mateGender, this.mateGenetics);
        defaultCreateAndSpawn(enhancedrabbit, level, babyGenes, -this.getAdultAge());
        enhancedrabbit.setMaxCoatLength();
        enhancedrabbit.currentCoatLength = 0;
        enhancedrabbit.setCoatLength(0);

        this.level.addFreshEntity(enhancedrabbit);
    }

    @Override
    protected boolean canBePregnant() {
        return true;
    }

    @Override
    protected boolean canLactate() {
        return false;
    }

    public class JumpHelperController extends JumpControl {
        private final EnhancedRabbit rabbit;
        private boolean canJump;

        public JumpHelperController(EnhancedRabbit rabbit) {
            super(rabbit);
            this.rabbit = rabbit;
        }

        public boolean getIsJumping() {
            return this.jump;
        }

        public boolean canJump() {
            return this.canJump;
        }

        public void setCanJump(boolean canJumpIn) {
            this.canJump = canJumpIn;
        }

        /**
         * Called to actually make the entity jump if isJumping is true.
         */
        public void tick() {
            if (this.jump) {
                this.rabbit.startJumping();
                this.jump = false;
            }

        }
    }

    public boolean isCarrotEaten() {
        return this.carrotTicks == 0;
    }

    static class MoveHelperController extends MoveControl {
        private final EnhancedRabbit rabbit;
        private double nextJumpSpeed;

        public MoveHelperController(EnhancedRabbit rabbit) {
            super(rabbit);
            this.rabbit = rabbit;
        }

        public void tick() {
            if (this.rabbit.onGround && !this.rabbit.jumping && !((EnhancedRabbit.JumpHelperController)this.rabbit.jumpControl).getIsJumping()) {
                this.rabbit.setMovementSpeed(0.0D);
            } else if (this.hasWanted()) {
                this.rabbit.setMovementSpeed(this.nextJumpSpeed);
            }

            super.tick();
        }

        /**
         * Sets the speed and location to move to
         */
        public void setWantedPosition(double x, double y, double z, double speedIn) {
            if (this.rabbit.isInWater()) {
                speedIn = 1.5D;
            }

            super.setWantedPosition(x, y, z, speedIn);
            if (speedIn > 0.0D) {
                this.nextJumpSpeed = speedIn;
            }

        }
    }

    @Override
    protected boolean shouldDropExperience() {
        int i = random.nextInt(100);
        if (this.getEnhancedAnimalAge()/480 >= i) {
            return true;
        } else {
            return false;
        }
    }

    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropCustomDeathLoot(source, looting, recentlyHitIn);
        int age = this.getEnhancedAnimalAge();
        float size = getAnimalSize();

        if ((((size-0.3F) * 71.4286F) + 25) > random.nextInt(100)) {
            ItemStack meatStack = new ItemStack(Items.RABBIT, 1 + looting);
            if (size <= 0.65F || age < 48000 || (size < 0.8F && (size-0.65F)/0.0015F < random.nextInt(100))) {
                //small meat
                if (isOnFire()) {
                    meatStack = new ItemStack(ModItems.COOKEDRABBIT_SMALL.get(), 1 + looting);
                } else {
                    meatStack = new ItemStack(ModItems.RAWRABBIT_SMALL.get(), 1 + looting);
                }
            } else if (isOnFire()) {
                meatStack = new ItemStack(Items.COOKED_RABBIT, 1 + looting);
            }

            this.spawnAtLocation(meatStack);
        }

        if (!this.isOnFire() && ((((size-0.3F) * 71.4286F) + 25) > random.nextInt(100))) {
            ItemStack coatStack = new ItemStack(Items.RABBIT_HIDE, 1 + looting);
            if (maxCoatLength != 0 && currentCoatLength >= 1) {
                if (currentCoatLength == 1) {
                    int i = this.random.nextInt(3);
                    if (i>2){
                        coatStack = new ItemStack(Blocks.WHITE_WOOL, 1 + looting);
                    }
                } else if (currentCoatLength == 2) {
                    int i = this.random.nextInt(1);
                    if (i>0){
                        coatStack = new ItemStack(Blocks.WHITE_WOOL, 1 + looting);
                    }
                } else if (currentCoatLength == 3) {
                    int i = this.random.nextInt(3);
                    if (i>0){
                        coatStack = new ItemStack(Blocks.WHITE_WOOL, 1 + looting);
                    }
                } else if (currentCoatLength == 4) {
                    coatStack = new ItemStack(Blocks.WHITE_WOOL, 1 + looting);
                }
            }
            this.spawnAtLocation(coatStack);
        }

        if (age > 48000 && random.nextInt(20) >= 18-looting) {
            this.spawnAtLocation(Items.RABBIT_FOOT, 1);
        }
    }

    @Override
    @Nullable
    protected ResourceLocation getDefaultLootTable() {

        if (!this.level.isClientSide) {
            if (this.getAnimalSize() <= 0.8F || this.getEnhancedAnimalAge() < 48000) {
                dropMeatType = "rawrabbit_small";
            } else {
                dropMeatType = "rawrabbit";
            }
        }

        return new ResourceLocation(Reference.MODID, "enhanced_rabbit");
    }

    public void lethalGenes(){
        int[] genes = this.genetics.getAutosomalGenes();
        if(genes[34] == 2 && genes[35] == 2) {
            this.remove(RemovalReason.KILLED);
        }
    }

    public String getDropMeatType() {
        return dropMeatType;
    }

    protected SoundEvent getJumpSound() {
        if (!this.isSilent() && this.getBells()) {
            this.playSound(SoundEvents.NOTE_BLOCK_CHIME, 1.75F, 2.5F);
        }
        return SoundEvents.RABBIT_JUMP;
    }

    protected SoundEvent getAmbientSound() {
        if (isAnimalSleeping()) {
            return null;
        }
        return SoundEvents.RABBIT_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.RABBIT_HURT;
    }

    protected SoundEvent getDeathSound()
    {
        return SoundEvents.RABBIT_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        if (!this.isSilent() && this.getBells()) {
            this.playSound(SoundEvents.NOTE_BLOCK_CHIME, 1.5F, 2.5F);
        }
    }

    @Override
    public boolean isShearable(ItemStack item, Level world, BlockPos pos) {
        return !this.level.isClientSide && currentCoatLength >= 1;
    }

    @Override
    public java.util.List<ItemStack> onSheared(Player player, ItemStack item, Level world, BlockPos pos, int fortune) {
        java.util.List<ItemStack> ret = new java.util.ArrayList<>();
        if (!this.level.isClientSide) {
            if (currentCoatLength == 1) {
                int i = this.random.nextInt(4);
                if (i==0){
                    ret.add(new ItemStack(Blocks.WHITE_WOOL));
                }
            } else if (currentCoatLength == 2) {
                int i = this.random.nextInt(2);
                if (i==0){
                    ret.add(new ItemStack(Blocks.WHITE_WOOL));
                }
            } else if (currentCoatLength == 3) {
                int i = this.random.nextInt(4);
                if (i!=0){
                    ret.add(new ItemStack(Blocks.WHITE_WOOL));
                }
            } else if (currentCoatLength == 4) {
                ret.add(new ItemStack(Blocks.WHITE_WOOL));
            }

        }
        currentCoatLength = 0;
        setCoatLength(currentCoatLength);
        return ret;
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);

        compound.putFloat("CoatLength", this.getCoatLength());
        compound.putBoolean("Pregnant", this.pregnant);
        compound.putInt("Gestation", this.gestationTimer);
    }

    /**
     * (abstract) Protected helper method to read subclass entity assets from NBT.
     */
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);

        currentCoatLength = compound.getInt("CoatLength");
        this.setCoatLength(currentCoatLength);

        this.pregnant = compound.getBoolean("Pregnant");
        this.gestationTimer = compound.getInt("Gestation");

        //resets the max so we don't have to store it
        setMaxCoatLength();

        if (!compound.getString("breed").isEmpty()) {
            int age = this.getEnhancedAnimalAge();
            this.currentCoatLength = age >= this.getAdultAge() ? this.maxCoatLength : (int)(this.maxCoatLength*(((float)age/(float)this.getAdultAge())));
            this.setCoatLength(this.currentCoatLength);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public String getTexture() {
        if (this.enhancedAnimalTextures.isEmpty()) {
            this.setTexturePaths();
        } else if (this.reload ^ this.getReloadTexture()) {
            this.reload = !this.reload;
            this.reloadTextures();
        }

        return getCompiledTextures("enhanced_rabbit");

    }
    @OnlyIn(Dist.CLIENT)
    protected void setTexturePaths() {
        if (this.getSharedGenes() != null) {
            int[] genesForText = this.getSharedGenes().getAutosomalGenes();

            int under = 0;
            int lower = 0;
            int middle = 0;
            int higher = 0;
            int top = 0;
            int dutch = 0;
            int broken = 0;
            int vienna = 0;
            int fur = 0;
            int eyes = 0;
            int vieye = 0;
            int skin = 0;

            int UNDER = 3;
            int HIGH = 3;
            int TOPS = 4;
            int shade = 0; // [ 0 = black, 1 = blue, 2 = choc, 3 = lilac ]
            int c = 0; // [ 0 = normal or chinchilla, 1 = seal, 2 = sable, 3 = himilayan ]
            // i is a random modifier
            char[] uuidArry = getStringUUID().toCharArray();


            if(genesForText[4] == 5 && genesForText[5] == 5){
                //Red Eyed White (albino)
                under = 2;
                eyes = 2;

            }else if(genesForText[14] == 2 && genesForText[15] == 2){
                //Blue Eyed White
                under = 2;
                vieye = 8;

            }else {

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

                // [ "", "fur_angora1.png", "fur_normal.png", "fur_satin.png" ]
                // [ coat genes 26/27 angora, 28/29 rex, 30/31 satin ]
            if(genesForText[26] == 2 && genesForText[27] == 2){
                //angora
                if (genesForText[50] == 1 && genesForText[51] == 1 || genesForText[50] == 3 && genesForText[51] == 3){
                    fur = 3;
                }else if ( genesForText[50] == 1 || genesForText[51] == 1 || genesForText[50] == 3 || genesForText[51] == 3){
                    fur = 4;
                }else{
                    fur = 5;
                }

                if ( genesForText[52] >= 2 && genesForText[53] >= 2){
                    fur = fur + 1;
                    if ( genesForText[52] == 3 && genesForText[53] == 3 && fur <= 5){
                        fur = fur + 1;
                    }
                }

                if ( genesForText[54] == 1 || genesForText[55] == 1 && fur >= 4){
                    fur = fur - 1;
                    if ( genesForText[54] == 1 && genesForText[55] == 1 && fur >= 4){
                        fur = fur - 1;
                    }
                }
            }else if (genesForText[28] == 1 || genesForText[29] == 1){
                if (genesForText[30] == 2 && genesForText[31] == 2){
                    //satin
                    fur = 2;
                }else {
                    //normal
                    fur = 1;
                }
            }
            //otherwise rex aka no fur filter

            addTextureToAnimal(RABBIT_TEXTURES_UNDER, under, null);
            addTextureToAnimal(RABBIT_TEXTURES_LOWER, lower, l -> l != 0);
            addTextureToAnimal(RABBIT_TEXTURES_MIDDLE, middle, m -> m != 0);
            addTextureToAnimal(RABBIT_TEXTURES_HIGHER, higher, h -> h != 0);
            addTextureToAnimal(RABBIT_TEXTURES_TOP, top, t -> t != 0);
            addTextureToAnimal(RABBIT_TEXTURES_DUTCH, dutch, d -> d != 0);
            addTextureToAnimal(RABBIT_TEXTURES_BROKEN, broken, b -> b != 0);
            addTextureToAnimal(RABBIT_TEXTURES_VIENNA, vienna, v -> v != 0);
            addTextureToAnimal(RABBIT_TEXTURES_FUR, fur, f -> f != 0);
            addTextureToAnimal(RABBIT_TEXTURES_EYES, eyes,null);
            addTextureToAnimal(RABBIT_TEXTURES_VIENNAEYES, vieye, v -> v > 7 && (v <= 17 || v >= 25));
            addTextureToAnimal(RABBIT_TEXTURES_SKIN, skin, null);

        } //if genes are not null end bracket

    } // setTexturePaths end bracket

    @Override
    protected void setAlphaTexturePaths() {
    }

    @Override
    public void initilizeAnimalSize() {
        int[] genes = this.genetics.getAutosomalGenes();
        float size = 1F; // [minimum size = 0.3 maximum size = 1]

        if (genes[46] < 5){
            size = size - 0.07F;
            if (genes[46] < 4){
                size = size - 0.07F;
                if (genes[46] < 3){
                    size = size - 0.07F;
                    if (genes[46] < 2){
                        size = size - 0.03F;
                    }
                }
            }
        }
        if (genes[46] < 5){
            size = size - 0.07F;
            if (genes[46] < 4){
                size = size - 0.07F;
                if (genes[46] < 3){
                    size = size - 0.07F;
                    if (genes[46] < 2){
                        size = size - 0.03F;
                    }
                }
            }
        }
        if (genes[48] == 3 && genes[49] == 3){
            size = size - 0.075F;
        }else if (genes[48] == 2 && genes[49] == 2){
            size = size - 0.05F;
        }else if (genes[48] == 2 || genes[49] == 2){
            size = size - 0.025F;
        }

        if (genes[34] == 2 || genes[35] == 2){
            size = 0.3F + ((size - 0.3F)/2F);
        }

        this.setAnimalSize(size);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor inWorld, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag itemNbt) {
        livingdata = commonInitialSpawnSetup(inWorld, livingdata, getAdultAge(), 30000, 80000, spawnReason);

        setInitialCoat();

        return livingdata;
    }

    @Override
    protected void initializeHealth(EnhancedAnimalAbstract animal, float health) {
//        int[] genes = animal.genetics.getAutosomalGenes();

        health = 3.0F;

        super.initializeHealth(animal, health);
    }

    private void setMaxCoatLength() {
        int[] genes = this.genetics.getAutosomalGenes();
        int angora = 0;

        if ( genes[26] == 2 && genes[27] == 2){
            if (genes[50] == 1 && genes[51] == 1 || genes[50] == 3 && genes[51] == 3){
                angora = 1;
            }else if ( genes[50] == 1 || genes[51] == 1 || genes[50] == 3 || genes[51] == 3){
                angora = 2;
            }else{
                angora = 3;
            }

            if ( genes[52] >= 2 && genes[53] >= 2){
                angora = angora + 1;
                if ( genes[52] == 3 && genes[53] == 3 && angora <= 3){
                    angora = angora + 1;
                }
            }

            if ( genes[54] == 1 || genes[55] == 1 && angora >= 2){
                angora = angora - 1;
                if ( genes[54] == 1 && genes[55] == 1 && angora >= 2){
                    angora = angora - 1;
                }
            }
        }

        this.maxCoatLength = angora;

    }

    @Override
    protected Genes createInitialGenes(LevelAccessor world, BlockPos pos, boolean isDomestic) {
        return new RabbitGeneticsInitialiser().generateNewGenetics(world, pos, isDomestic);
    }

    @Override
    public Genes createInitialBreedGenes(LevelAccessor world, BlockPos pos, String breed) {
        return new RabbitGeneticsInitialiser().generateWithBreed(world, pos, breed);
    }

    public void setInitialCoat() {
        setMaxCoatLength();
        int age = this.getEnhancedAnimalAge();
        this.currentCoatLength = age >= this.getAdultAge() ? this.maxCoatLength : (int)(this.maxCoatLength*(((float)age/(float)this.getAdultAge())));
        setCoatLength(this.currentCoatLength);
    }
}
