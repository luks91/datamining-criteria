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

package com.github.luks91.data;

public final class ClusteredDataset {

	public static final int NON_CLUSTERED_VERTEX_CLUSTER_NUMBER = -1;
	
	private final double[][] mAdjacencyMatrix;
	private final int[] mClusterNumber;
	
	public ClusteredDataset(double[][] adjacencyMatrix, int[] clusteredNumber) {
		mAdjacencyMatrix = adjacencyMatrix;
		mClusterNumber = clusteredNumber;
	}
	
	public int size() {
		return mAdjacencyMatrix[0].length;
	}
	
	public double[][] getAdjacencyMatrix() {
		return null;
	}
	
	public int getClustersAmount() {
		return 0;
	}
	
	public int getClusterIndex(int vertex) {
		return mClusterNumber[vertex];
	}
}
