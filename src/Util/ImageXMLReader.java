import java.io.File;

import java.io.IOException;
import java.util.ArrayList;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

public class ImageXMLReader {

	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ArrayList<ArrayList<Double>> arrayImages = new ArrayList<ArrayList<Double>>();
	private ArrayList<Integer> arrayImagesLabels = new ArrayList<Integer>();
	private ArrayList<Image> array = new ArrayList<Image>();
	private int quantidadeCaracteristicas;
	private boolean normalized = false;

	public ImageXMLReader(String name) throws ParserConfigurationException, IOException, SAXException{
		docBuilderFactory = DocumentBuilderFactory.newInstance();
		docBuilder = docBuilderFactory.newDocumentBuilder();
		doc = docBuilder.parse(new File(name));
		this.load();
	}
	
	public ArrayList<Image> getImages(){
		Image img = null;
		int label = 0;
		for (int count = 0; count < arrayImages.size() ; count+=1){
			
			label = arrayImagesLabels.get(count);
			img = new Image(count, label, arrayImages.get(count), quantidadeCaracteristicas);
			array.add(img);
		}
		return array;
	}

	public ArrayList<ArrayList<Double>> getArrayImages(){
		return arrayImages;
	}

	public void normalizarArray(double a, double b){
		
		double value;
		ArrayList<Double> minValues = new ArrayList<Double>();
		ArrayList<Double> maxValues = new ArrayList<Double>();
		for (int i =0 ; i < quantidadeCaracteristicas; i+=1){
			minValues.add(Double.MAX_VALUE);
			maxValues.add(Double.MIN_VALUE);
		}
		
		if(!normalized){

			for(int i=0; i<quantidadeCaracteristicas; i+=1){
				for (ArrayList<Double> temp : arrayImages) {
					value = temp.get(i);
					
					if (value>maxValues.get(i)){
						maxValues.set(i, value);
					}

					if (value<minValues.get(i)){
						minValues.set(i, value);
					}
				}
			}

			double newValue;
			for(int i=0; i<quantidadeCaracteristicas; i+=1){

				for (ArrayList<Double> temp : arrayImages) {
					value = temp.get(i);
					newValue = (b - a) * (value - minValues.get(i))/ (maxValues.get(i) - minValues.get(i)) + a;
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
		System.out.println("Total de imagens carregadas: "+ totalImages + " imagens." );
		int totalDescritores = 0;
		
		for (int count = 0; count < totalImages; count+=1){
			Node firstImageNode = listOfImages.item(count);
			if(firstImageNode.getNodeType() == Node.ELEMENT_NODE){

				Element firstImageElement = (Element)firstImageNode;

				Attr atributoTextura = firstImageElement.getAttributeNode("textura");
				String textura = atributoTextura.getNodeValue();
				arrayImagesLabels.add(Integer.parseInt(textura));
				
				NodeList descritoresList = firstImageElement.getElementsByTagName("descritor");
				totalDescritores = descritoresList.getLength();
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
		quantidadeCaracteristicas = totalDescritores;
	}
}
