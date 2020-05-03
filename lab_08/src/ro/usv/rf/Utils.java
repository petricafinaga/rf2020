package ro.usv.rf;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Map.Entry;

class Element implements Comparable<Element>{

	@Override
	public int compareTo(Element elem) {
		if (this.distance > elem.distance) return 1;
		else if (this.distance < elem.distance) return -1;
		else return 0;
	}

	public Element(double dist, String name) {
		this.distance = dist;
		this.name = name;
	}

	double distance;
	String name;
}

public class Utils {

	 protected static String NNMethod(Queue<Element> pq, int n) {
		 Map<String, Integer> map = new HashMap<>();
		 for (int i = 0; i < n; ++i) {
			 map.merge(pq.poll().name, 1, Integer::sum);
		 }

		 int max = 0;
		 String className = "";

		 for(Entry<String, Integer> entry: map.entrySet()) {
			 if(entry.getValue() > max) {
				 max = entry.getValue();
				 className = entry.getKey();
			 }
		 }

		 return className;
	 }

	protected static double[][] calculateEuclidianGeneralizedDistance(String[][] evaluationSet, String[][] learningSet) {

		int evalLength = evaluationSet.length;
		int learningLength = learningSet.length;
		double[][] matrix = new double[evalLength][learningLength];

		for (int i = 0; i < evalLength; i++) {
			for (int j = 0; j < learningLength; j++) {
				matrix[i][j] = calculateEuclidianDistance(evaluationSet[i], learningSet[j]);
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
