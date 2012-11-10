/*
 * Created on Mar 26, 2005
 */
package GraFix.Util;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.swt.graphics.Color;

/**
 * @author dindiver
 */
public class GraFixConstants {
    
    public static double xSpan = 800;
    public static double ySpan = 300;
    public static double zoomFactor = 10;
    public static int    xOffset = 20;
    
    private static int autoColor = 0;
    public static Color xAxisColor = ColorConstants.white;
    public static Color yAxisColor = ColorConstants.white;
    
    public static Color getAutoColour(){
        if(autoColor==4)
            autoColor=0;
        else
            autoColor++;
        
        switch(autoColor){
	        case 1: return ColorConstants.red;
	        case 2: return ColorConstants.cyan;
	        case 3: return ColorConstants.green;
	        case 4: return ColorConstants.blue;
	        default: return ColorConstants.lightGray;
        }//end of switch
    }
    
    public static void setXSpan(double span) {
        xSpan = span;
    }
    public static void setYSpan(double span) {
        ySpan = span;
    }
}
