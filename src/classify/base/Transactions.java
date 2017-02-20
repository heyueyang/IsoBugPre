package classify.base;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.omg.CORBA.PUBLIC_MEMBER;

import predict.FileUtil;
import weka.associations.Apriori;
import weka.associations.AprioriItemSet;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class Transactions {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		weka.associations.Apriori ap = new Apriori();
		String filePath = "E:\\dataset\\change7.0\\com_csv\\" + "ant.csv";//"D:\\weka install\\Weka-3-6\\data\\supermarket.arff";
		Instances ins = FileUtil.ReadDataCSV(filePath);
		int attrNum = ins.numAttributes();
		int insNum = ins.numInstances();
		Instances res = new Instances("File",new FastVector(), insNum);
		
		weka.core.Attribute fileId = ins.attribute(1);
		weka.core.Attribute commitId = ins.attribute(2);
		
		Map<String,List<String>> data= new HashMap<String,List<String>>();
		List<String> fileList= new LinkedList<String>();
		try {
			
			for(int i = 0 ; i < insNum; i++){
				String curFileId = String.valueOf(ins.instance(i).value(fileId));
				String curCommitId = String.valueOf(ins.instance(i).value(commitId));
				if(!fileList.contains(curFileId)){
					fileList.add(curFileId);
				}
				if(data.containsKey(curCommitId)){
					List<String> curFileList= data.get(curCommitId);
					curFileList.add(curFileId);
					data.put(curCommitId, curFileList);
				}else{
					List<String> list= new LinkedList<String>();
					list.add(curFileId);
					data.put(curCommitId, list);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void Write2TempArff(List<String> fileList, Map<String, List<String>> data) throws IOException{
		String outPath = "E:\\dataset\\change7.0\\asso\\" + "test.arff";
		BufferedWriter bWriter = new BufferedWriter(new FileWriter(outPath));
		//StringBuffer sb = new StringBuffer();
		bWriter.append("@relation files" + "\n");
		
		for(int i = 0 ; i < fileList.size(); i++){
			String file_id = fileList.get(i);
			bWriter.append("@attribute '" + file_id + "' { t}" + "\n");
		}
		bWriter.append("@data" + "\n");
		Set<String> commitIds = data.keySet();
		int[] cnt = new int[fileList.size()];
		int n = 0;
		for(String commit_id : commitIds){
			System.out.print(n++ + ":" + "\t");
			List<String> list = data.get(commit_id);
			for(int j = 0 ; j < list.size(); j++){
				System.out.print(list.get(j) + "\t");
			}
			System.out.println();
		
			StringBuffer temp = new StringBuffer();
			for(int j = 0 ; j < fileList.size(); j++){
				if(list.contains(fileList.get(j))){
					temp.append("t"+",");
					cnt[j]++;
				}else{
					temp.append("?"+",");
				}
			}
			bWriter.append(temp.subSequence(0, temp.length()-1)+"\n");
		}
			
			bWriter.flush();
			bWriter.close();
			System.out.println("writed into" + outPath);
	
			//for(int j = 0 ; j < fileList.size(); j++){
			//	System.out.println(j+ "," + fileList.get(j) + "," + cnt[j]);
			//}
	}

}
