import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.RowSorterListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import org.w3c.dom.Text;



//import ComboBoxItem.Item;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CenGUI {
	/**
	 * @param args
	 */
	
	JDesktopPane desktop;
	static int xpts[] = { 2,4,6 } ;
	static int ypts[] = { 3,5,7 } ;
	static Polygon p = new Polygon( xpts, ypts, xpts.length );
	JTable table;
	private static Vector<String[]> cache;// holds currently displayed rows
	private static Map<Integer, String[]> updatedData; // holds updated rows
	final static JComboBox<String> comboBox = new JComboBox<String>();
	final static JComboBox<String> ecmbo = new JComboBox<String>();
	public static boolean reselectFlag = true;
	static int bdFlag = 0;
	static int edFlag = 0;
	static int eRainFlag = 0;
	static int bindex = 0;
	
	public static String value, savebdate, evlue, saveedate;
	public static String s1;
	public static Date s2;
	public static float pArea = 0;
	public static float pX = 0;
	public static float pY = 0;
	
	// test to save sysid from JCB combo 
		
	// end of test to save sysid from JCB combo
	
	@SuppressWarnings({ "unchecked", "unchecked" })
	public static void main(String[] args) {
		final JFrame frame = new JFrame("Test October 22, 2012");
		frame.getContentPane().setLayout(new BorderLayout());
		//frame.setSize(900, 900);
		cache = new Vector<String[]>();
        //JTextField bDate = new JTextField(10);
        //JTextField eDate = new JTextField(10);
        JLabel beginRain = new JLabel("Begin Rain Event");
        JLabel endRain   = new JLabel("End Rain Event");
        final JTextField bRain = new JTextField(10);
        final JTextField eRain = new JTextField(10);
        final ArrayList<String> arrDate = new ArrayList<String>(); 
        final ArrayList<String> aDate = new ArrayList<String>(); 
        final ArrayList<String> fDate = new ArrayList<String>();  //find date array
        final ArrayList<String> pData = new ArrayList<String>();  //Precipitation data
        ArrayList<String> idArray = new ArrayList<String>();
        JButton cRun = new JButton("Centroid Area");
        cRun.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e){
        		String str_date = "8/30/2006  6:15:00 AM"; //"08-30-2006";
        		DateFormat formatter;
        		Date date;
        		// find the first 12 char
        		//   1234567890123 
        		//   8/30/06 6:15 AM	0.255085205
        		//   8/30/06 6:30 AM	0.340042005
        		//8/30/2006  6:15:00 AM
        		formatter = new SimpleDateFormat("M/dd/yyyy HH:mm");//"MM-dd-yyyy");
        		try {
        			String arrayDate = "";
        			//String str = "8/30/06 6:15 AM";// need to read all data
        			//File file = new File("C:\\Users\\Public\\CentroidData.txt");
        			File file = new File("C:\\Users\\jwaterma\\workspace\\ESFmarch2012\\CentroidData.txt");
        			StringBuffer contents = new StringBuffer();
        			BufferedReader reader = null;
        			try {
        				reader = new BufferedReader(new FileReader(file));
        				String text = null;
        				//FileWriter fstream = new FileWriter("out.txt");
        				PrintWriter fstream = new PrintWriter(new BufferedWriter(new FileWriter("out.txt")));
        				BufferedWriter out = new BufferedWriter(fstream);
        				while ((text = reader.readLine()) != null) {
        					contents.append(text).append(System.getProperty("line.separator"));
        					aDate.add(text);
        					//System.out.println(" text = "+text);
        					String sPrec = String.valueOf(text);
        					String vPrec = sPrec.substring(16, sPrec.length());
        					//System.out.println(" vPrec = "+vPrec);
        					String sDate = String.valueOf(text); 
        					String vDate = sDate.substring(0, 13);
        					//System.out.println(" vDate = "+vDate);
        					date = (Date)formatter.parse(vDate);
        					long longDate = date.getTime();
        					longDate = Math.abs(longDate);
        					//int longDate = (int) date.getTime();
        					String lDt = Long.toString(longDate);
        					out.write(lDt+" "+vPrec);//(int) (Math.abs(longDate)));
        					out.write("\n");
        					out.flush();
        					System.out.println("L# 156, long Date = "+Math.abs(longDate));
        					//System.out.println(" text data "+text+"     length = "+aDate.size()+"  String is "+sDate);
        					//out.append((char)Math.abs(longDate));
        				}
        				out.close();
        				} catch (FileNotFoundException ex) {
        				ex.printStackTrace();  
        				} catch (IOException ex) {
        					ex.printStackTrace();
        					} finally {   
        						try { 
        							if (reader != null) {
        								reader.close();
        								} 
        							} catch (IOException ex) {
        								ex.printStackTrace();
        								}
        						} 
        			//System.out.println(contents.toString());
        			arrayDate = str_date.substring(0, 15);
        			//System.out.println(" array Date = "+arrayDate);
        			arrDate.add(arrayDate);
        			//System.out.println("arrDate = "+ arrDate);
        			date = (Date)formatter.parse(str_date);
					long longDate = date.getTime();
					//System.out.println(" long Date = "+longDate);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
        		//double c = 0.0;
        		
        		int N = 3;
        		//Float N = (float) 0.0;
        		//Point[] polygon = new Point[3];
        		java.awt.geom.Point2D.Float[] polygon = new Point2D.Float[3];
        		
        		
        		polygon[0] = new Point2D.Float((float)0.0f,   (float)  0.0);
        		polygon[1] = new Point2D.Float((float)0.5f,   (float)  1.0);
        		polygon[2] = new Point2D.Float((float)1.0f,   (float)  0.0);
        		// area = 0.5,    x = 0.5,   y = 0.3333333
        		
        		
        		// same answer for centroid as web site 
        		//             http://www.icoachmath.com/math_dictionary/centroid.html
        		//polygon[0] = new Point2D.Float((float)-4.0f,   (float)  2.0);
        		//polygon[1] = new Point2D.Float((float)0.0f,   (float)  5.0);
        		//polygon[2] = new Point2D.Float((float)3.0f,   (float)  -1.0);
        		
        		//         [4,  4,  8,  8, -4,-4]; area = 128 same as web site
        		//         [6, -4, -4, -8, -8, 6];
        		//polygon[0] = new Point2D.Float((float)4.0f,   (float)  6.0);
        		//polygon[1] = new Point2D.Float((float)4.0f,   (float) -4.0);
        		//polygon[2] = new Point2D.Float((float)8.0f,   (float)  -4.0);
        		//polygon[3] = new Point2D.Float((float)8.0f,   (float)  -8.0);
        		//polygon[4] = new Point2D.Float((float)-4.0f,   (float) -8.0);
        		//polygon[5] = new Point2D.Float((float)-4.0f,   (float)  6.0);
        		
        		/*polygon[0] = new Point2D.Float((float)38829.875f,     (float)  0.0);
        		polygon[1] = new Point2D.Float((float)38829.88542f,   (float)  0.02);
        		polygon[2] = new Point2D.Float((float)38829.89583f,   (float)  0.0);
        		polygon[3] = new Point2D.Float((float)38829.99625f,   (float)  0.0);
        		polygon[4] = new Point2D.Float((float)38829.91667f,   (float)  0.0);
        		polygon[5] = new Point2D.Float((float)38829.92708f,   (float)  0.0);
        		polygon[6] = new Point2D.Float((float)38829.9375f,    (float)  0.0);
        		polygon[7] = new Point2D.Float((float)38829.94792f,   (float)  0.0);
        		polygon[8] = new Point2D.Float((float)38829.95833f,   (float)  0.0);
        		polygon[9] = new Point2D.Float((float)38829.96875f,   (float)  0.04);
        		polygon[10] = new Point2D.Float((float)38829.97917f,  (float)  0.0);
        		polygon[11] = new Point2D.Float((float)38829.98958f,  (float)  0.01);*/
        		float[] ret = new float[3];
        		float[] p2Data = new Point2Df().PolygonCenterOfMass(polygon, N, ret);
        		// answer from Point2Df()  same as MS Excel "Copy of centroid.xls", sheet "example2"
        		// area = 0.5,    x = 0.5,   y = 0.3333333
        		
        		System.out.println(" array from p2Data\t = "+ret[0]+"\t "+ret[1]+"\t"+ret[2]);
        		pX    = ret[0];
        		pY    = ret[1];
        		pArea = ret[2];
        		System.out.println(" array    X\t = "+pX+"\t Y = "+pY+"\tArea = "+pArea);
        		//System.out.println("vArea = "+vArea); 
           	}
        });        
        
        JButton bRun = new JButton("Run");
        GraphPnl graphpnl = new GraphPnl(frame);
        bRun.addActionListener(graphpnl);
        /*bRun.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		
        		//double c = 0.0;
        		//pScreen(c); // class in this file CentroidGUI.java for Centroid area?
        		//System.out.println(" c = "+c);
        		//plotArea(null);
        		//cScreen pFrame = new cScreen();
        		//System.out.println(" Run  button");
              	//pFrame.setVisible(true);
              	//pFrame.getContentPane();
           	}
        });
        */
       
		
        JButton openButton = new JButton("Open Data file(test)");
        
        // next steps 
        // a- check dates 
        // b- prepared plot screen area
        // need put data from db into array
        
        openButton.addActionListener(graphpnl.getDataPanel());
        
        //graphpanel.getDataPanel();
        
        //frame.setVisible(true);
        //frame.pack();
        JButton bSve = new JButton("Save Data");
        
        JButton bSel = new JButton("Select Another Set of Data");
        bSel.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String sbRain = bRain.getText();
        		int lenbRain = sbRain.length();
        		if (lenbRain > 0){
        		    bRain.setText("");
        			reselectFlag = true;
        	    }
        		System.out.println("Select bSel"+reselectFlag);
        	}
        }); 
        JButton bExt = new JButton("Exit");
        bExt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        //      READ IN DATA FROM MySQL
        // stores updated rows rDate, rHour, rPCP, sysid
		updatedData = new HashMap<Integer, String[]>(); // 02.23.2012
		Vector<String> columnNames = new Vector<String>();
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        comboBox.addItem("Date,              Precipitation");
        ecmbo.addItem("Date,     Precipitation");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ESF", "root", "hoTTub11");
			Statement stAll = con.createStatement();
			ResultSet rsAll = stAll.executeQuery("select * from rainESF3"); //test data
			ResultSetMetaData md = rsAll.getMetaData();
			
			int columns = md.getColumnCount();
			for (int i =1; i <= columns; i++) {
				columnNames.addElement(md.getColumnName(i));
			}
			while (rsAll.next()) {
				Vector<Object> row = new Vector<Object>(columns);
				for (int i = 1; i <= columns; i++) {
					if (i == 1) {
					System.out.println("L# 329, i = "+i+" data = "+rsAll.getObject(i));
					}
					row.addElement(rsAll.getObject(i));
				}
				comboBox.addItem(rsAll.getString("rDate")+",  "+rsAll.getString("rPCP"));  //+rsAll.getString("Sysid"));
			    ecmbo.addItem(rsAll.getString("rDate")+",  "+rsAll.getString("rPCP"));
			  	data.add(row);
			}
		} catch (Exception e) {
			System.out.println("Could not initialize the database.");
			e.printStackTrace();
		}
			comboBox.putClientProperty("JComboBox.isTableCellEditor", Boolean.FALSE);
	   // comboBox.setRenderer(new aItemRenderer());
		comboBox.addActionListener(new ActionListener() {

			//public void itemStateChanged(ItemEvent e) {
			
		//}
			public void actionPerformed(ActionEvent e) {
//To avoid this exception we have to add Integer y = new Integer((String)obj) instead of String y = (String)obj			
				//@SuppressWarnings({ "rawtypes", "rawtypes", "rawtypes", "unused" })
				//Integer bIndex = 0;
				JComboBox comboBox = (JComboBox)e.getSource();
                Item item = (Item)comboBox.getSelectedItem();
                Item item1 = item;                  				//bIndex = new Integer((Integer)bIndex);
				System.out.println("L# 355, combo   Item  = "+item1);//item.getId());//item.getId());//comboBox.getSelectedItem().toString());//.getId());      //.getItemAt(4)); // .getSelectedIndex());
				value = comboBox.getSelectedItem().toString();
				savebdate = value;
	
				System.out.println("L# 357,  value  bdate = "+value+", savedate = "+savebdate+" sysid = ");
	
				if (bdFlag == 0) {
					bdFlag = 1;
					String bdate = "";
					int vin = value.indexOf(",");
					//System.out.println(" line 140, value = "+value+"    vin = "+vin);
					//        01234567890  
					//value = 01-01-1997, 
					if (vin == 10) {
						bdate = value.substring(0, vin);
					} else {
						bdate = value.substring(0, vin);
					}
					bRain.setText(bdate);
					//System.out.println(" getComponent, bindex = "+bRain.getComponent(n));
					System.out.println("l# 373,  beginning Date = "+bdate.toString());  // line 149,  b Date = 08/30/2006
					//try {
						//DateFormat df = new SimpleDateFormat("yyyy/MM/dd");//"MM/dd/yyyy");
						//s1 = df.format(bdate);//df.parse(bdate);
					//} //catch (ParseException ex) {
						//ex.printStackTrace();
					//}		
					//ibdate = combo.insertItemAt(s1, 0);//.codePointAt(bindex);
				}
			}
		}
		);//******************************************************************
		
		ecmbo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				evlue = ecmbo.getSelectedItem().toString();
				saveedate = evlue;
				//System.out.println("End date "+evlue+", "+saveedate);
				String edate = "";
				int evin = evlue.indexOf(",");
				//System.out.println("evlue = "+evlue+"   2:17 end date vin = "+evin);
				if (evin == 10) {
					edate = evlue.substring(0, evin);
				} else {
					edate = evlue.substring(0, evin);
				}
				eRain.setText(edate);
				//System.out.println("line 188,  e Date = "+edate.toString());  //          .getClass().getName());	
				try {
					DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
					s2 = df.parse(edate);
				} catch (ParseException ex) {
					ex.printStackTrace();
				}	
				//need to test if end date > beginning date, works OK 05/31/2012, 
				// if not, need to repaint p2 bdate*s1*  & p2 edata*s2*
				System.out.println("before call cDate -  s2 is before s1 "+s1+",  s2 "+s2+"  bdFlag = "+bdFlag);
				//cDate(s1,s2);			//plotData();
				System.out.println("after  call cDate -  s2 is before s1 "+s1+",  s2 "+s2+"  bdFlag = "+bdFlag);
			}	
		});
		
        //
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//f.add(new Point2Df());
		f.setSize(1000,500);  //800); 
		f.setLocation(20,20);
		Date d =  new Date();
		DateFormat.getInstance().format(d);
		f.setTitle("ESF, Centriod - "+d);
		f.setVisible(true); 
		
		
		//table = new JTable(new AbstractTableModel());
        JTable table = new JTable(data, columnNames);
        ChangeName(table,1,"Date");
        ChangeName(table,2,"Hour");
        ChangeName(table,3,"Precipitation");
        Remove(table,0);
        table.getSelectedRow();  //.addRowSelectionInterval(index0, index1);
        //table.setModel(new AbstractTableModel);// = new JTable(new AbstractTableModel());
        //table.getSelectionModel().addListSelectionListener(new RowListener());
        table.getSelectionModel().addListSelectionListener(table);
        
        table.setPreferredScrollableViewportSize(new Dimension (520,90));
                
        
		JScrollPane scrollPane = new JScrollPane(table);
		
		//System.out.println("table.getSelectedrow = "+table.getSelectedRow());
		
		JPanel p1 = new JPanel(new GridBagLayout());
		JPanel p2 = new JPanel(new GridBagLayout());
		JPanel p2a= new JPanel(new GridBagLayout());
		JPanel p3 = new JPanel(new GridBagLayout());
				
		//G U I
		GridBagConstraints c = new GridBagConstraints();
        f.getContentPane().add(p1, BorderLayout.NORTH);
		f.setVisible(true); 
		c.gridx = 0;
		c.gridy = 0;
		//c.fill 
		// GridBagConstraints.NORTH;  
		p1.add(scrollPane, c);
			
		f.getContentPane().add(p2, BorderLayout.CENTER);
		c.insets = new Insets(20,20,20,20);
		c.gridx = 0;
		c.gridy = 0;
		p2.add(comboBox,c);
		
		c.gridx = 1;
		c.gridy = 0;
		p2.add(beginRain,c); //label  
		
		c.gridx = 2;
		c.gridy = 0;
		p2.add(bRain, c);
		
		if (reselectFlag = true) {
			//bRain.setText("");
			p2.repaint();
			reselectFlag = false;
			//System.out.println(" Line 269,  in if, Select bSel = "+reselectFlag);
		}
		
		c.gridx = 3;
		c.gridy = 0;
		p2.add(ecmbo,c);
		
		c.gridx = 4;
		c.gridy = 0;
		p2.add(endRain,c);
		
		c.gridx = 5;
		c.gridy = 0;
		p2.add(eRain,c);
	    
		// Plot area
		//p2a.//****** TO DO yet!!!!!!!!!***********************************
		//call GraphicPanal from cScreen
		//cScreen cScr = new cScreen();
		
		//p2a.paintComponents(g);
		
		
		//Buttons
		f.getContentPane().add(p3, BorderLayout.SOUTH);
		c.insets = new Insets(10,10,10,10);
		c.gridx = 0;
		c.gridy = 0;
		p3.add(openButton,c);
		
		c.gridx = 1;
		c.gridy = 0;
		p3.add(bRun,c);
		
		c.gridx = 3;
		c.gridy = 0;
		p3.add(bSve,c);
		
		c.gridx = 9;
		c.gridy = 0;
		p3.add(bSel, c);
		
		c.gridx = 12;
		c.gridy = 0;
		p3.add(bExt,c);
		
		c.gridx = 16;
		c.gridy = 0;
		p3.add(cRun, c);
	} 
	

	class Item {
	        public int id;
	        private String description;
	        public Item(int id, String description) {
	            this.id = id;
	            this.description = description;
	        }
	        public int getId() {
	        	System.out.println(" in getId() ");
	            return id;
	        }
	        public String getDescription() {
	            return description;
	        }
	        public String toString() {
	            return description;
	        }
	}
	 
	protected static void plotData() {
		// get data from db that user selected and copy into array
		System.out.println("in plotData "+savebdate+", ending data =  "+saveedate );
		// beginning date
		// 08/30/2006,  1.76627
		// ending date
	    try {
	    	Class.forName("com.mysql.jdbc.Driver");
	    	Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "hoTTub11");
	    	// UD User selected Data 
	    	Statement stUD = con.createStatement();
	    	//ResultSet rsUD = stUD.executeQuery("select * from rainESF3 where "); //test data
	    }  catch (Exception e) {
			System.out.println("Could not initialize the database.");
			e.printStackTrace();
	    }
	}
	 
	protected static void cDate(Date s1, Date s2) {
		if (s2.before(s1)) {
			System.out.println("cDate -  s2 is before s1 "+s1+",  s2 "+s2);
			JOptionPane.showMessageDialog(null,"Please choose another Date.");
			reselectFlag = true;
			bdFlag = 0;
			//bRain.setText("");
			String bdate = "";
			//rep(f);
			//repaint(p2);
		} else {
			plotData();
		}
		//System.out.println("in cDate -  s1 = "+s1+" s2 = "+s2);		
	}

	
	//public static void rep(Frame f){
	//	f.repaint();
	//}
	
	public void valueChanged(ListSelectionEvent e) {
		System.out.println("in valueChanged");
		 if (e.getSource() == table.getSelectionModel()
	              && table.getRowSelectionAllowed()) {
	            // Column selection changed
	            int first = e.getFirstIndex();
	            int last = e.getLastIndex();
	        } else if (e.getSource() == table.getColumnModel().getSelectionModel()
	               && table.getColumnSelectionAllowed() ){
	            // Row selection changed
	            int first = e.getFirstIndex();
	            int last = e.getLastIndex();
	        }

	}

	public static void ChangeName(JTable table, int col_index, String col_name){
		  table.getColumnModel().getColumn(col_index).setHeaderValue(col_name);
	}

	public static void Remove(JTable table, int col_index){
		  TableColumn tcol = table.getColumnModel().getColumn(col_index);
		  table.removeColumn(tcol);
    }

	
	public boolean isCellEditable(int row, int col) {
		if (col == 3)
			return false;
		else
			return true;
	}
	
	//private class RowListener implements ListSelectionListener {
	//	public void valueChanged(ListSelectionEvent e){
	//		if (e.getValueIsAdjusting()) {
    //				System.out.println("in Row Listener");
	//			return;
		//	}
		//}
	//}
	
	//public static void plotArea(Graphics g) {
		//Graphics2D g = null;
		//super.paintComponent(g);
		//Graphics2D g2d = (Graphics2D) g;
		//g2d.setPaint(Color.BLACK);
		//g2d.setStroke(new BasicStroke());
		//g2d.setFont(new Font("Century Schoolbook", Font.PLAIN, 12));
		//System.out.println("in plotArea()");
	//}
	
	
	//	[]	x	y
	//  0	4	10	
	//	1	9	7
	//	2	11	2	
	//	3	2	2
	public static double pScreen(double c) {
		double xTest = 1.0;
		c = xTest;
		double  area = 0.0;
		//double[] xPts = {4.0,9.0,11.0,2.0};
		//double[] yPts = {10.0,7.0,2.0,2.0};
		double[] xPts = {2,4,6};
		double[] yPts = {3,5,7};
		Point[] polygon = new Point[3];
		polygon[0] = new Point(2,3);
		polygon[1] = new Point(4,5);
		polygon[2] = new Point(6,7);
		// area = -20
		int n = 3;
		
		int j;// = 0;   //xPts.length - 1;
					
			/*for (int i=0;i<N;i++) {
				j = (i + 1) % N;
				System.out.println("  i="+i+" j="+j);				
				area += polygon[i].x * polygon[j].y;
				System.out.println("line 323, area = "+area+" x = "+polygon[i].x+"   y = "+  polygon[j].y);
				area -= polygon[i].y * polygon[j].x;
				System.out.println("line 325, area = "+area+" x = "+polygon[i].x+"   y = "+polygon[j].y);	
			}*/
			//System.out.println("line 453, area = "+area);
		
			  	return c;
	}//****** TO DO yet!!!!!!!!!***********************************
		
	private class RowListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			if (e.getValueIsAdjusting()){
				return;
			}
			
		}
	}
}

// (2+4) *(2+10) = 6  *  12 = 72
// (4+9) *(10+7) = 13 *  17 = 221
// (9+11)*(7+2)  = 20 *   9 = 180
/* 
{ 
  area = 0;         // Accumulates area in the loop
  j = numPoints-1;  // The last vertex is the 'previous' one to the first
  for (i=0; i<numPoints; i++)
    { area = area +  (X[j]+X[i]) * (Y[j]-Y[i]); 
      j = i;  //j is previous vertex to i
    }
  return area/2;
}
(2*3)-(2*0)=  6,  i 0
(2*5)-(4*3)= -2,  i 1
(4*7)-(6*5)= -2,  i 2
(6*0)-(6*7)=-42,  i 3
(6*0)-(2*0)=  0,  1 4
_____________
         -40/2 = -20
The algorithm assumes the usual mathematical convention that positive y points upwards. 
In computer systems where positive y is downwards (most of them) the easiest thing to do is list the 
vertices counter-clockwise using the "positive y down" coordinates.
 The two effects then cancel out to produce a positive area. 

Example
 The following JavaScript code will find the area of the figure on the right. 
 It will output "Area=128" which by inspection matches the figure. 

var xPts = [4,  4,  8,  8, -4,-4];
var yPts = [6, -4, -4, -8, -8, 6];
var a = polygonArea(xPts, yPts, 6);
alert("Area  = " + a);

test beginning date < ending date
 *  if OK, put in an array 
 *           long longDate=date.getTime();
 * function polygonArea(X, Y, numPoints)  * 
 * 
 */







class GraphPnl implements ActionListener {
	DataPnl datapnl;
	JFrame frame;
	GraphicPnl panel;
	static int wF = 0;
	static int wP = 0;
	GraphPnl(JFrame newFrame) {
		frame = newFrame;
		panel = new GraphicPnl();
		panel.setDisplayPlot(false);
		datapnl = new DataPnl(frame);
		panel.setDataPanel(datapnl);
		//int wF = frame.getWidth();
		//int wP = panel.getWidth();
		//System.out.println("L# 697, wF="+wF+"  wP="+wP);
		frame.getContentPane().add(panel, "Center");
	}
	
	public void actionPerformed(ActionEvent e) {
		if (!datapnl.isInitialized()) {
			return;
		}//		System.out.println("Line 481,  in ActionEvent-GraphPanel  datapanel = "+datapanel.initialized);
		datapnl.refreshData();
		panel.setDisplayPlot(true);
		panel.update(panel.getGraphics());
		frame.setSize(700, 600);   // 700, 600
		frame.setPreferredSize(new Dimension(900, 850)); //700,850 965 0193
		int wF = frame.getWidth();
		int wP = panel.getWidth();
		int hF = frame.getHeight();
		int hP = panel.getHeight();
		//System.out.println("L# 714, wF="+wF+"  wP="+wP+", hF="+hF+", hP="+hP);
		frame.setVisible(true);
		frame.pack();
	}
	
	ActionListener getDataPanel() {
		return datapnl;
	}
}

class GraphicPnl extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final int PAD = 20;
	boolean display_plot;
	DataPnl d;
	public int cnt = 0;
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setPaint(Color.BLACK);
		g2d.setStroke(new BasicStroke());
		g2d.setFont(new Font("Century Schoolbook", Font.PLAIN, 12));
		if (d.isInitialized() && display_plot) {
            d.refreshData(); 
            Float xLower = d.getXLower();
            Float xUpper = d.getXUpper();
            Float xInterval = d.getXInterval();
            Float yLower = d.getYLower();
            Float yUpper = d.getYUpper();
            Float yInterval = d.getYInterval();
            Float dx = xUpper - xLower;
            Float dy = yUpper - yLower;
            
            drawCenteredString(g2d, d.getTitle(),  250, 25,  (float) 0.);
            drawCenteredString(g2d, d.getXTitle(), 250, 475, (float) 0.);
            drawCenteredString(g2d, d.getYTitle(), 25,  250, (float) -Math.PI / 2);
            drawCenteredString(g2d, xLower.toString(), 50, 475, (float) 0);
            drawCenteredString(g2d, xUpper.toString(), 450,475, (float) 0);
            drawCenteredString(g2d, yLower.toString(), 25, 450, (float) 0);
            drawCenteredString(g2d, yUpper.toString(), 25,  50, (float) 0);
            //System.out.println("L# 757, Y Title = "+d.getYTitle());
            
            //                                     1.0                         .75
            //System.out.println("L# 760, xInterval = "+d.xInterval+", yInterval = "+d.yInterval+", d.getTitle = "+d.getTitle());
            // need to find the scale?
            // draws the lines in the box
            g2d.setPaint(Color.blue);//gray);// Lines color
            for (Float x = new Float(50); x <= 450; x += 400 * xInterval / dx )
            	g2d.draw(new Line2D.Double(x, 450, x, 50));
            //waiting(5000);            
            for (Float y = new Float(50); y <= 450; y += 400 * yInterval / dy )
            	g2d.draw(new Line2D.Float(45, y, 450, y));
            
            // DATA for plot
            //             
            //            0	    0
            //            0.5	1
            //draw lines only?
            //   A R E A
            // area = 0.5,    x = 0.5,   y = 0.3333333
            //System.out.println("3 for plo.0t   X\t = "+CentroidGUI.pX+"\t Y = "+CentroidGUI.pY+"\tArea = "+CentroidGUI.pArea);
            g2d.setPaint(Color.red); // color of Points
            Float diam = new Float(8);
            int num_points = d.getNumberOfPoints(); // 25
            int w = getWidth(); // 
        	int h = getHeight();// 
        	//System.out.println("L# 784,  w = "+w+"  h = "+h);
            //System.out.println("L# 785,  num_points="+num_points+",  wF="+GraphPnl.wF+", wP="+GraphPnl.wP);
            double xInc = (double) ((w - 2*PAD)/(num_points-1));   
            //System.out.println("L# 787m xInc ="+xInc+" w ="+w+" PAD="+PAD+"  num_points-1="+(num_points-1));//double xInc = (double)(w - 2*PAD)/(data.length-1);                            100,400
            //double xInc = (double) GraphPnl.wF/(num_points-1);// (174-2*400)/(num_points-1);// need to find w,  maybe from line 704
            //   xInc = 174-800/2
            //   xInc = -313     should be ???
           // System.out.println("L# 791, xInc = "+xInc+",  w = "+w+", num_points = "+num_points);
            double scale = (double) (h - 2*PAD)/getMax();                       // need to find h 
            System.out.println("L# 793,  scale="+scale+" getMax = "+getMax());           //getMax();
            for (int i = 0; i < num_points-1; i++) {                            //System.out.println("d,x = "+d.getPoint(i).x+" d,y = "+d.getPoint(i).y);
            	Float ex = 400 * (d.getPoint(i).x - xLower) / dx + 50;        	//System.out.println("  ex = "+ex+" d.getPoint(i).x = "+d.getPoint(i).x+" xLower = "+xLower+"  dx = "+dx);
            	ex -= diam / 2;            	                                   //System.out.println("ex = "+ex);            	//waiting(2000);
            	Float ey = -400 * (d.getPoint(i).y - yLower) / dy + 450;            	//System.out.println("L# 798,  ey = "+ey+" d.getPoint(i).y = "+d.getPoint(i).y+" yLower = "+yLower+"  dy = "+dy);
            	ey -= diam / 2;            	                                   //System.out.println("ey = "+ey);            	//waiting(2000);            	//System.out.println("     ex = "+ex+"   ey = "+ey+" i = "+i+"\t\t d.getPoint(i).x="+d.getPoint(i).x+"\t d.getPoint(i).y="+d.getPoint(i).y);
            	g2d.fill(new Ellipse2D.Float(ex, ey, diam, diam));             //waiting(3000);
            	double x1 = ex;                                                //+2
            	double y1 = ey;                                             	//double x2 = 150; //153,155,148,154,144,104,83,63,43,23;
            	double x2 = (-145+PAD) + (i+1)*xInc;                            //(d.getPoint(i).x+1)*xInc;//double x2 =  (d.getPoint(i).x+1)*xInc; // first 
            	double y2 = h - PAD - scale*d.getPoint(i+1).x; //
            	System.out.println("L# 804,            x1="+x1+" y1="+y1+ " x2="+x2+"  y2="+y2+",  d.getPoint(i).x+1 = "+(d.getPoint(i).x+1)+",  xInc = "+xInc);//           x2= 5,10               
            	//double y2 = 182;                                                //180,175,170,164,154,144,104,84,24,4            	//
            	g2d.draw(new Line2D.Double(x1, y1, x2, y2));
            }
		}
	}
	private float getMax() {
		float max = -Float.MAX_VALUE;
		for(int i = 0; i < d.getNumberOfPoints(); i++) {
			if(d.getPoint(i).x > max)
				max =  d.getPoint(i).x;
				//System.out.println(" max = "+ max+"        x="+d.getPoint(i).x+"    y="+d.getPoint(i).y);
		}
		return max;
	}
	public static void waiting (int n){
	      long t0, t1;
	      t0 =  System.currentTimeMillis();
	      do {
		     t1 = System.currentTimeMillis();
		  }
		  while (t1 - t0 < n);
    }
	public void setDataPanel(DataPnl new_d) {
		d = new_d;
	}
	
	public void setDisplayPlot(boolean new_display) {
		display_plot = new_display;
	}
	// draws the plot box titles runs 21 times or more??
	void drawCenteredString(Graphics2D g2d, String string, int x0, int y0, float angle) {
		FontRenderContext frc = g2d.getFontRenderContext();
		Rectangle2D bounds = g2d.getFont().getStringBounds(string, frc);
		//System.out.println(" string, text = "+string);
		LineMetrics metrics = g2d.getFont().getLineMetrics(string, frc);
		//System.out.println(" string, text  = "+string);
		//System.out.println("L# 852, angle = "+angle+"\tfrc = "+frc.getTransformType()+"\tbounds = "+bounds.toString()+"\tmetrics = "+metrics.toString());
		
		if (angle == 0 ) {
			g2d.drawString(string, x0 - (float) bounds.getWidth() / 2,
					y0 + metrics.getHeight() / 2);
		} else {
			g2d.rotate(angle, x0, y0);
			g2d.drawString(string, x0 - (float) bounds.getWidth() / 2,
					y0 + metrics.getHeight() / 2);
			//System.out.println("L# 861 getWidth = "+bounds.getWidth()+" getHieght = "+bounds.getHeight());
			g2d.rotate(-angle, x0, y0);
		}
		//cnt = cnt +1;
		//System.out.println(" cnt = "+cnt);
	}
}

class DataPnl implements ActionListener {
	boolean initialized;
    int titleIndex, xTitleIndex, yTitleIndex;
	int xLowerIndex, xUpperIndex, xIntervalIndex;
	int yLowerIndex, yUpperIndex, yIntervalIndex;
	JFrame frame;
	JPanel panel;
	JLabel msg;
	String title;
	String xTitle;
	String yTitle;
	float xLower, xUpper, xInterval;
	float yLower, yUpper, yInterval;
	Point2D.Float[] points;
	int numberOfPoints;
	int numberOfPointsAllocated = 0;
	JLabel[] paramLabels;
	JTextField[] paramFields;
	JTextField[] dataFields;//	JTextField[] rainFields;  //04-30-2012
	
		DataPnl(JFrame newFrame) {
			//System.out.println("Line 647, in DataPanel");
			initialized = false;
			numberOfPoints = numberOfPointsAllocated = 0;
	        titleIndex = 0;
	        xTitleIndex = 1;
	        xLowerIndex = 2;
	        xUpperIndex = 3;
	        xIntervalIndex = 4;
	        yTitleIndex = 5;
	        yLowerIndex = 6;
	        yUpperIndex = 7;
	        yIntervalIndex = 8;
	        paramLabels = new JLabel[9];
	        paramLabels[titleIndex] = new JLabel("Title");
	        paramLabels[xTitleIndex] = new JLabel("X Axis Title");
	        paramLabels[yTitleIndex] = new JLabel("Y Axis Title");
	        paramLabels[xLowerIndex] = new JLabel("X lower bound");
	        paramLabels[xUpperIndex] = new JLabel("X upper bound");
	        paramLabels[xIntervalIndex] = new JLabel("X tick interval");
	        paramLabels[yLowerIndex] = new JLabel("Y lower bound");
	        paramLabels[yUpperIndex] = new JLabel("Y upper bound");
	        paramLabels[yIntervalIndex] = new JLabel("Y tick interval");
	        paramFields = new JTextField[9];
	        paramFields[titleIndex] = new JTextField("Test Title");
	        paramFields[xTitleIndex] = new JTextField("X");
	        paramFields[yTitleIndex] = new JTextField("Y");
	        paramFields[xLowerIndex] = new JTextField("0");
	        paramFields[xUpperIndex] = new JTextField("10");
	        paramFields[xIntervalIndex] = new JTextField("1");
	        paramFields[yLowerIndex] = new JTextField("0");
	        paramFields[yUpperIndex] = new JTextField("10");
	        paramFields[yIntervalIndex] = new JTextField("1");
	        frame = newFrame;
	        panel = new JPanel(new FlowLayout());
	        frame.getContentPane().add(panel, "West");  //"West");
		}
		
	public void actionPerformed(ActionEvent e) { // O.K. for now June 11, 2012 - need to locate, save, find file on user(s) main dir 
		//System.out.println("Line 650,  in actionEvent");
		
		JFrame fileFrame = new JFrame();
		JPanel filePanel = new JPanel();
		javax.swing.filechooser.FileFilter filter = new FileNameExtensionFilter("Text File", "txt");
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new java.io.File("."));
	    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY); //.DIRECTORIES_ONLY);
		fileChooser.setFileFilter(filter);
		fileChooser.addChoosableFileFilter((javax.swing.filechooser.FileFilter) filter);
		filePanel.add(fileChooser);
		fileFrame.getContentPane().add(filePanel);
		
		
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		int result = fileChooser.showOpenDialog(filePanel);
		if (result != JFileChooser.APPROVE_OPTION) {
			msg = new JLabel("No File Selected");
			panel.add(msg);
			return;
		}
		
		File dataFile = fileChooser.getSelectedFile();
		initialized = readFile(dataFile);
		panel.update(panel.getGraphics());
		frame.pack();
		frame.setVisible(true);
	}
	
	boolean readFile(File datafile) {
		int numAllocated = 10;
		int numRead = 0;
		int numDataPoints = 0;
		String[] dataStrings = new String[numAllocated];
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(datafile));
			String text = null;
			while ((text = reader.readLine()) != null) {
				if (numRead >= numAllocated) {
					numAllocated  = 2 * numAllocated;
					String[] temp = dataStrings;
					dataStrings = new String[numAllocated];
					System.arraycopy(temp, 0, dataStrings, 0, numRead);
				}
				//System.out.println("text = "+text); //  return 1 line
				dataStrings[numRead] = text;
				numRead = numRead + 1;
				//System.out.println(" numRead = "+numRead);  //numRead = 35
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("IO Exception");			
		}
		try {
			if (reader != null)
				reader.close();
		} catch (IOException e) {
				System.out.println("IO Exception on close");
		}
		
		int thisCase = -2;
		//long thisDataPoint = 0;
		int thisDataPoint = 0;
		for (int i = 0; i < numRead; i++) {
			String[] segments = dataStrings[i].split(" ");
			//System.out.println("line 301, segments = "+segments[0].toString());
			//String[] esfData  = dataStrings[i].
			if (segments[0].equals("Title")) {
                thisCase = titleIndex;
            } else if (segments[0].equals("xTitle")) {
                thisCase = xTitleIndex;
            } else if (segments[0].equals("yTitle")) {
                thisCase = yTitleIndex;
            } else if (segments[0].equals("xLower")) {
                thisCase = xLowerIndex;
            } else if (segments[0].equals("xUpper")) {
                thisCase = xUpperIndex;
            } else if (segments[0].equals("xInterval")) {
                thisCase = xIntervalIndex;
            } else if (segments[0].equals("yLower")) {
                thisCase = yLowerIndex;
            } else if (segments[0].equals("yUpper")) {
                thisCase = yUpperIndex;
            } else if (segments[0].equals("yInterval")) {
                thisCase = yIntervalIndex;
            } else if (segments[0].equals("Data")) {
                thisCase = -1;
                numDataPoints = numRead - i - 1;
                dataFields = new JTextField[2 * numDataPoints];
                points = new Point2D.Float[numDataPoints];
                //System.out.println(" dataFields = "+dataFields.toString());
                //System.out.println("line 811, points = "+points.toString());
            } else if (thisCase != -1) {
                thisCase = -2;
            }
			//need to find how many data points from MySQL or text file
			//from text file
		/*  Data
			8/30/06 6:15 AM	0.255085205
			8/30/06 6:30 AM	0.340042005
			8/30/06 6:45 AM	0.250616583
			8/30/06 7:00 AM	0.459282214
			8/30/06 7:15 AM	0.239816963
			8/30/06 7:30 AM	0.516535054
			8/30/06 7:45 AM	0.919816963
 * */
			if (thisCase >= 0 && segments.length > 1) {
				String temp = segments[1];
				//System.out.println(" temp = "+temp.toString()+"   length of segments = "+segments.length);				
				for (int j = 2; j < segments.length; j++) 
					temp += " " + segments[j];
					paramFields[thisCase].setText(temp);
					thisCase = -2;
				} else if (thisCase == -1 && !segments[0].equals("Data")  && thisDataPoint < numDataPoints ) {
					dataFields[2 * thisDataPoint] = new JTextField(segments[0]);
					//System.out.println("before line 834, dataField = thisDataPoint, "+thisDataPoint);
					dataFields[2 * thisDataPoint + 1] = new JTextField(segments[1]);
					thisDataPoint++;
			    }
				
		} //for
		
		frame.getContentPane().remove(panel);
		panel = new JPanel(new GridLayout(9 + numDataPoints, 2)); //2));
		for (int i = 0; i < 9; i++) {
			panel.add(paramLabels[i]);
			panel.add(paramFields[i]);
		}
		for (int i = 0; i < numDataPoints; i++) {
			panel.add(dataFields[2 * i]);
			panel.add(dataFields[2 * i + 1]);
			//System.out.println(" i = "+i+"    dataFields = "+dataFields[2*i].getText());
			//System.out.println("    dataFields 2*i+1 = "+dataFields[2*i+1].getText());
		}
		frame.getContentPane().add(panel, "West");
		//int w = frame.getContentPane().getWidth();// .getSize();//.getMinimumSize();
		//System.out.println("L# 1064 w="+w);
		return true;
	}
	
	void refreshData() {
		if (!initialized) {
			return;
		}
		title = paramFields[titleIndex].getText();
        xTitle = paramFields[xTitleIndex].getText();
        yTitle = paramFields[yTitleIndex].getText();
        xLower = Float.parseFloat(paramFields[xLowerIndex].getText());
        xUpper = Float.parseFloat(paramFields[xUpperIndex].getText());
        xInterval = Float.parseFloat(paramFields[xIntervalIndex].getText());
        yLower = Float.parseFloat(paramFields[yLowerIndex].getText());
        yUpper = Float.parseFloat(paramFields[yUpperIndex].getText());
        yInterval = Float.parseFloat(paramFields[yIntervalIndex].getText());
        for (int i = 0; i < points.length; i++) {
            Float x = Float.parseFloat(dataFields[2 * i].getText());
            Float y = Float.parseFloat(dataFields[2 * i + 1].getText());
            points[i] = new Point2D.Float(x, y);
        }
	}
	
	boolean isInitialized() {
		return initialized;
	}
	
	String getTitle() {
	   return title;
	}
	
    String getXTitle() {
       return xTitle;
    }
    
    String getYTitle() {
       return yTitle;
    }
    
    float getXLower() {
        return xLower;
    }
    
    float getXUpper() {
        return xUpper;
    }
    
    float getXInterval() {
        return xInterval;
    }
    
    float getYLower() {
        return yLower;
    }
    
    float getYUpper() {
        return yUpper;
    }
    
    float getYInterval() {
        return yInterval;
    }
    
    int getNumberOfPoints() {
    	return points.length;
    }
    
    Point2D.Float getPoint(int i) {
    	if (i < 0) {
    		i = 0;
    	} else if (i >= points.length) {
    		i = points.length - 1;
    	}
    	return points[i];
    }
}

	/*
	 * 
	 * from Copy of Centroid.xls 
8/30/06 6:15 AM	0.255085205
8/30/06 6:30 AM	0.340042005
8/30/06 6:45 AM	0.250616583
8/30/06 7:00 AM	0.459282214
8/30/06 7:15 AM	0.239816963
8/30/06 7:30 AM	0.516535054
8/30/06 7:45 AM	0.919816963
8/30/06 8:00 AM	1.207390616
8/30/06 8:15 AM	1.766274002
8/30/06 8:30 AM	2.331328721
8/30/06 8:45 AM	2.925085205
8/30/06 9:00 AM	3.259449401
8/30/06 9:15 AM	3.498838818
8/30/06 9:30 AM	4.038764708
8/30/06 9:45 AM	4.975916289
8/30/06 10:00 AM	5.537006761
8/30/06 10:15 AM	5.704065418
8/30/06 10:30 AM	5.688852717
8/30/06 10:45 AM	6.351754808
8/30/06 11:00 AM	6.931754808
8/30/06 11:15 AM	7.302946302
8/30/06 11:30 AM	8.102075577
8/30/06 11:45 AM	8.418146805
8/30/06 12:00 PM	8.757513671
8/30/06 12:15 PM	8.934636513
8/30/06 12:30 PM	8.797075895
8/30/06 12:45 PM	9.070091562
8/30/06 1:00 PM	9.054753334
8/30/06 1:15 PM	9.094040602
8/30/06 1:30 PM	9.121034655
8/30/06 1:45 PM	9.072781548
8/30/06 2:00 PM	8.995790088
8/30/06 2:15 PM	9.116273392
8/30/06 2:30 PM	9.149762649
8/30/06 2:45 PM	9.166733413
8/30/06 3:00 PM	9.38931728
8/30/06 3:15 PM	9.593517401
8/30/06 3:30 PM	9.566964373
8/30/06 3:45 PM	9.679008803
8/30/06 4:00 PM	9.412130073
8/30/06 4:15 PM	9.263517401
8/30/06 4:30 PM	9.383517401
8/30/06 4:45 PM	9.076615829
8/30/06 5:00 PM	8.824513551
8/30/06 5:15 PM	8.7127821
8/30/06 5:30 PM	8.46758391
8/30/06 5:45 PM	8.298907446
8/30/06 6:00 PM	7.888474492
8/30/06 6:15 PM	7.479762649
8/30/06 6:30 PM	7.210539682
8/30/06 6:45 PM	6.895283427
8/30/06 7:00 PM	6.741776003
8/30/06 7:15 PM	6.464753334
8/30/06 7:30 PM	6.204199731
8/30/06 7:45 PM	5.941857382
8/30/06 8:00 PM	5.651252584
8/30/06 8:15 PM	5.420624035
8/30/06 8:30 PM	5.164636513
8/30/06 8:45 PM	4.972164543
8/30/06 9:00 PM	4.689659862
8/30/06 9:15 PM	4.455328584
8/30/06 9:30 PM	4.269154403
8/30/06 9:45 PM	4.084731376
8/30/06 10:00 PM	3.854828701
8/30/06 10:15 PM	3.674843357
8/30/06 10:30 PM	3.456602711
8/30/06 10:45 PM	3.268292967
8/30/06 11:00 PM	3.061754808
8/30/06 11:15 PM	2.845160104
8/30/06 11:30 PM	2.598508579
8/30/06 11:45 PM	2.473659298
8/31/06 12:00 AM	2.248764708
8/31/06 12:15 AM	2.077563914
8/31/06 12:30 AM	1.988210307
8/31/06 12:45 AM	1.780714549
8/31/06 1:00 AM	1.635085205
8/31/06 1:15 AM	1.459449401
8/31/06 1:30 AM	1.325688602
8/31/06 1:45 AM	1.280042005
8/31/06 2:00 AM	1.128158364
8/31/06 2:15 AM	1.072503112
8/31/06 2:30 AM	0.946841356
8/31/06 2:45 AM	0.831173086
8/31/06 3:00 AM	0.725498292
8/31/06 3:15 AM	0.687921732
8/31/06 3:30 AM	0.592231673
8/31/06 3:45 AM	0.542733658
8/31/06 4:00 AM	0.427026082
8/31/06 4:15 AM	0.343682766
8/31/06 4:30 AM	0.290303444
8/31/06 4:45 AM	0.268806694
8/31/06 5:00 AM	0.23343624
8/31/06 5:15 AM	0.138018224
8/31/06 5:30 AM	0.132552438
8/31/06 5:45 AM	0.027038677
8/31/06 6:00 AM	0.021476732
8/31/06 6:15 AM	0

	 * 
	 */
/*polygon[0] = new Point2D.Float((float)61957140300000.0f,   (float)  0.255085205);
polygon[1] = new Point2D.Float((float)61957139400000.0f,   (float)  0.340042005);
polygon[2] = new Point2D.Float((float)61957138500000.0f,   (float)  0.250616583);
polygon[3] = new Point2D.Float((float)61957137600000.0f,   (float)  0.459282214);
polygon[4] = new Point2D.Float((float)61957136700000.0f,   (float)  0.239816963);
polygon[5] = new Point2D.Float((float)61957135800000.0f,   (float)  0.516535054);
polygon[6] = new Point2D.Float((float)61957134900000.0f,   (float)  0.919816963);
polygon[7] = new Point2D.Float((float)61957134000000.0f,   (float)  1.207390616);
polygon[8] = new Point2D.Float((float)61957133100000.0f,   (float)  1.766274002);
polygon[9] = new Point2D.Float((float)61957132200000.0f,   (float)  2.331328721);
polygon[10] = new Point2D.Float((float)61957131300000.0f,   (float)  2.925085205);
polygon[11] = new Point2D.Float((float)61957130400000.0f,   (float)  3.259449401);
polygon[12] = new Point2D.Float((float)61957129500000.0f,   (float)  3.498838818);
polygon[13] = new Point2D.Float((float)61957128600000.0f,   (float)  4.038764708);
polygon[14] = new Point2D.Float((float)61957127700000.0f,   (float)  4.975916289);
polygon[15] = new Point2D.Float((float)61957126800000.0f,   (float)  	5.537006761);
polygon[16] = new Point2D.Float((float)61957125900000.0f,   (float)  	5.704065418);
polygon[17] = new Point2D.Float((float)61957125000000.0f,   (float)  	5.688852717);
polygon[18] = new Point2D.Float((float)61957124100000.0f,   (float)  	6.351754808);
polygon[19] = new Point2D.Float((float)61957123200000.0f,   (float)  	6.931754808);
polygon[20] = new Point2D.Float((float)61957122300000.0f,   (float)  	7.302946302);
polygon[21] = new Point2D.Float((float)61957121400000.0f,   (float)  	8.102075577);
polygon[22] = new Point2D.Float((float)61957120500000.0f,   (float)  	8.418146805);
polygon[23] = new Point2D.Float((float)61957119600000.0f,   (float)  	8.757513671);
polygon[24] = new Point2D.Float((float)61957118700000.0f,   (float)  	8.934636513);
polygon[25] = new Point2D.Float((float)61957117800000.0f,   (float)  	8.797075895);
polygon[26] = new Point2D.Float((float)61957116900000.0f,   (float)  	9.070091562);
polygon[27] = new Point2D.Float((float)61957159200000.0f,   (float)  9.054753334);
polygon[28] = new Point2D.Float((float)61957158300000.0f,   (float)  9.094040602);
polygon[29] = new Point2D.Float((float)61957157400000.0f,   (float)  9.121034655);
polygon[30] = new Point2D.Float((float)61957156500000.0f,   (float)  9.072781548);
polygon[31] = new Point2D.Float((float)61957155600000.0f,   (float)  8.995790088);
polygon[32] = new Point2D.Float((float)61957154700000.0f,   (float)  9.116273392);
polygon[33] = new Point2D.Float((float)61957153800000.0f,   (float)  9.149762649);
polygon[34] = new Point2D.Float((float)61957152900000.0f,   (float)  9.166733413);
polygon[35] = new Point2D.Float((float)61957152000000.0f,   (float)  9.38931728);
polygon[36] = new Point2D.Float((float)61957151100000.0f,   (float)  9.593517401);
polygon[37] = new Point2D.Float((float)61957150200000.0f,   (float)  9.566964373);
polygon[38] = new Point2D.Float((float)61957149300000.0f,   (float)  9.679008803);
polygon[39] = new Point2D.Float((float)61957148400000.0f,   (float)  9.412130073);
polygon[40] = new Point2D.Float((float)61957147500000.0f,   (float)  9.263517401);
polygon[41] = new Point2D.Float((float)61957146600000.0f,   (float)  9.383517401);
polygon[42] = new Point2D.Float((float)61957145700000.0f,   (float)  9.076615829);
polygon[43] = new Point2D.Float((float)61957144800000.0f,   (float)  8.824513551);
polygon[44] = new Point2D.Float((float)61957143900000.0f,   (float)  8.7127821);
polygon[45] = new Point2D.Float((float)61957143000000.0f,   (float)  8.46758391);
polygon[46] = new Point2D.Float((float)61957142100000.0f,   (float)  8.298907446);
polygon[47] = new Point2D.Float((float)61957141200000.0f,   (float)  7.888474492);
polygon[48] = new Point2D.Float((float)61957140300000.0f,   (float)  7.479762649);
polygon[49] = new Point2D.Float((float)61957139400000.0f,   (float)  7.210539682);
polygon[50] = new Point2D.Float((float)61957138500000.0f,   (float)  6.895283427);
polygon[51] = new Point2D.Float((float)61957137600000.0f,   (float)  6.741776003);
polygon[52] = new Point2D.Float((float)61957136700000.0f,   (float)  6.464753334);
polygon[53] = new Point2D.Float((float)61957135800000.0f,   (float)  6.204199731);
polygon[54] = new Point2D.Float((float)61957134900000.0f,   (float)  5.941857382);
polygon[55] = new Point2D.Float((float)61957134000000.0f,   (float)  5.651252584);
polygon[56] = new Point2D.Float((float)61957133100000.0f,   (float)  5.420624035);
polygon[57] = new Point2D.Float((float)61957132200000.0f,   (float)  5.164636513);
polygon[58] = new Point2D.Float((float)61957131300000.0f,   (float)  4.972164543);
polygon[59] = new Point2D.Float((float)61957130400000.0f,   (float)  4.689659862);
polygon[60] = new Point2D.Float((float)61957129500000.0f,   (float)  4.455328584);
polygon[61] = new Point2D.Float((float)61957128600000.0f,   (float)  4.269154403);
polygon[62] = new Point2D.Float((float)61957127700000.0f,   (float)  4.084731376);
polygon[63] = new Point2D.Float((float)61957126800000.0f,   (float)  	3.854828701);
polygon[64] = new Point2D.Float((float)61957125900000.0f,   (float)  	3.674843357);
polygon[65] = new Point2D.Float((float)61957125000000.0f,   (float)  	3.456602711);
polygon[66] = new Point2D.Float((float)61957124100000.0f,   (float)  	3.268292967);
polygon[67] = new Point2D.Float((float)61957123200000.0f,   (float)  	3.061754808);
polygon[68] = new Point2D.Float((float)61957122300000.0f,   (float)  	2.845160104);
polygon[69] = new Point2D.Float((float)61957121400000.0f,   (float)  	2.598508579);
polygon[70] = new Point2D.Float((float)61957120500000.0f,   (float)  	2.473659298);
polygon[71] = new Point2D.Float((float)61957033200000.0f,   (float)  	2.248764708);
polygon[72] = new Point2D.Float((float)61957032300000.0f,   (float)  	2.077563914);
polygon[73] = new Point2D.Float((float)61957031400000.0f,   (float)  	1.988210307);
polygon[74] = new Point2D.Float((float)61957030500000.0f,   (float)  	1.780714549);
polygon[75] = new Point2D.Float((float)61957072800000.0f,   (float)  1.635085205);
polygon[76] = new Point2D.Float((float)61957071900000.0f,   (float)  1.459449401);
polygon[77] = new Point2D.Float((float)61957071000000.0f,   (float)  1.325688602);
polygon[78] = new Point2D.Float((float)61957070100000.0f,   (float)  1.280042005);
polygon[79] = new Point2D.Float((float)61957069200000.0f,   (float)  1.128158364);
polygon[80] = new Point2D.Float((float)61957068300000.0f,   (float)  1.072503112);
polygon[81] = new Point2D.Float((float)61957067400000.0f,   (float)  0.946841356);
polygon[82] = new Point2D.Float((float)61957066500000.0f,   (float)  0.831173086);
polygon[83] = new Point2D.Float((float)61957065600000.0f,   (float)  0.725498292);
polygon[84] = new Point2D.Float((float)61957064700000.0f,   (float)  0.687921732);
polygon[85] = new Point2D.Float((float)61957063800000.0f,   (float)  0.592231673);
polygon[86] = new Point2D.Float((float)61957062900000.0f,   (float)  0.542733658);
polygon[87] = new Point2D.Float((float)61957062000000.0f,   (float)  0.427026082);
polygon[88] = new Point2D.Float((float)61957061100000.0f,   (float)  0.343682766);
polygon[89] = new Point2D.Float((float)61957060200000.0f,   (float)  0.290303444);
polygon[90] = new Point2D.Float((float)61957059300000.0f,   (float)  0.268806694);
polygon[91] = new Point2D.Float((float)61957058400000.0f,   (float)  0.23343624);
polygon[92] = new Point2D.Float((float)61957057500000.0f,   (float)  0.138018224);
polygon[93] = new Point2D.Float((float)61957056600000.0f,   (float)  0.132552438);
polygon[94] = new Point2D.Float((float)61957055700000.0f,   (float)  0.027038677);
polygon[95] = new Point2D.Float((float)61957054800000.0f,   (float)  0.021476732);
polygon[96] = new Point2D.Float((float)61957053900000.0f,   (float)  0.0);
*/

/*
 * I'm assuming you want to show a comobox with names, but be able to map those names back to an ID. In access you hide things, but with Swing you don't have to make those types of "hacks" to get it mapped back to your ID. Swing's MVC architecture makes it much easier to map your display in the UI back to your data in your model.

If you don't already have an object that represents your data, you'll need to create one similiar to the one below. Think of this object as representing one row out of your database table.
[code]
public class Person {
private int id;
private String name;

public Person( int anId, String aName ) {
id = anId;
name = aName;
}

public String getName() {
return name;
}

public int getId() {
return id;
}

public String toString() {
return getName();
}
}
[/code]

Code to create a JComboBox that displays a list of person.
[code]
// I'm using a vector here because it's the easiest to
// hook up with a Swing UI. But, vector performance
// is less than, say a java.util.List, because vectors are synchronized.
Vector listOfPeople = new Vector();
listOfPeople.add( new Person( 4, "Billy" ));
listOfPeople.add( new Person( 5, "Joe" ));
listOfPeople.add( new Person( 6, "Chuck" ));
listOfPeople.add( new Person( 7, "Andy" ));
JComboBox box = new JComboBox( listOfPeople );
box.addItemListener( new ItemListener() {
public void itemStateChanged(ItemEvent e) {
Person selectedPerson = (Person)box.getSelectedValue();
System.out.println( selectedPerson.getId() + " " + selectedPerson.getName() );
}
});

JFrame frame = new JFrame( "My ComboBox Test" );
frame.getContentPane().add( listOfPeople );
frame.pack();
frame.setVisible(true);


 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * for(double i = -6.0; i<=6.0;i+=0.25){
      double j = (double) Math.sqrt(36 - Math.pow(i, 2));
       p[k] = new point(i, j);
        k++;
}*/  

//double j = polygon[i].getX()		
//polygon[0] = new Point(1,1);
//polygon[1] = new Point(1,3);
//polygon[2] = new Point(3,1);

