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

public final class DatasetContentFactory {
	private DatasetContentFactory(){}
	
	public static IDatasetContent createZaharyWeightenedKarateDataset(String filePath,
			String groundTruthFilePath) {
		return new KarateDatasetContent(filePath, groundTruthFilePath);
	}
	
	public static IDatasetContent createSawmillStrikeDataset(String filePath,
			String groundTruthFilePath) {
		return new SawmillStrikeDatasetContent(filePath, groundTruthFilePath);
	}
	
	public static IDatasetContent createNCAAFootballDataset(String filePath,
			String groundTruthFilePath) {
		return new NCAAFootballDatasetContent(filePath, groundTruthFilePath);
	}
	
	public static interface IDatasetContent {
		public String getFilePath();
		public int getVerticesAmount();
		public ClusteredDataset getGroundTruthDataset() throws Exception;
	}
}
