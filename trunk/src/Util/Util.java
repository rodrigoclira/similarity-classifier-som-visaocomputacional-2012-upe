package Util;

import java.util.ArrayList;

import Classification.Image;

public class Util {
	
	public static double euclidianDistance(double[] cordinate1, double[] cordinate2){
		double sqDistance = 0.0;

		for (int i=0; i<cordinate1.length; ++i)
		{
			sqDistance += Math.pow((cordinate1[i] - cordinate2[i]),2) ;
		}
 
		return Math.sqrt(sqDistance);

	}
	
	public static int[] contarClasse(ArrayList<Image> arrayImages){
		
		int ultimaImagem = arrayImages.size()-1;
		int quantidadeClasse[] = new int[arrayImages.get(ultimaImagem).getLabel()+1];
		
		for(Image img : arrayImages){
			quantidadeClasse[img.getLabel()]+=1;
		}
		
		return quantidadeClasse;
	}
	
}
