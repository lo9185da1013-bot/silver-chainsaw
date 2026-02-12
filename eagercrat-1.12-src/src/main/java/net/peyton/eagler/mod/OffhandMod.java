package net.peyton.eagler.mod;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;

/**
 * Offhand Mod - Allows placing items in offhand from inventory
 * Press F to toggle items between main hand and offhand
 */
public class OffhandMod extends BaseMod {
    private static final int OFFHAND_KEY = 33; // F key
    private EntityPlayer player;
    private Minecraft mc = Minecraft.getMinecraft();

    public OffhandMod() {
        super("Offhand", "Press F to move items to/from offhand");
    }

    @Override
    protected void onEnable() {
    }

    @Override
    protected void onDisable() {
    }

    @Override
    public void onTick() {
    }

    @Override
    public boolean onKeyPress(int keyCode) {
        if (keyCode == OFFHAND_KEY) {
            player = mc.player;
            if (player == null || player.inventory == null) {
                return true;
            }

            ItemStack offhandStack = player.inventory.offHandInventory.get(0);
            ItemStack mainStack = player.inventory.getCurrentItem();

            if (!mainStack.isEmpty()) {
                // Move main hand item to offhand
                player.inventory.offHandInventory.set(0, mainStack.copy());
                player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemStack.EMPTY);
            } else if (!offhandStack.isEmpty()) {
                // Move offhand item back to main hand
                player.inventory.setInventorySlotContents(player.inventory.currentItem, offhandStack.copy());
                player.inventory.offHandInventory.set(0, ItemStack.EMPTY);
            }

            return true;
        }
        return false;
    }
}
