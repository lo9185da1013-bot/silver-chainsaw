package net.peyton.eagler.mod;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.CPacketEntityAction;

import net.minecraft.client.Minecraft;

/**
 * Toggle Sneak Mod - Toggle sneak with a keybind instead of holding
 */
public class ToggleSneakMod extends BaseMod {
    private static final int SNEAK_KEY = 42; // Left Shift
    private boolean sneakActive = false;

    public ToggleSneakMod() {
        super("Toggle Sneak", "Press keybind to toggle sneak on/off");
    }

    @Override
    protected void onEnable() {
        this.sneakActive = false;
    }

    @Override
    protected void onDisable() {
        this.sneakActive = false;
    }

    @Override
    public void onTick() {
        if (sneakActive && mc.player != null && !mc.player.isSneaking()) {
            mc.player.setSneaking(true);
        }
    }

    @Override
    public boolean onKeyPress(int keyCode) {
        if (keyCode == SNEAK_KEY) {
            sneakActive = !sneakActive;
            try {
                if (mc.player != null) {
                    mc.player.setSneaking(sneakActive);
                }
                if (mc.player instanceof EntityPlayerSP) {
                    EntityPlayerSP sp = (EntityPlayerSP) mc.player;
                    if (sneakActive) {
                        sp.connection.sendPacket(new CPacketEntityAction(sp, CPacketEntityAction.Action.START_SNEAKING));
                    } else {
                        sp.connection.sendPacket(new CPacketEntityAction(sp, CPacketEntityAction.Action.STOP_SNEAKING));
                    }
                }
            } catch (Throwable t) {
            }
            return true;
        }
        return false;
    }
}
