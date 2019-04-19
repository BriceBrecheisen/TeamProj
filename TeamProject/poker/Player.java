package poker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Player {
    private Card[] holeCards = new Card[2];
    private Move move;
    private long id;
    
    //constructor
    public Player(){
    	//Starting the move object for the player.
    	move = new Move();
    	id = 0;
    }
    
    public void setID(long id)
    {
    	this.id = id;
    }
    
    public long getID()
    {
    	return id;
    }
    
    public Move getMoves()
    {
    	return move;
    }

    public Player(Card card1, Card card2){
        holeCards[0] = card1;
        holeCards[1] = card2;
    }

    //methods
    public void setCard(Card card, int cardNum){
        holeCards[cardNum] = card;
    }

    public Card getCard(int cardNum){
        return holeCards[cardNum];
    }

    public int holeCardsSize(){
        return holeCards.length;
    }

    public void printPlayerCards(int playerNumber){
        System.out.println("Player " + (playerNumber+1) + " cards:");
        for (int i=0;i<2;i++){
            System.out.println(holeCards[i].printCard());
        }
        System.out.println("\n");
    }
    
    // The following methods will handle a player's moves
    public String getMove() 
    {
    	String move = "";
		String userInput = "";

		// get bet from player
		System.out.println("Select your action: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		try { 
			userInput = br.readLine(); 
		} catch (IOException ioe) { 
			System.out.println("Error: IO error trying to read input!"); 
			System.exit(1); 
		}
		
		return move;
    }
    
    public int bet()
    {
    	int bet = 0;
		String userInput = "";

		// Get number of players from user.
		System.out.println("Enter bet: $");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		try { 
			userInput = br.readLine(); 
		} catch (IOException ioe) { 
			System.out.println("Error: IO error trying to read input!"); 
			System.exit(1); 
		}

		// convert user input to an integer
		try {
			bet = Integer.parseInt(userInput);
		} catch (NumberFormatException nfe) {
			System.out.println("Error: Input provided is not a valid Integer!"); 
			System.exit(1); 
		}     

		return bet;
    }
    
    public boolean fold()
    {
    	return true;
    }
    
    
    
    
    

}