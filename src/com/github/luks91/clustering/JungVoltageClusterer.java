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

package com.github.luks91.clustering;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import com.github.luks91.data.ClusteredDataset;
import com.github.luks91.data.adapter.ClusteredDatasetAdapterFactory.ClusteredDatasetAdaptable;
import com.github.luks91.util.DataUtil;
import com.github.luks91.util.DataUtil.Edge;
import com.github.luks91.util.DataUtil.Node;

import edu.uci.ics.jung.algorithms.cluster.VoltageClusterer;
import edu.uci.ics.jung.graph.Graph;

class JungVoltageClusterer extends AbstractJungClusterer {

	public JungVoltageClusterer(ClusteredDatasetAdaptable<Collection<Set<Node>>> clusteringAdapter) {
		super(clusteringAdapter);
	}
	
	@Override
	public ClusteredDataset performClustering(String filePath, int vertexAmount)
			throws IOException {
		
		try {
			Graph<Node, Edge> inputGraph = DataUtil.readGraphFromGraphML(filePath);
			VoltageClusterer<Node, Edge> clusterer = new VoltageClusterer<>(inputGraph, 3);

			return mClusteringAdapter.adapt(
					clusterer.cluster(2),
					DataUtil.readAdjacencyMatrixFromGraphML(filePath));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getDescription() {
		return "Jung Voltage Clusterer";
	}
}
