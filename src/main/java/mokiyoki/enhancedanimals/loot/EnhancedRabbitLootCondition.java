package mokiyoki.enhancedanimals.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import mokiyoki.enhancedanimals.entity.EnhancedRabbit;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;

import javax.annotation.Nonnull;
import java.util.Random;

public class EnhancedRabbitLootCondition implements LootCondition {
    private final String rabbitType;

    public EnhancedRabbitLootCondition(String rabbitType) {
        this.rabbitType = rabbitType;
    }

    @Override
    public boolean testCondition(Random rand, LootContext context) {
        int i = 0;
        return context.getLootedEntity() instanceof EnhancedRabbit && ((EnhancedRabbit) context.getLootedEntity()).getDropMeatType().equals(rabbitType);
    }

    public static class Serializer extends LootCondition.Serializer<EnhancedRabbitLootCondition> {
        public Serializer() {
            super(new ResourceLocation(Reference.MODID, "type_of_rabbit"), EnhancedRabbitLootCondition.class);
        }

        @Override
        public void serialize(@Nonnull JsonObject json, @Nonnull EnhancedRabbitLootCondition value, @Nonnull JsonSerializationContext context) {
            json.addProperty("rabbitType", value.rabbitType);
        }

        @Nonnull
        @Override
        public EnhancedRabbitLootCondition deserialize(@Nonnull JsonObject json, @Nonnull JsonDeserializationContext context) {
            return new EnhancedRabbitLootCondition(JsonUtils.getString(json, "rabbitType"));
        }
    }
}