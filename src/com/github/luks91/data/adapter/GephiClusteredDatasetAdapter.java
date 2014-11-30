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

import org.gephi.clustering.api.Cluster;
import org.gephi.graph.api.Node;

import com.github.luks91.data.ClusteredDataset;
import com.github.luks91.data.adapter.ClusteredDatasetAdapterFactory.ClusteredDatasetAdaptable;

class GephiClusteredDatasetAdapter implements
		ClusteredDatasetAdaptable<Cluster[]> {

	@Override
	public ClusteredDataset adapt(Cluster[] initialDataset,
			double[][] adjacencyMatrix) {

		int[] clusteredNumber = new int[adjacencyMatrix.length];

		for (int clusterIndex = 0; clusterIndex < initialDataset.length; clusterIndex++) {
			Cluster currentCluster = initialDataset[clusterIndex];
			
			List<Integer> clusterVertexes = new ArrayList<Integer>();
			for (Node clusterNode : currentCluster.getNodes()) {
				clusterVertexes.add(clusterNode.getId());
				clusteredNumber[clusterNode.getId()] = clusterIndex;
			}
		}

		return new ClusteredDataset(adjacencyMatrix, clusteredNumber);
	}
}
