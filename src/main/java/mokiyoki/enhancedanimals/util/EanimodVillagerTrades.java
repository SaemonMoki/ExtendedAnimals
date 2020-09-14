package mokiyoki.enhancedanimals.util;

import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.items.CustomizableAnimalEquipment;
import mokiyoki.enhancedanimals.items.CustomizableSaddleEnglish;
import mokiyoki.enhancedanimals.items.CustomizableSaddleVanilla;
import mokiyoki.enhancedanimals.items.CustomizableSaddleWestern;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;

import java.util.concurrent.ThreadLocalRandom;

public class EanimodVillagerTrades extends MerchantOffers {
    private static int itemType = -1; //TODO make this an ENUM probably

    public static MerchantOffer getEanimodTrade(int level) {
        int maxTradeNumber = ThreadLocalRandom.current().nextInt(1, 13);
        ItemStack primarypayment = getPaymentItems();
        ItemStack saleitem = getLeveledSaleItem(level);

        if (ThreadLocalRandom.current().nextBoolean()) {
            return new MerchantOffer(primarypayment, saleitem, maxTradeNumber, 0, 0.2F);
        }

        if (primarypayment.getItem() != Items.EMERALD && ThreadLocalRandom.current().nextInt(5) == 0) {
            setItemType(-1);
        }
        return new MerchantOffer(primarypayment, getSecondaryPaymentItems(getItemType()), getRandomDye(saleitem), maxTradeNumber, 0, 0.2F);
    }

    public static MerchantOffer getWanderingEanimodTrade(int level) {
        int maxTradeNumber = ThreadLocalRandom.current().nextInt(1, 13);
        ItemStack primarypayment = new ItemStack(Items.EMERALD, ThreadLocalRandom.current().nextInt(4, 9));
        ItemStack saleitem = getLeveledSaleItem(level);

        if (ThreadLocalRandom.current().nextBoolean()) {
            return new MerchantOffer(primarypayment, saleitem, maxTradeNumber, 0, 0.2F);
        }

        Item item = saleitem.getItem();
        if (item instanceof CustomizableSaddleVanilla || item instanceof CustomizableSaddleWestern || item instanceof CustomizableSaddleEnglish) {
            new MerchantOffer(primarypayment, new ItemStack(Items.SADDLE, 1), ThreadLocalRandom.current().nextInt(4)==0 ? getRandomDye(saleitem) : saleitem, maxTradeNumber, 0, 0.2F);
        }

        return new MerchantOffer(primarypayment, getRandomDye(saleitem), maxTradeNumber, 0, 0.2F);
    }

    private static ItemStack getRandomDye(ItemStack itemStack) {
        if (itemStack.getItem() instanceof CustomizableAnimalEquipment) {
            int randomColour = ThreadLocalRandom.current().nextInt(16);
            ((CustomizableAnimalEquipment)itemStack.getItem()).setColor(itemStack, DyeColor.byId(randomColour).getColorValue());
        }
        return itemStack;
    }

    private static void setItemType(int type) {
        // type [ 0=iron, 1=gold, 2=diamond, 3=wood, 4=emerald, 5=redstone, 6=lapis ]
        itemType = type;
    }

    private static int getItemType() {
        return itemType;
    }

    private static ItemStack getPaymentItems() {
        switch (ThreadLocalRandom.current().nextInt(12)) {
            case 0:
                return new ItemStack(Items.LEATHER, ThreadLocalRandom.current().nextInt(4, 13));
            case 1:
                return new ItemStack(Items.RABBIT_HIDE, ThreadLocalRandom.current().nextInt(1, 6) * 4);
            case 2:
                return new ItemStack(Items.SADDLE, 1);
            case 3:
                return new ItemStack(Items.LEATHER_HORSE_ARMOR, 1);
            default:
                return new ItemStack(Items.EMERALD, ThreadLocalRandom.current().nextInt(4, 9));
        }
    }

    private static ItemStack getSecondaryPaymentItems(int type) {
        // type [ 0=iron, 1=gold, 2=diamond, 3=wood, 4=emerald, 5=redstone, 6=lapis ]
        ItemStack payment;
        switch (type) {
            case -1:
                return new ItemStack(Items.EMERALD, ThreadLocalRandom.current().nextInt(1, 7));
            case 0:
                return new ItemStack(Items.IRON_INGOT, ThreadLocalRandom.current().nextInt(1, 7));
            case 1:
                return new ItemStack(Items.GOLD_INGOT, ThreadLocalRandom.current().nextInt(1, 7));
            case 2:
                return new ItemStack(Items.DIAMOND, ThreadLocalRandom.current().nextInt(1, 5));
            case 3:
                return new ItemStack(Items.STICK, 16);
            case 4:
                return new ItemStack(Items.EMERALD, 6);
            case 5:
                return new ItemStack(Items.REDSTONE, ThreadLocalRandom.current().nextInt(1, 9));
            case 6:
                return new ItemStack(Items.LAPIS_LAZULI, ThreadLocalRandom.current().nextInt(1, 9));
            default:
                return new ItemStack(Items.EMERALD, 1);
        }
    }

    private static ItemStack getLeveledSaleItem(int level) {
        switch (level) {
            case 1 :
                return getCollarItem();
            case 2 :
                if (ThreadLocalRandom.current().nextBoolean()) {
                    return getCollarItem();
                } else {
                    return getBridleItem();
                }
            case 3 :
                if (ThreadLocalRandom.current().nextBoolean()) {
                    return getCollarItem();
                } else {
                    if (ThreadLocalRandom.current().nextBoolean()) {
                        return getSaddleItem();
                    } else {
                        return getBridleItem();
                    }
                }
            case 4 :
                switch (ThreadLocalRandom.current().nextInt(3)) {
                    case 0 : return getCollarItem();
                    case 1 : return getBridleItem();
                    case 2 : return getSaddleItem();
                }
            case 5 :
            default:
                if (ThreadLocalRandom.current().nextBoolean()) {
                    return getBridleItem();
                }
        }
        return getSaddleItem();
    }


    private static ItemStack getSaddleItem() {
        setItemType(0); //iron
        switch (ThreadLocalRandom.current().nextInt(13)) {
            case 0:
                return new ItemStack(ModItems.SADDLE_BASIC_CLOTH, 1);
            case 1:
                return new ItemStack(ModItems.SADDLE_ENGLISH_CLOTH, 1);
            case 2:
                return new ItemStack(ModItems.SADDLE_BASICPOMEL_CLOTH, 1);
            case 3:
                return new ItemStack(ModItems.SADDLE_BASIC_LEATHER, 1);
            case 4:
                return new ItemStack(ModItems.SADDLE_ENGLISH_LEATHER, 1);
            case 5:
                return new ItemStack(ModItems.SADDLE_BASICPOMEL_LEATHER, 1);
            case 6:
                return new ItemStack(ModItems.SADDLE_BASIC_LEATHERCLOTHSEAT, 1);
            case 7:
                return new ItemStack(ModItems.SADDLE_ENGLISH_LEATHERCLOTHSEAT, 1);
            case 8:
                return new ItemStack(ModItems.SADDLE_BASICPOMEL_LEATHERCLOTHSEAT, 1);
            case 9:
                return new ItemStack(ModItems.SADDLE_BASIC_CLOTH, 1);
            case 10:
                return new ItemStack(ModItems.SADDLE_BASIC_LEATHER, 1);
            case 11:
                return new ItemStack(ModItems.SADDLE_BASIC_LEATHERCLOTHSEAT, 1);
        }
        if (ThreadLocalRandom.current().nextBoolean()) {
            setItemType(1); //gold
            switch (ThreadLocalRandom.current().nextInt(13)) {
                case 0:
                    return new ItemStack(ModItems.SADDLE_BASIC_CLOTH_GOLD, 1);
                case 1:
                    return new ItemStack(ModItems.SADDLE_ENGLISH_CLOTH_GOLD, 1);
                case 2:
                    return new ItemStack(ModItems.SADDLE_BASICPOMEL_CLOTH_GOLD, 1);
                case 3:
                    return new ItemStack(ModItems.SADDLE_BASIC_LEATHER_GOLD, 1);
                case 4:
                    return new ItemStack(ModItems.SADDLE_ENGLISH_LEATHER_GOLD, 1);
                case 5:
                    return new ItemStack(ModItems.SADDLE_BASICPOMEL_LEATHER_GOLD, 1);
                case 6:
                    return new ItemStack(ModItems.SADDLE_BASIC_LEATHERCLOTHSEAT_GOLD, 1);
                case 7:
                    return new ItemStack(ModItems.SADDLE_ENGLISH_LEATHERCLOTHSEAT_GOLD, 1);
                case 8:
                    return new ItemStack(ModItems.SADDLE_BASICPOMEL_LEATHERCLOTHSEAT_GOLD, 1);
                case 9:
                    return new ItemStack(ModItems.SADDLE_BASIC_CLOTH_GOLD, 1);
                case 10:
                    return new ItemStack(ModItems.SADDLE_BASIC_LEATHER_GOLD, 1);
                case 11:
                    return new ItemStack(ModItems.SADDLE_BASIC_LEATHERCLOTHSEAT_GOLD, 1);
            }
        } else {
            setItemType(3); //wood
            switch (ThreadLocalRandom.current().nextInt(13)) {
                case 0:
                    return new ItemStack(ModItems.SADDLE_BASIC_CLOTH_WOOD, 1);
                case 1:
                    return new ItemStack(ModItems.SADDLE_ENGLISH_CLOTH_WOOD, 1);
                case 2:
                    return new ItemStack(ModItems.SADDLE_BASICPOMEL_CLOTH_WOOD, 1);
                case 3:
                    return new ItemStack(ModItems.SADDLE_BASIC_LEATHER_WOOD, 1);
                case 4:
                    return new ItemStack(ModItems.SADDLE_ENGLISH_LEATHER_WOOD, 1);
                case 5:
                    return new ItemStack(ModItems.SADDLE_BASICPOMEL_LEATHER_WOOD, 1);
                case 6:
                    return new ItemStack(ModItems.SADDLE_BASIC_LEATHERCLOTHSEAT_WOOD, 1);
                case 7:
                    return new ItemStack(ModItems.SADDLE_ENGLISH_LEATHERCLOTHSEAT_WOOD, 1);
                case 8:
                    return new ItemStack(ModItems.SADDLE_BASICPOMEL_LEATHERCLOTHSEAT_WOOD, 1);
                case 9:
                    return new ItemStack(ModItems.SADDLE_BASIC_CLOTH_WOOD, 1);
                case 10:
                    return new ItemStack(ModItems.SADDLE_BASIC_LEATHER_WOOD, 1);
                case 11:
                    return new ItemStack(ModItems.SADDLE_BASIC_LEATHERCLOTHSEAT_WOOD, 1);
            }
        }
        setItemType(2); //diamond
        switch (ThreadLocalRandom.current().nextInt(12)) {
            case 0:
                return new ItemStack(ModItems.SADDLE_BASIC_CLOTH_DIAMOND, 1);
            case 1:
                return new ItemStack(ModItems.SADDLE_ENGLISH_CLOTH_DIAMOND, 1);
            case 2:
                return new ItemStack(ModItems.SADDLE_BASICPOMEL_CLOTH_DIAMOND, 1);
            case 3:
                return new ItemStack(ModItems.SADDLE_BASIC_LEATHER_DIAMOND, 1);
            case 4:
                return new ItemStack(ModItems.SADDLE_ENGLISH_LEATHER_DIAMOND, 1);
            case 5:
                return new ItemStack(ModItems.SADDLE_BASICPOMEL_LEATHER_DIAMOND, 1);
            case 6:
                return new ItemStack(ModItems.SADDLE_BASIC_LEATHERCLOTHSEAT_DIAMOND, 1);
            case 7:
                return new ItemStack(ModItems.SADDLE_ENGLISH_LEATHERCLOTHSEAT_DIAMOND, 1);
            case 8:
                return new ItemStack(ModItems.SADDLE_BASICPOMEL_LEATHERCLOTHSEAT_DIAMOND, 1);
            case 9:
                return new ItemStack(ModItems.SADDLE_BASIC_CLOTH_DIAMOND, 1);
            case 10:
                return new ItemStack(ModItems.SADDLE_BASIC_LEATHER_DIAMOND, 1);
            case 11:
            default:
                return new ItemStack(ModItems.SADDLE_BASIC_LEATHERCLOTHSEAT_DIAMOND, 1);
        }
    }

    private static ItemStack getCollarItem() {
        setItemType(0);
        switch (ThreadLocalRandom.current().nextInt(9)) {
            case 0 :
                return new ItemStack(ModItems.COLLAR_BASIC_CLOTH);
            case 1 :
                return new ItemStack(ModItems.COLLAR_BASIC_LEATHER);
            case 2 :
                return new ItemStack(ModItems.COLLAR_BASIC_CLOTH_IRONRING);
            case 3 :
                return new ItemStack(ModItems.COLLAR_BASIC_LEATHER_IRONRING);
            case 4 :
                return new ItemStack(ModItems.COLLAR_BASIC_CLOTH_IRONBELL);
            case 5 :
                return new ItemStack(ModItems.COLLAR_BASIC_LEATHER_IRONBELL);
            case 6 :
                return new ItemStack(ModItems.COLLAR_BASIC_CLOTH);
            case 7 :
                return new ItemStack(ModItems.COLLAR_BASIC_LEATHER);
        }
        setItemType(1);
        switch (ThreadLocalRandom.current().nextInt(5)) {
            case 0:
                return new ItemStack(ModItems.COLLAR_BASIC_CLOTH_GOLDRING);
            case 1:
                return new ItemStack(ModItems.COLLAR_BASIC_LEATHER_GOLDRING);
            case 2:
                return new ItemStack(ModItems.COLLAR_BASIC_CLOTH_GOLDBELL);
            case 3:
                return new ItemStack(ModItems.COLLAR_BASIC_LEATHER_GOLDBELL);
        }
        setItemType(2);
        switch (ThreadLocalRandom.current().nextInt(4)) {
            case 0:
                return new ItemStack(ModItems.COLLAR_BASIC_CLOTH_DIAMONDRING);
            case 1:
                return new ItemStack(ModItems.COLLAR_BASIC_LEATHER_DIAMONDRING);
            case 2:
                return new ItemStack(ModItems.COLLAR_BASIC_CLOTH_DIAMONDBELL);
            default:
                return new ItemStack(ModItems.COLLAR_BASIC_LEATHER_DIAMONDBELL);
        }
    }

    private static ItemStack getBridleItem() {
        setItemType(0);
        switch (ThreadLocalRandom.current().nextInt(4)) {
            case 0 :
                return new ItemStack(ModItems.BRIDLE_BASIC_CLOTH);
            case 1 :
                return new ItemStack(ModItems.BRIDLE_BASIC_LEATHER);
            case 2 :
                return new ItemStack(ModItems.BRIDLE_BASIC_CLOTH);
            case 3 :
                return new ItemStack(ModItems.BRIDLE_BASIC_LEATHER);
        }
        setItemType(1);
        switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0:
                return new ItemStack(ModItems.BRIDLE_BASIC_CLOTH_GOLD);
            case 1:
                return new ItemStack(ModItems.BRIDLE_BASIC_LEATHER_GOLD);
        }
        setItemType(2);
        if (ThreadLocalRandom.current().nextBoolean()) {
            return new ItemStack(ModItems.BRIDLE_BASIC_CLOTH_DIAMOND);
        } else {
            return new ItemStack(ModItems.BRIDLE_BASIC_LEATHER_DIAMOND);
        }
    }
}
