package net.turrem.client.game.entity;

import org.lwjgl.opengl.GL11;

import net.turrem.client.game.world.ClientWorld;

public abstract class ClientEntity
{
	public final int entityId;
	public ClientWorld theWorld;
	
	protected boolean isPresent = true;
	
	protected double xPos;
	protected double yPos;
	protected double zPos;
	
	public ClientEntity(int id, ClientWorld world)
	{
		this.entityId = id;
		this.theWorld = world;
		this.theWorld.pushEntity(this);
	}
	
	public boolean isPresent()
	{
		return this.isPresent;
	}
	
	public abstract void renderEntity();
	
	public void render()
	{
		GL11.glPushMatrix();
		GL11.glTranslated(this.xPos, this.yPos, this.zPos);
		this.renderEntity();
		GL11.glPopMatrix();
	}
}
