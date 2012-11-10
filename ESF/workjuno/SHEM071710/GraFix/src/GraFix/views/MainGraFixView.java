/*
 * Created on Mar 21, 2005
 */
package GraFix.views;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import GraFix.Plotters.DirectedGraphXYPlotter;


/**
 * @author dindiver
 * 
 */
public class MainGraFixView extends ViewPart{
		
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createPartControl(Composite parent) {
	    ArrayList seriesData = dataGenerator();//get data from any source in an ArrayList
	    DirectedGraphXYPlotter dgXYGraph = new DirectedGraphXYPlotter(parent); // instantiate the plotter, pass it an SWT Composite
		dgXYGraph.setData(seriesData); // provide data as an ArrayList		
		dgXYGraph.plot();//ask it to plot. 
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	public void setFocus() {
		
	}
	
	private ArrayList dataGenerator() {
	    ArrayList seriesData = new ArrayList();
        double series1[] = new double[5]; 
		for(int i=0; i<series1.length; i++)
			series1[i] = (i*10) + 10;// a linear series containing 10,20,30,40,50
		
		double series2[] = new double[9]; 
		series2[0] = 20; series2[1] = 150; series2[2] = 5;
		series2[3] = 90; series2[4] = 35;  series2[5] = 20; 
		series2[6] = 150;series2[7] = 5;   series2[8] = 45;
		
		double series3[] = new double[7]; 
		for(int i=0; i<series3.length; i++)
			series3[i] = (i*20) + 15;

		seriesData.add(series1);
		seriesData.add(series2);
		seriesData.add(series3);
		return seriesData;
	}

}//end of class MainGraFixView