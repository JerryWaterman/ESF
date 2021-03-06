// This program connect to the VM at RTP
// give the use the choose on their tables and ask if other users can read and/or write to their tables
//      
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
/*
 * InternalFrameDemo.java requires:
 *   MyInternalFrame.java
 */
public class ESFmain4c extends JFrame implements ActionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String DBt = "";
	JDesktopPane desktop;
	
    public ESFmain4c() {
        super("ESF Database Application, Version 0.03a - September 15, 2011");

        int inset = 2;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                  screenSize.width  - inset*2,
                  screenSize.height - inset*20);//2
        //Set up the GUI.
        desktop = new JDesktopPane(); //a specialized layered pane
        //createFrame(); //create first "window"
        setContentPane(desktop);
        setJMenuBar(createMenuBar());

        //Make dragging a little faster but perhaps uglier.
        desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
    }

    public JMenuBar createMenuBar() {
    	JMenuBar menuBar;
		JMenu menu, menuIn, submenu;
        JMenuItem menuItem;
    	menuBar = new JMenuBar();
                
        //Set up the menu bar.
        menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(menu);
               
        //Set up the first menu item.
        menuItem = new JMenuItem("Log in");
        menuItem.setMnemonic(KeyEvent.VK_N);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("new");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        //Set up the second menu item.
        menuItem = new JMenuItem("Exit");
        menuItem.setMnemonic(KeyEvent.VK_Q);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("quit");
        menuItem.addActionListener(this);
        menu.add(menuItem);
               
        menu = new JMenu("Tables");  //Omenu
        menu.setMnemonic(KeyEvent.VK_T);
        menuBar.add(menu);
        
        //Set up the menu item.
        menuItem = new JMenuItem("Experiment Data");
        menuItem.setMnemonic(KeyEvent.VK_E);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("Experiment");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Nutrient Data");
        menuItem.setMnemonic(KeyEvent.VK_N);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("Nutrient");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Periphyton Data");
        menuItem.setMnemonic(KeyEvent.VK_P);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("Periphyton");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Site Data");
        menuItem.setMnemonic(KeyEvent.VK_S);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("Site");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Water Quality Data");
        menuItem.setMnemonic(KeyEvent.VK_W);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("WaterQuality");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        JMenuItem menuOp = new JMenuItem("Search by");
        menuOp.setMnemonic(KeyEvent.VK_S);
        menuOp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
        //menuItem.setActionCommand("new");
        menuOp.addActionListener(this);
        menu.add(menuOp);
        
        JMenuItem menuRe = new JMenuItem("Reports");
        menuRe.setMnemonic(KeyEvent.VK_R);
        menuRe.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));
        menuRe.addActionListener(this);
        menu.add(menuRe);
       
        menu.addSeparator();
        menuIn = new JMenu("Input Data");
        menuIn.setMnemonic(KeyEvent.VK_I);
        menuIn.addActionListener(this);
        menu.add(menuIn);
                
        menuItem = new JMenuItem("for Sediment");
        menuIn.add(menuItem);
        menuItem = new JMenuItem("for Invert");
        menuIn.add(menuItem);
        
        //a submenu
        //menu.addSeparator();
        submenu = new JMenu("Export data");
        submenu.setMnemonic(KeyEvent.VK_S);
         
        menuItem = new JMenuItem("to SAS");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_2, ActionEvent.ALT_MASK));
        submenu.add(menuItem);

        menuItem = new JMenuItem("to Excel");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_E, ActionEvent.ALT_MASK));
        submenu.add(menuItem);
        menu.add(submenu);   
                
        //Set up the third menu item.
        JMenu menuHelp = new JMenu("Help");
        menu.setMnemonic(KeyEvent.VK_H);
        menuBar.add(menuHelp);
        
        JMenuItem menuAb = new JMenuItem("About ESF");
        menuAb.setMnemonic(KeyEvent.VK_A);
        menuAb.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.ALT_MASK));
        menuAb.setActionCommand("aesf");
        menuAb.addActionListener(this);
        menuHelp.add(menuAb);
        
        JMenuItem menuSW = new JMenuItem("About ESF Software");
        menuSW.setMnemonic(KeyEvent.VK_W);
        menuSW.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.ALT_MASK));
        //menuItem.setActionCommand("new");
        menuSW.addActionListener(this);
        menuHelp.add(menuSW);
   
        return menuBar;
    }
    //React to menu selections.
    public void actionPerformed(ActionEvent e) {
        //String DBt = "";
        if ("new".equals(e.getActionCommand())) { //new
            try {
				createFrame();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
        if ("Experiment".equals(e.getActionCommand())) {
        	DBt = "Experiment"; 
        	//String DBt = e.
        	//System.out.println("if Nutrient getActionCommand  DBt  = "+DBt);
        	createPeriphytonFrame();
        	DBt = " ";
        }
        if ("Nutrient".equals(e.getActionCommand())) {
        	DBt = "Nutrient"; 
        	createPeriphytonFrame();
        	DBt = " ";
        }
        if ("Site".equals(e.getActionCommand())) {
        	DBt = "Site"; 
        	createPeriphytonFrame();
        	DBt = " ";
        }
        if ("WaterQuality".equals(e.getActionCommand())){
        	DBt = "WaterQuality";
        	//System.out.println("in if WaterQuality = "+DBt);
        	createPeriphytonFrame();
        }
        if ("Periphyton".equals(e.getActionCommand())) {
        	DBt = "Periphyton";
          	createPeriphytonFrame();
        } 
        if  ("quit".equals(e.getActionCommand())) {
        	quit();
        }
        if ("aesf".equals(e.getActionCommand())) {
        	//System.out.println("About ESF");
        }
    }
    //Create a new internal frame.
    protected void createFrame() throws SQLException {
        ESFInternalFrame frame = new ESFInternalFrame();
        frame.setVisible(true); //necessary as of 1.3
        desktop.add(frame);
        try {
            frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {}

    }
    
    //Create a new internal frame with the Periphyton table
    protected void createPeriphytonFrame() {
        //0DBt = "Nutrient";
    	//System.out.println(" in createPeriphyton()  DBt = "+DBt);
        MainClass peryPithonframe = new MainClass();
        
        peryPithonframe.setResizable(true);
        peryPithonframe.setIconifiable(true);
        peryPithonframe.setMaximizable(true);
        peryPithonframe.setClosable(false);
        peryPithonframe.setVisible(true);
        peryPithonframe.setName(DBt);
        
        desktop.add(peryPithonframe);
        
        try {
        	peryPithonframe.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {}
    }
    //Quit the application.
    protected void quit() {
        System.exit(0);
    }
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        //InternalFrameDemo frame = new InternalFrameDemo();
        ESFmain4c frame = new ESFmain4c();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Display the window.
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
