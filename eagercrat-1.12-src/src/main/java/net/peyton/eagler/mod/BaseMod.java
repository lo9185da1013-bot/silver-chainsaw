package net.peyton.eagler.mod;

import net.minecraft.client.Minecraft;

/**
 * Base class for AstroClient mods
 */
public abstract class BaseMod {
    protected String name;
    protected String description;
    protected boolean enabled;
    protected Minecraft mc;

    public BaseMod(String name, String description) {
        this.name = name;
        this.description = description;
        this.enabled = false;
        this.mc = Minecraft.getMinecraft();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (enabled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    public void toggle() {
        setEnabled(!enabled);
    }

    /**
     * Called when the mod is enabled
     */
    protected abstract void onEnable();

    /**
     * Called when the mod is disabled
     */
    protected abstract void onDisable();

    /**
     * Called every game tick
     */
    public void onTick() {
    }

    /**
     * Called when a key is pressed
     */
    public boolean onKeyPress(int keyCode) {
        return false;
    }
}
