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

public class PBMCriteria implements ClusteringCriteriaCalculable {

	@Override
	public double calculateCriteria(ClusteredDataset clusteredDataset,
			INodesDistanceCalculable nodesDistanceCalculator) {

		int clustersAmount = clusteredDataset.getClustersAmount();
		double[] maxClustersIndexes = getClusterIndexesWithMaxCentroidDistances(
				clusteredDataset, nodesDistanceCalculator);

		// TODO Auto-generated method stub
		return 0;
	}

	private double[] getClusterIndexesWithMaxCentroidDistances(
			ClusteredDataset clusteredDataset,
			INodesDistanceCalculable nodesDistanceCalculator) {

		int maxValueCluster1 = 0, maxValueCluster2 = 0;
		int clustersAmount = clusteredDataset.getClustersAmount();
		double maximumDistanceValue = Double.MIN_VALUE;

		for (int firstCluster = 0; firstCluster < clustersAmount - 1; ++firstCluster) {
			for (int secondCluster = firstCluster + 1; secondCluster < clustersAmount; 
					++secondCluster) {
				
				double currentDistance = 0.0d; // TODO: calculate distance to
											   // centroids

				if (maximumDistanceValue < currentDistance) {
					maximumDistanceValue = currentDistance;
					maxValueCluster1 = firstCluster;
					maxValueCluster2 = secondCluster;
				}
			}
		}

		throw new UnsupportedOperationException("Not implemented."); //TODO: implemnted
		//return new double[] { 1.0d * maxValueCluster1, 1.0d * maxValueCluster2 };
	}
	
	private double calculateSumOfDistancesFromVertexesToTheirCentroids(
			ClusteredDataset clusteredDataset,
			INodesDistanceCalculable nodesDistanceCalculator) {
		
		double totalSum = 0.0d;
		int clustersAmount = clusteredDataset.getClustersAmount();
		for (int currentCluster = 0; currentCluster < clustersAmount; ++currentCluster) {
			//TODO: implement
		}
		
		return totalSum;
	}
}
