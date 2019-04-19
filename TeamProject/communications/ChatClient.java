package communications;

import ocsf.client.AbstractClient;
import poker.*;

public class ChatClient extends AbstractClient
{
	public GameData data;
	public Player player;
	//public MainPanel login;

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
		}
		
		//If the server sends a GameData object

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
