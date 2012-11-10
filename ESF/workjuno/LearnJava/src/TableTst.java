import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Arrays;
 
public class TableTst {
    private EditableTableModel model;
    private JTable table;
    private JCheckBox editYear;
 
    public TableTst() {
 
        String[] columnNames = {"First Name",
                "Last Name",
                "Sport",
                "# of Years",
                "Vegetarian"};
 
        Object[][] data = {
                {"Mary", "Campione",
                        "Snowboarding", new Integer(5), new Boolean(false)},
                {"Alison", "Huml",
                        "Rowing", new Integer(3), new Boolean(true)},
                {"Kathy", "Walrath",
                        "Knitting", new Integer(2), new Boolean(false)},
                {"Sharon", "Zakhour",
                        "Speed reading", new Integer(20), new Boolean(true)},
                {"Philip", "Milne",
                        "Pool", new Integer(10), new Boolean(false)}
        };
 
        model = new EditableTableModel(data, columnNames);
 

        table = new JTable(model);
 
        JPanel checkBoxPanel = new JPanel();
        editYear = new JCheckBox("edit # of years", false);
        editYear.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e) {
                int column = 3;
                model.setColumnEditable(column, editYear.isSelected());
            }
        });
        checkBoxPanel.add(editYear);
 
        JFrame frame = new JFrame();
        frame.getContentPane().add(checkBoxPanel, BorderLayout.NORTH);
        frame.getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
 
    public static void main(String[] args) {
        new TableTst();
    }
 
    private class EditableTableModel extends DefaultTableModel {
 
        boolean[] columnEditable;
 
        public EditableTableModel(Object[][] data, String[] columnNames) {
            super(data, columnNames);
            columnEditable = new boolean[columnNames.length];
            Arrays.fill(columnEditable, false);
        }
 
        public boolean isCellEditable(int row, int column) {
            if (!columnEditable[column]) {
                return false;
            }
            else {
                return super.isCellEditable(row, column);
            }
        }
 
        public void setColumnEditable(int column, boolean editable) {
            columnEditable[column] = editable;
        }
 
        public boolean getColumnEditable(int column) {
            return columnEditable[column];
        }
    }
}