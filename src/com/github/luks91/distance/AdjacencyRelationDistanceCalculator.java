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
class AdjacencyRelationDistanceCalculator implements NodesDistanceCalculable {

	@Override
	public double calculate(double[][] adjacencyMatrix, int i, int j) {
		return Math.sqrt(
				DistanceUtil.calculateNeighbourhoodDistancePowDiff(
						adjacencyMatrix, i, j));
	}
	
	@Override
	public String toString() {
		return "Adjacency Relation Distance Calculator";
	}
}
