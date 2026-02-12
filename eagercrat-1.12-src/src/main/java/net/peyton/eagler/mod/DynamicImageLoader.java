package net.peyton.eagler.mod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import net.lax1dude.eaglercraft.EagRuntime;
import net.lax1dude.eaglercraft.opengl.WorldRenderer;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Utility class to load and render custom JPEG images as textures
 */
public class DynamicImageLoader {
    private static ResourceLocation cachedTextureLocation = null;
    private static DynamicTexture cachedTexture = null;

    public static ResourceLocation loadImageAsTexture(String imagePath) {
        if (cachedTextureLocation != null) {
            return cachedTextureLocation;
        }

        try {
            // Load the image file
            File imageFile = new File(imagePath);
            if (!imageFile.exists()) {
                return null;
            }

            BufferedImage bufferedImage = ImageIO.read(imageFile);
            if (bufferedImage == null) {
                return null;
            }

            // Convert to RGBA if needed
            BufferedImage rgbaImage = new BufferedImage(
                    bufferedImage.getWidth(),
                    bufferedImage.getHeight(),
                    BufferedImage.TYPE_INT_ARGB
            );

            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                for (int x = 0; x < bufferedImage.getWidth(); x++) {
                    rgbaImage.setRGB(x, y, bufferedImage.getRGB(x, y) | 0xFF000000);
                }
            }

            // Create dynamic texture
            int[] textureData = new int[rgbaImage.getWidth() * rgbaImage.getHeight()];
            rgbaImage.getRGB(0, 0, rgbaImage.getWidth(), rgbaImage.getHeight(), textureData, 0, rgbaImage.getWidth());

            DynamicTexture dynamicTexture = new DynamicTexture(rgbaImage.getWidth(), rgbaImage.getHeight());
            int[] texturePixels = dynamicTexture.getTextureData();
            System.arraycopy(textureData, 0, texturePixels, 0, textureData.length);
            dynamicTexture.updateDynamicTexture();

            // Register with texture manager
            Minecraft mc = Minecraft.getMinecraft();
            cachedTexture = dynamicTexture;
            cachedTextureLocation = mc.getTextureManager().getDynamicTextureLocation("astroclient_bg", dynamicTexture);

            return cachedTextureLocation;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void drawTexturedRect(int x, int y, int width, int height, ResourceLocation texture) {
        if (texture == null) {
            return;
        }

        Minecraft mc = Minecraft.getMinecraft();
        mc.getTextureManager().bindTexture(texture);

        net.lax1dude.eaglercraft.opengl.GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        net.lax1dude.eaglercraft.opengl.GlStateManager.enableBlend();

        net.minecraft.client.renderer.Tessellator tessellator = net.minecraft.client.renderer.Tessellator.getInstance();
        WorldRenderer bufferBuilder = tessellator.getBuffer();

        bufferBuilder.begin(7, net.minecraft.client.renderer.vertex.DefaultVertexFormats.POSITION_TEX);
        bufferBuilder.pos(x, y + height, 0).tex(0, 1).endVertex();
        bufferBuilder.pos(x + width, y + height, 0).tex(1, 1).endVertex();
        bufferBuilder.pos(x + width, y, 0).tex(1, 0).endVertex();
        bufferBuilder.pos(x, y, 0).tex(0, 0).endVertex();
        tessellator.draw();

        net.lax1dude.eaglercraft.opengl.GlStateManager.disableBlend();
    }
}
