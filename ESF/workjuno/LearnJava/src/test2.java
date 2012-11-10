import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class test2 extends JFrame {
    public test2() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = c.gridy = 0;  c.insets = new Insets(4,4,4,4);
        c.fill = GridBagConstraints.BOTH;

        JPanel p1 = new JPanel();
        p1.setLayout(new GridBagLayout());
        p1.setBorder(BorderFactory.createTitledBorder("Blue Panel"));
        p1.setPreferredSize(new Dimension(320,240));
        p1.setBackground(Color.BLUE);
        add(p1,c);

        ++c.gridx;
        JPanel p2 = new JPanel();
        p2.setLayout(new GridBagLayout());
        p2.setPreferredSize(new Dimension(320,240));
        p2.setBackground(Color.RED);
        add(p2,c);

        String[] ord = {"One","Two","Three","Four","Five","Six"};
        JLabel[] l = new JLabel[ord.length];
        for (int i=0; i<ord.length; i++) {
            l[i] = new JLabel(ord[i]);
            l[i].setOpaque(true);
        }

        c.gridx = c.gridy = 0;
        p1.add(l[0],c);
        ++c.gridy;
        p1.add(l[1],c);

        c.weightx = 0.2;  c.weighty = 0.9;
        c.gridx = c.gridy = 0;
        p2.add(l[2],c);
        ++c.gridx;  c.weightx = 0.8;
        p2.add(l[3],c);

        c.gridx = 0;  ++c.gridy;  c.gridwidth = 2;  c.weighty = 0.1;
        p2.add(l[4],c);

        setSize(800,600);
        setVisible(true);
    }

    public static void main(String[] args) {
        new test2();
    }
}

