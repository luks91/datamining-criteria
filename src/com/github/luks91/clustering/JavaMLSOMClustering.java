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
import net.sf.javaml.clustering.SOM;
import net.sf.javaml.core.Dataset;

import com.github.luks91.adapter.IClusteredDatasetAdapter;
import com.github.luks91.data.ClusteredDataset;

public class JavaMLSOMClustering extends JavaMLClusteringBase {

	public JavaMLSOMClustering(
			IClusteredDatasetAdapter<Dataset[]> clusteringAdapter) {
		super(clusteringAdapter);
	}

	@Override
	public ClusteredDataset performClustering(String filePath, int vertexAmount)
			throws IOException {

		Dataset rawData = constructDataset(filePath, vertexAmount);
		return mClusteringAdapter.translateDataset(
				performDensityBasedSpatialClustering(rawData),
				constructAdjacencyMatrix(rawData));
	}

	private Dataset[] performDensityBasedSpatialClustering(Dataset rawData)
			throws IOException {

		Clusterer km = new SOM();
		Dataset[] clusters = km.cluster(rawData);
		return clusters;
	}

	@Override
	public String getDescription() {
		return "JavaML Self Organizing Maps Clustering";
	}

}
