package mokiyoki.enhancedanimals.entity;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

import java.util.Map;

public abstract class EnhancedAnimalChestedAbstract extends EnhancedAnimalAbstract {

    private static final DataParameter<Boolean> HAS_CHEST = EntityDataManager.createKey(EnhancedAnimalChestedAbstract.class, DataSerializers.BOOLEAN);

    protected EnhancedAnimalChestedAbstract(EntityType<? extends EnhancedAnimalAbstract> type, World worldIn, int genesSize, Ingredient temptationItems, Ingredient breedItems, Map<Item, Integer> foodWeightMap, boolean bottleFeedable) {
        super(type, worldIn, genesSize, temptationItems, breedItems, foodWeightMap, bottleFeedable);
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(HAS_CHEST, false);
    }

    protected void dropInventory() {
        super.dropInventory();
        if (this.hasChest()) {
            if (!this.world.isRemote) {
                this.entityDropItem(Blocks.CHEST);
            }
            this.setChested(false);
        }

    }

    @Override
    public boolean canHaveChest() {
        return true;
    }

    public boolean hasChest() {
        return this.dataManager.get(HAS_CHEST);
    }

    public void setChested(boolean chested) {
        this.dataManager.set(HAS_CHEST, chested);
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

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("Chested", this.hasChest());
    }


    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setChested(compound.getBoolean("Chested"));
    }
}
