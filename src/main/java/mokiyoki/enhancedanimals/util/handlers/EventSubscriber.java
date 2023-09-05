package mokiyoki.enhancedanimals.util.handlers;

import mokiyoki.enhancedanimals.EnhancedAnimals;
import mokiyoki.enhancedanimals.blocks.EnhancedChickenEggBlock;
import mokiyoki.enhancedanimals.blocks.SparseGrassBlock;
import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import mokiyoki.enhancedanimals.entity.EnhancedAxolotl;
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
import mokiyoki.enhancedanimals.items.EnhancedEgg;
import mokiyoki.enhancedanimals.network.EAEquipmentPacket;
import mokiyoki.enhancedanimals.tileentity.ChickenNestTileEntity;
import mokiyoki.enhancedanimals.util.EanimodVillagerTrades;
import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HayBlock;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.RemoveBlockGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.animal.horse.TraderLlama;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_AXOLOTL;
import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_CHICKEN;
import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_COW;
import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_LLAMA;
import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_MOOBLOOM;
import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_MOOSHROOM;
import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_PIG;
import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_RABBIT;
import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_SHEEP;
import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_TURTLE;

/**
 * Created by saemon on 8/09/2018.
 */
//@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class EventSubscriber {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void editMobs(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Villager) {
            Set<String> tags = entity.getTags();
            if (!tags.contains("eanimodTradeless")) {
                if (!entity.getTags().contains("eanimodTrader")) {
                    entity.addTag("eanimodTrader");
                }
            }
        } else if (entity instanceof Wolf) {
            final Predicate<LivingEntity> TARGETS = (targetEntity) -> {
                return targetEntity instanceof EnhancedSheep || targetEntity instanceof EnhancedRabbit || ((targetEntity instanceof EnhancedPig || targetEntity instanceof EnhancedCow) && targetEntity.isBaby()) ;
            };
            ((Wolf) entity).targetSelector.addGoal(3, new AvoidEntityGoal<>((Wolf) entity, EnhancedLlama.class, 24.0F, 1.5D, 1.5D));
            ((Wolf) entity).targetSelector.addGoal(5, new NonTameRandomTargetGoal<>((Wolf) entity, Animal.class, false, TARGETS));
            ((Wolf) entity).targetSelector.addGoal(6, new NonTameRandomTargetGoal<>((Wolf) entity, EnhancedTurtle.class, false, EnhancedTurtle.TARGET_DRY_BABY));
        } else if (entity instanceof Fox) {
            int priority = ((Fox) entity).getFoxType() == Fox.Type.RED ? 4 : 6;
            ((Fox) entity).targetSelector.addGoal(priority, new NearestAttackableTargetGoal<>((Fox) entity, Animal.class, 10, false, false, (targetEntity) -> {
                return targetEntity instanceof EnhancedChicken || targetEntity instanceof EnhancedRabbit;
            }));
            ((Fox) entity).targetSelector.addGoal(priority, new NearestAttackableTargetGoal<>((Fox) entity, EnhancedTurtle.class, 10, true, false, EnhancedTurtle.TARGET_DRY_BABY));
        } else if (entity instanceof Cat) {
            ((Cat) entity).targetSelector.addGoal(1, new NonTameRandomTargetGoal<>((Cat) entity, EnhancedTurtle.class, false, EnhancedTurtle.TARGET_DRY_BABY));
            ((Cat) entity).targetSelector.addGoal(1, new NonTameRandomTargetGoal<>((Cat) entity, Animal.class, false, (targetEntity) -> {
                return targetEntity.isBaby() && (targetEntity instanceof EnhancedChicken || targetEntity instanceof EnhancedRabbit);
            }));
        } else if (entity instanceof Ocelot) {
            ((Ocelot) entity).targetSelector.addGoal(1, new NearestAttackableTargetGoal<>((Ocelot) entity, EnhancedTurtle.class, 10, true, false, EnhancedTurtle.TARGET_DRY_BABY));
            ((Ocelot) entity).targetSelector.addGoal(1, new NearestAttackableTargetGoal<>((Ocelot) entity, EnhancedChicken.class, false));
        } else if (entity instanceof Zombie) {
            if (!(entity instanceof ZombifiedPiglin)) {
                ((Zombie) entity).targetSelector.addGoal(5, new NearestAttackableTargetGoal<>((Zombie) entity, EnhancedTurtle.class, 10, true, false, EnhancedTurtle.TARGET_DRY_BABY));
                if (entity instanceof Drowned) {
                    ((Drowned) entity).targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(((Drowned) entity), EnhancedAxolotl.class, true, false));
                }
            }
            ((Zombie) entity).targetSelector.addGoal(4, new BreakCustomBlockGoal(ModBlocks.TURTLE_EGG.get(), (Zombie) entity, SoundEvents.ZOMBIE_DESTROY_EGG, SoundSource.HOSTILE, SoundEvents.TURTLE_EGG_BREAK, SoundSource.BLOCKS, 1.0D, 3));
        } else if (entity instanceof AbstractSkeleton) {
            ((AbstractSkeleton) entity).targetSelector.addGoal(3, new NearestAttackableTargetGoal<>((AbstractSkeleton) entity, EnhancedTurtle.class, 10, true, false, EnhancedTurtle.TARGET_DRY_BABY));
        } else if (entity instanceof Guardian) {
            ((Guardian) entity).targetSelector.addGoal(1, new NearestAttackableTargetGoal<>((Guardian) entity, LivingEntity.class, 10, true, false, (targetEntity) -> {
                return (targetEntity instanceof EnhancedAxolotl) && targetEntity.distanceToSqr((Guardian) entity) > 9.0D;
            }));
        } else if (entity instanceof EnhancedAnimalAbstract) {
            if (((EnhancedAnimalAbstract) entity).isLeashed()) {
                Entity leashHolder = ((EnhancedAnimalAbstract) entity).getLeashHolder();
                if (leashHolder instanceof WanderingTrader) {
                    ((EnhancedAnimalAbstract) entity).scheduleDespawn(((WanderingTrader) leashHolder).getDespawnDelay()-1);
                }
            } else if (entity instanceof EnhancedChicken) {
                if (((EnhancedChicken) entity).isChickenJockey()) {
                    ((EnhancedChicken) entity).scheduleDespawn(4000);
                }
            }
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
        if (entity instanceof Villager && entity.getTags().contains("eanimodTrader")) {
            VillagerProfession profession = ((Villager)entity).getVillagerData().getProfession();
            Set<String> tags = entity.getTags();
            if (profession != null) {
                if ((profession.equals(VillagerProfession.LEATHERWORKER) && EanimodCommonConfig.COMMON.leatherWorkerTrades.get())|| (profession.equals(VillagerProfession.SHEPHERD) && EanimodCommonConfig.COMMON.shepardTrades.get())) {
                    int level = ((Villager)entity).getVillagerData().getLevel();
                    switch (level) {
                        case 1 :
                            if (!tags.contains("eanimodTrader_1")) {
                                entity.addTag("eanimodTrader_1");
                                ((Villager)entity).getOffers().add(new EanimodVillagerTrades().getEanimodTrade(profession, 1));
                            }
                            break;
                        case 2 :
                            if (!tags.contains("eanimodTrader_2")) {
                                entity.addTag("eanimodTrader_2");
                                if (ThreadLocalRandom.current().nextBoolean()) {
                                    ((Villager)entity).getOffers().add(new EanimodVillagerTrades().getEanimodTrade(profession,2));
                                }
                            }
                            break;
                        case 3 :
                            if (!tags.contains("eanimodTrader_3")) {
                                entity.addTag("eanimodTrader_3");
                                if (ThreadLocalRandom.current().nextBoolean()) {
                                    ((Villager)entity).getOffers().add(new EanimodVillagerTrades().getEanimodTrade(profession,3));
                                }
                            }
                            break;
                        case 4 :
                            if (!tags.contains("eanimodTrader_4")) {
                                entity.addTag("eanimodTrader_4");
                                if (ThreadLocalRandom.current().nextBoolean()) {
                                    ((Villager)entity).getOffers().add(new EanimodVillagerTrades().getEanimodTrade(profession,4));
                                }
                            }
                            break;
                        case 5 :
                            if (!tags.contains("eanimodTrader_5")) {
                                entity.addTag("eanimodTrader_5");
                                if (ThreadLocalRandom.current().nextBoolean()) {
                                    ((Villager)entity).getOffers().add(new EanimodVillagerTrades().getEanimodTrade(profession,5));
                                }
                            }
                    }
                } else {
                    if (tags.contains("eanimodTrader_1")) {
                        entity.removeTag("eanimodTrader_1");
                    }
                }
            }
        } else if (entity instanceof WanderingTrader) {
            if (!entity.getTags().contains("eanimodTradeless")) {
                entity.addTag("eanimodTradeless");
                int i = 1;
                while (ThreadLocalRandom.current().nextInt(2, 6) >= i) {
                    ((WanderingTrader)entity).getOffers().add(new EanimodVillagerTrades().getWanderingEanimodTrade());
                    i++;
                }
            }
        } else if (!entity.getCommandSenderWorld().isClientSide()) {
            if (entity instanceof Sheep) {
                if (!EanimodCommonConfig.COMMON.spawnVanillaSheep.get() && EanimodCommonConfig.COMMON.spawnGeneticSheep.get()) {
                    if (entity.getClass().getName().toLowerCase().contains("sheep")) {
                        EnhancedSheep enhancedSheep = ENHANCED_SHEEP.get().spawn((ServerLevel) entity.getCommandSenderWorld(), null, null, null, entity.blockPosition(), MobSpawnType.NATURAL, false, false);
                        if (enhancedSheep != null) {
                            enhancedSheep.setFleeceDyeColour(((Sheep) entity).getColor());
                            if (entity.hasCustomName()) {
                                enhancedSheep.setCustomName(entity.getCustomName());
                            }
                            if (((Sheep) entity).isBaby()) {
                                int age = ((Sheep) entity).getAge();
                                enhancedSheep.setAge(age);
                                enhancedSheep.setBirthTime(entity.getCommandSenderWorld(), (-age / 24000) * 72000);
                            } else {
                                enhancedSheep.setAge(0);
                                enhancedSheep.setBirthTime(entity.getCommandSenderWorld(), -500000);
                            }
                            if (((Sheep) entity).isLeashed()) {
                                enhancedSheep.setLeashedTo(((Sheep) entity).getLeashHolder(), true);
                            }
                        }
                        entity.remove(Entity.RemovalReason.DISCARDED);
                    }
                }
            } else if (entity instanceof Chicken) {
                if (!EanimodCommonConfig.COMMON.spawnVanillaChickens.get() && EanimodCommonConfig.COMMON.spawnGeneticChickens.get()) {
                    if (entity.getClass().getName().toLowerCase().contains("chicken")) {
                        if (((Chicken)entity).isChickenJockey){
                            Chicken vanillaChicken = ((Chicken) entity);
                            if (!vanillaChicken.getPassengers().isEmpty() && entity.tickCount > 1) {
                                List<Entity> riders = vanillaChicken.getPassengers();
                                EnhancedChicken enhancedChicken = ENHANCED_CHICKEN.get().spawn((ServerLevel) entity.getCommandSenderWorld(), null, null, null, entity.blockPosition(), MobSpawnType.JOCKEY, false, false);
                                if (enhancedChicken != null) {
                                    if (entity.hasCustomName()) {
                                        enhancedChicken.setCustomName(entity.getCustomName());
                                    }
                                    if (((Chicken) entity).isBaby()) {
                                        int age = ((Chicken) entity).getAge();
                                        enhancedChicken.setAge(age);
                                        enhancedChicken.setBirthTime(entity.getCommandSenderWorld(), (-age / 24000) * 60000);
                                    } else {
                                        enhancedChicken.setAge(0);
                                        enhancedChicken.setBirthTime(entity.getCommandSenderWorld(), -500000);
                                    }
                                    if (((Chicken) entity).isLeashed()) {
                                        enhancedChicken.setLeashedTo(((Chicken) entity).getLeashHolder(), true);
                                    }
                                    enhancedChicken.setChickenJockey(true);
                                    for (Entity rider : riders) {
                                        rider.startRiding(enhancedChicken);
                                    }
                                }
                                entity.remove(Entity.RemovalReason.DISCARDED);
                            } else {
                                if (!entity.hasCustomName()) {
                                    entity.remove(Entity.RemovalReason.DISCARDED);
                                }
                            }
                        } else {
                            EnhancedChicken enhancedChicken = ENHANCED_CHICKEN.get().spawn((ServerLevel) entity.getCommandSenderWorld(), null, null, null, entity.blockPosition(), MobSpawnType.NATURAL, false, false);
                            if (enhancedChicken != null) {
                                if (entity.hasCustomName()) {
                                    enhancedChicken.setCustomName(entity.getCustomName());
                                }
                                if (((Chicken) entity).isBaby()) {
                                    int age = ((Chicken) entity).getAge();
                                    enhancedChicken.setAge(age);
                                    enhancedChicken.setBirthTime(entity.getCommandSenderWorld(), (-age / 24000) * 60000);
                                } else {
                                    enhancedChicken.setAge(0);
                                    enhancedChicken.setBirthTime(entity.getCommandSenderWorld(), -500000);
                                }
                                if (((Chicken) entity).isLeashed()) {
                                    enhancedChicken.setLeashedTo(((Chicken) entity).getLeashHolder(), true);
                                }
                            }
                            entity.remove(Entity.RemovalReason.DISCARDED);
                            event.setCanceled(true);
                        }
                    }
                }
            } else if (entity instanceof Pig) {
                if (!EanimodCommonConfig.COMMON.spawnVanillaPigs.get() && EanimodCommonConfig.COMMON.spawnGeneticPigs.get()) {
                    if (entity.getClass().getName().toLowerCase().contains("pig")) {
                        EnhancedPig enhancedPig = ENHANCED_PIG.get().spawn((ServerLevel) entity.getCommandSenderWorld(), null, null, null, entity.blockPosition(), MobSpawnType.NATURAL, false, false);
                        if (enhancedPig != null) {
                            if (entity.hasCustomName()) {
                                enhancedPig.setCustomName(entity.getCustomName());
                            }
                            if (((Pig) entity).isBaby()) {
                                int age = ((Pig) entity).getAge();
                                enhancedPig.setAge(age);
                                enhancedPig.setBirthTime(entity.getCommandSenderWorld(), (-age / 24000) * 60000);
                            } else {
                                enhancedPig.setAge(0);
                                enhancedPig.setBirthTime(entity.getCommandSenderWorld(), -500000);
                            }
                            if (((Pig) entity).isSaddled()) {
                                enhancedPig.equipAnimal(false, true, null);
                            }
                            if (((Pig) entity).isLeashed()) {
                                enhancedPig.setLeashedTo(((Pig) entity).getLeashHolder(), true);
                            }
                        }
                        entity.remove(Entity.RemovalReason.DISCARDED);
                        event.setCanceled(true);
                    }
                }
            } else if (entity instanceof Cow) {
                if (entity instanceof MushroomCow) {
                    if (!EanimodCommonConfig.COMMON.spawnVanillaMooshroom.get() && EanimodCommonConfig.COMMON.spawnGeneticMooshroom.get()) {
                        if (entity.getClass().getName().toLowerCase().contains("shroom")) {
                            EnhancedMooshroom enhancedMooshroom = ENHANCED_MOOSHROOM.get().spawn((ServerLevel) entity.getCommandSenderWorld(), null, null, null, entity.blockPosition(), MobSpawnType.NATURAL, false, false);
                            if (enhancedMooshroom != null) {
                                if (entity.hasCustomName()) {
                                    enhancedMooshroom.setCustomName(entity.getCustomName());
                                }
                                if (((MushroomCow) entity).isBaby()) {
                                    int age = ((MushroomCow) entity).getAge();
                                    enhancedMooshroom.setAge(age);
                                    enhancedMooshroom.setBirthTime(entity.getCommandSenderWorld(), (-age / 24000) * 84000);
                                } else {
                                    enhancedMooshroom.setAge(0);
                                    enhancedMooshroom.setBirthTime(entity.getCommandSenderWorld(), -500000);
                                }
                                enhancedMooshroom.setMooshroomType(EnhancedMooshroom.Type.valueOf(((MushroomCow) entity).getMushroomType().name()));
                                if (((MushroomCow) entity).isLeashed()) {
                                    enhancedMooshroom.setLeashedTo(((MushroomCow) entity).getLeashHolder(), true);
                                }
                            }
                            entity.remove(Entity.RemovalReason.DISCARDED);
                            event.setCanceled(true);
                        }
                    }
                } else if (entity.getClass().getName().toLowerCase().contains("bloom")) {
                    if (EanimodCommonConfig.COMMON.spawnGeneticMoobloom.get()) {
                        EnhancedMoobloom enhancedMoobloom = ENHANCED_MOOBLOOM.get().spawn((ServerLevel) entity.getCommandSenderWorld(), null, null, null, entity.blockPosition(), MobSpawnType.NATURAL, false, false);
                        if (enhancedMoobloom!=null) {
                            if (entity.hasCustomName()) {
                                enhancedMoobloom.setCustomName(entity.getCustomName());
                            }
                            if (((Cow) entity).isBaby()) {
                                int age = ((Cow) entity).getAge();
                                enhancedMoobloom.setAge(age);
                                enhancedMoobloom.setBirthTime(entity.getCommandSenderWorld(), (-age / 24000) * 84000);
                            } else {
                                enhancedMoobloom.setAge(0);
                                enhancedMoobloom.setBirthTime(entity.getCommandSenderWorld(), -500000);
                            }
                            if (((Cow) entity).isLeashed()) {
                                enhancedMoobloom.setLeashedTo(Objects.requireNonNull(((Cow) entity).getLeashHolder()), true);
                            }

                            entity.remove(Entity.RemovalReason.DISCARDED);
                            event.setCanceled(true);
                        }
                    }
                } else if (entity.getClass().getName().toLowerCase().contains("cow")) {
                    if (!EanimodCommonConfig.COMMON.spawnVanillaCows.get() && EanimodCommonConfig.COMMON.spawnGeneticCows.get()) {
                        EnhancedCow enhancedCow = ENHANCED_COW.get().spawn((ServerLevel) entity.getCommandSenderWorld(), null, null, null, entity.blockPosition(), MobSpawnType.NATURAL, false, false);;
                        if (enhancedCow!=null) {
                            if (entity.hasCustomName()) {
                                enhancedCow.setCustomName(entity.getCustomName());
                            }
                            if (((Cow)entity).isBaby()) {
                                int age = ((Cow) entity).getAge();
                                enhancedCow.setAge(age);
                                enhancedCow.setBirthTime(entity.getCommandSenderWorld(), (-age / 24000) * 84000);
                            } else {
                                enhancedCow.setAge(0);
                                enhancedCow.setBirthTime(entity.getCommandSenderWorld(), -500000);
                            }
                            if (((Cow) entity).isLeashed()) {
                                enhancedCow.setLeashedTo(((Cow) entity).getLeashHolder(), true);
                            }

                            entity.remove(Entity.RemovalReason.DISCARDED);
                            event.setCanceled(true);
                        }
                    }
                }
            } else if (entity instanceof Llama) {
                if (!EanimodCommonConfig.COMMON.spawnVanillaLlamas.get() && EanimodCommonConfig.COMMON.spawnGeneticLlamas.get()) {
                    if (entity.getClass().getName().toLowerCase().contains("llama")) {
                        if (!(((Llama) entity).getLeashHolder() instanceof WanderingTrader)) {
                            EnhancedLlama enhancedLlama = ENHANCED_LLAMA.get().create(entity.getCommandSenderWorld());
                            enhancedLlama.moveTo(entity.getX(), entity.getY(), entity.getZ(), (entity.getYRot()), entity.getXRot());
                            enhancedLlama.yBodyRot = ((Llama) entity).yBodyRot;
                            String breed = "";
                            switch (((Llama) entity).getVariant()) {
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
                            Genes llamaGenes = enhancedLlama.createInitialBreedGenes(entity.getCommandSenderWorld(), entity.blockPosition(), breed);
                            enhancedLlama.setGenes(llamaGenes);
                            enhancedLlama.setSharedGenes(llamaGenes);
                            enhancedLlama.initilizeAnimalSize();
                            enhancedLlama.setInitialCoat();
                            enhancedLlama.getReloadTexture();
                            enhancedLlama.setTame(((Llama) entity).isTamed());

                            if (enhancedLlama != null) {
                                if (entity.hasCustomName()) {
                                    enhancedLlama.setCustomName(entity.getCustomName());
                                }
                                if (((Llama) entity).isBaby()) {
                                    int age = ((Llama) entity).getAge();
                                    enhancedLlama.setAge(age);
                                    enhancedLlama.setBirthTime(entity.getCommandSenderWorld(), (-age / 24000) * 120000);
                                    if (entity instanceof TraderLlama) {
                                        enhancedLlama.equipTraderAnimal(false);
                                    } else {
                                        enhancedLlama.equipAnimal(false, ((Llama) entity).getSwag());
                                    }
                                } else {
                                    enhancedLlama.setAge(0);
                                    enhancedLlama.setBirthTime(entity.getCommandSenderWorld(), -500000);
                                    if (entity instanceof TraderLlama) {
                                        enhancedLlama.equipTraderAnimal(((Llama) entity).hasChest());
                                        enhancedLlama.makeTraderLlama();
                                        enhancedLlama.setInitialDefaults();
                                    } else {
                                        enhancedLlama.equipAnimal(((Llama) entity).hasChest(), ((Llama) entity).getSwag());
                                    }
                                    if (((Llama) entity).hasChest()) {
                                        CompoundTag nbt = entity.serializeNBT();
                                        ListTag listnbt = nbt.getList("Items", 10);

                                        for (int i = 0; i < listnbt.size(); ++i) {
                                            CompoundTag compoundnbt = listnbt.getCompound(i);
                                            int j = compoundnbt.getByte("Slot") & 255;
                                            if (j >= 2 && j + 5 < enhancedLlama.getEnhancedInventory().getContainerSize()) {
                                                enhancedLlama.getEnhancedInventory().setItem(j + 5, ItemStack.of(compoundnbt));
                                            }
                                        }
                                    }
                                }
                                enhancedLlama.getCommandSenderWorld().addFreshEntity(enhancedLlama);
                                if (((Llama) entity).isLeashed()) {
                                    enhancedLlama.setLeashedTo(((Llama) entity).getLeashHolder(), true);
                                }
                            }
                        } else if (entity instanceof TraderLlama) {
                            ((TraderLlama) entity).dropLeash(true, false);
                        }
                        entity.remove(Entity.RemovalReason.DISCARDED);
                        event.setCanceled(true);
                    }
                }
            } else if (entity instanceof Rabbit) {
                if (!EanimodCommonConfig.COMMON.spawnVanillaRabbits.get() && EanimodCommonConfig.COMMON.spawnGeneticRabbits.get()) {
                    if (entity.getClass().getName().toLowerCase().contains("rabbit")) {
                        EnhancedRabbit enhancedRabbit = ENHANCED_RABBIT.get().create(entity.getCommandSenderWorld());
                        if (enhancedRabbit != null) {
                            enhancedRabbit.moveTo(entity.getX(), entity.getY(), entity.getZ(), (entity.getYRot()), entity.getXRot());
                            enhancedRabbit.yBodyRot = ((Rabbit) entity).yBodyRot;
                            String breed = "";
                            switch (((Rabbit) entity).getRabbitType()) {
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
                            Genes rabbitGenes = enhancedRabbit.createInitialBreedGenes(entity.getCommandSenderWorld(), entity.blockPosition(), breed);
                            enhancedRabbit.setGenes(rabbitGenes);
                            enhancedRabbit.setSharedGenes(rabbitGenes);
                            enhancedRabbit.initilizeAnimalSize();
                            enhancedRabbit.setInitialCoat();
                            enhancedRabbit.getReloadTexture();

                            if (entity.hasCustomName()) {
                                enhancedRabbit.setCustomName(entity.getCustomName());
                            }
                            if (((Rabbit) entity).isBaby()) {
                                int age = ((Rabbit) entity).getAge();
                                enhancedRabbit.setAge(age);
                                enhancedRabbit.setBirthTime(entity.getCommandSenderWorld(), (-age / 24000) * 48000);
                            } else {
                                enhancedRabbit.setAge(0);
                                enhancedRabbit.setBirthTime(entity.getCommandSenderWorld(), -500000);
                            }
                            entity.getCommandSenderWorld().addFreshEntity(enhancedRabbit);
                        }
                        entity.remove(Entity.RemovalReason.DISCARDED);
                        event.setCanceled(true);
                    }
                }
            } else if (entity instanceof Turtle) {
                if (!EanimodCommonConfig.COMMON.spawnVanillaTurtles.get() && EanimodCommonConfig.COMMON.spawnGeneticTurtles.get()) {
                    if (entity.getClass().getName().toLowerCase().contains("turtle")) {
                        EnhancedTurtle enhancedTurtle = ENHANCED_TURTLE.get().spawn((ServerLevel) entity.getCommandSenderWorld(), null, null, null, entity.blockPosition(), MobSpawnType.NATURAL, false, false);
                        if (enhancedTurtle != null) {
                            if (entity.hasCustomName()) {
                                enhancedTurtle.setCustomName(entity.getCustomName());
                            }
                            if (((Turtle) entity).isBaby()) {
                                int age = ((Turtle) entity).getAge();
                                enhancedTurtle.setAge(age);
                                enhancedTurtle.setBirthTime(entity.getCommandSenderWorld(), (-age / 24000) * 60000);
                            } else {
                                enhancedTurtle.setAge(0);
                                enhancedTurtle.setBirthTime(entity.getCommandSenderWorld(), -500000);
                            }
                            if (((Turtle) entity).isLeashed()) {
                                enhancedTurtle.setLeashedTo(((Turtle) entity).getLeashHolder(), true);
                            }
                        }
                        entity.remove(Entity.RemovalReason.DISCARDED);
                        event.setCanceled(true);
                    }
                }
            } else if (entity instanceof Axolotl) {
                if (!EanimodCommonConfig.COMMON.spawnVanillaAxolotls.get() && EanimodCommonConfig.COMMON.spawnGeneticAxolotls.get()) {
                    if (entity.getClass().getName().toLowerCase().contains("axolotl")) {
                        EnhancedAxolotl enhancedAxolotl = ENHANCED_AXOLOTL.get().create(entity.getCommandSenderWorld());
                        if (enhancedAxolotl != null) {
                            enhancedAxolotl.moveTo(entity.getX(), entity.getY(), entity.getZ(), (entity.getYRot()), entity.getXRot());
                            enhancedAxolotl.yBodyRot = ((Axolotl) entity).yBodyRot;
                            String breed = "";
                            if (entity.getTags().contains("WanderingTrader")) {
                                breed = "WanderingTrader";
                            } else {
                                switch (((Axolotl) entity).getVariant()) {
                                    case BLUE -> breed = "rarebluevarient";
                                    case CYAN -> breed = "cyanvarient";
                                    case LUCY -> breed = "lucyvarient";
                                    case GOLD -> breed = "goldvarient";
                                    case WILD -> breed = "wildvarient";
                                }
                            }
                            Genes axolotlGenes = enhancedAxolotl.createInitialBreedGenes(entity.getCommandSenderWorld(), entity.blockPosition(), breed);
                            enhancedAxolotl.setGenes(axolotlGenes);
                            enhancedAxolotl.setSharedGenes(axolotlGenes);
                            enhancedAxolotl.initilizeAnimalSize();
                            enhancedAxolotl.getReloadTexture();

                            if (((Axolotl)entity).fromBucket()) {
                                enhancedAxolotl.setFromBucket(true);
                            }
                            if (entity.hasCustomName()) {
                                enhancedAxolotl.setCustomName(entity.getCustomName());
                            }
                            if (((Axolotl) entity).isBaby()) {
                                int age = ((Axolotl) entity).getAge();
                                enhancedAxolotl.setAge(age);
                                enhancedAxolotl.setBirthTime(entity.getCommandSenderWorld(), (-age / 24000) * 48000);
                            } else {
                                enhancedAxolotl.setAge(0);
                                enhancedAxolotl.setBirthTime(entity.getCommandSenderWorld(), -500000);
                            }
                            entity.getCommandSenderWorld().addFreshEntity(enhancedAxolotl);
                        }
                        entity.remove(Entity.RemovalReason.DISCARDED);
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
            Player trackingPlayer = event.getPlayer();

            for(int invSlot = 0; invSlot < 7; invSlot++) {
                ItemStack inventoryItemStack = ((EnhancedAnimalAbstract)targetEntity).getEnhancedInventory().getItem(invSlot);
                EAEquipmentPacket equipmentPacket = new EAEquipmentPacket(targetEntity.getId(), invSlot, inventoryItemStack);
                EnhancedAnimals.channel.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) trackingPlayer), equipmentPacket);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void livingspawnEvent(LivingSpawnEvent.SpecialSpawn event) {
        Entity entity = event.getEntity();
        if (entity instanceof WanderingTrader) {
            LevelAccessor world = event.getWorld();
            ((WanderingTrader)entity).setDespawnDelay(48000);
            if(world instanceof ServerLevel) {
                if(!EanimodCommonConfig.COMMON.spawnVanillaLlamas.get()) {
                    for (int i = 0; i < 2; i++) {
                        BlockPos blockPos = nearbySpawn(((ServerLevel)world), new BlockPos(entity.blockPosition()));
                        EnhancedLlama enhancedLlama = ENHANCED_LLAMA.get().spawn((ServerLevel)world, null, null, null, blockPos, MobSpawnType.EVENT, false, false);
                        if(enhancedLlama != null) {
                            enhancedLlama.setLeashedTo(entity, true);
                            enhancedLlama.setAge(0);
                            enhancedLlama.setBirthTime(entity.getCommandSenderWorld(), -((ThreadLocalRandom.current().nextInt(25)*500000) + 10000000));
                            enhancedLlama.makeTraderLlama();
                            enhancedLlama.setInitialDefaults();
                            enhancedLlama.scheduleDespawn(((WanderingTrader) entity).getDespawnDelay());
                        }
                    }
                }

                if (ThreadLocalRandom.current().nextInt(5) == 0) {
                    List<EntityType> animals = new ArrayList<>();
                    if (EanimodCommonConfig.COMMON.spawnGeneticCows.get() && EanimodCommonConfig.COMMON.wanderingTraderCow.get()) {
                        animals.add(ENHANCED_COW.get());
                    }
                    if (EanimodCommonConfig.COMMON.spawnGeneticSheep.get() && EanimodCommonConfig.COMMON.wanderingTraderSheep.get()) {
                        animals.add(ENHANCED_SHEEP.get());
                    }
                    if (EanimodCommonConfig.COMMON.spawnGeneticChickens.get() && EanimodCommonConfig.COMMON.wanderingTraderChicken.get()) {
                        animals.add(ENHANCED_CHICKEN.get());
                    }
                    if (EanimodCommonConfig.COMMON.spawnGeneticPigs.get() && EanimodCommonConfig.COMMON.wanderingTraderPig.get()) {
                        animals.add(ENHANCED_PIG.get());
                    }
                    if (EanimodCommonConfig.COMMON.spawnGeneticRabbits.get() && EanimodCommonConfig.COMMON.wanderingTraderRabbit.get()) {
                        animals.add(ENHANCED_RABBIT.get());
                    }
                    if (EanimodCommonConfig.COMMON.spawnGeneticLlamas.get() && EanimodCommonConfig.COMMON.wanderingTraderLlama.get()) {
                        animals.add(ENHANCED_LLAMA.get());
                    }
                    if (EanimodCommonConfig.COMMON.spawnGeneticTurtles.get() && EanimodCommonConfig.COMMON.wanderingTraderTurtle.get()) {
                        animals.add(ENHANCED_TURTLE.get());
                    }
                    if (EanimodCommonConfig.COMMON.spawnGeneticMooshroom.get() && EanimodCommonConfig.COMMON.wanderingTraderMooshroom.get()) {
                        animals.add(ENHANCED_MOOSHROOM.get());
                    }
                    if (EanimodCommonConfig.COMMON.spawnGeneticMoobloom.get() && EanimodCommonConfig.COMMON.wanderingTraderMoobloom.get()) {
                        animals.add(ENHANCED_MOOBLOOM.get());
                    }
                    if (EanimodCommonConfig.COMMON.spawnGeneticAxolotls.get() && EanimodCommonConfig.COMMON.wanderingTraderAxolotl.get()) {
                        animals.add(ENHANCED_AXOLOTL.get());
                    }

                    int selection = animals.size()==1?0:ThreadLocalRandom.current().nextInt(animals.size());

                    if (animals.get(selection) == ENHANCED_TURTLE.get()) {
                        ItemStack stack = new ItemStack(ModItems.TURTLE_EGG_ITEM.get(), 1);
                        ((WanderingTrader) entity).getOffers().add(new MerchantOffer(new ItemStack(Items.EMERALD, 16), stack, ThreadLocalRandom.current().nextBoolean()?1:2, 1, 0.0F));
                    } else if (animals.get(selection) == ENHANCED_AXOLOTL.get()) {
                        ItemStack stack = new ItemStack(Items.AXOLOTL_BUCKET, 1);
                        CompoundTag tag = stack.getOrCreateTag();
                        tag.putInt("Variant", ThreadLocalRandom.current().nextInt(1200)==0?4:ThreadLocalRandom.current().nextInt(4));
                        //TODO make some sort of nicer wandering trader axolotl bucket. The other animals should probably also get some sort of item with which they can be traded for.
//                        CompoundTag entityTags = new CompoundTag();
//                        ListTag listtag = new ListTag();
//                        listtag.add(StringTag.valueOf("WanderingTrader"));
//                        entityTags.put("Tags", listtag);
//                        tag.put("EntityTags",entityTags);
                        ((WanderingTrader) entity).getOffers().add(new MerchantOffer(new ItemStack(Items.EMERALD, 16), stack, ThreadLocalRandom.current().nextBoolean()?1:2, 1, 0.0F));
                    } else {
                        for (int i = 1; i <= 2; i++) {
                            BlockPos blockPos = nearbySpawn(((ServerLevel) world), new BlockPos(entity.blockPosition()));
                            Entity animal = animals.get(selection).spawn((ServerLevel) world, null, null, null, blockPos, MobSpawnType.EVENT, false, false);
                            if (animal instanceof EnhancedAnimalAbstract) {
                                EnhancedAnimalAbstract enhancedAnimal = (EnhancedAnimalAbstract) animal;
                                Genes animalGenes = enhancedAnimal.createInitialBreedGenes(entity.getCommandSenderWorld(), entity.blockPosition(), "WanderingTrader");
                                enhancedAnimal.setGenes(animalGenes);
                                enhancedAnimal.setSharedGenes(animalGenes);
                                enhancedAnimal.setInitialDefaults();
                                enhancedAnimal.initilizeAnimalSize();
                                enhancedAnimal.getReloadTexture();
                                enhancedAnimal.setLeashedTo(entity, true);
                                enhancedAnimal.scheduleDespawn(((WanderingTrader) entity).getDespawnDelay());
                            }
                            if (animal instanceof EnhancedLlama || ThreadLocalRandom.current().nextBoolean()) {
                                break;
                            }
                        }
                    }
                }
            }

            if (!entity.getTags().contains("eanimodTradeless")) {
                entity.addTag("eanimodTradeless");
                if (EanimodCommonConfig.COMMON.wanderingTraderTrades.get()) {
                    int i = 1;
                    while (ThreadLocalRandom.current().nextInt(2, 6) >= i) {
                        ((WanderingTrader) entity).getOffers().add(new EanimodVillagerTrades().getWanderingEanimodTrade());
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
            Direction.Axis axis = event.getWorld().getBlockState(event.getPos()).getValue(BlockStateProperties.AXIS);
            event.getWorld().setBlock(event.getPos(), ModBlocks.UNBOUNDHAY_BLOCK.get().defaultBlockState().setValue(BlockStateProperties.AXIS, axis), 11);
        } else if (block instanceof SparseGrassBlock && (item instanceof HoeItem)) {
            event.getWorld().setBlock(event.getPos(), Blocks.FARMLAND.defaultBlockState(), 11);
        } else if (block instanceof SparseGrassBlock && (item instanceof ShovelItem)) {
            event.getWorld().setBlock(event.getPos(), Blocks.DIRT_PATH.defaultBlockState(), 11);
        }
    }


    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onLivingHurtEvent(LivingHurtEvent event) {
        if (EanimodCommonConfig.COMMON.onlyKilledWithAxe.get()) {
            if (event.getEntity() instanceof EnhancedAnimalAbstract) {
                Entity damageSource = event.getSource().getDirectEntity();
                if (damageSource instanceof Player) {
                    if (!(((Player) damageSource).getMainHandItem().getItem() instanceof AxeItem)) {
                        event.setCanceled(true);
                    }
                } else {
                    event.setCanceled(true);
                }
            }
        } else if (event.getEntity().hasCustomName()) {
            Entity entity = event.getEntity();
            if (entity instanceof EnhancedPig || entity.getClass().getName().toLowerCase().contains("pig")) {
                String name = entity.getCustomName().getString().toLowerCase();
                if (name.equals("technoblade") || name.equals("techno")) {
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

    private BlockPos nearbySpawn(Level world, BlockPos blockPosOfEntity) {
        BlockPos blockpos = blockPosOfEntity;

        for(int i = 0; i < 10; ++i) {
            int j = blockPosOfEntity.getX() + world.random.nextInt(4 * 2) - 4;
            int k = blockPosOfEntity.getZ() + world.random.nextInt(4 * 2) - 4;
            int l = world.getHeight(Heightmap.Types.WORLD_SURFACE, j, k);
            BlockPos blockpos1 = new BlockPos(j, l, k);
            if (NaturalSpawner.isSpawnPositionOk(SpawnPlacements.Type.ON_GROUND, world, blockpos1, EntityType.WANDERING_TRADER)) {
                blockpos = blockpos1;
                break;
            }
        }

        return blockpos;
    }

    private class BreakCustomBlockGoal extends RemoveBlockGoal {
        Random rand = new Random();
        SoundEvent breakingEvent;
        SoundSource breakingSoundCategory;
        SoundEvent brokenEvent;
        SoundSource brokenSoundCategory;

        BreakCustomBlockGoal(Block block, PathfinderMob creatureIn, SoundEvent breakingEvent, SoundSource breakingSoundCategory, SoundEvent brokenEvent, SoundSource brokenSoundCategory, double speed, int yMax) {
            super(block, creatureIn, speed, yMax);
            this.breakingEvent = breakingEvent;
            this.breakingSoundCategory = breakingSoundCategory;
            this.brokenEvent = brokenEvent;
            this.brokenSoundCategory = brokenSoundCategory;
        }

        public void playDestroyProgressSound(LevelAccessor worldIn, BlockPos pos) {
            worldIn.playSound((Player)null, pos, this.breakingEvent, this.breakingSoundCategory, 0.5F, 0.9F + rand.nextFloat() * 0.2F);
        }

        public void playBreakSound(Level worldIn, BlockPos pos) {
            worldIn.playSound((Player)null, pos, this.brokenEvent, this.brokenSoundCategory, 0.7F, 0.9F + worldIn.random.nextFloat() * 0.2F);
        }

        public double acceptedDistance() {
            return 1.14D;
        }
    }
}
