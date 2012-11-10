//import statements
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import java.awt.*;

/**
* SimpleTableFrame.java
* This class shows how to create a simple JTable
* using table model
*
* @author Rahul Sapkal(rahul@javareference.com)
*/
public class SimpleTableFrame extends JFrame
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//JTable table
    private JTable m_simpleTable;
    //JTable model
    private SimpleTableModel m_simpleTableModel;
    
    /**
    * Constructor
    *
    * @return void
    * @exception
    */
    public SimpleTableFrame()
    {
        super("Simple Table Demo");
        
        //creating the table model by passing the data
        //the data is vector of MachineData objects, generated
        //by a getDummyData function
        m_simpleTableModel = new SimpleTableModel(getDummyData());
        
        //creating the JTable by passing the table model
        m_simpleTable = new JTable(m_simpleTableModel);
        
        //Add the JTable to the scroll pane to handle table
        //scrolling and to display the table header which displays
        //the column names
        JScrollPane scrollPane = new JScrollPane(m_simpleTable);
        
        //add the scroll pane to the frame
        getContentPane().add(scrollPane);
    }
    
    /**
    * getDummyData
    * generating dummy MachineData object and creating a vector
    */
    private Vector<MachineData> getDummyData()
    {
        Vector<MachineData> dummyMacData = new Vector<MachineData>(10, 10);
        
        dummyMacData.addElement(new MachineData
                               (new Integer(100), "Robert", "288.209.140.223",
                                "Win NT", "Engineering"));
        dummyMacData.addElement(new MachineData
                               (new Integer(105), "Rahul", "288.209.140.214",
                                "Solaris 5", "Engineering"));
        dummyMacData.addElement(new MachineData
                               (new Integer(110), "Daina", "288.209.140.220",
                                "HP UX", "Engineering"));
        dummyMacData.addElement(new MachineData
                               (new Integer(106), "Vijay", "288.209.140.215",
                                "Win NT", "Engineering"));
        dummyMacData.addElement(new MachineData
                                (new Integer(302), "Andy", "288.209.142.203",
                                "Win 98", "Human Resource"));
        dummyMacData.addElement(new MachineData
                                (new Integer(504), "Tom", "288.209.141.207",
                                "Win NT", "Marketing"));
        dummyMacData.addElement(new MachineData
                                (new Integer(104), "Bill", "288.209.140.222",
                                "Win NT", "Engineering"));
        
        return dummyMacData;
    }

    /**
    * main
    * main method, starting point
    */
    public static void main(String[] arg)
    {
        SimpleTableFrame m = new SimpleTableFrame();
        
        m.setVisible(true);
        m.setSize(new Dimension(600, 300));
        m.validate();
    }
    
    /**
    * SimpleTableModel.java
    * This class is the table nodel
    *
    * @author Rahul Sapkal(rahul@javareference.com)
    */
    public class SimpleTableModel extends AbstractTableModel
    {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		//Columns Number.
        public static final int    MAC_CODE_COL = 0;                                   
        public static final int    MAC_NAME_COL = 1;
        public static final int    IP_COL = 2;
        public static final int    OS_COL = 3;  
        public static final int    DOMAIN_COL = 4;
        
        //Names of the columns
        public String[] m_colNames = {"Machine Code",
                                      "Machine Name",
                                      "IP Address",
                                      "Operating System",
                                      "Domain"};
        // Types of the columns.
        @SuppressWarnings("unchecked")
		public Class[]  m_colTypes = {Integer.class,
                                      String.class, 
                                      String.class,
                                      String.class,
                                      String.class};
        
        //store the data
        Vector<?> m_macDataVector;
        
        /**
        * Constructor
        */
        public SimpleTableModel(Vector<?> macDataVector)
        {
            super();
            
            //store the data
            m_macDataVector = macDataVector;
        }

        /**
* getColumnCount
* Number columns same as the column array length
*/
        public int getColumnCount()
        {  
            return m_colNames.length;
        }
        
        /**
* getRowCount
* Row count same as the size of data vector
*/
        public int getRowCount()    
        {  
            return m_macDataVector.size();
        }
        
        
        /**
* setValueAt
* This function updates the data in the TableModel
* depending upon the change in the JTable
*/
        public void setValueAt(Object value, int row, int col) 
        {    
            MachineData macData = (MachineData)(m_macDataVector.elementAt(row));
            
            switch(col)
            {
            case MAC_CODE_COL : macData.setMacCode((Integer) value);
                                break;
            case MAC_NAME_COL : macData.setMacName((String) value);
                                break;
            case IP_COL : macData.setMacIP((String) value);
                          break;
            case OS_COL : macData.setMacOS((String) value);
                          break;
            case DOMAIN_COL : macData.setMacDomain((String) value);
                              break;
            }
        }

        
        public String getColumnName(int col)
        {
            return m_colNames[col];
        }

        public Class<?> getColumnClass(int col) 
        {
            return m_colTypes[col];
        }

        /**
* getValueAt
* This function updates the JTable depending upon the
* data in the TableModel
*/
        public Object getValueAt(int row, int col)
        {
            MachineData macData = (MachineData)(m_macDataVector.elementAt(row));
            
            switch(col)
            {
            case MAC_CODE_COL : return macData.getMacCode();
            case MAC_NAME_COL : return macData.getMacName();
            case IP_COL : return macData.getMacIP();
            case OS_COL : return macData.getMacOS();
            case DOMAIN_COL : return macData.getMacDomain();
            }

            return new String();
        }
    }
    
    /**
    * MachineData.java
    * The data object to store the Machine Information
    *
    * @author Rahul Sapkal(rahul@javareference.com)
    */
    public class MachineData    {
        private Integer m_macCode;
        private String m_macName;
        private String m_macIP;
        private String m_macOS;
        private String m_macDomain;
        
        public MachineData()
        {
        }

        public MachineData(Integer macCode, String macName,
                           String macIP, String macOS,
                           String macDomain)
        {
            m_macCode = macCode;
            m_macName = macName;
            m_macIP = macIP;
            m_macOS = macOS;
            m_macDomain = macDomain;
        }
        
        public Integer getMacCode()
        {
            return m_macCode;
        }
        
        public String getMacName()
        {
            return m_macName;
        }
        
        public String getMacIP()
        {
            return m_macIP;
        }
        
        public String getMacOS()
        {
            return m_macOS;
        }
        
        public String getMacDomain()
        {
            return m_macDomain;
        }
        
        public void setMacCode(Integer macCode)
        {
            m_macCode = macCode;
        }
        
        public void setMacName(String macName)
        {
            m_macName = macName;
        }
        
        public void setMacIP(String macIP)
        {
            m_macIP = macIP;
        }
        
        public void setMacOS(String macOS)
        {
            m_macOS = macOS;
        }
        
        public void setMacDomain(String macDomain)
        {
            m_macDomain = macDomain;
        }
    }   
}