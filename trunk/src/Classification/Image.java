package Classification;

import java.util.ArrayList;

public class Image implements Comparable<Image> {

	private int id;
	private int label;
	private ArrayList<Double> features;
	private int numberOfFeatures;
	private double distanceBySearchImage;
	
	
	
	public Image(int id, int label, ArrayList<Double> features, int numberOfFeatures) {
		super();
		this.id = id;
		this.label = label;
		this.features = features;
		this.numberOfFeatures = numberOfFeatures;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLabel() {
		return label;
	}
	public void setLabel(int label) {
		this.label = label;
	}
	public ArrayList<Double> getFeatures() {
		return features;
	}
	public void setFeatures(ArrayList<Double> features) {
		this.features = features;
	}
	public void setNumberOfFeatures(int numberOfFeatures) {
		this.numberOfFeatures = numberOfFeatures;
	}
	public int getNumberOfFeatures() {
		return numberOfFeatures;
	}

	public void setDistanceBySearchImage(double distanceBySearchImage) {
		this.distanceBySearchImage = distanceBySearchImage;
	}

	public double getDistanceBySearchImage() {
		return distanceBySearchImage;
	}
	
	@Override
	public int compareTo(Image o) {
				
		double dif = this.distanceBySearchImage - o.distanceBySearchImage;
		return (int)dif;
		
		
	}
	
	
}
