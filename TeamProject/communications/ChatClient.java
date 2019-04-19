package communications;

import ocsf.client.AbstractClient;

public class ChatClient extends AbstractClient
{
  //Keeping a logincontrol object to be able to call the successful login method.
	private LoginControl logincontrol;
	private CreateAccountControl createcontrol;
	
  public ChatClient()
  {
    super("localhost",8300);
  }

  @Override
  public void handleMessageFromServer(Object arg0)
  {
    //System.out.println("Server Message sent to Client " + (String)arg0);
	  
	  //If server returns truth, it means that the user trying to login does indeed exist.
	  //They can now login.
	  if (((String)arg0).equals("truth"))
	  {
		  logincontrol.loginSuccess();
	  }
	  
	  //If server returns falsehood, it means that the user doesnt exist.
	  else if (((String)arg0).equals("falsehood"))
	  {
		  logincontrol.displayError("Incorrect username/password combination!");
	  }
	  
	  //If server returns exists, it means that a user has been trying to make a username/pass combo, and it already exists.
	  else if (((String)arg0).equals("exists"))
	  {
		  createcontrol.displayError("Username selected already exists my friend!");
	  }
	  
	  //If server returns, it means that a new username and password was successfully made.
	  else if (((String)arg0).equals("done"))
	  {
		  createcontrol.createAccountSuccess();
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
  
  public void setLoginControl(LoginControl control)
  {
	  this.logincontrol=control;
  }
  
  public void setCreateAccountControl(CreateAccountControl control)
  {
	  this.createcontrol=control;
  }

}
