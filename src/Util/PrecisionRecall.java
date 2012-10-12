package Util;
import java.io.FilePermission;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import Classification.Classificator;
import Classification.Image;


public class PrecisionRecall {
	
	private Classificator classificator;
	private ArrayList<Image> arrayImage;
	private ArrayList<Double> pointsPrecision;
	private ArrayList<Double> pointsRecall;

	public PrecisionRecall (ArrayList<Image> arrayImage, String path)
			throws Exception{

		this.arrayImage = arrayImage;
		classificator = new Classificator(path);

	}
	
	public void saveResult(String path) throws IOException{
		
		FileWriter file = new FileWriter(path);
		PrintWriter printWriter = new PrintWriter(file);
		
		printWriter.println("Precicision\n");
		for(Double value : pointsPrecision){
			printWriter.println(""+value);
		}
		
		printWriter.write("Recall\n");
		for(Double value : pointsRecall){
			printWriter.println(""+value);
		}

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
	
	public void run(int step, int endpoint){
		pointsPrecision = new ArrayList<Double>();
		pointsRecall = new ArrayList<Double>();
		int _step = step;
		int count = 0;
		List<Image> bestImages;
		ArrayList<Image> tempList;
		int relevant = 0; 
		double accPrecision = 0;
		double accRecall = 0;
		int position = 0;
		
		while (_step < endpoint){
			
			accPrecision = 0;
			accRecall = 0;
			
			for(int img = 0 ; img < arrayImage.size(); img+=1){
				count=0;	
				Image image = arrayImage.get(img);
				tempList = classificator.classify(image, arrayImage);

				//Contar os a quantidade de acerto				
				bestImages = tempList.subList(0, _step+1); // gambilight
				relevant = 0;
				position = 0;
				
				while(count<_step){

					if (image.getId() == bestImages.get(position).getId()){
						position+=1;
						continue;
					}else{

						if (image.getLabel() == bestImages.get(position).getLabel()){
							relevant+=1;
						}
					}
					position+=1;
					count+=1;
				}
				accPrecision += (double)relevant/_step;
				accRecall += (double) relevant/15; //HARDCODE!
			}
			
			pointsPrecision.add((double)accPrecision/arrayImage.size());
			pointsRecall.add((double)accRecall/arrayImage.size());
			
			_step+=step;
		}
	}
}
