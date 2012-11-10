import javax.swing.JOptionPane;
import java.sql.*;

public class JDBCProgram{
	
	static String userid="root", password = "hoTTub11";
	static String url = "jdbc:mysql://localhost:3306"
;	// String url = "jdbc:mySubprotocol:myDataSource"; ?
	static Statement stmt;
	static PreparedStatement pstmt;
	static Connection con;
	public static void main(String args[]){

	JOptionPane.showMessageDialog(null,"JDBC Programming showing Updation of Table Data");
		int choice = -1;
		
		do{
			choice = getChoice();
			if (choice != 0){
				getSelected(choice);
			}
		}
		while ( choice !=  0);
			System.exit(0);
	}

	public static int getChoice()
	{
		String choice;
		int ch;
		choice = JOptionPane.showInputDialog(null,
	"1. Create Employees Table\n"+
	"2. Create Products Table\n"+
	"3. Insert data into Employees Table\n"+
	"4. Insert data into Orders Table\n"+
	"5. Retrieve data for Employees Table\n"+
	"6. Retrieve data for Orders Table\n"+
	"7. Update Employees Table\n"+
	"8. Update Employees Table Using a Prepared Statement\n"+
	"9. Update many records of Orders Table Using a Prepared Statement\n"+
	"10. List the name of employees who bought CD's\n"+
			"0. Exit\n\n"+
			"Enter your choice");
		ch = Integer.parseInt(choice);
		return ch;

	}

	public static void getSelected(int choice){
		if(choice==1){
			createEmployees();
		}
		if(choice==2){
			createOrders();
		}
		if(choice==3){
			insertEmployees();
		}
		if(choice==4){
			insertOrders();
		}
		if(choice==5){
			retrieveEmployees();
		}
		if(choice==6){
			retrieveOrders();
		}
		if(choice==7){
			updateEmployees();
		}
		if(choice==8){
			updateEmployeesPrepared();
		}
		if(choice==9){
			updateOrdersPrepared();
		}
		if(choice==10){
			dynamicQuery();
		}
	}

	public static Connection getConnection()
	{
				
		try {
	Class.forName("com.mysql.jdbc.Driver");	
//Class.forName("myDriver.ClassName"); ?

		} catch(java.lang.ClassNotFoundException e) {
			System.err.print("ClassNotFoundException: ");
			System.err.println(e.getMessage());
		}

		try {
			con = DriverManager.getConnection(url,
		 userid, password);

		} catch(SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}
		
		return con;
	}
	
	/*CREATE TABLE Employees (
		    Employee_ID INTEGER,
		    Name VARCHAR(30)
		);*/
	

	public static void createEmployees()
	{
		Connection con = getConnection();
		
		String createString;
		createString = "create table test.Employees (" +
						"Employee_ID INTEGER, " +
						"Name VARCHAR(30))";
		try {
			stmt = con.createStatement();
	   		stmt.executeUpdate(createString);
			stmt.close();
			con.close();

		} catch(SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}
	JOptionPane.showMessageDialog(null,"Employees Table Created");
	}

	/*CREATE TABLE Orders (
		    Prod_ID INTEGER,
		    ProductName VARCHAR(20),
		    Employee_ID INTEGER
		);*/
	
	public static void createOrders()
	{
		Connection con = getConnection();
		
		String createString;
		createString = "create table test.Orders (" +
						"Prod_ID INTEGER, " +
						"ProductName VARCHAR(20), "+
						"Employee_ID INTEGER )";
		

		try {
			stmt = con.createStatement();
	   		stmt.executeUpdate(createString);

			stmt.close();
			con.close();

		} catch(SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}
	JOptionPane.showMessageDialog(null,"Orders Table Created");
	}
	
	/*Employee_ID 	Name 
	 	6323 		Hemanth 
	 	5768 		Bob 
	 	1234 		Shawn 
	 	5678 		Michaels */
	public static void insertEmployees()
	{
		Connection con = getConnection();
		
	  String insertString1, insertString2, insertString3, insertString4;
	  insertString1 = "insert into test.Employees values(6323, 'Hemanth')";
	  insertString2 = "insert into test.Employees values(5768, 'Bob')";
	  insertString3 = "insert into test.Employees values(1234, 'Shawn')";
	  insertString4 = "insert into test.Employees values(5678, 'Michaels')";
		

		try {
			stmt = con.createStatement();
	   		stmt.executeUpdate(insertString1);
	   		stmt.executeUpdate(insertString2);
	   		stmt.executeUpdate(insertString3);
	   		stmt.executeUpdate(insertString4);

			stmt.close();
			con.close();

		} catch(SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}
	JOptionPane.showMessageDialog(null,"Data Inserted into Employees Table");
	}
	
	/*	Prod_ID 	ProductName 	Employee_ID 
	 		543 	Belt 			6323 
	 		432 	Bottle 			1234 
	 		876 	Ring			5678 
 */
	public static void insertOrders()
	{
		Connection con = getConnection();
		
	  String insertString1, insertString2, insertString3, insertString4;
	  insertString1 = "insert into test.Orders values(543, 'Belt', 6323)";
	  insertString2 = "insert into test.Orders values(432, 'Bottle', 1234)";
	  insertString3 = "insert into test.Orders values(876, 'Ring', 5678)";
		
	
		try {
			stmt = con.createStatement();
	   		stmt.executeUpdate(insertString1);
	   		stmt.executeUpdate(insertString2);
	   		stmt.executeUpdate(insertString3);
	
			stmt.close();
			con.close();
	
		} catch(SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}
	JOptionPane.showMessageDialog(null,"Data Inserted into Orders Table");
	}
	
	public static void retrieveEmployees(){
		Connection con = getConnection();
		String result = null;
		String selectString;
		selectString = "select * from test.Employees";
	    result ="Employee_ID\t \tName\n";
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(selectString);
			while (rs.next()) {
			    int id = rs.getInt("Employee_ID");
			    String name = rs.getString("Name");
			    result+=id+"\t \t"+ name+"\n";
			}
			stmt.close();
			con.close();

		} catch(SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}
	JOptionPane.showMessageDialog(null, result);
	}
	
	public static void retrieveOrders(){
		Connection con = getConnection();
		String result = null;
		String selectString;
		selectString = "select * from test.Orders";
		result ="Prod_ID\t\tProductName\t\tEmployee_ID\n";
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(selectString);
			while (rs.next()) {
				int pr_id = rs.getInt("Prod_ID");
				String prodName = rs.getString("ProductName");
			    int id = rs.getInt("Employee_ID");
			    result +=pr_id+"\t\t"+ prodName+"\t\t"+id+"\n";
			}
			stmt.close();
			con.close();

		} catch(SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}
	JOptionPane.showMessageDialog(null, result);
	}
	
	public static void updateEmployees(){
		Connection con = getConnection();
		
		String updateString1;
		updateString1 = "update test.Employees set name = 'hemanthbalaji' where Employee_id = 6323";
	

		try {
			stmt = con.createStatement();
	   		stmt.executeUpdate(updateString1);

			stmt.close();
			con.close();

		} catch(SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}
	JOptionPane.showMessageDialog(null,"Data Updated into Employees Table");
	}
	
	public static void updateEmployeesPrepared(){
		Connection con = getConnection();
// create prepared statement
		try {
	pstmt = con.prepareStatement
("update test.Employees set name = ? where Employee_Id  = ?");
	pstmt.setString(1, "hemanth bob");	//Note index starts with 1
	pstmt.setInt(2, 6323);

	   		pstmt.executeUpdate();

			pstmt.close();
			con.close();

		} catch(SQLException ex) {
		   System.err.println("SQLException: " + ex.getMessage());
		}
	JOptionPane.showMessageDialog(null,"Data Updated into Employees Table");
	}
	//phone # 866-299-3188  	code # 513 569 7076
	public static void updateOrdersPrepared(){

		int [] productIds = {543, 432, 876};
		String [] productNames = {"cds", "dvds", "Espresso"};
		int len = productNames.length;

		Connection con = getConnection();

		try {
	pstmt = con.prepareStatement
("update test.Orders set productname = ? where Prod_Id  = ?");
			for(int i = 0; i < len; i++) {
				pstmt.setInt(2, productIds[i]);
				pstmt.setString(1, productNames[i]);
				pstmt.executeUpdate();
			}

			pstmt.close();
			con.close();

		} catch(SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}
	JOptionPane.showMessageDialog(null,"Data Updated into Orders Table");
	}
	
	public static void dynamicQuery(){
		Connection con = getConnection();
		String result = null;
		String selectString;
		selectString = "select Employees.name from test.Employees, test.Orders where productname = 'cds' " +
			"and Employees.employee_id = Orders.employee_id ";
	    result ="Name\n";
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(selectString);
			while (rs.next()) {
			    String name = rs.getString("Name");
			    result+=name+"\n";
			}
			stmt.close();
			con.close();

		} catch(SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}
	JOptionPane.showMessageDialog(null, result);
	}
	


}//End of class