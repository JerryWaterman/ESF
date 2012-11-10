
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ESFConnFrame1 {
	
  public void setVisible(boolean b) throws SQLException {
		// TODO Auto-generated method stub
	  
	  try {
		  String driver = "com.mysql.jdbc.Driver";
		  //		  String driver = "com.mysql.jdbc.Driver";
	      String url = "jdbc:mysql://127.0.0.1/test";
	    	  //"            //localhost/test";
		  String username = "root";
		  String password = "hoTTub11";
		  Class.forName(driver);
		  Connection conn = DriverManager.getConnection(url, username, password);
		  
		  Statement Stmt = (Statement) conn.createStatement();

          ResultSet RS = Stmt.executeQuery
          ("SELECT sysID,pdate FROM test.periphyton");

          while (RS.next()) {
              System.out.print("\"" + RS.getString(1));
              System.out.print(" date " + RS.getString(2)+"\n");
              //System.out.println(": " + RS.getString(3));
         }
     
		  
		  
		  
	 		} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  System.out.println("ESF Conn Frame1");		
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

