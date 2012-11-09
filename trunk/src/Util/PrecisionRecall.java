package Util;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import Classification.Classificator;
import Classification.Image;


public class PrecisionRecall {

	private int qtdImages;
	private ArrayList<Double> pointsPrecision;
	private ArrayList<Double> pointsRecall;


	public PrecisionRecall(){

		pointsPrecision = new ArrayList<Double>();
		pointsRecall = new ArrayList<Double>();

		for(int i = 0; i<191; i+=1){
			pointsPrecision.add(0.0);
			pointsRecall.add(0.0);
		}

	}

	public PrecisionRecall(int qtdImages){

		pointsPrecision = new ArrayList<Double>();
		pointsRecall = new ArrayList<Double>();

		for(int i = 0; i<191; i+=1){
			pointsPrecision.add(0.0);
			pointsRecall.add(0.0);
		}

	}

	public void temp(){
		double value = 0;
		for(int i = 0 ; i < pointsPrecision.size(); i+=1){

			value = pointsPrecision.get(i);
			value = value/960;
			pointsPrecision.set(i, value);

			value = pointsRecall.get(i);
			value = value/960;
			pointsRecall.set(i, value);
		}
	}


	public void saveResult(String path, double acuracyTax) throws IOException{

		temp();

		FileWriter file = new FileWriter(path);
		PrintWriter printWriter = new PrintWriter(file);

		printWriter.println("Precicision\n");
		for(Double value : pointsPrecision){
			printWriter.println(""+value);
		}

		printWriter.write("\nRecall\n");
		for(Double value : pointsRecall){
			printWriter.println(""+value);
		}

		printWriter.write("\nAcuracyTax: " + acuracyTax);

		printWriter.flush();
		printWriter.close();
	}

	public ArrayList<Double> getPointsPrecision() {
		return pointsPrecision;
	}

	public void setPointsPrecision(ArrayList<Double> pointsPrecision) {
		this.pointsPrecision = pointsPrecision;
	}

	public ArrayList<Double> getPointsRecall() {
		return pointsRecall;
	}

	public void setPointsRecall(ArrayList<Double> pointsRecall) {
		this.pointsRecall = pointsRecall;
	}

	public void run(int step, int endpoint, Image image, ArrayList<Image> result){
		int relevant = 0; 
		double stepPrecision = 0;
		double stepRecall = 0;
		double value = 0;
		Image resultImage;
		int label = image.getLabel();
		int count = 0;

		for(int i = 1; i<= endpoint - step; i+=1 ){	

			resultImage = result.get(i - 1);

			if (resultImage.getLabel() == label){
				relevant += 1;
			}

			if (i % 5 == 0){
				stepPrecision = (double) relevant/i;
				stepRecall = (double) relevant/15; //HARDCODE!

				value = pointsPrecision.get(count);
				value += stepPrecision;
				pointsPrecision.set(count, value);

				value = pointsRecall.get(count);
				value += stepRecall;
				pointsRecall.set(count, value);
				count+=1;
			}
		}
	}
}
