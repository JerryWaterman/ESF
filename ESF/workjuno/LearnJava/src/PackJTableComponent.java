import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class PackJTableComponent {
  JTable table;
  public static void main(String[] args) {
	  new PackJTableComponent();
}

public PackJTableComponent() {
  JFrame frame = new JFrame("Packing a JTable Component!");
  JPanel panel = new JPanel();
  String data[][] = {{"Vinod","Computer","3","67"},
  {"Rahul","History","2","89"},
  {"Manoj","Biology","4","54"},
  {"Sanjay","PSD","5","90"}};
  String col [] = {"Name","Course","Year","Phone"};
  DefaultTableModel model = new DefaultTableModel(data,col);
  table = new JTable(model){
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Dimension getPreferredScrollableViewportSize() {
		return getPreferredSize();
	}
  };  
  JTableHeader header = table.getTableHeader();
  header.setBackground(Color.yellow);
  JScrollPane pane = new JScrollPane(table);
  panel.add(pane);
  frame.add(panel);
  frame.setSize(400,250);
  frame.setUndecorated(true);
  frame.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  frame.setVisible(true);
  }
} 