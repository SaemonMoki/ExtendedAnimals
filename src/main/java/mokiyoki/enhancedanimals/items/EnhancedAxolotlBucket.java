package mokiyoki.enhancedanimals.items;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mokiyoki.enhancedanimals.entity.EnhancedAxolotl;
import mokiyoki.enhancedanimals.entity.genetics.AxolotlGeneticsInitialiser;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.model.EnhancedAxolotlBucketModel;
import mokiyoki.enhancedanimals.renderer.RenderEnhancedAxolotlBucket;
import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.client.IItemRenderProperties;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class EnhancedAxolotlBucket extends MobBucketItem {
    private static final int[] x = {
            3,4,6,9,11,12,
            1,2,4,5,10,11,13,14,
            2,3,4,5,6,7,8,9,10,11,12,13,
            1,3,4,5,6,7,8,9,10,11,12,14,
            2,3,4,5,6,7,8,9,10,11,12,13,
            1,2,4,5,6,7,8,9,10,11,13,14,
            5,6,7,8,9,10,
            5,7,8,10,
            5,10,
            4,5,6,9,10,11,
            4,5,6,9,10,11,
            5,10
    };
    private static final int[] y = {
            0,0,0,0,0,0,
            1,1,1,1,1,1,1,1,
            2,2,2,2,2,2,2,2,2,2,2,2,
            3,3,3,3,3,3,3,3,3,3,3,3,
            4,4,4,4,4,4,4,4,4,4,4,4,
            5,5,5,5,5,5,5,5,5,5,5,5,
            6,6,6,6,6,6,
            7,7,7,7,
            8,8,
            9,9,9,9,9,9,
            10,10,10,10,10,10,
            11,11
    };

    public EnhancedAxolotlBucket(Properties properties, Supplier<? extends EntityType<?>> entitySupplier, Supplier<? extends Fluid> fluidSupplier, Supplier<? extends SoundEvent> soundSupplier) {
        super(entitySupplier, fluidSupplier, soundSupplier, properties);
    }

    @Override
    public void initializeClient(@Nonnull Consumer<IItemRenderProperties> consumer) {
        consumer.accept(new IItemRenderProperties() {
            @Override
            public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
                return RenderEnhancedAxolotlBucket.AXOLOTL_BUCKET_RENDERER;
            }
        });
    }

    public static void setGenes(ItemStack stack, Genes genes) {
        if (genes != null) {
            stack.getOrCreateTagElement("Genetics").putIntArray("SGenes", genes.getSexlinkedGenes());
            stack.getOrCreateTagElement("Genetics").putIntArray("AGenes", genes.getAutosomalGenes());
        }
    }

    public Genes getGenes(ItemStack stack) {
        CompoundTag genetics = stack.getOrCreateTagElement("Genetics");
        return new Genes(genetics.getIntArray("SGenes"), genetics.getIntArray("AGenes"));
    }

    public static void setImage(ItemStack stack, int[] imageArray) {
        if (imageArray != null) {
            stack.getOrCreateTagElement("display").putIntArray("image", imageArray);
        }
    }

    public static int[] getImage(ItemStack stack) {
        CompoundTag compoundTag = stack.getOrCreateTagElement("display");
        return buildImage(compoundTag.contains("image") ? compoundTag.getIntArray("image") : getBlankImage());
    }

    private static int[] getBlankImage() {
        int a = 254;
        int b = 190;
        int g = 180;
        int r = 255;
        int[] colour = new int[y.length];
        for (int i = 0, l = colour.length; i < l; i++) {
            colour[i]= combine(a, b, g, r);
        }
        return colour;
        // red = blue
    }

    public static int combine(int a, int b, int g, int r) {
        return (a & 255) << 24 | (b & 255) << 16 | (g & 255) << 8 | (r & 255) << 0;
    }

    private static int[] buildImage(int[] colours) {
        List<Integer> image = new ArrayList();
        for (int i = 0, l=colours.length; i < l; i++) {
            if (colours[i] != -1) {
                image.add(x[i]);
                image.add(y[i]);
                image.add(colours[i]);
            }
        }
        int [] imageArray = new int[image.size()];
        for (int i = 0, l=imageArray.length; i < l; i++) imageArray[i] = image.get(i);
        return imageArray;
    }

    public static String getTexture(ItemStack stack) {
        StringBuilder texture = new StringBuilder();
        int[] invImage = getImage(stack);
        for (int i : invImage) {
            texture.append(i).append("-");
        }
        return texture.toString();
    }


    private void spawnAxolotl(ServerLevel level, ItemStack stack, BlockPos pos) {
        Entity entity = getFishType().spawn(level, stack, (Player)null, pos, MobSpawnType.BUCKET, true, false);
        if (entity instanceof EnhancedAxolotl) {
            EnhancedAxolotl axolotl = (EnhancedAxolotl)entity;
            axolotl.loadFromBucketTag(stack.getOrCreateTag());
            axolotl.setFromBucket(true);
            if (this.getGenes(stack).isValid()) {
                axolotl.setSharedGenes(this.getGenes(stack));
            }
        }
    }

    @Override
    public void checkExtraContent(@org.jetbrains.annotations.Nullable Player player, Level level, ItemStack stack, BlockPos pos) {
        if (level instanceof ServerLevel) {
            this.spawnAxolotl((ServerLevel)level, stack, pos);
            level.gameEvent(player, GameEvent.ENTITY_PLACE, pos);
        }
    }

    @Override
    public void appendHoverText(ItemStack p_151155_, @Nullable Level p_151156_, List<Component> p_151157_, TooltipFlag p_151158_) { }
}
