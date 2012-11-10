
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import java.util.*;

public class periInternalFrame implements ActionListener {
	
	
	/**
	 * 
	 * 
	 * generates necessary number of records ( String[] ) and adds them to
	 * the List collection that added to the 'Vector cache' collection
	 * at the MainClass class
	 * 
	*/	
	private static final long serialVersionUID = 1L;
	private JDialog frame;
	private JTable inputTable;
	private Vector <String> columnNames, data;
	private QueryTableModel qtm = null;
	private JTextField rowsToAdd;
	private JTextArea comments;
	private MainClass ob;


	/**
	 * @param headers
	 */
	public periInternalFrame(String[] headers){
		
		setHeaders(headers);
    	frame = new JDialog();
    	frame.setMinimumSize(new Dimension(1190, 380));
    	frame.setResizable(false);
       
 	   
        
        Dimension dim = frame.getToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - frame.getWidth() / 2, dim.height / 2
				- frame.getHeight() / 2);
		
 	    frame.setTitle("Set up the data for the Periphyton table. Ver. 0.2b August 17, 2009");
 	    frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
 	    frame.setAlwaysOnTop(true);
        Container content = frame.getContentPane();
        
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        
        content.setLayout(gbl);
        
               
        data = new Vector<String>();
        Iterator <String> it = columnNames.iterator();
        while(it.hasNext()){
        	it.next();
        	data.add("");
        }
        Vector<Vector<String>> rowData = new Vector<Vector<String>>();
        rowData.add(data);
        inputTable = new JTable(rowData, columnNames);
        inputTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        inputTable.setRowSelectionAllowed(false);
        inputTable.getTableHeader().setReorderingAllowed(false);
        formatCells();
        
        //<<
        JScrollPane scrollPane = new JScrollPane(inputTable);
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 6;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.NORTH;
        
        
        gbc.ipady = 130;
        gbl.setConstraints(scrollPane, gbc);
        content.add(scrollPane);
        
      //<<
        JLabel commentLabel = new JLabel("Comments: ");
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 1.0;
        gbc.ipady = 10;
        
        gbc.insets = new Insets(0, 10, 5, 0);
        
        
        gbl.setConstraints(commentLabel, gbc);
        content.add(commentLabel);  
        
        
        //<<
        comments = new JTextArea();
        comments.setLineWrap(true);
        JScrollPane textPane = new JScrollPane(comments);
        textPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        gbc.weightx = 0.0;
        gbc.weighty = 1.0;
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(5, 10, 10, 10);
        gbc.ipadx = 400;
        gbc.ipady = 100;
        gbl.setConstraints(textPane, gbc);
        content.add(textPane);
 	    
        //<<
        JLabel rowsToEnterLabel = new JLabel("Number of rows to add: ");
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.ipady = 10;
        
        gbc.weightx = 0.0;
        gbc.weighty = 1.0;
        
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 0, 5, 10);
        gbc.ipadx = 0;
        gbc.ipady = 10;
        
        gbl.setConstraints(rowsToEnterLabel, gbc);
        content.add(rowsToEnterLabel);
        
        //<<
        rowsToAdd = new JTextField(10);
        rowsToAdd.setText("1");
        gbc.gridx = 3;
        gbc.gridy = 1;
        
        gbc.weightx = 0.0;
        gbc.weighty = 1.0;
        gbc.ipady = 0;
        gbc.insets = new Insets(5, 0, 5, 10);
        gbc.ipadx = 100;
        gbc.ipady = 0;
         
        gbl.setConstraints(rowsToAdd, gbc);
        content.add(rowsToAdd);
      
        //<<
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(this);
        gbc.gridx = 4;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0.0;
        gbc.weighty = 1.0;
        gbc.ipady = 0;
        gbc.insets = new Insets(5, 150, 5, 10);
        gbc.ipadx = 0;
        gbc.ipady = 0;
         
        gbl.setConstraints(saveButton, gbc);
        content.add(saveButton);
 	    
        //<<
      //<<
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(this);
        gbc.gridx = 5;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 0;
        gbc.weighty = 1.0;
        gbc.ipady = 0;
        gbc.insets = new Insets(5, 0, 5, 10);
        gbc.ipadx = 0;
        gbc.ipady = 0;
         
        gbl.setConstraints(exitButton, gbc);
        content.add(exitButton);
        
        
 	   frame.setVisible(true);
 	    
 	    //System.out.println("  06-29-09     in   periInternalFrame   ");
    }
	
	public void setQTM(QueryTableModel arg){
		this.qtm = arg;
	}
	
	
	
	public void setMainClass(MainClass obj){
		this.ob = obj;
	}
	
	private void saveToDatabase(){
		inputTable.editCellAt(-1, -1);
		int indexOfFirstAddedRow = qtm.getRowCount();
		
		int numberOfColumns = inputTable.getModel().getColumnCount();
		int numberOfRowsToAdd = Integer.valueOf(rowsToAdd.getText());
		
		String [] rowToAdd; 
		
		
		String value;
	for (int i = 0; i < numberOfRowsToAdd; i++ ){
		
		rowToAdd = new String[numberOfColumns+2];
		rowToAdd[0]="";//sets keyValue to ""
		rowToAdd[rowToAdd.length-1] = comments.getText();
		
		for(int k = 1; k <= numberOfColumns; k++ ){
			value = (String) inputTable.getModel().getValueAt(0, k-1);
			rowToAdd[k] = value;
						
		}
		int indexOfRow = qtm.getTableCache().size();
		qtm.getTableCache().add(rowToAdd);
		qtm.setUpdatedData(rowToAdd,indexOfRow );
		
		/*System.out.println("Number of rows in the model "+qtm.getRowCount()+
				" number of keys "+qtm.getUpdatedData().size()+ " key "+ indexOfRow);*/
		
		
		/*Map<Integer, String[]> mod = qtm.getUpdatedData();
		Set<Integer> keys = mod.keySet();
		Iterator it = keys.iterator();
		while(it.hasNext())
			System.out.println(it.next());*/
		
	}
	
	qtm.fireTableRowsInserted(indexOfFirstAddedRow, qtm.getRowCount());
	ob.scrollToRow((qtm.getRowCount()-numberOfRowsToAdd)+1);
	
	frame.dispose();
	
	
	}


	private void setHeaders(String[] headersName){
		
		String [] headers = headersName;
		columnNames = new Vector<String>();
		
		for(int k=1; k < headers.length-1; k++ ){
			columnNames.add(headers[k]);
			}
		
		
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if(arg0.getActionCommand().equals("Save")){
			
			saveToDatabase();
			
		}
		else if(arg0.getActionCommand().equals("Exit")){
			frame.dispose();
		}
	}


	
	/**
	 * Formats Table cells
	 */
	private void formatCells() {
		TableColumnModel colMod = inputTable.getColumnModel();
		TableModel stm = inputTable.getModel();
		
		
		TableColumn column1; 
		
		/*right-alligns data in cells 
		 * 
		 * DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) table.getDefaultRenderer(table.getColumnClass(1));
		renderer.setHorizontalAlignment(SwingUtilities.RIGHT);*/
		
		
		column1 = colMod.getColumn(0);
		column1.setMinWidth(70);
		int numberofColumns = inputTable.getModel().getColumnCount()-1;
		for(int k = 1; k < numberofColumns; k++){// was 26 now numberofColumns
			column1 = colMod.getColumn(k);
			column1.setPreferredWidth(40);
		}
		
		
		
		TableCellRenderer headerRenderer = new JComponentTableCellRenderer();
		Enumeration <TableColumn> columns = inputTable.getColumnModel().getColumns();
		Border headerBorder = UIManager.getBorder("TableHeader.cellBorder");
		TableColumn column;
		JLabel label;
		int k=0;
		while (columns.hasMoreElements()) {
			 label = new JLabel("  "+stm.getColumnName(k), JLabel.LEFT);
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

}


