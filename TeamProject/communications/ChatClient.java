package communications;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;


import GUIs.MainPanel;
import ocsf.client.AbstractClient;
import poker.*;

public class ChatClient extends AbstractClient
{
	public GameData data;
	public Player player;
	public MainPanel mainpanel;
	public String username;
	public ArrayList<Player> players;

	public ChatClient()
	{
		super("localhost",8300);
		data = new GameData();
		player = new Player();
		players = new ArrayList<Player>();
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public void setUsername(String s)
	{
		username = s;
	}

	public ChatClient(String ip, int port)
	{
		super(ip,port);
		data = new GameData();
		player = new Player();
		players = new ArrayList<Player>();
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
				//If login worked, then take user to main-panel.
				mainpanel.homePanel(username);
			}

			//If server returns falsehood, it means that the user doesnt exist.
			else if (((String)arg0).equals("falsehood"))
			{
				JOptionPane.showMessageDialog(null,"Error, your credentials do not exist!");
				mainpanel.loginPanel();
			}

			//If server returns exists, it means that a user has been trying to make a username/pass combo, and it already exists.
			else if (((String)arg0).equals("exists"))
			{
				JOptionPane.showMessageDialog(null,"Error, this username exists my friend!");
				//Take user back to create account panel.
				mainpanel.newuserPanel();
			}

			//If server returns, it means that a new username and password was successfully made.
			else if (((String)arg0).equals("done"))
			{
				JOptionPane.showMessageDialog(null,"Success, new user account made! Go ahead and log in my friend!");
				//Take user back to login panel.
				mainpanel.loginPanel();
			}

			//If the server returns waiting, then the player has to wait to start playing.
			else if (((String)arg0).equals("wait"))
			{
				//mainpanel.waitPanel();
				//When they are waiting for round to end.
			}

			else if (((String)arg0).equals("go"))
			{
				mainpanel.goPanel();
			}

			else if (((String)arg0).equals("Full!"))
			{
				//MainPanel.fullPanel();
				//Game is full.
			}
			else if (((String)arg0).equals("Freeze!"))
			{
				mainpanel.freezePanel();
				//When its not their turn just yet.
			}
			
			//If they are the first player, and they have to wait for others to show up.
			else if (((String)arg0).equals("alone"))
			{
				//mainpanel.alonePanel();
				//When its not their turn just yet.
			}
			
			else if (((String)arg0).equals("Ready"))
			{
				//Reset their ready buttons.
				mainpanel.setReadyVisible();
			}
			
			else if (((String)arg0).contains("Winner info:"))
			{
				//Display winner info on panel
				//mainpanel.setReadyVisible();
			}
			
			
		}

		//If the server sends a GameData object

		//If the server sends a NewPlayerData object
		else if (arg0 instanceof NewPlayerData)
		{
			NewPlayerData temp = (NewPlayerData)arg0;
			//Set the players seat number and id number
			player.setID(temp.getId());
			player.setSeat(temp.getSeat());
			//players.add(player);
			mainpanel.setplay(player);
			//data.playersSetter(players);
			players = temp.players;
			mainpanel.players = temp.players;
			
			try {
				mainpanel.gamePanel();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//Dummy comment
		
		//If the server sends card information
		else if (arg0 instanceof Cards)
		{
			Cards cards = (Cards)arg0;
			
			//If the server sent two cards, its this player's hole cards.
			if (cards.getCards().size()==2)
			{
				//Set players cards
				player.setHoleCards(cards.getCards());
				mainpanel.displayCards();
			}
			//If the server sent three cards, its the dealer's first three community cards
			else if (cards.getCards().size()==3)
			{
				//Set Dealers community cards. Remove ; above.
				mainpanel.cardsSetter(cards);
				mainpanel.displayCards();
			}
			
			//If the server sent one card, its the dealer's community cards after first three.
			else if (cards.getCards().size()==1)
			{
				//Set Dealers community cards. Remove ; above.
				mainpanel.cardsSetter(cards);
				mainpanel.displayCards();
			}
		}
		
		else if (arg0 instanceof GameData)
		{
			//Set data to game data object.
			data = ((GameData)arg0);
			
			if(data.returnBet()==99999)
			{
				//Get player data.
				//Now update the client GUI
				//players = data.playerGetter();
				players = data.playerGetter();
				mainpanel.players = data.playerGetter();
				mainpanel.setVis();
			}
			
			else
			{
				//Actual moves are coming in.
				players = data.playerGetter();
				mainpanel.update();
			}
			
		
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
