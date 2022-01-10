package mokiyoki.enhancedanimals.items;

import mokiyoki.enhancedanimals.EnhancedAnimals;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;

import java.util.concurrent.ThreadLocalRandom;

public class MixableMilkBucket extends Item {
    private boolean newBucket = true;
    private int fullness = 0;
    private int colour = 16777215;
    private float redMushroomInfected = 0.0F;
    private float brownMushroomInfected = 0.0F;
    private float yellowFlowerInfected = 0.0F;
    private float cowMilkPercentage = 0.0F;
    private float mooshroomMilkPercentage = 0.0F;
    private float moobloomMilkPercentage = 0.0F;
    private float sheepMilkPercentage = 0.0F;
    private float goatMilkPercentage = 0.0F;
    private float creaminess = 0.0F;

    public MixableMilkBucket(Item.Properties builder) {
        super(builder);
    }

    private void setCurrentData() {

    }

    public ItemStack mix(ItemStack stack, int milkVolume, int colour, float creaminess, MilkType type, InoculationType inoculationType) {
        if (milkVolume > this.getSpaceLeft()) {
            milkVolume = this.getSpaceLeft();
        }
        if (this.newBucket) {
            this.newBucket = false;
            this.fullness = milkVolume;
            this.colour = colour;
            this.creaminess = creaminess;
            switch (type) {
                case SHEEP:
                    this.sheepMilkPercentage = 1.0F;
                case COW:
                    this.cowMilkPercentage = 1.0F;
                case RED_MOOSHROOM:
                case BROWN_MOOSHROOM:
                    this.mooshroomMilkPercentage = 1.0F;
                case MOOBLOOM:
                    this.mooshroomMilkPercentage = 1.0F;
                case GOAT:
                    this.mooshroomMilkPercentage = 1.0F;
            }
            switch (inoculationType) {
                case RED_MUSHROOM:
                    this.redMushroomInfected = ThreadLocalRandom.current().nextFloat();
                case BROWN_MUSHROOM:
                    this.brownMushroomInfected = ThreadLocalRandom.current().nextFloat();
                case YELLOW_FLOWER:
                    this.yellowFlowerInfected = ThreadLocalRandom.current().nextFloat()*0.5F;
            }
        } else {
            float redMushroom = inoculationType == InoculationType.RED_MUSHROOM ? ThreadLocalRandom.current().nextFloat() :  0.0F;
            float brownMushroom = inoculationType == InoculationType.BROWN_MUSHROOM ? ThreadLocalRandom.current().nextFloat() :  0.0F;
            float yellowFlower = inoculationType == InoculationType.YELLOW_FLOWER ? ThreadLocalRandom.current().nextFloat() :  0.0F;
            float sheepMilk = type == MilkType.SHEEP ? 1.0F :  0.0F;
            float cowMilk = type == MilkType.COW ? 1.0F :  0.0F;
            float goatMilk = type == MilkType.GOAT ? 1.0F : 0.0F;
            float mooshroomMilk = 0.0F;
            float bloomMilk = 0.0F;

            if (type == MilkType.RED_MOOSHROOM) {
                float red = ThreadLocalRandom.current().nextFloat();
                redMushroom = red > redMushroom ? red : redMushroom;
            } else if (type == MilkType.BROWN_MOOSHROOM) {
                float brown = ThreadLocalRandom.current().nextFloat();
                brownMushroom = brown > brownMushroom ? brown : brownMushroom;
            } else if (type == MilkType.MOOBLOOM) {
                float yellow = ThreadLocalRandom.current().nextFloat();
                yellowFlower = yellow > yellowFlower ? yellow : yellowFlower;
            }

            /**
             *  add check for when new milk is more than can be added
             */
            float newMilkPercent = (float) milkVolume/(this.fullness+milkVolume);
            float oldMilkPercent = (float) this.fullness/(this.fullness+milkVolume);

            this.sheepMilkPercentage = sheepMilk*newMilkPercent + this.sheepMilkPercentage*oldMilkPercent;
            this.cowMilkPercentage = cowMilk*newMilkPercent + this.cowMilkPercentage*oldMilkPercent;
            this.mooshroomMilkPercentage = mooshroomMilk*newMilkPercent + this.mooshroomMilkPercentage*oldMilkPercent;
            this.moobloomMilkPercentage = bloomMilk*newMilkPercent + this.moobloomMilkPercentage*oldMilkPercent;
            this.goatMilkPercentage = goatMilk*newMilkPercent + this.goatMilkPercentage*oldMilkPercent;

            this.redMushroomInfected = redMushroom*newMilkPercent + this.redMushroomInfected*oldMilkPercent;
            this.brownMushroomInfected = brownMushroom*newMilkPercent + this.brownMushroomInfected*oldMilkPercent;
            this.yellowFlowerInfected = yellowFlower*newMilkPercent + this.yellowFlowerInfected*oldMilkPercent;

            this.creaminess = creaminess*newMilkPercent + this.creaminess*oldMilkPercent;

            this.colour = setColour(colour, oldMilkPercent, newMilkPercent);

            this.fullness = milkVolume > 6 ? 6 : milkVolume;


        }

        return stack;
    }

    public int getColor(ItemStack stack) {
        CompoundTag compoundnbt = stack.getTagElement("display");
        return compoundnbt != null && compoundnbt.contains("color", 99) ? compoundnbt.getInt("color") : 10511680;
    }

    public int setColour(int milkColour, float oldMilkPercent, float newMilkPercent) {
        float[] oldColour = Colouration.getHSBFromABGR(this.colour);
        float[] newColour = Colouration.getHSBFromABGR(milkColour);

        newColour[0] = oldColour[0]*oldMilkPercent + newColour[0]*newMilkPercent;
        newColour[1] = oldColour[1]*oldMilkPercent + newColour[1]*newMilkPercent;
        newColour[2] = oldColour[2]*oldMilkPercent + newColour[2]*newMilkPercent;

        return Colouration.HSBtoABGR(newColour[0], newColour[1], newColour[2]);
    }

    public int getSpaceLeft() {
        return 6 - this.fullness;
    }

    public InoculationType infectBaby(float age) {
        float random = ThreadLocalRandom.current().nextFloat();
        if (random <= age*this.redMushroomInfected) {
            return InoculationType.RED_MUSHROOM;
        } else if (random <= age*this.brownMushroomInfected) {
            return InoculationType.BROWN_MUSHROOM;
        } else if (random <= age*this.yellowFlowerInfected) {
            return InoculationType.YELLOW_FLOWER;
        }

        return InoculationType.NONE;
    }

    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
     * the Item before the action is complete.
     */
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
        int i = ThreadLocalRandom.current().nextInt(6);
        if (!worldIn.isClientSide && i == 0) entityLiving.curePotionEffects(stack); // FORGE - move up so stack.shrink does not turn stack into air

        if (entityLiving instanceof ServerPlayer) {
            ServerPlayer serverplayerentity = (ServerPlayer)entityLiving;
            CriteriaTriggers.CONSUME_ITEM.trigger(serverplayerentity, stack);
            serverplayerentity.awardStat(Stats.ITEM_USED.get(this));
        }

        if (entityLiving instanceof Player && !((Player)entityLiving).getAbilities().instabuild) {
            stack.shrink(1);
        }

        if (i < this.fullness) {
            if (!worldIn.isClientSide) {
                entityLiving.removeAllEffects();
            }
        }

        return stack.isEmpty() ? new ItemStack(Items.BUCKET) : stack;
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getUseDuration(ItemStack stack) {
        return 32*(this.fullness/6);
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    /**
     * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
     * {@link #onItemUse}.
     */
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        playerIn.startUsingItem(handIn);
        return InteractionResultHolder.success(playerIn.getItemInHand(handIn));
    }

    public enum MilkType {
        COW,
        SHEEP,
        GOAT,
        RED_MOOSHROOM,
        BROWN_MOOSHROOM,
        MOOBLOOM
    }

    public enum InoculationType {
        RED_MUSHROOM,
        BROWN_MUSHROOM,
        YELLOW_FLOWER,
        NONE
    }
}
