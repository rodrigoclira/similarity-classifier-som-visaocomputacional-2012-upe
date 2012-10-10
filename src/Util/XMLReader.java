import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;



public class XMLReader {

	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ArrayList<ArrayList<Double>> arrayImages = new ArrayList<ArrayList<Double>>();
	private boolean normalized = false;

	public XMLReader(String name) throws ParserConfigurationException, IOException, SAXException{
		docBuilderFactory = DocumentBuilderFactory.newInstance();
		docBuilder = docBuilderFactory.newDocumentBuilder();
		doc = docBuilder.parse(new File(name));
		this.load();
	}

	public ArrayList<ArrayList<Double>> getArrayImages(){
		return arrayImages;
	}

	public void normalizarArray(double a, double b){

		Double minValues[] = {Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE, 
				Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE};

		Double maxValues[] = {Double.MIN_VALUE ,Double.MIN_VALUE ,Double.MIN_VALUE, Double.MIN_VALUE, 
				Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE};

		double value;
		if(!normalized){

			for(int i=0; i<8; i+=1){
				for (ArrayList<Double> temp : arrayImages) {
					value = temp.get(i);

					if (value>maxValues[i]){
						maxValues[i] = value;
					}

					if (value<minValues[i]){
						minValues[i] = value;
					}
				}
			}

			double newValue;
			for(int i=0; i<8; i+=1){

				for (ArrayList<Double> temp : arrayImages) {
					value = temp.get(i);
					newValue = (b - a) * (value - minValues[i])/ (maxValues[i] - minValues[i]) + a;
					temp.set(i, newValue);
				}

			}

			normalized = true;
		}

	}

	private void load(){
		doc.getDocumentElement ().normalize ();
		NodeList listOfImages = doc.getElementsByTagName("image");
		int totalImages = listOfImages.getLength();
		System.out.println("Total de imagens carregadas: "+ totalImages + "imagens." );

		for (int count = 0; count < totalImages; count+=1){
			Node firstImageNode = listOfImages.item(count);
			if(firstImageNode.getNodeType() == Node.ELEMENT_NODE){

				Element firstImageElement = (Element)firstImageNode;
				NodeList descritoresList = firstImageElement.getElementsByTagName("descritor");
				int totalDescritores = descritoresList.getLength();

				ArrayList<Double> arrayDescritores = new ArrayList<Double>();
				for (int count1 = 0; count1 < totalDescritores; count1+=1){

					Element DescritorElement = (Element)descritoresList.item(count1);
					NodeList textDescritorList = DescritorElement.getChildNodes();
					double number = Double.valueOf(((Node)textDescritorList.item(0)).getNodeValue().trim());
					arrayDescritores.add(number);
				}

				arrayImages.add(arrayDescritores);
			}

		}

	}

	public static void main(String[] args) {

		try{

			XMLReader Xml = new XMLReader("images.xml");
			ArrayList<Double> temp = Xml.getArrayImages().get(0);
			
			for (Double double1 : temp) {
				System.out.print(double1 + " ");
			}
			
			Xml.normalizarArray(0.1, 0);
			temp = Xml.getArrayImages().get(0);
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
