package predict;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.ThresholdCurve;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

public class RF{

	private static String result_folder = "C:/Users/Administrator/Workspaces/weka-test/rf result/";
	private static String result_path = "C:/Users/Administrator/Workspaces/weka-test/rf result/";

	private static String data_folder = "E://dadaset//new data//selected//";
	private static String data_path = data_folder+"ant.arff";//"E://dadaset//arff//ant1.arff";
	private static String select_folder = data_folder+"antSelected/";
	private static String[] head = {"time","AttrNum","MaxDepth","NumTrees","accuracy","sensitive","pecificity","AUC"};
	
	public static void main(String[] args) throws Exception{
		long start = System.currentTimeMillis();
		long end = System.currentTimeMillis();
		SimpleDateFormat dateFor = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = null;
		start = System.currentTimeMillis();
		date =   dateFor.format(new Date(start));
    	System.out.println("============Start Time:"+date+"============");
    	
		String c = "trees.RandomForest";
    	//String c = "functions.LibSVM";

		Instances trainIns = null;
		Instances newIns = null;
		int attrNum = 0;
		double acc = 0,sen = 0,pre = 0, auc = 0;
		int idx = 1;
		
		
		Class<?> c1 = Class.forName("weka.classifiers."+c);
		//Classifier clas = (Classifier)c1.newInstance();
		RandomForest clas = new  RandomForest();
		int flag0 = 0;
		
	    
	    File f = new File(result_path);
    	if(!f.exists()) f.mkdirs();
    	//trianAndEval(select_folder+"antSelectedFilte_Linea.arff","C:/Users/Administrator/Workspaces/weka-test/result/Various.xls");
    	File ff = new File(data_folder);
    	String[] files = ff.list();
    	for(int i = 0;i < files.length; i++){
    		System.out.println("=========File:"+files[i]+"=======");
    		data_path = data_folder + files[i];
    		String outPath = result_folder + files[i] + ".xls";
    		/*
    		 * */
    		try{
				File f0 = new File(data_path);
				ArffLoader loader0 = new ArffLoader();
				loader0.setFile(f0);
				trainIns = loader0.getDataSet();
				newIns = trainIns;
		        attrNum = newIns.numAttributes()-1;
		     
		       
		        System.out.println("=========Data Information=========");
		        System.out.println("======AttrNum:"+attrNum+"======");
		        System.out.println("======InstancesNum:"+trainIns.numInstances()+"======");
		        //set the classIndex
		        trainIns.setClassIndex(trainIns.numAttributes()-1);

		    	} catch (Exception e) { 
		    		flag0 = 1;
		    		System.out.println(e.toString());
				
		    	}
		
			int cnt = (int) ((trainIns.numInstances()/10)*0.9);
		    String[][] result = new String[8][cnt];
		    
    	    for(int i1=0;i1 < 20;i1++){
            	System.out.println("======i1======");
            	int numFolds = 10;
            	Instances data = newIns;    
                data.randomize(new Random());    
                if (data.classAttribute().isNominal()) {    
                      data.stratify(numFolds);    
                 } 
                Result tempRes = null;
                
                //clas.setMaxDepth((i+1)*1);
                clas.setNumTrees((i1+1)*10);
                //clas.setNumFeatures((i)*10+20);
      
            	
        		Evaluation e1 = new Evaluation(trainIns);
                e1.crossValidateModel(clas, trainIns, 10, new Random(1));
                auc = e1.areaUnderROC(idx);
                e1.confusionMatrix();
                sen = e1.truePositiveRate(idx);
                acc = e1.correct()/newIns.numInstances();
                pre = e1.trueNegativeRate(idx);
        		ThresholdCurve tc = new ThresholdCurve();
        		
        		File f1 = new File(result_folder);
        		if(!f1.exists())  f1.mkdirs();
    			//String out = result_path + files[i] + ".xls";
            	//String[] head0 = {"score","label","prediction"} ;
            	//Test.exportObjFile(e1.predictions().toArray(),out,head0);
                
            	//System.out.println(isoF.distributionForInstance(trainIns.instance(i))[0]+"    "+isoF.distributionForInstance(trainIns.instance(i))[1]);
            	//System.out.println(isoF.classifyInstance(trainIns.instance(i))[0]);
                //evaludate
                //double[][] score = evaluate(testIns,isoF);
                end = System.currentTimeMillis();
                double time = end-start;
                String ti = (end-start)/(60*1000)+"mins"+" "+((end-start)%(60*1000))/1000+"s";
      
                
                result[0][i1] = ti;
                result[1][i1] = String.valueOf(newIns.numAttributes());
                result[2][i1] = String.valueOf(clas.getMaxDepth());
                result[3][i1] = String.valueOf(clas.getNumFeatures());
                result[4][i1] = String.valueOf(acc);
                result[5][i1] = String.valueOf(sen);
                result[6][i1] = String.valueOf(pre);
                result[7][i1] = String.valueOf(auc);
                
                
                
                //finalRes.out(i);
                System.out.println("======Time Used:"+ti+"======");
                
            	//isoF.setNumTrees(num);
    	    	acc = 0;
               	sen = 0;
               	pre = 0;
               	
            }
            	
            	if(flag0 == 0) {
            		FileUtil.outFile(outPath, result, head);
    	        	System.out.println("======File Writed======");
    	        }
    	
    		}
	    
    
	}
}