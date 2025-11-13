package mokiyoki.enhancedanimals.blocks;

import mokiyoki.enhancedanimals.capability.nestegg.EggHolder;
import mokiyoki.enhancedanimals.capability.nestegg.NestCapabilityProvider;
import mokiyoki.enhancedanimals.config.GeneticAnimalsConfig;
import mokiyoki.enhancedanimals.entity.EnhancedBee;
import mokiyoki.enhancedanimals.entity.EnhancedTurtle;
import mokiyoki.enhancedanimals.util.Genes;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_BEE;
import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_TURTLE;

//TODO destroy block when block that it is "on" is destroyed
//TODO destroy block when all eggs have hatched

public class EnhancedBeeNestBlock extends NestBlock {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final IntegerProperty HATCH = BlockStateProperties.HATCH;
    public final int eggCapacity;

    public EnhancedBeeNestBlock(int eggCapacity, Properties properties) {
        super(properties.randomTicks());
        this.eggCapacity = eggCapacity;
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void subtractEggState(Level world, BlockPos pos, BlockState state) {
    }

    @Override
    protected void addEggState(Level world, BlockPos pos, BlockState state) {
    }

    @Override
    protected int getNumberOfEggs(BlockState state) {
        return 1;
    }

    @Override
    protected SoundEvent getEggBreakSound() {
        return SoundEvents.BEEHIVE_EXIT;
    }

    @Override
    protected boolean isEgg(Item item) {
        return false;
    }

    @Override
    protected boolean usesCapabilities() {
        return true;
    }

    public boolean hasAnalogOutputSignal(BlockState p_49618_) {
        return false;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        hatch(state, level, pos, random);
    }

    private static void hatch(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        level.playSound((Player)null, pos, SoundEvents.BEEHIVE_EXIT, SoundSource.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
        level.removeBlock(pos, false);

        List<EggHolder> eggList = level.getCapability(NestCapabilityProvider.NEST_CAP, null).orElse(new NestCapabilityProvider()).removeEggsFromNest(pos);
        int j = 1;

        if (eggList!=null) {
            for (EggHolder egg : eggList) {
                level.levelEvent(2001, pos, Block.getId(state));
                EnhancedBee bee = ENHANCED_BEE.get().create(level);
                if (egg.getGenes().getNumberOfSexlinkedGenes() < Reference.BEE_SEXLINKED_GENES_LENGTH) {
                    int length = Reference.BEE_SEXLINKED_GENES_LENGTH;
                    int oldLength = egg.getGenes().getNumberOfSexlinkedGenes();
                    int WTC = GeneticAnimalsConfig.COMMON.wildTypeChance.get();
                    Genes fixedGenetics = new Genes(length);
                    for (int e = 0; e < length; e++) {
                        fixedGenetics.setAutosomalGene(e, e < oldLength ? egg.getGenes().getAutosomalGene(e) : ThreadLocalRandom.current().nextInt(100) > WTC ? 2 : 1);
                    }
                    bee.setGenes(fixedGenetics);
                    bee.setSharedGenes(fixedGenetics);
                } else {
                    bee.setGenes(egg.getGenes());
                    bee.setSharedGenes(egg.getGenes());
                }
                bee.setSireName(egg.getSire());
                bee.setDamName(egg.getDam());
                bee.setGrowingAge();
                bee.initilizeAnimalSize();
                bee.setBirthTime();
                bee.moveTo((double) pos.getX() + 0.3D + (double) j++ * 0.2D, (double) pos.getY(), (double) pos.getZ() + 0.3D, 0.0F, 0.0F);
                level.addFreshEntity(bee);
            }
        } else {
            for (int k = 0; k < 1; k++) {
                level.levelEvent(2001, pos, Block.getId(state));
                EnhancedBee bee = ENHANCED_BEE.get().create(level);
                Genes beeGenes = bee.createInitialBreedGenes(bee.getCommandSenderWorld(), bee.blockPosition(), "WanderingTrader");
                bee.setGenes(beeGenes);
                bee.setSharedGenes(beeGenes);
                bee.setSireName("???");
                bee.setDamName("???");
                bee.setGrowingAge();
                bee.initilizeAnimalSize();
                bee.setBirthTime();
                bee.moveTo((double) pos.getX() + 0.3D + (double) j++ * 0.2D, (double) pos.getY(), (double) pos.getZ() + 0.3D, 0.0F, 0.0F);
                level.addFreshEntity(bee);
            }
        }
    }

    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return (BlockState)blockState.setValue(FACING, rotation.rotate((Direction)blockState.getValue(FACING)));
    }

    public BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.rotate(mirror.getRotation((Direction)blockState.getValue(FACING)));
    }

    public BlockState getStateForPlacement(BlockPlaceContext p_49573_) {
        return (BlockState)this.defaultBlockState().setValue(FACING, p_49573_.getNearestLookingDirection().getOpposite());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49646_) {
        p_49646_.add(new Property[]{FACING});
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader worldIn, BlockPos blockPos) {
        BlockState blockOn;
        switch (blockState.getValue(FACING)) {
            case UP -> blockOn = worldIn.getBlockState(blockPos.below());
            case NORTH -> blockOn = worldIn.getBlockState(blockPos.south());
            case SOUTH -> blockOn = worldIn.getBlockState(blockPos.north());
            case EAST -> blockOn = worldIn.getBlockState(blockPos.west());
            case WEST -> blockOn = worldIn.getBlockState(blockPos.east());
            default -> blockOn = worldIn.getBlockState(blockPos.above());
        }

        return !blockOn.isAir();
    }

    @Override
    public boolean propagatesSkylightDown(BlockState p_49928_, BlockGetter p_49929_, BlockPos p_49930_) {
        return true;
    }

    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState1, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos1) {
        boolean test = this.canSurvive(blockState,levelAccessor,blockPos);
        return this.canSurvive(blockState,levelAccessor,blockPos) ? super.updateShape(blockState, direction, blockState1, levelAccessor, blockPos, blockPos1) : Blocks.AIR.defaultBlockState();
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return Shapes.empty();
    }
}
