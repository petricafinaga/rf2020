package ro.usv.rf;

public class Utils {
	
	// For K-NN rule
	protected static double[][] calculateEuclidianGeneralizedDistance(String[][] evaluation, String[][] training) {

		int evalLength = evaluation.length;
		int trainLength = training.length;
		double[][] matrix = new double[evalLength][trainLength];

		for (int i = 0; i < evalLength; i++) {
			matrix[i][i] = 0.0;

			for (int j = 0; j < trainLength; j++) {
				matrix[i][j] = calculateEuclidianDistance(evaluation[i], training[j]);
			}
		}

		return matrix;
	}

	private static double calculateEuclidianDistance(String[] a, String[] b) {
		double sum = 0.0;
		for(int i = 0; i < a.length - 1; ++i) {
			sum += sum = Math.pow(Double.valueOf(a[i]) - Double.valueOf(b[i]), 2);
		}

		return Math.floor(Math.sqrt(sum) * 100) / 100;
	}
}
