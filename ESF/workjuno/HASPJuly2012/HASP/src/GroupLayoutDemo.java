import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;

import javax.swing.*;

/**
 * A demo class showing layout implementation of the Address Book form from
 * John O'Connor's "Layout Manager Showdown" blog:
 * http://weblogs.java.net/blog/joconner/archive/2006/10/more_informatio.html
  */
public class GroupLayoutDemo extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GroupLayoutDemo() {
        //JScrollPane scrollpane = new JScrollPane();
        /*JList namesList = new JList(new String[] {
            "Bugs Bunny", "Sylvester Cat", "Wile  Coyote", "Tasmanian Devil",
            "Daffy Duck", "Elmer Fudd", "Pepe Le Pew", "Marvin Martian"
        });*/
        JLabel HASPLabel = new JLabel();
        JTextField HASPField = new JTextField();
        JLabel firstNameLabel = new JLabel();
        JCheckBox CheckBox1 = new JCheckBox();
        JCheckBox CheckBox2 = new JCheckBox();
        JCheckBox CheckBox3 = new JCheckBox();
        JCheckBox CheckBox4 = new JCheckBox();
        JCheckBox CheckBox5 = new JCheckBox();
        JTextField firstNameField = new JTextField();
        JTextField PI1 = new JTextField();
        JLabel titleLabel = new JLabel();
        JTextField titleField = new JTextField();
        JLabel emailLabel = new JLabel();
        JTextField emailField = new JTextField();
        JLabel preparerLabel = new JLabel();
        JLabel PInvestigator1 = new JLabel();
        JLabel PInvestigator2 = new JLabel();
        JLabel PInvestigator3 = new JLabel();
        JLabel PInvestigator4 = new JLabel();
        JLabel PInvestigator5 = new JLabel();
        JLabel PInvestigator6 = new JLabel();
        JLabel PInvestigator7 = new JLabel();
        JLabel PInvestigator8 = new JLabel();
        JLabel branchChief = new JLabel();
        JTextField address1Field = new JTextField();
        JLabel address2Label = new JLabel();
        JTextField adress2Field = new JTextField();
        JLabel cityLabel = new JLabel();
        JTextField cityField = new JTextField();
        JLabel stateLabel = new JLabel();
        JTextField stateField = new JTextField();
        JLabel countryLabel = new JLabel();
        JTextField countryField = new JTextField();
        JLabel postalLabel = new JLabel();
        JTextField postalField = new JTextField();
        JButton newButton = new JButton();
        JButton nextButton = new JButton();
        JButton cancelButton = new JButton();
        JButton deleteButton = new JButton();
        JButton editButton = new JButton();
        JButton saveButton = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //scrollpane.setViewportView(namesList);

        HASPLabel.setText("HASP#");
        HASPField.setText("");
        firstNameLabel.setText("First Name:");
        firstNameField.setText("                   ");
        CheckBox1.setText("Inactive ");
        CheckBox2.setText("Field Activities ");
        CheckBox3.setText("Treatability Study");
        CheckBox4.setText("Lab Activities");
        CheckBox5.setText("Hazardous Waste Generator");
        HASPField.setText(" ");
        titleLabel.setText("Title ");
        titleField.setText("                                            " );
        emailLabel.setText("Email:");
        emailField.setText("marvin@wb.com");
        preparerLabel.setText(" HASP Preparer");
        PInvestigator1.setText("  Principle Investigator #1"); 
        PInvestigator2.setText("  Principle Investigator #2"); 
        PInvestigator3.setText("  Principle Investigator #3"); 
        PInvestigator4.setText("  Principle Investigator #4");
        PInvestigator5.setText("  Principle Investigator #5");
        PInvestigator6.setText("  Principle Investigator #6");
        PInvestigator7.setText("  Principle Investigator #7");
        PInvestigator8.setText("  Principle Investigator #8");
        PI1.setText("                           ");
        branchChief.setText("Branch Chief "); 
        address1Field.setText("1001001010101 Martian Way");
        adress2Field.setText("Suite 10111011");
        address2Label.setText("Address 2:");
        cityLabel.setText("City:");
        cityField.setText(" Ventura");
        stateLabel.setText("State:");
        stateField.setText("CA");
        countryLabel.setText("Country:");
        postalLabel.setText("Postal Code:");
        postalField.setText("93001");
        newButton.setText("New");
        cancelButton.setText("Cancel");
        nextButton.setText("Next Record");
        deleteButton.setText("Delete");
        editButton.setText("Edit");
        saveButton.setText("Save");

        GroupLayout layout = new GroupLayout(getContentPane());
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        getContentPane().setLayout(layout);

layout.setHorizontalGroup(layout.createSequentialGroup()
    //.addComponent(scrollpane, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
    .addGroup(layout.createParallelGroup()
        .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                .addComponent(HASPLabel)
                .addComponent(titleLabel)
                .addComponent(preparerLabel)
                .addComponent(PInvestigator1)
                .addComponent(PInvestigator2)
                .addComponent(PInvestigator3)
                .addComponent(PInvestigator4)
                .addComponent(branchChief)
                .addComponent(address2Label)
                .addComponent(cityLabel)
                .addComponent(stateLabel)
                .addComponent(countryLabel)
            )
            .addGroup(layout.createParallelGroup()
            	.addComponent(titleField)
            	.addComponent(address1Field)
                .addComponent(adress2Field)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup()
                        .addComponent(HASPField)
                        .addComponent(PI1)
                        .addComponent(cityField)
                        .addComponent(stateField)
                        .addComponent(countryField)
                    )
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)//leading
                    		.addGroup(layout.createParallelGroup(LEADING) 
                    				//.addComponent(CheckBox1)//
                                    .addComponent(PInvestigator5)
                                    .addComponent(PInvestigator6)
                                    .addComponent(PInvestigator7)
                                    .addComponent(PInvestigator8)
                    		)	
                    	.addGroup(layout.createSequentialGroup()
                    		.addComponent(CheckBox1)	
                            .addComponent(CheckBox2)
                            .addComponent(CheckBox3)
                            .addComponent(CheckBox4)
                            .addComponent(CheckBox5)
                         )
                         /*.addGroup(layout.createParallelGroup(LEADING) 
                    				.addComponent(CheckBox2)//
                                    .addComponent(PInvestigator5)
                                    .addComponent(PInvestigator6)
                    		)*/	
                         
                         .addGroup(layout.createSequentialGroup()
                            .addComponent(postalLabel)
                            .addComponent(postalField)
                            //.addComponent(PInvestigator5)
                         )
                    )
                )
            )
        )
        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addComponent(newButton)
            .addComponent(nextButton)
            .addComponent(deleteButton)
            .addComponent(editButton)
            .addComponent(saveButton)
            .addComponent(cancelButton)
         )
    )
  );

        layout.linkSize(SwingConstants.HORIZONTAL, cancelButton, deleteButton, editButton, newButton, saveButton, nextButton);
//      westenfall 512 high st. 513 248 0999             06vhdb000
        layout.setVerticalGroup(layout.createParallelGroup()
            //.addComponent(scrollpane)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(HASPLabel)
                    .addComponent(HASPField)
                    .addComponent(CheckBox1)
                    .addComponent(CheckBox2)
                    .addComponent(CheckBox3)
                    .addComponent(CheckBox4)
                    .addComponent(CheckBox5)
                )
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(titleLabel)
                    .addComponent(titleField)
                )
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(preparerLabel)
                    .addComponent(address1Field))
                    .addGroup(layout.createParallelGroup(BASELINE)
                              .addComponent(PInvestigator1)
                              .addComponent(PI1)
                              .addComponent(PInvestigator5)
                    )           
                    .addGroup(layout.createParallelGroup(BASELINE)
                    	      .addComponent(PInvestigator2)
                    	      .addComponent(PInvestigator6)
                    )
                    .addGroup(layout.createParallelGroup(BASELINE)
                    		.addComponent(PInvestigator3)
                    		.addComponent(PInvestigator7)
                    )
                    .addGroup(layout.createParallelGroup(BASELINE)
                            .addComponent(PInvestigator4)
                            .addComponent(PInvestigator8)
                    )
                    .addComponent(branchChief)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                	.addComponent(address2Label)
                    .addComponent(adress2Field)
                )
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(cityLabel)
                    .addComponent(cityField)
                )
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(stateLabel)
                    .addComponent(stateField)
                    .addComponent(postalLabel)
                    .addComponent(postalField)
                )
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(countryLabel)
                    .addComponent(countryField))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(newButton)
                    .addComponent(nextButton)
                    .addComponent(cancelButton)
                    .addComponent(deleteButton)
                    .addComponent(editButton)
                    .addComponent(saveButton)
                )
            )
         );

        pack();
    }

    /*public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GroupLayoutDemo().setVisible(true);
            }
        });
    }*/
}

