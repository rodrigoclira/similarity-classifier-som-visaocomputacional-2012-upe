package Classification;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import SOM.LearningAlgorithm;
import SOM.SOM;

public class Classificator {

	private SOM som;
	private LearningAlgorithm learning;
	
	
	public Classificator(){
		this.som = new SOM();
		this.learning = new LearningAlgorithm(this.som);
	}
	
	public Classificator(String path) throws Exception{
		
		ObjectInputStream in = null;
		File file = new File(path);		
		
		if(file.exists()){
			try{
				in = new ObjectInputStream(new FileInputStream(file));
				this.som = (SOM) in.readObject();
				this.learning = new LearningAlgorithm(this.som);
				in.close();
			}catch (Exception e) {
				throw new Exception("Erro ao tenatar ler arquivo da rede serializada\n JavaError: " + e.getMessage());
			}		
		}else
			throw new Exception("Não esxiste arquivo de SOM no caminho passado");
			
	}
	
	public void training(){
		//TODO implementar lógica de treinamento completo da rede
	}
	
	public ArrayList<Image> classify(Image searchImage , ArrayList<Image> images){
		
		int activatedNeuronPos = this.som.findBestMatchingNode(searchImage.getFeatures());
		int dataBaseImagePos = 0;
		double distance = 0.0;
		
		for (Image currentImage : images) {
			dataBaseImagePos = this.som.findBestMatchingNode(currentImage.getFeatures());
			distance = this.som.calculateDistanceBetweenGradeNodes(activatedNeuronPos, dataBaseImagePos);
			currentImage.setDistanceBySearchImage(distance);
		}
		
		Collections.sort(images);
		
		return images;
	}
	
	public void saveSOM(String path, String fileName) throws Exception{
		ObjectOutputStream out = null;
		String fullFileName = path + File.separator + fileName; 
		File file = new File(fullFileName);				
		
		try{
			out = new ObjectOutputStream(new FileOutputStream(file));
			out.writeObject(this.som);						
			out.close();
		}catch (Exception e) {
			throw new Exception("Erro ao tenatar escrever o arquivo da rede serializada\n JavaError: " + e.getMessage());
		}	
	}
	
	
	
	
	
}
