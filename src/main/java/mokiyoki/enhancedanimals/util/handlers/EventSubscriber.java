package mokiyoki.enhancedanimals.util.handlers;

import mokiyoki.enhancedanimals.blocks.SparseGrassBlock;
import mokiyoki.enhancedanimals.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.HayBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.passive.horse.TraderLlamaEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ShearsItem;
import net.minecraft.item.SwordItem;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * Created by saemon on 8/09/2018.
 */
//@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class EventSubscriber {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void replaceVanillaMobs(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        //TODO figure out how to not delete named entities and maybe convert them instead.
        if (entity instanceof ChickenEntity) {
            if(!ConfigHandler.COMMON.spawnVanillaChickens.get()) {
                event.setCanceled(true);
            }
        }
        if (entity instanceof RabbitEntity) {
            if(!ConfigHandler.COMMON.spawnVanillaRabbits.get()) {
                event.setCanceled(true);
            }
        }
        if (entity instanceof LlamaEntity && !(entity instanceof TraderLlamaEntity)) {
            if(!ConfigHandler.COMMON.spawnVanillaLlamas.get()) {
                event.setCanceled(true);
            }
        }
        if (entity instanceof SheepEntity) {
            if(!ConfigHandler.COMMON.spawnVanillaSheep.get()) {
                event.setCanceled(true);
            }
        }
        if (entity instanceof CowEntity && !(entity instanceof MooshroomEntity)) {
            if(!ConfigHandler.COMMON.spawnVanillaCows.get()) {
                event.setCanceled(true);
            }
        }
        if (entity instanceof MooshroomEntity) {
            if(!ConfigHandler.COMMON.spawnVanillaMooshroom.get()) {
                event.setCanceled(true);
            }
        }
        if (entity instanceof PigEntity) {
            if(!ConfigHandler.COMMON.spawnVanillaPigs.get()) {
                event.setCanceled(true);
            }
        }

    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onBlockInteractEvent(PlayerInteractEvent.RightClickBlock event) {
        Item item = event.getItemStack().getItem();
        Block block = event.getWorld().getBlockState(event.getPos()).getBlock();
        if (block instanceof HayBlock && (item instanceof AxeItem || item instanceof SwordItem || item instanceof ShearsItem)) {
            event.getWorld().setBlockState(event.getPos(), ModBlocks.UnboundHay_Block.getDefaultState(), 11);
        } else if (block instanceof SparseGrassBlock && (item instanceof HoeItem)) {
            event.getWorld().setBlockState(event.getPos(), Blocks.FARMLAND.getDefaultState(), 11);
        }
    }

}
