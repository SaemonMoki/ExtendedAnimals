package mokiyoki.enhancedanimals.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import mokiyoki.enhancedanimals.entity.EnhancedLlama;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;

import javax.annotation.Nonnull;
import java.util.Random;

public class EnhancedLlamaLootCondition implements LootCondition {
    private final String llamaType;

    public EnhancedLlamaLootCondition(String llamaType) {
        this.llamaType = llamaType;
    }

    @Override
    public boolean testCondition(Random rand, LootContext context) {
        int i = 0;
        return context.getLootedEntity() instanceof EnhancedLlama && ((EnhancedLlama) context.getLootedEntity()).getDropMeatType().equals(llamaType);
    }

    public static class Serializer extends LootCondition.Serializer<EnhancedLlamaLootCondition> {
        public Serializer() {
            super(new ResourceLocation(Reference.MODID, "type_of_llama"), EnhancedLlamaLootCondition.class);
        }

        @Override
        public void serialize(@Nonnull JsonObject json, @Nonnull EnhancedLlamaLootCondition value, @Nonnull JsonSerializationContext context) {
            json.addProperty("llamaType", value.llamaType);
        }

        @Nonnull
        @Override
        public EnhancedLlamaLootCondition deserialize(@Nonnull JsonObject json, @Nonnull JsonDeserializationContext context) {
            return new EnhancedLlamaLootCondition(JsonUtils.getString(json, "llamaType"));
        }
    }
}