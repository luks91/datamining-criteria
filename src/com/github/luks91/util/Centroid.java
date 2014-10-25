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

package com.github.luks91.util;

import com.github.luks91.distance.NodesDistanceFactory.INodesDistanceCalculable;

public final class Centroid {

	private Centroid() {
	}

	public static int calculateCentroid(INodesDistanceCalculable distance,
			double[][] adjacencyMatrix, int i) {

		double argMin = Double.MAX_VALUE;
		double centroidDistanceSum;
		int centroidIndex = -1;

		for (int j = 0; j < adjacencyMatrix[i].length - 1; j++) {
			centroidDistanceSum = 0;
			for (int currIndex = j + 1; currIndex < adjacencyMatrix[i].length; currIndex++) {
				centroidDistanceSum += distance.calculate(adjacencyMatrix,
						currIndex, j);
			}
			if (centroidDistanceSum < argMin) {
				argMin = centroidDistanceSum;
				centroidIndex = j;
			}
		}

		return centroidIndex;
	}

}
