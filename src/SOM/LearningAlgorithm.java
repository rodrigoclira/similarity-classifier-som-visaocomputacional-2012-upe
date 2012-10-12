package SOM;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Classification.Image;
import Util.IConstants;

public class LearningAlgorithm {
	
	private SOM som;
	private double mapRadius, neighbourhooodRadius, learningRate;
	public int curretIteration;
	private int trainingSetSize;
	
	public LearningAlgorithm(SOM som){
		this.som = som;
		this.mapRadius = Math.max(this.som.getMapWidth(),this.som.getMapHeight()) / 2 ;
		this.learningRate = IConstants.STARTLEARNINGRATE;
		this.curretIteration = 0;
	}
	private double getTimeConstant(){
		return  this.trainingSetSize/Math.log(mapRadius);
	}
	
	private double calculateNeighbourhooodRadius (){
		this.neighbourhooodRadius = mapRadius * Math.exp(-(double) curretIteration/getTimeConstant());
		return neighbourhooodRadius;
	}
	
	private double updateLearningRate(){
		this.learningRate = IConstants.STARTLEARNINGRATE * Math.exp(-(double)curretIteration/this.trainingSetSize);
		return this.learningRate;
	}
	
	private double calculateInfluenceFactor(int BMUPos, int nodeX, int nodeY){
		
		double gradeDistance = Math.pow((som.getSomNode(BMUPos).getXGrade() - nodeX),2) +
								Math.pow((som.getSomNode(BMUPos).getYGrade() - nodeY), 2);
			
		 
		return Math.exp(-(gradeDistance/(2*Math.pow(this.neighbourhooodRadius, 2))));
	}
	
	private double calculateDistnceSQToBMU(int BMUPos, int nodeX, int nodeY){
		
		return Math.pow((som.getSomNode(BMUPos).getXGrade() - nodeX),2) +
		Math.pow((som.getSomNode(BMUPos).getYGrade() - nodeY), 2);
	}
	

	public void training(ArrayList<Image> setOfTrainingInputs) throws Exception{
		
		ArrayList<Double> inputVector = null;
		this.trainingSetSize = setOfTrainingInputs.size();		
		
		Collections.shuffle(setOfTrainingInputs);		
				
		while(curretIteration < this.trainingSetSize){
								
			inputVector = setOfTrainingInputs.remove(0).getFeatures();
			
			this.epoch(inputVector);
			
			curretIteration++;
		}
		
	}
	
	private void epoch(ArrayList<Double> inputVector) throws Exception{
		
		
		if(inputVector.size() != IConstants.NUMBEROFWEIGHTS) throw new Exception("O tamnho do vetor de entrada � diferente do tamanho do vetor de pesos do n�");
		
		int BMUpos = this.som.findBestMatchingNode(inputVector); 
			
		this.calculateNeighbourhooodRadius();
		
		
		double distToNodeSq = 0;
		for (Node currentNode : som.getSOM()) {		
			
			distToNodeSq = this.calculateDistnceSQToBMU(BMUpos, currentNode.getXGrade(), currentNode.getYGrade());
			
			double widthSq = Math.pow(this.neighbourhooodRadius,2);
			if(distToNodeSq < widthSq ){
				
				double influenceFactor = this.calculateInfluenceFactor(BMUpos, currentNode.getXGrade(), currentNode.getYGrade());
				currentNode.adjustWeight(inputVector, influenceFactor, this.learningRate);
				
			}			
		}
		
		this.updateLearningRate();	
		
	}
}
