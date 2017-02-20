package preprocess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Solution {
	public static void main(String[] args) throws IOException{
		fuck();
	}

	public static void fuck() throws IOException {
		//1.��ȡ�ļ����������������ѡ��֮����ļ�
		String total_dir = "E://dataset//change6.0//";
		String selected_dir = "com_net_other_bow_arff_selected//CfsSu_BestF//";
		File fa=new File(total_dir + selected_dir);
		File[] ch=fa.listFiles();
		Set<String> attrbutes=new LinkedHashSet<>();//�������е�������
		Map<String,Set<String>> array=new HashMap<String,Set<String>>();//����ÿһ���ļ����������������֮���ӳ��
		for (File file : ch) {
			System.out.println("=========="+file.getName()+"==========");
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			Set<String> Mset=new HashSet<>();//�������еĴ�����������
			Set<String> content=new HashSet<>();
			while ((line = br.readLine()) != null) {
				//��"@data"��ͷ����֮�������ݲ��֣�ֱ�ӽ���ѭ�����������±���
				if (line.startsWith("@data")) {
					break;
				}
				//��"@attribute"��ͷ������������
				if (line.startsWith("@attribute")) {
					//��s��ͷ˵���������Ǵ��������ԣ�Ҫȥ�ֵ�����ҵ���Ӧ�ص���
					if (line.split(" ")[1].startsWith("s")) {
						Mset.add(line.split(" ")[1]);
					}else {
					//������Ǵ��������ԣ����ò��Ҵʵ䣬ֱ�Ӽ�������
						content.add(line.split(" ")[1]);
						attrbutes.add(line.split(" ")[1]);
					}
				}
			}
			br.close();
			//ȥ�ʵ�����Ҵ��������Զ�Ӧ�ĵ���
			String arffName=file.getName().substring(0, file.getName().indexOf("."));
			String textName=arffName + "Dic.csv";
			File textFile=new File("E://dataset//change6.0//dic_csv//"+textName);
			br=new BufferedReader(new FileReader(textFile));
			while ((line=br.readLine())!=null) {
				String num=line.substring(0,line.indexOf("="));
				String word=line.substring(line.indexOf("=")+1);
				//�������ѡ�������������������������Ӧ�ص��ʼ��뵽�������������
				if (Mset.contains(num)) {
					content.add(word);
					attrbutes.add(word);
				}
			}
			array.put(arffName,content);//���ļ����������������������ӳ������
			br.close();
		}

		Map<String,StringBuilder> con=new LinkedHashMap<String,StringBuilder>();
		Map<String, Integer> attrFreq = new HashMap<String, Integer>();
		for (String file : array.keySet()) {
			//����ÿһ���ļ�
			Set<String> set = array.get(file);
			StringBuilder stringBuilder=new StringBuilder();
			//���������ļ����е���������
			for (String string : attrbutes) {
				//ͳ�Ƶ�ǰ�ļ��¸����Գ��Ƿ���֣���ͳ�Ƹ������ܵĳ���Ƶ��
				if (set.contains(string)) {
					stringBuilder.append("1,");
					if(!attrFreq.containsKey(string)){
						attrFreq.put(string, 1);
					}else{
						attrFreq.put(string, attrFreq.get(string)+1);
					}
				}else {
					stringBuilder.append("0,");
				}
				
			}
			
			con.put(file,stringBuilder);
		}
		StringBuilder freqBuilder=new StringBuilder();
		for (String string: attrbutes) {
			freqBuilder.append(attrFreq.get(string)+",");
		}
		con.put("Frequency", freqBuilder);
		//���������ʽ�������csv�ļ�����
		File csvFile=new File("E://dataset//change6.0//select_analyze//"+ selected_dir.substring(0, selected_dir.indexOf("//")) + "_attr_selected.csv");
		if (!csvFile.exists()) {
			csvFile.createNewFile();
		}
		BufferedWriter bWriter=new BufferedWriter(new FileWriter(csvFile));
		StringBuilder title=new StringBuilder();
		//�����������У�������������ļ�
		title.append("Attribute"+",");
		for (String string: attrbutes) {
			title.append(string+",");
		}
		bWriter.append(title+"\n");
		//����ÿһ�е����ݣ�
		for (String file : con.keySet()) {
			StringBuilder stringBuilder=con.get(file);
			bWriter.append(file+","+stringBuilder+"\n");
		}
		bWriter.flush();
		bWriter.close();
	}

}
