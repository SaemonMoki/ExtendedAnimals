package mokiyoki.enhancedanimals.blocks;

import mokiyoki.enhancedanimals.capability.egg.EggCapabilityProvider;
import mokiyoki.enhancedanimals.capability.nestegg.EggHolder;
import mokiyoki.enhancedanimals.capability.nestegg.NestCapabilityProvider;
import mokiyoki.enhancedanimals.items.EnhancedEgg;
import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nullable;
import java.util.List;

public abstract class NestBlock extends Block {

    public NestBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    protected abstract void subtractEggState(Level world, BlockPos pos, BlockState state);

    protected abstract void addEggState(Level world, BlockPos pos, BlockState state);

    protected abstract int getNumberOfEggs(BlockState state);

    protected abstract SoundEvent getEggBreakSound();

    protected List<EggHolder> getEggsRemoveNestCapability(Level world, BlockPos pos) {
        return world.getCapability(NestCapabilityProvider.NEST_CAP, null).orElse(new NestCapabilityProvider()).removeEggsFromNest(pos);
    }

    protected void removeOneEgg(Level world, BlockPos pos, BlockState state) {
        removeOneEgg(world, pos, state, true);
    }

    protected void removeOneEgg(Level world, BlockPos pos, BlockState state, boolean removeEgg) {
        world.playSound((Player)null, pos, getEggBreakSound(), SoundSource.BLOCKS, 0.7F, 0.9F + world.random.nextFloat() * 0.2F);
        int i = getNumberOfEggs(state);
        if (i <= 1) {
            if (removeEgg) {
                world.getCapability(NestCapabilityProvider.NEST_CAP, null).orElse(new NestCapabilityProvider()).removeNestPos(pos);
            }
            world.destroyBlock(pos, false);
        } else {
            if (removeEgg) {
                world.getCapability(NestCapabilityProvider.NEST_CAP, null).orElse(new NestCapabilityProvider()).removeEggFromNest(pos);
            }
            this.subtractEggState(world, pos, state);
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos());

        EggHolder eggHolder = context.getItemInHand().getCapability(EggCapabilityProvider.EGG_CAP, null).orElse(null).getEggHolder(context.getItemInHand());

        if (eggHolder.getGenes()!=null) {
            context.getLevel().getCapability(NestCapabilityProvider.NEST_CAP, null).orElse(new NestCapabilityProvider()).addNestEggPos(context.getClickedPos(), eggHolder.getSire(), eggHolder.getDam(), eggHolder.getGenes(), true);
        }

        return blockstate.is(this) ? blockstate : super.getStateForPlacement(context);
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
            state.spawnAfterBreak((ServerLevel)worldIn, pos, stack, true);
        }
        this.removeOneEgg(worldIn, pos, state, false);
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
