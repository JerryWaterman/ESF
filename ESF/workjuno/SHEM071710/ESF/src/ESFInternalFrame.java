
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.LayoutManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/* Used by InternalFrameDemo.java. */
public class ESFInternalFrame extends JInternalFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField hostField, queryField;
	String url, currentURL= "jdbc:mysql://134.67.216.25:3306";
	JTextField loginName;
	JLabel loginText, pwText;
	LayoutManager Layout; 
	Container Panel;
	String newline = "\n";
	private ResultSetMetaData meta;

	private Statement statement;

	public String SQL_query;

	private JDesktopPane desktop_pane;
	//private DesktopTest();
	static int openFrameCount = 0;
    static final int xOffset = 200, yOffset = 50;

    public ESFInternalFrame() throws SQLException {
    	  		
        super("Please Login");
        //("Login attempt " + (++openFrameCount), 
          //    true, //resizable
          //    true, //closable
          //    true, //maximizable
          //    true);//iconifiable

        if (openFrameCount == 3){
        	System.exit(0);    	
        }
        JLabel loginText = new JLabel("  Login:");
        JTextField loginName = new JTextField(20);
        JLabel pwText = new JLabel("Password:");
        JTextField pw = new JTextField(10);	
        //...Create the GUI and put it in the window...
        this.desktop_pane = new JDesktopPane();
        this.getContentPane().setLayout(new FlowLayout());
        this.getContentPane().add(this.desktop_pane, FlowLayout.LEFT);
        this.getContentPane().add(loginText);
        this.getContentPane().add(loginName);
        this.getContentPane().add(pwText);
        this.getContentPane().add(pw);
        this.setVisible(true);
        
        //JPanel namePanel = new JPanel(new BorderLayout()); 
        //JLabel nameLabel = new JLabel("Login Name:");
        //...Then set the window size or call pack...
        setSize(300,300);

        //Set the window's location.
        setLocation(xOffset*openFrameCount, yOffset*openFrameCount);
        //need to add listener
        
        // add buttons  "OK"   "Cancel" "New User"
        
    MainClass mc = new MainClass();
    QueryTableModel qt = new QueryTableModel();
    hostField = new JTextField("jdbc:mysql://134.67.216.25:3306");
    // from  RTP MySQL  table =  ESFusers 
    //  UserLoginID    UserPassWord
    queryField = new JTextField("SELECT * FROM Jerry.ESFusers");
    qt.setHostURL(hostField.getText().trim());
    qt.setQuerySQL(queryField.getText().trim());
    
    
    
    System.out.println("SQL_query = "+ queryField.getText().trim());//null why?
    
    url = currentURL;
    
    qt.setHostURL(url);
    
    qt.setQuerySQL(queryField.getText().trim());
    qt.setQuery(); //line 725 in MainClass.java
    
    
    //ResultSet rs = statement.executeQuery(SQL_query);
    
    //meta = rs.getMetaData();
    
    //System.out.println("Column Count "+qt.getColumnCount());
    //ask for user login name
    //System.out.println("JInternalFrame meta "+meta.getColumnName(1));
    
    
    
  
    
    }
/*
 * http://www.daniweb.com/forums/thread119702-2.html
 * //LoginForm.java

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LoginForm extends JFrame {

    // Variables declaration
        private JLabel jLabel1;
        private JLabel jLabel2;
        private JTextField jTextField1;
        private JPasswordField jPasswordField1;
        private JButton jButton1;
        private JPanel contentPane;
        boolean loop = false;
    // End of variables declaration

    public LoginForm() {
        super();
        create();
        this.setVisible(true);
    }

    private void create() {         
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        jTextField1 = new JTextField();
        jPasswordField1 = new JPasswordField();
        jButton1 = new JButton();
        contentPane = (JPanel)this.getContentPane();

        //
        // jLabel1
        //
            jLabel1.setHorizontalAlignment(SwingConstants.LEFT);
            jLabel1.setForeground(new Color(0, 0, 255));
            jLabel1.setText("username:");
        //
        // jLabel2
        //
            jLabel2.setHorizontalAlignment(SwingConstants.LEFT);
            jLabel2.setForeground(new Color(0, 0, 255));
            jLabel2.setText("password:");
        //
        // jTextField1
        //
            jTextField1.setForeground(new Color(0, 0, 255));
            jTextField1.setSelectedTextColor(new Color(0, 0, 255));
            jTextField1.setToolTipText("Enter your username");

        //
        // jPasswordField1
        //
            jPasswordField1.setForeground(new Color(0, 0, 255));
            jPasswordField1.setToolTipText("Enter your password");

        //
        // jButton1
        //
            jButton1.setBackground(new Color(204, 204, 204));
            jButton1.setForeground(new Color(0, 0, 255));
            jButton1.setText("Login");
            jButton1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButton1_actionPerformed(e);
                }

            });
            
        //
        // contentPane
        //
            contentPane.setLayout(null);
            contentPane.setBorder(BorderFactory.createEtchedBorder());
            contentPane.setBackground(new Color(204, 204, 204));
            addComponent(contentPane, jLabel1, 5,10,106,18);
            addComponent(contentPane, jLabel2, 5,47,97,18);
            addComponent(contentPane, jTextField1, 110,10,183,22);
            addComponent(contentPane, jPasswordField1, 110,45,183,22);
            addComponent(contentPane, jButton1, 150,75,83,28);

        //
        // login
        //
            this.setTitle("Login To Members Area");
            this.setLocation(new Point(76, 182));
            this.setSize(new Dimension(335, 141));
            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            this.setResizable(false);
    } //end of create()

     Add Component Without a Layout Manager (Absolute Positioning) 
    private void addComponent(Container container,Component c,int x,int y,int width,int height) {
        c.setBounds(x,y,width,height);
        container.add(c);
    } //end of addComponent()

    private void jButton1_actionPerformed(ActionEvent e) {
        
        String username = new String(jTextField1.getText());
        String password = new String(jPasswordField1.getText());

        if(username.equals("") || password.equals("")) { // If password and username is empty > Do this >>>
        
            jButton1.setEnabled(false);
            JLabel errorFields = new JLabel("<HTML><FONT COLOR = Blue>You must enter a username and password to login.</FONT></HTML>");	
            JOptionPane.showMessageDialog(null,errorFields); 
            jTextField1.setText("");
            jPasswordField1.setText("");
            jButton1.setEnabled(true);
            this.setVisible(true);
        } //end of if
        else {

            UserDB udb=new UserDB();
            boolean canLogin = udb.validateUser(username, password);

            do{
                loop = false;

                if(!canLogin) {
                    JOptionPane.showMessageDialog(null, "Wrong Username or Password, try again", "Warning !!!", JOptionPane.WARNING_MESSAGE);
                    jTextField1.setText("");
                    jPasswordField1.setText("");
                    loop = true;
                    break;
                } //end of if
                else {
                    JOptionPane.showMessageDialog(null, "Welcome, you can use the program ...", "Welcome", JOptionPane.WARNING_MESSAGE);	
                } //end of else
            } //end of do
            while (loop); 
        } //end of else  
    } //end of jButton1_actionPerformed()  

    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } //end of try
        
        catch (Exception ex) {
            System.out.println("Failed loading L&F: ");
            System.out.println(ex);
        } //end of catch
        
        new LoginForm();
    }; //end of main()

} //end of LoginForm
 * */



    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
