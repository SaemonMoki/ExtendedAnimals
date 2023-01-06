
package mokiyoki.enhancedanimals.blocks;

import mokiyoki.enhancedanimals.capability.nestegg.EggHolder;
import mokiyoki.enhancedanimals.capability.nestegg.NestCapabilityProvider;
import mokiyoki.enhancedanimals.entity.EnhancedTurtle;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_TURTLE;

public class EnhancedTurtleEggBlock extends NestBlock {
    private static final VoxelShape ONE_EGG_SHAPE = Block.box(3.0D, 0.0D, 3.0D, 12.0D, 7.0D, 12.0D);
    private static final VoxelShape MULTI_EGG_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 7.0D, 15.0D);
    public static final IntegerProperty HATCH = BlockStateProperties.HATCH;
    public static final IntegerProperty EGGS = BlockStateProperties.EGGS;

    public EnhancedTurtleEggBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HATCH, Integer.valueOf(0)).setValue(EGGS, Integer.valueOf(1)));
    }

    @Override
    protected void subtractEggState(Level world, BlockPos pos, BlockState state) {
        int i = state.getValue(EGGS);
        if (i>1) {
            state.setValue(EGGS, i - 1);
        }
    }

    @Override
    protected void addEggState(Level world, BlockPos pos, BlockState state) {
        int i = state.getValue(EGGS);
        if (i<4) {
            state.setValue(EGGS, i + 1);
        }
    }

    @Override
    protected int getNumberOfEggs(BlockState state) {
        return state.getValue(EGGS);
    }

    @Override
    protected SoundEvent getEggBreakSound() {
        return SoundEvents.TURTLE_EGG_BREAK;
    }

    public void stepOn(Level level, BlockPos pos, BlockState blockState, Entity entity) {
        this.tryTrample(level, pos, entity, 100);
        super.stepOn(level, pos, blockState, entity);
    }

    @Override
    public void fallOn(Level worldIn, BlockState blockState, BlockPos pos, Entity entityIn, float fallDistance) {
        if (!(entityIn instanceof Zombie)) {
            this.tryTrample(worldIn, pos, entityIn, 3);
        }

        super.fallOn(worldIn, blockState, pos, entityIn, fallDistance);
    }

    private void tryTrample(Level worldIn, BlockPos pos, Entity trampler, int chances) {
        if (this.canTrample(worldIn, trampler)) {
            if (!worldIn.isClientSide && worldIn.random.nextInt(chances) == 0) {
                BlockState blockstate = worldIn.getBlockState(pos);
                if (blockstate.is(ModBlocks.TURTLE_EGG.get())) {
                    this.removeOneEgg(worldIn, pos, blockstate);
                }
            }

        }
    }

    protected void removeOneEgg(Level worldIn, BlockPos pos, BlockState state) {
        removeOneEgg(worldIn, pos, state, true);
    }

    protected void removeOneEgg(Level worldIn, BlockPos pos, BlockState state, boolean removeEgg) {
        worldIn.playSound((Player)null, pos, SoundEvents.TURTLE_EGG_BREAK, SoundSource.BLOCKS, 0.7F, 0.9F + worldIn.random.nextFloat() * 0.2F);
        int i = state.getValue(EGGS);
        if (i <= 1) {
            if (removeEgg) {
                worldIn.getCapability(NestCapabilityProvider.NEST_CAP, null).orElse(new NestCapabilityProvider()).removeNestPos(pos);
            }
            worldIn.destroyBlock(pos, false);
        } else {
            if (removeEgg) {
                worldIn.getCapability(NestCapabilityProvider.NEST_CAP, null).orElse(new NestCapabilityProvider()).removeEggFromNest(pos);
            }
            worldIn.setBlock(pos, state.setValue(EGGS, Integer.valueOf(i - 1)), 2);
            worldIn.levelEvent(2001, pos, Block.getId(state));
        }
    }

    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        if (this.canGrow(level) && hasProperHabitat(level, pos)) {
            int i = state.getValue(HATCH);
            if (i < 2) {
                level.playSound((Player)null, pos, SoundEvents.TURTLE_EGG_CRACK, SoundSource.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
                level.setBlock(pos, state.setValue(HATCH, Integer.valueOf(i + 1)), 2);
            } else {
                level.playSound((Player)null, pos, SoundEvents.TURTLE_EGG_HATCH, SoundSource.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
                level.removeBlock(pos, false);

                List<EggHolder> eggList = level.getCapability(NestCapabilityProvider.NEST_CAP, null).orElse(new NestCapabilityProvider()).removeEggsFromNest(pos);
                int j = 1;

                if (eggList!=null) {
                    for (EggHolder egg : eggList) {
                        level.levelEvent(2001, pos, Block.getId(state));
                        EnhancedTurtle turtle = ENHANCED_TURTLE.get().create(level);
                        turtle.setGenes(egg.getGenes());
                        turtle.setSharedGenes(egg.getGenes());
                        turtle.setSireName(egg.getSire());
                        turtle.setDamName(egg.getDam());
                        turtle.setGrowingAge();
                        turtle.initilizeAnimalSize();
                        turtle.setBirthTime();
                        turtle.moveTo((double) pos.getX() + 0.3D + (double) j++ * 0.2D, (double) pos.getY(), (double) pos.getZ() + 0.3D, 0.0F, 0.0F);
                        turtle.setHome(pos);
                        turtle.setHasScute();
                        level.addFreshEntity(turtle);
                    }
                } else {
                    for (int k = 0; k < state.getValue(EGGS); k++) {
                        level.levelEvent(2001, pos, Block.getId(state));
                        EnhancedTurtle turtle = ENHANCED_TURTLE.get().create(level);
                        Genes turtleGenes = turtle.createInitialBreedGenes(turtle.getCommandSenderWorld(), turtle.blockPosition(), "WanderingTrader");
                        turtle.setGenes(turtleGenes);
                        turtle.setSharedGenes(turtleGenes);
                        turtle.setSireName("???");
                        turtle.setDamName("???");
                        turtle.setGrowingAge();
                        turtle.initilizeAnimalSize();
                        turtle.setBirthTime();
                        turtle.moveTo((double) pos.getX() + 0.3D + (double) j++ * 0.2D, (double) pos.getY(), (double) pos.getZ() + 0.3D, 0.0F, 0.0F);
                        turtle.setHome(pos);
                        turtle.setHasScute();
                        level.addFreshEntity(turtle);
                    }
                }
            }
        }

    }

    public static boolean hasProperHabitat(BlockGetter reader, BlockPos blockReader) {
        return isProperHabitat(reader, blockReader.below());
    }

    public static boolean isProperHabitat(BlockGetter reader, BlockPos pos) {
        return reader.getBlockState(pos).is(BlockTags.SAND);
    }

    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (hasProperHabitat(level, pos) && !level.isClientSide) {
            level.levelEvent(2005, pos, 0);
        }

    }

    private boolean canGrow(Level worldIn) {
        float f = worldIn.getTimeOfDay(1.0F);
        if ((double)f < 0.69D && (double)f > 0.65D) {
            return true;
        } else {
            return worldIn.random.nextInt(500) == 0;
        }
    }

    public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
        return useContext.getItemInHand().getItem() == this.asItem() && state.getValue(EGGS) < 4 || super.canBeReplaced(state, useContext);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos());
        return blockstate.is(this) ? super.getStateForPlacement(context).setValue(EGGS, Integer.valueOf(Math.min(4, blockstate.getValue(EGGS) + 1))) : super.getStateForPlacement(context);
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return state.getValue(EGGS) > 1 ? MULTI_EGG_SHAPE : ONE_EGG_SHAPE;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HATCH, EGGS);
    }

    private boolean canTrample(Level worldIn, Entity trampler) {
        if (!(trampler instanceof Turtle) && !(trampler instanceof Bat) && !(trampler instanceof EnhancedTurtle)) {
            if (!(trampler instanceof LivingEntity)) {
                return false;
            } else {
                return trampler instanceof Player || net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(worldIn, trampler);
            }
        } else {
            return false;
        }
    }
}
