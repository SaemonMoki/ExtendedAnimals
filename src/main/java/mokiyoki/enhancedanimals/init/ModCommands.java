  package mokiyoki.enhancedanimals.init;

import mokiyoki.enhancedanimals.EnhancedAnimals;
import mokiyoki.enhancedanimals.command.GeneticCommand;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

  @Mod.EventBusSubscriber(modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
  public class ModCommands {
      @SubscribeEvent
      public static void registerCommands(RegisterCommandsEvent event){
          GeneticCommand.register(event.getDispatcher());
      }
}