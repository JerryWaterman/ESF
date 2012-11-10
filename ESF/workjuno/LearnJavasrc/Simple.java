import javax.swing.JFrame;

public class Simple extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Simple() {
		
		setSize(300, 200);
		setTitle("Simple");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	
	public static void main(String[] args) {
		
		Simple simple = new Simple();
		simple.setVisible(true);
		
	}
	
	
	
}