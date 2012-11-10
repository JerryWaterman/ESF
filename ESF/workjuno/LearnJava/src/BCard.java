import javax.swing.*;
import java.awt.event.*;

public class BCard extends JFrame
{
	private JPanel panel;
	//private <strong class="highlight">JPanel</strong> panel;
    //private <strong class="highlight">JPanel</strong> panel2;
    //private <strong class="highlight">JPanel</strong> panel3;
    //private <strong class="highlight">JPanel</strong> panel4;
   // private <strong class="highlight">JPanel</strong> panel5;
    //private <strong class="highlight">JPanel</strong> panel6;
    //private <strong class="highlight">JPanel</strong> panel7;
    private JLabel msgLbl;
    private JLabel lbl;
    private JLabel lbl2;
    private JRadioButton oneB;
    private JRadioButton bothB;
    private JRadioButton glossB;
    private JRadioButton matteB;
    private JRadioButton twoDB;
    private JRadioButton sevenB;
    private JRadioButton wksB;
    private JRadioButton reqB;
    private JRadioButton dontB;
    private JTextField bCTotal;
    private String[] bC = {"100", "250", "500", "1000", "2000"};
    private JComboBox bCQty;
    private ButtonGroup radioButtonGroupSd;
    private ButtonGroup radioButtonGroupQual;
    private ButtonGroup radioButtonGroupDel;
    private ButtonGroup radioButtonGroupDes;
    private final int WINDOW_WIDTH = 300;
    private final int WINDOW_HEIGHT = 200;
    boolean value = true;

    public BCard()
    {
        setTitle("Business Cards");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buildBCPanel();
        add(panel);
        //add(panel2);
        //add(panel3);
        //add(panel4);
        //add(panel5);
        //add(panel6);
        //add(panel7);
        setVisible(value);
    }

     private void buildBCPanel()
    {
        msgLbl = new JLabel("Select the criteria that fits your needs:");
        lbl = new JLabel("Quantity:");
        bCQty = new JComboBox(bC);
        oneB = new JRadioButton("One side");
        bothB = new JRadioButton("Both sides");
        glossB = new JRadioButton("Gloss");
        matteB = new JRadioButton("Matte");
        twoDB = new JRadioButton("Next Day");
        sevenB = new JRadioButton("Seven Days");
        wksB = new JRadioButton("Up Two Weeks");
        reqB = new JRadioButton("Request Design");
        dontB = new JRadioButton("Don't Require");
        lbl2 = new JLabel("Your total may  $");
        bCTotal = new JTextField(10);
        bCTotal.setEditable(false);

        radioButtonGroupSd = new ButtonGroup();
        radioButtonGroupSd.add(oneB);
        radioButtonGroupSd.add(bothB);
        radioButtonGroupQual = new ButtonGroup();
        radioButtonGroupQual.add(glossB);
        radioButtonGroupQual.add(matteB);
        radioButtonGroupDel = new ButtonGroup();
        radioButtonGroupDel.add(twoDB);
        radioButtonGroupDel.add(sevenB);
        radioButtonGroupDel.add(wksB);
        radioButtonGroupDes = new ButtonGroup();
        radioButtonGroupDes.add(reqB);
        radioButtonGroupDes.add(dontB);

        bCQty.addActionListener(new ComboListener());
        //oneB.addActionListener(new RadioButtonListener());
       // bothB.addActionListener(new RadioButtonListener());
        //glossB.addActionListener(new RadioButtonListener());
        //matteB.addActionListener(new RadioButtonListener());
        //twoDB.addActionListener(new RadioButtonListener2());
        //sevenB.addActionListener(new RadioButtonListener2());
        //wksB.addActionListener(new RadioButtonListener2());
        //reqB.addActionListener(new RadioButtonListener());
        //dontB.addActionListener(new RadioButtonListener());

        panel = new JPanel();
        panel.add(msgLbl);
        //panel2 = new <strong class="highlight">JPanel</strong>();
        panel.add(lbl);
        panel.add(bCQty);
        //panel3 = new <strong class="highlight">JPanel</strong>();
        panel.add(oneB);
        panel.add(bothB);
        //panel4 = new <strong class="highlight">JPanel</strong>();
        panel.add(glossB);
        panel.add(matteB);
       // panel5 = new <strong class="highlight">JPanel</strong>();
        panel.add(twoDB);
        panel.add(sevenB);
        panel.add(wksB);
       // panel6 = new <strong class="highlight">JPanel</strong>();
        panel.add(reqB);
        panel.add(dontB);
        //panel7 = new <strong class="highlight">JPanel</strong>();
        panel.add(lbl2);
        panel.add(bCTotal);
    }

     private class ComboListener implements ActionListener
     {
        public void actionPerformed (ActionEvent e)
        {
            double oneHun = 8.95;
            double twoFif = 11.95;
            double fiveHun = 12.95;
            double oneThou = 13.95;
            double twoThou = 27.95;
            String oneHunS = Double.toString(oneHun);
            String twoFifS = Double.toString(twoFif);
            String fiveHunS = Double.toString(fiveHun);
            String oneThouS = Double.toString(oneThou);
            String twoThouS = Double.toString(twoThou);

            if("100".equals(e.getActionCommand()))
            {
              //JTextField bCTotal = (JTextField)e.getSource();
            bCTotal.setText(oneHunS);
              }
            else if("250".equals(e.getActionCommand()))
            {
            //JTextField bCTotal = (JTextField)e.getSource();
            bCTotal.setText(twoFifS);
              }
            else if("500".equals(e.getActionCommand()))
            {
            //JTextField bCTotal = (JTextField)e.getSource();
            bCTotal.setText(fiveHunS);
              }
            else if("1000".equals(e.getActionCommand()))
            {
            //JTextField bCTotal = (JTextField)e.getSource();
            bCTotal.setText(oneThouS);
              }
            else
            {
                //JTextField bCTotal = (JTextField)e.getSource();
            bCTotal.setText(twoThouS);
            }

        }
            
      
     }

       

    public static void main(String[] args)
     {
        BCard bCard = new BCard();


     }
}