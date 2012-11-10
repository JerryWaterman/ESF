import java.awt.*;
import java.sql.*;
import java.util.Vector;

import javax.swing.*;

import java.awt.event.*;

class  JTable02142012{
	public static void main(String[] args){
		JFrame f=new JFrame();
		JLabel label1=new JLabel("Name: ");
		JLabel label2=new JLabel("Address: ");
		JTextField vName=new JTextField(20); 
		JTextField text2=new JTextField(20);
		//JTable table;
		final Vector<String> columnNames = new Vector<String>();
		final Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		
		try{ 
			Class.forName("oracle.jbdc.driver.OracleDriver");
			//Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@comanche.wic.epa.gov:1521:cintst");
			//Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jerry", "root", "hoTTub11");
			Statement stAll = con.createStatement();
			ResultSet rsAll = stAll.executeQuery("SELECT * FROM SHASPTEST");
			//ResultSet rsAll = stAll.executeQuery("select * from test.site;");
			ResultSetMetaData md = rsAll.getMetaData();
			int columns = md.getColumnCount();
			for (int i =1; i <= columns; i++) {
				columnNames.addElement(md.getColumnName(i));
			}
			while (rsAll.next()) {
				Vector<Object> row = new Vector<Object>(columns);
				for (int i =1; i <= columns; i++) {
					row.addElement(rsAll.getObject(i));
				}
				data.add(row);
			}
			
			
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("select * from SHASPTEST where sysid=1"); 
			String name="",address="";
			if(rs.next()){
				name=rs.getString("LastName");
				address=rs.getString("address");
				}
			vName.setText(name);
			text2.setText(address);
			}
		catch(Exception e){        }
		final JTable table = new JTable(data, columnNames);
		table.setPreferredScrollableViewportSize(new Dimension (700,50));
		JScrollPane scrollPane = new JScrollPane(table);
		JPanel p1 = new JPanel(new GridBagLayout());
		JPanel p2 = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		p1.add(scrollPane,c);
		
		c.gridx = 0;
		c.gridy = 1;
		p2.add(label1,c);
		
		c.gridx = 1;
		c.gridy = 1;
        p2.add(vName,c);
        
        c.gridx = 2;
		c.gridy = 1;
        p2.add(label2,c);
       
        c.gridx = 3;
		c.gridy = 1;
        p2.add(text2,c);
        
        f.getContentPane().add(p1, BorderLayout.NORTH);
        f.getContentPane().add(p2, BorderLayout.CENTER);
        f.setLocation(50, 50);
        f.setSize(800, 900);
        f.setVisible(true); 
        f.pack();
}
	}
