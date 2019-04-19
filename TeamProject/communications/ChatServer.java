package communications;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ChatServer extends AbstractServer
{
  private JTextArea log;
  private JLabel status;
  
  public ChatServer()
  {
    //super(12345);
	  super(8300);
	  super.setTimeout(500);
  }
  
  public ChatServer(int port)
  {
    super(port);
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
	  if (arg0 instanceof LoginData)
	    {
	       LoginData loginData = (LoginData)arg0;
	       int id = loginData.getID();
	       DatabaseFile file = new DatabaseFile();
	       
	       //If id is 1, it means someone is trying to login. So just check for the users info in the file.
	       //If the data exists in file, send "true" back to the server.
	       //Else, send back false.
	       if (id==1)
	       {
	    	   if(file.checkUser(new User(loginData.getUsername(),loginData.getPassword())))
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
	       
	     //If id is not 1, it means someone is trying to create an account. So just check for the users username in the file.
	       //If it doesn't exist, only then make a new entry to the file.
	       //Else, send back "exists" to let client know that the username already exists and they must try again.
	       else
	       {
	    	   if(file.checkName(new User(loginData.getUsername(),loginData.getPassword())))
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
	    		   User newdude=new User(loginData.getUsername(),loginData.getPassword());
	    		   newdude.setID(id);
	    		   file.createUser(newdude);
	    		   
	    		   try {
						arg1.sendToClient("done");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
	    	   }
	       }
	    }

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
    System.out.println("Client Connected");
    //log.append("Client Connected\n");
  }
}
