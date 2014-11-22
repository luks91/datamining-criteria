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

class VarianceRatioCriteriaCalculator implements ClusteringCriteriaCalculable {

	@Override
	public double calculateCriteria(ClusteredDataset clusteredDataset,
			NodesDistanceCalculable nodesDistanceCalculator) {
		
		double upperFormulaParam = calculateUpperFormulaParam(clusteredDataset, 
				nodesDistanceCalculator);
		double lowerFormulaParam = calculateLowerFormulaParam(clusteredDataset, 
				nodesDistanceCalculator);
		
		int clustersAmount = clusteredDataset.getClustersAmount();
		int verticesCount = clusteredDataset.size();
		
		return (upperFormulaParam / lowerFormulaParam) 
				* ((verticesCount - clustersAmount) / (clustersAmount - 1));
	}

	private double calculateUpperFormulaParam(ClusteredDataset clusteredDataset, 
			NodesDistanceCalculable nodesDistanceCalculator) {
		
		double returnSum = 0.0d;
		int communityCentroid = Centroid.calculateCentroid(clusteredDataset, nodesDistanceCalculator);
		for (int currentCluster = 0; currentCluster < clusteredDataset.getClustersAmount(); ++ currentCluster) {
			int clusterSize = clusteredDataset.getAllVertexesForCluster(currentCluster).size();
			int clusterCentroid = Centroid.calculateCentroid(clusteredDataset, 
					nodesDistanceCalculator, currentCluster);
			returnSum += (double) clusterSize * nodesDistanceCalculator.calculate(
					clusteredDataset.getAdjacencyMatrix(), clusterCentroid, communityCentroid);
		}
		
		return returnSum;
	}
	
	private double calculateLowerFormulaParam(ClusteredDataset clusteredDataset, 
			NodesDistanceCalculable nodesDistanceCalculator) {
		
		double returnSum = 0.0d;
		for (int currentCluster = 0; currentCluster < clusteredDataset.getClustersAmount(); ++ currentCluster) {
			int clusterCentroid = Centroid.calculateCentroid(clusteredDataset, 
					nodesDistanceCalculator, currentCluster);
			
			for (Integer clusterVertex : clusteredDataset.getAllVertexesForCluster(currentCluster)) {
				returnSum += nodesDistanceCalculator.calculate(clusteredDataset.getAdjacencyMatrix(), 
						clusterVertex, clusterCentroid);
			}
		}
		
		return returnSum;
	}
	
	@Override
	public String toString() {
		return "Variance Ratio Criteria Calculator";
	}
}