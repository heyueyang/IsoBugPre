package compare;

import java.io.File;
import java.util.Arrays;
import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

public class Test2 {

	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		// TODO Auto-generated method stub
		Instances ins = null;

	       Classifier cfs = null;

	       try {

	           //����ѵ����������

	           File file = new File("E://dataset//change7.0//com_arff//ant.arff");

	           ArffLoader loader = new ArffLoader();

	           loader.setFile(file);

	           ins = loader.getDataSet();

	           ins.setClassIndex(ins.numAttributes()-1);

	           //��ʼ��������

	           cfs = (Classifier)Class.forName("weka.classifiers.bayes.NaiveBayes").newInstance();

	           Instance testInst;

	           Evaluation testingEvaluation = new Evaluation(ins);
	           
	           int length = 10;
	           Random rand= new Random(1);

	           for(int i = 0; i < length ; i++){

	              //testInst = ins.instance(i);
	        	  Instances train = ins.trainCV(length, i, rand);  
				  Instances test = ins.testCV(length, i);
				  //ʹ��ѵ���������з���
		           cfs.buildClassifier(train);
		          //ʹ�ò����������Է�������ѧϰЧ��
	              //testingEvaluation.evaluateModelOnceAndRecordPrediction(cfs, test);
	              
	              System.out.println("�������ȷ��"+(1-testingEvaluation.errorRate()));

	           }

	           //��ӡ������

	           

	       } catch (Exception e) {

	           e.printStackTrace();

	       }
	}

}
