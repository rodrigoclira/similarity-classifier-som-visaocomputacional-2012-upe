package SOM;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import Classification.Image;
import Util.IConstants;

public class LearningAlgorithm {

	private SOM som;
	private double mapRadius, neighbourhooodRadius, learningRate;
	public int curretIteration, totalOfIterations;

	private ArrayList<Integer>[] patternTest = null;

	public LearningAlgorithm(SOM som){
		this.som = som;
		this.mapRadius = Math.max(this.som.getMapWidth(),this.som.getMapHeight()) / 2 ;
		this.learningRate = IConstants.STARTLEARNINGRATE;
		this.curretIteration = 0;	
		this.totalOfIterations = IConstants.TOTALOFITERATIONS;

	}
	
	private double getTimeConstant(){
		return  this.totalOfIterations/Math.log(mapRadius);
	}
	

	public void training(ArrayList<Image> setOfTrainingInputs) throws Exception{
		ArrayList<Double> inputVector = null;
			
		
		this.totalOfIterations = setOfTrainingInputs.size();
		Collections.shuffle(setOfTrainingInputs);
		
		for (Image sample : setOfTrainingInputs) {
			inputVector = sample.getFeatures();

			int neuronPos = this.epoch(inputVector);		
			
			curretIteration++;
		}			

	}

	private int epoch(ArrayList<Double> inputVector) throws Exception{


		if(inputVector.size() != IConstants.NUMBEROFWEIGHTS) throw new Exception("O tamnho do vetor de entrada é diferente do tamanho do vetor de pesos do nó");

		//acha o nó com menor distância euclidiana
		int BMUpos = this.som.findBestMatchingNode(inputVector); 

		//calcula o tamanho da vizinhaça
		this.neighbourhooodRadius = mapRadius * Math.exp(-(double) curretIteration/getTimeConstant());

		double distToNodeSq = 0;
		
		for (Node currentNode : som.getSOM()) {		
			
			//calcula a distância ao quadrado do nó atual para o nó ativado
			distToNodeSq = Math.pow((som.getSomNode(BMUpos).getXGrade() - currentNode.getXGrade()),2) +
							Math.pow((som.getSomNode(BMUpos).getYGrade() - currentNode.getYGrade()), 2);
						

			//calcula o raio da vizinhça ao quadrado
			double widthSq = Math.pow(this.neighbourhooodRadius,2);
			
			//se o nó estiver dentro da vizinhaça
			if(distToNodeSq < widthSq ){	
				
				//calcula o fator de influência
				double influenceFactor = Math.exp(-distToNodeSq/(2*widthSq));
				
				//ajusta os pesos
				currentNode.adjustWeight(inputVector, influenceFactor, this.learningRate);

			}			
		}
		//atualiza a taxa de aprendizagem
		this.learningRate = IConstants.STARTLEARNINGRATE * Math.exp(-(double)(curretIteration/(double)this.totalOfIterations));
		
		return BMUpos;

	}
}
