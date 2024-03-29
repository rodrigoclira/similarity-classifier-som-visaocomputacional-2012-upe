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
	private LearningAlgorithm learning;
	private double acuracyTax;
	
	public SOM getSom() {
		return som;
	}

	public void setSom(SOM som) {
		this.som = som;
	}

	public Classificator(ArrayList<Image> dataSet){
		ArrayList<double[]> dataSetValues = new ArrayList<double[]>();
		for (Image img : dataSet) {
			double[] values = new double[img.getFeatures().size()];
			for (int i = 0; i < values.length; i++) {
				values[i] = img.getFeatures().get(i).doubleValue();				
			}			
			dataSetValues.add(values);
		}
		
		this.som = new SOM(dataSetValues);
		this.learning = new LearningAlgorithm(this.som);
		
	}
	
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
			throw new Exception("N�o esxiste arquivo de SOM no caminho passado");
			
	}
	
	public void training(ArrayList<Image> dataBase, double trainingPercent) throws Exception{
		
		//ArrayList<Image> trainingSet = this.splitDataBase(dataBase, trainingPercent);
				
		//this.learning.training(trainingSet);
		this.learning.training(dataBase);
						
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
		double hitCount = 0.0;
		double classificationCount = 0.0;
		
		for (Image currentImage : classificatedImages) {
			dataBaseImagePos = this.som.findBestMatchingNode(currentImage.getFeatures());
			distance = this.som.calculateDistanceBetweenGradeNodes(activatedNeuronPos, dataBaseImagePos);			
			currentImage.setDistanceBySearchImage(distance);
			
			if(searchImage.getLabel() == currentImage.getLabel())
			{
				if(distance == 0.0)
					hitCount++;
			}
			else{
				if(distance > 0)
					hitCount++;
			}
						
			classificationCount++;
		}
		
		Collections.sort(classificatedImages);
		
		this.acuracyTax = (hitCount/classificationCount);
		
		return classificatedImages;
	}
	
	public double getAcuracyTax(){
		return acuracyTax;
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
