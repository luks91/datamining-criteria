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
import com.github.luks91.util.Centroid;

class PBMCriteriaCalculator implements ClusteringCriteriaCalculable {

	@Override
	public double calculateCriteria(ClusteredDataset clusteredDataset,
			NodesDistanceCalculable nodesDistanceCalculator) {

		
		double clustersAmount = (double) clusteredDataset.getClustersAmount();
		double maximumCentroidsDistance = calculateMaximumDistanceBetweenCentroids(
				clusteredDataset, nodesDistanceCalculator);
		double toCentroidsDistancesSum = calculateToCentroidsDistanceSum(
				clusteredDataset, nodesDistanceCalculator);
		
		return (1.0d / clustersAmount) * 
				(maximumCentroidsDistance / toCentroidsDistancesSum);
	}
	
	private double calculateMaximumDistanceBetweenCentroids(ClusteredDataset clusteredDataset,
			NodesDistanceCalculable nodesDistanceCalculator) {
		
		double maxDistance = Double.MIN_VALUE;
		int clustersCount = clusteredDataset.getClustersAmount();
		for (int cluster1 = 0; cluster1 < clustersCount; ++cluster1) {
			for (int cluster2 = 0; cluster2 < clustersCount; ++cluster2) {

				int cluster1Centroid = Centroid.calculateCentroid(clusteredDataset, 
						nodesDistanceCalculator, cluster1);
				int cluster2Centroid = Centroid.calculateCentroid(clusteredDataset, 
						nodesDistanceCalculator, cluster2);
				
				double currentDistance = nodesDistanceCalculator.calculate(
						clusteredDataset.getAdjacencyMatrix(), cluster1Centroid, 
						cluster2Centroid);
				
				if (currentDistance > maxDistance)
					maxDistance = currentDistance;
			}
		}
		return maxDistance;
	}
	
	private double calculateToCentroidsDistanceSum(ClusteredDataset clusteredDataset,
			NodesDistanceCalculable nodesDistanceCalculator) {
		
		double returnSum = 0.0d;
		int clustersAmount = clusteredDataset.getClustersAmount();
		for (int currentCluster = 0; currentCluster < clustersAmount; ++currentCluster) {
			
			int clusterCentroid = Centroid.calculateCentroid(clusteredDataset, 
					nodesDistanceCalculator, currentCluster);
			
			for (int i : clusteredDataset.getAllVertexesForCluster(currentCluster)) {
				returnSum += nodesDistanceCalculator.calculate(clusteredDataset.getAdjacencyMatrix(), 
						clusterCentroid, i);
			}
		}
		
		return returnSum;
	}
	
	@Override
	public String toString() {
		return "PBM Criteria Calculator";
	}
}
