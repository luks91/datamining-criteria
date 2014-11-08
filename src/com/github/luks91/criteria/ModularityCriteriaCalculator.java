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

import com.github.luks91.criteria.ClusteringCriteriaFactory.ClusteringCriteriaCalculable;
import com.github.luks91.data.ClusteredDataset;
import com.github.luks91.distance.NodesDistanceFactory.NodesDistanceCalculable;
import com.github.luks91.util.ArrayUtil;

/**
 * Modularity is the fraction of the edges that fall within the given groups
 * minus the expected such fraction if edges were distributed at random. The
 * value of the modularity lies in the range [−1/2,1). It is positive if the
 * number of edges within groups exceeds the number expected on the basis of
 * chance. For a given division of the network's vertices into some modules,
 * modularity reflects the concentration of edges within modules compared with
 * random distribution of links between all nodes regardless of modules.
 */
class ModularityCriteriaCalculator implements ClusteringCriteriaCalculable {

	@Override
	public double calculateCriteria(ClusteredDataset clusteredDataset,
			NodesDistanceCalculable nodesDistanceCalculator) {

		int datasetSize = clusteredDataset.size();

		double[][] adjacencyMatrix = clusteredDataset.getAdjacencyMatrix();
		double[] clustersSums = new double[clusteredDataset.getClustersAmount()];
		double E = calculateE(adjacencyMatrix, datasetSize);

		for (int i = 0; i < datasetSize - 1; ++i) {
			for (int j = i + 1; j < datasetSize; ++j) {
				if (verticesAreInTheSameClaster(clusteredDataset, i, j)) {
					int clusterNumber = clusteredDataset.getClusterIndex(i);
					clustersSums[clusterNumber] += adjacencyMatrix[i][j]
							- (calculateMultSums(adjacencyMatrix, i, j,
									datasetSize, clusteredDataset) / (2.0d * E));
				}
			}
		}

		return (1.0d / (2.0d * E)) * ArrayUtil.sumArrayElements(clustersSums);
	}

	private double calculateE(double[][] adjacencyMatrix, int datasetSize) {
		double returnSum = 0.0d;

		for (int i = 0; i < datasetSize - 1; ++i) {
			for (int j = i + 1; j < datasetSize; ++j) {
				returnSum += adjacencyMatrix[i][j];
			}
		}

		return returnSum;
	}

	private boolean verticesAreInTheSameClaster(ClusteredDataset dataset,
			int i, int j) {

		return dataset.getClusterIndex(i) == dataset.getClusterIndex(j);
	}

	private double calculateMultSums(double[][] adjacencyMatrix, int i, int j,
			int datasetSize, ClusteredDataset clusteredDataset) {

		double iSum = 0.0d;
		double jSum = 0.0d;

		for (int currIndex = 0; currIndex < datasetSize; ++currIndex) {
				iSum += adjacencyMatrix[i][currIndex];
				jSum += adjacencyMatrix[currIndex][j];
		}

		return iSum * jSum;
	}

	@Override
	public String toString() {
		return "Modularity Q Criteria Calculator";
	}
}
