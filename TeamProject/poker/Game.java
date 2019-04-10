package poker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Game 
{
	public Game()
	{
		
	}
	public int getNumberOfPlayers() throws Exception 
	{
		int intPlayers = 0;
		String userInput = "";

		// Get number of players from user.
		System.out.println("Enter number of players (1-5):");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		try { 
			userInput = br.readLine(); 
		} catch (IOException ioe) { 
			System.out.println("Error: IO error trying to read input!"); 
			System.exit(1); 
		}

		// convert user input to an integer
		try {
			intPlayers = Integer.parseInt(userInput);
		} catch (NumberFormatException nfe) {
			System.out.println("Error: Input provided is not a valid Integer!"); 
			System.exit(1); 
		}

		if ((intPlayers<1) || (intPlayers>9)){
			throw new Exception("Error: Number of players must be an integer between 1 and 9");
		}       

		return intPlayers;
	}

	public static void main(String[] args) throws Exception 
	{
		// variables
		Dealer deck = new Dealer();
		Game game = new Game();
		int numPlayers = 0;
		int cardCounter = 0;
		int commCounter = 0;
		int pot = 0;
		boolean fold = false;

		// initializations      
		numPlayers = game.getNumberOfPlayers();
		ArrayList<Player> players = new ArrayList<Player>();


		// Initialize players
		for (int i=0;i<numPlayers;i++){
			players.add(new Player());
		}

		//this gives our players cards, 2 to a player
		for (int i=0;i<2;i++){
			for (int j=0;j<numPlayers;j++){
				players.get(j).setCard(deck.getCard(cardCounter++), i);
			}
		}
	

		// first round,tried to do moves but not working properly
//		for (int i = 0; i < numPlayers; i++)
//		{
//
//			String move = players.get(i).getMove();
//			if (move.equals("bet"))
//			{
//				int bet = players.get(i).bet();
//				pot+=bet;
//			}
//			else if (move.equals("fold"))
//				fold = players.get(i).fold();
//		}


		// deal flop
		for (int i=0; i<3;i++){
			deck.setcommCard(deck.getCard(cardCounter++), commCounter++);
		}

		// second round

		// deal turn
		deck.setcommCard(deck.getCard(cardCounter++), commCounter++);

		// third round

		// deal river
		deck.setcommCard(deck.getCard(cardCounter++), commCounter++);
		
		//fourth round

		//------------------------
		// end dealing board
		//------------------------

		System.out.println("The hand is complete...\n");

		// print deck
		//deck.printDeck();

		//print board
		deck.printCommunityCards();

		// print player cards
		System.out.println("Player's cards:\n");
		for (int i=0;i<numPlayers;i++){
			players.get(i).printPlayerCards(i);
		}
		
		//This compares each player's hands
		for (int i=0;i<numPlayers;i++){
			Hand handToEval = new Hand();

			// populate with player cards           
			for (int j = 0; j < players.get(i).holeCardsSize(); j++)
			{
				handToEval.addCard(players.get(i).getCard(j),j);
			}

			//populate with board cards
			for (int j=players.get(i).holeCardsSize();j<(players.get(i).holeCardsSize()+deck.commSize());j++)
			{
				handToEval.addCard(deck.getcommCard(j-players.get(i).holeCardsSize()),j);
			}

			System.out.println("Player " + (i+1) + " hand value: " + handToEval.evaluateHand());    
		}
	}


	
}



