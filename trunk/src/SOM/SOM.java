package SOM;

import java.io.Serializable;
import java.util.ArrayList;

import Kmeans.Group;
import Kmeans.KMeans;
import Util.IConstants;
import Util.Util;

public class SOM implements Serializable {
	
	private ArrayList<Node> SOM;
	private int mapWidth, mapHeight;	
	
	public static int MAPWIDTH = 10;
	public static int MAPHEIGHT = 6;
	
	
	
	public SOM(ArrayList<double[]> dataSet){
		this.mapWidth = MAPWIDTH;
		this.mapHeight = MAPHEIGHT; 
		this.SOM = new ArrayList<Node>();
		InitializationMethod init = new InitializationMethod();
		double[][][] weigthsGrid = init.execute(dataSet, this.mapHeight, this.mapWidth);					
		
		for (int l = 0; l <  this.mapHeight; l++)
		{
			for (int c = 0; c <  this.mapWidth; c++){
				Node node = new Node(l, c, IConstants.NUMBEROFWEIGHTS);
				node.setWeights(weigthsGrid[l+1][c+1]);
				this.SOM.add(node);
			}
		}		
	}
	
	
	public SOM(){
		this.mapWidth = MAPWIDTH;
		this.mapHeight = MAPHEIGHT; 
		this.SOM = new ArrayList<Node>();
		for (int y = 0; y <  this.mapHeight; y++)
		{
			for (int x = 0; x <  this.mapWidth; x++){
				Node node = new Node(x, y, IConstants.NUMBEROFWEIGHTS);
				this.SOM.add(node);
			}
		}		
	}
	
	public double calculateDistanceBetweenGradeNodes(int Node1Pos, int Nodo2Pos){
		Node node1 = this.getSomNode(Node1Pos);
		Node node2 = this.getSomNode(Nodo2Pos);
				
		
		double distance = 0.0;
		for (int i = 0; i < node1.getWeights().length; i++) {
			distance += Math.pow((node1.getWeights()[i] - node2.getWeights()[i]), 2);
			
		}
		return Math.sqrt(distance);
		
}

	public int findBestMatchingNode(ArrayList<Double> inputVector) {
		double minDinstance = Double.MAX_VALUE;
		int minPos = Integer.MAX_VALUE;
		double distance = 0;
		Node currentNode = null;
		
		for (int n = 0; n < this.SOM.size(); n++) {
			
			currentNode = this.SOM.get(n);			
			distance = currentNode.calculateDistance(inputVector);
			
			if(distance<minDinstance){
				minDinstance = distance;
				minPos = n;
			}
		}
		
		return minPos;					
	}
	
	public Node getSomNode(int pos){
		return SOM.get(pos);
	}
	public ArrayList<Node> getSOM() {
		return SOM;
	}


	public void setSOM(ArrayList<Node> sOM) {
		SOM = sOM;
	}


	public int getMapWidth() {
		return mapWidth;
	}


	public void setMapWidth(int mapWidth) {
		this.mapWidth = mapWidth;
	}


	public int getMapHeight() {
		return mapHeight;
	}


	public void setMapHeight(int mapHeight) {
		this.mapHeight = mapHeight;
	}
	
	
}
