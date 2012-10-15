package Simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Classification.Classificator;
import Classification.Image;
import Util.ImageXMLReader;
import Util.PrecisionRecall;

public class ClassificatorTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			String resultFolder = "src//Results//"; 
			ImageXMLReader xml = new ImageXMLReader("src//xml//images.xml");
			xml.normalizarArray(0.1, 0.9);
			ArrayList<Image> dataBase = xml.getImages();

			Classificator somClassificator = new Classificator();							
			somClassificator.training((ArrayList<Image>)dataBase.clone(), (100/100.00));

			Image query = dataBase.get(0);
			Collections.shuffle(dataBase);
		
			ArrayList<Image> result = somClassificator.classify(query, dataBase);
			
			for (Image image : result) {
				System.out.println("ID: " + image.getId() + " Label: "+ image.getLabel() + " distance: " + image.getDistanceBySearchImage());
			}			

			
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

}
