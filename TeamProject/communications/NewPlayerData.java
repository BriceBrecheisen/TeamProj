package communications;

public class NewPlayerData {
	
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
