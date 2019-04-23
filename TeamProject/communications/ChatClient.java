package communications;

import GUIs.MainPanel;
import ocsf.client.AbstractClient;
import poker.*;

public class ChatClient extends AbstractClient
{
	public GameData data;
	public Player player;
	public MainPanel mainpanel;

	public ChatClient()
	{
		super("localhost",8300);
		data = new GameData();
		player = new Player();
	}

	public ChatClient(String ip, int port)
	{
		super(ip,port);
		data = new GameData();
		player = new Player();
	}

	public void setMainPanel(MainPanel m)
	{
		mainpanel = m;
	}

	@Override
	public void handleMessageFromServer(Object arg0)
	{
		//System.out.println("Server Message sent to Client " + (String)arg0);

		//If server returns truth, it means that the user trying to login does indeed exist.
		//They can now login.
		if (arg0 instanceof String)
		{
			if (((String)arg0).equals("truth"))
			{
				//logincontrol.loginSuccess();
			}

			//If server returns falsehood, it means that the user doesnt exist.
			else if (((String)arg0).equals("falsehood"))
			{
				//logincontrol.displayError("Incorrect username/password combination!");
			}

			//If server returns exists, it means that a user has been trying to make a username/pass combo, and it already exists.
			else if (((String)arg0).equals("exists"))
			{
				//createcontrol.displayError("Username selected already exists my friend!");
			}

			//If server returns, it means that a new username and password was successfully made.
			else if (((String)arg0).equals("done"))
			{
				// createcontrol.createAccountSuccess();
			}

			//If the server returns waiting, then the player has to wait to start playing.
			else if (((String)arg0).equals("wait"))
			{
				//mainpanel.waitPanel();
				//When they are waiting for round to end.
			}

			else if (((String)arg0).equals("go"))
			{
				//mainpanel.getgamePanel();
			}

			else if (((String)arg0).equals("Full!"))
			{
				//MainPanel.fullPanel();
				//Game is full.
			}
			else if (((String)arg0).equals("Freeze!"))
			{
				//mainpanel.freezePanel();
				//When its not their turn just yet.
			}
			
			//If they are the first player, and they have to wait for others to show up.
			else if (((String)arg0).equals("alone"))
			{
				//mainpanel.alonePanel();
				//When its not their turn just yet.
			}
		}

		//If the server sends a GameData object

		//If the server sends a NewPlayerData object
		if (arg0 instanceof NewPlayerData)
		{
			NewPlayerData temp = (NewPlayerData)arg0;
			//Set the players seat number and id number
			player.setID(temp.getId());
			player.setSeat(temp.getSeat());

		}

	}

	public void connectionException (Throwable exception) 
	{
		//Add your code here
	}
	public void connectionEstablished()
	{
		//Add your code here
	}



}
