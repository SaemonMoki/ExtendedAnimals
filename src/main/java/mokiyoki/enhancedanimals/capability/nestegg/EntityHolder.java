package mokiyoki.enhancedanimals.capability.nestegg;

import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.world.item.ItemStack;

public class EntityHolder {
    private final String name;
    private final Genes genes;

    public EntityHolder() {
        this.name = null;
        this.genes = null;
    }

    public EntityHolder(String name, Genes genes) {
        this.name = name;
        this.genes = genes;
    }
}
