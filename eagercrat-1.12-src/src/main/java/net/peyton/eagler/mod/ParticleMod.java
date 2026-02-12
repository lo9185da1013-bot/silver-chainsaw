package net.peyton.eagler.mod;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;

/**
 * Particle Mod - Control which particles are rendered in the game
 */
public class ParticleMod extends BaseMod {
    private ParticleSettings particleSettings;

    public ParticleMod() {
        super("Particle Mod", "Control particle rendering with customizable settings");
        this.particleSettings = new ParticleSettings();
    }

    public ParticleSettings getParticleSettings() {
        return particleSettings;
    }

    public void toggleParticleType(int typeIndex) {
        switch (typeIndex) {
            case 0:
                particleSettings.showSmoke = !particleSettings.showSmoke;
                break;
            case 1:
                particleSettings.showExplosions = !particleSettings.showExplosions;
                break;
            case 2:
                particleSettings.showItemLoot = !particleSettings.showItemLoot;
                break;
            case 3:
                particleSettings.showEnchant = !particleSettings.showEnchant;
                break;
            case 4:
                particleSettings.showDamage = !particleSettings.showDamage;
                break;
            case 5:
                particleSettings.showPortal = !particleSettings.showPortal;
                break;
            case 6:
                particleSettings.showFireworks = !particleSettings.showFireworks;
                break;
            case 7:
                particleSettings.showTotemPops = !particleSettings.showTotemPops;
                break;
            case 8:
                particleSettings.toggleAllParticles();
                break;
        }
    }

    @Override
    protected void onEnable() {
        particleSettings.loadDefaults();
    }

    @Override
    protected void onDisable() {
        particleSettings.resetAll();
    }

    @Override
    public void onTick() {
        // Particle filtering happens at render time, not tick time
    }

    public static class ParticleSettings {
        public boolean showSmoke = true;
        public boolean showTotemPops = true;
        public boolean showExplosions = true;
        public boolean showFireworks = true;
        public boolean showItemLoot = true;
        public boolean showEnchant = true;
        public boolean showDamage = true;
        public boolean showPortal = true;
        public boolean allParticlesOff = false;

        public void loadDefaults() {
            showSmoke = true;
            showTotemPops = true;
            showExplosions = true;
            showFireworks = true;
            showItemLoot = true;
            showEnchant = true;
            showDamage = true;
            showPortal = true;
            allParticlesOff = false;
        }

        public void resetAll() {
            loadDefaults();
        }

        public void toggleAllParticles() {
            allParticlesOff = !allParticlesOff;
        }

        public boolean shouldRenderParticle(EnumParticleTypes particleType) {
            if (allParticlesOff) {
                return false;
            }
            
            // Map particle types to settings
            switch (particleType) {
                case SMOKE_NORMAL:
                case SMOKE_LARGE:
                    return showSmoke;
                case EXPLOSION_LARGE:
                case EXPLOSION_NORMAL:
                    return showExplosions;
                case FIREWORKS_SPARK:
                    return showFireworks;
                case ITEM_CRACK:
                    return showItemLoot;
                case ENCHANTMENT_TABLE:
                    return showEnchant;
                case DAMAGE_INDICATOR:
                    return showDamage;
                case PORTAL:
                    return showPortal;
                default:
                    return true;
            }
        }
    }
}
