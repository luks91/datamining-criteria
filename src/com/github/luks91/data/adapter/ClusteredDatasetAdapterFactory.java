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

import net.sf.javaml.core.Dataset;

import org.gephi.clustering.api.Cluster;

import com.github.luks91.data.ClusteredDataset;
import com.github.luks91.util.DataUtil.Node;

public final class ClusteredDatasetAdapterFactory {

	private ClusteredDatasetAdapterFactory() {
	}

	public static ClusteredDatasetAdaptable<Cluster[]> createGephiClusteredDatasetAdapter() {
		return new GephiClusteredDatasetAdapter();
	}

	public static ClusteredDatasetAdaptable<Collection<Set<Node>>> createJungClusteredDatasetAdapter() {
		return new JungClusteredDatasetAdapter(); 
	}

	public static ClusteredDatasetAdaptable<Dataset[]> createJavaMLClusteredDatasetAdapter() {
		return new JavaMLClusteredDatasetAdapter();
	}
	
	public static interface ClusteredDatasetAdaptable<InitialType> {

		public ClusteredDataset adapt(InitialType initialDataset,
				double[][] adjacencyMatrix);
	}
}
