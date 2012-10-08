package SOM;

import java.io.Serializable;
import java.util.ArrayList;

import Util.IConstants;

public class SOM implements Serializable {
	
	private ArrayList<Node> SOM;
	private int mapWidth, mapHeight;	
	
	
	public SOM(){
		this.mapWidth = IConstants.MAPWIDTH;
		this.mapHeight = IConstants.MAPHEIGHT;
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
		
		return Math.sqrt( Math.pow((node1.getXGrade() - node2.getXGrade()),2) +
		Math.pow((node1.getYGrade() - node2.getYGrade()), 2));
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
