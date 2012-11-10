// ESF

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class MainClass extends JInternalFrame implements TableModelListener, ActionListener {

	private static final long serialVersionUID = 1L;

	private QueryTableModel qtm;
	
	private EnterValuesChecker check;

	private JTable table;

	private JTextField hostField;

	private JTextField queryField;

	private JButton saveData, addRow, deleteRow, setupData, exitButton, refreshButton;
	
	private JScrollPane scrollpane;
	
	private boolean isValueCorrect = true;
	
	public String vType = " ";

	JDesktopPane desktop;
	
	public MainClass() {
		
		qtm = new QueryTableModel();
			
		/*
		 * listener for JInternalFrame 
		 *  correctly close the application after its termination
		 *  
		 */
		WindowDestroyer dest = new WindowDestroyer();
		
		dest.setQTM(qtm);// pass "qtm" object to the WindowDestroyer 	object						 
		this.addInternalFrameListener(dest);

		setSize(835, 600);//1235
		setMinimumSize(new Dimension(400, 200));//800, 600
		setTitle("Periphyton Table");
		
		//setIconImage(Toolkit.getDefaultToolkit().getImage("images/epa_logo.png"));
		setFrameIcon(new ImageIcon("images/epa_logo.png"));
		
		table = new JTable();
		
		table.setModel(qtm);
		table.setAutoCreateColumnsFromModel(true);
		table.setRowSelectionAllowed(true);
		table.getTableHeader().setReorderingAllowed(false);
		
			
		table.getModel().addTableModelListener(this);// adds TableModelListener
		
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollpane = new JScrollPane(table);
		
		
		
		JPanel commandPanel = new JPanel();

		commandPanel.setLayout(new GridLayout(1, 2));
		saveData = new JButton("Save Changes");
		addRow = new JButton("Add Row");
		deleteRow = new JButton("Delete Row");
		setupData = new JButton("Set up Data");
		refreshButton = new JButton("Refresh Table");
		exitButton = new JButton("Exit");

		deleteRow.setForeground(Color.RED);
		refreshButton.setForeground(Color.GREEN);
		
		//hostField = new JTextField("jdbc:mysql://localhost:3306");
		hostField = new JTextField("jdbc:mysql://134.67.216.25:3306");
		
		//queryField = new JTextField("SELECT sysid,pdate,sid FROM test.jerryperiphyton");
		//queryField = new JTextField("SELECT * FROM test.jerryperiphyton");
        queryField = new JTextField("SELECT * FROM Jerry.ESFperiphyton");
		
		commandPanel.add(addRow);
		commandPanel.add(deleteRow);
		commandPanel.add(saveData);
		commandPanel.add(setupData);
		commandPanel.add(refreshButton);
		commandPanel.add(exitButton);

		saveData.addActionListener(this);
		addRow.addActionListener(this);
		deleteRow.addActionListener(this);
		setupData.addActionListener(this);
		refreshButton.addActionListener(this);
		exitButton.addActionListener(this);

		qtm.setHostURL(hostField.getText().trim());  //to line 706
		qtm.setQuerySQL(queryField.getText().trim());
		qtm.setQuery();
		
		check = new EnterValuesChecker(qtm.getColumnTypes(), qtm.getColumnPrecision());
		

		getContentPane().add(commandPanel, BorderLayout.SOUTH);
		getContentPane().add(scrollpane, BorderLayout.CENTER);

		// lines up JFrame on the screen
		//Dimension dim = getToolkit().getScreenSize();
		//System.out.println("dim = " + dim);
		//System.out.println("getWidth() = " + getWidth());
		//setLocation(dim.width / 2 - getWidth() / 2, dim.height / 2 - getHeight() / 2);
		//setLocation(dim.width / 4 - getWidth() / 4, dim.height / 4 - getHeight() / 4);
		setLocation(4,4);
		setBounds(4,4,1260,900);//4,4,1660,900
		
		formatCells();
		
						
		setVisible(true);
		
	}

	/**
	 * Formats Table cells
	 */
	private void formatCells() {
		TableColumnModel colMod = table.getColumnModel();
		
	if(qtm.getColumnCount() == colMod.getColumnCount())	
		colMod.removeColumn(colMod.getColumn(0)); //this may remove the sysid???
	   
	    //System.out.println("qtm = "+qtm.getColumnCount()+" Column count = "+colMod.getColumnCount());
	
	    TableColumn column1; 
		
		/*right-alligns data in cells 
		 * 
		 * DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) table.getDefaultRenderer(table.getColumnClass(1));
		renderer.setHorizontalAlignment(SwingUtilities.RIGHT);*/
		
		
		column1 = colMod.getColumn(1);//0
		column1.setMinWidth(80);// 70 sets width of the date column 
		int numberofColumns = table.getModel().getColumnCount()-2;
		//System.out.println(numberofColumns);
		
		//sets width of all except the last column
		for(int k = 1; k < numberofColumns; k++){// was 27 now numberofColumns
			column1 = colMod.getColumn(k);
			column1.setPreferredWidth(50);//40
		}
		column1 = colMod.getColumn(numberofColumns);
		column1.setPreferredWidth(100); //sets the width of the last, "comments", column
		
		
		TableCellRenderer headerRenderer = new JComponentTableCellRenderer();
		Enumeration <TableColumn> columns = table.getColumnModel().getColumns();
		Border headerBorder = UIManager.getBorder("TableHeader.cellBorder");
		TableColumn column;
		JLabel label;
		int k=1;
		while (columns.hasMoreElements()) {
			 label = new JLabel("  "+qtm.getColumnName(k), JLabel.LEFT);
			 label.setBorder(headerBorder);
			 label.setBackground(Color.LIGHT_GRAY);
			 label.setOpaque(true);
		     label.setUI(new VerticalLabelUI());
			 column = (TableColumn) columns.nextElement();
			 column.setHeaderRenderer(headerRenderer);
			 column.setHeaderValue(label);
			 k++;
		}
	}

	// scrolls to the rowNumber row
	public void scrollToRow(int rowNumber){
		int rowNo = rowNumber;
		if (rowNo > 0){
		
		      
		      //Scroll such that entered row no. is at the top of the screen
			  table.setRowSelectionInterval(rowNo-1,rowNo-1);
		      int y1 = (rowNo) * table.getRowHeight();
		      int w1 = table.getWidth();
		      int h1 = table.getHeight();
		      table.scrollRectToVisible(new Rectangle(0,y1,w1,h1));

		}
			
		 
		
	}
	
	/*public static void main(String args[]) {
		new MainClass();

	}*/

	/*
	 * (non-Javadoc)
	 * 
	 * @seejavax.swing.event.TableModelListener#tableChanged(javax.swing.event.
	 * TableModelEvent)
	 * 
	 * This method triggers after various changes in the JTable
	 * 
	 * TableModelEvent.UPDATE event is fired after switching from editing one
	 * table's cell to another.
	 */
	//@Override
	public void tableChanged(TableModelEvent event) {
		
		/*
		 * if data in a cell changed and focus was switched from the edited cell
		 * to another cell, the row with modified data is going to the
		 * collection that holds updated rows.
		 */
		//saveData.setEnabled(true);
		String[] updatedRow;
		Integer updatedRowIndex, updatedColumnIndex;
		
		

		switch (event.getType()) {
		case TableModelEvent.UPDATE:
			if (table.getSelectedRow() == -1 && table.getSelectedColumn() == -1) {
				break;
			} else {
				
				// Returns the index of the row that contains the cell that was just
				//  edited.
				updatedRowIndex = table.getEditingRow();
				//tableData = table.
				System.out.println("Line 280, updated Row Index = "+updatedRowIndex);
				
				// Returns the index of the column that contains the cell that was just
				//  edited.
				updatedColumnIndex = table.getEditingColumn();
				System.out.println("updated Column Index = "+updatedColumnIndex);
				
				// Gets updated row from 'cache' and assigns it to the 'String[]
				// updatedRow' array.
				updatedRow = qtm.getUpdatedRow(updatedRowIndex);
				//System.out.println("line 290, updated Row = "+updatedRow);
				
				String valueToCheck = null;
				
				if(updatedRowIndex != -1 && updatedColumnIndex != -1)
					valueToCheck = (String) table.getValueAt(updatedRowIndex, updatedColumnIndex);
				valueToCheck = valueToCheck.trim();
				//System.out.println("line 296,valueToCheck = "+table.getValueAt(updatedRowIndex, updatedColumnIndex)+"    table Row Index = "+table.getValueAt(updatedRowIndex, updatedColumnIndex));
				
				if(valueToCheck.equals("")){
					valueToCheck = null;
					updatedRow[updatedColumnIndex+1] = null;
				}
								
				    //System.out.println("Line 303, before isValueCorrect = "+check.checkEnteredData(valueToCheck, updatedColumnIndex)); 
					isValueCorrect = check.checkEnteredData(valueToCheck, updatedColumnIndex);
					System.out.println("after isValueCorrect = "+isValueCorrect);					
					
				// Sets updated row to the Map collection where the index of the
				// updated row is a key and the 'String[] updatedRow' array is a value
			if(isValueCorrect){
				//table.getColumnModel().getColumn(updatedColumnIndex).setCellRenderer(new MyTableCellRenderer(isValueCorrect));
				
				qtm.setUpdatedData(updatedRow, updatedRowIndex);
				
				
			}	
			else{
				//table.getColumnModel().getColumn(updatedColumnIndex).setCellRenderer(new MyTableCellRenderer(isValueCorrect));
				
				//String correctedValue = (String) JOptionPane.showInputDialog(this, "Wrong value at row: "+(updatedRowIndex+1)
					//	+" col: "+qtm.getColumnName(updatedColumnIndex+1)+"\n"+"Please enter correct value:","Wrong value warning", JOptionPane.ERROR_MESSAGE,null, null,null);
				
				
				String correctedValue = (String) JOptionPane.showInputDialog(this, "Wrong value at row: "+(updatedRowIndex+1)+""+vType
						+" col: "+qtm.getColumnName(updatedColumnIndex+1)+"\n"+"Please enter correct value:","Wrong value warning", JOptionPane.ERROR_MESSAGE,null, null,null);
			
				if(correctedValue.equals(""))
				correctedValue = null;
			
			
				if(check.checkEnteredData(correctedValue, updatedColumnIndex)){
					table.setValueAt(correctedValue, updatedRowIndex, updatedColumnIndex);
					updatedRow = qtm.getUpdatedRow(updatedRowIndex);
					//System.out.println(updatedRow[updatedColumnIndex+1]);
					qtm.setUpdatedData(updatedRow, updatedRowIndex);
				}else{
							
					table.setValueAt("", updatedRowIndex, updatedColumnIndex);
					updatedRow[updatedColumnIndex+1] = null;
					qtm.setUpdatedData(updatedRow, updatedRowIndex);
				}		
			}
			
			}
			break;
		

		}

	}

	// Invoked when one of the buttons was clicked
	public void actionPerformed(ActionEvent event) {

		if (event.getActionCommand().equals("Save Changes")) {
			/*
			 * 
			 * ensures that the last edited row
			 * was sent to the collection that holds updated rows.
			 * (fires 'TableModelEvent.UPDATE' event
			 */
			this.table.editCellAt(-1, -1); 

			qtm.saveDB();
			//saveData.setEnabled(false);
		}
		else if (event.getActionCommand().equals("Add Row")) {

			qtm.addRow();
			this.scrollToRow(qtm.getRowCount());
		}
		else if (event.getActionCommand().equals("Set up Data")) {
            
			this.setupWindow();
			
		}
		else if(event.getActionCommand().equals("Refresh Table")){
			
			table.setAutoCreateColumnsFromModel(false);

			qtm.refreshData();
			table.createDefaultColumnsFromModel();
			formatCells();
			
		}
		else if (event.getActionCommand().equals("Delete Row")) {
			
			
			
			
			// numbers sorted in asc. order 1,2,3...6,7 etc
			int [] indexes = table.getSelectedRows();
			
			/*
			 * 
			 * ensures that the last edited row
			 * was sent to the collection that holds updated rows.
			 * (fires 'TableModelEvent.UPDATE' event
			 */
			this.table.editCellAt(-1, -1); 
			
			/*
			 * sets array of indexes of selected
			 * for deleting rows
			 */
			qtm.setRowToDelete(indexes);
			qtm.deleteRow();

		}
		else if (event.getActionCommand().equals("Exit")){
			qtm.closeDB();
			this.dispose();
			System.gc();
		}
	}
	
	public JTable getTable(){
		return table;
	}

	private void setupWindow() {
		
		// Ask user how many new records, date, and....
		
		periInternalFrame frame = new periInternalFrame(qtm.getHeaders());
		frame.setQTM(qtm);
		frame.setMainClass(this);
		
		
     }

}

class QueryTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private Vector<String[]> cache;// holds currently displayed rows

	private Map<Integer, String[]> updatedData; // holds updated rows

	private int colCount; // number of columns to display

	private String[] headers; // names of columns' headers (was fetched from the
								// database)
	private int[] columnType, columnPrecision; // SQL types of columns and their precision

	private Connection db;

	private PreparedStatement pstmt;

	private ResultSetMetaData meta;

	private Statement statement;

	public String currentURL, SQL_query;

	private int[] rowsToDelete;// holds a row to delete

	public QueryTableModel() {

		cache = new Vector<String[]>();

		// stores updated rows
		updatedData = new HashMap<Integer, String[]>(); // 06.16.09

	}
	
	public void setQuerySQL(String trim) {
		SQL_query = trim;
		
	}

	//Refresh table's data
	public void refreshData(){
		cache.clear();
try {
			
			ResultSet rs = statement.executeQuery(SQL_query);
			
			meta = rs.getMetaData();
			colCount = meta.getColumnCount();
			//int colType;
			//String colName;
			headers = new String[colCount];
			
			for (int h = 0; h < colCount; h++) {
				headers[h] = meta.getColumnName(h+1);
				
				System.out.println(h+"  Line 486, Type = "+meta.getColumnType(h+1)+"     Name of column "+meta.getColumnName(h+1));
				
				
			}
			
			while (rs.next()) {
				String[] record = new String[colCount];
				for (int i = 0; i < colCount; i++) {
					
					record[i] = rs.getString(i + 1);
					
				}
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

	/*
	 * set a row for deleting to 'int [] rowsToDelete'
	 */
	public void setRowToDelete(int[] arg) {
		this.rowsToDelete = arg;
	}

	
	public String [] getHeaders(){
		
		return headers;
	}
	
	// 06.12.09 adds a blank array of strings into the 'cache' Vector
	public void addRow() {
		String[] newBlankRow = new String[colCount];

		for (int h = 0; h < colCount; h++) {
			newBlankRow[h] = "";
		}
		cache.addElement(newBlankRow);
		

		fireTableRowsInserted(cache.size()-1, cache.size());

	}

	public void deleteRow() {

		int rowToDelete = -1;
		int[] selectedRow = this.rowsToDelete;
		for(int i=0; i<selectedRow.length;i++)
			
			//System.out.println("Rows to delete "+selectedRow[i]);
		
		
		try {
			pstmt = db.prepareStatement(deleteStatement());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		/*
		 * iterates indexes of selected rows backward because
		 * after deleting the element from a Vector,
		 * shifted any subsequent elements to the left 
		 * (subtracts one from their indices).
		 */
		for (int k = selectedRow.length-1; k >= 0; k--) {
			
		if(selectedRow[k] < cache.size())	{
				rowToDelete = selectedRow[k];
				//System.out.println("Row to delete "+rowToDelete + "cache size "+cache.size());
		}else
			continue;
			
			if (updatedData.containsKey(rowToDelete)) {
				
				updatedData.remove(rowToDelete);
				//System.out.println(rowToDelete+" Removed from updatedData");
				cache.remove(rowToDelete);
				//System.out.println(rowToDelete+" Removed from cache");
				fireTableRowsDeleted(rowToDelete, rowToDelete);
				

			} 
			else if (!updatedData.containsKey(rowToDelete))

			{
				
				try {
					 
					String[] elementToDelete = cache.elementAt(rowToDelete);
				
					// if a row's 'id' is not blank (it indicates that this row
					// is stored in the DB), this row is deleting from 'cache' and DB
					if (!elementToDelete[0].equals("")) {
						
						int id = Integer.parseInt(elementToDelete[0]);
						cache.remove(rowToDelete);

						pstmt.setInt(1, id);
						pstmt.executeUpdate();
						
						fireTableRowsDeleted(rowToDelete, rowToDelete);

						
						// if a row's 'id' is blank (it indicates that this row
						// is NOT stored in the DB),
						// row is deleting only from memory ('cache')
					} 
					
					else if(elementToDelete[0].equals("")) {

						cache.remove(rowToDelete);
						fireTableRowsDeleted(rowToDelete, rowToDelete);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
								

			}

			
		}
		if (pstmt != null)
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

	// Returns column's name
	public String getColumnName(int i) {
		return headers[i];
	}

	// Returns number of columns
	public int getColumnCount() {
		return colCount;
	}

	// saves updated rows to the collection, as key value is the index of edited
	// row
	public void setUpdatedData(String[] data, Integer key) {

		updatedData.put(key, data);

	}

	// gets a row from 'cache'
	public String[] getUpdatedRow(int row) {

		return cache.elementAt(row);
	}

	// Returns number of rows currently displayed
	public int getRowCount() {
		return cache.size();
	}

	public Object getValueAt(int row, int col) {

		return ((String[]) cache.elementAt(row))[col];
	}

	// ensures that cells of the first column are not editable (firs column
	// displays id values)
	public boolean isCellEditable(int row, int col) {
		if (col == 0)
			return false;
		else
			return true;
	}

	public void setValueAt(Object value, int row, int col) {

		String[] rowData = cache.elementAt(row);
		rowData[col] = (String) value;
		cache.set(row, rowData);

		fireTableRowsUpdated(row, row);
	}

	public void setHostURL(String url) {
		if (url.equals(currentURL)) {
			//System.out.println("in setHostURL, url.equals currentURL");
			return;
		}
		closeDB();
		initDB(url);
		
		//currentURL = "jdbc:mysql://localhost:3306";
		//System.out.println("before currentURL");
        currentURL = "jdbc:mysql://134.67.216.25:3306"; 
        
	}

	// Fetches data from the DB at the program's start up
	/*
	 * Starting with Connector/J 3.1.7, ResultSet.getString() can be decoupled from this behavior via 
	 * noDatetimeStringSync=true
	 * */
	public void setQuery() {
		
		try {
			
			ResultSet rs = statement.executeQuery(SQL_query);
			//boolean noDatetimeStringSync = false;
			meta = rs.getMetaData();
			colCount = meta.getColumnCount();
			headers = new String[colCount];
			columnType = new int[colCount];
			columnPrecision = new int[colCount];
			for (int h = 0; h < colCount; h++) {
				headers[h] = meta.getColumnName(h+1);
				columnType[h] = meta.getColumnType(h+1);
				columnPrecision[h] = meta.getPrecision(h+1);
				//System.out.println("in setQuery = "+ columnType[h]);
			}
			
			while (rs.next()) {
				String[] record = new String[colCount];
				for (int i = 0; i < colCount; i++) {
					
					
					
					 /** Shows Date in "MM-dd-yyyy" format
					 * 
					 */
					  	/*if(columnType[i] == Types.DATE){
						
						String sqlDate = rs.getString(i + 1);
						
						
						if(sqlDate != null){
							
							DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
							Date date = df.parse(sqlDate);
							date.getTime();
							df = new SimpleDateFormat("MM-dd-yyyy");
							record[i] = df.format(date);
						
						
						}else
							record[i] = sqlDate;	
						
					}else{
						
						record[i] = rs.getString(i + 1);
					}*/
					
						record[i] = rs.getString(i + 1);
						
				}
				cache.addElement(record);
			}
			
			fireTableStructureChanged();
			
		} catch (Exception e) {

			e.printStackTrace();
		}
		
	}

	// initialize DB connection
	private void initDB(String url) {
		try {

			Class.forName("com.mysql.jdbc.Driver");
			//String username = "root";
			String username = "jwaterma";
			  
		    //String password = "hoTTub11";
			String password = "Jw08-18*!";
			  
			//System.out.println("BEFORE db"); 
			//db = DriverManager.registerDriver(new com.mysql.jbdc.Driver());
			db = DriverManager.getConnection(url, username, password);
			//System.out.println("After db = "+db);  
			statement = db.createStatement();
			

		} catch (Exception e) {
			System.out.println("Could not initialize the database.");
			e.printStackTrace();
		}
	}

	// Generates SQL statement for updating a row values in the DB
	private String updateStatement() {

		String tableName;
		String querySQL = "";
		final int COLUMN = 1; // number of column by which the name of the
								// database is going to be known

		try {

			//tableName = "test." + meta.getTableName(COLUMN);
			tableName = "Jerry."+ meta.getTableName(COLUMN);
			querySQL = "UPDATE " + tableName + " SET ";
			System.out.println(" querySQL = "+ querySQL);
			
		} catch (SQLException e) {

			e.printStackTrace();
		}
		String temp = "";

		// names of all but the first column are inserted into SQL statement for
		// updating
		int h;
		for (h = 1; h < headers.length; h++) {
			temp += headers[h] + " = ? , ";
		}

		// deletes unnecessary marks from the generated string
		temp = temp.trim().substring(0, temp.length() - 2);

		querySQL = querySQL + temp + " WHERE " + getColumnName(0) + " = ?";
		// UPDATE <tablename> SET <columnname> = ? WHERE key = ?
		return querySQL;
	}

	// 06.12.09 generates INSERT SQL statement
	private String insertStatement() {
		String insertSQL = "";
		String tableName;
		int COLUMN = 1;

		try {

			//tableName = "test." + meta.getTableName(COLUMN);
			tableName = "Jerry."+ meta.getTableName(COLUMN);
			insertSQL = "INSERT INTO " + tableName;

		} catch (SQLException e) {

			e.printStackTrace();
		}
		String temp = "";
		String temp_2 = "";
		int h;
		for (h = 1; h < headers.length; h++) {
			temp += headers[h] + ", ";
			temp_2 += "?, ";
		}
		temp_2 = temp_2.trim().substring(0, temp_2.length()-2);
		temp = temp.trim().substring(0, temp.length() - 2);

		
		insertSQL = insertSQL + " (" + temp + ")" + " VALUES (" + temp_2+")";
		// "INSERT INTO test.<table name> (<Name1>, <Name2> ) VALUES (?, ?)"
		
		//System.out.println(insertSQL);

		return insertSQL;
	}

	// Generates SQL statement for deleting selected colum(s) from the DB
	private String deleteStatement() {
		String deleteStatement = "";
		String tableName;
		int COLUMN = 1;

		try {

			//tableName = "test." + meta.getTableName(COLUMN);
			tableName = "Jerry."+ meta.getTableName(COLUMN);
			deleteStatement = "DELETE FROM " + tableName + " WHERE "
					+ headers[0] + " = ?";
			// "DELETE FROM test.jerryperibackup WHERE id = ?"

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return deleteStatement;
	}

	// saves updated rows to the DB //06.12.09
	public void saveDB() {

		String[] temp;

		Iterator<String[]> it = updatedData.values().iterator();// iterator for
																// the
																// Collection
																// that holds
																// updated rows
		
		//System.out.println("Size of collection to save"+updatedData.size());
		
		Set<Integer> keys = updatedData.keySet();
		Iterator<Integer> it2 = keys.iterator(); // iterator for the Collection
													// that holds indexes of
													// updated rows
		int keyValue;

		while (it.hasNext()) {

			keyValue = it2.next();

			try {

				temp = it.next();
				// if updated row's 'id' is not blank (it indicates that this
				// row is stored in the DB),
				if (!temp[0].equals("")) {
					pstmt = db.prepareStatement(updateStatement());
					/*pstmt.setString(1, temp[1]);
					pstmt.setString(2, temp[2]);
					pstmt.setString(3, temp[3]);
					pstmt.setString(4, temp[4]);
					pstmt.setString(5, temp[5]);*/
					
				for(int k=1; k < temp.length; k++){ //was 29 now temp.length
					
					//System.out.println(temp[k]);
					
					
						pstmt.setString(k, temp[k]);
						
					
				}
					pstmt.setInt(temp.length, Integer.parseInt(temp[0]));
					pstmt.executeUpdate();

				}
				// if updated row's 'id' is blank (it indicates that this row is
				// NOT stored in the DB),
				if (temp[0].equals("")) {
					
					

					// ensures that after inserting to the DB a new record,
					// autogenerated key is returned
					// by 'getGeneratedKeys()' method
					pstmt = db.prepareStatement(insertStatement(),
							Statement.RETURN_GENERATED_KEYS);
					//System.out.println(createStatement());
					
					
					//if user does not put a date in, then   
					//    set pdate default to '0000-00-00' HOWEVER, JTable will not read it  06-22-09
//					if (temp[1].equals("")) {
//						temp[1] = "0000-00-00";
//					}
					//System.out.println("Date, pstmt temp[1]="+temp[1]);
					
					/*pstmt.setString(1, temp[1]);
					pstmt.setString(2, temp[2]);*/
					
					for(int k=1; k < temp.length; k++){
						
						if (temp[k].equals("")){
							//System.out.println(k+ " value "+ temp[k]);
							pstmt.setString(k, null);
							temp[k] = null;
							
							
						}else{
							//System.out.println(k+ " value "+ temp[k]);
							pstmt.setString(k, temp[k]);
						}
					}
					
					pstmt.executeUpdate();
					cache.set(keyValue, temp);
					// returns generated by DB key
					ResultSet genKey = pstmt.getGeneratedKeys();

					int key = -1;
					while (genKey.next()) {
						key = genKey.getInt(1);
					}

					// adds generated key to the table (cache)
					String[] updRow = this.getUpdatedRow(keyValue);
					updRow[0] = Integer.toString(key);
					cache.setElementAt(updRow, keyValue);

					fireTableRowsInserted(keyValue, keyValue);

				}

			} catch (SQLException e) {

				e.printStackTrace();
			}
			
			
		}
		
		

		// clears the collection 'updatedData' that stores updated rows
		updatedData.clear();

		
	}

	public void closeDB() {
		try {
			if (statement != null) {
				statement.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (db != null) {
				db.close();
				System.out.println("Database was successfully closed !");
			}
		} catch (Exception e) {
			System.out.println("Could not close the current connection.");
			e.printStackTrace();
		}
	}
}

/*
 * Helping class for proper closing the application
 */
class WindowDestroyer  extends InternalFrameAdapter {

	private QueryTableModel qtm;

	public void setQTM(QueryTableModel qtm) {
		this.qtm = qtm;
	}

	public void internalFrameClosed(InternalFrameEvent e) {

		qtm.closeDB();
		System.gc();
		
	}

}
/*
 * Custom TableCellRenderer for vertical headers
 */
class JComponentTableCellRenderer implements TableCellRenderer {
	  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
	      boolean hasFocus, int row, int column) {
	    return (JComponent) value;
	  }
	}
