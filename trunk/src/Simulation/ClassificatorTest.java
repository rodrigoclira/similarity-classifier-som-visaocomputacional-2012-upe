package Simulation;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import Classification.Classificator;
import Classification.Image;
import Util.IConstants;
import Util.ImageXMLReader;
import Util.PrecisionRecall;
import SOM.SOM;

public class ClassificatorTest {

	//simula��o de kohonen com as caracter�sticas da primeira parte
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			double acuracyTax = 0.0;
			int count = 0;

			String resultFolder = "src"+File.separator+"Results"+File.separator; 
			ImageXMLReader xml = new ImageXMLReader("src"+File.separator+"xml"+File.separator+"images.xml");
			xml.normalizarArray(0.1, 0.9);
			ArrayList<Image> dataBase = xml.getImages();
			int qtdImagens = dataBase.size();
			PrecisionRecall pr = new PrecisionRecall(qtdImagens);
			double t0 = System.currentTimeMillis();
			int histogramaClasse[] = Util.Util.contarClasse(dataBase);


			SOM.MAPHEIGHT = 12;
			SOM.MAPWIDTH = 12;
			SOM.NUMBEROFWEIGHTS = 8;

			

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

				pr.run(5, qtdImagens, image, result, histogramaClasse);
				//fazer calculo do precision recall
				//salvar resultados

				//				for (Image test : result) {
				//					System.out.println("ID: " + test.getId() + " Label: "+ test.getLabel() + " distance: " + test.getDistanceBySearchImage());
				//				}				
			}					

			pr.saveResult("src" + File.separator + "Results" + File.separator + "precisionRecall.txt", (acuracyTax/count));

			System.out.println("Tempo: " + (System.currentTimeMillis() - t0));


		}catch (Exception e) {
			e.printStackTrace();
		}

	}

}
