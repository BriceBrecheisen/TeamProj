package communications;

import java.io.Serializable;
import java.util.ArrayList;

import poker.Card;

public class Cards implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Card> cards;
	
	public Cards()
	{
		cards = new ArrayList<Card>();
	}
	
	
	public Cards(ArrayList<Card> c)
	{
		cards = c;
	}
	
	public ArrayList<Card> getCards()
	{
		return cards;
	}
	
	public void setCards(ArrayList<Card> c)
	{
		cards = c;
	}
	
	public void addCard(Card c)
	{
		cards.add(c);
	}

}
