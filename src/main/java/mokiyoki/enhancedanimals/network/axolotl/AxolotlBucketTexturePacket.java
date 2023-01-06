package mokiyoki.enhancedanimals.network.axolotl;

import mokiyoki.enhancedanimals.entity.EnhancedAxolotl;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AxolotlBucketTexturePacket {

    private int entityID;
    private int[] bucketTexture;

    public AxolotlBucketTexturePacket(int entityID, int[] bucketTexture) {
        this.entityID = entityID;
        this.bucketTexture = bucketTexture;
    }

    public AxolotlBucketTexturePacket(FriendlyByteBuf buf) {
        this.entityID = buf.readVarInt();
        this.bucketTexture = buf.readVarIntArray();
    }

    public void writePacketData(FriendlyByteBuf buf) {
        buf.writeVarInt(this.entityID);
        buf.writeVarIntArray(this.bucketTexture);
    }

    public static void processPacket(AxolotlBucketTexturePacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer serverPlayer = ctx.get().getSender();
            if (serverPlayer != null && serverPlayer.level instanceof ServerLevel) {
                ServerLevel server = (ServerLevel)serverPlayer.level;
                Entity entity = server.getEntity(packet.getEntityID());
                if (entity instanceof EnhancedAxolotl) {
                    EnhancedAxolotl axolotl = (EnhancedAxolotl) entity;
                    axolotl.setBucketImageData(packet.bucketTexture);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }

    private int getEntityID() {
        return this.entityID;
    }

}
