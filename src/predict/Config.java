package predict;

import java.io.File;
import java.io.IOException;

import weka.core.Instances;
import weka.core.converters.ArffSaver;

public class Config{
	
	public static String total_folder = "E://dataset//change2.0//";
	public static String result_folder = total_folder + "compare result//";//"F:/change-prone/result/";
	//private static String data_folder = "E://dadaset//nasa-data//data//";
	//public static String data_folder = "E://dadaset//change//arff//";
	public static String data_folder = total_folder + "com_net_bow_arff_selected//";
	public static String select_folder = total_folder + "selected/";
	public static String sample_folder = total_folder + "sample/";
	//public static String data_folder = "E://dadaset//new data//binbin//";
	
	//private static String select_folder = "E://dadaset//nasa-data//"+"selected/";

	//public static String select_folder = "E://dadaset//change//selected//";
	
	
	private static String data_path = "";

	private static String[] EvalS = {"ChiSquaredAttributeEval"};
	//,"ReliefFAttributeEval","OneRAttributeEval","GainRatioAttributeEval"
	//,"CostSensitiveAttributeEval"
	private static String[] RankS = {"Ranker"};
	static String[] SubEvalS = {"CfsSubsetEval","FilteredSubsetEval","WrapperSubsetEval","ClassifierSubsetEval","ConsistencySubsetEval","WrapperSubsetEval","CostSentitiveSubsetEval"};//
	//"ClassifierSubsetEval","ConsistencySubsetEval","CostSentitiveSubsetEval"}
	//private static String[] SearchS = {"BestFirst","ExhaustiveSearch","FCBFSearch","GeneticSearch","GreedyStepwise","LinearForwardSelection","RandomSearch",
    //		"RankSearch","ScatterSearchV1","SubsetSizeForwardSelection","TabuSearch"};
	static String[] SearchS = {"LinearForwardSelection","BestFirst"};//,"GreedyStepwise","SubsetSizeForwardSelection","BestFirst""GeneticSearch",,"RaceSearch","BestFirst","GreedyStepwise"
	public static String[] head = {"file","AttrNum","SamSize","NumTrees","Threshold","accuracy","gmean","recall-0","recall-1","precision-0","precision-1","fMeasure-0","fMeasure-1","balance_0","balance_1","AUC","AnomalyClas","time","Evaluation","Search"};
	public static String file="";
	
	/*public static double[] getInsDistribution(Instances ins){
		double[] res = new double[5];
		int valueCnt0[] = {0,0};
		int temp = -1;
		for(int i = 0 ; i< ins.numInstances(); i++){
			temp = (int) ins.instance(i).classValue();
    		valueCnt0[temp]++;
		}
		res[0] = (valueCnt0[0] > valueCnt0[1]) ? 1 : 0;
		res[1] = (valueCnt0[0] > valueCnt0[1]) ? valueCnt0[1] : valueCnt0[0];
		res[2] = res[1]/ins.numInstances();
		res[3] = valueCnt0[0];
		res[4] = valueCnt0[1];
    	
		return res;
		
	}*/

}