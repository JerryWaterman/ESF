/*
 * Created on Mar 30, 2005
 */
package GraFix.Figure;

import java.util.ArrayList;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.Polyline;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;

import GraFix.Util.GraFixConstants;
import GraFix.Util.Utilities;

/**
 * @author dindiver
 * A Figure that can render itself as the y-Axis for a given set of series data.
 */
public class YRulerBar extends Polyline {
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
    int maxElements = 0;
    int numSegments = 0; 
    
    int xOffset = GraFixConstants.xOffset;
    //Point start = new Point(xOffset,ySpan);
    //Point end   = new Point(xOffset,0);
    String labelData[] = null;
    ArrayList _seriesData = null;
    
    public YRulerBar(IFigure contents, ArrayList seriesData){
        _seriesData = new ArrayList(seriesData);
        this.setLineWidth(1);
        this.setLineStyle(SWT.LINE_SOLID);
        this.setForegroundColor(GraFixConstants.yAxisColor);
        
        int longestSeries = Utilities.getLongestSeries(seriesData);
        maxElements = ((double[])seriesData.get(longestSeries)).length;
        int sectionWidth = (int)xSpan / maxElements; //we want to evenly divide span of xAxis
        Point start = new Point(sectionWidth-xOffset,ySpan);
        Point end   = new Point(sectionWidth-xOffset,0);

        int yScale[] = getScale(seriesData);
        
		PointList points = new PointList();
		points.addPoint(start);
		for(int i=0;i<numSegments;i++){
		    //points.addPoint(xOffset,(int)yScale[i]);
		    points.addPoint(sectionWidth-xOffset,(int)yScale[i]);
		}
		points.addPoint(end);
		this.setPoints(points);
		
		Label labels[] = new Label[numSegments];
		for(int i=0; i<numSegments; i++){
		    labels[i] = new Label(new Integer(yScale[i]).toString());
		    labels[i].setForegroundColor(ColorConstants.titleGradient);
		    contents.add(labels[i]);
		    int xPos = xOffset;
		    int yPos = (int)ySpan - yScale[i];
		    int width = 40;//labels[i].getSize().width;
		    int height = 10;//labels[i].getSize().height;		  
		    contents.setConstraint(labels[i], new Rectangle(xPos, yPos, width, height));
		}
		int breakpoint=0;		
    }
    
    
	private int[] getScale(ArrayList seriesData){
		int numSeries = seriesData.size();
		ArrayList scaledValuesArrayList = new ArrayList(numSeries);
		
		Double maxValueOfEachSeries[] = new Double[numSeries];
		Double minValueOfEachSeries[] = new Double[numSeries];
		for(int i=0; i<numSeries; i++){
			double data[] = (double[])seriesData.get(i);
			Double dataObjects[] = new Double[data.length];
			for(int j=0;j<data.length;j++)
				dataObjects[j] = new Double(data[j]); 
			scaledValuesArrayList.add(new ArrayList(data.length));
			maxValueOfEachSeries[i] = new Double(Utilities.getMaximum(dataObjects));
			minValueOfEachSeries[i] = new Double(Utilities.getMinimum(dataObjects));
		}
		double maxValue = Utilities.getMaximum(maxValueOfEachSeries);
		double minValue = Utilities.getMinimum(minValueOfEachSeries);
		
		double segmentIncrement = 10;	
		if(0!=minValue)
		    segmentIncrement = maxValue / minValue;
		else
		    segmentIncrement = maxValue / 5;
		
		numSegments = (int)(maxValue / segmentIncrement);
		
		int retVal[] = new int[numSegments];
		
		for(int i=0; i<numSegments; i++){
		    retVal[i] = (int)((i+1)*segmentIncrement);
		}
		
		return retVal;
	}
}
