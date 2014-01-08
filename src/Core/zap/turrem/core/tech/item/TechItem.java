package zap.turrem.core.tech.item;

import zap.turrem.core.tech.list.TechList;

public abstract class TechItem
{
	private boolean pushed = false;

	public abstract String getIdentifier();

	public abstract String getName();
	
	public abstract void loadBranches();
	
	protected void onPush()
	{
		
	}
	
	public final void push()
	{
		TechList.addTech(this);
		this.onPush();
		this.pushed = true;
	}

	public final int getId()
	{
		if (this.pushed)
		{
			return TechList.getIndex(this);
		}
		else
		{
			return -1;
		}
	}
}