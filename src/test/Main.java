package test;
import java.io.File;
import java.io.IOException;

import weka.core.Instances;
import weka.core.converters.ArffLoader;

public class Main {

	public static void main(String[] args) throws IOException {
		ArffLoader loader = new ArffLoader();
		loader.setFile(new File("E:\\dataset\\change3.0\\com_arff\\eclipse.arff"));
		Instances ins = loader.getDataSet();
		ins.setClassIndex(ins.numAttributes() - 1);
		System.out.println(ins.instance(0).classValue());

	}

}