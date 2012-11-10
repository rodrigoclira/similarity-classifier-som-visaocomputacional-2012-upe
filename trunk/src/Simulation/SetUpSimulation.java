package Simulation;

import java.io.File;
import java.util.ArrayList;

import Classification.Classificator;
import Classification.Image;
import SOM.SOM;
import Util.ImageXMLReader;
import Util.PrecisionRecall;

public class SetUpSimulation {

	//teste do kmeans para melhorar o classificador kohonen
	public static void main(String[] args) {
		try{
			double acuracyTax = 0.0;
			int count = 0;
			
			String resultFolder = "src//Results//"; 
			
			ImageXMLReader xml = new ImageXMLReader("src//xml//images.xml");
			
			xml.normalizarArray(0.1, 0.9);
			ArrayList<Image> dataBase = xml.getImages();

			for(int mapsize = 4 ; mapsize <= 12 ; mapsize+=1){
				
				
				acuracyTax = 0.0;
				count = 0;
				Double precision[] = new Double[191];
				Double recall[] = new Double[191];
				PrecisionRecall pr = new PrecisionRecall();
				double t0 = System.currentTimeMillis();

				
				SOM.MAPHEIGHT = mapsize;
				SOM.MAPWIDTH = mapsize;
				SOM.NUMBEROFWEIGHTS = 8;

				System.out.println("****************************** Map SIze = "+ mapsize + " x " + mapsize);
				System.out.println("****************************** Map SIze = "+ mapsize + " x " + mapsize);
				
				for (Image image : dataBase) {
					
					System.out.println("************* search for image: " + image.getId());

					ArrayList<Image> partialImageSet = (ArrayList<Image>)dataBase.clone();
					partialImageSet.remove(image);

					Classificator somClassificator = new Classificator(partialImageSet);							
					somClassificator.training((ArrayList<Image>)partialImageSet.clone(), (100/100.00));

					//sem shuffle os resultados ficam super foda
					//Collections.shuffle(partialImageSet);
					ArrayList<Image> result = somClassificator.classify(image, partialImageSet);
					
					//get Acuracy
					acuracyTax += somClassificator.getAcuracyTax();
					count++;
					
					pr.run(5, 960, image, result);
					//fazer calculo do precision recall
					//salvar resultados

					//				for (Image test : result) {
					//					System.out.println("ID: " + test.getId() + " Label: "+ test.getLabel() + " distance: " + test.getDistanceBySearchImage());
					//				}		
										
				}					

				pr.saveResult("src" + File.separator + "Results" + File.separator + "precisionRecall_"+ mapsize + ".txt", (acuracyTax/count));

				System.out.println("Tempo: " + (System.currentTimeMillis() - t0));
			}

		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	
}
