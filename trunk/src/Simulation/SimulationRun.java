package Simulation;

import java.util.ArrayList;
import java.util.Arrays;

import Classification.Classificator;
import Classification.Image;
import SOM.Node;
import Util.ImageXMLReader;
import Util.PrecisionRecall;

public class SimulationRun {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			String resultFolder = "src//Results//"; 
			ImageXMLReader xml = new ImageXMLReader("src//xml//images.xml");
			xml.normalizarArray(0.1, 0.9);
			ArrayList<Image> dataBase = xml.getImages();

			for (int i = 50; i <= 100; i+=10 ) {

				Classificator somClassificator = new Classificator();				

				somClassificator.training((ArrayList<Image>)dataBase.clone(), (i/100.00));


				PrecisionRecall pr = new PrecisionRecall(dataBase, somClassificator);
				int step = 5;
				//				for (int step = 5; step < 955; step+=5) {
				pr.run(step, 955);
				pr.saveResult(resultFolder+i+"_"+step+".txt");
				//				}

			}

		}catch (Exception e) {
			e.printStackTrace();
		}

	}

}
