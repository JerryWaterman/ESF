
import javax.swing.JInternalFrame;

/* Used by InternalFrameDemo.java. */
public class ESFInternalFrame extends JInternalFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static int openFrameCount = 0;
    static final int xOffset = 200, yOffset = 50;

    public ESFInternalFrame() {
    	  		
        super("Login attempt " + (++openFrameCount), 
              true, //resizable
              true, //closable
              true, //maximizable
              true);//iconifiable

        if (openFrameCount == 4){
        	System.exit(0);
    	}
        //...Create the GUI and put it in the window...

        //...Then set the window size or call pack...
        setSize(300,300);

        //Set the window's location.
        setLocation(xOffset*openFrameCount, yOffset*openFrameCount);
    }
}
