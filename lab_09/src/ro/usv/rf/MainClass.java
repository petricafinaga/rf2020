package ro.usv.rf;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainClass {
	
	
	public static void main(String[] args) {
		double[][] evaluationSet;
		try {
			evaluationSet = FileUtils.readLearningSetFromFile("in.txt");
			int numberOfPatterns = evaluationSet.length;
			int numberOfFeatures = evaluationSet[0].length;
			System.out.println(String.format("The evaluation set has %s patters and %s features", numberOfPatterns, numberOfFeatures));
			
			List<Point> points = new ArrayList<>();
			for (int i = 0; i < evaluationSet.length; ++i) {
				points.add(new Point(evaluationSet[i][0], evaluationSet[i][1]));
			}

			int[] classification = Utils.dynamicKernels(points, 2);
			
			for (int i = 0; i < classification.length; ++i) {
				System.out.print(classification[i] + " ");
			}

		} catch (USVInputFileCustomException e) {
			System.out.println(e.getMessage());
		} finally {
			System.out.println("Finished evaluation set operations");
		}
	}

}
