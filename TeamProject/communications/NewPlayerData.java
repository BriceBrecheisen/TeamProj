package communications;

import java.io.Serializable;

public class NewPlayerData implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private int seatno;
	
	public NewPlayerData(long i, int s)
	{
		id = i;
		seatno = s;
	}
	
	public long getId()
	{
		return id;
	}
	
	public int getSeat()
	{
		return seatno;
	}

}
