import java.awt.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import java.awt.event.*;

public class  OJTable02282012 implements TableModelListener, ActionListener, ListSelectionListener {  
	
	public static void main(String[] args){
		boolean ALLOW_COLUMN_SELECTION = false;
		boolean ALLOW_ROW_SELECTION = true;
		QueryModelTable qm;
		qm = new QueryModelTable();
		
		WinDestroyer dest = new WinDestroyer();
		dest.setQTM(qm);
		
		JFrame f=new JFrame();
		JLabel labelH = new JLabel("HASP #: ");
		JLabel label2 = new JLabel("Inactive: ");
		JLabel labelFA = new JLabel("Field Activities: ");
		JLabel labelTS = new JLabel("Treatability Study: ");
		JLabel labelLA = new JLabel("Lab Activities: ");
		JLabel labelHW = new JLabel("Hazardous Waste Generator: ");
		JLabel label3 = new JLabel("Preparer: ");
		JLabel lTitle = new JLabel("Title:");
		JLabel lOffice = new JLabel("Office: ");
		JLabel lPI1    = new JLabel("PI ");
		
		JTextField vNum = new JTextField();
		JTextField vInact = new JTextField(5);
		JTextField vdbFA = new JTextField(5);
		JTextField vdbTS = new JTextField(5);
		JTextField vdbLA = new JTextField(5);
		JTextField vdbHW = new JTextField(5);
		JTextField vTitle = new JTextField();
		JTextField vPreparer =new JTextField(15);
		JTextField vOffice = new JTextField(10);
		JTextField vPI = new JTextField();
		
		JButton bNew = new JButton("New");
		JButton bNxt = new JButton("Next");
		JButton bDel = new JButton("Delete");
		JButton bSve = new JButton("Save");
		JButton bRef = new JButton("Refresh");
		JButton bCal = new JButton("Cancel");
		
		//JTable table;
		Vector<String> columnNames = new Vector<String>();
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		

		try{ 
			//Class.forName("com.mysql.jdbc.Driver");
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//System.out.println("line 22, Before for LOOP");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@comanche.wic.epa.gov:1521:cintst","CSHEM","welcome1");
			//Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "hoTTub11");
			//System.out.println("line 25, Before for LOOP");
			Statement stAll = con.createStatement();
			ResultSet rsAll = stAll.executeQuery("select * from SHASPTEST");
			ResultSetMetaData md = rsAll.getMetaData();
			int columns = md.getColumnCount();
			//System.out.println("Before for LOOP");
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
			//ResultSet rs=st.executeQuery("select * from SHASPTEST where sysid = 2");
			//select * from SHASPTEST s, HASP_PI p where s.pi_sysid = p.PI_SYSID AND S.sysid = 2
			ResultSet rs=st.executeQuery("select * from SHASPTEST s, HASP_PI p where s.pi_sysid = p.PI_SYSID AND S.sysid = 2");
			String name="",inact="",dbFA="",dbTS="",dbLA="",dbHW="",Preparer="",dbPI="",dbTitle="",dbOffice="";
			
			if(rs.next()){
				name   = rs.getString("HASPNUM");
				inact  = rs.getString("INACTIVE");
				dbFA = rs.getString("FIELDACTIVITIES");
				dbTS = rs.getString("TREATABILITYSTUDY");
				dbLA  = rs.getString("LABACTIVITIES");
				dbHW  = rs.getString("HAZARDOUSWASTEGENERATOR");
				Preparer = rs.getString("HASP_Preparer");
				dbTitle  = rs.getString("Title");
				dbOffice = rs.getString("Office");
				dbPI     = rs.getString("PI_Name"); 
			}
			vNum.setText(name);
			vPreparer.setText(Preparer);
			vInact.setText(inact);
			vdbFA.setText(dbFA);
			vdbTS.setText(dbTS);
			vdbLA.setText(dbLA);
			vdbHW.setText(dbHW);
			vTitle.setText(dbTitle);
			vOffice.setText(dbOffice);
			vPI.setText(dbPI);
			}
		catch(Exception e){     
			
		}
		
		final JTable table = new JTable(data, columnNames);
        
		table.setPreferredScrollableViewportSize(new Dimension (1000,80));
		
		JScrollPane scrollPane = new JScrollPane(table);
		if (ALLOW_ROW_SELECTION) { //true
			ListSelectionModel rowSM = table.getSelectionModel();
			rowSM.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					if (e.getValueIsAdjusting()) return;
					ListSelectionModel lsm = (ListSelectionModel)e.getSource();
					if (lsm.isSelectionEmpty()) {
						System.out.println("No Rows are selected.");
					} else {
						int selectedRow = lsm.getMinSelectionIndex();
						System.out.println("Row  "+selectedRow+" - is now selected");
					}
				}
			});
		}else{
			table.setRowSelectionAllowed(false);
		}
			
		JPanel p1 = new JPanel(new GridBagLayout());
		JPanel p2 = new JPanel(new GridBagLayout());
		JPanel p3 = new JPanel(new GridBagLayout());
		
			
		GridBagConstraints c = new GridBagConstraints();
	
		c.gridx = 0;
		c.gridy = 0;
		p1.add(scrollPane,c);
		
		// new panel p2
		c.insets = new Insets(1,1,1,1);
        c.weightx = 1.0;
        c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 0;
		c.anchor =  GridBagConstraints.LINE_START;
		p2.add(labelH,c);
		
		c.gridx = 1;
		c.gridy = 0;
        p2.add(vNum,c);
               
        //c.anchor = GridBagConstraints.CENTER;
        c.gridx = 2;
        c.gridy = 0;
        //c.ipadx = 1;
        p2.add(label2,c);
        
        c.gridx = 3;
        c.gridy = 0;
        p2.add(vInact,c);
        
        c.gridx = 4;
        c.gridy = 0;
        p2.add(labelFA, c);
        
        c.gridx = 5;
        c.gridy = 0;
        p2.add(vdbFA, c);
        
        c.gridx = 6;
        c.gridy = 0;
        p2.add(labelTS, c);
        
        c.gridx = 7;
        c.gridy = 0;
        p2.add(vdbTS, c);
        
        //c.gridx = 8;
        //c.gridy = 0;
        //p2.add(labelHW, c);
        
        //c.gridx = 9;
        //c.gridy = 0;
        //p2.add(vdbHW, c);
               
        // Line 2 
        c.gridx = 0;
        c.gridy = 1;
        p2.add(lTitle,c);
        
        //c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        p2.add(vTitle,c);
                
        // Line 3
        c.gridx = 0;
		c.gridy = 2;
        p2.add(label3,c);
       
        c.gridx = 1;
		c.gridy = 2;
        p2.add(vPreparer,c);
         
        //Line 4
        c.gridx = 0;
		c.gridy = 3;
        p2.add(lOffice,c);
       
        c.gridx = 1;
		c.gridy = 3;
        p2.add(vOffice,c);
                
        c.gridx = 3;
        c.gridy = 3;
        p2.add(lPI1,c);
        
        c.gridx = 4;
        c.gridy = 3;
        p2.add(vPI,c);
        
        
        
        
        //new panel p3
        //c.anchor = GridBagConstraints.PAGE_START;
        c.gridx = 0;
        c.gridy = 0;
        p3.add(bNew,c);
        
        c.gridx = 1;
        c.gridy = 0;
        p3.add(bNxt,c);
        
        c.gridx = 2;
        c.gridy = 0;
        p3.add(bDel,c);
        
        c.gridx = 3;
        c.gridy = 0;
        p3.add(bSve,c);
        
        c.gridx = 4;
        c.gridy = 0;
        p3.add(bCal,c);
        
        c.gridx = 5;
        c.gridy = 0;
        p3.add(bRef, c);
        
        f.getContentPane().add(p1, BorderLayout.NORTH);
        f.getContentPane().add(p2, BorderLayout.CENTER);
        f.getContentPane().add(p3, BorderLayout.SOUTH);
        f.setLocation(0,0);
        f.setSize(1100, 900);
        f.setVisible(true); 
        f.pack();
	}
	
	public void actionPerformed(ActionEvent e){
		if (e.getActionCommand().equals("Refresh")){
			
		}
	}
	
	private class ColumnListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e){
			if (e.getValueIsAdjusting()){
				return;
			}
		}
		
		
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tableChanged(TableModelEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
class QueryModelTable extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Vector<String[]> cache;
	
	private Map<Integer, String[]> updatedData;
	
	private int[] columnType, columnPrecision;
	
	private int colCount;
	
	private Statement statement;
	
	private ResultSetMetaData meta;
	
	private String[] headers;
	
	public QueryModelTable(){
		
		cache = new Vector<String[]>();
		
		updatedData = new HashMap<Integer, String[]>();
		
	}
	
	public void refreshData(){
		cache.clear();
		try {
			ResultSet rs = statement.executeQuery("select * from SHASPTEST");
			meta = rs.getMetaData();
			colCount = meta.getColumnCount();
			//int colType;
			//String colName;
			headers = new String[colCount];
			for (int h = 0; h < colCount; h++) {
				headers[h] = meta.getColumnName(h+1);
				//System.out.println(h+"  Line 486, Type = "+meta.getColumnType(h+1)+"     Name of column "+meta.getColumnName(h+1));
			}
			while (rs.next()) {
				String[] record = new String[colCount];
				for (int i = 0; i < colCount; i++) {
					record[i] = rs.getString(i + 1);
				}
				//System.out.println(" get Selected Row "+cache.);
				cache.addElement(record);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int[] getColumnPrecision(){
		return columnPrecision;
	}

	public int[] getColumnTypes(){
		return columnType;
	}
	public Map<Integer, String[]> getUpdatedData(){
		return updatedData;
	}
	
	public Vector<String[]> getTableCache(){
		return this.cache;
	}
	
	public String[] getHeaders(){
		return headers;
	}
	
	public void setUpdatedData(String[] data, Integer key) {
		updatedData.put(key, data);		
	} 
	//@Override
	public int getColumnCount() {
		return colCount;
	}

	//@Override
	public int getRowCount() {
		return cache.size();
	}
	
	public String[] getUpdatedRow(int row) {
		return cache.elementAt(row);
	}

	//@Override
	public Object getValueAt(int row, int col) {
		return ((String[]) cache.elementAt(row)) [col];
	}
	
} 

/*
 * Helping class for proper closing the application
 */
class WinDestroyer  extends InternalFrameAdapter {

	private QueryModelTable qm;

	public void setQTM(QueryModelTable qm) {
		this.qm = qm;
	}

	public void internalFrameClosed(InternalFrameEvent e) {
		//qm.closeDB();
		System.gc();
		
	}

}
//To do List
// add Row Listener
// add JComboBox for names










