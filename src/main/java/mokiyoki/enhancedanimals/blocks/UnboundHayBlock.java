package mokiyoki.enhancedanimals.blocks;

import com.google.common.collect.ImmutableMap;
import mokiyoki.enhancedanimals.capability.hay.HayCapabilityProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UnboundHayBlock extends FallingBlock implements SimpleWaterloggedBlock {

    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty BITES = BlockStateProperties.LEVEL_COMPOSTER;

    private static final VoxelShape HAY_BLACK = Block.box(0D, 0D, 0D, 5D, 16D, 5D);
    private static final VoxelShape HAY_GREY = Block.box(5D, 0D, 0D, 11D, 16D, 5D);
    private static final VoxelShape HAY_WHITE = Block.box(11D, 0D, 0D, 16D, 16D, 5D);
    private static final VoxelShape HAY_YELLOW = Block.box(0D, 0D, 5D, 5D, 16D, 11D);
    private static final VoxelShape HAY_FUSHIA = Block.box(5D, 0D, 5D, 11D, 16D, 11D);
    private static final VoxelShape HAY_CYAN = Block.box(11D, 0D, 5D, 16D, 16D, 11D);
    private static final VoxelShape HAY_GREEN = Block.box(0D, 0D, 11D, 5D, 16D, 16D);
    private static final VoxelShape HAY_RED = Block.box(5D, 0D, 11D, 11D, 16D, 16D);
    private static final VoxelShape HAY_BLUE = Block.box(11D, 0D, 11D, 16D, 16D, 16D);

    private static final VoxelShape X_HAY_BLACK = Block.box(0D, 0D, 0D, 16D, 5D, 5D);
    private static final VoxelShape X_HAY_GREY = Block.box(0D, 5D, 0D, 16D, 11D, 5D);
    private static final VoxelShape X_HAY_WHITE = Block.box(0D, 11D, 0D, 16D, 16D, 5D);
    private static final VoxelShape X_HAY_YELLOW = Block.box(0D, 0D, 5D, 16D, 5D, 11D);
    private static final VoxelShape X_HAY_FUSHIA = Block.box(0D, 5D, 5D, 16D, 11D, 11D);
    private static final VoxelShape X_HAY_CYAN = Block.box(0D, 11D, 5D, 16D, 16D, 11D);
    private static final VoxelShape X_HAY_GREEN = Block.box(0D, 0D, 11D, 16D, 5D, 16D);
    private static final VoxelShape X_HAY_RED = Block.box(0D, 5D, 11D, 16D, 11D, 16D);
    private static final VoxelShape X_HAY_BLUE = Block.box(0D, 11D, 11D, 16D, 16D, 16D);

    private static final VoxelShape Z_HAY_BLACK = Block.box(0D, 0D, 0D, 5D, 5D, 16D);
    private static final VoxelShape Z_HAY_GREY = Block.box(5D, 0D, 0D, 11D, 5D, 16D);
    private static final VoxelShape Z_HAY_WHITE = Block.box(11D, 0D, 0D, 16D, 5D, 16D);
    private static final VoxelShape Z_HAY_YELLOW = Block.box(0D, 5D, 0D, 5D, 11D, 16D);
    private static final VoxelShape Z_HAY_FUSHIA = Block.box(5D, 5D, 0D, 11D, 11D, 16D);
    private static final VoxelShape Z_HAY_CYAN = Block.box(11D, 5D, 0D, 16D, 11D, 16D);
    private static final VoxelShape Z_HAY_GREEN = Block.box(0D, 11D, 0D, 5D, 16D, 16D);
    private static final VoxelShape Z_HAY_RED = Block.box(5D, 11D, 0D, 11D, 16D, 16D);
    private static final VoxelShape Z_HAY_BLUE = Block.box(11D, 11D, 0D, 16D, 16D, 16D);
    
    private final Map<BlockState, VoxelShape> stateToShapeMap;

    public UnboundHayBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(BITES, Integer.valueOf(0)).setValue(WATERLOGGED, Boolean.valueOf(false)).setValue(AXIS, Direction.Axis.Y));
        this.stateToShapeMap = ImmutableMap.copyOf(this.stateDefinition.getPossibleStates().stream().collect(Collectors.toMap(Function.identity(), UnboundHayBlock::getShapeForState)));
    }

    private static VoxelShape getShapeForState(BlockState state) {
        VoxelShape voxelshape = Shapes.empty();
        Direction.Axis axis = state.getValue(AXIS);
        int bites = 9 - (state.getValue(BITES));

        switch ((Direction.Axis)state.getValue(AXIS)) {
            case X:
            default:
                if (bites > 0) {
                    voxelshape = X_HAY_BLACK;
                }
                if (bites > 1) {
                    voxelshape = Shapes.or(voxelshape, X_HAY_YELLOW);
                }
                if (bites > 2) {
                    voxelshape = Shapes.or(voxelshape, X_HAY_GREEN);
                }
                if (bites > 3) {
                    voxelshape = Shapes.or(voxelshape, X_HAY_FUSHIA);
                }
                if (bites > 4) {
                    voxelshape = Shapes.or(voxelshape, X_HAY_RED);
                }
                if (bites > 5) {
                    voxelshape = Shapes.or(voxelshape, X_HAY_GREY);
                }
                if (bites > 6) {
                    voxelshape = Shapes.or(voxelshape, X_HAY_CYAN);
                }
                if (bites > 7) {
                    voxelshape = Shapes.or(voxelshape, X_HAY_WHITE);
                }
                if (bites > 8) {
                    voxelshape = Shapes.or(voxelshape, X_HAY_BLUE);
                }
                break;
            case Z:
                if (bites > 0) {
                    voxelshape = Z_HAY_BLACK;
                }
                if (bites > 1) {
                    voxelshape = Shapes.or(voxelshape, Z_HAY_GREY);
                }
                if (bites > 2) {
                    voxelshape = Shapes.or(voxelshape, Z_HAY_WHITE);
                }
                if (bites > 3) {
                    voxelshape = Shapes.or(voxelshape, Z_HAY_FUSHIA);
                }
                if (bites > 4) {
                    voxelshape = Shapes.or(voxelshape, Z_HAY_CYAN);
                }
                if (bites > 5) {
                    voxelshape = Shapes.or(voxelshape, Z_HAY_YELLOW);
                }
                if (bites > 6) {
                    voxelshape = Shapes.or(voxelshape, Z_HAY_RED);
                }
                if (bites > 7) {
                    voxelshape = Shapes.or(voxelshape, Z_HAY_GREEN);
                }
                if (bites > 8) {
                    voxelshape = Shapes.or(voxelshape, Z_HAY_BLUE);
                }
                break;
            case Y:
                if (bites > 0) {
                    voxelshape = HAY_GREEN;
                }
                if (bites > 1) {
                    voxelshape = Shapes.or(voxelshape, HAY_RED);
                }
                if (bites > 2) {
                    voxelshape = Shapes.or(voxelshape, HAY_BLUE);
                }
                if (bites > 3) {
                    voxelshape = Shapes.or(voxelshape, HAY_FUSHIA);
                }
                if (bites > 4) {
                    voxelshape = Shapes.or(voxelshape, HAY_CYAN);
                }
                if (bites > 5) {
                    voxelshape = Shapes.or(voxelshape, HAY_YELLOW);
                }
                if (bites > 6) {
                    voxelshape = Shapes.or(voxelshape, HAY_GREY);
                }
                if (bites > 7) {
                    voxelshape = Shapes.or(voxelshape, HAY_BLACK);
                }
                if (bites > 8) {
                    voxelshape = Shapes.or(voxelshape, HAY_WHITE);
                }
                break;
        }

        return voxelshape;
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return this.stateToShapeMap.get(state);
    }

    public VoxelShape getShape(BlockState state) {
        return this.stateToShapeMap.get(state);
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        switch(rot) {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:
                switch((Direction.Axis)state.getValue(AXIS)) {
                    case X:
                        return state.setValue(AXIS, Direction.Axis.Z);
                    case Z:
                        return state.setValue(AXIS, Direction.Axis.X);
                    default:
                        return state;
                }
            default:
                return state;
        }
    }

    public void eatFromBlock(Level world, BlockState state, BlockPos pos) {
        passBiteUp(world, state, pos);
    }

    public void passBiteUp(Level world, BlockState state, BlockPos pos) {
        if (world.getBlockState(pos.above()).getBlock() instanceof UnboundHayBlock) {
            ((UnboundHayBlock) world.getBlockState(pos.above()).getBlock()).passBiteUp(world, world.getBlockState(pos.above()), pos.above());
        } else {
            int bites = state.getValue(BITES);
            if (bites < 8) {
                world.setBlockAndUpdate(pos, state.setValue(BITES, Integer.valueOf(bites + 1)).setValue(WATERLOGGED, state.getValue(WATERLOGGED)));
            } else {
                world.getCapability(HayCapabilityProvider.HAY_CAP, null).orElse(new HayCapabilityProvider()).removeHayPos(pos);
                world.removeBlock(pos, false);
            }
        }
    }

//    public void bringBitesUp(World world, BlockState state, BlockPos pos) {
//        int bites = state.get(BITES);
//        BlockState blockunder = world.getBlockState(pos.down());
//        if (blockunder.getBlock() instanceof UnboundHayBlock) {
//            int underbites = blockunder.get(BITES);
//
//
//
//        }
//    }

    @Override
    public void handlePrecipitation(BlockState blockState, Level worldIn, BlockPos pos, Biome.Precipitation precipitation) {
        if (shouldHandlePrecipitation(worldIn, precipitation)) {
            BlockState state = worldIn.getBlockState(pos);
            int i = state.getValue(BITES);
            if (i < 8) {
                worldIn.setBlock(pos, state.setValue(BITES, Integer.valueOf(i + 1)), 3);
            } else {
                worldIn.getCapability(HayCapabilityProvider.HAY_CAP, null).orElse(new HayCapabilityProvider()).removeHayPos(pos);
                worldIn.removeBlock(pos, false);
            }
        }
    }

    protected static boolean shouldHandlePrecipitation(Level world, Biome.Precipitation precipitation) {
        if (precipitation == Biome.Precipitation.RAIN) {
            return world.getRandom().nextFloat() < 0.05F;
        } else if (precipitation == Biome.Precipitation.SNOW) {
            return world.getRandom().nextFloat() < 0.1F;
        } else {
            return false;
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {

        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());

        return this.defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER)).setValue(AXIS, context.getClickedFace().getAxis());
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos pos, BlockPos facingPos) {
        if (state.getValue(WATERLOGGED)) {
            if (worldIn instanceof ServerLevel) {
                ((ServerLevel)worldIn).getCapability(HayCapabilityProvider.HAY_CAP, null).orElse(new HayCapabilityProvider()).removeHayPos(pos);
            }
            worldIn.removeBlock(pos, false);
        }

        //TODO add way to pass bites up on block update

        return super.updateShape(state, facing, facingState, worldIn, pos, facingPos);
    }

    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        if (state.getValue(WATERLOGGED)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

//    @Override
//    public BlockState rotate(BlockState state, Rotation rot) {
//        return state.with(FACING, rot.rotate(state.get(FACING)));
//    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BITES, WATERLOGGED, AXIS);
    }


    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        entityIn.setOnGround(true);

        double entityx = entityIn.getX() - pos.getX();
        double entityy = entityIn.getY() - pos.getY();
        double entityz = entityIn.getZ() - pos.getZ();

        AABB box = this.getShape(state).bounds();

        if (box.contains(entityx, entityy, entityz)) {
            entityIn.makeStuckInBlock(state, new Vec3(0.9D, (double) 0.05F, 0.9D));
            super.entityInside(state, worldIn, pos, entityIn);
        }
    }

    /**
     * Block's chance to react to a living entity falling on it.
     */
    @Override
    public void fallOn(Level worldIn, BlockState blockState, BlockPos pos, Entity entityIn, float fallDistance) {
        BlockState state = worldIn.getBlockState(pos);
        float bites = state.getValue(BITES);
        entityIn.causeFallDamage(fallDistance, 0.2F + (bites*0.1F), entityIn.damageSources().fall());
    }

    @Override
    public void wasExploded(Level worldIn, BlockPos pos, Explosion explosionIn) {
        worldIn.getCapability(HayCapabilityProvider.HAY_CAP, null).orElse(new HayCapabilityProvider()).removeHayPos(pos);
        super.wasExploded(worldIn, pos, explosionIn);
    }

    @Override
    public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
        worldIn.getCapability(HayCapabilityProvider.HAY_CAP, null).orElse(new HayCapabilityProvider()).removeHayPos(pos);
        if (!worldIn.isClientSide && !player.isCreative()) {
            int bites = state.getValue(BITES);
            ItemStack itemstack = new ItemStack(Items.WHEAT, (9-bites));
            ItemEntity itementity = new ItemEntity(worldIn, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), itemstack);
            itementity.setDefaultPickUpDelay();
            worldIn.addFreshEntity(itementity);
        }
        super.playerWillDestroy(worldIn, pos, state, player);
    }

    @Override
    public void onPlace(BlockState state, Level worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        worldIn.getCapability(HayCapabilityProvider.HAY_CAP, null).orElse(new HayCapabilityProvider()).addHayPos(pos);
        super.onPlace(state, worldIn, pos, oldState, isMoving);
    }

    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        worldIn.getCapability(HayCapabilityProvider.HAY_CAP, null).orElse(new HayCapabilityProvider()).removeHayPos(pos);
        super.onRemove(state, worldIn, pos, newState, isMoving);
    }

    //TODO add being able to add loose wheat back to the block
//    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn) {
//        int i = state.get(BITES);
//        if (i > 0 && player.getHeldItem(handIn).getItem() == Items.WHEAT) {
//            worldIn.setBlockState(pos, state.with(BITES, Integer.valueOf(i - 1)));
//            worldIn.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F + worldIn.rand.nextFloat() * 0.4F);
//            return ActionResultType.SUCCESS;
//        }
//        return ActionResultType.PASS;
//    }
}
