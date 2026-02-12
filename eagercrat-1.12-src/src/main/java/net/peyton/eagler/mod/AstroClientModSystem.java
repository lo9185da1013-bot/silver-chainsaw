package net.peyton.eagler.mod;

import net.minecraft.client.Minecraft;
import net.lax1dude.eaglercraft.EagRuntime;
import net.lax1dude.eaglercraft.internal.EnumPlatformType;

/**
 * AstroClient Mod System Initializer
 * Registers all built-in mods when the client starts
 */
public class AstroClientModSystem {
    private static boolean initialized = false;

    public static void initialize(Minecraft mc) {
        if (initialized) {
            return;
        }

        // Register all default mods
        ModManager manager = ModManager.getInstance();
        
        manager.registerMod(new SprintToggleMod());
        manager.registerMod(new ToggleSneakMod());
        manager.registerMod(new FastCrystalMod());
        manager.registerMod(new OffhandMod());
        // ViaItems works on all platforms now
        manager.registerMod(new ViaItemsMod());
        manager.registerMod(new ElytraFixMod());
        manager.registerMod(new ParticleMod());

        initialized = true;
    }

    public static ModManager getModManager() {
        return ModManager.getInstance();
    }

    public static void onClientTick() {
        ModManager.getInstance().onClientTick();
    }

    public static void onKeyPress(int keyCode) {
        // F7 opens mod menu globally
        if (keyCode == 65) { // F7
            Minecraft mc = Minecraft.getMinecraft();
            mc.displayGuiScreen(new net.peyton.eagler.mod.GuiModMenu(mc.currentScreen));
            return;
        }
        ModManager.getInstance().onKeyPress(keyCode);
    }
}
