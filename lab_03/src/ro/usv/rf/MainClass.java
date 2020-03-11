package ro.usv.rf;

public class MainClass {

	public static void main(String[] args) {
		double[][] learningSet;
		try {
			learningSet = FileUtils.readLearningSetFromFile("in.txt");
			int numberOfPatterns = learningSet.length;
			int numberOfFeatures = learningSet[0].length;
			System.out.println(String.format("The learning set has %s patters and %s features", numberOfPatterns,
					numberOfFeatures));

			// Calculate Euclidian distance between 2 columns
			double[] a = new double[numberOfPatterns];
			double[] b = new double[numberOfPatterns];

			for (int i = 0; i < numberOfPatterns; ++i) {
				a[i] = learningSet[i][0];
				b[i] = learningSet[i][1];
			}
			DistanceUtils.calculateEuclidianDistance(a, b);

			// Calculate Mahalanobis distance between 2 features
			double[] feature1 = new double[numberOfFeatures];

			for (int i = 0; i < numberOfFeatures; ++i) {
				feature1[i] = learningSet[0][i];
			}
			DistanceUtils.calculateMahalanobisDistance(feature1, learningSet[1], numberOfPatterns);

			// Calculate Cebisev distance between first and all of the features
			for (int i = 1; i < numberOfPatterns; ++i) {
				DistanceUtils.calculateCebisevDistance(feature1, learningSet[i]);
			}
			
			// Calculate City block distance
			for (int i = 1; i < numberOfPatterns; ++i) {
				DistanceUtils.calculateCityBlockDistance(feature1, learningSet[i]);
			}

		} catch (USVInputFileCustomException e) {
			System.out.println(e.getMessage());
		} finally {
			System.out.println("Finished learning set operations");
		}

	}

}
