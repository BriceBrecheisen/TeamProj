package communications;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ServerGUI extends JFrame {
	
	 //Declaring all the variables to be used for the GUI.
	  private JLabel status;
	  private String[] labels = {"Status:","Port # :", "Timeout: ", "Server Log Below:"};
	  private JTextField[] textFields;
	  private JTextArea serverLog;
	  
	  //Buttons
	  private JButton listen;
	  private JButton close;
	  private JButton stop;
	  private JButton quit;
	  
	  //Server
	  private ChatServer server;
	  
	  
	public ServerGUI(String title)
	{
		//Setting up this JFrame.
	    this.setTitle(title);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLayout(new BorderLayout());
	    this.setSize(600, 600);
	    
	    
	  //Initializing all the declared variables.
	    status = new JLabel();
	    textFields = new JTextField[2];
	    serverLog = new JTextArea(10, 20);
	    
	    listen = new JButton("Listen");
		close = new JButton("Close");
		stop  = new JButton("Stop");
		quit  = new JButton("Quit");
	    
	  //Initializing and setting the sizes of the textFields array
	    for (int a=0; a<2; a++)
	    	textFields[a] = new JTextField(10);
	    
	    System.out.println("Server GUI opened!");
	    
	    ///////////////////////////////
	    //Instantiating the server
	    server = new ChatServer();
	    server.setLog(serverLog);
	    server.setStatus(status);
	    
	    ///////////////////////////////
	    
	  //First, making a JPanel with Grid-Layout, so that we can 
	    //add each JLabel and the corresponding JTextfield in rows.
	    JPanel toppart = new JPanel();
	    GridLayout grid = new GridLayout(3, 1);
	    grid.setVgap(10);
	    toppart.setLayout(grid);
	    
	    //Setting the layout manager for the individual JPanels
	    GridLayout line = new GridLayout(1, 2);
	    
	    //Next, adding the Jlabels and their corresponding JTextFields
	    //to the grid layout panel, each pair of JLabel and JTextField 
	    //being contained in their own JPanel, implementing flow layout.
	    
	    //First JLabel pair.
	    JLabel literallystatus = new JLabel(labels[0]);
	    status.setText("Not Connected!");
	    status.setForeground(Color.RED);
	    JPanel one = new JPanel();
	    one.setLayout(line);
	    one.add(literallystatus);
	    one.add(status);
	    
	    //Second JLabel and JTextField pair.
	    JLabel portnum = new JLabel(labels[1]);
	    JPanel two = new JPanel();
	    two.setLayout(line);
	    two.add(portnum);
	    two.add(textFields[0]);
	    
	    //Third JLabel and JTextField pair.
	    JLabel timeout = new JLabel(labels[2]);
	    JPanel three = new JPanel();
	    three.setLayout(line);
	    three.add(timeout);
	    three.add(textFields[1]);
	    
	    //Now adding all the individual JPanels to the overall
	    //grid layout panel.
	    toppart.add(one);
	    toppart.add(two);
	    toppart.add(three);
	    
	    //Creating the north JPanel and adding the top panel to it.
	    JPanel north = new JPanel();
	    north.setLayout(new FlowLayout());
	    north.add(toppart);
	    
	    //Creating the center JPanel
	    JPanel center = new JPanel();
	    center.setLayout(new BorderLayout());
	    
	    //Creating the components to add to the panel   
	    JScrollPane aha = new JScrollPane(serverLog);
	    
	    //Finishing up the center. Adding components to the panel.
	    center.add(new JLabel(labels[3]),BorderLayout.NORTH);
	    center.add(aha,BorderLayout.CENTER);
	    
	    //Finally, creating the buttons panel for the south.
	    //Adding action-listeners for the buttons.
	    //Implementing the event handlers.
	    listen.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e)
	      {
	        //Checking if both, the port# and timeout have been entered.
	    	  if (textFields[0].getText().isEmpty() || textFields[1].getText().isEmpty())
	    		  serverLog.append("\nMy friend, Port Number/timeout not entered before pressing Listen!");
	    	  else
	    	  {
	    		  if (!server.isListening())
	    		  {
	    			  //Setting up the server port and timeout
	    			  server.setPort(Integer.parseInt(textFields[0].getText()));
	    			  server.setTimeout(Integer.parseInt(textFields[1].getText()));

	    			  //Invoking listen service method
	    			  try {
	    				  server.listen();
	    				  serverLog.append("\nListening intently!");
	    			  } catch (IOException e1) {
	    				  // TODO Auto-generated catch block
	    				  e1.printStackTrace();
	    			  }}
	    		  else
	    			  serverLog.append("\nServer already listening!");
	    	  }
	      }
	    });
	    
	    close.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e)
	      {
	    	  //Checking if listen button has been pressed
	    	  if (!server.isListening())
	    		  serverLog.append("\nServer not currently started!");
	    	  
	    	  else 
	    	  {
	    		  //Invoking the close service method
	    		  try {
					server.close();
				    serverLog.append("\nServer closed");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    	  }
	    		  
	      }
	    });
	    
	    stop.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e)
	      {
	    	//Checking if listen button has been pressed
	    	  //if (!server.isListening())
	    		  //serverLog.append("\nServer not currently started!");
	    	  
	    	 // else
	    		  //Invoking the stop listening method
	    		  server.stopListening();
	    		  serverLog.append("\nStopped Listening!");
	      }
	    });
	    
	    quit.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e)
		      {
		    	 System.exit(0);
		      }
		    });
	    
	    //Adding the initialized buttons to the south panel.
	    //Creating the south JPanel
	    JPanel south = new JPanel();
	    south.setLayout(new FlowLayout());
	    
	    //Adding the buttons to the south panel
	    south.add(listen);
	    south.add(close);
	    south.add(stop);
	    south.add(quit);
	    
	    //Making some buffers (not really required)
	    JPanel buffer_east = new JPanel();
	    JPanel buffer_west = new JPanel();
	    
	    //Adding the JPanels to this JFrame.
	    this.add(north, BorderLayout.NORTH);
	    this.add(south, BorderLayout.SOUTH);
	    this.add(center, BorderLayout.CENTER);
	    this.add(buffer_east, BorderLayout.EAST);
	    this.add(buffer_west, BorderLayout.WEST);
	    
	    //Finishing up the GUI
	    this.setVisible(true);
	}

	public static void main(String[] args)
	  {
	    new ServerGUI("Server"); //Hardcoded title of the GUI
	  }
}
