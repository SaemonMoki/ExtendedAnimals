package mokiyoki.enhancedanimals.util.handlers;

import mokiyoki.enhancedanimals.EnhancedAnimals;
import mokiyoki.enhancedanimals.blocks.SparseGrassBlock;
import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.entity.EnhancedCow;
import mokiyoki.enhancedanimals.entity.EnhancedLlama;
import mokiyoki.enhancedanimals.entity.EnhancedMoobloom;
import mokiyoki.enhancedanimals.entity.EnhancedMooshroom;
import mokiyoki.enhancedanimals.entity.EnhancedPig;
import mokiyoki.enhancedanimals.entity.EnhancedRabbit;
import mokiyoki.enhancedanimals.entity.EnhancedSheep;
import mokiyoki.enhancedanimals.entity.EnhancedTurtle;
import mokiyoki.enhancedanimals.init.FoodSerialiser;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.network.EAEquipmentPacket;
import mokiyoki.enhancedanimals.util.EanimodVillagerTrades;
import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.HayBlock;
import net.minecraft.block.TurtleEggBlock;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.BreakBlockGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.NonTamedTargetGoal;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.WanderingTraderEntity;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.BlazeEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.monster.ZombifiedPiglinEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.passive.OcelotEntity;
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
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.WorldEntitySpawner;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
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
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_TURTLE;

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
            ((WolfEntity) entity).targetSelector.addGoal(6, new NonTamedTargetGoal<>((WolfEntity) entity, EnhancedTurtle.class, false, EnhancedTurtle.TARGET_DRY_BABY));
        } else if (entity instanceof FoxEntity) {
            int priority = ((FoxEntity) entity).getVariantType() == FoxEntity.Type.RED ? 4 : 6;
            ((FoxEntity) entity).targetSelector.addGoal(priority, new NearestAttackableTargetGoal<>((FoxEntity) entity, AnimalEntity.class, 10, false, false, (targetEntity) -> {
                return targetEntity instanceof EnhancedChicken || targetEntity instanceof EnhancedRabbit;
            }));
            ((FoxEntity) entity).targetSelector.addGoal(priority, new NearestAttackableTargetGoal<>((FoxEntity) entity, EnhancedTurtle.class, 10, true, false, EnhancedTurtle.TARGET_DRY_BABY));
        } else if (entity instanceof CatEntity) {
            ((CatEntity) entity).targetSelector.addGoal(1, new NonTamedTargetGoal<>((CatEntity) entity, EnhancedTurtle.class, false, EnhancedTurtle.TARGET_DRY_BABY));
            ((CatEntity) entity).targetSelector.addGoal(1, new NonTamedTargetGoal<>((CatEntity) entity, AnimalEntity.class, false, (targetEntity) -> {
                return targetEntity.isChild() && (targetEntity instanceof EnhancedChicken || targetEntity instanceof EnhancedRabbit);
            }));
        } else if (entity instanceof OcelotEntity) {
            ((OcelotEntity) entity).targetSelector.addGoal(1, new NearestAttackableTargetGoal<>((OcelotEntity) entity, EnhancedTurtle.class, 10, true, false, EnhancedTurtle.TARGET_DRY_BABY));
            ((OcelotEntity) entity).targetSelector.addGoal(1, new NearestAttackableTargetGoal<>((OcelotEntity) entity, EnhancedChicken.class, false));
        } else if (entity instanceof ZombieEntity) {
            if (!(entity instanceof ZombifiedPiglinEntity)) {
                ((ZombieEntity) entity).targetSelector.addGoal(5, new NearestAttackableTargetGoal<>((ZombieEntity) entity, EnhancedTurtle.class, 10, true, false, EnhancedTurtle.TARGET_DRY_BABY));
            }

            ((ZombieEntity) entity).targetSelector.addGoal(4, new BreakCustomBlockGoal(ModBlocks.TURTLE_EGG, (ZombieEntity) entity, SoundEvents.ENTITY_ZOMBIE_DESTROY_EGG, SoundCategory.HOSTILE, SoundEvents.ENTITY_TURTLE_EGG_BREAK, SoundCategory.BLOCKS, 1.0D, 3));

        } else if (entity instanceof AbstractSkeletonEntity) {
            ((AbstractSkeletonEntity) entity).targetSelector.addGoal(3, new NearestAttackableTargetGoal<>((AbstractSkeletonEntity) entity, EnhancedTurtle.class, 10, true, false, EnhancedTurtle.TARGET_DRY_BABY));
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void babyEntitySpawnEvent(BabyEntitySpawnEvent event) {
        if (event.getParentA() instanceof EnhancedAnimalAbstract && event.getChild() == null) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onAddReloadListenerEvent(AddReloadListenerEvent e) {
        e.addListener(new FoodSerialiser());
    }


    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof VillagerEntity && entity.getTags().contains("eanimodTrader")) {
            VillagerProfession profession = ((VillagerEntity)entity).getVillagerData().getProfession();
            Set<String> tags = entity.getTags();
            if (profession != null) {
                if ((profession.equals(VillagerProfession.LEATHERWORKER) && EanimodCommonConfig.COMMON.leatherWorkerTrades.get())|| (profession.equals(VillagerProfession.SHEPHERD) && EanimodCommonConfig.COMMON.shepardTrades.get())) {
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
                    if (entity.getClass().getName().toLowerCase().contains("sheep")) {
                        EnhancedSheep enhancedSheep = ENHANCED_SHEEP.spawn((ServerWorld) entity.getEntityWorld(), null, null, null, entity.getPosition(), SpawnReason.NATURAL, false, false);
                        if (enhancedSheep != null) {
                            enhancedSheep.setFleeceDyeColour(((SheepEntity) entity).getFleeceColor());
                            if (entity.hasCustomName()) {
                                enhancedSheep.setCustomName(entity.getCustomName());
                            }
                            if (((SheepEntity) entity).isChild()) {
                                int age = ((SheepEntity) entity).getGrowingAge();
                                enhancedSheep.setBirthTimeFromVanillaAge(age, entity.getEntityWorld());
                            } else {
                                enhancedSheep.setBirthTimeFromVanillaAge(0, entity.getEntityWorld());
                            }
                            if (((SheepEntity) entity).getLeashed()) {
                                enhancedSheep.setLeashHolder(((SheepEntity) entity).getLeashHolder(), true);
                            }
                        }
                        entity.remove();
                    }
                }
            } else if (entity instanceof ChickenEntity) {
                if (!EanimodCommonConfig.COMMON.spawnVanillaChickens.get() && EanimodCommonConfig.COMMON.spawnGeneticChickens.get()) {
                    if (entity.getClass().getName().toLowerCase().contains("chicken")) {
                        EnhancedChicken enhancedChicken = ENHANCED_CHICKEN.spawn((ServerWorld) entity.getEntityWorld(), null, null, null, entity.getPosition(), SpawnReason.NATURAL, false, false);
                        if (enhancedChicken != null) {
                            if (entity.hasCustomName()) {
                                enhancedChicken.setCustomName(entity.getCustomName());
                            }
                            if (((ChickenEntity) entity).isChild()) {
                                int age = ((ChickenEntity) entity).getGrowingAge();
                                enhancedChicken.setGrowingAge(age);
                                enhancedChicken.setBirthTime(entity.getEntityWorld(), (-age / 24000) * 60000);
                            } else {
                                enhancedChicken.setGrowingAge(0);
                                enhancedChicken.setBirthTime(entity.getEntityWorld(), -500000);
                            }
                            if (((ChickenEntity) entity).getLeashed()) {
                                enhancedChicken.setLeashHolder(((ChickenEntity) entity).getLeashHolder(), true);
                            }
                            if (entity.isBeingRidden()) {
                                Entity rider = entity.getRidingEntity();
                                if (rider != null) {
                                    enhancedChicken.updatePassenger(rider);
                                    enhancedChicken.setChickenJockey(true);
                                }
                            }
                        }
                        entity.remove();
                        event.setCanceled(true);
                    }
                }
            } else if (entity instanceof PigEntity) {
                if (!EanimodCommonConfig.COMMON.spawnVanillaPigs.get() && EanimodCommonConfig.COMMON.spawnGeneticPigs.get()) {
                    if (entity.getClass().getName().toLowerCase().contains("pig")) {
                        EnhancedPig enhancedPig = ENHANCED_PIG.spawn((ServerWorld) entity.getEntityWorld(), null, null, null, entity.getPosition(), SpawnReason.NATURAL, false, false);
                        if (enhancedPig != null) {
                            if (entity.hasCustomName()) {
                                enhancedPig.setCustomName(entity.getCustomName());
                            }
                            if (((PigEntity) entity).isChild()) {
                                int age = ((PigEntity) entity).getGrowingAge();
                                enhancedPig.setGrowingAge(age);
                                enhancedPig.setBirthTime(entity.getEntityWorld(), (-age / 24000) * 60000);
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
                }
            } else if (entity instanceof MooshroomEntity) {
                if (!EanimodCommonConfig.COMMON.spawnVanillaMooshroom.get() && EanimodCommonConfig.COMMON.spawnGeneticMooshroom.get()) {
                    if (entity.getClass().getName().toLowerCase().contains("mooshroom")) {
                        EnhancedMooshroom enhancedMooshroom = ENHANCED_MOOSHROOM.spawn((ServerWorld) entity.getEntityWorld(), null, null, null, entity.getPosition(), SpawnReason.NATURAL, false, false);
                        if (enhancedMooshroom != null) {
                            if (entity.hasCustomName()) {
                                enhancedMooshroom.setCustomName(entity.getCustomName());
                            }
                            if (((MooshroomEntity) entity).isChild()) {
                                int age = ((MooshroomEntity) entity).getGrowingAge();
                                enhancedMooshroom.setGrowingAge(age);
                                enhancedMooshroom.setBirthTime(entity.getEntityWorld(), (-age / 24000) * 84000);
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
                    } else if (entity.getClass().getName().toLowerCase().contains("cow")) {
                        enhancedCow = ENHANCED_COW.spawn((ServerWorld) entity.getEntityWorld(), null, null, null, entity.getPosition(), SpawnReason.NATURAL, false, false);
                    } else {
                        enhancedCow = null;
                        flag = false;
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
                    if (entity.getClass().getName().toLowerCase().contains("llama")) {
                        if (!(((LlamaEntity) entity).getLeashHolder() instanceof WanderingTraderEntity)) {
                            EnhancedLlama enhancedLlama = ENHANCED_LLAMA.create(entity.getEntityWorld());
                            enhancedLlama.setLocationAndAngles(entity.getPosX(), entity.getPosY(), entity.getPosZ(), (entity.rotationYaw), entity.rotationPitch);
                            enhancedLlama.renderYawOffset = ((LlamaEntity) entity).renderYawOffset;
                            String breed = "";
                            switch (((LlamaEntity) entity).getVariant()) {
                                case 0:
                                    breed = "cream";
                                    break;
                                case 1:
                                    breed = "white";
                                    break;
                                case 2:
                                    breed = "brown";
                                    break;
                                case 3:
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
                }
            } else if (entity instanceof RabbitEntity) {
                if (!EanimodCommonConfig.COMMON.spawnVanillaRabbits.get() && EanimodCommonConfig.COMMON.spawnGeneticRabbits.get()) {
                    if (entity.getClass().getName().toLowerCase().contains("rabbit")) {
                        EnhancedRabbit enhancedRabbit = ENHANCED_RABBIT.create(entity.getEntityWorld());
                        if (enhancedRabbit != null) {
                            enhancedRabbit.setLocationAndAngles(entity.getPosX(), entity.getPosY(), entity.getPosZ(), (entity.rotationYaw), entity.rotationPitch);
                            enhancedRabbit.renderYawOffset = ((RabbitEntity) entity).renderYawOffset;
                            String breed = "";
                            switch (((RabbitEntity) entity).getRabbitType()) {
                                case 1:
                                case 3:
                                    breed = "snow";
                                    break;
                                case 4:
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
                                enhancedRabbit.setBirthTime(entity.getEntityWorld(), (-age / 24000) * 48000);
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
                    List<EntityType> animals = new ArrayList<>();
                    if (EanimodCommonConfig.COMMON.spawnGeneticCows.get() && EanimodCommonConfig.COMMON.wanderingTraderCow.get()) {
                        animals.add(ENHANCED_COW);
                    }
                    if (EanimodCommonConfig.COMMON.spawnGeneticSheep.get() && EanimodCommonConfig.COMMON.wanderingTraderSheep.get()) {
                        animals.add(ENHANCED_SHEEP);
                    }
                    if (EanimodCommonConfig.COMMON.spawnGeneticChickens.get() && EanimodCommonConfig.COMMON.wanderingTraderChicken.get()) {
                        animals.add(ENHANCED_CHICKEN);
                    }
                    if (EanimodCommonConfig.COMMON.spawnGeneticPigs.get() && EanimodCommonConfig.COMMON.wanderingTraderPig.get()) {
                        animals.add(ENHANCED_PIG);
                    }
                    if (EanimodCommonConfig.COMMON.spawnGeneticRabbits.get() && EanimodCommonConfig.COMMON.wanderingTraderRabbit.get()) {
                        animals.add(ENHANCED_RABBIT);
                    }
                    if (EanimodCommonConfig.COMMON.spawnGeneticLlamas.get() && EanimodCommonConfig.COMMON.wanderingTraderLlama.get()) {
                        animals.add(ENHANCED_LLAMA);
                        r = 1;
                    }
                    if (EanimodCommonConfig.COMMON.spawnGeneticTurtles.get() && EanimodCommonConfig.COMMON.wanderingTraderTurtle.get()) {
                        animals.add(ENHANCED_TURTLE);
                    }
                    if (EanimodCommonConfig.COMMON.spawnGeneticMooshroom.get() && EanimodCommonConfig.COMMON.wanderingTraderMooshroom.get()) {
                        animals.add(ENHANCED_MOOSHROOM);
                    }
                    if (EanimodCommonConfig.COMMON.spawnGeneticMoobloom.get() && EanimodCommonConfig.COMMON.wanderingTraderMoobloom.get()) {
                        animals.add(ENHANCED_MOOBLOOM);
                    }

                    Collections.shuffle(animals);

                    for (int i = 0; i < r; i++) {
                        BlockPos blockPos = nearbySpawn(((ServerWorld) world), new BlockPos(entity.getPosition()));
                        Entity animal = animals.get(0).spawn((ServerWorld) world, null, null, null, blockPos, SpawnReason.EVENT, false, false);
                        if (animal instanceof EnhancedAnimalAbstract) {
                            EnhancedAnimalAbstract enhancedAnimal = (EnhancedAnimalAbstract)animal;
                            Genes animalGenes = enhancedAnimal.createInitialBreedGenes(entity.getEntityWorld(), entity.getPosition(), "WanderingTrader");
                            enhancedAnimal.setGenes(animalGenes);
                            enhancedAnimal.setSharedGenes(animalGenes);
                            enhancedAnimal.setInitialDefaults();
                            enhancedAnimal.initilizeAnimalSize();
                            enhancedAnimal.getReloadTexture();
                            enhancedAnimal.setLeashHolder(entity, true);
                        }
                    }
                }
            }

            if (!entity.getTags().contains("eanimodTradeless")) {
                entity.addTag("eanimodTradeless");
                if (EanimodCommonConfig.COMMON.wanderingTraderTrades.get()) {
                    int i = 1;
                    while (ThreadLocalRandom.current().nextInt(2, 6) >= i) {
                        ((WanderingTraderEntity) entity).getOffers().add(new EanimodVillagerTrades().getWanderingEanimodTrade());
                        i++;
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

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onLivingHurtEvent(LivingHurtEvent event) {
        if (EanimodCommonConfig.COMMON.onlyKilledWithAxe.get()) {
            if (event.getEntity() instanceof EnhancedAnimalAbstract) {
                Entity damageSource = event.getSource().getTrueSource();
                if (damageSource instanceof PlayerEntity) {
                    if (!(((PlayerEntity) damageSource).getHeldItemMainhand().getItem() instanceof AxeItem)) {
                        event.setCanceled(true);
                    }
                } else {
                    event.setCanceled(true);
                }
            }
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

    private class BreakCustomBlockGoal extends BreakBlockGoal {
        Random rand = new Random();
        SoundEvent breakingEvent;
        SoundCategory breakingSoundCategory;
        SoundEvent brokenEvent;
        SoundCategory brokenSoundCategory;

        BreakCustomBlockGoal(Block block, CreatureEntity creatureIn, SoundEvent breakingEvent, SoundCategory breakingSoundCategory, SoundEvent brokenEvent, SoundCategory brokenSoundCategory, double speed, int yMax) {
            super(block, creatureIn, speed, yMax);
            this.breakingEvent = breakingEvent;
            this.breakingSoundCategory = breakingSoundCategory;
            this.brokenEvent = brokenEvent;
            this.brokenSoundCategory = brokenSoundCategory;
        }

        public void playBreakingSound(IWorld worldIn, BlockPos pos) {
            worldIn.playSound((PlayerEntity)null, pos, this.breakingEvent, this.breakingSoundCategory, 0.5F, 0.9F + rand.nextFloat() * 0.2F);
        }

        public void playBrokenSound(World worldIn, BlockPos pos) {
            worldIn.playSound((PlayerEntity)null, pos, this.brokenEvent, this.brokenSoundCategory, 0.7F, 0.9F + worldIn.rand.nextFloat() * 0.2F);
        }

        public double getTargetDistanceSq() {
            return 1.14D;
        }
    }
}
