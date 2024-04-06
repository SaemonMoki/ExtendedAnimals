package mokiyoki.enhancedanimals.blocks;

import mokiyoki.enhancedanimals.capability.egg.EggCapabilityProvider;
import mokiyoki.enhancedanimals.capability.nestegg.EggHolder;
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.items.EnhancedEgg;
import mokiyoki.enhancedanimals.tileentity.ChickenNestTileEntity;
import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_CHICKEN;

public class EnhancedChickenEggBlock extends NestBlock implements EntityBlock {
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 2.0D, 14.0D);
    protected static final VoxelShape SHAPE_EGGS = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 5.0D, 14.0D);
    protected static final VoxelShape COLLISION_SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 1.0D, 14.0D);
    public EnhancedChickenEggBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ChickenNestTileEntity(blockPos,blockState);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return COLLISION_SHAPE;
    }

    @Override
    protected void subtractEggState(Level world, BlockPos pos, BlockState state) {}

    @Override
    protected void addEggState(Level world, BlockPos pos, BlockState state) {}

    @Override
    protected int getNumberOfEggs(BlockState state) {
        return -1;
    }

    @Override
    protected SoundEvent getEggBreakSound() {
        return SoundEvents.TURTLE_EGG_BREAK;
    }

    public void stepOn(Level level, BlockPos pos, BlockState blockState, Entity entity) {
        if (!(entity instanceof EnhancedChicken chicken || entity instanceof Chicken || entity instanceof Bat || entity instanceof Parrot)) {
            this.tryTrample(level, pos, 100);
        }
        super.stepOn(level, pos, blockState, entity);
    }

    @Override
    public void fallOn(Level worldIn, BlockState blockState, BlockPos pos, Entity entity, float fallDistance) {
        if (!(entity instanceof Chicken || entity instanceof EnhancedChicken || entity instanceof Bat || entity instanceof Parrot)) {
            this.tryTrample(worldIn, pos, 3);
        }
        super.fallOn(worldIn, blockState, pos, entity, fallDistance);
    }

    private void tryTrample(Level worldIn, BlockPos pos, int chances) {
        if (!worldIn.isClientSide && worldIn.random.nextInt(chances) == 0) {
            if (worldIn.getBlockEntity(pos) instanceof ChickenNestTileEntity nestEntity) {
                if (nestEntity.isEmpty()) {
                    worldIn.removeBlockEntity(pos);
                    worldIn.removeBlock(pos, true);
                } else {
                    this.removeOneEgg(worldIn, pos, worldIn.getBlockState(pos));
                }
            }
        }
    }

    @Override
    protected void removeOneEgg(Level worldIn, BlockPos pos, BlockState state) {
        worldIn.playSound((Player)null, pos, getEggBreakSound(), SoundSource.BLOCKS, 0.7F, 0.9F + worldIn.random.nextFloat() * 0.2F);
        if (worldIn.getBlockEntity(pos) instanceof ChickenNestTileEntity nestEntity) {
            nestEntity.removeItem(nestEntity.getEggCount()-1, 1);
        }
    }

    protected void removeOneEgg(Level worldIn, BlockPos pos, BlockState state, boolean removeEgg) {
        worldIn.playSound((Player)null, pos, getEggBreakSound(), SoundSource.BLOCKS, 0.7F, 0.9F + worldIn.random.nextFloat() * 0.2F);
        if (worldIn.getBlockEntity(pos) instanceof ChickenNestTileEntity nestEntity) {
            nestEntity.removeItem(nestEntity.getEggCount()-1, 1);
        }
    }

    public static void hatchEggs(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        if (level.getBlockEntity(pos) instanceof ChickenNestTileEntity nestEntity && !nestEntity.isEmpty()) {
            level.playSound((Player) null, pos, SoundEvents.TURTLE_EGG_HATCH, SoundSource.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);

            while (!nestEntity.isEmpty()) {
                ItemStack eggStack = nestEntity.removeItem(nestEntity.getSlotWithEgg(),1);
                EggHolder egg = eggStack.getCapability(EggCapabilityProvider.EGG_CAP, null).orElse(null).getEggHolder(eggStack);

                if (egg.hasParents()) {
                    if (egg.getGenes()!=null) {
                        level.levelEvent(2001, pos, Block.getId(state));
                        EnhancedChicken chicken = ENHANCED_CHICKEN.get().create(level);
                        chicken.setGenes(egg.getGenes());
                        chicken.setSharedGenes(egg.getGenes());
                        chicken.setSireName(egg.getSire());
                        chicken.setDamName(egg.getDam());
                        chicken.setGrowingAge();
                        chicken.initilizeAnimalSize();
                        chicken.setBirthTime();
                        chicken.moveTo((double) pos.getX() + 0.3D + (random.nextFloat()*0.4D), (double) pos.getY(), (double) pos.getZ() + 0.3D + (random.nextFloat()*0.4D), 0.0F, 0.0F);
                        level.addFreshEntity(chicken);
                    }
                } else {
                    level.levelEvent(2001, pos, Block.getId(state));
                    EnhancedChicken chicken = ENHANCED_CHICKEN.get().create(level);
                    Genes chickenGenes = chicken.createInitialBreedGenes(chicken.getCommandSenderWorld(), chicken.blockPosition(), "WanderingTrader");
                    chicken.setGenes(chickenGenes);
                    chicken.setSharedGenes(chickenGenes);
                    chicken.setSireName("???");
                    chicken.setDamName("???");
                    chicken.setGrowingAge();
                    chicken.initilizeAnimalSize();
                    chicken.setBirthTime();
                    chicken.moveTo((double) pos.getX() + 0.3D + (random.nextFloat()*0.4D), (double) pos.getY(), (double) pos.getZ() + 0.5D + (random.nextFloat()*0.4D), 0.0F, 0.0F);
                    level.addFreshEntity(chicken);
                }
            }
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        ChickenNestTileEntity nestEntity = (ChickenNestTileEntity) level.getBlockEntity(blockPos);
        if (nestEntity!=null) {
            if (itemStack.isEmpty() && !nestEntity.isEmpty()) {
                player.setItemInHand(interactionHand, nestEntity.removeItem(nestEntity.getSlotWithEgg(), 1));
                level.playSound(player, player.getX(), player.getY(),player.getZ(), SoundEvents.CHICKEN_EGG, SoundSource.NEUTRAL, 1.0F, 1.0F);
                return InteractionResult.sidedSuccess(level.isClientSide);
            } else if (itemStack.getItem() instanceof EnhancedEgg && !nestEntity.isFull()) {
                nestEntity.addEggToNest(itemStack);
                level.playSound(player, player.getX(), player.getY(),player.getZ(), SoundEvents.CHICKEN_EGG, SoundSource.NEUTRAL, 1.0F, 1.0F);
                player.setItemInHand(interactionHand, new ItemStack(Items.AIR));
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }

        return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
    }

    public boolean isPathfindable(BlockState blockState, BlockGetter p_53307_, BlockPos blockPos, PathComputationType p_53309_) {
        return p_53309_ == PathComputationType.LAND;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext context) {
        if (blockGetter.getBlockEntity(blockPos) instanceof ChickenNestTileEntity nestTileEntity) {
            return nestTileEntity.isEmpty()?SHAPE:SHAPE_EGGS;
        }
        return SHAPE;
    }



    @Override
    public boolean hasAnalogOutputSignal(BlockState blockState) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos blockPos) {
        ChickenNestTileEntity nestEntity = (ChickenNestTileEntity) level.getBlockEntity(blockPos);
        if (nestEntity != null) {
            return nestEntity.getEggCount();
        }
        return 0;
    }
}
