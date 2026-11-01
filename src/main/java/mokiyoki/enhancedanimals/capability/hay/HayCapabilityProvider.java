package mokiyoki.enhancedanimals.capability.hay;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.core.HolderLookup;
import mokiyoki.enhancedanimals.init.ModAttachments;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.minecraft.core.BlockPos;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by saemon on 29/09/2018.
 */
public class HayCapabilityProvider implements IHayCapability, INBTSerializable<Tag> {

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<HayCapabilityProvider>> HAY_CAP = ModAttachments.HAY_CAP;

    Set<BlockPos> hayBlockPositions = new HashSet<>();

    @Override
    public Set<BlockPos> getAllHayPos() {
        return hayBlockPositions;
    }

    @Override
    public void addHayPos(BlockPos blockPos) {
        hayBlockPositions.add(blockPos);
    }

    @Override
    public void removeHayPos(BlockPos blockPos) {
        hayBlockPositions.remove(blockPos);
    }

    @Override
    public void setAllHayPos(Set<BlockPos> blockPosList) {
        this.hayBlockPositions = blockPosList;
    }

    @Override
    public Tag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag compound = new CompoundTag();
        Set<BlockPos> allHayBlockPos = this.getAllHayPos();

        ListTag nbttaglist = new ListTag();

        for (BlockPos blockPos : allHayBlockPos)
        {
            CompoundTag nbttagcompound = new CompoundTag();
            nbttagcompound.putInt("X", blockPos.getX());
            nbttagcompound.putInt("Y", blockPos.getY());
            nbttagcompound.putInt("Z", blockPos.getZ());
            nbttaglist.add(nbttagcompound);
        }

        compound.put("HaysPos", nbttaglist);

        return compound;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, Tag nbt) {
        CompoundTag compound = (CompoundTag) nbt;
        Set<BlockPos> allHayBlockPos = new HashSet<>();
        ListTag nbttaglist = compound.getList("HaysPos", 10);

        for (int i = 0; i < nbttaglist.size(); ++i) {
            CompoundTag nbttagcompound = nbttaglist.getCompound(i);
            BlockPos blockPosOfHay = new BlockPos(nbttagcompound.getInt("X"), nbttagcompound.getInt("Y"), nbttagcompound.getInt("Z"));
            allHayBlockPos.add(blockPosOfHay);
        }

        this.setAllHayPos(allHayBlockPos);
    }
}
