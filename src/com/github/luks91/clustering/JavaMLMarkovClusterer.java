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

import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.clustering.mcl.MCL;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.distance.JaccardIndexSimilarity;

import com.github.luks91.data.ClusteredDataset;
import com.github.luks91.data.adapter.ClusteredDatasetAdapterFactory.ClusteredDatasetAdaptable;

class JavaMLMarkovClusterer extends AbstractJavaMLClusterer {

	public JavaMLMarkovClusterer(
			ClusteredDatasetAdaptable<Dataset[]> clusteringAdapter) {
		super(clusteringAdapter);
	}

	@Override
	public ClusteredDataset performClustering(String filePath, int vertexAmount)
			throws Exception {

		Dataset rawData = constructDataset(filePath, vertexAmount);
		return mClusteringAdapter.adapt(
				performMarkovClustering(rawData),
				constructAdjacencyMatrix(rawData));
	}

	private Dataset[] performMarkovClustering(Dataset rawData)
			throws IOException {

		Clusterer km = new MCL(new JaccardIndexSimilarity());
		Dataset[] clusters = km.cluster(rawData);
		return clusters;
	}

	@Override
	public String getDescription() {
		return "JavaML Markov Cluster Algorithm";
	}
}
