package mokiyoki.enhancedanimals.entity;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;
import java.util.UUID;

public interface EnhancedAnimalTamableInterface {

    @Nullable
    public UUID getOwnerUUID();

    public void setOwnerUUID(@Nullable UUID uuid);

    public Animal getAnimal();

    public default void tame(Player player) {
        this.setTame(true);
        this.setOwnerUUID(player.getUUID());
        if (player instanceof ServerPlayer) {
            CriteriaTriggers.TAME_ANIMAL.trigger((ServerPlayer)player, getAnimal());
        }

    }

    public boolean isTame();
    public boolean isOrderedToSit();

    public void setTame(boolean tameness);
    public void setOrderedToSit(boolean sitting);

    @Nullable
    public MinecraftServer getServer();

    public default void addTamableSaveData(CompoundTag compound) {
        if (this.getOwnerUUID() != null) {
            compound.putUUID("Owner", this.getOwnerUUID());
        }

        compound.putBoolean("Sitting", this.isOrderedToSit());
    }

    public default void readTamableSaveData(CompoundTag compound) {
        UUID uuid;
        if (compound.hasUUID("Owner")) {
            uuid = compound.getUUID("Owner");
        } else {
            String s = compound.getString("Owner");
            uuid = OldUsersConverter.convertMobOwnerIfNecessary(this.getServer(), s);
        }

        if (uuid != null) {
            try {
                this.setOwnerUUID(uuid);
                this.setTame(true);
            } catch (Throwable throwable) {
                this.setTame(false);
            }
        }

        this.setOrderedToSit(compound.getBoolean("Sitting"));
    }

}
