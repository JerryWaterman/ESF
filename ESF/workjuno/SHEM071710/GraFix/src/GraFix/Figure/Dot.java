/*
 * Created on Mar 22, 2005
 */

package GraFix.Figure;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.geometry.Dimension;

/**
 * @author dindiver
 * A Figure to represent Node(s) in a graph.
 */
public class Dot extends Ellipse {
	
	public Dot(){
		Dimension dim = new Dimension(5,5);
		this.setSize(dim);
		this.setBackgroundColor(ColorConstants.yellow);
		this.setOpaque(true);
	}
	
}
