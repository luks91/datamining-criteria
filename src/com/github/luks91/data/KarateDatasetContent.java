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

package com.github.luks91.data;

import java.util.ArrayList;
import java.util.List;

import com.github.luks91.data.DatasetContentFactory.IDatasetContent;
import com.github.luks91.util.DataUtil;

class KarateDatasetContent implements IDatasetContent {
	private static final int KARATE_DATASET_VERTICES_AMOUNT = 34;
	
	private final String mDatasetFilePath;

	public KarateDatasetContent(String filePath) {
		mDatasetFilePath = filePath;
	}

	@Override
	public String getFilePath() {
		return mDatasetFilePath;
	}
	
	@Override
	public int getVerticesAmount() {
		return KARATE_DATASET_VERTICES_AMOUNT;
	}
	
	@Override
	public ClusteredDataset getGroundTruthDataset() throws Exception {
		/* http://spaghetti-os.blogspot.com/2014/05/zacharys-karate-club.html */
		int[] clusteredNumber = new int[KARATE_DATASET_VERTICES_AMOUNT];
		List<List<Integer>> clustersMapping = new ArrayList<>();
		
		List<Integer> firstClusterVertices = new ArrayList<>();
		List<Integer> secondClusterVertices = new ArrayList<>();
		firstClusterVertices.add(0);
		firstClusterVertices.add(1);
		firstClusterVertices.add(2);
		firstClusterVertices.add(3);
		firstClusterVertices.add(4);
		firstClusterVertices.add(5);
		firstClusterVertices.add(6);
		firstClusterVertices.add(7);
		firstClusterVertices.add(8);
		secondClusterVertices.add(9);
		firstClusterVertices.add(10);
		firstClusterVertices.add(11);
		firstClusterVertices.add(12);
		firstClusterVertices.add(13);
		secondClusterVertices.add(14);
		secondClusterVertices.add(15);
		firstClusterVertices.add(16);
		firstClusterVertices.add(17);
		secondClusterVertices.add(18);
		firstClusterVertices.add(19);
		secondClusterVertices.add(20);
		firstClusterVertices.add(21);
		secondClusterVertices.add(22);
		secondClusterVertices.add(23);
		secondClusterVertices.add(24);
		secondClusterVertices.add(25);
		secondClusterVertices.add(26);
		secondClusterVertices.add(27);
		secondClusterVertices.add(28);
		secondClusterVertices.add(29);
		secondClusterVertices.add(30);
		secondClusterVertices.add(31);
		secondClusterVertices.add(32);
		secondClusterVertices.add(33);
		clustersMapping.add(firstClusterVertices);
		clustersMapping.add(secondClusterVertices);
		
		for (Integer vertex : firstClusterVertices)
			clusteredNumber[vertex] = 0;

		for (Integer vertex : secondClusterVertices)
			clusteredNumber[vertex] = 1;

		return new ClusteredDataset(DataUtil.readAdjacencyMatrixFromGraphML(mDatasetFilePath), 
				clusteredNumber);
	}
	
	@Override
	public String toString() {
		return "Zahary Karate Dataset - Weightened Undirected.";
	}
}
