package mokiyoki.enhancedanimals.blocks;

import mokiyoki.enhancedanimals.capability.egg.EggCapabilityProvider;
import mokiyoki.enhancedanimals.capability.turtleegg.EggHolder;
import mokiyoki.enhancedanimals.capability.turtleegg.NestCapabilityProvider;
import mokiyoki.enhancedanimals.items.EnhancedEgg;
import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.List;

public abstract class NestBlock extends Block {

    public NestBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    protected abstract void subtractEggState(World world, BlockPos pos, BlockState state);

    protected abstract void addEggState(World world, BlockPos pos, BlockState state);

    protected abstract int getNumberOfEggs(BlockState state);

    protected List<EggHolder> getEggsRemoveNestCapability(World world, BlockPos pos) {
        return world.getCapability(NestCapabilityProvider.NEST_CAP, null).orElse(new NestCapabilityProvider()).removeEggsFromNest(pos);
    }



    protected void removeOneEgg(World world, BlockPos pos, BlockState state) {
        removeOneEgg(world, pos, state, true);
    }

    protected void removeOneEgg(World world, BlockPos pos, BlockState state, boolean removeEgg) {
        world.playSound((PlayerEntity)null, pos, SoundEvents.ENTITY_TURTLE_EGG_BREAK, SoundCategory.BLOCKS, 0.7F, 0.9F + world.rand.nextFloat() * 0.2F);
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
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState blockstate = context.getWorld().getBlockState(context.getPos());

        EggHolder eggHolder = context.getItem().getCapability(EggCapabilityProvider.EGG_CAP, null).orElse(null).getEggHolder(context.getItem());

        if (eggHolder.getGenes()!=null) {
            context.getWorld().getCapability(NestCapabilityProvider.NEST_CAP, null).orElse(new NestCapabilityProvider()).addNestEggPos(context.getPos(), eggHolder.getSire(), eggHolder.getDam(), eggHolder.getGenes(), true);
        }

        return blockstate.isIn(this) ? blockstate : super.getStateForPlacement(context);
    }

    /**
     * Spawns the block's drops in the world. By the time this is called the Block has possibly been set to air via
     * Block.removedByPlayer
     */
    public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity tileEntityIn, ItemStack stack) {
        player.addStat(Stats.BLOCK_MINED.get(this));
        player.addExhaustion(0.005F);
        if (worldIn instanceof ServerWorld) {
            getDrops(state, (ServerWorld)worldIn, pos, tileEntityIn, player, stack).forEach((stackToSpawn) -> {
                spawnAsGeneticItemEntity(worldIn, pos, stackToSpawn);
            });
            state.spawnAdditionalDrops((ServerWorld)worldIn, pos, stack);
        }
        this.removeOneEgg(worldIn, pos, state, false);
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
        ItemStack itemStack = super.getPickBlock(state, target, world, pos, player);
        EggHolder egg = itemStack.getCapability(NestCapabilityProvider.NEST_CAP, null).orElse(new NestCapabilityProvider()).getEggInNest(pos);
        if (egg!=null) {
            itemStack.getCapability(EggCapabilityProvider.EGG_CAP, null).orElse(new EggCapabilityProvider()).setEggData(egg);
            if (itemStack.getItem() instanceof EnhancedEgg) {
                ((EnhancedEgg) itemStack.getItem()).setHasParents(itemStack, egg.hasParents());
            }
            CompoundNBT nbtTagCompound = itemStack.serializeNBT();
            itemStack.deserializeNBT(nbtTagCompound);
        }
        return itemStack;
    }

    public static void spawnAsGeneticItemEntity(World worldIn, BlockPos pos, ItemStack stack) {
        if (!worldIn.isRemote && !stack.isEmpty() && worldIn.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && !worldIn.restoringBlockSnapshots) {
            float f = 0.5F;
            double d0 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            double d1 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            double d2 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            EggHolder eggHolder = worldIn.getCapability(NestCapabilityProvider.NEST_CAP, null).orElse(new NestCapabilityProvider()).removeEggFromNest(pos);
            stack.getCapability(EggCapabilityProvider.EGG_CAP, null).orElse(new EggCapabilityProvider()).setEggData(new Genes(eggHolder.getGenes()), eggHolder.getSire(), eggHolder.getDam());
            if (stack.getItem() instanceof EnhancedEgg) {
                ((EnhancedEgg)stack.getItem()).setHasParents(stack, eggHolder.getGenes()!=null);
            }
            CompoundNBT nbtTagCompound = stack.serializeNBT();
            stack.deserializeNBT(nbtTagCompound);
            ItemEntity itementity = new ItemEntity(worldIn, (double)pos.getX() + d0, (double)pos.getY() + d1, (double)pos.getZ() + d2, stack);
            itementity.setDefaultPickupDelay();
            worldIn.addEntity(itementity);
        }
    }

}
