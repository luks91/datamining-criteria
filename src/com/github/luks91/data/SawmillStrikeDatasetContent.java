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

class SawmillStrikeDatasetContent extends AbstractDatasetContent {

	private static final int SAWMILL_STRIKE_VERTICES_AMOUNT = 24;

	public SawmillStrikeDatasetContent(String filePath, String groundTruthPath) {
		super(filePath, groundTruthPath, SAWMILL_STRIKE_VERTICES_AMOUNT);
	}
	
	@Override
	public ClusteredDataset getGroundTruthDataset() throws Exception {
		return new ClusteredDataset(
				DataUtil.readAdjacencyMatrixFromPairsFile(mDatasetFilePath, 
						SAWMILL_STRIKE_VERTICES_AMOUNT), 
				DataUtil.readGroundTruthClustering(mGroundTruthFilePath, 
						SAWMILL_STRIKE_VERTICES_AMOUNT));
	}
	
	@Override
	public String getDescription() {
		return "Sawmill Strike Dataset - Unweightened Undirected.";
	}
}
