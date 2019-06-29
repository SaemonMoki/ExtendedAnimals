package mokiyoki.enhancedanimals.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraft.world.storage.loot.conditions.ILootCondition;

import javax.annotation.Nonnull;

public class EnhancedChickenLootCondition implements ILootCondition {
    private final String chickenType;

    @Override
    public boolean test(LootContext lootContext) {
        return lootContext.get(LootParameters.THIS_ENTITY) instanceof EnhancedChicken && ((EnhancedChicken) lootContext.get(LootParameters.THIS_ENTITY)).getDropMeatType().equals(chickenType);
    }

    public EnhancedChickenLootCondition(String chickenType) {
        this.chickenType = chickenType;
    }

    public static class Serializer extends AbstractSerializer<EnhancedChickenLootCondition> {
        public Serializer() {
            super(new ResourceLocation(Reference.MODID, "type_of_chicken"), EnhancedChickenLootCondition.class);
        }

        @Override
        public void serialize(@Nonnull JsonObject json, @Nonnull EnhancedChickenLootCondition value, @Nonnull JsonSerializationContext context) {
            json.addProperty("chickenType", value.chickenType);
        }

        @Nonnull
        @Override
        public EnhancedChickenLootCondition deserialize(@Nonnull JsonObject json, @Nonnull JsonDeserializationContext context) {
            return new EnhancedChickenLootCondition(JSONUtils.getString(json, "chickenType"));
        }
    }






}