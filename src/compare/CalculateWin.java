package compare;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class CalculateWin {

	public static void main(String[] args) throws IOException {
		Map<String, int[]> res = dealWith("F:\\科研\\change论文\\论文实验结果\\对比结果统计\\NB.csv");
		for (String method : res.keySet()) {
			System.out.print("," + method + ",");
			for (int i = 0; i < 8; i++) {
				System.out.print(res.get(method)[i]+"/8" + ",");
			}
			System.out.print(res.get(method)[8]+"/8");
			System.out.println();
		}
	}

	private static Map<String, int[]> dealWith(String input) throws IOException {
		BufferedReader bReader = new BufferedReader(new FileReader(new File(
				input)));
		Set<String> methods = new LinkedHashSet<>();
		String line = bReader.readLine();
		int evalLen = 9;
		Map<String, Map<String, double[]>> data = new LinkedHashMap<>();
		String curPro = "";
		while (line != null && (!line.equals(""))) {
			if (line.startsWith("Pro")) {
				line = bReader.readLine();
				continue;
			}
			curPro = line.split(",")[0];
			Map<String, double[]> methodValue = new LinkedHashMap<>();
			for (int i = 0; i < 8; i++) {
				String[] array = line.split(",");
				double[] values = new double[9];
				for (int j = 2; j < array.length; j++) {
					values[j - 2] = Double.parseDouble(array[j].trim());
				}
				methodValue.put(array[1].trim(), values);
				methods.add(array[1].trim());
				line = bReader.readLine();
			}
			data.put(curPro, methodValue);
		}
		bReader.close();
		Map<String, int[]> res = new LinkedHashMap<>();
		for (String method : methods) {
			res.put(method, new int[9]);
		}
		for (int i = 0; i < evalLen; i++) {
			for (String pro : data.keySet()) {
				double maxV = 0;
				for (String me : methods) {
					if (data.get(pro).get(me)[i] > maxV) {
						maxV = data.get(pro).get(me)[i];
					}
				}
				for (String me : methods) {
					if (data.get(pro).get(me)[i] == maxV) {
						res.get(me)[i]++;
					}
				}
			}
		}
		return res;
	}

}

