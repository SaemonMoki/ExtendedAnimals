package mokiyoki.enhancedanimals.capability.egg;

import mokiyoki.enhancedanimals.capability.turtleegg.EggHolder;
import mokiyoki.enhancedanimals.items.EnhancedEgg;
import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.Tag;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by saemon on 30/09/2018.
 */
public class EggCapabilityProvider implements IEggCapability, ICapabilitySerializable<Tag> {

    @CapabilityInject(IEggCapability.class)
    public static final Capability<IEggCapability> EGG_CAP = null;

    private final LazyOptional<IEggCapability> holder = LazyOptional.of(() -> this);

    private Genes genes;

    @Override
    public Genes getGenes() {
        return this.genes;
    }

    @Override
    public void setGenes(Genes chickGenes) {
        this.genes = chickGenes;
    }

    private String sireName;

    @Override
    public String getSire() { return this.sireName; }

    public void setSire(String name) {
        if (name!=null && !name.equals("")) {
            this.sireName = name;
        } else {
            this.sireName = "???";
        }
    }

    private String damName;

    @Override
    public String getDam() { return this.damName; }

    @Override
    public void setDam(String name) {
        if (name!= null && !name.equals("")) {
            this.damName = name;
        } else {
            this.damName = "???";
        }
    }

    @Override
    public void setEggData(Genes chickgenes, String sireName, String damName) {
        setGenes(chickgenes);
        setSire(sireName);
        setDam(damName);
    }

    @Override
    public void setEggData(EggHolder eggHolder) {
        setGenes(eggHolder.getGenes());
        setSire(eggHolder.getSire());
        setDam(eggHolder.getDam());
    }

    @Override
    public EggHolder getEggHolder(ItemStack stack) {
        boolean hasParents = false;
        if (stack.getItem() instanceof EnhancedEgg) {
            hasParents = ((EnhancedEgg) stack.getItem()).getHasParents(stack);
        }
        return new EggHolder(this.sireName, this.damName, this.genes, hasParents);
    }

    @Nullable
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
        return EGG_CAP.orEmpty(capability, holder);
    }

    @Override
    public Tag serializeNBT() {
        return EGG_CAP.getStorage().writeNBT(EGG_CAP, this, null);
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        EGG_CAP.getStorage().readNBT(EGG_CAP, this, null, nbt);
    }
}
