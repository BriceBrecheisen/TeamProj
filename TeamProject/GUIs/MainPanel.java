package GUIs;

import communications.*;
import poker.*;
import java.awt.BorderLayout;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;

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
	
	
	
	private JPanel waiting;
	private JLabel waitplease;
	
	private ChatClient client;
	private Player player;
	
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
		newuser.setBounds(0,0,500,330);
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
				port1.setText("");
				IP1.setText("");
				usertext.setText("");
				password1.setText("");
				loginpanel.setVisible(false);
				homePanel(user);
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
					password2.setText("");
					password3.setText("");
					newusertext.setText("");
					newuser.setVisible(false);
					loginPanel();
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
				gamePanel();
			}
		});
		jointable.setBounds(450,300,300,50);
		home.add(jointable);
	}
	
	public void gamePanel()
	{
		//this is just a fake comment
		waiting.setVisible(false);
		gamegui.setVisible(true);

		dealer = new JLabel();
		dealer.setIcon(new ImageIcon(MainPanel.class.getResource("/GUIs/person.png")));
		dealer.setBounds(540,10,128,128);
		gamegui.add(dealer);
		
		dealdeck = new JLabel();
		dealdeck.setIcon(new ImageIcon(MainPanel.class.getResource("/cardsimages/s11.bmp")));
		dealdeck.setBounds(540,140,71,100);
		gamegui.add(dealdeck);
		
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
}
