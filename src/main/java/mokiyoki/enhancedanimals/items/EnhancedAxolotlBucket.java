package mokiyoki.enhancedanimals.items;

import mokiyoki.enhancedanimals.entity.EnhancedAxolotl;
import mokiyoki.enhancedanimals.renderer.RenderEnhancedAxolotlBucket;
import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
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

    public static void setGillType(ItemStack stack, GillType type) {
            stack.getOrCreateTagElement("display").putString("type", type.name());
    }

    public static void setGlowType(ItemStack stack, boolean eyes, boolean body, boolean gills) {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("eyes", eyes);
        tag.putBoolean("body", body);
        tag.putBoolean("gills", gills);
        stack.getOrCreateTagElement("display").put("gfp", tag);
    }

    public static GillType getGillType(ItemStack stack) {
        if (stack.getOrCreateTagElement("display").contains("type")) {
            String type = stack.getOrCreateTagElement("display").getString("type");
            if (type.equals("GREATER")) {
                return GillType.GREATER;
            } else if (type.equals("LONG")) {
                return GillType.LONG;
            }
        }
        return GillType.MINOR;
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

            setGillType(stack, (genes.isHomozygousFor(34, 2) && genes.isHomozygousFor(36, 2))? GillType.GREATER : (genes.isHomozygousFor(34, 2) || genes.isHomozygousFor(36, 2)? GillType.LONG : GillType.MINOR));
            setGlowType(stack, genes.has(20, 6), genes.has(10, 3) && !genes.has(10, 2), genes.has(38, 3) && !genes.has(38, 2));
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



    public static void setMateGenes(ItemStack stack, Genes genes, boolean isFemale) {
        if (genes != null) {
            stack.getOrCreateTagElement("MateGenetics").putIntArray("SGenes", genes.getSexlinkedGenes());
            stack.getOrCreateTagElement("MateGenetics").putIntArray("AGenes", genes.getAutosomalGenes());
            stack.getOrCreateTagElement("MateGenetics").putBoolean("MateIsFemale", isFemale);
        }
    }

    private Genes getMateGenes(ItemStack stack) {
        CompoundTag genetics = stack.getOrCreateTagElement("MateGenetics");
        return new Genes(genetics.getIntArray("SGenes"), genetics.getIntArray("AGenes"));
    }

    public static void setAxolotlUUID(ItemStack stack, String uuid) {
        stack.getOrCreateTagElement("display").putString("UUID", uuid);
    }

    private boolean getMateIsFemale(ItemStack stack) {
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

    public static int[] getGlowingImage(ItemStack stack) {
        if (stack.getOrCreateTagElement("display").contains("gfp")) {
            int[] image = getImage(stack);
            CompoundTag tag = stack.getOrCreateTagElement("display").getCompound("gfp");
            boolean body = tag.getBoolean("body");
            boolean eyes = body || tag.getBoolean("eyes");
            boolean gills = body || tag.getBoolean("gills");
            boolean hasEquipment = body && stack.getOrCreateTagElement("display").contains("collar");

            List<Integer> glowImage = new ArrayList();

            for (int i = 0, l = image.length; i < l; i = i + 3) {
                int x = image[i];
                int y = image[i + 1];
                if (body) {
                    if (hasEquipment) {
                        if (y==7) {
                            if (!(x==7||x==8)) {
                                glowImage.add(x);
                                glowImage.add(y);
                                glowImage.add(image[i + 2]);
                            }
                        } else if (y!=6){
                            glowImage.add(x);
                            glowImage.add(y);
                            glowImage.add(image[i + 2]);
                        }
                    } else {
                        return image;
                    }
                } else if (((x == 4 || x == 11) && y == 3) && eyes) {
                    glowImage.add(x);
                    glowImage.add(y);
                    glowImage.add(image[i + 2]);
                } else if (y <= 5 && gills) {
                    if (y <= 1) {
                        glowImage.add(x);
                        glowImage.add(y);
                        glowImage.add(image[i + 2]);
                    } else if (x < 4 || x > 11) {
                        glowImage.add(x);
                        glowImage.add(y);
                        glowImage.add(image[i + 2]);
                    }
                }
            }
            int [] imageArray = new int[glowImage.size()];
            for (int i = 0, l=imageArray.length; i < l; i++) imageArray[i] = glowImage.get(i);
            return imageArray;
        }
            return new int[3];
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
            if (colours[i] != -2) {
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
        axolotl.setFromBucket(true);
        CompoundTag data = stack.getOrCreateTagElement("display");
        if (level.getEntity(UUID.fromString(data.getString("UUID"))) == null) {
            axolotl.setUUID(UUID.fromString(data.getString("UUID")));
        }
        axolotl.setSireName(data.getString("SireName"));
        axolotl.setDamName(data.getString("DamName"));
        if (this.getGenes(stack) != null) {
            Genes genes = this.getGenes(stack);
            if (!genes.isValid() && genes.getNumberOfAutosomalGenes() != 0) {
                genes.fixGenes(1);
            }
            axolotl.setGenes(genes);
            axolotl.setSharedGenes(genes);

            genes = this.getMateGenes(stack);
            if (genes.isValid()) {
                axolotl.setMateGender(this.getMateIsFemale(stack));
                axolotl.setMateGenes(this.getMateGenes(stack));
                axolotl.setHasEgg(true);
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
        axolotl.initilizeAnimalSize();
        axolotl.loadFromBucketTag(stack.getOrCreateTag());
        axolotl.moveTo((double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, 0.0F, 0.0F);
        level.addFreshEntity(axolotl);
    }

    @Override
    public void appendHoverText(ItemStack p_151155_, @Nullable Level p_151156_, List<Component> p_151157_, TooltipFlag p_151158_) { }

    public enum GillType {
        GREATER,
        LONG,
        MINOR
    }
}
