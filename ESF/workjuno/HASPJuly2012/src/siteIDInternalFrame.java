import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.mysql.jdbc.Statement;

import java.awt.*;
import java.sql.ResultSet;


/* Used by InternalFrameDemo.java. */
public class siteIDInternalFrame extends JDialog { //implements ListSelectionListener {
	//JInternalFrame {
    /**
	 * 
	 */
	private JDialog vframe;
	private static final long serialVersionUID = 1L;
	//static int openFrameCount = 0;
    //static final int xOffset = 30, yOffset = 30;
    public JLabel label;
    public String vsite;
    public QueryTableModel qtm = null;
    public JTable table;
    public MainClass ob;
    public JTextField hostField, queryField; 
    public Statement statement;
    public String SQL_query;
    
    public siteIDInternalFrame() {
    	/*hostField = new JTextField("jdbc:mysql://134.67.216.25:3306");
    	//732-7000 CC   Elizabeth 732-4831  
    	qtm.setHostURL(hostField.getText().trim());
    	queryField = new JTextField("SELECT * FROM Jerry.August112011ESFperiphyton");
    	qtm.setQuerySQL(queryField.getText().trim());
    	System.out.println(" SQL query "+queryField);
    	try {
    		ResultSet rs = statement.executeQuery(SQL_query);
    	} 	catch (Exception e){
    		e.printStackTrace();
    	}
    	*/
    	// Create a new table instance
    	
    	//table = new JTable();
    	//table.setModel(qtm);
    	//System.out.println(" Column name = "+qtm.getColumnName(1));
    	
    	// Handle the listener
    	//ListSelectionModel selectionModel = table.getSelectionModel();
    	//selectionModel.addListSelectionListener( this );
    	//ESFmain4c ESFmain4c = new ESFmain4c();
    	    
    	//qtm.refreshData();
    	//System.out.println("qtm refreshData ");
    	//MainClass.queryField = new JTextField("SELECT * FROM Jerry.August112011ESFperiphyton");
    	//ListSelectionEvent event = null;
		//ob.valueChanged(event);
    	JLabel label = new JLabel("   Site ID "+vsite);
    	vframe = new JDialog();
    	vframe.setMinimumSize(new Dimension(240,80));
    	vframe.setResizable(true);
    	
    	vframe.setLocation(90,20); //xOffset*openFrameCount, yOffset*openFrameCount);
        
    	//table.setModel(qtm);
        //table.getModel().addTableModelListener(this);
    	
        vframe.setTitle("Site ID test-c"); 
        vframe.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel(new GridLayout(1,2,10,10));
        // add buttons to the panel
        panel.add(label);

        //...Create the GUI and put it in the window...
        //...Then set the window size or call pack...
        //vframe.setSize(240, 80);
        //System.out.println("line 28, inside siteIDInternalFrame() ");
        vframe.setAlwaysOnTop(true);
        vframe.getContentPane().add(panel);
        vframe.setVisible(true);
        //Set the window's location.
        
        //TableModel tm;
        //tm = jtabOrders.getModel();

        //tm.addTableModelListener(new TableModelListener()        
    }
    
    /*public void setQTM(QueryTableModel arg){
		this.qtm = arg;
	} 
    
    public void setMainClass(MainClass obj){
		this.ob = obj;
	}*/
    
    //public void currentRow(){
    //	qtm.refreshData();
    //}

	//@Override
	//public void tableChanged(TableModelEvent tme) {
		// TODO Auto-generated method stub
	//	if (tme.getType() == TableModelEvent.UPDATE) {
	//          System.out.println("Cell " + tme.getFirstRow() + ", " + tme.getColumn() + " changed."
	//              + " The new value: " + tm.getValueAt(tme.getFirstRow(), tme.getColumn()));
	//        }
	//}
    
}
