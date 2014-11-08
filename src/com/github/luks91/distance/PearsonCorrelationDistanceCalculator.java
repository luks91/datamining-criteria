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

package com.github.luks91.distance;

import com.github.luks91.distance.NodesDistanceFactory.NodesDistanceCalculable;

/** Verified */
class PearsonCorrelationDistanceCalculator implements NodesDistanceCalculable {

	@Override
	public double calculate(double[][] adjacencyMatrix, int i, int j) {
		return 1.0d - calculateUpperValue(adjacencyMatrix, i, j)
				/ calculateLowerValue(adjacencyMatrix, i, j);
	}

	private double calculateUpperValue(double[][] adjacencyMatrix, int i, int j) {
		double upperValue = 0.0d;

		double nodesAmount = 1.0d * adjacencyMatrix[i].length;
		double iAverage = DistanceUtil.calculateNeighbourhoodWeightSum(
				adjacencyMatrix, i) / nodesAmount;
		double jAverage = DistanceUtil.calculateNeighbourhoodWeightSum(
				adjacencyMatrix, j) / nodesAmount;
		
		for (int currIndex = 0; currIndex < adjacencyMatrix[i].length; ++currIndex) {
			upperValue += (adjacencyMatrix[i][currIndex] - iAverage)
					* (adjacencyMatrix[j][currIndex] - jAverage);
		}
		
		return upperValue;
	}

	private double calculateLowerValue(double[][] adjacencyMatrix, int i, int j) {
		double nodesAmount = 1.0 * adjacencyMatrix[i].length;
		return nodesAmount * calculateVariance(adjacencyMatrix, i)
				* calculateVariance(adjacencyMatrix, j);
	}

	private double calculateVariance(double[][] adjacencyMatrix, int i) {
		double partialSum = 0.0d;
		double nodesAmount = 1.0 * adjacencyMatrix[i].length;
		double iAverage = DistanceUtil.calculateNeighbourhoodWeightSum(
				adjacencyMatrix, i) / nodesAmount;

		for (int currIndex = 0; currIndex < adjacencyMatrix[i].length; ++currIndex) {
			partialSum += Math.pow(adjacencyMatrix[i][currIndex] - iAverage,
					2.0d);
		}

		return Math.sqrt(partialSum / nodesAmount);
	}
	
	@Override
	public String toString() {
		return "Pearson Correlation Distance Calculator";
	}
}