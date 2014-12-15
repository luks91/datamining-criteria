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

import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;
import net.sf.javaml.filter.normalize.NormalizeMidrange;

import com.github.luks91.clustering.NetworkClustererFactory.NetworkGraphClusterable;
import com.github.luks91.data.adapter.ClusteredDatasetAdapterFactory.ClusteredDatasetAdaptable;
import com.github.luks91.util.DataUtil;

abstract class AbstractJavaMLClusterer implements NetworkGraphClusterable {

	protected final ClusteredDatasetAdaptable<Dataset[]> mClusteringAdapter;

	public AbstractJavaMLClusterer(
			ClusteredDatasetAdaptable<Dataset[]> clusteringAdapter) {
		mClusteringAdapter = clusteringAdapter;
	}

	protected Dataset constructDataset(String filePath, int vertexAmount)
			throws Exception {

		double[][] adjacencyMatrix = DataUtil.readAdjacencyMatrixFromGraphML(filePath);
		Dataset data = new DefaultDataset();
		
		for (int i = 0; i < adjacencyMatrix.length; ++i) {
			Instance currentInstance = new DenseInstance(adjacencyMatrix[i], i);
			data.add(currentInstance);
		}
		
		NormalizeMidrange nmr=new NormalizeMidrange(0.5, 1.0);
		nmr.build(data);
		nmr.filter(data);
		
		return data;
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
