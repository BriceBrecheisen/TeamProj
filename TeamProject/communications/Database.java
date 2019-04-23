package communications;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class Database
{
  private Connection con;
  private Statement stmt;
  private ResultSet rs;
  private ResultSetMetaData rmd;
  
  //Add any other data fields you like – at least a Connection object is mandatory
  public Database() throws IOException
  {
    //Add your code here
	//Read properties file
	 Properties prop = new Properties();
	 FileInputStream fis = new FileInputStream("communications/db.properties");
	 prop.load(fis);
	 String url = prop.getProperty("url");
	 String user = prop.getProperty("user");
	 String pass = prop.getProperty("password"); 
	 
	//Perform the Connection
     try {
		con = DriverManager.getConnection(url,user,pass);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
  }
  
  public ArrayList<String> query(String query)
  {
	  ArrayList<String> dude=new ArrayList<String>();
	  String builder = "";
    //Add your code here
	  //Create a comma delimited record: using the ResultMetaData and ResultSet
	  //1. Get the number of columns.
	  //2. Iterate through each record. While (rs.next())
	  //3. Have a for loop based on the number of columns
	  //4. for (i=1;i<=no_columns;i++);
	  //5. Concatenate the field followed by commas.
	//Create a statement
      try {
		stmt=con.createStatement();
		
		//Execute a query
	    rs=stmt.executeQuery(query);  
	      
	    //Get metadata about the query
	    rmd = rs.getMetaData();
	      
	    //Get the # of columns
	    int no_columns = rmd.getColumnCount();
	    
	    //Get a column name
	    String name = rmd.getColumnName(1);
	      
	    //Fetch each row (use numeric numbering
	    while(rs.next()) 
	    {
	    	for (int i=1;i<=no_columns;i++)
	    	{
	    		builder+=rs.getString(i)+",";
	    	}
	    //Add the string to the Arraylist.
	    	dude.add(builder);
	    //Clear the builder string
	    	builder="";
	    }
	    
	    //Return the arraylist
	    return dude;
	      
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	} 
	  
  }
  
  public void executeDML(String dml) throws SQLException
  {
    //Add your code here
	  //Just run the query. Query function.
    
      //Create a statement
      stmt=con.createStatement();  
      
      //Execute a DML statement
      stmt.execute(dml);
	  
  }
  
  //The following methods are implemented in order to insert/check users within the database.
  public boolean createUser(User user)
  {
	  //Creating user, i.e. inserting the users information into the user table.
	  //Starting the query string
	  //Adding the users information into the query string.
	  String query = "INSERT into poker_users values('"+user.getUsername()
	  +"',aes_encrypt('"+user.getPassword()+"','key');";
	  
	  //Executing the query as a DML
	  try {
		executeDML(query);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
	}
	  return true;
  }
  
  public boolean checkUser(User user)
  {
	  //Checking if the user already exists in the database
	  //Starting the query string
	  //Adding user's user-name into the query so that it can be checked for.
	  String query = "SELECT username,aes_decrypt(password,'key') FROM poker_users WHERE username='"+user.getUsername()+"';";
	  
	  //Executing the query, and catching the results in an ArrayList object.
	  ArrayList<String> result = query(query);
	  
	  //If the size of the results returned array is 0, it means the user doesn't exist.
	  if (result.size()==0)
		  return false;
	  
	  //Else if the password doesn't match, return false as well.
	  else if (!(result.get(0).split(",")[1].equals(user.getPassword())))
		  return false;
	  
	  //Else, the user exists, and the user-name/password combination is correct. So return true my friend.
	  return true;
  }
  
  public boolean checkName(User user)
  {
	//Checking if the user already exists in the database
	  //Starting the query string
	  //Adding user's user-name into the query so that it can be checked for.
	  String query = "SELECT username FROM poker_users WHERE username='"+user.getUsername()+"';";
	  
	  //Executing the query, and catching the results in an ArrayList object.
	  ArrayList<String> result = query(query);
	  
	  //If the size of the results returned array is 0, it means the user doesn't exist.
	  if (result.size()==0)
		  return false;
	  
	  //Else, the user exists. So return true my friend.
	  return true;
  }
  
}

