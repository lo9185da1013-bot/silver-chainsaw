package net.peyton.eagler.mod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.CPacketEntityAction;

/**
 * Sprint Toggle Mod - Toggle sprint with a keybind instead of holding
 */
public class SprintToggleMod extends BaseMod {
    private static final int SPRINT_KEY = 29; // Left Control
    private boolean sprintActive = false;

    public SprintToggleMod() {
        super("Sprint Toggle", "Press keybind to toggle sprint on/off");
    }

    @Override
    protected void onEnable() {
        this.sprintActive = false;
    }

    @Override
    protected void onDisable() {
        this.sprintActive = false;
    }

    @Override
    public void onTick() {
        if (sprintActive && mc.player != null) {
            // Apply sprint every tick when toggled on
            if (!mc.player.isSprinting()) {
                mc.player.setSprinting(true);
            }
        }
    }

    @Override
    public boolean onKeyPress(int keyCode) {
        if (keyCode == SPRINT_KEY) {
            sprintActive = !sprintActive;
            if (mc.player != null) {
                mc.player.setSprinting(sprintActive);
            }
            try {
                if (mc.player instanceof EntityPlayerSP) {
                    EntityPlayerSP sp = (EntityPlayerSP) mc.player;
                    if (sprintActive) {
                        sp.connection.sendPacket(new CPacketEntityAction(sp, CPacketEntityAction.Action.START_SPRINTING));
                    } else {
                        sp.connection.sendPacket(new CPacketEntityAction(sp, CPacketEntityAction.Action.STOP_SPRINTING));
                    }
                }
            } catch (Throwable t) {
            }
            return true;
        }
        return false;
    }
}
