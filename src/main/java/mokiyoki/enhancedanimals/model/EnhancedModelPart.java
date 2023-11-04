package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.core.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.stream.Stream;

@OnlyIn(Dist.CLIENT)
public class EnhancedModelPart {
   public float x;
   public float y;
   public float z;
   public float xRot;
   public float yRot;
   public float zRot;
   public boolean visible = true;
   private final List<EnhancedModelPart.Cube> cubes;
   private final Map<String, EnhancedModelPart> children;

   public EnhancedModelPart(List<EnhancedModelPart.Cube> cubes, Map<String, EnhancedModelPart> children) {
      this.cubes = cubes;
      this.children = children;
   }

   public PartPose storePose() {
      return PartPose.offsetAndRotation(this.x, this.y, this.z, this.xRot, this.yRot, this.zRot);
   }

   public void loadPose(PartPose p_171323_) {
      this.x = p_171323_.x;
      this.y = p_171323_.y;
      this.z = p_171323_.z;
      this.xRot = p_171323_.xRot;
      this.yRot = p_171323_.yRot;
      this.zRot = p_171323_.zRot;
   }

   public void copyFrom(EnhancedModelPart p_104316_) {
      this.xRot = p_104316_.xRot;
      this.yRot = p_104316_.yRot;
      this.zRot = p_104316_.zRot;
      this.x = p_104316_.x;
      this.y = p_104316_.y;
      this.z = p_104316_.z;
   }

   public EnhancedModelPart getChild(String p_171325_) {
      EnhancedModelPart modelpart = this.children.get(p_171325_);
      if (modelpart == null) {
         throw new NoSuchElementException("Can't find part " + p_171325_);
      } else {
         return modelpart;
      }
   }

   public void setPos(float p_104228_, float p_104229_, float p_104230_) {
      this.x = p_104228_;
      this.y = p_104229_;
      this.z = p_104230_;
   }

   public void setRotation(float p_171328_, float p_171329_, float p_171330_) {
      this.xRot = p_171328_;
      this.yRot = p_171329_;
      this.zRot = p_171330_;
   }

   public void render(PoseStack p_104302_, VertexConsumer p_104303_, int p_104304_, int p_104305_) {
      this.render(p_104302_, p_104303_, p_104304_, p_104305_, 1.0F, 1.0F, 1.0F, 1.0F);
   }

   public void render(PoseStack p_104307_, VertexConsumer p_104308_, int p_104309_, int p_104310_, float p_104311_, float p_104312_, float p_104313_, float p_104314_) {
      if (this.visible) {
         if (!this.cubes.isEmpty() || !this.children.isEmpty()) {
            p_104307_.pushPose();
            this.translateAndRotate(p_104307_);
            this.compile(p_104307_.last(), p_104308_, p_104309_, p_104310_, p_104311_, p_104312_, p_104313_, p_104314_);

            for(EnhancedModelPart modelpart : this.children.values()) {
               modelpart.render(p_104307_, p_104308_, p_104309_, p_104310_, p_104311_, p_104312_, p_104313_, p_104314_);
            }

            p_104307_.popPose();
         }
      }
   }

   public void visit(PoseStack p_171310_, EnhancedModelPart.Visitor p_171311_) {
      this.visit(p_171310_, p_171311_, "");
   }

   private void visit(PoseStack p_171313_, EnhancedModelPart.Visitor p_171314_, String p_171315_) {
      if (!this.cubes.isEmpty() || !this.children.isEmpty()) {
         p_171313_.pushPose();
         this.translateAndRotate(p_171313_);
         PoseStack.Pose posestack$pose = p_171313_.last();

         for(int i = 0; i < this.cubes.size(); ++i) {
            p_171314_.visit(posestack$pose, p_171315_, i, this.cubes.get(i));
         }

         String s = p_171315_ + "/";
         this.children.forEach((p_171320_, p_171321_) -> {
            p_171321_.visit(p_171313_, p_171314_, s + p_171320_);
         });
         p_171313_.popPose();
      }
   }

   public void translateAndRotate(PoseStack p_104300_) {
      p_104300_.translate((double)(this.x / 16.0F), (double)(this.y / 16.0F), (double)(this.z / 16.0F));
      if (this.xRot != 0.0F || this.yRot != 0.0F || this.zRot != 0.0F) {
         p_104300_.mulPose((new Quaternionf()).rotationZYX(this.zRot, this.yRot, this.xRot));
      }

   }

   private void compile(PoseStack.Pose p_104291_, VertexConsumer p_104292_, int p_104293_, int p_104294_, float p_104295_, float p_104296_, float p_104297_, float p_104298_) {
      for(EnhancedModelPart.Cube modelpart$cube : this.cubes) {
         modelpart$cube.compile(p_104291_, p_104292_, p_104293_, p_104294_, p_104295_, p_104296_, p_104297_, p_104298_);
      }

   }

   public EnhancedModelPart.Cube getRandomCube(Random p_104329_) {
      return this.cubes.get(p_104329_.nextInt(this.cubes.size()));
   }

   public boolean isEmpty() {
      return this.cubes.isEmpty();
   }

   public Stream<EnhancedModelPart> getAllParts() {
      return Stream.concat(Stream.of(this), this.children.values().stream().flatMap(EnhancedModelPart::getAllParts));
   }

   @OnlyIn(Dist.CLIENT)
   public static class Cube {
      private final EnhancedModelPart.Polygon[] polygons;
      public final float minX;
      public final float minY;
      public final float minZ;
      public final float maxX;
      public final float maxY;
      public final float maxZ;

      public Cube(int p_104343_, int p_104344_, float p_104345_, float p_104346_, float p_104347_, float p_104348_, float p_104349_, float p_104350_, float p_104351_, float p_104352_, float p_104353_, boolean p_104354_, float p_104355_, float p_104356_) {
         this.minX = p_104345_;
         this.minY = p_104346_;
         this.minZ = p_104347_;
         this.maxX = p_104345_ + p_104348_;
         this.maxY = p_104346_ + p_104349_;
         this.maxZ = p_104347_ + p_104350_;
         this.polygons = new EnhancedModelPart.Polygon[6];
         float f = p_104345_ + p_104348_;
         float f1 = p_104346_ + p_104349_;
         float f2 = p_104347_ + p_104350_;
         p_104345_ -= p_104351_;
         p_104346_ -= p_104352_;
         p_104347_ -= p_104353_;
         f += p_104351_;
         f1 += p_104352_;
         f2 += p_104353_;
         if (p_104354_) {
            float f3 = f;
            f = p_104345_;
            p_104345_ = f3;
         }

         EnhancedModelPart.Vertex modelpart$vertex7 = new EnhancedModelPart.Vertex(p_104345_, p_104346_, p_104347_, 0.0F, 0.0F);
         EnhancedModelPart.Vertex modelpart$vertex = new EnhancedModelPart.Vertex(f, p_104346_, p_104347_, 0.0F, 8.0F);
         EnhancedModelPart.Vertex modelpart$vertex1 = new EnhancedModelPart.Vertex(f, f1, p_104347_, 8.0F, 8.0F);
         EnhancedModelPart.Vertex modelpart$vertex2 = new EnhancedModelPart.Vertex(p_104345_, f1, p_104347_, 8.0F, 0.0F);
         EnhancedModelPart.Vertex modelpart$vertex3 = new EnhancedModelPart.Vertex(p_104345_, p_104346_, f2, 0.0F, 0.0F);
         EnhancedModelPart.Vertex modelpart$vertex4 = new EnhancedModelPart.Vertex(f, p_104346_, f2, 0.0F, 8.0F);
         EnhancedModelPart.Vertex modelpart$vertex5 = new EnhancedModelPart.Vertex(f, f1, f2, 8.0F, 8.0F);
         EnhancedModelPart.Vertex modelpart$vertex6 = new EnhancedModelPart.Vertex(p_104345_, f1, f2, 8.0F, 0.0F);
         float f4 = (float)p_104343_;
         float f5 = (float)p_104343_ + p_104350_;
         float f6 = (float)p_104343_ + p_104350_ + p_104348_;
         float f7 = (float)p_104343_ + p_104350_ + p_104348_ + p_104348_;
         float f8 = (float)p_104343_ + p_104350_ + p_104348_ + p_104350_;
         float f9 = (float)p_104343_ + p_104350_ + p_104348_ + p_104350_ + p_104348_;
         float f10 = (float)p_104344_;
         float f11 = (float)p_104344_ + p_104350_;
         float f12 = (float)p_104344_ + p_104350_ + p_104349_;
         this.polygons[2] = new EnhancedModelPart.Polygon(new EnhancedModelPart.Vertex[]{modelpart$vertex4, modelpart$vertex3, modelpart$vertex7, modelpart$vertex}, f5, f10, f6, f11, p_104355_, p_104356_, p_104354_, Direction.DOWN);
         this.polygons[3] = new EnhancedModelPart.Polygon(new EnhancedModelPart.Vertex[]{modelpart$vertex1, modelpart$vertex2, modelpart$vertex6, modelpart$vertex5}, f6, f11, f7, f10, p_104355_, p_104356_, p_104354_, Direction.UP);
         this.polygons[1] = new EnhancedModelPart.Polygon(new EnhancedModelPart.Vertex[]{modelpart$vertex7, modelpart$vertex3, modelpart$vertex6, modelpart$vertex2}, f4, f11, f5, f12, p_104355_, p_104356_, p_104354_, Direction.WEST);
         this.polygons[4] = new EnhancedModelPart.Polygon(new EnhancedModelPart.Vertex[]{modelpart$vertex, modelpart$vertex7, modelpart$vertex2, modelpart$vertex1}, f5, f11, f6, f12, p_104355_, p_104356_, p_104354_, Direction.NORTH);
         this.polygons[0] = new EnhancedModelPart.Polygon(new EnhancedModelPart.Vertex[]{modelpart$vertex4, modelpart$vertex, modelpart$vertex1, modelpart$vertex5}, f6, f11, f8, f12, p_104355_, p_104356_, p_104354_, Direction.EAST);
         this.polygons[5] = new EnhancedModelPart.Polygon(new EnhancedModelPart.Vertex[]{modelpart$vertex3, modelpart$vertex4, modelpart$vertex5, modelpart$vertex6}, f8, f11, f9, f12, p_104355_, p_104356_, p_104354_, Direction.SOUTH);
      }

      public void compile(PoseStack.Pose p_171333_, VertexConsumer p_171334_, int p_171335_, int p_171336_, float p_171337_, float p_171338_, float p_171339_, float p_171340_) {
         Matrix4f matrix4f = p_171333_.pose();
         Matrix3f matrix3f = p_171333_.normal();

         for(EnhancedModelPart.Polygon modelpart$polygon : this.polygons) {
            Vector3f vector3f = matrix3f.transform(new Vector3f((Vector3fc)modelpart$polygon.normal));
            float f = vector3f.x();
            float f1 = vector3f.y();
            float f2 = vector3f.z();

            for(EnhancedModelPart.Vertex modelpart$vertex : modelpart$polygon.vertices) {
               float f3 = modelpart$vertex.pos.x() / 16.0F;
               float f4 = modelpart$vertex.pos.y() / 16.0F;
               float f5 = modelpart$vertex.pos.z() / 16.0F;
               Vector4f vector4f = matrix4f.transform(new Vector4f(f3, f4, f5, 1.0F));
               p_171334_.vertex(vector4f.x(), vector4f.y(), vector4f.z(), p_171337_, p_171338_, p_171339_, p_171340_, modelpart$vertex.u, modelpart$vertex.v, p_171336_, p_171335_, f, f1, f2);
            }
         }

      }
   }

   @OnlyIn(Dist.CLIENT)
   static class Polygon {
      public final EnhancedModelPart.Vertex[] vertices;
      public final Vector3f normal;

      public Polygon(EnhancedModelPart.Vertex[] p_104362_, float p_104363_, float p_104364_, float p_104365_, float p_104366_, float p_104367_, float p_104368_, boolean p_104369_, Direction p_104370_) {
         this.vertices = p_104362_;
         float f = 0.0F / p_104367_;
         float f1 = 0.0F / p_104368_;
         p_104362_[0] = p_104362_[0].remap(p_104365_ / p_104367_ - f, p_104364_ / p_104368_ + f1);
         p_104362_[1] = p_104362_[1].remap(p_104363_ / p_104367_ + f, p_104364_ / p_104368_ + f1);
         p_104362_[2] = p_104362_[2].remap(p_104363_ / p_104367_ + f, p_104366_ / p_104368_ - f1);
         p_104362_[3] = p_104362_[3].remap(p_104365_ / p_104367_ - f, p_104366_ / p_104368_ - f1);
         if (p_104369_) {
            int i = p_104362_.length;

            for(int j = 0; j < i / 2; ++j) {
               EnhancedModelPart.Vertex modelpart$vertex = p_104362_[j];
               p_104362_[j] = p_104362_[i - 1 - j];
               p_104362_[i - 1 - j] = modelpart$vertex;
            }
         }

         this.normal = p_104370_.step();
         if (p_104369_) {
            this.normal.mul(-1.0F, 1.0F, 1.0F);
         }

      }
   }

   @OnlyIn(Dist.CLIENT)
   static class Vertex {
      public final Vector3f pos;
      public final float u;
      public final float v;

      public Vertex(float p_104375_, float p_104376_, float p_104377_, float p_104378_, float p_104379_) {
         this(new Vector3f(p_104375_, p_104376_, p_104377_), p_104378_, p_104379_);
      }

      public EnhancedModelPart.Vertex remap(float p_104385_, float p_104386_) {
         return new EnhancedModelPart.Vertex(this.pos, p_104385_, p_104386_);
      }

      public Vertex(Vector3f p_104381_, float p_104382_, float p_104383_) {
         this.pos = p_104381_;
         this.u = p_104382_;
         this.v = p_104383_;
      }
   }

   @FunctionalInterface
   @OnlyIn(Dist.CLIENT)
   public interface Visitor {
      void visit(PoseStack.Pose p_171342_, String p_171343_, int p_171344_, EnhancedModelPart.Cube p_171345_);
   }
}