package mokiyoki.enhancedanimals.capability.egg;

import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.nbt.NBTBase;
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
    public NBTBase writeNBT(Capability<IEggCapability> capability, IEggCapability instance, EnumFacing side) {
        NBTTagCompound compound = new NBTTagCompound();

        int[] genes = instance.getGenes();

        NBTTagList geneList = new NBTTagList();
        if (genes != null) {
            for (int gene : genes) {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setInteger("Gene", gene);
                geneList.appendTag(nbttagcompound);
            }
        }
        compound.setTag("Genes", geneList);

        return compound;

    }

    @Override
    public void readNBT(Capability<IEggCapability> capability, IEggCapability instance, EnumFacing side, NBTBase nbt) {
        NBTTagCompound compound = (NBTTagCompound) nbt;
        int[] genes = new int[Reference.CHICKEN_GENES_LENGTH];

        NBTTagList geneList = compound.getTagList("Genes", 10);
        for (int i = 0; i < geneList.tagCount(); ++i) {
            NBTTagCompound nbttagcompound = geneList.getCompoundTagAt(i);
            int gene = nbttagcompound.getInteger("Gene");
            genes[i] = gene;
        }
        instance.setGenes(genes);
    }
}
