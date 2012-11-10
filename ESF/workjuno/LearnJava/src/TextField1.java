import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class TextField1 extends JApplet implements ActionListener {
  JTextField Input;
  JTextField Echo;
  Container Panel;
  LayoutManager Layout;
  public TextField1 () {
    Input = new JTextField ("Input", 20);
    Echo = new JTextField ("Echo", 20);
    Layout = new FlowLayout ();
    Panel = getContentPane ();
    Echo.setEditable (false);
    Input.setBackground (Color.green);
    Input.addActionListener (this);
    Panel.setLayout (Layout);
    Panel.add (Input);
    Panel.add (Echo);
    Panel.setBackground (Color.yellow);
  }
  public void actionPerformed (ActionEvent e) {
    Echo.setText (Input.getText());
  }
}
