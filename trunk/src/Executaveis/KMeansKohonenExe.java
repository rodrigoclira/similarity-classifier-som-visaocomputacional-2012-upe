package Executaveis;

import java.util.ArrayList;
import java.util.Scanner;

import Classification.Classificator;
import Classification.Image;
import SOM.SOM;
import Util.ImageXMLReader;
import Util.PrecisionRecall;

public class KMeansKohonenExe {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			Scanner scanner = new Scanner( System.in );
			
			System.out.println("Tamanho do mapa (inteiro x => x*x) ?");			
			SOM.MAPWIDTH = SOM.MAPHEIGHT = Integer.parseInt(scanner.nextLine());
						 			 			
			System.out.println("Tamanho do vetor de descritores ?");
			SOM.NUMBEROFWEIGHTS = Integer.parseInt(scanner.nextLine());
			
			System.out.println("Camino do arquivo de descritores ?");
			String fileName = scanner.nextLine();
			
			double acuracyTax = 0.0;
			int count = 0;
						
			ImageXMLReader xml = new ImageXMLReader(fileName);
			xml.normalizarArray(0.1, 0.9);
			ArrayList<Image> dataBase = xml.getImages();

			int qtdImages = dataBase.size();			
			PrecisionRecall pr = new PrecisionRecall(qtdImages);
			double t0 = System.currentTimeMillis();


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

				pr.run(5, qtdImages, image, result);
				//fazer calculo do precision recall
				//salvar resultados

				//				for (Image test : result) {
				//					System.out.println("ID: " + test.getId() + " Label: "+ test.getLabel() + " distance: " + test.getDistanceBySearchImage());
				//				}				
			}					

			pr.saveResult(fileName +"SurfPrecisionRecall.txt", (acuracyTax/count));

			System.out.println("Tempo: " + (System.currentTimeMillis() - t0));


		}catch (Exception e) {
			e.printStackTrace();
		}


	}

}
