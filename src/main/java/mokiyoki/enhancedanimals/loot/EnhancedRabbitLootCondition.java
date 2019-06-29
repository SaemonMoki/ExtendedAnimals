package mokiyoki.enhancedanimals.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import mokiyoki.enhancedanimals.entity.EnhancedRabbit;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraft.world.storage.loot.conditions.ILootCondition;

import javax.annotation.Nonnull;

public class EnhancedRabbitLootCondition implements ILootCondition {
    private final String rabbitType;

    public EnhancedRabbitLootCondition(String rabbitType) {
        this.rabbitType = rabbitType;
    }

    @Override
    public boolean test(LootContext lootContext) {
        return lootContext.get(LootParameters.THIS_ENTITY) instanceof EnhancedRabbit && ((EnhancedRabbit) lootContext.get(LootParameters.THIS_ENTITY)).getDropMeatType().equals(rabbitType);
    }

    public static class Serializer extends AbstractSerializer<EnhancedRabbitLootCondition> {
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
            return new EnhancedRabbitLootCondition(JSONUtils.getString(json, "rabbitType"));
        }
    }
}