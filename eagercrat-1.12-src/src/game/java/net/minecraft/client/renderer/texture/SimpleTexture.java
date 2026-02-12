package net.minecraft.client.renderer.texture;

import java.io.Closeable;
import java.io.IOException;

import net.lax1dude.eaglercraft.IOUtils;
import net.lax1dude.eaglercraft.opengl.ImageData;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.util.ResourceLocation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleTexture extends AbstractTexture {
	private static final Logger LOG = LogManager.getLogger();
	protected final ResourceLocation textureLocation;

	public SimpleTexture(ResourceLocation textureResourceLocation) {
		this.textureLocation = textureResourceLocation;
	}

	public void loadTexture(IResourceManager resourceManager) throws IOException {
		this.deleteGlTexture();
		IResource iresource = null;

		try {
			try {
				iresource = resourceManager.getResource(this.textureLocation);
			} catch (IOException ioe) {
				// try fallbacks if enabled
				iresource = null;
				if (net.peyton.eagler.util.ResourceFallbacks.isEnabled()) {
					for (net.minecraft.util.ResourceLocation alt : net.peyton.eagler.util.ResourceFallbacks.alternatives(this.textureLocation)) {
						try {
							iresource = resourceManager.getResource(alt);
							if (iresource != null) break;
						} catch (IOException ex) {
							iresource = null;
						}
					}
				}
				if (iresource == null) throw ioe;
			}
			ImageData bufferedimage = TextureUtil.readBufferedImage(iresource.getInputStream());
			boolean flag = false;
			boolean flag1 = false;

			if (iresource.hasMetadata()) {
				try {
					TextureMetadataSection texturemetadatasection = (TextureMetadataSection) iresource
							.getMetadata("texture");

					if (texturemetadatasection != null) {
						flag = texturemetadatasection.getTextureBlur();
						flag1 = texturemetadatasection.getTextureClamp();
					}
				} catch (RuntimeException runtimeexception) {
					LOG.warn("Failed reading metadata of: {}", this.textureLocation, runtimeexception);
				}
			}

			regenerateIfNotAllocated();
			TextureUtil.uploadTextureImageAllocate(this.getGlTextureId(), bufferedimage, flag, flag1);
		} finally {
			IOUtils.closeQuietly((Closeable) iresource);
		}
	}
}