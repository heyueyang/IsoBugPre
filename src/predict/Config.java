package predict;

public class Config{
	
	public static String total_folder = "E://dataset//binbin//";//"E://dataset//change4.0//";
	public static String result_folder = total_folder + "compare result//";
	//private static String data_folder = "E://dadaset//nasa-data//data//";
	//public static String data_folder = "E://dadaset//change//arff//";
	//public static String data_folder = total_folder + "com_net_other_arff//";//"com_net_arff_selected//CfsSu_BestF//"
	public static String select_folder = total_folder + "selected/";
	public static String sample_folder = total_folder + "sample/";
	public static String data_folder = "E://dataset//binbin//myselect//";
	

	private static String[] EvalS = {"ChiSquaredAttributeEval"};
	//,"ReliefFAttributeEval","OneRAttributeEval","GainRatioAttributeEval"
	//,"CostSensitiveAttributeEval"
	private static String[] RankS = {"Ranker"};
	static String[] SubEvalS = {"CfsSubsetEval"};//,"FilteredSubsetEval","WrapperSubsetEval","ClassifierSubsetEval","ConsistencySubsetEval","WrapperSubsetEval","CostSentitiveSubsetEval"
	//"ClassifierSubsetEval","ConsistencySubsetEval","CostSentitiveSubsetEval"}
	//private static String[] SearchS = {"BestFirst","ExhaustiveSearch","FCBFSearch","GeneticSearch","GreedyStepwise","LinearForwardSelection","RandomSearch",
    //		"RankSearch","ScatterSearchV1","SubsetSizeForwardSelection","TabuSearch"};
	static String[] SearchS = {"BestFirst"};//"LinearForwardSelection","GreedyStepwise","SubsetSizeForwardSelection","GeneticSearch","RaceSearch"
	public static String[] head = {"file","AttrNum","SamSize","NumTrees","Threshold","accuracy","gmean","recall-0","recall-1","precision-0","precision-1","fMeasure-0","fMeasure-1","balance_0","balance_1","AUC","AnomalyClas","time","Evaluation","Search"};
	public static String file="";

}