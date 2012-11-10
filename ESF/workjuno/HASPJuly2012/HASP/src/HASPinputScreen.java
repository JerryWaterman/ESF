import java.sql.*;
import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
/*
 * set up an index
 * get the first row of data
 *  if the user select another HASP num from the JComoBox, 
 *  then need to find that row and display it.
 *  
 *   create two navitatoin buttons.
 * 
 *  public Object getValueAt(int row, int col) {
 *	   return ((String[]) cache.elementAt(row))[col];
 *	}
 *
 *
 *
 * Converts a visible column index to a column index in the model.
// Returns -1 if the index does not exist.
public int toModel(JTable table, int vColIndex) {
    if (vColIndex >= table.getColumnCount()) {
        return -1;
    }
    return table.getColumnModel().getColumn(vColIndex).getModelIndex();
}

// Converts a column index in the model to a visible column index.
// Returns -1 if the index does not exist.
public int toView(JTable table, int mColIndex) {
    for (int c=0; c<table.getColumnCount(); c++) {
        TableColumn col = table.getColumnModel().getColumn(c);
        if (col.getModelIndex() == mColIndex) {
            return c;
        }
    }
    return -1;
}

 * 
*/


public class HASPinputScreen {  
	public HASPinputScreen() {
		JFrame f = new JFrame();
		String firstHASP = "";
		f.getContentPane().setLayout(null);
		JLabel jLabel1 = new javax.swing.JLabel();
		JLabel jLabel2 = new javax.swing.JLabel();
		final JComboBox combo = new JComboBox();
		//String titleField = null;
		final JComboBox comboLN1 = new JComboBox();
		JTextArea tField = new JTextArea();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection connect = DriverManager.getConnection("jdbc:oracle:thin:@comanche.wic.epa.gov:1521:cintst","CSHEM","welcome1");
			Statement st = connect.createStatement();
			ResultSet rsNum = st.executeQuery("Select * from shasptest");
			// load the JComboBox (s)
			while (rsNum.next()) {
					combo.addItem(rsNum.getString("HASPnum"));
					
					if (rsNum.isFirst()){
						//firstHASP = combo.addItem(rsNum.getString("HASPNum"));				
                        
						System.out.println("is First "+firstHASP+"   "+rsNum.getString("HASPnum"));					
					};
					
				    //tField = rsNum.getString("Title");
					comboLN1.addItem(rsNum.getString("invest1LastName"));
					//System.out.println(rsNum.getString("HASPnum"));
					//System.out.println(rsNum.getString("Title"));
					combo.setVisible(true);  
					comboLN1.setVisible(true); 
			}         
		} catch (Exception ex) { 
			System.out.println("Could not initialize the database.");
			ex.printStackTrace();
		}  
		jLabel1.setBounds(5, 50, 100, 20);
		jLabel1.setText("HASP#");
		jLabel2.setBounds(5, 80, 50, 20);
		jLabel2.setText("Title "+tField.getText());
				
		combo.setBounds(50, 50, 150, 20);
		comboLN1.setBounds(50, 120, 150, 20);
		f.add(jLabel1);
		f.add(jLabel2);
		//f.add(titleField);
		f.add(combo); 
		f.add(comboLN1);
		f.setSize(800, 600);//400,200
		f.setVisible(true);      
		combo.setVisible(true);//false
	}
}

/*class ComboExample {
	ComboExample() {
	JFrame f = new JFrame();
	f.getContentPane().setLayout(null);
	final JComboBox combo = new JComboBox();
	final JTextField text = new JTextField();
	text.addKeyListener(new KeyAdapter() {
	    public void keyReleased(KeyEvent e) {
			//String ch = text.getText();
			combo.removeAllItems();
		//	if (ch.equals("")) {
				combo.setVisible(true); //false);
			//} else {
					//System.out.println("ch = "+ch);
					try {
						Class.forName("oracle.jdbc.driver.OracleDriver");
						Connection connect = DriverManager.getConnection("jdbc:oracle:thin:@comanche.wic.epa.gov:1521:cintst","CSHEM","welcome1");
						Statement st = connect.createStatement();
						ResultSet rs = st.executeQuery("Select HASPnum from shasptest");
						while (rs.next()) {
							String name = rs.getString("HASPnum");
							//if (name.equals("")) {
								//combo.addItem("");
								//combo.setVisible(false);
								//} else {
									combo.addItem(rs.getString("HASPnum"));
									System.out.println(rs.getString("HASPnum"));
									combo.setVisible(true);  
									//}                        
							}                      
					} catch (Exception ex) { 
						System.out.println("Could not initialize the database.");
						ex.printStackTrace();
					}   
			//	};
	 };
	//	public void keyTyped(KeyEvent e) {}   
	//	public void keyPressed(KeyEvent e) {}   
}
	);
	text.setBounds(20, 20, 150, 20); 
	combo.setBounds(20, 50, 150, 20);
			f.add(text);
			f.add(combo); 
			f.setSize(800, 600);//400,200
			f.setVisible(true);      
			combo.setVisible(true);//false
	}        
}*/