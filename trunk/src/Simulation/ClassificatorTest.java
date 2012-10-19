package Simulation;

import java.util.ArrayList;
import java.util.Collections;

import Classification.Classificator;
import Classification.Image;
import Util.ImageXMLReader;

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
			
			double t0 = System.currentTimeMillis();
			for (Image image : dataBase) {
				
				System.out.println("************* search for image: " + image.getId());
				
				ArrayList<Image> partialImageSet = (ArrayList<Image>)dataBase.clone();
				partialImageSet.remove(image);
				
				Classificator somClassificator = new Classificator();							
				somClassificator.training((ArrayList<Image>)partialImageSet.clone(), (100/100.00));
				
				//sem shuffle os resultados ficam super foda
				//Collections.shuffle(partialImageSet);
				ArrayList<Image> result = somClassificator.classify(image, partialImageSet);
				
				//fazer calculo do precision recall
				//salvar resultados
				
				for (Image test : result) {
					System.out.println("ID: " + test.getId() + " Label: "+ test.getLabel() + " distance: " + test.getDistanceBySearchImage());
				}				
			}					
			
			System.out.println("Tempo: " + (System.currentTimeMillis() - t0));

			
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

}
