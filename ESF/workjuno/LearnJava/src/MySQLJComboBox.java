import java.sql.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class  MySQLJComboBox {
	public static void main(String[] args)     {
		JFrame f=new JFrame();
		f.setLayout(null);
		JLabel lab=new JLabel("Course Items:");
		final JComboBox combo=new JComboBox();
		combo.addItem("Select");
		try{   
			Class.forName("com.mysql.jdbc.Driver"); 
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "hoTTub11");
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("select site from epa_site");
			while(rs.next()){   
				combo.addItem(rs.getString("site"));
				}
			}catch(Exception e){}
		JButton b=new JButton("Get");
		lab.setBounds(20,20,100,20);
		combo.setBounds(120,20,150,20);
		b.setBounds(120,50,80,20);
		b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String value = combo.getSelectedItem().toString();
				JOptionPane.showMessageDialog(null,"You have selected '"+value+"' from ComboBox"); 
				}
			});
		f.add(lab);   
		f.add(combo); 
		f.add(b);
		f.setVisible(true);
		f.setSize(300,120);
}
	}