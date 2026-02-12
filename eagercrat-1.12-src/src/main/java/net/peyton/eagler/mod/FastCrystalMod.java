package net.peyton.eagler.mod;

import net.lax1dude.eaglercraft.EagRuntime;
import net.lax1dude.eaglercraft.internal.EnumPlatformType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.lang.reflect.Field;

/**
 * Fast Crystal Mod - makes crystal placement feel instant by spawning a
 * client-side Ender Crystal immediately when placing while still allowing the
 * server to resolve the actual placement.
 */
public class FastCrystalMod extends BaseMod {

    private int placeCooldown = 0;

    public FastCrystalMod() {
        super("Fast Crystal", "Reduce right-click delay when holding end crystals");
    }

    @Override
    protected void onEnable() {
        placeCooldown = 0;
    }

    @Override
    protected void onDisable() {
        placeCooldown = 0;
    }

    @Override
    public void onTick() {
        try {
            if (placeCooldown > 0) placeCooldown--;

            if (!(mc.player instanceof EntityPlayerSP)) return;
            EntityPlayerSP sp = (EntityPlayerSP) mc.player;
            ItemStack held = sp.getHeldItemMainhand();
            if (held == null || held.getItem() != Items.END_CRYSTAL) return;

            // reduce right click delay client-side when holding crystals
            if (EagRuntime.getPlatformType() != EnumPlatformType.JAVASCRIPT) {
                try {
                    Field f = Minecraft.class.getDeclaredField("rightClickDelayTimer");
                    f.setAccessible(true);
                    f.setInt(mc, 0);
                } catch (Throwable t) {
                }
            }

            // attempt client-side placement when player is looking at a valid block
            if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == net.minecraft.util.math.RayTraceResult.Type.BLOCK) {
                BlockPos blockpos = mc.objectMouseOver.getBlockPos();
                if (blockpos != null) {
                    // mimic ItemEndCrystal placement rules
                    net.minecraft.block.state.IBlockState iblockstate = mc.world.getBlockState(blockpos);
                    net.minecraft.block.Block blk = iblockstate.getBlock();
                    if (blk == net.minecraft.init.Blocks.OBSIDIAN || blk == net.minecraft.init.Blocks.BEDROCK) {
                        BlockPos place = blockpos.up();
                        BlockPos place2 = place.up();
                        boolean flag = !mc.world.isAirBlock(place) && !mc.world.getBlockState(place).getBlock().isReplaceable(mc.world, place);
                        flag = flag | (!mc.world.isAirBlock(place2) && !mc.world.getBlockState(place2).getBlock().isReplaceable(mc.world, place2));
                        if (!flag) {
                            double d0 = (double) place.getX();
                            double d1 = (double) place.getY();
                            double d2 = (double) place.getZ();
                            java.util.List<net.minecraft.entity.Entity> list = mc.world.getEntitiesWithinAABBExcludingEntity(null,
                                    new AxisAlignedBB(d0, d1, d2, d0 + 1.0D, d1 + 2.0D, d2 + 1.0D));
                            if (list.isEmpty()) {
                                // only spawn once per click via placeCooldown
                                if (placeCooldown == 0 && mc.gameSettings.keyBindUseItem.isKeyDown()) {
                                    EntityEnderCrystal crystal = new EntityEnderCrystal(mc.world, (double) ((float) blockpos.getX() + 0.5F), (double) (blockpos.getY() + 1), (double) ((float) blockpos.getZ() + 0.5F));
                                    crystal.setShowBottom(false);
                                    mc.world.spawnEntityInWorld(crystal);
                                    // reduce client stack count
                                    try {
                                        held.func_190918_g(1);
                                    } catch (Throwable t) {
                                    }
                                    placeCooldown = 6;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Throwable t) {
        }
    }
}
