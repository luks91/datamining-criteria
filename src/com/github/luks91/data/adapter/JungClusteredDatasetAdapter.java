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

import java.util.Collection;
import java.util.Set;

import com.github.luks91.data.ClusteredDataset;
import com.github.luks91.data.adapter.ClusteredDatasetAdapterFactory.ClusteredDatasetAdaptable;
import com.github.luks91.util.DataUtil.Node;

class JungClusteredDatasetAdapter implements ClusteredDatasetAdaptable<Collection<Set<Node>>>{

	@Override
	public ClusteredDataset adapt(Collection<Set<Node>> initialDataset,
			double[][] adjacencyMatrix) {
		
		int[] clusteredNumber = new int[adjacencyMatrix.length];
		int currentClusterIndex = 0;
		
		for (Set<Node> currentCluster : initialDataset) {
			for (Node node : currentCluster) {
				clusteredNumber[node.getNodeIndex()] = currentClusterIndex;
			}
			
			currentClusterIndex++;
		}
		
		return new ClusteredDataset(adjacencyMatrix, clusteredNumber);
	}
}
