package mokiyoki.enhancedanimals.blocks;

import mokiyoki.enhancedanimals.capability.nestegg.EggHolder;
import mokiyoki.enhancedanimals.capability.nestegg.NestCapabilityProvider;
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.List;
import java.util.Random;

import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_CHICKEN;

public class EnhancedChickenEggBlock extends NestBlock {
    public static final IntegerProperty HATCH = BlockStateProperties.HATCH;
    public static final IntegerProperty EGG_0 = IntegerProperty.create("egg_0", 0, 4);
    public static final IntegerProperty EGG_1 = IntegerProperty.create("egg_1", 0, 4);
    public static final IntegerProperty EGG_2 = IntegerProperty.create("egg_2", 0, 4);
    public static final IntegerProperty EGG_3 = IntegerProperty.create("egg_3", 0, 4);
    public static final IntegerProperty EGG_4 = IntegerProperty.create("egg_4", 0, 4);
    public static final IntegerProperty EGG_5 = IntegerProperty.create("egg_5", 0, 4);
    public static final IntegerProperty EGG_6 = IntegerProperty.create("egg_6", 0, 4);
    public static final IntegerProperty EGG_7 = IntegerProperty.create("egg_7", 0, 4);
    public static final IntegerProperty EGG_8 = IntegerProperty.create("egg_8", 0, 4);
    public static final IntegerProperty EGG_9 = IntegerProperty.create("egg_9", 0, 4);
    public static final IntegerProperty EGG_10 = IntegerProperty.create("egg_10", 0, 4);
    public static final IntegerProperty EGG_11 = IntegerProperty.create("egg_11", 0, 4);

    public EnhancedChickenEggBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HATCH, Integer.valueOf(0)).setValue(EGG_0, Integer.valueOf(1)).setValue(EGG_1, Integer.valueOf(0)).setValue(EGG_2, Integer.valueOf(0)).setValue(EGG_3, Integer.valueOf(0)).setValue(EGG_4, Integer.valueOf(0)).setValue(EGG_5, Integer.valueOf(0)).setValue(EGG_6, Integer.valueOf(0)).setValue(EGG_7, Integer.valueOf(0)).setValue(EGG_8, Integer.valueOf(0)).setValue(EGG_9, Integer.valueOf(0)).setValue(EGG_10, Integer.valueOf(0)).setValue(EGG_11, Integer.valueOf(0)));
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
        int eggs = state.getValue(EGG_0)!=0?1:0;
        if (state.getValue(EGG_1)!=0) eggs++;
        if (state.getValue(EGG_2)!=0) eggs++;
        if (state.getValue(EGG_3)!=0) eggs++;
        if (state.getValue(EGG_4)!=0) eggs++;
        if (state.getValue(EGG_5)!=0) eggs++;
        if (state.getValue(EGG_6)!=0) eggs++;
        if (state.getValue(EGG_7)!=0) eggs++;
        if (state.getValue(EGG_8)!=0) eggs++;
        if (state.getValue(EGG_9)!=0) eggs++;
        if (state.getValue(EGG_10)!=0) eggs++;
        if (state.getValue(EGG_11)!=0) eggs++;
        return eggs;
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
//                if (blockstate.is(ModBlocks.CHICKEN_NEST.get())) {
//                    this.removeOneEgg(worldIn, pos, blockstate);
//                }
            }

        }
    }

    protected void removeOneEgg(Level worldIn, BlockPos pos, BlockState state) {
        removeOneEgg(worldIn, pos, state, true);
    }

    protected void removeOneEgg(Level worldIn, BlockPos pos, BlockState state, boolean removeEgg) {
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
        int i = state.getValue(HATCH);
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
                for (int k = 0; k < state.getValue(EGG_0); k++) {
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
        builder.add(HATCH, EGG_0, EGG_1, EGG_2, EGG_3, EGG_4, EGG_5, EGG_6, EGG_7, EGG_8, EGG_9, EGG_10, EGG_11);
    }

    public static int getEggColour(BlockState state, BlockPos pos, int tintIndex) {
        if (tintIndex==0) {
            return state.getValue(EGG_0)==0?-1:11393254;
        } else if (tintIndex==1) {
            return state.getValue(EGG_1)==0?-1:16776656;
        } else if (tintIndex==2) {
            return state.getValue(EGG_2)==0?-1:9498256;
        }/* else if (tintIndex==3) {
            return state.getValue(EGG_3)==0?-1:10506797;
        } else if (tintIndex==4) {
            return state.getValue(EGG_4)==0?-1:11907932;
        } else if (tintIndex==5) {
            return state.getValue(EGG_5)==0?-1:16762029;
        }*/
            return -1;
    }
}
