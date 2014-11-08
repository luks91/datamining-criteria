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

package com.github.luks91.criteria;

import java.util.Arrays;

import com.github.luks91.criteria.ClusteringCriteriaFactory.ClusteringCriteriaCalculable;
import com.github.luks91.data.ClusteredDataset;
import com.github.luks91.distance.NodesDistanceFactory.NodesDistanceCalculable;

class CIndexCriteriaCalculator implements ClusteringCriteriaCalculable {

	@Override
	public double calculateCriteria(ClusteredDataset clusteredDataset,
			NodesDistanceCalculable nodesDistanceCalculator) {

		int datasetSize = clusteredDataset.size();

		double distances[] = new double[datasetSize * (datasetSize - 1) / 2];
		double theta = 0.0d;

		int currentPair = 0;
		int clusteredPairsAmount = 0;
		for (int i = 0; i < datasetSize - 1; ++i) {
			for (int j = i + 1; j < datasetSize; ++j) {
				double nodesDistance = nodesDistanceCalculator.calculate(
						clusteredDataset.getAdjacencyMatrix(), i, j);
				distances[currentPair] = nodesDistance;
				currentPair++;

				if (verticesAreInTheSameClaster(clusteredDataset, i, j)) {
					theta += nodesDistance;
					clusteredPairsAmount++;
				}
			}
		}

		Arrays.sort(distances);
		double minTheta = 0.0d, maxTheta = 0.0d;
		for (int i = 0; i < clusteredPairsAmount; ++i) {
			minTheta += distances[i];
			maxTheta += distances[(distances.length - 1) - i];
		}
		
		return (theta - minTheta) / (maxTheta - minTheta);
	}

	private boolean verticesAreInTheSameClaster(ClusteredDataset dataset,
			int i, int j) {
		return dataset.getClusterIndex(i) == dataset.getClusterIndex(j);
	}
	
	@Override
	public String toString() {
		return "C Index Criteria Calculator";
	}
}
