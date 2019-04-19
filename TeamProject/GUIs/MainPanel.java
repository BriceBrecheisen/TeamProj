package GUIs;

import poker.*;
import java.awt.BorderLayout;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainPanel extends JPanel {
	
	private JPanel loginpanel;
	private JTextField usertext;
	private JPasswordField password1;
	private JLabel loginuserlabel;
	private JLabel loginuserpass;
	private JLabel loginmainlabel;
	private JButton loginnewuser;
	private JButton LoginButton;
	private String user = new String();
	
	private JPanel newuser;
	private JLabel newuserlabel;
	private JLabel newuserpass;
	private JLabel newusermain;
	private JTextField newusertext;
	private JPasswordField password2;
	private JButton newuserlogin;
	private JButton previous;
	
	private JPanel home;
	private JLabel welcome;
	private JButton jointable;
	private JButton logout;
	private JCheckBox stats;
	private JLabel wins;
	private JLabel loss;
	
	private JPanel game;
	
	private Player player;
	
	public MainPanel() {
		
		player = new Player();
		setLayout(null);
		
		loginpanel = new JPanel();
		loginpanel.setBackground(Color.BLACK);
		loginpanel.setBounds(0,0,500,220);
		add(loginpanel);
		loginpanel.setLayout(null);
		
		loginuserlabel = new JLabel("Username: ");
		loginuserlabel.setForeground(Color.GREEN);
		loginuserlabel.setBounds(10, 55, 150, 55);
		loginpanel.add(loginuserlabel);
		
		loginuserpass = new JLabel("Password: ");
		loginuserpass.setBounds(10, 115, 150, 55);
		loginuserpass.setForeground(Color.GREEN);
		loginpanel.add(loginuserpass);
		
		usertext = new JTextField();
		usertext.setBounds(170, 65, 150, 30);
		loginpanel.add(usertext);
		
		password1 = new JPasswordField();
		password1.setBounds(170, 125, 150, 30);
		loginpanel.add(password1);
		
		loginmainlabel = new JLabel("Please Enter Username and Password Or Select Create New User Below!");
		loginmainlabel.setForeground(Color.GREEN);
		loginmainlabel.setBounds(10, -10, 550, 100);
		loginpanel.add(loginmainlabel);
		
		home = new JPanel();
		home.setBounds(0,0,1600,1000);
		home.setBackground(Color.RED);
		add(home);
		home.setLayout(null);
		home.setVisible(false);
		
		jointable = new JButton("Join Table");
		jointable.setBounds(600,400,300,50);
		home.add(jointable);
		
		logout = new JButton("Logout");
		logout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				home.setVisible(false);
				loginpanel.setVisible(true);
			}
		});
		logout.setBounds(600,460,300,50);
		home.add(logout);
		
		LoginButton = new JButton("Login");
		LoginButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//if statements to check database go here
				password1.setText("");
				user = usertext.getText();
				usertext.setText("");
				loginpanel.setVisible(false);
				home.setVisible(true);
			}
		});
		LoginButton.setBounds(248, 170, 150, 30);
		loginpanel.add(LoginButton);
		
		welcome = new JLabel("Welcome Back ");
		welcome.setBounds(600,200,300,50);
		welcome.setForeground(Color.BLACK);
		home.add(welcome);
		
		newuser = new JPanel();
		newuser.setBackground(Color.BLACK);
		newuser.setBounds(0,0,500,220);
		add(newuser);
		newuser.setLayout(null);
		newuser.setVisible(false);
		
		newuserlabel = new JLabel("Username: ");
		newuserlabel.setForeground(Color.GREEN);
		newuserlabel.setBounds(10,55,150,55);
		newuser.add(newuserlabel);
		
		newuserpass = new JLabel("Password: ");
		newuserpass.setForeground(Color.GREEN);
		newuserpass.setBounds(10,115,150,55);
		newuser.add(newuserpass);
		
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
		
		newuserlogin = new JButton("Create New User");
		newuserlogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				newuser.setVisible(false);
				password2.setText("");
				newusertext.setText("");
				loginpanel.setVisible(true);
			}
		});
		newuserlogin.setBounds(60,170,150,30);
		newuser.add(newuserlogin);
		
		previous = new JButton("Return To Login");
		previous.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				newuser.setVisible(false);
				loginpanel.setVisible(true);
			}
		});
		previous.setBounds(248, 170, 150, 30);
		newuser.add(previous);
		
		loginnewuser = new JButton("Create New User");
		loginnewuser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				loginpanel.setVisible(false);
				usertext.setText("");
				password1.setText("");
				newuser.setVisible(true);
				
			}
		});
		loginnewuser.setBounds(60, 170, 150, 30);
		loginpanel.add(loginnewuser);
		
	}
}
