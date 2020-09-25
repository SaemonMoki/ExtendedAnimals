package mokiyoki.enhancedanimals.init;

import mokiyoki.enhancedanimals.tileentity.EggCartonTileEntity;
import net.minecraft.tileentity.TileEntityType;

public class ModTileEntities {

    public static final TileEntityType<EggCartonTileEntity> EGG_CARTON_TILE_ENTITY = TileEntityType.Builder.create(EggCartonTileEntity::new, ModBlocks.EGG_CARTON).build(null);

}
