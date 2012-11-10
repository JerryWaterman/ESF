import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;

public class Info extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel titleLabel = new JLabel("Title: ",SwingConstants.RIGHT);
	private JTextField title;
	private JLabel addressLabel = new JLabel("Address: ", SwingConstants.RIGHT);
	private JTextField address;
	private JLabel typeLabel = new JLabel("Type: ", SwingConstants.RIGHT);
	private JTextField type;
	
	public Info() {
		super("Site Information");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//site name
		String respones1 = JOptionPane.showInputDialog(null, "Enter the site title");
		title = new JTextField(respones1, 40);
		
		// site address
		String respones2 = JOptionPane.showInputDialog(null, "Enter the site address");
		address = new JTextField(respones2, 20);
		
		//Site type
		String[] choices = {"Personal","Commercial","Unknown"};
		int respones3 = JOptionPane.showOptionDialog(null, "What type of site is it?", "Site Type", 0, JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
	    type = new JTextField(choices[respones3], 20);
	    
	    JPanel pane = new JPanel();
	    pane.setLayout(new GridLayout(3,2));
	    pane.add(titleLabel);
	    pane.add(title);
	    pane.add(addressLabel);
	    pane.add(address);
	    pane.add(typeLabel);
	    pane.add(type);
	    
	    setContentPane(pane);
	}
	 public static void main(String[] auguments){
		 try {
			 UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		 } catch (Exception e) {
			 System.err.println("Couldn't use the system look + feel" + e);
		 }
		JFrame frame = new Info();
		frame.pack();
		frame.setVisible(true);
	 }

}
