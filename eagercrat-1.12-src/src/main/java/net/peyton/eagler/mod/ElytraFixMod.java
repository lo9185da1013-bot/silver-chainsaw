package net.peyton.eagler.mod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.init.Items;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;

/**
 * Elytra Fix Mod - Allows starting elytra flight while airborne when enabled.
 */
public class ElytraFixMod extends BaseMod {

    public ElytraFixMod() {
        super("ElytraFix", "Use elytra anytime while in the air");
    }

    @Override
    protected void onEnable() {
    }

    @Override
    protected void onDisable() {
    }

    @Override
    public void onTick() {
        if (mc.player instanceof EntityPlayerSP) {
            EntityPlayerSP sp = (EntityPlayerSP) mc.player;

            // Check if player is falling and has elytra
            if (!sp.onGround && sp.motionY < 0.0D && !sp.isElytraFlying()) {
                ItemStack chest = sp.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
                
                // Verify elytra exists and isn't broken
                if (!chest.isEmpty() && chest.getItem() instanceof ItemElytra && !ItemElytra.isBroken(chest)) {
                    try {
                        // Check if jump key is pressed
                        if (sp.movementInput != null && sp.movementInput.jump) {
                            // Send packet to start elytra flight
                            sp.connection.sendPacket(new CPacketEntityAction(sp, CPacketEntityAction.Action.START_FALL_FLYING));
                        }
                    } catch (Throwable t) {
                        // Silently fail
                    }
                }
            }
        }
    }

    private boolean isAirborne(EntityPlayer player) {
        return !player.onGround && player.motionY < 0;
    }
}
