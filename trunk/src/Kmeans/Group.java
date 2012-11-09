package Kmeans;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Group {

	private double[] centroid;
	private ArrayList<double[]> elements;
	private boolean changedOnLastUpdate;


	public Group(double[] centroid) {
		super();
		this.centroid = centroid;
		this.elements = new ArrayList<double[]>();
	}

	public double[] getCentroid() {
		return centroid;
	}
	public void setCentroid(double[] centroid) {
		this.centroid = centroid;
	}
	public ArrayList<double[]> getElements() {
		return elements;
	}
	public void setElements(ArrayList<double[]> elements) {
		this.elements = elements;
	}

	public void updateCentroid() {
				
		double[] newPosition =  new double[centroid.length];
		
		for (int axis = 0; axis < newPosition.length; axis++) {
			double sum = 0.0;
			for (double[] element : this.getElements()) {
				sum += element[axis];
			}
			newPosition[axis] = sum/this.getElements().size();
		}
		
		
		
		boolean isUpdated = false;
		for (int axis = 0; axis < newPosition.length; axis++) {
			if(newPosition[axis] != this.centroid[axis]){
				isUpdated = true;
				break;
			}			
		}
		
		this.centroid = newPosition;
		this.changedOnLastUpdate= isUpdated;
				
	}

	public boolean isCentroidChanged() {
		return changedOnLastUpdate;
	}


}
