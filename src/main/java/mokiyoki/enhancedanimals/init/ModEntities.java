package mokiyoki.enhancedanimals.init;

import mokiyoki.enhancedanimals.entity.EnhancedAxolotl;
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.entity.EnhancedCow;
import mokiyoki.enhancedanimals.entity.EnhancedEntityEgg;
import mokiyoki.enhancedanimals.entity.EnhancedEntityLlamaSpit;
import mokiyoki.enhancedanimals.entity.EnhancedHorse;
import mokiyoki.enhancedanimals.entity.EnhancedLlama;
import mokiyoki.enhancedanimals.entity.EnhancedMoobloom;
import mokiyoki.enhancedanimals.entity.EnhancedMooshroom;
import mokiyoki.enhancedanimals.entity.EnhancedPig;
import mokiyoki.enhancedanimals.entity.EnhancedRabbit;
import mokiyoki.enhancedanimals.entity.EnhancedSheep;
import mokiyoki.enhancedanimals.entity.EnhancedTurtle;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES_DEFERRED_REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITIES, Reference.MODID);

    public static final RegistryObject<EntityType<EnhancedEntityLlamaSpit>> ENHANCED_LLAMA_SPIT = ENTITIES_DEFERRED_REGISTRY.register("enhanced_entity_llama_spit", () -> EntityType.Builder.<EnhancedEntityLlamaSpit>of(EnhancedEntityLlamaSpit::new, MobCategory.MISC).sized(0.25F, 0.25F).build(Reference.MODID + ":enhanced_entity_llama_spit"));
    public static final RegistryObject<EntityType<EnhancedEntityEgg>> ENHANCED_ENTITY_EGG_ENTITY_TYPE = ENTITIES_DEFERRED_REGISTRY.register("enhanced_entity_egg", () -> EntityType.Builder.<EnhancedEntityEgg>of(EnhancedEntityEgg::new, MobCategory.MISC).sized(0.25F, 0.25F).build(Reference.MODID + ":enhanced_entity_egg"));
    public static final RegistryObject<EntityType<EnhancedChicken>> ENHANCED_CHICKEN = ENTITIES_DEFERRED_REGISTRY.register("enhanced_chicken", () -> EntityType.Builder.of(EnhancedChicken::new, MobCategory.CREATURE).sized(0.4F, 0.7F).setTrackingRange(64).setUpdateInterval(1).build(Reference.MODID + ":enhanced_chicken"));
    public static final RegistryObject<EntityType<EnhancedRabbit>> ENHANCED_RABBIT = ENTITIES_DEFERRED_REGISTRY.register("enhanced_rabbit", () -> EntityType.Builder.of(EnhancedRabbit::new, MobCategory.CREATURE).sized(0.4F, 0.5F).build(Reference.MODID + ":enhanced_rabbit"));
    public static final RegistryObject<EntityType<EnhancedSheep>> ENHANCED_SHEEP = ENTITIES_DEFERRED_REGISTRY.register("enhanced_sheep", () -> EntityType.Builder.of(EnhancedSheep::new, MobCategory.CREATURE).sized(0.9F, 1.3F).build(Reference.MODID + ":enhanced_sheep"));
    public static final RegistryObject<EntityType<EnhancedLlama>> ENHANCED_LLAMA = ENTITIES_DEFERRED_REGISTRY.register("enhanced_llama", () -> EntityType.Builder.of(EnhancedLlama::new, MobCategory.CREATURE).sized(0.9F, 1.87F).build(Reference.MODID + ":enhanced_llama"));
    public static final RegistryObject<EntityType<EnhancedCow>> ENHANCED_COW = ENTITIES_DEFERRED_REGISTRY.register("enhanced_cow", () -> EntityType.Builder.of(EnhancedCow::new, MobCategory.CREATURE).sized(1.0F, 1.5F).build(Reference.MODID + ":enhanced_cow"));
    public static final RegistryObject<EntityType<EnhancedMooshroom>> ENHANCED_MOOSHROOM = ENTITIES_DEFERRED_REGISTRY.register("enhanced_mooshroom", () -> EntityType.Builder.of(EnhancedMooshroom::new, MobCategory.CREATURE).sized(1.0F, 1.5F).build(Reference.MODID + ":enhanced_mooshroom"));
    public static final RegistryObject<EntityType<EnhancedMoobloom>> ENHANCED_MOOBLOOM = ENTITIES_DEFERRED_REGISTRY.register("enhanced_moobloom", () -> EntityType.Builder.of(EnhancedMoobloom::new, MobCategory.CREATURE).sized(1.0F, 1.5F).build(Reference.MODID + ":enhanced_moobloom"));
    public static final RegistryObject<EntityType<EnhancedPig>> ENHANCED_PIG = ENTITIES_DEFERRED_REGISTRY.register("enhanced_pig", () -> EntityType.Builder.of(EnhancedPig::new, MobCategory.CREATURE).sized(0.9F, 0.9F).build(Reference.MODID + ":enhanced_pig"));
    public static final RegistryObject<EntityType<EnhancedHorse>> ENHANCED_HORSE = ENTITIES_DEFERRED_REGISTRY.register("enhanced_horse", () -> EntityType.Builder.of(EnhancedHorse::new, MobCategory.CREATURE).sized(1.0F, 1.6F).build(Reference.MODID + ":enhanced_horse"));
    public static final RegistryObject<EntityType<EnhancedTurtle>> ENHANCED_TURTLE = ENTITIES_DEFERRED_REGISTRY.register("enhanced_turtle", () -> EntityType.Builder.of(EnhancedTurtle::new, MobCategory.CREATURE).sized(1.2F, 0.4F).build(Reference.MODID + ":enhanced_turtle"));
    public static final RegistryObject<EntityType<EnhancedAxolotl>> ENHANCED_AXOLOTL = ENTITIES_DEFERRED_REGISTRY.register("enhanced_axolotl", () -> EntityType.Builder.of(EnhancedAxolotl::new, MobCategory.AXOLOTLS).sized(0.75F, 0.42F).build(Reference.MODID + ":enhanced_axolotl"));
//    public static final EntityType<EnhancedCat> ENHANCED_CAT = EntityType.Builder.create(EnhancedCat::new, EntityClassification.CREATURE).size(0.6F, 0.7F).build(Reference.MODID + ":enhanced_cat");
//    public static final EntityType<EnhancedBee> ENHANCED_BEE = EntityType.Builder.create(EnhancedBee::new, EntityClassification.CREATURE).size(0.4F, 0.4F).build(Reference.MODID + ":enhanced_bee");

    public static void register(IEventBus modEventBus) {
        ENTITIES_DEFERRED_REGISTRY.register(modEventBus);
    }
}
