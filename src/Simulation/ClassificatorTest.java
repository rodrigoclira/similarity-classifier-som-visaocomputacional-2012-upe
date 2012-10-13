package Simulation;

import java.util.ArrayList;
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

			List<Image> test = dataBase.subList(0, 16);
			
			int pos = somClassificator.testClassificator(test.get(0));
			
			System.out.println("neron:" + pos);
			for (Image image : test) {
				System.out.println(somClassificator.testClassificator(image));
			}

			



		}catch (Exception e) {
			e.printStackTrace();
		}

	}

}
