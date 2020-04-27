package ro.usv.rf;

import java.util.Random;

public class MainClass {
	
	
	public static void main(String[] args) {
		double[][] evaluationSet;
		try {
			evaluationSet = FileUtils.readLearningSetFromFile("in.txt");
			int numberOfPatterns = evaluationSet.length;
			int numberOfFeatures = evaluationSet[0].length;
			System.out.println(String.format("The evaluation set has %s patters and %s features", numberOfPatterns, numberOfFeatures));

			


			
			
			
			

		} catch (USVInputFileCustomException e) {
			System.out.println(e.getMessage());
		} finally {
			System.out.println("Finished evaluation set operations");
		}
	}

}
