package mokiyoki.enhancedanimals.capability.egg;

import mokiyoki.enhancedanimals.capability.nestegg.EggHolder;
import mokiyoki.enhancedanimals.items.EnhancedEgg;
import mokiyoki.enhancedanimals.util.Genes;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.Tag;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.init.ModDataComponents;
import net.minecraft.world.item.component.CustomData;

import java.util.Optional;

/**
 * Created by saemon on 30/09/2018.
 */
public class EggCapabilityProvider implements IEggCapability {

    private ItemStack stack;

    public static Optional<EggCapabilityProvider> of(ItemStack stack) {
        if (!(stack.getItem() instanceof EnhancedEgg) && stack.getItem() != ModBlocks.TURTLE_EGG.get().asItem()) {
            return Optional.empty();
        }
        EggCapabilityProvider provider = new EggCapabilityProvider();
        CustomData eggData = stack.get(ModDataComponents.EGG_DATA.get());
        if (eggData != null) {
            provider.deserializeNBT(eggData.copyTag());
        }
        provider.stack = stack;
        return Optional.of(provider);
    }

    private void save() {
        if (this.stack != null) {
            this.stack.set(ModDataComponents.EGG_DATA.get(), CustomData.of((CompoundTag) this.serializeNBT()));
        }
    }

    private Genes genes;

    @Override
    public Genes getGenes() {
        return this.genes;
    }

    @Override
    public void setGenes(Genes genes) {
        this.genes = genes;
        save();
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
        save();
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
        save();
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
