package mokiyoki.enhancedanimals.network.axolotl;

import mokiyoki.enhancedanimals.entity.EnhancedAxolotl;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static mokiyoki.enhancedanimals.util.Reference.MODID;

public class AxolotlBucketTexturePacket implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<AxolotlBucketTexturePacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MODID, "axolotl_bucket_texture"));
    public static final StreamCodec<FriendlyByteBuf, AxolotlBucketTexturePacket> STREAM_CODEC = StreamCodec.ofMember(AxolotlBucketTexturePacket::writePacketData, AxolotlBucketTexturePacket::new);

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

    public static void processPacket(AxolotlBucketTexturePacket packet, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            if (ctx.player() instanceof ServerPlayer serverPlayer && serverPlayer.level() instanceof ServerLevel) {
                ServerLevel server = (ServerLevel)serverPlayer.level();
                Entity entity = server.getEntity(packet.getEntityID());
                if (entity instanceof EnhancedAxolotl) {
                    EnhancedAxolotl axolotl = (EnhancedAxolotl) entity;
                    axolotl.setBucketImageData(packet.bucketTexture);
                }
            }
        });
    }

    @Override
    public CustomPacketPayload.Type<AxolotlBucketTexturePacket> type() {
        return TYPE;
    }

    private int getEntityID() {
        return this.entityID;
    }

}
