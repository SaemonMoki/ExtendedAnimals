package mokiyoki.enhancedanimals.blocks;

import mokiyoki.enhancedanimals.capability.nestegg.EggHolder;
import mokiyoki.enhancedanimals.entity.EnhancedAxolotl;
import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_AXOLOTL;

public class EnhancedAxolotlEggBlock extends NestBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty HATCH = BlockStateProperties.HATCH;
    public static final IntegerProperty EGGS = BlockStateProperties.EGGS;

    public EnhancedAxolotlEggBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.valueOf(true)).setValue(HATCH, Integer.valueOf(0)).setValue(EGGS, Integer.valueOf(1)));
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
        return SoundEvents.SLIME_BLOCK_HIT;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos());
        return (blockstate.is(this) ? super.getStateForPlacement(context).setValue(EGGS, Integer.valueOf(Math.min(4, blockstate.getValue(EGGS) + 1))) : super.getStateForPlacement(context)).setValue(WATERLOGGED, true);
    }

    /**
     * Performs a random tick on a block.
     */
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        if (random.nextFloat() < 0.001F) {
            int i = state.getValue(HATCH);
            if (i < 2) {
                world.playSound((Player)null, pos, SoundEvents.SLIME_SQUISH_SMALL, SoundSource.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
                world.setBlock(pos, state.setValue(HATCH, Integer.valueOf(i + 1)), 2);
            } else {
                world.playSound((Player) null, pos, SoundEvents.SLIME_SQUISH, SoundSource.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
                world.removeBlock(pos, false);

                List<EggHolder> eggList = getEggsRemoveNestCapability(world, pos);
                int j = 1;

                if (eggList!=null) {
                    for (EggHolder egg : eggList) {
                        world.levelEvent(2001, pos, Block.getId(state));
                        EnhancedAxolotl axolotl = ENHANCED_AXOLOTL.get().create(world);
                        axolotl.setGenes(egg.getGenes());
                        axolotl.setSharedGenes(egg.getGenes());
                        axolotl.setSireName(egg.getSire());
                        axolotl.setDamName(egg.getDam());
                        axolotl.setGrowingAge();
                        axolotl.initilizeAnimalSize();
                        axolotl.setBirthTime();
                        axolotl.moveTo((double) pos.getX() + 0.3D + (double) j++ * 0.2D, (double) pos.getY(), (double) pos.getZ() + 0.3D, 0.0F, 0.0F);
                        world.addFreshEntity(axolotl);
                    }
                } else {
                    for (int k = 0; k < state.getValue(EGGS); k++) {
                        world.levelEvent(2001, pos, Block.getId(state));
                        EnhancedAxolotl axolotl = ENHANCED_AXOLOTL.get().create(world);
                        Genes axolotlGenes = axolotl.createInitialBreedGenes(axolotl.getCommandSenderWorld(), axolotl.blockPosition(), "WanderingTrader");
                        axolotl.setGenes(axolotlGenes);
                        axolotl.setSharedGenes(axolotlGenes);
                        axolotl.setSireName("???");
                        axolotl.setDamName("???");
                        axolotl.setGrowingAge();
                        axolotl.initilizeAnimalSize();
                        axolotl.setBirthTime();
                        axolotl.moveTo((double) pos.getX() + 0.3D + (double) j++ * 0.2D, (double) pos.getY(), (double) pos.getZ() + 0.3D, 0.0F, 0.0F);
                        world.addFreshEntity(axolotl);
                    }
                }
            }
        }

    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, HATCH, EGGS);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }
}
