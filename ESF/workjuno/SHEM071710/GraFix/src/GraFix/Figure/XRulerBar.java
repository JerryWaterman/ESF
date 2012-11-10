/*
 * Created on Mar 26, 2005
 */
package GraFix.Figure;

import java.util.ArrayList;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.Polyline;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;

import GraFix.Util.GraFixConstants;
import GraFix.Util.Utilities;

/**
 * @author dindiver
 * A Figure that can render itself as the x-Axis for a given set of series data.
 */
public class XRulerBar extends Polyline {
    /**
     * _______________________________
     * |0,0                           |
     * |                              |
     * |                              |
     * |                              |
     * |                              |
     * |                              |
     * |                              |
     * |0,ySpan           xSpan,ySpan |
     * --------------------------------
     * **/
    double xSpan = GraFixConstants.xSpan;
    double ySpan = GraFixConstants.ySpan;
    double xOffset = GraFixConstants.xOffset;
    int numSegments = 0; 
    
    //Point start = new Point(0,ySpan);
    //Point end   = new Point(xSpan,ySpan);
    String labelData[] = null;
    
    public XRulerBar(IFigure contents, ArrayList seriesData){
        this.setLineWidth(1);
        this.setLineStyle(SWT.LINE_SOLID);
        this.setForegroundColor(GraFixConstants.xAxisColor);
        
        int longestSeries = Utilities.getLongestSeries(seriesData);
        numSegments = ((double[])seriesData.get(longestSeries)).length;        
        int sectionWidth = (int)xSpan / numSegments; //we want to evenly divide span of xAxis
        Point start = new Point(sectionWidth-xOffset,ySpan);
        Point end   = new Point(xSpan+xOffset,ySpan);
        
        int labelPositions[] = new int[numSegments]; // will contain x cordinate of all labels
		for(int i=0; i<numSegments; i++){
			labelPositions[i]= (i+1)*sectionWidth;//labels evenly spaced at a distance of sectionWidth			
		}
		
		PointList points = new PointList();
		points.addPoint(start);
		for(int i=0;i<numSegments;i++){
		    points.addPoint(labelPositions[i],(int)ySpan);
		}
		points.addPoint(end);
		this.setPoints(points);
		
		Label labels[] = new Label[numSegments];
		for(int i=0; i<numSegments; i++){
		    labels[i] = new Label(new Integer(i+1).toString());
		    labels[i].setForegroundColor(ColorConstants.titleGradient);
		    contents.add(labels[i]);
		    int xPos = labelPositions[i];
		    int yPos = (int)ySpan;
		    int width = 10;//labels[i].getSize().width;
		    int height = 10;//labels[i].getSize().height;		  
		    contents.setConstraint(labels[i], new Rectangle(xPos, yPos, width, height));
		}
		int breakpoint=0;		
    }

}
