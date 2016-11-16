package preprocess;

import java.io.File;


import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.AttributeSelection;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;

class AttrSelectTest{
	/*
	public static void main(){
		File fData = new File("");
		ArffLoader loader0 = new ArffLoader();
		loader0.setFile(fData);
		Instances ins = loader0.getDataSet();
		try{
			Class<?> c1 = Class.forName("weka.attributeSelection." + Eval);
			Class<?> c2 = Class.forName("weka.attributeSelection." + Search);	
			ASEvaluation eval = (ASEvaluation) c1.newInstance();
			ASSearch search = (ASSearch) c2.newInstance();
			AttributeSelection attrs = new AttributeSelection();
			attrs.setEvaluator(eval);
			attrs.setSearch(search);
			System.out.println("1");
			attrs.SelectAttributes(ins);
			System.out.println("2");
			Instances tempIns = attrs.reduceDimensionality(ins);
		}catch(Exception e){
			System.out.println("Attribute Selection Error::"+e.getMessage());
			throw new Exception();
		}
		
		
		

		Instances newIns = ins;
		int attrNum = ins.numAttributes()-1;
        System.out.println("=========Data Information=========");
        System.out.println("======AttrNum:"+attrNum+"======");
        System.out.println("======InstancesNum:"+trainIns.numInstances()+"======");
        //set the classIndex
        	newIns = selector(45,ins,eval,search, flag);
        
			System.out.println("======Attribute Selecting...======");
			System.out.println("======New AttrNum="+(newIns.numAttributes())+"======");

			ArffSaver saver = new ArffSaver(); 
    		saver.setInstances(newIns);  
    	    saver.setFile(new File(outArffPath));  
    	    saver.writeBatch(); 
    	    System.out.println("==Selected Attrbutes Writed:"+outArffPath+"==");
	    
	    }*/

}