//This is only a test file to figure out Java and MySQL my local laptop 09-23-10 

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
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
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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

	JDesktopPane desktop;

	JLabel lNum = new JLabel("HASP #");
	JLabel lTitle = new JLabel("Title ");
	JLabel lPreparer = new JLabel("HASP Preparer ");
	JLabel lPI1 = new JLabel("Principle Investigator #1 ");
	JLabel lPI2 = new JLabel("Principle Investigator #2 ");
	JLabel lPI3 = new JLabel("Principle Investigator #3 ");
	JLabel lPI4 = new JLabel("Principle Investigator #4 ");
	JLabel lPI5 = new JLabel("Principle Investigator #5 ");
	JLabel lPI6 = new JLabel("Principle Investigator #6 ");
	JLabel lPI7 = new JLabel("Principle Investigator #7 ");
	JLabel lPI8 = new JLabel("Principle Investigator #8 ");
	JLabel lBC = new JLabel("Branch Chief ");
	JLabel lOffice = new JLabel("Office ");
	JLabel lLab = new JLabel("Lab ");
	JLabel lDiv = new JLabel("Division ");
	JLabel lBranch = new JLabel("Branch ");
	JLabel lLocat1 = new JLabel("Location #1 ");
	JLabel lLocat2 = new JLabel("Location #2 ");
	JLabel lLocat3 = new JLabel("Location #3 ");
	JLabel lLocat4 = new JLabel("Location #4 ");
	JLabel lLocat5 = new JLabel("Location #5 ");
	JLabel lLocat6 = new JLabel("Location #6 ");
	JLabel lLocat7 = new JLabel("Location #7 ");
	JLabel lLocat8 = new JLabel("Location #8 ");
	JLabel lCodes = new JLabel("Building Codes");
	JLabel lBC1 = new JLabel("AW = Andrew W. Briedenbach Research Center");//Andrew W. Briedenbach Research Center");
	JLabel lBC2 = new JLabel("CH = Center Hill Facility");
	JLabel lBC3 = new JLabel("FC = Full Containment Facility");
	JLabel lBC4 = new JLabel("ED = Edison, NJ Urban Watershed Management Research");
	JLabel lBC5 = new JLabel("ES = Experimental Streams Facility");
	JLabel lBC6 = new JLabel("RK = Robert S. Kerr Environmental Research Facility (Ada, OK)");
	JLabel lBC7 = new JLabel("TE = Testing and Evaluation Facility");
	JLabel lAdate = new JLabel("Approval Date");
	JLabel lEdate = new JLabel("Expiration Date");
	JLabel lNdate = new JLabel("Next Review Date");
	JLabel lNotes = new JLabel("Notes");
	
	//   J T e x t F i e l d
	JTextField vNum = new JTextField(10);
	//String sTitle = "";
	static JTextField vInvest1 = new JTextField(15);
	static JTextField vInvest2 = new JTextField(15);
	static JTextField vInvest3 = new JTextField(15);
    JTextField vInvest4 = new JTextField(15);
    JTextField vInvest5 = new JTextField(15);
    JTextField vInvest6 = new JTextField(15);
    JTextField vInvest7 = new JTextField(15);
    JTextField vInvest8 = new JTextField(15);
    JTextField vBC      = new JTextField(15);
    JTextField vOffice  = new JTextField(15);
    JTextField vLab     = new JTextField(15);
    JTextField vDiv     = new JTextField(15);
    JTextField vBranch  = new JTextField(15);
    static JTextField vTitle = new JTextField(60);
    JTextField vPreparer = new JTextField(20);
    JTextField vHASP = new JTextField(10);
    static JTextField vLoc1 = new JTextField(5);
    JTextField vLoc2 = new JTextField(5);
    JTextField vLoc3 = new JTextField(5);
    JTextField vLoc4 = new JTextField(5);
    JTextField vLoc5 = new JTextField(5);
    JTextField vLoc6 = new JTextField(5);
    JTextField vLoc7 = new JTextField(5);
    JTextField vLoc8 = new JTextField(5);
    JTextField vAdat = new JTextField(10);
    JTextField vEdat = new JTextField(10);
    JTextField vNdat = new JTextField(10);
    static JTextField vNote = new JTextField(20);
   
    //J B U T T O N
    JButton jbChem = new JButton("Chemical List");
    JButton jbBio  = new JButton("Biological Agent List");
    
    //  J C o m b o B o x
    JComboBox jcbPreparer = new JComboBox();

	public String sTitle;
    
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

		setSize(1235, 850); //setSize(1235, 600);
		setMinimumSize(new Dimension(1000, 800));    //setMinimumSize(new Dimension(800, 600));
		setTitle("test Table");
		
		//setIconImage(Toolkit.getDefaultToolkit().getImage("images/epa_logo.png"));
		setFrameIcon(new ImageIcon("images/epa_logo.png"));
		
		table = new JTable();
		
		table.setModel(qtm);
		table.setAutoCreateColumnsFromModel(true);
		table.setRowSelectionAllowed(true);
		table.getTableHeader().setReorderingAllowed(false);
		
			
		table.getModel().addTableModelListener(this);// adds TableModelListener
		
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        table.setPreferredScrollableViewportSize(new Dimension (1000,80));
		//JScrollPane scrollPane = new JScrollPane(table);
		//table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

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
		  hostField = new JTextField("jdbc:oracle:thin:@comanche.wic.epa.gov:1521:cintst");
		//queryField = new JTextField("SELECT sysid,pdate,sid FROM test.jerryperiphyton");
       // queryField = new JTextField("SELECT * FROM test.jerryperiphyton");
         queryField = new JTextField("select * from shasptest order by haspnum");
		  
		commandPanel.add(addRow);
		commandPanel.add(deleteRow);
		commandPanel.add(saveData);
		commandPanel.add(setupData);
		commandPanel.add(refreshButton);
		commandPanel.add(exitButton);
        commandPanel.add(jbChem);
		commandPanel.add(jbBio);
        
		saveData.addActionListener(this);
		addRow.addActionListener(this);
		deleteRow.addActionListener(this);
		setupData.addActionListener(this);
		refreshButton.addActionListener(this);
		exitButton.addActionListener(this);
		jbChem.addActionListener(this);
		jbBio.addActionListener(this);
		
		qtm.setHostURL(hostField.getText().trim()); //line 678
		qtm.setQuerySQL(queryField.getText().trim());//line 449
		qtm.setQuery();//line 693
		
		check = new EnterValuesChecker(qtm.getColumnTypes(), qtm.getColumnPrecision());
	
		getContentPane().add(commandPanel, BorderLayout.SOUTH); //SOUTH
		getContentPane().add(scrollpane, BorderLayout.NORTH);   //CENTER);

		// lines up JFrame on the screen
		Dimension dim = getToolkit().getScreenSize();
		//System.out.println("dim ="+dim+"    dim.width  ="+dim.width+" getWidth()="+getWidth()+"  dim.height ="+dim.height+" getHeight ="+getHeight());
		//setLocation(dim.width / 2 - getWidth() / 2, dim.height / 2 - getHeight() / 2);
		  setLocation(dim.width / 8 - getWidth() / 8, dim.height / 18 - getHeight() / 18);
				
		formatCells();
		
		int vID = 0;
		JCheckBox jcbInact = new JCheckBox("Inactive");
		JCheckBox jcbFA    = new JCheckBox("Field Activities");
		JCheckBox jcbLA    = new JCheckBox("Lab Activities");
		JCheckBox jcbTS    = new JCheckBox("Treatability Study");
		JCheckBox jcbHW    = new JCheckBox("Hazadous Waste Generator");
      
		
		readData(        table,             vID,            vNum,            vPreparer,            vInvest1,           vInvest2,            vInvest3,            vInvest4,            vInvest5,                vInvest6,            vInvest7,            vInvest8,           vBC,            vOffice,            vLab,            vDiv,            vBranch,            vTitle,            vLoc1,               vLoc2,           vLoc3,           vLoc4,                        vLoc5,           vLoc6,           vLoc7,           vLoc8,           vAdat,           vEdat,           vNdat,  vNote);
//              readData(table,             vID,            vNum,            vPreparer,            vInvest1,           vInvest2,            vInvest3,            vInvest4,   561-0069 vInvest5,                vInvest6,            vInvest7,            vInvest8,           vBC,            vOffice,            vLab             vDiv,            vBranch,            vTitle,            vLoc1,               vLoc2,           vLoc3,           vLoc4,                        vLoc5,           vLoc6,           vLoc7,           vLoc8,           vAdat,           vEdat,           vNdat,  vNote);
		//		readData(JTable table,  int vID, JTextField vNum, JTextField vPreparer,	JTextField vInvest1,JTextField vInvest2, JTextField vInvest3, JTextField vInvest4, JTextField vInvest5,		JTextField vInvest6, JTextField vInvest7, JTextField vInvest8,JTextField vBC, JTextField vOffice, JTextField vLab, JTextField vDiv, JTextField vBranch, JTextField vTitle, JTextField vLoc1,	JTextField vLoc2,JTextField vLoc3,JTextField vLoc4,				JTextField vLoc5,JTextField vLoc6,JTextField vLoc7,JTextField vLoc8,JTextField vAdat,JTextField vEdat,JTextField vNdat) {
	    	//  re 3964 rB
		// void readData(JTable table,  int vID, JTextField vNum, JTextField vPreparer,JTextField vInvest1,JTextField vInvest2, JTextField vInvest3, JTextField vInvest4, JTextField vInvest5, JTextField vInvest6, JTextField vInvest7, JTextField vInvest8,	JTextField vTitle, JTextField vTitle2)
	    System.out.println("L# 257 "+vTitle.getText());	
		JPanel p1 = new JPanel(new GridBagLayout());				
	    GridBagConstraints c = new GridBagConstraints();
	    getContentPane().add(p1, BorderLayout.CENTER);
	    
	    c.insets = new Insets(1,1,1,1);
        c.weightx = 1.0;
        c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 0;
		c.anchor =  GridBagConstraints.LINE_START;
		p1.add(lNum,c);
		
		c.gridx = 1;
		c.gridy = 0;
		p1.add(vNum,c);
		
		c.gridx = 2;
		c.gridy = 0;
		p1.add(jcbInact,c);
		
		c.gridx = 3;
		c.gridy = 0;
		p1.add(jcbFA,c);
		
		c.gridx = 4;
		c.gridy = 0;
		p1.add(jcbLA,c);
		
		c.gridx = 5;
		c.gridy = 0;
		p1.add(jcbTS,c);
		
		c.gridx = 6;
		c.gridy = 0;
		p1.add(jcbHW,c);

		c.gridx = 0;
		c.gridy = 1;
		p1.add(lTitle,c);
		//c.fill = GridBagConstraints.BOTH; //.HORIZONTAL;    
		//c.ipady = 20; //30;  
		c.gridwidth = 4;
		//c.gridheight = 1;
		c.gridx = 1;
		c.gridy = 1;
		sTitle = vTitle.getText();
	    System.out.println("L# 304, getText ="+sTitle.toString());//.getText());
		p1.add(vTitle,c);
	
		c.fill = GridBagConstraints.BASELINE;
		c.ipady = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 6;
		p1.add(lPreparer,c);
		c.gridx = 1;
        c.gridy = 6;		
		p1.add(vPreparer,c);
        c.gridx = 5;
        c.gridy = 6;
        p1.add(lAdate,c);
        c.gridx = 6;
        c.gridy = 6;
        p1.add(vAdat,c);
        
		c.gridx = 0;
		c.gridy = 7;
		p1.add(lPI1,c);
		c.gridx = 1;
		c.gridy = 7;
		p1.add(vInvest1,c);
		c.gridx = 3;
		c.gridy = 7;
		p1.add(lPI5,c);
		c.gridx = 4;
		c.gridy = 7;
		p1.add(vInvest5,c);
		c.gridx = 5;
        c.gridy = 7;
        p1.add(lEdate,c);
        c.gridx = 6;
        c.gridy = 7;
        p1.add(vEdat,c);
		
		c.gridx = 0;
		c.gridy = 8;
		p1.add(lPI2,c);
		c.gridx = 1;
		c.gridy = 8;
		p1.add(vInvest2,c);
		c.gridx = 3;
		c.gridy = 8;
		p1.add(lPI6,c);
		c.gridx = 4;
		c.gridy = 8;
		p1.add(vInvest6,c);
		c.gridx = 5;
        c.gridy = 8;
        p1.add(lNdate,c);
        c.gridx = 6;
        c.gridy = 8;
        p1.add(vNdat,c);
		
		c.gridx = 0;
		c.gridy = 9;
		p1.add(lPI3,c);
		c.gridx = 1;
		c.gridy = 9;
		p1.add(vInvest3,c);
		c.gridx = 3;
		c.gridy = 9;
		p1.add(lPI7,c);
		c.gridx = 4;
		c.gridy = 9;
		p1.add(vInvest7,c);
		c.gridx = 5;
		c.gridy = 9;
		p1.add(lNotes,c);
		//c.gridwidth = 2;
		//c.gridheight = 2;
		c.gridx = 6;
		c.gridy = 9;
		p1.add(vNote,c);
		
		
		c.gridx = 0;
		c.gridy = 10;
		p1.add(lPI4,c);
		c.gridx = 1;
		c.gridy = 10;
		p1.add(vInvest4,c);
		c.gridx = 3;
		c.gridy = 10;
		p1.add(lPI8,c);
		c.gridx = 4;
		c.gridy = 10;
		p1.add(vInvest8,c);
		
		c.gridx = 0;
		c.gridy = 11;
		p1.add(lBC,c);
		c.gridx = 1;
		c.gridy = 11;
		p1.add(vBC, c); 
		//System.out.println(" BC = "+vBC.getText());
	
		c.gridx = 0;
		c.gridy = 12;
		p1.add(lOffice,c);
		c.gridx = 1;
		c.gridy = 12;
		p1.add(vOffice,c);
		
		c.gridx = 0;
		c.gridy = 13;
		p1.add(lLab,c);
		c.gridx = 1;
		c.gridy = 13;
		p1.add(vLab,c);
		
		c.gridx = 0;
		c.gridy = 14;
		p1.add(lDiv,c);
		c.gridx = 1;
		c.gridy = 14;
		p1.add(vDiv,c);
		
		c.gridx = 0;
		c.gridy = 15;
		p1.add(lBranch,c);
		c.gridx = 1;
		c.gridy = 15;
		p1.add(vBranch,c);
		c.gridx = 4;
		c.gridy = 15;
		p1.add(lCodes,c);
			
		c.gridx = 0;
		c.gridy = 16;
		p1.add(lLocat1,c);
		c.gridx = 1;
		c.gridy = 16;
		p1.add(vLoc1,c);
		c.gridx = 2;
		c.gridy = 16;
		p1.add(lLocat5,c);
		c.gridx = 3;
		c.gridy = 16;
		p1.add(vLoc5,c);
		c.gridwidth = 3;
		c.gridx = 4;
		c.gridy = 16;
		p1.add(lBC1,c);
		
		c.gridx = 0;
		c.gridy = 17;
		p1.add(lLocat2,c);
		c.gridx = 1;
		c.gridy = 17;
		p1.add(vLoc2,c);
		c.gridx = 2;
		c.gridy = 17;
		p1.add(lLocat6, c);
		c.gridx = 3;
		c.gridy = 17;
		p1.add(vLoc6,c);
		c.gridx = 4;
		c.gridy = 17;
		p1.add(lBC2,c);
		
		c.gridx = 0;
		c.gridy = 18;
		p1.add(lLocat3,c);
		c.gridx = 1;
		c.gridy = 18;
		p1.add(vLoc3,c);
		
		c.gridx = 2;
		c.gridy = 18;
		p1.add(lLocat7, c);
		c.gridx = 3;
		c.gridy = 18;
		p1.add(vLoc7,c);
		c.gridx = 4;
		c.gridy = 18;
		p1.add(lBC3,c);
		
		c.gridx = 0;
		c.gridy = 19;
		p1.add(lLocat4,c);
		c.gridx = 1;
		c.gridy = 19;
		p1.add(vLoc4,c);
		c.gridx = 2;
		c.gridy = 19;
		p1.add(lLocat8, c);
		c.gridx = 3;
		c.gridy = 19;
		p1.add(vLoc8,c);
		c.gridwidth = 3;
		c.gridx = 4;
		c.gridy = 19;
		p1.add(lBC4,c);
		
		c.gridx = 1;
		c.gridy = 20;
		p1.add(jbChem,c);
		c.gridx = 2;
		c.gridy = 20;
		p1.add(jbBio,c);
		c.gridx = 4;
		c.gridy = 20;
		p1.add(lBC5,c);
		
		c.gridwidth = 3;
		c.gridx = 4;
		c.gridy = 21;
		p1.add(lBC6,c);
		
		c.gridx = 4;
		c.gridy = 22;
		p1.add(lBC7,c);
		
		setVisible(true);
	}
	
	
	
	



	/**
	 * Formats Table cells
	 */
	private void formatCells() {
		TableColumnModel colMod = table.getColumnModel();
	if(qtm.getColumnCount() == colMod.getColumnCount())	
		colMod.removeColumn(colMod.getColumn(0));
		
		TableColumn column1; 
		
		/*right-alligns data in cells 
		 * 
		 * DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) table.getDefaultRenderer(table.getColumnClass(1));
		renderer.setHorizontalAlignment(SwingUtilities.RIGHT);*/
		
		
		column1 = colMod.getColumn(0);
		column1.setMinWidth(70);// sets width of the date column 
		int numberofColumns = table.getModel().getColumnCount()-2;
		//System.out.println(numberofColumns);
		
		//sets width of all except the last column
		for(int k = 1; k < numberofColumns; k++){// was 27 now numberofColumns
			column1 = colMod.getColumn(k);
			column1.setPreferredWidth(40);
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
		      //System.out.println("in scrollToRow "+rowNo);
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
				//System.out.println(" TableModelEvent = "+updatedRowIndex);
				// Returns the index of the column that contains the cell that was just
				//  edited.
				updatedColumnIndex = table.getEditingColumn();
				
				// Gets updated row from 'cache' and assigns it to the 'String[]
				// updatedRow' array.
				updatedRow = qtm.getUpdatedRow(updatedRowIndex);
				
				String valueToCheck = null;
				if(updatedRowIndex != -1 && updatedColumnIndex != -1)
					valueToCheck = (String) table.getValueAt(updatedRowIndex, updatedColumnIndex);
				valueToCheck = valueToCheck.trim();
				
				if(valueToCheck.equals("")){
					valueToCheck = null;
					updatedRow[updatedColumnIndex+1] = null;
					
				}
				
				
					isValueCorrect = check.checkEnteredData(valueToCheck, updatedColumnIndex);
				
										
				// Sets updated row to the Map collection where the index of the
				// updated row is a key and the 'String[] updatedRow' array is a value
			if(isValueCorrect){
				//table.getColumnModel().getColumn(updatedColumnIndex).setCellRenderer(new MyTableCellRenderer(isValueCorrect));
				qtm.setUpdatedData(updatedRow, updatedRowIndex);
			}	
			else{
				//table.getColumnModel().getColumn(updatedColumnIndex).setCellRenderer(new MyTableCellRenderer(isValueCorrect));
				String correctedValue = (String) JOptionPane.showInputDialog(this, "Wrong value at row: "+(updatedRowIndex+1)
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
			//System.out.println("before qtm.saveDB()");
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
		else if (event.getActionCommand().equals("Chemical List")) {
			System.out.println(" Chem list");
			cList cL = new cList();
		}
		else if (event.getActionCommand().equals("Biological Agent List")) {
			System.out.println(" Biological Agent List");
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
//	vBC  vOffice  vLab   vDiv   vBranch  vTitle
	public static void readData(JTable table,  int vID, JTextField vNum, JTextField vPreparer,
			JTextField vInvest1,JTextField vInvest2, JTextField vInvest3, JTextField vInvest4, JTextField vInvest5,
			JTextField vInvest6, JTextField vInvest7, JTextField vInvest8,JTextField vBC, 
			JTextField vOffice, JTextField vLab, JTextField vDiv, JTextField vBranch, JTextField vTitle, JTextField vLoc1,
			JTextField vLoc2,JTextField vLoc3,JTextField vLoc4,
			JTextField vLoc5,JTextField vLoc6,JTextField vLoc7,JTextField vLoc8,JTextField vAdat,JTextField vEdat,JTextField vNdat,JTextField vNote) {
    	Vector<String> columnNames = new Vector<String>();
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
    	Connection con;
    	String num = "",dbPreparer="",dbInvest1="",dbInvest2="",dbInvest3="",dbInvest4="",dbInvest5="",dbInvest6="",dbInvest7="",dbInvest8="",
    			dbBC = "", dbOffice= "",dbLab="",dbDiv="", dbBrnch="",dbTitle="",dbLoc1="",dbLoc2="",dbLoc3="",dbLoc4="",
    			dbLoc5="",dbLoc6="",dbLoc7="",dbLoc8="",dbvAdat="",dbvEdat="",dbvNdat="",dbvNote="";
		boolean inact; 
    	try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@comanche.wic.epa.gov:1521:cintst","CSHEM","welcome1");
			Statement stAll = con.createStatement();
			String fristHASP = "'2009-071'";
	    	ResultSet rs = stAll.executeQuery("Select * From SHASPtest where HASPNUM = "+fristHASP+" ");
	    	//ResultSet rs = stAll.executeQuery("select * from SHASPtest where sysid = "+vID+" ");
	    	ResultSetMetaData md = rs.getMetaData();
	    	int columns = md.getColumnCount();
	    	for (int i=1; i<=columns; i++){
	    		columnNames.addElement(md.getColumnName(i));
	    	}
	    	while (rs.next()) {
	    		Vector<Object> row = new Vector<Object>(columns);
	    		for (int i=1; i<=columns; i++) {
	    			row.addElement(rs.getObject(i));
	    			//System.out.println("L# 793 "+row.toString());
	    		}
	    	data.add(row);
	    	num     = rs.getString("HASPNUM");
//	    	  	inact   = rs.getBoolean("INACTIVE");//.getString("INACTIVE");
	    	  	dbPreparer = rs.getString("HASP_Preparer"); //= rs.getString( = rs.getString( = rs.getString( = rs.getString(
	    	  	dbInvest1  = rs.getString("Invest1LastName");
	    	  	dbInvest2  = rs.getString("Invest2LastName");
	    	  	dbInvest3  = rs.getString("Invest3LastName");
	    	  	dbInvest4  = rs.getString("Invest4LastName");
	    	  	dbInvest5  = rs.getString("Invest5LastName");
	    	  	dbInvest6  = rs.getString("Invest6LastName");
	    	  	dbInvest7  = rs.getString("Invest7LastName");
	    	  	dbInvest8  = rs.getString("Invest8LastName");
	    	  	dbBC       = rs.getString("BRANCHCHIEF");
	            dbOffice   = rs.getString("Office");
	            dbLab      = rs.getString("Lab");
	            dbDiv      = rs.getString("Division_ID");
	            dbBrnch    = rs.getString("Branch_ID");
	    	  	dbTitle    = rs.getString("TITLE");
	    	  	System.out.println("L# 823,  Title = "+dbTitle);
	    	  	dbLoc1     = rs.getString("Location1");
	    	  	dbLoc2     = rs.getString("Location2");
	    	  	dbLoc3     = rs.getString("Location3");
	    	  	dbLoc4     = rs.getString("Location4");
	    	  	dbLoc5     = rs.getString("Location5");
	    	  	dbLoc6     = rs.getString("Location6");
	    	  	dbLoc7     = rs.getString("Location7");
	    	  	dbLoc8     = rs.getString("Location8");
	    	  	
	    	  	dbvAdat    = rs.getString("APPROVALDATE");
	    	  	dbvEdat    = rs.getString("EXPIRATIONDATE");
	    	  	dbvNdat    = rs.getString("NEXTReview");
	    	  	dbvNote    = rs.getString("Notes");
	    	}
	    	//if (rs.next()){          //Uncle Tim cell 706-5049
	    		//num = rs.getString("HASPNUM");	    		//inact   = rs.getString("INACTIVE");
	    	  	//dbTitle = rs.getString("Title");	    		//System.out.println("L# 693,  num="+num.toString());
	    	//}
	    	vNum.setText(num);
	    	//jcbInact.setText(inact);
	    	vPreparer.setText(dbPreparer);
	    	vInvest1.setText(dbInvest1);
	    	vInvest2.setText(dbInvest2);
	    	vInvest3.setText(dbInvest3);
	    	vInvest4.setText(dbInvest4);
	    	vInvest5.setText(dbInvest5);
	    	vInvest6.setText(dbInvest6);
	    	vInvest7.setText(dbInvest7);
	    	vInvest8.setText(dbInvest8);
	    	vBC.setText(dbBC);
	    	vOffice.setText(dbOffice);
	    	vLab.setText(dbLab);
	    	vDiv.setText(dbDiv);
	    	vBranch.setText(dbBrnch);
	    	vTitle.setText(dbTitle);
	    	System.out.println("L# 858   Title = "+vTitle.getText());
	    	vLoc1.setText(dbLoc1);
	    	vLoc2.setText(dbLoc2);
	    	vLoc3.setText(dbLoc2);
	    	vLoc4.setText(dbLoc2);
	    	vLoc5.setText(dbLoc5);
	    	vLoc6.setText(dbLoc6);
	    	vLoc7.setText(dbLoc7);
	    	vLoc8.setText(dbLoc8);
	    	
	    	vAdat.setText(dbvAdat);
	    	vEdat.setText(dbvEdat);
	    	vNdat.setText(dbvNdat);
	    	vNote.setText(dbvNote);
	    } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  }
}

class QueryTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private static Object vtitle;

	private static String sTitle;

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

	private String currentURL, SQL_query;

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
			headers = new String[colCount];
			
			for (int h = 0; h < colCount; h++) {
				headers[h] = meta.getColumnName(h+1);
				
				
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
		//System.out.println(" in getValueAt "+cache.toString());  // .elementAt(row)[col]);
		return ((String[]) cache.elementAt(row))[col];
	}

	// ensures that cells of the first column are not editable (first column
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
		//System.out.println(" rowData[col] = "+ rowData[col]);
		fireTableRowsUpdated(row, row);
	}

	public void setHostURL(String url) {
		if (url.equals(currentURL)) {
			return;
		}
		closeDB();
		initDB(url);//line 758
	//  currentURL = "jdbc:mysql://localhost:3306";
        currentURL = "jdbc:oracle:thin:@comanche.wic.epa.gov:1521:cintst";
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
				
			}
			//System.out.println("in setQuery = "+SQL_query);
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
						//System.out.println("line 745, record = "+record[i]);		
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

			//Class.forName("com.mysql.jdbc.Driver");
			//String username = "root";
			//String password = "hoTTub11";
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String username = "CSHEM";
			String password = "welcome1";
			db = DriverManager.getConnection(url, username, password);
			statement = db.createStatement();
			//System.out.println("statement is "+statement);			

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

			tableName = "shasptest" + meta.getTableName(COLUMN);
			//System.out.println("line 791, tableName = "+tableName);
			querySQL = "UPDATE " + tableName + " SET ";

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
		System.out.println("in insertStatement");
		try {

			tableName = "test." + meta.getTableName(COLUMN);
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

			tableName = "test." + meta.getTableName(COLUMN);
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
			System.out.println("  keyValue = "+keyValue);
			try {
				temp = it.next();
				// if updated row's 'id' is not blank (it indicates that this
				// row is stored in the DB),
				if (!temp[0].equals("")) {
					pstmt = db.prepareStatement(updateStatement());
					System.out.println(" pstmt = "+pstmt);
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
					pstmt.executeUpdate();//************

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
