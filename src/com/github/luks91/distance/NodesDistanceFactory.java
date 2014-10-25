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

public final class NodesDistanceFactory {

	private NodesDistanceFactory(){}
	
	public static INodesDistanceCalculable createAdjacencyRelationDistanceCalculator() {
		return new AdjacencyRelationDistanceCalculator();
	}
	
	public static INodesDistanceCalculable createEdgePathDistanceCalculator() {
		return new EdgePathDistanceCalculator();
	}

	public static INodesDistanceCalculable createIClosenessDistanceCalculator() {
		return new IClosenessDistanceCalculator();
	}
	
	public static INodesDistanceCalculable createNeighbourOverlapDistanceCalculator() {
		return new NeighbourOverlapDistanceCalculator();
	}
	
	public static INodesDistanceCalculable createPearsonCorrelationDistanceCalculator() {
		return new PearsonCorrelationDistanceCalculator();
	}
	
	public static INodesDistanceCalculable createShortestPathDistanceCalculator() {
		return new ShortestPathDistanceCalculator();
	}
	
	public static interface INodesDistanceCalculable {
		public double calculate(double[][] adjacencyMatrix, int i, int j);
	}
}
