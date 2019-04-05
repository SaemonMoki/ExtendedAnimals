package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCarrot;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static mokiyoki.enhancedanimals.util.handlers.RegistryHandler.ENHANCED_RABBIT;

public class EnhancedRabbit extends EntityAnimal implements net.minecraftforge.common.IShearable {

    private static final DataParameter<Integer> COAT_LENGTH = EntityDataManager.createKey(EnhancedRabbit.class, DataSerializers.VARINT);
    private static final DataParameter<String> SHARED_GENES = EntityDataManager.<String>createKey(EnhancedRabbit.class, DataSerializers.STRING);
    private static final DataParameter<Integer> DATA_COLOR_ID = EntityDataManager.createKey(EnhancedRabbit.class, DataSerializers.VARINT);

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

    // 1 5 9
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
       "", "fur_normal.png", "fur_satin.png", "fur_angora1.png" , "fur_angora2.png", "fur_angora4.png", "fur_angora4.png"
    };

    private static final String[] RABBIT_TEXTURES_EYES = new String[] {
        "eyes_black.png", "eyes_grey.png", "eyes_albino.png", "eyes_pink.png"
    };

    private static final String[] RABBIT_TEXTURES_VIENNAEYES = new String[] {
        "", "", "", "", "", "", "", "", "eyes_blue.png", "eyes_blue.png", "eyes_blue.png", "eyes_blue.png", "eyes_bluel.png", "eyes_bluel.png", "eyes_bluel.png", "eyes_bluer.png", "eyes_bluer.png", "eyes_bluer.png",
        "", "", "", "", "", "", "", "", "eyes_albino.png", "eyes_albino.png", "eyes_albino.png", "eyes_albino.png", "eyes_redl.png", "eyes_redl.png", "eyes_redl.png", "eyes_redr.png", "eyes_redr.png", "eyes_redr.png"
    };

    private static final String[] RABBIT_TEXTURES_SKIN = new String[] {
        "skin_pink.png", "skin_brown.png", "skin_white.png"
    };

    private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Items.DANDELION_YELLOW, Items.CARROT, Items.GOLDEN_CARROT);

    private final List<String> rabbitTextures = new ArrayList<>();

    private int jumpTicks;
    private int jumpDuration;
    private boolean wasOnGround;
    private int currentMoveTypeDuration;
    private int carrotTicks;
    private String dropMeatType;

    private int maxCoatLength;
    private int currentCoatLength;
    private int timeForGrowth = 0;

    private static final int WTC = 90;
    private static final int GENES_LENGTH = 56;
    private int[] genes = new int[GENES_LENGTH];
    private int[] mateGenes = new int[GENES_LENGTH];
    private int[] mitosisGenes = new int[GENES_LENGTH];
    private int[] mateMitosisGenes = new int[GENES_LENGTH];

    public EnhancedRabbit(World worldIn) {
        super(ENHANCED_RABBIT, worldIn);
        this.setSize(0.4F, 0.5F);
        this.jumpHelper = new EnhancedRabbit.RabbitJumpHelper(this);
        this.moveHelper = new EnhancedRabbit.RabbitMoveHelper(this);
        this.setMovementSpeed(0.0D);
    }

    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIMate(this, 0.8D));
        this.tasks.addTask(3, new EntityAITempt(this, 1.0D, TEMPTATION_ITEMS, false));
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 0.6D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));

        this.tasks.addTask(1, new EnhancedRabbit.AIPanic(this, 2.2D));
        this.tasks.addTask(4, new EnhancedRabbit.AIAvoidEntity<>(this, EntityPlayer.class, 8.0F, 2.2D, 2.2D));
        this.tasks.addTask(4, new EnhancedRabbit.AIAvoidEntity<>(this, EntityWolf.class, 10.0F, 2.2D, 2.2D));
        this.tasks.addTask(4, new EnhancedRabbit.AIAvoidEntity<>(this, EntityMob.class, 4.0F, 2.2D, 2.2D));
        this.tasks.addTask(5, new EnhancedRabbit.AIRaidFarm(this));

    }

    protected float getJumpUpwardsMotion() {
        if (!this.collidedHorizontally && (!this.moveHelper.isUpdating() || !(this.moveHelper.getY() > this.posY + 0.5D))) {
            Path path = this.navigator.getPath();
            if (path != null && path.getCurrentPathIndex() < path.getCurrentPathLength()) {
                Vec3d vec3d = path.getPosition(this);
                if (vec3d.y > this.posY + 0.5D) {
                    return 0.5F;
                }
            }

            return this.moveHelper.getSpeed() <= 0.6D ? 0.2F : 0.3F;
        } else {
            return 0.5F;
        }
    }

    /**
     * Causes this entity to do an upwards motion (jumping).
     */
    protected void jump() {
        super.jump();
        double d0 = this.moveHelper.getSpeed();
        if (d0 > 0.0D) {
            double d1 = this.motionX * this.motionX + this.motionZ * this.motionZ;
            if (d1 < 0.010000000000000002D) {
                this.moveRelative(0.0F, 0.0F, 1.0F, 0.1F);
            }
        }

        if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)1);
        }

    }

    @OnlyIn(Dist.CLIENT)
    public float getJumpCompletion(float tick) {
        return this.jumpDuration == 0 ? 0.0F : ((float)this.jumpTicks + tick) / (float)this.jumpDuration;
    }

    public void setMovementSpeed(double newSpeed) {
        this.getNavigator().setSpeed(newSpeed);
        this.moveHelper.setMoveTo(this.moveHelper.getX(), this.moveHelper.getY(), this.moveHelper.getZ(), newSpeed);
    }

    public void setJumping(boolean jumping) {
        super.setJumping(jumping);
        if (jumping) {
            this.playSound(this.getJumpSound(), this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);
        }

    }

    public void startJumping() {
        this.setJumping(true);
        this.jumpDuration = 10;
        this.jumpTicks = 0;
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(SHARED_GENES, new String());
        this.dataManager.register(COAT_LENGTH, 0);
        this.dataManager.register(DATA_COLOR_ID, -1);
    }

    private void setCoatLength(int coatLength) {
        this.dataManager.set(COAT_LENGTH, coatLength);
    }

    public int getCoatLength() {
        return this.dataManager.get(COAT_LENGTH);
    }

    public void updateAITasks() {
        if (this.currentMoveTypeDuration > 0) {
            --this.currentMoveTypeDuration;
        }

        if (this.onGround) {
            if (!this.wasOnGround) {
                this.setJumping(false);
                this.checkLandingDelay();
            }

            EnhancedRabbit.RabbitJumpHelper enhancedRabbit$rabbitjumphelper = (EnhancedRabbit.RabbitJumpHelper)this.jumpHelper;
            if (!enhancedRabbit$rabbitjumphelper.getIsJumping()) {
                if (this.moveHelper.isUpdating() && this.currentMoveTypeDuration == 0) {
                    Path path = this.navigator.getPath();
                    Vec3d vec3d = new Vec3d(this.moveHelper.getX(), this.moveHelper.getY(), this.moveHelper.getZ());
                    if (path != null && path.getCurrentPathIndex() < path.getCurrentPathLength()) {
                        vec3d = path.getPosition(this);
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

    /**
     * Attempts to create sprinting particles if the entity is sprinting and not in water.
     */
    public void spawnRunningParticles() {
    }

    private void calculateRotationYaw(double x, double z) {
        this.rotationYaw = (float)(MathHelper.atan2(z - this.posZ, x - this.posX) * (double)(180F / (float)Math.PI)) - 90.0F;
    }

    private void enableJumpControl() {
        ((EnhancedRabbit.RabbitJumpHelper)this.jumpHelper).setCanJump(true);
    }

    private void disableJumpControl() {
        ((EnhancedRabbit.RabbitJumpHelper)this.jumpHelper).setCanJump(false);
    }

    private void updateMoveTypeDuration() {
        if (this.moveHelper.getSpeed() < 2.2D) {
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
    public void handleStatusUpdate(byte id) {
        if (id == 1) {
            this.createRunningParticles();
            this.jumpDuration = 10;
            this.jumpTicks = 0;
        } else {
            super.handleStatusUpdate(id);
        }

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
        if (this.jumpTicks != this.jumpDuration) {
            ++this.jumpTicks;
        } else if (this.jumpDuration != 0) {
            this.jumpTicks = 0;
            this.jumpDuration = 0;
            this.setJumping(false);
        }
        timeForGrowth++;
        if (maxCoatLength == 1){
            if (timeForGrowth >= 48000) {
                timeForGrowth = 0;
                if (maxCoatLength > currentCoatLength) {
                    currentCoatLength++;
                    setCoatLength(currentCoatLength);
                }
            }
        }else if (maxCoatLength == 2){
            if (timeForGrowth >= 24000) {
                timeForGrowth = 0;
                if (maxCoatLength > currentCoatLength) {
                    currentCoatLength++;
                    setCoatLength(currentCoatLength);
                }
            }
        }else if (maxCoatLength == 3){
            if (timeForGrowth >= 16000) {
                timeForGrowth = 0;
                if (maxCoatLength > currentCoatLength) {
                    currentCoatLength++;
                    setCoatLength(currentCoatLength);
                }
            }
        }else if (maxCoatLength == 4){
            if (timeForGrowth >= 12000) {
                timeForGrowth = 0;
                if (maxCoatLength > currentCoatLength) {
                    currentCoatLength++;
                    setCoatLength(currentCoatLength);
                }
            }
        }

    }

    public class RabbitJumpHelper extends EntityJumpHelper {
        private final EnhancedRabbit rabbit;
        private boolean canJump;

        public RabbitJumpHelper(EnhancedRabbit rabbit) {
            super(rabbit);
            this.rabbit = rabbit;
        }

        public boolean getIsJumping() {
            return this.isJumping;
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
            if (this.isJumping) {
                this.rabbit.startJumping();
                this.isJumping = false;
            }

        }
    }

    private boolean isCarrotEaten() {
        return this.carrotTicks == 0;
    }

    static class RabbitMoveHelper extends EntityMoveHelper {
        private final EnhancedRabbit rabbit;
        private double nextJumpSpeed;

        public RabbitMoveHelper(EnhancedRabbit rabbit) {
            super(rabbit);
            this.rabbit = rabbit;
        }

        public void tick() {
            if (this.rabbit.onGround && !this.rabbit.isJumping && !((EnhancedRabbit.RabbitJumpHelper)this.rabbit.jumpHelper).getIsJumping()) {
                this.rabbit.setMovementSpeed(0.0D);
            } else if (this.isUpdating()) {
                this.rabbit.setMovementSpeed(this.nextJumpSpeed);
            }

            super.tick();
        }

        /**
         * Sets the speed and location to move to
         */
        public void setMoveTo(double x, double y, double z, double speedIn) {
            if (this.rabbit.isInWater()) {
                speedIn = 1.5D;
            }

            super.setMoveTo(x, y, z, speedIn);
            if (speedIn > 0.0D) {
                this.nextJumpSpeed = speedIn;
            }

        }
    }


    static class AIAvoidEntity<T extends Entity> extends EntityAIAvoidEntity<T> {
        private final EnhancedRabbit rabbit;

        public AIAvoidEntity(EnhancedRabbit rabbit, Class<T> p_i46403_2_, float p_i46403_3_, double p_i46403_4_, double p_i46403_6_) {
            super(rabbit, p_i46403_2_, p_i46403_3_, p_i46403_4_, p_i46403_6_);
            this.rabbit = rabbit;
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            return super.shouldExecute();
        }
    }

    static class AIEvilAttack extends EntityAIAttackMelee {
        public AIEvilAttack(EnhancedRabbit rabbit) {
            super(rabbit, 1.4D, true);
        }

        protected double getAttackReachSqr(EntityLivingBase attackTarget) {
            return (double)(4.0F + attackTarget.width);
        }
    }

    static class AIPanic extends EntityAIPanic {
        private final EnhancedRabbit rabbit;

        public AIPanic(EnhancedRabbit rabbit, double speedIn) {
            super(rabbit, speedIn);
            this.rabbit = rabbit;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            super.tick();
            this.rabbit.setMovementSpeed(this.speed);
        }
    }

    static class AIRaidFarm extends EntityAIMoveToBlock {
        private final EnhancedRabbit rabbit;
        private boolean wantsToRaid;
        private boolean canRaid;

        public AIRaidFarm(EnhancedRabbit rabbitIn) {
            super(rabbitIn, (double)0.7F, 16);
            this.rabbit = rabbitIn;
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            if (this.runDelay <= 0) {
                if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.rabbit.world, this.rabbit)) {
                    return false;
                }

                this.canRaid = false;
                this.wantsToRaid = this.rabbit.isCarrotEaten();
                this.wantsToRaid = true;
            }

            return super.shouldExecute();
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            return this.canRaid && super.shouldContinueExecuting();
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            super.tick();
            this.rabbit.getLookHelper().setLookPosition((double)this.destinationBlock.getX() + 0.5D, (double)(this.destinationBlock.getY() + 1), (double)this.destinationBlock.getZ() + 0.5D, 10.0F, (float)this.rabbit.getVerticalFaceSpeed());
            if (this.getIsAboveDestination()) {
                World world = this.rabbit.world;
                BlockPos blockpos = this.destinationBlock.up();
                IBlockState iblockstate = world.getBlockState(blockpos);
                Block block = iblockstate.getBlock();
                if (this.canRaid && block instanceof BlockCarrot) {
                    Integer integer = iblockstate.get(BlockCarrot.AGE);
                    if (integer == 0) {
                        world.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 2);
                        world.destroyBlock(blockpos, true);
                    } else {
                        world.setBlockState(blockpos, iblockstate.with(BlockCarrot.AGE, Integer.valueOf(integer - 1)), 2);
                        world.playEvent(2001, blockpos, Block.getStateId(iblockstate));
                    }

                    this.rabbit.carrotTicks = 40;
                }

                this.canRaid = false;
                this.runDelay = 10;
            }

        }

        /**
         * Return true to set given position as destination
         */
        protected boolean shouldMoveTo(IWorldReaderBase worldIn, BlockPos pos) {
            Block block = worldIn.getBlockState(pos).getBlock();
            if (block == Blocks.FARMLAND && this.wantsToRaid && !this.canRaid) {
                pos = pos.up();
                IBlockState iblockstate = worldIn.getBlockState(pos);
                block = iblockstate.getBlock();
                if (block instanceof BlockCarrot && ((BlockCarrot)block).isMaxAge(iblockstate)) {
                    this.canRaid = true;
                    return true;
                }
            }

            return false;
        }
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() {

        if (!this.world.isRemote) {

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
                if (size <= 0.8F) {
                    dropMeatType = "rawrabbit_small";
                } else {
                    dropMeatType = "rawrabbit";
                }
        }

        return new ResourceLocation(Reference.MODID, "enhanced_rabbit");
    }

    public String getDropMeatType() {
        return dropMeatType;
    }

    protected SoundEvent getJumpSound() {
        return SoundEvents.ENTITY_RABBIT_JUMP;
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

    @Override
    public boolean isShearable(ItemStack item, net.minecraft.world.IWorldReader world, BlockPos pos) {
        if (currentCoatLength >=1) {
            return true;
        }
        return false;
    }

    @Override
    public java.util.List<ItemStack> onSheared(ItemStack item, net.minecraft.world.IWorld world, BlockPos pos, int fortune) {
        java.util.List<ItemStack> ret = new java.util.ArrayList<>();
        if (!this.world.isRemote) {
            if (currentCoatLength == 1) {
                int i = this.rand.nextInt(3);
                if (i>2){
                    ret.add(new ItemStack(Blocks.WHITE_WOOL));
                }
            } else if (currentCoatLength == 2) {
                int i = this.rand.nextInt(1);
                if (i>0){
                    ret.add(new ItemStack(Blocks.WHITE_WOOL));
                }
            } else if (currentCoatLength == 3) {
                int i = this.rand.nextInt(3);
                if (i>0){
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

    public EntityAgeable createChild(EntityAgeable ageable) {
        this.mateGenes = ((EnhancedRabbit) ageable).getGenes();
        mixMateMitosisGenes();
        mixMitosisGenes();
        EnhancedRabbit enhancedrabbit = new EnhancedRabbit(this.world);
        enhancedrabbit.setGrowingAge(0);
        int[] babyGenes = getBunnyGenes();
        enhancedrabbit.setGenes(babyGenes);
        enhancedrabbit.setSharedGenes(babyGenes);
        enhancedrabbit.setMaxCoatLength();
        enhancedrabbit.currentCoatLength = enhancedrabbit.maxCoatLength;
        enhancedrabbit.setCoatLength(enhancedrabbit.currentCoatLength);
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

        //resets the max so we don't have to store it
        setMaxCoatLength();

    }

    @OnlyIn(Dist.CLIENT)
    public String getRabbitTexture() {
        if (this.rabbitTextures.isEmpty()) {
            this.setTexturePaths();
        }
        return this.rabbitTextures.stream().collect(Collectors.joining("/","enhanced_rabbit",""));

    }

    @OnlyIn(Dist.CLIENT)
    public String[] getVariantTexturePaths() {
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
                }

            }

                // [ "", "fur_angora1.png", "fur_normal.png", "fur_satin.png" ]
                // [ coat genes 26/27 angora, 28/29 rex, 30/31 satin ]
            if(genesForText[26] == 2 && genesForText[27] == 2){
                //angora
                if ( genesForText[26] == 2 && genesForText[27] == 2){
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
        setMaxCoatLength();
        this.currentCoatLength = this.maxCoatLength;
        setCoatLength(this.currentCoatLength);
        return livingdata;
    }

    private void setMaxCoatLength() {
        float angora = 0.0F;

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

        this.maxCoatLength = (int)angora;

    }

    private int[] createInitialGenes() {
        int[] initialGenes = new int[GENES_LENGTH];
        //TODO create biome WTC variable [hot and dry biomes, cold biomes ] WTC is neutral biomes "all others"

        //[ 0=forest wildtype, 1=cold wildtype, 2=desert wildtype, 3=extreme cold ]
        int wildType = 0;
        Biome biome = this.world.getBiome(new BlockPos(this));

        if (biome.getDefaultTemperature() < 0.5F) // cold
        {
            wildType  = 1;
        }else if (biome.getDefaultTemperature() > 0.8F) // desert
        {
            wildType = 2;
        }else if (biome.getDefaultTemperature() < 0.5F) // cold
        {
            wildType  = 3;
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
                initialGenes[5] = (ThreadLocalRandom.current().nextInt(5) + 1);
            }else if (wildType == 1 || wildType == 3){
                initialGenes[4] = (ThreadLocalRandom.current().nextInt(3) + 3);
                initialGenes[5] = (ThreadLocalRandom.current().nextInt(3) + 2);
            }else{
                initialGenes[4] = (ThreadLocalRandom.current().nextInt(5) + 1);
            }
        } else {
            if (wildType == 0){
                initialGenes[4] = (1);
                initialGenes[5] = (1);
            }else if (wildType == 1 || wildType == 3) {
                initialGenes[4] = (2);
                initialGenes[5] = (2);
            }else{
                initialGenes[4] = (1);
            }
        }
        if (wildType == 2) {
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[5] = (ThreadLocalRandom.current().nextInt(5) + 1);
            } else {
                initialGenes[5] = (1);
            }
        }

        //Dilute [ wildtype, dilute ]
        if (wildType == 1) {
            if (ThreadLocalRandom.current().nextInt(100) > WTC/2) {
                initialGenes[6] = (ThreadLocalRandom.current().nextInt(2) + 1);

            } else {
                initialGenes[6] = (1);
            }
            if (ThreadLocalRandom.current().nextInt(100) > WTC/2) {
                initialGenes[7] = (ThreadLocalRandom.current().nextInt(2) + 1);

            } else {
                initialGenes[7] = (1);
            }
        }else {
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
        }

        //E Locus [ Steel, Wildtype, Japanese Brindle, Non Extension ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[8] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            if (wildType == 0){
                initialGenes[8] = (3);
            }else if(wildType == 3) {
                initialGenes[8] = (4);
            }else {
                initialGenes[8] = (2);
            }

        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[9] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            if (wildType == 3){
                initialGenes[9] = (4);
            }else{
                initialGenes[9] = (2);
            }

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
        if (wildType == 2){
            if (ThreadLocalRandom.current().nextInt(100) > WTC/3) {
                initialGenes[20] = (ThreadLocalRandom.current().nextInt(2) + 1);

            } else {
                initialGenes[20] = (2);
            }
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[21] = (ThreadLocalRandom.current().nextInt(2) + 1);

            } else {
                initialGenes[21] = (2);
            }
        }else {
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
        }

        /**
         * Coat Genes
         */

        //Furless [ wildtype, furless]
//        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//            initialGenes[22] = (ThreadLocalRandom.current().nextInt(2) + 1);
//
//        } else {
            initialGenes[22] = (1);
//        }
//        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//            initialGenes[23] = (ThreadLocalRandom.current().nextInt(2) + 1);
//
//        } else {
            initialGenes[23] = (1);
//        }

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
        if (ThreadLocalRandom.current().nextInt(100) > WTC || wildType == 1) {
            initialGenes[26] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
                initialGenes[26] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC || wildType == 1) {
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

        //Fur Length Enhancer 1 [ normal, longer, normal]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[50] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[50] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[51] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[51] = (1);
        }

        //Fur Length Enhancer 2 [ normal, longer, longest]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[52] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[52] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[53] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[53] = (1);
        }

        //Fur Length Enhancer 3 [ shorter, normal]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[54] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            if (wildType == 1){
                initialGenes[54] = (2);
            }else {
                initialGenes[54] = (1);
            }
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[55] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            if (wildType == 1){
                initialGenes[55] = (2);
            }else {
                initialGenes[55] = (1);
            }
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
