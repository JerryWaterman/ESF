   
import java.awt.*;   
import java.awt.event.*;   
import javax.swing.*;   
    
class shemInputForm {   
   
  JLabel jlab;    
  JButton jbtnAlpha;  
  JButton jbtnBeta;  
  JTextField firstnameField, lastnameField,emailField;
  
  shemInputForm() {   
 
    // Create a new JFrame container. Use the default 
    // border layout. 
    JFrame jfrm = new JFrame("SHEM Input Form"); 
 
    // Specify FlowLayout manager.   
    jfrm.getContentPane().setLayout(new FlowLayout());  
    //jfrm.getContentPane().setLayout(new GridBagLayout()); 
    
    // Give the frame an initial size. 
    jfrm.setLocation(20, 20);
    jfrm.setSize(900, 700);   
   
    // Terminate the program when the user closes the application. 
    jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
   
    // Create the first JPanel.  
    JPanel jpnl = new JPanel(); 
    jpnl.setLayout(new FlowLayout());
    //jpnl.setLayout(new BorderLayout());  
 
    // Set the preferred size of the first panel. 
    jpnl.setPreferredSize(new Dimension(800, 500)); 
 
    // Make the panel opaque. 
    jpnl.setOpaque(true); 
 
    // Add a blue border to the panel. 
    jpnl.setBorder(BorderFactory.createLineBorder(Color.BLUE)); 
       
    //Create the text fields
    firstnameField = new JTextField();
    firstnameField.setColumns(20);
    
    jpnl.add(Box.createRigidArea(new Dimension(5,50)));
    jpnl.add(new JLabel("First Name: "));
    jpnl.add(firstnameField);
    
    lastnameField = new JTextField();
    lastnameField.setColumns(30);
    jpnl.add(Box.createRigidArea(new Dimension(2000, 20)));
    jpnl.add(new JLabel("Last Name: "));
    jpnl.add(lastnameField);
    
    //emailField = new JTextField();
    //emailField.setColumns(50);
    //jpnl.add(emailField);
    
    // Create the second JPanel. 
    JPanel jpnl2 = new JPanel(); 
 
    // Set the preferred size of the second panel. 
    jpnl2.setPreferredSize(new Dimension(700, 150)); 
 
    // Make the panel opaque. 
    jpnl2.setOpaque(true); 
 
    // Add a color border to the panel. 
    jpnl2.setBorder( 
         BorderFactory.createLineBorder(Color.MAGENTA)); 
 
    // Create a label.   
    jlab = new JLabel("Press a button.");   
   
    // Make two buttons.   
    jbtnAlpha = new JButton("Alpha"); 
    jbtnBeta = new JButton("Beta");   
  
    // Add action listeners for the buttons. 
    jbtnAlpha.addActionListener(new ActionListener() {  
      public void actionPerformed(ActionEvent ae) {  
        jlab.setText("Alpha pressed.");   
      }  
    });  
  
    jbtnBeta.addActionListener(new ActionListener() {  
      public void actionPerformed(ActionEvent ae) {  
        jlab.setText("Beta pressed.");   
      }  
    });  
   
    // Add the buttons and label to the panel. 
    jpnl.add(jbtnAlpha);    
    jpnl.add(jbtnBeta);    
    jpnl.add(jlab); 
 
    // Add some labels to the second JPanel. 
    jpnl2.add(new JLabel("One"));    
    jpnl2.add(new JLabel("Two")); 
    jpnl2.add(new JLabel("Three")); 
 
    // Add the panels to the frame.   
    jfrm.getContentPane().add(jpnl); 
    jfrm.getContentPane().add(jpnl2); 
 
    // Display the frame.   
    jfrm.setVisible(true);   
  }   
    
  public static void main(String args[]) {   
    // Create the frame on the event dispatching thread.   
    SwingUtilities.invokeLater(new Runnable() {   
      public void run() {   
        new shemInputForm();   
      }   
    });   
  }   
}