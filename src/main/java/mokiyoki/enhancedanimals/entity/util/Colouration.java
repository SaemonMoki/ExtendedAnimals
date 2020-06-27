package mokiyoki.enhancedanimals.entity.util;

public class Colouration {

    private RGBHolder dyeColour;
    private RGBHolder pheomelaninColour;
    private RGBHolder melaninColour;
    private RGBHolder leftEyeColour;
    private RGBHolder rightEyeColour;
    private RGBHolder saddleColour;
    private RGBHolder armourColour;
    private RGBHolder bridleColour;
    private RGBHolder harnessColour;

    public RGBHolder getDyeColour() {
        return dyeColour;
    }

    public void setDyeColour(float[] colour) {
        this.dyeColour = new RGBHolder(colour[0], colour[1], colour[2]);
    }

    public RGBHolder getPheomelaninColour() {
        return pheomelaninColour;
    }

    public void setPheomelaninColour(float[] colour) {
        this.pheomelaninColour = new RGBHolder(colour[0], colour[1], colour[2]);
    }

    public RGBHolder getMelaninColour() {
        return melaninColour;
    }

    public void setMelaninColour(float[] colour) {
        this.melaninColour = new RGBHolder(colour[0], colour[1], colour[2]);
    }

    public RGBHolder getLeftEyeColour() {
        return leftEyeColour;
    }

    public void setLeftEyeColour(float[] colour) {
        this.leftEyeColour = new RGBHolder(colour[0], colour[1], colour[2]);
    }

    public RGBHolder getRightEyeColour() {
        return rightEyeColour;
    }

    public void setRightEyeColour(float[] colour) {
        this.rightEyeColour = new RGBHolder(colour[0], colour[1], colour[2]);
    }
    
    public RGBHolder getSaddleColour() {
        return saddleColour;
    }

    public void setSaddleColour(float[] colour) {
        this.saddleColour = new RGBHolder(colour[0], colour[1], colour[2]);
    }

    public RGBHolder getArmourColour() {
        return armourColour;
    }

    public void setArmourColour(float[] colour) {
        this.armourColour = new RGBHolder(colour[0], colour[1], colour[2]);
    }

    public RGBHolder getBridleColour() {
        return bridleColour;
    }

    public void setBridleColour(float[] colour) {
        this.bridleColour = new RGBHolder(colour[0], colour[1], colour[2]);
    }

    public RGBHolder getHarnessColour() {
        return harnessColour;
    }

    public void setHarnessColour(float[] colour) {
        this.harnessColour = new RGBHolder(colour[0], colour[1], colour[2]);
    }

    public class RGBHolder {
        float red;
        float green;
        float blue;

        private RGBHolder(float red, float green, float blue) {
            this.red = red;
            this.green = green;
            this.blue = blue;
        }

        public float getRed() {
            return red;
        }

        public float getGreen() {
            return green;
        }

        public float getBlue() {
            return blue;
        }

        public String getRGBString() {
            return String.valueOf(red)+String.valueOf(green)+String.valueOf(blue);
        }
    }

    public String getRGBStrings() {
        String rgbString = "";
        if (getDyeColour() != null) {
            rgbString = rgbString + getDyeColour().getRGBString();
        }
        if (getPheomelaninColour() != null) {
            rgbString = rgbString + getPheomelaninColour().getRGBString();
        }
        if (getMelaninColour() != null) {
            rgbString = rgbString + getMelaninColour().getRGBString();
        }
        if (getLeftEyeColour() != null) {
            rgbString = rgbString + getLeftEyeColour().getRGBString();
        }
        if (getRightEyeColour() != null) {
            rgbString = rgbString + getRightEyeColour().getRGBString();
        }
        if (getSaddleColour() != null) {
            rgbString = rgbString + getSaddleColour().getRGBString();
        }
        if (getArmourColour() != null) {
            rgbString = rgbString + getArmourColour().getRGBString();
        }
        if (getBridleColour() != null) {
            rgbString = rgbString + getMelaninColour().getRGBString();
        }
        if (getHarnessColour() != null) {
            rgbString = rgbString + getMelaninColour().getRGBString();
        }

        return rgbString;
    }
}
