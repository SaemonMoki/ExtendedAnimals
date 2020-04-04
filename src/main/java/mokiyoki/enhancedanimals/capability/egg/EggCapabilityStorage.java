package mokiyoki.enhancedanimals.capability.egg;

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

        int[] genes = instance.getGenes();

        ListNBT geneList = new ListNBT();
        if (genes != null) {
            for (int gene : genes) {
                CompoundNBT nbttagcompound = new CompoundNBT();
                nbttagcompound.putInt("Gene", gene);
                geneList.add(nbttagcompound);
            }
        }
        compound.put("Genes", geneList);

        return compound;

    }

    @Override
    public void readNBT(Capability<IEggCapability> capability, IEggCapability instance, Direction side, INBT nbt) {
        CompoundNBT compound = (CompoundNBT) nbt;

        ListNBT geneList = compound.getList("Genes", 10);
        if (geneList.size()>0) {
            int[] genes = new int[Reference.CHICKEN_GENES_LENGTH];
            for (int i = 0; i < geneList.size(); ++i) {
                CompoundNBT nbttagcompound = geneList.getCompound(i);
                int gene = nbttagcompound.getInt("Gene");
                genes[i] = gene;
            }
            instance.setGenes(genes);
        }
    }
}
