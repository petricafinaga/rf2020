package ro.usv.rf;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Utils {

	protected static double[][] calculateWMatrix(double[][] learningSet) {
		int rows = learningSet.length;
		int cols = learningSet[0].length;

		Set<Double> classes = new HashSet<>();
		for (int i = 0; i < rows; ++i) {
			classes.add(learningSet[i][cols - 1]);
		}
		
		int nrOfClasses = classes.size();
		
		double[][] w = new double[nrOfClasses][cols];
		
		int classIndex = 0;
		int classCount = 0;
		int count = 0;
		
		while(classCount < nrOfClasses) {
			double[] wLine = new double[cols - 1];
			for(int j = 0; j < cols - 1; ++j) {
				count = 0;
				for(int i = classIndex; learningSet[i][cols] == learningSet[classIndex][cols]; ++i) {
					wLine[j] += learningSet[j][i];
					count++;
				}
				wLine[j] = wLine[j] / count;
				w[classCount][j] = wLine[j]; 
			}
			double sum = 0;
			for(int j = 0; j < cols - 1; ++j) {
				sum += Math.pow(wLine[j], 2);
			}
			w[classCount][cols - 1] = sum * (-1.0/2.0);
			++classCount;
			classIndex += count;
		}
	
		return w;
	}
}
