import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class MainClassLayout {
  public static void main(String args[]) {
    JFrame f = new JFrame("JPasswordField Sample");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Box rowOne = Box.createHorizontalBox();
    rowOne.add(new JLabel("Username"));
    rowOne.add(new JTextField());
    Box rowTwo = Box.createHorizontalBox();
    rowTwo.add(new JLabel("Password"));
    rowTwo.add(new JPasswordField());
    f.add(rowOne, BorderLayout.CENTER);
    f.add(rowTwo, BorderLayout.SOUTH);
    f.setSize(300, 200);
    f.setVisible(true);
  }
}

