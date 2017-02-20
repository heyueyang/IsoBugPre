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
		//1.读取文件夹下面的所有属性选择之后的文件
		String total_dir = "E://dataset//change6.0//";
		String selected_dir = "com_net_other_bow_arff_selected//CfsSu_BestF//";
		File fa=new File(total_dir + selected_dir);
		File[] ch=fa.listFiles();
		Set<String> attrbutes=new LinkedHashSet<>();//保存所有的属性名
		Map<String,Set<String>> array=new HashMap<String,Set<String>>();//保存每一个文件和其包含的属性名之间的映射
		for (File file : ch) {
			System.out.println("=========="+file.getName()+"==========");
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			Set<String> Mset=new HashSet<>();//保存所有的词向量属性名
			Set<String> content=new HashSet<>();
			while ((line = br.readLine()) != null) {
				//以"@data"开头的行之后是数据部分，直接结束循环，不再往下遍历
				if (line.startsWith("@data")) {
					break;
				}
				//以"@attribute"开头的行是属性名
				if (line.startsWith("@attribute")) {
					//以s开头说明该属性是词向量属性，要去字典里查找到相应地单词
					if (line.split(" ")[1].startsWith("s")) {
						Mset.add(line.split(" ")[1]);
					}else {
					//如果不是词向量属性，不用查找词典，直接加入结果集
						content.add(line.split(" ")[1]);
						attrbutes.add(line.split(" ")[1]);
					}
				}
			}
			br.close();
			//去词典里查找词向量属性对应的单词
			String arffName=file.getName().substring(0, file.getName().indexOf("."));
			String textName=arffName + "Dic.csv";
			File textFile=new File("E://dataset//change6.0//dic_csv//"+textName);
			br=new BufferedReader(new FileReader(textFile));
			while ((line=br.readLine())!=null) {
				String num=line.substring(0,line.indexOf("="));
				String word=line.substring(line.indexOf("=")+1);
				//如果属性选择结果集里包含该属性名，将相应地单词加入到属性名结果集中
				if (Mset.contains(num)) {
					content.add(word);
					attrbutes.add(word);
				}
			}
			array.put(arffName,content);//将文件名和其包含的属性名集合映射起来
			br.close();
		}

		Map<String,StringBuilder> con=new LinkedHashMap<String,StringBuilder>();
		Map<String, Integer> attrFreq = new HashMap<String, Integer>();
		for (String file : array.keySet()) {
			//对于每一个文件
			Set<String> set = array.get(file);
			StringBuilder stringBuilder=new StringBuilder();
			//遍历所有文件共有的所有属性
			for (String string : attrbutes) {
				//统计当前文件下该属性出是否出现，并统计该属性总的出现频率
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
		//构造输出格式，输出至csv文件保存
		File csvFile=new File("E://dataset//change6.0//select_analyze//"+ selected_dir.substring(0, selected_dir.indexOf("//")) + "_attr_selected.csv");
		if (!csvFile.exists()) {
			csvFile.createNewFile();
		}
		BufferedWriter bWriter=new BufferedWriter(new FileWriter(csvFile));
		StringBuilder title=new StringBuilder();
		//构造属性名行，将行名输出至文件
		title.append("Attribute"+",");
		for (String string: attrbutes) {
			title.append(string+",");
		}
		bWriter.append(title+"\n");
		//构造每一行的内容，
		for (String file : con.keySet()) {
			StringBuilder stringBuilder=con.get(file);
			bWriter.append(file+","+stringBuilder+"\n");
		}
		bWriter.flush();
		bWriter.close();
	}

}
