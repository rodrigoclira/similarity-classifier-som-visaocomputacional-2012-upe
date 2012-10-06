package SOM;

import java.util.ArrayList;

import Util.IConstants;

public class LearningAlgorithm {
	
	private SOM som;
	private double mapRadius, neighbourhooodRadius, learningRate;
	public int curretIteration;
	
	public LearningAlgorithm(SOM som){
		this.som = som;
		this.mapRadius = Math.max(this.som.getMapWidth(),this.som.getMapHeight()) / 2 ;
		this.learningRate = IConstants.STARTLEARNINGRATE;
		this.curretIteration = 0;
	}
	private double getTimeConstant(){
		return  IConstants.TOTALOFITERATIONS/Math.log(mapRadius);
	}
	
	private double calculateNeighbourhooodRadius (){
		this.neighbourhooodRadius = mapRadius * Math.exp(-(double) curretIteration/getTimeConstant());
		return neighbourhooodRadius;
	}
	
	private double updateLearningRate(){
		this.learningRate = IConstants.STARTLEARNINGRATE * Math.exp(-(double)curretIteration/IConstants.TOTALOFITERATIONS);
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
	

	
	private int findBestMatchingNode(ArrayList<Double> inputVector) {
		double minDinstance = Double.MAX_VALUE;
		int minPos = Integer.MAX_VALUE;
		double distance = 0;
		Node currentNode = null;
		
		for (int n = 0; n < this.som.getSOM().size(); n++) {
			
			currentNode = this.som.getSomNode(n);			
			distance = currentNode.calculateDistance(inputVector);
			
			if(distance<minDinstance){
				minDinstance = distance;
				minPos = n;
			}
		}
		
		return minPos;					
	}
	
	public void training(ArrayList<ArrayList<Double>> setOfInputVectors) throws Exception{
		
		int randomPos = 0;
		ArrayList<Double> inputVector = null;
		
		while(curretIteration < IConstants.TOTALOFITERATIONS){
			
			randomPos = (int)(setOfInputVectors.size() * Math.random());
			inputVector = setOfInputVectors.get(randomPos);
			
			this.epoch(inputVector);
			
			curretIteration++;
		}
		
	}
	
	private void epoch(ArrayList<Double> inputVector) throws Exception{
		
		
		if(inputVector.size() != IConstants.NUMBEROFWEIGHTS) throw new Exception("O tamnho do vetor de entrada � diferente do tamanho do vetor de pesos do n�");
		
		int BMUpos = this.findBestMatchingNode(inputVector);
		this.calculateNeighbourhooodRadius();
		
		Node currentNode = null;
		double distToNodeSq = 0;
		for(int n = 0 ; n < som.getSOM().size(); n++){
			currentNode = som.getSomNode(n);
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
