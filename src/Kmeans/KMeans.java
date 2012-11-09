package Kmeans;

import java.util.ArrayList;

import Util.IConstants;
import Util.Util;

public class KMeans {

	private Group[] clusters;
	private int K;

	public KMeans( int k) {				
		this.K = k;
	}

	public Group[] execute(ArrayList<double[]> dataSet){
		boolean isCentroidChanged = false;		
		double time = System.currentTimeMillis();
		int count = 0;

		//initialization
		this.clusters = new Group[this.K];
		for (int i = 0; i < clusters.length; i++) {
			int randomSelection =  (int)Math.floor(dataSet.size() * Math.random());						
			double[] centroid = dataSet.get(randomSelection);			
			clusters[i] = new Group(centroid);			
		}

		do{				
			for (Group cluster : clusters) {
				cluster.getElements().clear();
			}
			
			for (double[] cordinates : dataSet) {
				int nearClusterPosition = Integer.MAX_VALUE;
				double nearestDistance = Double.MAX_VALUE;
				for(int i = 0 ; i < this.clusters.length; i++){
					double distance = Util.euclidianDistance(this.clusters[i].getCentroid(),cordinates);
					if(distance < nearestDistance){
						nearestDistance = distance;
						nearClusterPosition = i;
					}
				}
				this.clusters[nearClusterPosition].getElements().add(cordinates);
			}

			for (Group cluster : this.clusters) {
				cluster.updateCentroid();
			}

			isCentroidChanged = false;		
			for (Group cluster : this.clusters) {
				if(cluster.isCentroidChanged()){
					isCentroidChanged = true;
					break;
				}
			}

			count++;			
			
		}while(count <= 100);

		
		
		return  this.clusters;
	}

	public void setClusters(Group[] clusters) {
		this.clusters = clusters;
	}

	public Group[] getClusters() {
		return clusters;
	}

}
