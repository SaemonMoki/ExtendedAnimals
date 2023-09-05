package mokiyoki.enhancedanimals.blocks;

import mokiyoki.enhancedanimals.capability.nestegg.EggHolder;
import mokiyoki.enhancedanimals.capability.nestegg.NestCapabilityProvider;
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.items.EnhancedEgg;
import mokiyoki.enhancedanimals.tileentity.ChickenNestTileEntity;
import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_CHICKEN;

public class EnhancedChickenEggBlock extends NestBlock implements EntityBlock {
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
    protected void subtractEggState(Level world, BlockPos pos, BlockState state) {
//        int i = state.getValue(EGG_0);
//        if (i>1) {
//            state.setValue(EGG_0, i - 1);
//        }
    }

    @Override
    protected void addEggState(Level world, BlockPos pos, BlockState state) {
//        int i = state.getValue(EGG_0);
//        if (i<4) {
//            state.setValue(EGG_0, i + 1);
//        }
    }

    @Override
    protected int getNumberOfEggs(BlockState state) {
        return 0;
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
        if (!(entityIn instanceof Chicken || entityIn instanceof EnhancedChicken)) {
            this.tryTrample(worldIn, pos, entityIn, 3);
        }
        super.fallOn(worldIn, blockState, pos, entityIn, fallDistance);
    }

    private void tryTrample(Level worldIn, BlockPos pos, Entity trampler, int chances) {
        if (this.canTrample(worldIn, trampler)) {
            if (!worldIn.isClientSide && worldIn.random.nextInt(chances) == 0) {
                BlockState blockstate = worldIn.getBlockState(pos);
                if (getNumberOfEggs(blockstate)!=0) {
                    this.removeOneEgg(worldIn, pos, blockstate);
                }
            }

        }
    }

    protected void removeOneEgg(Level worldIn, BlockPos pos, BlockState state) {
        getOneEgg(worldIn, pos, state, true);
    }

    protected void getOneEgg(Level worldIn, BlockPos pos, BlockState state, boolean removeEgg) {
        CompoundTag data = worldIn.getBlockEntity(pos).getTileData();
        NonNullList<ItemStack> items = NonNullList.withSize(12, ItemStack.EMPTY);
        ListTag listtag = data.getList("Items", 10);

        for(int i = 0; i < listtag.size(); ++i) {
            CompoundTag compoundtag = listtag.getCompound(i);
            int j = compoundtag.getByte("Slot") & 255;
            if (j >= 0 && j < 12) {
                items.set(j, ItemStack.of(compoundtag));
            }
        }



//        worldIn.playSound((Player)null, pos, SoundEvents.TURTLE_EGG_BREAK, SoundSource.BLOCKS, 0.7F, 0.9F + worldIn.random.nextFloat() * 0.2F);
//        int i = state.getValue(EGG_0);
//        if (i <= 1) {
//            if (removeEgg) {
//                worldIn.getCapability(NestCapabilityProvider.NEST_CAP, null).orElse(new NestCapabilityProvider()).removeNestPos(pos);
//            }
//            worldIn.destroyBlock(pos, false);
//        } else {
//            if (removeEgg) {
//                worldIn.getCapability(NestCapabilityProvider.NEST_CAP, null).orElse(new NestCapabilityProvider()).removeEggFromNest(pos);
//            }
//            worldIn.setBlock(pos, state.setValue(EGG_0, Integer.valueOf(i - 1)), 2);
//            worldIn.levelEvent(2001, pos, Block.getId(state));
//        }
    }

    public void hatchEggs(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        int i = getNumberOfEggs(state);
        if (i == 1) {
            level.playSound((Player) null, pos, SoundEvents.CHICKEN_AMBIENT, SoundSource.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
        } else if (i >= 2) {
            level.playSound((Player) null, pos, SoundEvents.TURTLE_EGG_HATCH, SoundSource.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
            level.removeBlock(pos, false);

            List<EggHolder> eggList = level.getCapability(NestCapabilityProvider.NEST_CAP, null).orElse(new NestCapabilityProvider()).removeEggsFromNest(pos);
            int j = 1;

            if (eggList != null) {
                for (EggHolder egg : eggList) {
                    level.levelEvent(2001, pos, Block.getId(state));
                    EnhancedChicken chicken = ENHANCED_CHICKEN.get().create(level);
                    chicken.setGenes(egg.getGenes());
                    chicken.setSharedGenes(egg.getGenes());
                    chicken.setSireName(egg.getSire());
                    chicken.setDamName(egg.getDam());
                    chicken.setGrowingAge();
                    chicken.initilizeAnimalSize();
                    chicken.setBirthTime();
                    chicken.moveTo((double) pos.getX() + 0.3D + (double) j++ * 0.2D, (double) pos.getY(), (double) pos.getZ() + 0.3D, 0.0F, 0.0F);
                    level.addFreshEntity(chicken);
                }
            } else {
                for (int k = 0; k < i; k++) {
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
                    chicken.moveTo((double) pos.getX() + 0.3D + (double) j++ * 0.2D, (double) pos.getY(), (double) pos.getZ() + 0.3D, 0.0F, 0.0F);
                    level.addFreshEntity(chicken);
                }
            }
        }
    }


    private boolean canTrample(Level worldIn, Entity trampler) {
        if (!(trampler instanceof Chicken) && !(trampler instanceof Bat) && !(trampler instanceof EnhancedChicken)) {
            if (!(trampler instanceof LivingEntity)) {
                return false;
            } else {
                return trampler instanceof Player || net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(worldIn, trampler);
            }
        } else {
            return false;
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add();
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
}
