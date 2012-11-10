// I will use this java program to find the Centroid from the data that is in the array
// data
import java.awt.*; 
import java.awt.font.*;
import java.awt.geom.*;
import java.sql.DriverManager;
import java.util.Date;

import javax.swing.*;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class CentriodData extends JPanel {
	/***/
	private static final long serialVersionUID = 1L;
    private Connection db;
    private Statement statement;
	private PreparedStatement pstmt;
    public String currentURL;
    //open MySQL
	//read the data from MySQL table 
	//int[] data = {21, 14, 18, 03, 86, 88, 74, 87, 54, 77, 61, 55, 48, 60, 49, 36, 38, 27, 20, 18};
	int[] data =  {0,1,0}; //   Y            0,45,50,45,40,35};
    //int[][] data = {{1,2}, {1,4}, {1,8}, {0,3}, {8,6}, {8,8}, {7,4}, {8,7}, {5,4}};
	final int PAD = 20;
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int w = getWidth();
		int h = getHeight();
		//Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        //System.out.println(" dim = "+dim);
		// Draw ordinate.
		g2.draw(new Line2D.Double(PAD, PAD, PAD, h-PAD)); 
		//g2.draw(new Line2D.Double(getLocationOnScreen(), getLocation()));
		// Draw abcissa.         
		g2.draw(new Line2D.Double(PAD, h-PAD, w-PAD, h-PAD));
		// Draw labels.         
		Font font = g2.getFont();
		FontRenderContext frc = g2.getFontRenderContext(); 
		LineMetrics lm = font.getLineMetrics("0", frc);
		float sh = lm.getAscent() + lm.getDescent();
		//System.out.println(" sh ="+sh);
		// Ordinate label.
		String s = "Y   data";
		float sy = PAD + ((h - 2*PAD) - s.length()*sh)/2 + lm.getAscent();
		for(int i = 0; i < s.length(); i++) {
			String letter = String.valueOf(s.charAt(i));  
			float sw = (float)font.getStringBounds(letter, frc).getWidth();
			float sx = (PAD - sw)/2;
			g2.drawString(letter, sx, sy); 
			sy += sh;   
		}
		// Abcissa label.
		s = "0.2                     0.4                              0.6                 0.8                    1.0 ";
		//s = "X axis";
		sy = h - PAD + (PAD - sh)/2 + lm.getAscent();
		float sw = (float)font.getStringBounds(s, frc).getWidth();
		float sx = (w - sw)/2;  
		g2.drawString(s, sx, sy);
		// Draw lines.
		double xInc = (double)(w - 2*PAD)/(data.length-1);
		System.out.println(" xInc = "+xInc+" w = "+w);
		double scale = (double)(h - 2*PAD)/getMax();
		System.out.println(" scale = "+scale+" h = "+h);
		g2.setPaint(Color.green.darker());
		for(int i = 0; i < data.length-1; i++) {
			double x1 = PAD + i*xInc;
			double y1 = h - PAD - scale*data[i];
			double x2 = PAD + (i+1)*xInc;
			double y2 = h - PAD - scale*data[i+1];
			g2.draw(new Line2D.Double(x1, y1, x2, y2));   
			System.out.println("x1 = "+x1+"   y1 = "+y1+"  x2 = "+x2+"   y2 = "+y2);
		}
		// Mark data points.
		g2.setPaint(Color.red);
		for(int i = 0; i < data.length; i++) {
			double x = PAD + i*xInc;
			double y = h - PAD - scale*data[i];
			g2.fill(new Ellipse2D.Double(x-2, y-2, 4, 4));   //4 is the size of the dot
			//g2.fill(new Ellipse2D.Double(x, y, 4, 4));
		}
		getCentroid();
		// Mark the Centroid point
		

	}
	private int getMax() {
		int max = -Integer.MAX_VALUE;
		for(int i = 0; i < data.length; i++) {
			if(data[i] > max) 
				max = data[i];
				//System.out.println(" max = "+max);
			}
		return max;
	}
	
	private double getCentroid(){
        //x	    y
		//-------
		//2	    3
		//4     5
		//6 	7
		
		/*                                                                i=3 
		 *                                                 i=2            6,7
		 *                                                 4,5             *
		 *                                  i=1             *______________|
		 *                                  2,3             |              | 
		 *                                   *______________|              |
		 *                                   |                             |
		 *                                   |                             |
		 *                                   *_____________________________*
		 *                                  i=0                           i=4
		 *                                  2,0                           6,0                
		 *
		 */
		int dataLen = 0;
	    double iTotal = 0.0;
	    double gTotal = 0.0;
		double centroid = 0.0;
		double[] xdata = new double[] {2,4,6};        //
		double[] ydata = new double[] {3,5,7};
		
	    for(int j=0; j< ydata.length; j++){
	    	//System.out.println(" ydata = "+ydata[j]);
	    }	
	    //Find A = 20
	    // Beginning point  bp    i = 0               (2*3)-(2*0)
	    //                                               ()-()
	    // Ending point     ep    i = xdata + 1 i=4   (6*0)-(2*0)
	    dataLen = xdata.length+2;
	    System.out.println("data Len = "+dataLen);
	    for (int i = 0; i < xdata.length; i++){
			System.out.println(" xdata length = "+ xdata.length);
	    	
	    	if (xdata[i] == 0) {
	    		System.out.println("        xdata[1] = "+ xdata[i]);
	    	} 
	    	
	    	iTotal = (xdata[i]*ydata[i])-(xdata[i]*0);
	    	gTotal = gTotal + iTotal;
	    	System.out.println(" iTotal = "+ iTotal +"  gTotal = "+ gTotal);
		}
	    
	    //Find Cy = 2.6333
	    
	    //Find Cx = 4.2666
	    
		return centroid; //
		
	} 
	/*
	public void setHostURL(String url) {
		if (url.equals(currentURL)) {
			//System.out.println("in setHostURL, url.equals currentURL");
			return;
		}
		closeDB();
		initDB(url); 
		currentURL = "jdbc:mysql://localhost:3306";
		//System.out.println("before currentURL");
    	}
	
	
	private void initDB(String url) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String username = "root";
		    String password = "hoTTub11";
			//System.out.println("BEFORE db"); 
			//db = DriverManager.registerDriver(new com.mysql.jbdc.Driver());
			db = (Connection) DriverManager.getConnection(url, username, password);
			//System.out.println("After db = "+db);  
			statement = (Statement) db.createStatement();
		} catch (Exception e) {
			System.out.println("Could not initialize the database.");
			e.printStackTrace();
		}
	}
	
	public void closeDB() {
		try {
			if (statement != null) {
				statement.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (db != null) {
				db.close();
				System.out.println("Database was successfully closed !");
				Hi Sheryl,
				Thanks for the voice message, I have a busy week at work and need to get ready go of town next Sunday. Let's try to together the week 
				of November 29 for lunch north of Cincinnati.
			}
		} catch (Exception e) {
			System.out.println("Could not close the current connection.");
			e.printStackTrace();
		}
	}
	*/
	public static void main(String[] args) { 
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(new CentriodData());
		f.setSize(400,400); 
		f.setLocation(200,200);
		Date d = new Date();
		f.setTitle("ESF, Centriod-"+d);
		f.setVisible(true);  
	}
}
