import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class FormUtilityDemo {

    public static void main(String[] args) {
        JFrame f = new JFrame("Form Utility Demo");

        // Make a panel to hold the demo "form", then
        // add it to the top of the frame's content pane
        JPanel form = new JPanel();
        f.getContentPane().setLayout(new BorderLayout());
        f.getContentPane().add(form, BorderLayout.NORTH);

        // Set the form panel's layout to GridBagLayout
        // and create a FormUtility to add things to it.
        form.setLayout(new GridBagLayout());
        FormUtility formUtility = new FormUtility();

        // Add some sample fields
        formUtility.addLabel("Name: ", form);
        formUtility.addLastField(new JTextField(), form);

        formUtility.addLabel("Address: ", form);
        formUtility.addLastField(new JTextField(), form);

        // Using a blank label to indent an (unlabelled)
        // field. Without the blank label, the field would
        // take the entire width of the form
        formUtility.addLabel("", form);
        formUtility.addLastField(new JTextField(), form);

        // A more complex "field", with multiple
        // components. We want the city field to stretch
        // with the form, while the state and zip input
        // fields stay the same width. Hence we add
        // the city input field as a "middle" field.
        formUtility.addLabel("City: ", form);
        formUtility.addMiddleField(new JTextField(), form);

        // Put the state and zip labels and fields
        // in their own panel, each added as a "label"
        // in a FormUtility-managed GridBagLayout. This
        // has the effect of giving each piece only as
        // much as is needed. When placed in the "last"
        // field position in the main form, these will
        // have a fixed width and the city field can
        // stretch.
        JPanel stateZip = new JPanel();
        stateZip.setLayout(new GridBagLayout());
        formUtility.addLabel(" State: ", stateZip);
        JTextField state = new JTextField();
        Dimension stateSize = state.getPreferredSize();
        stateSize.width = 30;
        state.setPreferredSize(stateSize);
        formUtility.addLabel(state, stateZip);
        formUtility.addLabel(" Zip: ", stateZip);
        JTextField zip = new JTextField();
        Dimension zipSize = zip.getPreferredSize();
        zipSize.width = 80;
        zip.setPreferredSize(zipSize);
        formUtility.addLabel(zip, stateZip);

        // The panel containing the state and the zip
        // gets added as another (fixed width) label field.
        // This is less than ideal, but will suffice for
        // this example. A blank "last field" component
        // gets us to a new line.
        formUtility.addLabel(stateZip, form);
        formUtility.addLastField(new JPanel(), form);

        // And finally, an input field that shouldn't stretch to the end
        formUtility.addLabel("Phone: ", form);
        JTextField phone = new JTextField();
        Dimension phoneSize = phone.getPreferredSize();
        phoneSize.width = 200;
        phone.setPreferredSize(phoneSize);
        JPanel phonePanel = new JPanel();
        phonePanel.setLayout(new BorderLayout());
        phonePanel.add(phone, BorderLayout.WEST);
        formUtility.addLastField(phonePanel, form);

        // Add an little padding around the form
        form.setBorder(new EmptyBorder(2, 2, 2, 2));

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Note that we don't use pack() here, since that
        // may shrink the "last" column more than we want.
        f.setSize(400, 400);
        f.setVisible(true);
    }

}
