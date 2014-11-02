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

package com.github.luks91.main;

import com.github.luks91.adapter.JavaMLClusteredDatasetAdapter;
import com.github.luks91.clustering.JavaMLKNodeClustering;
import com.github.luks91.criteria.ClusteringCriteriaFactory;
import com.github.luks91.criteria.ClusteringCriteriaFactory.ClusteringCriteriaCalculable;
import com.github.luks91.data.ClusteredDataset;
import com.github.luks91.distance.NodesDistanceFactory;

public class Main {
	public static void main(String[] args) throws Exception {
		ClusteredDataset clusteredDataset = new JavaMLKNodeClustering(
				new JavaMLClusteredDatasetAdapter()).performClustering(
				"dataset/karate2.txt", 34);

		ClusteringCriteriaCalculable criteriaCalculator = ClusteringCriteriaFactory
				.createModularityCriteriaCalculator();
		double criteria = criteriaCalculator
				.calculateCriteria(clusteredDataset, NodesDistanceFactory
						.createNeighbourOverlapDistanceCalculator());

		System.out.println("Criteria: " + criteria);
	}

}
