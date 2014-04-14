package net.turrem.server.entity.unit;

import net.turrem.server.Realm;
import net.turrem.server.entity.IHolding;
import net.turrem.server.entity.SoftEntity;

public abstract class EntityUnit extends SoftEntity implements IHolding
{
	protected Realm allegiance = null;
	
	public abstract float veiwDistance();
	
	public Realm getAllegiance()
	{
		return this.allegiance;
	}
	
	public Realm setAllegiance(Realm newrealm)
	{
		Realm oldrealm = this.allegiance;
		if (oldrealm != null)
		{
			oldrealm.leaveRealm(this, "CHANGED");
		}
		this.allegiance = newrealm;
		if (newrealm != null)
		{
			newrealm.joinRealm(this);
		}
		return oldrealm;
	}
}
