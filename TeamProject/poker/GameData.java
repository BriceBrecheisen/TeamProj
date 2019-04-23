package poker;

import java.util.ArrayList;

public class GameData {
	
	//Data fields.
	private ArrayList<Player> players;
	private int bet;
	private int bettingplayer;
	private String winnerinfo;
	
	public GameData()
	{
		//Starting all the data fields.
		players=new ArrayList<Player>();
		bet=0;
		bettingplayer=-1;
		winnerinfo = "";
	}
	
	public void playersSetter(ArrayList<Player> p)
	{
		players = p;
	}
	
	public void winnerSetter(String w)
	{
		winnerinfo = w;
	}
	
	public void betsetter(int b)
	{
		bet = b;
	}
	
	public ArrayList<Player> playerGetter()
	{
		return players;
	}
	
	public String winnerGetter()
	{
		return winnerinfo;
	}
	
	public int returnBet()
	{
		return bet;
	}
	
	public int bettingPlayerGetter()
	{
		return bettingplayer;
	}
	
	public void bettingPlayerSetter(int b)
	{
		bettingplayer = b;
	}
	
}
