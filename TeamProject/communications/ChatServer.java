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

	public ChatServer() throws IOException
	{
		//super(12345);
		super(8300);
		super.setTimeout(500);
		database = new Database();
		game = new Game();
		data = new GameData();
		clients = new ArrayList<ConnectionToClient>();
	}
	
	public void setClients(ConnectionToClient p)
	{
		//If this is the first player, just add the dude to the list of players in the game.
		if (clients.isEmpty())
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
			clients.add(p);
		}
	}

	public ChatServer(int port)
	{
		super(port);
	}

	void setDatabase(Database database)
	{
		this.database = database;
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
			//Set this client in the clients arraylist
			setClients(arg1);
			//Set this new player in the game object.
			game.setPlayers((Player)arg0);
			//Check if the player made a bet
			//If they did make a bet, set the GameData's bet equal to it, to let all the players know.
			//Then set the player's bet equal to 0.
			if (((Player)arg0).getMoves().getbet()>0)
			{
				data.betsetter(((Player)arg0).getMoves().getbet());
				game.getPlayer(arg1.getId()).getMoves().setbet(0);
			}
			
			//Let all the players know of the player's move.
			letAllKnow();
		}

	}
	
	public void letAllKnow()
	{	
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
