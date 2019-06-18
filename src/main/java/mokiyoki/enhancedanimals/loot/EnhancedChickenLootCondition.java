package mokiyoki.enhancedanimals.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;

import javax.annotation.Nonnull;
import java.util.Random;

public class EnhancedChickenLootCondition implements LootCondition {
    private final String chickenType;

    public EnhancedChickenLootCondition(String chickenType) {
        this.chickenType = chickenType;
    }

    @Override
    public boolean testCondition(Random rand, LootContext context) {
        int i = 0;
        return context.getLootedEntity() instanceof EnhancedChicken && ((EnhancedChicken) context.getLootedEntity()).getDropMeatType().equals(chickenType);
    }

    public static class Serializer extends LootCondition.Serializer<EnhancedChickenLootCondition> {
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
            return new EnhancedChickenLootCondition(JsonUtils.getString(json, "chickenType"));
        }
    }
}