package compare;

import java.io.*;
import java.nio.channels.SelectableChannel;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import predict.AttrSelect;
import predict.Config;
import predict.FileUtil;
import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.AttributeSelection;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

public class ClassifyMain {

	/**
	 * @param args
	 * @throws Exception 
	 */
	private static String data_folder = "E://dataset//change7.0//com_arff//";
	private static String selected_folder = "";
	private static String result_folder = "E://dataset//result//";
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		if(!new File(result_folder).exists()){
    		new File(result_folder).mkdirs();
    	}
		
		File ff = new File(data_folder);
    	//File ff = new File(select_folder);
    	File[] files = ff.listFiles();
    	if(!ff.exists()){
    		ff.mkdirs();
    	}
    	List<String> resultList = new ArrayList<String>();
    	resultList.add("Project,Method,Acc,Gmean,Recall-0,Precision-0,fMeasure-0,Recall-1,Precision-1,fMeasure-1,AUC" + "\n");
	    for(int i = 0;i < files.length; i++){
			Instances originIns = ReadData(files[i].getAbsolutePath());
			System.out.println(files[i].getAbsolutePath());
			System.out.println(originIns.numInstances());
			Instances selectedIns = Select(originIns);
			
			
			J48 classifier1= new J48();
			classifier1.buildClassifier(originIns);
			J48 classifier2= new J48();
			classifier2.buildClassifier(selectedIns);
			
			
			Random rand = new Random(1);
			Evaluation eval1 = new Evaluation(originIns);
			eval1.crossValidateModel(classifier1, originIns, 10, rand);
			Evaluation eval2 = new Evaluation(selectedIns);
			eval2.crossValidateModel(classifier1, originIns, 10, rand);
			//generate the output string of origin
			StringBuilder sBuilder1 = new StringBuilder();
			sBuilder1.append(files[i].getName()+",");
			sBuilder1.append("Origin" + ",");
			sBuilder1.append(1-eval1.errorRate()+ ",");
			sBuilder1.append(Math.sqrt(eval1.recall(0)*eval1.recall(1)) + ",");
			sBuilder1.append(eval1.recall(0) + ",");
			sBuilder1.append(eval1.precision(0) + ",");
			sBuilder1.append(eval1.fMeasure(0)+ ",");
			sBuilder1.append(eval1.recall(1) + ",");
			sBuilder1.append(eval1.precision(1)+ ",");
			sBuilder1.append(eval1.fMeasure(1)+ ",");
			sBuilder1.append(eval1.areaUnderROC(0));
			resultList.add(sBuilder1.toString());
			//generate the output string of origin
			StringBuilder sBuilder2 = new StringBuilder();
			sBuilder2.append(files[i].getName()+",");
			sBuilder2.append("Selected" + ",");
			sBuilder2.append(1-eval2.errorRate()+ ",");
			sBuilder2.append(Math.sqrt(eval2.recall(0)*eval2.recall(1)) + ",");
			sBuilder2.append(eval2.recall(0) + ",");
			sBuilder2.append(eval2.precision(0) + ",");
			sBuilder2.append(eval2.fMeasure(0)+ ",");
			sBuilder2.append(eval2.recall(1) + ",");
			sBuilder2.append(eval2.precision(1)+ ",");
			sBuilder2.append(eval2.fMeasure(1)+ ",");
			sBuilder2.append(eval2.areaUnderROC(0));
			resultList.add(sBuilder2.toString());
	    }
		
		exportCsv(new File(result_folder + "result.csv"), resultList);
		

	}

	private static Instances Select(Instances ins) throws Exception {
		// TODO Auto-generated method stub
		Instances tempIns = ins;
		
		/*
		 * 方法二：属性子集评估器+搜索方法
		 * */
		try{


			ASEvaluation eval = new weka.attributeSelection.CfsSubsetEval();;
			ASSearch search = new weka.attributeSelection.LinearForwardSelection();
			AttributeSelection attrs = new AttributeSelection();

			attrs.setEvaluator(eval);
			attrs.setSearch(search);
			attrs.SelectAttributes(ins);
			tempIns = attrs.reduceDimensionality(ins);
		}catch(Exception e){
			System.out.println("Attribute Selection Error::"+e.getMessage());
			throw new Exception();
		}
		
		return tempIns;

	}

	private static Instances ReadData(String filePath) {
		// TODO Auto-generated method stub
		System.out.println("======Read data from " + filePath + "======");
		if(!new File(filePath).exists()){
			System.out.println("======This File doesn't exist!" + "======");
			return null;
		}
		Instances ResultIns = null;
		//不做属性选择
		File fData = new File(filePath);	
		ArffLoader loader = new ArffLoader();
		try {
			loader.setFile(fData);
			ResultIns = loader.getDataSet();
			System.out.println("=========Data Information=========");
		    System.out.println("======AttrNum:"+ResultIns.numAttributes()+"======");
		    System.out.println("======InstancesNum:"+ResultIns.numInstances()+"======");
		} catch (IOException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return ResultIns;
	}
	
	/**
     * 导出
     * 
     * @param file csv文件(路径+文件名)，csv文件不存在会自动创建
     * @param dataList 数据
     * @return
     */
    public static boolean exportCsv(File file, List<String> dataList){
        boolean isSucess=false;
        System.out.println("Write into" + file.getAbsolutePath());
        FileOutputStream out=null;
        OutputStreamWriter osw=null;
        BufferedWriter bw=null;
        try {
            out = new FileOutputStream(file);
            osw = new OutputStreamWriter(out);
            bw =new BufferedWriter(osw);
            if(dataList!=null && !dataList.isEmpty()){
                for(String data : dataList){
                    bw.append(data).append("\r");
                }
            }
            isSucess=true;
        } catch (Exception e) {
            isSucess=false;
        }finally{
            if(bw!=null){
                try {
                    bw.close();
                    bw=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
            if(osw!=null){
                try {
                    osw.close();
                    osw=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
            if(out!=null){
                try {
                    out.close();
                    out=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
        }
        
        return isSucess;
    }

}
