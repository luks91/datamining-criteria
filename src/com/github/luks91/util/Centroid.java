/*
 * Copyright (C) 2014 Lukasz Szczyglowski, Radoslaw Trzcionkowski
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.luks91.util;

import com.github.luks91.data.ClusteredDataset;
import com.github.luks91.distance.NodesDistanceFactory.INodesDistanceCalculable;

public final class Centroid {

	private Centroid() {
	}

	/**
	 * 
	 * This Method calculates centroid of given cluster (in case when
	 * clusterIndex is given), or dataset (in case when clusterIndex is not
	 * given).
	 * 
	 * @see IT MUST BE CHECKED IF RETURNED VALUE IS DIFFERENT than -1
	 */
	public static int calculateCentroid(ClusteredDataset clusteredDataset,
			INodesDistanceCalculable nodesDistanceCalculator,
			int... clusterIndex) {

		double argMin = Double.MAX_VALUE;
		double centroidDistanceSum;
		int centroidIndex = -1;
		double[][] adjacencyMatrix = clusteredDataset.getAdjacencyMatrix();
		boolean happendAtLeastOnce = false;
		
		for (int j = 0; j < adjacencyMatrix[0].length ; j++) {
			//checking if we are looking in all clusters, or if vertex belongs to demanded cluster
			if (clusterIndex.length == 0		
					|| clusteredDataset.getClusterIndex(j) == clusterIndex[0]) {
				centroidDistanceSum = 0;
				
				
				for (int currIndex = 0 ; currIndex < adjacencyMatrix[0].length; currIndex++) {
					if (clusterIndex.length == 0
							|| clusteredDataset.getClusterIndex(currIndex) == clusterIndex[0]) {
						double tmp = nodesDistanceCalculator
						.calculate(adjacencyMatrix, currIndex, j);
						centroidDistanceSum += tmp;
						happendAtLeastOnce = true;			//to be sure that we have changed centroidDistanceSum
					}
				}
				
				
				if (centroidDistanceSum < argMin && happendAtLeastOnce) {
					argMin = centroidDistanceSum;
					centroidIndex = j;
					happendAtLeastOnce = false;
				}
			}
		}

		return centroidIndex;
	}

}
