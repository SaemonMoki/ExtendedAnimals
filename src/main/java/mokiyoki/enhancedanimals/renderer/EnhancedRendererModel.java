package mokiyoki.enhancedanimals.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class EnhancedRendererModel extends RendererModel {
    private boolean compiled;
    private int displayList;
    public final String boxName;

    public EnhancedRendererModel(Model model, String boxNameIn) {
        super(model, boxNameIn);
        model.boxList.add(this);
        this.boxName = boxNameIn;
        this.setTextureSize(model.textureWidth, model.textureHeight);
    }

    public EnhancedRendererModel(Model model, int texOffX, int texOffY) {
        this(model, (String)null);
        this.setTextureOffset(texOffX, texOffY);
    }

    public EnhancedRendererModel(Model model, int texOffX, int texOffY, String boxNameIn) {
        this(model, boxNameIn);
        this.setTextureOffset(texOffX, texOffY);
    }

    public void render(float scale, Map<String, List<Float>> mapOfScale, List<String> boxesToNotRender) {
        if (!this.isHidden) {
            if (this.showModel) {
                if (!this.compiled) {
                    this.compileDisplayList(scale, boxesToNotRender);
                }

//                GlStateManager.pushMatrix();
                GlStateManager.translatef(this.offsetX, this.offsetY, this.offsetZ);

                if (mapOfScale != null) {
                    if (mapOfScale.containsKey(this.boxName)) {
                        List<Float> scaleAmounts = mapOfScale.get(this.boxName);
                        Float scaling = scaleAmounts.get(0);
                        if (scaling != null) {
                            GlStateManager.scalef(scaling, scaling, scaling);
                        }
                        if (scaleAmounts.get(1) != null && scaleAmounts.get(2) != null && scaleAmounts.get(3) != null) {
                            GlStateManager.translatef(scaleAmounts.get(1), scaleAmounts.get(2), scaleAmounts.get(3));
                        }
                    }
                }

                if (this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F) {
                    if (this.rotationPointX == 0.0F && this.rotationPointY == 0.0F && this.rotationPointZ == 0.0F) {
                        if (this.boxName == null || !(boxesToNotRender.contains(this.boxName))) {
                            GlStateManager.callList(this.displayList);
                        }
                        if (this.childModels != null) {
                            for(int k = 0; k < this.childModels.size(); ++k) {
                                ((EnhancedRendererModel)this.childModels.get(k)).render(scale, mapOfScale, boxesToNotRender);
                            }
                        }
                    } else {
                        GlStateManager.pushMatrix();
                        GlStateManager.translatef(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
                        if (this.boxName == null || !(boxesToNotRender.contains(this.boxName))) {
                            GlStateManager.callList(this.displayList);
                        }
                        if (this.childModels != null) {
                            for(int j = 0; j < this.childModels.size(); ++j) {
                                ((EnhancedRendererModel)this.childModels.get(j)).render(scale, mapOfScale, boxesToNotRender);
                            }
                        }

                        GlStateManager.popMatrix();
                    }
                } else {
                    GlStateManager.pushMatrix();
                    GlStateManager.translatef(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
                    if (this.rotateAngleZ != 0.0F) {
                        GlStateManager.rotatef(this.rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
                    }

                    if (this.rotateAngleY != 0.0F) {
                        GlStateManager.rotatef(this.rotateAngleY * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
                    }

                    if (this.rotateAngleX != 0.0F) {
                        GlStateManager.rotatef(this.rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
                    }

                    if (this.boxName == null || !(boxesToNotRender.contains(this.boxName))) {
                        GlStateManager.callList(this.displayList);
                    }
                    if (this.childModels != null) {
                        for(int i = 0; i < this.childModels.size(); ++i) {
                            ((EnhancedRendererModel)this.childModels.get(i)).render(scale, mapOfScale, boxesToNotRender);
                        }
                    }

                    GlStateManager.popMatrix();
                }

//                GlStateManager.popMatrix();
            }
        }
    }

    /**
     * Compiles a GL display list for this model
     */
    private void compileDisplayList(float scale, List<String> boxesToNotRender) {
        this.displayList = GLAllocation.generateDisplayLists(1);
        GlStateManager.newList(this.displayList, 4864);
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();

        for (int i = 0; i < this.cubeList.size(); ++i) {
            this.cubeList.get(i).render(bufferbuilder, scale);
        }

        GlStateManager.endList();
        this.compiled = true;
    }

}
