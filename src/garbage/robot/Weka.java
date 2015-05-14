package garbage.robot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
	
	public String predictItem(String params)
	{
		return "Stain";
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
		writer.write(tree.graph());		
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

