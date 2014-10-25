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

final class DistanceUtil {

	private DistanceUtil() {
	};

	public static double calculateNeighbourhoodWeightSum(
			double[][] adjacencyMatrix, int i) {
		
		double weightSum = 0.0d;

		for (int currIndex = 0; currIndex < adjacencyMatrix[i].length; ++currIndex) {
			weightSum += adjacencyMatrix[i][currIndex];
		}

		return weightSum;
	}

	public static double calculateNeighbourhoodDistanceSum(
			double[][] adjacencyMatrix, int i, int j) {
		
		return calculateNeighbourhoodDistance(adjacencyMatrix, i, j,
				NeighbourhoodDistanceType.SUM_ABS);
	}

	public static double calculateNeighbourhoodDistanceDiff(
			double[][] adjacencyMatrix, int i, int j) {
		
		return calculateNeighbourhoodDistance(adjacencyMatrix, i, j,
				NeighbourhoodDistanceType.DIFF_ABS);
	}

	public static double calculateNeighbourhoodDistancePowSum(
			double[][] adjacencyMatrix, int i, int j) {
		
		return calculateNeighbourhoodDistance(adjacencyMatrix, i, j,
				NeighbourhoodDistanceType.SUM_POW);
	}

	public static double calculateNeighbourhoodDistancePowDiff(
			double[][] adjacencyMatrix, int i, int j) {
		
		return calculateNeighbourhoodDistance(adjacencyMatrix, i, j,
				NeighbourhoodDistanceType.DIFF_POW);
	}

	private static double calculateNeighbourhoodDistance(
			double[][] adjacencyMatrix, int i, int j,
			NeighbourhoodDistanceType distanceType) {

		double neighbourhoodSum = 0.0d;

		for (int currIndex = 0; currIndex < adjacencyMatrix[i].length; ++currIndex) {
			if (currIndex == i || currIndex == j)
				continue;

			neighbourhoodSum += distanceType.createPartialDistanceSum(
					adjacencyMatrix[i][currIndex],
					adjacencyMatrix[j][currIndex]);
		}

		return neighbourhoodSum;
	}

	private static enum NeighbourhoodDistanceType {

		SUM_POW {
			@Override
			public double createPartialDistanceSum(double firstValue,
					double secondValue) {
				return Math.pow(firstValue + secondValue, 2.0d);
			}

		},
		DIFF_POW {
			@Override
			public double createPartialDistanceSum(double firstValue,
					double secondValue) {
				return Math.pow(firstValue - secondValue, 2.0d);
			}
		},
		SUM_ABS {
			@Override
			public double createPartialDistanceSum(double firstValue,
					double secondValue) {
				return Math.abs(firstValue + secondValue);
			}
		},
		DIFF_ABS {
			@Override
			public double createPartialDistanceSum(double firstValue,
					double secondValue) {
				return Math.abs(firstValue - secondValue);
			}
		};

		public abstract double createPartialDistanceSum(double firstValue,
				double secondValue);
	}
}
