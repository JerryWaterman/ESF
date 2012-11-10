import java.awt.Color;
import java.awt.Component;
 
import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
 
public class TableTest extends JPanel {
 
    public TableTest() {
        init();
    }
    
    private void init() {
        // test data
        Object[] columns = new Object[] {"Select", "Name", "Rule"};
        Object[][] data = new Object[2][3];
        data[0][0] = new Boolean(false);
        data[0][1] = "John";
        data[0][2] = new Boolean(false);
        data[1][0] = new Boolean(false);
        data[1][1] = "Bill";
        data[1][2] = new Boolean(false);
        
        MyTableModel model = new MyTableModel(data, columns);
        JTable table = new JTable(model);
        
        table.getColumnModel().getColumn(0).setCellEditor(new CheckBoxCellEditor());
        
        table.getColumnModel().getColumn(2).setCellEditor(new CheckBoxCellEditor());
        
        table.getColumnModel().getColumn(0).setCellRenderer(new CWCheckBoxRenderer());
        
        table.getColumnModel().getColumn(2).setCellRenderer(new CWCheckBoxRenderer());
        
        JScrollPane tableScroller = new JScrollPane(table);
        
        add(tableScroller);
    }
    
    private class MyTableModel extends AbstractTableModel {
        
        private Object[][] data;
        private Object[] columns;
        
        public MyTableModel(Object[][] data, Object[] columns) {
            this.data = data;
            this.columns = columns;
        }
        
        public Class getColumnClass(int columnIndex) {
            return data[0][columnIndex].getClass();
        }
        public int getColumnCount() {
            return columns.length;
        }
        public int getRowCount() {
            return data.length;
        }
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }
        
        // only allow the first and third column to be editable, these are the columns
        // with our checkboxes
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return (columnIndex == 0 || columnIndex == 2);
        }
    }
    
    private class CheckBoxCellEditor extends AbstractCellEditor implements TableCellEditor {
        protected JCheckBox checkBox;
        
        public CheckBoxCellEditor() {
            checkBox = new JCheckBox();
            checkBox.setHorizontalAlignment(SwingConstants.CENTER);
            checkBox.setBackground( Color.white);
        }
        
        public Component getTableCellEditorComponent(
                JTable table, 
                Object value, 
                boolean isSelected, 
                int row, 
                int column) {
 
            checkBox.setSelected(((Boolean) value).booleanValue());
            
//            Component c = table.getDefaultRenderer(String.class).getTableCellRendererComponent(table, value, isSelected, false, row, column);
//            if (c != null) {
//                checkBox.setBackground(c.getBackground());
//            }
            
            return checkBox;
        }
        public Object getCellEditorValue() {
            return Boolean.valueOf(checkBox.isSelected());
        }
    }
    
    private class CWCheckBoxRenderer extends JCheckBox implements TableCellRenderer {
Border border = new EmptyBorder(1,2,1,2);

public CWCheckBoxRenderer() {
super();
setOpaque(true);
setHorizontalAlignment(SwingConstants.CENTER);
}
 
public Component getTableCellRendererComponent(
JTable table,
Object value,
boolean isSelected,
boolean hasFocus,
int row,
int column) {

if (value instanceof Boolean) {
setSelected(((Boolean)value).booleanValue()); 
setEnabled(table.isCellEditable(row, column));
 
if (isSelected) {
setBackground(table.getSelectionBackground());
setForeground(table.getSelectionForeground());
} else {
setForeground(table.getForeground());
setBackground(table.getBackground());
}
}
else {
//setSelected(false);
//setEnabled(false);
return null;
}

return this; 
}
 
}
 
    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        TableTest test = new TableTest();
        
        f.getContentPane().add(test);
        f.setSize(350, 350);
        f.show();
    }
}
 