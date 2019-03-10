package mokiyoki.enhancedanimals.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static mokiyoki.enhancedanimals.util.handlers.RegistryHandler.ENHANCED_RABBIT;

public class EnhancedRabbit extends EntityAnimal {

    private static final DataParameter<String> SHARED_GENES = EntityDataManager.<String>createKey(EnhancedRabbit.class, DataSerializers.STRING);

    private static final String[] RABBIT_TEXTURES_UNDER = new String[] {
        "under_cream.png", "under_grey.png", "under_white.png"
    };

    // 1 7 13
    private static final String[] RABBIT_TEXTURES_LOWER = new String[] {
        ""
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
            "higher_agoutihimy_lilac.png", "higher_tanhimy_lilac.png", "higher_selfhimy_lilac.png"
    };

    private static final String[] RABBIT_TEXTURES_TOP = new String[] {
        "", "top_steal.png", "top_stealdark.png", "top_tan.png", "top_self.png",
        "top_steal_blue.png", "top_stealdark_blue.png", "top_tan_blue.png", "top_self_blue.png",
        "top_steal_choc.png", "top_stealdark_choc.png", "top_tan_choc.png", "top_self_choc.png",
        "top_steal_lilac.png", "top_stealdark_lilac.png", "top_tan_lilac.png", "top_self_lilac.png",
            "top_harly0.png", "top_harly1.png", "top_harly2.png", "top_harly3.png", "top_harly4.png", "top_harly5.png", "top_harly6.png", "top_harly7.png", "top_harly8.png", "top_harly9.png", "top_harlya.png", "top_harlyb.png", "top_harlyc.png", "top_harlyd.png", "top_harlye.png", "top_harlyf.png",
            "top_harly0_blue.png", "top_harly1_blue.png", "top_harly2_blue.png", "top_harly3_blue.png", "top_harly4_blue.png", "top_harly5_blue.png", "top_harly6_blue.png", "top_harly7_blue.png", "top_harly8_blue.png", "top_harly9_blue.png", "top_harlya_blue.png", "top_harlyb_blue.png", "top_harlyc_blue.png", "top_harlyd_blue.png", "top_harlye_blue.png", "top_harlyf_blue.png",
            "top_harly0_choc.png", "top_harly1_choc.png", "top_harly2_choc.png", "top_harly3_choc.png", "top_harly4_choc.png", "top_harly5_choc.png", "top_harly6_choc.png", "top_harly7_choc.png", "top_harly8_choc.png", "top_harly9_choc.png", "top_harlya_choc.png", "top_harlyb_choc.png", "top_harlyc_choc.png", "top_harlyd_choc.png", "top_harlye_choc.png", "top_harlyf_choc.png",
            "top_harly0_lilac.png", "top_harly1_lilac.png", "top_harly2_lilac.png", "top_harly3_lilac.png", "top_harly4_lilac.png", "top_harly5_lilac.png", "top_harly6_lilac.png", "top_harly7_lilac.png", "top_harly8_lilac.png", "top_harly9_lilac.png", "top_harlya_lilac.png", "top_harlyb_lilac.png", "top_harlyc_lilac.png", "top_harlyd_lilac.png", "top_harlye_lilac.png", "top_harlyf_lilac.png",
    };

    private static final String[] RABBIT_TEXTURES_DUTCH = new String[] {
        "", "dutch0.png", "dutch1.png", "dutch2.png", "dutch3.png", "dutch4.png", "dutch5.png", "dutch6.png", "dutch7.png", "dutch8.png", "dutch9.png", "dutcha.png", "dutchb.png", "dutchc.png", "dutchd.png", "dutche.png", "dutchf.png"
    };

    // higher numbers are more white
    private static final String[] RABBIT_TEXTURES_BROKEN = new String[] {
        "", "broken0.png", "broken1.png", "broken2.png", "broken3.png", "broken4.png", "broken5.png", "broken6.png", "broken7.png", "broken8.png", "broken9.png", "brokena.png", "brokenb.png", "brokenc.png", "brokend.png", "brokene.png", "brokenf.png",
            "charlie0.png", "charlie1.png", "charlie2.png", "charlie3.png", "charlie4.png", "charlie5.png", "charlie6.png", "charlie7.png", "charlie8.png", "charlie9.png", "charliea.png", "charlieb.png", "charliec.png", "charlied.png", "charliee.png", "charlief.png",
    };

    private static final String[] RABBIT_TEXTURES_VIENNA = new String[] {
            "", "vienna0.png", "vienna1.png", "vienna2.png", "vienna3.png", "vienna4.png", "vienna5.png", "vienna6.png", "vienna7.png", "vienna8.png", "vienna9.png", "viennaa.png", "viennab.png", "viennac.png", "viennad.png", "viennae.png", "viennaf.png"
    };

    private static final String[] RABBIT_TEXTURES_FUR = new String[] {
       "", "fur_angora.png", "fur_normal.png", "fur_satin.png"
    };

    private static final String[] RABBIT_TEXTURES_EYES = new String[] {
        "eyes_black.png", "eyes_grey.png", "eyes_albino.png"
    };

    private static final String[] RABBIT_TEXTURES_VIENNAEYES = new String[] {
        "", "", "", "", "", "", "", "", "eyes_blue.png", "eyes_blue.png", "eyes_blue.png", "eyes_blue.png", "eyes_bluel.png", "eyes_bluel.png", "eyes_bluel.png", "eyes_bluer.png", "eyes_bluer.png", "eyes_bluer.png"
    };

    private static final String[] RABBIT_TEXTURES_SKIN = new String[] {
        "skin_pink.png", "skin_brown.png", "skin_white.png"
    };

    private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Items.DANDELION_YELLOW, Items.CARROT, Items.GOLDEN_CARROT);

    private final List<String> rabbitTextures = new ArrayList<>();

    public float destPos;

    private static final int WTC = 90;
    private static final int GENES_LENGTH = 50;
    private int[] genes = new int[GENES_LENGTH];
    private int[] mateGenes = new int[GENES_LENGTH];
    private int[] mitosisGenes = new int[GENES_LENGTH];
    private int[] mateMitosisGenes = new int[GENES_LENGTH];

    public EnhancedRabbit(World worldIn) {
        super(ENHANCED_RABBIT, worldIn);
        this.setSize(0.4F, 0.5F);
        this.setPathPriority(PathNodeType.WATER, 0.0F);
        //TODO Add the jumping stuff
    }

    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIMate(this, 0.8D));
        this.tasks.addTask(3, new EntityAITempt(this, 1.0D, TEMPTATION_ITEMS, false));
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 0.6D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
    }

    //TODO put the rest of the jumping stuff here

    protected void registerData() {
        super.registerData();
        this.dataManager.register(SHARED_GENES, new String());
    }

    public void setSharedGenes(int[] genes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < genes.length; i++) {
            sb.append(genes[i]);
            if (i != genes.length - 1) {
                sb.append(",");
            }
        }
        this.dataManager.set(SHARED_GENES, sb.toString());
    }

    public int[] getSharedGenes() {
        String sharedGenes = ((String) this.dataManager.get(SHARED_GENES)).toString();
        if (sharedGenes.isEmpty()) {
            return null;
        }
        String[] genesToSplit = sharedGenes.split(",");
        int[] sharedGenesArray = new int[genesToSplit.length];

        for (int i = 0; i < sharedGenesArray.length; i++) {
            //parse and store each value into int[] to be returned
            sharedGenesArray[i] = Integer.parseInt(genesToSplit[i]);
        }
        return sharedGenesArray;
    }

    public float getEyeHeight()
    {
        return this.height;
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(4.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    public void livingTick()
    {
        super.livingTick();
        this.destPos = (float)((double)this.destPos + (double)(this.onGround ? -1 : 4) * 0.3D);
        this.destPos = MathHelper.clamp(this.destPos, 0.0F, 1.0F);
    }

    public void fall(float distance, float damageMultiplier)
    {

    }

    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_RABBIT_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_RABBIT_HURT;
    }

    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_RABBIT_DEATH;
    }

    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(SoundEvents.ENTITY_RABBIT_JUMP, 0.15F, 1.0F);
    }

    public boolean isBreedingItem(ItemStack stack) {
        return TEMPTATION_ITEMS.test(stack);
    }

    public EntityAgeable createChild(EntityAgeable ageable) {
        this.mateGenes = ((EnhancedRabbit) ageable).getGenes();
        mixMateMitosisGenes();
        mixMitosisGenes();
        EnhancedRabbit enhancedrabbit = new EnhancedRabbit(this.world);
        enhancedrabbit.setGrowingAge(0);
        int[] babyGenes = getBunnyGenes();
        enhancedrabbit.setGenes(babyGenes);
        enhancedrabbit.setSharedGenes(babyGenes);
        return enhancedrabbit;
    }

    public void writeAdditional(NBTTagCompound compound) {
        super.writeAdditional(compound);

        //store this rabbits's genes
        NBTTagList geneList = new NBTTagList();
        for (int i = 0; i < genes.length; i++) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setInt("Gene", genes[i]);
            geneList.add(nbttagcompound);
        }
        compound.setTag("Genes", geneList);

        //store this rabbits's mate's genes
        NBTTagList mateGeneList = new NBTTagList();
        for (int i = 0; i < mateGenes.length; i++) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setInt("Gene", mateGenes[i]);
            mateGeneList.add(nbttagcompound);
        }
        compound.setTag("FatherGenes", mateGeneList);
    }

    @OnlyIn(Dist.CLIENT)
    public String getRabbitTexture() {
        if (this.rabbitTextures.isEmpty()) {
            this.setTexturePaths();
        }
        return this.rabbitTextures.stream().collect(Collectors.joining(", ","[","]"));

    }

    @OnlyIn(Dist.CLIENT)
    public String[] getVariantTexturePaths()
    {
        if (this.rabbitTextures.isEmpty()) {
            this.setTexturePaths();
        }

        return this.rabbitTextures.stream().toArray(String[]::new);
    }

    @OnlyIn(Dist.CLIENT)
    private void setTexturePaths() {
        int[] genesForText = getSharedGenes();
        if (genesForText != null) {
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
            char[] uuidArry = getCachedUniqueIdString().toCharArray();


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
                    top = 17 + ( 16 * shade);
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

                if (genesForText[4] >= 4 && genesForText[5] >= 4){
                    eyes = 2;
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

                if (genesForText[10] == 2 || genesForText[11] == 2) {
                    //broken patterned
                    if ( Character.isDigit(uuidArry[5]) ){
                        broken = 1 + (uuidArry[5]-48);
                    } else {
                        char d = uuidArry[5];

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
                    //END OF NON GENETIC VARIATIONS
                }

//                 Wildtype+, Dark Chinchilla, Light Chinchilla, Pale Chinchilla, Himalayan, Albino
                if (genesForText[4] != 1 && genesForText[5] != 1) {
                    under = 2;
                    middle = 0;
                    if (genesForText[4] < 4 && genesForText[5] < 4) {
                        eyes = 5;
                    } else if (genesForText[4] < 2 && genesForText[5] < 2) {
                        eyes = 4;
                    }
                }
            }

                //coat genes 26 angora, 28 rex, 30 satin
            if(genesForText[28] == 2 && genesForText[29] == 2){
                //angora
                fur = 1;
            }else if (genesForText[26] == 1 || genesForText[27] == 1){
                if (genesForText[30] == 2 && genesForText[31] == 2){
                    //satin
                    fur = 3;
                }else {
                    //normal
                    fur = 2;
                }
            }
            //otherwise rex aka no fur filter




            this.rabbitTextures.add(RABBIT_TEXTURES_UNDER[under]);
//            if (lower != 0) {
//                this.rabbitTextures.add(RABBIT_TEXTURES_LOWER[lower]);
//            }
            if(middle != 0) {
                this.rabbitTextures.add(RABBIT_TEXTURES_MIDDLE[middle]);
            }
            if(higher != 0) {
                this.rabbitTextures.add(RABBIT_TEXTURES_HIGHER[higher]);
            }
            if(top != 0) {
                this.rabbitTextures.add(RABBIT_TEXTURES_TOP[top]);
            }
            if(dutch != 0) {
                this.rabbitTextures.add(RABBIT_TEXTURES_DUTCH[dutch]);
            }
            if(broken != 0) {
                this.rabbitTextures.add(RABBIT_TEXTURES_BROKEN[broken]);
            }
            if (vienna != 0){
                this.rabbitTextures.add(RABBIT_TEXTURES_VIENNA[vienna]);
            }
            if (fur != 0) {
                this.rabbitTextures.add(RABBIT_TEXTURES_FUR[fur]);
            }
            this.rabbitTextures.add(RABBIT_TEXTURES_EYES[eyes]);
            if(vieye > 7) {
                this.rabbitTextures.add(RABBIT_TEXTURES_VIENNAEYES[vieye]);
            }
            this.rabbitTextures.add(RABBIT_TEXTURES_SKIN[skin]);


        } //if genes are not null end bracket

    } // setTexturePaths end bracket

    /**
     * (abstract) Protected helper method to read subclass entity assets from NBT.
     */
    public void readAdditional(NBTTagCompound compound) {
        super.readAdditional(compound);

        NBTTagList geneList = compound.getList("Genes", 10);
        for (int i = 0; i < geneList.size(); ++i) {
            NBTTagCompound nbttagcompound = geneList.getCompound(i);
            int gene = nbttagcompound.getInt("Gene");
            genes[i] = gene;
        }

        NBTTagList mateGeneList = compound.getList("FatherGenes", 10);
        for (int i = 0; i < mateGeneList.size(); ++i) {
            NBTTagCompound nbttagcompound = mateGeneList.getCompound(i);
            int gene = nbttagcompound.getInt("Gene");
            mateGenes[i] = gene;
        }

        setSharedGenes(genes);

    }

    public void mixMitosisGenes() {
        punnetSquare(mitosisGenes, genes);
    }

    public void mixMateMitosisGenes() {
        punnetSquare(mateMitosisGenes, mateGenes);
    }

    public void punnetSquare(int[] mitosis, int[] parentGenes) {
        Random rand = new Random();
        for (int i = 0; i < genes.length; i = (i + 2)) {
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

    public int[] getBunnyGenes() {
        Random rand = new Random();
        int[] bunnyGenes = new int[GENES_LENGTH];

        for (int i = 0; i < genes.length; i++) {
            boolean thisOrMate = rand.nextBoolean();
            if (thisOrMate) {
                bunnyGenes[i] = mitosisGenes[i];
            } else {
                bunnyGenes[i] = mateMitosisGenes[i];
            }
        }

        return bunnyGenes;
    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata, @Nullable NBTTagCompound itemNbt) {
        livingdata = super.onInitialSpawn(difficulty, livingdata, itemNbt);
        int[] spawnGenes;

        if (livingdata instanceof GroupData) {
            spawnGenes = ((GroupData) livingdata).groupGenes;
        } else {
            spawnGenes = createInitialGenes();
            livingdata = new GroupData(spawnGenes);
        }

        this.genes = spawnGenes;
        setSharedGenes(genes);
        return livingdata;
    }

    private int[] createInitialGenes() {
        int[] initialGenes = new int[GENES_LENGTH];
        //TODO create biome WTC variable [hot and dry biomes, cold biomes ] WTC is neutral biomes "all others"

        //[ 0=desert wildtype, 1=cold wildtype ]
        int wildType = 0;
        Biome biome = this.world.getBiome(new BlockPos(this));

        if (biome.getDefaultTemperature() < 0.3F) // cold
        {
            wildType  = 1;
        }


/**
 * Genes List
 */

        /**
         * Colour Genes
         */

        //Agouti [ Agouti, Tan, Self ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[0] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[0] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[1] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[1] = (1);
        }

        //Brown/Chocolate [ wildtype, brown ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[2] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[2] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[3] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[3] = (1);
        }

        //Colour Completion [ Wildtype+, Dark Chinchilla, Light Chinchilla, Himalayan, Albino ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            if (wildType == 0){
                initialGenes[4] = (ThreadLocalRandom.current().nextInt(5) + 1);
            }else{
                initialGenes[4] = (ThreadLocalRandom.current().nextInt(3) + 3);
            }
        } else {
            if (wildType == 0){
                initialGenes[4] = (1);
            }else {
                initialGenes[4] = (2);
            }
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            if (wildType == 0){
                initialGenes[5] = (ThreadLocalRandom.current().nextInt(5) + 1);
            }else{
                initialGenes[5] = (ThreadLocalRandom.current().nextInt(3) + 2);
            }
        } else {
            if (wildType == 0){
                initialGenes[5] = (1);
            }else {
                initialGenes[5] = (2);
            }
        }

        //Dilute [ wildtype, dilute ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[6] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[6] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[7] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[7] = (1);
        }

        //E Locus [ Steel, Wildtype, Japanese Brindle, Non Extension ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[8] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[8] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[9] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[9] = (2);
        }

        //Spotted [ wildtype, spotted ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[10] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[10] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[11] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[11] = (1);
        }

        //Dutch [ wildtype, dutch]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[12] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[12] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[13] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[13] = (1);
        }

        //Vienna [ wildtype, vienna]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[14] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[14] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[15] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[15] = (1);
        }

        //Wideband [ wildtype, wideband]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[16] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[16] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[17] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[17] = (1);
        }

        //Silver [ wildtype, silver]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[18] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[18] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[19] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[19] = (1);
        }

        //Lutino [ wildtype, lutino]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[20] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[20] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[21] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[21] = (1);
        }

        /**
         * Coat Genes
         */

        //Furless [ wildtype, furless]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[22] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[22] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[23] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[23] = (1);
        }

        //Lion Mane [ wildtype, lion mane]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[24] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            if (wildType == 1){
                initialGenes[24] = (2);
            }else {
                initialGenes[24] = (1);
            }
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[25] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[25] = (1);
        }

        //Angora [ wildtype, angora]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[26] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            if (wildType == 1){
                initialGenes[26] = (2);
            }else {
                initialGenes[26] = (1);
            }
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[27] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[27] = (1);
        }

        //Rex [ wildtype, rex]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[28] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[28] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[29] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[29] = (1);
        }

        //Satin [ wildtype, satin]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[30] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[30] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[31] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[31] = (1);
        }

        //Waved [ wildtype, waved]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[32] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[32] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[33] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[33] = (1);
        }

        /**
         * Shape and Size Genes
         */

        //Dwarf [ wildtype, dwarf]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[34] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[34] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[35] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[35] = (1);
        }

        //Lop1 [ wildtype, halflop, lop1]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[36] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[36] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[37] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[37] = (1);
        }

        //Lop2 [ wildtype, lop2]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[38] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[38] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[39] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[39] = (1);
        }

        //long ears [ wildtype, longer ears, longest ears]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[40] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[40] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[41] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[41] = (1);
        }

        //ear length bias [ normal ears, shorter ears, longest ears ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[42] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[42] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[43] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[43] = (1);
        }

        //longer body [ normal length, longer body ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[44] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[44] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[45] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[45] = (1);
        }

        //Size tendency [ small, normal, small2, big, extra large ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[46] = (ThreadLocalRandom.current().nextInt(5) + 1);

        } else {
            initialGenes[46] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[47] = (ThreadLocalRandom.current().nextInt(5) + 1);

        } else {
            initialGenes[47] = (2);
        }

        //Size Enhancer [ big, normal, small ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[48] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[48] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[49] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[49] = (2);
        }

        return initialGenes;
    }

    public void setGenes(int[] genes) {
        this.genes = genes;
    }

    public int[] getGenes() {
        return this.genes;
    }

    public static class GroupData implements IEntityLivingData {

        public int[] groupGenes;

        public GroupData(int[] groupGenes) {
            this.groupGenes = groupGenes;
        }

    }
}
