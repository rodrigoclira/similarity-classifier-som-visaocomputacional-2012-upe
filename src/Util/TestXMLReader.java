package Util;
import java.util.ArrayList;

import Classification.Image;

public class TestXMLReader {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try{

			ImageXMLReader Xml = new ImageXMLReader("xml/images.xml");
			
			Xml.normalizarArray(0.1, 0.9);
			
			ArrayList<Double> temp = Xml.getArrayImages().get(959);
			
			for (Double double1 : temp) {
				System.out.print(double1 + " ");
			}
			
			System.out.println("");
			Image img = Xml.getImages().get(15);
			System.out.println("\nLabel " + img.getLabel() + " ID: " + img.getId());
			temp = img.getFeatures();
			System.out.println("");
			
			for (Double double1 : temp) {
				System.out.print(double1 + " ");
			}

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
