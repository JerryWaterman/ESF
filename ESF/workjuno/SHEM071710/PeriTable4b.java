
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class PeriTable4b extends JFrame implements ActionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TableColumn column;

	public PeriTable4b()  {
        //Vector<String> columnNames = new Vector<String>();
        Vector<String> colNames = new Vector<String>();
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        try
        {
            //  Connect to the Database
       	 	Class.forName("com.mysql.jdbc.Driver");
   			String driver = "com.mysql.jdbc.Driver";
   			//String url = "jdbc:mysql://192.168.1.11/test";
   		    String url = "jdbc:mysql://localhost/test";
   			String username = "root";
   			String password = "hoTTub11";
   			Class.forName(driver); // load MySQL driver
   			Connection con = DriverManager.getConnection(url, username, password);
     		//  Read data from a table
   			//String sql = "Select * from test.jerryperiphyton";
            String sql = "Select pdate,sid,lid,cid,rep,matrix,ptype,name,1stdilution_mL,1stsubsample_mL from test.jerryperiphyton";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();
            //  Get column names
            //for (int i = 1; i <= columns; i++)
            //{
              //  columnNames.addElement(md.getColumnName(i));
            //}
            colNames.addElement("Date");
            colNames.addElement("SID");
            colNames.addElement("LID");
            colNames.addElement("CID");
            colNames.addElement("REP");
            colNames.addElement("Matrix");
            colNames.addElement("Type");
            colNames.addElement("Name");
            colNames.addElement("Vol. After Wash (mL)");
            colNames.addElement("Pre-dilution Aliquot (mL)");
            colNames.addElement("1st Dilution (mL)");
            colNames.addElement("1st Sub-Sample mL");
            //DefaultTableModel dm = new DefaultTableModel();
            //dm.addRow(arg0)
            // slim woman 21 or older, must have U.S. passport
            // disable married 51 years old man, 
            // St. Maarten for 7 to 10 days starting May 6 or 7
             //one room, 2 beds, no sex
             //will pay for everything, except acohol
             //will spent a lot of time on French side of the island on the nude beach
             //if you do not get nude too, that OK
             //Will give you $500.00 for your time, once we get to the island  
            // email me  freetriptostmaartem@yahoo.com
            //
            //
            //
            //
            //AbstractTableModel  am = new AbstractTableModel();
            
            //  Get row data
            while (rs.next())
            {
                Vector<Object> row = new Vector<Object>(columns);
                 for (int i = 1; i <= columns; i++)
                {
                    row.addElement( rs.getObject(i) );
                    //System.out.println("get String  " + rs.toString());
                }
                data.addElement( row );
                              
                System.out.println("row firstElement = " + row.firstElement()); 
                //System.out.println("      = " + data.firstElement()); 
            }
             rs.close();
             stmt.close();
        }
        catch(Exception e)
        {
            System.out.println( e );
        }
        //  Create table with database data
        JTable table = new JTable(data, colNames);
        MultiLineHeaderRenderer renderer = new MultiLineHeaderRenderer();
        Enumeration<TableColumn> e =  table.getColumnModel().getColumns();
        while (e.hasMoreElements()) {
        	((TableColumn) e.nextElement()).setHeaderRenderer(renderer);
        }
        JScrollPane scrollPane = new JScrollPane( table );
        column = table.getColumnModel().getColumn(0);  //pdata
        column.setPreferredWidth(130);
        column = table.getColumnModel().getColumn(1);  //sid
        column.setPreferredWidth(40);
        column = table.getColumnModel().getColumn(2);  //lid 
        column.setPreferredWidth(40);
        column = table.getColumnModel().getColumn(3);  //cid
        column.setPreferredWidth(35);
        column = table.getColumnModel().getColumn(4);  //rep
        column.setPreferredWidth(40);
        column = table.getColumnModel().getColumn(5);  //matrix
        column.setPreferredWidth(60);
        column = table.getColumnModel().getColumn(6);  //ptype
        column.setPreferredWidth(45);
        column = table.getColumnModel().getColumn(7);  //name
        column.setPreferredWidth(65);
        column = table.getColumnModel().getColumn(8);  //volafterwash_mL
        column.setPreferredWidth(65);
        
        
        getContentPane().add( scrollPane );
        JPanel buttonPanel = new JPanel();
        getContentPane().add( buttonPanel, BorderLayout.SOUTH);
         
        JButton jb = new JButton ("Add Data");
        jb.addActionListener (this);
        buttonPanel.add (jb);
   
        jb = new JButton ("Delete Data");
        jb.addActionListener (this);
        buttonPanel.add (jb);
   
        jb = new JButton ("Edit Data");
        jb.addActionListener (this);
        buttonPanel.add (jb);
   
        // sets size of chat window
        //this.setSize(2000,800);
        this.setTitle("Periphyton Table, version-4b");
    }
  public void actionPerformed (ActionEvent e)
  {
      //code for jbuttons go here
  }
   public static void main(String[] args)
    {
        PeriTable4b frame = new PeriTable4b();
        frame.setDefaultCloseOperation( EXIT_ON_CLOSE );
        frame.pack();
        frame.setLocation(150, 100);
        frame.setVisible(true);
    }
   
   class MultiLineHeaderRenderer extends JList implements
   TableCellRenderer {
	   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MultiLineHeaderRenderer(){
		setOpaque(true);
		setForeground(UIManager.getColor("TableHeader.foreground"));
		setBackground(UIManager.getColor("TableHeader.background"));
		setBorder(UIManager.getBorder("TableHeader.cellBorder"));
		ListCellRenderer renderer = getCellRenderer();
		((JLabel)renderer).setHorizontalAlignment(JLabel.CENTER);
		setCellRenderer(renderer);
	   }

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int colunm) {
		setFont(table.getFont());
		String str = (value == null) ? "" : value.toString();
		BufferedReader br = new BufferedReader(new StringReader(str));
		String line;
		Vector<String> v = new Vector<String>();
		try {
		  while ((line = br.readLine()) != null )
			  v.addElement(line);
		}catch (IOException ex) {
			ex.printStackTrace();
		}
		setListData(v);
		return this;
	}
	   
   }
}
//
//
//
//
//
//
//
//
//
//
//
//
//
////
////Looking for a slim woman 21 or older, must have U.S. passport
//I'm a disabled married 51 years old man, my wife going Europe and I need to relax!!  
//St. Maarten for 7 to 10 days starting May 6 or 7
//one room, 2 beds, no sex is OK!!
//will pay for everything, except alcohol
//will spent a lot of time on French side of the island on the nude beach
//if you do not get nude too, thats OK
//Will give you $500.00 for your time, once we get to the island
//For more details  
////
////
////
////
////
////
////
//