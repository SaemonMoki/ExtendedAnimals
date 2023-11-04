package mokiyoki.enhancedanimals.init.datagen;

import net.minecraft.client.renderer.texture.atlas.sources.SingleFile;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SpriteSourceProvider;

import java.util.Optional;

import static mokiyoki.enhancedanimals.renderer.EggCartonTileEntityRenderer.EGG_CARTON_TEXTURE;
import static net.minecraftforge.versions.forge.ForgeVersion.MOD_ID;

public class GASpriteSourceProvider extends SpriteSourceProvider {

    public GASpriteSourceProvider(PackOutput output, ExistingFileHelper fileHelper) {
        super(output, fileHelper, MOD_ID);
    }

    @Override
    protected void addSources() {
        atlas(SpriteSourceProvider.CHESTS_ATLAS).addSource(new SingleFile(EGG_CARTON_TEXTURE, Optional.empty()));
    }
}
