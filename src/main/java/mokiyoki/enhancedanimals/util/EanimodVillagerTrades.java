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

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public class EanimodVillagerTrades extends MerchantOffers {
    private static LinkedList<SaleItemHolder> trades = new LinkedList<>();

    public EanimodVillagerTrades() {
        trades.add(new SaleItemHolder(ModItems.COLLAR_BASIC_CLOTH, 1, 6, 1, 1, true, Items.BLACK_WOOL, Items.LIGHT_GRAY_WOOL, Items.LEATHER));
        trades.add(new SaleItemHolder(ModItems.COLLAR_BASIC_CLOTH_IRONRING, 1, 9, 1, 1, true, Items.BLACK_WOOL, Items.LIGHT_GRAY_WOOL, Items.LEATHER, Items.IRON_NUGGET));
        trades.add(new SaleItemHolder(ModItems.COLLAR_BASIC_CLOTH_GOLDRING, 1, 9, 1, 1, true, Items.BLACK_WOOL, Items.LIGHT_GRAY_WOOL, Items.LEATHER, Items.GOLD_NUGGET));
        trades.add(new SaleItemHolder(ModItems.COLLAR_BASIC_CLOTH_DIAMONDRING, 1, 9, 1, 1, true, Items.BLACK_WOOL, Items.LIGHT_GRAY_WOOL, Items.LEATHER, Items.DIAMOND));
        trades.add(new SaleItemHolder(ModItems.COLLAR_BASIC_CLOTH_IRONBELL, 3, 9, 1, 1, true, Items.BLACK_WOOL, Items.LIGHT_GRAY_WOOL, Items.LEATHER, Items.IRON_NUGGET, Items.FLINT));
        trades.add(new SaleItemHolder(ModItems.COLLAR_BASIC_CLOTH_GOLDBELL, 3, 9, 1, 1, true, Items.BLACK_WOOL, Items.LIGHT_GRAY_WOOL, Items.LEATHER, Items.GOLD_NUGGET, Items.FLINT));
        trades.add(new SaleItemHolder(ModItems.COLLAR_BASIC_CLOTH_DIAMONDBELL, 3, 9, 1, 1, true, Items.BLACK_WOOL, Items.LIGHT_GRAY_WOOL, Items.LEATHER, Items.GOLD_INGOT, Items.FLINT));
        trades.add(new SaleItemHolder(Items.SADDLE, 3, 9, 1, 1, true, Items.BLACK_WOOL, Items.LIGHT_GRAY_WOOL, Items.LEATHER, Items.GOLD_INGOT, Items.FLINT));
    }

    public static MerchantOffer getEanimodTrade(int level, Item prefferedPayment) {
        int numberOfTrades = trades.size();
        if (numberOfTrades!=0) {
            Collections.shuffle(trades);
            for (int i = 0; i <= numberOfTrades; i++) {
                if (trades.get(i).getLevel(level)) {
                    return trades.get(i).getSale(level, prefferedPayment, false);
                }
            }
        }
        return new MerchantOffer(new ItemStack(Items.EMERALD), new ItemStack(ModItems.COLLAR_BASIC_CLOTH), ThreadLocalRandom.current().nextInt(level)+10, 0, 0.2F);
    }

    public static MerchantOffer getWanderingEanimodTrade() {
        int numberOfTrades = trades.size();
        return numberOfTrades!= 0 ? trades.get(ThreadLocalRandom.current().nextInt(numberOfTrades)).getSale(ThreadLocalRandom.current().nextInt(8)+2, Items.EMERALD, true) : new MerchantOffer(new ItemStack(Items.EMERALD), new ItemStack(ModItems.COLLAR_BASIC_CLOTH), ThreadLocalRandom.current().nextInt(5)+10, 0, 0.2F);
    }

    private static ItemStack getRandomDye(ItemStack itemStack) {
        if (itemStack.getItem() instanceof CustomizableAnimalEquipment) {
            int randomColour = ThreadLocalRandom.current().nextInt(16);
            ((CustomizableAnimalEquipment)itemStack.getItem()).setColor(itemStack, DyeColor.byId(randomColour).getColorValue());
        }
        return itemStack;
    }

    public class SaleItemHolder {
        int minLevel;
        int maxLevel;
        Item saleItem;
        LinkedList<Item> types = new LinkedList<>();
        int minValue;
        int maxValue;
        boolean dyeable = false;

        public SaleItemHolder(Item saleItem, int minLevel, int maxLevel, int minValue, int maxValue, boolean dyeable, Item... types) {
            this.minLevel = minLevel;
            this.maxLevel = maxLevel;
            this.saleItem = saleItem;
            this.minValue = minValue;
            this.maxValue = maxValue+1;
            this.dyeable = dyeable;
            this.types.addAll(Arrays.asList(types));
        }

        public boolean getLevel(int villagerLevel) {
            return villagerLevel <= this.maxLevel && villagerLevel >= this.minLevel;
        }

        public MerchantOffer getSale(int villagerLevel, Item prefferedPayment, boolean isTrader) {
            ItemStack payment = ItemStack.EMPTY;
            if (this.saleItem == Items.SADDLE) {
                int rand = ThreadLocalRandom.current().nextInt(36);
                switch (rand) {
                    case 0 : payment = new ItemStack(ModItems.SADDLE_BASIC_CLOTH);
                        break;
                    case 1 : payment = new ItemStack(ModItems.SADDLE_BASIC_CLOTH_GOLD);
                        break;
                    case 2 : payment = new ItemStack(ModItems.SADDLE_BASIC_CLOTH_DIAMOND);
                        break;
                    case 3 : payment = new ItemStack(ModItems.SADDLE_BASIC_CLOTH_WOOD);
                        break;
                    case 4 : payment = new ItemStack(ModItems.SADDLE_BASIC_LEATHER);
                        break;
                    case 5 : payment = new ItemStack(ModItems.SADDLE_BASIC_LEATHER_GOLD);
                        break;
                    case 6 : payment = new ItemStack(ModItems.SADDLE_BASIC_LEATHER_DIAMOND);
                        break;
                    case 7 : payment = new ItemStack(ModItems.SADDLE_BASIC_LEATHER_WOOD);
                        break;
                    case 8 : payment = new ItemStack(ModItems.SADDLE_BASIC_LEATHERCLOTHSEAT);
                        break;
                    case 9 : payment = new ItemStack(ModItems.SADDLE_BASIC_LEATHERCLOTHSEAT_GOLD);
                        break;
                    case 10 : payment = new ItemStack(ModItems.SADDLE_BASIC_LEATHERCLOTHSEAT_DIAMOND);
                        break;
                    case 11 : payment = new ItemStack(ModItems.SADDLE_BASIC_LEATHERCLOTHSEAT_WOOD);
                        break;
                    case 12 : payment = new ItemStack(ModItems.SADDLE_BASICPOMEL_CLOTH);
                        break;
                    case 13 : payment = new ItemStack(ModItems.SADDLE_BASICPOMEL_CLOTH_GOLD);
                        break;
                    case 14 : payment = new ItemStack(ModItems.SADDLE_BASICPOMEL_CLOTH_DIAMOND);
                        break;
                    case 15 : payment = new ItemStack(ModItems.SADDLE_BASICPOMEL_CLOTH_WOOD);
                        break;
                    case 16 : payment = new ItemStack(ModItems.SADDLE_BASICPOMEL_LEATHER);
                        break;
                    case 17 : payment = new ItemStack(ModItems.SADDLE_BASICPOMEL_LEATHER_GOLD);
                        break;
                    case 18 : payment = new ItemStack(ModItems.SADDLE_BASICPOMEL_LEATHER_DIAMOND);
                        break;
                    case 19 : payment = new ItemStack(ModItems.SADDLE_BASICPOMEL_LEATHER_WOOD);
                        break;
                    case 20 : payment = new ItemStack(ModItems.SADDLE_BASICPOMEL_LEATHERCLOTHSEAT);
                        break;
                    case 21 : payment = new ItemStack(ModItems.SADDLE_BASICPOMEL_LEATHERCLOTHSEAT_GOLD);
                        break;
                    case 22 : payment = new ItemStack(ModItems.SADDLE_BASICPOMEL_LEATHERCLOTHSEAT_DIAMOND);
                        break;
                    case 23 : payment = new ItemStack(ModItems.SADDLE_BASICPOMEL_LEATHERCLOTHSEAT_WOOD);
                        break;
                    case 24 : payment = new ItemStack(ModItems.SADDLE_ENGLISH_CLOTH);
                        break;
                    case 25 : payment = new ItemStack(ModItems.SADDLE_ENGLISH_CLOTH_GOLD);
                        break;
                    case 26 : payment = new ItemStack(ModItems.SADDLE_ENGLISH_CLOTH_DIAMOND);
                        break;
                    case 27 : payment = new ItemStack(ModItems.SADDLE_ENGLISH_CLOTH_WOOD);
                        break;
                    case 28 : payment = new ItemStack(ModItems.SADDLE_ENGLISH_LEATHER);
                        break;
                    case 29 : payment = new ItemStack(ModItems.SADDLE_ENGLISH_LEATHER_GOLD);
                        break;
                    case 30 : payment = new ItemStack(ModItems.SADDLE_ENGLISH_LEATHER_DIAMOND);
                        break;
                    case 31 : payment = new ItemStack(ModItems.SADDLE_ENGLISH_LEATHER_WOOD);
                        break;
                    case 32 : payment = new ItemStack(ModItems.SADDLE_ENGLISH_LEATHERCLOTHSEAT);
                        break;
                    case 33 : payment = new ItemStack(ModItems.SADDLE_ENGLISH_LEATHERCLOTHSEAT_GOLD);
                        break;
                    case 34 : payment = new ItemStack(ModItems.SADDLE_ENGLISH_LEATHERCLOTHSEAT_DIAMOND);
                        break;
                    case 35 : payment = new ItemStack(ModItems.SADDLE_ENGLISH_LEATHERCLOTHSEAT_WOOD);
                        break;
                }
            } else if (ThreadLocalRandom.current().nextInt(4)==0 && !isTrader) {
                Collections.shuffle(this.types);
                Item paymentItem = ThreadLocalRandom.current().nextBoolean() ? this.types.getFirst() : prefferedPayment;
                payment = new ItemStack(paymentItem);
                if (payment.isStackable()) {
                    payment.grow(ThreadLocalRandom.current().nextInt(this.minValue, this.maxValue));
                }
            } else {
                int a = ThreadLocalRandom.current().nextInt(this.minValue, this.maxValue);
                int b = ThreadLocalRandom.current().nextInt(this.minValue, this.maxValue);
                payment = new ItemStack(Items.EMERALD, a<=b? a : b);
            }

            if (this.dyeable) {
                return new MerchantOffer(payment, ThreadLocalRandom.current().nextInt(4)==0 ? getRandomDye(new ItemStack(this.saleItem)) : new ItemStack(this.saleItem), ThreadLocalRandom.current().nextInt(villagerLevel)+10, 0, 0.2F);
            } else {
                return new MerchantOffer(payment, new ItemStack(this.saleItem), ThreadLocalRandom.current().nextInt(villagerLevel)+10, 0, 0.2F);
            }
        }
    }
}
