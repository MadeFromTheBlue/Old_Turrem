package zap.turrem.client.render.object;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import zap.turrem.client.render.object.model.TVFBuffer;

public class RenderObject implements IRenderObject
{
	public final int index;
	
	private int verts;
	private int normals;
	private int colors;
	private int vertnum;
	private int vao;
	
	public RenderObject(int indx)
	{
		this.index = indx;
	}
	
	public void push(TVFBuffer buffer)
	{
		this.verts = buffer.getVboVertsId();
		this.colors = buffer.getVboColorsId();
		this.normals = buffer.getVboNormalsId();
		this.vertnum = buffer.getVertCount();
		this.vao = buffer.getVaoId();
	}
	
	@Override
	public void doRender()
	{
		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.verts);
		GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);

		GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.colors);
		GL11.glColorPointer(3, GL11.GL_FLOAT, 0, 0);
		
		GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.normals);
		GL11.glNormalPointer(GL11.GL_FLOAT, 0, 0);

		GL11.glDrawArrays(GL11.GL_QUADS, 0, this.vertnum);
	}
	
	@Override
	public void doDelete()
	{
		GL15.glDeleteBuffers(this.verts);
		GL15.glDeleteBuffers(this.colors);
		GL15.glDeleteBuffers(this.normals);
		GL30.glDeleteVertexArrays(this.vao);
	}
}
