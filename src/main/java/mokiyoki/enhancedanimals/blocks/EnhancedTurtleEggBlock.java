package mokiyoki.enhancedanimals.blocks;

import mokiyoki.enhancedanimals.capability.egg.EggCapabilityProvider;
import mokiyoki.enhancedanimals.capability.turtleegg.EggHolder;
import mokiyoki.enhancedanimals.capability.turtleegg.NestCapabilityProvider;
import mokiyoki.enhancedanimals.entity.EnhancedTurtle;
import mokiyoki.enhancedanimals.items.EnhancedEgg;
import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_TURTLE;

public class EnhancedTurtleEggBlock extends NestBlock {
    private static final VoxelShape ONE_EGG_SHAPE = Block.box(3.0D, 0.0D, 3.0D, 12.0D, 7.0D, 12.0D);
    private static final VoxelShape MULTI_EGG_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 7.0D, 15.0D);
    public static final IntegerProperty HATCH = BlockStateProperties.HATCH;
    public static final IntegerProperty EGGS = BlockStateProperties.EGGS;

    public EnhancedTurtleEggBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HATCH, Integer.valueOf(0)).setValue(EGGS, Integer.valueOf(1)));
    }

    /**
     * Called when the given entity walks on this Block
     */
    public void stepOn(Level level, BlockPos pos, BlockState blockState, Entity entity) {
        this.tryTrample(level, pos, entity, 100);
        super.stepOn(level, pos, blockState, entity);
    }

    /**
     * Block's chance to react to a living entity falling on it.
     */
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
                if (blockstate.is(Blocks.TURTLE_EGG)) {
                    this.removeOneEgg(worldIn, pos, blockstate);
                }
            }

        }
    }

    protected void removeOneEgg(Level worldIn, BlockPos pos, BlockState state) {
        removeOneEgg(worldIn, pos, state, true);
    }

    private void removeOneEgg(Level worldIn, BlockPos pos, BlockState state, boolean removeEgg) {
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

    /**
     * Performs a random tick on a block.
     */
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

    public void onPlace(BlockState state, Level worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (hasProperHabitat(worldIn, pos) && !worldIn.isClientSide) {
            worldIn.levelEvent(2005, pos, 0);
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

    /**
     * Spawns the block's drops in the world. By the time this is called the Block has possibly been set to air via
     * Block.removedByPlayer
     */
    public void playerDestroy(Level worldIn, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity tileEntityIn, ItemStack stack) {
        player.awardStat(Stats.BLOCK_MINED.get(this));
        player.causeFoodExhaustion(0.005F);
        if (worldIn instanceof ServerLevel) {
            getDrops(state, (ServerLevel)worldIn, pos, tileEntityIn, player, stack).forEach((stackToSpawn) -> {
                spawnAsGeneticItemEntity(worldIn, pos, stackToSpawn);
            });
            state.spawnAfterBreak((ServerLevel)worldIn, pos, stack);
        }
        this.removeOneEgg(worldIn, pos, state, false);
    }

    public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
        return useContext.getItemInHand().getItem() == this.asItem() && state.getValue(EGGS) < 4 || super.canBeReplaced(state, useContext);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos());

        EggHolder eggHolder = context.getItemInHand().getCapability(EggCapabilityProvider.EGG_CAP, null).orElse(null).getEggHolder(context.getItemInHand());

        if (eggHolder.getGenes()!=null) {
            context.getLevel().getCapability(NestCapabilityProvider.NEST_CAP, null).orElse(new NestCapabilityProvider()).addNestEggPos(context.getClickedPos(), eggHolder.getSire(), eggHolder.getDam(), eggHolder.getGenes(), true);
        }

        return blockstate.is(this) ? blockstate.setValue(EGGS, Integer.valueOf(Math.min(4, blockstate.getValue(EGGS) + 1))) : super.getStateForPlacement(context);
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

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        ItemStack itemStack = super.getCloneItemStack(state, target, world, pos, player);
        EggHolder egg = itemStack.getCapability(NestCapabilityProvider.NEST_CAP, null).orElse(new NestCapabilityProvider()).getEggInNest(pos);
        if (egg!=null) {
            itemStack.getCapability(EggCapabilityProvider.EGG_CAP, null).orElse(new EggCapabilityProvider()).setEggData(egg);
            if (itemStack.getItem() instanceof EnhancedEgg) {
                ((EnhancedEgg) itemStack.getItem()).setHasParents(itemStack, egg.hasParents());
            }
            CompoundTag nbtTagCompound = itemStack.serializeNBT();
            itemStack.deserializeNBT(nbtTagCompound);
        }
        return itemStack;
    }

    public static void spawnAsGeneticItemEntity(Level worldIn, BlockPos pos, ItemStack stack) {
        if (!worldIn.isClientSide && !stack.isEmpty() && worldIn.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && !worldIn.restoringBlockSnapshots) {
            float f = 0.5F;
            double d0 = (double)(worldIn.random.nextFloat() * 0.5F) + 0.25D;
            double d1 = (double)(worldIn.random.nextFloat() * 0.5F) + 0.25D;
            double d2 = (double)(worldIn.random.nextFloat() * 0.5F) + 0.25D;
            EggHolder eggHolder = worldIn.getCapability(NestCapabilityProvider.NEST_CAP, null).orElse(new NestCapabilityProvider()).removeEggFromNest(pos);
            stack.getCapability(EggCapabilityProvider.EGG_CAP, null).orElse(new EggCapabilityProvider()).setEggData(new Genes(eggHolder.getGenes()), eggHolder.getSire(), eggHolder.getDam());
            if (stack.getItem() instanceof EnhancedEgg) {
                ((EnhancedEgg)stack.getItem()).setHasParents(stack, eggHolder.getGenes()!=null);
            }
            CompoundTag nbtTagCompound = stack.serializeNBT();
            stack.deserializeNBT(nbtTagCompound);
            ItemEntity itementity = new ItemEntity(worldIn, (double)pos.getX() + d0, (double)pos.getY() + d1, (double)pos.getZ() + d2, stack);
            itementity.setDefaultPickUpDelay();
            worldIn.addFreshEntity(itementity);
        }
    }
}
