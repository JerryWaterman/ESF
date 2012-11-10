
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class PeriTable4d extends JInternalFrame implements ActionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TableColumn column;
	//static final int xOffset = 1200, yOffset = 500;
	
	public PeriTable4d()  {
		 super("Perithyton attempt ", 
	              true, //resizable
	              true, //closable
	              true, //maximizable
	              true);//iconifiable
	     //...Then set the window size or call pack...
	    setSize(300,300);
        //Set the window's location.
	    setLocation(1000, 2000);
	    setVisible(true);
	    System.out.println("after setLocation in Periphyton4d");
        //Vector<String> columnNames = new Vector<String>();
        Vector<String> colNames = new Vector<String>();
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        try
        {
            //  Connect to the Database
       	 	Class.forName("com.mysql.jdbc.Driver");
   			String driver = "com.mysql.jdbc.Driver";
   			String url = "jdbc:mysql://localhost/test";
   			String username = "root";
   			String password = "hoTTub11";
   			Class.forName(driver); // load MySQL driver
   			Connection con = DriverManager.getConnection(url, username, password);
     		//  Read data from a table
   			//String sql = "Select * from test.jerryperiphyton";
            String sql = "Select pdate,sid,lid,cid,rep,matrix,ptype,name,1stdilution_mL," +
            		"1stsubsample_mL,2nddilution_ml,2ndsubsamlpe_mL,3rddilution_ml,47mmfiltervolume_mL," +
            		"25mmfiltervolume_ml,chlafiltervolume_mL from test.jerryperiphyton";
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
            colNames.addElement("Vol. After \nWash\n (mL)");
            colNames.addElement("Pre-dilution\nAliquot\n (mL)");
            colNames.addElement("1st\n Dilution\n(mL)");
            colNames.addElement("1st Sub-\nSample\n(mL)");
            colNames.addElement("2nd\nDilution\n(mL)");
            colNames.addElement("3rd\nDilution");
            colNames.addElement("47 mm\nFilter\nVolume");
            colNames.addElement("25 mm");
            colNames.addElement("chla filter");
            colNames.addElement("");
            //colNames.addElement("");
            //  Get row data 
            while (rs.next())
            {
                Vector<Object> row = new Vector<Object>(columns);
                 for (int i = 1; i <= columns; i++)
                {
                    row.addElement( rs.getObject(i) );
                    //System.out.println("get String  " + rs.toString().substring(1, 8));
                }
                data.addElement( row );
                //data.
                System.out.println("row firstElement = " + row.firstElement()); 
                String sDate = row.toString();
                String sd = sDate.substring(1, 11);
                System.out.println(" date  = " + sd); 
            } 
            rs.close();
            stmt.close();
        }
        catch(Exception e)
        {
            System.out.println( e );
        }
        //  Create table with database data
        //DefaultTableModel dm = new DefaultTableModel();//dm.
        JTable table = new JTable(data, colNames);
        MultiLineHeaderRenderer renderer = new MultiLineHeaderRenderer();
        Enumeration<TableColumn> e = table.getColumnModel().getColumns();
        while (e.hasMoreElements()) {
        	((TableColumn) e.nextElement()).setHeaderRenderer(renderer);
        }
        JScrollPane scrollPane = new JScrollPane( table );
        column = table.getColumnModel().getColumn(0);  //pdata
        column.setPreferredWidth(130);
        column = table.getColumnModel().getColumn(1);  //sid
        column.setPreferredWidth(35);
        column = table.getColumnModel().getColumn(2);  //lid 
        column.setPreferredWidth(30);
        column = table.getColumnModel().getColumn(3);  //cid
        column.setPreferredWidth(35);
        column = table.getColumnModel().getColumn(4);  //rep
        column.setPreferredWidth(35);
        column = table.getColumnModel().getColumn(5);  //matrix
        column.setPreferredWidth(45);
        column = table.getColumnModel().getColumn(6);  //ptype
        column.setPreferredWidth(35);
        column = table.getColumnModel().getColumn(7);  //name
        column.setPreferredWidth(50);
        column = table.getColumnModel().getColumn(8);  //volafterwash_mL
        column.setPreferredWidth(65);
        column = table.getColumnModel().getColumn(9);  //predilutionaliquid
        column.setPreferredWidth(85);
        column = table.getColumnModel().getColumn(10); //1stdilutiond
        column.setPreferredWidth(65);
        column = table.getColumnModel().getColumn(11); //1stsubdilutiond
        column.setPreferredWidth(65);
        
        getContentPane().add(scrollPane);
        //getContentPane().
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
   
        this.setTitle("Periphyton Table, version-4c");
        
    }
  public void actionPerformed (ActionEvent e)
  {
      //code for jbuttons go here
  }
   //public static void main(String[] args)
    //{
    //    PeriTable4d frame = new PeriTable4d();
    //    frame.setDefaultCloseOperation( EXIT_ON_CLOSE );
    //    frame.pack();
    //    frame.setLocation(20, 20);
    //    frame.setSize(1000, 700);
    //    frame.setVisible(true);
   // }
   
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