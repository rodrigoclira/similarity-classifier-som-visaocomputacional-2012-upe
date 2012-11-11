package Simulation;

import java.io.File;
import java.util.ArrayList;
import Classification.Classificator;
import Classification.Image;
import SOM.SOM;
import Util.ImageXMLReader;
import Util.PrecisionRecall;

public class KohonenSimulationSurf {

	//classe para simular kohonen com o surf
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			double acuracyTax = 0.0;
			int count = 0;

			String resultFolder = "src"+File.separator+"Results"+File.separator; 
			ImageXMLReader xml = new ImageXMLReader("src"+File.separator+"xml"+File.separator+"ImagesSurf.xml");
			xml.normalizarArray(0.1, 0.9);
			ArrayList<Image> dataBase = xml.getImages();
			int qtdImages = dataBase.size();
			PrecisionRecall pr = new PrecisionRecall(qtdImages);
			double t0 = System.currentTimeMillis();

			SOM.MAPHEIGHT = 12;
			SOM.MAPWIDTH = 12;
			SOM.NUMBEROFWEIGHTS = 20;
			//SOM.NUMBEROFWEIGHTS = 60;
			//SOM.NUMBEROFWEIGHTS = 100;
			
			int histogramaClasse[] = Util.Util.contarClasse(dataBase);
			
			for (Image image : dataBase) {

				System.out.println("************* search for image: " + image.getId());

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

				pr.run(5, qtdImages, image, result, histogramaClasse);
				//fazer calculo do precision recall
				//salvar resultados

				//				for (Image test : result) {
				//					System.out.println("ID: " + test.getId() + " Label: "+ test.getLabel() + " distance: " + test.getDistanceBySearchImage());
				//				}				
			}					

			pr.saveResult("src" + File.separator + "Results" + File.separator + "SurfPrecisionRecall.txt", (acuracyTax/count));

			System.out.println("Tempo: " + (System.currentTimeMillis() - t0));


		}catch (Exception e) {
			e.printStackTrace();
		}

	}

}
