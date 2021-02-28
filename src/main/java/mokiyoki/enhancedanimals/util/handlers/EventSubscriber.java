package mokiyoki.enhancedanimals.util.handlers;

import mokiyoki.enhancedanimals.EnhancedAnimals;
import mokiyoki.enhancedanimals.blocks.SparseGrassBlock;
import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.entity.EnhancedCow;
import mokiyoki.enhancedanimals.entity.EnhancedLlama;
import mokiyoki.enhancedanimals.entity.EnhancedMooshroom;
import mokiyoki.enhancedanimals.entity.EnhancedPig;
import mokiyoki.enhancedanimals.entity.EnhancedRabbit;
import mokiyoki.enhancedanimals.entity.EnhancedSheep;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.network.EAEquipmentPacket;
import mokiyoki.enhancedanimals.util.EanimodVillagerTrades;
import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.HayBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.NonTamedTargetGoal;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.WanderingTraderEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.passive.horse.TraderLlamaEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.WorldEntitySpawner;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_CHICKEN;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_COW;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_LLAMA;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_MOOBLOOM;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_MOOSHROOM;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_PIG;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_RABBIT;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_SHEEP;

/**
 * Created by saemon on 8/09/2018.
 */
//@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class EventSubscriber {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void editMobs(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof VillagerEntity) {
            Set<String> tags = entity.getTags();
            if (!tags.contains("eanimodTradeless")) {
                if (!entity.getTags().contains("eanimodTrader")) {
                    entity.addTag("eanimodTrader");
                }
            }
        } else if (entity instanceof WolfEntity) {
            final Predicate<LivingEntity> TARGETS = (targetEntity) -> {
                return targetEntity instanceof EnhancedSheep || targetEntity instanceof EnhancedRabbit || ((targetEntity instanceof EnhancedPig || targetEntity instanceof EnhancedCow) && targetEntity.isChild()) ;
            };
            ((WolfEntity) entity).targetSelector.addGoal(3, new AvoidEntityGoal<>((WolfEntity) entity, EnhancedLlama.class, 24.0F, 1.5D, 1.5D));
            ((WolfEntity) entity).targetSelector.addGoal(5, new NonTamedTargetGoal<>((WolfEntity) entity, AnimalEntity.class, false, TARGETS));
        } else if (entity instanceof FoxEntity) {
            if (((FoxEntity) entity).getVariantType() == FoxEntity.Type.RED) {
                ((FoxEntity) entity).targetSelector.addGoal(4, new NearestAttackableTargetGoal<>((FoxEntity) entity, AnimalEntity.class, 10, false, false, (targetEntity) -> {
                    return targetEntity instanceof EnhancedChicken || targetEntity instanceof EnhancedRabbit;
                }));
            } else {
                ((FoxEntity) entity).targetSelector.addGoal(6, new NearestAttackableTargetGoal<>((FoxEntity) entity, AnimalEntity.class, 10, false, false, (targetEntity) -> {
                    return targetEntity instanceof EnhancedChicken || targetEntity instanceof EnhancedRabbit;
                }));
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void babyEntitySpawnEvent(BabyEntitySpawnEvent event) {
        if (event.getParentA() instanceof EnhancedAnimalAbstract && event.getChild() == null) {
            event.setCanceled(true);
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
        } else if (!entity.getEntityWorld().isRemote()) {
            if (entity instanceof SheepEntity) {
                if (!EanimodCommonConfig.COMMON.spawnVanillaSheep.get() && EanimodCommonConfig.COMMON.spawnGeneticSheep.get()) {
                    EnhancedSheep enhancedSheep = ENHANCED_SHEEP.spawn((ServerWorld) entity.getEntityWorld(), null, null, null, entity.getPosition(), SpawnReason.NATURAL, false, false);
                    if (enhancedSheep != null) {
                        enhancedSheep.setFleeceDyeColour(((SheepEntity) entity).getFleeceColor());
                        if (entity.hasCustomName()) {
                            enhancedSheep.setCustomName(entity.getCustomName());
                        }
                        if (((SheepEntity) entity).isChild()) {
                            int age = ((SheepEntity) entity).getGrowingAge();
                            enhancedSheep.setGrowingAge(age);
                            enhancedSheep.setBirthTime(entity.getEntityWorld(), (-age/24000)*72000);
                        } else {
                            enhancedSheep.setGrowingAge(0);
                            enhancedSheep.setBirthTime(entity.getEntityWorld(), -500000);
                        }
                        if (((SheepEntity) entity).getLeashed()) {
                            enhancedSheep.setLeashHolder(((SheepEntity) entity).getLeashHolder(), true);
                        }
                    }
                    entity.remove();
                }
            } else if (entity instanceof ChickenEntity) {
                if (!EanimodCommonConfig.COMMON.spawnVanillaChickens.get() && EanimodCommonConfig.COMMON.spawnGeneticChickens.get()) {
                    EnhancedChicken enhancedChicken = ENHANCED_CHICKEN.spawn((ServerWorld) entity.getEntityWorld(), null, null, null, entity.getPosition(), SpawnReason.NATURAL, false, false);
                    if (enhancedChicken != null) {
                        if (entity.hasCustomName()) {
                            enhancedChicken.setCustomName(entity.getCustomName());
                        }
                        if (((ChickenEntity) entity).isChild()) {
                            int age = ((ChickenEntity) entity).getGrowingAge();
                            enhancedChicken.setGrowingAge(age);
                            enhancedChicken.setBirthTime(entity.getEntityWorld(), (-age/24000)*60000);
                        } else {
                            enhancedChicken.setGrowingAge(0);
                            enhancedChicken.setBirthTime(entity.getEntityWorld(), -500000);
                        }
                        if (((ChickenEntity) entity).getLeashed()) {
                            enhancedChicken.setLeashHolder(((ChickenEntity) entity).getLeashHolder(), true);
                        }
                    }
                    entity.remove();
                }
            } else if (entity instanceof PigEntity) {
                if (!EanimodCommonConfig.COMMON.spawnVanillaPigs.get() && EanimodCommonConfig.COMMON.spawnGeneticPigs.get()) {
                    EnhancedPig enhancedPig = ENHANCED_PIG.spawn((ServerWorld) entity.getEntityWorld(), null, null, null, entity.getPosition(), SpawnReason.NATURAL, false, false);
                    if (enhancedPig != null) {
                        if (entity.hasCustomName()) {
                            enhancedPig.setCustomName(entity.getCustomName());
                        }
                        if (((PigEntity) entity).isChild()) {
                            int age = ((PigEntity) entity).getGrowingAge();
                            enhancedPig.setGrowingAge(age);
                            enhancedPig.setBirthTime(entity.getEntityWorld(), (-age/24000)*60000);
                        } else {
                            enhancedPig.setGrowingAge(0);
                            enhancedPig.setBirthTime(entity.getEntityWorld(), -500000);
                        }
                        if (((PigEntity) entity).isHorseSaddled()) {
                            enhancedPig.equipAnimal(false, true, null);
                        }
                        if (((PigEntity) entity).getLeashed()) {
                            enhancedPig.setLeashHolder(((PigEntity) entity).getLeashHolder(), true);
                        }
                    }
                    entity.remove();
                    event.setCanceled(true);
                }
            } else if (entity instanceof MooshroomEntity) {
                if (!EanimodCommonConfig.COMMON.spawnVanillaMooshroom.get() && EanimodCommonConfig.COMMON.spawnGeneticMooshroom.get()) {
                    EnhancedMooshroom enhancedMooshroom = ENHANCED_MOOSHROOM.spawn((ServerWorld) entity.getEntityWorld(), null, null, null, entity.getPosition(), SpawnReason.NATURAL, false, false);
                    if (enhancedMooshroom != null) {
                        if (entity.hasCustomName()) {
                            enhancedMooshroom.setCustomName(entity.getCustomName());
                        }
                        if (((MooshroomEntity) entity).isChild()) {
                            int age = ((MooshroomEntity) entity).getGrowingAge();
                            enhancedMooshroom.setGrowingAge(age);
                            enhancedMooshroom.setBirthTime(entity.getEntityWorld(), (-age/24000)*84000);
                        } else {
                            enhancedMooshroom.setGrowingAge(0);
                            enhancedMooshroom.setBirthTime(entity.getEntityWorld(), -500000);
                        }
                        enhancedMooshroom.setMooshroomType(EnhancedMooshroom.Type.valueOf(((MooshroomEntity) entity).getMooshroomType().name()));
                        if (((MooshroomEntity) entity).getLeashed()) {
                            enhancedMooshroom.setLeashHolder(((MooshroomEntity) entity).getLeashHolder(), true);
                        }
                    }
                    entity.remove();
                    event.setCanceled(true);
                }
            } else if (entity instanceof CowEntity) {
                if (!EanimodCommonConfig.COMMON.spawnVanillaCows.get() && EanimodCommonConfig.COMMON.spawnGeneticCows.get()) {
                    EnhancedCow enhancedCow;
                    boolean flag = true;
                    if (entity.getClass().getName().toLowerCase().contains("moobloom")) {
                        if (EanimodCommonConfig.COMMON.spawnGeneticMoobloom.get()) {
                            enhancedCow = ENHANCED_MOOBLOOM.spawn((ServerWorld) entity.getEntityWorld(), null, null, null, entity.getPosition(), SpawnReason.NATURAL, false, false);
                        } else {
                            enhancedCow = null;
                            flag = false;
                        }
                    } else {
                        enhancedCow = ENHANCED_COW.spawn((ServerWorld) entity.getEntityWorld(), null, null, null, entity.getPosition(), SpawnReason.NATURAL, false, false);
                    }

                    if (flag) {
                        if (enhancedCow != null) {
                            if (entity.hasCustomName()) {
                                enhancedCow.setCustomName(entity.getCustomName());
                            }
                            if (((CowEntity) entity).isChild()) {
                                int age = ((CowEntity) entity).getGrowingAge();
                                enhancedCow.setGrowingAge(age);
                                enhancedCow.setBirthTime(entity.getEntityWorld(), (-age / 24000) * 84000);
                            } else {
                                enhancedCow.setGrowingAge(0);
                                enhancedCow.setBirthTime(entity.getEntityWorld(), -500000);
                            }
                            if (((CowEntity) entity).getLeashed()) {
                                enhancedCow.setLeashHolder(((CowEntity) entity).getLeashHolder(), true);
                            }
                        }
                        entity.remove();
                        event.setCanceled(true);
                    }
                }
            } else if (entity instanceof LlamaEntity && entity.getClass().getName().toLowerCase().contains("llama")) {
                if (!EanimodCommonConfig.COMMON.spawnVanillaLlamas.get() && EanimodCommonConfig.COMMON.spawnGeneticLlamas.get()) {
                    if (!(((LlamaEntity) entity).getLeashHolder() instanceof WanderingTraderEntity)) {
                    EnhancedLlama enhancedLlama = ENHANCED_LLAMA.create(entity.getEntityWorld());
                    enhancedLlama.setLocationAndAngles(entity.getPosX(), entity.getPosY(), entity.getPosZ(), (entity.rotationYaw), entity.rotationPitch);
                    enhancedLlama.renderYawOffset = ((LlamaEntity) entity).renderYawOffset;
                    String breed = "";
                    switch (((LlamaEntity) entity).getVariant()) {
                        case 0 :
                            breed = "cream";
                            break;
                        case 1 :
                            breed = "white";
                            break;
                        case 2 :
                            breed = "brown";
                            break;
                        case 3 :
                        default:
                            breed = "gray";
                    }
                    Genes llamaGenes = enhancedLlama.createInitialBreedGenes(entity.getEntityWorld(), entity.getPosition(), breed);
                    enhancedLlama.setGenes(llamaGenes);
                    enhancedLlama.setSharedGenes(llamaGenes);
                    enhancedLlama.initilizeAnimalSize();
                    enhancedLlama.setInitialCoat();
                    enhancedLlama.getReloadTexture();
                    enhancedLlama.setTame(((LlamaEntity) entity).isTame());

                        if (enhancedLlama != null) {
                            if (entity.hasCustomName()) {
                                enhancedLlama.setCustomName(entity.getCustomName());
                            }
                            if (((LlamaEntity) entity).isChild()) {
                                int age = ((LlamaEntity) entity).getGrowingAge();
                                enhancedLlama.setGrowingAge(age);
                                enhancedLlama.setBirthTime(entity.getEntityWorld(), (-age / 24000) * 120000);
                                if (entity instanceof TraderLlamaEntity) {
                                    enhancedLlama.equipTraderAnimal(false);
                                } else {
                                    enhancedLlama.equipAnimal(false, ((LlamaEntity) entity).getColor());
                                }
                            } else {
                                enhancedLlama.setGrowingAge(0);
                                enhancedLlama.setBirthTime(entity.getEntityWorld(), -500000);
                                if (entity instanceof TraderLlamaEntity) {
                                    enhancedLlama.equipTraderAnimal(((LlamaEntity) entity).hasChest());
                                    enhancedLlama.getGenes().setAutosomalGene(2, 2, 3, 2, 3, 2, 3);
                                } else {
                                    enhancedLlama.equipAnimal(((LlamaEntity) entity).hasChest(), ((LlamaEntity) entity).getColor());
                                }
                                if (((LlamaEntity) entity).hasChest()) {
                                    CompoundNBT nbt = entity.serializeNBT();
                                    ListNBT listnbt = nbt.getList("Items", 10);

                                    for (int i = 0; i < listnbt.size(); ++i) {
                                        CompoundNBT compoundnbt = listnbt.getCompound(i);
                                        int j = compoundnbt.getByte("Slot") & 255;
                                        if (j >= 2 && j + 5 < enhancedLlama.getEnhancedInventory().getSizeInventory()) {
                                            enhancedLlama.getEnhancedInventory().setInventorySlotContents(j + 5, ItemStack.read(compoundnbt));
                                        }
                                    }
                                }
                            }
                            entity.getEntityWorld().addEntity(enhancedLlama);
                            if (((LlamaEntity) entity).getLeashed()) {
                                enhancedLlama.setLeashHolder(((LlamaEntity) entity).getLeashHolder(), true);
                            }
                        }
                    }
                    entity.remove();
                    event.setCanceled(true);
                }
            } else if (entity instanceof RabbitEntity) {
                if (!EanimodCommonConfig.COMMON.spawnVanillaRabbits.get() && EanimodCommonConfig.COMMON.spawnGeneticRabbits.get()) {
                    EnhancedRabbit enhancedRabbit = ENHANCED_RABBIT.create(entity.getEntityWorld());
                    if (enhancedRabbit != null) {
                        enhancedRabbit.setLocationAndAngles(entity.getPosX(), entity.getPosY(), entity.getPosZ(), (entity.rotationYaw), entity.rotationPitch);
                        enhancedRabbit.renderYawOffset = ((RabbitEntity) entity).renderYawOffset;
                        String breed = "";
                        switch (((RabbitEntity) entity).getRabbitType()) {
                            case 1:
                            case 3 :
                                breed = "snow";
                                break;
                            case 4 :
                                breed = "desert";
                                break;
                            default:
                                breed = "forest";
                        }
                        Genes rabbitGenes = enhancedRabbit.createInitialBreedGenes(entity.getEntityWorld(), entity.getPosition(), breed);
                        enhancedRabbit.setGenes(rabbitGenes);
                        enhancedRabbit.setSharedGenes(rabbitGenes);
                        enhancedRabbit.initilizeAnimalSize();
                        enhancedRabbit.setInitialCoat();
                        enhancedRabbit.getReloadTexture();

                        if (entity.hasCustomName()) {
                            enhancedRabbit.setCustomName(entity.getCustomName());
                        }
                        if (((RabbitEntity) entity).isChild()) {
                            int age = ((RabbitEntity) entity).getGrowingAge();
                            enhancedRabbit.setGrowingAge(age);
                            enhancedRabbit.setBirthTime(entity.getEntityWorld(), (-age/24000)*48000);
                        } else {
                            enhancedRabbit.setGrowingAge(0);
                            enhancedRabbit.setBirthTime(entity.getEntityWorld(), -500000);
                        }
                        entity.getEntityWorld().addEntity(enhancedRabbit);
                    }
                    entity.remove();
                    event.setCanceled(true);
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
            IWorld world = event.getWorld();
            ((WanderingTraderEntity)entity).setDespawnDelay(48000);
            if(world instanceof ServerWorld) {
                if(!EanimodCommonConfig.COMMON.spawnVanillaLlamas.get()) {
                    for (int i = 0; i < 2; i++) {
                        BlockPos blockPos = nearbySpawn(((ServerWorld)world), new BlockPos(entity.getPosition()));
                        EnhancedLlama enhancedLlama = ENHANCED_LLAMA.spawn((ServerWorld)world, null, null, null, blockPos, SpawnReason.EVENT, false, false);
                        if(enhancedLlama != null) {
                            enhancedLlama.getGenes().setAutosomalGene(2, 2, 3, 2, 3, 2, 3);
                            enhancedLlama.setLeashHolder(entity, true);
                            enhancedLlama.setDespawnDelay(48000, true);
                            enhancedLlama.setGrowingAge(0);
                            enhancedLlama.setBirthTime(entity.getEntityWorld(), -((ThreadLocalRandom.current().nextInt(25)*500000) + 10000000));
                        }
                    }
                }

                if (ThreadLocalRandom.current().nextInt(5) == 0) {
                    int r = ThreadLocalRandom.current().nextInt(2) + 1;
                    switch (ThreadLocalRandom.current().nextInt(6)) {
                        case 0:
                            if (EanimodCommonConfig.COMMON.spawnGeneticCows.get()) {
                                for (int i = 0; i < r; i++) {
                                    BlockPos blockPos = nearbySpawn(((ServerWorld) world), new BlockPos(entity.getPosition()));
                                    EnhancedCow enhancedCow = ENHANCED_COW.spawn((ServerWorld) world, null, null, null, blockPos, SpawnReason.EVENT, false, false);
                                    if (enhancedCow != null) {
                                        Genes cowGenes = enhancedCow.createInitialBreedGenes(entity.getEntityWorld(), entity.getPosition(), "WanderingTrader");
                                        enhancedCow.setGenes(cowGenes);
                                        enhancedCow.setSharedGenes(cowGenes);
                                        enhancedCow.initilizeAnimalSize();
                                        enhancedCow.getReloadTexture();
                                        enhancedCow.setLeashHolder(entity, true);
                                    }
                                }
                            }
                            break;
                        case 1:
                            if (EanimodCommonConfig.COMMON.spawnGeneticSheep.get()) {
                                for (int i = 0; i < r; i++) {
                                    BlockPos blockPos = nearbySpawn(((ServerWorld) world), new BlockPos(entity.getPosition()));
                                    EnhancedSheep enhancedSheep = ENHANCED_SHEEP.spawn((ServerWorld) world, null, null, null, blockPos, SpawnReason.EVENT, false, false);
                                    if (enhancedSheep != null) {
                                        Genes sheepGenes = enhancedSheep.createInitialBreedGenes(entity.getEntityWorld(), entity.getPosition(), "WanderingTrader");
                                        enhancedSheep.setGenes(sheepGenes);
                                        enhancedSheep.setSharedGenes(sheepGenes);
                                        enhancedSheep.initilizeAnimalSize();
                                        enhancedSheep.setInitialCoat();
                                        enhancedSheep.getReloadTexture();
                                        enhancedSheep.setLeashHolder(entity, true);
                                    }
                                }
                            }
                            break;
                        case 2:
                            if (EanimodCommonConfig.COMMON.spawnGeneticChickens.get()) {
                                for (int i = 0; i < r; i++) {
                                    BlockPos blockPos = nearbySpawn(((ServerWorld) world), new BlockPos(entity.getPosition()));
                                    EnhancedChicken enhancedChicken = ENHANCED_CHICKEN.spawn((ServerWorld) world, null, null, null, blockPos, SpawnReason.EVENT, false, false);
                                    if (enhancedChicken != null) {
                                        Genes chickenGenes = enhancedChicken.createInitialBreedGenes(entity.getEntityWorld(), entity.getPosition(), "WanderingTrader");
                                        enhancedChicken.setGenes(chickenGenes);
                                        enhancedChicken.setSharedGenes(chickenGenes);
                                        enhancedChicken.initilizeAnimalSize();
                                        enhancedChicken.getReloadTexture();
                                        enhancedChicken.setLeashHolder(entity, true);
                                    }
                                }
                            }
                            break;
                        case 3:
                            if (EanimodCommonConfig.COMMON.spawnGeneticRabbits.get()) {
                                for (int i = 0; i < r; i++) {
                                    BlockPos blockPos = nearbySpawn(((ServerWorld) world), new BlockPos(entity.getPosition()));
                                    EnhancedRabbit enhancedRabbit = ENHANCED_RABBIT.spawn((ServerWorld) world, null, null, null, blockPos, SpawnReason.EVENT, false, false);
                                    if (enhancedRabbit != null) {
                                        Genes rabbitGenes = enhancedRabbit.createInitialBreedGenes(entity.getEntityWorld(), entity.getPosition(), "WanderingTrader");
                                        enhancedRabbit.setGenes(rabbitGenes);
                                        enhancedRabbit.setSharedGenes(rabbitGenes);
                                        enhancedRabbit.initilizeAnimalSize();
                                        enhancedRabbit.getReloadTexture();
                                        enhancedRabbit.setLeashHolder(entity, true);
                                    }
                                }
                            }
                            break;
                        case 4:
                            if (EanimodCommonConfig.COMMON.spawnGeneticPigs.get()) {
                                for (int i = 0; i < r; i++) {
                                    BlockPos blockPos = nearbySpawn(((ServerWorld) world), new BlockPos(entity.getPosition()));
                                    EnhancedPig enhancedPig = ENHANCED_PIG.spawn((ServerWorld) world, null, null, null, blockPos, SpawnReason.EVENT, false, false);
                                    if (enhancedPig != null) {
                                        Genes pigGenes = enhancedPig.createInitialBreedGenes(entity.getEntityWorld(), entity.getPosition(), "WanderingTrader");
                                        enhancedPig.setGenes(pigGenes);
                                        enhancedPig.setSharedGenes(pigGenes);
                                        enhancedPig.initilizeAnimalSize();
                                        enhancedPig.getReloadTexture();
                                        enhancedPig.setLeashHolder(entity, true);
                                    }
                                }
                            }
                            break;
                        case 5:
                        default:
                            if (EanimodCommonConfig.COMMON.spawnGeneticLlamas.get()) {
                                BlockPos blockPos = nearbySpawn(((ServerWorld) world), new BlockPos(entity.getPosition()));
                                EnhancedLlama enhancedLlama = ENHANCED_LLAMA.spawn((ServerWorld) world, null, null, null, blockPos, SpawnReason.EVENT, false, false);
                                if (enhancedLlama != null) {
                                    Genes llamaGenes = enhancedLlama.createInitialBreedGenes(entity.getEntityWorld(), entity.getPosition(), "WanderingTrader");
                                    enhancedLlama.setGenes(llamaGenes);
                                    enhancedLlama.setSharedGenes(llamaGenes);
                                    enhancedLlama.initilizeAnimalSize();
                                    enhancedLlama.setInitialCoat();
                                    enhancedLlama.getReloadTexture();
                                    enhancedLlama.setLeashHolder(entity, true);
                                    enhancedLlama.setDespawnDelay(48000, false);
                                }
                            }
                            break;
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
                IWorld world = event.getWorld();
                Entity rider = entity.getRidingEntity();
                entity.remove();

                if(world instanceof ServerWorld) {
                    BlockPos blockPos = nearbySpawn((ServerWorld)world, new BlockPos(entity.getPosition()));
                    EnhancedChicken enhancedChicken = ENHANCED_CHICKEN.spawn((ServerWorld)world, null, null, null, blockPos, SpawnReason.EVENT, false, false);
                    if(enhancedChicken != null) {
                        enhancedChicken.updatePassenger(rider);
                        enhancedChicken.setChickenJockey(true);
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onBlockInteractEvent(PlayerInteractEvent.RightClickBlock event) {
        Item item = event.getItemStack().getItem();
        Block block = event.getWorld().getBlockState(event.getPos()).getBlock();
        if (block instanceof HayBlock && (item instanceof AxeItem || item instanceof SwordItem || item instanceof ShearsItem)) {
            Direction.Axis axis = event.getWorld().getBlockState(event.getPos()).get(BlockStateProperties.AXIS);
            event.getWorld().setBlockState(event.getPos(), ModBlocks.UNBOUNDHAY_BLOCK.getDefaultState().with(BlockStateProperties.AXIS, axis), 11);
        } else if (block instanceof SparseGrassBlock && (item instanceof HoeItem)) {
            event.getWorld().setBlockState(event.getPos(), Blocks.FARMLAND.getDefaultState(), 11);
        } else if (block instanceof SparseGrassBlock && (item instanceof ShovelItem)) {
            event.getWorld().setBlockState(event.getPos(), Blocks.GRASS_PATH.getDefaultState(), 11);
        }
    }

//    @SubscribeEvent
//    public void openInventoryEvent(GuiOpenEvent event) {
//        if (event.getGui() instanceof InventoryScreen) {
//                PlayerEntity player = Minecraft.getInstance().player;
//            if (player!= null) {
//                if (player.world.isRemote()) {
//                    Entity riddenAnimal = player.getRidingEntity();
//                    if (riddenAnimal instanceof EnhancedAnimalRideableAbstract) {
//                        if (!riddenAnimal.getTags().contains("OpenEnhancedAnimalRidenGUI")) {
//                            riddenAnimal.addTag("OpenEnhancedAnimalRidenGUI");
//                        }
//                        event.setCanceled(true);
//                    }
//                }
//            }
//        }


//        if (event instanceof PlayerContainerEvent.Open && player.getRidingEntity() instanceof EnhancedAnimalRideableAbstract) {
//            PlayerInventory playerInv = player.inventory;
//            Entity ridingAnimal = player.getRidingEntity();
//            event.setCanceled(true);
//            ((EnhancedAnimalRideableAbstract)ridingAnimal).openInfoInventory(((EnhancedAnimalRideableAbstract) ridingAnimal), ((EnhancedAnimalRideableAbstract) ridingAnimal).getEnhancedInventory(), player);
//        }
//    }

//    @SubscribeEvent
//    public void onLootTableLoad(LootTableLoadEvent event) {
//        ResourceLocation name = event.getName();
//        String path = event.getName().getPath();
//       if (path.startsWith("chests/")) {
//            if (name.equals(LootTables.CHESTS_WOODLAND_MANSION)) {
//                event.getTable().addPool();
//            }
//       }
//    }

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
