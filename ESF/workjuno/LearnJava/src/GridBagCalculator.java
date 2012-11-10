// Program 16.5: GridbagLayout Example
import java.applet.Applet;
import java.awt.*;

public class GridBagCalculator extends Applet {

  public void init() {
    GridBagCalculator gcf = new GridBagCalculator();
    gcf.resize(100, 300);
    gcf.show();
  }

}


class GridbagCalculatorFrame extends Frame {

  public static void main(String args[]) {
    
    GridBagCalculator a = new GridBagCalculator();
    a.init();
    a.start();
  
    Frame appletFrame = new Frame("Applet Window");
    appletFrame.add("Center", a);
    appletFrame.resize(150,200);
    appletFrame.move(120,100);
    appletFrame.show();
 
  }

  public void init () {
 
    GridBagLayout gbl = new GridBagLayout();
    setLayout(gbl);
    
    // Add the display to the top four cells
    GridBagConstraints GBC_display = new GridBagConstraints();
    GBC_display.gridx = 0;
    GBC_display.gridy = 0;
    GBC_display.gridwidth = 4;
    GBC_display.gridheight = 1;
    GBC_display.fill = GridBagConstraints.HORIZONTAL;
    
    // add the text field
    TextField display = new TextField(12);
    gbl.setConstraints(display, GBC_display);
    add(display);

    // Add the clear button
    GridBagConstraints GBC_clear = new GridBagConstraints();
    GBC_clear.gridx = 0;
    GBC_clear.gridy = 1;
    GBC_clear.gridwidth = 1;
    GBC_clear.gridheight = 1;
    GBC_clear.fill = GridBagConstraints.BOTH;
    GBC_clear.insets = new Insets(3, 3, 3, 3);
    
    // add the button
    Button clear = new Button("C");
    gbl.setConstraints(clear, GBC_clear);
    add(clear);

    
    // Add the equals button    
    GridBagConstraints GBC_equals = new GridBagConstraints();
    GBC_equals.gridx = 1;
    GBC_equals.gridy = 1;
    GBC_equals.gridwidth = 1;
    GBC_equals.gridheight = 1;
    GBC_equals.fill = GridBagConstraints.BOTH;
    GBC_equals.insets = new Insets(3, 3, 3, 3);

    // add the = button
    Button equals = new Button("=");
    gbl.setConstraints(equals, GBC_equals);
    add(equals);

    // Add the / button    
    GridBagConstraints GBC_slash = new GridBagConstraints();
    GBC_slash.gridx = 2;
    GBC_slash.gridy = 1;
    GBC_slash.gridwidth = 1;
    GBC_slash.gridheight = 1;
    GBC_slash.fill = GridBagConstraints.BOTH;
    GBC_slash.insets = new Insets(3, 3, 3, 3);

    // add the button
    Button slash = new Button("/");
    gbl.setConstraints(slash, GBC_slash);
    add(slash);

    
    // Add the * button    
    GridBagConstraints GBC_times = new GridBagConstraints();
    GBC_times.gridx = 3;
    GBC_times.gridy = 1;
    GBC_times.gridwidth = 1;
    GBC_times.gridheight = 1;
    GBC_times.fill = GridBagConstraints.BOTH;
    GBC_times.insets = new Insets(3, 3, 3, 3);
    
    // add the button
    Button star = new Button("*");
    gbl.setConstraints(star, GBC_times);
    add(star);

    // Add the 7 key
    GridBagConstraints GBC_7 = new GridBagConstraints();
    GBC_7.gridx = 0;
    GBC_7.gridy = 2;
    GBC_7.gridwidth = 1;
    GBC_7.gridheight = 1;
    GBC_7.fill = GridBagConstraints.BOTH;
    GBC_7.insets = new Insets(3, 3, 3, 3);
    
    // add the button
    Button b7 = new Button("7");
    gbl.setConstraints(b7, GBC_7);
    add(b7);

    
    // Add the 8 key    
    GridBagConstraints GBC_8 = new GridBagConstraints();
    GBC_8.gridx = 1;
    GBC_8.gridy = 2;
    GBC_8.gridwidth = 1;
    GBC_8.gridheight = 1;
    GBC_8.fill = GridBagConstraints.BOTH;
    GBC_8.insets = new Insets(3, 3, 3, 3);

    // add the button
    Button b8 = new Button("8");
    gbl.setConstraints(b8, GBC_8);
    add(b8);

    // Add the 9 key    
    GridBagConstraints GBC_9 = new GridBagConstraints();
    GBC_9.gridx = 2;
    GBC_9.gridy = 2;
    GBC_9.gridwidth = 1;
    GBC_9.gridheight = 1;
    GBC_9.fill = GridBagConstraints.BOTH;
    GBC_9.insets = new Insets(3, 3, 3, 3);

    // add the button
    Button b9 = new Button("9");
    gbl.setConstraints(b9, GBC_9);
    add(b9);

    
    // Add the - key    
    GridBagConstraints GBC_minus = new GridBagConstraints();
    GBC_minus.gridx = 3;
    GBC_minus.gridy = 2;
    GBC_minus.gridwidth = 1;
    GBC_minus.gridheight = 1;
    GBC_minus.fill = GridBagConstraints.BOTH;
    GBC_minus.insets = new Insets(3, 3, 3, 3);
    
    // add the button
    Button minus = new Button("-");
    gbl.setConstraints(minus, GBC_minus);
    add(minus);
    
    // Add the 4 key
    GridBagConstraints GBC_4 = new GridBagConstraints();
    GBC_4.gridx = 0;
    GBC_4.gridy = 3;
    GBC_4.gridwidth = 1;
    GBC_4.gridheight = 1;
    GBC_4.fill = GridBagConstraints.BOTH;
    GBC_4.insets = new Insets(3, 3, 3, 3);
    
    // add the button
    Button b4 = new Button("4");
    gbl.setConstraints(b4, GBC_4);
    add(b4);

    
    // Add the 5 key    
    GridBagConstraints GBC_5 = new GridBagConstraints();
    GBC_5.gridx = 1;
    GBC_5.gridy = 3;
    GBC_5.gridwidth = 1;
    GBC_5.gridheight = 1;
    GBC_5.fill = GridBagConstraints.BOTH;
    GBC_5.insets = new Insets(3, 3, 3, 3);

    // add the button
    Button b5 = new Button("5");
    gbl.setConstraints(b5, GBC_5);
    add(b5);

    // Add the 6 key    
    GridBagConstraints GBC_6 = new GridBagConstraints();
    GBC_6.gridx = 2;
    GBC_6.gridy = 3;
    GBC_6.gridwidth = 1;
    GBC_6.gridheight = 1;
    GBC_6.fill = GridBagConstraints.BOTH;
    GBC_6.insets = new Insets(3, 3, 3, 3);

    // add the button
    Button b6 = new Button("6");
    gbl.setConstraints(b6, GBC_6);
    add(b6);

    
    // Add the + key    
    GridBagConstraints GBC_plus = new GridBagConstraints();
    GBC_plus.gridx = 3;
    GBC_plus.gridy = 3;
    GBC_plus.gridwidth = 1;
    GBC_plus.gridheight = 1;
    GBC_plus.fill = GridBagConstraints.BOTH;
    GBC_plus.insets = new Insets(3, 3, 3, 3);
    
    // add the button
    Button plus = new Button("+");
    gbl.setConstraints(plus, GBC_plus);
    add(plus);    

    // Add the 1 key
    GridBagConstraints GBC_1 = new GridBagConstraints();
    GBC_1.gridx = 0;
    GBC_1.gridy = 4;
    GBC_1.gridwidth = 1;
    GBC_1.gridheight = 1;
    GBC_1.fill = GridBagConstraints.BOTH;
    GBC_1.insets = new Insets(3, 3, 3, 3);
    
    // add the button
    Button b1 = new Button("1");
    gbl.setConstraints(b1, GBC_1);
    add(b1);

    
    // Add the 2 key    
    GridBagConstraints GBC_2 = new GridBagConstraints();
    GBC_2.gridx = 1;
    GBC_2.gridy = 4;
    GBC_2.gridwidth = 1;
    GBC_2.gridheight = 1;
    GBC_2.fill = GridBagConstraints.BOTH;
    GBC_2.insets = new Insets(3, 3, 3, 3);

    // add the button
    Button b2 = new Button("2");
    gbl.setConstraints(b2, GBC_2);
    add(b2);

    // Add the 3 key    
    GridBagConstraints GBC_3 = new GridBagConstraints();
    GBC_3.gridx = 2;
    GBC_3.gridy = 4;
    GBC_3.gridwidth = 1;
    GBC_3.gridheight = 1;
    GBC_3.fill = GridBagConstraints.BOTH;
    GBC_3.insets = new Insets(3, 3, 3, 3);

    // add the button
    Button b3 = new Button("3");
    gbl.setConstraints(b3, GBC_3);
    add(b3);

    
    // Add the = key    
    GridBagConstraints GBC_bigequals = 
      new GridBagConstraints();
    GBC_bigequals.gridx = 3;
    GBC_bigequals.gridy = 4;
    GBC_bigequals.gridwidth = 1;
    GBC_bigequals.gridheight = 2;
    GBC_bigequals.fill = GridBagConstraints.BOTH;
    GBC_bigequals.insets = new Insets(3, 3, 3, 3);
    
    // add the button
    Button bigequals = new Button("=");
    gbl.setConstraints(bigequals, GBC_bigequals);
    add(bigequals);
    
    // Add the 0 key
    GridBagConstraints GBC_0 = new GridBagConstraints();
    GBC_0.gridx = 0;
    GBC_0.gridy = 5;
    GBC_0.gridwidth = 2;
    GBC_0.gridheight = 1;
    GBC_0.fill = GridBagConstraints.BOTH;
    GBC_0.insets = new Insets(3, 3, 3, 3);
    
    // add the button
    Button b0 = new Button("0");
    gbl.setConstraints(b0, GBC_0);
    add(b0);

    
    // Add the . key    
    GridBagConstraints GBC_decimal = new GridBagConstraints();
    GBC_decimal.gridx = 2;
    GBC_decimal.gridy = 5;
    GBC_decimal.gridwidth = 1;
    GBC_decimal.gridheight = 1;
    GBC_decimal.fill = GridBagConstraints.BOTH;
    GBC_decimal.insets = new Insets(3, 3, 3, 3);

    // add the button
    Button bdecimal = new Button(".");
    gbl.setConstraints(bdecimal, GBC_decimal);
    add(bdecimal);

  }

  public Insets insets() {
  
    return new Insets(5, 5, 5, 5);
  
  }

}

