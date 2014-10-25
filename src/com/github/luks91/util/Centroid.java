package com.github.luks91.util;

import com.github.luks91.distance.NodesDistanceFactory.INodesDistanceCalculable;

public final class Centroid {

	private Centroid() {
	}

	public static int calculateCentroid(INodesDistanceCalculable distance,
			double[][] adjacencyMatrix, int i) {

		double argMin = Double.MAX_VALUE;
		double centroidDistanceSum;
		int centroidIndex = -1;

		for (int j = 0; j < adjacencyMatrix[i].length - 1; j++) {
			centroidDistanceSum = 0;
			for (int currIndex = j + 1; currIndex < adjacencyMatrix[i].length - 1; currIndex++) {
				centroidDistanceSum += distance.calculate(adjacencyMatrix,
						currIndex, j);
			}
			if (centroidDistanceSum < argMin) {
				argMin = centroidDistanceSum;
				centroidIndex = j;
			}
		}

		return centroidIndex;
	}

}
