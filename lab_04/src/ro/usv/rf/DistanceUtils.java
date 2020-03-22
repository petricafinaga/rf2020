package ro.usv.rf;

public class DistanceUtils {

	protected static double[][] calculateEuclidianGeneralizedDistance(double[][] learningSet) {
		int patterns = learningSet.length;

		double[][] matrix = new double[patterns][patterns];

		for (int i = 0; i < patterns; i++) {
			matrix[i][i] = 0.0;

			for (int j = i + 1; j < patterns; j++) {
				matrix[i][j] = calculateEuclidianDistance(learningSet[i], learningSet[j]);
				matrix[j][i] = matrix[i][j];
			}
		}

		return matrix;
	}

	private static double calculateEuclidianDistance(double[] a, double[] b) {
		double sum = 0.0;
		for (int k = 0; k < a.length; k++) {
			sum += Math.pow(a[k] - b[k], 2);
		}

		return Math.floor(Math.sqrt(sum) * 100) / 100;
	}
}
