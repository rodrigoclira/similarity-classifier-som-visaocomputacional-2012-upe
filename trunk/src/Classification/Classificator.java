package Classification;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import SOM.LearningAlgorithm;
import SOM.SOM;

public class Classificator {

	private SOM som;
	public SOM getSom() {
		return som;
	}

	public void setSom(SOM som) {
		this.som = som;
	}

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
	
	public void training(ArrayList<Image> dataBase, double trainingPercent) throws Exception{
		
		ArrayList<Image> trainingSet = this.splitDataBase(dataBase, trainingPercent);
				
		this.learning.training(trainingSet);
						
	}
	
	private ArrayList<Image> splitDataBase(ArrayList<Image> dataBase,
			double trainingPerCentSamples) {
		//hardcoded
		ArrayList<Image> ret = new ArrayList<Image>();
		int qtd = 0, min=0, max=16;
		
		
		List<Image> aux =null;
		while(max <= dataBase.size()) {
			
			 aux = dataBase.subList(min, max);
			
			 Collections.shuffle(aux);
			 qtd = (int) (aux.size()*trainingPerCentSamples);			 
			 ret.addAll(aux.subList(0, qtd));
			 
			 min+=16;
			 max+=16;
		}
		
		return ret;
		
	
	}

	public ArrayList<Image> classify(Image searchImage , ArrayList<Image> images){
		
		ArrayList<Image> classificatedImages = (ArrayList<Image>)images.clone();
		int activatedNeuronPos = this.som.findBestMatchingNode(searchImage.getFeatures());
		int dataBaseImagePos = 0;
		double distance = 0.0;
		
		for (Image currentImage : classificatedImages) {
			dataBaseImagePos = this.som.findBestMatchingNode(currentImage.getFeatures());
			distance = this.som.calculateDistanceBetweenGradeNodes(activatedNeuronPos, dataBaseImagePos);			
			currentImage.setDistanceBySearchImage(distance);
			
			currentImage.setNodeActivatedX(this.som.getSomNode(dataBaseImagePos).getXGrade());
			currentImage.setNodeActivatedY(this.som.getSomNode(dataBaseImagePos).getYGrade());
		}
		
		Collections.sort(classificatedImages);
		
		return classificatedImages;
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
	
	public int testClassificator(Image teste){
		return this.som.findBestMatchingNode(teste.getFeatures());
	}
	
	
	
	
	
}
