import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;

public class JComboBoxExample extends JPanel {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
JLabel jlbPicture;

public JComboBoxExample() {
String[] comboTypes = { "Numbers", "Alphabets", "Symbols"};

Vector<Object> vector = new Vector<Object>();
Connection con = null;
String url = "jdbc:mysql://localhost:3306";;
//String db = "test";        //userdetails";
//String driver = "com.mysql.jdbc.Driver";

try {
	String user = "root";
	String pass = "hoTTub11";
    Class.forName("com.mysql.jdbc.Driver");
    //System.out.println(" url"+url);
    con = DriverManager.getConnection(url, user, pass);

    try{
    	
    	Statement st = con.createStatement();
    	ResultSet res = st.executeQuery("SELECT * FROM test.epa_site");

    	while (res.next()) {
    		vector.add(res.getString("site"));
        }
    	con.close();
    }
    catch (SQLException s){
    	System.out.println("SQL code does not execute.");
    } 
}
	catch (Exception e){
		e.printStackTrace();
}


JComboBox comboTypesList = new JComboBox(vector);
comboTypesList.setSelectedIndex(2);
comboTypesList.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
	JComboBox jcmbType = (JComboBox)e.getSource();
	String cmbType = (String)jcmbType.getSelectedItem();
	System.out.println(" cmbType = "+cmbType);
	jlbPicture.setIcon(new ImageIcon(""+cmbType.trim().toLowerCase() + ".jpg"));
}
}	
);

// Set up the picture
jlbPicture = new JLabel(new ImageIcon("" +
comboTypes[comboTypesList.getSelectedIndex()] +
".jpg"));
jlbPicture.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));

jlbPicture.setPreferredSize(new Dimension(177, 122+10));

// Layout the demo
setLayout(new BorderLayout());
add(comboTypesList, BorderLayout.NORTH);
add(jlbPicture, BorderLayout.SOUTH);
setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
}

public static void main(String s[]) {
	JFrame frame = new JFrame("JComboBox Usage Demo");

	frame.addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	}	
);

	frame.setContentPane(new JComboBoxExample());
	frame.pack();
	frame.setVisible(true);
	}
}


