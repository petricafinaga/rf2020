package ro.usv.rf;

public class DistanceUtils {
	
	protected static double[] calculateEuclidianDistance(double[] a, double[] b) {
		double[] euclidianDistances = new double[a.length-1];
		
		for(int i=0; i < a.length-1; ++i) {
			double dist = Math.sqrt(Math.pow(a[i] - a[i+1], 2) +
					Math.pow(b[i] - b[i+1], 2));
			euclidianDistances[i] = dist;
			System.out.println(dist);
		}
		
		return euclidianDistances;
	}
	
	protected static double calculateMahalanobisDistance(double[] a, double[] b, double nrOfForms) {
		double mahalanobisDistance = 0.0;
		
		double sum = 0;
		for(int i=0; i < a.length; ++i) {
			sum += Math.pow(a[i] - b[i], nrOfForms);
		}
		
		mahalanobisDistance = Math.pow(sum, 1/nrOfForms);
		System.out.println("Mahalanobis distance: " + mahalanobisDistance);
		
		return mahalanobisDistance;
	}
	
	protected static double calculateCebisevDistance(double[] a, double[] b) {

		double max = 0;	
		for(int i = 0; i < a.length; ++i) {
			double  val = Math.abs(a[i] - b[i]);
			if(val > max) {
				max = val;
			}
		}
		System.out.println("Cebisev distance is: " + max);

		return max;
	}
	
	protected static double calculateCityBlockDistance(double[] a, double[] b) {
		double distance = 0.0;

		for(int i = 0; i < a.length; ++i) {
			distance += Math.abs(a[i] - b[i]);
		}
		System.out.println("City block distance is: " + distance);

		return distance;
	}
}
