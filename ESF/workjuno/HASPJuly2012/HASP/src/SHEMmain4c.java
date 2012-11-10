
//HASP
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

//import ComboExample.HASPinputSrceen;
/*
 * InternalFrameDemo.java requires:
 *   MyInternalFrame.java
 */
public class SHEMmain4c extends JFrame implements ActionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JDesktopPane desktop;

    public SHEMmain4c() {
        super("SHEM (HASP) Database Application, Version 0.04d - April 28, 2011");

        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 8;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                  screenSize.width  - inset*2,
                  screenSize.height - inset*5);//*2

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
        menuItem = new JMenuItem("New User");
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
               
        menu = new JMenu("Option");  //Omenu
        menu.setMnemonic(KeyEvent.VK_O);
        menuBar.add(menu);
        
        //Set up the menu item.
        menuItem = new JMenuItem("HASP");
        menuItem.setMnemonic(KeyEvent.VK_P);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.ALT_MASK));
        //menuItem.setActionCommand("HASP");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        //JMenuItem menuOp = new JMenuItem("Search by");
        JMenuItem menuOp = new JMenuItem("Input Form for HASP");
        menuOp.setMnemonic(KeyEvent.VK_I);
        menuOp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.ALT_MASK));
        //menuItem.setActionCommand("HASPform");
        menuOp.addActionListener(this);
        menu.add(menuOp);
        
        JMenuItem menuRe = new JMenuItem("Reports");
        menuRe.setMnemonic(KeyEvent.VK_R);
        menuRe.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));
        menuRe.addActionListener(this);
        menu.add(menuRe);
       
        menu.addSeparator();
        //menuIn = new JMenu("Input Data");
        //menuIn.setMnemonic(KeyEvent.VK_I);
        //menuIn.addActionListener(this);
        //menu.add(menuIn);
                
       // menuItem = new JMenuItem("for Sediment");
        //menuIn.add(menuItem);
        //menuItem = new JMenuItem("for Invert");
        //menuIn.add(menuItem);
        
        //a submenu
        //menu.addSeparator();
        submenu = new JMenu("Export data");
        submenu.setMnemonic(KeyEvent.VK_S);
         
        menuItem = new JMenuItem("to SAS");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
        submenu.add(menuItem);

        menuItem = new JMenuItem("to Excel");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));
        submenu.add(menuItem);
        menu.add(submenu);   
                
        //Set up the third menu item.
        JMenu menuHelp = new JMenu("Help");
        menu.setMnemonic(KeyEvent.VK_H);
        menuBar.add(menuHelp);
        
        JMenuItem menuAb = new JMenuItem("About HASP");
        menuAb.setMnemonic(KeyEvent.VK_A);
        menuAb.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.ALT_MASK));
        menuAb.setActionCommand("aesf");
        menuAb.addActionListener(this);
        menuHelp.add(menuAb);
        
        JMenuItem menuSW = new JMenuItem("About HASP Software");
        menuSW.setMnemonic(KeyEvent.VK_W);
        menuSW.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.ALT_MASK));
        //menuItem.setActionCommand("new");
        menuSW.addActionListener(this);
        menuHelp.add(menuSW);
   
        return menuBar;
    }
    //React to menu selections.
    public void actionPerformed(ActionEvent e) {
        if ("new".equals(e.getActionCommand())) { //new
        	//System.out.println("getAction new");
            createFrame();
        }
        //System.out.println("before HASP "+e.getActionCommand());
        if ("HASP".equals(e.getActionCommand())) {
        	//System.out.println("getAction   create Periphyton Frame");
            createPeriphytonFrame();
        } 
        if ("Input Form for HASP".equals(e.getActionCommand())) { //new
        	//System.out.println("getAction   HASP Frame");
            HASPcreateFrame();
        }
        if  ("quit".equals(e.getActionCommand())) {
        	quit();
        }
        if ("aesf".equals(e.getActionCommand())) {
        	//System.out.println("About ESF");
        }
    }
    //Create a new internal frame.
    protected void createFrame() {
        SHEMInternalFrame frame = new SHEMInternalFrame();
        //dataInternalFrame frame = new dataInternalFrame();
    	frame.setVisible(true); //necessary as of 1.3
        desktop.add(frame);
        try {
            frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {}
    }
    //Create a new internal frame with the Periphyton table
    protected void createPeriphytonFrame() {
    	//System.out.println("create Periphyton Frame");
        MainClass peryPithonframe = new MainClass();
        
        peryPithonframe.setResizable(true);
        peryPithonframe.setIconifiable(true);
        peryPithonframe.setMaximizable(true);
        peryPithonframe.setClosable(false);
        peryPithonframe.setVisible(true);
        
        desktop.add(peryPithonframe);
        
        try {
        	peryPithonframe.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {}
    }
    protected void HASPcreateFrame() {
        //SHEMInputFrame frame = new SHEMInputFrame();
        //HASPinput frame = new HASPinput();
        HASPinputScreen frame = new HASPinputScreen();
    	//HASPInputForm frame = new HASPInputForm();
        
    	//InputForm frame = new InputForm();
    	//frame.setVisible(true); //necessary as of 1.3
        //desktop.add(frame);
        /*try {
            frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {}*/
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
        SHEMmain4c frame = new SHEMmain4c();
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
