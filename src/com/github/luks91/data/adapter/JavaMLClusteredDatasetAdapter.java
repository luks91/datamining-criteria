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

package com.github.luks91.data.adapter;

import java.util.ArrayList;
import java.util.List;

import net.sf.javaml.core.Dataset;

import com.github.luks91.data.ClusteredDataset;
import com.github.luks91.data.adapter.ClusteredDatasetAdapterFactory.ClusteredDatasetAdaptable;

class JavaMLClusteredDatasetAdapter implements ClusteredDatasetAdaptable<Dataset[]>{

	@Override
	public ClusteredDataset adapt(Dataset[] clusteredDataset, double[][] adjacencyMatrix) {
		return adaptToClusteredDataset(clusteredDataset, adjacencyMatrix);
	}


	private static ClusteredDataset adaptToClusteredDataset(Dataset[] clusters,
			double[][] adjacencyMatrix) {
		
		int[] clusterIndexes = new int[34];

		for (int clustersIndex = 0; clustersIndex < clusters.length; clustersIndex++) {
			List<Integer> clusterContent = new ArrayList<>();

			for (int nodeIndex = 0; nodeIndex < clusters[clustersIndex].size(); nodeIndex++) {
				clusterContent.add(nodeIndex);
				clusterIndexes[(int) clusters[clustersIndex].get(nodeIndex).classValue()] = clustersIndex;
			}
		}

		return new ClusteredDataset(adjacencyMatrix, clusterIndexes);
	}
}
