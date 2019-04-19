package poker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Game 
{
	// variables
	Dealer deck = new Dealer();
	Game game = new Game();
	int numPlayers = 0;
	int cardCounter = 0;
	int commCounter = 0;
	int pot = 0;
	boolean fold = false;
	ArrayList<Player> players;
	
	public Game()
	{
		// initializations 
		//numPlayers = game.getNumberOfPlayers();
		players = new ArrayList<Player>();

	}
	
	public void setPlayers(Player p)
	{
		//If this is the first player, just add the dude to the list of players in the game.
		if (players.isEmpty())
			players.add(p);

		//If the player doesnt exist, then add him to players as well.
		//If the player does exist, set the player at that index equal to the incoming player, since they are the same person,
		//but maybe with a different move.
		//Check players array	
		else
		{
			for (int i=0; i<players.size(); i++)
			{
				if (p.getID() == players.get(i).getID())
				{
					players.set(i, p);
					return;
				}
			}
			
			//If the program gets here, it means it couldn't find the player already existing. In that case, just add player to the
			//arraylist of players.
			players.add(p);
		}
	}
	
	public Player getPlayer(long a)
	{
		//Return the player who's id is being passed in.
		for (int i=0; i<players.size(); i++)
		{
			if (a == players.get(i).getID())
			{
				return players.get(i);
			}
		}
		
		return null;
	}
	
	public ArrayList<Player> getAllPlayers()
	{
		return players;
	}
	
	
	
	public int numberOfPlayers()
	{
		return players.size();
	}
	
	public void dealCardsToPlayers()
	{
		//this gives our players cards, 2 to a player
				for (int i=0;i<2;i++){
					for (int j=0;j<players.size();j++){
						players.get(j).setCard(deck.getCard(cardCounter++), i);
					}
				}
	}

	public static void main(String[] args) throws Exception 
	{
		/* variables
		Dealer deck = new Dealer();
		Game game = new Game();
		int numPlayers = 0;
		int cardCounter = 0;
		int commCounter = 0;
		int pot = 0;
		boolean fold = false;*/

		     
		

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


	/*public int getNumberOfPlayers() throws Exception 
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
	}*/
}



