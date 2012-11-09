package SOM;

import java.util.ArrayList;

import jminhep.cluster.DataHolder;
import jminhep.cluster.DataPoint;
import jminhep.cluster.Partition;



import Kmeans.Group;
import Kmeans.KMeans;
import Util.IConstants;
import Util.Util;

public class InitializationMethod {

	private int weightSize;
	private int M;
	private int N;

	public double[][][] execute(ArrayList<double[]> dataSet, int height, int width){
		this.weightSize = dataSet.get(0).length;
		this.M = height;
		this.N = width ;
		double[][][] retorno = new double[M + 1][N + 1][weightSize];
		
		DataHolder centroids = this.executeKmeans(dataSet,height * width);
		
		double largerDistance = Double.MIN_VALUE;
		DataPoint cornerM1 = null ;
		DataPoint corner1N = null;
		for (DataPoint dp1 : centroids.getArray()) {
			for (DataPoint dp2 : centroids.getArray()) {
				double distanceBetweenCentroids = DataPoint.distance(dp1, dp2);
				if(distanceBetweenCentroids > largerDistance){
					largerDistance = distanceBetweenCentroids;
					cornerM1 = dp1;
					corner1N = dp2;
				}
			}			
		}
						
		retorno[M][1] = this.returnDoubleArray(cornerM1);
		retorno[1][N] = this.returnDoubleArray(corner1N);
		
		DataPoint corner11 = null;
		largerDistance = Double.MIN_VALUE;
		for (DataPoint dp1 : centroids.getArray()) {
			if(!dp1.equals(cornerM1) && !dp1.equals(corner1N)){
				double distance = DataPoint.distance(dp1, cornerM1);
				distance += DataPoint.distance(dp1, corner1N);
				if(distance > largerDistance){
					largerDistance = distance;
					corner11 = dp1;
				}
			}
		}

		retorno[1][1] = this.returnDoubleArray(corner11);

		DataPoint cornerMN = null;
		largerDistance = Double.MIN_VALUE;
		for (DataPoint dp1 : centroids.getArray()) {
			if(!dp1.equals(cornerM1) && !dp1.equals(corner1N) && !dp1.equals(corner11)){
				double distance = DataPoint.distance(dp1, cornerM1);
				distance += DataPoint.distance(dp1, corner1N);
				distance += DataPoint.distance(dp1, corner11);
				if(distance > largerDistance){
					largerDistance = distance;
					cornerMN = dp1;
				}
			}
		}
		retorno[M][N] = this.returnDoubleArray(cornerMN);
				
		for(int i = 1 ; i<= M ; i++){
			for(int j = 1 ; j <= N; j++){
				if(!((i==1 && j == 1) || (i==M && j==N) || (i==M && j==1 ) || (i==1 && j==N) )){
					double[] p1 =  formulaP1(i,j,retorno);
					double[] p2 =  formulaP2(i,j,retorno);
					double[] p3 =  formulaP3(i,j,retorno);
					double[] p4 =  formulaP4(i,j,retorno);

					retorno[i][j] = new double[weightSize];
					for (int k = 0; k < p4.length; k++) {											
						retorno[i][j][k] = p1[k] + p2[k] + p3[k] + p4[k];
					}
				}
			}
		}

		return retorno;

	}

	private double[] returnDoubleArray(DataPoint dp) {
		double[] retorno = new double[dp.getDimension()];
		for (int i = 0; i < dp.getDimension(); i++) {
			retorno[i] = dp.getAttribute(i);
		}
		return retorno;
	}

	private DataHolder executeKmeans(ArrayList<double[]> dataSet, int i) {
		DataHolder data = new DataHolder("DataSet");
		
		for (double[] values : dataSet) {
			double[] a = new double[values.length];
			for (int j = 0; j < values.length; j++) {
				a[j] = values[j];				
			}
			
			data.add(new DataPoint(a));
		}
		
		Partition pat = new Partition(data);
		
		pat.set(i, 0.001, 1.7, 1000);
		
		pat.run(111);
		
		DataHolder Centers = pat.getCenters();
		Centers.analyseSet();
		
		return Centers;
		
	}

	private double[] formulaP4(int i, int j, double[][][] retorno) {
		double[] result = new double[this.weightSize];				

		for (int k = 0; k < result.length; k++) {
			result[k] = ((N-j)*(M-i)*retorno[1][1][k])/((N-1)*(M-1));
			
		}
				
		return result;
	}

	private double[] formulaP3(int i, int j, double[][][] retorno) {
		double[] result = new double[this.weightSize];				

		for (int k = 0; k < result.length; k++) {
			result[k] = ((N-j)*(i-1)*retorno[M][1][k])/((N-1)*(M-1));
			
		}
				
		return result;

	}

	private double[] formulaP2(int i, int j, double[][][] retorno) {
		double[] result = new double[this.weightSize];		
		
		for (int k = 0; k < result.length; k++) {
			result[k] = ((j-1)*(M-i)*retorno[1][N][k])/((N-1)*(M-1));
			
		}
				
		return result;

	}

	private double[] formulaP1(int i, int j, double[][][] retorno) {
		double[] result = new double[this.weightSize];		

		for (int k = 0; k < result.length; k++) {
			result[k] = ((j-1)*(i-1)*retorno[M][N][k])/((N-1)*(M-1));			
		}
				
		return result;
	}

}
