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

import com.github.luks91.data.ClusteredDataset;
import com.github.luks91.data.adapter.ClusteredDatasetAdapterFactory;

public final class NetworkClustererFactory {

	private NetworkClustererFactory() {
	}

	public static NetworkGraphClusterable createJungVoltageClusterer() {
		return new JungVoltageClusterer(
				ClusteredDatasetAdapterFactory.createJungClusteredDatasetAdapter());
	}

	public static NetworkGraphClusterable createJungBicomponentClusterer() {
		return new JungBicomponentClusterer(
				ClusteredDatasetAdapterFactory.createJungClusteredDatasetAdapter());
	}

	public static NetworkGraphClusterable createJungEdgeBetweennessClusterer() {
		return new JungEdgeBetweennessClusterer(
				ClusteredDatasetAdapterFactory.createJungClusteredDatasetAdapter());
	}
	
	public static NetworkGraphClusterable createJavaMLKMeansClusterer() {
		return new JavaMLKMeansClusterer(
				ClusteredDatasetAdapterFactory.createJavaMLClusteredDatasetAdapter());
	}

	public static NetworkGraphClusterable createJavaMLMarkovClusterer() {
		return new JavaMLMarkovClusterer(
				ClusteredDatasetAdapterFactory.createJavaMLClusteredDatasetAdapter());
	}
	
	public static NetworkGraphClusterable createJavaMLSOMClusterer() {
		return new JavaMLSOMClusterer(
				ClusteredDatasetAdapterFactory.createJavaMLClusteredDatasetAdapter());
	}
	
	public static NetworkGraphClusterable createJavaMLDensityBasedSpatialClusterer() {
		return new JavaMLDensityBasedSpatialClusterer(
				ClusteredDatasetAdapterFactory.createJavaMLClusteredDatasetAdapter());
	}
	
	public static interface NetworkGraphClusterable {

		public ClusteredDataset performClustering(String filePath,
				int vertexAmount) throws Exception;

		public String getDescription();
	}
}
