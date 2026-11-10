package mokiyoki.enhancedanimals.neoforge.platform;

import com.mojang.serialization.Codec;
import mokiyoki.enhancedanimals.platform.IPlatformHelper;
import mokiyoki.enhancedanimals.platform.attachment.LevelAttachment;
import mokiyoki.enhancedanimals.platform.attachment.LevelSerializable;
import mokiyoki.enhancedanimals.platform.attachment.PlayerAttachment;
import mokiyoki.enhancedanimals.platform.config.ConfigBuilder;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.fml.ModList;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.common.EffectCures;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class NeoForgePlatformHelper implements IPlatformHelper {

    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, Reference.MODID);

    static {
        ATTACHMENT_TYPES.register(NeoForgeRegistrationFactory.modEventBus(Reference.MODID));
    }

    @Override
    public String getPlatformName() {
        return "NeoForge";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }

    @Override
    public Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }

    @Override
    public boolean canEntityGrief(Level level, Entity entity) {
        return EventHooks.canEntityGrief(level, entity);
    }

    @Override
    public boolean onAnimalTame(Animal animal, Player tamer) {
        return EventHooks.onAnimalTame(animal, tamer);
    }

    @Override
    public void cureEffectsLikeMilk(LivingEntity entity) {
        entity.removeEffectsCuredBy(EffectCures.MILK);
    }

    @Override
    public SpawnEggItem createSpawnEgg(Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor, Item.Properties properties) {
        return new DeferredSpawnEggItem(type, backgroundColor, highlightColor, properties);
    }

    @Override
    public CreativeModeTab.Builder creativeTabBuilder(ResourceKey<CreativeModeTab> placeAfter) {
        return CreativeModeTab.builder().withTabsBefore(placeAfter);
    }

    @Override
    public <T extends AbstractContainerMenu> MenuType<T> createExtendedMenuType(ExtendedMenuFactory<T> factory) {
        return IMenuTypeExtension.create(factory::create);
    }

    @Override
    public void openExtendedMenu(ServerPlayer player, MenuProvider provider, Consumer<RegistryFriendlyByteBuf> extraData) {
        player.openMenu(provider, extraData);
    }

    @Override
    public SoundType getSoundType(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
        return state.getSoundType(level, pos, entity);
    }

    @Override
    public float getFriction(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
        return state.getFriction(level, pos, entity);
    }

    @Override
    public float getBiomeDownfall(Biome biome) {
        return biome.getModifiedClimateSettings().downfall();
    }

    @Override
    public boolean isAddedToLevel(Entity entity) {
        return entity.isAddedToLevel();
    }

    @Override
    public boolean isRestoringBlockSnapshots(Level level) {
        return level.restoringBlockSnapshots;
    }

    @Override
    public <T> PlayerAttachment<T> createPlayerAttachment(String name, Codec<T> codec, Supplier<T> defaultValue) {
        DeferredHolder<AttachmentType<?>, AttachmentType<T>> type = ATTACHMENT_TYPES.register(name, () -> AttachmentType.builder(defaultValue).serialize(codec).build());
        return new PlayerAttachment<>() {
            @Override
            public T get(Player player) {
                return player.getData(type);
            }

            @Override
            public void set(Player player, T value) {
                player.setData(type, value);
            }
        };
    }

    @Override
    public <T extends LevelSerializable> LevelAttachment<T> createLevelAttachment(String name, Supplier<T> factory) {
        // Same format as AttachmentType.serializable, so existing worlds keep their data.
        DeferredHolder<AttachmentType<?>, AttachmentType<T>> type = ATTACHMENT_TYPES.register(name, () -> AttachmentType.builder(factory)
                .serialize(new IAttachmentSerializer<Tag, T>() {
                    @Override
                    public T read(IAttachmentHolder holder, Tag tag, HolderLookup.Provider provider) {
                        T data = factory.get();
                        data.deserializeNBT(provider, tag);
                        return data;
                    }

                    @Override
                    public Tag write(T attachment, HolderLookup.Provider provider) {
                        return attachment.serializeNBT(provider);
                    }
                })
                .build());
        return level -> level.getData(type);
    }

    @Override
    public <T> T registerConfig(String fileName, Function<ConfigBuilder, T> factory) {
        NeoForgeConfigBuilder builder = new NeoForgeConfigBuilder();
        T config = factory.apply(builder);
        ModList.get().getModContainerById(Reference.MODID)
                .orElseThrow(() -> new IllegalStateException("Config registered before the mod was constructed"))
                .registerConfig(ModConfig.Type.STARTUP, builder.build(), fileName);
        return config;
    }
}
