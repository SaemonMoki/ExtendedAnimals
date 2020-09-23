package mokiyoki.enhancedanimals.util.handlers;

import mokiyoki.enhancedanimals.EnhancedAnimals;
import mokiyoki.enhancedanimals.blocks.SparseGrassBlock;
import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.entity.EnhancedLlama;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.network.EAEquipmentPacket;
import mokiyoki.enhancedanimals.util.EanimodVillagerTrades;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.HayBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.merchant.villager.VillagerData;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.WanderingTraderEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.passive.horse.TraderLlamaEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;
import net.minecraft.item.ShearsItem;
import net.minecraft.item.SwordItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.spawner.WorldEntitySpawner;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_CHICKEN;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_LLAMA;

/**
 * Created by saemon on 8/09/2018.
 */
//@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class EventSubscriber {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void replaceVanillaMobs(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();

            if (entity instanceof VillagerEntity) {
            Set<String> tags = entity.getTags();
            if (!tags.contains("eanimodTradeless")) {
                if (!entity.getTags().contains("eanimodTrader")) {
                    entity.addTag("eanimodTrader");
                }
            }
        }
        //TODO figure out how to not delete named entities and maybe convert them instead.
        if (entity instanceof ChickenEntity) {
            if(!((ChickenEntity)entity).isChickenJockey() && !EanimodCommonConfig.COMMON.spawnVanillaChickens.get()) {
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
//                    if(!EanimodCommonConfig.COMMON.spawnVanillaLlamas.get()) {
//                        event.setCanceled(true);
//                    }
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

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof VillagerEntity && entity.getTags().contains("eanimodTrader")) {
            VillagerProfession profession = ((VillagerEntity)entity).getVillagerData().getProfession();
            Set<String> tags = entity.getTags();
            if (profession.equals(VillagerProfession.LEATHERWORKER) || profession.equals(VillagerProfession.SHEPHERD)) {
                int level = ((VillagerEntity)entity).getVillagerData().getLevel();
                switch (level) {
                    case 1 :
                        if (!tags.contains("eanimodTrader_1")) {
                            entity.addTag("eanimodTrader_1");
                            ((VillagerEntity)entity).getOffers().add(new EanimodVillagerTrades().getEanimodTrade(profession, 1));
                        }
                        break;
                    case 2 :
                        if (!tags.contains("eanimodTrader_2")) {
                            entity.addTag("eanimodTrader_2");
                            if (ThreadLocalRandom.current().nextBoolean()) {
                                ((VillagerEntity)entity).getOffers().add(new EanimodVillagerTrades().getEanimodTrade(profession,2));
                            }
                        }
                        break;
                    case 3 :
                        if (!tags.contains("eanimodTrader_3")) {
                            entity.addTag("eanimodTrader_3");
                            if (ThreadLocalRandom.current().nextBoolean()) {
                                ((VillagerEntity)entity).getOffers().add(new EanimodVillagerTrades().getEanimodTrade(profession,3));
                            }
                        }
                        break;
                    case 4 :
                        if (!tags.contains("eanimodTrader_4")) {
                            entity.addTag("eanimodTrader_4");
                            if (ThreadLocalRandom.current().nextBoolean()) {
                                ((VillagerEntity)entity).getOffers().add(new EanimodVillagerTrades().getEanimodTrade(profession,4));
                            }
                        }
                        break;
                    case 5 :
                        if (!tags.contains("eanimodTrader_5")) {
                            entity.addTag("eanimodTrader_5");
                            if (ThreadLocalRandom.current().nextBoolean()) {
                                ((VillagerEntity)entity).getOffers().add(new EanimodVillagerTrades().getEanimodTrade(profession,5));
                            }
                        }
                }
            } else {
                if (tags.contains("eanimodTrader_1")) {
                    entity.removeTag("eanimodTrader_1");
                }
            }
        } else if (entity instanceof WanderingTraderEntity) {
            if (!entity.getTags().contains("eanimodTradeless")) {
                entity.addTag("eanimodTradeless");
                int i = 1;
                while (ThreadLocalRandom.current().nextInt(2, 6) >= i) {
                    ((WanderingTraderEntity)entity).getOffers().add(new EanimodVillagerTrades().getWanderingEanimodTrade());
                    i++;
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onStartEntityTracking(PlayerEvent.StartTracking event) {
        Entity targetEntity = event.getTarget();
        if (targetEntity instanceof EnhancedAnimalAbstract) {
            PlayerEntity trackingPlayer = event.getPlayer();

            for(int invSlot = 0; invSlot < 7; invSlot++) {
                ItemStack inventoryItemStack = ((EnhancedAnimalAbstract)targetEntity).getEnhancedInventory().getStackInSlot(invSlot);
                EAEquipmentPacket equipmentPacket = new EAEquipmentPacket(targetEntity.getEntityId(), invSlot, inventoryItemStack);
                EnhancedAnimals.channel.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) trackingPlayer), equipmentPacket);
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
                    if(enhancedLlama != null) {
                        enhancedLlama.setLeashHolder(entity, true);
                    }
                }
            }
            if (!entity.getTags().contains("eanimodTradeless")) {
                entity.addTag("eanimodTradeless");
                int i = 1;
                while (ThreadLocalRandom.current().nextInt(2, 6) >= i) {
                    ((WanderingTraderEntity)entity).getOffers().add(new EanimodVillagerTrades().getWanderingEanimodTrade());
                    i++;
                }
            }
        } else if (entity instanceof ChickenEntity) {
            if (((ChickenEntity)entity).isChickenJockey()) {
                World world = event.getWorld().getWorld();
                Entity rider = entity.getRidingEntity();
                entity.remove();
                BlockPos blockPos = nearbySpawn(world, new BlockPos(entity));
                EnhancedChicken enhancedChicken = ENHANCED_CHICKEN.spawn(world, null, null, null, blockPos, SpawnReason.EVENT, false, false);
                if(enhancedChicken != null) {
                    enhancedChicken.updatePassenger(rider);
                    enhancedChicken.setChickenJockey(true);
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onBlockInteractEvent(PlayerInteractEvent.RightClickBlock event) {
        Item item = event.getItemStack().getItem();
        Block block = event.getWorld().getBlockState(event.getPos()).getBlock();
        if (block instanceof HayBlock && (item instanceof AxeItem || item instanceof SwordItem || item instanceof ShearsItem)) {
            event.getWorld().setBlockState(event.getPos(), ModBlocks.UNBOUNDHAY_BLOCK.getDefaultState(), 11);
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
