package SOM;

import java.util.ArrayList;

import Util.IConstants;

public class SOM {
	
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
