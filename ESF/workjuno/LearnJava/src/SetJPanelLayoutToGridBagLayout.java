import javax.swing.*;
import java.awt.*;
public class SetJPanelLayoutToGridBagLayout extends JPanel {
/**Note**
 *When it say, "It will take 4 grid for it's width"
 *it mean it will use 4 box in grid and it's direction is to right
 *When it say, "It will take 4 grid for it's height"
 *it mean it will use 4 box in grid and it's direction is to bottom
 */
//Create GridBigLayout object
GridBagLayout gbl=new GridBagLayout();
//Create GridBagConstraints object
GridBagConstraints gbc=new GridBagConstraints();
//Create a label using JLabel with text ( Name : )
JLabel label=new JLabel("Name :");
//Create a label using JLabel with text ( Age : )
JLabel label2=new JLabel("Age :");
//Create a text field using JTextField with numbers of column equal to 12
JTextField textField=new JTextField(12);
//Create a text field using JTextField with numbers of column equal to 3
JTextField textField2=new JTextField(3);
JButton button=new JButton("OK");
JButton button2=new JButton("Cancel");
Box box1=new Box(BoxLayout.Y_AXIS);
Box box2=new Box(BoxLayout.X_AXIS);
Box box3=new Box(BoxLayout.X_AXIS);
Box box4=new Box(BoxLayout.Y_AXIS);
JFrame frame=new JFrame("Set JPanel layout using GridBagLayout");
//Constructor
public SetJPanelLayoutToGridBagLayout() {
 //Make text in label allign to right
 label.setHorizontalAlignment(SwingConstants.RIGHT);
 label2.setHorizontalAlignment(SwingConstants.RIGHT);
 //Set panel layout to GridBagLayout
 setLayout(gbl);
 //Field that will be use when display area is larger than component size.
 //It mean when it has some space between component, component will
 //enlarge to horizontal direction
 gbc.fill=GridBagConstraints.HORIZONTAL;
 /*Add label into panel with top left corner of the label at crossing between
  *first horizontal and first vertical line.
  *It will take 4 grid for it's width
  *It will take 1 grid for it's height  */
 addComponent1(label,1,1,4,1);
 /*Add label2 into panel with top left corner of the
  *label at crossing between
  *third horizontal and first vertical line.
  *It will take 4 grid for it's width
  *It will take 1 grid for it's height  */
 addComponent1(label2,3,1,4,1);
 /*Add textField into panel with top left corner of the text field at
  *crossing between first horizontal and sixth vertical line
  *It will take 12 grid for it's width
  *It will take 1 grid for it's height  */
 addComponent1(textField,1,6,12,1);
 /*Add textField2 into panel with top left corner of the text field at
  *crossing between third horizontal and sixth vertical line
  *It will take 3 grid for it's width
  *It will take 1 grid for it's height  */
 addComponent1(textField2,3,6,3,1);
 /*Add button into panel with top left corner of the button at
  *crossing between fifth horizontal and fifth vertical line
  *It will take 5 grid for it's width
  *It will take 2 grid for it's height  */
 addComponent1(button,5,5,5,2);
 /*Add button2 into panel with top left corner of the button at
  *crossing between fifth horizontal and eleventh vertical line
  *It will take 5 grid for it's width
  *It will take 2 grid for it's height */
 addComponent1(button2,5,11,5,2);
 /*Add a component that is invisible with 10 pixels width
  *and 10 pixels height into box1  */
 box1.add(Box.createRigidArea(new Dimension(10,10)));
 /*Add box1 into panel with top left corner of the box at
  *crossing between first horizontal and fifth vertical line
  *It will take 1 grid for it's width
  *It will take 3 grid for it's height */
 addComponent1(box1,1,5,1,3);
 /*Add a component that is invisible with 30 pixels width
  *and 10 pixels height into box2 */
 box2.add(Box.createRigidArea(new Dimension(30,10)));
 /*Add box2 into panel with top left corner of the box at
  *crossing between second horizontal and first vertical line
  *It will take 17 grid for it's width
  *It will take 1 grid for it's height */
 addComponent1(box2,2,1,17,1);
 /*Add a component that is invisible with 30 pixels width
  *and 10 pixels height into box3  */
 box3.add(Box.createRigidArea(new Dimension(30,10)));
 /*Add box3 into panel with top left corner of the box at
  *crossing between fourth horizontal and first vertical line
  *It will take 17 grid for it's width
  *It will take 1 grid for it's height  */
 addComponent1(box3,4,1,17,1);
 /*Add a component that is invisible with 10 pixels width
  *and 10 pixels height into box4 */
 box4.add(Box.createRigidArea(new Dimension(10,10)));
 /*Add box4 into panel with top left corner of the box at
  *crossing between fifth horizontal and tenth vertical line
  *It will take 1 grid for it's width
  *It will take 2 grid for it's height  */
 addComponent1(box4,5,10,1,2);
 //Add panel into JFrame
 frame.add(this);
 //Set default close operation for JFrame
 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 //SetJFrame size
 frame.setSize(500,400);
 //Make JFrame center on the screen
 frame.setLocationRelativeTo(null);
 //Disble JFrame from resizable
 frame.setResizable(true);//false);
 frame.setVisible(true);
}
//Method addComponent
public void addComponent1(Component component, int row, int column, int width, int height){
 gbc.gridx=column;
 gbc.gridy=row;
 gbc.gridwidth=width;
 gbc.gridheight=height;
 gbl.setConstraints(component,gbc);
 add(component);
}
//Main method
public static void main(String[]args) {
 SetJPanelLayoutToGridBagLayout sjpltgbl=new SetJPanelLayoutToGridBagLayout();
} }
