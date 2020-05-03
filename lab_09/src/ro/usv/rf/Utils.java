package ro.usv.rf;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Point {
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void plus(Point p) {
		this.x += p.x;
		this.y += p.y;
	}
	
	public Point div(int x) {
		this.x /= x;
		this.y /= y;
		
		return this;
	}

	double x;
	double y;
}

public class Utils {
	static Random rand = new Random();
	static int[] classes;

	protected static int[] dynamicKernels(List<Point> points, int m) {
		int nrOfPoints = points.size();
		List<Point> kernels = initializeKernels(points, m);

		classes = new int[nrOfPoints];

		boolean done = false;
		do {
			done = true;
			int[] classMembers = new int[m];
			Point[] gravityCenters = new Point[m];
			for (int i = 0; i < m; ++i) {
				gravityCenters[i] = new Point(0,0);
			}

			for (int i = 0; i < nrOfPoints; ++i) {
				double minDistance = Double.MAX_VALUE;
				int classIndex = 0;
				for (int j = 0; j < kernels.size(); ++j) {
					double dist = calculateEuclidianDistance(points.get(i), kernels.get(j));
					if (dist < minDistance) {
						minDistance = dist;
						classIndex = j;
					}
				}

				if (classes[i] != classIndex) {
					done = false;
				}

				gravityCenters[classIndex].plus(points.get(i));
				classes[i] = classIndex;
				classMembers[classIndex]++;
			}
			System.out.println("1");
			if (!done) {
				for (int i = 0; i < m; ++i) {
					Point centroid = getCentroid(points, i, gravityCenters[i].div(classMembers[i]));
					kernels.set(i, centroid);
//					kernels.set(i, gravityCenters[i].div(classMembers[i]));
				}
			}
			
		} while(!done); 

		return classes;
	}
	
	//get closest point to gravity center from all points that has the same class
	private static Point getCentroid(List<Point> points, int classIndex, Point gravityCenter) {
		double minDist = Double.MAX_VALUE;
		Point closestPoint = new Point(0, 0);
		for (int i = 0; i < points.size(); ++i) {
			if (classes[i] == classIndex) {
				double dist = calculateEuclidianDistance(points.get(i), gravityCenter);
				
				if (dist < minDist) {
					minDist = dist;
					closestPoint = points.get(i);
				}
			}
		}
		
		return closestPoint;
	}

	private static List<Point> initializeKernels(List<Point> points, int k) {
		List<Point> kernels = new ArrayList<Point>();
		
		for (int i = 0; i < k; ++i) {
			kernels.add(points.get(rand.nextInt(points.size())));
		}
		return kernels;
	}

	private static double calculateEuclidianDistance(Point a, Point b) {
		return Math.sqrt(Math.pow(b.x - a.x, 2) + Math.pow(b.y - a.y, 2));
	}
}
