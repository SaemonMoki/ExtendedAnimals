package mokiyoki.enhancedanimals.capability.egg;

import mokiyoki.enhancedanimals.util.Genes;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.ListTag;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

/**
 * Created by saemon on 30/09/2018.
 */
public class EggCapabilityStorage implements Capability.IStorage<IEggCapability> {

    @Nullable
    @Override
    public Tag writeNBT(Capability<IEggCapability> capability, IEggCapability instance, Direction side) {
        CompoundTag compound = new CompoundTag();
        Genes genes = instance.getGenes();
        if (genes != null) {
            CompoundTag nbttagcompound = new CompoundTag();
            nbttagcompound.putIntArray("SGenes", genes.getSexlinkedGenes());
            nbttagcompound.putIntArray("AGenes", genes.getAutosomalGenes());
            compound.put("Genetics", nbttagcompound);
        }

        String sireName = instance.getSire();
        if (sireName!=null) {
            compound.putString("SireName", sireName);
        }

        String damName = instance.getDam();
        if (damName!=null) {
            compound.putString("DamName", damName);
        }

        return compound;

    }

    @Override
    public void readNBT(Capability<IEggCapability> capability, IEggCapability instance, Direction side, Tag nbt) {
        CompoundTag compound = (CompoundTag) nbt;

        if (compound.contains("Genetics")) {
            CompoundTag nbtGenetics = compound.getCompound("Genetics");
            instance.setGenes(new Genes(nbtGenetics.getIntArray("SGenes"), nbtGenetics.getIntArray("AGenes")));
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
                    instance.setGenes(genetics);
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
                    instance.setGenes(genetics);
                }
            }
        }

        String sireName = compound.getString("SireName");
        if (!sireName.equals("") || sireName!=null) {
            instance.setSire(sireName);
        } else {
            instance.setSire("???");
        }
        String damName = compound.getString("DamName");
        if (!damName.equals("") || damName!=null) {
            instance.setDam(damName);
        } else {
            instance.setDam("???");
        }
    }
}
