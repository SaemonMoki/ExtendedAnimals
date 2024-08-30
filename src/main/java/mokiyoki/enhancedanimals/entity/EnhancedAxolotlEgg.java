package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.config.GeneticAnimalsConfig;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.items.EnhancedAxolotlEggBucket;
import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Tilt;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

import java.util.List;

import static mokiyoki.enhancedanimals.blocks.GrowableDoubleHigh.HALF;
import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_AXOLOTL;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.TILT;

public class EnhancedAxolotlEgg extends Entity {
    private static final EntityDataAccessor<String> GENES = SynchedEntityData.<String>defineId(EnhancedAxolotlEgg.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> SIRE = SynchedEntityData.<String>defineId(EnhancedAxolotlEgg.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> DAM = SynchedEntityData.<String>defineId(EnhancedAxolotlEgg.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Integer> HATCH_TIME = SynchedEntityData.<Integer>defineId(EnhancedAxolotlEgg.class, EntityDataSerializers.INT);

    private boolean hasParents = false;
    public int time;
    private boolean clockwise = this.random.nextBoolean();
    private int animationTicks = this.level().isClientSide ? this.random.nextInt(500) : 0;

    public EnhancedAxolotlEgg(EntityType<? extends EnhancedAxolotlEgg> entityType, Level level) {
        super(entityType, level);
        setHatchTime(GeneticAnimalsConfig.COMMON.axolotlHatchTime.get());
    }

    public void setGenes(Genes eggGenes) {
        if (eggGenes != null) {
            this.getEntityData().set(GENES, eggGenes.getGenesAsString());
        } else {
            this.getEntityData().set(GENES, "INFERTILE");
        }
    }

    public void setGenes(String genes) {
        this.getEntityData().set(GENES, genes);
    }

    public String getGenes() {
        return this.entityData.get(GENES);
    }

    public void setParentNames(String sireName, String damName) {
        if (sireName!=null && !sireName.equals("")) {
            this.getEntityData().set(SIRE, sireName);
        }
        if (damName!=null && !damName.equals("")) {
            this.getEntityData().set(DAM, damName);
        }
    }

    public String getSire() {
        String sireName = this.entityData.get(SIRE);
        if (sireName!=null && !sireName.equals("")) {
            return sireName;
        } else {
            return "???";
        }
    }

    public String getDam() {
        String damName = this.entityData.get(DAM);
        if (damName!=null && !damName.equals("")) {
            return damName;
        } else {
            return "???";
        }
    }

    private int getHatchTime() {
        return this.entityData.get(HATCH_TIME);
    }

    public void setHatchTime(int hatchTime) {
        this.entityData.set(HATCH_TIME, hatchTime);
    }

    public boolean skipAttackInteraction(Entity entity) {
        if (entity instanceof Player player) {
            return !this.level().mayInteract(player, this.blockPosition()) || this.hurt(this.damageSources().playerAttack(player), 0.0F);
        } else {
            return false;
        }
    }

    public boolean hurt(DamageSource damageSource, float p_31716_) {
        if (this.isInvulnerableTo(damageSource)) {
            return false;
        } else {
            if (!this.isRemoved() && !this.level().isClientSide) {
                this.kill();
                this.markHurt();
            }

            return true;
        }
    }

    public InteractionResult interact(Player player, InteractionHand interactionHand) {
        if (this.level().isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            ItemStack itemStack = player.getItemInHand(interactionHand);
            if (!itemStack.isEmpty() && itemStack.is(Items.WATER_BUCKET)) {
                this.discard();
                itemStack = new ItemStack(ModItems.ENHANCED_AXOLOTL_EGG_BUCKET.get());
                EnhancedAxolotlEggBucket.setData(itemStack, this.getSire(), this.getDam(), this.getGenes(), this.getHatchTime());
                if (this.hasCustomName()) {
                    itemStack.setHoverName(this.getCustomName());
                }
                player.setItemInHand(interactionHand, itemStack);
            }

            return InteractionResult.CONSUME;
        }
    }

    protected Entity.MovementEmission getMovementEmission() {
        return Entity.MovementEmission.NONE;
    }

    public boolean isPickable() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();

        //TODO put axolotlEggAttachableToo here
        if (!onEggAttachableBlock(this.isInWater(), this.blockPosition(), this.position(), this.level())) {
            fall();
        }

        pushEntities();

        if (this.getHatchTime() == 0) {
            if (!this.level().isClientSide) {
                createAndSpawnChild();
            }
        } else if (this.getHatchTime() > 0){
            this.setHatchTime(this.getHatchTime() - 1);
        }

        if (this.level().isClientSide && this.fallDistance == 0.0F && this.isInWater()) {
            this.animationTicks++;
        }
    }

    public static boolean isEggLayableBlock(Boolean inWater, BlockState blockState) {
        return !blockState.getBlock().equals(Blocks.BIG_DRIPLEAF_STEM) && isEggAttachableBlock(inWater, blockState) && (!blockState.hasProperty(HALF) || blockState.getValue(HALF) == DoubleBlockHalf.UPPER);
    }

    public static boolean isEggAttachableBlock(Boolean inWater, BlockState blockState) {
        if (inWater) {
            Block block = blockState.getBlock();
            return Blocks.SMALL_DRIPLEAF.equals(block) || Blocks.SEAGRASS.equals(block) || Blocks.TALL_SEAGRASS.equals(block) || Blocks.BIG_DRIPLEAF.equals(block) || Blocks.BIG_DRIPLEAF_STEM.equals(block);
        }
        return false;
    }

    public boolean onEggAttachableBlock(Boolean inWater, BlockPos blockPos, Vec3 position, LevelReader level) {
        BlockGetter blockGetter = level.getChunkForCollisions(this.chunkPosition().x, this.chunkPosition().z);
        if (inWater) {
            BlockState blockState = level.getBlockState(blockPos);
            VoxelShape voxelShape = blockState.getShape(blockGetter, this.blockPosition());
            if (!voxelShape.isEmpty()) {
                Vec3 relativePos = new Vec3(position.x - blockPos.getX(), position.y - blockPos.getY(), position.z - blockPos.getZ());
                AABB shape = blockState.getShape(blockGetter, this.blockPosition()).bounds();
                return isEggAttachableBlock(true, blockState) && shape.contains(relativePos) && (!blockState.hasProperty(TILT) || (blockState.getValue(TILT) != Tilt.FULL));
            } else {
                return false;
            }
        }
        return false;
    }

    private void fall() {
        ++this.time;
        if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D,-0.04D, 0.0D));
        }

        if (this.isInWater()) {
            this.setDeltaMovement(this.getDeltaMovement().scale(0.3D));
            if (!this.onGround()) {
                Double spiralX = Math.sin(this.time*0.2F)*0.075F;
                Double spiralZ = Math.cos  (this.time*0.2F)*0.075F;
                this.setDeltaMovement(this.getDeltaMovement().add(this.clockwise ? spiralX : -spiralX, 0.0F, spiralZ));
            }
        } else {
            this.setDeltaMovement(this.getDeltaMovement().scale(0.98D));
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
    }

    protected void pushEntities() {
        List<EnhancedAxolotlEgg> list = this.level().getEntitiesOfClass(EnhancedAxolotlEgg.class, this.getBoundingBox().inflate(0.1D));
        if (!list.isEmpty()) {
            int i = this.level().getGameRules().getInt(GameRules.RULE_MAX_ENTITY_CRAMMING);
            if (i > 0 && list.size() > i - 1 && this.random.nextInt(4) == 0) {
                int j = 0;

                for(int k = 0; k < list.size(); ++k) {
                    if (!list.get(k).isPassenger()) {
                        ++j;
                    }
                }

                if (j > i - 1) {
                    this.hurt(this.damageSources().cramming(), 6.0F);
                }
            }

            for(int l = 0; l < list.size(); ++l) {
                Entity entity = list.get(l);
                entity.push(this);
            }
        }

    }

    private void createAndSpawnChild() {
        this.playSound(SoundEvents.SLIME_BLOCK_HIT, 1.0F, 1.0F);
        EnhancedAxolotl axolotl = ENHANCED_AXOLOTL.get().create(level());
        if (!this.getGenes().isEmpty() && !this.getGenes().equals("INFERTILE")) {
            axolotl.setGenes(new Genes(this.getGenes()));
            axolotl.setSharedGenes(new Genes(this.getGenes()));
        } else {
            Genes genes = axolotl.createInitialBreedGenes(axolotl.getCommandSenderWorld(), axolotl.blockPosition(), "WanderingTrader");
            axolotl.setGenes(genes);
            axolotl.setSharedGenes(genes);
        }
        axolotl.setSireName(this.getSire());
        axolotl.setDamName(this.getDam());
        axolotl.initilizeAnimalSize();
        axolotl.setAge(-axolotl.getAdultAge());
        axolotl.setBirthTime(String.valueOf(level().getGameTime()));
        axolotl.initilizeAnimalSize();
        axolotl.moveTo(this.xo,this.yo, this.zo, this.xRotO, this.yRotO);
        if (this.hasCustomName()) {
            axolotl.setCustomName(this.getCustomName());
        }
        level().addFreshEntity(axolotl);
        this.remove(RemovalReason.DISCARDED);
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(GENES, new String());
        this.getEntityData().define(SIRE, new String());
        this.getEntityData().define(DAM, new String());
        this.getEntityData().define(HATCH_TIME, -1);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        String genes = compound.getString("genes");
        this.getEntityData().set(GENES, genes);
        this.getEntityData().set(SIRE, compound.getString("SireName"));
        this.getEntityData().set(DAM, compound.getString("DamName"));
        this.hasParents = compound.getBoolean("hasParents");
        this.setHatchTime(compound.contains("HatchTime") ? compound.getInt("HatchTime") : GeneticAnimalsConfig.COMMON.axolotlHatchTime.get());
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        String genes = this.getGenes();
        if (!genes.isEmpty()) {
            compound.putString("genes", genes);
        }
        compound.putString("SireName", this.getSire());
        compound.putString("DamName", this.getDam());
        compound.putBoolean("hasParents", this.hasParents);
        compound.putInt("HatchTime", this.getHatchTime());
    }

    @OnlyIn(Dist.CLIENT)
    public int getAddAnimationTick() {
        return this.animationTicks;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
