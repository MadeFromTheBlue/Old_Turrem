package net.turrem.client.network.server;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ServerPacketManager
{
	public ServerPacket readSinglePacket(InputStream stream) throws IOException
	{
		byte type = (byte) stream.read();
		int length = (stream.read() << 8) | (stream.read() << 0);
		byte[] packet = new byte[length];
		stream.read(packet);
		DataInput input = new DataInputStream(new ByteArrayInputStream(packet));
		return this.readPacket(type, length, input);
	}
	
	public ServerPacket readPacket(byte packetType, int length, DataInput data) throws IOException
	{
		switch (packetType & 0xFF)
		{
			case 0xFE:
				return new ServerPacketCustomNBT(data, length);
			case 0xFF:
				return new ServerPacketCustom(data, length);
			default:
				return null;
		}
	}
}