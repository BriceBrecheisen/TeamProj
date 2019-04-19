package poker;

public class Move {
	
	private int bet;
	private boolean check,call,fold,yesbet;
	
	public Move()
	{
		bet=0;
		check = false;
		call = false;
		fold = false;
		yesbet = false;
	}

	public void call()
	{
		call = true;
	}
	
	public void fold()
	{
		fold = true;
	}
	
	public void check()
	{
		check = true;
	}
	
	public void bet()
	{
		yesbet = true;
	}
	
	public int getbet()
	{
		return bet;
	}
	
	public void setbet(int bet)
	{
		this.bet = bet;
	}
	
	public String getMove()
	{
		if (yesbet)
			return "bet";
		else if (check)
			return "check";
		else if (call)
			return "call";
		else
			return "fold";
	}
	
	
	
	
}
