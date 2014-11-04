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

import com.github.luks91.adapter.IClusteredDatasetAdapter;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.tools.data.FileHandler;

abstract class JavaMLClusteringBase implements IClustering {

	protected final IClusteredDatasetAdapter<Dataset[]> mClusteringAdapter;

	public JavaMLClusteringBase(
			IClusteredDatasetAdapter<Dataset[]> clusteringAdapter) {
		mClusteringAdapter = clusteringAdapter;
	}

	protected Dataset constructDataset(String filePath, int vertexAmount)
			throws IOException {
		return FileHandler.loadDataset(new File(filePath), vertexAmount, " ");
	}

	protected double[][] constructAdjacencyMatrix(Dataset rawData) {
		double[][] adjacencyMatrix = new double[34][34];

		for (int i = 0; i < 34; i++) {
			for (int j = 0; j < 34; j++)
				adjacencyMatrix[i][j] = rawData.get(i).value(j);
		}

		return adjacencyMatrix;
	}

}
