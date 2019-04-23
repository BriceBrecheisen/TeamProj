package communications;

import java.io.Serializable;
import java.util.ArrayList;

import poker.Player;

public class NewPlayerData implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private int seatno;
	public ArrayList<Player> players;
	
	public NewPlayerData(long i, int s)
	{
		id = i;
		seatno = s;
		players = new ArrayList<Player>();
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
