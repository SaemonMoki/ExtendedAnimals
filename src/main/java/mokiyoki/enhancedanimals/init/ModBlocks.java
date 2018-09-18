package mokiyoki.enhancedanimals.init;

import mokiyoki.enhancedanimals.blocks.BlockBase;
import mokiyoki.enhancedanimals.blocks.Post;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

/**
 * Created by moki on 25/08/2018.
 */
@ObjectHolder(Reference.MODID)
public class ModBlocks {

    public static final Block PostAcacia = new Post(Material.WOOD,"postAcacia", "post_acacia");
    public static final Block PostBirch = new Post(Material.WOOD,"postBirch", "post_birch");
    public static final Block PostDarkOak = new Post(Material.WOOD,"postDarkOak", "post_dark_oak");
    public static final Block PostJungle = new Post(Material.WOOD,"postJungle", "post_jungle");
    public static final Block PostOak = new Post(Material.WOOD,"postOak", "post_oak");
    public static final Block PostSpruce = new Post(Material.WOOD,"postSpruce", "post_spruce");

}