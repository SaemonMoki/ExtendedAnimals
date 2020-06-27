package mokiyoki.enhancedanimals.entity.util;

import com.sun.javafx.css.converters.ColorConverter;
import mokiyoki.enhancedanimals.items.CustomizableAnimalEquipment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.client.event.RenderTooltipEvent;

import java.awt.*;

public class Colouration {

    private int dyeColour = -1;
    private int pheomelaninColour = -1;
    private int melaninColour = -1;
    private int leftEyeColour = -1;
    private int rightEyeColour = -1;
    private int saddleColour = -1;
    private int armourColour = -1;
    private int bridleColour = -1;
    private int harnessColour = -1;


    public int getDyeColour() {
        return dyeColour;
    }

    public void setDyeColour(int colour) {
        this.dyeColour = colour;
    }

    public int getPheomelaninColour() {
        return pheomelaninColour;
    }

    public void setPheomelaninColour(int colour) {
        this.pheomelaninColour = colour;
    }

    public int getMelaninColour() {
        return melaninColour;
    }

    public void setMelaninColour(int colour) {
        this.melaninColour = colour;
    }

    public int getLeftEyeColour() {
        return leftEyeColour;
    }

    public void setLeftEyeColour(int colour) {
        this.leftEyeColour = colour;
    }

    public int getRightEyeColour() {
        return rightEyeColour;
    }

    public void setRightEyeColour(int colour) {
        this.rightEyeColour = colour;
    }

    public int getSaddleColour() {
        return saddleColour;
    }

    public void setSaddleColour(int colour) {
        this.saddleColour = colour;
    }

    public int getArmourColour() {
        return armourColour;
    }

    public void setArmourColour(int colour) {
        this.armourColour = colour;
    }

    public int getBridleColour() {
        return bridleColour;
    }

    public void setBridleColour(int colour) {
        this.bridleColour = colour;
    }

    public int getHarnessColour() {
        return harnessColour;
    }

    public void setHarnessColour(int colour) {
        this.harnessColour = colour;
    }

    public String getRGBStrings() {
        String rgbString = "";
        if (getDyeColour() != -1) {
            rgbString = rgbString + String.valueOf(getDyeColour());
        }
        if (getPheomelaninColour() != -1) {
            rgbString = rgbString + String.valueOf(getPheomelaninColour());
        }
        if (getMelaninColour() != -1) {
            rgbString = rgbString + String.valueOf(getMelaninColour());
        }
        if (getLeftEyeColour() != -1) {
            rgbString = rgbString + String.valueOf(getLeftEyeColour());
        }
        if (getRightEyeColour() != -1) {
            rgbString = rgbString + String.valueOf(getRightEyeColour());
        }
        if (getSaddleColour() != -1) {
            rgbString = rgbString + String.valueOf(getSaddleColour());
        }
        if (getArmourColour() != -1) {
            rgbString = rgbString + String.valueOf(getArmourColour());
        }
        if (getBridleColour() != -1) {
            rgbString = rgbString + String.valueOf(getBridleColour());
        }
        if (getHarnessColour() != -1) {
            rgbString = rgbString + String.valueOf(getHarnessColour());
        }

        return rgbString;
    }

    public static float mixColours(float colour1, float colour2, float percentage) {
        colour1 = (colour1 * (1.0F - percentage)) + (colour2 * percentage);
        return colour1;
    }

    public static int HSBtoABGR(float hue, float saturation, float brightness) {
        int r = 0, g = 0, b = 0;
        if (saturation == 0) {
            r = g = b = (int) (brightness * 255.0f + 0.5f);
        } else {
            float h = (hue - (float)Math.floor(hue)) * 6.0f;
            float f = h - (float)java.lang.Math.floor(h);
            float p = brightness * (1.0f - saturation);
            float q = brightness * (1.0f - saturation * f);
            float t = brightness * (1.0f - (saturation * (1.0f - f)));
            switch ((int) h) {
                case 0:
                    r = (int) (brightness * 255.0f + 0.5f);
                    g = (int) (t * 255.0f + 0.5f);
                    b = (int) (p * 255.0f + 0.5f);
                    break;
                case 1:
                    r = (int) (q * 255.0f + 0.5f);
                    g = (int) (brightness * 255.0f + 0.5f);
                    b = (int) (p * 255.0f + 0.5f);
                    break;
                case 2:
                    r = (int) (p * 255.0f + 0.5f);
                    g = (int) (brightness * 255.0f + 0.5f);
                    b = (int) (t * 255.0f + 0.5f);
                    break;
                case 3:
                    r = (int) (p * 255.0f + 0.5f);
                    g = (int) (q * 255.0f + 0.5f);
                    b = (int) (brightness * 255.0f + 0.5f);
                    break;
                case 4:
                    r = (int) (t * 255.0f + 0.5f);
                    g = (int) (p * 255.0f + 0.5f);
                    b = (int) (brightness * 255.0f + 0.5f);
                    break;
                case 5:
                    r = (int) (brightness * 255.0f + 0.5f);
                    g = (int) (p * 255.0f + 0.5f);
                    b = (int) (q * 255.0f + 0.5f);
                    break;
            }
        }
        return 0xff000000 | (102 << 24) | (b << 16) | (g << 8) | (r);
    }

    public static float[] getHSBFromABGR(int colourABGR) {
        int colour[] = getRGBFromABGR(colourABGR);
        int r = colour[0];
        int g = colour[1];
        int b = colour[2];
        return Color.RGBtoHSB(r, g, b, null);
    }

    private static int[] getRGBFromABGR(int colourABGR) {
        int[] colour = {0,0,0};
        colour[0] = colourABGR & 255;
        colour[1] = colourABGR >> 8 & 255;
        colour[2] = colourABGR >> 16 & 255;

        return colour;
    }

    public static int getEquipmentColor(ItemStack stack) {
        int colour = 0;
        if (stack.getItem() instanceof CustomizableAnimalEquipment) {
            CompoundNBT compoundnbt = stack.getChildTag("display");
            colour = compoundnbt.getInt("color");
        }

        colour = getABGRFromARGB(colour);

        return colour;
    }

    public static int getABGRFromARGB (int colour) {
        int a = colour >> 24 & 0xFF;
        int r = colour >> 16 & 0xFF;
        int g = colour >> 8 & 0xFF;
        int b = colour & 0xFF;

        return a << 24 | b << 16 | g << 8 | r;
    }

}
