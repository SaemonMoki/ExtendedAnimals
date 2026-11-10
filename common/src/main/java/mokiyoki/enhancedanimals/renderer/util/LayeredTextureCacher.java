package mokiyoki.enhancedanimals.renderer.util;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LayeredTextureCacher {

    private static final HashMap<String, CachedLayeredTexture> LAYERED_LOCATION_CACHE = new HashMap<>();

    private long cacheClearTimer;

    public LayeredTextureCacher() {
        this.cacheClearTimer = System.currentTimeMillis();
    }

    public ResourceLocation getFromCache(String key) {
        CachedLayeredTexture cachedLayeredTexture = LAYERED_LOCATION_CACHE.get(key);
        ResourceLocation resourceLocation;

        if (cachedLayeredTexture == null) {
            return null;
        } else {
            cachedLayeredTexture.recentlyAccessed = true;
            resourceLocation = cachedLayeredTexture.layeredTexture;
        }

        if (System.currentTimeMillis() - cacheClearTimer >= 200000L) {
            cacheRefresh();
            cacheClearTimer = System.currentTimeMillis();
        }

        return resourceLocation;
    }

    public void putInCache(String textureKey, ResourceLocation resourcelocation) {
        LAYERED_LOCATION_CACHE.put(textureKey, new CachedLayeredTexture(resourcelocation));
    }

    private void cacheRefresh() {
        List<String> deleteKeys = new ArrayList<>();

        for (String key : LAYERED_LOCATION_CACHE.keySet()) {
            CachedLayeredTexture layeredTexture = LAYERED_LOCATION_CACHE.get(key);

            if (layeredTexture.recentlyAccessed) {
                layeredTexture.recentlyAccessed = false; // for removal next refresh if not used
            } else {
                Minecraft.getInstance().getTextureManager().release(layeredTexture.layeredTexture);
                deleteKeys.add(key);
            }
        }

        for (String key : deleteKeys) {
            LAYERED_LOCATION_CACHE.remove(key);
        }
    }

    public void removeFromCache(String key) {
        LAYERED_LOCATION_CACHE.remove(key);
    }

    private class CachedLayeredTexture {
        boolean recentlyAccessed = true;
        ResourceLocation layeredTexture;

        private CachedLayeredTexture(ResourceLocation layeredTexture) {
            this.layeredTexture = layeredTexture;
        }
    }
}
