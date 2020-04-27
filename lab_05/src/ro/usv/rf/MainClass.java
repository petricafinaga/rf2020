package ro.usv.rf;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class MainClass {

	public static void main(String[] args) {
		String[][] learningSet;
		String[][] evaluationSet;

		try {
			learningSet = FileUtils.readCsvSetFromFile("data.csv");
			evaluationSet = FileUtils.readCsvSetFromFile("in.txt");

			int numberOfPatterns = learningSet.length;
			int numberOfFeatures = learningSet[0].length;
			System.out.println(String.format("The learning set has %s patters and %s features", numberOfPatterns, numberOfFeatures));

			double[][] distMatrix = Utils.calculateEuclidianGeneralizedDistance(evaluationSet, learningSet);

			Queue<Element> pq = new PriorityQueue<>();
			int[] nn = { 1, 3, 5, 7, 9, 13, 17 };

			int nameIndex = numberOfFeatures - 1;
			for (int i = 0; i < evaluationSet.length; ++i) {
				for (int j = 0; j < numberOfPatterns ; ++j) {
					pq.add(new Element(distMatrix[i][j], learningSet[j][nameIndex]));
				}

				System.out.print("Pattern " + evaluationSet[i][0] + ": ");
				for (int n : nn) {
					Queue<Element> tempQ = new PriorityQueue<>(pq);
					System.out.print(Utils.NNMethod(tempQ, n) + " ");
				}

				System.out.println();
				pq.clear();
			}

		} catch (USVInputFileCustomException e) {
			System.out.println(e.getMessage());
		} finally {
			System.out.println("Finished learning set operations");
		}
	}
	


}
