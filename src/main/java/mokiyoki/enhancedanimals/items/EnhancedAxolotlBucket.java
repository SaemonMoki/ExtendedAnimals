package mokiyoki.enhancedanimals.items;

import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import mokiyoki.enhancedanimals.entity.EnhancedAxolotl;
import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.core.BlockPos;
import mokiyoki.enhancedanimals.util.CustomDataHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.CustomData;
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

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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
        super(entitySupplier.get(), fluidSupplier.get(), soundSupplier.get(), properties);
    }

    public static void setGillType(ItemStack stack, GillType type) {
            CustomDataHelper.updateTagElement(stack, "display", element -> element.putString("type", type.name()));
    }

    public static void setGlowType(ItemStack stack, boolean eyes, boolean body, boolean gills) {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("eyes", eyes);
        tag.putBoolean("body", body);
        tag.putBoolean("gills", gills);
        CustomDataHelper.updateTagElement(stack, "display", element -> element.put("gfp", tag));
    }

    public static GillType getGillType(ItemStack stack) {
        if (CustomDataHelper.getTagElementOrEmpty(stack, "display").contains("type")) {
            String type = CustomDataHelper.getTagElementOrEmpty(stack, "display").getString("type");
            if (type.equals("GREATER")) {
                return GillType.GREATER;
            } else if (type.equals("LONG")) {
                return GillType.LONG;
            }
        }
        return GillType.MINOR;
    }

    public static void setEquipment(ItemStack stack, ItemStack equipment, HolderLookup.Provider provider) {
        if (equipment != ItemStack.EMPTY) {
            CustomDataHelper.updateTagElement(stack, "display", element -> element.put("collar", equipment.saveOptional(provider)));
        }
    }

    public static void setGenes(ItemStack stack, Genes genes) {
        if (genes != null) {
            CustomDataHelper.updateTagElement(stack, "Genetics", element -> element.putIntArray("SGenes", genes.getSexlinkedGenes()));
            CustomDataHelper.updateTagElement(stack, "Genetics", element -> element.putIntArray("AGenes", genes.getAutosomalGenes()));

            setGillType(stack, (genes.isHomozygousFor(34, 2) && genes.isHomozygousFor(36, 2))? GillType.GREATER : (genes.isHomozygousFor(34, 2) || genes.isHomozygousFor(36, 2)? GillType.LONG : GillType.MINOR));
            setGlowType(stack, genes.has(20, 6), genes.has(10, 3) && !genes.has(10, 2), genes.has(38, 3) && !genes.has(38, 2));
        }
    }

    public Genes getGenes(ItemStack stack) {
        CompoundTag genetics = CustomDataHelper.getTagElementOrEmpty(stack, "Genetics");
        return new Genes(genetics.getIntArray("SGenes"), genetics.getIntArray("AGenes"));
    }

    public static void setParentNames(ItemStack stack, String sireName, String damName) {
        CustomDataHelper.updateTagElement(stack, "display", element -> element.putString("SireName", sireName));
        CustomDataHelper.updateTagElement(stack, "display", element -> element.putString("DamName", damName));
    }



    public static void setMateGenes(ItemStack stack, Genes genes, boolean isFemale) {
        if (genes != null) {
            CustomDataHelper.updateTagElement(stack, "MateGenetics", element -> element.putIntArray("SGenes", genes.getSexlinkedGenes()));
            CustomDataHelper.updateTagElement(stack, "MateGenetics", element -> element.putIntArray("AGenes", genes.getAutosomalGenes()));
            CustomDataHelper.updateTagElement(stack, "MateGenetics", element -> element.putBoolean("MateIsFemale", isFemale));
        }
    }

    private Genes getMateGenes(ItemStack stack) {
        CompoundTag genetics = CustomDataHelper.getTagElementOrEmpty(stack, "MateGenetics");
        return new Genes(genetics.getIntArray("SGenes"), genetics.getIntArray("AGenes"));
    }

    public static void setAxolotlUUID(ItemStack stack, String uuid) {
        CustomDataHelper.updateTagElement(stack, "display", element -> element.putString("UUID", uuid));
    }

    //kept separately from the uuid, which can't always be restored
    public static void setVariation(ItemStack stack, int variationSeed, boolean isFemale) {
        CustomDataHelper.updateTagElement(stack, "display", element -> element.putInt("VariationSeed", variationSeed));
        CustomDataHelper.updateTagElement(stack, "Genetics", element -> element.putBoolean("IsFemale", isFemale));
    }

    private boolean getMateIsFemale(ItemStack stack) {
        return CustomDataHelper.getTagElementOrEmpty(stack, "MateGenetics").getBoolean("MateIsFemale");
    }

    public static void setBirthTime(ItemStack stack, long birthTime) {
        CustomDataHelper.updateTagElement(stack, "display", element -> element.putLong("BirthTime", birthTime));
    }

    public static void setImage(ItemStack stack, int[] imageArray) {
        if (imageArray != null) {
            CustomDataHelper.updateTagElement(stack, "display", element -> element.putIntArray("image", imageArray));
        }
    }

    public static int[] getImage(ItemStack stack) {
        CompoundTag compoundTag = CustomDataHelper.getTagElementOrEmpty(stack, "display");
        return buildImage(compoundTag.contains("image") ? compoundTag.getIntArray("image") : getBlankImage());
    }

    public static int[] getGlowingImage(ItemStack stack) {
        if (CustomDataHelper.getTagElementOrEmpty(stack, "display").contains("gfp")) {
            int[] image = getImage(stack);
            CompoundTag tag = CustomDataHelper.getTagElementOrEmpty(stack, "display").getCompound("gfp");
            boolean body = tag.getBoolean("body");
            boolean eyes = body || tag.getBoolean("eyes");
            boolean gills = body || tag.getBoolean("gills");
            boolean hasEquipment = body && CustomDataHelper.getTagElementOrEmpty(stack, "display").contains("collar");

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
        CompoundTag data = CustomDataHelper.getTagElementOrEmpty(stack, "display");
        if (level.getEntity(UUID.fromString(data.getString("UUID"))) == null) {
            axolotl.setUUID(UUID.fromString(data.getString("UUID")));
        }
        if (data.contains("VariationSeed")) {
            axolotl.setVariationSeed(data.getInt("VariationSeed"));
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
            if (genes.isValid() && genes.getSexlinkedGenes().length > 0 && genes.getAutosomalGenes().length > 0) {
                axolotl.setMateGender(this.getMateIsFemale(stack));
                axolotl.setMateGenes(this.getMateGenes(stack));
                axolotl.setHasEgg(true);
            }
        }
        if (data.contains("collar")) {
            CompoundTag collar = data.getCompound("collar");
            axolotl.getEnhancedInventory().setItem(1, ItemStack.parseOptional(level.registryAccess(), collar));
        }
        if (stack.has(DataComponents.CUSTOM_NAME)) {
            axolotl.setCustomName(stack.getHoverName());
        }
        //buckets filled before birth times became longs hold a string
        if (data.contains("BirthTime", 4)) {
            axolotl.setBirthTime(data.getLong("BirthTime"));
        } else {
            axolotl.setBirthTime(EnhancedAnimalAbstract.parseBirthTime(data.getString("BirthTime")));
        }
        axolotl.initilizeAnimalSize();
        CompoundTag bucketTag = stack.getOrDefault(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY).copyTag();
        bucketTag.merge(CustomDataHelper.getTagOrEmpty(stack));
        axolotl.loadFromBucketTag(bucketTag);
        axolotl.moveTo((double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, 0.0F, 0.0F);
        level.addFreshEntity(axolotl);
    }

    @Override
    public void appendHoverText(ItemStack p_151155_, Item.TooltipContext p_151156_, List<Component> p_151157_, TooltipFlag p_151158_) { }

    public enum GillType {
        GREATER,
        LONG,
        MINOR
    }
}
