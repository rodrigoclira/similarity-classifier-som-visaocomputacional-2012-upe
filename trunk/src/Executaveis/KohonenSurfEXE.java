package Executaveis;

import java.io.File;
import java.util.ArrayList;

import Classification.Classificator;
import Classification.Image;
import SOM.SOM;
import Util.ImageXMLReader;
import Util.PrecisionRecall;

public class KohonenSurfEXE {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			double acuracyTax = 0.0;
			int count = 0;
			
			ImageXMLReader xml = new ImageXMLReader("imagesSurf.xml");
			xml.normalizarArray(0.1, 0.9);
			ArrayList<Image> dataBase = xml.getImages();

			int qtdImages = dataBase.size();			
			PrecisionRecall pr = new PrecisionRecall();
			double t0 = System.currentTimeMillis();


			SOM.MAPHEIGHT = 12;
			SOM.MAPWIDTH = 12;

			

			for (Image image : dataBase) {

				System.out.println("************surf* search for image: " + image.getId());

				ArrayList<Image> partialImageSet = (ArrayList<Image>)dataBase.clone();
				partialImageSet.remove(image);

				Classificator somClassificator = new Classificator();							
				somClassificator.training((ArrayList<Image>)partialImageSet.clone(), (100/100.00));

				//sem shuffle os resultados ficam super foda
				//Collections.shuffle(partialImageSet);
				ArrayList<Image> result = somClassificator.classify(image, partialImageSet);

				//get Acuracy
				acuracyTax += somClassificator.getAcuracyTax();
				count++;

				pr.run(5, qtdImages, image, result);
				//fazer calculo do precision recall
				//salvar resultados

				//				for (Image test : result) {
				//					System.out.println("ID: " + test.getId() + " Label: "+ test.getLabel() + " distance: " + test.getDistanceBySearchImage());
				//				}				
			}					

			pr.saveResult("SurfPrecisionRecall.txt", (acuracyTax/count));

			System.out.println("Tempo: " + (System.currentTimeMillis() - t0));


		}catch (Exception e) {
			e.printStackTrace();
		}


	}

}
