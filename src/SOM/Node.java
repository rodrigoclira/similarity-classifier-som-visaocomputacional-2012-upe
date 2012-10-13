package SOM;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import Util.IConstants;

public class Node implements Serializable {
	
	
	private double[] weights;
	private int numWeights, xGrade, yGrade;
		
	
	public Node(int xGrade, int yGrade, int numWeights ) {
		super();		
		this.xGrade = xGrade;
		this.yGrade = yGrade;
		weights = new double[numWeights];
		this.numWeights = numWeights;
		Random random = new Random();  
		for (int i = 0; i <numWeights; i++) {			
			
			double randomWeight =  IConstants.MINWEIGHTVALUE + 
			(IConstants.MAXWEIGHTVALUE - IConstants.MINWEIGHTVALUE) 
			* random.nextDouble();
		
			weights[i] = randomWeight;
		}
	}
	
	public double calculateDistance(ArrayList<Double> inputVector){
		double distance = 0;
 
		for (int i=0; i<this.weights.length; ++i)
		{
			distance += Math.pow((inputVector.get(i) - weights[i]),2) ;
		}
 
		return Math.sqrt(distance);
	}
	
	public void adjustWeight(ArrayList<Double> inputVector, double influenceFactor, double learningRate){
		for (int i = 0; i < weights.length; i++) {
			weights[i] += influenceFactor * learningRate * (inputVector.get(i)-weights[i]); 
		}
	}
	
	//Getters and Setters
	public double[] getWeights() {
		return weights;
	}
	public void setWeights(double[]  weights) {
		this.weights = weights;
	}
	public int getXGrade() {
		return xGrade;
	}
	public void setXGrade(int xGrade) {
		this.xGrade = xGrade;
	}
	public int getYGrade() {
		return yGrade;
	}
	public void setYGrade(int yGrade) {
		this.yGrade = yGrade;
	}



	public int getNumWeights() {
		return numWeights;
	}



	public void setNumWeights(int numWeights) {
		this.numWeights = numWeights;
	}
	
	
}
