package classify.base;

import java.util.List;

import predict.FileUtil;
import weka.associations.Apriori;
import weka.associations.AprioriItemSet;
import weka.core.FastVector;
import weka.core.Instances;

public class AssociationTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		weka.associations.Apriori ap = new Apriori();
		String filePath = "D:\\weka install\\Weka-3-6\\data\\supermarket.arff";
		Instances ins = FileUtil.ReadData(filePath);
		int attrNum = ins.numAttributes();
		try {
			ap.buildAssociations(ins);
			ap.getCapabilities();
			FastVector[] rules = ap.getAllTheRules();
			System.out.println("======================Result=====================");
			System.out.println(ap.toString());
			for(int i = 0 ; i < attrNum; i++){
				System.out.print(ins.instance(0).value(i));
			}
			System.out.println();
			for(int i = 0 ; i < rules[0].size(); i++){
				System.out.print((i+1) + ":");
				System.out.print(((AprioriItemSet)rules[0].elementAt(i)).toString(ins));
				System.out.print("==>" );
				System.out.print(((AprioriItemSet)rules[1].elementAt(i)).toString(ins));
				/*AprioriItemSet rule = (AprioriItemSet) info[i];
				int[] items = rule.items();
				for(int k = 0 ; k <items.length ; k++){
					System.out.print((items[k]==-1)?"":ins.attribute(items[k]).name() + "\t");
				}*/
				
				System.out.println("\t" + rules[2].elementAt(i));
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
