package net.turrem.client.render.texture;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import net.turrem.client.asset.AssetLoader;
import net.turrem.utils.graphics.ImgUtils;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

public class TextureObject implements ITextureObject
{
	private String name;

	private int textureId = -1;

	private int width;
	private int height;
	private float aspect;

	public TextureObject(String name)
	{
		this.name = name;
	}

	@Override
	public void unbind()
	{
		GL11.glDeleteTextures(this.textureId);
		this.textureId = -1;
	}

	@Override
	public int bind(AssetLoader assets)
	{
		if (this.textureId != -1)
		{
			return this.textureId;
		}
		try
		{
			BufferedImage img = assets.loadTexture(this.name);
			this.width = img.getWidth();
			this.height = img.getHeight();
			this.aspect = (float) this.width / (float) this.height;
			ByteBuffer bytes = ImgUtils.imgToByteBuffer(img);

			int texId = GL11.glGenTextures();
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, texId);

			GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);

			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, this.width, this.height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, bytes);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);

			this.textureId = texId;
			return this.textureId;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			this.textureId = -1;
			return this.textureId;
		}
	}
	
	@Override
	public int rebind(AssetLoader assets)
	{
		if (this.textureId != -1)
		{
			this.unbind();
		}
		try
		{
			BufferedImage img = assets.loadTexture(this.name);
			this.width = img.getWidth();
			this.height = img.getHeight();
			this.aspect = (float) this.width / (float) this.height;
			ByteBuffer bytes = ImgUtils.imgToByteBuffer(img);

			int texId = GL11.glGenTextures();
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, texId);

			GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);

			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, this.width, this.height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, bytes);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);

			this.textureId = texId;
			return this.textureId;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			this.textureId = -1;
			return this.textureId;
		}
	}

	@Override
	public String getName()
	{
		return this.name;
	}

	@Override
	public final int getWidth()
	{
		return this.width;
	}

	@Override
	public final int getHeight()
	{
		return this.height;
	}

	@Override
	public final float getAspect()
	{
		return this.aspect;
	}

	@Override
	public void start()
	{

		GL11.glPushMatrix();
		if (this.textureId != -1)
		{
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.textureId);
		}
	}

	@Override
	public void end()
	{
		if (this.textureId != -1)
		{
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
			GL11.glDisable(GL11.GL_BLEND);
		}
		GL11.glPopMatrix();
	}
}
