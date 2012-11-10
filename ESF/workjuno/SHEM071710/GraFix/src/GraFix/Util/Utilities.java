/*
 * Created on Mar 23, 2005
 */
package GraFix.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * @author dindiver
 */
public class Utilities {
	
	/**
	 * returns the LARGEST value in the array
	 * */
	public static double getMaximum(Object values[]){
		Arrays.sort(values);
		//return ((Double)values[values.length-1]).doubleValue();	
		
		double maxValue=0;
		if(values instanceof Integer[]){
		    maxValue = ((Integer)values[values.length-1]).doubleValue();
		}
		if(values instanceof Double[]){
		    maxValue = ((Double)values[values.length-1]).doubleValue();
		}
		if(values instanceof Float[]){
		    maxValue = ((Float)values[values.length-1]).doubleValue();
		}
		return maxValue;
	}
	
	/**
	 * returns the SMALLEST value in the array
	 * */
	public static double getMinimum(Object values[]){
		Arrays.sort(values);
		//return ((Double)values[0]).doubleValue();
		
		double minValue = 0;
		if(values instanceof Integer[]){
		    minValue = ((Integer)values[0]).doubleValue();
		}
		if(values instanceof Double[]){
		    minValue = ((Double)values[0]).doubleValue();
		}
		if(values instanceof Float[]){
		    minValue = ((Float)values[0]).doubleValue();
		}
		return minValue;
	}
	
	public static int getLongestSeries(ArrayList seriesData){
	    int longestSeries = 0;
	    int maxLengthFound = 0;
	    Iterator iter = seriesData.iterator();
	    
	    for(int i=0; i<seriesData.size(); i++){//for each series
	        int len = 0;
	        if(seriesData.get(i) instanceof int[]){
	            int data[] = (int[])seriesData.get(i);
	            len = data.length;
	        }
	        if(seriesData.get(i) instanceof double[]){
	            double data[] = (double[])seriesData.get(i);
	            len = data.length;
	        }
	        if(seriesData.get(i) instanceof float[]){
	            float data[] = (float[])seriesData.get(i);
	            len = data.length;
	        }
	        if(len>maxLengthFound){
	            maxLengthFound=len;
	            longestSeries = i;
	        }
	    }	    
	    return longestSeries;
	}
}
