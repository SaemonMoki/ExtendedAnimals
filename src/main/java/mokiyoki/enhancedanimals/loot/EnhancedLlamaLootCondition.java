package mokiyoki.enhancedanimals.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import mokiyoki.enhancedanimals.entity.EnhancedLlama;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraft.world.storage.loot.conditions.ILootCondition;

import javax.annotation.Nonnull;

public class EnhancedLlamaLootCondition implements ILootCondition {
    private final String llamaType;

    public EnhancedLlamaLootCondition(String llamaType) {
        this.llamaType = llamaType;
    }

    @Override
    public boolean test(LootContext lootContext) {
        return lootContext.get(LootParameters.THIS_ENTITY) instanceof EnhancedLlama && ((EnhancedLlama) lootContext.get(LootParameters.THIS_ENTITY)).getDropMeatType().equals(llamaType);
    }

    public static class Serializer extends AbstractSerializer<EnhancedLlamaLootCondition> {
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
            return new EnhancedLlamaLootCondition(JSONUtils.getString(json, "llamaType"));
        }
    }
}