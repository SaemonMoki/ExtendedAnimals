package mokiyoki.enhancedanimals.items;

import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class AxolotlBucket extends BucketItem {
    private static final int[][] axolotlBase = new int[][]{
            {   0,   0,4001,4002,   0,4101,   0,   0,4601,   0,4702,4701,   0,   0},
            {3706,3806,   0,4003,4103,   0,   0,   0,   0,4603,4703,   0,4906,5006},
            {   0,3807,3907, 206, 501, 601, 801, 901,1101,1201,1506,4807,4907,   0},
            {   1,   0,3908, 502, 602, 702, 802, 902,1002,1102,1202,4808,   0,   1},
            {   0,3810,3911, 503, 603, 703, 803, 903,1003,1103,1203,4811,4910,   0},
            {3710,   1,   0, 306, 305, 504, 504,1204,1204,1405,1406,   0,   1,5010},
            {   0,   0,   0,   0,5902,   0,   0,   0,   0,5908,   0,   0,   0,   0},
            {   0,   0,   0,   0,5903,   0,   0,   0,   0,5909,   0,   0,   0,   0},
            {   0,   0,   0,5805,5905,6005,   0,   0,5811,5911,6011,   0,   0,   0},
            {   0,   0,   0,   0,5906,   0,   0,   0,   0,5912,   0,   0,   0,   0},
    };

    public AxolotlBucket(Supplier<? extends Fluid> supplier, Properties builder) {
        super(supplier, builder);
    }

    public boolean hasColor(ItemStack stack) {
        /**
         *  returns true if has collar
         */

        CompoundNBT compoundnbt = stack.getChildTag("display");
        return compoundnbt != null && compoundnbt.contains("color", 99);
    }

    public int getColor(ItemStack stack) {
        /**
         *  gets collar colour
         */

        CompoundNBT compoundnbt = stack.getChildTag("display");
        return compoundnbt != null && compoundnbt.contains("color", 99) ? compoundnbt.getInt("color") : 10511680;
    }

    public void setColor(ItemStack stack, int color) {
        /**
         *  sets collar colour
         */

        stack.getOrCreateChildTag("display").putInt("color", color);
    }

    public void setAxolotlBucketImageNBT(ItemStack stack, NativeImage image, boolean isGreater) {
        List<Integer> axolotlimage = new ArrayList<>();

        for (int i = 1; i < 15; i++) {
            for (int j = 0; j < 10; i++) {
                if (axolotlBase[i][j] != 0) {
                    axolotlimage.add(getPixel(image, i, j, isGreater));
                }
            }
        }


        stack.getOrCreateChildTag("display").putIntArray("axolotlimage", axolotlimage);
    }

    public int getPixel(NativeImage image, int x, int y, boolean isGreater) {
        if ((x > 0 && x < 15) && (y < 10)) {
            int coords = axolotlBase[x-1][y];

            if (coords == 0) {
                return 0;
            } else if (isGreater) {
                switch (coords) {
                    case 4001: coords = 3900; break;
                    case 4701: coords = 4800; break;
                    case 3706: coords = 3705; break;
                    case 5006: coords = 5005; break;
                    case 3710: coords = 3610; break;
                    case 5010: coords = 5110; break;
                }
            }

            x = (int)(coords * 0.01);
            y = coords - (x * 100);

            return image.getPixelRGBA(x, y);
        }

        return 0;
    }

    private class CollarType {
        boolean isLeather;

        CollarType(boolean isLeather, CollarHardware hardware) {

        }
    }

    private enum CollarHardware {
        IRON,
        GOLD,
        DIAMOND
    }
}
