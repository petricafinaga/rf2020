package ro.usv.rf;

import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.PriorityQueue;
import java.util.Queue;

public class MainClass {

	public static void main(String[] args) {
		String[][] learningSet;
		try {
			learningSet = FileUtils.readLearningSetFromFile("iris.csv");
			int numberOfPatterns = learningSet.length;
			int numberOfFeatures = learningSet[0].length-1;

			System.out.println(String.format("The learning set has %s patters and %s features", numberOfPatterns,
					numberOfFeatures));
			
			Map<String, Integer> classesMap = new HashMap<String, Integer>();
			
			//create map with distinct classes and number of occurence for each class
			for (int i=0; i<numberOfPatterns; i++)
			{
				String clazz = learningSet[i][learningSet[i].length-1];
				if (classesMap.containsKey(clazz))
				{
					Integer nrOfClassPatterns = classesMap.get(clazz);
					classesMap.put(clazz, nrOfClassPatterns + 1);
				}
				else
				{
					classesMap.put(clazz, 1);
				}
			}
			Random random = new Random();
			//map that keeps for each class the random patterns selected for evaluation set
			Map<String, List<Integer>> classesEvaluationPatterns = new HashMap<String, List<Integer>>();
			Integer evaluationSetSize = 0;
			for (Map.Entry<String, Integer> entry: classesMap.entrySet())
			{
				String className = entry.getKey();
				Integer classMembers = entry.getValue();
				Integer evaluationPatternsNr = Math.round(classMembers *15/100);
				evaluationSetSize += evaluationPatternsNr;
				List<Integer> selectedPatternsForEvaluation = new ArrayList<Integer>();
				for (int i=0; i<evaluationPatternsNr; i++)
				{
					Integer patternNr = random.nextInt(classMembers ) +1;
					while (selectedPatternsForEvaluation.contains(patternNr))
					{
						patternNr = random.nextInt(classMembers ) +1;
					}
					selectedPatternsForEvaluation.add(patternNr);
				}
				classesEvaluationPatterns.put(className, selectedPatternsForEvaluation);				
			}
			
			String[][] evaluationSet = new String[evaluationSetSize][numberOfPatterns];
			String[][] trainingSet = new String[numberOfPatterns-evaluationSetSize][numberOfPatterns];
			int evaluationSetIndex = 0;
			int trainingSetIndex = 0;
			Map<String, Integer> classCurrentIndex = new HashMap<String, Integer>();
			for (int i=0; i<numberOfPatterns; i++)
			{
				String className = learningSet[i][numberOfFeatures];
				if (classCurrentIndex.containsKey(className))
				{
					int currentIndex = classCurrentIndex.get(className);
					classCurrentIndex.put(className, currentIndex+1);
				}
				else
				{
					classCurrentIndex.put(className, 1);
				}
				if (classesEvaluationPatterns.get(className).contains(classCurrentIndex.get(className)))
				{
					evaluationSet[evaluationSetIndex] = learningSet[i];
					evaluationSetIndex++;
				}
				else
				{
					trainingSet[trainingSetIndex] = learningSet[i];
					trainingSetIndex++;
				}
			}

			FileUtils.writeLearningSetToFile("eval.txt", evaluationSet);
			FileUtils.writeLearningSetToFile("train.txt", trainingSet);

			double[][] distMatrix = Utils.calculateEuclidianGeneralizedDistance(evaluationSet, trainingSet);
			Queue<Element> pq = new PriorityQueue<>();
			int[] nn = { 1, 3, 5, 7, 9, 13, 17 };

			int nameIndex = numberOfFeatures;
			for (int i = 0; i < distMatrix.length; ++i) {
				for (int j = 0; j < distMatrix[0].length ; ++j) {
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
			
			System.out.println("///////////////////////////////////////////////////////////////////////////////////" + "\n");
			
			Map<String, Integer> classesNumber = new HashMap<>();
			Map<String, Integer> classesIndex = new HashMap<>();

			// populate the map with number of occurrences for each class
			// populate the map with class indexes
			int index = 0;
			for (int i = 0; i < trainingSet.length; i++)
			{
				String className = trainingSet[i][numberOfFeatures];
				if (!classesIndex.containsKey(className)) {
					classesIndex.put(className, index++);
				}
				classesNumber.merge(className, 1, Integer::sum);
			}

			double[][] w = new double[classesNumber.keySet().size()][numberOfFeatures + 1];
			for (int i = 0; i < trainingSet.length; ++i) {
				String className = trainingSet[i][numberOfFeatures];
				for (int j = 0; j < numberOfFeatures; ++j) {
					w[classesIndex.get(className)][j] += Double.valueOf(trainingSet[i][j]);
				}
			}

			for (Map.Entry<String, Integer> entry : classesIndex.entrySet()) {
				for (int i = 0; i < (numberOfFeatures); ++i) {
					w[entry.getValue()][i] /= classesNumber.get(entry.getKey());
					w[entry.getValue()][numberOfFeatures] -= Math.pow(w[entry.getValue()][i], 2) / 2;
				}
				
			}

			FileUtils.writeLearningSetToFile("w.txt", w);
			
			int correctGuesses = 0;
			for (int k = 0; k < evaluationSet.length; ++k) {
				System.out.print("Pattern " +  (k + 1) + " ");

				double fiMax = Double.NEGATIVE_INFINITY;
				String className = "";
				for (Map.Entry<String, Integer> entry : classesIndex.entrySet()) {
					double fi = 0.0;
					for (int i = 0; i < w[0].length; ++i) {
						if (i == w[0].length - 1) {
							fi += 1 * w[entry.getValue()][i];
						} else {
							fi += Double.valueOf(evaluationSet[k][i]) * w[entry.getValue()][i];
						}
					}

					System.out.print("fi:" + fi + ", ");
					if (fi > fiMax) {
						fiMax = fi;
						className = entry.getKey();
					}
				}
				if (className == evaluationSet[k][numberOfFeatures]) {
					++correctGuesses;
				}
				System.out.print("class found is:" + className + "| expected: " + evaluationSet[k][numberOfFeatures] + "\n");
			}
			

			
			
		} catch (USVInputFileCustomException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Finished learning set operations");
		}
	}

}
