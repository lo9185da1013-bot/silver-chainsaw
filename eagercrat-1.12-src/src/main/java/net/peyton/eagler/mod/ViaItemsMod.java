package net.peyton.eagler.mod;

import net.peyton.eagler.util.ResourceFallbacks;

/**
 * ViaItems Mod - toggles resource fallback logic so the client can use newer
 * resource pack layouts (and display newer textures even without an explicit
 * resource pack) when enabled.
 */
public class ViaItemsMod extends BaseMod {

    public ViaItemsMod() {
        super("ViaItems", "Use new textures and items on older servers");
    }

    @Override
    protected void onEnable() {
        ResourceFallbacks.setEnabled(true);
    }

    @Override
    protected void onDisable() {
        ResourceFallbacks.setEnabled(false);
    }

    @Override
    public void onTick() {
        // no per-tick work required
    }
}
