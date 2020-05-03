package ro.usv.rf;

import java.util.HashMap;
import java.util.Map;

public class MainClass {

	public static void main(String[] args) {
		double[][] learningSet;
		try {
			learningSet = FileUtils.readLearningSetFromFile("in.txt");
			double[][] evaluationSet = FileUtils.readLearningSetFromFile("eval.txt");

			int numberOfPatterns = learningSet.length;
			int numberOfFeatures = learningSet[0].length;
			System.out.println(String.format("The learning set has %s patterns and %s features", numberOfPatterns, numberOfFeatures));

			Map<String, Integer> classesNumber = new HashMap<>();
			Map<String, Integer> classesIndex = new HashMap<>();

			// populate the map with number of occurrences for each class
			// populate the map with class indexes
			int classNameIndex = numberOfFeatures - 1;
			int index = 0;
			for (int i = 0; i < numberOfPatterns; i++)
			{
				String className = Integer.toString((int)learningSet[i][classNameIndex]);
				if (!classesIndex.containsKey(className)) {
					classesIndex.put(className, index++);
				}
				classesNumber.merge(className, 1, Integer::sum);
			}

			double[][] w = new double[classesNumber.keySet().size()][numberOfFeatures];
			for (int i = 0; i < learningSet.length; ++i) {
				String className = Integer.toString((int)learningSet[i][classNameIndex]);
				for (int j = 0; j < numberOfFeatures - 1; ++j) {
					w[classesIndex.get(className)][j] += learningSet[i][j];
				}
			}

			for (Map.Entry<String, Integer> entry : classesIndex.entrySet()) {
				for (int i = 0; i < numberOfFeatures - 1; ++i) {
					w[entry.getValue()][i] /= classesNumber.get(entry.getKey());
					w[entry.getValue()][numberOfFeatures - 1] -= Math.pow(w[entry.getValue()][i], 2) / 2;
				}
				
			}

			FileUtils.writeLearningSetToFile("w.txt", w);

			for (int k = 0; k < evaluationSet.length; ++k) {
				double fiMax = Double.NEGATIVE_INFINITY;
				String className = "";
				for (Map.Entry<String, Integer> entry : classesIndex.entrySet()) {
					double fi = 0.0;
					for (int i = 0; i < w[0].length; ++i) {
						if (i == w[0].length - 1) {
							fi += 1 * w[entry.getValue()][i];
						} else {
							fi += evaluationSet[k][i] * w[entry.getValue()][i];
							System.out.print(evaluationSet[k][i] + " ");
						}
					}

					System.out.print("fi " + fi + ", ");
					if (fi > fiMax) {
						fiMax = fi;
						className = entry.getKey();
					}
				}

				System.out.print("class " + className +"\n");
			}

		} catch (USVInputFileCustomException e) {
			System.out.println(e.getMessage());
		} finally {
			System.out.println("Finished learning set operations");
		}
	}

}
