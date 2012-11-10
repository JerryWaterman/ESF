/*
 * Created on Mar 22, 2005
 */
package GraFix.Plotters;

import java.util.ArrayList;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.EllipseAnchor;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Panel;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.XYAnchor;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.graph.DirectedGraph;
import org.eclipse.draw2d.graph.DirectedGraphLayout;
import org.eclipse.draw2d.graph.Edge;
import org.eclipse.draw2d.graph.EdgeList;
import org.eclipse.draw2d.graph.Node;
import org.eclipse.draw2d.graph.NodeList;
import org.eclipse.draw2d.internal.graph.BreakCycles;
import org.eclipse.draw2d.internal.graph.InvertEdges;
import org.eclipse.draw2d.internal.graph.LocalOptimizer;
import org.eclipse.draw2d.internal.graph.PlaceEndpoints;
import org.eclipse.draw2d.internal.graph.VerticalPlacement;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import GraFix.Figure.Dot;
import GraFix.Figure.XRulerBar;
import GraFix.Figure.YRulerBar;
import GraFix.Util.GraFixConstants;
import GraFix.Util.Utilities;

/******************************************************
 * @author dindiver 
 * Plots a X-Y Graph for a given set of series.
 * Usage:
 *  ArrayList seriesData = dataGenerator();//get data from any source in an ArrayList
    DirectedGraphXYPlotter dgXYGraph = new DirectedGraphXYPlotter(parent); // instantiate the plotter, pass it an SWT Composite
	dgXYGraph.setData(seriesData); // provide data as an ArrayList		
	dgXYGraph.plot();//ask it to plot. 
 ******************************************************/
public class DirectedGraphXYPlotter {
	
	Composite _parent = null;
	
	int _numSeries = 0;
	ArrayList _seriesData = null;
	ArrayList _seriesScaledValues = null;
	ArrayList _nodeLists = null;
	ArrayList _edgeLists = null;
	
	public DirectedGraphXYPlotter(Composite parent){
		_parent = parent;
	}
	
	public void setData(ArrayList seriesData){
		_numSeries = seriesData.size();//how many series' to plot		
		_seriesData = new ArrayList(seriesData);	
	}
	
	private void populateNodesAndEdges(){
	    
	    _seriesScaledValues = new ArrayList(getScaledValues(_seriesData));
		_nodeLists = new ArrayList();
		_edgeLists = new ArrayList();
		
		for(int i=0; i<_numSeries; i++){
			_nodeLists.add(new NodeList());// one NodeList per series.
			_edgeLists.add(new EdgeList());// one EdgeList per series.
		}
		//populate all NodeLists with the Nodes. 
		for(int i=0; i<_numSeries; i++){//for each series
			double data[] = (double[])_seriesData.get(i);//get the series
			int xCoOrds[] = getXCoordinates(_seriesData);
			int yCoOrds[] = getYCoordinates(i, data);
			for(int j=0; j<data.length; j++){//each NodeList will have as many Nodes required in a series
				Double doubleValue = new Double(data[j]);
				Node node = new Node(doubleValue);
				node.x = xCoOrds[j];
				node.y = yCoOrds[j];				
				((NodeList)_nodeLists.get(i)).add(node);
			}					
		}
		//populate all EdgeLists with the Edges. 
		for(int i=0; i<_numSeries; i++){
			NodeList nodes = (NodeList)_nodeLists.get(i);			
			for(int j=0; j<nodes.size()-1; j++){
				Node leftNode = nodes.getNode(j);
				Node rightNode = nodes.getNode(j+1);
				Edge edge = new Edge(leftNode,rightNode);
				edge.start = new Point(leftNode.x, leftNode.y); 
				edge.end = new Point(rightNode.x, rightNode.y);
				((EdgeList)_edgeLists.get(i)).add(edge);
			}	
		}
		int breakpoint = 0;
	}
	
	private int[] getXCoordinates(ArrayList seriesData){
	    int xSpan = (int)GraFixConstants.xSpan;
	    int longestSeries = Utilities.getLongestSeries(seriesData);
        int numSegments = ((double[])seriesData.get(longestSeries)).length;        
        int sectionWidth = (int)xSpan / numSegments; //we want to evenly divide span of xAxis
        
        int xPositions[] = new int[numSegments]; // will contain x cordinate of all dots.
		for(int i=0; i<numSegments; i++){
			xPositions[i]= (i+1)*sectionWidth;//dots evenly spaced at a distance of sectionWidth			
		}
		return xPositions;
	}
	
	private int[] getYCoordinates(int seriesIndex, double data[]){		
		int ySpan = (int)GraFixConstants.ySpan;//300;//assumed value. this should be height of eclipse viewpart 
		int maxValues = data.length;
		int yPositions[] = new int[maxValues]; // will contain y cordinate
		for(int i=0; i<maxValues; i++){
			Double scaledVal = (Double)((ArrayList)_seriesScaledValues.get(seriesIndex)).get(i);
			yPositions[i] = ySpan - scaledVal.intValue();
		}
		return yPositions;		
	}

	private ArrayList getScaledValues(ArrayList seriesData){
	    Double yScaleLow = new Double(0);
		Double yScaleHigh = new Double(100);
		
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
		yScaleLow = new Double(minValue);
		yScaleHigh = new Double(maxValue);
		
		/**
		 * @Author: dindiver@in.ibm.com
		 * Equivalent scaling ladder is:
		 * |----|Hi|---|/|---|val|---------(Factor1)--------| 
		 * |----|Hi|---|-|---|Low|---------(Factor2)--------|
		 * |----|F2|---|/|---|F1 |---------(out-put)--------|
		 * */
		for(int i=0; i<numSeries; i++){//for each series
	        double data[] = (double[])seriesData.get(i);
	        Double dataObjects[] = new Double[data.length];
			for(int j=0;j<data.length;j++)
				dataObjects[j] = new Double(data[j]); 
			for(int n=0; n<data.length; n++){ // for each data value
				double Factor1 = 0;
				double Factor2 = 0; 
				Double scaledValue = new Double(0);
				if(0 != dataObjects[n].intValue())
					Factor1 = yScaleHigh.doubleValue() / dataObjects[n].doubleValue();			
				Factor2 = yScaleHigh.doubleValue() - yScaleLow.doubleValue();			
				if(0 != Factor1)
					scaledValue = new Double(Factor2 / Factor1);
				
				((ArrayList)scaledValuesArrayList.get(i)).add(scaledValue);
			}
		}		
		return scaledValuesArrayList;
	}
	
	/**
	 * plot() should not be called w/o first setting the data using setData() 
	 * */
	public void plot(){
	    //if no place to plot, or no data to plot, return.
	    if(null==_parent || null==_seriesData)
	        return;

	    Composite composite = new Composite(_parent, SWT.BORDER);
	    composite.setLayout(new FillLayout());
	    FigureCanvas canvas = new FigureCanvas(composite);
	    
	    Panel contents = new Panel();//A Panel is a general purpose container figure
	    contents.setLayoutManager(new XYLayout());
	    initializeSpan(contents.getClientArea());
	    
	    populateNodesAndEdges();	    
	    
	    drawAxis(contents);
	    for(int i=0; i<_numSeries; i++){
	    	drawDotsAndConnections(contents,getDirectedGraph(i)); // draw the points and connecting wires.
	    }
	    canvas.setBackground(ColorConstants.black);
	    canvas.setContents(contents);
	}
	private void initializeSpan(Rectangle rect){
	    double zoom = GraFixConstants.zoomFactor;
	    GraFixConstants.setXSpan(rect.width*zoom);
	    GraFixConstants.setYSpan(rect.height*zoom);
	}
	private void drawAxis(IFigure contents){
	    //draw X Axis
	    XRulerBar xBar = new XRulerBar(contents, _seriesData);
	    contents.add(xBar);
	    int xPos = 0;
	    int yPos = (int)GraFixConstants.ySpan;
	    int width = (int)GraFixConstants.xSpan + GraFixConstants.xOffset;
	    int height = 20;
	    contents.setConstraint(xBar, new Rectangle(xPos,yPos,width,height));
	    
	    //draw Y Axis
	    YRulerBar yBar = new YRulerBar(contents, _seriesData);
	    contents.add(yBar);
	    xPos = GraFixConstants.xOffset;
	    yPos = (int)GraFixConstants.ySpan;
	    width = 20;
	    height = (int)GraFixConstants.ySpan;
	    contents.setConstraint(yBar, new Rectangle(xPos,yPos,width,height));
	}
	private void drawDotsAndConnections(IFigure contents, DirectedGraph graph){
	    for (int i = 0; i < graph.nodes.size(); i++) {
	        Node node = graph.nodes.getNode(i);
	        drawNode(contents, node);
	    }
	    Color color = GraFixConstants.getAutoColour();
	    for (int i = 0; i < graph.edges.size(); i++) {
	        Edge edge = graph.edges.getEdge(i);
	        drawEdge(contents, edge, color);
	    }		
	}
	private void drawNode(IFigure contents, Node node){
		 Dot dotFigure = new Dot();
		 node.data = dotFigure;
		 int xPos = node.x;
		 int yPos = node.y;	 
		 contents.add(dotFigure);
		 contents.setConstraint(dotFigure, new Rectangle(xPos,yPos,-1,-1));
	}
	private void drawEdge(IFigure contents, Edge edge, Color color){
	    PolylineConnection wireFigure = new PolylineConnection();
	    wireFigure.setForegroundColor(color);
	    EllipseAnchor sourceAnchor = new EllipseAnchor((Dot)edge.source.data);//edge.source is the Node to the left of this edge
		EllipseAnchor targetAnchor = new EllipseAnchor((Dot)edge.target.data);//edge.target is the Node to the right of this edge
	    wireFigure.setSourceAnchor(sourceAnchor);
	    wireFigure.setTargetAnchor(targetAnchor);
	    contents.add(wireFigure);	    
	}
	private DirectedGraph getDirectedGraph(int index) {
		    NodeList nodes = new NodeList((NodeList)_nodeLists.get(index));
		    EdgeList edges = (EdgeList)_edgeLists.get(index);
		    DirectedGraph graph = new DirectedGraph();
		    graph.nodes = nodes;
		    graph.edges = edges;		    
		    //new DirectedGraphLayout().visit(graph);//A visit by DirectedGraphLayout() touches the x and y cordinates, which is not something we want.
		    new BreakCycles().visit(graph);
		    return graph;
    }
}//end of class DirectedGraphXYPlotter