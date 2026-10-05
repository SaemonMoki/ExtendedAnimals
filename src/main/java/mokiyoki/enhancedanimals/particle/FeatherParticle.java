package mokiyoki.enhancedanimals.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.EmptyModelData;

import java.util.List;

//This is just basically copying the maths from items dropping and applying it to the feather particles
@OnlyIn(Dist.CLIENT)
public class FeatherParticle extends TextureSheetParticle {
    private static final float HALF_SIZE = 0.25F;
    private static final float CENTRE_OFFSET = 0.25F;

    private final float bobOffs;

    protected FeatherParticle(ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
        super(level, x, y, z);
        ItemStack feather = new ItemStack(Items.FEATHER);
        BakedModel model = Minecraft.getInstance().getItemRenderer().getModel(feather, level, (LivingEntity) null, 0);
        this.setSprite(model.getParticleIcon(EmptyModelData.INSTANCE));

        this.setSize(0.25F, 0.25F);
        this.setPos(x, y, z);
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;
        this.lifetime = 20;
        this.bobOffs = this.random.nextFloat() * (float) Math.PI * 2.0F;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
            return;
        }

        this.yd -= 0.04D;
        this.move(this.xd, this.yd, this.zd);

        float friction = 0.98F;
        if (this.onGround) {
            BlockPos below = new BlockPos(this.x, this.y - 1.0D, this.z);
            friction = this.level.getBlockState(below).getFriction(this.level, below, null) * 0.98F;
        }
        this.xd *= friction;
        this.yd *= 0.98D;
        this.zd *= friction;
        if (this.onGround && this.yd < 0.0D) {
            this.yd *= -0.5D;
        }
    }

    @Override
    public void move(double dx, double dy, double dz) {
        Vec3 moved = Entity.collideBoundingBox(null, new Vec3(dx, dy, dz), this.getBoundingBox(), this.level, List.of());
        if (moved.lengthSqr() != 0.0D) {
            this.setBoundingBox(this.getBoundingBox().move(moved));
            this.setLocationFromBoundingbox();
        }

        this.onGround = dy != moved.y && dy < 0.0D;
        if (dx != moved.x) {
            this.xd = 0.0D;
        }
        if (dz != moved.z) {
            this.zd = 0.0D;
        }
    }

    @Override
    public void render(VertexConsumer buffer, Camera camera, float partialTicks) {
        Vec3 cam = camera.getPosition();
        float age = this.age + partialTicks;
        float bob = Mth.sin(age / 10.0F + this.bobOffs) * 0.1F + 0.1F;
        float spin = age / 20.0F + this.bobOffs;

        float cx = (float) (Mth.lerp(partialTicks, this.xo, this.x) - cam.x());
        float cy = (float) (Mth.lerp(partialTicks, this.yo, this.y) - cam.y()) + bob + CENTRE_OFFSET;
        float cz = (float) (Mth.lerp(partialTicks, this.zo, this.z) - cam.z());

        float rx = Mth.cos(spin) * HALF_SIZE;
        float rz = -Mth.sin(spin) * HALF_SIZE;

        float u0 = this.getU0();
        float u1 = this.getU1();
        float v0 = this.getV0();
        float v1 = this.getV1();
        int light = this.getLightColor(partialTicks);

        vertex(buffer, cx - rx, cy - HALF_SIZE, cz - rz, u0, v1, light);
        vertex(buffer, cx + rx, cy - HALF_SIZE, cz + rz, u1, v1, light);
        vertex(buffer, cx + rx, cy + HALF_SIZE, cz + rz, u1, v0, light);
        vertex(buffer, cx - rx, cy + HALF_SIZE, cz - rz, u0, v0, light);
        vertex(buffer, cx - rx, cy - HALF_SIZE, cz - rz, u0, v1, light);
        vertex(buffer, cx - rx, cy + HALF_SIZE, cz - rz, u0, v0, light);
        vertex(buffer, cx + rx, cy + HALF_SIZE, cz + rz, u1, v0, light);
        vertex(buffer, cx + rx, cy - HALF_SIZE, cz + rz, u1, v1, light);
    }

    private void vertex(VertexConsumer buffer, float x, float y, float z, float u, float v, int light) {
        buffer.vertex(x, y, z).uv(u, v).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.TERRAIN_SHEET;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
            return new FeatherParticle(level, x, y, z, xd, yd, zd);
        }
    }
}
