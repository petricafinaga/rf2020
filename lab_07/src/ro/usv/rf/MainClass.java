package ro.usv.rf;

public class MainClass {

	public static void main(String[] args) {
		double[][] learningSet;
		try {
			learningSet = FileUtils.readLearningSetFromFile("in.txt");
			int numberOfPatterns = learningSet.length;
			int numberOfFeatures = learningSet[0].length;
			System.out.println(String.format("The learning set has %s patterns and %s features", numberOfPatterns, numberOfFeatures));

			int nrOfPatterns = numberOfPatterns - 1;
			int nrOfFeatures = numberOfFeatures;

			double[][] newLearningSet = new double[nrOfPatterns][nrOfFeatures];				
			for(int i = 0; i < nrOfPatterns; ++i) {
				for(int j = 0; j < nrOfFeatures; ++j) {
					newLearningSet[i][j] = learningSet[i][j]; 
				}
			}

			double[][] w = Utils.calculateWMatrix(newLearningSet);

			FileUtils.writeLearningSetToFile("out.txt", w);

		} catch (USVInputFileCustomException e) {
			System.out.println(e.getMessage());
		} finally {
			System.out.println("Finished learning set operations");
		}
	}

}
