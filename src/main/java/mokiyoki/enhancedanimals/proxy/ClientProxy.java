package mokiyoki.enhancedanimals.proxy;

//import mokiyoki.enhancedanimals.gui.EggCartonScreen;
//import mokiyoki.enhancedanimals.gui.EncyclopediaScreen;
//import mokiyoki.enhancedanimals.gui.EnhancedAnimalScreen;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GrassColor;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

//import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.EGG_CARTON_CONTAINER;
//import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_ANIMAL_CONTAINER;

/**
 * Created by moki on 24/08/2018.
 */
//@Mod.EventBusSubscriber(modid = Reference.MODID, value = Dist.CLIENT)
//public class ClientProxy implements IProxy {
//    @Override
//    public void init(FMLCommonSetupEvent event) {
//    }
//
////    @Override
//    public void setEncylopediaInfo(ItemStack itemStack) {
////        EncyclopediaScreen.encyclopedia = itemStack;
//    }
//
////    @Override
//    public void openEncyclodepia() {
////        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> Minecraft.getInstance().setScreen(EncyclopediaScreen.currentEncyclopedia));
//    }
//}
