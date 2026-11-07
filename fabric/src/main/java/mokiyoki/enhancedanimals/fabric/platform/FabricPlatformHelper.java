package mokiyoki.enhancedanimals.fabric.platform;

import io.netty.buffer.Unpooled;
import mokiyoki.enhancedanimals.platform.IPlatformHelper;
import mokiyoki.enhancedanimals.platform.attachment.LevelAttachment;
import mokiyoki.enhancedanimals.platform.attachment.LevelSerializable;
import mokiyoki.enhancedanimals.platform.config.ConfigBuilder;
import mokiyoki.enhancedanimals.platform.config.TomlFileConfigBuilder;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }

    @Override
    public boolean canEntityGrief(Level level, Entity entity) {
        return level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
    }

    @Override
    public boolean onAnimalTame(Animal animal, Player tamer) {
        // Fabric has no animal tame event.
        return false;
    }

    @Override
    public void cureEffectsLikeMilk(LivingEntity entity) {
        entity.removeAllEffects();
    }

    @Override
    public SpawnEggItem createSpawnEgg(Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor, Item.Properties properties) {
        // Entity types are already registered here, see EnhancedAnimals.init().
        return new SpawnEggItem(type.get(), backgroundColor, highlightColor, properties);
    }

    @Override
    public CreativeModeTab.Builder creativeTabBuilder(ResourceKey<CreativeModeTab> placeAfter) {
        // Fabric has no tab ordering.
        return FabricItemGroup.builder();
    }

    @Override
    public <T extends AbstractContainerMenu> MenuType<T> createExtendedMenuType(ExtendedMenuFactory<T> factory) {
        return new ExtendedScreenHandlerType<>((windowId, inventory, data) -> factory.create(windowId, inventory, wrap(data, inventory)), ByteBufCodecs.BYTE_ARRAY);
    }

    @Override
    public void openExtendedMenu(ServerPlayer player, MenuProvider provider, Consumer<RegistryFriendlyByteBuf> extraData) {
        RegistryFriendlyByteBuf buf = new RegistryFriendlyByteBuf(Unpooled.buffer(), player.registryAccess());
        byte[] data;
        try {
            extraData.accept(buf);
            data = new byte[buf.readableBytes()];
            buf.readBytes(data);
        } finally {
            buf.release();
        }
        player.openMenu(new ExtendedScreenHandlerFactory<byte[]>() {
            @Override
            public byte[] getScreenOpeningData(ServerPlayer serverPlayer) {
                return data;
            }

            @Override
            public Component getDisplayName() {
                return provider.getDisplayName();
            }

            @Override
            public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player menuPlayer) {
                return provider.createMenu(windowId, inventory, menuPlayer);
            }
        });
    }

    private static RegistryFriendlyByteBuf wrap(byte[] data, Inventory inventory) {
        return new RegistryFriendlyByteBuf(Unpooled.wrappedBuffer(data), inventory.player.registryAccess());
    }

    @Override
    public SoundType getSoundType(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
        return state.getSoundType();
    }

    @Override
    public float getFriction(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
        return state.getBlock().getFriction();
    }

    @Override
    public float getBiomeDownfall(Biome biome) {
        return biome.climateSettings.downfall();
    }

    @Override
    public boolean isAddedToLevel(Entity entity) {
        return !entity.isRemoved() && entity.level().getEntity(entity.getId()) == entity;
    }

    @Override
    public boolean isRestoringBlockSnapshots(Level level) {
        return false;
    }

    @Override
    public <T extends LevelSerializable> LevelAttachment<T> createLevelAttachment(String name, Supplier<T> factory) {
        return new SavedDataLevelAttachment<>(name, factory);
    }

    @Override
    public <T> T registerConfig(String fileName, Function<ConfigBuilder, T> factory) {
        TomlFileConfigBuilder builder = new TomlFileConfigBuilder(getConfigDirectory().resolve(fileName));
        T config = factory.apply(builder);
        builder.load();
        return config;
    }
}
