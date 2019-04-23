package communications;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import poker.*;
import GUIs.*;

public class ChatServer extends AbstractServer
{
	private JTextArea log;
	private JLabel status;
	private Database database;
	private Game game;
	private GameData data;
	private ArrayList<ConnectionToClient> clients;
	private ArrayList<ConnectionToClient> waitingclients;

	public ChatServer() throws IOException
	{
		//super(12345);
		super(8300);
		super.setTimeout(500);
		database = new Database();
		//game = new Game();
		//data = new GameData();
		clients = new ArrayList<ConnectionToClient>();
	}

	public String setClients(ConnectionToClient p)
	{
		//If this is the first player, just add the dude to the list of players in the game.
		/*if (clients.isEmpty())
			clients.add(p);

		//If the player doesnt exist, then add him to players as well.
		//If the player does exist, set the player at that index equal to the incoming player, since they are the same person,
		//but maybe with a different move.
		//Check players array	
		else
		{
			for (int i=0; i<clients.size(); i++)
			{
				if (p.getId() == clients.get(i).getId())
				{
					clients.set(i, p);
					return;
				}
			}

			//If the program gets here, it means it couldn't find the player already existing. In that case, just add player to the
			//arraylist of players.
		 * 
		 */
		//If clients doesn't contain the passed in client, and game is not being played,
		//the passed in client will be added.
		if ((!clients.contains(p))&&(game.isPlaying()==false))
		{
			clients.add(p);
			return "added";
		}

		//If the client is new, but game is being played, save client in waitlist
		else if ((!clients.contains(p))&&(game.isPlaying()==true))
		{
			waitingclients.add(p);
			return "wait";
		}

		//Else the client has already been added before, return playing.
		else if ((clients.contains(p))&&(game.isPlaying()==true))
		{
			return "playing";
		}

		else
			return "";
	}

	public ArrayList<ConnectionToClient> getClients()
	{
		return clients;
	}

	public ChatServer(int port)
	{
		super(port);
	}

	public void setDatabase(Database database)
	{
		this.database = database;
	}

	public void setGame(Game g)
	{
		this.game = g;
	}

	public void addWaitingClients()
	{
		clients.addAll(waitingclients);
		waitingclients = null;
	}

	public void setLog(JTextArea log)
	{
		this.log = log;
	}

	public JTextArea getLog()
	{
		return log;
	}

	public void setStatus(JLabel status)
	{
		this.status = status;
	}

	public JLabel getStatus()
	{
		return status;
	}



	@Override
	protected void handleMessageFromClient(Object arg0, ConnectionToClient arg1)
	{
		// TODO Auto-generated method stub
		//System.out.println("Message from Client" + arg0.toString() + arg1.toString());
		//log.append("Message from Client" + arg0.toString() + arg1.toString() + "\n");

		//New logindata message from client update:
		//If arg0 is of LoginData type, it means someone is trying to login. So just check for the users info in the file.
		//If the data exists in file, send "true" back to the server.
		//Else, send back false.
		if (arg0 instanceof LoginData)
		{
			LoginData loginData = (LoginData)arg0;
			int id = loginData.getID();

			if(database.checkUser(new User(loginData.getUsername(),loginData.getPassword())))
			{
				try {
					arg1.sendToClient("truth");
					log.append(loginData.getUsername() + " is logged in!");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
			else
			{
				try {
					arg1.sendToClient("falsehood");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}

		}

		//Else if arg0 is of the CreateAccountData type, it means someone is trying to create an account. So just check for the users username in the file.
		//If it doesn't exist, only then make a new entry to the file.
		//Else, send back "exists" to let client know that the username already exists and they must try again.
		else if (arg0 instanceof CreateAccountData)
		{
			CreateAccountData createAccData = (CreateAccountData)arg0;
			int id = createAccData.getID();

			if(database.checkName(new User(createAccData.getUsername(),createAccData.getPassword())))
			{
				try {
					arg1.sendToClient("exists");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}

			else //If checkuser returns false.
			{
				User newdude=new User(createAccData.getUsername(),createAccData.getPassword());
				newdude.setID(id);
				database.createUser(newdude);

				try {
					arg1.sendToClient("done");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		}

		//If a player makes a move/joins.
		else if (arg0 instanceof Player)
		{
			//First check if the players exceeded our limit
			if ((clients.size() + waitingclients.size())<=5)
			{
				if (game.isPlaying())
				{
					//If the client has already been added before, and the game is being played,
					//then they are just responding with their moves. 
					//Send their movies to all the other players.
					if (setClients(arg1).equals("playing"))
					{
						//Check if its their turn.
						if (game.checkTurn() == ((Player)arg0).getSeat())
						{
							//If it is their turn, then set their new moves into game.
							game.setPlayers((Player)arg0);
							
							//Update Server GUI
							log.append("Player: "+arg1.getId()+" moved: " +((Player)arg0).getMoves().getMove());
							
							//Deal with the player's moves
							game.makinMoves();
							
							//Check if the player made a bet
							//If they did make a bet, set the GameData's bet equal to it, to let all the players know.
							//Then set the player's bet equal to 0.
							if (((Player)arg0).getMoves().getbet()>0)
							{
								data.betsetter(((Player)arg0).getMoves().getbet());
								data.bettingPlayerSetter(((Player)arg0).getSeat());
								
								//Set the game's bet amount
								game.setBet(((Player)arg0).getMoves().getbet());
							}

							//Let all the players know of the player's move.
							letAllKnow();

							//Rotate round
							game.rotateTurn();
						}
					}

					else if (setClients(arg1).equals("wait"))
					{
						//Add the player data to the player waitlist.
						game.setPlayers((Player)arg0);
						//Update Server GUI
						log.append("Player: "+arg1.getId()+" is waiting!");

						//Tell client to wait.
						try {
							arg1.sendToClient("wait");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}}

				//Else if the game is not being played, add the player and client. If they are new,
				//they will get added to the list of clients/players.
				else if (!game.isPlaying())
				{
					//If they have been added successfully, they will be sent their id and seat number.
					if(setClients(arg1).equals("Added"))
					{
						Player temp = (Player)arg0;
						temp.setSeat(clients.size());
						//Add Player data to the game.
						game.setPlayers(temp);
						
						//Update Server GUI
						log.append("Player: "+arg1.getId()+" got added to game!");

						//Send id and seat number.
						try {
							arg1.sendToClient(new NewPlayerData(arg1.getId(),clients.size()));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

			}

			else {
				//Else let the player know, they can't join
				try {
					arg1.sendToClient("Full!");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public void letAllKnow()
	{	
		//Let everyone know of only the moves of all the players who have moved.
		//First create the game data object, so that it can be sent to all players.
		data.playersSetter(game.getAllPlayers());

		//Send player move info to all players.
		for (int i=0; i<clients.size(); i++)
		{
			try {
				clients.get(i).sendToClient(data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Could not send data to player!");
			}
		}
		
		//After sending move, clear turn player's move, so that the new one can then come in.

		//Clear out game data to be reused for next round of moves by players.
		resetGameData();

	}

	public void setWinnerInfo(String w)
	{
		data.winnerSetter(w);

		//Send winner info to all players.
		for (int i=0; i<clients.size(); i++)
		{
			try {
				clients.get(i).sendToClient(data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Could not send data to player!");
			}
		}

		//Reset Gamedata object
		resetGameData();
	}

	public void resetGameData()
	{
		data.playersSetter(null);
		data.betsetter(0);
		data.winnerSetter("");
	}

	public void listeningException(Throwable exception)
	{
		log.append("\n"+"Listening Exception: "+exception.getMessage());
		log.append("\nPress Listen again to restart server my friend!");

		//Changing the status label
		status.setText("Exception occurred when listening!");
		status.setForeground(Color.RED);
	}

	public void serverStarted()
	{	
		//Changing the status label
		status.setText("Listening!");
		status.setForeground(Color.GREEN);

		//Changing the server log
		log.append("\nServer Started my friend!");
	}

	public void serverStopped()
	{
		//Changing the status label
		status.setText("Stopped!");
		status.setForeground(Color.RED);

		//Changing the server log
		log.append("\nServer Stopped Accepting New Clients - Press Listen to Start Accepting New Clients");
	}

	public void serverClosed()
	{
		//Changing the status label
		status.setText("Close!");
		status.setForeground(Color.RED);

		//Changing the server log
		log.append("\nServer and all current clients are closed - Press Listen to Restart");
	}

	protected void clientConnected(ConnectionToClient client) 
	{
		log.append("Player"+client.getId()+" connected!");
	}
}
