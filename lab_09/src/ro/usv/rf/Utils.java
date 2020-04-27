package ro.usv.rf;

import java.util.Random;

public class Utils {

	protected static double calculateEuclidianDistance(double[] a, double[] b) {
		return Math.sqrt(Math.pow(b[0] - a[0], 2) + Math.pow(b[1] - a[1], 2));
	}

	protected static double[] dynamicKernels(double[][] evaluationSet, int m) {
		int numberOfPatterns = evaluationSet.length;
		int numberOfFeatures = evaluationSet[0].length;

		double[] iClass = new double[numberOfPatterns];
		double[][] kernels = new double[m][numberOfFeatures];
				
//		kernels[0] = evaluationSet[rand.nextInt(numberOfPatterns)];
//		kernels[1] = evaluationSet[rand.nextInt(numberOfPatterns)];
		kernels[0] = evaluationSet[0];
		kernels[1] = evaluationSet[1];

		boolean done = true;
		do {
			
		} while(!done); 

		return iClass;
	}
	

}
