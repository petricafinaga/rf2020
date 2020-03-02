package ro.usv.rf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainClass {
	
	
	public static void main(String[] args) {
		double[][] learningSet = FileUtils.readLearningSetFromFile("in.txt");
		
		double[][] normalizedSet = normalizeLearningSet(learningSet);
		
		FileUtils.writeLearningSetToFile("out.csv", normalizedSet);
	}

	 private static double[][] normalizeLearningSet(double[][] learningSet) {

		 int lines = learningSet.length;
		 int columns = learningSet[0].length;

		 double[][] normalizedLearningSet = new double[lines][columns];

		 double[] min = new double[columns];
		 double[] max = new double[columns];

		 // Initialize min and max arrays
		 for(int i = 0; i < columns; i++)
		 {
			 min[i] = Double.MAX_VALUE;
			 max[i] = Double.NEGATIVE_INFINITY;
		 }

		 // Calculate min and max value for each column
		 for(int col = 0; col < columns; ++col) {
			 for(int line = 0; line < lines; ++line) {
				 if(learningSet[line][col] < min [col]) {
					 min[col] = learningSet[line][col];
				 }
				 
				 if(learningSet[line][col] > max[col]) {
					 max[col] = learningSet[line][col];
				 }
			 }
		 }

		 // Calculate the normalized value for each feature
		 for(int line = 0; line < learningSet.length; ++line) {
			 for(int col = 0; col < learningSet[0].length; ++col) {
				 normalizedLearningSet[line][col] = (learningSet[line][col] - min[col]) / (max[col] - min[col]);
			 }
		 }
		 
		 return normalizedLearningSet;  
	 }
}
