package mokiyoki.enhancedanimals.capability.egg;

import mokiyoki.enhancedanimals.util.Genes;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

/**
 * Created by saemon on 30/09/2018.
 */
public class EggCapabilityStorage implements Capability.IStorage<IEggCapability> {

    @Nullable
    @Override
    public INBT writeNBT(Capability<IEggCapability> capability, IEggCapability instance, Direction side) {
        CompoundNBT compound = new CompoundNBT();
        Genes genes = instance.getGenes();
        if (genes != null) {
            ListNBT geneList = new ListNBT();
            int length = genes.getNumberOfSexlinkedGenes();
            int[] sexlinked = genes.getSexlinkedGenes();
            int[] autosomal = genes.getAutosomalGenes();
            for (int i = 0; i < length; i++) {
                CompoundNBT nbttagcompound = new CompoundNBT();
                nbttagcompound.putInt("Sgene", sexlinked[i]);
                geneList.add(nbttagcompound);
            }
            length = genes.getNumberOfAutosomalGenes();
            for (int i = 0; i < length; i++) {
                CompoundNBT nbttagcompound = new CompoundNBT();
                nbttagcompound.putInt("Agene", autosomal[i]);
                geneList.add(nbttagcompound);
            }
            compound.put("Genes", geneList);
        }

        return compound;

    }

    @Override
    public void readNBT(Capability<IEggCapability> capability, IEggCapability instance, Direction side, INBT nbt) {
        CompoundNBT compound = (CompoundNBT) nbt;
        Genes genetics = new Genes(Reference.CHICKEN_SEXLINKED_GENES_LENGTH, Reference.CHICKEN_GENES_LENGTH);
        ListNBT geneList = compound.getList("Genes", 10);
        if (geneList.size() > 0) {
            if (geneList.getCompound(0).contains("Sgene") && geneList.getCompound(0).getInt("Sgene") != 0) {
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
}
