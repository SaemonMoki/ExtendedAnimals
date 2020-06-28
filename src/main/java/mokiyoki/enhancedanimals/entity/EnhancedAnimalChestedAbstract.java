package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.entity.util.Colouration;
import mokiyoki.enhancedanimals.entity.util.Equipment;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.items.CustomizableAnimalEquipment;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class EnhancedAnimalChestedAbstract extends EnhancedAnimalAbstract {

    private static final DataParameter<Boolean> HAS_CHEST = EntityDataManager.createKey(EnhancedAnimalChestedAbstract.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> HAS_BLANKET = EntityDataManager.createKey(EnhancedAnimalChestedAbstract.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> HAS_BRIDLE = EntityDataManager.createKey(EnhancedAnimalChestedAbstract.class, DataSerializers.BOOLEAN);

    private static final String CHEST_TEXTURE = "chest.png";

    private static final String[] BLANKET_TEXTURE = new String[] {
            "blanket_trader.png", "blanket_black.png", "blanket_blue.png", "blanket_brown.png", "blanket_cyan.png", "blanket_grey.png", "blanket_green.png", "blanket_lightblue.png", "blanket_lightgrey.png", "blanket_lime.png", "blanket_magenta.png", "blanket_orange.png", "blanket_pink.png", "blanket_purple.png", "blanket_red.png", "blanket_white.png", "blanket_yellow.png"
    };

    private static final String BRIDLE_TEXTURE = "bridle.png";

    private static final String[] BRIDLE_HARDWEAR_TEXTURE = new String[] {
            "bridle_iron.png", "bridle_gold.png", "bridle_diamond.png"
    };

    private static final String HARNESS_TEXTURE = "harness.png";

    private static final String[] HARNESS_HARDWEAR_TEXTURE = new String[] {
            "harness_iron.png", "harness_gold.png", "harness_diamond.png"
    };

    protected EnhancedAnimalChestedAbstract(EntityType<? extends EnhancedAnimalAbstract> type, World worldIn, int genesSize, Ingredient temptationItems, Ingredient breedItems, Map<Item, Integer> foodWeightMap, boolean bottleFeedable) {
        super(type, worldIn, genesSize, temptationItems, breedItems, foodWeightMap, bottleFeedable);
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(HAS_CHEST, false);
        this.dataManager.register(HAS_BLANKET, false);
        this.dataManager.register(HAS_BRIDLE, false);
    }

    @Override
    public boolean canHaveChest() {
        return true;
    }

    public boolean hasChest() {
        return this.dataManager.get(HAS_CHEST);
    }

    public void setChested(boolean chested) {
        this.dataManager.set(HAS_CHEST,chested);
        if (chested && !this.enhancedAnimalTextures.contains(CHEST_TEXTURE)) {
            this.enhancedAnimalTextures.add(CHEST_TEXTURE);
        } else if (!chested && this.enhancedAnimalTextures.contains(CHEST_TEXTURE)) {
            this.enhancedAnimalTextures.remove(CHEST_TEXTURE);
        }
    }

    @Override
    public boolean canHaveBlanket() {
        return true;
    }

    public boolean hasBlanket() {
        return this.dataManager.get(HAS_BLANKET);
    }

    public void setBlanket(boolean blanketed) {
        this.dataManager.set(HAS_BLANKET, blanketed);
        if (blanketed && !this.enhancedAnimalTextures.contains(BLANKET_TEXTURE)) {
            this.enhancedAnimalTextures.add(getBlanketString());
        } else if (!blanketed && this.enhancedAnimalTextures.contains(BLANKET_TEXTURE)) {
            this.enhancedAnimalTextures.remove(BLANKET_TEXTURE);
        }
    }

    @Override
    public boolean canHaveBridle() {
        return true;
    }

    public boolean hasBridle() {
        return this.dataManager.get(HAS_BRIDLE);
    }

    public void setBridle(boolean bridled) {
        this.dataManager.set(HAS_BRIDLE, bridled);
        List<String> previousBridleTextures = equipmentTextures.get(Equipment.BRIDLE);
        List<String> newBridleTextures = getBridleTextures();

        if(bridled) {
            if(previousBridleTextures == null || !previousBridleTextures.containsAll(newBridleTextures)){
                equipmentTextures.put(Equipment.BRIDLE, newBridleTextures);
            }
        } else {
            if(previousBridleTextures != null){
                equipmentTextures.remove(Equipment.BRIDLE);
            }
        }
    }

    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        if (!itemstack.isEmpty()) {
            if (!this.hasChest() && itemstack.getItem() == Blocks.CHEST.asItem()) {
                this.setChested(true);
                this.playChestEquipSound();
                this.animalInventory.setInventorySlotContents(0, itemstack);
                this.initInventory();
                return true;
            }
        }
        return super.processInteract(player, hand);
    }

    protected void playChestEquipSound() {
        this.playSound(SoundEvents.ENTITY_DONKEY_CHEST, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
    }

    @Override
    public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn) {
        if (inventorySlot == 499) {
            if (this.hasChest() && itemStackIn.isEmpty()) {
                this.setChested(false);
                this.initInventory();
                return true;
            }

            if (!this.hasChest() && itemStackIn.getItem() == Blocks.CHEST.asItem()) {
                this.setChested(true);
                this.initInventory();
                return true;
            }
        }

        return super.replaceItemInInventory(inventorySlot, itemStackIn);
    }

    @Override
    protected int getInventorySize() {
        return this.hasChest() ? 17 : super.getInventorySize();
    }

    @Override
    protected void updateInventorySlots() {
        super.updateInventorySlots();
    }

    private String getBlanketString() {
        if (this.getEnhancedInventory() != null) {
            String blanket = "blanket_";
            ItemStack blanketSlot = this.animalInventory.getStackInSlot(4);
            if (blanketSlot != ItemStack.EMPTY) {
                Item blanketColour = blanketSlot.getItem();
                if (blanketColour == Items.BLACK_CARPET) {
                    blanket = blanket + "black";
                } else if (blanketColour == Items.BLUE_CARPET) {
                    blanket = blanket + "blue";
                } else if (blanketColour == Items.BROWN_CARPET) {
                    blanket = blanket + "brown";
                } else if (blanketColour == Items.CYAN_CARPET) {
                    blanket = blanket + "cyan";
                } else if (blanketColour == Items.GRAY_CARPET) {
                    blanket = blanket + "grey";
                } else if (blanketColour == Items.GREEN_CARPET) {
                    blanket = blanket + "green";
                } else if (blanketColour == Items.LIGHT_BLUE_CARPET) {
                    blanket = blanket + "lightblue";
                } else if (blanketColour == Items.LIGHT_GRAY_CARPET) {
                    blanket = blanket + "lightgrey";
                } else if (blanketColour == Items.LIME_CARPET) {
                    blanket = blanket + "lime";
                } else if (blanketColour == Items.MAGENTA_CARPET) {
                    blanket = blanket + "magenta";
                } else if (blanketColour == Items.ORANGE_CARPET) {
                    blanket = blanket + "orange";
                } else if (blanketColour == Items.PINK_CARPET) {
                    blanket = blanket + "pink";
                } else if (blanketColour == Items.PURPLE_CARPET) {
                    blanket = blanket + "purple";
                } else if (blanketColour == Items.RED_CARPET) {
                    blanket = blanket + "red";
                } else if (blanketColour == Items.WHITE_CARPET) {
                    blanket = blanket + "white";
                } else if (blanketColour == Items.YELLOW_CARPET) {
                    blanket = blanket + "yellow";
                }

                blanket = blanket + ".png";
                return blanket;
            }
        }
        return "";
    }

    private List<String> getBridleTextures() {
        List<String> bridleTextures = new ArrayList<>();

        if (this.getEnhancedInventory() != null) {
            ItemStack blanketSlot = this.animalInventory.getStackInSlot(3);
            if (blanketSlot != ItemStack.EMPTY) {
                Item blanketColour = blanketSlot.getItem();
                if (blanketColour == ModItems.BRIDLE_BASIC_CLOTH) {
                    bridleTextures.add(BRIDLE_TEXTURE);
                    bridleTextures.add(BRIDLE_HARDWEAR_TEXTURE[0]);
                }
            }
        }

        return bridleTextures;
    }

    @OnlyIn(Dist.CLIENT)
    public Colouration getRgb() {
        ItemStack bridle = this.getEnhancedInventory().getStackInSlot(3);
//        ItemStack harness = this.getEnhancedInventory().getStackInSlot(6);

        if (bridle != ItemStack.EMPTY) {
            this.colouration.setBridleColour(Colouration.getEquipmentColor(bridle));
        }

//        if (harness != ItemStack.EMPTY) {
//            this.colouration.setHarnessColour(Colouration.getEquipmentColor(harness));
//        }
        return this.colouration;
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("Chested", this.hasChest());
        compound.putBoolean("Bridled", this.hasBridle());
    }


    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setChested(compound.getBoolean("Chested"));
        this.setBridle(compound.getBoolean("Bridled"));
    }
}
