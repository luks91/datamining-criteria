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

import com.github.luks91.util.DataUtil;

class KarateDatasetContent extends AbstractDatasetContent {
	
	private static final int KARATE_DATASET_VERTICES_AMOUNT = 34;
	
	public KarateDatasetContent(String filePath, String groundTruthPath) {
		super(filePath, groundTruthPath, KARATE_DATASET_VERTICES_AMOUNT);
	}
	
	@Override
	protected ClusteredDataset createGroundTruthDataset() throws Exception {
		/* http://spaghetti-os.blogspot.com/2014/05/zacharys-karate-club.html */
		return new ClusteredDataset(
				DataUtil.readAdjacencyMatrixFromGraphML(mDatasetFilePath), 
				DataUtil.readGroundTruthClustering(mGroundTruthFilePath, 
						KARATE_DATASET_VERTICES_AMOUNT));
	}
	
	@Override
	public String getDescription() {
		return "Zahary Karate Dataset - Weightened Undirected.";
	}
}
