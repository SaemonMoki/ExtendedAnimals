package mokiyoki.enhancedanimals.util;

import net.minecraft.network.FriendlyByteBuf;

public class EnhancedAnimalInfo {

    public int health = 0;
    public int hunger = 0;
    public int tameness = 0;
    public boolean isFemale = true;
    public int pregnant = 0;
    public String name = "Animal";
    public String agePrefix = "ADULT";
    public Integer age = 0;
    public String sire = "???";
    public String dam = "???";
    public boolean hasName = false;

    public boolean created = false;

    public EnhancedAnimalInfo(){}

    public EnhancedAnimalInfo(FriendlyByteBuf buf) {
        health = buf.readVarInt();
        hunger = buf.readVarInt();
        isFemale = buf.readBoolean();
        pregnant = buf.readVarInt();
        name = buf.readUtf();
        agePrefix = buf.readUtf();
        age = buf.readVarInt();
        sire = buf.readUtf();
        dam = buf.readUtf();
        tameness = buf.readVarInt();
        hasName = buf.readBoolean();
        created = true;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeVarInt(health);
        buf.writeVarInt(hunger);
        buf.writeBoolean(isFemale);
        buf.writeVarInt(pregnant);
        buf.writeUtf(name);
        buf.writeUtf(agePrefix);
        buf.writeVarInt(age);
        buf.writeUtf(sire);
        buf.writeUtf(dam);
        buf.writeVarInt(tameness);
        buf.writeBoolean(hasName);
    }


}
