//This is the mainpanel. Horribly coded.

package GUIs;

import communications.*;
import poker.*;
import java.awt.BorderLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Font;
import java.awt.Image;

public class MainPanel extends JPanel {
	
	private JPanel loginpanel;
	private JTextField usertext;
	private JPasswordField password1;
	private JLabel loginuserlabel;
	private JLabel loginuserpass;
	private JLabel loginmainlabel;
	private JLabel port;
	private JLabel IP;
	private JTextField port1;
	private JTextField IP1;
	private JButton loginnewuser;
	private JButton LoginButton;
	private String user;
	
	private JPanel newuser;
	private JLabel newuserlabel;
	private JLabel newuserpass;
	private JLabel newuserpass2;
	private JLabel newusermain;
	private JTextField newusertext;
	private JPasswordField password2;
	private JPasswordField password3;
	private JButton newuserlogin;
	private JButton previous;
	
	private JPanel home;
	private JLabel welcome;
	private JButton jointable;
	private JButton logout;
	private JCheckBox stats;
	private JLabel wins;
	private JLabel loss;
	
	private JPanel gamegui;
	private JLabel dealer;
	private JLabel player1;
	private JLabel player2;
	private JLabel player3;
	private JLabel player4;
	private JLabel player5;
	private JLabel dealer1;
	private JLabel play1;
	private JLabel play2;
	private JLabel play3;
	private JLabel play4;
	private JLabel play5;
	private JLabel dealdeck;
	private JLabel commdeck1;
	private JLabel commdeck2;
	private JLabel commdeck3;
	private JLabel commdeck4;
	private JLabel commdeck5;
	private JLabel play1deck1;
	private JLabel play1deck2;
	private JLabel play2deck1;
	private JLabel play2deck2;
	private JLabel play3deck1;
	private JLabel play3deck2;
	private JLabel play4deck1;
	private JLabel play4deck2;
	private JLabel play5deck1;
	private JLabel play5deck2;
	
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private int deltaY = 0;
	private JSlider slider;
	private JButton ready;
	private JButton moveit;
	
	private JPanel waiting;
	private JLabel waitplease;
	
	private ChatClient client;
	private Player player;
	private Cards cards;
	
	private ArrayList<Card> card;
	
	private ArrayList<String> turd;
	
	private MainPanel self;
	
	public MainPanel() {
		setLayout(null);
		
		user = new String();
		
		
		loginpanel = new JPanel();
		loginpanel.setBackground(Color.BLACK);
		loginpanel.setBounds(0,0,650,220);
		add(loginpanel);
		loginpanel.setLayout(null);
		loginpanel.setVisible(false);
		
		newuser = new JPanel();
		newuser.setBackground(Color.BLACK);
		newuser.setBounds(0,0,650,330);
		add(newuser);
		newuser.setLayout(null);
		newuser.setVisible(false);
		
		home = new JPanel();
		home.setBounds(0,0,1200,800);
		home.setBackground(Color.RED);
		add(home);
		home.setLayout(null);
		home.setVisible(false);
		
		waiting = new JPanel();
		waiting.setVisible(false);
		waiting.setBounds(0,0,1200,800);
		waiting.setBackground(Color.BLACK);
		add(waiting);
		waiting.setLayout(null);
		
		gamegui = new JPanel();
		gamegui.setVisible(false);
		gamegui.setBounds(0,0,1200,800);
		gamegui.setBackground(Color.RED);
		add(gamegui);
		gamegui.setLayout(null);
		
		self = this;
		
		loginPanel();
	}
	
	public void loginPanel() {
		loginpanel.setVisible(true);
		
		loginuserlabel = new JLabel("Username: ");
		loginuserlabel.setForeground(Color.GREEN);
		loginuserlabel.setBounds(10, 55, 150, 55);
		loginpanel.add(loginuserlabel);
		
		loginuserpass = new JLabel("Password: ");
		loginuserpass.setBounds(10, 115, 150, 55);
		loginuserpass.setForeground(Color.GREEN);
		loginpanel.add(loginuserpass);
		
		port = new JLabel("Port: ");
		port.setBounds(330,55,150,55);
		port.setForeground(Color.GREEN);
		loginpanel.add(port);
		
		IP = new JLabel("IP: ");
		IP.setBounds(330,115,150,55);
		IP.setForeground(Color.GREEN);
		loginpanel.add(IP);
		
		port1 = new JTextField();
		port1.setBounds(400,65,150,30);
		loginpanel.add(port1);
		
		IP1 = new JTextField();
		IP1.setBounds(400,125,150,30);
		loginpanel.add(IP1);
		
		usertext = new JTextField();
		usertext.setBounds(100, 65, 150, 30);
		loginpanel.add(usertext);
		
		password1 = new JPasswordField();
		password1.setBounds(100, 125, 150, 30);
		loginpanel.add(password1);
		
		loginmainlabel = new JLabel("Please Enter Username, Password, Port, and IP, or Select Create New User Below!");
		loginmainlabel.setForeground(Color.GREEN);
		loginmainlabel.setBounds(10, -10, 550, 100);
		loginpanel.add(loginmainlabel);
		
		LoginButton = new JButton("Login");
		
		
		LoginButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				user = usertext.getText();
				//if statements to check database go here
				String porky = port1.getText();
				int porty = Integer.parseInt(porky);
				client = new ChatClient(IP1.getText(),porty);
				client.setMainPanel(self);
				
				//Check with database.
				LoginData login = new LoginData(user,(new String(password1.getPassword())));
				
				try {
					client.sendToServer(login);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				port1.setText("");
				IP1.setText("");
				usertext.setText("");
				password1.setText("");
			}
		});
		LoginButton.setBounds(315, 170, 150, 30);
		loginpanel.add(LoginButton);
		
		
		loginnewuser = new JButton("Create New User");
		loginnewuser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				loginpanel.setVisible(false);
				port1.setText("");
				IP1.setText("");
				usertext.setText("");
				password1.setText("");
				newuserPanel();
			}
		});
		loginnewuser.setBounds(140, 170, 150, 30);
		loginpanel.add(loginnewuser);
		
	}
	
	public void setClient(ChatClient c)
	{
		client = c;
	}
	
	public void newuserPanel()
	{
		newuser.setVisible(true);
		
		newuserlabel = new JLabel("Username: ");
		newuserlabel.setForeground(Color.GREEN);
		newuserlabel.setBounds(10,55,150,55);
		newuser.add(newuserlabel);
		
		newuserpass = new JLabel("Password: ");
		newuserpass.setForeground(Color.GREEN);
		newuserpass.setBounds(10,115,150,55);
		newuser.add(newuserpass);
		
		newuserpass2 = new JLabel("Re-Enter Password: ");
		newuserpass2.setForeground(Color.GREEN);
		newuserpass2.setBounds(10,175,150,55);
		newuser.add(newuserpass2);
		
		newusermain = new JLabel("Please Enter New Username and Password!");
		newusermain.setForeground(Color.GREEN);
		newusermain.setBounds(10,-10,550,100);
		newuser.add(newusermain);
		
		newusertext = new JTextField();
		newusertext.setBounds(170,65,150,30);
		newuser.add(newusertext);
		
		port = new JLabel("Port: ");
		port.setBounds(330,55,150,55);
		port.setForeground(Color.GREEN);
		newuser.add(port);
		
		IP = new JLabel("IP: ");
		IP.setBounds(330,115,150,55);
		IP.setForeground(Color.GREEN);
		newuser.add(IP);
		
		port1 = new JTextField();
		port1.setBounds(400,65,150,30);
		newuser.add(port1);
		
		IP1 = new JTextField();
		IP1.setBounds(400,125,150,30);
		newuser.add(IP1);
		
		//Poop
		
		password2 = new JPasswordField();
		password2.setBounds(170,125,150,30);
		newuser.add(password2);
		
		password3 = new JPasswordField();
		password3.setBounds(170,185,150,30);
		newuser.add(password3);
		
		newuserlogin = new JButton("Create New User");
		newuserlogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(new String(password2.getPassword()).equals(new String(password3.getPassword())))
				{
					
					//Check with database
					//Check with database.
					CreateAccountData create = new CreateAccountData(newusertext.getText(),(new String(password1.getPassword())));
					
					try {
						client.sendToServer(create);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					password2.setText("");
					password3.setText("");
					newusertext.setText("");					
				}
				
				else
				{
					JOptionPane.showMessageDialog(null,"Error, Passwords Do Not Match!");
					password2.setText("");
					password3.setText("");
					newusertext.setText("");
				}
			}
		});
		newuserlogin.setBounds(60,230,150,30);
		newuser.add(newuserlogin);
		
		previous = new JButton("Return To Login");
		previous.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				newuser.setVisible(false);
				loginpanel.setVisible(true);
			}
		});
		previous.setBounds(248, 230, 150, 30);
		newuser.add(previous);
		
	}
	
	public void homePanel(String bob)
	{
		home.setVisible(true);

		logout = new JButton("Logout");
		logout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				home.setVisible(false);
				loginPanel();
			}
		});
		logout.setBounds(450,360,300,50);
		home.add(logout);
		
		welcome = new JLabel("Welcome Back " +bob);
		welcome.setBounds(550,200,300,50);
		welcome.setForeground(Color.BLACK);
		home.add(welcome);
		
		jointable = new JButton("Join Table");
		jointable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				home.setVisible(false);
				try {
					gamePanel();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		jointable.setBounds(450,300,300,50);
		home.add(jointable);
	}
	
	public void gamePanel() throws IOException
	{
		//this is just a fake comment
		waiting.setVisible(false);
		gamegui.setVisible(true);

		dealer = new JLabel();
		dealer.setIcon(new ImageIcon(MainPanel.class.getResource("/GUIs/person.png")));
		dealer.setBounds(540,10,128,128);
		gamegui.add(dealer);		
		
		dealdeck = new JLabel();
		dealdeck.setIcon(new ImageIcon(MainPanel.class.getResource("/cardsimages/back.jpg")));
		dealdeck.setBounds(540,140,71,100);
		gamegui.add(dealdeck);
		
		commdeck1 = new JLabel();
		commdeck1.setIcon(new ImageIcon(MainPanel.class.getResource("/cardsimages/back.jpg")));
		commdeck1.setBounds(425,315,71,100);
		gamegui.add(commdeck1);
		
		commdeck2 = new JLabel();
		commdeck2.setIcon(new ImageIcon(MainPanel.class.getResource("/cardsimages/back.jpg")));
		commdeck2.setBounds(500,315,71,100);
		gamegui.add(commdeck2);
		
		commdeck3 = new JLabel();
		commdeck3.setIcon(new ImageIcon(MainPanel.class.getResource("/cardsimages/back.jpg")));
		commdeck3.setBounds(575,315,71,100);
		gamegui.add(commdeck3);
		
		commdeck4 = new JLabel();
		commdeck4.setIcon(new ImageIcon(MainPanel.class.getResource("/cardsimages/back.jpg")));
		commdeck4.setBounds(650,315,71,100);
		gamegui.add(commdeck4);
		
		commdeck5 = new JLabel();
		commdeck5.setIcon(new ImageIcon(MainPanel.class.getResource("/cardsimages/back.jpg")));
		commdeck5.setBounds(725,315,71,100);
		gamegui.add(commdeck5);
		
		play1deck1 = new JLabel();
		play1deck1.setIcon(new ImageIcon(MainPanel.class.getResource("/cardsimages/back.jpg")));
		play1deck1.setBounds(10,160,71,100);
		gamegui.add(play1deck1);
		
		play1deck2 = new JLabel();
		play1deck2.setIcon(new ImageIcon(MainPanel.class.getResource("/cardsimages/back.jpg")));
		play1deck2.setBounds(90,160,71,100);
		gamegui.add(play1deck2);
		
		play2deck1 = new JLabel();
		play2deck1.setIcon(new ImageIcon(MainPanel.class.getResource("/cardsimages/back.jpg")));
		play2deck1.setBounds(10,540,71,100);
		gamegui.add(play2deck1);
		
		play2deck2 = new JLabel();
		play2deck2.setIcon(new ImageIcon(MainPanel.class.getResource("/cardsimages/back.jpg")));
		play2deck2.setBounds(90,540,71,100);
		gamegui.add(play2deck2);
		
		play3deck1 = new JLabel();
		play3deck1.setIcon(new ImageIcon(MainPanel.class.getResource("/cardsimages/back.jpg")));
		play3deck1.setBounds(540,500,71,100);
		gamegui.add(play3deck1);
		
		play3deck2 = new JLabel();
		play3deck2.setIcon(new ImageIcon(MainPanel.class.getResource("/cardsimages/back.jpg")));
		play3deck2.setBounds(620,500,71,100);
		gamegui.add(play3deck2);
		
		play4deck1 = new JLabel();
		play4deck1.setIcon(new ImageIcon(MainPanel.class.getResource("/cardsimages/back.jpg")));
		play4deck1.setBounds(1030,540,71,100);
		gamegui.add(play4deck1);
		
		play4deck2 = new JLabel();
		play4deck2.setIcon(new ImageIcon(MainPanel.class.getResource("/cardsimages/back.jpg")));
		play4deck2.setBounds(1110,540,71,100);
		gamegui.add(play4deck2);
		
		play5deck1 = new JLabel();
		play5deck1.setIcon(new ImageIcon(MainPanel.class.getResource("/cardsimages/back.jpg")));
		play5deck1.setBounds(1030,160,71,100);
		gamegui.add(play5deck1);
		
		play5deck2 = new JLabel();
		play5deck2.setIcon(new ImageIcon(MainPanel.class.getResource("/cardsimages/back.jpg")));
		play5deck2.setBounds(1110,160,71,100);
		gamegui.add(play5deck2);
		
		dealer1 = new JLabel("Dealer");
		dealer1.setBounds(620,140,50,25);
		dealer1.setForeground(Color.BLACK);
		gamegui.add(dealer1);
		
		player1 = new JLabel();
		player1.setIcon(new ImageIcon(MainPanel.class.getResource("/GUIs/person.png")));
		player1.setBounds(10,10,128,128);
		gamegui.add(player1);
		 
		play1 = new JLabel("Player 1");
		play1.setBounds(10,140,50,25);
		play1.setForeground(Color.BLACK);
		gamegui.add(play1);
		
		player2 = new JLabel();
		player2.setBounds(10,400,128,128);
		player2.setIcon(new ImageIcon(MainPanel.class.getResource("/GUIs/person.png")));
		gamegui.add(player2);
		
		play2 = new JLabel("Player 2");
		play2.setBounds(10,370,50,25);
		play2.setForeground(Color.BLACK);
		gamegui.add(play2);
		
		player3 = new JLabel();
		player3.setBounds(540,620,128,128);
		player3.setIcon(new ImageIcon(MainPanel.class.getResource("/GUIs/person.png")));
		gamegui.add(player3);
		
		play3 = new JLabel("Player 3");
		play3.setBounds(540,600,50,25);
		play3.setForeground(Color.BLACK);
		gamegui.add(play3);
		
		player4 = new JLabel();
		player4.setBounds(1040,400,128,128);
		player4.setIcon(new ImageIcon(MainPanel.class.getResource("/GUIs/person.png")));
		gamegui.add(player4);
		
		play4 = new JLabel("Player 4");
		play4.setBounds(1030,370,50,25);
		play4.setForeground(Color.BLACK);
		gamegui.add(play4);
		
		player5 = new JLabel();
		player5.setBounds(1040,10,128,128);
		player5.setIcon(new ImageIcon(MainPanel.class.getResource("/GUIs/person.png")));
		gamegui.add(player5);
		
		play5 = new JLabel("Player 5");
		play5.setBounds(1040,140,50,25);
		play5.setForeground(Color.BLACK);
		gamegui.add(play5);
		
		
		ready = new JButton("Ready!");
		ready.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				ready.setVisible(false);
				goPanel();
			}
		});
		ready.setBounds(200, 10, 89, 23);
		gamegui.add(ready);
		
	}
	
	public void waitPanel()
	{
		home.setVisible(false);
		gamegui.setVisible(false);
		waiting.setVisible(true);
		
		waitplease = new JLabel("PLEASE WAIT FOR GAME TO FINISH");
		waitplease.setFont(new Font("Tahoma", Font.PLAIN, 50));
		waitplease.setBounds(300, 300, 700, 300);
		waitplease.setForeground(Color.WHITE);
		waiting.add(waitplease);
	}
	
	public void fullPanel()
	{
		JOptionPane.showMessageDialog(null,"Sorry! Game is Currently Full! Please Wait!");
	}
	
	public void goPanel()
	{
		ArrayList<String> moves = new ArrayList<String>(4);
		moves.add("Call");
		moves.add("Bet");
		moves.add("Fold");
		moves.add("Check");
		
		for (String s : moves) {
			JRadioButton rdbtnRb = new JRadioButton(s);
			buttonGroup.add(rdbtnRb);
			if (deltaY ==0)
				rdbtnRb.setSelected(true);
			rdbtnRb.setBounds(170, 540+deltaY, 100, 25);
			rdbtnRb.setActionCommand(s);
			rdbtnRb.setVisible(true);
			gamegui.add(rdbtnRb);
			deltaY += 30;
		}
		
		slider = new JSlider();
		slider.setBounds(300, 570, 200, 75);
		slider.setMaximum(10000);
		slider.setMinimum(0);
		slider.setPaintTicks(true);
		slider.setMajorTickSpacing(2500);
		slider.setPaintLabels(true);
		if(buttonGroup.getSelection().getActionCommand().toString().equals("Bet"))
		{
			slider.setVisible(true);
		}
		else
		{
			slider.setVisible(false);
		}
		gamegui.add(slider);
		
		moveit = new JButton("Move");
		moveit.setBounds(170,675,100,25);
		gamegui.add(moveit);
		moveit.setVisible(true);
		
	}
	
	
	public void displayCards()
	{        
		card = cards.getCards();
        for(int i = 0; i<card.size();i++)
        {
        	int pob = card.get(i).getSuit();
        	int bob = card.get(i).getRank();
        	
        	String joe = null;
        	String jum = null;
        	turd = new ArrayList<String>();
        	
        	if(pob==0)
        	{
        		joe = "d";
        	}
        	if(pob ==1)
        	{
        		joe = "c";
        	}
        	if(pob==2)
        	{
        		joe = "h";
        	}
        	if(pob==3)
        	{
        		joe = "s";
        	}
        	if(bob==0)
        	{
        		jum = "14";
        	}
        	if(bob==1)
        	{
        		jum = "2";
        	}
        	if(bob==2)
        	{
        		jum = "3";
        	}
        	if(bob==3)
        	{
        		jum="4";
        	}
        	if(bob==4)
        	{
        		jum="5";
        	}
        	if(bob==5)
        	{
        		jum="6";
        	}
        	if(bob==6)
        	{
        		jum="7";
        	}
        	if(bob==7)
        	{
        		jum="8";
        	}
        	if(bob==8)
        	{
        		jum="9";
        	}
        	if(bob==9)
        	{
        		jum="10";
        	}
        	if(bob==10)
        	{
        		jum="11";
        	}
        	if(bob==11)
        	{
        		jum="12";
        	}
        	if(bob==12)
        	{
        		jum="13";
        	}
        	
        	turd.add(joe+jum);
        }
        
        
        if(card.size()==3)
        {
        	commdeck1.setIcon(new ImageIcon(MainPanel.class.getResource("/cardsimages/"+turd.get(0))));
        	commdeck2.setIcon(new ImageIcon(MainPanel.class.getResource("/cardsimages/"+turd.get(1))));
        	commdeck3.setIcon(new ImageIcon(MainPanel.class.getResource("/cardsimages/"+turd.get(1))));
        }
        
        else if(card.size()==2)
        {
        	int hop = player.getSeat();
        	if(hop == 1)
        	{
        		play1deck1.setIcon(new ImageIcon(MainPanel.class.getResource("/cardsimages/"+turd.get(0))));
        		play1deck2.setIcon(new ImageIcon(MainPanel.class.getResource("/cardsimages/"+turd.get(1))));
        	}
        	else if(hop == 2)
        	{
        		play2deck1.setIcon(new ImageIcon(MainPanel.class.getResource("/cardsimages/"+turd.get(0))));
        		play2deck2.setIcon(new ImageIcon(MainPanel.class.getResource("/cardsimages/"+turd.get(1))));
        	}
        	else if(hop == 3)
        	{
        		play3deck1.setIcon(new ImageIcon(MainPanel.class.getResource("/cardsimages/"+turd.get(0))));
        		play3deck2.setIcon(new ImageIcon(MainPanel.class.getResource("/cardsimages/"+turd.get(1))));
        	}
        	else if(hop == 4)
        	{
        		play4deck1.setIcon(new ImageIcon(MainPanel.class.getResource("/cardsimages/"+turd.get(0))));
        		play4deck2.setIcon(new ImageIcon(MainPanel.class.getResource("/cardsimages/"+turd.get(1))));
        	}
        	else if(hop == 5)
        	{
        		play5deck1.setIcon(new ImageIcon(MainPanel.class.getResource("/cardsimages/"+turd.get(0))));
        		play5deck2.setIcon(new ImageIcon(MainPanel.class.getResource("/cardsimages/"+turd.get(1))));
        	}
        	
        }
        
        else if(card.size()==0)
        {
        	ImageIcon image = new ImageIcon("/cardsimages/back.bmp");
        	if(commdeck4.getIcon().equals(image))
        	{
        		commdeck4.setIcon(new ImageIcon(MainPanel.class.getResource("/cardsimages/"+turd.get(0))));
        	}
        	else if(commdeck5.getIcon().equals(image))
        	{
        		commdeck5.setIcon(new ImageIcon(MainPanel.class.getResource("/cardsimages/"+turd.get(0))));
        	}
        }
        
	}
}
