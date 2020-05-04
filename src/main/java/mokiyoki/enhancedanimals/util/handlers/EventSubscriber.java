package mokiyoki.enhancedanimals.util.handlers;

import mokiyoki.enhancedanimals.blocks.SparseGrassBlock;
import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
import mokiyoki.enhancedanimals.entity.EnhancedLlama;
import mokiyoki.enhancedanimals.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.HayBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.merchant.villager.WanderingTraderEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.passive.horse.TraderLlamaEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ShearsItem;
import net.minecraft.item.SwordItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.spawner.WorldEntitySpawner;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_LLAMA;

/**
 * Created by saemon on 8/09/2018.
 */
//@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class EventSubscriber {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void replaceVanillaMobs(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        //TODO figure out how to not delete named entities and maybe convert them instead.
        if (entity instanceof ChickenEntity) {
            if(!EanimodCommonConfig.COMMON.spawnVanillaChickens.get()) {
                event.setCanceled(true);
            }
        }
        if (entity instanceof RabbitEntity) {
            if(!EanimodCommonConfig.COMMON.spawnVanillaRabbits.get()) {
                event.setCanceled(true);
            }
        }
        if (entity instanceof LlamaEntity) {
            if (!(entity instanceof TraderLlamaEntity)) {
                    if(!EanimodCommonConfig.COMMON.spawnVanillaLlamas.get()) {
                        event.setCanceled(true);
                    }
                } else if (EanimodCommonConfig.COMMON.spawnGeneticLlamas.get()) {
                    event.setCanceled(true);
                }
        }
        if (entity instanceof SheepEntity) {
            if(!EanimodCommonConfig.COMMON.spawnVanillaSheep.get()) {
                event.setCanceled(true);
            }
        }
        if (entity instanceof CowEntity && !(entity instanceof MooshroomEntity)) {
            if(!EanimodCommonConfig.COMMON.spawnVanillaCows.get()) {
                event.setCanceled(true);
            }
        }
        if (entity instanceof MooshroomEntity) {
            if(!EanimodCommonConfig.COMMON.spawnVanillaMooshroom.get()) {
                event.setCanceled(true);
            }
        }
        if (entity instanceof PigEntity) {
            if(!EanimodCommonConfig.COMMON.spawnVanillaPigs.get()) {
                event.setCanceled(true);
            }
        }

    }

    @SubscribeEvent(priority = EventPriority.HIGH)
        public void livingspawnEvent(LivingSpawnEvent.SpecialSpawn event) {
        Entity entity = event.getEntity();
        if (entity instanceof WanderingTraderEntity) {
            if(!EanimodCommonConfig.COMMON.spawnVanillaLlamas.get()) {
                ((WanderingTraderEntity)entity).setDespawnDelay(48000);
                World world = event.getWorld().getWorld();

                for (int i = 0; i < 2; i++) {
                    BlockPos blockPos = nearbySpawn(world, new BlockPos(entity));
                    EnhancedLlama enhancedLlama = ENHANCED_LLAMA.spawn(world, null, null, null, blockPos, SpawnReason.EVENT, false, false);
                    enhancedLlama.setLeashHolder(entity, true);
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onBlockInteractEvent(PlayerInteractEvent.RightClickBlock event) {
        Item item = event.getItemStack().getItem();
        Block block = event.getWorld().getBlockState(event.getPos()).getBlock();
        if (block instanceof HayBlock && (item instanceof AxeItem || item instanceof SwordItem || item instanceof ShearsItem)) {
            event.getWorld().setBlockState(event.getPos(), ModBlocks.UnboundHay_Block.getDefaultState(), 11);
        } else if (block instanceof SparseGrassBlock && (item instanceof HoeItem)) {
            event.getWorld().setBlockState(event.getPos(), Blocks.FARMLAND.getDefaultState(), 11);
        }
    }


    private BlockPos nearbySpawn(World world, BlockPos blockPosOfEntity) {
        BlockPos blockpos = null;

        for(int i = 0; i < 10; ++i) {
            int j = blockPosOfEntity.getX() + world.rand.nextInt(4 * 2) - 4;
            int k = blockPosOfEntity.getZ() + world.rand.nextInt(4 * 2) - 4;
            int l = world.getHeight(Heightmap.Type.WORLD_SURFACE, j, k);
            BlockPos blockpos1 = new BlockPos(j, l, k);
            if (WorldEntitySpawner.canCreatureTypeSpawnAtLocation(EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, world, blockpos1, EntityType.WANDERING_TRADER)) {
                blockpos = blockpos1;
                break;
            }
        }

        return blockpos;
    }


}
