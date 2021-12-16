package mokiyoki.enhancedanimals.entity.util;

import mokiyoki.enhancedanimals.items.CustomizableAnimalEquipment;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

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
    private int collarColour = -1;
    private int alphaMelanin = -1;


    public int getDyeColour() {
        return dyeColour;
    }

    public void setDyeColour(int colour) {
        this.dyeColour = colour;
    }

    public int getPheomelaninColour() {
        return this.pheomelaninColour;
    }

    public void setPheomelaninColour(int colour) {
        this.pheomelaninColour = colour;
    }

    public int getMelaninColour() {
        return this.melaninColour;
    }

    public void setMelaninColour(int colour) {
        this.melaninColour = colour;
    }

    public void setBabyAlpha(float shadeIntensity) {
        if (this.alphaMelanin == -1) {
            this.alphaMelanin = this.melaninColour >> 24 & 0xFF;
        }
        int a = (int)((this.alphaMelanin) * shadeIntensity);
        int b = this.melaninColour >> 16 & 0xFF;
        int g = this.melaninColour >> 8 & 0xFF;
        int r = this.melaninColour & 0xFF;

        this.melaninColour = a << 24 | b << 16 | g << 8 | r;
    }

    public int getLeftEyeColour() {
        return this.leftEyeColour;
    }

    public void setLeftEyeColour(int colour) {
        this.leftEyeColour = colour;
    }

    public int getRightEyeColour() {
        return this.rightEyeColour;
    }

    public void setRightEyeColour(int colour) {
        this.rightEyeColour = colour;
    }

    public int getSaddleColour() {
        return this.saddleColour;
    }

    public void setSaddleColour(int colour) {
        this.saddleColour = colour;
    }

    public int getArmourColour() {
        return this.armourColour;
    }

    public void setArmourColour(int colour) {
        this.armourColour = colour;
    }

    public int getBridleColour() {
        return this.bridleColour;
    }

    public void setBridleColour(int colour) {
        this.bridleColour = colour;
    }

    public int getHarnessColour() {
        return this.harnessColour;
    }

    public void setHarnessColour(int colour) {
        this.harnessColour = colour;
    }

    public int getCollarColour() {
        return this.collarColour;
    }

    public void setCollarColour(int colour) {
        this.collarColour = colour;
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
        if (getCollarColour() != -1) {
            rgbString = rgbString + String.valueOf(getCollarColour());
        }

        return rgbString;
    }

    public static float mixColourComponent(float colour1, float colour2, float percentage) {
        colour1 = (colour1 * (1F-percentage)) + (colour2*percentage);
        return colour1;
    }

    public static float mixHueComponent(float colour1, float colour2, float percentage) {
        if (colour2 > colour1) {
            if (colour2 > 0.1F) {
                colour1 = (colour1 * (1F - percentage)) + ((colour2 - 1F) * percentage);
            } else {
                colour1 = (colour1 * (1F - percentage)) + (colour2 * percentage);
            }
        } else {
            if (colour1 > 0.1F && !(colour2 > 0.1F)) {
                colour1 = ((colour1 - 1F) * (1F - percentage)) + (colour2 * percentage);
            } else {
                colour1 = (colour1 * (1F - percentage)) + (colour2 * percentage);
            }
        }

        if (colour1 < 0.0F) {
            colour1 = 1.0F + colour1;
        } else if (colour1 > 1.0F) {
            colour1 = colour1 - 1.0F;
        }


        return colour1;
    }

    public static int HSBtoABGR(float hue, float saturation, float brightness) {
        return HSBAtoABGR(hue,saturation,brightness, 0.5F);
    }

    public static int HSBAtoABGR(float hue, float saturation, float brightness, float alpha) {
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

        int a = (int)(alpha * 255.0f);

        return a << 24 | b << 16 | g << 8 | r;
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

    private static int[] getRGBAFromABGR(int colourABGR) {
        int[] colour = {0,0,0,0};
        colour[0] = colourABGR & 255;
        colour[1] = colourABGR >> 8 & 255;
        colour[2] = colourABGR >> 16 & 255;
        colour[3] = colourABGR >> 24 & 255;

        return colour;
    }

    public static int getEquipmentColor(ItemStack stack) {
        int colour = 0;
        if (stack.getItem() instanceof CustomizableAnimalEquipment) {
            CompoundTag compoundnbt = stack.getTagElement("display");
            if (compoundnbt != null && compoundnbt.contains("color", 99)) {
                colour = compoundnbt.getInt("color");
            } else {
                colour = ((CustomizableAnimalEquipment) stack.getItem()).getDefaultColour();
            }
        } else {
            colour = 10511680; //leather colour
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

    public static int getABGRFromBGR(float[] bgr) {
        return getABGRFromBGR(bgr[0], bgr[1], bgr[2]);
    }
    public static int getABGRFromBGR(float blue, float green, float red) {
        int b = (int) (blue * 255.0f + 0.5f);
        int g = (int) (green * 255.0f + 0.5f);
        int r = (int) (red * 255.0f + 0.5f);
        int a = 255;

        return a << 24 | b << 16 | g << 8 | r;
    }
}
