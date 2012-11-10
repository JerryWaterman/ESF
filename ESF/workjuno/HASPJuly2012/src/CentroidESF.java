// I will use this java program to find the Centroid from the data that is in the array
// data 
import java.awt.*; 
import java.awt.font.*;
import java.awt.geom.*;
import java.sql.DriverManager;
import javax.swing.*;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class CentroidESF extends JPanel {
	/***/
	private static final long serialVersionUID = 1L;
    private Connection db;
    private Statement statement;
	private PreparedStatement pstmt;
    public String currentURL;
    //open MySQL
	//read the data from MySQL table 
	//int[] data = {21, 14, 18, 03, 86, 88, 74, 87, 54, 77, 61, 55, 48, 60, 49, 36, 38, 27, 20, 18};
    //  x = 0, 0.5, 1 
    double[] Xdata = {0.0,0.5,1.0}; 
    int[] Ydata =  {0,1,0}; //   Y            0,45,50,45,40,35};
    
	//int[][] data = {{1,2}, {1,4}, {1,8}, {0,3}, {8,6}, {8,8}, {7,4}, {8,7}, {5,4}};
	final int PAD = 20;
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int w = getWidth();
		int h = getHeight();
		// Draw ordinate.
		g2.draw(new Line2D.Double(PAD, PAD, PAD, h-PAD)); 
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
		// Abcissa label. need to calculate X axis
		s = "0.2                     0.4                              0.6                 0.8                    1.0 ";
		sy = h - PAD + (PAD - sh)/2 + lm.getAscent();
		float sw = (float)font.getStringBounds(s, frc).getWidth();
		float sx = (w - sw)/2;  
		g2.drawString(s, sx, sy);
		// Draw lines.
		double xInc = (double)(w - 2*PAD)/(Ydata.length-1);
		System.out.println(" w = "+w+" PAD = "+PAD+"   length of data = "+Ydata.length+" xInc = "+xInc);
		double scale = (double)(h - 2*PAD)/getMax();
		g2.setPaint(Color.green.darker());
		for(int i = 0; i < Ydata.length-1; i++) {
			double x1 = PAD + i*xInc;
			double y1 = h - PAD - scale*Ydata[i];
			double x2 = PAD + (i+1)*xInc;
			double y2 = h - PAD - scale*Ydata[i+1];
			g2.draw(new Line2D.Double(x1, y1, x2, y2));   
			System.out.println("x1 = "+x1+"   y1 = "+y1+"  x2 = "+x2+"   y2 = "+y2);
		}
		// Mark data points.
		g2.setPaint(Color.red);
		for(int i = 0; i < Ydata.length; i++) {
			double x = PAD + i*xInc;
			double y = h - PAD - scale*Ydata[i];
			g2.fill(new Ellipse2D.Double(x-2, y-2, 4, 4));
			
			
		}
	}
	private int getMax() {
		int max = -Integer.MAX_VALUE;
		for(int i = 0; i < Ydata.length; i++) {
			if(Ydata[i] > max) 
				max = Ydata[i];
				//System.out.println(" max = "+max);
			}
		return max;
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
			}
		} catch (Exception e)
			System.out.println("Could not close the current connection.");
			e.printStackTrace();
		}
	}
	*/
	public static void main(String[] args) { 
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(new CentroidESF());
		f.setSize(400,400); 
		f.setLocation(10,10);
		f.setTitle("ESF, Centriod, November 7, 2011");
		f.setVisible(true);  
	}
}
