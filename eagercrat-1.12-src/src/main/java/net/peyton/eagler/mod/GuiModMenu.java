package net.peyton.eagler.mod;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.lax1dude.eaglercraft.opengl.GlStateManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.lax1dude.eaglercraft.EagRuntime;
import net.lax1dude.eaglercraft.internal.EnumPlatformType;

/**
 * Space-themed Mod Menu GUI
 */
public class GuiModMenu extends GuiScreen {
    private GuiScreen parentScreen;
    private List<ModButtonWidget> modButtons = new ArrayList<>();
    private GuiButton backButton;
    private float scrollOffset = 0;
    private float starRotation = 0;
    private int selectedModIndex = -1;    private ParticleMod particleMod = null;
    public GuiModMenu(GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.clear();
        modButtons.clear();

        int startY = 60;
        int buttonHeight = 35;
        int centerX = this.width / 2;

        // Back button
        backButton = new GuiButton(0, centerX - 100, this.height - 30, 200, 20, "Back");
        this.buttonList.add(backButton);

        // Get mods from manager and create buttons
        List<BaseMod> mods = ModManager.getInstance().getModList();
        if (mods.isEmpty()) {
            // Register default mods if none exist
            ModManager.getInstance().registerMod(new SprintToggleMod());
            ModManager.getInstance().registerMod(new ToggleSneakMod());
            ModManager.getInstance().registerMod(new FastCrystalMod());
            // Only add ViaItems when not running as JavaScript/WASM
            if (EagRuntime.getPlatformType() != EnumPlatformType.JAVASCRIPT) {
                ModManager.getInstance().registerMod(new ViaItemsMod());
            }
            ModManager.getInstance().registerMod(new ElytraFixMod());
            ModManager.getInstance().registerMod(new ParticleMod());
            mods = ModManager.getInstance().getModList();
        }

        for (int i = 0; i < mods.size(); i++) {
            BaseMod mod = mods.get(i);
            if (mod instanceof ParticleMod) {
                this.particleMod = (ParticleMod) mod;
            }
            ModButtonWidget button = new ModButtonWidget(i + 1, centerX - 150, startY + (i * (buttonHeight + 5)), 150, buttonHeight, mod);
            modButtons.add(button);
            this.buttonList.add(button);
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            // Back button
            this.mc.displayGuiScreen(parentScreen);
        } else if (button instanceof ModButtonWidget) {
            ModButtonWidget modBtn = (ModButtonWidget) button;
            modBtn.getMod().toggle();
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        
        // Handle right-click on mod buttons
        if (mouseButton == 1) { // Right click
            for (ModButtonWidget button : modButtons) {
                if (button.getMod() instanceof ParticleMod && 
                    mouseX >= button.xPosition && mouseY >= button.yPosition && 
                    mouseX < button.xPosition + button.width && 
                    mouseY < button.yPosition + 20) { // Standard button height is 20
                    // Open particle settings
                    this.mc.displayGuiScreen(new GuiParticleSettings(this, (ParticleMod) button.getMod()));
                    return;
                }
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        // Draw space-themed background
        this.drawSpaceBackground(partialTicks);

        // Update star rotation
        this.starRotation += partialTicks * 0.5f;

        super.drawScreen(mouseX, mouseY, partialTicks);

		// Draw large title with space theme
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) this.width / 2, 30.0F, 0.0F);
		float titleScale = 2.8f;
		GlStateManager.scale(titleScale, titleScale, titleScale);
		this.drawCenteredString(this.fontRendererObj, "MOD MENU", 0, 0, 0xFF00FFFF);
		GlStateManager.popMatrix();
		
		this.drawSpaceDecorations();
	}

	private void drawSpaceBackground(float partialTicks) {
		// Draw background image or gradient fallback
		ResourceLocation bgTexture = new ResourceLocation("minecraft", "textures/gui/astroclient_bg.png");
		try {
			this.mc.getTextureManager().bindTexture(bgTexture);
			net.lax1dude.eaglercraft.opengl.GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
			net.lax1dude.eaglercraft.opengl.WorldRenderer wr = net.minecraft.client.renderer.Tessellator.getInstance().getBuffer();
			wr.begin(7, net.minecraft.client.renderer.vertex.DefaultVertexFormats.POSITION_TEX);
			wr.pos(0, this.height, 0).tex(0, 1).endVertex();
			wr.pos(this.width, this.height, 0).tex(1, 1).endVertex();
			wr.pos(this.width, 0, 0).tex(1, 0).endVertex();
			wr.pos(0, 0, 0).tex(0, 0).endVertex();
			net.minecraft.client.renderer.Tessellator.getInstance().draw();
		} catch (Exception e) {
			// Fallback: premium deep space gradient background
			this.drawGradientRect(0, 0, this.width, this.height, 0xFF0a0e27, 0xFF1a0a2e);
			// Add nebula effect with semi-transparent rectangles
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(770, 771);
			this.drawRect(0, 0, this.width / 4, this.height / 2, 0x1aFF00FF);
			this.drawRect(this.width * 3 / 4, this.height / 2, this.width, this.height, 0x1a00FFFF);
			this.drawRect(0, this.height * 2 / 3, this.width / 3, this.height, 0x15FF8800);
			GlStateManager.disableBlend();
		}
	}

	private void drawSpaceDecorations() {
        // Draw stars around the edges
        drawStar(20, 40, 8, 0xFFFFFF);
        drawStar(this.width - 20, 40, 8, 0xFFFFFF);
        drawStar(20, this.height - 40, 8, 0xFFFFFF);
        drawStar(this.width - 20, this.height - 40, 8, 0xFFFFFF);

        // Draw planet-like circles at corners
        drawFilledCircle(this.width - 60, 50, 15, 0xFF4488FF);
        drawFilledCircle(40, this.height - 60, 12, 0xFFFF8844);
    }

    private void drawStar(int x, int y, int size, int color) {
        // Draw 4-pointed star
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;
        GlStateManager.color(r / 255f, g / 255f, b / 255f, 0.8f);

        this.drawRect(x - 1, y - size, x + 1, y + size, color);
        this.drawRect(x - size, y - 1, x + size, y + 1, color);
    }

    private void drawFilledCircle(int x, int y, int radius, int color) {
        // Simple circle approximation using rectangles
        for (int r = radius; r > 0; r--) {
            int alpha = (int) (255 * (1.0f - (float) (radius - r) / radius) * 0.5f);
            int colorWithAlpha = (alpha << 24) | (color & 0xFFFFFF);
            this.drawRect(x - r, y - r, x + r, y + r, colorWithAlpha);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    /**
     * Custom button widget for mods
     */
    static class ModButtonWidget extends GuiButton {
        private BaseMod mod;

        ModButtonWidget(int buttonId, int x, int y, int width, int height, BaseMod mod) {
            super(buttonId, x, y, width, height, mod.getName());
            this.mod = mod;
        }

        BaseMod getMod() {
            return mod;
        }

        @Override
        public void func_191745_a(net.minecraft.client.Minecraft mc, int mouseX, int mouseY, float partialTicks) {
            if (this.visible) {
                this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;

                // Color based on enabled state
                int bgColor = this.mod.isEnabled() ? 0xFF00AA00 : 0xFFAA0000;
                if (this.hovered) {
                    bgColor = this.mod.isEnabled() ? 0xFF00FF00 : 0xFFFF0000;
                }

                // Draw button background with gradient
                drawGradientRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, bgColor, bgColor & 0xFF7F7F7F);

                // Draw button border
                drawRect(this.xPosition - 1, this.yPosition - 1, this.xPosition + this.width + 1, this.yPosition + this.height + 1, 0xFF00FFFF);

                // Draw mod name
                int textColor = 0xFFFFFF;
                drawString(mc.fontRendererObj, this.displayString, this.xPosition + 5, this.yPosition + 5, textColor);

                // Draw description
                String desc = this.mod.getDescription();
                int descX = this.xPosition + 5;
                int descY = this.yPosition + 16;
                drawString(mc.fontRendererObj, desc.substring(0, Math.min(20, desc.length())), descX, descY, 0xFFAAAAA);

                // Draw status
                String status = this.mod.isEnabled() ? "[ON]" : "[OFF]";
                drawString(mc.fontRendererObj, status, this.xPosition + this.width - 35, this.yPosition + 12, this.mod.isEnabled() ? 0xFF00FF00 : 0xFFFF0000);
            }
        }
    }
}
