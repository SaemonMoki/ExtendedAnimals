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
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_AXOLOTL;

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
            8,8,8,8,8,8,
            9,9,9,9,9,9,
            10,10
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

    public static void setEquipment(ItemStack stack, ItemStack equipment) {
        if (equipment != ItemStack.EMPTY) {
            stack.getOrCreateTagElement("display").put("collar", equipment.save(new CompoundTag()));
        }
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

    public static void setParentNames(ItemStack stack, String sireName, String damName) {
        stack.getOrCreateTagElement("display").putString("SireName", sireName);
        stack.getOrCreateTagElement("display").putString("DamName", damName);
    }

    public static void setMateGenes(ItemStack stack, Genes genes) {
        if (genes != null) {
            stack.getOrCreateTagElement("MateGenetics").putIntArray("SGenes", genes.getSexlinkedGenes());
            stack.getOrCreateTagElement("MateGenetics").putIntArray("AGenes", genes.getAutosomalGenes());
        }
    }

    private Genes getMateGenes(ItemStack stack) {
        CompoundTag genetics = stack.getOrCreateTagElement("MateGenetics");
        return new Genes(genetics.getIntArray("SGenes"), genetics.getIntArray("AGenes"));
    }

    public static void mateIsFemale(ItemStack stack, boolean isFemale) {
        stack.getOrCreateTagElement("MateGenetics").putBoolean("MateIsFemale", isFemale);
    }

    public static void setAxolotlUUID(ItemStack stack, UUID uuid) {
        stack.getOrCreateTagElement("display").putUUID("originalUUID", uuid);
    }

    private boolean mateIsFemale(ItemStack stack) {
        return stack.getOrCreateTagElement("MateGenetics").getBoolean("MateIsFemale");
    }

    public static void setBirthTime(ItemStack stack, String birthTime) {
        stack.getOrCreateTagElement("display").putString("BirthTime", birthTime);
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

    @Override
    public void checkExtraContent(@Nullable Player player, Level level, ItemStack stack, BlockPos pos) {
        if (level instanceof ServerLevel) {
            this.spawnAxolotl((ServerLevel)level, stack, pos);
            level.gameEvent(player, GameEvent.ENTITY_PLACE, pos);
        }
    }

    private void spawnAxolotl(ServerLevel level, ItemStack stack, BlockPos pos) {
        EnhancedAxolotl axolotl = ENHANCED_AXOLOTL.get().create(level);
        axolotl.loadFromBucketTag(stack.getOrCreateTag());
        axolotl.setFromBucket(true);
        CompoundTag data = stack.getOrCreateTagElement("display");
//        axolotl.setUUID(data.getUUID("originalUUID"));
        axolotl.setSireName(data.getString("SireName"));
        axolotl.setDamName(data.getString("DamName"));
        if (this.getGenes(stack).isValid()) {
            axolotl.setGenes(this.getGenes(stack));
            axolotl.setSharedGenes(this.getGenes(stack));
            if (this.getMateGenes(stack).isValid()) {
                axolotl.setMateGenes(this.getMateGenes(stack));
                axolotl.setMateGender(this.mateIsFemale(stack));
            }
        }
        if (data.contains("collar")) {
            CompoundTag collar = data.getCompound("collar");
            axolotl.getEnhancedInventory().setItem(1, ItemStack.of(collar));
        }
        if (stack.hasCustomHoverName()) {
            axolotl.setCustomName(stack.getHoverName());
        }
        axolotl.setBirthTime(data.getString("BirthTime"));
        axolotl.moveTo((double) pos.getX() + 0.3D + 0.2D, (double) pos.getY(), (double) pos.getZ() + 0.3D, 0.0F, 0.0F);
        level.addFreshEntity(axolotl);
    }

    @Override
    public void appendHoverText(ItemStack p_151155_, @Nullable Level p_151156_, List<Component> p_151157_, TooltipFlag p_151158_) { }
}
