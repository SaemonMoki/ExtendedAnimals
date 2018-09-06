package mokiyoki.enhancedanimals.init;

import mokiyoki.enhancedanimals.blocks.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moki on 25/08/2018.
 */
public class ModBlocks {
    public static final List<Block> BLOCKS = new ArrayList<Block>();
//Blocks
    public static final Block POST_ACACIA = new Post_Acacia("post_acacia", Material.WOOD);
    public static final Block POST_BIRCH = new Post_Birch("post_birch", Material.WOOD);
    public static final Block POST_DARK_OAK = new Post_Dark_Oak("post_dark_oak", Material.WOOD);
    public static final Block POST_JUNGLE = new Post_Jungle("post_jungle", Material.WOOD);
    public static final Block POST_OAK = new Post_Oak("post_oak", Material.WOOD);
    public static final Block POST_SPRUCE = new Post_Spruce("post_spruce", Material.WOOD);
}
