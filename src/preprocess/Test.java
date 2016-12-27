package preprocess;

import java.io.File;
import java.io.IOException;

import weka.core.Instances;
import weka.core.converters.CSVLoader;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Instances ResultIns = null;
		//不做属性选择
		String filePath = "E:\\dataset\\change5.0\\com_net_other_replaced\\ant.csv";
		File fData = new File(filePath);	
		CSVLoader loader = new CSVLoader();
		try {
			loader.setSource(fData);
			ResultIns = loader.getDataSet();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
