package mokiyoki.enhancedanimals.util.handlers;

import mokiyoki.enhancedanimals.util.Reference;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class ClientTickScheduler {

    public static ArrayList<Task> scheduledClientTasks = new ArrayList<Task>();

    @SubscribeEvent
    public static void onTick(TickEvent.ClientTickEvent event) {
        scheduledClientTasks.removeIf( ct -> {
            ct.ticks--;
            if (ct.ticks <= 0) {
                ct.task.run();
                return true;
            } else {
                return false;
            }
        }
        );
    }

    public static class Task {
        Integer ticks;
        Runnable task;

        public Task(Integer ticks, Runnable task) {
            this.ticks = ticks;
            this.task = task;
        }

    }

}
