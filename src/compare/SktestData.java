package compare;
import predict.Config;
import classify.base.*;
import predict.FileUtil;
import predict.Result;
import preprocess.Sample;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import classify.base.NewIsolationForest;
import classify.base.OtherBagging;
import classify.base.OtherEvaluation;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.Bagging;
import weka.core.Instances;

public class SktestData{
		static String[] c = {"weka.classifiers.bayes.NaiveBayes",
			"weka.classifiers.trees.J48",
			"weka.classifiers.functions.SMO"};
		static String[] cc = {"N", "J", "S"};

			//,"weka.classifiers.trees.MyIsolationForest"//"classify.base.NewIsolationForest"};
		static String m_class = "change_prone";//"bug_introducing";//"bug_introducingCopy";//
		
		public static void main(String[] args) throws Exception {
			long start_time = System.currentTimeMillis();
			File ff = new File(Config.data_folder);//select_folder
	    	//File ff = new File(select_folder);//
	    	File[] files = ff.listFiles();
	    	
	    	if(!new File(Config.result_folder).exists()){
	    		new File(Config.result_folder).mkdirs();
	    	}
	    	if(!ff.exists()){
	    		System.out.println("======Directory:" + Config.data_folder + " doesn't exists======");
	    	}else{
		    	for(int i = 0;i < files.length; i++){
	
		    		run(Config.result_folder,i,files[i].getAbsolutePath());
		    		
		    	}
	    	}
			long end_time = System.currentTimeMillis();
		 	System.out.println("======Time Used:" + (end_time - start_time)/1000 + "s======");
		}
		public static void run(String resultFolder, int sheet,String project) throws Exception{

			
			
			String tempPath  = Config.data_folder.substring(Config.total_folder.length());
			String out = resultFolder + tempPath.substring(0,tempPath.indexOf("//")) + "_SKresult"+".xls";
			System.out.println("Output Path:" + out);
	    	String[] head = {"classifier","bag","accuracy","gmean","recall-0","recall-1","precision-0","precision-1","fMeasure-0","fMeasure-1","AUC","time"};	    	
			String[] methods = {"Simple","Bagging","Undersample","UnderBag", "Oversample",	"OverBag", "SMOTE", "SMOTEBag"};//,"SMOTELog","OverLog","UnderLog"
			String[] mm = {"S","B","U","UB", "O",	"OB", "M", "MB"};
			int cnt = c.length;
			
			

			OtherEvaluation eval = null;
			Classifier clas = null;
			
			int m =0;
			int classIndex = 0;
			int run_times = 10;
			String[][] result = new String[cnt*methods.length*run_times][12];
			
			//inputFile = Config.select_folder + project;
			System.out.println(project);
			Instances data = FileUtil.ReadData(project);
			
			classIndex = data.numAttributes()-1;
			data.setClassIndex(classIndex);
			
			
			for(int i = 0;i < cnt; i++){//方法个数
				int sampleCnt = methods.length;
				//System.out.println("Classifier:" + c[i]);				
				clas = (weka.classifiers.Classifier) Class.forName(c[i]).newInstance();
				for(int j = 0;j < sampleCnt;j++){//抽样个数
					
					for(int t = 0;t < run_times; t++){
						Random rand = new Random(t);
						data.randomize(rand);	
						switch(j){
							case 0:{//Simple
								
								eval = new OtherEvaluation(data,0);
								eval.crossValidateModel(clas, data, 10, rand);

								break;
							
							}
							case 1:{//Simple Bagging

								OtherBagging c2 = new OtherBagging(clas, m_class, 0);
								c2.setClassifier(clas);

								eval = new OtherEvaluation(data,0);
								eval.crossValidateModel(c2, data, 10, rand);

								
								break;
							}
							case 2:{//Undersample

								eval = new OtherEvaluation(data,1);
								eval.crossValidateModel(clas, data, 10, rand);
								break;
							}
							case 3:{//Undersample and Bagging

								OtherBagging c2 = null;
								c2 = new OtherBagging(clas,m_class,1);
								c2.setClassifier((weka.classifiers.Classifier) clas);
								eval = new OtherEvaluation(data,0);
								eval.crossValidateModel(c2, data, 10, rand);

								break;
							}
							case 4:{//Oversample

								eval = new OtherEvaluation(data,2);
								eval.crossValidateModel(clas, data, 10, rand);

								break;
							}
							case 5:{//Oversample and Bagging

								OtherBagging c2 = null;
								c2 = new OtherBagging(clas, m_class,2);

								c2.setClassifier((weka.classifiers.Classifier) clas);
								eval = new OtherEvaluation(data,0);
								eval.crossValidateModel(c2, data, 10, rand);

								break;
							}
							case 6:{//SMOTE
								eval = new OtherEvaluation(data,5);
								eval.crossValidateModel(clas, data, 10, rand);
								break;
							}
							case 7:{//SMOTE and Bagging
								OtherBagging c2 = new OtherBagging(clas, m_class,5);
								c2.setClassifier((weka.classifiers.Classifier) clas);
								eval = new OtherEvaluation(data,0);
								eval.crossValidateModel(c2, data, 10, rand);
								break;
							}
						
						}
						
						
						result[m][0] = project.substring(project.lastIndexOf("\\")+1,project.lastIndexOf("."));
						result[m][1] = cc[i]+mm[j];
						result[m][2] = String.valueOf(1-eval.errorRate());
				        result[m][3] = String.valueOf(Math.sqrt(eval.recall(0)*eval.recall(1)));
				        result[m][4] = String.valueOf(eval.recall(0));
				        result[m][5] = String.valueOf(eval.recall(1));
				        result[m][6] = String.valueOf(eval.precision(0));
				        result[m][7] = String.valueOf(eval.precision(1));
				        result[m][8] = String.valueOf(eval.fMeasure(0));
				        result[m][9] = String.valueOf(eval.fMeasure(1));
				        result[m][10] = String.valueOf(eval.areaUnderROC(0));	
				        System.out.println(result[m][1]+"-"+t);
				        m++;
				        
				    }

				}
			}
		    FileUtil.exportFile(result, out, project, sheet, head);
		}

		public static double findThreshold(MyIsolationForest iso,Instances ins) throws Exception{
		 	/*return 0.5;*/
		 	
	  	   	int cnt =21; 
	  	   	String[][] result = new String[cnt][17];   	
		    int numFolds = 10;
		    double bestThres = 0.0;
		    double thres = 0;
		    double gmean = 0.0, balance = 0.0, buggy_presicion = 0.0, fmeasure = 0.0;
		    Instances data = ins;    
		    //data = new LogPre().run(data,0.5);
		   	ArrayList<double[]> predict = new ArrayList<double[]>();
		   	String path = Config.result_folder + Config.file +"/temp/";
		   	File fold = new File(path);
		    data.setClassIndex(data.numAttributes()-1);
		   	if(! fold.exists()) fold.mkdirs();
		   	String out_path = null;
	  	
		    if (data.classAttribute().isNominal()) {    
		          data.stratify(numFolds);    
		    } 
		    int m_anomaly = FileUtil.anomalyIndex(data);        
		    data.randomize(new Random());
			for(int i = 0; i < cnt; i++){
				thres = 0.4 + i*0.01;
				iso.setAjust(thres);
				iso.setSubsampleSize(20);
			 	//OtherEvaluation eval = new OtherEvaluation(data,0);
			 	Evaluation eval = new Evaluation(data);
				eval.crossValidateModel(iso, data, 10, new Random());
				
				 out_path = path + "findThres" + ".xls";
				 double extra = eval.recall(0)*eval.recall(1)*eval.precision(0)*eval.precision(1);
				 if(extra != 0 && eval.fMeasure(m_anomaly) > fmeasure){// tempRes.getGmean() > gmean,tempRes.getGmean() > gmean    tempRes.getPrecision()[anomaly] > buggy_presicion tempRes.getBalance()[anomaly] > balance
					 gmean = Math.sqrt(eval.recall(0)*eval.recall(1));
					 //balance = tempRes.getBalance()[anomaly];
					 fmeasure = eval.fMeasure(m_anomaly);
					 buggy_presicion = eval.precision(m_anomaly);
					 bestThres = thres;
					 System.out.print(fmeasure + "  ");
					
				 }
			    			            			          	            
		     }
			FileUtil.outFile(out_path, result, Config.head);
		    System.out.println("-------> bestThres : " + bestThres);
			return bestThres;
		}

		
	}
	