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

import java.io.File;
import java.io.IOException;

import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.clustering.KMeans;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.tools.data.FileHandler;

import com.github.luks91.adapter.IClusteredDatasetAdapter;
import com.github.luks91.data.ClusteredDataset;

public class JavaMLKNodeClustering implements IClustering {

	private final IClusteredDatasetAdapter<Dataset[]> mClusteringAdapter;

	public JavaMLKNodeClustering(
			IClusteredDatasetAdapter<Dataset[]> clusteringAdapter) {
		mClusteringAdapter = clusteringAdapter;
	}

	@Override
	public ClusteredDataset performClustering(String filePath, int vertexAmount)
			throws IOException {

		Dataset rawData = FileHandler.loadDataset(new File(filePath),
				vertexAmount, " ");

		return mClusteringAdapter.translateDataset(
				performKNodeClustering(rawData),
				constructAdjacencyMatrix(rawData));

	}

	private Dataset[] performKNodeClustering(Dataset rawData)
			throws IOException {
		Clusterer km = new KMeans();
		Dataset[] clusters = km.cluster(rawData);
		return clusters;
	}

	private double[][] constructAdjacencyMatrix(Dataset rawData) {
		double[][] adjacencyMatrix = new double[34][34];

		for (int i = 0; i < 34; i++) {
			for (int j = 0; j < 34; j++)
				adjacencyMatrix[i][j] = rawData.get(i).value(j);
		}

		return adjacencyMatrix;
	}

	@Override
	public String getDescription() {
		return "JavaML KNode Clustering";
	}
	
}
