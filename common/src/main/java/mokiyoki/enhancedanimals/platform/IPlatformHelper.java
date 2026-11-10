package mokiyoki.enhancedanimals.platform;

import mokiyoki.enhancedanimals.platform.attachment.LevelAttachment;
import mokiyoki.enhancedanimals.platform.attachment.LevelSerializable;
import mokiyoki.enhancedanimals.platform.config.ConfigBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
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

public interface IPlatformHelper {

    String getPlatformName();

    boolean isModLoaded(String modId);

    boolean isDevelopmentEnvironment();

    Path getConfigDirectory();

    boolean canEntityGrief(Level level, Entity entity);

    boolean onAnimalTame(Animal animal, Player tamer);

    void cureEffectsLikeMilk(LivingEntity entity);

    SpawnEggItem createSpawnEgg(Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor, Item.Properties properties);

    CreativeModeTab.Builder creativeTabBuilder(ResourceKey<CreativeModeTab> placeAfter);

    <T extends AbstractContainerMenu> MenuType<T> createExtendedMenuType(ExtendedMenuFactory<T> factory);

    void openExtendedMenu(ServerPlayer player, MenuProvider provider, Consumer<RegistryFriendlyByteBuf> extraData);

    SoundType getSoundType(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity);

    float getFriction(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity);

    float getBiomeDownfall(Biome biome);

    boolean isAddedToLevel(Entity entity);

    boolean isRestoringBlockSnapshots(Level level);

    <T extends LevelSerializable> LevelAttachment<T> createLevelAttachment(String name, Supplier<T> factory);

    <T> T registerConfig(String fileName, Function<ConfigBuilder, T> factory);

    @FunctionalInterface
    interface ExtendedMenuFactory<T extends AbstractContainerMenu> {
        T create(int windowId, Inventory inventory, RegistryFriendlyByteBuf data);
    }
}
