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
	
	public static NodesDistanceCalculable createAdjacencyRelationDistanceCalculator() {
		return new AdjacencyRelationDistanceCalculator();
	}
	
	public static NodesDistanceCalculable createEdgePathDistanceCalculator() {
		return new EdgePathDistanceCalculator();
	}
	
	public static NodesDistanceCalculable createNeighbourOverlapDistanceCalculator() {
		return new NeighbourOverlapDistanceCalculator();
	}
	
	public static NodesDistanceCalculable createPearsonCorrelationDistanceCalculator() {
		return new PearsonCorrelationDistanceCalculator();
	}
	
	public static interface NodesDistanceCalculable {
		public double calculate(double[][] adjacencyMatrix, int i, int j);
	}
}
