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

import java.util.List;

import com.github.luks91.criteria.ClusteringCriteriaFactory.ClusteringCriteriaCalculable;
import com.github.luks91.data.ClusteredDataset;
import com.github.luks91.distance.NodesDistanceFactory.NodesDistanceCalculable;

class PointBiserialCriteriaCalculator implements ClusteringCriteriaCalculable {

	@Override
	public double calculateCriteria(ClusteredDataset clusteredDataset,
			NodesDistanceCalculable nodesDistanceCalculator) {
		
		double M1 = calculateM1(clusteredDataset, nodesDistanceCalculator);
		double M0 = calculateM0(clusteredDataset, nodesDistanceCalculator);
		double m = calculateSmallM(clusteredDataset, nodesDistanceCalculator);
		double S = calculateS(clusteredDataset, nodesDistanceCalculator, m);
		double m1 = calculateSmallM1(clusteredDataset, nodesDistanceCalculator);
		double m0 = calculateSmallM0(clusteredDataset, nodesDistanceCalculator);
		
		return ((M1 - M0) / S) * Math.sqrt((m1 * m0) / Math.pow(m, 2.0d));
	}
	
	private double calculateM1(ClusteredDataset clusteredDataset,
			NodesDistanceCalculable nodesDistanceCalculator) {
		
		double returnSum = 0.0d;
		for (int currentCluster = 0; currentCluster < clusteredDataset.getClustersAmount(); ++currentCluster) {
			List<Integer> clusterVertices = clusteredDataset.getAllVertexesForCluster(currentCluster);
			for (Integer i : clusterVertices) {
				for (Integer j : clusterVertices) {
					returnSum += nodesDistanceCalculator.calculate(
							clusteredDataset.getAdjacencyMatrix(), i, j);
				}
			}
		}
		
		return 0.5d * returnSum;
	}
	
	private double calculateM0(ClusteredDataset clusteredDataset,
			NodesDistanceCalculable nodesDistanceCalculator) {
		
		double returnSum = 0.0d;
		for (int currentCluster = 0; currentCluster < clusteredDataset.getClustersAmount(); ++currentCluster) {
			List<Integer> clusterVertices = clusteredDataset.getAllVertexesForCluster(currentCluster);
			for (Integer i : clusterVertices) {
				for (int j = 0; j < clusteredDataset.size(); ++j) {
					if (clusteredDataset.getClusterIndex(j) == currentCluster)
						continue;
					
					returnSum += nodesDistanceCalculator.calculate(
							clusteredDataset.getAdjacencyMatrix(), i, j);
				}
			}
		}
		
		return 0.5d * returnSum;
	}
	
	private double calculateS(ClusteredDataset clusteredDataset,
			NodesDistanceCalculable nodesDistanceCalculator, double m) {
		
		double returnValue = 0.0d;
		
		for (int i=0; i < clusteredDataset.size(); ++i) {
			for (int j=0; j < clusteredDataset.size(); ++j) {
				returnValue += calculateSInnerSum(clusteredDataset, 
						nodesDistanceCalculator, i, j, m);
			}
		}
		
		return Math.sqrt((1.0d / m) * returnValue);
	}
	
	private double calculateSInnerSum(ClusteredDataset clusteredDataset,
			NodesDistanceCalculable nodesDistanceCalculator, int i, int j, 
			double m) {
		
		double returnValue = 0.0d;
		for (int i2 = 0; i2 < clusteredDataset.size(); ++i2) {
			for (int j2 = 0; j2 < clusteredDataset.size(); ++j2) {
				returnValue += nodesDistanceCalculator.calculate(
						clusteredDataset.getAdjacencyMatrix(), i2, j2);
			}
		}
		
		double nodesDistance = nodesDistanceCalculator.calculate(
				clusteredDataset.getAdjacencyMatrix(), i, j);
		
		return Math.pow(nodesDistance  - (1.0d / m) * returnValue, 2.0d);
	}
	
	private double calculateSmallM1(ClusteredDataset clusteredDataset,
			NodesDistanceCalculable nodesDistanceCalculator) {
		
		double returnSum = 0.0d;
		for (int i=0; i < clusteredDataset.getClustersAmount(); ++i) {
			double clusterCount = (double) clusteredDataset.getAllVertexesForCluster(i).size();
			returnSum += (clusterCount * (clusterCount - 1.0d)) / 2.0d;
		}
		
		return returnSum;
	}
	
	private double calculateSmallM0(ClusteredDataset clusteredDataset,
			NodesDistanceCalculable nodesDistanceCalculator) {

		double verticesCount = (double) clusteredDataset.size();
		double returnSum = 0.0d;
		for (int i=0; i < clusteredDataset.getClustersAmount(); ++i) {
			double clusterCount = (double) clusteredDataset.getAllVertexesForCluster(i).size();
			returnSum += (clusterCount * (verticesCount - clusterCount)) / 2.0d;
		}
		
		return returnSum;
	}
	
	private double calculateSmallM(ClusteredDataset clusteredDataset,
			NodesDistanceCalculable nodesDistanceCalculator) {
		
		double verticesAmount = (double) clusteredDataset.size();
		return (verticesAmount * (verticesAmount - 1.0d)) / 2.0d;
	}
	
	@Override
	public String toString() {
		return "Point Biserial Criteria Calculator";
	}
}
