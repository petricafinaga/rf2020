package ro.usv.rf;

public class MainClass {

	public static void main(String[] args) {
		double[][] learningSet;
		try {
			learningSet = FileUtils.readLearningSetFromFile("in1.txt");
			int numberOfPatterns = learningSet.length;
			int numberOfFeatures = learningSet[0].length;
			System.out.println(String.format("The learning set has %s patterns and %s features", numberOfPatterns, numberOfFeatures));

			int newNrOfFeatures = numberOfFeatures - 1;
			double[][] newLearningSet = new double[numberOfPatterns][newNrOfFeatures];

			//Copy first learning set into new learning set without last column
			for(int i = 0; i < numberOfPatterns; i++) {
				for(int j=0 ;j < newNrOfFeatures; j++) {
					newLearningSet[i][j] = learningSet[i][j];
				}
			}

			// Calculate the Euclidian generalized distance
			double[][] distanceMatrix = DistanceUtils.calculateEuclidianGeneralizedDistance(newLearningSet);
			FileUtils.writeLearningSetToFile("out.csv", distanceMatrix);

			int lastPattern = numberOfPatterns -1;
			double minDistance = distanceMatrix[lastPattern][0];
			int closestPattern = 0;

			for(int i = 0; i < numberOfPatterns; i++) {
				if(distanceMatrix[lastPattern][i] < minDistance && i != lastPattern) {
					minDistance = distanceMatrix[lastPattern][i];
					closestPattern = i;
				}
			}
			System.out.println("Clasa ultimului pattern: " + learningSet[closestPattern][numberOfFeatures -1]);

		} catch (USVInputFileCustomException e) {
			System.out.println(e.getMessage());
		} finally {
			System.out.println("Finished learning set operations");
		}
	}

}
