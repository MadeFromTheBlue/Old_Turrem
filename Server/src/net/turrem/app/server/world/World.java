package net.turrem.app.server.world;

import java.util.HashMap;

import java.io.File;

import net.turrem.app.server.Realm;
import net.turrem.app.server.TurremServer;
import net.turrem.app.server.world.gen.WorldGen;
import net.turrem.app.server.world.mesh.WorldMesh;
import net.turrem.app.server.world.settings.WorldSettings;
import net.turrem.app.server.world.storage.WorldStorage;

public class World
{
	private HashMap<String, Integer> realmMap = new HashMap<String, Integer>();
	private Realm[] realms = new Realm[16];
	public long worldTime = 0;
	public File saveLoc;
	public long seed;
	public TurremServer theTurrem;
	
	private Chunk lastChunk;
	
	public WorldGen theWorldGen;
	
	public WorldStorage storage;
	
	public WorldSettings genSettings;
	
	public World(File theSaveDir, long seed, WorldSettings set, TurremServer turrem)
	{
		this.theTurrem = turrem;
		this.genSettings = set;
		this.saveLoc = theSaveDir;
		this.seed = seed;
		this.storage = new WorldStorage(32, 9, this);
		this.theWorldGen = new WorldGen(this.seed, turrem, this);
	}
	
	public void addPlayer(ClientPlayer player)
	{
		Integer id = this.realmMap.get(player.username);
		Realm realm;
		if (id == null)
		{
			realm = new Realm(player.username, this);
			this.realmMap.put(player.username, realm.realmId);
			this.realms[realm.realmId] = realm;
		}
		else
		{
			realm = this.realms[id];
		}
		realm.setClient(player);
	}
	
	public void tick()
	{
		this.worldTime++;
		this.storage.tickChunks();
		this.storage.tickEntities();
		for (Realm realm : this.realms)
		{
			if (realm != null)
			{
				realm.tick();
			}
		}
	}
	
	public void unloadAll()
	{
		this.storage.clear();
	}
	
	public short getHeight(int x, int z)
	{
		int chunkx = x >> 4;
		int chunky = z >> 4;
		if (this.lastChunk != null && this.lastChunk.chunkx == chunkx && this.lastChunk.chunkz == chunky)
		{
			return this.lastChunk.getHeight(x, z);
		}
		Chunk c = this.getChunk(chunkx, chunky);
		this.lastChunk = c;
		return c.getHeight(x, z);
	}
	
	public Chunk chunkAt(int x, int z)
	{
		return this.storage.chunks.findAndGenerateChunk(x >> 4, z >> 4, this);
	}
	
	public Chunk getChunk(int chunkx, int chunkz)
	{
		return this.storage.chunks.findAndGenerateChunk(chunkx, chunkz, this);
	}
	
	public int getSideDrop(int x, int z)
	{
		int height = this.getHeight(x, z);
		int diff = 0;
		int h;
		
		h = this.getHeight(x + 1, z);
		h -= height;
		if (h < diff)
		{
			diff = h;
		}
		
		h = this.getHeight(x, z + 1);
		h -= height;
		if (h < diff)
		{
			diff = h;
		}
		
		h = this.getHeight(x - 1, z);
		h -= height;
		if (h < diff)
		{
			diff = h;
		}
		
		h = this.getHeight(x, z - 1);
		h -= height;
		if (h < diff)
		{
			diff = h;
		}
		
		return -diff;
	}
	
	public Realm[] getRealms()
	{
		return this.realms;
	}
	
	public float[] getGrid(int width, int height)
	{
		float[] grid = new float[width * height];
		WorldMesh mesh = this.theWorldGen.getFinalMesh();
		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < height; j++)
			{
				int k = i + width * j;
				grid[k] = mesh.createVertexFull(i, j).getData().height;
			}
		}
		return grid;
	}
}
