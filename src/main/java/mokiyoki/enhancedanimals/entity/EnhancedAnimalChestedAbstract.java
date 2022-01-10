//package mokiyoki.enhancedanimals.entity;
//
//import mokiyoki.enhancedanimals.entity.util.Equipment;
//import mokiyoki.enhancedanimals.init.ModItems;
//import mokiyoki.enhancedanimals.items.CustomizableBridle;
//import mokiyoki.enhancedanimals.items.CustomizableCollar;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.Container;
//import net.minecraft.world.item.DyeColor;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.Items;
//import net.minecraft.world.item.crafting.Ingredient;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.network.syncher.EntityDataAccessor;
//import net.minecraft.network.syncher.EntityDataSerializers;
//import net.minecraft.network.syncher.SynchedEntityData;
//import net.minecraft.world.InteractionResult;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.sounds.SoundEvents;
//import net.minecraft.network.chat.TextComponent;
//import net.minecraft.world.level.Level;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//public abstract class EnhancedAnimalChestedAbstract extends EnhancedAnimalAbstract {
//
//    private static final EntityDataAccessor<Boolean> HAS_CHEST = SynchedEntityData.defineId(EnhancedAnimalChestedAbstract.class, EntityDataSerializers.BOOLEAN);
//    private static final EntityDataAccessor<Boolean> HAS_BLANKET = SynchedEntityData.defineId(EnhancedAnimalChestedAbstract.class, EntityDataSerializers.BOOLEAN);
//    private static final EntityDataAccessor<Boolean> HAS_BRIDLE = SynchedEntityData.defineId(EnhancedAnimalChestedAbstract.class, EntityDataSerializers.BOOLEAN);
//
//    private static final String CHEST_TEXTURE = "chest.png";
//
//    private static final String[] BLANKET_TEXTURE = new String[] {
//            "blanket_trader.png", "blanket_black.png", "blanket_blue.png", "blanket_brown.png", "blanket_cyan.png", "blanket_grey.png", "blanket_green.png", "blanket_lightblue.png", "blanket_lightgrey.png", "blanket_lime.png", "blanket_magenta.png", "blanket_orange.png", "blanket_pink.png", "blanket_purple.png", "blanket_red.png", "blanket_white.png", "blanket_yellow.png"
//    };
//
//    private static final String BRIDLE_TEXTURE = "d_bridle.png";
//
//    private static final String BRIDLE_LEATHER_TEXTURE = "bridle_leather.png";
//
//    private static final String[] BRIDLE_HARDWEAR_TEXTURE = new String[] {
//            "bridle_iron.png", "bridle_gold.png", "bridle_diamond.png"
//    };
//
//    private static final String HARNESS_TEXTURE = "harness.png";
//
//    private static final String[] HARNESS_HARDWEAR_TEXTURE = new String[] {
//            "harness_iron.png", "harness_gold.png", "harness_diamond.png"
//    };
//
//    protected EnhancedAnimalChestedAbstract(EntityType<? extends EnhancedAnimalAbstract> type, Level worldIn,int SgenesSize, int AgenesSize, boolean bottleFeedable) {
//        super(type, worldIn, SgenesSize, AgenesSize, bottleFeedable);
//    }
//
//    protected void defineSynchedData() {
//        super.defineSynchedData();
//        this.entityData.define(HAS_CHEST, false);
//        this.entityData.define(HAS_BLANKET, false);
//        this.entityData.define(HAS_BRIDLE, false);
//    }
//
//    @Override
//    public Boolean isAnimalSleeping() {
//        if (this.hasChest()) {
//            return false;
//        }
//        return super.isAnimalSleeping();
//    }
//
//    @Override
//    public boolean canHaveChest() {
//        return this.getAge() >= this.getAdultAge();
//    }
//
//    public boolean hasChest() {
//        if (this.animalInventory!=null) {
//            return this.animalInventory.getItem(0).getItem() == Items.CHEST;
//        }
//        return false;
//    }
//
////    public void setChested(boolean chested) {
////        this.dataManager.set(HAS_CHEST,chested);
////        if (chested && !this.enhancedAnimalTextures.contains(CHEST_TEXTURE)) {
////            this.enhancedAnimalTextures.add(CHEST_TEXTURE);
////        } else if (!chested && this.enhancedAnimalTextures.contains(CHEST_TEXTURE)) {
////            this.enhancedAnimalTextures.remove(CHEST_TEXTURE);
////        }
////    }
//
//    @Override
//    public boolean canHaveBlanket() {
//        return true;
//    }
//
//    public boolean hasBlanket() {
//        return this.entityData.get(HAS_BLANKET);
//    }
//
//    public void setBlanket(boolean blanketed) {
//        this.entityData.set(HAS_BLANKET, blanketed);
//        List<String> previousBlanketTextures = equipmentTextures.get(Equipment.BLANKET);
//        List<String> newBlanketTextures = getBlanket();
//
//        if(blanketed) {
////            if(previousBlanketTextures == null || !previousBlanketTextures.containsAll(newBlanketTextures)){
//                this.equipmentTextures.put(Equipment.BLANKET, newBlanketTextures);
////            }
//        } else {
//            if(previousBlanketTextures != null){
//                this.equipmentTextures.remove(Equipment.BLANKET);
//            }
//        }
//        this.compiledEquipmentTexture = null; //reset compiled string
//    }
//
//    public void setChest(boolean chested) {
//        List<String> previousChestTexture = equipmentTextures.get(Equipment.CHEST);
//        List<String> newChestTexture = getChestTexture();
//
//        if(chested) {
////            if(previousChestTexture == null || !previousChestTexture.containsAll(newChestTexture)){
//                this.equipmentTextures.put(Equipment.CHEST, newChestTexture);
////            }
//        } else {
//            if(previousChestTexture != null){
//                this.equipmentTextures.remove(Equipment.CHEST);
//            }
//        }
//        this.compiledEquipmentTexture = null; //reset compiled string
//    }
//
//    @Override
//    public boolean canHaveBridle() {
//        return true;
//    }
//
//    public boolean hasBridle() {
//        return this.entityData.get(HAS_BRIDLE);
//    }
//
//    public void setBridle(boolean bridled) {
//        this.entityData.set(HAS_BRIDLE, bridled);
//        List<String> previousBridleTextures = equipmentTextures.get(Equipment.BRIDLE);
//        List<String> newBridleTextures = getBridleTextures();
//
//        if(bridled) {
////            if(previousBridleTextures == null || !previousBridleTextures.containsAll(newBridleTextures)){
//                equipmentTextures.put(Equipment.BRIDLE, newBridleTextures);
////            }
//        } else {
//            if(previousBridleTextures != null){
//                equipmentTextures.remove(Equipment.BRIDLE);
//            }
//        }
//        this.compiledEquipmentTexture = null; //reset compiled string
//    }
//
//    @Override
//    public InteractionResult mobInteract(Player player, InteractionHand hand) {
//        ItemStack itemstack = player.getItemInHand(hand);
//        if (!itemstack.isEmpty()) {
//            Item item = itemstack.getItem();
//            if (this.canHaveChest() && !this.hasChest() && item == Items.CHEST) {
//                this.setChest(true);
//                this.playChestEquipSound();
//                this.animalInventory.setItem(0, new ItemStack(itemstack.getItem(), 1));
//                this.initInventory();
//                itemstack.shrink(1);
//                return InteractionResult.SUCCESS;
//            }
//            if (this.canHaveBlanket() && isCarpet(itemstack)) {
//                return InteractionResult.sidedSuccess(this.blanketAnimal(itemstack, player, hand, this));
//            }
//            if (this.canHaveBridle() && item instanceof CustomizableBridle) {
//                return InteractionResult.sidedSuccess(this.bridleAnimal(itemstack, player, hand, this));
//            }
//        }
//        return super.mobInteract(player, hand);
//    }
//
//    public boolean canBeControlledByRider() {
//        return this.getControllingPassenger() instanceof LivingEntity/* && this.isTame()*/ && this.entityData.get(HAS_BRIDLE);
//    }
//
//    public void equipAnimal(boolean hasChest, DyeColor blanketColour) {
//        if (hasChest) {
//            this.setChest(true);
//            this.animalInventory.setItem(0, new ItemStack(Items.CHEST, 1));
//            this.initInventory();
//        }
//        if (blanketColour != null) {
//            ItemStack equipedBlanket = new ItemStack(this.getCarpetByColour(blanketColour), 1);
//            this.animalInventory.setItem(4, equipedBlanket);
//        }
//    }
//
//    public void equipTraderAnimal(boolean hasChest) {
//        if (hasChest) {
//            this.setChest(true);
//            this.animalInventory.setItem(0, new ItemStack(Items.CHEST, 1));
//            this.initInventory();
//        }
//        ItemStack traderBlanket = new ItemStack(Items.BLUE_CARPET).setHoverName(new TextComponent("Trader's Blanket"));
//        traderBlanket.getOrCreateTagElement("tradersblanket");
//        this.animalInventory.setItem(4, traderBlanket);
//    }
//
//    public boolean blanketAnimal(ItemStack blanketItemStack, Player player, InteractionHand hand, LivingEntity target) {
//        EnhancedAnimalChestedAbstract enhancedAnimal = (EnhancedAnimalChestedAbstract) target;
//        if (enhancedAnimal.isAlive()) {
//            ItemStack otherCarpet = this.getEnhancedInventory().getItem(4);
//            ItemStack equipedBlanket = new ItemStack(blanketItemStack.getItem(), 1).setHoverName(blanketItemStack.getHoverName());
//            if (blanketItemStack.getTagElement("tradersblanket") != null) {
//                equipedBlanket.getOrCreateTagElement("tradersblanket");
//            }
//            this.animalInventory.setItem(4, equipedBlanket);
//            this.playSound(SoundEvents.LLAMA_SWAG, 0.5F, 1.0F);
//            blanketItemStack.shrink(1);
//            if (!otherCarpet.isEmpty()) {
//                player.setItemInHand(hand, otherCarpet);
//            }
//        }
//
//        return true;
//    }
//
//    public boolean bridleAnimal(ItemStack bridleItemStack, Player player, InteractionHand hand, LivingEntity target) {
//        EnhancedAnimalChestedAbstract enhancedAnimal = (EnhancedAnimalChestedAbstract) target;
//        if (enhancedAnimal.isAlive()) {
//            ItemStack otherBridle = this.getEnhancedInventory().getItem(3);
//            this.animalInventory.setItem(3, getReplacementItemWithColour(bridleItemStack));
////            this.animalInventory.setInventorySlotContents(3, bridleItemStack);
//            this.playSound(SoundEvents.ARMOR_EQUIP_LEATHER, 0.5F, 1.0F);
//            bridleItemStack.shrink(1);
//            if (!otherBridle.isEmpty()) {
//                player.setItemInHand(hand, otherBridle);
//            }
//        }
//        return true;
//    }
//
//    public void containerChanged(Container invBasic) {
//        boolean flag = this.entityData.get(HAS_BLANKET);
//        this.updateInventorySlots();
//        if (this.tickCount > 20 && !flag && this.entityData.get(HAS_BLANKET)) {
//            this.playSound(SoundEvents.LLAMA_SWAG, 0.5F, 1.0F);
//        }
//    }
//
//    protected void playChestEquipSound() {
//        this.playSound(SoundEvents.DONKEY_CHEST, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
//
//    }
//
//    @Override
//    public boolean setSlot(int inventorySlot, ItemStack itemStackIn) {
//        if (inventorySlot == 499) {
//            if (this.hasChest() && itemStackIn.isEmpty()) {
////                this.setChested(false);
//                this.initInventory();
//                return true;
//            }
//
//            if (!this.hasChest() && itemStackIn.getItem() == Blocks.CHEST.asItem()) {
////                this.setChested(true);
//                this.initInventory();
//                return true;
//            }
//        }
//
//        int i = inventorySlot - 400;
//        if (i >= 0 && i < 2 && i < this.animalInventory.getContainerSize()) {
//            if (i != 1 && (isCarpet(itemStackIn))) {
//                this.animalInventory.setItem(i, itemStackIn);
//                this.updateInventorySlots();
//                return true;
//            } else {
//                return false;
//            }
//        }
//
//        return super.setSlot(inventorySlot, itemStackIn);
//    }
//
//    @Override
//    public int getInventorySize() {
//        return this.hasChest() ? 22 : super.getInventorySize();
//    }
//
//    @Override
//    protected void updateInventorySlots() {
//        this.setBridle(this.animalInventory.getItem(3).getItem() instanceof CustomizableBridle);
//        this.setBlanket(!this.animalInventory.getItem(4).isEmpty() && !(this.animalInventory.getItem(4).getItem() instanceof CustomizableCollar));
//        super.updateInventorySlots();
//    }
//
//    public void addAdditionalSaveData(CompoundTag compound) {
//        super.addAdditionalSaveData(compound);
////        compound.putBoolean("Chested", this.hasChest());
//        compound.putBoolean("Bridled", this.hasBridle());
//    }
//
//
//    public void readAdditionalSaveData(CompoundTag compound) {
//        super.readAdditionalSaveData(compound);
////        this.setChested(compound.getBoolean("Chested"));
//        this.setBridle(compound.getBoolean("Bridled"));
//    }
//
//    private List<String> getBlanket() {
//        List<String> blanketTextures = new ArrayList<>();
//
//        if (this.getEnhancedInventory() != null) {
//            ItemStack blanketSlot = this.animalInventory.getItem(4);
//            if (blanketSlot != ItemStack.EMPTY) {
//                Item blanket = blanketSlot.getItem();
//                if (blanket == Items.BLACK_CARPET) {
//                    blanketTextures.add(BLANKET_TEXTURE[1]);
//                } else if (blanket == Items.BLUE_CARPET) {
//                    if (blanketSlot.hasCustomHoverName()) {
//                        if (blanketSlot.getTagElement("tradersblanket") != null) {
//                            blanketTextures.add(BLANKET_TEXTURE[0]);
//                            return blanketTextures;
//                        }
//                    }
//                        blanketTextures.add(BLANKET_TEXTURE[2]);
//                } else if (blanket == Items.BROWN_CARPET) {
//                    blanketTextures.add(BLANKET_TEXTURE[3]);
//                } else if (blanket == Items.CYAN_CARPET) {
//                    blanketTextures.add(BLANKET_TEXTURE[4]);
//                } else if (blanket == Items.GRAY_CARPET) {
//                    blanketTextures.add(BLANKET_TEXTURE[5]);
//                } else if (blanket == Items.GREEN_CARPET) {
//                    blanketTextures.add(BLANKET_TEXTURE[6]);
//                } else if (blanket == Items.LIGHT_BLUE_CARPET) {
//                    blanketTextures.add(BLANKET_TEXTURE[7]);
//                } else if (blanket == Items.LIGHT_GRAY_CARPET) {
//                    blanketTextures.add(BLANKET_TEXTURE[8]);
//                } else if (blanket == Items.LIME_CARPET) {
//                    blanketTextures.add(BLANKET_TEXTURE[9]);
//                } else if (blanket == Items.MAGENTA_CARPET) {
//                    blanketTextures.add(BLANKET_TEXTURE[10]);
//                } else if (blanket == Items.ORANGE_CARPET) {
//                    blanketTextures.add(BLANKET_TEXTURE[11]);
//                } else if (blanket == Items.PINK_CARPET) {
//                    blanketTextures.add(BLANKET_TEXTURE[12]);
//                } else if (blanket == Items.PURPLE_CARPET) {
//                    blanketTextures.add(BLANKET_TEXTURE[13]);
//                } else if (blanket == Items.RED_CARPET) {
//                    blanketTextures.add(BLANKET_TEXTURE[14]);
//                } else if (blanket == Items.WHITE_CARPET) {
//                    blanketTextures.add(BLANKET_TEXTURE[15]);
//                } else if (blanket == Items.YELLOW_CARPET) {
//                    blanketTextures.add(BLANKET_TEXTURE[16]);
//                }
//
//            }
//        }
//        return blanketTextures;
//    }
//
//    protected Item getCarpetByColour(DyeColor color) {
//        switch (color) {
//            case WHITE:
//                return Items.WHITE_CARPET;
//            case RED:
//                return Items.RED_CARPET;
//            case BROWN:
//                return Items.BROWN_CARPET;
//            case BLACK:
//                return Items.BLACK_CARPET;
//            case LIGHT_GRAY:
//                return Items.LIGHT_GRAY_WOOL;
//            case GRAY:
//                return Items.GRAY_CARPET;
//
//            case BLUE:
//                return Items.BLUE_CARPET;
//            case CYAN:
//                return Items.CYAN_CARPET;
//            case LIME:
//                return Items.LIME_CARPET;
//            case PINK:
//                return Items.PINK_CARPET;
//            case GREEN:
//                return Items.GREEN_CARPET;
//            case ORANGE:
//                return Items.ORANGE_CARPET;
//            case PURPLE:
//                return Items.PURPLE_CARPET;
//            case YELLOW:
//                return Items.YELLOW_CARPET;
//            case MAGENTA:
//                return Items.MAGENTA_CARPET;
//            case LIGHT_BLUE:
//            default:
//                return Items.LIGHT_BLUE_CARPET;
//        }
//    }
//
//    protected boolean isCarpet(ItemStack itemStack) {
//        Item item = itemStack.getItem();
//        if (item == Items.BLACK_CARPET) {
//            return true;
//        }
//        if (item == Items.BLUE_CARPET) {
//            return true;
//        }
//        if (item == Items.BROWN_CARPET) {
//            return true;
//        }
//        if (item == Items.CYAN_CARPET) {
//            return true;
//        }
//        if (item == Items.GRAY_CARPET) {
//            return true;
//        }
//        if (item == Items.GREEN_CARPET) {
//            return true;
//        }
//        if (item == Items.LIGHT_BLUE_CARPET) {
//            return true;
//        }
//        if (item == Items.LIGHT_GRAY_CARPET) {
//            return true;
//        }
//        if (item == Items.LIME_CARPET) {
//            return true;
//        }
//        if (item == Items.MAGENTA_CARPET) {
//            return true;
//        }
//        if (item == Items.ORANGE_CARPET) {
//            return true;
//        }
//        if (item == Items.PINK_CARPET) {
//            return true;
//        }
//        if (item == Items.PURPLE_CARPET) {
//            return true;
//        }
//        if (item == Items.RED_CARPET) {
//            return true;
//        }
//        if (item == Items.WHITE_CARPET) {
//            return true;
//        }
//        if (item == Items.YELLOW_CARPET) {
//            return true;
//        }
//        return false;
//    }
//
//    private List<String> getBridleTextures() {
//        List<String> bridleTextures = new ArrayList<>();
//
//        if (this.getEnhancedInventory() != null) {
//            ItemStack bridleSlot = this.animalInventory.getItem(3);
//            if (bridleSlot != ItemStack.EMPTY) {
//                Item bridle = bridleSlot.getItem();
//                bridleTextures.add(BRIDLE_TEXTURE);
//                if (bridleSlot.getItem() instanceof CustomizableBridle) {
//                    if (bridle == ModItems.BRIDLE_BASIC_CLOTH) {
//                        bridleTextures.add(BRIDLE_HARDWEAR_TEXTURE[0]);
//                    } else if (bridle == ModItems.BRIDLE_BASIC_LEATHER) {
//                        bridleTextures.add(BRIDLE_LEATHER_TEXTURE);
//                        bridleTextures.add(BRIDLE_HARDWEAR_TEXTURE[0]);
//                    } else if (bridle == ModItems.BRIDLE_BASIC_CLOTH_GOLD) {
//                        bridleTextures.add(BRIDLE_HARDWEAR_TEXTURE[1]);
//                    } else if (bridle == ModItems.BRIDLE_BASIC_LEATHER_GOLD) {
//                        bridleTextures.add(BRIDLE_LEATHER_TEXTURE);
//                        bridleTextures.add(BRIDLE_HARDWEAR_TEXTURE[1]);
//                    } else if (bridle == ModItems.BRIDLE_BASIC_CLOTH_DIAMOND) {
//                        bridleTextures.add(BRIDLE_HARDWEAR_TEXTURE[2]);
//                    } else if (bridle == ModItems.BRIDLE_BASIC_LEATHER_DIAMOND) {
//                        bridleTextures.add(BRIDLE_LEATHER_TEXTURE);
//                        bridleTextures.add(BRIDLE_HARDWEAR_TEXTURE[2]);
//                    }
//                }
//            }
//        }
//
//        return bridleTextures;
//    }
//
//    private List<String> getChestTexture() {
//        List<String> chestTexture = new ArrayList<>();
//
//        if (this.getEnhancedInventory() != null) {
//            ItemStack chestSlot = this.animalInventory.getItem(0);
//            if (chestSlot != ItemStack.EMPTY) {
//                chestTexture.add(CHEST_TEXTURE);
//            }
//        }
//
//        return chestTexture;
//    }
//
//
//}
