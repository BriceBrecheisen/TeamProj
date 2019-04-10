package poker;

import java.util.ArrayList;
import java.util.Collections;


/*This class builds a deck of Card, allows shuffling deck
 * also handles dealing the "community cards"*/
public class Dealer
{
	
	private ArrayList<Card> deck;
	private ArrayList<Card> community;
    //private Card[] burnCards = new Card[3];


	//Constructor
	public Dealer()
	{
		//for loops puts all cards into a "deck"
		//i = suits, j = card rank(king,queen, ace, etc.)
		community = new ArrayList<Card>();
		deck = new ArrayList<Card>();
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 13; j++)
				deck.add(new Card(j,i));
		Collections.shuffle(deck);
		
	}

	// Test just to see if it works properly
	public void printDeck(){
		for(int i = 0; i < deck.size();i++){
			System.out.println(i+1 + ": " + deck.get(i).printCard());
		}
		System.out.println("\n");
	}


	//return specified card from deck
	public Card getCard(int cardNum) 
	{
		return deck.get(cardNum);
	}

	//java util lets us shuffle our deck easily
	public void shuffle()
	{
		Collections.shuffle(deck);
		
	}
	
	//setter/getter for community cards
	public void setcommCard(Card card, int cardNum)
	{
        community.add(cardNum, card); 
    }

    public Card getcommCard(int cardNum)
    {
        return community.get(cardNum);
    }

    //returns size, kind of unnecessary atm but added in anyway
    public int commSize()
    {
        return community.size();
    }

    //method to print community cards, for now just prints to console
    public void printCommunityCards(){
        System.out.println("The community contains the following cards:");
        for(int i = 0; i < community.size() ;i++)
        {
            System.out.println(i+1 + ": " + getcommCard(i).printCard());
        }
        System.out.println("\n");
    }
	
	

}