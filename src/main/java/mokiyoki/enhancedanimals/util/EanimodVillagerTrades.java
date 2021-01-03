package mokiyoki.enhancedanimals.util;

import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.items.CustomizableAnimalEquipment;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

import static net.minecraft.entity.merchant.villager.VillagerProfession.LEATHERWORKER;
import static net.minecraft.entity.merchant.villager.VillagerProfession.SHEPHERD;

public final class EanimodVillagerTrades extends MerchantOffers {
    private static ArrayList<SaleItemHolder> LeatherWorkerTrades = new ArrayList<>();
    private static ArrayList<SaleItemHolder> ShepardTrades = new ArrayList<>();
//    private static LinkedList<SaleItemHolder> FarmerTrades = new LinkedList<>();

    public EanimodVillagerTrades() {
        ShepardTrades.add(new SaleItemHolder(ModItems.COLLAR_BASIC_CLOTH, 1, 5, 1, 1, true, Items.BLACK_WOOL, Items.LIGHT_GRAY_WOOL));
        ShepardTrades.add(new SaleItemHolder(ModItems.COLLAR_BASIC_CLOTH_IRONRING, 1, 5, 1, 1, true, Items.BLACK_WOOL, Items.LIGHT_GRAY_WOOL));
        ShepardTrades.add(new SaleItemHolder(ModItems.COLLAR_BASIC_CLOTH_GOLDRING, 1, 5, 1, 1, true, Items.BLACK_WOOL, Items.LIGHT_GRAY_WOOL));
        ShepardTrades.add(new SaleItemHolder(ModItems.COLLAR_BASIC_CLOTH_DIAMONDRING, 1, 5, 1, 1, true, Items.BLACK_WOOL, Items.LIGHT_GRAY_WOOL));
        ShepardTrades.add(new SaleItemHolder(ModItems.COLLAR_BASIC_CLOTH_IRONBELL, 2, 5, 1, 1, true, Items.BLACK_WOOL, Items.LIGHT_GRAY_WOOL, Items.FLINT));
        ShepardTrades.add(new SaleItemHolder(ModItems.COLLAR_BASIC_CLOTH_GOLDBELL, 2, 3, 1, 1, true, Items.BLACK_WOOL, Items.LIGHT_GRAY_WOOL, Items.FLINT));
        ShepardTrades.add(new SaleItemHolder(ModItems.COLLAR_BASIC_CLOTH_DIAMONDBELL, 2, 3, 1, 1, true, Items.BLACK_WOOL, Items.LIGHT_GRAY_WOOL, Items.FLINT));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.COLLAR_BASIC_CLOTH, 1, 2, 1, 1, true, Items.LEATHER));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.COLLAR_BASIC_LEATHER, 1, 2, 1, 1, true, Items.LEATHER));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.COLLAR_BASIC_CLOTH_IRONRING, 1, 3, 1, 1, true, Items.LEATHER));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.COLLAR_BASIC_LEATHER_IRONRING, 1, 3, 1, 1, true, Items.LEATHER));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.COLLAR_BASIC_CLOTH_GOLDRING, 1, 3, 1, 1, true, Items.BLACK_WOOL, Items.LIGHT_GRAY_WOOL, Items.LEATHER));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.COLLAR_BASIC_LEATHER_GOLDRING, 1, 3, 1, 1, true, Items.LEATHER));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.COLLAR_BASIC_CLOTH_DIAMONDRING, 1, 3, 1, 1, true, Items.BLACK_WOOL, Items.LIGHT_GRAY_WOOL, Items.LEATHER));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.COLLAR_BASIC_LEATHER_DIAMONDRING, 1, 3, 1, 1, true, Items.LEATHER));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.COLLAR_BASIC_CLOTH_IRONBELL, 2, 3, 1, 1, true, Items.BLACK_WOOL, Items.LIGHT_GRAY_WOOL, Items.LEATHER, Items.FLINT));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.COLLAR_BASIC_LEATHER_IRONBELL, 2, 3, 1, 1, true, Items.LEATHER, Items.FLINT));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.COLLAR_BASIC_CLOTH_GOLDBELL, 2, 3, 1, 1, true, Items.BLACK_WOOL, Items.LIGHT_GRAY_WOOL, Items.LEATHER, Items.FLINT));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.COLLAR_BASIC_LEATHER_GOLDBELL, 2, 3, 1, 1, true, Items.LEATHER, Items.FLINT));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.COLLAR_BASIC_CLOTH_DIAMONDBELL, 2, 3, 1, 1, true, Items.BLACK_WOOL, Items.LIGHT_GRAY_WOOL, Items.LEATHER, Items.FLINT));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.COLLAR_BASIC_LEATHER_DIAMONDBELL, 2, 3, 1, 1, true, Items.LEATHER, Items.FLINT));
        LeatherWorkerTrades.add(new SaleItemHolder(Items.SADDLE, 3, 5, 1, 1, false, ModItems.SADDLE_BASIC_LEATHER));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.SADDLE_BASIC_CLOTH, 3, 5, 8, 14, true, Items.SADDLE, Items.LEATHER, Items.EMERALD));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.SADDLE_BASIC_CLOTH_GOLD, 3, 5, 8, 14, true, Items.SADDLE, Items.LEATHER, Items.EMERALD));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.SADDLE_BASIC_CLOTH_DIAMOND, 3, 5, 8, 14, true, Items.SADDLE, Items.LEATHER, Items.EMERALD));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.SADDLE_BASIC_CLOTH_WOOD, 3, 5, 8, 14, true, Items.SADDLE, Items.LEATHER, Items.EMERALD));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.SADDLE_BASIC_LEATHER, 3, 5, 8, 14, true, Items.SADDLE, Items.LEATHER, Items.EMERALD));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.SADDLE_BASIC_LEATHER_GOLD, 3, 5, 8, 14, true, Items.SADDLE, Items.LEATHER, Items.EMERALD));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.SADDLE_BASIC_LEATHER_DIAMOND, 3, 5, 8, 14, true, Items.SADDLE, Items.LEATHER, Items.EMERALD));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.SADDLE_BASIC_LEATHER_WOOD, 3, 5, 8, 14, true, Items.SADDLE, Items.LEATHER, Items.EMERALD));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.SADDLE_BASIC_LEATHERCLOTHSEAT, 3, 5, 8, 14, true, Items.SADDLE, Items.LEATHER, Items.EMERALD));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.SADDLE_BASIC_LEATHERCLOTHSEAT_GOLD, 3, 5, 8, 14, true, Items.SADDLE, Items.LEATHER, Items.EMERALD));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.SADDLE_BASIC_LEATHERCLOTHSEAT_DIAMOND, 3, 5, 8, 14, true, Items.SADDLE, Items.LEATHER, Items.EMERALD));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.SADDLE_BASIC_LEATHERCLOTHSEAT_WOOD, 3, 5, 8, 14, true, Items.SADDLE, Items.LEATHER, Items.EMERALD));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.SADDLE_ENGLISH_CLOTH, 3, 5, 8, 14, true, Items.SADDLE, Items.LEATHER, Items.EMERALD));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.SADDLE_ENGLISH_CLOTH_GOLD, 3, 5, 8, 14, true, Items.SADDLE, Items.LEATHER, Items.EMERALD));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.SADDLE_ENGLISH_CLOTH_DIAMOND, 3, 5, 8, 14, true, Items.SADDLE, Items.LEATHER, Items.EMERALD));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.SADDLE_ENGLISH_CLOTH_WOOD, 3, 5, 8, 14, true, Items.SADDLE, Items.LEATHER, Items.EMERALD));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.SADDLE_ENGLISH_LEATHER, 3, 5, 8, 14, true, Items.SADDLE, Items.LEATHER, Items.EMERALD));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.SADDLE_ENGLISH_LEATHER_GOLD, 3, 5, 8, 14, true, Items.SADDLE, Items.LEATHER, Items.EMERALD));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.SADDLE_ENGLISH_LEATHER_DIAMOND, 3, 5, 8, 14, true, Items.SADDLE, Items.LEATHER, Items.EMERALD));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.SADDLE_ENGLISH_LEATHER_WOOD, 3, 5, 8, 14, true, Items.SADDLE, Items.LEATHER, Items.EMERALD));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.SADDLE_ENGLISH_LEATHERCLOTHSEAT, 3, 5, 8, 14, true, Items.SADDLE, Items.LEATHER, Items.EMERALD));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.SADDLE_ENGLISH_LEATHERCLOTHSEAT_GOLD, 3, 5, 8, 14, true, Items.SADDLE, Items.LEATHER, Items.EMERALD));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.SADDLE_ENGLISH_LEATHERCLOTHSEAT_DIAMOND, 3, 5, 8, 14, true, Items.SADDLE, Items.LEATHER, Items.EMERALD));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.SADDLE_ENGLISH_LEATHERCLOTHSEAT_WOOD, 3, 5, 8, 14, true, Items.SADDLE, Items.LEATHER, Items.EMERALD));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.SADDLE_BASICPOMEL_CLOTH, 3, 5, 8, 14, true, Items.SADDLE, Items.LEATHER, Items.EMERALD));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.SADDLE_BASICPOMEL_CLOTH_GOLD, 3, 5, 8, 14, true, Items.SADDLE, Items.LEATHER, Items.EMERALD));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.SADDLE_BASICPOMEL_CLOTH_DIAMOND, 3, 5, 8, 14, true, Items.SADDLE, Items.LEATHER, Items.EMERALD));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.SADDLE_BASICPOMEL_CLOTH_WOOD, 3, 5, 8, 14, true, Items.SADDLE, Items.LEATHER, Items.EMERALD));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.SADDLE_BASICPOMEL_LEATHER, 3, 5, 8, 14, true, Items.SADDLE, Items.LEATHER, Items.EMERALD));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.SADDLE_BASICPOMEL_LEATHER_GOLD, 3, 5, 8, 14, true, Items.SADDLE, Items.LEATHER, Items.EMERALD));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.SADDLE_BASICPOMEL_LEATHER_DIAMOND, 3, 5, 8, 14, true, Items.SADDLE, Items.LEATHER, Items.EMERALD));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.SADDLE_BASICPOMEL_LEATHER_WOOD, 3, 5, 8, 14, true, Items.SADDLE, Items.LEATHER, Items.EMERALD));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.SADDLE_BASICPOMEL_LEATHERCLOTHSEAT, 3, 5, 8, 14, true, Items.SADDLE, Items.LEATHER, Items.EMERALD));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.SADDLE_BASICPOMEL_LEATHERCLOTHSEAT_GOLD, 3, 5, 8, 14, true, Items.SADDLE, Items.LEATHER, Items.EMERALD));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.SADDLE_BASICPOMEL_LEATHERCLOTHSEAT_DIAMOND, 3, 5, 8, 14, true, Items.SADDLE, Items.LEATHER, Items.EMERALD));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.SADDLE_BASICPOMEL_LEATHERCLOTHSEAT_WOOD, 3, 5, 8, 14, true, Items.SADDLE, Items.LEATHER, Items.EMERALD));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.BRIDLE_BASIC_CLOTH, 1, 4, 2, 4, true, Items.FLINT, Items.LEATHER, Items.EMERALD));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.BRIDLE_BASIC_CLOTH_GOLD, 1, 4, 2, 4, true, Items.FLINT, Items.LEATHER, Items.EMERALD));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.BRIDLE_BASIC_CLOTH_DIAMOND, 1, 4, 2, 4, true, Items.FLINT, Items.LEATHER, Items.EMERALD));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.BRIDLE_BASIC_LEATHER, 1, 4, 2, 4, true, Items.FLINT, Items.LEATHER, Items.EMERALD));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.BRIDLE_BASIC_LEATHER_GOLD, 1, 4, 2, 4, true, Items.FLINT, Items.LEATHER, Items.EMERALD));
        LeatherWorkerTrades.add(new SaleItemHolder(ModItems.BRIDLE_BASIC_LEATHER_DIAMOND, 1, 4, 2, 4, true, Items.FLINT, Items.LEATHER, Items.EMERALD));
        ShepardTrades.add(new SaleItemHolder(ModItems.BRIDLE_BASIC_CLOTH, 1, 4, 2, 4, true, Items.FLINT, Items.EMERALD));
        ShepardTrades.add(new SaleItemHolder(ModItems.BRIDLE_BASIC_CLOTH_GOLD, 1, 4, 2, 4, true, Items.FLINT, Items.EMERALD));
        ShepardTrades.add(new SaleItemHolder(ModItems.BRIDLE_BASIC_CLOTH_DIAMOND, 1, 4, 2, 4, true, Items.FLINT, Items.EMERALD));

//        FarmerTrades.add(new SaleItemHolder(fertilized_eggs, 1, 4, 2, 4, true, Items.FLINT, Items.EMERALD));

    }

    public static MerchantOffer getEanimodTrade(VillagerProfession profession, int level) {
        LinkedList<SaleItemHolder> selectedTradeType = new LinkedList<>();
        Item prefferedPayment = Items.EMERALD;
        if (profession.equals(LEATHERWORKER)) {
            selectedTradeType.addAll(LeatherWorkerTrades);
            prefferedPayment = Items.LEATHER;

        } else if (profession.equals(SHEPHERD)) {
            selectedTradeType.addAll(ShepardTrades);
            prefferedPayment = ThreadLocalRandom.current().nextBoolean()? Items.LIGHT_GRAY_WOOL : Items.BLACK_WOOL;

        }
//        else if (profession.equals(FARMER)) {
//            prefferedPayment = Items.WHEAT;
//        }

        int numberOfTrades = selectedTradeType.size();
        if (numberOfTrades!=0) {
            Collections.shuffle(selectedTradeType);
            for (int i = 0; i <= numberOfTrades; i++) {
                if (selectedTradeType.get(i).getLevel(level)) {
                    return selectedTradeType.get(i).getSale(level, prefferedPayment, false);
                }
            }
        }
        return new MerchantOffer(new ItemStack(Items.EMERALD), new ItemStack(ModItems.COLLAR_BASIC_CLOTH), ThreadLocalRandom.current().nextInt(level)+10, level*level, 0.2F);
    }

    public static MerchantOffer getWanderingEanimodTrade() {
        LinkedList<SaleItemHolder> selectedTradeType = new LinkedList<>();
        selectedTradeType.addAll(LeatherWorkerTrades);
        selectedTradeType.addAll(ShepardTrades);
        int numberOfTrades = selectedTradeType.size();
        return numberOfTrades!= 0 ? selectedTradeType.get(ThreadLocalRandom.current().nextInt(numberOfTrades)).getSale(ThreadLocalRandom.current().nextInt(8)+2, Items.EMERALD, true) : new MerchantOffer(new ItemStack(Items.EMERALD), new ItemStack(ModItems.COLLAR_BASIC_CLOTH), ThreadLocalRandom.current().nextInt(5)+10, 0, 0.2F);
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
        ArrayList<Item> types = new ArrayList<>();
        int minValue;
        int maxValue;
        boolean dyeable = false;

        public SaleItemHolder(Item saleItem, int minLevel, int maxLevel, int minValue, int maxValue, boolean dyeable, Item... types) {
            this.minLevel = minLevel;
            this.maxLevel = maxLevel;
            this.saleItem = saleItem;
            this.minValue = minValue;
            this.maxValue = maxValue;
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
                Item paymentItem = ThreadLocalRandom.current().nextBoolean() ? this.types.get(0) : prefferedPayment;
                payment = new ItemStack(paymentItem);
                if (payment.isStackable()) {
                    if (this.minValue == this.maxValue) {
                        payment.grow(this.minValue);
                    } else {
                        payment.grow(ThreadLocalRandom.current().nextInt(this.minValue, this.maxValue));
                    }
                }
            } else {
                if (this.minValue == this.maxValue) {
                    payment = new ItemStack(Items.EMERALD);
                } else {
                    int a = ThreadLocalRandom.current().nextInt(this.minValue, this.maxValue);
                    int b = ThreadLocalRandom.current().nextInt(this.minValue, this.maxValue);
                    payment = new ItemStack(Items.EMERALD, a <= b ? a : b);
                }
            }

            int experience = this.minLevel >= villagerLevel? villagerLevel*villagerLevel : ThreadLocalRandom.current().nextInt(this.minLevel, villagerLevel) * ThreadLocalRandom.current().nextInt(this.minLevel, villagerLevel);

            if (this.dyeable) {
                return new MerchantOffer(payment, ThreadLocalRandom.current().nextInt(4)==0 ? getRandomDye(new ItemStack(this.saleItem)) : new ItemStack(this.saleItem), ThreadLocalRandom.current().nextInt(villagerLevel)+10, experience, 0.2F);
            } else {
                return new MerchantOffer(payment, new ItemStack(this.saleItem), ThreadLocalRandom.current().nextInt(villagerLevel)+10, experience, 0.2F);
            }
        }
    }
}
