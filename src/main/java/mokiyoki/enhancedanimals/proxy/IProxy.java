package mokiyoki.enhancedanimals.proxy;


import mokiyoki.enhancedanimals.gui.EnhancedAnimalScreen;
import mokiyoki.enhancedanimals.util.EnhancedAnimalInfo;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

/**
 * Created by moki on 24/08/2018.
 */
public interface IProxy {
    public void init(FMLCommonSetupEvent event);

    void initLoadComplete(FMLLoadCompleteEvent event);

    void setEncylopediaInfo(ItemStack itemStack);

    void openEncyclodepia();

    void setEnhancedAnimalInfo(EnhancedAnimalInfo enhancedAnimal);

    void openAnimalInfoGui(EnhancedAnimalScreen animalScreen);

    void initClientSetup(FMLClientSetupEvent event);
}
