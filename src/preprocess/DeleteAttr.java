package preprocess;

import java.io.BufferedReader;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import predict.Config;
import predict.FileUtil;
import weka.core.Instances;

public class DeleteAttr {

	/**
	 * @param args
	 */
	static String data_dir = "com_net_other_bow_csv\\";
	static String data_folder = Config.total_folder + data_dir;
	static String replaced_folder = Config.total_folder + data_dir.substring(0,data_dir.indexOf("csv"))+ "replaced\\";
	static String result_folder = Config.total_folder + data_dir.substring(0,data_dir.indexOf("csv"))+ "arff\\";
	public static void main(String[] args) {
		// TODO Auto-generated method stub

    	File f = new File(result_folder);
    	if(!f.exists()) f.mkdirs();
    	
    	File f1 = new File(replaced_folder);
    	if(!f1.exists()) f1.mkdirs();
  
    	File ff = new File(data_folder);//select_folder
    	File[] files = ff.listFiles();
    	for(int i = 0;i < files.length; i++){
    		try{
    			String filePath = files[i].getAbsolutePath();
    			String fileName = files[i].getName();
    			System.out.println("--->" + filePath);

    			String replacedPath = replaced_folder + fileName.substring(0,fileName.lastIndexOf(".")) + ".csv";
    			if(! new File(replacedPath).exists()){
    				ReplaceMissingValues(filePath, replacedPath);
    			}
    			System.out.println("====" + replacedPath);
    			String path = result_folder + FileUtil.getFileName(files[i].getAbsolutePath()) + ".arff";	
    			if(!new File(path).exists()){
    				Instances inputIns = FileUtil.ReadDataCSV(replacedPath);
    				//Instances inputIns = FileUtil.ReadDataCSV(filePath);
	    			inputIns = DeleteAttributes(inputIns);
	    		
		    		weka.filters.unsupervised.attribute.RemoveUseless remove = new weka.filters.unsupervised.attribute.RemoveUseless();
					remove.setMaximumVariancePercentageAllowed(0.99);				
					remove.setInputFormat(inputIns);
					inputIns = remove.useFilter(inputIns, remove);
		    		System.out.println("==Removed Attr:"+inputIns.numAttributes());
		    		
		    		FileUtil.WriteData(inputIns, path);
    			}
	    		
    		}catch(Exception e){
    			e.printStackTrace();
    			continue;
    		}
    	}
	}
	
	private static Instances DeleteAttributes(Instances inputIns){
    		try{
	    		//ɾ��id,commit_id,file_id,type
	    		inputIns.deleteAttributeAt(0);
	    		inputIns.deleteAttributeAt(0);
	    		inputIns.deleteAttributeAt(0);
	    		inputIns.deleteAttributeAt(0);
	    		//ɾ��changeloc
	    		inputIns.deleteAttributeAt(38);
	    		
	    		inputIns.deleteAttributeAt(42);
	    		inputIns.deleteAttributeAt(42);		
	    		
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    		return inputIns;

	}
	
	private static String ReplaceMissingValues(String path, String result_path) throws IOException{
		
		System.out.println("====" + result_path);
		try {
			File file = new File(result_path);
			if (file.exists()) {
			    return result_path;
			}else{
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(result_path);
			BufferedWriter bw = new BufferedWriter(fw);
			
			BufferedReader bReader = new BufferedReader(new FileReader(path));
			//write the head
			String line = bReader.readLine();
			if(line != null) bw.write(line + "\n");

			while ((line = bReader.readLine()) != null) {
				String[] temp = line.split(",");
				int i = 0;
				while(i < temp.length - 1){
					if(temp[i].equals("")){
						//System.out.println(i + " ");
						bw.write("0" + ",");
					}else{
						bw.write(temp[i] + ",");
					}
					i++;
				}
				bw.write(temp[i] + "\n");

			}
			bw.flush();
			bw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result_path;

	}
}
