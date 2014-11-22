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

class ZStatisticsCriteriaCalculator implements ClusteringCriteriaCalculable {

	@Override
	public double calculateCriteria(ClusteredDataset clusteredDataset,
			NodesDistanceCalculable nodesDistanceCalculator) {
		
		double theta = calculateTheta(clusteredDataset, nodesDistanceCalculator);
		double eForTheta = calculateEForTheta(clusteredDataset, nodesDistanceCalculator);
		double varForTheta = calculateVarForTheta(clusteredDataset, nodesDistanceCalculator);
		
		return (theta - eForTheta) / Math.sqrt(varForTheta);
	}

	private double calculateTheta(ClusteredDataset clusteredDataset,
			NodesDistanceCalculable nodesDistanceCalculator) {
		
		double totalSum = 0.0d;
		int clustersCount = clusteredDataset.getClustersAmount();
		
		for (int currentCluster = 0; currentCluster < clustersCount; ++currentCluster) {
			for (int i : clusteredDataset.getAllVertexesForCluster(currentCluster)) {
				for (int j : clusteredDataset.getAllVertexesForCluster(currentCluster)) {
					totalSum += nodesDistanceCalculator.calculate(
							clusteredDataset.getAdjacencyMatrix(), i, j);
				}
			}
		}
		
		return 0.5d * totalSum;
	}
	
	private double calculateEForTheta(ClusteredDataset clusteredDataset,
			NodesDistanceCalculable nodesDistanceCalculator) {
		
		int verticesCount = clusteredDataset.size();
		double totalSum = 0.0d;
		
		for (int i=0; i < verticesCount; ++i) {
			for (int j=0; j < verticesCount; ++j) {
				totalSum += nodesDistanceCalculator.calculate(
						clusteredDataset.getAdjacencyMatrix(), i, j);
			}
		}
		
		return (1.0d / ((double) verticesCount)) * totalSum;
	}
	
	private double calculateVarForTheta(ClusteredDataset clusteredDataset,
			NodesDistanceCalculable nodesDistanceCalculator) {
		
		double doubleN = (double) clusteredDataset.size();
		double totalSum = calculateVarTotalSum(clusteredDataset, nodesDistanceCalculator);
		double partialPowSum = calculateVarPartialPowSum(clusteredDataset, nodesDistanceCalculator);
		double powSum = calculateVarPowSum(clusteredDataset, nodesDistanceCalculator);
		
		double firstSumParam = (Math.pow(totalSum, 2.0d) - 2.0d * partialPowSum)
				/ (doubleN * (doubleN - 1));
		double secondSumParam = Math.pow(totalSum, 2.0d) / Math.pow(doubleN, 2.0d);
		double thirdSumParam = powSum / doubleN;
		
		return firstSumParam - secondSumParam + thirdSumParam;
	}
	
	private double calculateVarTotalSum(ClusteredDataset clusteredDataset,
			NodesDistanceCalculable nodesDistanceCalculator) {
		
		int verticesCount = clusteredDataset.size();
		double totalSum = 0.0d;
		
		for (int i=0; i < verticesCount; ++i) {
			for (int j=0; j < verticesCount; ++j) {
				totalSum += nodesDistanceCalculator.calculate(
						clusteredDataset.getAdjacencyMatrix(), i, j);
			}
		}
		
		return totalSum;
	}
	
	private double calculateVarPartialPowSum(ClusteredDataset clusteredDataset,
			NodesDistanceCalculable nodesDistanceCalculator) {
		
		int verticesCount = clusteredDataset.size();
		double totalSum = 0.0d;
		
		for (int i=0; i < verticesCount; ++i) {
			double partialSum = 0.0d;
			for (int j=0; j < verticesCount; ++j) {
				partialSum += nodesDistanceCalculator.calculate(
						clusteredDataset.getAdjacencyMatrix(), i, j);
			}
			totalSum += Math.pow(partialSum, 2.0d);
		}
		
		return totalSum;
	}
	
	private double calculateVarPowSum(ClusteredDataset clusteredDataset,
			NodesDistanceCalculable nodesDistanceCalculator) {
		
		int verticesCount = clusteredDataset.size();
		double totalSum = 0.0d;
		
		for (int i=0; i < verticesCount; ++i) {
			for (int j=0; j < verticesCount; ++j) {
				totalSum += Math.pow(nodesDistanceCalculator.calculate(
						clusteredDataset.getAdjacencyMatrix(), i, j), 2.0d);
			}
		}
		
		return totalSum;
	}
	
	@Override
	public String toString() {
		return "Z-Statistics Criteria Calculator";
	}
}
