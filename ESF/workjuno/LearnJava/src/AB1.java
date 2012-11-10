import java.awt.*;
import javax.swing.*;

public class AB1 extends JFrame
{
 GridBagLayout gbl;

 public AB1 ()
 {
  super ("Address Book");

  setDefaultCloseOperation (EXIT_ON_CLOSE);

  getContentPane ().add (buildGUI ());

  pack ();

  dumpLayoutInfo ();

  setVisible (true);
 }

 public JPanel buildGUI ()
 {
  JPanel panel = new JPanel (gbl = new GridBagLayout ());
  panel.setBorder (BorderFactory.createEmptyBorder (5, 5, 10, 10));
  GridBagConstraints gbc = new GridBagConstraints ();

  // Set shared constraints for the name, address, city, region, country,
  // and phone area code/prefix code/number label, text, and combo box
  // fields.

  // The insets constraint provides a five-pixel insets separator between
  // each label and its text or combo box field, and between a label plus
  // its text/combo box field and the previous label plus its text/combo
  // box field.

  gbc.insets = new Insets (5, 5, 0, 0);

  // The anchor constraint left-aligns all labels in their leftmost
  // column. Furthermore, this constraint ensures that the phone’s number
  // field is positioned at the start of its display area. If this wasn’t
  // done, the number field would be centered within its lengthy display
  // area, putting it far to the right of the area code and prefix code
  // fields.

  gbc.anchor = GridBagConstraints.LINE_START;

  // For each of the name, address, city, region, country, and phone area
  // code/prefix code/number, install appropriate label, text, and combo
  // box fields.

  JLabel lblName = new JLabel ("Name:");
  //lblName.setDisplayedMnemonic (’N’);
  JTextField txtName = new JTextField (25);
  lblName.setLabelFor (txtName);

  gbc.gridy = 0;
  panel.add (lblName, gbc);
  gbc.gridwidth = GridBagConstraints.REMAINDER;
  gbc.fill = GridBagConstraints.HORIZONTAL;
  gbc.weightx = 1.0;
  panel.add (txtName, gbc);
  gbc.gridwidth = 1;
  gbc.fill = GridBagConstraints.NONE;
  gbc.weightx = 0.0;

  JLabel lblAddress = new JLabel ("Address:");
  //lblAddress.setDisplayedMnemonic (’A’);
  JTextField txtAddress = new JTextField (25);
  lblAddress.setLabelFor (txtAddress);

  gbc.gridy = 1;
  panel.add (lblAddress, gbc);
  gbc.gridwidth = GridBagConstraints.REMAINDER;
  gbc.fill = GridBagConstraints.HORIZONTAL;
  panel.add (txtAddress, gbc);
  gbc.gridwidth = 1;
  gbc.fill = GridBagConstraints.NONE;

  JLabel lblCity = new JLabel ("City:");
  //lblCity.setDisplayedMnemonic (’C’);
  JTextField txtCity = new JTextField (25);
  lblCity.setLabelFor (txtCity);

  gbc.gridy = 2;
  panel.add (lblCity, gbc);
  gbc.gridwidth = GridBagConstraints.REMAINDER;
  gbc.fill = GridBagConstraints.HORIZONTAL;
  panel.add (txtCity, gbc);
  gbc.gridwidth = 1;
  gbc.fill = GridBagConstraints.NONE;

  JLabel lblRegion = new JLabel ("Region:");
  //lblRegion.setDisplayedMnemonic (’R’);
  JTextField txtRegion = new JTextField (25);
  lblRegion.setLabelFor (txtRegion);

  gbc.gridy = 3;
  panel.add (lblRegion, gbc);
  gbc.gridwidth = GridBagConstraints.REMAINDER;
  gbc.fill = GridBagConstraints.HORIZONTAL;
  panel.add (txtRegion, gbc);
  gbc.gridwidth = 1;
  gbc.fill = GridBagConstraints.NONE;

  JLabel lblCountry = new JLabel ("Country:");
  //lblCountry.setDisplayedMnemonic (’o’);
  String [] countries = { "Brazil", "Ireland", "Sweden" };
  JComboBox cbCountry = new JComboBox (countries);
  lblCountry.setLabelFor (cbCountry);

  gbc.gridy = 4;
  panel.add (lblCountry, gbc);
  gbc.gridwidth = GridBagConstraints.REMAINDER;
  gbc.fill = GridBagConstraints.HORIZONTAL;
  panel.add (cbCountry, gbc);
  gbc.gridwidth = 1;
  gbc.fill = GridBagConstraints.NONE;

  JLabel lblPhone = new JLabel ("Phone:");
  //lblPhone.setDisplayedMnemonic (’P’);
  JTextField txtAreaCode = new JTextField (3);
  JTextField txtPrefixCode = new JTextField (3);
  JTextField txtNumber = new JTextField (4);
  lblPhone.setLabelFor (txtAreaCode);

  gbc.gridy = 5;
  panel.add (lblPhone, gbc);
  panel.add (txtAreaCode, gbc);
  panel.add (txtPrefixCode, gbc);
  panel.add (txtNumber, gbc);

  // Install the OK and Cancel buttons panel -- it’s easier to treat the
  // two buttons as a single component.

  JButton btnOK = new JButton ("OK");
  Dimension size = btnOK.getPreferredSize ();
  size.width = 100;
  btnOK.setPreferredSize (size);

  JButton btnCancel = new JButton ("Cancel");
  size = btnCancel.getPreferredSize ();
  size.width = 100;
  btnCancel.setPreferredSize (size);

  JPanel panel2 = new JPanel (new GridBagLayout ());
  GridBagConstraints gbc2 = new GridBagConstraints ();
  gbc2.insets = new Insets (0, 5, 0, 0);
  panel2.add (btnOK, gbc2);
  panel2.add (btnCancel, gbc2);

  gbc.gridy = 6;
  gbc.gridwidth = GridBagConstraints.REMAINDER;
  gbc.anchor = GridBagConstraints.LINE_END;
  panel.add (panel2, gbc);

  // Install a weighted glue row to take up all vertical extra space, so
  // that this program’s GUI appears near the top of its main window when
  // the window is enlarged vertically.

  panel.add (Box.createGlue (),
     new GridBagConstraints (0, 7, 1, 1, 0.0, 1.0,
      GridBagConstraints.EAST, GridBagConstraints.NONE,
      new Insets (0, 0, 0, 0), 0, 0));

  return panel;
 }

 public void dumpLayoutInfo ()
 {
  System.out.println ("Layout Dimensions");
  System.out.println ();

  int [][] x = gbl.getLayoutDimensions ();
  if (x == null || x.length < 2)
  {
   System.err.println ("Unable to obtain layout dimensions");
   return;
  }

  System.out.println (" Column Widths");
  System.out.println ();

  System.out.print (" ");
  for (int i = 0; i < x [0].length; i++)
   System.out.print (x [0][i] + " ");

  System.out.println ();
  System.out.println ();

  System.out.println (" Row Heights");
  System.out.println ();

  System.out.print (" ");
  for (int i = 0; i < x [1].length; i++)
   System.out.print (x [1][i] + " ");

  System.out.println ();
  System.out.println ();

  System.out.println ("Layout Weights");
  System.out.println ();

  double [][] w = gbl.getLayoutWeights ();
  if (w == null || w.length < 2)
  {
   System.err.println ("Unable to obtain layout weights");
   return;
  }

  System.out.println (" Horizontal Weights");
  System.out.println ();

  System.out.print (" ");
  for (int i = 0; i < w [0].length; i++)
   System.out.print (w [0][i] + " ");

  System.out.println ();
  System.out.println ();

  System.out.println (" Vertical Weights");
  System.out.println ();

  System.out.print (" ");
  for (int i = 0; i < w [1].length; i++)
   System.out.print (w [1][i] + " ");
 }

 public static void main (String [] args)
 {
  new AB1 ();
 }
}