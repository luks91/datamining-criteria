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

import java.util.Collection;
import java.util.Set;

import com.github.luks91.clustering.NetworkClustererFactory.NetworkGraphClusterable;
import com.github.luks91.data.adapter.ClusteredDatasetAdapterFactory.ClusteredDatasetAdaptable;
import com.github.luks91.util.DataUtil.Node;

abstract class AbstractJungClusterer implements NetworkGraphClusterable {

	protected final ClusteredDatasetAdaptable<Collection<Set<Node>>> mClusteringAdapter;

	public AbstractJungClusterer(ClusteredDatasetAdaptable<Collection<Set<Node>>> clusteringAdapter) {
		mClusteringAdapter = clusteringAdapter;
	}
}
