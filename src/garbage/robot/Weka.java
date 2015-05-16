package garbage.robot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import weka.classifiers.trees.J48;
import weka.core.Instances;


public class Weka {
	
	private J48 tree;
	private Instances train;
	private Instances test;
	private Instances predicted;
	
	public Weka(String trainFile, String testFile) throws Exception
	{
		BufferedReader reader = null;		
		reader = new BufferedReader(new FileReader(trainFile));
		train = new Instances(reader);
		train.setClassIndex(train.numAttributes() - 1);
		
		reader = new BufferedReader(new FileReader(testFile));
		test = new Instances(reader);
		test.setClassIndex(test.numAttributes() - 1);		
		reader.close();
		
		tree = new J48();
		tree.buildClassifier(train);
		
		predicted = new Instances(test);		
		for(int i = 0; i < test.numInstances(); i++)
		{
			double clsLabel = tree.classifyInstance(test.instance(i));
			predicted.instance(i).setClassValue(clsLabel);
		}	
	}
	
	public Weka(String trainFile) throws Exception
	{
		BufferedReader reader = null;		
		reader = new BufferedReader(new FileReader(trainFile));
		train = new Instances(reader);
		train.setClassIndex(train.numAttributes() - 1);
		reader.close();
		
		tree = new J48();
		tree.buildClassifier(train);
		
		test = null;
		predicted = null;
	}
	
	public String predictItem(Stain stain, String path) throws Exception
	{
		String itemClass = "";
		String fileContent = 
				"@relation rubbish-predict\n"
				+ "@attribute wetness numeric\n"
				+ "@attribute colorIntensity numeric\n"
				+ "@attribute smellIntensity numeric\n"
				+ "@attribute isSticky {true,false}\n"
				+ "@attribute isGreasy {true,false}\n"
				+ "@attribute roughness numeric\n"
				+ "@attribute dangerousBacteries numeric\n"
				+ "@attribute isFruity {true,false}\n"
				+ "@attribute density numeric\n"
				+ "@attribute class {wine,water,coffee,ink,cake,sauce,dressing,oil,blood,lubricant,mud,grass,glue,sand,dust,paste,glass,paper,rot,fungus}\n"
				+ "\n"
				+ "@data\n";
		
		String line = fileContent
				+ stain.getWetness() + "," + stain.getColorIntensity() + "," + stain.getSmellIntensity() + "," + stain.isSticky() + "," + stain.isGreasy() 
				+ "," + stain.getRoughness() + "," + stain.getDangerousBacteries() + "," + stain.isFruity() + "," + stain.getDensity() + ",?";
		
		BufferedWriter writer = null;	
		writer = new BufferedWriter(new FileWriter(path));
		writer.write(line);
		writer.close();
		
		BufferedReader reader = new BufferedReader(new FileReader(path));
		Instances item = new Instances(reader);
		item.setClassIndex(item.numAttributes() - 1);		
		double clsLabel = tree.classifyInstance(item.instance(0));
		item.instance(0).setClassValue(clsLabel);
		reader.close();
		
		File file = new File(path);
		file.delete();
		
		String itemLine = item.instance(0).toString();
		if(itemLine.contains("wine")) itemClass = "wine";
		else if(itemLine.contains("water")) itemClass = "water";
		else if(itemLine.contains("coffee")) itemClass = "coffee";
		else if(itemLine.contains("ink")) itemClass = "ink";
		else if(itemLine.contains("cake")) itemClass = "cake";
		else if(itemLine.contains("sauce")) itemClass = "sauce";
		else if(itemLine.contains("dressing")) itemClass = "dressing";
		else if(itemLine.contains("oil")) itemClass = "oil";
		else if(itemLine.contains("blood")) itemClass = "blood";
		else if(itemLine.contains("lubricant")) itemClass = "lubricant";
		else if(itemLine.contains("mud")) itemClass = "mud";
		else if(itemLine.contains("grass")) itemClass = "grass";
		else if(itemLine.contains("glue")) itemClass = "glue";
		else if(itemLine.contains("sand")) itemClass = "sand";
		else if(itemLine.contains("dust")) itemClass = "dust";
		else if(itemLine.contains("paste")) itemClass = "paste";
		else if(itemLine.contains("glass")) itemClass = "glass";
		else if(itemLine.contains("paper")) itemClass = "paper";
		else if(itemLine.contains("rot")) itemClass = "rot";
		else if(itemLine.contains("fungus")) itemClass = "fungus";
		
		return itemClass;
	}
	
	public String predictDetergent(Stain stain, String path) throws Exception
	{
		String itemClass = "";
		String fileContent = 
				"@relation cleaner\n"
				+ "@attribute rubbish {blood, cake, coffee, dressing, dust, fungus, glass, glue, grass, ink, lubricant, mud, oil, paper, paste, rot, sand, sauce, water, wine}\n"
				+ "@attribute age numeric\n"
				+ "@attribute base {floor, wood, carpet, carpetCotton}\n"
				+ "@attribute class {airFreshener,alcohol,bakingSoda,cleaningMilk,dishSoap,floorSoap,fumigator,nothing,paste,soap,solvent,spray,vanish,vinegar,washingPowder,water}\n"
				+ "\n"
				+ "@data\n";
		
		String line = fileContent + stain.getType() + "," + stain.getAge() + "," + stain.getBase() + ",?";
		
		BufferedWriter writer = null;	
		writer = new BufferedWriter(new FileWriter(path));
		writer.write(line);
		writer.close();
		
		BufferedReader reader = new BufferedReader(new FileReader(path));
		Instances item = new Instances(reader);
		item.setClassIndex(item.numAttributes() - 1);		
		double clsLabel = tree.classifyInstance(item.instance(0));
		item.instance(0).setClassValue(clsLabel);
		reader.close();
		
		File file = new File(path);
		file.delete();
		
		String itemLine = item.instance(0).toString();
		if(itemLine.contains("airFreshener")) itemClass = "airFreshener";
		else if(itemLine.contains("alcohol")) itemClass = "alcohol";
		else if(itemLine.contains("bakingSoda")) itemClass = "bakingSoda";
		else if(itemLine.contains("cleaningMilk")) itemClass = "cleaningMilk";
		else if(itemLine.contains("dishSoap")) itemClass = "dishSoap";
		else if(itemLine.contains("floorSoap")) itemClass = "floorSoap";
		else if(itemLine.contains("fumigator")) itemClass = "fumigator";
		else if(itemLine.contains("nothing")) itemClass = "nothing";
		else if(itemLine.contains("paste")) itemClass = "paste";
		else if(itemLine.contains("soap")) itemClass = "soap";
		else if(itemLine.contains("solvent")) itemClass = "solvent";
		else if(itemLine.contains("spray")) itemClass = "spray";
		else if(itemLine.contains("vanish")) itemClass = "vanish";
		else if(itemLine.contains("vinegar")) itemClass = "vinegar";
		else if(itemLine.contains("washingPowder")) itemClass = "washingPowder";
		else if(itemLine.contains("water")) itemClass = "water";
		
		return itemClass;
	}
	
	public String predictEquipment(Stain stain, String path) throws Exception
	{
		String itemClass = "";
		String fileContent = 
				"@relation equipment\n"
				+ "@attribute detergent {airFreshener,alcohol,bakingSoda,cleaningMilk,dishSoap,floorSoap,fumigator,nothing,paste,soap,solvent,spray,vanish,vinegar,washingPowder,water}\n"
				+ "@attribute isTall {true, false}\n"
				+ "@attribute size numeric\n"
				+ "@attribute class {besom,brush,grasper,pressureWasher,rubber,sandpaper,scraper,sponge,tweezer,vacuumCleaner}\n"
				+ "\n"
				+ "@data\n";
		
		String line = fileContent + stain.getDetergent() + "," + stain.isTall() + "," + stain.getSize() + ",?";
		
		BufferedWriter writer = null;	
		writer = new BufferedWriter(new FileWriter(path));
		writer.write(line);
		writer.close();
		
		BufferedReader reader = new BufferedReader(new FileReader(path));
		Instances item = new Instances(reader);
		item.setClassIndex(item.numAttributes() - 1);		
		double clsLabel = tree.classifyInstance(item.instance(0));
		item.instance(0).setClassValue(clsLabel);
		reader.close();
		
		File file = new File(path);
		file.delete();
		
		String itemLine = item.instance(0).toString();
		if(itemLine.contains("besom")) itemClass = "besom";
		else if(itemLine.contains("brush")) itemClass = "brush";
		else if(itemLine.contains("grasper")) itemClass = "grasper";
		else if(itemLine.contains("pressureWasher")) itemClass = "pressureWasher";
		else if(itemLine.contains("rubber")) itemClass = "rubber";
		else if(itemLine.contains("sandpaper")) itemClass = "sandpaper";
		else if(itemLine.contains("scraper")) itemClass = "scraper";
		else if(itemLine.contains("sponge")) itemClass = "sponge";
		else if(itemLine.contains("tweezer")) itemClass = "tweezer";
		else if(itemLine.contains("vacuumCleaner")) itemClass = "vacuumCleaner";
		
		return itemClass;
	}
	
	public void writePredictions(String predictions) throws Exception
	{	
		if(predicted != null)
		{
			BufferedWriter writer = null;	
			writer = new BufferedWriter(new FileWriter(predictions));
			writer.write(predicted.toString());
			writer.close();
		}
		else 
		{
			System.out.println("No test data to make predictions");
		}
	}
	
	public void writeTree(String treeGraph) throws IOException, Exception
	{
		BufferedWriter writer = null;		
		writer = new BufferedWriter(new FileWriter(treeGraph));
		writer.write(tree.toString()  + "\n\n" + tree.graph());		
		writer.close();
	}
	
	public J48 getTree() {
		return tree;
	}

	public void setTree(J48 tree) {
		this.tree = tree;
	}

	public Instances getTrain() {
		return train;
	}

	public void setTrain(Instances train) {
		this.train = train;
	}

	public Instances getTest() {
		return test;
	}

	public void setTest(Instances test) {
		this.test = test;
	}

	public Instances getPredicted() {
		return predicted;
	}

	public void setPredicted(Instances predicted) {
		this.predicted = predicted;
	}



}

