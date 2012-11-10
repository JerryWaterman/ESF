

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class shemConnFrame1 {
	
  public void setVisible(boolean b) throws SQLException {
		// TODO Auto-generated method stub
	  
	  try {
		  String driver = "com.mysql.jdbc.Driver";
		  //		  String driver = "com.mysql.jdbc.Driver";
	      String url = "jdbc:mysql://commanche.wic.epa.gov/test";
	    	  //"            //localhost/test";
		  String username = "CSHEM";
		  String password = "welcome1";
		  Class.forName(driver);
		  Connection conn = DriverManager.getConnection(url, username, password);
		  
		  Statement Stmt = (Statement) conn.createStatement();

          ResultSet RS = Stmt.executeQuery
          ("SELECT sysID,pdate FROM test.jerryperiphyton");

          while (RS.next()) {
              System.out.print("\"" + RS.getString(1));
              System.out.print(" Date = " + RS.getString(2)+"\n");
              //System.out.println(": " + RS.getString(3));
         }
     
		  
		  
		  
	 		} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  System.out.println("SHEM Conn Frame1");		
	}

}
//*
//ConnString = "DRIVER={MySQL ODBC 3.51 Driver}; SERVER=192.168.1.11; DATABASE=test; " & _
//"UID=camile;PASSWORD=camile; OPTION=3"
//Set Connection = CreateObject("ADODB.Connection")
//Set objRS = CreateObject("ADODB.Recordset")
//Connection.Open ConnString
//If (Err.Number <> 0) Then
//   MsgBox "Database Connection failed! Inform the administrator!"
//End If

