package net.peyton.eagler.mod;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.lax1dude.eaglercraft.opengl.GlStateManager;

import java.io.IOException;

/**
 * Particle Settings GUI - Control individual particle types
 */
public class GuiParticleSettings extends GuiScreen {
    private GuiScreen parentScreen;
    private ParticleMod particleMod;
    private GuiButton backButton;
    private ParticleToggleButton[] particleButtons;

    // Particle names matching the toggleParticleType indices
    private static final String[] PARTICLE_NAMES = {
            "Smoke Particles",
            "Explosion Particles", 
            "Item Particles",
            "Enchantment Particles",
            "Damage Particles",
            "Portal Particles",
            "Fireworks Particles",
            "Totem Pop Particles",
            "Toggle All Particles"
    };

    public GuiParticleSettings(GuiScreen parentScreen, ParticleMod particleMod) {
        this.parentScreen = parentScreen;
        this.particleMod = particleMod;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.clear();

        int centerX = this.width / 2;
        int startY = 70;
        int buttonHeight = 25;

        backButton = new GuiButton(0, centerX - 100, this.height - 30, 200, 20, "Back to Mod Menu");
        this.buttonList.add(backButton);

        // Create buttons for each particle type
        particleButtons = new ParticleToggleButton[PARTICLE_NAMES.length];
        for (int i = 0; i < PARTICLE_NAMES.length; i++) {
            ParticleToggleButton btn = new ParticleToggleButton(
                    i + 1,
                    centerX - 140,
                    startY + (i * (buttonHeight + 3)),
                    280,
                    buttonHeight,
                    PARTICLE_NAMES[i],
                    i,
                    particleMod
            );
            particleButtons[i] = btn;
            this.buttonList.add(btn);
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            this.mc.displayGuiScreen(parentScreen);
        } else if (button instanceof ParticleToggleButton) {
            ParticleToggleButton particleBtn = (ParticleToggleButton) button;
            particleBtn.toggle();
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        // Draw background gradient
        this.drawGradientRect(0, 0, this.width, this.height, 0xFF0a0e27, 0xFF1a0a2e);

        // Nebula effect
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        this.drawRect(0, 0, this.width / 4, this.height / 2, 0x1aFF00FF);
        this.drawRect(this.width * 3 / 4, this.height / 2, this.width, this.height, 0x1a00FFFF);
        GlStateManager.disableBlend();

        // Title
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) this.width / 2, 25.0F, 0.0F);
        float titleScale = 2.0f;
        GlStateManager.scale(titleScale, titleScale, titleScale);
        this.drawCenteredString(this.fontRendererObj, "PARTICLE SETTINGS", 0, 0, 0xFF00FFFF);
        GlStateManager.popMatrix();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    /**
     * Custom button for particle type toggles
     */
    static class ParticleToggleButton extends GuiButton {
        int particleIndex;
        ParticleMod particleMod;

        ParticleToggleButton(int buttonId, int x, int y, int width, int height, String name, int particleIndex, ParticleMod particleMod) {
            super(buttonId, x, y, width, height, name);
            this.particleIndex = particleIndex;
            this.particleMod = particleMod;
        }

        void toggle() {
            particleMod.toggleParticleType(particleIndex);
        }

        boolean isEnabled() {
            ParticleMod.ParticleSettings settings = particleMod.getParticleSettings();
            switch (particleIndex) {
                case 0: return settings.showSmoke;
                case 1: return settings.showExplosions;
                case 2: return settings.showItemLoot;
                case 3: return settings.showEnchant;
                case 4: return settings.showDamage;
                case 5: return settings.showPortal;
                case 6: return settings.showFireworks;
                case 7: return settings.showTotemPops;
                case 8: return !settings.allParticlesOff;
                default: return true;
            }
        }

        @Override
        public void func_191745_a(net.minecraft.client.Minecraft mc, int mouseX, int mouseY, float partialTicks) {
            if (this.visible) {
                this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;

                boolean enabled = isEnabled();
                int bgColor = enabled ? 0xFF004400 : 0xFF440000;
                if (this.hovered) {
                    bgColor = enabled ? 0xFF00AA00 : 0xFFAA0000;
                }

                // Draw button background
                drawGradientRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, bgColor, bgColor & 0xFF7F7F7F);

                // Draw border
                drawRect(this.xPosition - 1, this.yPosition - 1, this.xPosition + this.width + 1, this.yPosition + this.height + 1, 0xFF00FFFF);

                // Draw text
                drawString(mc.fontRendererObj, this.displayString, this.xPosition + 10, this.yPosition + 7, 0xFFFFFF);

                // Draw status indicator
                String status = enabled ? "[ON]" : "[OFF]";
                int statusColor = enabled ? 0xFF00FF00 : 0xFFFF0000;
                drawString(mc.fontRendererObj, status, this.xPosition + this.width - 40, this.yPosition + 7, statusColor);
            }
        }
    }
}
