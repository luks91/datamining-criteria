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

class NCAAFootballDatasetContent extends AbstractDatasetContent {

	private static final int NCAA_FOOTBALL_VERTICES_AMOUNT = 180;

	public NCAAFootballDatasetContent(String filePath, String groundTruthPath) {
		super(filePath, groundTruthPath, NCAA_FOOTBALL_VERTICES_AMOUNT);
	}
	
	@Override
	protected ClusteredDataset createGroundTruthDataset() throws Exception {
		return new ClusteredDataset(
				DataUtil.readAdjacencyMatrixFromPairsFile(mDatasetFilePath, 
						NCAA_FOOTBALL_VERTICES_AMOUNT), 
				DataUtil.readGroundTruthClustering(mGroundTruthFilePath, 
						NCAA_FOOTBALL_VERTICES_AMOUNT));
	}
	
	@Override
	public String getDescription() {
		return "NCAA Football Dataset - Unweightened Undirected.";
	}
}
