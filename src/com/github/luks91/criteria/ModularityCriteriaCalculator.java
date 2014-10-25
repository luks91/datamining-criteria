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
import com.github.luks91.distance.NodesDistanceFactory.INodesDistanceCalculable;

class ModularityCriteriaCalculator implements ClusteringCriteriaCalculable {

	@Override
	public double calculateCriteria(ClusteredDataset clusteredDataset,
			INodesDistanceCalculable nodesDistanceCalculator) {
		
		int datasetSize = clusteredDataset.size();
		
		double[][] adjacencyMatrix = clusteredDataset.getAdjacencyMatrix();
		double[] clustersSums = new double[clusteredDataset.getClustersAmount()];
		double E = calculateE(adjacencyMatrix, datasetSize);
		
		for (int i = 0; i < datasetSize - 1; ++i) {
			for (int j = i + 1; j < datasetSize - 1; ++j) {
				if (verticesAreInTheSameClaster(clusteredDataset, i, j)) {
					int clusterNumber = clusteredDataset.getClusterIndex(i);
					clustersSums[clusterNumber] += adjacencyMatrix[i][j]
							- (calculateMultSums(adjacencyMatrix, i, j,
									datasetSize) / 2 * E);
				}
			}
		}
		
		return 0.5d * E * sumDoubleArray(clustersSums);
	}
	
	private double calculateE(double[][] adjacencyMatrix, int datasetSize) {
		double returnSum = 0.0d;
		
		for (int i=0; i < datasetSize - 1; ++i) {
			for (int j=i+1; i < datasetSize - 1; ++i) {
				returnSum += adjacencyMatrix[i][j];
			}
		}
		
		return 0.5d * returnSum;
	}

	private boolean verticesAreInTheSameClaster(ClusteredDataset dataset,
			int i, int j) {
		return dataset.getClusterIndex(i) == dataset.getClusterIndex(j);
	}
	
	private double calculateMultSums(double[][] adjacencyMatrix, int i, int j, int datasetSize) {
		double iSum = 0.0d;
		double jSum = 0.0d;
		
		for (int currIndex = 0; currIndex < datasetSize; ++currIndex) {
			iSum += adjacencyMatrix[i][currIndex];
			jSum += adjacencyMatrix[currIndex][j];
		}
		
		return iSum * jSum;
	}
	
	private double sumDoubleArray(double[] array) {
		double sum = 0.0d;
		for (int i=0; i < array.length; ++i) {
			sum += array[i];
		}
		return sum;
	}
}
