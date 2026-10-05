package mokiyoki.enhancedanimals.capability.carry;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CarryCapabilityProvider implements ICarryCapability, ICapabilitySerializable<Tag> {

    public static Capability<ICarryCapability> CARRY_CAP = CapabilityManager.get(new CapabilityToken<>() {});

    private final LazyOptional<ICarryCapability> holder = LazyOptional.of(() -> this);

    private ItemStack carried = ItemStack.EMPTY;

    @Override
    public ItemStack getCarried() {
        return this.carried;
    }

    @Override
    public void setCarried(ItemStack stack) {
        this.carried = stack;
    }

    @Override
    public boolean isCarrying() {
        return !this.carried.isEmpty();
    }

    @Nullable
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
        return CARRY_CAP.orEmpty(capability, holder);
    }

    @Override
    public Tag serializeNBT() {
        CompoundTag compound = new CompoundTag();
        compound.put("Carried", this.carried.save(new CompoundTag()));
        return compound;
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        CompoundTag compound = (CompoundTag) nbt;
        this.carried = compound.contains("Carried") ? ItemStack.of(compound.getCompound("Carried")) : ItemStack.EMPTY;
    }
}
