package poker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import communications.Cards;
import communications.ChatClient;
import communications.ChatServer;

public class Game 
{
	// variables
	private Dealer deck = new Dealer();
	private Game game = new Game();
	//private int numPlayers = 0;
	private int cardCounter = 0;
	private int commCounter = 0;
	private int pot = 0;
	private int turn = 0;
	private int round = 1;
	private int bet = 0;
	//private boolean fold = false;
	private boolean isplaying = false;
	
	ArrayList<Player> players;
	ArrayList<Player> waitingplayers;
	
	ChatServer server;
	
	public Game()
	{
		// initializations 
		//numPlayers = game.getNumberOfPlayers();
		players = new ArrayList<Player>();
		waitingplayers = new ArrayList<Player>();
	}
	
	public int getBet()
	{
		return bet;
	}
	
	public void setBet(int a)
	{
		bet = a;
	}
	
	public int getTurn()
	{
		return turn;
	}
	
	public boolean isPlaying()
	{
		return isplaying;
	}
	
	public void setPlayers(Player p)
	{
		//Only add the player while the game isn't already being played.
		if (!isplaying)
		{
			//If this is the first player, just add the dude to the list of players in the game.
			if (players.isEmpty())
			{
				//Add player to game, but let them know that they have to wait for someone else to join.
				players.add(p);
				try {
					server.getClients().get(0).sendToClient("alone");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
			}
			
			//If this is the second player, then we can start the game.
			else if (players.size()==1)
			{
				players.add(p);
				
				//Give turn to the first player, start game.
				startRound();
				return;
			}

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
			}}
		
		//If game is ongoing, add the players to the waitlist if they are new, else update their moves.
		else if (isplaying)
		{
			//Update moves if they exist.
			for (int i=0; i<players.size(); i++)
			{
				if (p.getID() == players.get(i).getID())
				{
					players.set(i, p);
					return;
				}
			}

			//Else, add them to waitlist and return.
			waitingplayers.add(p);
			return;
		}
		
		/*if ((!players.contains(p))&&(game.isPlaying()==false))
		{
			players.add(p);
			return true;
		}
		//If the player is new, but game is being played, save client in waitlist
		else if ((!players.contains(p))&&(game.isPlaying()==true))
		{
			waitingplayers.add(p);
			return false;
		}
		
		else if ((players.contains(p))&&(game.isPlaying()==true))
		{
			return true;
		}
		
		//Else the player has already been added before, return false.
		return false;*/
	}
	
	public void rotateTurn()
	{
		turn++;
		
		//If we are out of players, end round.
		if (turn>players.size())
		{
			endTurn();
			//Reset turn
			turn = 1;
			return;
		}
		
		//Else lets players know if they can play
		//Freeze all the other players who's turn it isn't.
		for (int i=0; i<players.size();i++)
		{
			if (players.get(i).getSeat() != turn)
			{
				try {
					server.getClients().get(i).sendToClient("Freeze!");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			else
			{
				//Let the player who's turn it is to make a move.
				try {
					server.getClients().get(i).sendToClient("go");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		
	}
	
	public void endTurn()
	{
		//When all the turns have ended, increment round.
		round++;
		
		//Start the next round.
		startRound();
	}
	
	public void startRound()
	{
		//Deal cards to each player if its the second round.
		if (round==1)
		{
			dealCardsToPlayers();
			startTurn();
		}

		//If its the second round, then deal the three comm cards.
		else if (round==2)
		{
			dealThreeCommCards();
			startTurn();
		}
		
		//If its the third round
		else if (round==3)
		{
			dealOneCommCard();
			startTurn();
		}
		
		//If its the third round
		else if (round==4)
		{
			dealOneCommCard();
			startTurn();
		}

		else if (round>4)
		{
			endRound();
		}
	}
	
	public void startTurn()
	{
		//Give turn to the first player.
		turn=1;
		//Set isplaying to true.
		isplaying = true;
		
		//Tell players if it's their turn or not.
		for (int i=0; i<players.size();i++)
		{
			if (players.get(i).getSeat() != turn)
			{
				try {
					server.getClients().get(i).sendToClient("Freeze!");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			else
			{
				try {
					server.getClients().get(i).sendToClient("go");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}
	
	public int checkTurn()
	{
		return turn;
	}
	
	public void setServer(ChatServer s)
	{
		this.server = s;
	}
	
	public void endRound()
	{
		//When the round ends, set isplaying to false.
		isplaying = false;
		
		//Add the waiting players to the players list, and clear waitlist.
		players.addAll(waitingplayers);
		server.addWaitingClients();
		
		waitingplayers = null;
		
		//Evaluate everyone's hands.
		//Let all players know who the winner is.
		displayWinner(evaluateHands());
		
		//Wait for 5 seconds.
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Reset round count
		round = 1;
		
		//Restart game
		startRound();
	}
	
	public void displayWinner(String a)
	{
		//Add all money from pot to this player's chips.
		
		//Send winning info to all clients.
		String b = "Winner info:";
		a = b+a;
		
		for (int k=0;k<players.size();k++)
		{
			try {
				server.getClients().get(k).sendToClient(a);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String evaluateHands()
	{
		String winninginfo = "";
	
		for (int i=0;i<players.size();i++)
		{
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

			winninginfo+="Player " + (i+1) + " hand value: " + handToEval.evaluateHand() + "\n";  
		}
		
		return winninginfo;
	}

	public void chipsUpdate()
	{
		//Check if anyone bet
		for (int i=0; i<players.size();i++)
		{
			if (players.get(i).getMoves().getbet()>0)
			{
				//Substract the bet from all players chips
				//Add these substractions to the pot.
				for (int j=0; j<players.size();j++)
				{
					players.get(j).setChips(players.get(j).getChips()-players.get(i).getMoves().getbet());
					pot += players.get(i).getMoves().getbet();
				}
				//Set the better's bet back to 0.
				players.get(i).getMoves().setbet(0);
			}
		}
		
	}
	
	//This function will handle the moves of every player who's turn it is.
	public void makinMoves()
	{
		Player dude = players.get(turn-1);
		//If the player bet
		if (dude.getMoves().getbet()>0)
		//Update everyone's chips.
			game.chipsUpdate();
		
		//If the player call-ed
		else if (dude.getMoves().getMove()=="call")
			//Update everyone's chips.
			game.chipsUpdate();
		
		//If the player folded
		else if (dude.getMoves().getMove()=="fold")
			//Remove player.
			players.remove(dude);
		
		//If the player call-ed
		//else if (dude.getMoves().getMove()=="call";
		//Do nothing, because the server will rotate round anyways
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
		//Return only the players who have made a move.
		ArrayList<Player> returnval=new ArrayList<Player>();
		returnval.addAll(players.subList(0, turn-1));
		return returnval;
	}
	
	
	
	public int numberOfPlayers()
	{
		return players.size();
	}
	
	public void dealCardsToPlayers()
	{
		//this gives our players cards, 2 to a player
		//Make an arrayList of cards to be sent to each player.
		ArrayList<Cards> cards = new ArrayList<Cards>();
		
		//Initialize the 2d array of cards.
		for (int a=0; a<players.size(); a++)
			cards.add(new Cards());
			
		
		//Initialize cards 
		//Save two cards for each player in the array list of cards.
		for (int i=0;i<2;i++)
		{
			for (int j=0;j<players.size();j++)
			{
				//players.get(j).setCard(deck.getCard(cardCounter++), i);
				cards.get(j).addCard(deck.getCard(cardCounter++));
			}
		}
		
		//Sending each player their cards.
		for (int k=0;k<players.size();k++)
		{
			try {
				server.getClients().get(k).sendToClient(cards.get(k));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void dealThreeCommCards()
	{
		Cards sender = new Cards();
		for (int i=0; i<3;i++){
			Card a = deck.getCard(cardCounter++);
			
			//Set the deck's community cards.
			deck.setcommCard(a, commCounter++);
			sender.addCard(a);
		}
		
		//Send the dealer's community cards to all players.
		for (int k=0;k<players.size();k++)
		{
			try {
				server.getClients().get(k).sendToClient(sender);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void dealOneCommCard()
	{
		Cards sender = new Cards();

		Card a = deck.getCard(cardCounter++);

		//Set the deck's community cards.
		deck.setcommCard(a, commCounter++);
		sender.addCard(a);


		//Send the dealer's community cards to all players.
		for (int k=0;k<players.size();k++)
		{
			try {
				server.getClients().get(k).sendToClient(sender);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}

	//public static void main(String[] args) throws Exception 
	//{
		/* variables
		Dealer deck = new Dealer();
		Game game = new Game();
		int numPlayers = 0;
		int cardCounter = 0;
		int commCounter = 0;
		int pot = 0;
		boolean fold = false;*/

		     
		

		// Initialize players
		/*for (int i=0;i<numPlayers;i++){
			players.add(new Player());
		}*/

		//this gives our players cards, 2 to a player
		/*for (int i=0;i<2;i++){
			for (int j=0;j<numPlayers;j++){
				players.get(j).setCard(deck.getCard(cardCounter++), i);
			}
		}*/
	

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
		/*for (int i=0; i<3;i++){
			deck.setcommCard(deck.getCard(cardCounter++), commCounter++);
		}

		// second round

		// deal turn
		deck.setcommCard(deck.getCard(cardCounter++), commCounter++);

		// third round

		// deal river
		deck.setcommCard(deck.getCard(cardCounter++), commCounter++);*/
		
		//fourth round

		//------------------------
		// end dealing board
		//------------------------

		//System.out.println("The hand is complete...\n");

		// print deck
		//deck.printDeck();

		//print board
		//deck.printCommunityCards();

		// print player cards
		/*System.out.println("Player's cards:\n");
		for (int i=0;i<numPlayers;i++){
			players.get(i).printPlayerCards(i);
		}*/
		
		//This compares each player's hands
		/*for (int i=0;i<numPlayers;i++){
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
<<<<<<< HEAD
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
=======
	}*/
//}
>>>>>>> branch 'master' of https://github.com/BriceBrecheisen/TeamProj.git



