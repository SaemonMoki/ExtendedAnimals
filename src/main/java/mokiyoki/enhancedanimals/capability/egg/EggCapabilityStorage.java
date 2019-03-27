package mokiyoki.enhancedanimals.capability.egg;

import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

/**
 * Created by saemon on 30/09/2018.
 */
public class EggCapabilityStorage implements Capability.IStorage<IEggCapability> {

    @Nullable
    @Override
    public INBTBase writeNBT(Capability<IEggCapability> capability, IEggCapability instance, EnumFacing side) {
        NBTTagCompound compound = new NBTTagCompound();

        int[] genes = instance.getGenes();

        NBTTagList geneList = new NBTTagList();
        if (genes != null) {
            for (int gene : genes) {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setInt("Gene", gene);
                geneList.add(nbttagcompound);
            }
        }
        compound.setTag("Genes", geneList);

        return compound;

    }

    @Override
    public void readNBT(Capability<IEggCapability> capability, IEggCapability instance, EnumFacing side, INBTBase nbt) {
        NBTTagCompound compound = (NBTTagCompound) nbt;

        NBTTagList geneList = compound.getList("Genes", 10);
        if (geneList.size()>0) {
            int[] genes = new int[Reference.CHICKEN_GENES_LENGTH];
            for (int i = 0; i < geneList.size(); ++i) {
                NBTTagCompound nbttagcompound = geneList.getCompound(i);
                int gene = nbttagcompound.getInt("Gene");
                genes[i] = gene;
            }
            instance.setGenes(genes);
        }
    }
}
