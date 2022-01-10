package mokiyoki.enhancedanimals.capability.egg;

import mokiyoki.enhancedanimals.capability.turtleegg.EggHolder;
//import mokiyoki.enhancedanimals.items.EnhancedEgg;
import mokiyoki.enhancedanimals.util.Genes;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.Tag;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by saemon on 30/09/2018.
 */
public class EggCapabilityProvider implements IEggCapability, ICapabilitySerializable<Tag> {

    public static Capability<IEggCapability> EGG_CAP = CapabilityManager.get(new CapabilityToken<>() {});

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
//        if (stack.getItem() instanceof EnhancedEgg) {
//            hasParents = ((EnhancedEgg) stack.getItem()).getHasParents(stack);
//        }
        return new EggHolder(this.sireName, this.damName, this.genes, hasParents);
    }

    @Nullable
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
        return EGG_CAP.orEmpty(capability, holder);
    }

    @Override
    public Tag serializeNBT() {
        CompoundTag compound = new CompoundTag();
        Genes genes = this.getGenes();
        if (genes != null) {
            CompoundTag nbttagcompound = new CompoundTag();
            nbttagcompound.putIntArray("SGenes", genes.getSexlinkedGenes());
            nbttagcompound.putIntArray("AGenes", genes.getAutosomalGenes());
            compound.put("Genetics", nbttagcompound);
        }

        String sireName = this.getSire();
        if (sireName!=null) {
            compound.putString("SireName", sireName);
        }

        String damName = this.getDam();
        if (damName!=null) {
            compound.putString("DamName", damName);
        }

        return compound;

    }

    @Override
    public void deserializeNBT(Tag nbt) {
        CompoundTag compound = (CompoundTag) nbt;

        if (compound.contains("Genetics")) {
            CompoundTag nbtGenetics = compound.getCompound("Genetics");
            this.setGenes(new Genes(nbtGenetics.getIntArray("SGenes"), nbtGenetics.getIntArray("AGenes")));
        } else {
            ListTag geneList = compound.getList("Genes", 10);
            if (geneList.size() > 0) {
                if (geneList.getCompound(0).contains("Sgene") && geneList.getCompound(0).getInt("Sgene") != 0) {
                    Genes genetics = new Genes(Reference.CHICKEN_SEXLINKED_GENES_LENGTH, Reference.CHICKEN_AUTOSOMAL_GENES_LENGTH);
                    int sexlinkedlength = genetics.getNumberOfSexlinkedGenes();
                    for (int i = 0; i < sexlinkedlength; i++) {
                        genetics.setSexlinkedGene(i, geneList.getCompound(i).getInt("Sgene"));
                    }

                    int length = genetics.getNumberOfAutosomalGenes();
                    for (int i = 0; i < length; i++) {
                        genetics.setAutosomalGene(i, geneList.getCompound(i + sexlinkedlength).getInt("Agene"));
                    }
                    this.setGenes(genetics);
                } else {
                    Genes genetics = new Genes(Reference.CHICKEN_SEXLINKED_GENES_LENGTH, Reference.CHICKEN_AUTOSOMAL_GENES_LENGTH);
                    for (int i = 0; i < 9; ++i) {
                        int gene = geneList.getCompound(i).getInt("Gene");
                        if (gene == 10) {
                            break;
                        }
                        genetics.setSexlinkedGene(i * 2, gene);
                        genetics.setSexlinkedGene((i * 2) + 1, gene);
                    }

                    for (int i = 0; i < geneList.size(); ++i) {
                        if (i < 20) {
                            genetics.setAutosomalGene(i, 1);
                        }
                        genetics.setAutosomalGene(i, geneList.getCompound(i).getInt("Gene"));
                    }
                    this.setGenes(genetics);
                }
            }
        }

        String sireName = compound.getString("SireName");
        if (!sireName.equals("") || sireName!=null) {
            this.setSire(sireName);
        } else {
            this.setSire("???");
        }
        String damName = compound.getString("DamName");
        if (!damName.equals("") || damName!=null) {
            this.setDam(damName);
        } else {
            this.setDam("???");
        }
    }
}
