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
import java.util.List;

import com.github.luks91.criteria.ClusteringCriteriaFactory.ClusteringCriteriaCalculable;
import com.github.luks91.data.ClusteredDataset;
import com.github.luks91.distance.NodesDistanceFactory.NodesDistanceCalculable;

class DunnIndexCriteriaCalculator implements ClusteringCriteriaCalculable {

	@Override
	public double calculateCriteria(ClusteredDataset clusteredDataset,
			NodesDistanceCalculable nodesDistanceCalculator) {

		int clustersAmount = clusteredDataset.getClustersAmount();
		
		double lowerFormulaParam = calculateLowerFormulaParam(
				clusteredDataset, nodesDistanceCalculator);
		
		double[] allTheValues = new double[clustersAmount * (clustersAmount - 1)];
		int currentIndex = 0;
		for (int i = 0; i < clustersAmount; ++i) {
			for (int j = 0; j < clustersAmount; ++j) {
				if (i == j)
					continue;

				allTheValues[currentIndex++] = calculateDelta(
						clusteredDataset, nodesDistanceCalculator, i, j)
						/ lowerFormulaParam;
			}
		}

		Arrays.sort(allTheValues);
		return allTheValues[allTheValues.length - 1];
	}	
	
	private double calculateLowerFormulaParam(ClusteredDataset clusteredDataset,
			NodesDistanceCalculable nodesDistanceCalculator) {
		
		double maxSumForAllClusters = Double.MIN_VALUE;
		int clustersCount = clusteredDataset.getClustersAmount();
	
		for (int currentCluster=0; currentCluster < clustersCount; ++currentCluster) {
			
			double maxSumForCluster = Double.MIN_VALUE;
			List<Integer> clusterIndices = clusteredDataset.getAllVertexesForCluster(currentCluster);
			for (int i : clusterIndices) {
				for (int j : clusterIndices) {
					double currentDistance = nodesDistanceCalculator.calculate(
							clusteredDataset.getAdjacencyMatrix(), i, j);
					
					if (maxSumForCluster < currentDistance)
						maxSumForCluster = currentDistance;
				}
			}
			if (maxSumForCluster > maxSumForAllClusters)
				maxSumForAllClusters = maxSumForCluster;
		}
		
		return maxSumForAllClusters;
	}
	
	private double calculateDelta(ClusteredDataset clusteredDataset,
			NodesDistanceCalculable nodesDistanceCalculator, int firstCluster,
			int secondCluster) {
		
		double minimumDistance = Double.MAX_VALUE;
		List<Integer> firstClusterIndices = clusteredDataset.getAllVertexesForCluster(firstCluster);
		List<Integer> secondClusterIndices = clusteredDataset.getAllVertexesForCluster(secondCluster);
		
		for (int i : firstClusterIndices) {
			for (int j : secondClusterIndices) {
				double distance = nodesDistanceCalculator.calculate(
						clusteredDataset.getAdjacencyMatrix(), i, j);
				
				if (distance < minimumDistance)
					minimumDistance = distance;
			}
		}
		return minimumDistance;
	}
	
	@Override
	public String toString() {
		return "Dunn Index Criteria Calculator";
	}
}
