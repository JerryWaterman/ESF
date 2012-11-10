import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Point2D.Float;

/** A 2D POINTB FROMED BY FLOATS **/

public class Point2Df {
	public float x,y;
	/** Creates a new instance of Point2Df */
	public Point2Df() {
	}

 
 
/** METHODS TO CALCULATE THE AREA AND CENTROID OF A POLYGON
    INSERT THEM INTO THE CORRESPONDING CLASS **/
public double SignedPolygonArea(Float[] polygon,int N) {
	Polygon P;
	int i,j;
	double area = 0;

	for (i=0;i<N;i++) {
		//System.out.println(" i = "+i+", N = "+N);
		j = (i + 1) % N;
		//System.out.println(" i = "+i+", N = "+N+", j = "+j);
		area += polygon[i].x * polygon[j].y;
		//System.out.println(" polygon[i].x  "+polygon[i].x+"  polygon.y = "+polygon[i].y);
		area -= polygon[i].y * polygon[j].x;
		System.out.println("L# 29,  in Point2d  polygon[i].x  "+polygon[i].x);
	}
	
	area /= 2.0;
	//System.out.println(" area = "+Math.abs(area));
   	return(area);
   	//return(area < 0 ? -area : area);// for unsigned
}
 
 
/* CENTROID */

public float[] PolygonCenterOfMass(java.awt.geom.Point2D.Float[] polygon,int N, float[] ret) {
	float cx=0,cy=0;
	//System.out.println(" polygon Length,  N = "+N);
	float A=(float)SignedPolygonArea(polygon,N);
	float vArea = Math.abs(A);
	System.out.println(" Point2Df area,  A = "+Math.abs(A));
    Point2Df res=new Point2Df();
	int i,j;

	float factor=0;
	for (i=0;i<N;i++) {
		j = (i + 1) % N;
		factor=(polygon[i].x*polygon[j].y-polygon[j].x*polygon[i].y);
		cx+=(polygon[i].x+polygon[j].x)*factor;
		cy+=(polygon[i].y+polygon[j].y)*factor;
	}
	A*=6.0f;
	factor=1/A;
	cx*=factor;
	cy*=factor;
	System.out.println(" cx = "+cx+"  cy = "+cy);
	res.x=cx;
	res.y=cy;
	//float[] ret = new float[3];
	ret[0] = cx;
	ret[1] = cy;
	ret[2] = vArea;
	System.out.println("res x = "+res.x+"\ty = "+res.y);
	System.out.println("ret[0] = "+ret[0]);
	System.out.println("ret[1] = "+ret[1]);
	System.out.println("ret[2] = "+ret[2]);
	//return res;
	return ret;
}

}

/*
 int[] ret = new int[2];  

             int[0] = Male;  

             int[1] = Female;  

             return ret 
*/

