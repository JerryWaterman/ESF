import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class TableSelectionDemo2 extends JPanel {
  JTextArea output;
  JList list;
  JTable table;
  String newline = "\n";
  ListSelectionModel listSelectionModel;

  public TableSelectionDemo2() {
    super(new BorderLayout());

    String[] columnNames = { "French", "Spanish", "Italian" };
    String[][] tableData = { { "un", "uno", "uno" }, { "deux", "dos", "due" },
        { "trois", "tres", "tre" }, { "quatre", "cuatro", "quattro" },
        { "cinq", "cinco", "cinque" }, { "six", "seis", "sei" },
        { "sept", "siete", "sette" } };

    table = new JTable(tableData, columnNames);
    listSelectionModel = table.getSelectionModel();
    listSelectionModel
        .addListSelectionListener(new SharedListSelectionHandler());
    table.setSelectionModel(listSelectionModel);
    JScrollPane tablePane = new JScrollPane(table);

    // Build control area (use default FlowLayout).
    JPanel controlPane = new JPanel();
    String[] modes = { "SINGLE_SELECTION", "SINGLE_INTERVAL_SELECTION",
        "MULTIPLE_INTERVAL_SELECTION" };

    final JComboBox comboBox = new JComboBox(modes);
    comboBox.setSelectedIndex(2);
    comboBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String newMode = (String) comboBox.getSelectedItem();
        if (newMode.equals("SINGLE_SELECTION")) {
          listSelectionModel
              .setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        } else if (newMode.equals("SINGLE_INTERVAL_SELECTION")) {
          listSelectionModel
              .setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        } else {
          listSelectionModel
              .setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        }
        output.append("----------" + "Mode: " + newMode + "----------"
            + newline);
      }
    });
    controlPane.add(new JLabel("Selection mode:"));
    controlPane.add(comboBox);

    // Build output area.
    output = new JTextArea(1, 10);
    output.setEditable(false);
    JScrollPane outputPane = new JScrollPane(output,
        ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    // Do the layout.
    JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    add(splitPane, BorderLayout.CENTER);

    JPanel topHalf = new JPanel();
    topHalf.setLayout(new BoxLayout(topHalf, BoxLayout.LINE_AXIS));
    JPanel listContainer = new JPanel(new GridLayout(1, 1));
    JPanel tableContainer = new JPanel(new GridLayout(1, 1));
    tableContainer.setBorder(BorderFactory.createTitledBorder("Table"));
    tableContainer.add(tablePane);
    tablePane.setPreferredSize(new Dimension(420, 130));
    topHalf.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
    topHalf.add(listContainer);
    topHalf.add(tableContainer);

    topHalf.setMinimumSize(new Dimension(250, 50));
    topHalf.setPreferredSize(new Dimension(200, 110));
    splitPane.add(topHalf);

    JPanel bottomHalf = new JPanel(new BorderLayout());
    bottomHalf.add(controlPane, BorderLayout.PAGE_START);
    bottomHalf.add(outputPane, BorderLayout.CENTER);
    // XXX: next line needed if bottomHalf is a scroll pane:
    // bottomHalf.setMinimumSize(new Dimension(400, 50));
    bottomHalf.setPreferredSize(new Dimension(450, 110));
    splitPane.add(bottomHalf);
  }

  /**
   * Create the GUI and show it. For thread safety, this method should be
   * invoked from the event-dispatching thread.
   */
  private static void createAndShowGUI() {
    // Create and set up the window.
    JFrame frame = new JFrame("Table Selection Demo");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Create and set up the content pane.
    TableSelectionDemo2 demo = new TableSelectionDemo2();
    demo.setOpaque(true);
    frame.setContentPane(demo);

    // Display the window.
    frame.pack();
    frame.setVisible(true);
  }

  public static void main(String[] args) {
    // Schedule a job for the event-dispatching thread:
    // creating and showing this application's GUI.
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        createAndShowGUI();
      }
    });
  }

  class SharedListSelectionHandler implements ListSelectionListener {
    public void valueChanged(ListSelectionEvent e) {
      ListSelectionModel lsm = (ListSelectionModel) e.getSource();

      int firstIndex = e.getFirstIndex();
      int lastIndex = e.getLastIndex();
      boolean isAdjusting = e.getValueIsAdjusting();
      output.append("Event for indexes " + firstIndex + " - " + lastIndex
          + "; isAdjusting is " + isAdjusting + "; selected indexes:");

      if (lsm.isSelectionEmpty()) {
        output.append(" <none>");
      } else {
        // Find out which indexes are selected.
        int minIndex = lsm.getMinSelectionIndex();
        int maxIndex = lsm.getMaxSelectionIndex();
        for (int i = minIndex; i <= maxIndex; i++) {
          if (lsm.isSelectedIndex(i)) {
            output.append(" " + i);
          }
        }
      }
      output.append(newline);
      output.setCaretPosition(output.getDocument().getLength());
    }
  }
}