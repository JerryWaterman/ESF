
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JDialog;
 

public class periLabelFrame  {

	
	private JDialog frame;
	private Vector <String> data;
	
	public periLabelFrame() {
	
		frame = new JDialog();
		frame.setMinimumSize(new Dimension(1100, 280));
		frame.setResizable(false);
		
		Dimension dim = frame.getToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getWidth()/2,dim.height/2-frame.getHeight()/2);
		frame.setTitle("Print Avery Label 5267 for Periphyton");
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frame.setAlwaysOnTop(true);
		Container content = frame.getContentPane();
		
		GridBagLayout gbl  = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		
		content.setLayout(gbl);
		
		
		System.out.println("Periphyton Labels");
	}
	
  
	
}
