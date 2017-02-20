package predict;

import java.io.File;
import classify.base.*;

import java.util.ArrayList;
import java.util.Random;
import classify.base.OtherEvaluation;

import weka.classifiers.Evaluation;
import weka.core.Instances;

/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
public class ParaAnalyze {

	/**
	 * @param args
	 */
	public static String[] arr = {"thres", "numTrees", "sam"};
	public static String total_folder = "E://dataset//binbin//";
	public static String result_folder = total_folder + "para_analyze//";
	public static String data_folder = total_folder + "myselect//";
	public static String[] head = {"file","AttrNum","sam","numTrees","thres","accuracy","gmean","recall-0","recall-1","precision-0","precision-1","fMeasure-0","fMeasure-1","balance_0","balance_1","AUC","AnomalyClas","time","Evaluation","Search"};
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		long start_time = System.currentTimeMillis();
		File ff = new File(data_folder);//select_folder
    	//File ff = new File(select_folder);//
    	File[] files = ff.listFiles();
    	for(int k = 0; k < 3; k++){
    		String dir = result_folder + arr[k] + "//";
    		File file = new File(dir);
    		if(!file.exists()){
    			file.mkdirs();
    		}
	    	for(int i = 0;i < files.length; i++){
	    		Instances data = FileUtil.ReadData(files[i].getAbsolutePath());
	    		//String 	out_path = result_folder + files[i].getName() + "_Para" + ".csv";
	    		String 	out_path = dir + arr[k] + "SubFigure_" + files[i].getName().substring(2, files[i].getName().indexOf("_")) + ".csv";
	    		try {
					excute(new MyIsolationForest(), data,out_path, k+1);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		
	    	}
    	}
		long end_time = System.currentTimeMillis();
	 	System.out.println("======Time Used:" + (end_time - start_time)/1000 + "s======");

	}
	
	
	public static void excute(MyIsolationForest iso, Instances ins, String outPath, int flag) throws Exception{
	 	/*return 0.5;*/

  	   	int cnt = 50; 
  	   	String[][] result = new String[cnt][17];   	
	    int numFolds = 10;
	    double thres = 0.5;
	    double samRatio = 0.03;
	    int numTrees = 40;
	    Instances data0 = ins;    
	    //data = new LogPre().run(data,0.5);
	   	ArrayList<double[]> predict = new ArrayList<double[]>();
	   	String path = Config.result_folder;
	   	File fold = new File(path);
	    data0.setClassIndex(data0.numAttributes()-1);
	   	if(! fold.exists()) fold.mkdirs();
  	
	    if (data0.classAttribute().isNominal()) {    
	          data0.stratify(numFolds);    
	    } 
	     int anomaly = FileUtil.anomalyIndex(ins);      
	    data0.randomize(new Random());
	    
	    iso.setNumTrees(40);
	    iso.setSubsampleSize((int)(0.03*ins.numInstances()));
	    //iso.setAjust(0.5);
		for(int i = 0; i < cnt; i++){
			
			//ClassifyWithIsolation classifier = new ClassifyWithIsolation(data0);
			//flag=1,计算阈值的变化对结果的影响
			if(flag == 1){
				iso.setSubsampleSize(20);
				thres = 0.0 + (i+1)*0.02;
				//thres = 0.5;
				iso.setAjust(thres);
				//classifier.setThreshold(thres);
			}else if(flag == 2){//flag=2,计算树的数量对结果的影响
				numTrees = 0 + (i+1)*10;
				iso.setNumTrees(numTrees);
			}else if(flag == 3){//flag=3,计算抽样率对结果的影响
				samRatio = 0 + (i+1)*0.02;
				iso.setSubsampleSize((int)(samRatio*ins.numInstances()));
			}
			
			//Result eval = classifier.crossValidateModel(iso,data0, 10);
		 	//OtherEvaluation eval = new OtherEvaluation(data0,0);
			Evaluation eval = new Evaluation(data0);
			eval.crossValidateModel(iso, data0, 10, new Random());

			result[i][0] = "0";
	        result[i][1] = String.valueOf(ins.numAttributes());
	        result[i][2] = String.valueOf(samRatio);
	        result[i][3] = String.valueOf(iso.getNumTrees());
	        result[i][4] = String.valueOf(iso.getAjust());
	        result[i][5] = String.valueOf(1 - eval.errorRate());
	        result[i][6] = String.valueOf(Math.sqrt(eval.recall(0)*eval.recall(1)));
	        result[i][7] = String.valueOf(eval.recall(0));
	        result[i][8] = String.valueOf(eval.recall(1));
	        result[i][9] = String.valueOf(eval.precision(0));
	        result[i][10] = String.valueOf(eval.precision(1));
	        result[i][11] = String.valueOf(eval.fMeasure(0));
	        result[i][12] = String.valueOf(eval.fMeasure(1));
	        result[i][13] = String.valueOf(0);
	        result[i][14] = String.valueOf(0);
	        result[i][15] = String.valueOf(eval.areaUnderROC(1));
	        result[i][16] = String.valueOf(anomaly);
		    			            			          	            
	     }
		FileUtil.outCSVFile(outPath, result, head);
	}
	
  

}
