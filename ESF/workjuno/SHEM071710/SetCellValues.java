import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class SetCellValues{
  JTable table;
  public static void main(String[] args) {
    new SetCellValues();
  }

  public SetCellValues(){
    JFrame frame = new JFrame("Setting Cell Values in JTable");
    JPanel panel = new JPanel();
    String data[][] = {{"Vinod","MCA","Computer"},
                       {"Deepak","PGDCA","History"},
                       {"Ranjan","M.SC.","Biology"},
                       {"Radha","BCA","Computer"}};
    String col[] = {"Name","Course","Subject"};  
    DefaultTableModel model = new DefaultTableModel(data, col);
    table = new JTable(model);
    SetData("Math",2,2);
    SetData("Santosh",3,0);
    JTableHeader header = table.getTableHeader();
    header.setBackground(Color.yellow);
    JScrollPane pane = new JScrollPane(table);
    panel.add(pane);
    frame.add(panel);
    frame.setSize(500,150);
    frame.setUndecorated(true);
    frame.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  public void SetData(Object obj, int row_index, int col_index){
    table.getModel().setValueAt(obj,row_index,col_index);
    System.out.println("Value is added");
  }
}